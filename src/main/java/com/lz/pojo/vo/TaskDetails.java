package com.lz.pojo.vo;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/16/22:04
 * @Description:
 */

import com.lz.pojo.entity.Task;
import com.lz.pojo.entity.TaskAcceptRecords;
import com.lz.pojo.entity.UsersInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDetails {
    UsersInfo usersInfo;
    Task task;
    TaskAcceptRecords taskAcceptRecords;
    /**
     * 委托任务发布总数
     */
    Long taskPublishedTotal;
    /**
     * 委托任务完成总数
     */
    Long taskAcceptedTotal;
    /**
     *委托任务过期数
     */
    Long taskOverdueTotal;
    /**
     * 委托任务取消总数
     */
    Long taskCanceledTotal;
}