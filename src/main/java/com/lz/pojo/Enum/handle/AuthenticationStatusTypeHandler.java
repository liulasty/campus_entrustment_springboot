package com.lz.pojo.Enum.handle;


import com.lz.pojo.Enum.AuthenticationStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * AuthenticationStatus TypeHandler
 *
 * @author lz
 * @since 2024/05/03
 */
@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes(AuthenticationStatus.class)
public class AuthenticationStatusTypeHandler extends BaseTypeHandler<AuthenticationStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, AuthenticationStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getDbValue());
    }

    @Override
    public AuthenticationStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return getAuthenticationStatusByCode(code);
    }

    @Override
    public AuthenticationStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return getAuthenticationStatusByCode(code);
    }

    @Override
    public AuthenticationStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return getAuthenticationStatusByCode(code);
    }

    private AuthenticationStatus getAuthenticationStatusByCode(int code) {
        for (AuthenticationStatus status : AuthenticationStatus.values()) {
            if (status.getDbValue() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid code for AuthenticationStatus: " + code);
    }
}