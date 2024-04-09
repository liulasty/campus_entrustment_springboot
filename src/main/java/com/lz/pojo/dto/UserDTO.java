package com.lz.pojo.dto;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/04/21:32
 * @Description:
 */

import com.lz.Annotation.HaveNoBlank;
import com.lz.Annotation.emailVerification;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author lz
 */
@Data
@ApiModel(value="用户注册对象",description = "用户注册时传递的数据模型")
public class UserDTO {
    @ApiModelProperty(value = "用户名",required = true)
    @emailVerification
    private String username;
    @ApiModelProperty("密码")
    @HaveNoBlank
    @NotBlank(message = "password 不能为空")
    private String password;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("电话")
    private String phoneNumber;
    
}