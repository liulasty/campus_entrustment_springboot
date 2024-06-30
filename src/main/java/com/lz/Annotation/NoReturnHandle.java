package com.lz.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/06/29/下午4:14
 * @Description:自定义注解 - 返回值不做封装处理
 */
// 作用到方法上
@Target(ElementType.METHOD)
// 运行时有效
@Retention(RetentionPolicy.RUNTIME)
public @interface NoReturnHandle {
}