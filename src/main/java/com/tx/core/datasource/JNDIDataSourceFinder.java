/**
 * 文 件 名:  DataSourceFinder.java
 * 版    权:  TX Workgroup . Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  PengQingyang
 * 修改时间:  2012-10-5
 * <修改描述:>
 */
package com.tx.core.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.jndi.JndiTemplate;

/**
 * 从Jndi上下文获取数据源
 * <从Jndi上下文获取数据源>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JNDIDataSourceFinder implements DataSourceFinder
{
    private final static String COMP_ENV = "java:comp/env/";
    
    private JndiTemplate jndiTemplate = new JndiTemplate();
    
    private Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
    
    /**
     * 从上下文获取数据源
     * @param jndiName
     * @return
     */
    private DataSource lookupDataSource(String jndiName)
    {
        try
        {
            Object objfound = this.jndiTemplate.lookup(jndiName);
            if (objfound != null && objfound instanceof DataSource)
            {
                DataSource dsWant = (DataSource) objfound;
                return dsWant;
            }
        }
        catch (NamingException e)
        {
        }
        return null;
    }
    
    /**
     * <根据jndi名获取jndi数据源>
     * @param jndiName
     * @return
     */
    @Override
    public DataSource getDataSource(String jndiName)
    {
        // 这里不做同步控制
        DataSource ds1 = (DataSource) this.dataSourceMap.get(jndiName);
        
        if (ds1 != null)
        {
            return ds1;
        }
        ds1 = this.lookupDataSource(jndiName);
        
        if (ds1 != null)
        {
            dataSourceMap.put(jndiName, ds1);
            return ds1;
        }
        else
        {
            if (jndiName.startsWith(COMP_ENV))
            {
                String jndiNameAlias = jndiName.substring(COMP_ENV.length());
                ds1 = this.lookupDataSource(jndiNameAlias);
                if (ds1 != null)
                {
                    dataSourceMap.put(jndiNameAlias, ds1);
                    return ds1;
                }
            }
            else
            {
                String jndiNameAlias = COMP_ENV.concat(jndiName);
                ds1 = this.lookupDataSource(jndiNameAlias);
                if (ds1 != null)
                {
                    dataSourceMap.put(jndiNameAlias, ds1);
                    return ds1;
                }
            }
        }
        return null;
    }
}
