package com.lz.mapper;

import com.lz.pojo.entity.DelegationCategories;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用于存储委托类别常量的表 Mapper 接口
 * </p>
 *
 * @author lz
 * @since 2024-04-10
 */
@Mapper
public interface DelegationCategoriesMapper extends BaseMapper<DelegationCategories> {

}