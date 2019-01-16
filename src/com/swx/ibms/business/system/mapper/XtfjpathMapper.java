/**
 * 
 */
package com.swx.ibms.business.system.mapper;

import java.util.Map;


/**
 * 上传附件路径
 * @author 封泽超
 *@since 2017年4月7日
 */
public interface XtfjpathMapper {
	/**
	 * 修改上传路径
	 * @param map 
	 * @return
	 */
	void insert(Map<String,String> map);
	/**
	 * 查询上传路径
	 * @param map 
	 */
	void select(Map map);
}
