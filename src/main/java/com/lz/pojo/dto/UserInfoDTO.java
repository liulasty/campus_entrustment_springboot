package com.lz.pojo.dto;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/15/0:44
 * @Description:
 */

import lombok.Data;

/**
 * 用户信息 DTO
 *
 * @author lz
 * @date 2024/04/15
 */
@Data
public class UserInfoDTO {
    private Long id;
    private String imgUrl;
    private String name;
    private String qq;
    private String phone;
    private String role;
}