/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-11-5
 * <修改描述:>
 */
package com.tx.core.mybatis.interceptor;

import java.util.Properties;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.RowBounds;
import org.hibernate.dialect.Dialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    
    private Logger logger = LoggerFactory.getLogger(PagedDiclectStatementHandlerInterceptor.class);
    
    private Dialect dialect;
    
    /**
     * @param invocation
     * @return
     * @throws Throwable
     */
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler);
        
        RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
        if (rowBounds == null
                || rowBounds.equals(RowBounds.DEFAULT)
                || (rowBounds.getLimit() == RowBounds.NO_ROW_LIMIT 
                    && rowBounds.getOffset() == RowBounds.NO_ROW_OFFSET)) {
            return invocation.proceed();
        }
        
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        metaStatementHandler.setValue("delegate.boundSql.sql",
                dialect.getLimitString(sql,
                        rowBounds.getOffset(),
                        rowBounds.getLimit()));
        metaStatementHandler.setValue("delegate.rowBounds.offset",
                RowBounds.NO_ROW_OFFSET);
        metaStatementHandler.setValue("delegate.rowBounds.limit",
                RowBounds.NO_ROW_LIMIT);
        
        if (logger.isDebugEnabled()) {
            logger.debug("生成分页SQL : " + boundSql.getSql());
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
