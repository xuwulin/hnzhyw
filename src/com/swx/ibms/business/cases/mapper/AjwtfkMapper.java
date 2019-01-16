package com.swx.ibms.business.cases.mapper;

import java.util.Map;
/**
 * 案件问题问题反馈Mapper接口
 * @author 李佳 
 * @date: 2017年3月15日
 */
public interface AjwtfkMapper {
 
	/**
	 * 通过wbid(部门受案号)查询工号	
	 * @param map map对象
	 */
	void getGhByBmsah(Map<String, Object> map);
	/**
	 * 新增案件问题反馈信息	
	 * @param map map对象
	 */
	void insert(Map<String, Object> map);

	/**
	 * 删除案件问题反馈信息			
	 * @param map map对象
	 */
	void delete(Map<String, Object> map);

	/**
	 * 更新案件问题反馈信息	
	 * @param map map对象
	 */
	void update(Map<String, Object> map);

	
	/**
	 * 根据id获得案件问题反馈信息
	 * @param map map对象         
	 */
	void getAjwtfk(Map<String, Object> map);
	
	/**
	 * 通过id查询统一受案号和部门受案号
	 * @param map map对象         
	 */
	void getBmsahById(Map<String, Object> map);

}
