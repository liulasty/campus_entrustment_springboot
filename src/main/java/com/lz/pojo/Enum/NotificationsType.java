package com.lz.pojo.Enum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * 通知类型
 *
 * @author lz
 * @date 2024/04/10
 */
@ApiModel(value = "通知类型枚举")
@Getter
public enum NotificationsType {
    /**
     * 都
     */
    OWN("OWN","个人信息通知"),
    TASK("USER","委托信息通知"),
    SYSTEM("SYSTEM","系统信息通知"),
    MARKETING("MARKETING","营销信息通知");
    

    @ApiModelProperty(value = "数据库值")
    @EnumValue
    final String dbValue;
    @ApiModelProperty(value = "前端展示值")
    @JsonValue
    final String webValue;

    NotificationsType(String dbValue, String webValue) {
        this.dbValue = dbValue;
        this.webValue = webValue;
    }

    public static NotificationsType fromDbValue(String dbValue) {
        for (NotificationsType type : values()) {
            if (type.getDbValue().equalsIgnoreCase(dbValue)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的通知类型值: " + dbValue);
    }
}