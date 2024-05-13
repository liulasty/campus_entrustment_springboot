package com.lz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.Exception.MyException;
import com.lz.mapper.*;
import com.lz.pojo.Enum.*;
import com.lz.pojo.Page.DraftConfig;
import com.lz.pojo.constants.AuditResult;
import com.lz.pojo.constants.MessageConstants;
import com.lz.pojo.dto.AuditResultDTO;
import com.lz.pojo.dto.TaskCountDTO;
import com.lz.pojo.dto.TaskDTO;
import com.lz.pojo.dto.TaskPageDTO;
import com.lz.pojo.entity.*;
import com.lz.pojo.result.PageResult;
import com.lz.pojo.vo.*;
import com.lz.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 存储任务相关信息 服务实现类
 * </p>
 *
 * @author lz
 * @since 2024-04-04
 */
@Service
@Slf4j

public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

    @Autowired
    private IDelegationCategoriesService delegationCategoriesService;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersInfoMapper usersInfoMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ISystemAnnouncementsService systemAnnouncementsService;

    @Autowired
    private IDelegateAuditRecordsService delegateAuditRecordsService;

    @Autowired
    private DelegationCategoriesMapper delegationCategoriesMapper;

    @Autowired
    private ITaskUpdatesService taskUpdateService;

    @Autowired
    private TaskAcceptRecordsMapper taskAcceptRecordsMapper;

    @Autowired
    private TaskUpdatesMapper taskUpdatesMapper;


    /**
     * 获取最新信息
     *
     * @param id 同上
     */
    @Override
    public NewestInfoVO getNewestInfo(Long id) {
        NewestInfoVO newestInfoVO = new NewestInfoVO();
        newestInfoVO.setSystemAnnouncements(systemAnnouncementsService.getNewestAnnouncement());
        newestInfoVO.setNewestTask(getNewestTask());
        newestInfoVO.setHotTaskCategory(getHotTaskCategory());
        newestInfoVO.setTransactionStats(getTransactionStats());
        newestInfoVO.setTasksWithUser(getTasksWithUser(id));
        log.info("最新信息：{}", newestInfoVO);
        return newestInfoVO;
    }

    /**
     * 更新委托审核状态
     *
     * @param auditResultDTO 审计结果 DTO
     */
    @Override
    @Transactional
    public void updateTask(AuditResultDTO auditResultDTO) throws MyException {
        try {
            if (auditResultDTO.getReviewStatus().equals(AuditResult.APPROVED)) {
                updateById(Task.builder().taskId(auditResultDTO.getDelegateId()).status(TaskStatus.PENDING_RELEASE).build());
            } else if (auditResultDTO.getReviewStatus().equals(AuditResult.REJECTED)) {
                updateById(Task.builder().taskId(auditResultDTO.getDelegateId()).status(TaskStatus.AUDIT_FAILED).build());
            }
            //todo 保存审核记录
            DelegateAuditRecords delegateAuditRecords = new DelegateAuditRecords();
            BeanUtils.copyProperties(auditResultDTO, delegateAuditRecords);
            delegateAuditRecords.setReviewTime(new Date(System.currentTimeMillis()));
            log.info("保存审核记录：{}", delegateAuditRecords);
            
            delegateAuditRecordsService.save(delegateAuditRecords);
        } catch (BeansException e) {
            e.printStackTrace();
            log.error("更新委托审核状态失败");
            throw new MyException(MessageConstants.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public TaskDraftVO searchTask(Long id) throws MyException {
        Task task = taskMapper.selectById(id);
        if (task == null) {
            throw new MyException("未找到该委托");
        }
        log.info("查询结果：{}", task);
        TaskDraftVO taskDraftVO = new TaskDraftVO();
        BeanUtils.copyProperties(task, taskDraftVO);
        taskDraftVO.setType(delegationCategoriesMapper.selectById(task.getType()).getCategoryName());
        return taskDraftVO;

    }

    @Override
    public PageResult<Task> searchPage(TaskPageDTO taskPageDTO) {
        Page<Task> page = new Page<>(taskPageDTO.getPageNum(),
                                     taskPageDTO.getPageSize());

        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", TaskStatus.AUDITING);
        queryWrapper.like("description", taskPageDTO.getDescription());
        queryWrapper.like("location", taskPageDTO.getLocation());
        log.info("查询条件：{}", queryWrapper);
        Page<Task> taskPage = page(page, queryWrapper);


        PageResult<Task> pageResult = new PageResult<>();

        pageResult.setTotal(taskPage.getTotal());
        pageResult.setRecords(taskPage.getRecords());
        log.info("分页结果：{}", pageResult);

        return pageResult;
    }

    /**
     * 获取最新委托
     *
     * @return {@code Task}
     */
    @Override
    public List<Task> getNewestTask() {
        Page<Task> page = new Page<>(1, 5); // 分页查询，第1页，每页5条记录
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        // 设置查询条件
        wrapper
                // 设置排序规则
                .orderByDesc("StartTime")
                .lt("StartTime", new Date(System.currentTimeMillis()))
                // 设置查询条件
                .eq("status", TaskStatus.ONGOING);
        // 获取最新委托


        return taskMapper.selectPage(page,
                                     wrapper).getRecords();
    }

    @Override
    public Map<String, TaskCountDTO> getHotTaskCategory() {


        List<TaskCountDTO> tasks = taskMapper.selectTaskTypeCountTop5();
        log.info("热门类别：{}", tasks);
        // 获取热门委托类别
        HashMap<String, TaskCountDTO> hashMap = new LinkedHashMap<>();
        tasks.forEach(taskCountDTO -> hashMap.put(
                delegationCategoriesService.getById(taskCountDTO.getTaskTypeId()).getCategoryName(), taskCountDTO));


        return hashMap;
    }

    /**
     * 按类别搜索委托
     *
     * @param category 类别
     */
    @Override
    public void searchTaskByCategory(String category) {

    }

    /**
     * 获取委托统计信息
     *
     * @return {@code Throwable}
     */
    @Override
    public Map<String, Integer> getTransactionStats() {
        //查询本月 本周 今天的 委托记录
        Map<String, Integer> map = new LinkedHashMap<String, Integer>();

        Integer tasksTodayCount1 =
                taskMapper.getTasksTodayCount(TaskStatus.ACCEPTED.getDbValue());
        map.put("今日已接受", tasksTodayCount1);
        Integer tasksWeeklyCount1 =
                taskMapper.getTasksWeeklyCount(TaskStatus.ACCEPTED.getDbValue());
        map.put("本周已接受", tasksWeeklyCount1);
        Integer tasksMonthlyCount1 =
                taskMapper.getTasksMonthlyCount(TaskStatus.ACCEPTED.getDbValue());
        map.put("本月已接受", tasksMonthlyCount1);

        Integer tasksTodayCount =
                taskMapper.getTasksTodayCount(TaskStatus.ONGOING.getDbValue());
        map.put("今日已发布", tasksTodayCount + tasksTodayCount1);
        Integer tasksWeeklyCount =
                taskMapper.getTasksWeeklyCount(TaskStatus.ONGOING.getDbValue());
        map.put("本周已发布", tasksWeeklyCount + tasksWeeklyCount1);
        Integer tasksMonthlyCount =
                taskMapper.getTasksMonthlyCount(TaskStatus.ONGOING.getDbValue());
        map.put("本月已发布", tasksMonthlyCount + tasksMonthlyCount1);


        return map;
    }

    @Override
    public List<Task> getTasksWithUser(Long userId) {
        Map<String, Task> map = new LinkedHashMap<String, Task>();
        // 设置查询条件
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        // 设置查询条件
        wrapper
                // 设置排序规则
                .orderByDesc("CreatedAt")
                // 设置查询条件
                .eq("ReceiverID", userId)
                .or()
                .eq("OwnerID", userId);


        return taskMapper.selectList(wrapper);
    }

    /**
     * 获取用户委托草稿
     *
     * @param userId 用户 ID
     *
     * @return {@code List<Task>}
     */
    @Override
    public List<UserDelegateDraft> getUserDelegateDraft(Long userId) {
        // 设置查询条件
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        // 设置查询条件
        wrapper
                // 设置排序规则
                .orderByDesc("CreatedAt")
                .lt("CreatedAt", new Date(System.currentTimeMillis()))
                // 设置查询条件
                .eq("OwnerID", userId)
                .in("Status", Arrays.asList(TaskStatus.DRAFT,
                                            TaskStatus.AUDITING,
                                            TaskStatus.PENDING_RELEASE,
                                            TaskStatus.AUDIT_FAILED))
        ;

        List<Task> tasks = taskMapper.selectList(wrapper);

        List<UserDelegateDraft> userDelegateDrafts = new ArrayList<>();
        for (Task task : tasks) {
            UserDelegateDraft userDelegateDraft = new UserDelegateDraft();
            BeanUtils.copyProperties(task, userDelegateDraft);
            userDelegateDraft.setCreateTime(task.getCreatedAt());
            userDelegateDrafts.add(userDelegateDraft);
        }
        return userDelegateDrafts;
    }

    /**
     * 创建用户委托草稿
     *
     * @param taskDTO
     */
    @Override
    public void createTask(TaskDTO taskDTO) throws MyException {
        Users users = usersMapper.selectById(taskDTO.getOwnerId());
        if (users == null || !Objects.equals(users.getRole(), "USER")) {
            throw new MyException("用户不存在");
        }

        DelegationCategories delegationCategories = delegationCategoriesService.getTaskCategoryByCategoryName(taskDTO.getType());
        if (delegationCategories == null) {
            throw new MyException("类别不存在");
        }


        Task task = Task.builder()
                .createdAt(new Date(System.currentTimeMillis()))
                .description(taskDTO.getContent())
                .ownerId(taskDTO.getOwnerId())
                .status(TaskStatus.DRAFT)
                .type(delegationCategories.getCategoryId())
                .location(taskDTO.getLocation())
                .build();

        taskMapper.insert(task);
        //todo 添加更新记录
        TaskUpdates taskUpdates =
                TaskUpdates.builder()
                        .taskId(task.getTaskId())
                        .updateType(TaskUpdateType.CREATED)
                        .updateDescription("创建委托草稿")
                        .updateTime(new Date(System.currentTimeMillis()))
                        .userId(taskDTO.getOwnerId()).build();
        taskUpdatesMapper.insert(taskUpdates);
    }

    /**
     * 管理员搜索委托
     *
     * @param draftConfig 草稿配置
     *
     * @return {@code PageResult<Task>}
     */
    @Override
    public PageResult<Task> searchPageByAdmin(DraftConfig draftConfig) {
        Page<Task> page = new Page<>(draftConfig.getPageNum(),
                                     draftConfig.getPageSize());

        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        if (draftConfig.getTaskType() != null) {
            wrapper.eq("TaskType", draftConfig.getTaskType());
        }
        if (draftConfig.getCreatedAt() != null) {
            wrapper.gt("CreatedAt", draftConfig.getCreatedAt());
        }
        if (draftConfig.getDescription() != null && !"".equals(draftConfig.getDescription())) {
            wrapper.like("Description", draftConfig.getDescription());
        }
        if (draftConfig.getLocation() != null && !"".equals(draftConfig.getLocation())) {
            wrapper.eq("Location", draftConfig.getLocation());
        }
        if (draftConfig.getTypePhase() != null) {
            wrapper.in("Status", TaskStatus.getStatusesForPhase(draftConfig.getTypePhase()));
        }

        page = taskMapper.selectPage(page, wrapper);

        log.info("page:{}", page.getRecords());
        return new PageResult<>(page.getTotal(), page.getRecords());
    }

    /**
     * 回退草稿
     *
     * @param taskId 任务 ID
     *
     * @return 布尔
     */
    @Override
    public Boolean fallbackDraft(Long taskId) throws MyException {
        Task task = taskMapper.selectById(taskId);
        if (task == null) {
            throw new MyException(MessageConstants.TASK_NOT_EXIST);
        }

        List<TaskStatus> statusList = TaskStatus.getFallbackDraft();
        boolean existsInList = statusList.stream()
                .anyMatch(status -> status.equals(task.getStatus()));
        if (existsInList) {
            task.setStatus(TaskStatus.DRAFT);
            taskMapper.updateById(task);
            taskUpdateService.fallbackDraft(taskId);
            return true;
        }
        return false;
    }

    /**
     * 允许发布
     *
     * @param taskId 任务 ID
     *
     * @return 布尔
     */
    @Override
    public Boolean allowPublish(Long taskId) throws MyException {
        Task task = taskMapper.selectById(taskId);
        if (task == null) {
            throw new MyException(MessageConstants.TASK_NOT_EXIST);
        }
        if (task.getStatus() == TaskStatus.AUDITING) {
            task.setStatus(TaskStatus.PENDING_RELEASE);
            taskMapper.updateById(task);
            delegateAuditRecordsService.createNewRecord(taskId, AuditResult.APPROVED,
                                                        TaskStatus.PENDING_RELEASE);
            taskUpdateService.allowPublish(taskId);
            return true;
        }
        return false;
    }

    /**
     * 不允许
     *
     * @param taskId 任务 ID
     *
     * @return 布尔
     */
    @Override
    public Boolean notAllowed(Long taskId) throws MyException {
        Task task = taskMapper.selectById(taskId);
        if (task == null) {
            throw new MyException(MessageConstants.TASK_NOT_EXIST);
        }
        if (task.getStatus() == TaskStatus.AUDITING) {
            task.setStatus(TaskStatus.AUDIT_FAILED);
            taskMapper.updateById(task);
            taskUpdateService.notAllowed(taskId);
            delegateAuditRecordsService.createNewRecord(taskId, AuditResult.REJECTED,
                                                        TaskStatus.AUDIT_FAILED);
            taskUpdateService.createNewRecord(taskId, TaskUpdateType.AUDITING,
                                              MessageConstants.DATA_AUDIT_FAIL);
            return true;
        }
        return false;
    }

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
    @Override
    public PageResult<Task> searchPage(int pageNum, int pageSize,
                                       String location, String description,
                                       Long taskTypeId, Integer queryRules,
                                       TaskStatus status) {
        Page<Task> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        if (status != null) {
            wrapper.eq("Status", status);
        } else {
            wrapper.in("Status", TaskStatus.ACCEPTED, TaskStatus.ONGOING);
        }
        if (taskTypeId != null) {
            wrapper.eq("TaskType", taskTypeId);
        }
        if (location != null && !"".equals(location)) {
            wrapper.eq("Location", location);
        }
        if (description != null && !"".equals(description)) {
            wrapper.like("Description", description);
        }
        if (queryRules == 0) {
            wrapper.orderByDesc("StartTime");

        } else {
            wrapper.orderByAsc("StartTime");
        }
        page = taskMapper.selectPage(page, wrapper);
        return new PageResult<>(page.getTotal(), page.getRecords());
    }

    /**
     * 搜委托信息与委托人信息
     *
     * @param id 委托id
     *
     * @return <p>
     */
    @Override
    public TaskAndUserInfoVO getTaskAndPublisherInfo(Long id) throws MyException {
        Task task = taskMapper.selectById(id);
        if (task == null) {
            throw new MyException(MessageConstants.TASK_NOT_EXIST);
        }

        UsersInfo usersInfo = usersInfoMapper.selectById(task.getOwnerId());
        if (usersInfo == null || usersInfo.getAuthStatus() != AuthenticationStatus.AUTHENTICATED) {
            throw new MyException(MessageConstants.USER_NOT_EXIST);
        }
        usersInfo.setRoleImgSrc("");
        usersInfo.setCertifieTime(null);
        usersInfo.setCertifiedTime(null);
        List<TaskAcceptRecords> records =
                taskAcceptRecordsMapper.selectList(new QueryWrapper<TaskAcceptRecords>().eq("taskId", id));

        if (records.size() == 0) {
            return new TaskAndUserInfoVO(task, usersInfo);
        }
        ArrayList<TaskAcceptRecordVO> list = new ArrayList<>();
        for (TaskAcceptRecords record : records) {
            UsersInfo info = usersInfoMapper.selectById(record.getReceiverId());
            log.info("发布者信息 {}", info);
            if (info == null) {
                throw new MyException(MessageConstants.USER_INFO_ERROR);
            }
            TaskAcceptRecordVO vo = new TaskAcceptRecordVO(record,
                                                           info.getUserId(),
                                                           info.getName(),
                                                           info.getUserRole());
            list.add(vo);
        }
        return new TaskAndUserInfoVO(task, usersInfo, list);
    }

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
    @Override
    public PageResult<Task> searchPageByPublisher(int pageNum, int pageSize, String location, String description, Long taskType, Integer queryRules, TaskStatus status) {
        Page<Task> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        if (status != null) {
            wrapper.eq("Status", status);
        } else {
            wrapper.in("Status", TaskStatus.ACCEPTED, TaskStatus.ONGOING,
                       TaskStatus.EXPIRED, TaskStatus.CANCELLED);
        }
        Users currentAdmin = getCurrentAdmin();
        wrapper.eq("OwnerID", currentAdmin.getUserId());
        if (taskType != null) {
            wrapper.eq("TaskType", taskType);
        }
        if (location != null && !"".equals(location)) {
            wrapper.eq("Location", location);
        }
        if (description != null && !"".equals(description)) {
            wrapper.like("Description", description);
        }
        if (queryRules == 0) {
            wrapper.orderByDesc("StartTime");

        } else {
            wrapper.orderByAsc("StartTime");
        }
        page = taskMapper.selectPage(page, wrapper);
        return new PageResult<>(page.getTotal(), page.getRecords());
    }

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
    @Override
    public PageResult<TaskAcceptRecord> searchPageByAcceptor(int pageNum,
                                                             int pageSize,
                                                             String location,
                                                             String description,
                                                             Long taskType,
                                                             Integer queryRules,
                                                             TaskStatus status) {
        log.info("接受者搜索任务 {}", queryRules);
        Page<TaskAcceptRecord> page = new Page<>(pageNum, pageSize);
        Long userId = getCurrentAdmin().getUserId();
        IPage<TaskAcceptRecord> taskAcceptRecord =
                taskAcceptRecordsMapper.searchTaskAcceptRecord(page,
                                                               userId,
                                                               null,
                                                               status,
                                                               location,
                                                               description,
                                                               taskType,
                                                               queryRules
                );

        return new PageResult(taskAcceptRecord.getTotal(), taskAcceptRecord.getRecords());
    }

    /**
     * 确认委托接受者
     *
     * @param taskId        任务 ID
     * @param acceptRecords 同上
     */
    @Override
    @Transactional(rollbackFor = MyException.class)
    public void confirmTheRecipient(Long taskId, TaskAcceptRecords acceptRecords) throws MyException {
        if (acceptRecords == null) {
            throw new MyException(MessageConstants.TASK_NOT_EXIST);
        }
        List<TaskAcceptRecords> list = taskAcceptRecordsMapper.selectList(new QueryWrapper<TaskAcceptRecords>().eq("taskId", taskId));

        acceptRecords.setStatus(AcceptStatus.CHECKED);
        acceptRecords.setAdoptTime(new Date(System.currentTimeMillis()));
        int byId = taskAcceptRecordsMapper.updateById(acceptRecords);
        if (byId == 0) {
            throw new MyException(MessageConstants.TASK_NOT_EXIST);
        }
        UpdateWrapper<TaskAcceptRecords> updateWrapper = new UpdateWrapper<>();
        TaskAcceptRecords taskAcceptRecords = TaskAcceptRecords.builder().status(AcceptStatus.UNCHECKED).build();
        taskAcceptRecords.setAdoptTime(new Date(System.currentTimeMillis()));
        updateWrapper.eq("taskId", taskId).eq("status", AcceptStatus.PENDING);
        int update = taskAcceptRecordsMapper.update(taskAcceptRecords, updateWrapper);

        TaskUpdates taskupdates = TaskUpdates.builder()
                .taskId(taskId)
                .updateDescription(MessageConstants.TASK_ACCEPT_SUCCESS)
                .updateType(TaskUpdateType.RESULT)
                .updateTime(new Date(System.currentTimeMillis()))
                .build();

        taskUpdateService.save(taskupdates);

        if (update != list.size() - 1) {
            log.info("更新失败 update {} list.size() {}", update, list.size());
            throw new MyException(MessageConstants.DATABASE_ERROR);
        }
        Task task = Task.builder().taskId(taskId).status(TaskStatus.ACCEPTED).build();
        updateById(task);
        //todo 通知接受者

    }

    @Override
    public List<Task> getTaskByCategoryId(Long id) {
        List<Task> taskList = taskMapper.selectList(new QueryWrapper<Task>().eq("taskType", id));
        
        return taskList;
    }

    public Users getCurrentAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminName = authentication.getName();
        // log.info("管理员: {}", adminName);

        return usersMapper.getByUsername(adminName);
    }


}