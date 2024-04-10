package com.lz.service.impl;

import com.lz.mapper.ReviewsMapper;
import com.lz.mapper.UsersMapper;
import com.lz.pojo.entity.Task;
import com.lz.mapper.TaskMapper;
import com.lz.service.ITaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 存储任务相关信息 服务实现类
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@Service
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
}