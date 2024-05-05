package com.lz.controller;


import com.lz.Exception.MyException;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.dto.UserInfoDTO;
import com.lz.pojo.entity.UsersInfo;
import com.lz.pojo.result.Result;
import com.lz.service.ITaskService;
import com.lz.service.IUsersInfoService;
import com.lz.service.IUsersService;
import com.lz.pojo.Enum.AuthenticationStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 存储系统用户详细信息 前端控制器
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@RestController
@RequestMapping("/userInfo")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UsersinfoController {
    @Autowired
    private IUsersInfoService usersInfoService;

    @Autowired
    private IUsersService usersService;
    
    @Autowired
    private ITaskService taskService;

    /**
     * 查询用户信息
     *
     * @param id 同上
     *
     * @return {@code Result<UsersInfo>}
     */
    @GetMapping("/{id}")
    public Result<UsersInfo> userInfo(@PathVariable Long id) {
        UsersInfo usersInfo = usersInfoService.getById(id);
        if (usersInfo == null) {
            log.error("用户信息不存在");
            return Result.error("用户信息不存在");
        }

        return Result.success(usersInfo);
    }

    /**
     * 提交认证申请
     *
     * @param dto DTO
     *
     * @return {@code Result<String>}
     *
     * @throws MyException 我的异常
     */
    @PostMapping
    public Result<String> save(@RequestBody UserInfoDTO dto) throws MyException {
        log.info("用户认证信息提交");
        UsersInfo usersInfo = usersInfoService.getById(dto.getId());
        if (usersInfo != null) {
            if (usersInfo.getAuthStatus() == AuthenticationStatus.AUTHENTICATION_FAILED) {
                log.info("重新申请认证");
                UsersInfo info = UsersInfo.builder()
                        .userId(dto.getId())
                        .name(dto.getName())
                        .authStatus(AuthenticationStatus.AUTHENTICATING)
                        .userRole(dto.getRole())
                        .qqNumber(dto.getQq())
                        .certifieTime(new Date(System.currentTimeMillis()))
                        .phoneNumber(dto.getPhone()).build();
                usersInfoService.updateById(info);
                return Result.success("重新认证信息已提交审核");
            }else {
                log.error("用户认证信息已存在");
                return Result.error("用户认证信息已存在");
            }
            
        }else {
            usersInfoService.submitCertificationInformation(dto);
            log.info("用户认证信息提交成功");
            return Result.success(MessageConstants.USER_UPDATE_SUCCESS);
        }
        
    }


    /**
     * 确认通过审核
     *
     * @param id 同上
     *
     * @return {@code Result<String>}
     *
     * @throws MyException 我的异常
     */
    @PutMapping("/confirmToPassTheReview/{id}")
    public Result<String> confirmToPassTheReview(@PathVariable Long id) throws MyException {
        log.info("用户认证信息审核通过");
        usersInfoService.confirmToPassTheReview(id);
        log.info("用户认证信息审核通过成功");
        //todo 通知用户,用户认证信息审核通过
        return Result.success(MessageConstants.USER_UPDATE_SUCCESS);
    }


    /**
     * 拒绝通过审核
     *
     * @param id 同上
     *
     * @return {@code Result<String>}
     *
     * @throws MyException 我的异常
     */
    @PutMapping("/refuseToPassReview/{id}")
    public Result<String> refuseToPassReview(@PathVariable Long id) throws MyException {
        log.info("用户认证信息审核不通过");
        usersInfoService.refuseToPassReview(id);
        log.info("用户认证信息审核不通过成功");
        //todo 通知用户,用户认证信息审核不通过
        return Result.success(MessageConstants.USER_UPDATE_SUCCESS);
    }


    /**
     * 删除
     *
     * @param id 同上
     *
     * @return {@code Result<String>}
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        UsersInfo usersInfo = usersInfoService.getById(id);
        if (usersInfo == null) {
            log.error("用户认证信息不存在");
            return Result.error(MessageConstants.USER_AUTHENTICATION_INFO_NOT_EXIST);
        }
        if(taskService.getTasksWithUser(id).size() > 0){
            log.error("用户存在委托任务，无法删除认证信息");
            return Result.error(MessageConstants.USER_EXIST_TASK);
        }
        log.info("删除用户认证信息");
        usersInfoService.removeById(id);
        log.info("删除用户认证信息成功");
        //todo 删除用户认证信息，通知用户
        return Result.success(MessageConstants.USER_INFO_DELETE_SUCCESS);
    }


    /**
     * 取消用户信息身份验证
     *
     * @param id 同上
     *
     * @return {@code Result<String>}
     *
     * @throws MyException 我的异常
     */
    @PutMapping("/cancelUserInfoAuthentication/{id}")
    public Result<String> cancelUserInfoAuthentication(@PathVariable Long id) throws MyException {
        UsersInfo usersInfo = usersInfoService.getById(id);
        if (usersInfo == null) {
            log.error("用户认证信息不存在");
            return Result.error(MessageConstants.USER_AUTHENTICATION_INFO_NOT_EXIST);
        }
        log.info("取消用户认证");
        if (!usersInfoService.cancelUserInfoAuthentication(id)) {
            throw new MyException(MessageConstants.USER_CANCEL_FAIL);
        }

        log.info("取消用户认证成功");

        //todo 通知用户,已取消用户认证
        return Result.success(MessageConstants.USER_CANCEL_SUCCESS);
    }


}