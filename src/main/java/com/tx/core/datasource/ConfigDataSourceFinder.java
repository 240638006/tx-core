package com.tx.core.datasource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.math.NumberUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * 从配置中获取数据源
 * <从配置中获取数据源>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigDataSourceFinder implements DataSourceFinder {
    private final static String COMP_ENV = "java:comp/env/";
    
    private static boolean isReaded = false;
    
    private Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
    
    private String dbContextPath = "classpath:dbcontext/context.xml";
    
    private ResourceLoader defaultResourceLoader = new DefaultResourceLoader(
            ConfigDataSourceFinder.class.getClassLoader());
    
    /**
     * <根据jndi名获取jndi数据源>
     * @param jndiName
     * @return
     */
    @Override
    public DataSource getDataSource(String jndiName) {
        // 这里不做同步控制
        DataSource ds1 = (DataSource) this.dataSourceMap.get(jndiName);
        
        if (ds1 != null) {
            return ds1;
        }
        
        String jndiNameAlias = jndiName;
        if (jndiName.startsWith(COMP_ENV)) {
            jndiNameAlias = jndiName.substring(COMP_ENV.length());
        }
        
        ds1 = (DataSource) this.dataSourceMap.get(jndiNameAlias);
        if (ds1 != null) {
            return ds1;
        }
        
        if (isReaded && ds1 == null) {
            return null;
        }
        
        Resource dbcontextResource = defaultResourceLoader.getResource(dbContextPath);
        
        if (!dbcontextResource.exists()) {
            return null;
        }
        
        isReaded = true;
        SAXReader reader = new SAXReader();
        InputStream io = null;
        try {
            io = dbcontextResource.getInputStream();
            Document doc = reader.read(io);
            Element rootEl = doc.getRootElement();
            @SuppressWarnings("unchecked")
            List<Element> elList = rootEl.elements("Resource");
            
            if (elList == null) {
                return null;
            }
            
            for (Element elTemp : elList) {
                String name = elTemp.attributeValue("name");
                @SuppressWarnings("unused")
                String auth = elTemp.attributeValue("auth");
                @SuppressWarnings("unused")
                String type = elTemp.attributeValue("type");
                
                String maxActive = elTemp.attributeValue("maxActive");
                String maxIdle = elTemp.attributeValue("maxIdle");
                String maxWait = elTemp.attributeValue("maxWait");
                
                String username = elTemp.attributeValue("username");
                String password = elTemp.attributeValue("password");
                
                String driverClassName = elTemp.attributeValue("driverClassName");
                String url = elTemp.attributeValue("url");
                
                BasicDataSource bds = null;
                
                bds = new BasicDataSource();
                //设置驱动程序
                bds.setDriverClassName(driverClassName);
                //设置连接用户名
                bds.setUsername(username);
                //设置连接密码
                bds.setPassword(password);
                //设置连接地址
                bds.setUrl(url);
                //设置初始化连接总数
                bds.setInitialSize(NumberUtils.toInt(maxIdle, 10));
                //设置同时应用的连接总数
                bds.setMaxActive(NumberUtils.toInt(maxActive, -1));
                //设置在缓冲池的最大连接数
                bds.setMaxIdle(NumberUtils.toInt(maxIdle, -1));
                //设置在缓冲池的最小连接数
                bds.setMinIdle(0);
                //设置最长的等待时间
                bds.setMaxWait(NumberUtils.toInt(maxWait, -1));
                
                this.dataSourceMap.put(name, bds);
            }
            //DocumentSource dS = new DocumentSource(document)
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (DocumentException e) {
            e.printStackTrace();
        }
        finally {
            if (io != null) {
                try {
                    io.close();
                }
                catch (IOException e) {
                }
            }
        }
        
        return this.dataSourceMap.get(jndiNameAlias);
        
    }
}
