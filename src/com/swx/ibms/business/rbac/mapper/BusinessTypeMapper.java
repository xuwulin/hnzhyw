package com.swx.ibms.business.rbac.mapper;

import java.util.Map;

/**
 * 
 * @author east
 * @date:2017年4月21日
 * @version:1.0
 * @description: 业务类别mapper
 *
 */
public interface BusinessTypeMapper {
	
	/**
	 * 查询未被删除的业务类别信息
	 * @param map 参数信息map
	 */
	void selectList(Map<String,Object> map);
}
