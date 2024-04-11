package com.lz.pojo.dto;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/11/14:42
 * @Description:
 */

import lombok.Data;

/**
 * 委托计数 DTO
 *
 * @author lz
 * @date 2024/04/11
 */
@Data
public class TaskCountDTO {
    private Long taskTypeId;
    private Long typeCount;
}