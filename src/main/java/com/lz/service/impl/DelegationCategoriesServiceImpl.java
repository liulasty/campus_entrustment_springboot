package com.lz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.Exception.MyException;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.entity.DelegationCategories;
import com.lz.mapper.DelegationCategoriesMapper;
import com.lz.pojo.result.NameAndDescription;
import com.lz.pojo.result.PageResult;
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
        QueryWrapper<DelegationCategories> wrapper = new QueryWrapper<>();
        wrapper
                .orderBy(true, false, "category_id");
        return getNameAndDescriptions(wrapper);
    }

    private List<NameAndDescription> getNameAndDescriptions(QueryWrapper<DelegationCategories> wrapper) throws MyException {
        List<DelegationCategories> list = list(wrapper);

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

    @Override
    public List<NameAndDescription> getTaskCategoryUser() throws MyException {
        QueryWrapper<DelegationCategories> wrapper = new QueryWrapper<>();
        wrapper.eq("isEnable", 1)
                .orderBy(true, false, "category_id");
        return getNameAndDescriptions(wrapper);
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

    @Override
    public PageResult list(Page<DelegationCategories> page, String categoryName,
                           Boolean isEnable, String description) {
        QueryWrapper<DelegationCategories> wrapper = new QueryWrapper<>();
        if (categoryName != null && !categoryName.equals("")) {
            wrapper.like("category_name", categoryName);
        }
        if (isEnable != null) {
            wrapper.eq("isEnable", isEnable);
        }
        if (description != null && !description.equals("")) {
            wrapper.like("category_description", description);
        }
        if (page != null) {
            page = page(page, wrapper);
            return new PageResult(page.getTotal(), page.getRecords());
        }
        page = page(new Page<>(1, 5), wrapper);
        return new PageResult(page.getTotal(), page.getRecords());
    }

    @Override
    public void enable(Long id) throws MyException {
        DelegationCategories categories = getById(id);
        if (categories == null) {
            throw new MyException(MessageConstants.TASK_CATEGORY_NOT_EXIST_ERROR);
        }
        if (categories.getIsEnabled()) {
            categories.setIsEnabled(false);
        } else {
            categories.setIsEnabled(true);
        }
        updateById(categories);
    }
}