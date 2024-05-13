package com.lz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.pojo.entity.SystemAnnouncements;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 存储系统公告的信息 服务类
 * </p>
 *
 * @author lz
 * @since 2024-04-10
 */
public interface ISystemAnnouncementsService extends IService<SystemAnnouncements> {


    /**
     * 获取最新系统公告
     *
     * @return {@code SystemAnnouncements}
     */
    List<SystemAnnouncements> getNewestAnnouncement();

    IPage<SystemAnnouncements> page(Page<SystemAnnouncements> page,
                                    String status, String description,
                                    Integer queryRules);
}