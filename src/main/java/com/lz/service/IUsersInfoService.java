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

    /**
     * 确认通过审核
     *
     * @param id 同上
     *
     * @return {@code Boolean}
     */
    Boolean confirmToPassTheReview(Long id) throws MyException;

    /**
     * 拒绝通过审核
     *
     * @param id 同上
     *
     * @return {@code Boolean}
     */
    Boolean refuseToPassReview(Long id) throws MyException;

    /**
     * 取消用户信息身份
     *
     * @param id 同上
     *
     * @return {@code Boolean}
     */
    Boolean cancelUserInfoAuthentication(Long id) throws MyException;


}