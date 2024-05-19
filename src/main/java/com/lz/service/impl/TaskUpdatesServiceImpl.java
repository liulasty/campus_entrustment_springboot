package com.lz.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.Exception.MyException;
import com.lz.mapper.TaskMapper;
import com.lz.mapper.UsersMapper;
import com.lz.pojo.Enum.TaskUpdateType;
import com.lz.pojo.entity.TaskUpdates;
import com.lz.mapper.TaskUpdatesMapper;
import com.lz.pojo.entity.Users;
import com.lz.service.ITaskUpdatesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

/**
 * <p>
 * 记录任务的更新情况 服务实现类
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@Service
@Slf4j
public class TaskUpdatesServiceImpl extends ServiceImpl<TaskUpdatesMapper, TaskUpdates> implements ITaskUpdatesService {
    

    @Autowired
    private UsersMapper usersMapper;
    
    @Autowired
    private TaskMapper taskMapper;
    
    @Autowired
    private TaskUpdatesMapper taskUpdatesMapper;

    /**
     * 获取当前登录用户信息
     *
     * @return {@code Users}
     */
    public Users getCurrentAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminName = authentication.getName();
        log.info("管理员: {}", adminName);

        return usersMapper.getByUsername(adminName);
    }

    /**
     * 完成审核操作，并返回对应的状态结果。
     *
     * @return 审核完成状态（YourEnumClass.AUDITING）
     */
    @Override
    public void completeAudit() {
        
    }

    /**
     * 发布委托，并返回对应的状态结果。
     *
     * @return 发布委托状态（YourEnumClass.PUBLISHED）
     */
    @Override
    public void publishAssignment() {

    }

    /**
     * 创建新任务，并返回对应的状态结果。
     *
     * @return 新任务创建状态（YourEnumClass.CREATED）
     */
    @Override
    public void createNewTask() {

    }

    /**
     * 获取委托结果，并返回对应的状态结果。
     *
     * @return 委托结果状态（YourEnumClass.RESULT）
     */
    @Override
    public void getAssignmentResult() {
        

    }

    /**
     * 获取审核中状态，并返回对应的状态结果。
     */
    @Override
    public void getAuditingStatus() {

    }

    /**
     * 将委托回退为草稿
     *
     * @param taskId
     *
     * @return {@code Boolean}
     */
    @Override
    public Boolean fallbackDraft(Long taskId) throws MyException {
        Users currentAdmin = getCurrentAdmin();
        if (currentAdmin != null) {
            TaskUpdates taskupdates = new TaskUpdates();
            taskupdates.setTaskId(taskId);
            taskupdates.setUserId(currentAdmin.getUserId());
            taskupdates.setUpdateType(TaskUpdateType.FALLBACK_DRAFT);
            taskupdates.setUpdateDescription("回退为草稿");
            taskupdates.setUpdateTime(new java.util.Date());
            save(taskupdates);
            return true;
        }
        log.error("权限异常，信息不存在");
        return false;
    }

    @Override
    public void allowPublish(Long taskId) {
        
    }

    @Override
    public void notAllowed(Long taskId) {
        

    }

    @Override
    public Boolean createNewRecord(Long taskId, TaskUpdateType auditing,
                             String dataAuditFail) {
        Users currentAdmin = getCurrentAdmin();
        if (currentAdmin != null) {
            TaskUpdates taskupdates = new TaskUpdates();
            taskupdates.setTaskId(taskId);
            taskupdates.setUserId(currentAdmin.getUserId());
            taskupdates.setUpdateType(auditing);
            taskupdates.setUpdateDescription("回退为草稿");
            taskupdates.setUpdateTime(new java.util.Date());
            save(taskupdates);
            return true;
        }
        log.error("权限异常，信息不存在");
        return false;
        
    }

    @Override
    public IPage<TaskUpdates> page(Page<TaskUpdates> page, String delegateComment, String reviewStatus, Date reviewTime) {
        log.info("delegateComment: {}, reviewStatus: {}, reviewTime: {}", delegateComment, reviewStatus, reviewTime);
        IPage<TaskUpdates> list = taskUpdatesMapper.page(page, delegateComment,
                                                     reviewStatus, reviewTime);
        return list;
    }

    @Override
    public TaskUpdates cancelPublish(Long id) {
        Users users = getCurrentAdmin();
        TaskUpdates taskUpdates = TaskUpdates.builder().taskId(id)
                .userId(users.getUserId())
                .updateType(TaskUpdateType.RESULT)
                .updateDescription("管理员取消发布委托编号为"+id+"的任务")
                .updateTime(new Date(System.currentTimeMillis()))
                .build();

        save(taskUpdates);
        return taskUpdates;
    }
}