package com.lz.pojo.Enum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * 任务状态枚举，代表任务的不同状态。
 * @author lz
 */
@Getter
@ApiModel(value = "任务状态枚举")
public enum TaskStatus {
    /**
     * 审核中
     */
    @ApiModelProperty(value = "审核中")
    AUDITING("pending","审核中"),
    /**
     * 待处理
     */
    PENDING("pending","待处理"),
    /**
     * 进行中
     */
    ONGOING("Ongoing","进行中"),
    /**
     * 已完成
     */
    COMPLETED("Completed","已完成");

    
    /**
     * 数据库字符串表示。
     */
    @EnumValue
    private final String dbValue;

    /**
     * WEB字符串表示。
     */
    @JsonValue
    private final String webValue;

    /**
     * 构造函数，初始化任务状态及其对应的数据库值。
     * @param dbValue 状态在数据库中的字符串表示。
     * @param webValue 状态在Web应用中的字符串表示。
     */
    TaskStatus(String dbValue,String webValue) {
        
        this.dbValue = dbValue;
        this.webValue = webValue;
    }

    /**
     * 将从数据库获取的字符串值转换为相应的{@link TaskStatus}枚举实例。
     * 如果给定值与任何已知状态不匹配，则抛出{@link IllegalArgumentException}。
     *
     * @param dbValue 从数据库检索到的字符串值。
     * @return 对应的{@link TaskStatus}实例。
     * @throws IllegalArgumentException 如果给定的数据库值无效，则抛出此异常。
     */
    public static TaskStatus fromDbValue(String dbValue) {
        for (TaskStatus status : values()) {
            if (status.getDbValue().equalsIgnoreCase(dbValue)) {
                return status;
            }
        }
        throw new IllegalArgumentException("无效的任务状态值: " + dbValue);
    }
}