package com.lz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.mapper.UsersInfoMapper;
import com.lz.mapper.UsersMapper;
import com.lz.pojo.entity.NotificationReadStatus;
import com.lz.mapper.NotificationReadStatusMapper;

import com.lz.pojo.entity.Users;
import com.lz.pojo.entity.UsersInfo;
import com.lz.pojo.vo.NotificationReadStatusVO;
import com.lz.service.INotificationReadStatusService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 通知表记录用户是否已读通知 服务实现类
 * </p>
 *
 * @author lz
 * @since 2024-04-10
 */
@Service
public class NotificationReadStatusServiceImpl extends ServiceImpl<NotificationReadStatusMapper, NotificationReadStatus> implements INotificationReadStatusService {

    @Autowired
    private NotificationReadStatusMapper notificationReadStatusMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersInfoMapper usersInfoMapper;

    /**
     * 发送所有通知
     *
     * @param notificationId 通知 ID
     */
    @Override
    public void sendAllNotification(Long notificationId) {
        NotificationReadStatus notificationReadStatus = NotificationReadStatus.builder()
                .notificationId(notificationId)
                .sendTime(new Date(System.currentTimeMillis()))
                .isRead(false)
                .build();

        usersMapper.selectList(null).forEach(users -> {
            notificationReadStatus.setUserId(users.getUserId());
            notificationReadStatusMapper.insert(notificationReadStatus);
        });

    }

    @Override
    public void sendAuthenticatedNotification(Long sendId) {
        NotificationReadStatus notificationReadStatus = NotificationReadStatus.builder()
                .notificationId(sendId)
                .sendTime(new Date(System.currentTimeMillis()))
                .isRead(false)
                .build();
        QueryWrapper<UsersInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("auth_status", 3);
        usersInfoMapper.selectList(wrapper).forEach(usersInfo -> {
            notificationReadStatus.setUserId(usersInfo.getUserId());
            notificationReadStatusMapper.insert(notificationReadStatus);
        });

    }

    @Override
    public void sendStudentNotification(Long sendId) {
        NotificationReadStatus notificationReadStatus = NotificationReadStatus.builder()
                .notificationId(sendId)
                .sendTime(new Date(System.currentTimeMillis()))
                .isRead(false)
                .build();
        QueryWrapper<UsersInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("auth_status", 3)
                .eq("userRole", "student");
        usersInfoMapper.selectList(wrapper).forEach(users -> {
            notificationReadStatus.setUserId(users.getUserId());
            notificationReadStatusMapper.insert(notificationReadStatus);
        });
    }

    @Override
    public void sendOtherNotification(Long sendId) {
        NotificationReadStatus notificationReadStatus = NotificationReadStatus.builder()
                .notificationId(sendId)
                .sendTime(new Date(System.currentTimeMillis()))
                .isRead(false)
                .build();
        QueryWrapper<UsersInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("auth_status", 3)
                .eq("userRole", "other");
        usersInfoMapper.selectList(wrapper).forEach(users -> {
            notificationReadStatus.setUserId(users.getUserId());
            notificationReadStatusMapper.insert(notificationReadStatus);
        });
    }

    @Override
    public void sendTeacherNotification(Long sendId) {
        NotificationReadStatus notificationReadStatus = NotificationReadStatus.builder()
                .notificationId(sendId)
                .sendTime(new Date(System.currentTimeMillis()))
                .isRead(false)
                .build();
        QueryWrapper<UsersInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("auth_status", 3)
                .eq("userRole", "teacher");
        usersInfoMapper.selectList(wrapper).forEach(users -> {
            notificationReadStatus.setUserId(users.getUserId());
            notificationReadStatusMapper.insert(notificationReadStatus);
        });
    }

    @Override
    public void sendAdminNotification(Long sendId) {

    }

    @Override
    public Page<NotificationReadStatusVO> selectList(Page<NotificationReadStatusVO> page,
                                                     Date createAt, String messageType,
                                                     String description) {
        if (getCurrentAdmin().getRole().equals("ADMIN")) {
            return notificationReadStatusMapper.selectPageAdmin(page, createAt, messageType, description);
        }
        throw new RuntimeException("权限不足");
        
    }

    @Override
    public void addTaskNotification(Long id, Long ownerId, Long userId) {
        NotificationReadStatus notificationReadStatus = NotificationReadStatus.builder()
                .notificationId(id)
                .userId(ownerId)
                .sendTime(new Date(System.currentTimeMillis()))
                .isRead(false)
                .build();
        
        save(notificationReadStatus);
    }

    @Override
    public void delNotification(Long id) {
        remove(new QueryWrapper<NotificationReadStatus>().eq("NotificationID", id));
    }

    public Users getCurrentAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminName = authentication.getName();
        // log.info("管理员: {}", adminName);

        return usersMapper.getByUsername(adminName);
    }

}