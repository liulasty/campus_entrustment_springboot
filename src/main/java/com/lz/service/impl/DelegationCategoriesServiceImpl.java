package com.lz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectByMap;
import com.baomidou.mybatisplus.core.injector.methods.SelectCount;
import com.baomidou.mybatisplus.core.injector.methods.SelectMaps;
import com.baomidou.mybatisplus.core.injector.methods.SelectOne;
import com.lz.Exception.MyException;
import com.lz.pojo.Enum.TaskStatus;
import com.lz.pojo.entity.DelegationCategories;
import com.lz.mapper.DelegationCategoriesMapper;
import com.lz.pojo.entity.Task;
import com.lz.pojo.result.NameAndDescription;
import com.lz.pojo.vo.TaskTypeVO;
import com.lz.service.IDelegationCategoriesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 用于存储委托类别常量的表 服务实现类
 * </p>
 *
 * @author lz
 * @since 2024-04-10
 */
@Service
public class DelegationCategoriesServiceImpl extends ServiceImpl<DelegationCategoriesMapper, DelegationCategories> implements IDelegationCategoriesService {

    /**
     * 获取委托类别
     *
     * @return {@code List<DelegationCategories>}
     */
    @Override
    public List<NameAndDescription> getTaskCategory() throws MyException {
        List<DelegationCategories> list = list();

        if (list.size() == 0) {
            throw new MyException("委托类别为空");
        }


        List<NameAndDescription> nameAndDescriptions = new ArrayList<>();
        list.forEach(l -> {
            nameAndDescriptions.add(new NameAndDescription(l.getCategoryId(),
                                                           l.getCategoryName(), l.getCategoryDescription()));
        });

        return nameAndDescriptions;
    }

    /**
     * 根据名字查询相关实体
     *
     * @return {@code DelegationCategories}
     */
    @Override
    public DelegationCategories getTaskCategoryByCategoryName(String name) {
        // 设置查询条件
        QueryWrapper<DelegationCategories> wrapper = new QueryWrapper<>();
        // 设置查询条件
        wrapper
                
                // 设置查询条件
                .eq("category_name", name)

        ;
        return getOne(wrapper);
    }
}