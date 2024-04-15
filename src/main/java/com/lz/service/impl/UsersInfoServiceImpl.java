package com.lz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.Exception.MyException;
import com.lz.mapper.UsersInfoMapper;
import com.lz.mapper.UsersMapper;
import com.lz.pojo.Enum.AuthenticationStatus;
import com.lz.pojo.dto.UserInfoDTO;
import com.lz.pojo.entity.Users;
import com.lz.pojo.entity.UsersInfo;
import com.lz.service.IUsersInfoService;
import com.lz.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

/**
 * <p>
 * 存储系统用户详细信息 服务实现类
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@Service
public class UsersInfoServiceImpl extends ServiceImpl<UsersInfoMapper,
        UsersInfo> implements IUsersInfoService {
    @Autowired
    private UsersMapper usersMapper;

    /**
     * 提交认证信息
     *
     * @param dto DTO
     */
    @Override
    @PostMapping
    public void submitCertificationInformation(UserInfoDTO dto) throws MyException {
        Users byId = usersMapper.selectById(dto.getId());
        if (byId == null) {
            throw new MyException("用户不存在");
        }

        UsersInfo usersInfo = UsersInfo.builder()
                .roleImgSrc(dto.getImgUrl())
                .name(dto.getName())
                .qqNumber(dto.getQq())
                .userId(dto.getId())
                .phoneNumber(dto.getPhone())
                .userRole(dto.getRole())
                .authStatus(AuthenticationStatus.AUTHENTICATING)
                .certifieTime(new Date(System.currentTimeMillis()))
                .build();
        save(usersInfo);
    }
}