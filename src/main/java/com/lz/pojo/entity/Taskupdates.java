package com.lz.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.lz.pojo.Enum.TaskUpdateType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("taskupdates")
@ApiModel(value="Taskupdates对象", description="记录任务的更新情况")
public class Taskupdates implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "更新记录的ID")
    @TableId(value = "UpdateID", type = IdType.AUTO)
    private Integer updateId;

    @ApiModelProperty(value = "对应的任务ID")
    private Integer taskId;

    @ApiModelProperty(value = "进行更新的用户ID")
    private Integer userId;
    
    @ApiModelProperty(value = "更新类型")
    private TaskUpdateType updateType;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "更新内容")
    private String updateDescription;


}