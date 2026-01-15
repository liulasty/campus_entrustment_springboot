package com.lz.config;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/05/16:39
 * @Description:
 */

/**
 * @author lz
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lz.common.security.JWTAuthorizationFilter;
import com.lz.common.security.MyUserDetailServiceImpl;
import com.lz.pojo.result.ErrorCode;
import com.lz.pojo.result.Result;
import com.lz.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
/*
 * 自定义Web安全配置类
 * 该类用于配置Spring Security的Web安全设置，包括认证和授权等。
 */
public class WebSecurityConfig {
    private static final String PATH_SEPARATOR = "/campus_entrustment";
    private static final String[] AUTH_WHITELIST = {
            PATH_SEPARATOR + "/user/login",
            "/user/login",
            "/user/register",
            "/user/active/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**",
            "/swagger-resources",
            "/v2/api-docs",
            "/druid/**",
            "/doc.html",
            "/img/upload",
            "/user/logout",
            "/common/**",
            "/favicon.ico"
    };

    @Autowired
    private MyUserDetailServiceImpl myUserDetailService;

    @Autowired
    @Lazy
    private JWTAuthorizationFilter jwtAuthorizationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    Result<String> result = Result.error(ErrorCode.UNAUTHORIZED);
                    response.getWriter().write(objectMapper.writeValueAsString(result));
                })
                .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用自定义的密码加密工具
        return new PasswordUtils();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myUserDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}