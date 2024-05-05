package com.lz.task;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/10/14:47
 * @Description:
 */

import com.lz.pojo.Enum.TaskStatus;
import com.lz.pojo.dto.TaskPageDTO;
import com.lz.pojo.entity.DelegationCategories;
import com.lz.pojo.entity.Task;
import com.lz.pojo.result.PageResult;
import com.lz.service.IDelegationCategoriesService;
import com.lz.service.ITaskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

/**
 * @author lz
 */
@SpringBootTest
@Slf4j
public class taskServiceTest {
    @Autowired
    private ITaskService taskService;

    @Autowired
    private IDelegationCategoriesService delegationCategoriesService;
    
    
    @Test
    public void test01() {
        


    }

    @Test
    public void test06() {
        Task task = Task.builder()
                .receiverId(null)
                .description("测试")
                .createdAt(new Date(System.currentTimeMillis()))
                // .endTime(LocalDateTime.now().plusHours(1))
                // .startTime(LocalDateTime.now())
                .status(TaskStatus.DRAFT)
                .type(1)
                .location("测试")
                .build();

        taskService.save(task);
    }

    @Test
    public void test02() {
        // Task task = Task.builder()
        //         .receiverId(null)
        //         .createdAt(new Date(System.currentTimeMillis()))
        //         .endTime(new Date(System.currentTimeMillis() + 20 * 60 * 60 * 24 * 1000))
        //         .startTime(new Date(System.currentTimeMillis() + 10 * 60 * 60 * 24 * 1000))
        //         .location("修改测试")
        //         .build();
        // int count  = 1;
        // for (int i = 7; i < 23; i++) {
        //    
        //     task.setOwnerId(i + 1);
        //     task.setCreatedAt(new Date(System.currentTimeMillis() + (long) i * 60 * 60 * 24 * 1000));
        //     task.setStatus(TaskStatus.ONGOING);
        //    
        //    
        //    
        //    
        // }
    }
    

    @Test
    public void test03() {
        for (int i = 49; i < 100; i++) {
            taskService.removeById(i + 1);
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

        log.info("查询结果：{}", taskPageResult);
    }

    @Test
    public void test05() {

        log.info("查询结果：{}", taskService.getById(398L));
    }

    @Test
    public void AddSystemAnnouncement() {

        log.info("查询结果：{}", taskService.getById(398L));
    }
    
    @Test
    void addTaskType(){
        DelegationCategories delegationCategories = DelegationCategories.builder()
                .categoryName("测试")
                .categoryDescription("测试")
                .build();
        delegationCategoriesService.save(delegationCategories);
    }
    
    @Test
    void deleteTaskType(){
        List<DelegationCategories> list = delegationCategoriesService.list();
        log.info("查询结果：{}", list);
        delegationCategoriesService.removeById(11);
    }
    
    @Test
    void  getUserDelegateDraft(){
        
    }

    @Test
    public void getTasksWithUser(){
        taskService.getTasksWithUser(8L);
    }
    


}