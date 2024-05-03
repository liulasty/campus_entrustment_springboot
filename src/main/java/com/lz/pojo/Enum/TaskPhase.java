package com.lz.pojo.Enum;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/21/19:37
 * @Description:
 */

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.Getter;

/**
 * 委托任务阶段
 *
 * @author lz
 * @date 2024/04/21
 */
@Getter
public enum TaskPhase {
    /**
     * 编辑和审核
     */
    EDITING_AND_AUDITING( "EDITING_AND_AUDITING",1),
    /**
     * 发布和执行
     */
    PUBLISHING_AND_EXECUTION( "PUBLISHING_AND_EXECUTION",2),
    /**
     * 生命周期终止
     */
    LIFECYCLE_TERMINATION( "LIFECYCLE_TERMINATION",3);
    /**
     * 任务阶段
     */
    
    private final String taskPhase;
    
    private final int value;
    
    TaskPhase(String taskPhase,int value) {
        this.taskPhase = taskPhase;
        this.value = value;
    }
    
    public static TaskPhase fromValue(String Value) {
        for (TaskPhase taskPhase : TaskPhase.values()) {
            if (taskPhase.taskPhase.equals(Value)) {
                return taskPhase;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + Value);
    }
    

}