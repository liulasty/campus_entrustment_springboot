package com.lz.service;

import com.lz.pojo.dto.TaskCountDTO;
import com.lz.pojo.dto.TaskPageDTO;
import com.lz.pojo.entity.Task;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lz.pojo.result.PageResult;

import java.util.List;
import java.util.Map;

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

    /**
     * 获取最新委托
     *
     * @return {@code Task}
     */
    List<Task> getNewestTask();

    /**
     * 获取热门委托类别
     *
     * @return {@code String[]}
     */
    Map<String, TaskCountDTO> getHotTaskCategory();

    /**
     * 按类别搜索委托
     *
     * @param category 类别
     */
    void searchTaskByCategory(String category);


    /**
     * 获取委托成交统计信息
     *
     * @return {@code Throwable}
     */
    Map<String,Integer> getTransactionStats();

    /**
     * 获取用户相关委托
     *
     * @param id 同上
     *
     * @return {@code Map<String,Task>}
     */
    List<Task> getTasksWithUser(Long id);
}