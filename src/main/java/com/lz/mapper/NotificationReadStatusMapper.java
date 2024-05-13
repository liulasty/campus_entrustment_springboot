package com.lz.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.pojo.entity.NotificationReadStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lz.pojo.vo.NotificationReadStatusVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

/**
 * <p>
 * 通知表记录用户是否已读通知 Mapper 接口
 * </p>
 *
 * @author lz
 * @since 2024-04-10
 */
@Mapper
public interface NotificationReadStatusMapper extends BaseMapper<NotificationReadStatus> {

    Page<NotificationReadStatusVO> selectPageAdmin(Page<NotificationReadStatusVO> page, Date createAt, String messageType, String description);
}