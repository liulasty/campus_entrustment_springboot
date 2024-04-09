package com.lz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.Exception.MyException;
import com.lz.constants.MessageConstants;
import com.lz.mapper.UsersMapper;
import com.lz.pojo.dto.UserDTO;
import com.lz.pojo.dto.UserLoginDTO;
import com.lz.pojo.dto.UsersPageDTO;
import com.lz.pojo.entity.Users;
import com.lz.pojo.result.PageResult;
import com.lz.service.IUsersService;
import com.lz.utils.MailUtils;
import com.lz.utils.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 存储系统用户信息 服务实现类
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@Service
@Slf4j
@Transactional(rollbackFor = MyException.class )
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService{

    public static final String BASEURL = "http://localhost:80";

    @Autowired
    private UsersMapper usersMapper;

    /**
     * 登录
     *
     * @param userLoginDTO 用户登录 DTO
     */
    @Override
    public boolean login(UserLoginDTO userLoginDTO) throws MyException {
        if (userLoginDTO == null || userLoginDTO.getUsername() == null || userLoginDTO.getPassword() == null) {
            throw new MyException(MessageConstants.DATA_VALIDATION_ERROR);
        }

        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        QueryWrapper<Users> wrapper = queryWrapper.eq("UserName", userLoginDTO.getUsername());
        Users users = usersMapper.selectOne(wrapper);
        // 检查输入对象是否为null
        if (users == null) {
            throw new MyException(MessageConstants.USER_NOT_EXIST);
        }

        // 检查输入的密码是否为null或空
        String inputPassword = userLoginDTO.getPassword();
        if (inputPassword == null || inputPassword.trim().isEmpty()) {
            throw new MyException(MessageConstants.PASSWORD_CANNOT_BE_EMPTY);

        }

        if (PasswordUtils.check(inputPassword, users.getPassword())) {
            return true;
        } else if (!users.getIsActive()) {
            sendActivationEmail(users.getUserId(), users.getEmail(), BASEURL, 
                                "需激活后才能登录");
            throw new MyException(MessageConstants.USER_NOT_ACTIVE);
        }

        return false;

    }

    @Override
    public boolean register(UserDTO userDTO) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Username", userDTO.getUsername());
        Users users = usersMapper.selectOne(queryWrapper);


        // 检查输入对象是否为null
        if (users == null) {
            try {
                Users user = Users.builder().username(userDTO.getUsername())
                        .password(PasswordUtils.hashPassword(userDTO.getPassword()))
                        .role("user")
                        .isActive(false)
                        .email(userDTO.getEmail())
                        .phoneNumber(userDTO.getPhoneNumber())
                        .build();

                usersMapper.insert(user);
                // TODO: 2024/4/4
                sendActivationEmail(user.getUserId(), user.getEmail(),
                                    BASEURL, null);

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void sendActivationEmail(Integer id, String email, String baseUrl,
                                    String msg) throws MyException {
        try {
            // 构建激活链接
            String activeUrl = baseUrl + "/user/user/active/" + id;

            MailUtils.sendMail(email,
                               "你好，这是一封激活邮件，无需回复，点击此链接激活" + activeUrl
                                       + "\n" + msg,
                               "测试邮件");
            log.info("发送激活邮件成功");
        } catch (Exception e) {
            throw new MyException(MessageConstants.SEND_EMAIL_FAIL);
        }
    }

    @Override
    public boolean active(Long id) throws MyException {

        if (usersMapper.selectById(id) != null) {
            Users users = usersMapper.selectById(id);
            if (users.getIsActive()) {
                throw new MyException(MessageConstants.USER_ACTIVE_SUCCESS);
            }
            users.setIsActive(true);
            usersMapper.updateById(users);
            return true;
        }
        return false;
    }

    @Override
    public PageResult<?> getUserByPage(UsersPageDTO usersPageDTO) {
        if (usersPageDTO.validate() == null) {
            QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
            if (usersPageDTO.getUsername() != null&& !usersPageDTO.getUsername().trim().isEmpty()) {
                queryWrapper.like("Username", usersPageDTO.getUsername());
            }
            if (usersPageDTO.getRole() != null && !usersPageDTO.getRole().trim().isEmpty()) {
                queryWrapper.eq("Role", usersPageDTO.getRole());
            }
            if (usersPageDTO.getIsActive() != null ) {
                // 0 未激活 1 已激活
                if(usersPageDTO.getIsActive()){
                    queryWrapper.eq("IsActive", true);
                }else if(!usersPageDTO.getIsActive()){
                    queryWrapper.eq("IsActive", false);
                }
                
            }
            if (usersPageDTO.getPage() != null && usersPageDTO.getSize() != null) {
                Page<Users> page = new Page<>(usersPageDTO.getPage(), usersPageDTO.getSize());

                Page<Users> usersPage = usersMapper.selectPage(page, queryWrapper);

                usersPage.getRecords().forEach(users -> {
                    users.setPassword(null);
                });


                PageResult<Users> pageResult = new PageResult(usersPage.getTotal(),
                                                              usersPage.getRecords());
                return pageResult;
            }
        }
        return null;
    }

    /**
     * 重置密码
     *
     * @param users 用户
     */
    @Override
    public void resetPassword(Users users) {
        
    }

    /**
     * 取消禁用用户
     *
     * @param id 同上
     */
    @Override
    public void cancelDisableUser(Long id) {
        
    }

    /**
     * 禁用用户
     *
     * @param id 同上
     */
    @Override
    public void disableUser(Long id) {

    }

    @Override
    public Users getByUsername(String username) {
        
        return getOne(new QueryWrapper<Users>().eq("Username", username));
    }

}