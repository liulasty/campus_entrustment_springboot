package com.lz.common.security;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/05/17:59
 * @Description:
 */

import com.lz.mapper.UsersMapper;
import com.lz.pojo.entity.Users;
import com.lz.service.IUsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author lz
 */
@Service
@Slf4j
public class MyUserDetailServiceImpl implements UserDetailsService {
    
    @Autowired
    private UsersMapper usersMapper;
    

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
            throw new UsernameNotFoundException("用户不存在");
        }
        
        if (user.getIsActive() == null || !user.getIsActive()) {
            log.info("用户信息：{}", user);
            throw new UsernameNotFoundException("用户未激活");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new User(username, user.getPassword(), authorities);
    }

   
}