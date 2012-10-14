package com.tx.core.datasource;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * jndi数据源工厂类
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DataSourceFactoryBean implements
        FactoryBean<javax.sql.DataSource>, InitializingBean {
    private Logger logger = LoggerFactory.getLogger(DataSourceFactoryBean.class);
    
    private String jndiName;
    
    private DataSource ds = null;
    
    private DataSourceFinder jndiDataSourceFinder = new JNDIDataSourceFinder();
    
    private DataSourceFinder configDataSourceFinder = new ConfigDataSourceFinder();
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("Start init datasource................................");
        
        if (StringUtils.isEmpty(this.jndiName)) {
            throw new BeanInitializationException("jndiname is empty.");
        }
        
        logger.info("Try to init DataSource by jndi. jndiName : " + jndiName);
        this.ds = jndiDataSourceFinder.getDataSource(jndiName);
        if (this.ds != null) {
            logger.info("Init DataSource by jndi success.");
            logger.info("End init datasource................................");
            return;
        }
        
        logger.info("Cannot find jndi DataSource With Name: " + jndiName);
        logger.info("Try to init DataSource by classpath:/resources/context/dbContext.xml");
        
        this.ds = configDataSourceFinder.getDataSource(jndiName);
        if (this.ds != null) {
            logger.info("Init DataSource by configDataSource success.");
            return;
        }
        
        logger.error("Init DataSource fail. With Name: " + jndiName);
        
        throw new BeanInitializationException(
                "init DataSource fail. jndiName :" + this.jndiName);
        
        
    }
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public DataSource getObject() throws Exception {
        return this.ds;
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return DataSource.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
    
    /**
     * @return 返回 jndiName
     */
    public String getJndiName() {
        return jndiName;
    }
    
    /**
     * @param 对jndiName进行赋值
     */
    public void setJndiName(String jndiName) {
        this.jndiName = jndiName;
    }
}
