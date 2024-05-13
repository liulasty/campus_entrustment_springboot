package com.lz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.mapper.DelegateAuditRecordsMapper;
import com.lz.mapper.UsersMapper;
import com.lz.pojo.Enum.TaskStatus;
import com.lz.pojo.constants.AuditResult;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.entity.DelegateAuditRecords;
import com.lz.pojo.entity.Users;
import com.lz.service.IDelegateAuditRecordsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@Slf4j
public class DelegateAuditRecordsServiceImpl extends ServiceImpl<DelegateAuditRecordsMapper, DelegateAuditRecords> implements IDelegateAuditRecordsService {

   @Autowired
   private UsersMapper usersMapper;
    /**
     * 获取当前用户信息
     *
     * @return {@code Users}
     */
    public  Users getCurrentAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminName = authentication.getName();
        log.info("管理员: {}", adminName);

        return usersMapper.getByUsername(adminName);
    }
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
        if (auditRecords == null) {
            log.info("该委托没有审核记录");
            throw new RuntimeException(MessageConstants.TASK_NOT_EXIST);
        }
        return auditRecords;
    }

    @Override
    public void createNewRecord(Long taskId, String s) {
        DelegateAuditRecords delegateAuditRecords = new DelegateAuditRecords();
        
    }

    @Override
    public void createNewRecord(Long taskId, String msg, TaskStatus status) {
        Users users = getCurrentAdmin();
        DelegateAuditRecords delegateAuditRecords = new DelegateAuditRecords();
        delegateAuditRecords
                .setDelegateId(taskId)
                .setReviewComment(msg)
                .setReviewStatus(status.getWebValue())
                .setUserId(users.getUserId());
                
        
        save(delegateAuditRecords);
        
    }
}