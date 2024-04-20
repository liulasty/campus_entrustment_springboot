package com.lz.mapper;

import com.lz.pojo.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 存储系统用户信息 Mapper 接口
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

    /**
     * 按用户名获取
     *
     * @param username 用户名
     *
     * @return {@code Users}
     */
    Users getByUsername(String username);
}