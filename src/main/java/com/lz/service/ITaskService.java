package com.lz.service;

import com.lz.Exception.MyException;
import com.lz.pojo.Enum.TaskStatus;
import com.lz.pojo.Page.DraftConfig;
import com.lz.pojo.dto.AuditResultDTO;
import com.lz.pojo.dto.TaskCountDTO;
import com.lz.pojo.dto.TaskDTO;
import com.lz.pojo.dto.TaskPageDTO;
import com.lz.pojo.entity.Task;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lz.pojo.result.PageResult;
import com.lz.pojo.vo.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 存储任务相关信息 服务类
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
public interface ITaskService extends IService<Task> {

    /**
     * 获取最新信息
     *
     * @param id 同上
     *
     * @return {@code NewestInfoVO}
     */
    NewestInfoVO getNewestInfo(Long id);
    /**
    * 更新
     */
    void updateTask(AuditResultDTO auditResultDTO);


    /**
     * 查询
     *
     * @param id 同上
     *
     * @return {@code TaskDraftVO}
     *
     * @throws MyException 我的异常
     */
    TaskDraftVO searchTask(Long id) throws MyException;

    /**
     * 分页
     *
     * @param taskPageDTO 任务页 DTO
     *
     * @return {@code PageResult<Task>}
     */
    PageResult<Task> searchPage(TaskPageDTO taskPageDTO);

    /**
     * 获取最新委托
     *
     * @return {@code Task}
     */
    List<Task> getNewestTask();

    /**
     * 获取热门委托类别
     *
     * @return {@code String[]}
     */
    Map<String, TaskCountDTO> getHotTaskCategory();

    /**
     * 按类别搜索委托
     *
     * @param category 类别
     */
    void searchTaskByCategory(String category);


    /**
     * 获取委托成交统计信息
     *
     * @return {@code Throwable}
     */
    Map<String,Integer> getTransactionStats();

    /**
     * 获取用户相关委托
     *
     * @param id 同上
     *
     * @return {@code Map<String,Task>}
     */
    List<Task> getTasksWithUser(Long id);

    /**
     * 获取用户委托草稿
     *
     * @param userId 用户 ID
     *
     * @return {@code List<Task>}
     */
    List<UserDelegateDraft> getUserDelegateDraft(Long userId);

    /**
     * 创建用户委托草稿
     * @param taskDTO
     */
    void createTask(TaskDTO taskDTO) throws MyException;

    /**
     * 管理员搜索委托
     *
     * @param draftConfig 草稿配置
     *
     * @return {@code PageResult<Task>}
     */
    PageResult<Task> searchPageByAdmin(DraftConfig draftConfig);


    /**
     * 回退草稿
     *
     * @param taskId 任务 ID
     *
     * @return 布尔
     */
    Boolean fallbackDraft(Long taskId) throws MyException;

    /**
     * 允许发布
     *
     * @param taskId 任务 ID
     *
     * @return 布尔
     */
    Boolean allowPublish(Long taskId) throws MyException;

    /**
     * 不允许
     *
     * @param taskId 任务 ID
     *
     * @return 布尔
     */
    Boolean notAllowed(Long taskId) throws MyException;

    /**
     * 搜索已发布和已接收委托页面
     *
     * @param pageNum     页码
     * @param pageSize    页面大小
     * @param location    位置
     * @param description 描述
     * @param taskTypeId  任务类型 ID
     * @param queryRules  查询规则
     *
     * @return 页面结果<任务>
     */
    PageResult<Task> searchPage(int pageNum, int pageSize, String location,
                                String description, Long taskTypeId,
                                Integer queryRules, TaskStatus status);

    /**
     * 搜委托信息与委托人信息
     * @param id 委托id
     *
     * @return <p>
     */
    TaskAndUserInfoVO getTaskAndPublisherInfo(Long id) throws MyException;

    /**
     * 按发布者搜索页面
     *
     * @param pageNum     页码
     * @param pageSize    页面大小
     * @param location    位置
     * @param description 描述
     * @param taskType    任务类型
     * @param queryRules  查询规则
     * @param status      地位
     *
     * @return 页面结果<任务>
     */
    PageResult<Task> searchPageByPublisher(int pageNum, int pageSize, String location, String description, Long taskType, Integer queryRules, TaskStatus status) throws MyException;

    /**
     * 按接受者搜索页面
     *
     * @param pageNum     页码
     * @param pageSize    页面大小
     * @param location    位置
     * @param description 描述
     * @param taskType    任务类型
     * @param queryRules  查询规则
     * @param status      地位
     *
     * @return 页面结果<任务>
     */
    PageResult<TaskAcceptRecord> searchPageByAcceptor(int pageNum, int pageSize, String location, String description, Long taskType, Integer queryRules, TaskStatus status);    
}   