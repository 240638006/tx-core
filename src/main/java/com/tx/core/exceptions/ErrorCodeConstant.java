/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-14
 * <修改描述:>
 */
package com.tx.core.exceptions;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2012-10-14]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface ErrorCodeConstant {
    
    /** 参数异常  */
    String PARAMETER_EXCEPTION = "000-000-001-000";
    
    /** 参数不能为空  */
    String PARAMETER_IS_EMPTY = "000-000-001-001";
    
    /** 参数无效  */
    String PARAMETER_IS_INVALID = "000-000-001-002";
    
    /** 资源加载异常 */
    String RESOURCE_LOAD_EXCEPTION = "000-000-002-000";
    
}
