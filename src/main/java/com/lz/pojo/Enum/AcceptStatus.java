package com.lz.pojo.Enum;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/03/14:41
 * @Description:
 */

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 接受状态
 *
 * @author lz
 * @date 2024/05/03
 */
@Getter
public enum AcceptStatus {

    /**
     * 未选中
     */
    UNCHECKED("Unchecked", "未选中"),
    /**
     * 已接收
     */
    CHECKED("Checked", "已接收"),
    /**
     * 过期
     */
    EXPIRED("Expired", "已过期"),


    /**
     * 待定
     */
    PENDING("Pending", "待处理"),

    /**
     * 取消接收
     */
    CANCEL("Cancel","已取消");


    @EnumValue
    private final String dbValue;
    @JsonValue
    private final String webValue;

    AcceptStatus(String dbValue, String webValue) {
        this.dbValue = dbValue;
        this.webValue = webValue;

    }
}