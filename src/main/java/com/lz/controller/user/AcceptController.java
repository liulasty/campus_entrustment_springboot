package com.lz.controller.user;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/03/13:47
 * @Description:
 */

import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.dto.AcceptDTO;
import com.lz.pojo.result.Result;
import com.lz.service.ITaskAcceptRecordsService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lz
 */
@RestController
@RequestMapping("/user/Accept")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Api("接收者控制器")
public class AcceptController {
    @Autowired
    private  ITaskAcceptRecordsService taskAcceptRecordsService;
    @PostMapping
    public Result accept(@RequestBody AcceptDTO acceptDTO){
        log.info("接收委托留言 {}",acceptDTO);
        taskAcceptRecordsService.create(acceptDTO);
        return Result.success(MessageConstants.DATA_ACCEPT_SUCCESS);
    }
}