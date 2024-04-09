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
 * 存储系统用户详细信息
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("usersinfo")
@ApiModel(value="Usersinfo对象", description="存储系统用户详细信息")
public class UsersInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "UserID", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "电话号码")
    private String phoneNumber;

    @ApiModelProperty(value = "号码")
    private String qqNumber;

    @ApiModelProperty(value = "认证图片地址")
    private String roleImgSrc;

    @ApiModelProperty(value = "用户是否激活")
    private Boolean isActive;

    @ApiModelProperty(value = "用户角色")
    private String userRole;


}