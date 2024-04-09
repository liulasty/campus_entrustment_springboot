package com.lz.utils;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/04/21:16
 * @Description:
 */

/**
 * @author lz
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
public class PasswordUtils implements PasswordEncoder {
    
    /**
     * 使用BCrypt算法哈希密码。
     * @param plainTextPassword 明文密码。
     * @return 哈希后的密码。
     */
    public static String hashPassword(String plainTextPassword) {
        log.info("明文密码:{}然后加密",plainTextPassword);
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    /**
     * 检查明文密码与哈希后的密码是否匹配。
     * @param plainTextPassword 明文密码。
     * @param hashedPassword 哈希后的密码。
     * @return 如果密码匹配，则返回true；否则返回false。
     */
    public static boolean check(String plainTextPassword, String hashedPassword) {
        log.info("明文密码:{},哈希后的密码:{}",plainTextPassword,hashedPassword);
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

    /**
     * 编码
     *
     * @param rawPassword 原始密码
     *
     * @return {@code String}
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return hashPassword(rawPassword.toString());
    }

    /**
     * 检查明文密码与哈希后的密码是否匹配。
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 编码密码
     *
     * @return boolean
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return check(rawPassword.toString(), encodedPassword);
    }
    
}