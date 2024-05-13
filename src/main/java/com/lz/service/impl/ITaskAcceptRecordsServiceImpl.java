package com.lz.service.impl;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/03/14:55
 * @Description:
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectList;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.Exception.MyException;
import com.lz.common.security.AuthenticationService;
import com.lz.mapper.TaskAcceptRecordsMapper;
import com.lz.mapper.TaskMapper;
import com.lz.mapper.UsersMapper;
import com.lz.pojo.Enum.AcceptStatus;
import com.lz.pojo.Enum.TaskStatus;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.dto.AcceptDTO;
import com.lz.pojo.entity.Task;
import com.lz.pojo.entity.Users;
import com.lz.pojo.entity.TaskAcceptRecords;
import com.lz.service.ITaskAcceptRecordsService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lz
 */
@Service
@Slf4j
public class ITaskAcceptRecordsServiceImpl extends ServiceImpl<TaskAcceptRecordsMapper,
        TaskAcceptRecords> implements ITaskAcceptRecordsService {
    @Autowired
    private UsersMapper usersMapper;
    
    @Autowired
    private TaskMapper taskMapper;
    
    @Autowired
    private TaskAcceptRecordsMapper taskAcceptRecordsMapper;

    /**
     * 获取当前登录用户信息
     *
     * @return {@code Users}
     */
    public Users getCurrentAdmin() throws MyException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminName = authentication.getName();
        // log.info("管理员: {}", adminName);
        Users users = usersMapper.getByUsername(adminName);
        if (users == null){
            log.error("未登录，或者权限不足");
            throw new MyException("未登录，或者权限不足");
        }

        return users;
    }
    
    @SneakyThrows
    @Override
    public void create(AcceptDTO acceptDTO) {
        Task task = taskMapper.selectById(acceptDTO.getTask());
        if (task.getStatus() != TaskStatus.ONGOING){
            log.error("任务状态错误");
            throw  new MyException("任务状态错误");
        }
        Users currentAdmin = getCurrentAdmin();
        log.info("当前操作用户和委托任务{} {}",currentAdmin,task.getOwnerId());
        if (currentAdmin.getUserId().equals(task.getOwnerId())){
            log.error("当前用户非法操作");
            throw  new MyException("当前用户非法操作");
        }
        AuthenticationService authenticationService = new AuthenticationService();
        log.info("设置用户认证信息到安全上下文 {}",authenticationService.getAuthentication());
        if (task.getOwnerId().equals(acceptDTO.getUser())){
            log.error("接受者不能是自己");
            throw  new MyException("接受者不能是自己");
        }
        
        TaskAcceptRecords one =
                getTaskAcceptRecordsByTaskId(acceptDTO.getUser(),acceptDTO.getTask());

        if (one != null){
            log.error("该任务已接收");
            throw new MyException("该任务已接收");
        }
        TaskAcceptRecords taskAcceptRecords = TaskAcceptRecords.builder()
                .receiverId(acceptDTO.getUser())
                .taskId(acceptDTO.getTask())
                .str(acceptDTO.getStr())
                .status(AcceptStatus.PENDING)
                .acceptTime(new Date(System.currentTimeMillis()))
                .build();
        
        save(taskAcceptRecords);
    }

    @Override
    public TaskAcceptRecords getTaskAcceptRecordByTaskId(Long taskId) throws MyException {
        QueryWrapper<TaskAcceptRecords> queryWrapper = new QueryWrapper<>();
        Users users = getCurrentAdmin();
        queryWrapper.eq("taskId",taskId);
        queryWrapper.eq("AccepterId",users.getUserId());
        return getOne(queryWrapper);
    }

    @Override
    public TaskAcceptRecords getTaskAcceptRecordsByTaskId(Long userId,
                                                          Long taskId) {
        QueryWrapper<TaskAcceptRecords> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("taskId",taskId);
        queryWrapper.eq("AccepterId",userId);


        return getOne(queryWrapper);
    }

    @Override
    public List<TaskAcceptRecords> getTaskAcceptRecordsByTaskId(Long taskId) throws MyException {
        QueryWrapper<TaskAcceptRecords> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("taskId",taskId);
        List<TaskAcceptRecords> taskAcceptRecords = taskAcceptRecordsMapper.selectList(queryWrapper);
        if (taskAcceptRecords.size() == 0){
            throw new MyException(MessageConstants.TASK_NOT_EXIST);
        }
        return taskAcceptRecords;
        
    }


}