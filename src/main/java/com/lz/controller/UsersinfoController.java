package com.lz.controller;


import com.lz.Exception.MyException;
import com.lz.pojo.dto.UserInfoDTO;
import com.lz.pojo.entity.UsersInfo;
import com.lz.pojo.result.Result;
import com.lz.service.IUsersInfoService;
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
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UsersinfoController {
    @Autowired
    private IUsersInfoService usersInfoService;
    
    @GetMapping("/{id}")
    public Result<UsersInfo> userInfo(@PathVariable Long id) {
        UsersInfo usersInfo = usersInfoService.getById(id);

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
        usersInfoService.submitCertificationInformation(dto);
        return Result.success();
    }
    

}