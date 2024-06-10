package com.lz.service.impl;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/06/10/23:10
 * @Description:
 */

import com.lz.mapper.IpLogMapper;
import com.lz.pojo.entity.IpLogs;
import com.lz.service.IpLogsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author lz
 */
@Service
public class IpLogsServiceImpl extends ServiceImpl<IpLogMapper, IpLogs> implements IpLogsService {
}