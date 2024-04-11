package com.lz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        Page<SystemAnnouncements> page = new Page<>(1, 5); // 分页查询，第1页，每页5条记录

        QueryWrapper<SystemAnnouncements> wrapper = new QueryWrapper<>();
        // 设置查询条件
        wrapper
                // 设置排序规则
                .orderByDesc("is_pinned", "publish_time")
                // 设置查询条件
                .lt("status", AnnouncementStatus.PUBLISHED); 

        List<SystemAnnouncements> announcements = systemAnnouncementsMapper.selectPage(page, wrapper).getRecords();
        
        log.info("getNewestAnnouncement: {}", announcements);
        return announcements;
    }
}