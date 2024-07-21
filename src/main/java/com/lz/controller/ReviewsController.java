package com.lz.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.lz.Exception.MyException;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.dto.ReviewsDTO;
import com.lz.pojo.entity.Reviews;
import com.lz.pojo.result.Result;
import com.lz.service.IReviewsService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
    public void exportExcel(HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        log.info("开始导出excel");

        List<Reviews> reviewsList = reviewsService.exportExcel();

        if (reviewsList != null && !reviewsList.isEmpty()) {
            try {
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                String fileName = URLEncoder.encode("reviews", "UTF-8").replaceAll("\\+", "%20");
                response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

                writeExcel(outputStream, reviewsList, "reviews");

                // 如果需要保存到服务器磁盘，取消注释下面的代码
                saveToDisk(reviewsList);

                log.info("导出Excel成功");
            } catch (IOException e) {
                log.error("导出Excel数据失败", e);
                throw e;
            } finally {
                outputStream.close();
                log.info("关闭输出流");
            }
        }
    }

    private void writeExcel(ServletOutputStream outputStream, List<Reviews> reviewsList, String sheetName) throws IOException {
        ExcelWriter excelWriter = EasyExcel.write(outputStream,Reviews.class).build();
        excelWriter.write(reviewsList, EasyExcel.writerSheet(sheetName).build());
        excelWriter.close();
        excelWriter.finish();
    }

    // 可选：保存到服务器磁盘的方法
    private void saveToDisk(List<Reviews> reviewsList) {
        EasyExcel.write("D:\\reviews.xlsx", Reviews.class).excelType(ExcelTypeEnum.XLSX)
                .sheet("评论信息")
                .doWrite(reviewsList);
    }
}