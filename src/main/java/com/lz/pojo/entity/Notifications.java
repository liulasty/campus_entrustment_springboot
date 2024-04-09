package com.lz.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
@ApiModel(value="Notifications对象", description="存储系统通知信息")
public class Notifications implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "通知ID")
    @TableId(value = "NotificationID", type = IdType.AUTO)
    private Integer notificationId;

    @ApiModelProperty(value = "接收通知的用户ID")
    private Integer userId;

    @ApiModelProperty(value = "通知类型")
    private String notificationType;

    @ApiModelProperty(value = "通知消息")
    private String message;

    @ApiModelProperty(value = "是否已读")
    private Boolean isRead;

    @ApiModelProperty(value = "通知时间")
    private LocalDateTime notificationTime;


}