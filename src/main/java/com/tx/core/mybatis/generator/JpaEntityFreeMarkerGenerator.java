/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-9
 * <修改描述:>
 */
package com.tx.core.mybatis.generator;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.util.ClassUtils;

import com.tx.core.exceptions.parameter.ParameterIsInvalidException;
import com.tx.core.mybatis.generator.model.DeleteMapper;
import com.tx.core.mybatis.generator.model.InsertMapper;
import com.tx.core.mybatis.generator.model.JpaMetaClass;
import com.tx.core.mybatis.generator.model.SelectMapper;
import com.tx.core.mybatis.generator.model.SqlMapColumn;
import com.tx.core.mybatis.generator.model.SqlMapMapper;
import com.tx.core.mybatis.model.BatchResult;
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
    
    private String resultBasePath = "d:/mybatis/generator/jpa/";
    
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
    
    /** 默认的字段比较器，用以排序 */
    private static final Comparator<SqlMapColumn> columnComparator = new Comparator<SqlMapColumn>() {
        /**
         * @param o1
         * @param o2
         * @return
         */
        @Override
        public int compare(SqlMapColumn o1, SqlMapColumn o2) {
            if (o1.isId()) {
                return -1;
            }
            if (o2.isId()) {
                return 1;
            }
            if (o1.getClass().getName().length()
                    - o2.getClass().getName().length() > 0) {
                return 1;
            }
            else if (o1.getClass().getName().length()
                    - o2.getClass().getName().length() < 0) {
                return -1;
            }
            else {
                return 0;
            }
        }
    };
    
    public void generate(Class<?> type, String resultFolderPath) {
        JpaMetaClass japMetaClass = JpaMetaClass.forClass(type);
        
        //生成sqlMap
        generateSimpleSqlMap(japMetaClass, resultFolderPath);
        
        //生成Dao
        
        //生成Service
        
        //生成service单元测试类
    }
    
    private void generateSimpleSqlMap(JpaMetaClass jpaMetaClass,
            String resultFolderPath) {
        SqlMapMapper mapper = generateMapper(jpaMetaClass);
        InsertMapper insert = generateInsertMapper(jpaMetaClass);
        DeleteMapper delete = generateDeleteMapper(jpaMetaClass);
        SelectMapper select = generateSelectMapper(jpaMetaClass);
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("parseMessage", jpaMetaClass.getParseMessage().toString());
        data.put("mapper", mapper);
        data.put("insert", insert);
        data.put("delete", delete);
        data.put("select", select);
        
        //org.springframework.util.StringUtils
        String sqlMapPath = ClassUtils.convertClassNameToResourcePath(jpaMetaClass.getEntityTypeName())
                + "/../../dao/impl";
        sqlMapPath = org.springframework.util.StringUtils.cleanPath(sqlMapPath);
        
        FreeMarkerUtils.fprint(this.sqlMapTemplateFilePath,
                data,
                this.resultBasePath + "/" + sqlMapPath + "/"
                        + jpaMetaClass.getEntitySimpleName() + "SqlMap.xml");
        
    }
    
    /**
      * 生成查询映射
      * <功能详细描述>
      * @param jpaMetaClass
      * @return [参数说明]
      * 
      * @return SelectMapper [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public SelectMapper generateSelectMapper(JpaMetaClass jpaMetaClass) {
        SelectMapper selectMapper = new SelectMapper();
        
        selectMapper.setFindId("find" + jpaMetaClass.getEntitySimpleName());
        selectMapper.setQueryId("query" + jpaMetaClass.getEntitySimpleName());
        
        Map<String, String> columnNameMapping = jpaMetaClass.getColumnNameMapping();
        String idPropertyName = jpaMetaClass.getIdPropertyName();
        String idColumnName = columnNameMapping.get(idPropertyName);
        
        selectMapper.setIdColumnName(idColumnName == null ? "" : idColumnName.toUpperCase());
        selectMapper.setIdPropertyName(idPropertyName);
        
        selectMapper.setParameterType(jpaMetaClass.getEntityTypeName());
        selectMapper.setResultMapId(jpaMetaClass.getLowerCaseFirstCharEntitySimpleName()
                + "Map");
        
        selectMapper.setSimpleTableName(jpaMetaClass.getSimpleTableName()
                .toUpperCase());
        selectMapper.setTableName(jpaMetaClass.getTableName().toUpperCase());
        
        selectMapper.setSqlMapColumnList(generateColumnList(jpaMetaClass));
        
        return selectMapper;
    }
    
    /**
      * 生成删除映射
      * <功能详细描述>
      * @param jpaMetaClass
      * @return [参数说明]
      * 
      * @return DeleteMapper [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public DeleteMapper generateDeleteMapper(JpaMetaClass jpaMetaClass) {
        DeleteMapper deleteMapper = new DeleteMapper();
        
        deleteMapper.setId("insert" + jpaMetaClass.getEntitySimpleName());
        deleteMapper.setParameterType(jpaMetaClass.getEntityTypeName());
        
        Map<String, String> columnNameMapping = jpaMetaClass.getColumnNameMapping();
        String idPropertyName = jpaMetaClass.getIdPropertyName();
        String idColumnName = columnNameMapping.get(idPropertyName);
        
        deleteMapper.setIdColumnName(idColumnName == null ? "" : idColumnName.toUpperCase());
        deleteMapper.setIdPropertyName(idPropertyName);
        deleteMapper.setSimpleTableName(jpaMetaClass.getSimpleTableName()
                .toUpperCase());
        deleteMapper.setTableName(jpaMetaClass.getTableName().toUpperCase());
        
        return deleteMapper;
    }
    
    /**
      * 生成增加映射
      *<功能详细描述>
      * @param jpaMetaClass
      * @return [参数说明]
      * 
      * @return InsertMapper [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public InsertMapper generateInsertMapper(JpaMetaClass jpaMetaClass) {
        InsertMapper insertMapper = new InsertMapper();
        
        insertMapper.setId("insert" + jpaMetaClass.getEntitySimpleName());
        insertMapper.setParameterType(jpaMetaClass.getEntityTypeName());
        //设置表名，并转换为大写
        insertMapper.setTableName(jpaMetaClass.getTableName().toUpperCase());
        
        //字段
        insertMapper.getSqlMapColumnList()
                .addAll(generateColumnList(jpaMetaClass));
        
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
    private SqlMapMapper generateMapper(JpaMetaClass japMetaClass) {
        SqlMapMapper mapper = new SqlMapMapper();
        mapper.setNamespace(japMetaClass.getLowerCaseFirstCharEntitySimpleName());
        return mapper;
    }
    
    /** 
      * 生成字段列表
      *<功能详细描述>
      * @param jpaMetaClass
      * @return [参数说明]
      * 
      * @return List<SqlMapColumn> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private List<SqlMapColumn> generateColumnList(JpaMetaClass jpaMetaClass) {
        List<SqlMapColumn> columnList = new ArrayList<SqlMapColumn>();
        //生成对应需要的列关系
        List<String> getterNameList = jpaMetaClass.getGetterNames();
        //Map<String, Method> methodMap = jpaMetaClass.getGetterMethodMapping();
        Map<String, Class<?>> typeMap = jpaMetaClass.getGetterReturnTypeMapping();
        Map<String, Boolean> ignoreMap = jpaMetaClass.getIgnoreGetterMapping();
        Map<String, String> columnNameMapping = jpaMetaClass.getColumnNameMapping();
        String idPropertyName = jpaMetaClass.getIdPropertyName();
        for (String getterName : getterNameList) {
            if (StringUtils.isEmpty(getterName) || ignoreMap.get(getterName)) {
                continue;
            }
            Class<?> typeTemp = typeMap.get(getterName);
            SqlMapColumn columnTemp = null;
            if (SIMPLE_TYPE.contains(typeTemp)) {
                columnTemp = new SqlMapColumn(true, getterName,
                        columnNameMapping.get(getterName).toUpperCase(),
                        typeTemp, null);
            }
            else {
                JpaMetaClass temp = JpaMetaClass.forClass(typeTemp);
                String tempIdPropertyName = temp.getIdPropertyName();
                if (StringUtils.isEmpty(tempIdPropertyName)) {
                    //如果不为简单对象，关联对象中又不存在主键设置，这里将认为发生了异常，这样的情形不应该出现
                    throw new ParameterIsInvalidException(typeTemp.getName()
                            + " id property is empty");
                }
                columnTemp = new SqlMapColumn(false, getterName,
                        columnNameMapping.get(getterName).toUpperCase(),
                        typeTemp, tempIdPropertyName);
            }
            if (idPropertyName.equals(getterName)) {
                columnTemp.setId(true);
            }
            columnList.add(columnTemp);
        }
        
        Collections.sort(columnList, columnComparator);
        return columnList;
    }
    
    public static void main(String[] args) {
        JpaEntityFreeMarkerGenerator g = new JpaEntityFreeMarkerGenerator();
        
        g.generate(BatchResult.class, "d:/test/");
    }
}
