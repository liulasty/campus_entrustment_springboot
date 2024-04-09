package com.lz.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lz.pojo.entity.UsersInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 存储系统用户详细信息 Mapper 接口
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@Mapper
public interface UsersInfoMapper extends BaseMapper<UsersInfo> {

}