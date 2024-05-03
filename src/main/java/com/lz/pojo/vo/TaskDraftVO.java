package com.lz.pojo.vo;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/13/10:33
 * @Description:
 */

import com.lz.pojo.Enum.TaskStatus;
import lombok.Data;

import java.util.Date;

/**
 * 委托草稿 VO
 *
 * @author lz
 * @date 2024/04/13
 */
@Data
public class TaskDraftVO {
    Long taskId;
    String location;
    String description;
    String type;
    Date createdAt;
    TaskStatus status;
}