package com.lz.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.lz.pojo.Enum.TaskStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 存储任务相关信息
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("task")
@Builder
@ApiModel(value="Task对象", description="存储任务相关信息")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务ID")
    @TableId(value = "TaskID", type = IdType.AUTO)
    private Long taskId;

    @ApiModelProperty(value = "任务所有者的用户ID")
    @TableField("OwnerID")
    private Long ownerId;

    @ApiModelProperty(value = "任务接收者的用户ID")
    @TableField("ReceiverID")
    private Long receiverId;
    
    
    @ApiModelProperty(value = "创建时间")
    @TableField("CreatedAt")
    private Date createdAt;

    @ApiModelProperty(value = "任务描述")
    @TableField("Description")
    private String description;
    
    @ApiModelProperty(value = "委托类型")
    @TableField("TaskType")
    private Integer type;
    
    @ApiModelProperty(value = "任务地点")
    @TableField("Location")
    private String location;

    @ApiModelProperty(value = "委托发布时间")
    @TableField("StartTime")
    private Date startTime;

    @ApiModelProperty(value = "任务截止时间")
    @TableField("EndTime")
    private Date endTime;

    @ApiModelProperty(value = "任务状态")
    @TableField("STATUS")
    private TaskStatus status;


}