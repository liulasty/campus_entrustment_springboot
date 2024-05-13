package com.lz.service;

import com.lz.Exception.MyException;
import com.lz.pojo.Page.UsersConfig;
import com.lz.pojo.dto.UserDTO;
import com.lz.pojo.dto.UserLoginDTO;
import com.lz.pojo.dto.UsersPageDTO;
import com.lz.pojo.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lz.pojo.result.PageResult;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 存储系统用户信息 服务类
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
public interface IUsersService extends IService<Users>  {

    /**
     * 登录
     *
     * @param userLoginDTO 用户登录 DTO
     *
     * @return boolean
     */
    boolean login(UserLoginDTO userLoginDTO) throws MyException;

    /**
     * 注册
     *
     * @param userDTO 用户 DTO
     */
    boolean register(UserDTO userDTO);

    /**
     * 激活
     *
     * @param id 同上
     *
     * @return boolean
     */
    boolean active(Long id) throws MyException;

    /**
     * 按页面获取用户
     *
     * @param config 查询数据
     *
     * @return {@code PageResult}
     */
    PageResult getUserByPage(UsersConfig config);

    /**
     * 重置密码
     *
     * @param users 用户
     */
    void resetPassword(Users users);

    /**
     * 取消禁用用户
     *
     * @param id 同上
     */
    void cancelDisableUser(Long id) throws MyException;

    /**
     * 禁用用户
     *
     * @param id 同上
     */
    void disableUser(Long id) throws MyException;

    /**
     * 按用户名获取
     *
     * @param username 用户名
     *
     * @return {@code Users}
     */
    Users getByUsername(String username);

    /**
     * 管理员辅助激活
     *
     * @param id 同上
     *
     * @return boolean
     */
    boolean adminActivation(Long id);

    /**
     * 删除用户
     *
     * @param singleton 单身 人士
     */
    void deleteUsers(int[] singleton) throws MyException;

   
}