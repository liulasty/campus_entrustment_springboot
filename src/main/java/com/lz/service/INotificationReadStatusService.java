package com.lz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.pojo.entity.NotificationReadStatus;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lz.pojo.vo.NotificationReadStatusVO;

import java.util.Date;

/**
 * <p>
 * 通知表记录用户是否已读通知 服务类
 * </p>
 *
 * @author lz
 * @since 2024-04-10
 */
public interface INotificationReadStatusService extends IService<NotificationReadStatus> {

    void sendAllNotification(Long notificationId);

    void sendAuthenticatedNotification(Long sendId);

    void sendStudentNotification(Long sendId);

    void sendOtherNotification(Long sendId);

    void sendTeacherNotification(Long sendId);

    void sendAdminNotification(Long sendId);

    Page<NotificationReadStatusVO> selectList(Page<NotificationReadStatusVO> page, Date createAt, String messageType, String description);

    void addTaskNotification(Long id, Long ownerId, Long userId);

    void delNotification(Long id);

    void addTaskConfirmTheRecipient(int NotificationId, Long taskId, Long userId,
                                    Date date);


}