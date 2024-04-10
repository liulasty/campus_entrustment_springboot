package com.lz.controller;


import com.lz.constants.MessageConstants;
import com.lz.pojo.dto.TaskDTO;
import com.lz.pojo.dto.TaskPageDTO;
import com.lz.pojo.entity.Task;
import com.lz.pojo.result.PageResult;
import com.lz.pojo.result.Result;
import com.lz.service.ITaskService;
import com.lz.service.INotificationsService;
import com.lz.utils.ValidateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "委托相关接口")
public class TaskController {
    
    @Autowired
    private ITaskService taskService;
    
    @Autowired
    private INotificationsService notificationService;
    
    //创建单个委托
    @PostMapping("/createTask")
    @ApiOperation("创建单个委托")
    public Result<String> createTask(@RequestBody @Validated TaskDTO taskDTO,
                                     BindingResult result) {
        if(ValidateUtil.validate(result)!= null){
            
            return Result.error(ValidateUtil.validate(result));
        }
        
        // taskService.createTask(taskDTO);
        
        
        return Result.success(MessageConstants.DATA_VALIDATION_SUCCESS);
    }
    
    
    
    @PostMapping("/getTask")
    @ApiOperation("获取任务列表")
    public Result<String> getPendingAuditList() {
        return null;
    }
    
    
    //获取单个委托详细信息请
    @GetMapping("/getTask/{id}")
    @ApiOperation("获取单个委托详细信息")
    public Result<String> getTaskDetail() {
        return null;
    }

    
    @PostMapping("/updateTask")
    @ApiOperation("更新委托审核状态")
    public Result<String> updateTask() {
        return null;
    }
    
    
    @PostMapping("/deleteTask")
    @ApiOperation("删除委托")
    public Result<String> deleteTask() {
        return null;
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
    @GetMapping("/getNewTask")
    @ApiOperation("快速信息展示")
    public Result<String> getNewTask() {
        //系统公告与通知
        // notificationService.getNotifications();
        //最新委托
        // taskService.getNewTask();
        //热门委托
        // taskService.getHotTask();
        //委托成交统计
        // taskService.getTaskStatistics();
        //热门服务类别
        // taskService.getHotService();
        //即将到期委托
        // taskService.getExpireTask();
        return null;
    }
    
    //分页查询
    @PostMapping("/searchPage")
    @ApiOperation("分页查询")
    public Result<PageResult<Task>> searchPage(TaskPageDTO taskPageDTO) {
        PageResult<Task> taskPageResult = taskService.searchPage(taskPageDTO);
        return Result.success(taskPageResult);
    }
}