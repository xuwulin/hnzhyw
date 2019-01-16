package com.swx.ibms.business.archives.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author:徐武林
 * @date:2018年3月28日上午9:51:15
 * @Description:司法档案其他情况Mapper接口
 */

public interface SfdaQtqkMapper {
	
	/**
	 * 展示司法档案下的其他情况
	 * @param map 
	 */
	void getSfdaQtqkPageList(Map<String,Object> map);
	
	/**
	 * 添加司法档案下的其他情况
	 * @param map 
	 */
	int addSfdaQtqk(Map<String,Object> map);
	
	/**
	 * 修改司法档案下的其他情况
	 * @param map 
	 */
	void updateSfdaQtqk(Map<String,Object> map);
	
	/**
	 * 删除司法档案下的其他情况
	 * @param map 
	 */
	int deleteSfdaQtqk(Map<String,Object> map);

	/**
	 * 根据时间查询
	 * @param daid
	 * @param kssj
	 * @param jssj
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> serachQtqkByType(@Param("daid") String daid,
											   @Param("kssj") String kssj,
											   @Param("jssj") String jssj) throws Exception;
}
