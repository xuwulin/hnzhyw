package com.swx.ibms.business.system.mapper;

import java.util.Map;

/**
 * 部门指定人配置数据交换接口
 * @author 李治鑫
 * @since 2017年7月14日  上午11:32:35
 */
public interface BmzdpzMapper {
	/**
	 * 通过部门映射、审批类型、单位编码获取配置信息
	 * @param map 参数map
	 */
	void getPzByDwbm(Map<String,Object> map);
	
	/**
	 * 判断是否存在指定人配置信息
	 * @param map 参数map
	 */
	void isExistByDwbm(Map<String,Object> map);
	
	/**
	 * 添加配置信息
	 * @param map 参数map
	 */
	void addBmspr(Map<String,Object> map);
	
	/**
	 * 删除配置信息
	 * @param map 参数map
	 */
	void deleteBmsprById(Map<String,Object> map);
	
	/**
	 * 更新配置信息
	 * @param map 参数map
	 */
	void updateBmsprById(Map<String,Object> map);
	
	/**
	 * 获取某个流程中的部门映射
	 * @param map 参数map
	 */
	void getBmysBySplx(Map<String,Object> map);
	
	/**
	 * 获取部门映射的信息
	 * @param map 参数map
	 */
	void getInfoOfBmys(Map<String,Object> map);
	
	/**
	 * 通过部门映射，审批类型，单位编码获取指定人信息
	 * @param map 参数map
	 */
	void getZdrInfo(Map<String,Object> map) ;
	
	/**
	 * 获取部门列表
	 * @param map 参数map
	 */
	void getdepartlist(Map<String,Object> map);
	
	/**
	 * 获取人员列表
	 * @param map 参数map
	 */
	void getpersonofdepart(Map<String,Object> map);
	
	/**
	 * 获取全部部门审批人信息
	 * @param map 参数map
	 */
	void getBmsprPageList(Map<String,Object> map);
	
	/**
	 * 根据id获取部门审批人
	 * @param map 审批人配置id
	 */
	void getBmsprById(Map<String,Object> map);
	
	/**
	 * 根据单位编码获取部门列表
	 * @param map 单位编码
	 */
	void getBmListByDwbm(Map<String,Object> map);
	
	/**
	 * 查询某单位下某部门的人员 
	 * @param map 单位编码、部门编码
	 */
	void getRyListByBm(Map<String,Object> map);
}
