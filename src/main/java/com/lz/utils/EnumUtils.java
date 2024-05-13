package com.lz.utils;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/10/15:51
 * @Description:
 */

import com.lz.pojo.Enum.NotificationsType;
import com.lz.pojo.Enum.TaskUpdateType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lz
 */
public class EnumUtils {
    public static Map<String, String> generateKeyValues(NotificationsType[] enums) {
        Map<String, String> keyValueMap = new HashMap<>();
        for (NotificationsType type : enums) {
            keyValueMap.put(type.getDbValue(), type.getWebValue());
        }
        return keyValueMap;
    }

    public static Map<String, String> generateKeyValues(TaskUpdateType[] enums) {
        Map<String, String> keyValueMap = new HashMap<>();
        for (TaskUpdateType type : enums) {
            keyValueMap.put(type.getDbValue(), type.getWebValue());
        }
        return keyValueMap;
    }

    public static void main(String[] args) {
        Map<String, String> taskUpdateTypes = generateKeyValues(TaskUpdateType.values());
        for (Map.Entry<String, String> entry : taskUpdateTypes.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }
}