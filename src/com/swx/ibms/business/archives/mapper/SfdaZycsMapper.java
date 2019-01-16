package com.swx.ibms.business.archives.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author:徐武林
 * @date:2018年3月28日上午9:51:15
 * @Description:司法档案职业操守Mapper接口
 */

public interface SfdaZycsMapper {
	
	/**
	 * 展示职业操守（根据归档id（DID））
	 * @param map
	 */
	void getSfdaZycsPageList(Map<String,Object> map);
	
	/**
	 * 新增职业操守
	 * @param map 
	 */
	int addSfdaZycs(Map<String,Object> map);
	
	/**
	 * 修改职业操守
	 * @param map
	 * @return 是否修改成功 1 = 成功, 0 = 不成功
	 */
	void updateSfdaZycs(Map<String,Object> map);
	
	/**
	 * 删除司法档案下的职业操守
	 * @param map 
	 */
	int deleteSfdaZycs(Map<String,Object> map);

	/**
	 * 根据时间查询
	 * @param daid
	 * @param kssj
	 * @param jssj
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> serachZycsByType(@Param("daid") String daid,
											   @Param("kssj") String kssj,
											   @Param("jssj") String jssj) throws Exception;
	
}
