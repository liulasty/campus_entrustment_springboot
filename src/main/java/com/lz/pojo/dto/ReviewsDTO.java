package com.lz.pojo.dto;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/21/15:37
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
@ApiModel(value = "用户查询评论消息对象", description = "添加评论消息时对象")
@AllArgsConstructor
@NoArgsConstructor
public class ReviewsDTO {
    Long taskId;
    Long rate;
    String comment;
}