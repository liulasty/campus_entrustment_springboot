package com.lz.pojo.vo;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/14/11:37
 * @Description:
 */

import com.lz.pojo.Enum.AuthenticationStatus;
import lombok.Data;

import java.util.Date;

/**
 * 用户页面 vo
 *
 * @author lz
 * @date 2024/04/14
 */
@Data
public class UserPageVO {
    private Long userId;
    private String username;
    private String email;
    private String role;
    private Boolean isActive;
    private Date createTime;
    private Date activeTime;
    private AuthenticationStatus authStatus;
    private Boolean IsEnabled;
    
}