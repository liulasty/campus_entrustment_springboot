package com.lz.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 存储委托信息审核记录
 * </p>
 *
 * @author lz
 * @since 2024-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("delegateauditrecords")
@ApiModel(value="委托信息审核记录对象", description="存储委托信息审核记录")
public class DelegateAuditRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "审核记录ID")
    @TableId(value = "RecordID", type = IdType.AUTO)
    private Integer recordId;

    @ApiModelProperty(value = "委托任务ID")
    private Integer delegateId;

    @ApiModelProperty(value = "管理员ID")
    private Integer userId;

    @ApiModelProperty(value = "审核结果状态“已批准”、“已拒绝”、“待定”")
    private String reviewStatus;

    @ApiModelProperty(value = "审核意见或说明")
    private String reviewComment;

    @ApiModelProperty(value = "审核完成时间")
    private LocalDateTime reviewTime;


}