/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-5
 * <修改描述:>
 */
package com.tx.core.mybatis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <功能简述> <功能详细描述>
 * 
 * @author brady
 * @version [版本号, 2012-12-5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BatchResult {

	private boolean isSuccessAll = true;

	private int errorNum = 0;

	private List<Integer> errorRownumIndexList = new ArrayList<Integer>();

	private Map<Integer, Exception> errorExceptionRownumIndexMapping = new HashMap<Integer, Exception>();

	private Map<Integer, String> errorMessageRownumIndexMapping = new HashMap<Integer, String>();

	/**
	 * @return 返回 isSuccessAll
	 */
	public boolean isSuccessAll() {
		return isSuccessAll;
	}

	/**
	 * @param 对isSuccessAll进行赋值
	 */
	public void setSuccessAll(boolean isSuccessAll) {
		this.isSuccessAll = isSuccessAll;
	}

	/**
	 * @return 返回 errorNum
	 */
	public int getErrorNum() {
		return errorNum;
	}

	/**
	 * 当发生异常时设置 <功能详细描述> [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [异常类型] [异常说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void addErrorInfoWhenException(int rownumIndex, Exception ex) {
		isSuccessAll = false;
		this.errorRownumIndexList.add(rownumIndex);
		errorExceptionRownumIndexMapping.put(rownumIndex, ex);
		errorMessageRownumIndexMapping.put(rownumIndex, ex.toString());
	}

	/**
	 * 设置异常信息 1、因默认的错误信息填入的是exception.toString不便页面获取该对象进行显示
	 * 2、所以此处提供该方法，用于用户自定义错误信息的填入
	 * 
	 * @param rownum
	 * @param message
	 *            [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [异常类型] [异常说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void setErrorMessage(int rownumIndex, String message) {
		errorMessageRownumIndexMapping.put(rownumIndex, message);
	}
}
