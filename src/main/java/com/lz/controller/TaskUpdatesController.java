package com.lz.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.pojo.Enum.TaskUpdateType;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.entity.TaskUpdates;
import com.lz.pojo.result.PageResult;
import com.lz.pojo.result.Result;
import com.lz.service.ITaskUpdatesService;
import com.lz.utils.EnumUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 记录任务的更新情况 前端控制器
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@RestController
@RequestMapping("/taskUpdate")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class TaskUpdatesController {


    @Autowired
    private ITaskUpdatesService taskUpdateService;

    
    @GetMapping("/getTask/{id}")
    public Result getTask(@PathVariable("id") Long id) {
        TaskUpdates taskUpdates = taskUpdateService.getById(id);
        return Result.success(taskUpdates);
    }

    @GetMapping("/list")
    public Result list(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "DelegateComment", required = false) String DelegateComment,
            @RequestParam(value = "ReviewStatus", required = false) String ReviewStatus,
            @RequestParam(value = "ReviewTime", required = false) @DateTimeFormat(fallbackPatterns = "yyyy-MM-dd") Date ReviewTime

    ) {
        Page<TaskUpdates> page = new Page<>(pageNum, pageSize);
        log.info("delegateComment: {}, reviewStatus: {}, reviewTime: {}", DelegateComment, ReviewStatus, ReviewTime);

        IPage<TaskUpdates> taskUpdatesPage = taskUpdateService.page(page,
                                                                    DelegateComment, ReviewStatus, ReviewTime);

        return Result.success(new PageResult<>(taskUpdatesPage.getTotal(),
                                               taskUpdatesPage.getRecords()));
    }

    @GetMapping("/type")
    public Result getType() {
        Map<String, String> map = EnumUtils.generateKeyValues(TaskUpdateType.values());
        return Result.success(map);
    }
    
    @DeleteMapping("/{id}")
    public Result deleteRecords(@PathVariable("id") Long id){
        log.info("删除更新记录 {}",id);
        taskUpdateService.removeById(id);
        
        return Result.success(MessageConstants.TASK_RECORDS_DELETE_SUCCESS);
    }
}