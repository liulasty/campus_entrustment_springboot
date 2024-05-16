package com.lz.common.security;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/05/17:59
 * @Description:
 */

import com.lz.Exception.MyException;
import com.lz.mapper.UsersMapper;
import com.lz.pojo.entity.Users;
import com.lz.service.IUsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.lz.Exception.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.temporal.ValueRange;
import java.util.*;

/**
 * @author lz
 */
@Service
@Slf4j
public class MyUserDetailServiceImpl implements UserDetailsService {
    
    @Autowired
    private UsersMapper usersMapper;
    private String s;


    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Users user = usersMapper.getByUsername(username);
        
        if (user == null) {
            log.info("该用户不存在");
            throw new UsernameNotFoundException("用户不存在");
        }
        
        if (user.getIsActive() == null || !user.getIsActive() ){
            log.error("用户未激活");
            
            throw new UsernameNotFoundException("用户未激活");
        }
        
        if ( !user.getIsEnabled()) {
            log.error("用户信息：{}", user);
            throw new UsernameNotFoundException("用户未启用");
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new User(username, user.getPassword(), authorities);
    }

    

   
}