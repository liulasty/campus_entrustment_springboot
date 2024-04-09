package com.lz.pojo.dto;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/05/0:07
 * @Description:
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lz
 */
@Data
@ApiModel(value="用户查询对象",description = "查询用户时传递的数据模型")
public class UsersPageDTO implements Serializable {
    @ApiModelProperty(value = "用户名")
    private String username;
    
    
    @ApiModelProperty(value = "角色")
    private String role;
    
    @ApiModelProperty(value = "用户是否激活")
    private Boolean isActive;
    
    @ApiModelProperty(value = "页码",required = true)
    private Integer page;
    
    @ApiModelProperty(value = "每页显示条数",required = true)
    private Integer size;
    
    public String validate() {
        if (page == null || page < 1) {
            return "页码不能小于1";
        }
        if (size == null || size < 1) {
            return "每页显示条数不能小于1";
        }
        return null;
    }
    
    
}