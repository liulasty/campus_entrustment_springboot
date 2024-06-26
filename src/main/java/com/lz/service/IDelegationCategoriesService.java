package com.lz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.Exception.MyException;
import com.lz.pojo.entity.DelegationCategories;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lz.pojo.result.NameAndDescription;
import com.lz.pojo.result.PageResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用于存储委托类别常量的表 服务类
 * </p>
 *
 * @author lz
 * @since 2024-04-10
 */
public interface IDelegationCategoriesService extends IService<DelegationCategories> {

    /**
     * 获取委托类别
     *
     * @return {@code Map<Integer, NameAndDescription>}
     *
     * @throws MyException 我的异常
     */
    List<NameAndDescription> getTaskCategory() throws MyException;

    List<NameAndDescription> getTaskCategoryUser() throws MyException;
    

    /**
     * @return {@code DelegationCategories}
     */
    DelegationCategories getTaskCategoryByCategoryName(String name);

    PageResult list(Page<DelegationCategories> page, String categoryName,
                    Boolean isEnable, String description);

    void enable(Long id) throws MyException;
}