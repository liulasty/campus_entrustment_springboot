package com.lz.service.impl;

import com.lz.pojo.entity.Notificationreadstatus;
import com.lz.mapper.NotificationreadstatusMapper;

import com.lz.service.INotificationReadStatusService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通知表记录用户是否已读通知 服务实现类
 * </p>
 *
 * @author lz
 * @since 2024-04-10
 */
@Service
public class NotificationReadStatusServiceImpl extends ServiceImpl<NotificationreadstatusMapper, Notificationreadstatus> implements INotificationReadStatusService {

}