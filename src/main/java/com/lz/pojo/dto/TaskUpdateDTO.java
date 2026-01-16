package com.lz.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 任务进度更新 DTO
 *
 * @author lz
 */
@Data
@ApiModel(value = "任务进度更新对象", description = "任务进度更新时传递的数据模型")
public class TaskUpdateDTO {
    @ApiModelProperty(value = "任务ID", required = true)
    @NotNull(message = "任务ID不能为空")
    private Long taskId;

    @ApiModelProperty(value = "更新描述", required = true)
    @NotNull(message = "更新描述不能为空")
    private String description;
}
