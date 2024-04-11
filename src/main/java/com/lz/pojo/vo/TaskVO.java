package com.lz.pojo.vo;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/10/23:34
 * @Description:
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 委托信息前端展示
 * @author lz
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="委托信息",description = "委托信息数据模型")
public class TaskVO {

    /**
     * 委托编号
     */
    @ApiModelProperty("委托编号")
    private Long id;
    /**
     * 委托位置
     */
    @ApiModelProperty("委托位置")
    private String location;
    /**
     * 委托标题
     */
    @ApiModelProperty("委托标题")
    private String title;
    /**
     * 委托描述
     */
    @ApiModelProperty("委托描述")
    private String content;
    /**
     * 类型
     */
    @ApiModelProperty("委托类型")
    private String type;
    /**
     * 开始时间
     */
    @ApiModelProperty("委托开始时间")
    private String startTime;
    /**
     * 结束时间
     */
    @ApiModelProperty("委托结束时间")
    private String endTime;
    
}