package com.lz.pojo.vo;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/08/16:01
 * @Description:
 */

import com.lz.pojo.entity.TaskAcceptRecords;
import com.lz.pojo.entity.UsersInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskAcceptRecordVO {
    TaskAcceptRecords taskAcceptRecords;
    Long UserId;
    String UserName;
    String UserType;
}