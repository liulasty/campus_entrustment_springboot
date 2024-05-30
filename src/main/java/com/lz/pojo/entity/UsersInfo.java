package com.lz.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import com.lz.pojo.Enum.AuthenticationStatus;
import com.lz.pojo.Enum.handle.AuthenticationStatusTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 存储系统用户详细信息
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@Accessors(chain = true)
@TableName("usersinfo")
@ApiModel(value="Usersinfo对象", description="存储系统用户详细信息")
public class UsersInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "UserID")
    private Long userId;

    @ApiModelProperty(value = "姓名")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "电话号码")
    @TableField(value = "PhoneNumber")
    private String phoneNumber;

    @ApiModelProperty(value = "号码")
    @TableField(value = "qqNumber")
    private String qqNumber;

    @ApiModelProperty(value = "认证图片地址")
    @TableField(value = "roleImgSrc")
    private String roleImgSrc;



    /**
     * 认证时间
     */
    @ApiModelProperty(value = "认证申请时间")
    @TableField(value = "CertifieTime")
    private Date certifieTime;

    /**
     * 认证通过时间
     */
    @ApiModelProperty(value = "认证通过时间")
    @TableField(value = "CertifiedTime")
    private Date certifiedTime;

    @ApiModelProperty(value = "用户角色")
    @TableField(value = "UserRole")
    private String userRole;

    @ApiModelProperty(value = "认证状态")
    @TableField(value = "auth_status",typeHandler =
            AuthenticationStatusTypeHandler.class)
    private AuthenticationStatus authStatus;
    
}