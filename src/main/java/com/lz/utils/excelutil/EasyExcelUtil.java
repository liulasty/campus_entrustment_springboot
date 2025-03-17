package com.lz.utils.excelutil;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * @author LZ
 * @date 2023/08/08
 */
@Slf4j
public class EasyExcelUtil {

    private static final Logger logger = LoggerFactory.getLogger(EasyExcelUtil.class);
    // 最大导出excel数据条数
    public static final int MAX_EXPORT_EXCEL = 200000;

    /**
     * 获取注解的代理实例
     *
     * @param field 字段
     * @return 注解的代理实例
     */
    private static Object getProxyInstance(Field field) {
        // 获取字段上的注解
        ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
        if (excelProperty == null) {
            return null;
        }
        // 获取注解的代理实例
        InvocationHandler handler = Proxy.getInvocationHandler(excelProperty);
        return handler;
    }

    /**
     * 导出文件时为Writer生成OutputStream
     */
    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) {
        //创建本地文件
        try {
            fileName = URLEncoder.encode(fileName,"UTF-8")+".xlsx";
            response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.addHeader("Content-Disposition", "filename=" + fileName);
            return response.getOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    /**
//     * 返回 ExcelReader
//     *
//     * @param excel         需要解析的 Excel 文件
//     * @param excelListener new ExcelListener()
//     */
//    private static ExcelReader getReader(MultipartFile excel,
//                                         ExcelListener excelListener) {
//        String filename = excel.getOriginalFilename();
//        if (filename == null || (!filename.toLowerCase().endsWith(".xls") && !filename.toLowerCase().endsWith(".xlsx"))) {
//            throw new ExcelException("文件格式错误！");
//        }
//        InputStream inputStream;
//        try {
//            inputStream = new BufferedInputStream(excel.getInputStream());
//            return new ExcelReader(inputStream, null, excelListener, false);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    /**
     * 读取 Excel 文件并解析为对象列表
     *
     * @param file       上传的 Excel 文件
     * @param rowModel   Excel 数据映射的 Java 模型类
     * @param sheetNo    要读取的 Sheet 序号（从 0 开始）
     * @param headLineNum 表头行数
     * @param <T>        模型类型
     * @return 解析后的对象列表
     */
    public static <T> List<T> readExcel(MultipartFile file, Class<T> rowModel, int sheetNo, int headLineNum) {
        if (file == null || rowModel == null) {
            log.error("参数不能为空: file={}, rowModel={}", file, rowModel);
            throw new IllegalArgumentException("参数不能为空");
        }
        if (sheetNo < 0) {
            log.error("Sheet 序号不能小于 0: sheetNo={}", sheetNo);
            throw new IllegalArgumentException("Sheet 序号不能小于 0");
        }
        if (headLineNum < 1) {
            log.error("表头行数不能小于 1: headLineNum={}", headLineNum);
            throw new IllegalArgumentException("表头行数不能小于 1");
        }

        // 创建监听器
        ExcelListener<T> listener = new ExcelListener<>();

        try (InputStream inputStream = file.getInputStream()) {
            // 读取 Excel 文件
            ExcelReader excelReader = EasyExcel.read(inputStream)
                    .head(rowModel)
                    .registerReadListener(listener)
                    .build();

            // 读取指定的 Sheet
            ReadSheet readSheet = EasyExcel.readSheet(sheetNo)
                    .headRowNumber(headLineNum)
                    .build();
            excelReader.read(readSheet);

            // 返回解析后的数据
            return listener.getDataList();
        } catch (IOException e) {
            log.error("读取 Excel 文件失败", e);
            throw new RuntimeException("读取 Excel 文件失败", e);
        } catch (ExcelAnalysisException e) {
            log.error("解析 Excel 文件失败", e);
            throw new RuntimeException("解析 Excel 文件失败", e);
        }
    }

    /**
     * Excel 解析监听器
     *
     * @param <T> 模型类型
     */
    private static class ExcelListener<T> extends AnalysisEventListener<T> {

        private final List<T> dataList = new ArrayList<>();

        @Override
        public void invoke(T data, AnalysisContext context) {
            // 逐行解析数据
            dataList.add(data);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            // 解析完成
            log.info("Excel 解析完成，共解析 {} 条数据", dataList.size());
        }

        /**
         * 获取解析后的数据列表
         *
         * @return 数据列表
         */
        public List<T> getDataList() {
            return dataList;
        }
    }

    /**
     * 导出 Excel 文件（单个 Sheet）
     *
     * @param response  HttpServletResponse
     * @param fileName  导出的文件名（无需后缀）
     * @param sheetName Sheet 名称
     * @param title     标题
     * @param data      导出的数据列表
     * @param clazz     数据模型类
     * @param <T>       模型类型
     */
    public static <T> void exportExcel(HttpServletResponse response,
                                       String fileName,
                                       String sheetName,
                                       String title,
                                       List<T> data,
                                       Class<T> clazz) {
        extracted(response, fileName, sheetName, data, clazz);

        try {
            getOutputStream(fileName, response);

            // 创建 ExcelWriter
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), clazz).build();

            // 创建 WriteSheet
            WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();

            // 创建 WriteTable 用于设置标题
            WriteTable writeTable = new WriteTable();
            writeTable.setHead(Collections.singletonList(Collections.singletonList(title))); // 设置标题

            // 先写入标题
            excelWriter.write(Collections.emptyList(), writeSheet, writeTable);

            // 再写入数据
            excelWriter.write(data, writeSheet);

            // 关闭 ExcelWriter
            excelWriter.finish();
        } catch (IOException e) {
            log.error("导出 Excel 文件失败", e);
            throw new RuntimeException("导出 Excel 文件失败", e);
        }
    }

    private static <T> void extracted(HttpServletResponse response, String fileName, String sheetName, List<T> data, Class<T> clazz) {
        if (response == null || fileName == null || sheetName == null || data == null || clazz == null) {
            log.error("参数不能为空");
            throw new IllegalArgumentException("参数不能为空");
        }
        //检查是否超过最大导出数据条数
        int total = data.size();
        if(total > MAX_EXPORT_EXCEL) {
            throw new ExcelException("导出数据超过最大限制");
        }else if(total == 0){
            throw new ExcelException("导出数据为空");
        }
    }

    /**
     * 导出 Excel 文件（单个 Sheet）
     *
     * @param response  HttpServletResponse
     * @param fileName  导出的文件名（无需后缀）
     * @param sheetName Sheet 名称
     * @param title     标题
     * @param data      导出的数据列表
     * @param clazz     数据模型类
     * @param <T>       模型类型
     */
    public static <T> void exportExcelNew(HttpServletResponse response,
                                          String fileName,
                                          String sheetName,
                                          String title,
                                          List<T> data,
                                          Class<T> clazz) {
        extracted(response, fileName, sheetName, data, clazz);

        try {
            // 获取输出流
            getOutputStream(fileName, response);

            // 创建 ExcelWriter
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), clazz).build();

            // 创建 WriteSheet
            WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();

            // 创建 WriteTable 用于设置标题
            WriteTable writeTable = new WriteTable();
            writeTable.setHead(Collections.singletonList(Collections.singletonList(title))); // 设置标题

            // 先写入标题
            excelWriter.write(Collections.emptyList(), writeSheet, writeTable);

            // 再写入数据
            excelWriter.write(data, writeSheet);

            // 关闭 ExcelWriter
            excelWriter.finish();
        } catch (IOException e) {
            log.error("导出 Excel 文件失败", e);
            throw new RuntimeException("导出 Excel 文件失败", e);
        }
    }

    /**
     * 导出 Excel 文件（多个 Sheet）
     *
     * @param response  HttpServletResponse
     * @param fileName  导出的文件名（无需后缀）
     * @param sheetDataList  Sheet 数据列表（每个元素包含 Sheet 名称和数据列表）
     * @param clazz     数据模型类
     * @param <T>       模型类型
     */
    public static <T> void exportExcel(HttpServletResponse response, String fileName, List<SheetData<T>> sheetDataList, Class<T> clazz) {
        if (response == null || fileName == null || sheetDataList == null || clazz == null) {
            throw new IllegalArgumentException("参数不能为空");
        }

        try {
            // 设置响应头
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment;filename=" + encodedFileName + ".xlsx");

            // 创建 ExcelWriter
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), clazz).build();

            // 写入多个 Sheet
            for (SheetData<T> sheetData : sheetDataList) {
                WriteSheet writeSheet = EasyExcel.writerSheet(sheetData.getSheetName()).build();
                excelWriter.write(sheetData.getData(), writeSheet);
            }

            // 关闭 ExcelWriter
            excelWriter.finish();
        } catch (IOException e) {
            log.error("导出 Excel 文件失败", e);
            throw new RuntimeException("导出 Excel 文件失败", e);
        }
    }

    /**
     * Sheet 数据封装类
     *
     * @param <T> 数据模型类型
     */
    public static class SheetData<T> {
        private String sheetName;
        private List<T> data;

        public SheetData(String sheetName, List<T> data) {
            this.sheetName = sheetName;
            this.data = data;
        }

        public String getSheetName() {
            return sheetName;
        }

        public List<T> getData() {
            return data;
        }
    }


}
