package com.swx.ibms.business.common.mapper;

import java.util.Map;

/**
 * 日志信息数据交换接口
 * @author 李治鑫
 *
 */
public interface LogMapper {	
	/**一般信息
	 * @param map 参数map
	 */
	void info(Map<String,Object> map);
	
	/**警告信息
	 * @param map 参数map
	 */
	void warn(Map<String,Object> map);
	
	/**错误信息
	 * @param map 参数map
	 */
	void error(Map<String,Object> map);
	
	/**
	 * 获取日志信息(两个参数)
	 * @param map 参数map
	 */
	void getLog(Map<String,Object> map);
	
	/**
	 * 获取日志信息(没有参数)
	 * @param map 参数map
	 */
	void getLog1(Map<String,Object> map);
	/**
	 * 获取日志信息(只有日志类型)
	 * @param map 参数map
	 */
	void getLog2(Map<String,Object> map);
	/**
	 * 获取日志信息(只有日志种类)
	 * @param map 参数map
	 */
	void getLog3(Map<String,Object> map);
	
	/**删除所有日志信息
	 * @param map 参数map
 	 */
	void deleteAllLog(Map<String,Object> map);
	
	/**通过日志编号删除日志信息
	 * @param map 参数map
	 */
	void deleteLogById(Map<String,Object> map);
	
	/**
	 * 多条件搜索日志.
	 * @param map 
	 */
	void search(Map<String,Object> map);
	
}
