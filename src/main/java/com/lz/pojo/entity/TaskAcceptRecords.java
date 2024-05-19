package com.lz.pojo.entity;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/03/14:32
 * @Description:
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lz.pojo.Enum.AcceptStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 委托任务接受记录
 *
 * @author lz
 * @date 2024/05/07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("taskacceptrecords")
@Builder
@ApiModel(value="Task对象", description="存储接受委托留言记录相关信息")
public class TaskAcceptRecords {
    @ApiModelProperty(value = "接受记录ID")
    @TableId(value = "AcceptRecordId", type = IdType.AUTO)
    private Long id;
    
    @ApiModelProperty(value = "接受状态")
    @TableField(value = "status")
    private AcceptStatus status;
    
    @ApiModelProperty(value = "接受者ID")
    @TableField(value = "AccepterId")
    private Long receiverId;
    
    @ApiModelProperty(value = "委托任务ID")
    @TableField(value = "taskId")
    private Long taskId;
    
    @ApiModelProperty(value = "接受者留言")
    @TableField(value = "str")
    private String str;
    
    @ApiModelProperty(value = "接受时间")
    @TableField(value = "acceptTime")
    private Date acceptTime;

    @ApiModelProperty(value = "处理时间")
    @TableField(value = "adoptTime")
    private Date adoptTime;
    
    
    
}