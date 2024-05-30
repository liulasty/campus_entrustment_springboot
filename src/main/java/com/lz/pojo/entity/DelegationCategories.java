package com.lz.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用于存储委托类别常量的表
 * </p>
 *
 * @author lz
 * @since 2024-04-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "delegation_categories")
@ApiModel(value="DelegationCategories对象", description="用于存储委托类别常量的表")
public class DelegationCategories implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "委托类别唯一标识符")
    @TableId(value = "category_id", type = IdType.AUTO)
    private Long categoryId;

    @ApiModelProperty(value = "委托类别的名称，要求唯一")
    @TableField(value = "category_name")
    private String categoryName;

    @ApiModelProperty(value = "委托类别的详细描述，可为空")
    @TableField(value = "category_description")
    private String categoryDescription;
    
    @ApiModelProperty(value = "是否启用，默认为启用")
    @TableField(value = "isEnable")
    private Boolean isEnabled;


}