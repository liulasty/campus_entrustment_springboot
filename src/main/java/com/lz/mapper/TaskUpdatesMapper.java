package com.lz.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.pojo.Enum.TaskUpdateType;
import com.lz.pojo.entity.TaskUpdates;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.Date;

/**
 * <p>
 * 记录任务的更新情况 Mapper 接口
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@Mapper
public interface TaskUpdatesMapper extends BaseMapper<TaskUpdates> {


    void confirmTheRecipient(Long taskId, Long userId, TaskUpdateType result,
                             String taskAcceptSuccess) ;
    
    IPage<TaskUpdates> page(Page<TaskUpdates> page, String updateDescription,
                            String updateType, Date updateTime);
}