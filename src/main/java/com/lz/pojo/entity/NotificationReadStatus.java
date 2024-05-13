package com.lz.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 通知表记录用户是否已读通知
 * </p>
 *
 * @author lz
 * @since 2024-04-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@TableName("NotificationReadStatus")
@ApiModel(value="通知记录表", description="通知表记录用户是否已读通知")
public class NotificationReadStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "通知记录ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @ApiModelProperty(value = "通知ID")
    @TableField(value = "NotificationID")
    private Long notificationId;

    @ApiModelProperty(value = "通知用户ID")
    @TableField("UserID")
    private Long userId;
    
    @ApiModelProperty(value = "相关委托任务ID")
    @TableField("TaskID")
    private Long taskId;
    
    @ApiModelProperty(value = "用户通知时间")
    @TableField("SendTime")
    private Date sendTime;   
    
    @ApiModelProperty(value = "用户阅读通知的时间")
    @TableField("ReadTime")
    private Date readTime;

    @ApiModelProperty(value = "是否已读标志")
    @TableField("IsRead")
    private Boolean isRead;


}