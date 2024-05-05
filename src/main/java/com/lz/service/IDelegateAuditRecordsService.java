package com.lz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lz.pojo.Enum.TaskStatus;
import com.lz.pojo.entity.DelegateAuditRecords;

/**
 * <p>
 * 存储委托信息审核记录 服务类
 * </p>
 *
 * @author lz
 * @since 2024-04-05
 */
public interface IDelegateAuditRecordsService extends IService<DelegateAuditRecords> {

    /**
     * 按 ID 获取失败原因
     *
     * @param id 同上
     *
     * @return <p>
     */
    DelegateAuditRecords getFailReasonById(Long id);

    /**
     * 创建新记录
     *
     * @param taskId 任务 ID
     * @param s      s
     */
    void createNewRecord(Long taskId, String s);

    /**
     * 创建新记录
     *
     * @param taskId 任务 ID
     * @param msg    味精
     * @param status 地位
     */
    void createNewRecord(Long taskId, String msg, TaskStatus status);    
}