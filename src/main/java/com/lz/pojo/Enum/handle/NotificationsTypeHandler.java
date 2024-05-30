package com.lz.pojo.Enum.handle;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/30/22:54
 * @Description:
 */

import com.lz.pojo.Enum.NotificationsType;
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
@MappedTypes(NotificationsType.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
@Slf4j
public class NotificationsTypeHandler extends BaseTypeHandler<NotificationsType> {
    public void setNonNullParameter(PreparedStatement ps, int i, NotificationsType parameter, JdbcType jdbcType) throws java.sql.SQLException {
        ps.setString(i, parameter.getDbValue());
    }
    public NotificationsType getNullableResult(ResultSet rs, String columnName) throws java.sql.SQLException {
        
        return NotificationsType.fromDbValue(rs.getString(columnName));
    } 
    
    public NotificationsType getNullableResult(ResultSet rs, int columnIndex) throws java.sql.SQLException {
        
        return NotificationsType.fromDbValue(rs.getString(columnIndex));
    }
    public NotificationsType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        
        return NotificationsType.fromDbValue(cs.getString(columnIndex));
    }
    
}