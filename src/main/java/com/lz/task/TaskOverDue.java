package com.lz.task;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/13/18:46
 * @Description:
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.mapper.TaskAcceptRecordsMapper;
import com.lz.mapper.TaskMapper;
import com.lz.pojo.Enum.TaskStatus;
import com.lz.pojo.entity.Task;
import com.lz.service.INotificationsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
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

    @Autowired
    private INotificationsService notificationsService;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Autowired
    private TaskAcceptRecordsMapper taskAcceptRecordsMapper;
    //todo 委托过期处理
    @Scheduled(cron = "0/30 * * * * ?")
    public void delegateOverDue() {
        log.info("委托过期检查...");
        // 1. Check ONGOING tasks (Recruitment expired)
        QueryWrapper<Task> queryWrapper = new QueryWrapper();
        queryWrapper.eq("STATUS", TaskStatus.ONGOING);
        queryWrapper.le("EndTime", new Date(System.currentTimeMillis()));
        List<Task> tasks = taskMapper.selectList(queryWrapper);
        if (!tasks.isEmpty()) {
            tasks.forEach(task -> {
                task.setStatus(TaskStatus.EXPIRED);
                taskMapper.updateById(task);
                taskAcceptRecordsMapper.expireProcess(task.getTaskId());
                // 通知用户
                notificationsService.sendTaskNotification(
                        "您的委托已过期",
                        "您的委托 [" + (task.getDescription().length() > 10 ? task.getDescription().substring(0, 10) + "..." : task.getDescription()) + "] 因超过截止时间已自动关闭。",
                        task.getTaskId(),
                        task.getOwnerId()
                );
            });
            log.info("处理招募过期任务数量：" + tasks.size());
        }

        // 2. Check ACCEPTED tasks (Execution timeout)
        QueryWrapper<Task> acceptedWrapper = new QueryWrapper();
        acceptedWrapper.eq("STATUS", TaskStatus.ACCEPTED);
        acceptedWrapper.le("EndTime", new Date(System.currentTimeMillis()));
        List<Task> acceptedTasks = taskMapper.selectList(acceptedWrapper);
        if (!acceptedTasks.isEmpty()) {
            acceptedTasks.forEach(task -> {
                // Mark as UNFINISHED
                task.setStatus(TaskStatus.UNFINISHED);
                taskMapper.updateById(task);
                
                // Notify Owner
                notificationsService.sendTaskNotification(
                        "委托任务超时未完成",
                        "您的委托 [" + (task.getDescription().length() > 10 ? task.getDescription().substring(0, 10) + "..." : task.getDescription()) + "] 已超过截止时间，接收者未提交完成，系统已标记为未完成。",
                        task.getTaskId(),
                        task.getOwnerId()
                );
                
                // Notify Receiver
                if (task.getReceiverId() != null) {
                    notificationsService.sendTaskNotification(
                        "接收任务超时",
                         "您接收的委托 [" + (task.getDescription().length() > 10 ? task.getDescription().substring(0, 10) + "..." : task.getDescription()) + "] 已超过截止时间，系统已标记为未完成。",
                        task.getTaskId(),
                        task.getReceiverId()
                    );
                }
            });
            log.info("处理执行超时任务数量：" + acceptedTasks.size());
        }
        
    }
}