package com.lz.pojo.Enum;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/15/17:42
 * @Description:
 */

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.Api;
import lombok.Getter;

/**
 * 身份认证状态
 *
 * @author lz
 * @date 2024/04/15
 */
@Getter
@Api(value = "身份认证状态")
public enum AuthenticationStatus  {
    /**
     * 未认证
     */
    UNAUTHORIZED(0, "未认证"),
    /**
     * 认证中
     */
    AUTHENTICATING(1, "认证中"),
    /**
     * 认证失败
     */
    AUTHENTICATION_FAILED(2, "认证失败"),
    /**
     * 认证通过
     */
    AUTHENTICATED(3, "认证通过");

    @EnumValue
    private final int dbValue;
    @JsonValue
    private final String description;

    AuthenticationStatus(int dbValue, String description) {
        this.dbValue = dbValue;
        this.description = description;
    }

    public static AuthenticationStatus fromDbValue(int dbValue) {
        for (AuthenticationStatus status : AuthenticationStatus.values()) {
            if (status.getDbValue() == dbValue ) {
                return status;
            }
        }
        return null;
    }
    
    
}