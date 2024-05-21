package com.lz.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.Exception.MyException;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.entity.DelegationCategories;
import com.lz.pojo.entity.Task;
import com.lz.pojo.result.PageResult;
import com.lz.pojo.result.Result;
import com.lz.service.IDelegationCategoriesService;
import com.lz.service.ITaskService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用于存储委托类别常量的表 前端控制器
 * </p>
 *
 * @author lz
 * @since 2024-04-10
 */
@RestController
@RequestMapping("/delegation_categories")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Slf4j
@Api(tags = "委托类别常量控制器")
public class DelegationCategoriesController {

    @Autowired
    private IDelegationCategoriesService delegationCategoriesService;
    
    @Autowired
    private ITaskService taskService;
    @GetMapping("/list")
    public Result list(@RequestParam(value = "pageNum", defaultValue = "1") Long pageNum,
                       @RequestParam(value = "pageSize", defaultValue = "5") Long pageSize,
                       @RequestParam(value = "categoryName",
                               required = false) String categoryName,
                       @RequestParam(value = "isEnable",required = false) Boolean isEnable,
                       @RequestParam(value = "description",required = false) String description) {
        Page<DelegationCategories> page = new Page<>(pageNum, pageSize);
        PageResult pageResult =  delegationCategoriesService.list(page, categoryName,
                                                     isEnable,
                                                    description);
        return Result.success(pageResult);
        
    }
    
    @GetMapping("/{id}")
    public Result getById(@PathVariable("id") Long id) {
        DelegationCategories delegationCategories = delegationCategoriesService.getById(id);
        return Result.success(delegationCategories);
    }
    
    @PutMapping()
    public Result update(@RequestBody DelegationCategories delegationCategories) {
        delegationCategoriesService.updateById(delegationCategories);
        return Result.success(MessageConstants.TASK_CATEGORY_UPDATE_SUCCESS);
    }
    
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
        List<Task> list = taskService.getTaskByCategoryId(id);
        if (list.size() != 0) {
            return Result.error(MessageConstants.TASK_CATEGORY_DELETE_FAIL+"该类别下存在任务，无法删除");
        }
        delegationCategoriesService.removeById(id);
        return Result.success(MessageConstants.TASK_CATEGORY_DELETE_SUCCESS);
    }
    
    @PostMapping()
    public Result save(@RequestBody DelegationCategories delegationCategories) {
        delegationCategoriesService.save(delegationCategories);
        return Result.success(MessageConstants.TASK_CATEGORY_ADD_SUCCESS);
    }
    
    @PutMapping("/enable/{id}")
    public Result enable(@PathVariable("id") Long id) throws MyException {
        delegationCategoriesService.enable(id);
        return Result.success(MessageConstants.TASK_CATEGORY_UPDATE_SUCCESS);
    }
}