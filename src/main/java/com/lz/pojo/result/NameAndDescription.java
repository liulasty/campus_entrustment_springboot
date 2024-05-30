package com.lz.pojo.result;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/12/22:22
 * @Description:
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 名称和描述
 *
 * @author lz
 * @date 2024/04/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NameAndDescription {
    private Long id;
    private String name;
    private String description;
}