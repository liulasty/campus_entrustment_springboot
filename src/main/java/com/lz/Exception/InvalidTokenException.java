package com.lz.Exception;

   public class InvalidTokenException extends RuntimeException {
       private String message;
       
       public InvalidTokenException(String message) {
           
           super(message);
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