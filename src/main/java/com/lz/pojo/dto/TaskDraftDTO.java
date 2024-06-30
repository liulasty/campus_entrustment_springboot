package com.lz.pojo.dto;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/13/16:20
 * @Description:
 */

import lombok.Data;

import java.util.Date;

/**
 * 任务草案 DTO
 *
 * @author lz
 * @date 2024/04/13
 */
@Data
public class TaskDraftDTO {
    Long taskId;
    Long type;
    String location;
    String description;
    private Date createdAt;
}