package com.lz.service.impl;

import com.lz.pojo.entity.AdminSettings;
import com.lz.mapper.AdminsettingsMapper;
import com.lz.service.IAdminsettingsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 存储系统管理员相关设置 服务实现类
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@Service
public class AdminSettingsServiceImpl extends ServiceImpl<AdminsettingsMapper
        , AdminSettings> implements IAdminsettingsService {

}