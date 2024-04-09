package com.lz.pojo.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 后端统一返回结果
 * @author lz
 * @param <T>
 */
@Data
@Schema
public class Result<T> implements Serializable {

    /**
     * 编码：1成功，0和其它数字为失败
     */
    private Integer code; 
    /**
     * 信息
     */
    private String msg; 
    /**
     * 数据
     */
    private T data; 

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 1;
        return result;
    }

    /**
     * 生成一个表示成功的结果对象。
     * 
     * @param object 成功时返回的数据对象。
     * @param <T> 返回数据的类型。
     * @return 返回一个初始化为成功状态的 Result 对象，其中包含了指定的数据对象。
     */
    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>(); 
        result.data = object; 
        result.code = 1; 
        return result; 
    }

    public static <T> Result<T> success(T object,String msg) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 1;
        result.msg = msg;
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<T>();
        result.msg = msg;
        result.code = 0;
        return result;
    }

    public static <T> Result<T> error(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 0;
        return result;
    }

    public static <T> Result<T> success(String msg) {
        Result<T> result = new Result<T>();
        result.msg = msg;
        result.code = 1;
        return result;
    }

}