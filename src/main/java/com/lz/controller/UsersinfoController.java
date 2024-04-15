package com.lz.controller;


import com.lz.Exception.MyException;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.dto.UserInfoDTO;
import com.lz.pojo.entity.UsersInfo;
import com.lz.pojo.result.Result;
import com.lz.service.IUsersInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    
    @GetMapping("/{id}")
    public Result<UsersInfo> userInfo(@PathVariable Long id) {
        UsersInfo usersInfo = usersInfoService.getById(id);
        if (usersInfo == null){
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
        if (usersInfo != null){
            log.error("用户认证信息已存在");
            return Result.error("用户认证信息已存在");
        }
        usersInfoService.submitCertificationInformation(dto);
        log.info("用户认证信息提交成功");
        return Result.success(MessageConstants.USER_UPDATE_SUCCESS);
    }
    

}