package com.lz.pojo.Enum.handle;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/30/22:47
 * @Description:
 */

import com.lz.pojo.Enum.TaskUpdateType;
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
@MappedTypes(TaskUpdateType.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
@Slf4j
public class TaskUpdateTypeHandler extends BaseTypeHandler<TaskUpdateType> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, TaskUpdateType taskUpdateType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, taskUpdateType.getDbValue());
    }
    @Override
    public TaskUpdateType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String dbValue = resultSet.getString(s);
        if (dbValue == null) {
            return null;
        }
        return TaskUpdateType.getByDbValue(dbValue);
    }
    @Override
    public TaskUpdateType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String dbValue = resultSet.getString(i);
        if (dbValue == null) {
            return null;
        }
        return TaskUpdateType.getByDbValue(dbValue);
    }
    @Override
    public TaskUpdateType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String dbValue = callableStatement.getString(i);
        if (dbValue == null) {
            return null;
        }
        return TaskUpdateType.getByDbValue(dbValue);
    }
}