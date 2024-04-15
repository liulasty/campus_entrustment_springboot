package com.lz.controller;


import com.lz.Exception.MyException;
import com.lz.config.AppConfig;
import com.lz.pojo.Page.UsersConfig;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.dto.UserDTO;
import com.lz.pojo.dto.UserLoginDTO;
import com.lz.pojo.dto.UsersPageDTO;
import com.lz.pojo.entity.Users;
import com.lz.pojo.result.PageResult;
import com.lz.pojo.result.Result;
import com.lz.pojo.vo.UserLoginVO;
import com.lz.service.IUsersService;
import com.lz.utils.JwtUtil;
import com.lz.utils.ValidateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 存储系统用户信息 前端控制器
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/user")
@Api(tags = "用户相关接口")
@Slf4j
public class UsersController {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_USER = "ROLE_USER";

    @Autowired
    private IUsersService usersService;

    @Autowired
    private AppConfig appConfig;

    private final AuthenticationManager authenticationManager;


    @Autowired
    public UsersController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    /**
     * 登录
     */
    @PostMapping(value = "/login")
    @ApiOperation("登录")
    public Result<UserLoginVO> login(@Validated @RequestBody UserLoginDTO userLoginDTO, BindingResult result) throws MyException {
        log.info("用户登录:{}", userLoginDTO.getUsername());
        //校验结果
        if (ValidateUtil.validate(result) != null) {
            log.info("用户登录校验失败:{}", ValidateUtil.validate(result));
            return Result.error(ValidateUtil.validate(result));
        }
        {
            // 使用Spring Security进行身份验证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDTO.getUsername(),
                            userLoginDTO.getPassword()
                    )
            );
            log.info("用户登录成功:{}", userLoginDTO.getUsername());


            // 设置认证上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 获取当前用户的角色信息
            
            Users user = usersService.getByUsername(userLoginDTO.getUsername());

            HashMap<String, Object> claims = new HashMap<>();
            claims.put("username", user.getUsername());
            claims.put("role",user.getRole());
            String token = JwtUtil.genToken(claims, appConfig.getJwtKey());
            UserLoginVO loginVO = UserLoginVO.builder()
                    .userId(user.getUserId())
                    .userType(user.getRole())
                    .token(token)
                    .build();
            // boolean login = usersService.login(userLoginDTO);
            // if (!login) {
            //     return Result.error(MessageConstants.USER_LOGIN_FAIL);
            // }
            // 登录成功，生成JWT并添加到响应中
            // Spring Security验证成功，无需再执行usersService.login()
            return Result.success(loginVO, MessageConstants.USER_LOGIN_SUCCESS);
        }
    }

    /**
     * 注册
     *
     * @param userDTO 用户 DTO
     *
     * @return {@code Result<String>}
     */
    @PostMapping(value = "/register")
    @ApiOperation("注册")
    public Result<String> register(@Validated @RequestBody UserDTO userDTO, BindingResult result) {
        log.info("用户注册:{}", userDTO);
        //校验结果
        if (ValidateUtil.validate(result) != null) {
            return Result.error(ValidateUtil.validate(result));
        }


        boolean register = usersService.register(userDTO);

        if (!register) {
            return Result.error(MessageConstants.USER_REGISTER_FAIL);
        } else {
            return Result.success(MessageConstants.USER_REGISTER_SUCCESS);
        }


    }

    @GetMapping(value = "/active/{id}")
    @ApiOperation("激活")
    public Result<String> active(@PathVariable Long id) throws MyException {
        log.info("激活用户id:{}", id);
        boolean active = usersService.active(id);
        if (!active) {
            return Result.error(MessageConstants.DATABASE_ERROR);
        }

        return Result.success(MessageConstants.USER_ACTIVE_SUCCESS);
    }

    @PostMapping(value = "/logout")
    @ApiOperation("登出")
    public Result<String> logout(HttpServletRequest request) {
        log.info("用户登出请求");

        // 封装登出逻辑到独立的方法
        boolean logoutSuccess = tryLogout(request);

        // 根据登出结果返回相应的消息
        return logoutSuccess ? Result.success(MessageConstants.USER_LOGOUT_SUCCESS)
                : Result.error(MessageConstants.USER_LOGOUT_FAILURE);
    }

    private boolean tryLogout(HttpServletRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null) {
                new SecurityContextLogoutHandler().logout(request, null, authentication);
                SecurityContextHolder.clearContext();
                log.info("用户登出成功");
                return true;
            } else {
                log.warn("尝试登出时，Authentication为null");
                return false;
            }
        } catch (Exception e) {
            log.error("登出过程中发生异常", e);
            return false;
        }
    }

    /**
     * 获取用户信息
     *
     * @param id 同上
     *
     * @return {@code Result<Users>}
     */
    @GetMapping(value = "/getUserInfo/{id}")
    @ApiOperation("获取用户信息")
    public Result<Users> getUserInfo(@PathVariable Long id) {
        log.info("获取用户信息:{}", id);
        Users users = usersService.getById(id);

        return Result.success(users);
    }

    /**
     * 按页面获取用户信息
     *
     * @param username 用户名
     * @param email    电子邮件
     * @param isActive 处于活动状态
     * @param page     页
     * @param size     大小
     *
     * @return {@code Result<PageResult>}
     */
    @GetMapping(value = "/page")
    @ApiOperation("分页查询用户信息")
    public Result<PageResult> getUserInfoByPage(
            @RequestParam(required = false) String username,                 
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue ="1") long page,
            @RequestParam(defaultValue ="5") long size) {
        log.info("分页查询用户信息:{}", username);
        UsersConfig config = UsersConfig.builder()
                .username(username)
                .email(email)
                .isActive(isActive)
                .page(page)
                .size(size)
                .build();
        return Result.success(usersService.getUserByPage(config));
    }

    /**
     * 更新用户信息
     *
     * @param users 用户
     *
     * @return {@code Result<String>}
     */
    @PutMapping(value = "/updateUserInfo")
    @ApiOperation("更新用户信息")
    public Result<String> updateUserInfo(@RequestBody Users users) {
        log.info("更新用户信息:{}", users);
        usersService.updateById(users);
        return Result.success(MessageConstants.USER_UPDATE_SUCCESS);
    }


    @PostMapping(value = "/updateUserInfoByAdmin")
    @ApiOperation("管理员更新用户信息")
    public Result<String> updateUserInfoByAdmin(@RequestBody Users users) {
        log.info("管理员更新用户信息:{}", users);
        usersService.updateById(users);
        return Result.success(MessageConstants.USER_UPDATE_SUCCESS);
    }

    /**
     * 按 ID 删除用户信息
     *
     * @param id 同上
     *
     * @return {@code Result<String>}
     */
    @DeleteMapping(value = "/deleteUserInfoById/{id}")
    @ApiOperation("根据用户id删除用户信息")
    public Result<String> deleteUserInfoById(@PathVariable Long id) {
        log.info("根据用户id删除用户信息:{}", id);
        usersService.removeById(id);
        return Result.success(MessageConstants.USER_DELETE_SUCCESS);
    }

    /**
     * 通过管理员删除用户信息
     *
     * @param ids 身份证件
     *
     * @return {@code Result<String>}
     */
    @DeleteMapping(value = "/deleteUserInfoByAdmin")
    @ApiOperation("批量删除用户信息")
    public Result<String> deleteUserInfoByAdmin(@RequestBody List<Long> ids) {
        log.info("批量删除用户信息:{}", ids);
        usersService.removeByIds(ids);
        return Result.success(MessageConstants.USER_DELETE_SUCCESS);
    }


    //管理员激活用户
    @GetMapping(value = "/activeUserByAdmin/{id}")
    @ApiOperation("管理员激活用户")
    public Result<String> activeUserByAdmin(@PathVariable Long id) throws MyException {
        log.info("管理员激活用户:{}", id);
        usersService.active(id);
        return Result.success(MessageConstants.USER_ACTIVE_SUCCESS);
    }

    /**
     * 管理员禁用用户
     *
     * @param id 同上
     *
     * @return {@code Result<String>}
     */
    @GetMapping(value = "/disableUserByAdmin/{id}")
    @ApiOperation("管理员禁用用户")
    public Result<String> disableUserByAdmin(@PathVariable Long id) {
        log.info("管理员禁用用户:{}", id);
        usersService.disableUser(id);
        return Result.success(MessageConstants.USER_DISABLE_SUCCESS);
    }


    /**
     * 管理员取消禁用用户
     *
     * @param id 同上
     *
     * @return {@code Result<String>}
     */
    @GetMapping(value = "/cancelDisableUserByAdmin/{id}")
    @ApiOperation("管理员取消禁用用户")
    public Result<String> cancelDisableUserByAdmin(@PathVariable Long id) {
        log.info("管理员取消禁用用户:{}", id);
        usersService.cancelDisableUser(id);
        return Result.success(MessageConstants.USER_ABLE_SUCCESS);
    }


    /**
     * 重置密码
     *
     * @param users 用户
     *
     * @return {@code Result<String>}
     */
    @PostMapping(value = "/resetPassword")
    @ApiOperation("重置密码")
    public Result<String> resetPassword(@RequestBody Users users) {
        log.info("重置密码:{}", users);
        usersService.resetPassword(users);
        return Result.success(MessageConstants.USER_UPDATE_SUCCESS);
    }
    
    @GetMapping("/check")
    @ApiOperation("检查用户是否登录")
    public Result<String> check() {
        log.info("检查用户是否登录");
        return Result.success("用户已登录");
    }


}