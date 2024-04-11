package com.lz.pojo.vo;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/10/23:31
 * @Description:
 */

import com.lz.Annotation.HaveNoBlank;
import com.lz.pojo.dto.TaskDTO;
import com.lz.pojo.entity.SystemAnnouncements;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * 最新资讯 DTO
 *
 * @author lz
 * @date 2024/04/10
 */
@Data
@ApiModel(value="最新资讯对象",description = "首页展示的数据模型")
public class NewestInfoVO {
    /**
     * 最新系统公告
     */
    List<SystemAnnouncements> systemAnnouncements;
    /**
     * 最新委托
     */
    List<TaskVO> newestTask;
    /**
     * 热门类别
     */
    List<String> hotTaskCategory;
    
    /**
     * 委托统计
     */
    List<Integer> transactionStats;
    
    /**
     * 与我相关的委托
     */
    List<TaskVO> tasksWithUser;
    
}