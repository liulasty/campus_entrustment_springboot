package com.lz.pojo.vo;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/12/21:54
 * @Description:
 */

import com.lz.pojo.Enum.NotificationsType;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @author lz
 */
@Data
@ApiModel(value="通知记录对象",description = "通知记录数据模型")
public class NotificationReadStatusVO {
    Long id;
    Long notificationId;
    Date notificationTime;
    NotificationsType type;
    Long userId;
    String username;
    String title;
    String message;
    Boolean isRead;
    Date readTime;
}