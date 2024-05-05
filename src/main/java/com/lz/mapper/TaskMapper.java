package com.lz.mapper;

import com.lz.pojo.dto.TaskCountDTO;
import com.lz.pojo.entity.Task;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 存储任务相关信息 Mapper 接口
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {
    
    List<TaskCountDTO> selectTaskTypeCountTop5();

    /**
     * 查找任务统计信息
     */
    void selectTasksStats();


    /**
     * 获取每周查询委托数量
     *
     * @param key 钥匙
     *
     * @return {@code Integer}
     */
    Integer getTasksWeeklyCount( String key);

    /**
     * 获取任务每月查询委托数量
     *
     * @param key 钥匙
     *
     * @return {@code Integer}
     */
    Integer getTasksMonthlyCount(String key);


    /**
     * 今日查询委托数量
     *
     * @param key 钥匙
     *
     * @return {@code Integer}
     */
    Integer getTasksTodayCount(String key);
}