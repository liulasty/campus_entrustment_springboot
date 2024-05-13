package com.lz.pojo.vo;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/13/17:45
 * @Description:
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author lz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeItemVO {
    private Long id;
    private String title;
    private Boolean isRead;
    private Date date; 
    private String description;
}