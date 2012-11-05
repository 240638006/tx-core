/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-11-5
 * <修改描述:>
 */
package com.tx.core.mybatis.interceptor;

import java.util.Properties;

import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.session.RowBounds;
import org.hibernate.dialect.Dialect;

import com.tx.core.util.ReflectionUtils;

/**
 * <数据库分页容器处理器>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-11-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PagedDiclectStatementHandlerInterceptor implements Interceptor {
    
    private Dialect dialect;
    
    /**
     * @param invocation
     * @return
     * @throws Throwable
     */
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler statement = (RoutingStatementHandler) invocation.getTarget();
        PreparedStatementHandler handler = (PreparedStatementHandler) ReflectionUtils.getFieldValue(statement,
                "delegate");
        RowBounds rowBounds = (RowBounds) ReflectionUtils.getFieldValue(handler,
                "rowBounds");
        
        if (rowBounds.getLimit() > 0
                && rowBounds.getLimit() < RowBounds.NO_ROW_LIMIT) {
            BoundSql boundSql = statement.getBoundSql();
            String sql = boundSql.getSql();
            
            sql = dialect.getLimitString(sql,
                    rowBounds.getOffset(),
                    rowBounds.getLimit());
            
            ReflectionUtils.setFieldValue(boundSql, "sql", sql);
        }
        return invocation.proceed();
    }
    
    /**
     * @param target
     * @return
     */
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
    
    /**
     * @param properties
     */
    public void setProperties(Properties properties) {
        
    }
    
    /**
     * @return 返回 dialect
     */
    public Dialect getDialect() {
        return dialect;
    }
    
    /**
     * @param 对dialect进行赋值
     */
    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }
}
