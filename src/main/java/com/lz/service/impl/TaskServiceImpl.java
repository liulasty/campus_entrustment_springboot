package com.lz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.mapper.ReviewsMapper;
import com.lz.mapper.UsersMapper;
import com.lz.pojo.Enum.AnnouncementStatus;
import com.lz.pojo.Enum.TaskStatus;
import com.lz.pojo.dto.TaskCountDTO;
import com.lz.pojo.dto.TaskPageDTO;
import com.lz.pojo.entity.SystemAnnouncements;
import com.lz.pojo.entity.Task;
import com.lz.mapper.TaskMapper;
import com.lz.pojo.result.PageResult;
import com.lz.service.IDelegationCategoriesService;
import com.lz.service.ITaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
@Transactional
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

    @Autowired
    private IDelegationCategoriesService delegationCategoriesService;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private TaskMapper taskMapper;
    

    @Override
    public void addTask() {

    }

    @Override
    public void updateTask() {

    }

    @Override
    public void deleteTask() {

    }

    @Override
    public void searchTask(Long id) {
        Task task = taskMapper.selectById(id);
        System.out.println(task);
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


}