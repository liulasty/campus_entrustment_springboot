package com.lz.pojo.vo;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/29/19:03
 * @Description:
 */

import com.lz.pojo.entity.Task;
import com.lz.pojo.entity.TaskAcceptRecords;
import com.lz.pojo.entity.UsersInfo;
import lombok.Data;

import java.util.*;

/**
 * @author lz
 */
@Data
public class TaskAndUserInfoVO {
    UsersInfo usersInfo;
    Task task;
    List<TaskAcceptRecordVO> taskAcceptRecords;

    public TaskAndUserInfoVO(Task task, UsersInfo usersInfo, List<TaskAcceptRecordVO> taskAcceptRecords) {
        this.usersInfo = usersInfo;
        this.task = task;
        this.taskAcceptRecords = taskAcceptRecords;
    }

    public TaskAndUserInfoVO(Task task, UsersInfo usersInfo) {
        this.usersInfo = usersInfo;
        this.task = task;
        this.taskAcceptRecords = new ArrayList<>();
    }
}