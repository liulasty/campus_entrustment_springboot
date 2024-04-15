package com.lz.pojo.vo;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/14/16:29
 * @Description:
 */

import lombok.Data;

/**
 * 用户信息页面 VO
 *
 * @author lz
 * @date 2024/04/14
 */
@Data
public class UserInfoPageVO {
    private Boolean isAuthentication;
    private String userType;
}