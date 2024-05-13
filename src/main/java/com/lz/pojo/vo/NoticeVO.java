package com.lz.pojo.vo;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/13/18:30
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
public class NoticeVO {
    private Long id;
    private String title;
    private String description;
    private Date date;
}