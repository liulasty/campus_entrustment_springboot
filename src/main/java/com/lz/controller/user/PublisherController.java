package com.lz.controller.user;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/24/15:11
 * @Description:
 */

import com.lz.Exception.MyException;
import com.lz.common.security.AuthenticationService;
import com.lz.pojo.Enum.AuthenticationStatus;
import com.lz.pojo.Enum.TaskStatus;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.dto.PublishDTO;
import com.lz.pojo.entity.Task;
import com.lz.pojo.entity.UsersInfo;
import com.lz.pojo.result.PageResult;
import com.lz.pojo.result.Result;
import com.lz.service.ITaskService;
import com.lz.service.IUsersInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lz
 */
@RestController
@RequestMapping("/user/publisher")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Api("发布者控制器")
public class PublisherController {

    @Autowired
    private IUsersInfoService usersInfoService;

    @Autowired
    private ITaskService taskService;

    @GetMapping("/{id}")
    public Result getPublisher(@PathVariable("id") Long id) throws MyException {
        UsersInfo usersInfo = usersInfoService.getById(id);
        if (usersInfo == null) {
            throw new MyException(MessageConstants.USER_AUTHENTICATION_INFO_NOT_EXIST);
        }
        if (usersInfo.getAuthStatus() != AuthenticationStatus.AUTHENTICATED) {
            //正在认证中，请耐心等待
            throw new MyException(MessageConstants.USER_AUTHENTICATION_INFO_EXISTING);
        }


        return Result.success(usersInfo);
    }

    /**
     * 发布委托
     *
     * @param id 同上
     *
     * @return 结果<字符串>
     *
     * @throws MyException 我的异常
     */
    @PutMapping(value = "/confirmTask/{id}")
    @ApiOperation("发布委托")
    public Result<String> confirmTask(@PathVariable("id") Long id,
                                      @RequestBody PublishDTO data) throws MyException {
        try {
            Task byId = taskService.getById(id);
            if (byId == null) {
                log.error("数据库错误");
                return Result.error(MessageConstants.DATABASE_ERROR);
            }
            if (byId.getStatus() != TaskStatus.PENDING_RELEASE) {
                log.error("任务状态异常");
                return Result.error(MessageConstants.UNEXPECTED_EXCEPTION);
            }
            taskService.updateById(
                    Task.builder()
                            .taskId(id)
                            .startTime(data.getStart())
                            .endTime(data.getEnd())
                            .status(TaskStatus.ONGOING)
                            .build());
            log.info("发布委托成功");
            return Result.success(MessageConstants.TASK_PUBLISH_SUCCESS);
        } catch (Exception e) {
            log.error("发布委托失败");
            throw new MyException(MessageConstants.TASK_PUBLISH_FAIL);
        }

    }

    /**
     * “获取任务”页
     *
     * @param pageNum     页码
     * @param pageSize    页面大小
     * @param location    位置
     * @param description 描述
     * @param taskType    任务类型
     * @param queryRules  查询规则
     * @param status      地位
     *
     * @return 后端统一返回结果
     *
     * @throws MyException 我的异常
     */
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


        PageResult<Task> taskPageResult = taskService.searchPageByPublisher(pageNum,
                                                                            pageSize, location, description,
                                                                            taskType,
                                                                            queryRules, status);

        // 返回响应数据，根据实际情况调整
        return Result.success(taskPageResult);
    }

}