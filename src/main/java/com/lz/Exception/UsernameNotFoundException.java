package com.lz.Exception;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/16/8:28
 * @Description:
 */

import org.springframework.security.core.AuthenticationException;

/**
 * @author lz
 */
public class UsernameNotFoundException extends AuthenticationException {
    public UsernameNotFoundException(String msg) {
        super(msg);
    }

    public UsernameNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}