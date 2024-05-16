package com.lz.pojo.Enum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * 任务状态枚举，代表任务的不同状态。
 * @author lz
 */
@Getter
@ApiModel(value = "任务状态类型枚举")
public enum TaskStatus {
    /**
     * 草稿
     */
    @ApiModelProperty(value = "草稿")
    DRAFT("DRAFT","草稿"),
    /**
     * 审核中
     */
    @ApiModelProperty(value = "审核中")
    AUDITING("AUDITING","审核中"),
    /**
     * 审核未通过
     */
    @ApiModelProperty(value = "未通过")
    AUDIT_FAILED("AUDIT_FAILED","审核未通过"),
    /**
     * 待发布
     */
    @ApiModelProperty(value = "等待发布")
    PENDING_RELEASE("PENDING_RELEASE","等待发布"),
    /**
     * 委托发布中
     */
    @ApiModelProperty(value = "委托发布中")
    ONGOING("ONGOING","委托发布中"),
    /**
     * 已接收
     */
    @ApiModelProperty(value = "已接收")
    ACCEPTED("ACCEPTED","已接收"),
    /**
     * 已完成
     */
    @ApiModelProperty(value = "已完成")
    COMPLETED("COMPLETED","已完成"),
    /**
     * 未完成
     */
    @ApiModelProperty(value = "未完成")
    UNFINISHED("INCOMPLETE","未完成"),
    /**
     * 已过期
     */
    @ApiModelProperty(value = "已过期")
    EXPIRED("EXPIRED","已过期"),
    /**
     * 取消发布
     */
    @ApiModelProperty(value = "已取消")
    CANCELLED("CANCELLED","已取消");
    
    

    
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
    public static List<TaskStatus> getStatusesForPhase(TaskPhase phase) {
        
        switch (phase) {
            case EDITING_AND_AUDITING:
                return Arrays.asList(DRAFT, AUDITING, AUDIT_FAILED, PENDING_RELEASE);
            case PUBLISHING_AND_EXECUTION:
                return Arrays.asList(ONGOING, ACCEPTED);
            case LIFECYCLE_TERMINATION:
                return Arrays.asList(EXPIRED, CANCELLED);
            default:
                throw new IllegalArgumentException("Unknown content lifecycle phase: " + phase);
        }
    }
    
    public static List<TaskStatus> getFallbackDraft(){
        return Arrays.asList( AUDIT_FAILED, PENDING_RELEASE);
    }
}