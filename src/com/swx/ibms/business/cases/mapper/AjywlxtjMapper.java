package com.swx.ibms.business.cases.mapper;

import java.util.Map;

/**
 * 案件业务类型统计数据访问接口
 * @author 李治鑫
 *
 */
public interface AjywlxtjMapper {
	
	/**
	 * 获取个人在某年中办理案件每个类型的数量
	 * @param map 参数map
	 */
	void getMountOfAj(Map<String,Object> map);
}
