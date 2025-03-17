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
    private RabbitTemplate rabbitTemplate;
    
    @Autowired
    private TaskAcceptRecordsMapper taskAcceptRecordsMapper;
    //todo 委托过期处理
//    @Scheduled(cron = "0/30 * * * * ?")
    public void delegateOverDue() {
        log.info("委托过期");
        QueryWrapper<Task> queryWrapper = new QueryWrapper();
        queryWrapper.eq("STATUS", TaskStatus.ONGOING);
        queryWrapper.le("EndTime", new Date(System.currentTimeMillis()));
        List<Task> tasks = taskMapper.selectList(queryWrapper);
        tasks.forEach(task -> {
            task.setStatus(TaskStatus.EXPIRED);
            taskMapper.updateById(task);
            taskAcceptRecordsMapper.expireProcess(task.getTaskId());
            //todo 通知用户
        });
        log.info("过期任务：" + tasks);
        
    }

//    @Scheduled(cron = "0/30 * * * * ?")
    public void SimpleQueue() {
        // 队列名称
        String queueName = "simple.queue";
        // 消息
        String message = "hello, spring amqp!";
        // 当前时间
        Date now = new Date();
//
        //使用formDate格式化时间 YYYY-MM-DD HH:mm:ss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowString = now.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime().format(formatter);
        // 发送消息
        rabbitTemplate.convertAndSend(queueName, message+nowString);

    }

    /**
     * workQueue
     * 向队列中不停发送消息，模拟消息堆积。
     */

    public void testWorkQueue() throws InterruptedException {
        // 队列名称
        String queueName = "simple.queue";
        // 消息
        String message = "hello, message_";
        for (int i = 0; i < 50; i++) {
            // 发送消息
            rabbitTemplate.convertAndSend(queueName, message + i);
            Thread.sleep(20);
        }
    }

//    @Scheduled(cron = "0/30 * * * * ?")
    public void testFanoutExchange() {
        // 队列名称
        String exchangeName = "lz.fanout";
        // 消息
        String message = "hello, everyone!";
        rabbitTemplate.convertAndSend(exchangeName, "", message);
    }
}