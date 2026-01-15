package com.lz.controller;

import com.lz.pojo.result.ErrorCode;
import com.lz.pojo.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lz
 */
@RestController
@RequestMapping()
@Slf4j
@Api(tags = "错误页面处理相关接口")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class MyErrorController implements ErrorController {

    /**
     * 处理错误
     *
     * @param request 请求
     * @return {@code Result<String>}
     */
    @RequestMapping("/error")
    @ApiOperation("处理错误")
    public Result<String> handleError(HttpServletRequest request) {
        log.info("进入错误处理 URL: {}", request.getRequestURI());
        
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String exception = (String) request.getAttribute("exception");
        String message = (String) request.getAttribute("javax.servlet.error.message");
        
        if (exception == null) {
            exception = message;
        }

        if (statusCode == null) {
            return Result.error(ErrorCode.SYSTEM_ERROR, exception != null ? exception : "未知错误");
        }

        if (statusCode == 404) {
            return Result.error(ErrorCode.NOT_FOUND);
        } else if (statusCode == 401) {
             return Result.error(ErrorCode.UNAUTHORIZED);
        } else if (statusCode == 403) {
             return Result.error(ErrorCode.FORBIDDEN);
        }
        
        return Result.error(ErrorCode.SYSTEM_ERROR, exception != null ? exception : "系统内部错误");
    }
}
