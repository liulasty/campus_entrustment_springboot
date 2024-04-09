package com.lz.utils;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/05/7:27
 * @Description:
 */

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 验证 util
 *
 * @author lz
 * @date 2024/04/06
 */
public class ValidateUtil {
    public static String validate(BindingResult result) {
        // 异常防护：检查result是否为null
        if (result == null) {
            throw new IllegalArgumentException("BindingResult 不能为 null");
        }

        List<FieldError> fieldErrors = result.getFieldErrors();

        // 添加分隔符：使用 Collectors.joining() 方法拼接错误消息，以换行符分隔
        String errorMessages = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("\n"));

        if (!fieldErrors.isEmpty()) {
            return errorMessages;
        }
        return null;
    }
}