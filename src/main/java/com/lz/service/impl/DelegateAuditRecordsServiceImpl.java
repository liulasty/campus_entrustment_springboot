package com.lz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.mapper.DelegateAuditRecordsMapper;
import com.lz.pojo.constants.AuditResult;
import com.lz.pojo.entity.DelegateAuditRecords;
import com.lz.service.IDelegateAuditRecordsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 存储委托信息审核记录 服务实现类
 * </p>
 *
 * @author lz
 * @since 2024-04-05
 */
@Service
public class DelegateAuditRecordsServiceImpl extends ServiceImpl<DelegateAuditRecordsMapper, DelegateAuditRecords> implements IDelegateAuditRecordsService {

    /**
     * 按 ID 获取失败原因
     *
     * @param id 同上
     */
    @Override
    public DelegateAuditRecords getFailReasonById(Long id) {
        QueryWrapper<DelegateAuditRecords> queryWrapper = new QueryWrapper();
        queryWrapper.eq("DelegateID", id)
                .eq("ReviewStatus", AuditResult.REJECTED)
                .orderByDesc("ReviewTime");
        DelegateAuditRecords auditRecords = getOne(queryWrapper);
        return auditRecords;
    }
}