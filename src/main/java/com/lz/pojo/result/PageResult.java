package com.lz.pojo.result;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 封装分页查询结果
 * @author lz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("基类响应参数")
public class PageResult<T> implements Serializable {

    private long total; //总记录数

    /**
    当前页数据集合
     */
    private List<T> records;

    
    
    
}