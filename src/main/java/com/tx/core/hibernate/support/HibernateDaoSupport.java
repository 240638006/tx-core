/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-11-5
 * <修改描述:>
 */
package com.tx.core.hibernate.support;

import java.util.Collection;
import java.util.List;

import org.apache.cxf.common.util.StringUtils;
import org.hibernate.LockMode;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2012-11-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class HibernateDaoSupport {
    
    private HibernateTemplate hibernateTemplate;
    
    /**
      *<查询实体对象>
      *<功能详细描述>
      * @param queryClass
      * @param id
      * @param lockMode 可以为空
      * @return [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Object find(Class<?> queryClass, String id, LockMode lockMode) {
        if (lockMode == null) {
            return this.hibernateTemplate.get(queryClass, id);
        } else {
            return this.hibernateTemplate.get(queryClass, id, lockMode);
        }
    }
    
    /**
     *<查询实体对象>
     *<功能详细描述>
     * @param queryClass
     * @param id
     * @param lockMode
     * @return [参数说明]
     * 
     * @return Object [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public Object find(String entityName, String id, LockMode lockMode) {
        if (lockMode == null) {
            return this.hibernateTemplate.get(entityName, id);
        } else {
            return this.hibernateTemplate.get(entityName, id, lockMode);
        }
    }
    
    /**
      *<根据hql查询列表>
      *<功能详细描述>
      * @param queryString
      * @param values [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<?> queryByHql(String queryHql,Object... values){
        if(values == null){
            return this.hibernateTemplate.find(queryHql);
        }else{
            return this.hibernateTemplate.find(queryHql, values);
        }
    }
    
    /**
      *<功能简述>
      *<功能详细描述>
      * @param querySql
      * @return [参数说明]
      * 
      * @return List<?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<?> queryBySql(String querySql){
        
        return null;
    }
    
    
    /**
      *<插入对象>
      *<功能详细描述>
      * @param obj
      * @param entityName [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void insert(Object entity, String entityName) {
        if (StringUtils.isEmpty(entityName)) {
            this.hibernateTemplate.save(entity);
        } else {
            this.hibernateTemplate.save(entityName, entity);
        }
    }
    
    /**
      *<insert or update插入或保存对象>
      *<功能详细描述>
      * @param entity
      * @param entityName [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void save(Object entity, String entityName) {
        if (StringUtils.isEmpty(entityName)) {
            this.hibernateTemplate.saveOrUpdate(entity);
        } else {
            this.hibernateTemplate.saveOrUpdate(entityName, entity);
        }
    }
    
    /**
      *<批量插入>
      *<功能详细描述>
      * @param collection [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void batchInsert(Collection<?> collection) {
        if (collection == null) {
            return;
        }
        for (Object objTemp : collection){
            this.hibernateTemplate.save(objTemp);
        }
    }
    
    public static void main(String[] args) {
        System.out.println(args[0]);
    }
    
}
