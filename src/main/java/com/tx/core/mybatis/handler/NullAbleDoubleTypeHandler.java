/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-7
 * <修改描述:>
 */
package com.tx.core.mybatis.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2012-12-7]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@MappedTypes(value = { Double.class })
public class NullAbleDoubleTypeHandler extends BaseTypeHandler<Double> {
    
    /**
     * @param ps
     * @param i
     * @param parameter
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setParameter(PreparedStatement ps, int i, Double parameter,
            JdbcType jdbcType) throws SQLException {
        if (parameter == null
                && (jdbcType == null || JdbcType.OTHER == jdbcType)) {
            ps.setNull(i, JdbcType.DOUBLE.TYPE_CODE);
        } else {
            super.setParameter(ps, i, parameter, jdbcType);
        }
    }
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
            Double parameter, JdbcType jdbcType) throws SQLException {
        ps.setDouble(i, parameter);
    }
    
    @Override
    public Double getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        return rs.getDouble(columnName);
    }
    
    @Override
    public Double getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        return rs.getDouble(columnIndex);
    }
    
    @Override
    public Double getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return cs.getDouble(columnIndex);
    }
    
}
