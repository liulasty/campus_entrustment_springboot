package com.lz.service;

import com.lz.pojo.entity.Notifications;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lz.pojo.entity.SystemAnnouncements;

import java.util.List;

/**
 * <p>
 * 存储系统通知信息 服务类
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
public interface INotificationsService extends IService<Notifications> {

    /**
     * 按 ID 和时间获取通知
     */
    void getNotificationsByIdANDDate();

    /**
     * 获取最新通知
     */
    void getNewestNotifications();


    
    
}