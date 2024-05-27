package com.lz.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.pojo.entity.SystemAnnouncements;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 存储系统公告的信息 Mapper 接口
 * </p>
 *
 * @author lz
 * @since 2024-04-10
 */
@Mapper
public interface SystemAnnouncementsMapper extends BaseMapper<SystemAnnouncements> {
    
    IPage<SystemAnnouncements> selectPageAdmin(Page<SystemAnnouncements> page,
                                    String status, String description,
                                    Integer queryRules);
    
    List<SystemAnnouncements> getNewestAnnouncement();

}