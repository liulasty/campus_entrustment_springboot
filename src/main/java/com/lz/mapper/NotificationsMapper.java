package com.lz.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.pojo.entity.Notifications;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lz.pojo.vo.NoticeItemVO;
import com.lz.pojo.vo.NoticeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 存储系统通知信息 Mapper 接口
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@Mapper
public interface NotificationsMapper extends BaseMapper<Notifications> {

    Page<Notifications> selectPageAdmin(Page<Notifications> page, Date createAt, String messageType, String description);

    List<NoticeItemVO> selectListByType(Long userId, String type);

    NoticeVO getInfoById(Long id);
}