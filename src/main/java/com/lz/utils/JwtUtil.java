package com.lz.utils;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lz
 * @Date: 2023/11/08/10:14
 * @Description:
 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

/**
 * @author lz
 */
public class JwtUtil {

    /**
     * 生成JWT
     *
     * @param claims 索赔
     *
     * @return {@code String}
     */
    public static String genToken(Map<String, Object> claims, String key) {

        return JWT.create()
                // 该声明存储在JWT的payload（有效载荷）部分。"claims"参数是一个Map对象，可以存储任意数量的键值对。
                .withClaim("claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                // 使用指定的算法（Algorithm.HMAC256(KEY)）和密钥对JWT进行签名
                .sign(Algorithm.HMAC256(key));
    }

    /**
     * 解析令牌
     *
     * @param token 令 牌
     *
     * @return {@code Map<String,Object>}
     */
    public static Map<String, Object> parseToken(String token, String key) {
        return JWT.require(Algorithm.HMAC256(key))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }

    

}