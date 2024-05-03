package com.lz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lz.pojo.dto.AcceptDTO;
import com.lz.pojo.entity.TaskAcceptRecords;

/**
 * 委托 接受记录服务
 *
 * @author lz
 * @date 2024/05/03
 */
public interface ITaskAcceptRecordsService extends IService<TaskAcceptRecords> {
    void create(AcceptDTO acceptDTO);

    TaskAcceptRecords getTaskAcceptRecords(Long taskId);
    
    TaskAcceptRecords getTaskAcceptRecordsByTaskId(Long userId, Long taskId);
}