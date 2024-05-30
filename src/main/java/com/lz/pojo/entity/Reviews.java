package com.lz.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.lz.Annotation.Excel;
import com.lz.Annotation.Excel.Type;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 存储用户对委托任务的评价信息
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("reviews")
@Builder
@ApiModel(value="Reviews对象", description="存储用户对任务的评价信息")
public class Reviews implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评价ID")
    @TableId(value = "ReviewID", type = IdType.AUTO)
    @Excel(name = "评论编号", type = Type.EXPORT)
    private Long reviewId;

    @ApiModelProperty(value = "对应的任务ID")
    @TableField("TaskID")
    @Excel(name = "任务编号")
    private Long taskId;

    /**
     * 发布者 ID
     */
    @ApiModelProperty(value = "委托发布者的用户ID")
    @TableField("PublisherID")
    @Excel(name = "发布者编号")
    private Long publisherId;

    /**
     * 接受者 ID
     */
    @ApiModelProperty(value = "委托接收者的用户ID")
    @TableField("AcceptorID")
    @Excel(name = "接受者编号")
    private Long acceptorId;


    /**
     * 评价者 ID
     */
    @ApiModelProperty(value = "评价者的用户ID")
    @TableField("ReviewerID")
    @Excel(name = "评价者编号")
    private Long reviewerId;

    @ApiModelProperty(value = "评价等级")
    @TableField("Rating")
    @Excel(name = "评价等级")
    private Long rating;

    @ApiModelProperty(value = "评价评论")
    @TableField("COMMENT")
    @Excel(name = "评论")
    private String comment;

    /**
     * 已获批准
     */
    @ApiModelProperty(value = "是否已批准")
    @TableField("IsApproved")
    @Excel(name = "是否已批准")
    private Boolean isApproved;


}