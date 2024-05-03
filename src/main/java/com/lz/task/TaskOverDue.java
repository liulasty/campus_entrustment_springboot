package com.lz.task;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/13/18:46
 * @Description:
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.mapper.TaskMapper;
import com.lz.pojo.Enum.TaskStatus;
import com.lz.pojo.entity.Task;
import com.lz.service.ITaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author lz
 */
@Component
@Slf4j
public class TaskOverDue {
    @Autowired
    private TaskMapper taskMapper;
    //todo 委托过期处理
    @Scheduled(cron = "0/30 * * * * ?")
    public void delegateOverDue() {
        log.info("委托过期");
        QueryWrapper<Task> queryWrapper = new QueryWrapper();
        queryWrapper.eq("STATUS", TaskStatus.ONGOING);
        queryWrapper.le("EndTime", new Date(System.currentTimeMillis()));
        List<Task> tasks = taskMapper.selectList(queryWrapper);
        tasks.forEach(task -> {
            task.setStatus(TaskStatus.EXPIRED);
            taskMapper.updateById(task);
            //todo 通知用户
        });
        log.info("过期任务：" + tasks);
        
    }
}