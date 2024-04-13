package com.lz.pojo.dto;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/13/17:54
 * @Description:
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 审核结果结果 DTO
 *
 * @author lz
 * @date 2024/04/13
 */
@Data
@ApiModel(value = "审核结果结果对象", description = "审核结果结果对象")
public class AuditResultDTO {
    /**
     * 审核记录ID
     */
    @ApiModelProperty(value = "审核记录ID")
    Long delegateId;
    /**
     * 管理员ID
     */
    @ApiModelProperty(value = "管理员ID")
    Long userId;
    /**
     * 审核结果
     */
    @ApiModelProperty(value = "审核结果")
    String reviewStatus;
    /**
     * 审核意见
     */
    @ApiModelProperty(value = "审核意见")
    String reviewComment;
}