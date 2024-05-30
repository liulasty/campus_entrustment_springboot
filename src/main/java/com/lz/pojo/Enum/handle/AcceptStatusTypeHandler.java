package com.lz.pojo.Enum.handle;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/05/30/21:28
 * @Description:
 */

import com.lz.pojo.Enum.AcceptStatus;
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
@MappedTypes(AcceptStatus.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
@Slf4j
public class AcceptStatusTypeHandler extends BaseTypeHandler<AcceptStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, AcceptStatus acceptStatus, JdbcType jdbcType) throws SQLException {
        
        preparedStatement.setString(i, acceptStatus.getDbValue());
    }

    @Override
    public AcceptStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        if (resultSet.getString(s) != null) {
            return AcceptStatus.fromDbValue(resultSet.getString(s));
        }
        log.info("AcceptStatusTypeHandler:{}", resultSet.getString(s));
        return null;
    }

    @Override
    public AcceptStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        if (resultSet.getString(i) != null) {
            return AcceptStatus.fromDbValue(resultSet.getString(i));
        }
        log.info("AcceptStatusTypeHandler:{}", resultSet.getString(i));
        return null;
    }

    @Override
    public AcceptStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        
        
        if (callableStatement.getString(i) != null) {
            return AcceptStatus.fromDbValue(callableStatement.getString(i));
        }
        log.info("AcceptStatusTypeHandler:{}", callableStatement.getString(i));
        return null;
    }
}