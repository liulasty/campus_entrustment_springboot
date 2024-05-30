package com.lz.pojo.Enum.handle;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/30/21:20
 * @Description:
 */

import com.lz.pojo.Enum.TaskPhase;
import com.lz.pojo.Enum.TaskStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author lz
 */
@MappedTypes(TaskPhase.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
@Slf4j
public class TaskPhaseTypeHandle extends BaseTypeHandler<TaskPhase> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, TaskPhase taskPhase, JdbcType jdbcType) throws SQLException {
        
        preparedStatement.setString(i, taskPhase.getTaskPhase());
    }

    @Override
    public TaskPhase getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String taskPhase = resultSet.getString(s);
        if (taskPhase != null) {
            return TaskPhase.fromValue(taskPhase);
        }
        return null;
    }

    @Override
    public TaskPhase getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String taskPhase = resultSet.getString(i);
        if (taskPhase != null) {
            return TaskPhase.fromValue(taskPhase);
        }
        return null;
    }

    @Override
    public TaskPhase getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        
        String taskPhase = callableStatement.getString(i);
        if (taskPhase != null) {
            return TaskPhase.fromValue(taskPhase);
        }
        return null;
    }
}