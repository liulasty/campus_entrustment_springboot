package com.lz.controller;


import com.lz.Exception.MyException;
import com.lz.pojo.Enum.TaskStatus;
import com.lz.pojo.constants.AuditResult;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.dto.AuditResultDTO;
import com.lz.pojo.dto.TaskDTO;
import com.lz.pojo.dto.TaskDraftDTO;
import com.lz.pojo.dto.TaskPageDTO;
import com.lz.pojo.entity.DelegateAuditRecords;
import com.lz.pojo.entity.Task;
import com.lz.pojo.entity.UsersInfo;
import com.lz.pojo.result.NameAndDescription;
import com.lz.pojo.result.PageResult;
import com.lz.pojo.result.Result;
import com.lz.pojo.vo.AuditResultVO;
import com.lz.pojo.vo.NewestInfoVO;
import com.lz.pojo.vo.TaskDraftVO;
import com.lz.pojo.vo.UserDelegateDraft;
import com.lz.service.*;
import com.lz.utils.ValidateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 存储任务相关信息 前端控制器
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Api(tags = "委托相关接口")
@Slf4j
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @Autowired
    private IUsersService usersService;

    @Autowired
    private IUsersInfoService usersInfoService;

    @Autowired
    private INotificationsService notificationService;

    @Autowired
    private IDelegationCategoriesService delegationCategoriesService;

    @Autowired
    private IDelegateAuditRecordsService delegateAuditRecordsService;


    //创建单个委托
    @PostMapping("/addTaskDraft")
    @ApiOperation("创建单个委托草稿")
    public Result<String> createTask(@RequestBody @Validated TaskDTO taskDTO,
                                     BindingResult result) throws MyException {
        if (ValidateUtil.validate(result) != null) {

            return Result.error(ValidateUtil.validate(result));
        }

        taskService.createTask(taskDTO);


        return Result.success(MessageConstants.TASK_CREATE_SUCCESS);
    }


    @PostMapping("/getTask")
    @ApiOperation("获取任务列表")
    public Result<String> getPendingAuditList() {
        return null;
    }


    //获取单个委托详细信息请
    @GetMapping("/getTask/{id}")
    @ApiOperation("获取单个委托详细信息")
    public Result<TaskDraftVO> getTaskDetail(@PathVariable("id") Long id) throws MyException {
        TaskDraftVO taskDraftVO = taskService.searchTask(id);
        return Result.success(taskDraftVO);
    }


    @PostMapping("/updateTaskDraft")
    @ApiOperation("更新委托草稿信息")
    public Result<String> updateTask(@RequestBody TaskDraftDTO taskDTO) throws MyException {
        try {
            Task byId = taskService.getById(taskDTO.getTaskId());
            if (byId == null) {
                throw new MyException(MessageConstants.DATA_VALIDATION_ERROR);
            }
            Task task = Task.builder().taskId(taskDTO.getTaskId())
                    .type(taskDTO.getType())
                    .description(taskDTO.getDescription())
                    .location(taskDTO.getLocation())
                    .status(TaskStatus.DRAFT)
                    .createdAt(taskDTO.getCreatedAt()).build();

            taskService.updateById(task);
            return Result.success(MessageConstants.TASK_UPDATE_SUCCESS);
        } catch (Exception e) {
            throw new MyException(MessageConstants.UNEXPECTED_EXCEPTION);
        }
    }

    @DeleteMapping(value = "/deleteTaskDraft/{id}")
    @ApiOperation("删除委托草稿")
    public Result<String> deleteTaskDraft(@PathVariable("id") Long id) throws MyException {
        try {
            Task byId = taskService.getById(id);
            if (byId == null) {
                throw new MyException(MessageConstants.DATA_VALIDATION_ERROR);
            }
            taskService.removeById(id);
            return Result.success(MessageConstants.TASK_UPDATE_SUCCESS);
        } catch (Exception e) {
            throw new MyException(MessageConstants.UNEXPECTED_EXCEPTION);
        }
    }


    @PostMapping("/deleteTask")
    @ApiOperation("删除委托")
    public Result<String> deleteTask() {

        return null;
    }

    /**
     * 提交审核任务
     *
     * @param id 同上
     *
     * @return 结果<字符串>
     *
     * @throws MyException 我的异常
     */
    @PutMapping(value = "/auditTask/{id}")
    @ApiOperation("去审核")
    public Result<String> auditTask(@PathVariable("id") Long id) throws MyException {
        try {
            Task byId = taskService.getById(id);
            if (byId == null) {
                return Result.error(MessageConstants.DATABASE_ERROR);
            }
            if (byId.getStatus() != TaskStatus.DRAFT) {
                return Result.error(MessageConstants.UNEXPECTED_EXCEPTION);
            }
            taskService.updateById(Task.builder().taskId(id).status(TaskStatus.AUDITING).build());
            return Result.success(MessageConstants.DATA_VALIDATION_SUCCESS);
        } catch (Exception e) {
            throw new MyException(MessageConstants.UNEXPECTED_EXCEPTION);
        }

    }

    /**
     * 获取需要发布的委托
     *
     * @param id 同上
     *
     * @return 结果<字符串>
     *
     * @throws MyException 我的异常
     */
    @GetMapping(value = "/confirmTask/{id}")
    @ApiOperation("获取需要发布的委托")
    public Result<Task> confirmTask(@PathVariable("id") Long id) throws MyException {
        try {
            Task byId = taskService.getById(id);
            if (byId == null) {
                return Result.error(MessageConstants.DATABASE_ERROR);
            }
            if (byId.getStatus() != TaskStatus.PENDING_RELEASE) {
                return Result.error(MessageConstants.UNEXPECTED_EXCEPTION);
            }

            return Result.success(byId, MessageConstants.TASK_INFO_SUCCESS);
        } catch (Exception e) {
            throw new MyException(MessageConstants.TASK_PUBLISH_FAIL);
        }

    }

    /**
     * 用户取消发布
     *
     * @param id 同上
     *
     * @return 结果<字符串>
     *
     * @throws MyException 我的异常
     */
    @PutMapping(value = "/cancelTaskByUser/{id}")
    @ApiOperation("用户取消发布")
    public Result<String> cancelTask(@PathVariable("id") Long id) throws MyException {
        try {
            Task byId = taskService.getById(id);
            if (byId == null) {
                return Result.error(MessageConstants.DATABASE_ERROR);
            }
            if (byId.getStatus() != TaskStatus.ONGOING) {
                return Result.error(MessageConstants.UNEXPECTED_EXCEPTION);
            }
            taskService.updateById(Task.builder().taskId(id).status(TaskStatus.DRAFT).build());
            return Result.success(MessageConstants.TASK_CANCEL_SUCCESS);
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
    }

    /**
     * 提交审核结果
     *
     * @param auditResultDTO 审计结果 DTO
     *
     * @return 结果<字符串>
     *
     * @throws MyException 我的异常
     */
    @PostMapping(value = "/auditResult")
    @ApiOperation("提交审核结果")
    public Result<String> auditResult(@RequestBody AuditResultDTO auditResultDTO) throws MyException {
        try {
            Task byId = taskService.getById(auditResultDTO.getDelegateId());
            if (byId == null) {
                return Result.error(MessageConstants.DATABASE_ERROR);
            }
            if (byId.getStatus() != TaskStatus.AUDITING) {
                return Result.error(MessageConstants.UNEXPECTED_EXCEPTION);
            }
            taskService.updateTask(auditResultDTO);


            return Result.success(MessageConstants.DATA_AUDIT_SUCCESS);
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }

    }

    /**
     * 查询审核未通过原因
     *
     * @param id 同上
     *
     * @return 结果<审计结果 vo>
     *
     * @throws MyException 我的异常
     */
    @GetMapping("/getReason/{id}")
    @ApiOperation("获取审核未通过原因")
    public Result<AuditResultVO> getReason(@PathVariable("id") Long id) throws MyException {
        log.info("获取审核未通过原因 {}", id);
        Task byId = taskService.getById(id);
        if (byId == null) {
            throw new MyException(MessageConstants.DATABASE_ERROR);
            
        }
        if (byId.getStatus() != TaskStatus.AUDIT_FAILED) {
            throw new MyException(MessageConstants.UNEXPECTED_EXCEPTION);
            
        }
        DelegateAuditRecords failReason =
                delegateAuditRecordsService.getFailReasonById(id);
        log.info("failReason {} {}", failReason, byId.getOwnerId());
        UsersInfo usersInfo = usersInfoService.getById(byId.getOwnerId());
        log.info("userInfo {}", usersInfo);
        AuditResultVO auditResultVO = AuditResultVO.builder()
                .reviewStatus(AuditResult.REJECTED)
                .reviewComment(failReason.getReviewComment())
                .reviewTime(failReason.getReviewTime())
                .name(usersInfo.getName())
                .build();

        return Result.success(auditResultVO);

    }


    @PostMapping("/searchTask")
    @ApiOperation("搜索待审核委托")
    public Result<String> searchTask() {
        return null;
    }


    @PostMapping("/getHistory")
    @ApiOperation("获取审核历史记录")
    public Result<String> getHistory() {
        return null;
    }

    //最新发布的委托列表（简要信息）
    @GetMapping("/getNewTask/{id}")
    @ApiOperation("快速信息展示")
    public Result<NewestInfoVO> getNewTask(@PathVariable Long id) {
        // 系统公告与通知
        NewestInfoVO newestInfo = taskService.getNewestInfo(id);

        return Result.success(newestInfo);
    }

    //分页查询
    @PostMapping("/searchPage")
    @ApiOperation("分页查询")
    public Result<PageResult<Task>> searchPage(TaskPageDTO taskPageDTO) {
        PageResult<Task> taskPageResult = taskService.searchPage(taskPageDTO);
        return Result.success(taskPageResult);
    }

    //获取委托分类
    @GetMapping("/getTaskCategory")
    @ApiOperation("获取委托分类")
    public Result<List<NameAndDescription>> getTaskCategory() throws MyException {
        List<NameAndDescription> map =
                delegationCategoriesService.getTaskCategory();
        log.info("list:{}", map);
        return Result.success(map);
    }

    //获取用户委托草稿
    @GetMapping("/getUserDelegateDraft/{userId}")
    @ApiOperation("获取用户委托草稿")
    public Result<List<UserDelegateDraft>> getUserDelegateDraft(@PathVariable Long userId) {
        List<UserDelegateDraft> tasksWithUser = taskService.getUserDelegateDraft(userId);
        return Result.success(tasksWithUser);
    }
}