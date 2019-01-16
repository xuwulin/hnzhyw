package com.swx.ibms.business.archives.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author:徐武林
 * @date:2018年3月28日上午9:51:15
 * @Description:司法档案监督情况Mapper接口
 */

public interface SfdaJdqkMapper {
	
	/**
	 * 展示监督情况（根据归档id（DID））
	 * @param map
	 */
	void getSfdaJdqkPageList(Map<String,Object> map);
	
	/**
	 * 新增监督情况
	 * @param map 
	 */
	int addSfdaJdqk(Map<String,Object> map);
	
	/**
	 * 修改监督情况
	 * @param map
	 * @return 是否修改成功 1 = 成功, 0 = 不成功
	 */
	void updateSfdaJdqk(Map<String,Object> map);
	
	/**
	 * 删除司法档案下的监督情况
	 * @param map 
	 */
	int deleteSfdaJdqk(Map<String,Object> map);

	/**
	 * 根据时间查询
	 * @param daid
	 * @param kssj
	 * @param jssj
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> serachJdqkByType(@Param("daid") String daid,
											   @Param("kssj") String kssj,
											   @Param("jssj") String jssj) throws Exception;
}
