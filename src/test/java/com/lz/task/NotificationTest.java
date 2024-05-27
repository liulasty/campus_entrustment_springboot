package com.lz.task;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/10/20:57
 * @Description:
 */

import com.lz.pojo.Enum.AnnouncementStatus;
import com.lz.pojo.Enum.NotificationsType;
import com.lz.pojo.entity.NotificationReadStatus;
import com.lz.pojo.entity.Notifications;
import com.lz.pojo.entity.SystemAnnouncements;
import com.lz.service.INotificationReadStatusService;

import com.lz.service.INotificationsService;
import com.lz.service.ISystemAnnouncementsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author lz
 */
@SpringBootTest
@Slf4j
public class NotificationTest {
    @Autowired
    private INotificationReadStatusService notificationReadStatusService;

    @Autowired
    private INotificationsService notificationsService;

    @Autowired
    private ISystemAnnouncementsService systemAnnouncementsService;

    @Test
    public void getNotificationsByIdANDDate() {
        notificationsService.getNotificationsByIdANDDate();
    }

    //添加信息
    @Test
    public void addNotifications() {

        Notifications notifications = new Notifications();
        notifications.setMessage("测试消息");
        notifications.setNotificationTime(new Date(System.currentTimeMillis()));
        for (int i = 0; i < 100; i++) {
            notifications.setUserId(1L + i);
            if (i % 2 == 0) {

                if (i % 3 == 0) {
                    notifications.setNotificationType(NotificationsType.OWN);
                    notifications.setTitle("个人信息通知");
                } else {
                    notifications.setNotificationType(NotificationsType.TASK);
                    notifications.setTitle("委托通知");
                }
            } else {
                if (i % 3 == 0) {
                    notifications.setNotificationType(NotificationsType.SYSTEM);
                    notifications.setTitle("系统通知");
                } else {
                    notifications.setNotificationType(NotificationsType.MARKETING);
                    notifications.setTitle("营销通知");
                }
            }

            notificationsService.save(notifications);
        }

    }

    @Test
    public void getNotificationsRead() {
        NotificationReadStatus notificationreadstatus = new NotificationReadStatus();


        notificationreadstatus.setIsRead(false);
        for (int i = 1; i < 100; i++) {
            notificationreadstatus.setNotificationId((long) i);
            notificationreadstatus.setUserId((long) i);
            notificationreadstatus.setReadTime(new Date(System.currentTimeMillis() + 24 * 1000 * 60 * 60 * (long) i));
            notificationReadStatusService.save(notificationreadstatus);
        }

    }

    @Test
    public void getNotificationsReadByUserId() {
        notificationReadStatusService.getById(1);
    }

    /**
     * 添加系统公告测试
     */
    @Test
    public void getSystemAnnouncementsPage() {
        SystemAnnouncements systemAnnouncements = SystemAnnouncements.builder()
                .title("测试公告主题")
                .content("测试公告内容")
                .publisherId(9L)
                .publishTime(new Date(System.currentTimeMillis()))
                .status(AnnouncementStatus.DRAFT.getDbValue())
                .startEffectiveTime(new Date(System.currentTimeMillis()))
                .endEffectiveTime(new Date(System.currentTimeMillis() + (long) 24 * 1000 * 60 * 60))
                .isPinned(true)
                .createdAt(new Date(System.currentTimeMillis()))
                .updatedAt(new Date(System.currentTimeMillis() + (long) 24 * 1000 * 60 * 60 * 4))
                .updatedBy(9L)
                .build();
        for (int i = 0; i < 20; i++) {
            systemAnnouncements.setTitle("测试公告主题" + i);
            systemAnnouncements.setPublishTime(
                    new Date(systemAnnouncements.getPublishTime().getTime()+(long) 24 * 1000 * 60 * 60 *  i)
                    );
            systemAnnouncements.setIsPinned(false);
            systemAnnouncementsService.save(systemAnnouncements);
        }


    }

    @Test
    public void getNewestSystemAnnouncements() {
        systemAnnouncementsService.getNewestAnnouncement();
    }
    


}