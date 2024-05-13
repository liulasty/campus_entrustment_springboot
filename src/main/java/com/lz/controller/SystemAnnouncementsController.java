package com.lz.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.entity.SystemAnnouncements;
import com.lz.pojo.entity.Users;
import com.lz.pojo.result.PageResult;
import com.lz.pojo.result.Result;
import com.lz.service.ISystemAnnouncementsService;
import com.lz.service.IUsersService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 存储系统公告的信息 前端控制器
 * </p>
 *
 * @author lz
 * @since 2024-04-10
 */
@RestController
@Slf4j
@Api(tags = "系统公告控制器")
@RequestMapping("/system-announcements")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class SystemAnnouncementsController {
    
    @Autowired
    private IUsersService usersService;

    @Autowired
    private ISystemAnnouncementsService systemAnnouncementsService;

    @GetMapping("/list")
    public Result list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "status", required = false) String status,
                       @RequestParam(value = "description", required = false) String description,
                       @RequestParam(value = "queryRules", required = false) Integer queryRules) {
        Page<SystemAnnouncements> page = new Page<SystemAnnouncements>(pageNum, pageSize);
        IPage<SystemAnnouncements> pageResult = systemAnnouncementsService.page(page, status, description,
                                                                                queryRules);
        PageResult<SystemAnnouncements> systemAnnouncementsPageResult = new PageResult<>();
        systemAnnouncementsPageResult.setTotal(pageResult.getTotal());
        systemAnnouncementsPageResult.setRecords(pageResult.getRecords());
        return Result.success(systemAnnouncementsPageResult);
    }

    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        systemAnnouncementsService.removeById(id);
        return Result.success(MessageConstants.SYSTEM_ANNOUNCEMENTS_DELETE_SUCCESS);
    }

    @GetMapping(value = "/{id}")
    public Result getById(@PathVariable("id") Integer id) {
        SystemAnnouncements systemAnnouncements = systemAnnouncementsService.getById(id);
        return Result.success(systemAnnouncements);
    }

    
    @PutMapping()
    public Result update(@RequestBody SystemAnnouncements systemAnnouncements) {
        systemAnnouncements.setUpdatedAt(new Date(System.currentTimeMillis()))
                        .setUpdatedBy(getCurrentAdmin().getUserId());
        log.info("更新系统公告 {}", systemAnnouncements);
        systemAnnouncementsService.updateById(systemAnnouncements);
        return Result.success(MessageConstants.TASK_UPDATE_SUCCESS);
    }
    public Users getCurrentAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminName = authentication.getName();
        // log.info("管理员: {}", adminName);

        return usersService.getByUsername(adminName);
    }
    


}