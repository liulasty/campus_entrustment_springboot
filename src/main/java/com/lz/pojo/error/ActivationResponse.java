package com.lz.pojo.error;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/04/23:04
 * @Description:
 */

/**
 * @author lz
 */
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivationResponse {

    @JsonProperty("status")
    private int statusCode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("success")
    private boolean success;

   
}