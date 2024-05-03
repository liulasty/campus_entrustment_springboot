package com.lz.controller.admin;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/21/17:59
 * @Description:
 */

import com.lz.Exception.MyException;
import com.lz.pojo.Enum.TaskPhase;
import com.lz.pojo.Enum.TaskStatus;
import com.lz.pojo.Enum.TaskUpdateType;
import com.lz.pojo.Page.DraftConfig;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.entity.Task;
import com.lz.pojo.entity.Taskupdates;
import com.lz.pojo.entity.Users;
import com.lz.pojo.result.PageResult;
import com.lz.pojo.result.Result;
import com.lz.service.IDelegateAuditRecordsService;
import com.lz.service.ITaskService;
import com.lz.service.ITaskUpdatesService;
import com.lz.service.IUsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * @author lz
 */
@RestController
@RequestMapping("/admin/task")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Api(tags = "管理员管理委托相关接口")
@Slf4j
public class TaskAdminController {

    private final ITaskService taskService;
    private final IDelegateAuditRecordsService delegateAuditRecordsService;
    private final IUsersService usersService;
    private final ITaskUpdatesService taskUpdatesService;
    

    /**
     * 获取当前登录管理员的信息。
     *
     * @return 当前登录管理员的用户实体（Users）
     */


    @Autowired
    public TaskAdminController(ITaskService taskService,
                               IDelegateAuditRecordsService delegateAuditRecordsService,
                               IUsersService usersService,
                               ITaskUpdatesService taskupdatesService) {
        this.taskService = taskService;
        this.delegateAuditRecordsService = delegateAuditRecordsService;
        this.usersService = usersService;
        this.taskUpdatesService = taskupdatesService;
    }

    /**
     * 获取当前用户信息
     *
     * @return {@code Users}
     */
    public Users getCurrentAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminName = authentication.getName();
        log.info("管理员: {}", adminName);

        return usersService.getByUsername(adminName);
    }

    /**
     * 搜索页面
     * 分页查询
     *
     * @return {@code Result<PageResult<Task>>}
     */
    @GetMapping("/list")
    @ApiOperation("分页查询")
    public Result searchPage(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                             @RequestParam(value = "pageSize",
                                     defaultValue = "5") int pageSize,
                             @RequestParam(value = "Description", required = false) String description,
                             @RequestParam(value = "taskType", required =
                                     false) Integer taskType,
                             @RequestParam(value = "Location", required = false) String location,
                             @RequestParam(value = "CreatedAt", required = false) LocalDate createdAt,
                             @RequestParam(value = "TypePhase", required =
                                     false) String typePhase) {
        log.info("搜索页面{}，{}，{},{},{}", taskType, createdAt, location, typePhase, description);
        DraftConfig draftConfig = new DraftConfig(createdAt, description, location, pageNum, pageSize,
                                                  taskType,
                                                  TaskPhase.fromValue(typePhase));
        PageResult<Task> taskPageResult =
                taskService.searchPageByAdmin(draftConfig);
        return Result.success(taskPageResult);
    }


    @GetMapping("/{TaskID}")
    @ApiOperation("根据id查询")
    public Result searchTask(@PathVariable("TaskID") Long taskID) throws MyException {
        log.info("根据id查询{}", taskID);

        return Result.success(taskService.searchTask(taskID));
    }


    @DeleteMapping("/{TaskID}")
    @ApiOperation("管理员删除委托")
    public Result deleteTask(@PathVariable("TaskID") Long taskID) {
        Task task = taskService.getById(taskID);
        if (task == null || task.getStatus() == TaskStatus.ONGOING) {
            return Result.error(MessageConstants.TASK_NOT_EXIST);
        }

        log.info("管理员删除委托{}", taskID);
        taskService.removeById(taskID);
        Users users = getCurrentAdmin();

        Taskupdates taskupdates = Taskupdates.builder().taskId(taskID)
                .userId(users.getUserId())
                .updateType(TaskUpdateType.RESULT)
                .updateDescription(MessageConstants.TASK_DRAFT_DELETE_SUCCESS).build();
        taskUpdatesService.save(taskupdates);
        //todo 通知用户
        log.info("管理员删除委托成功{}", taskID);
        return Result.success(MessageConstants.TASK_DRAFT_DELETE_SUCCESS);
    }


    /**
     * 回退草稿
     *
     * @param taskId 任务 ID
     *
     * @return 后端统一返回结果
     *
     * @throws MyException 我的异常
     */
    @PutMapping("/getFallbackDraft/{TaskID}")
    public Result fallbackDraft(@PathVariable("TaskID") Long taskId) throws MyException {
        log.info("管理员获取回退草稿{}", taskId);
        Task task = taskService.getById(taskId);
        if (task == null || task.getStatus() == TaskStatus.ONGOING) {
            log.error("回退草稿失败{}", taskId);
            return Result.error(MessageConstants.TASK_NOT_EXIST);
        }

        if (!taskService.fallbackDraft(taskId)) {
            log.error("回退草稿失败{}", taskId);
            return Result.error(MessageConstants.DATABASE_ERROR);
        }

        return Result.success(MessageConstants.TASK_UPDATE_SUCCESS);
    }


    /**
     * 允许发布
     *
     * @param taskId 任务 ID
     *
     * @return 后端统一返回结果
     *
     * @throws MyException 我的异常
     */
    @PutMapping("/allowPublish/{TaskID}")
    public Result allowPublish(@PathVariable("TaskID") Long taskId) throws MyException {
        log.info("管理员允许发布{}", taskId);
        Task task = taskService.getById(taskId);
        if (task == null || task.getStatus() == TaskStatus.ONGOING) {
            log.error("委托信息异常{}", taskId);
            return Result.error(MessageConstants.TASK_NOT_EXIST);
        }

        if (!taskService.allowPublish(taskId)) {
            log.error("委托审核失败{}", taskId);
            return Result.error(MessageConstants.DATABASE_ERROR);
        }
        
        return Result.success(MessageConstants.TASK_UPDATE_SUCCESS);

    }


    /**
     * 不允许
     *
     * @param taskId 任务 ID
     *
     * @return 后端统一返回结果
     *
     * @throws MyException 我的异常
     */
    @PutMapping("/notAllowed/{TaskID}")
    public Result notAllowed(@PathVariable("TaskID") Long taskId) throws MyException {
        log.info("管理员不允许发布{}", taskId);
        Task task = taskService.getById(taskId);
        if (task == null || task.getStatus() == TaskStatus.ONGOING) {
            log.error("委托信息异常{}", taskId);
            return Result.error(MessageConstants.TASK_NOT_EXIST);
        }

        if (!taskService.notAllowed(taskId)) {
            log.error("委托审核失败{}", taskId);
            return Result.error(MessageConstants.DATABASE_ERROR);
        }
        
        return Result.success(MessageConstants.TASK_UPDATE_SUCCESS);
    }
    
    
    
    
    
    
}