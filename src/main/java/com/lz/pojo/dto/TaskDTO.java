package com.lz.pojo.dto;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/05/6:51
 * @Description:
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author lz
 */
@Data
@ApiModel(value="委托信息对象",description = "用户创建时传递的数据模型")
public class TaskDTO {
    @ApiModelProperty(value = "委托标题",required = true)
    @NotBlank(message = "委托标题 不能为空")
    String title;
    
    @ApiModelProperty(value = "委托地点",required = true)
    @NotBlank(message = "委托地点 不能为空")
    String location;
    
    @ApiModelProperty(value = "委托内容",required = true)
    @NotBlank(message = "委托内容 不能为空")
    String content;
    
    @ApiModelProperty(value = "委托类型",required = true)
    @NotBlank(message = "委托类型 不能为空")
    String type;
    
    
    @ApiModelProperty(value = "委托开始时间",required = true)
    @NotBlank(message = "委托开始时间 不能为空")
    String startTime;
    
    @ApiModelProperty(value = "委托结束时间",required = true)
    @NotBlank(message = "委托结束时间 不能为空")
    String endTime;
    
    public String validate() {
        if (title == null || "".equals(title)) {
            return "title 不能为空";
        }
        if (location == null || "".equals(location)) {
            return "location 不能为空";
        }
        if (content == null || "".equals(content)) {
            return "content 不能为空";
        }
        if (type == null || "".equals(type)) {
            return "type 不能为空";
        }
        
        
        return null;
    }
    
    
}