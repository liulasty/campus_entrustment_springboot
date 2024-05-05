package com.lz.common.security;

import com.lz.Exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 身份验证服务
 *
 * @author lz
 * @date 2024/05/03
 */
@Slf4j
public class AuthenticationService {

    /**
     * 设置用户认证信息到安全上下文。
     * @param username 用户名
     * @param role 用户角色
     */
    public void setAuthentication(String username, String role) {
        // 验证role的有效性
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("角色不能为空");
        }
                           
        // 创建角色授权列表
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));

        // 创建认证令牌
        UsernamePasswordAuthenticationToken authentication = 
            new UsernamePasswordAuthenticationToken(username, "password",
                                                    authorities);

        try {
            // 将认证令牌设置到安全上下文中
            // log.info("设置用户认证信息到安全上下文 {},{}",authentication.getPrincipal(),
            //          authentication.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
        } catch (Exception e) {
            // 处理可能的异常，例如日志记录或回滚操作
            log.error("设置用户认证信息到安全上下文失败", e);
            // 这里是简单的打印异常堆栈，根据实际需求进行修改
            e.printStackTrace();
        }
    }

    /**
     * 提取用户认证信息
     */
    public Authentication getAuthentication() throws MyException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new MyException("用户尚未登录，请先登录");
        }
        log.info("获取用户认证信息: {}",authentication);
        // 判断是否为匿名认证
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new MyException("用户尚未登录，请先登录");
        }

        // 检查用户是否已认证
        if (!authentication.isAuthenticated()) {
            throw new MyException("用户认证失败");
        }

        // 获取用户权限并检查是否具有ROLE_USER
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        log.info("获取用户权限: {}",authorities);

        return authentication;
    }
}