/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-11-4
 * <修改描述:>
 */
package com.tx.core.mybatis.support;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

import com.tx.core.mybatis.model.Order;
import com.tx.core.paged.model.PagedList;

/**
 * <mybatis数据库查询类>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-11-4]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MyBatisDaoSupport {
    
    private SqlSessionTemplate sqlSessionTemplate;
    
    /**
      *<查询实体对象>
      *<功能详细描述>
      * @param statement
      * @param parameter
      * @return [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Object find(String statement, Object parameter) {
        if (parameter != null) {
            return this.sqlSessionTemplate.selectOne(statement, parameter);
        }
        else {
            return this.sqlSessionTemplate.selectOne(statement);
        }
    }
    
    /**
      *<查询实体对象数>
      *<功能详细描述>
      * @param statement
      * @param parameter
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int count(String statement, Object parameter) {
        if (parameter != null) {
            return (Integer) this.sqlSessionTemplate.selectOne(statement,
                    parameter);
        }
        else {
            return (Integer) this.sqlSessionTemplate.selectOne(statement);
        }
    }
    
    /**
      *<查询列表对象>
      *<功能详细描述>
      * @param statement
      * @param parameter
      * @return [参数说明]
      * 
      * @return List<?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<?> queryList(String statement, Object parameter) {
        if (parameter != null) {
            return this.sqlSessionTemplate.selectList(statement, parameter);
        }
        else {
            return this.sqlSessionTemplate.selectList(statement);
        }
    }
    
    /**
     *<查询列表对象>
     *<功能详细描述>
     * @param statement
     * @param parameter
     * @return [参数说明]
     * 
     * @return List<?> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public List<?> queryList(String statement, Map<String, Object> parameter,
            List<Order> orders) {
        if (orders != null && orders.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Order orderTemp : orders) {
                sb.append(orderTemp.toSqlString()).append(",");
            }
            if (sb.length() > 0) {
                String orderSql = sb.substring(0, sb.length() - 1);
                
                parameter.put("orderSql", orderSql);
            }
        }
        return queryList(statement, parameter);
    }
    
    /**
      *<查询分页对象>
      *<功能详细描述>
      * @param statement
      * @param parameter
      * @param pageIndex
      * @param pageSize
      * @param orders
      * @return [参数说明]
      * 
      * @return PagedList<?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public PagedList<?> queryPagedList(String statement,
            Map<String, Object> parameter, int pageIndex, int pageSize,
            List<Order> orders) {
        if (orders != null && orders.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Order orderTemp : orders) {
                sb.append(orderTemp.toSqlString()).append(",");
            }
            if (sb.length() > 0) {
                String orderSql = sb.substring(0, sb.length() - 1);
                
                parameter.put("orderSql", orderSql);
            }
        }
        return queryPagedList(statement, parameter, pageIndex, pageSize);
    }
    
    /**
     *<查询分页对象>
     *<功能详细描述>
     * @param statement
     * @param parameter
     * @param pageIndex
     * @param pageSize
     * @param orders
     * @return [参数说明]
     * 
     * @return PagedList<?> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public PagedList<?> queryPagedList(String statement,
            Map<String, Object> parameter, int pageIndex, int pageSize,
            int count, List<Order> orders) {
        if (orders != null && orders.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Order orderTemp : orders) {
                sb.append(orderTemp.toSqlString()).append(",");
            }
            if (sb.length() > 0) {
                String orderSql = sb.substring(0, sb.length() - 1);
                
                parameter.put("orderSql", orderSql);
            }
        }
        return queryPagedList(statement, parameter, pageIndex, pageSize, count);
    }
    
    /**
      *<查询分页对象>
      *<功能详细描述>
      * @param statement
      * @param parameter
      * @param pageIndex
      * @param pageSize
      * @return [参数说明]
      * 
      * @return PagedList<?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public PagedList<?> queryPagedList(String statement, Object parameter,
            int pageIndex, int pageSize) {
        @SuppressWarnings("rawtypes")
        PagedList<?> result = new PagedList();
        
        //构建Count查询列表中数目
        String queryCountStatement = statement + "Count";
        int count = (Integer) this.sqlSessionTemplate.selectOne(queryCountStatement,
                parameter);
        result.setCount(count);
        if (count <= 0) {
            return result;
        }
        int offset = pageSize * (pageIndex - 1);
        int limit = pageSize * pageIndex;
        List<?> list = this.sqlSessionTemplate.selectList(statement,
                parameter,
                new RowBounds(offset, limit));
        result.setList(list);
        return result;
    }
    
    /**
     *<查询分页对象,传入count>
     *<当count <= 0 或当前显示页已经是最后一页，则会调用对应count>
     * @param statement
     * @param parameter
     * @param pageIndex
     * @param pageSize
     * @return [参数说明]
     * 
     * @return PagedList<?> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public PagedList<?> queryPagedList(String statement, Object parameter,
            int pageIndex, int pageSize, int count) {
        @SuppressWarnings("rawtypes")
        PagedList<?> result = new PagedList();
        
        //构建Count查询列表中数目
        if (count <= 0 || pageIndex * pageSize >= count) {
            String queryCountStatement = statement + "Count";
            count = (Integer) this.sqlSessionTemplate.selectOne(queryCountStatement,
                    parameter);
        }
        result.setCount(count);
        if (count <= 0) {
            return result;
        }
        int offset = pageSize * (pageIndex - 1);
        int limit = pageSize * pageIndex;
        List<?> list = this.sqlSessionTemplate.selectList(statement,
                parameter,
                new RowBounds(offset, limit));
        result.setList(list);
        return result;
    }
    
    /**
     *<查询分页对象,传入count>
     *<当count <= 0 或当前显示页已经是最后一页，则会调用对应count>
     * @param statement
     * @param parameter
     * @param pageIndex
     * @param pageSize
     * @return [参数说明]
     * 
     * @return PagedList<?> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public PagedList<?> querySkipQueryCountPagedList(String statement,
            Object parameter, int pageIndex, int pageSize, int count) {
        @SuppressWarnings("rawtypes")
        PagedList<?> result = new PagedList();
        result.setCount(count);
        if (count <= 0) {
            return result;
        }
        int offset = pageSize * (pageIndex - 1);
        int limit = pageSize * pageIndex;
        List<?> list = this.sqlSessionTemplate.selectList(statement,
                parameter,
                new RowBounds(offset, limit));
        result.setList(list);
        return result;
    }
    
    /**
      *<查询列表，并映射为map>
      *<功能详细描述>
      * @param statement
      * @param parameter
      * @param mapKey
      * @return [参数说明]
      * 
      * @return Map<?,?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Map<?, ?> queryToMap(String statement, Object parameter,
            String mapKey) {
        if (parameter != null) {
            return this.sqlSessionTemplate.selectMap(statement,
                    parameter,
                    mapKey);
        }
        else {
            return this.sqlSessionTemplate.selectMap(statement, mapKey);
        }
    }
    
    /**
      *<查询列表，并映射为map>
      * @param statement
      * @param parameter
      * @param mapKey
      * @param pageIndex
      * @param pageSize
      * @return [参数说明]
      * 
      * @return Map<?,?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Map<?, ?> queryToMapByPage(String statement, Object parameter,
            String mapKey, int pageIndex, int pageSize) {
        int offset = pageSize * (pageIndex - 1);
        int limit = pageSize * pageIndex;
        
        return this.sqlSessionTemplate.selectMap(statement,
                parameter,
                mapKey,
                new RowBounds(offset, limit));
    }
    
    /**
      *<查询>
      *<功能详细描述>
      * @param statement
      * @param parameter
      * @param handler [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void queryByResultHandler(String statement, Object parameter,
            ResultHandler handler) {
        if (parameter != null) {
            this.sqlSessionTemplate.select(statement, parameter, handler);
        }
        else {
            this.sqlSessionTemplate.select(statement, handler);
        }
    }
    
    /**
      *<插入对象>
      *<功能详细描述>
      * @param statement
      * @param parameter [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void insert(String statement, Object parameter) {
        if (parameter != null) {
            this.sqlSessionTemplate.insert(statement, parameter);
        }
        else {
            this.sqlSessionTemplate.insert(statement);
        }
    }
    
    /**
      *<批量插入列表>
      *<功能详细描述>
      * @param statement
      * @param objectList [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //批量插入
    public void batchInsert(String statement, Collection<?> objectList) {
        if (objectList == null) {
            return;
        }
        
        this.sqlSessionTemplate.getSqlSessionFactory()
                .openSession(ExecutorType.BATCH, true);
        for (Object objectTemp : objectList) {
            this.sqlSessionTemplate.insert(statement, objectTemp);
        }
        this.sqlSessionTemplate.flushStatements();
    }
    
    /**
      *<更新对象>
      *<功能详细描述>
      * @param statement
      * @param parameter
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int update(String statement, Object parameter) {
        if (parameter != null) {
            return this.sqlSessionTemplate.update(statement, parameter);
        }
        else {
            return this.sqlSessionTemplate.update(statement);
        }
    }
    
    /**
      *<增加或删除对象，先查询对应对象是否存在，如果存在，则执行更新操作，如果不存在，执行插入操作>
      *<功能详细描述>
      * @param findStatement 查询所用到的查询statement
      * @param insertStatement 插入所用到的查询statement
      * @param updateStatement 更新所用到的查询statement
      * @param parameter [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void save(String findStatement, String insertStatement,
            String updateStatement, Object parameter) {
        Object resObj = find(findStatement, parameter);
        if (resObj == null) {
            insert(insertStatement, parameter);
        }
        else {
            update(updateStatement, parameter);
        }
    }
    
    /**
      *<批量更新>
      *<功能详细描述>
      * @param statement
      * @param objectList
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int batchUpdate(String statement, Collection<?> objectList) {
        if (CollectionUtils.isEmpty(objectList)) {
            return 0;
        }
        
        //批量更新
        this.sqlSessionTemplate.getSqlSessionFactory()
                .openSession(ExecutorType.BATCH, true);
        int resultCount = 0;
        for (Object objTemp : objectList) {
            this.sqlSessionTemplate.update(statement, objTemp);
            resultCount++;
        }
        this.sqlSessionTemplate.flushStatements();
        return resultCount;
    }
    
    /**
      *<查询>
      *<功能详细描述>
      * @param statement
      * @param parameter
      * @param rowBounds
      * @param handler [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void query(String statement, Object parameter, RowBounds rowBounds,
            ResultHandler handler) {
        this.sqlSessionTemplate.select(statement, parameter, rowBounds, handler);
    }
    
    /**=
      *<删除对象>
      *<功能详细描述>
      * @param statement
      * @param parameter [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int delete(String statement, Object parameter) {
        if (parameter != null) {
            return this.sqlSessionTemplate.delete(statement, parameter);
        }
        else {
            return this.sqlSessionTemplate.delete(statement);
        }
    }
    
    /**
      *<获取sqlSessionFactory>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return SqlSessionFactory [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public SqlSessionFactory getSqlSessionFactory() {
        return this.sqlSessionTemplate.getSqlSessionFactory();
    }
    
    /**
     * @return 返回 sqlSessionTemplate
     */
    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }
    
    /**
     * @param 对sqlSessionTemplate进行赋值
     */
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
}
