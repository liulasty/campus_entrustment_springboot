package com.lz.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="Task对象", description="存储任务相关信息")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务ID")
    @TableId(value = "TaskID", type = IdType.AUTO)
    private Integer taskId;

    @ApiModelProperty(value = "任务所有者的用户ID")
    private Integer ownerId;

    @ApiModelProperty(value = "任务接收者的用户ID")
    private Integer receiverId;

    @ApiModelProperty(value = "任务描述")
    private String description;
    
    @ApiModelProperty(value = "委托类型")
    private String type;
    
    @ApiModelProperty(value = "任务地点")
    private String location;

    @ApiModelProperty(value = "任务开始时间")
    private Date startTime;

    @ApiModelProperty(value = "任务结束时间")
    private Date endTime;

    @ApiModelProperty(value = "任务状态")
    private String status;


}