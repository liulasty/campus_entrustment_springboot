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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    AUDITING("AUDITING", "审核"),
   //发布委托
    PUBLISHED("PUBLISHED", "发布委托"),
    
    /**
     * 新任务创建
     */
    CREATED("CREATED", "新委托创建"),
    
    // 委托结果
    RESULT("RESULT", "委托结果"),

    /**
     * 回退草稿
     */
    FALLBACK_DRAFT("FALLBACK_DRAFT", "回退草稿");
    


    
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

    TaskUpdateType(String dbValue, String webValue) {
        this.dbValue = dbValue;
        this.webValue = webValue;
    }


}