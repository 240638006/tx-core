/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-9
 * <修改描述:>
 */
package com.tx.core.mybatis;

import java.lang.reflect.Method;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.reflection.MetaClass;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.tx.core.mybatis.data.DemoTreeNode;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-9]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JpaMetaObjectTest {
    
    public static void main(String[] args) throws Exception{

        System.out.println(DemoTreeNode.class.getSimpleName());
        
        System.out.println(StringUtils.uncapitalize(DemoTreeNode.class.getSimpleName()));
    }
    
    public static void test1() throws Exception{
        Class<?> type = DemoTreeNode.class;
        
        MetaClass metaClass = MetaClass.forClass(type);
        
        for (String getterName : metaClass.getGetterNames()) {
            System.out.println(getterName + " : "
                    + metaClass.getGetterType(getterName));
            
            Method m = PropertyUtils.getReadMethod(BeanUtils.getPropertyDescriptor(type, getterName));
            
            System.out.println(m.getName());
        }
    }
    
}
