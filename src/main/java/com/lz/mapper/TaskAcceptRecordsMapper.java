package com.lz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.pojo.Enum.TaskStatus;
import com.lz.pojo.entity.TaskAcceptRecords;
import com.lz.pojo.vo.TaskAcceptRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lz
 */ 
@Mapper
public interface TaskAcceptRecordsMapper extends BaseMapper<TaskAcceptRecords> {

    /**
     * 搜索委托任务接受记录
     *
     * @param page          页
     * @param accepterId    接受者 ID
     * @param taskId        任务 ID
     * @param taskStatus    任务状态
     * @param location      位置
     * @param description   描述
     * @param taskType      任务类型
     * @param isAsc         是ASC
     *
     * @return iPage<任务接受记录>
     */
    IPage<TaskAcceptRecord> searchTaskAcceptRecord(
            Page<TaskAcceptRecord> page,
            Long accepterId, Long taskId,  TaskStatus taskStatus,
            String location, String description, Long taskType,
            Integer isAsc);

    /**
     * 过期处理
     *
     * @param taskId 任务 ID
     */
    void expireProcess(Long taskId);
    
    /**
     * 通过委托任务编号获取所有委托接受记录
     *
     * @param taskId 任务 ID
     *
     * @return 接受者接受记录
     */
   List<TaskAcceptRecords> getTaskAcceptRecordsByTaskId(Long taskId);
    
    
    // IPage<User> selectUsersWithRoleAndPage( @Param("userId") Long userId, @Param("orderByColumn") String orderByColumn, @Param("isAsc") );

}