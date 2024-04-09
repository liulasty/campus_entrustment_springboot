package com.lz.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="Reviews对象", description="存储用户对任务的评价信息")
public class Reviews implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评价ID")
    @TableId(value = "ReviewID", type = IdType.AUTO)
    private Integer reviewId;

    @ApiModelProperty(value = "对应的任务ID")
    private Integer taskId;

    @ApiModelProperty(value = "评价者的用户ID")
    private Integer reviewerId;

    @ApiModelProperty(value = "评价等级")
    private Integer rating;

    @ApiModelProperty(value = "评价评论")
    private String comment;

    @ApiModelProperty(value = "是否已批准")
    private Boolean isApproved;


}