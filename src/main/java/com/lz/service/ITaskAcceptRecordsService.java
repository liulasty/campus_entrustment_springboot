package com.lz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lz.Exception.MyException;
import com.lz.pojo.dto.AcceptDTO;
import com.lz.pojo.entity.TaskAcceptRecords;

import java.util.List;
import java.util.Map;

/**
 * 委托 接受记录服务
 *
 * @author lz
 * @date 2024/05/03
 */
public interface ITaskAcceptRecordsService extends IService<TaskAcceptRecords> {
    void create(AcceptDTO acceptDTO);

    TaskAcceptRecords getTaskAcceptRecordByTaskId(Long taskId) throws MyException;
    
    TaskAcceptRecords getTaskAcceptRecordsByTaskId(Long userId, Long taskId);

    List<TaskAcceptRecords> getTaskAcceptRecordsByTaskId(Long taskId) throws MyException;

    
}