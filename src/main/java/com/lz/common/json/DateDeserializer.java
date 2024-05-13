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
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String dateString = jsonParser.getText();

        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new IOException("Failed to deserialize Date", e);
        }
    }
}