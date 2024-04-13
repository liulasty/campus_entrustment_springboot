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

import com.lz.common.security.JWTAuthorizationFilter;
import com.lz.common.security.MyUserDetailServiceImpl;
import com.lz.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
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
            "/doc.html"
    };

    @Autowired
    private MyUserDetailServiceImpl myUserDetailService;

    @Autowired
    @Lazy
    private JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // 将AuthenticationManager作为一个Bean暴露出来
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用自定义的密码加密工具
        return new PasswordUtils();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 配置AuthenticationManager以使用自定义的userDetailsService和passwordEncoder
        auth.userDetailsService(myUserDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 配置忽略对AUTH_WHITELIST中指定路径的安全检查
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                .loginProcessingUrl(PATH_SEPARATOR + "/error")
                .and()
                .addFilterBefore(jwtAuthorizationFilter, BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated();// 其他请求都需要认证
    }
    
    

}