package com.lz.config;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/06/30/下午3:34
 * @Description:
 */

import com.lz.handle.CustomReturnValueHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 返回值配置
 *
 * @author lz
 * @date 2024/07/06
 */
@Configuration
public class ReturnValueConfig implements InitializingBean {
    @Resource
    RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    /**
     * 初始化自定义的返回值处理器。
     * 在Spring MVC中，处理方法返回值的处理器是通过HandlerMethodReturnValueHandler接口实现的。
     * 这个方法的目的是在Spring提供的处理器基础上，添加自定义的处理器逻辑。
     * 具体做法是遍历已有的返回值处理器列表，如果处理器是RequestResponseBodyMethodProcessor的实例，
     * 则用自定义的MyHandlerMethodReturnValueHandler替换它；否则直接添加到新列表中。
     * 最后，将新列表设置为RequestMappingHandlerAdapter的返回值处理器。
     * 这样，当方法返回值需要被处理时，会使用这个自定义处理器列表，从而实现特定的业务逻辑。
     *
     * @throws Exception 如果设置返回值处理器时发生错误，则抛出异常。
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 获取当前RequestMappingHandlerAdapter的返回值处理器列表
        List<HandlerMethodReturnValueHandler> unmodifiableList = this.requestMappingHandlerAdapter.getReturnValueHandlers();
        // 创建一个新的返回值处理器列表，用于存放修改后的处理器
        List<HandlerMethodReturnValueHandler> list = new ArrayList<>(unmodifiableList.size());
        // 遍历原返回值处理器列表
        for (HandlerMethodReturnValueHandler returnValueHandler : unmodifiableList) {
            // 如果当前处理器是RequestResponseBodyMethodProcessor的实例
            if (returnValueHandler instanceof RequestResponseBodyMethodProcessor) {
                // 用自定义的处理器替换它，并添加到新列表中
                list.add(new CustomReturnValueHandler(returnValueHandler));
            } else {
                // 如果不是，则直接添加到新列表中
                list.add(returnValueHandler);
            }
        }
        // 将修改后的返回值处理器列表设置给RequestMappingHandlerAdapter
        this.requestMappingHandlerAdapter.setReturnValueHandlers(list);
    }
}