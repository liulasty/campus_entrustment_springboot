package com.lz.pojo.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 后端统一返回结果
 *
 * @param <T> 返回的数据类型
 */
@Data
@Schema(description = "统一响应结果类")
public class Result<T> implements Serializable {

    /**
     * 状态码：1成功，0和其它数字为失败
     */
    @Schema(description = "状态码：1成功，0和其它数字为失败")
    private Integer code;

    /**
     * 信息
     */
    @Schema(description = "返回信息")
    private String msg;

    /**
     * 数据
     */
    @Schema(description = "返回数据")
    private T data;

    @Schema(description = "错误代码")
    private Integer errorCode;

    public Result() {}

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(Integer code, String msg) {
        this(code, msg, null);
    }

    public Result(Integer code, T data) {
        this(code, null, data);
    }

    public static <T> Result<T> success() {
        return new Result<>(1, null, null);
    }

    public static <T> Result<T> success(T object) {
        return new Result<>(1, null, object);
    }

    public static <T> Result<T> success(T object, String msg) {
        return new Result<>(1, msg, object);
    }

    public static <T> Result<T> success(String msg) {
        return new Result<>(1, msg, null);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(0, msg, null);
    }

    public static <T> Result<T> error(T object) {
        return new Result<>(0, null, object);
    }

    public static <T> Result<T> error(Integer errorCode, String msg) {
        Result<T> result = new Result<>(0, msg, null);
        result.setErrorCode(errorCode);
        return result;
    }

    public Result<T> code(Integer code) {
        this.code = code;
        return this;
    }

    public Result<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public Result<T> data(T data) {
        this.data = data;
        return this;
    }

    public Result<T> errorCode(Integer errorCode) {
        this.errorCode = errorCode;
        return this;
    }
}