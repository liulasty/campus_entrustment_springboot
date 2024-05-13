package com.lz.controller.user;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/29/17:18
 * @Description:
 */

import com.lz.Exception.MyException;
import com.lz.pojo.Enum.TaskStatus;
import com.lz.pojo.entity.Task;
import com.lz.pojo.result.NameAndDescription;
import com.lz.pojo.result.PageResult;
import com.lz.pojo.result.Result;
import com.lz.pojo.vo.TaskAndUserInfoVO;
import com.lz.service.IDelegationCategoriesService;
import com.lz.service.ITaskService;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 任务控制器
 *
 * @author lz
 * @date 2024/04/29
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/user/task")
@ApiModel(value = "任务控制器")
public class TaskUserController {
    @Autowired
    private ITaskService taskService;
    
    @Autowired
    private IDelegationCategoriesService delegationCategoriesService;
    
    @GetMapping("/page")
    public Result getTaskPage(
            @RequestParam(defaultValue = "1") int pageNum, // 默认值为1，如果请求中未提供则使用此默认值
            @RequestParam(defaultValue = "10") int pageSize, // 默认每页大小为10
            @RequestParam(required = false) String location, // 类型阶段参数
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long taskType,
            @RequestParam(defaultValue = "0") Integer queryRules,
            @RequestParam(required = false) TaskStatus status
    ) {
        // 这里处理业务逻辑，比如根据pageNum, pageSize, TypePhase查询数据库等


        PageResult<Task> taskPageResult = taskService.searchPage(pageNum, pageSize, location, description,
                                                                 taskType,
                                                                 queryRules,status);

        // 返回响应数据，根据实际情况调整
        return Result.success(taskPageResult);
    }

    /**
     * @param id
     *
     * @return 后端统一返回结果
     *
     * @throws MyException 我的异常
     */
    @GetMapping("/{id}")
    public Result getTask(@PathVariable("id") Long id) throws MyException {
        TaskAndUserInfoVO taskAndUserInfo =  taskService.getTaskAndPublisherInfo(id);
        
        return Result.success(taskAndUserInfo);
    }

    
        @GetMapping("/categories")
        public Result getTaskCategory() throws MyException {
            List<NameAndDescription> taskCategory = delegationCategoriesService.getTaskCategoryUser();
            return Result.success(taskCategory);
        }
}