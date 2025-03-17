package com.lz.common.security;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/06/0:46
 * @Description:
 */



import com.lz.Exception.InvalidTokenException;
import com.lz.Exception.MyException;
import com.lz.Exception.NoAthleteException;
import com.lz.config.AppConfig;
import com.lz.utils.JwtUtil;
import com.lz.utils.PathMatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * JWT验证过滤器
 *
 * @author lz
 * @date 2024/04/06
 */
@Slf4j
@Component
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {



    @Autowired
    private AppConfig appConfig;

    @Autowired
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
//            log.info("请求路径: {}", request.getRequestURI());
            if (PathMatcher.isUrlWhitelisted(request.getRequestURI())) {
                // log.info("请求路径在白名单中，无需验证");
                filterChain.doFilter(request, response);
            } else {
                String token = request.getHeader("JWT");
                // log.info("请求令牌: {}", token);
                if (isValidToken(token)) {
                    // log.info("令牌有效");
                    filterChain.doFilter(request, response);
                } else {
                    throw new InvalidTokenException("令牌无效");
                }
            }
        } catch (InvalidTokenException e) {
            log.error("令牌验证失败: {}", e.getMessage());

            request.setAttribute("javax.servlet.error.status_code", HttpServletResponse.SC_UNAUTHORIZED);
            request.setAttribute("exception", "令牌无效或过期");
            // 转发到/error
            request.getRequestDispatcher("/error").forward(request, response);
        }


    }


    private boolean isValidToken(String token) {

        if (token == null || token.isEmpty()) {
            return false;
        }
        try {
            Map<String, Object> map = JwtUtil.parseToken(token, appConfig.getJwtKey());

            if (map == null) {
                log.error("jwt携带信息为空");
                throw new IllegalArgumentException("jwt携带信息为空");
            }

            String username = convertToString(map.get("username"));
            String role = convertToString(map.get("role"));

            if (username == null || role == null) {
                log.error("jwt携带信息为空");
                return false;
            }




            AuthenticationService authenticationService = new AuthenticationService();
            authenticationService.setAuthentication(username, role);


            List<String> roles = extractUserRoles();
            // log.info("用户: {} 角色: {}", username, roles);
            return true;
        } catch (Exception ex) {
            log.error("解析jwt失败: {}", ex.getMessage());
            return false;

        }
    }

    private static String convertToString(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        } else {
            return null;
        }
    }

    /**
     * 提取当前用户的角色列表。
     * @return 当前用户的角色列表，如果无法获取或角色列表为空，则返回空列表。
     */
    public static List<String> extractUserRoles() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication.getAuthorities() == null) {
                // 安全上下文或权限列表为空，直接返回空列表
                log.error("安全上下文或权限列表为空,无法获取当前用户的角色列表");
                return Collections.emptyList();
            }

            // 使用Stream API转换权限列表为角色字符串列表
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            return authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // 捕获在提取角色过程中可能发生的任何异常，避免程序因为安全相关的问题崩溃
            // 根据实际情况可以记录日志或者进行其他异常处理
            log.error("提取当前用户的角色列表时发生异常: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}