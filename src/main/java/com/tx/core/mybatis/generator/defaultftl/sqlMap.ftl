<?xml version="1.0" encoding="UTF-8" ?>  
<!DOCTYPE mapper 
	PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
	"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapper.namespace}">

	<!-- auto generate default resultMap -->
	<resultMap id="${select.resultMapId}" 
		type="${select.parameterType}">
<#list insert.sqlMapColumnList as column>
<#if !column.isSimpleType()>
		<result column="${column.propertyName}_${column.joinPropertyName}" property="${column.propertyName}.${column.joinPropertyName}"/>
</#if>
</#list>
	</resultMap>
	
	<!-- auto generate default find -->
	<select id="${select.findId}" 
		parameterType="${select.parameterType}"
		resultMap="${select.resultMapId}">
		SELECT 
<#list insert.sqlMapColumnList as column>
<#if column.isSimpleType()>
				${select.simpleTableName}.${column.columnName}<#if !column.isSameName()> AS ${column.propertyName}</#if><#if column_has_next>,</#if>
<#else>
				${select.simpleTableName}.${column.columnName} AS ${column.propertyName}_${column.joinPropertyName}<#if column_has_next>,</#if>
</#if>
</#list>
		  FROM ${select.tableName} ${select.simpleTableName}
		 where
		<trim prefixOverrides="AND | OR">
			<if test="@org.apache.commons.lang.StringUtils@isNotEmpty(id)">  
	            AND ${select.simpleTableName}.${select.idColumnName} = ${r"#{"}${select.idPropertyName}${r"}"}
	        </if>
		</trim>
	</select>
	
	<!-- auto generate default query -->
	<select id="${select.queryId}" 
		parameterType="${select.parameterType}"
		resultMap="${select.resultMapId}">
		SELECT 
<#list insert.sqlMapColumnList as column>
<#if column.isSimpleType()>
				${select.simpleTableName}.${column.columnName}<#if !column.isSameName()> AS ${column.propertyName}</#if><#if column_has_next>,</#if>
<#else>
				${select.simpleTableName}.${column.columnName} AS ${column.propertyName}_${column.joinPropertyName}<#if column_has_next>,</#if>
</#if>
</#list>
		  FROM ${select.tableName} ${select.simpleTableName}
		 <trim prefix="WHERE" prefixOverrides="AND | OR">
<#list insert.sqlMapColumnList as column>
<#if column.isSimpleType()>
			<if test="@org.apache.commons.lang.StringUtils@isNotEmpty(${column.propertyName})">  
	            AND ${select.simpleTableName}.${column.columnName} = ${r"#{"}${column.propertyName}${r"}"}
	        </if>
<#else>
			<if test="${column.propertyName} != null">
				<if test="@org.apache.commons.lang.StringUtils@isNotEmpty(${column.propertyName}.${column.joinPropertyName})">  
		            AND ${select.simpleTableName}.${column.columnName} = ${r"#{"}${column.propertyName}.${column.joinPropertyName}${r"}"}
		        </if>
	        </if>
</#if>
</#list>

		</trim>
	</select>
    
    <!-- auto generate default insert -->
    <insert id="${insert.id}" 
		parameterType="${insert.parameterType}">
<#if insert.isUseSelectKey()>
		<selectKey keyProperty="${insert.selectKey.keyProperty}" resultType="${insert.selectKey.resultType}">
			SELECT ${insert.selectKey.sequence}.nextVal FROM DUAL
		</selectKey>	
</#if>
		INSERT INTO ${insert.tableName}
		(
<#list insert.sqlMapColumnList as column>
			${column.columnName}<#if column_has_next>,</#if>
</#list>
		)
		VALUES
		(
<#list insert.sqlMapColumnList as column>
<#if column.isSimpleType()>
			${r"#{"}${column.propertyName}${r"}"}<#if column_has_next>,</#if>
<#else>
			<if test="subDemo != null">
				${r"#{"}${column.propertyName}.${column.joinPropertyName}${r"}"}<#if column_has_next>,</#if>
	        </if>
	        <if test="subDemo == null">
				null<#if column_has_next>,</#if>
	        </if>
</#if>
</#list>
		)
	</insert>
	
	<!-- auto generate default delete -->
	<delete id="${delete.id}" 
		parameterType="${delete.parameterType}">
		DELETE FROM ${delete.tableName} ${delete.simpleTableName} WHERE
		<trim prefixOverrides="AND | OR">
			<if test="@org.apache.commons.lang.StringUtils@isNotEmpty(id)">  
	            AND ${delete.simpleTableName}.${delete.idColumnName} = ${r"#{"}${delete.idPropertyName}${r"}"}
	        </if>
		</trim>
	</delete>

</mapper>
<!--
sqlMap生成描述:
${parseMessage}
-->