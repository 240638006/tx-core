/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-7
 * <修改描述:>
 */
package com.tx.core.mybatis.model;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.mapping.MappedStatement;


 /**
  * 批量处理结果扩展类
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2012-12-7]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class BatchResultExt extends BatchResult{
    
    /** 是否成功 */
    private boolean isSuccess = false;
    
    /** 当前执行索引 */
    private int index = 0;
    
    /** 错误异常 */
    private Exception exception;
    
    
    

    public BatchResultExt(MappedStatement mappedStatement, String sql,
            Object parameterObject) {
        super(mappedStatement, sql, parameterObject);
    }
    
}
