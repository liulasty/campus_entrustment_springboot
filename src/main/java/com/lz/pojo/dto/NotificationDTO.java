package com.lz.pojo.dto;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/12/15:36
 * @Description:
 */

import com.lz.pojo.Enum.NotificationsType;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author lz
 */
@Data
@ApiModel(value = "通知消息对象", description = "添加通知消息时对象")
public class NotificationDTO {
    String title;
    String description;
    String type;
}