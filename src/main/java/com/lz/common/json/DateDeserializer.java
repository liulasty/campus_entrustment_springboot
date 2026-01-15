package com.lz.common.json;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/10/17:18
 * @Description:
 */

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 反序列化
 *
 * @author lz
 * @date 2024/05/10
 */
public  class DateDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String dateString = jsonParser.getText();
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }

        // 1. 尝试解析为时间戳 (Long)
        try {
            long timestamp = Long.parseLong(dateString);
            return new Date(timestamp);
        } catch (NumberFormatException e) {
            // 忽略，继续尝试其他格式
        }

        // 2. 尝试解析 yyyy-MM-dd
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            // 忽略，继续尝试其他格式
        }

        // 3. 尝试解析 yyyy-MM-dd HH:mm:ss
        try {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateTimeFormat.parse(dateString);
        } catch (ParseException e) {
            throw new IOException("Failed to deserialize Date: " + dateString, e);
        }
    }
}