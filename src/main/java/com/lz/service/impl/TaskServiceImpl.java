package com.lz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.Exception.MyException;
import com.lz.mapper.ReviewsMapper;
import com.lz.mapper.UsersMapper;
import com.lz.pojo.Enum.AnnouncementStatus;
import com.lz.pojo.Enum.TaskStatus;
import com.lz.pojo.constants.AuditResult;
import com.lz.pojo.dto.AuditResultDTO;
import com.lz.pojo.dto.TaskCountDTO;
import com.lz.pojo.dto.TaskDTO;
import com.lz.pojo.dto.TaskPageDTO;
import com.lz.pojo.entity.*;
import com.lz.mapper.TaskMapper;
import com.lz.pojo.result.PageResult;
import com.lz.pojo.vo.NewestInfoVO;
import com.lz.pojo.vo.TaskDraftVO;
import com.lz.pojo.vo.UserDelegateDraft;
import com.lz.service.IDelegateAuditRecordsService;
import com.lz.service.IDelegationCategoriesService;
import com.lz.service.ISystemAnnouncementsService;
import com.lz.service.ITaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
@Transactional(rollbackFor = Exception.class)
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

    @Autowired
    private IDelegationCategoriesService delegationCategoriesService;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ISystemAnnouncementsService systemAnnouncementsService;

    @Autowired
    private IDelegateAuditRecordsService delegateAuditRecordsService;
    

   

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
    public void updateTask(AuditResultDTO auditResultDTO) {
        try {
            if (auditResultDTO.getReviewStatus().equals(AuditResult.APPROVED)){
               updateById(Task.builder().taskId(auditResultDTO.getDelegateId()).status(TaskStatus.PENDING_RELEASE).build());
            }else if (auditResultDTO.getReviewStatus().equals(AuditResult.REJECTED)){
                updateById(Task.builder().taskId(auditResultDTO.getDelegateId()).status(TaskStatus.AUDIT_FAILED).build());
            }
            DelegateAuditRecords delegateAuditRecords = new DelegateAuditRecords();
            BeanUtils.copyProperties(auditResultDTO, delegateAuditRecords);
            delegateAuditRecords.setReviewTime(new Date(System.currentTimeMillis()));
            delegateAuditRecordsService.save(delegateAuditRecords);
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTask() {

    }

    @Override
    public TaskDraftVO searchTask(Long id) throws MyException {
        Task task = taskMapper.selectById(id);
        if (task == null) {
            throw new MyException("未找到该委托");
        }
        TaskDraftVO taskDraftVO = new TaskDraftVO();
        BeanUtils.copyProperties(task, taskDraftVO);
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
                .lt("StartTime",new Date(System.currentTimeMillis()) )
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
        HashMap<String, TaskCountDTO> hashMap = new LinkedHashMap<String, TaskCountDTO>();
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
    public Map<String,Integer>  getTransactionStats() {
        //查询本月 本周 今天的 委托记录
        Map<String,Integer> map = new LinkedHashMap<String, Integer>();

        Integer tasksTodayCount1 =
                taskMapper.getTasksTodayCount(TaskStatus.ACCEPTED.getDbValue());
        map.put("今日已接受",tasksTodayCount1);
        Integer tasksWeeklyCount1 =
                taskMapper.getTasksWeeklyCount(TaskStatus.ACCEPTED.getDbValue());
        map.put("本周已接受",tasksWeeklyCount1);
        Integer tasksMonthlyCount1 =
                taskMapper.getTasksMonthlyCount(TaskStatus.ACCEPTED.getDbValue());
        map.put("本月已接受",tasksMonthlyCount1);

        Integer tasksTodayCount =
                taskMapper.getTasksTodayCount(TaskStatus.ONGOING.getDbValue());
        map.put("今日已发布",tasksTodayCount+tasksTodayCount1);
        Integer tasksWeeklyCount =
                taskMapper.getTasksWeeklyCount(TaskStatus.ONGOING.getDbValue());
        map.put("本周已发布",tasksWeeklyCount+tasksWeeklyCount1);
        Integer tasksMonthlyCount =
                taskMapper.getTasksMonthlyCount(TaskStatus.ONGOING.getDbValue());
        map.put("本月已发布",tasksMonthlyCount+tasksMonthlyCount1);
        
        
        return map;
    }

    @Override
    public  List<Task> getTasksWithUser(Long userId) {
        Map<String,Task> map = new LinkedHashMap<String, Task>();
        // 设置查询条件
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        // 设置查询条件
        wrapper
                // 设置排序规则
                .orderByDesc("CreatedAt")
                // 设置查询条件
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
                .lt("CreatedAt",new Date(System.currentTimeMillis()))
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
        if (users == null  || !Objects.equals(users.getRole(), "user")){
            throw new MyException("用户不存在");
        }

        DelegationCategories delegationCategories = delegationCategoriesService.getTaskCategoryByCategoryName(taskDTO.getType());
        if (delegationCategories == null){
            throw new MyException("类别不存在");
        };


        Task task = Task.builder()
                .createdAt(new Date(System.currentTimeMillis()))
                .description(taskDTO.getContent())
                .ownerId(taskDTO.getOwnerId())
                .status(TaskStatus.DRAFT)
                .type(delegationCategories.getCategoryId())
                .location(taskDTO.getLocation())
                .build();
        taskMapper.insert(task);
    }


}