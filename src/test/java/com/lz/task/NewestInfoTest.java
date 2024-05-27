package com.lz.task;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/10/23:00
 * @Description:
 */

import com.lz.pojo.dto.TaskCountDTO;
import com.lz.pojo.entity.SystemAnnouncements;
import com.lz.pojo.entity.Task;
import com.lz.service.ISystemAnnouncementsService;
import com.lz.service.ITaskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * @author lz
 */
@SpringBootTest
@Slf4j
public class NewestInfoTest {
    @Autowired
    private ISystemAnnouncementsService systemAnnouncementsService;
    
    @Autowired
    private ITaskService taskService;
    
    @Test
    void testNewestInfo() {
        Long id = 1L;
        List<SystemAnnouncements> newestAnnouncement = systemAnnouncementsService.getNewestAnnouncement();
        List<Task> newestTask = taskService.getNewestTask();
        Map<String, TaskCountDTO> hotTaskCategory = taskService.getHotTaskCategory();
        Map<String, Integer> transactionStats = taskService.getTransactionStats();
        List<Task> tasksWithUser = taskService.getTasksWithUser(id);
        log.info("最新公告：{}", newestAnnouncement);
        log.info("最新委托：{}", newestTask);
        log.info("热门委托类别：{}", hotTaskCategory);
        log.info("委托信息统计：{}", transactionStats);
        log.info("与我相关：{}", tasksWithUser);
    }
    
    @Test
    void getNewestAnnouncement(){
        systemAnnouncementsService.getNewestAnnouncement();
    }
}