package com.tx.core.tree.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.tx.core.TxConstants;
import com.tx.core.tree.model.TreeAble;

/**
 * <树转换工具类>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TreeUtils {
    
    @SuppressWarnings("rawtypes")
    public static <C extends Collection<T>, T extends TreeAble> C changToTree(
            C treeAbleCollection, C parentNodeCollection) {
        //判断入参是否合法
        if (treeAbleCollection == null || parentNodeCollection == null
                || treeAbleCollection.size() == 0
                || parentNodeCollection.size() == 0) {
            //如果root或需要转换的集合其中有一个为空，直接返回root集合当作结果
            return parentNodeCollection;
        }
        
        //循环得到当前传入list上级节点有多少个
        //并进行非法节点过滤，如果某节点id  = parentId将会造成死循环 ,将这样的节点抛弃
        Map<String, C> parentIdIndexMap = new HashMap<String, C>();
        for (T treeAbleTemp : treeAbleCollection) {
            String superId = treeAbleTemp.getParentId();
            String id = treeAbleTemp.getId();
            if (StringUtils.isEmpty(id) || id.equals(superId)) {
                //并进行非法节点过滤，如果某节点id  = parentId将会造成死循环
                //doNothing
                continue;
            }
            
            //根据父节点形成父ID与集合的映射
            if (parentIdIndexMap.containsKey(superId)) {
                parentIdIndexMap.get(superId).add(treeAbleTemp);
            }
            else {
                C collectionTemp = newCollectionInstance(treeAbleCollection);
                collectionTemp.add(treeAbleTemp);
                parentIdIndexMap.put(superId, collectionTemp);
            }
        }
        
        //如果合法的节点映射得到的集合为空
        if (parentIdIndexMap.size() == 0) {
            return parentNodeCollection;
        }
        
        /** 将子节点放入父节点 */
        /** 并迭代进行下一级的节点放入 */
        C allNextChildCollecion = setNextLevelTree(parentIdIndexMap,
                parentNodeCollection);
        
        /** 开始迭代 */
        boolean flag = true;
        while (flag && allNextChildCollecion != null && allNextChildCollecion.size() > 0) {
            allNextChildCollecion = setNextLevelTree(parentIdIndexMap,
                    allNextChildCollecion);
            if (allNextChildCollecion == null || allNextChildCollecion.size() == 0) {
                flag = false;
            }
        }
        
        return parentNodeCollection;
    }
    
    /**
      *<获取下级树节点总集合>
      *<功能详细描述>
      * @param parentIdIndexMap
      * @param parentNodeCollection
      * @return [参数说明]
      * 
      * @return C [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static <C extends Collection<T>, T extends TreeAble> C setNextLevelTree(
            Map<String, C> parentIdIndexMap, C parentNodeCollection) {
        
        //将子节点放入父节点 
        //并迭代进行下一级的节点放入
        C allNextChildCollection = newCollectionInstance(parentNodeCollection);
        for (T parentTreeAbleNodeTemp : parentNodeCollection) {
            String parentTreeNodeId = parentTreeAbleNodeTemp.getId();
            if (parentIdIndexMap.containsKey(parentTreeNodeId)) {
                parentTreeAbleNodeTemp.setChilds(parentIdIndexMap.get(parentTreeNodeId));
                allNextChildCollection.addAll(parentIdIndexMap.get(parentTreeNodeId));
            }
        }
        return allNextChildCollection;
    }
    
    /**
      *<获取集合实体>
      *<功能详细描述>
      * @param treeAbleCollection
      * @return [参数说明]
      * 
      * @return Collection<T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static <C extends Collection<T>, T extends TreeAble> C newCollectionInstance(
            C treeAbleCollection) {
        if (treeAbleCollection instanceof List) {
            return (C) new ArrayList<T>(TxConstants.INITIAL_CONLLECTION_SIZE);
        }
        else if (treeAbleCollection instanceof Set) {
            return (C) new HashSet<T>(TxConstants.INITIAL_CONLLECTION_SIZE);
        }
        //when default
        return (C) new ArrayList<T>(TxConstants.INITIAL_CONLLECTION_SIZE);
    }
}
