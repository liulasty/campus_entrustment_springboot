package com.lz.pojo.vo;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/12/22:17
 * @Description:
 */

import com.lz.pojo.result.NameAndDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 委托类型 VO
 *
 * @author lz
 * @date 2024/04/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskTypeVO {
   List<NameAndDescription> taskTypeCount;
}