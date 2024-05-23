package com.lz.pojo.dto;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/23/16:03
 * @Description:
 */

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 修改密码 DTO
 *
 * @author lz
 * @date 2024/05/23
 */
@Data
public class PassWordDTO {
    @NotBlank(message = "password 不能为空")
    private String oldPassword;
    @NotBlank(message = "password 不能为空")
    private String newPassword;
    @NotBlank(message = "password 不能为空")
    private String confirmPassword;
}