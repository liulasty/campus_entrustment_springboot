package com.lz.service;

import com.lz.pojo.dto.TaskPageDTO;
import com.lz.pojo.entity.Task;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lz.pojo.result.PageResult;

/**
 * <p>
 * 存储任务相关信息 服务类
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
public interface ITaskService extends IService<Task> {
    /**
    * 添加
     */
    void addTask();
    /**
    * 更新
     */
    void updateTask();
    /**
    * 删除
     */
    void deleteTask();
    /**
    * 查询
     */
    void searchTask(Long id);

    /**
     * 分页
     *
     * @param taskPageDTO 任务页 DTO
     *
     * @return {@code PageResult<Task>}
     */
    PageResult<Task> searchPage(TaskPageDTO taskPageDTO);
}