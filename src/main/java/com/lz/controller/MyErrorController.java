package com.lz.controller;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/06/19:34
 * @Description:
 */

import com.lz.pojo.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lz
 */
@RestController
@RequestMapping()
@Slf4j
@Api(tags = "错误页面处理相关接口")
public class MyErrorController implements ErrorController {

    /**
     * 处理错误
     *
     * @param request 请求
     *
     * @return {@code Result<Map<String, Object>>}
     */
    @RequestMapping("/error")
    @ApiOperation("处理错误")
    public Result<Map<String, Object>> handleError(HttpServletRequest request) {
        log.info("进入错误处理");
        String exception =  (String) request.getAttribute("exception");
        
        return Result.error(exception);
    }
    
}