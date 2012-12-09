/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-9
 * <修改描述:>
 */
package com.tx.core.mybatis.generator.model;

/**
 * sqlMap中column定义
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-9]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SqlMapColumn {
    
    public SqlMapColumn() {
        super();
    }
    
    public SqlMapColumn(boolean isSimpleType, String propertyName,
            String columnName, Class<?> javaType, String joinPropertyName) {
        super();
        this.isSimpleType = isSimpleType;
        this.propertyName = propertyName;
        this.columnName = columnName;
        this.javaType = javaType;
        this.joinPropertyName = joinPropertyName;
    }
    
    /** 
     * 是否为基本类型
     * 是否为typeHandle能够处理的类型  
     */
    private boolean isSimpleType;
    
    private String propertyName;
    
    private String columnName;
    
    private Class<?> javaType;
    
    private String joinPropertyName;
    
    /**
     * @return 返回 isSimpleType
     */
    public boolean isSimpleType() {
        return isSimpleType;
    }
    
    /**
     * @param 对isSimpleType进行赋值
     */
    public void setSimpleType(boolean isSimpleType) {
        this.isSimpleType = isSimpleType;
    }
    
    /**
     * @return 返回 propertyName
     */
    public String getPropertyName() {
        return propertyName;
    }
    
    /**
     * @param 对propertyName进行赋值
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    
    /**
     * @return 返回 columnName
     */
    public String getColumnName() {
        return columnName;
    }
    
    /**
     * @param 对columnName进行赋值
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    
    /**
     * @return 返回 javaType
     */
    public Class<?> getJavaType() {
        return javaType;
    }
    
    /**
     * @param 对javaType进行赋值
     */
    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }
    
    /**
     * @return 返回 joinPropertyName
     */
    public String getJoinPropertyName() {
        return joinPropertyName;
    }
    
    /**
     * @param 对joinPropertyName进行赋值
     */
    public void setJoinPropertyName(String joinPropertyName) {
        this.joinPropertyName = joinPropertyName;
    }
}
