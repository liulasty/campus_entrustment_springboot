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
    UNCHECKED("Unchecked",0, "未选中"),
    /**
     * 已接收
     */
    CHECKED("Checked",1, "已接收"),
    /**
     * 过期
     */
    EXPIRED("Expired",2, "已过期"),


    /**
     * 待定
     */
    PENDING("Pending",3, "待处理"),

    /**
     * 取消接收
     */
    CANCEL("Cancel",4,"已取消");


    @EnumValue
    private final String dbValue;
    @JsonValue
    private final String webValue;
    
    private final Integer code;

    AcceptStatus(String dbValue, Integer code,String webValue ) {
        this.dbValue = dbValue;
        this.code = code;
        this.webValue = webValue;

    }
    public static AcceptStatus fromDbValue(String dbValue) {
        for (AcceptStatus status : AcceptStatus.values()) {
            if (status.getDbValue().equals(dbValue)) {
                return status;
            }
        }
        return null;
    }
}