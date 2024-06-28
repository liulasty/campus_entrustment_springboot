package com.lz.controller;


import com.alibaba.excel.EasyExcel;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.dto.ReviewsDTO;
import com.lz.pojo.entity.Reviews;
import com.lz.pojo.result.Result;
import com.lz.service.IReviewsService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    
    //导出excel
    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        log.info("导出excel");
        List<Reviews> reviewsList = reviewsService.exportExcel();
        if (reviewsList != null) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            
            String fileName = URLEncoder.encode("reviews", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            
            EasyExcel.write(response.getOutputStream(), Reviews.class)
                    .sheet("reviews").doWrite(reviewsList);
            
            // 保存到服务器磁盘（这部分根据实际需要决定是否保留）
            EasyExcel.write("D:\\reviews.xlsx", Reviews.class)
                    .sheet("评论信息").doWrite(reviewsList);
            
            log.info("导出Excel成功 {}", reviewsList);
        }
    }
}