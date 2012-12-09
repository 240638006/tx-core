<?xml version="1.0" encoding="UTF-8" ?>  
<!DOCTYPE mapper 
	PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
	"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapper.namespace}">
    
    <insert id="${insert.id}" 
		parameterType="${insert.parameterType}">
<#if (insert.selectKey)??>
		<selectKey keyProperty="${insert.selectKey.keyProperty}" resultType="${insert.selectKey.resultType}">
			select ${insert.selectKey.sequence}.nextVal from dual
		</selectKey>	
</#if>
		insert into ${insert.tableName}
		(
<#list insert.sqlMapColumnList as column>
<#if (column.isSimpleType??)>
			${column.columnName},
<#else>
			<if test="${column.propertyName} != null">
				<if test="@org.apache.commons.lang.StringUtils@isNotEmpty(${column.propertyName}.${column.joinPropertyName})">
					${column.columnName},
				</if>
			</if>
</#if>
</#list>
		)
		values
		(
<#list insert.sqlMapColumnList as column>
<#if (column.isSimpleType??)>
			${column.propertyName},
<#else>
			<if test="${column.propertyName} != null">
				<if test="@org.apache.commons.lang.StringUtils@isNotEmpty(${column.propertyName}.${column.joinPropertyName})">
					\#;\{;${column.propertyName}.${column.joinPropertyName}\};,
				</if>
			</if>
</#if>
</#list>
		)
	</insert>

</mapper>
<!--
sqlMap生成描述:
${parseMessage}
-->