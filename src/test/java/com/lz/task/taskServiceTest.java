package com.lz.task;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/10/14:47
 * @Description:
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.pojo.Enum.TaskStatus;
import com.lz.pojo.dto.TaskPageDTO;
import com.lz.pojo.entity.Task;
import com.lz.pojo.result.PageResult;
import com.lz.service.ITaskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author lz
 */
@SpringBootTest
@Slf4j
public class taskServiceTest {
    @Autowired
    private ITaskService taskService;
    @Test
    public void test01() {
        Task task = Task.builder()
                .receiverId(null)
                .endTime(new Date(System.currentTimeMillis()))
                .startTime(new Date(System.currentTimeMillis()))
                .status(TaskStatus.PENDING)
                .type("测试")
                .location("测试")
                .build();
        for (int i = 0; i < 100; i++){
            task.setOwnerId(i+1);
            task.setDescription("测试"+i);
            taskService.save(task);
        }
        
        
        
    }
    
    @Test
    public void test06() {
        Task task = Task.builder()
                .receiverId(null)
                .description("测试")
                .endTime(new Date(System.currentTimeMillis()+20*60*60*24*1000))
                .startTime(new Date(System.currentTimeMillis()))
                .status(TaskStatus.COMPLETED)
                .type("测试")
                .location("测试")
                .build();
        
        taskService.save(task);
            }
    
    @Test
    public void test02() {
        Task task = Task.builder()
                .receiverId(null)
                .endTime(new Date(System.currentTimeMillis()+20*60*60*24*1000))
                .startTime(new Date(System.currentTimeMillis()))
                .status(TaskStatus.PENDING)
                .type("修改测试")
                .location("修改测试")
                .build();
        for (int i = 304; i < 350; i++){
            task.setTaskId(i+1);
            task.setOwnerId(i+1);
            task.setDescription("修改测试"+i);
            log.info("修改开始时间：{}",task.getStartTime());
            log.info("修改结束时间：{}",task.getEndTime());
            taskService.updateById(task);
        }
    }
    
    @Test
    public void test03() {
        for (int i = 49; i < 100; i++){
            taskService.removeById(i+1);
        }
    }
    
    
    
    @Test
    public void test04() {
        TaskPageDTO taskPageDTO = new TaskPageDTO();
        taskPageDTO.setPageNum(1);
        
        taskPageDTO.setPageSize(10);
        taskPageDTO.setDescription("测试1");
        taskPageDTO.setLocation("");


        PageResult<Task> taskPageResult = taskService.searchPage(taskPageDTO);

        log.info("查询结果：{}",taskPageResult);
    }
    
    @Test
    public void test05() {
        
        log.info("查询结果：{}",taskService.getById(398L));
    }
    
    
}