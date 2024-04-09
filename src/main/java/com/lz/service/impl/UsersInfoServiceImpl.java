package com.lz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.mapper.UsersInfoMapper;
import com.lz.pojo.entity.UsersInfo;
import com.lz.service.IUsersInfoService;
import org.springframework.stereotype.Service;

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

   
}