package com.lz.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.entity.NotificationReadStatus;
import com.lz.pojo.entity.Notifications;
import com.lz.pojo.result.PageResult;
import com.lz.pojo.result.Result;
import com.lz.pojo.vo.NotificationReadStatusVO;
import com.lz.service.INotificationReadStatusService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 通知表记录用户是否已读通知 前端控制器
 * </p>
 *
 * @author lz
 * @since 2024-04-10
 */
@RestController
@RequestMapping("/notificationReadStatus")
@Slf4j
@Api(tags = "通知记录控制器")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class NotificationReadStatusController {
    
    @Autowired
    private INotificationReadStatusService notificationReadStatusService;
    @GetMapping("/list")
    public Result list(@RequestParam(value = "pageNum",
            defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "createdAt", required = false)
                       @DateTimeFormat(fallbackPatterns = "yyyy-MM-dd") Date createAt,
                       @RequestParam(value = "messageType",
                               required = false) String messageType,
                       @RequestParam(value = "description", required = false) String description) {
        Page<NotificationReadStatusVO> page = new Page<>(pageNum, pageSize);

        Page<NotificationReadStatusVO> notificationsPage =
                notificationReadStatusService.selectList(page, createAt, messageType,
                                                description);
        log.info("分页查询结果：{}", notificationsPage);
        return Result.success(new PageResult<NotificationReadStatusVO>(notificationsPage.getTotal(), notificationsPage.getRecords()));
    
    }
    
    @DeleteMapping("/{id}")
    public Result delNotification(@PathVariable("id") Long id) {
        notificationReadStatusService.delNotification(id);
        return Result.success(MessageConstants.DATA_DELETE_SUCCESS);
    }

}