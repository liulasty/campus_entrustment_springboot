package com.lz.pojo.vo;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/13/18:56
 * @Description:
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 审计结果 VO
 *
 * @author lz
 * @date 2024/04/13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditResultVO {
    private String name;
    private String reviewStatus;
    private String reviewComment;
    private Date reviewTime;
}