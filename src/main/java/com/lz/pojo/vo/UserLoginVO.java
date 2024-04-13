package com.lz.pojo.vo;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/13/7:32
 * @Description:
 */

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录 VO
 *
 * @author lz
 * @date 2024/04/13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="用户信息",description = "用户登录返回信息数据模型")
public class UserLoginVO {
    Long userId;
    String userName;
    String userType;
    String token;
}