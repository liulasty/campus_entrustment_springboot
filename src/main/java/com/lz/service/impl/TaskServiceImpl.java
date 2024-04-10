package com.lz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.mapper.ReviewsMapper;
import com.lz.mapper.UsersMapper;
import com.lz.pojo.Enum.TaskStatus;
import com.lz.pojo.dto.TaskPageDTO;
import com.lz.pojo.entity.Task;
import com.lz.mapper.TaskMapper;
import com.lz.pojo.result.PageResult;
import com.lz.service.ITaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 存储任务相关信息 服务实现类
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@Service
@Slf4j
@Transactional
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {
    
    @Autowired
    private UsersMapper usersMapper;
    
    @Autowired
    private TaskMapper taskMapper;
    @Override
    public void addTask() {
        
    }

    @Override
    public void updateTask() {

    }

    @Override
    public void deleteTask() {

    }
    
    @Override
    public void searchTask(Long id) {
        Task task = taskMapper.selectById(id);
        System.out.println(task);
    }

    @Override
    public PageResult<Task> searchPage(TaskPageDTO taskPageDTO) {
        Page<Task> page = new Page<>(taskPageDTO.getPageNum(),
                                     taskPageDTO.getPageSize());

        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", TaskStatus.AUDITING);
        queryWrapper.like("description",taskPageDTO.getDescription());
        queryWrapper.like("location",taskPageDTO.getLocation());
        log.info("查询条件：{}",queryWrapper);
        Page<Task> taskPage = page(page, queryWrapper);
        

        PageResult<Task> pageResult = new PageResult<>();
        
        pageResult.setTotal(taskPage.getTotal());
        pageResult.setRecords(taskPage.getRecords());
        log.info("分页结果：{}",pageResult);
        
        return pageResult;
    }
    

}