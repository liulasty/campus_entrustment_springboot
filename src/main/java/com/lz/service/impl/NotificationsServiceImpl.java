package com.lz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.Exception.MyException;
import com.lz.mapper.SystemAnnouncementsMapper;
import com.lz.mapper.UsersMapper;
import com.lz.pojo.Enum.NotificationsType;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.dto.NoticeDTO;
import com.lz.pojo.dto.NotificationDTO;
import com.lz.pojo.dto.SendDataDTO;
import com.lz.pojo.entity.NotificationReadStatus;
import com.lz.pojo.entity.Notifications;
import com.lz.mapper.NotificationsMapper;
import com.lz.pojo.entity.SystemAnnouncements;
import com.lz.pojo.entity.Users;
import com.lz.pojo.vo.NoticeItemVO;
import com.lz.pojo.vo.NoticeVO;
import com.lz.service.INotificationReadStatusService;
import com.lz.service.INotificationsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 存储系统通知信息 服务实现类
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@Service
@Slf4j
public class NotificationsServiceImpl extends ServiceImpl<NotificationsMapper, Notifications> implements INotificationsService {

    @Autowired
    private SystemAnnouncementsMapper systemAnnouncementsMapper;

    @Autowired
    private NotificationsMapper notificationsMapper;

    @Autowired
    private INotificationReadStatusService notificationReadStatusService;

    @Autowired
    private UsersMapper usersMapper;

    /**
     * 按 ID 和时间获取通知
     */
    @Override
    public void getNotificationsByIdANDDate() {

    }


    @Override
    public void getNewestNotifications() {

    }

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
    @Override
    public Page<Notifications> selectList(Page<Notifications> page, Date createAt, String messageType, String description) {
        Page<Notifications> notificationsPage = notificationsMapper.selectPageAdmin(page,
                                                                                    createAt, messageType,
                                                                                    description);

        return notificationsPage;
    }

    @Override
    @Transactional()
    public void add(NotificationDTO notificationDTO) throws MyException {
        Notifications notifications = Notifications.builder()
                .notificationTime(new Date(System.currentTimeMillis()))
                .notificationType(NotificationsType.fromDbValue(notificationDTO.getType()))
                .title(notificationDTO.getTitle())
                .message(notificationDTO.getDescription())
                .userId(getCurrentAdmin().getUserId())
                .build();
        boolean save = save(notifications);
        if (!save) {
            log.error(MessageConstants.ADD_MESSAGE_FAILURE);
            throw new MyException(MessageConstants.ADD_MESSAGE_FAILURE);
        }
    }

    @Override
    public void send(SendDataDTO sendData) throws MyException {
        if (sendData.getSendObject().equals("all")) {
            notificationReadStatusService.sendAllNotification(sendData.getSendId());
            return;
        }

        if (sendData.getSendObject().equals("authenticated")) {
            notificationReadStatusService.sendAuthenticatedNotification(sendData.getSendId());
            return;
        }

        if (sendData.getSendObject().equals("student")) {
            notificationReadStatusService.sendStudentNotification(sendData.getSendId());
            return;
        }

        if (sendData.getSendObject().equals("teacher")) {
            notificationReadStatusService.sendTeacherNotification(sendData.getSendId());
            return;
        }

        if (sendData.getSendObject().equals("admin")) {
            notificationReadStatusService.sendAdminNotification(sendData.getSendId());
            return;
        }

        if (sendData.getSendObject().equals("other")) {
            notificationReadStatusService.sendOtherNotification(sendData.getSendId());
            return;
        }

        throw new MyException(MessageConstants.DATA_VALIDATION_ERROR);

    }

    @Override
    public Long addTaskDeleteNotification(Long userId, String msg) {
        Notifications notifications = Notifications.builder()
                .notificationTime(new Date(System.currentTimeMillis()))
                .notificationType(NotificationsType.TASK)
                .title("任务删除通知")
                .message(msg)
                .userId(userId)
                .build();
        notificationsMapper.insert(notifications);
        return notifications.getNotificationId();
    }

    @Override
    @Transactional
    public void delNotification(Long id) {
        removeById(id);
        notificationReadStatusService.delNotification(id);
    }

    @Override
    public void getNoticeByIdANDType(NoticeDTO noticeDTO) {

    }

    @Override
    public List<NoticeItemVO> getNoticeType(String str) throws MyException {
        List<NoticeItemVO> list = new ArrayList<>();
        log.info("通知类型: {}", NotificationsType.fromDbValue(str).getWebValue());

        Users users = getCurrentAdmin();
        list =
                notificationsMapper.selectListByType(users.getUserId(),
                                                     NotificationsType.fromDbValue(str).getDbValue());
        return list;
    }

    @Override
    public NoticeVO getInfoById(Long id) {
        if (id != null) {
            NoticeVO noticeVO = notificationsMapper.getInfoById(id);
            if (!noticeVO.getIsRead()){
                NotificationReadStatus notificationReadStatus = new NotificationReadStatus();
                notificationReadStatus.setId(id);
                notificationReadStatus.setIsRead(true);
                notificationReadStatus.setReadTime(new Date(System.currentTimeMillis()));
                notificationReadStatusService.updateById(notificationReadStatus);
            }
            
            return noticeVO;
        }
        return null;
    }

    public Users getCurrentAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminName = authentication.getName();
        // log.info("管理员: {}", adminName);

        return usersMapper.getByUsername(adminName);
    }


}