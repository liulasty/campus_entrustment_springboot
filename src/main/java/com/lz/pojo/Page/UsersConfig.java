package com.lz.pojo.Page;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/15/1:32
 * @Description:
 */

import lombok.Builder;
import lombok.Data;

/**
 * 用户配置
 *
 * @author lz
 * @date 2024/04/15
 */
@Data
@Builder
public class UsersConfig {
    private String username;
    private String email;
    private Boolean isActive;
    private Long size;
    private Long page;
}