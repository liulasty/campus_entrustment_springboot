package com.lz.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.Exception.MyException;
import com.lz.pojo.Enum.TaskUpdateType;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.dto.TaskUpdateDTO;
import com.lz.pojo.entity.TaskUpdates;
import com.lz.pojo.result.PageResult;
import com.lz.pojo.result.Result;
import com.lz.service.ITaskUpdatesService;
import com.lz.utils.EnumUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

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
@Api(tags = "任务更新相关接口")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE })
public class TaskUpdatesController {

    @Autowired
    private ITaskUpdatesService taskUpdateService;

    @GetMapping("/getTask/{id}")
    public Result<?> getTask(@PathVariable("id") Long id) {
        TaskUpdates taskUpdates = taskUpdateService.getById(id);
        return Result.success(taskUpdates);
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "DelegateComment", required = false) String DelegateComment,
            @RequestParam(value = "ReviewStatus", required = false) String ReviewStatus,
            @RequestParam(value = "ReviewTime", required = false) @DateTimeFormat(fallbackPatterns = "yyyy-MM-dd") Date ReviewTime

    ) {
        Page<TaskUpdates> page = new Page<>(pageNum, pageSize);
        log.info("delegateComment: {}, reviewStatus: {}, reviewTime: {}", DelegateComment,
                ReviewStatus, ReviewTime);

        IPage<TaskUpdates> taskUpdatesPage = taskUpdateService.page(page, DelegateComment, ReviewStatus, ReviewTime);

        return Result.success(
                new PageResult<>(taskUpdatesPage.getTotal(), taskUpdatesPage.getRecords()));
    }

    @GetMapping("/type")
    public Result<?> getType() {
        Map<String, String> map = EnumUtils.generateKeyValues(TaskUpdateType.values());
        return Result.success(map);
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteRecords(@PathVariable("id") Long id) {
        log.info("删除更新记录 {}", id);
        taskUpdateService.removeById(id);

        return Result.success(MessageConstants.TASK_RECORDS_DELETE_SUCCESS);
    }

    @PostMapping("/add")
    @ApiOperation("添加任务进度更新")
    public Result<TaskUpdates> addUpdate(@RequestBody TaskUpdateDTO taskUpdateDTO)
            throws MyException {
        TaskUpdates taskUpdates = taskUpdateService.addUpdate(taskUpdateDTO);
        return Result.success(taskUpdates, MessageConstants.TASK_UPDATE_SUCCESS);
    }
}
