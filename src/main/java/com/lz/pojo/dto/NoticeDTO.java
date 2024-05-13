package com.lz.pojo.dto;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/13/17:07
 * @Description:
 */

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author lz
 */
@Data
@ApiModel(value = "用户查询通知消息对象", description = "添加通知消息时对象")
public class NoticeDTO {
    Long userId;
    String type;
}