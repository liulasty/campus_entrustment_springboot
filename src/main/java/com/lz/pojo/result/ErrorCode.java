package com.lz.pojo.result;

import lombok.Getter;

/**
 * 统一错误码枚举
 * @author lz
 */
@Getter
public enum ErrorCode {

    SUCCESS(200, "操作成功"),
    SYSTEM_ERROR(500, "系统内部异常"),
    PARAM_ERROR(400, "参数校验失败"),
    UNAUTHORIZED(401, "未授权或授权已过期"),
    FORBIDDEN(403, "没有权限，禁止访问"),
    NOT_FOUND(404, "请求的资源不存在"),
    METHOD_NOT_ALLOWED(405, "不支持当前请求方法"),
    
    // 业务相关
    USER_NOT_FOUND(1001, "用户不存在"),
    PASSWORD_ERROR(1002, "密码错误"),
    ACCOUNT_LOCKED(1003, "账号已被锁定"),
    TOKEN_INVALID(1004, "无效的令牌"),
    TOKEN_EXPIRED(1005, "令牌已过期"),
    
    // 数据库相关
    DUPLICATE_KEY(2001, "数据已存在"),
    DATABASE_ERROR(2002, "数据库操作异常"),
    
    // 自定义异常
    BUSINESS_ERROR(3000, "业务异常");

    private final int code;
    private final String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
