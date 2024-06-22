package com.lz.pojo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
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
    @ExcelProperty(value = "用户ID", index = 0)
    private Long reviewId;

    @ApiModelProperty(value = "对应的任务ID")
    @TableField("TaskID")
    @ExcelProperty(value = "委托ID", index = 1)
    private Long taskId;

    /**
     * 发布者 ID
     */
    @ApiModelProperty(value = "委托发布者的用户ID")
    @TableField("PublisherID")
    @ExcelProperty(value = "发布者ID", index = 2)
    private Long publisherId;

    /**
     * 接受者 ID
     */
    @ApiModelProperty(value = "委托接收者的用户ID")
    @TableField("AcceptorID")
    @ExcelProperty(value = "接受者ID", index = 3)
    private Long acceptorId;


    /**
     * 评价者 ID
     */
    @ApiModelProperty(value = "评价者的用户ID")
    @TableField("ReviewerID")
    @ExcelProperty(value = "评价者ID", index = 4)
    private Long reviewerId;

    @ApiModelProperty(value = "评价等级")
    @TableField("Rating")
    @ExcelProperty(value = "评价等级", index = 5)
    private Long rating;

    @ApiModelProperty(value = "评价评论")
    @TableField("COMMENT")
    @ExcelProperty(value = "评价评论", index = 6)
    private String comment;

    /**
     * 已获批准
     */
    @ApiModelProperty(value = "是否已批准")
    @TableField("IsApproved")
    @ExcelProperty(value = "是否已批准", index = 7)
    private Boolean isApproved;


}