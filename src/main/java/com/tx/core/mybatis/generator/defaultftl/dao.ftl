/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package ${dao.basePackage}.dao;

import java.util.List;
import java.util.Map;

import com.tx.core.mybatis.model.Order;
import com.tx.core.paged.model.PagedList;
import ${dao.entityTypeName};

/**
 * demo持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ${dao.simpleEntityTypeName}Dao {
    
    /**
      * 插入${dao.simpleEntityTypeName}对象实体
      * 1、auto generate
      * <功能详细描述>
      * @param ${dao.lownCaseSimpleEntityTypeName} [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public void insert${dao.simpleEntityTypeName}(${dao.simpleEntityTypeName} ${dao.lownCaseSimpleEntityTypeName});
    
    /**
      * 删除demo对象
      * 1、auto generate
      * 2、生成逻辑为根据主键进行删除
      * <功能详细描述>
      * @param condition [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public int deleteDemo(Demo condition);
    
    /**
      * 查询demo实体
      * <功能详细描述>
      * @param condition
      * @return [参数说明]
      * 
      * @return Demo [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public Demo findDemo(Demo condition);
    
    /**
      * 根据条件查询demo列表
      * <功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return List<Demo> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public List<Demo> query${dao.simpleEntityTypeName}List(Map<String, Object> params);
    
    /**
      * 根据指定查询条件以及排序列表查询demo列表
      * <功能详细描述>
      * @param params
      * @param orderList
      * @return [参数说明]
      * 
      * @return List<Demo> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public List<Demo> query${dao.simpleEntityTypeName}List(Map<String, Object> params,
            List<Order> orderList);
    
    /**
      * 根据条件查询demo列表数
      * <功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int count${dao.simpleEntityTypeName}(Map<String, Object> params);
    
    /**
      * 分页查询demo列表
      * <功能详细描述>
      * @param params
      * @param pageIndex
      * @param pageSize
      * @return [参数说明]
      * 
      * @return PagedList<Demo> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public PagedList<${dao.simpleEntityTypeName}> query${dao.simpleEntityTypeName}PagedList(Map<String, Object> params,
            int pageIndex, int pageSize);
    
    /**
      * 分页查询demo列表，传入排序字段
      *<功能详细描述>
      * @param params
      * @param pageIndex
      * @param pageSize
      * @param orderList
      * @return [参数说明]
      * 
      * @return PagedList<Demo> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public PagedList<${dao.simpleEntityTypeName}> query${dao.simpleEntityTypeName}PagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList);
    
    
    /**
      * 更新demo实体，
      * 1、传入demo中主键不能为空
      * <功能详细描述>
      * @param updateDemoRowMap
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int update${dao.simpleEntityTypeName}(Map<String, Object> updateRowMap);
}
