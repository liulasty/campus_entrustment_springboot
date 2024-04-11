package com.lz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.mapper.SystemAnnouncementsMapper;
import com.lz.pojo.entity.Notifications;
import com.lz.mapper.NotificationsMapper;
import com.lz.pojo.entity.SystemAnnouncements;
import com.lz.service.INotificationsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 存储系统通知信息 服务实现类
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@Service
public class NotificationsServiceImpl extends ServiceImpl<NotificationsMapper, Notifications> implements INotificationsService {
    
    @Autowired
    private SystemAnnouncementsMapper systemAnnouncementsMapper;

    /**
     * 按 ID 和时间获取通知
     */
    @Override
    public void getNotificationsByIdANDDate() {
        
    }
    
    
    
    @Override
    public void getNewestNotifications() {
        
    }


}