/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-9
 * <修改描述:>
 */
package com.tx.core.mybatis.generator.model;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2012-12-9]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class DeleteMapper {
    
    private String id;
    
    private String parameterType;
    
    private String tableName;
    
    private String simpleTableName;
    
    private String idColumnName;
    
    private String idPropertyName;

    /**
     * @return 返回 id
     */
    public String getId() {
        return id;
    }

    /**
     * @param 对id进行赋值
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 返回 parameterType
     */
    public String getParameterType() {
        return parameterType;
    }

    /**
     * @param 对parameterType进行赋值
     */
    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    /**
     * @return 返回 tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param 对tableName进行赋值
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return 返回 simpleTableName
     */
    public String getSimpleTableName() {
        return simpleTableName;
    }

    /**
     * @param 对simpleTableName进行赋值
     */
    public void setSimpleTableName(String simpleTableName) {
        this.simpleTableName = simpleTableName;
    }

    /**
     * @return 返回 idColumnName
     */
    public String getIdColumnName() {
        return idColumnName;
    }

    /**
     * @param 对idColumnName进行赋值
     */
    public void setIdColumnName(String idColumnName) {
        this.idColumnName = idColumnName;
    }

    /**
     * @return 返回 idPropertyName
     */
    public String getIdPropertyName() {
        return idPropertyName;
    }

    /**
     * @param 对idPropertyName进行赋值
     */
    public void setIdPropertyName(String idPropertyName) {
        this.idPropertyName = idPropertyName;
    }
}
