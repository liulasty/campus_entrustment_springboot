package com.lz.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.pojo.Enum.AnnouncementStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 存储系统公告的信息
 * </p>
 *
 * @author lz
 * @since 2024-04-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_announcements")
@ApiModel(value="SystemAnnouncements对象", description="存储系统公告的信息")
public class SystemAnnouncements implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公告ID，主键，自增")
    @TableId(value = "announcement_id", type = IdType.AUTO)
    private Long announcementId;

    @ApiModelProperty(value = "公告标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "公告内容，可使用TEXT类型容纳较长文本")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "发布者ID，外键关联用户表或其他相关表")
    @TableField("publisher_id")
    private Long publisherId;

    @ApiModelProperty(value = "发布时间")
    @TableField("publish_time")
    @JsonFormat(pattern = "yyyy年MM月dd日HH:mm:ss")
    private Date  publishTime;

    @ApiModelProperty(value = "公告状态（如：草稿、已发布、已撤回等）")
    @TableField("status")
    private String status;

    @ApiModelProperty(value = "公告开始生效时间（如有）")
    @TableField("start_effective_time")
    @JsonFormat(pattern = "yyyy年MM月dd日HH:mm:ss")
    private Date startEffectiveTime;

    @ApiModelProperty(value = "公告结束生效时间（如有）")
    @TableField("end_effective_time")
    @JsonFormat(pattern = "yyyy年MM月dd日HH:mm:ss")
    private Date endEffectiveTime;

    @ApiModelProperty(value = "是否置顶（0：否，1：是）")
    @TableField("is_pinned")
    private Boolean isPinned;

    @ApiModelProperty(value = "创建时间")
    @TableField("created_at")
    private Date createdAt;

    @ApiModelProperty(value = "更新时间")
    @TableField("updated_at")
    private Date updatedAt;
    
    
    @ApiModelProperty(value = "最后更新者编号")
    @TableField("updated_by")
    private Long updatedBy;


}