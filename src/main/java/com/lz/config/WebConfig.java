package com.lz.config;

/*
 * Created with IntelliJ IDEA.
 *
 * @Author: lz
 * @Date: 2023/11/08/15:57
 * @Description:
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lz.Interceptor.JwtTokenAdminInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Web 配置
 *
 * @author lz
 * @date 2023/11/08
 */
@Configuration
@Slf4j
public class WebConfig extends WebMvcConfigurationSupport {


    

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        // registry.addInterceptor(jwtTokenAdminInterceptor)
        //         .addPathPatterns("/users/*")
        //         .excludePathPatterns(
        //                 "/doc.html",
        //                 "/swagger-resources",
        //                 "/user/user/login",
        //                 "/user/user/register",
        //                 "/error",
        //                 "/user/user/active/");

    }
    

    /**
     * 设置静态资源映射
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始设置静态资源映射");
        
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 通过knife4j生成接口文档
     *
     * @return
     */
    @Bean
    public Docket docket1() {
        log.info("准备生成接口文档");
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("校园委托项目接口文档")
                .version("1.0")
                .description("校园委托项目接口文档")
                .build();

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户端")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lz.controller"))
                .paths(PathSelectors.any())
                .build();
        log.info("接口文档生成完毕:{}",docket);
        return docket;
    }

    /**
     * 扩展Spring MVC消息转换器
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器...");
        

        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
                ObjectMapper objectMapper = jsonConverter.getObjectMapper();

                objectMapper.setDateFormat(new SimpleDateFormat("yyyy年MM" +
                                                                        "月dd" +
                                                                        "日HH:mm:ss"));
                // objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                // 或者使用 Java 8 DateTimeFormatter
                // objectMapper.setDateFormat(new StdDateFormat().withTimeZone(TimeZone.getTimeZone("UTC")));

                // 可以添加其他定制配置
            }
        }
        // 创建一个消息转换器对象
        // MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // 需要为消息转换器设置一个对象转换器，对象转换器可以将Java对象序列化为json数据
        // converter.setObjectMapper(new JacksonObjectMapper());
        // 将自己的消息转化器加入容器中
        // converters.add(0,converter);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setDateFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        
        registrar.registerFormatters(registry);
    }





    
}