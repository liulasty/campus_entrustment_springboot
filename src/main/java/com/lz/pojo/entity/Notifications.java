package com.lz.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lz.pojo.Enum.NotificationsType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 存储系统通知信息
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("notifications")
@Builder
@ApiModel(value="Notifications对象", description="存储系统通知信息")
public class Notifications implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "通知ID")
    @TableId(value = "NotificationID", type = IdType.AUTO)
    private Long notificationId;

    @ApiModelProperty(value = "发送通知的用户ID")
    @TableField("UserID")
    private Long userId;

    @ApiModelProperty(value = "通知类型")
    @TableField("NotificationType")
    private NotificationsType notificationType;
    
    @ApiModelProperty(value = "通知标题")
    @TableField("Title")
    private String title;
    /**
     * 消息
     * VARCHAR(255)字段通常能够存储大约85个汉字
     */
    @ApiModelProperty(value = "通知消息")
    @TableField("Message")
    private String message;
    

    @ApiModelProperty(value = "通知时间")
    @TableField("NotificationTime")
    @DateTimeFormat(fallbackPatterns = "yyyy年MM月dd日HH:mm:ss")
    private Date notificationTime;


}