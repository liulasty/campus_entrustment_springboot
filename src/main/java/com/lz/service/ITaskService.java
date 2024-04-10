package com.lz.service;

import com.lz.pojo.entity.Task;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 存储任务相关信息 服务类
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
public interface ITaskService extends IService<Task> {

    void addTask();
    void updateTask();
    void deleteTask();
    void searchTask(Long id);
}