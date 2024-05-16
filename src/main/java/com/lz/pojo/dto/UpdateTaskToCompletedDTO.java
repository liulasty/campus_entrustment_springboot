package com.lz.pojo.dto;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/17/2:31
 * @Description:
 */

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="委托信息评分提交对象",description = "委托信息评分提交传递的数据模型")
public class UpdateTaskToCompletedDTO {
    private Long taskId;
    private Long taskAccomplishGrade;
    private String taskAccomplishReview;
}