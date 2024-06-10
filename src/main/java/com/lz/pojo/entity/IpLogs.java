package com.lz.pojo.entity;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/06/10/22:39
 * @Description:
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * @author lz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@TableName("ip_logs")
@ApiModel(value="IP日志表", description="IP日志表")
public class IpLogs {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "IP日志ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "IP地址")
    @TableField("ip_address")
    private String ip;
    @ApiModelProperty(value = "访问时间")
    @TableField("access_time")
    private Date visitTime;
    @ApiModelProperty(value = "访问来源")
    @TableField("user_agent")
    private String userAgent;
}