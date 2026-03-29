package com.lz.pojo.result;

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

    @Schema(description = "状态码：1成功，0和其它数字为失败")
    private Integer code;

    @Schema(description = "返回信息")
    private String msg;

    @Schema(description = "返回数据")
    private T data;

    @Schema(description = "错误代码")
    private Integer errorCode;

    public Result() {
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // ===================== 成功返回 =====================
    public static <T> Result<T> success() {
        return new Result<>(1, "操作成功", null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(1, "操作成功", data);
    }

    public static <T> Result<T> success(String msg) {
        return new Result<>(1, msg, null);
    }

    public static <T> Result<T> success(T data, String msg) {
        return new Result<>(1, msg, data);
    }

    // ===================== 失败返回 =====================
    public static <T> Result<T> error(String msg) {
        return new Result<>(0, msg, null);
    }

    public static <T> Result<T> error(Integer errorCode, String msg) {
        Result<T> result = new Result<>(0, msg, null);
        result.setErrorCode(errorCode);
        return result;
    }

    // The method error(Integer, String) in the type Result is not applicable for
    // the arguments (ErrorCode, String)
    public static <T> Result<T> error(ErrorCode errorCode, String msg) {
        return error(errorCode.getCode(), msg);
    }

    // The method error(ErrorCode, String) in the type Result is not applicable for
    // the arguments (ErrorCode)
    public static <T> Result<T> error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMsg());
    }

    // 链式调用
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
}