package com.swx.ibms.business.rbac.mapper;

import java.util.Map;

/**
 * <p>
 * Title:GlyMapper
 * </p>
 * <p>
 * Description: 管理员管理Mapper
 * </p>
 * author 朱春雨 date 2017年4月27日 下午1:38:40
 */
public interface GlyMapper {
	/**
	 * 插入管理员
	 * 
	 * @param map
	 */
	void insertGly(Map<String, Object> map);

	/**
	 * 删除管理员
	 * 
	 * @param map
	 */
	void deleteGly(Map<String, Object> map);

	/**
	 * 更新管理员
	 * 
	 * @param map
	 */
	void updateGly(Map<String, Object> map);
	/**
	 * 查询管理员
	 * @param map
	 */
	void selectGly(Map<String, Object> map);
}
