package com.swx.ibms.business.rbac.mapper;

import java.util.Map;

/**
 * 
 * @author east
 * @date:2017年4月14日
 * @version:1.0
 * @description:角色mapper
 *
 */
public interface RoleMapper {
	/**
	 * 根据单位编码获取角色全部信息
	 * @param map 参数集合
	 * 
	 */
	void selectPageList(Map<String,Object> map);
	
	/**
	 * 根据单位编码、部门编码获取角色全部信息
	 * @param map 参数集合
	 */
	void selectZhywRoleList(Map<String,Object> map);
	
	/**
	 * 添加某单位下的角色信息
	 * @param map 参数集合
	 */
	void insertData(Map<String,Object> map);
	
	/**
	 * 修改某单位下的角色信息
	 * @param map 参数集合
	 */ 
	void updateData(Map<String,Object> map);
	
	/**
	 * 删除某单位下的角色信息
	 * @param map 参数集合
	 */
	void deleteData(Map<String,Object> map);
	
}
