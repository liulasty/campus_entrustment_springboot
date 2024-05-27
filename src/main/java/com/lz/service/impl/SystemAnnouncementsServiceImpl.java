package com.lz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.pojo.Enum.AnnouncementStatus;
import com.lz.pojo.entity.SystemAnnouncements;
import com.lz.mapper.SystemAnnouncementsMapper;
import com.lz.service.ISystemAnnouncementsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 存储系统公告的信息 服务实现类
 * </p>
 *
 * @author lz
 * @since 2024-04-10
 */
@Service
@Slf4j
public class SystemAnnouncementsServiceImpl extends ServiceImpl<SystemAnnouncementsMapper, SystemAnnouncements> implements ISystemAnnouncementsService {
    @Autowired
    private SystemAnnouncementsMapper systemAnnouncementsMapper;
    /**
     * 获取最新公告
     *
     * @return {@code List<SystemAnnouncements>}
     */
    @Override
    public List<SystemAnnouncements> getNewestAnnouncement() {
        

        List<SystemAnnouncements> announcements = systemAnnouncementsMapper.getNewestAnnouncement();
        
        log.info("getNewestAnnouncement: {}", announcements);
        return announcements;
    }

    @Override
    public IPage<SystemAnnouncements> page(Page<SystemAnnouncements> page,
                                           String status, String description,
                                           Integer queryRules) {
        log.info("page: {}", page);
        
        IPage<SystemAnnouncements> iPage =
                systemAnnouncementsMapper.selectPageAdmin(page, status,
                                                       description, queryRules);
        return iPage;
    }
}