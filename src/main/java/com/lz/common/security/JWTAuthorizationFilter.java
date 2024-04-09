package com.lz.common.security;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/06/0:46
 * @Description:
 */

/**
 * @author lz
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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

    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_USER = "ROLE_USER";


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
            log.info("请求路径: {}", request.getRequestURI());
            if (PathMatcher.isUrlWhitelisted(request.getRequestURI())) {
                log.info("请求路径在白名单中，无需验证");
                filterChain.doFilter(request, response);
            } else {
                String token = request.getHeader("JWT");
                log.info("请求令牌: {}", token);
                if (isValidToken(token)) {
                    log.info("令牌有效");
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
                throw new IllegalArgumentException("Map cannot be null");
            }

            String username = convertToString(map.get("username"));
            String role = convertToString(map.get("role"));

            if (username == null || role == null) {
                return false;
            }

            if (!role.equals(ROLE_ADMIN) && !role.equals(ROLE_USER)) {
                // 如果是非法角色
                log.error("非法角色: {}", role); // 使用参数化日志记录
                return false;
            }


            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Collection<? extends GrantedAuthority> authorities1 = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            List<String> roles = authorities1.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            if (roles.contains(ROLE_ADMIN)) {
                log.info("用户: {} 是管理员", username);
            } else if (roles.contains(ROLE_USER)) {
                log.info("用户: {} 是普通用户", username);
            } else {
                log.error("用户: {} 没有任何角色", username);

            }
            log.info("用户: {} 角色: {}", username, role);
            return true;
        } catch (Exception ex) {
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
}