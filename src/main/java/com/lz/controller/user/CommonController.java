package com.lz.controller.user;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/06/10/22:44
 * @Description:
 */

import com.lz.context.BaseContext;
import com.lz.pojo.entity.IpLogs;
import com.lz.pojo.result.Result;
import com.lz.service.IpLogsService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author lz
 */
@RestController
@RequestMapping("/common")
@Api(tags = "公共控制器")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CommonController {
    
    @Autowired
    private IpLogsService ipLogsService;
    /**
     * 获取当前登录用户的IP地址
     *
     * @return
     */
    @GetMapping("/getUserIp")
    public Result getIp( ServletRequest request) {
        if (request != null) {
            
            Long currentId = BaseContext.getCurrentId(); 
            log.info("当前登录用户ID：{}", currentId);
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String clientIp = httpRequest.getRemoteAddr(); // 获取客户端IP地址
            log.info("客户端IP地址：{}", clientIp);

            IpLogs ipLogs = IpLogs.builder().ip(clientIp)
                    .userAgent(httpRequest.getHeader("User-Agent"))
                    .visitTime(new Date(System.currentTimeMillis()))
                    .build();
            ipLogsService.save(ipLogs);
            return Result.success();
        }
        if (BaseContext.getCurrentId() != null) {
            log.info("当前登录用户ID：{}", BaseContext.getCurrentId());
            return Result.success();
        }
        
        return Result.error("获取IP地址失败");
    }
}