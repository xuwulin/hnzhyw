package com.swx.ibms.business.archives.mapper;

import java.util.Map;

/**
 * @author 朱春雨
 *司法档案审批
 */
public interface SfdaspMapper {
	
	/**审批流程实例
	 * @param map 
	 */
	void addSplcsl(Map<String,Object> map);
	
	/**
	 * 处理事务
	 * @param map 
	 */
	void addClsw(Map<String,Object> map);
	
	/**查询审批流程信息
	 * @param map 
	 */
	void showSplc(Map<String,Object> map);
	
	/**
	 *查询是否有审批流程实例
	 *@param map 
	 */
	void sfysplcsl(Map<String,Object> map);
	
	/**
	 * 查看流程节点数据
	 * @param map 
	 */
	void select_jdlc(Map<String,Object> map);
	
	/**更改表结构后的新增实例接口
	 * @param map
	 */
	void addNewSplcsl(Map<String,Object> map);
	
	/**更改表结构后的更新实例接口
	 * @param map
	 */
	void updateNewSplcsl(Map<String,Object> map);
	
	/**通过流程类型、节点状态获取流程模板信息
	 * @param map
	 */
	void getLcmb(Map<String,Object> map);
	
	/**通过节点类型获取流程模板信息
	 * @param map
	 */
	void getLcmbByJdlx(Map<String,Object> map);
	
	/**更新审批流程实例
	 * @param map
	 */
	void updatesplcsl(Map<String,Object> map);
	
	/**
	 * 根据审批实体ID获取最新的流程实例信息
	 * @param map
	 */
	void getrecentlcsl(Map<String,Object> map);
	/**
	 * 根据流程id得到流程信息
	 * @param map
	 */
	void getlcydwlist(Map map);
}
