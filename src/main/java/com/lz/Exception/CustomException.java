package com.lz.Exception;

/**
 * Created with IntelliJ IDEA.
 *
 * @author lz
 * @Author: lz
 * @Date: 2024/03/11/18:23
 * @Description:
 * @date 2024/03/11
 */

public class CustomException extends Exception {
    private String message;
    
    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * 返回此可抛出对象的详细信息消息字符串。
     *
     * @return此 {@code Throwable} 实例的详细消息字符串
     *（可能是 {@code null}）。
     */
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}