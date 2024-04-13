package com.lz.pojo.dto;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/05/6:51
 * @Description:
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author lz
 */
@Data
@ApiModel(value="委托信息对象",description = "用户创建时传递的数据模型")
public class TaskDTO {
    @ApiModelProperty(value = "委托ID",required = true)
    Long taskId;
    /**
     *委托人
     */
    @ApiModelProperty(value = "委托人id",required = true)
    Long ownerId;
    
    @ApiModelProperty(value = "委托地点",required = true)
    @NotBlank(message = "委托地点 不能为空")
    String location;
    
    @ApiModelProperty(value = "委托内容",required = true)
    @NotBlank(message = "委托内容 不能为空")
    String content;
    
    @ApiModelProperty(value = "委托类型",required = true)
    @NotBlank(message = "委托类型 不能为空")
    String type;
    
    
    
    
    
}