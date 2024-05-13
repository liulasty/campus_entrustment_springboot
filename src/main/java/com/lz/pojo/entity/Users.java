package com.lz.pojo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户
 *
 * @author lz
 * @date 2024/04/04
 * @since 2024-04-04
 */
@Data
// 重写equals和hashCode方法，不调用父类的equals和hashCode方法
@EqualsAndHashCode(callSuper = false)
@TableName("users")
@Builder
@ApiModel(value="Users对象", description="存储系统用户信息")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "UserID", type = IdType.AUTO)
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "电子邮件地址")
    private String email;



    @ApiModelProperty(value = "用户是否激活")
    @TableField("IsActive")
    private Boolean isActive;

    @ApiModelProperty(value = "用户角色")
    private String role;
    @TableField(value = "CreateTime")
    private Date createTime;
    @TableField(value = "ActiveTime")
    private Date activeTime;

    /**
     * 是否启用
     */
    @ApiModelProperty(value = "是否启用")
    @TableField("IsEnabled")
    private Boolean isEnabled;

    
}