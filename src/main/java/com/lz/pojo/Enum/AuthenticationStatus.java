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

/**
 * 身份认证状态
 *
 * @author lz
 * @date 2024/04/15
 */

public enum AuthenticationStatus implements IEnum<Integer> {
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
    private final int code;
    
    private final String description;

    AuthenticationStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public Integer getValue() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static AuthenticationStatus ofCode(int code) {
        for (AuthenticationStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid auth status code: " + code);
    }
}