/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-9
 * <修改描述:>
 */
package com.tx.core.mybatis.generator;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.cxf.common.util.StringUtils;

import com.tx.core.exceptions.parameter.ParameterIsInvalidException;
import com.tx.core.mybatis.generator.model.Demo;
import com.tx.core.mybatis.generator.model.InsertMapper;
import com.tx.core.mybatis.generator.model.JpaMetaClass;
import com.tx.core.mybatis.generator.model.SqlMapColumn;
import com.tx.core.mybatis.generator.model.SqlMapMapper;
import com.tx.core.util.FreeMarkerUtils;


 /**
  * 根据JPA实体生成sqlMap
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2012-12-9]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class JpaEntityFreeMarkerGenerator {
    
    /** 基本类型集合 */
    private static final Set<Class<?>> SIMPLE_TYPE = new HashSet<Class<?>>();
    
    private String sqlMapTemplateFilePath = "com/tx/core/mybatis/generator/defaultftl/sqlMap.ftl";
    
    private String resultBasePath = "d:/test/sqlMap/";
    
    static {
        SIMPLE_TYPE.add(char.class);
        SIMPLE_TYPE.add(byte.class);
        SIMPLE_TYPE.add(int.class);
        SIMPLE_TYPE.add(short.class);
        SIMPLE_TYPE.add(double.class);
        SIMPLE_TYPE.add(float.class);
        SIMPLE_TYPE.add(long.class);
        SIMPLE_TYPE.add(boolean.class);
        
        SIMPLE_TYPE.add(Character.class);
        SIMPLE_TYPE.add(Byte.class);
        SIMPLE_TYPE.add(Integer.class);
        SIMPLE_TYPE.add(Short.class);
        SIMPLE_TYPE.add(Double.class);
        SIMPLE_TYPE.add(Float.class);
        SIMPLE_TYPE.add(Long.class);
        SIMPLE_TYPE.add(Boolean.class);
        
        SIMPLE_TYPE.add(String.class);
        SIMPLE_TYPE.add(Date.class);
        SIMPLE_TYPE.add(java.sql.Date.class);
        SIMPLE_TYPE.add(Timestamp.class);
        SIMPLE_TYPE.add(BigDecimal.class);
        SIMPLE_TYPE.add(com.ibm.icu.math.BigDecimal.class);
    }
    
    public void generate(Class<?> type,String resultFolderPath){
        JpaMetaClass japMetaClass = JpaMetaClass.forClass(type);
        
        //生成sqlMap
        generateSimpleSqlMap(japMetaClass,resultFolderPath);
        
        //生成Dao
        
        //生成Service
        
        //生成service单元测试类
    }
    
    private void generateSimpleSqlMap(JpaMetaClass jpaMetaClass,String resultFolderPath){
        SqlMapMapper mapper = generateMapper(jpaMetaClass);
        InsertMapper insert = generateInsertMapper(jpaMetaClass);
        
        String modelClassPath = jpaMetaClass.getEntityTypeName();
        
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("parseMessage", jpaMetaClass.getParseMessage().toString());
        data.put("mapper", mapper);
        data.put("insert", insert);
        
        FreeMarkerUtils.fprint(this.sqlMapTemplateFilePath, data, this.resultBasePath);
        
    }
    
    public InsertMapper generateInsertMapper(JpaMetaClass jpaMetaClass){
        InsertMapper insertMapper = new InsertMapper();
        
        insertMapper.setId("insert" + jpaMetaClass.getEntitySimpleName());
        insertMapper.setParameterType(jpaMetaClass.getEntityTypeName());
        insertMapper.setTableName(jpaMetaClass.getTableName());
        
        List<String> getterNameList = jpaMetaClass.getGetterNames();
        //Map<String, Method> methodMap = jpaMetaClass.getGetterMethodMapping();
        Map<String, Class<?>> typeMap = jpaMetaClass.getGetterReturnTypeMapping();
        Map<String, Boolean> ignoreMap = jpaMetaClass.getIgnoreGetterMapping();
        Map<String, String> columnNameMapping = jpaMetaClass.getColumnNameMapping();
        for(String getterName : getterNameList){
            if(ignoreMap.get(getterName)){
                continue;
            }
            Class<?> typeTemp = typeMap.get(getterName);
            SqlMapColumn columnTemp = null;
            if(SIMPLE_TYPE.contains(typeTemp)){
                columnTemp = new SqlMapColumn(true, getterName, columnNameMapping.get(getterName), typeTemp, "");
            }else{
                JpaMetaClass temp = JpaMetaClass.forClass(typeTemp);
                String idPropertyName = temp.getIdPropertyName();
                if(StringUtils.isEmpty(idPropertyName)){
                    throw new ParameterIsInvalidException(typeTemp.getName() + " id property is empty");
                }
                columnTemp = new SqlMapColumn(false, getterName, columnNameMapping.get(getterName), typeTemp, idPropertyName);
            }
            insertMapper.getSqlMapColumnList().add(columnTemp);
        }
        
        
        return insertMapper;
    }
    
    /**
      * 根据jpa对象解析结果生成映射mapper
      *<功能详细描述>
      * @param japMetaClass
      * @return [参数说明]
      * 
      * @return SqlMapMapper [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private SqlMapMapper generateMapper(JpaMetaClass japMetaClass){
        SqlMapMapper mapper = new SqlMapMapper();
        mapper.setNamespace(japMetaClass.getLowerCaseFirstCharEntitySimpleName());
        return mapper;
    }
    
    public static void main(String[] args) {
        JpaEntityFreeMarkerGenerator g = new JpaEntityFreeMarkerGenerator();
        
        g.generate(Demo.class,"d:/test/");
    }
}
