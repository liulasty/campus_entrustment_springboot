package com.lz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lz.Exception.MyException;
import com.lz.pojo.dto.UserInfoDTO;
import com.lz.pojo.entity.UsersInfo;

/**
 * <p>
 * 存储系统用户详细信息 服务类
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
public interface IUsersInfoService extends IService<UsersInfo> {

    /**
     * 提交认证信息
     *
     * @param dto DTO
     */
    void submitCertificationInformation(UserInfoDTO dto) throws MyException;
}