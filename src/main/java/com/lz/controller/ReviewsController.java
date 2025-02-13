package com.lz.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.lz.Exception.MyException;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.dto.ReviewsDTO;
import com.lz.pojo.entity.Reviews;
import com.lz.pojo.result.Result;
import com.lz.service.IReviewsService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * <p>
 * 存储用户对任务的评价信息 前端控制器
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Api(tags = "评论控制器")
@Slf4j
public class ReviewsController {
    @Autowired
    private IReviewsService reviewsService;
    
    @PostMapping("/addReviews")
    public Result addReviews(@RequestBody ReviewsDTO reviewsDTO) {
        reviewsService.save(reviewsDTO);
        return Result.success(MessageConstants.REVIEWS_ADD_SUCCESS);
    }

    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) throws IOException, MyException {
        ServletOutputStream outputStream = response.getOutputStream();
        log.info("开始导出excel");

        List<Reviews> reviewsList = reviewsService.exportExcel();

        // 检查返回的列表是否为空或null，并处理其中的null元素
        if (reviewsList == null) {
            throw new MyException(MessageConstants.REVIEWS_EXPORT_FAIL);
        }
        reviewsList.replaceAll(review -> review == null ? new Reviews() : review);

        if (reviewsList.isEmpty()) {
            throw new MyException(MessageConstants.REVIEWS_EXPORT_FAIL);
        }

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            // 对文件名进行安全处理
            String safeFileName = FilenameUtils.getName(URLEncoder.encode("reviews", StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20"));
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + safeFileName + ".xlsx");

            writeExcel(outputStream, reviewsList, "reviews");

            // 如果需要保存到服务器磁盘，取消注释下面的代码，并确保路径来自配置
            // saveToDisk(reviewsList);

            log.info("导出Excel成功");
        } catch (IOException e) {
            log.error("导出Excel数据失败" );
            throw new MyException(MessageConstants.REVIEWS_EXPORT_FAIL);
        } finally {
            // 确保输出流被正确关闭
            try {
                outputStream.close();
            } catch (IOException e) {
                log.error("关闭输出流失败", e);
            }
            log.info("关闭输出流");
        }
    }

    private void writeExcel(ServletOutputStream outputStream, List<Reviews> reviewsList, String sheetName) throws IOException {
        ExcelWriter excelWriter = EasyExcel.write(outputStream, Reviews.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
        excelWriter.write(reviewsList, writeSheet);
        // 确保在finally块中调用finish，以避免在关闭时发生异常
        try {
            excelWriter.finish();
        } finally {
            excelWriter.close();
        }
    }

    // 可选：保存到服务器磁盘的方法，路径应来自配置
    private void saveToDisk(List<Reviews> reviewsList) {
        // 假设路径来自配置
        String savePath = "D:\\reviews.xlsx";
        EasyExcel.write(savePath, Reviews.class).excelType(ExcelTypeEnum.XLSX)
                .sheet("评论信息")
                .doWrite(reviewsList);
    }
}