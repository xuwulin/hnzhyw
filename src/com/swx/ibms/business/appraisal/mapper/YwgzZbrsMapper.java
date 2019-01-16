package com.swx.ibms.business.appraisal.mapper;

import java.util.Map;

/**
 * 
 * YwgzZbrsMapper.java 在编人数mapper
 * @author east
 * @date<p>2017年6月14日</p>
 * @version 1.0
 * @description<p></p>
 */
public interface YwgzZbrsMapper {
	/**
	 * 分页查询在编人数
	 * @param map 参数集合
	 */
	void zbrsSelectPageList(Map<String,Object> map);
	
	/**
	 * 根据id查询在编人数
	 * @param map 参数集合
	 */
	void zbrsSelectById(Map<String,Object> map);
	
	/**
	 * 添加在编人数
	 * @param map 参数集合
	 */
	void zbrsInsertData(Map<String,Object> map);
	
	/**
	 * 修改在编人数
	 * @param map 参数集合
	 */ 
	void zbrsUpdateData(Map<String,Object> map);
	
	/**
	 * 删除在编人数
	 * @param map 参数集合
	 */
	void zbrsDeleteData(Map<String,Object> map);
	
}
