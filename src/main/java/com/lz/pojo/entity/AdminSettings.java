package com.lz.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 存储系统管理员相关设置
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("adminsettings")
@ApiModel(value="AdminSettings对象", description="存储系统管理员相关设置")
public class AdminSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设置ID")
    @TableId(value = "SettingID", type = IdType.AUTO)
    private Long settingID;

    @ApiModelProperty(value = "设置键")
    @TableField("SettingKey")
    private String settingKey;

    @ApiModelProperty(value = "设置值")
    @TableField("SettingValue")
    private Long settingValue;
    


}