package com.lz.pojo.dto;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/29/15:45
 * @Description:
 */

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 发布 DTO
 *
 * @author lz
 * @date 2024/04/29
 */
@Data
public class PublishDTO {
    private Long id;
    /**
     * 
     */
    private Date start;
    private Date end;
}