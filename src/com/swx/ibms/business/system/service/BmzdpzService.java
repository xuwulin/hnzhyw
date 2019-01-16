package com.swx.ibms.business.system.service;

import com.swx.ibms.business.system.bean.Bmzdpz;
import com.swx.ibms.common.utils.PageCommon;

import java.util.List;
import java.util.Map;



/**
 * 部门指定人配置服务
 * @author 李治鑫
 * @since 2017年7月14日  下午1:41:08
 */
public interface BmzdpzService {
	/**
	 * 通过部门映射、审批类型、单位编码获取配置信息
	 * @param map 参数map
	 * @return List<Bmzdpz>
	 */
	List<Bmzdpz> getPzByDwbm(Map<String,Object> map);
	
	/**
	 * 判断是否存在指定人配置信息
	 * @param map 参数map
	 * @return true：存在        false：不存在
	 */
	boolean isExistByDwbm(Map<String,Object> map);
	
	/**
	 * 添加配置信息
	 * @param map 参数map
	 * @return 字符串
	 */
	String addBmspr(Map<String,Object> map);
	
	/**
	 * 删除配置信息
	 * @param map 参数map
	 * @return 字符串
	 */
	String deleteBmsprById(Map<String,Object> map);
	
	/**
	 * 更新配置信息
	 * @param map 参数map
	 * @return 字符串
	 */
	String updateBmsprById(Map<String,Object> map);
	
	/**
	 * 通过审批类型获取信息
	 * @param map 参数map
	 * @return List<Map>
	 */
	List<Map<String,Object>> getBmysFromJdlcBysplx(Map<String,Object> map);
	
	/**
	 * 通过部门映射，审批类型，单位编码获取指定人信息
	 * @param map 参数map
	 * @return 指定人信息
	 */
	Bmzdpz getZdrInfo(Map<String,Object> map);
	
	/**
	 * 获取部门列表 
	 * @param map 参数map
	 * @return List<Map<String,Object>>
	 */
	List<Map<String,Object>> getdepartlist (Map<String,Object> map);
	
	/**
	 * 获取人员列表
	 * @param map 参数map
	 * @return List<Map<String,Object>>
	 */
	List<Map<String,Object>> getpersonofdepart(Map<String,Object> map);
	
	/**
	 * 获取全部部门审批人信息
	 * @param map 
	 * @return 分页对象
	 */
	PageCommon<Bmzdpz> getBmsprPageList(Map<String,Object> map);
	
	/**
	 * 根据id获取部门审批人
	 * @param map 审批人配置id
	 * @return 审批人配置对象
	 */
	Bmzdpz getBmsprById(Map<String,Object> map);
	
	/**
	 * 根据单位编码获取部门列表
	 * @param map 单位编码
	 * @return 部门列表
	 */
	List<Map<String,Object>> getBmListByDwbm(Map<String,Object> map);
	
	/**
	 * 查询某单位下某部门的人员 
	 * @param map 单位编码、部门编码
	 * @return 人员 列表
	 */
	List<Map<String,Object>> getRyListByBm(Map<String,Object> map);
}
