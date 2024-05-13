package com.lz.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.Exception.MyException;
import com.lz.pojo.Enum.NotificationsType;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.dto.NoticeDTO;
import com.lz.pojo.dto.NotificationDTO;
import com.lz.pojo.dto.SendDataDTO;
import com.lz.pojo.entity.Notifications;
import com.lz.pojo.result.PageResult;
import com.lz.pojo.result.Result;
import com.lz.pojo.vo.NoticeItemVO;
import com.lz.pojo.vo.NoticeVO;
import com.lz.service.INotificationsService;
import com.lz.utils.EnumUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 存储系统通知信息 前端控制器
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@RestController
@RequestMapping("/notifications")
@Slf4j
@Api(tags = "通知控制器")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class NotificationsController {

    @Autowired
    private INotificationsService notificationsService;

    @GetMapping("/list")
    public Result list(@RequestParam(value = "pageNum",
            defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "createdAt", required = false)
                       @DateTimeFormat(fallbackPatterns = "yyyy-MM-dd") Date createAt,
                       @RequestParam(value = "messageType",
                               required = false) String messageType,
                       @RequestParam(value = "description", required = false) String description) {
        Page<Notifications> page = new Page<>(pageNum, pageSize);

        Page<Notifications> notificationsPage =
                notificationsService.selectList(page, createAt, messageType,
                                                description);
        log.info("分页查询结果：{}", notificationsPage);
        return Result.success(new PageResult<Notifications>(notificationsPage.getTotal(), notificationsPage.getRecords()));
    }

    /**
     * 获取类型
     *
     * @return 后端统一返回结果
     */
    @GetMapping("/type")
    public Result getType() {
        Map<String, String> map = EnumUtils.generateKeyValues(NotificationsType.values());
        return Result.success(map);
    }

    /**
     * 添加通知消息
     *
     * @param notificationDTO 通知 DTO
     *
     * @return 后端统一返回结果
     */
    @PostMapping()
    public Result add(@RequestBody NotificationDTO notificationDTO) throws MyException {
        log.info("添加通知信息：{}", notificationDTO);
        notificationsService.add(notificationDTO);

        return Result.success(MessageConstants.ADD_MESSAGE_SUCCESS);
    }

    @PutMapping()
    public Result update(@RequestBody Notifications notifications) throws MyException {
        log.info("更新通知信息：{}", notifications);
        notificationsService.updateById(notifications);

        return Result.success(MessageConstants.ADD_MESSAGE_SUCCESS);
    }


    /**
     * 发送通知
     *
     * @param sendData 发送数据
     *
     * @return 后端统一返回结果
     *
     * @throws MyException 我的异常
     */
    @PostMapping("/send")
    public Result send(
            @RequestBody SendDataDTO sendData) throws MyException {
        log.info("发送通知信息：{}", sendData);
        notificationsService.send(sendData);
        return Result.success(MessageConstants.ADD_MESSAGE_SUCCESS);
    }

    /**
     * 按 ID 获取
     *
     * @param id 同上
     *
     * @return 后端统一返回结果
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable("id") Long id) {
        log.info("根据id查询通知信息：{}", id);
        Notifications notifications = notificationsService.getById(id);
        return Result.success(notifications);
    }


    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
        log.info("根据id删除通知信息：{}", id);
        notificationsService.delNotification(id);
        return Result.success(MessageConstants.DELETE_MESSAGE_SUCCESS);
    }


    @GetMapping("/getList/{str}")
    public Result getNotificationsByIdANDType(@PathVariable("str") String str) throws MyException {
        log.info("获取通知信息{}", str);

        List<NoticeItemVO> list = notificationsService.getNoticeType(str);
        return Result.success(list);
    }


    /**
     * 按 ID 获取通知详情
     *
     * @param id 同上
     *
     * @return 后端统一返回结果
     */
    @GetMapping("/info/{id}")
    public Result getNotificationsById(@PathVariable("id") Long id) {
        log.info("根据id查询通知信息：{}", id);
        NoticeVO noticeVO = notificationsService.getInfoById(id);
        return Result.success(noticeVO);
    }

}