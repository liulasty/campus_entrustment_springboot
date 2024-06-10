package com.lz.mapper;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/06/10/23:08
 * @Description:
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lz.pojo.entity.IpLogs;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lz
 */
@Mapper
public interface IpLogMapper  extends BaseMapper<IpLogs> {
}