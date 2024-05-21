package com.lz.service;

import com.lz.pojo.dto.ReviewsDTO;
import com.lz.pojo.entity.Reviews;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 存储用户对任务的评价信息 服务类
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
public interface IReviewsService extends IService<Reviews> {

    void save(ReviewsDTO reviewsDTO);
}