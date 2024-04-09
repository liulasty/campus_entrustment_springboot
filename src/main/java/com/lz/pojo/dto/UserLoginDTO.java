package com.lz.pojo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.lz.Annotation.HaveNoBlank;
import com.lz.Annotation.emailVerification;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author lz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="用户登录对象",description = "用户登录时传递的数据模型")
public class UserLoginDTO implements Serializable{
    
    @ApiModelProperty(value = "用户名",required = true)
    @emailVerification
    private String username;

    @ApiModelProperty(value ="密码",required = true)
    @HaveNoBlank
    @NotBlank(message = "password 不能为空")
    private String password;

   

    

}