package com.lz.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("reviews")
@Builder
@ApiModel(value="Reviews对象", description="存储用户对任务的评价信息")
public class Reviews implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评价ID")
    @TableId(value = "ReviewID", type = IdType.AUTO)
    private Long reviewId;

    @ApiModelProperty(value = "对应的任务ID")
    @TableField("TaskID")
    private Long taskId;

    /**
     * 发布者 ID
     */
    @ApiModelProperty(value = "委托发布者的用户ID")
    @TableField("PublisherID")
    private Long publisherId;

    /**
     * 接受者 ID
     */
    @ApiModelProperty(value = "委托接收者的用户ID")
    @TableField("AcceptorID")
    private Long acceptorId;


    /**
     * 评价者 ID
     */
    @ApiModelProperty(value = "评价者的用户ID")
    @TableField("ReviewerID")
    private Long reviewerId;

    @ApiModelProperty(value = "评价等级")
    @TableField("Rating")
    private Long rating;

    @ApiModelProperty(value = "评价评论")
    @TableField("COMMENT")
    private String comment;

    /**
     * 已获批准
     */
    @ApiModelProperty(value = "是否已批准")
    @TableField("IsApproved")
    private Boolean isApproved;


}