package com.lz.pojo.vo;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/04/17:50
 * @Description:
 */

import com.lz.pojo.Enum.AcceptStatus;
import com.lz.pojo.Enum.TaskStatus;
import lombok.Data;

import java.util.Date;

/**
 * 接收记录
 * @author lz
 */
@Data
public class TaskAcceptRecord {
    /**
     * 接收记录
     */
    private Long id;
    /**
     * 委托编号
     */
    private Long taskId;
    /**
     * 接收委托者编号
     */
    private Long acceptorId;
    /**
     * 委托留言日期
     */
    private Date acceptTime;
    /**
     * 委托状态
     */
    private TaskStatus taskStatus;
    /**
     * 委托类型
     */
    private Long taskType;

    /**
     * 委托留言
     */
    private String str;
    /**
     * 描述
     */
    private String description;
    /**
     * 位置
     */
    private String location;
    /**
     * 接收状态
     */
    private AcceptStatus status;
}