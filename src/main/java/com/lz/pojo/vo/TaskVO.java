package com.lz.pojo.vo;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/10/23:34
 * @Description:
 */

import com.lz.pojo.Enum.TaskStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    
    @ApiModelProperty("委托发起人")
    private Long ownerId;
    
    @ApiModelProperty("委托创建时间")
    private Date createdAt;
    /**
     * 委托描述
     */
    @ApiModelProperty("委托描述")
    private String content;
    /**
     * 类型
     */
    @ApiModelProperty("委托类型")
    private TaskStatus type;
    /**
     * 开始时间
     */
    @ApiModelProperty("委托开始时间")
    private Date startTime;
    /**
     * 结束时间
     */
    @ApiModelProperty("委托结束时间")
    private Date endTime;
    
}