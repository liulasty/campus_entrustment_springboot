package com.lz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.Exception.MyException;
import com.lz.pojo.dto.NoticeDTO;
import com.lz.pojo.dto.NotificationDTO;
import com.lz.pojo.dto.SendDataDTO;
import com.lz.pojo.entity.Notifications;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lz.pojo.vo.NoticeItemVO;
import com.lz.pojo.vo.NoticeVO;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 存储系统通知信息 服务类
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
public interface INotificationsService extends IService<Notifications> {

    /**
     * 按 ID 和时间获取通知
     */
    void getNotificationsByIdANDDate();

    /**
     * 获取最新通知
     */
    void getNewestNotifications();


    /**
     * 分页查询列表
     *
     * @param page        页
     * @param createAt    创建位置
     * @param messageType 消息类型
     * @param description 描述
     *
     * @return 页面<通知>
     */
    Page<Notifications> selectList(Page<Notifications> page, Date createAt, String messageType, String description);

    void add(NotificationDTO notificationDTO) throws MyException;

    void send(SendDataDTO sendData) throws MyException;

    Long addTaskDeleteNotification(Long userId, String msg);

    void delNotification(Long id);

    void getNoticeByIdANDType(NoticeDTO noticeDTO);

    List<NoticeItemVO> getNoticeType(String str) throws MyException;

    NoticeVO   getInfoById(Long id);
}