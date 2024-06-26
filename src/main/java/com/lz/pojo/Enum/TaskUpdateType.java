package com.lz.pojo.Enum;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/11/15:22
 * @Description:
 */

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import lombok.Getter;

/**
 * 委托更新类型
 *
 * @author lz
 * @date 2024/04/11
 */
@Getter
@ApiModel(value = "委托更新类型枚举")
public enum TaskUpdateType {
    /**
     * 审核完成
     */
    AUDITING("AUDITING",0, "审核"),
   //发布委托
    PUBLISHED("PUBLISHED", 1,"发布委托"),
    
    /**
     * 新任务创建
     */
    CREATED("CREATED",2, "新委托创建"),
    
    // 委托结果
    RESULT("RESULT",3, "委托结果"),

    /**
     * 回退草稿
     */
    FALLBACK_DRAFT("FALLBACK_DRAFT",4, "回退草稿");
    


    
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
    
    private final Integer code;

    TaskUpdateType(String dbValue, Integer code, String webValue) {
        this.dbValue = dbValue;
        this.code = code;
        this.webValue = webValue;
    }
    
    public static TaskUpdateType fromDbValue(String dbValue) {
        for (TaskUpdateType value : values()) {
            if (value.getDbValue().equals(dbValue)) {
                return value;
            }
        }
        return null;
    }
    
    public static TaskUpdateType fromCode(Integer code) {
        for (TaskUpdateType value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
    
    public static TaskUpdateType fromWebValue(String webValue) {
        for (TaskUpdateType value : values()) {
            if (value.getWebValue().equals(webValue)) {
                return value;
            }
        }
        return null;
    }


    public static TaskUpdateType getByDbValue(String dbValue) {
        for (TaskUpdateType value : values()) {
            if (value.getDbValue().equals(dbValue)) {
                return value;
            }
        }
        return null;
    }
}