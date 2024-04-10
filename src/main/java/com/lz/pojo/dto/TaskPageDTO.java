package com.lz.pojo.dto;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/10/15:36
 * @Description:
 */

import com.lz.pojo.Enum.TaskStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 委托信息分页查询
 * @author lz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="委托信息分页查询对象",description = "委托信息查询时传递的数据模型")
public class TaskPageDTO {
    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码")
    private Integer pageNum;
    /**
     * 每页显示条数
     */
    @ApiModelProperty(value = "每页显示条数")
    private Integer pageSize;
    /**
     * 搜索条件
     */
    @ApiModelProperty(value = "搜索条件")
    private String description;
    /**
     * 搜索地点
     */
    @ApiModelProperty(value = "搜索地点")
    private String location;
    /**
     * 搜索类型
     */
    @ApiModelProperty(value = "搜索类型")
    private String type;
    /**
     * 搜索状态
     */
    @ApiModelProperty(value = "搜索状态")
    private TaskStatus status;
    /**
     * 搜索开始时间
     */
    @ApiModelProperty(value = "搜索开始时间")
    private String startTime;
    /**
     * 搜索结束时间
     */
    @ApiModelProperty(value = "搜索结束时间")
    private String endTime;
}