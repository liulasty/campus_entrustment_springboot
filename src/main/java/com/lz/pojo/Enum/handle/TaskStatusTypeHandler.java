package com.lz.pojo.Enum.handle;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/29/14:34
 * @Description:
 */

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
@MappedTypes(TaskStatus.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
@Slf4j
public class TaskStatusTypeHandler extends BaseTypeHandler<TaskStatus> {
    /**
     * 将非空的TaskStatus对象设置到PreparedStatement中。
     * 
     * @param ps PreparedStatement，用于设置参数。
     * @param i 参数在PreparedStatement中的位置。
     * @param parameter TaskStatus对象，待设置到PreparedStatement中的非空参数。
     * @param jdbcType JDBC类型，此处未使用。
     * @throws SQLException 如果设置参数时发生SQL异常。
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, TaskStatus parameter, JdbcType jdbcType) throws SQLException {
        log.info("setNonNullParameter");
        ps.setString(i, parameter.getDbValue());
    }

    /**
     * 从ResultSet中获取可能为null的TaskStatus对象。
     * 
     * @param rs ResultSet，从中获取数据。
     * @param columnName 列名称，根据此名称获取数据。
     * @return TaskStatus对象，如果对应列的值为null，则返回null。
     * @throws SQLException 如果从ResultSet中获取数据时发生SQL异常。
     */
    @Override
    public TaskStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        log.info("getNullableResult");
        String dbValue = rs.getString(columnName);
        return dbValue != null ? TaskStatus.fromDbValue(dbValue) : null;
    }

    /**
     * 从ResultSet中获取可能为null的TaskStatus对象。
     * 
     * @param rs ResultSet，从中获取数据。
     * @param columnIndex 列索引，根据此索引获取数据。
     * @return TaskStatus对象，如果对应列的值为null，则返回null。
     * @throws SQLException 如果从ResultSet中获取数据时发生SQL异常。
     */
    @Override
    public TaskStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        log.info("getNullableResult");
        String dbValue = rs.getString(columnIndex);
        return dbValue != null ? TaskStatus.fromDbValue(dbValue) : null;
    }

    /**
     * 从CallableStatement中获取可能为null的TaskStatus对象。
     * 
     * @param cs CallableStatement，从中获取数据。
     * @param columnIndex 列索引，根据此索引获取数据。
     * @return TaskStatus对象，如果对应列的值为null，则返回null。
     * @throws SQLException 如果从CallableStatement中获取数据时发生SQL异常。
     */
    @Override
    public TaskStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        log.info("getNullableResult");
        String dbValue = cs.getString(columnIndex);
        return dbValue != null ? TaskStatus.fromDbValue(dbValue) : null;
    }

}