package com.lz.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.lz.pojo.Enum.TaskUpdateType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 记录任务的更新情况
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("taskupdates")
@ApiModel(value="Taskupdates对象", description="记录任务的更新情况")
public class Taskupdates implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "更新记录的ID")
    @TableId(value = "UpdateID", type = IdType.AUTO)
    private Long updateId;

    @ApiModelProperty(value = "对应的任务ID")
    @TableField(value = "TaskID")
    private Long taskId;

    @ApiModelProperty(value = "进行更新的用户ID")
    @TableField(value = "UserID")
    private Long userId;
    
    @ApiModelProperty(value = "更新类型")
    @TableField(value = "UpdateType")
    private TaskUpdateType updateType;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "UpdateTime")
    private Date updateTime;

    @ApiModelProperty(value = "更新内容")
    @TableField(value = "UpdateDescription")
    private String updateDescription;


}