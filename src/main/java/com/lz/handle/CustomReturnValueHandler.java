package com.lz.handle;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/06/29/下午4:10
 * @Description:
 */

import com.lz.Annotation.NoReturnHandle;
import com.lz.pojo.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lz
 */
@Slf4j
public class CustomReturnValueHandler implements HandlerMethodReturnValueHandler {

    private  HandlerMethodReturnValueHandler returnValueHandler;
    
    
    public CustomReturnValueHandler(HandlerMethodReturnValueHandler returnValueHandler) {
        this.returnValueHandler = returnValueHandler;
    }
    
    
    
    /**
     * 判断当前注解处理器是否支持给定方法参数的返回类型。
     * 
     * 本方法通过委托给returnValueHandler来确定是否支持特定的返回类型。
     * 这种设计允许灵活地处理返回值，而不需要直接在当前类中实现所有的支持逻辑。
     * 
     * @param returnType 方法参数的返回类型，用于检查是否支持。
     * @return 如果支持该返回类型，则返回true；否则返回false。
     */
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        log.info("返回值类型：{}", returnType.getParameterType().getName());
        return this.returnValueHandler.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, 
                                  MethodParameter returnType, 
                                  ModelAndViewContainer mavContainer, 
                                  NativeWebRequest webRequest) throws Exception {
        HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        log.info("返回值类型：{}", returnType.getParameterType().getName());
        // 判断外层是否由Result包裹
        if (returnValue instanceof Result) {
            this.returnValueHandler.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
            return;
        }

        // 判断该api是否需要是否处理返回值
        if (nativeRequest != null) {
            String method = nativeRequest.getMethod();
        }

        // 判断此方法上是否有直接放行不处理返回值的注解
        if (nativeRequest==null){
            this.returnValueHandler.handleReturnValue(returnValue, returnType,
                                                      mavContainer, webRequest);
        }
        /*
          从原生请求中获取最佳匹配的处理器方法。
          这一步是为了确定处理当前请求的具体方法，以便后续根据方法的特性进行进一步处理。
          @param nativeRequest 原生请求对象，从中获取最佳匹配的处理器属性。
         * @return HandlerMethod 对象，表示处理当前请求的方法。
         */
        HandlerMethod handlerMethod = (HandlerMethod) nativeRequest.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
        
        /*
          检查当前处理方法是否注解了 NoReturnHandle。
          这个注解用于标记那些在执行完成后不需要返回任何内容的方法。
          通过检查这个注解，可以决定是否需要对方法的执行结果进行处理。
         */
        boolean isNoReturnHandle = handlerMethod.getMethod().isAnnotationPresent(NoReturnHandle.class);
        
        if (isNoReturnHandle) {
            // 如果是，则直接返回
            this.returnValueHandler.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
            return;
        }
        Result<Object> result = Result.success(returnValue);
        this.returnValueHandler.handleReturnValue(result, returnType, mavContainer, webRequest);
    }
}