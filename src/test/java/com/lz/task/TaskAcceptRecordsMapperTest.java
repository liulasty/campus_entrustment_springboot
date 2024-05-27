package com.lz.task;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/05/17:30
 * @Description:
 */

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.mapper.TaskAcceptRecordsMapper;
import com.lz.pojo.Enum.TaskStatus;
import com.lz.pojo.entity.TaskAcceptRecords;
import com.lz.pojo.vo.TaskAcceptRecord;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author lz
 */
@SpringBootTest
@Slf4j
public class TaskAcceptRecordsMapperTest {
    @Autowired
    private TaskAcceptRecordsMapper taskAcceptRecordsMapper;

    @Test
    void testSearchTaskAcceptRecord() {
        Long accepterId = 2L;

        TaskStatus taskStatus = TaskStatus.EXPIRED;
        // String location = "New York";
        // String description = "Test task";
        Long taskType = 1L;
        String orderByColumn = "acceptTime";
        Integer isAsc = 1;

        Page<TaskAcceptRecord> page = new Page<>(1, 10);

        IPage<TaskAcceptRecord> result =
                taskAcceptRecordsMapper.searchTaskAcceptRecord(page,
                                                               null,
                                                               null, null,
                                                               null, null, null,
                                                                isAsc);
        TaskAcceptRecord taskAcceptRecord = new TaskAcceptRecord();
        System.out.println("taskAcceptRecord.toString():" + taskAcceptRecord);
        log.info("Result: {}", result.getRecords());
    }
    
    @Test
    void testGetTaskAcceptRecordsByTaskId() {
        Long id = 3L;
        List<TaskAcceptRecords> taskAcceptRecords =
                taskAcceptRecordsMapper.getTaskAcceptRecordsWithUser(id);
        log.info("Result: {}", taskAcceptRecords);
    }

    @Test
    void testGetTaskAcceptRecordsTaskId() {
        Long id = 132L;
        List<TaskAcceptRecords> taskAcceptRecords =
                taskAcceptRecordsMapper.getTaskAcceptRecordsByTaskId(id);
        log.info("Result: {}", taskAcceptRecords);
    }
}