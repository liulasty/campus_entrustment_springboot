package com.lz.controller.user;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/03/13:47
 * @Description:
 */

import com.lz.Exception.MyException;
import com.lz.pojo.Enum.TaskStatus;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.dto.AcceptDTO;
import com.lz.pojo.entity.Task;
import com.lz.pojo.result.PageResult;
import com.lz.pojo.result.Result;
import com.lz.pojo.vo.TaskAcceptRecord;
import com.lz.service.ITaskAcceptRecordsService;
import com.lz.service.ITaskService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lz
 */
@RestController
@RequestMapping("/user/accept")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Api("接收者控制器")
public class AcceptController {
    @Autowired
    private ITaskAcceptRecordsService taskAcceptRecordsService;

    @Autowired
    private ITaskService taskService;

    @PostMapping
    public Result accept(@RequestBody AcceptDTO acceptDTO) {
        log.info("接收委托留言 {}", acceptDTO);
        taskAcceptRecordsService.create(acceptDTO);
        return Result.success(MessageConstants.DATA_ACCEPT_SUCCESS);
    }

    @GetMapping("/page")
    public Result getTaskPage(
            @RequestParam(defaultValue = "1") int pageNum, // 默认值为1，如果请求中未提供则使用此默认值
            @RequestParam(defaultValue = "10") int pageSize, // 默认每页大小为10
            @RequestParam(required = false) String location, // 类型阶段参数
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long taskType,
            @RequestParam(defaultValue = "0") Integer queryRules,
            @RequestParam(required = false) TaskStatus status
    ) throws MyException {
        // 这里处理业务逻辑，比如根据pageNum, pageSize, TypePhase查询数据库等


        PageResult<TaskAcceptRecord> taskPageResult =
                taskService.searchPageByAcceptor(pageNum,
                                                 pageSize, location, description,
                                                 taskType,
                                                 queryRules, status);

        // 返回响应数据，根据实际情况调整
        return Result.success(taskPageResult);
    }
}