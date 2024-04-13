package com.lz.pojo.vo;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/13/0:48
 * @Description:
 */

import com.lz.pojo.Enum.TaskStatus;
import lombok.Data;

import java.util.Date;

/**
 * @author lz
 */
@Data
public class UserDelegateDraft {
    private Long taskId;
    /**
     * 类别
     */
    private Integer type;
    /**
     * 位置
     */
    private String location;
    /**
     * 描述
     */
    private String description;
    /**
     * 状态
     */
    private TaskStatus status;
    /**
     * 创建时间
     */
    private Date createTime;
}