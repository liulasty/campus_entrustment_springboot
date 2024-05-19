package com.lz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.Exception.MyException;
import com.lz.pojo.Enum.TaskUpdateType;
import com.lz.pojo.entity.TaskUpdates;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.Date;

/**
 * <p>
 * 记录任务的更新情况 服务类
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
public interface ITaskUpdatesService extends IService<TaskUpdates> {
    /**
     * 完成审核操作，并返回对应的状态结果。
     *

     */
    void completeAudit();

    /**
     * 发布委托，并返回对应的状态结果。
     *

     */
    void publishAssignment();

    /**
     * 创建新任务，并返回对应的状态结果。
     *

     */
    void createNewTask();

    /**
     * 获取委托结果，并返回对应的状态结果。
     *

     */
    void getAssignmentResult();

    /**
     * 获取审核中状态，并返回对应的状态结果。
     */
    void getAuditingStatus();

    /**
     * 记录：将委托回退为草稿
     * @return {@code Boolean}
     */
    Boolean fallbackDraft(Long taskID) throws MyException;

    void allowPublish(Long taskId);

    void notAllowed(Long taskId);

    Boolean createNewRecord(Long taskId, TaskUpdateType auditing,
                      String dataAuditFail);


    IPage<TaskUpdates> page(Page<TaskUpdates> page, String delegateComment, String reviewStatus, Date reviewTime);

    /**
     * 委托任务取消发布
     *
     * @param id 同上
     */
    TaskUpdates cancelPublish(Long id);
}