package com.swx.ibms.business.archives.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author:徐武林
 * @date:2018年3月28日上午9:51:15
 * @Description:司法档案责任追究Mapper接口
 */

public interface SfdaZrzjMapper {
	
	/**
	 * 展示责任追究（根据归档id（DID））
	 * @param map
	 */
	void getSfdaZrzjPageList(Map<String,Object> map);
	
	/**
	 * 新增责任追究
	 * @param map 
	 */
	int addSfdaZrzj(Map<String,Object> map);
	
	/**
	 * 修改责任追究
	 * @param map
	 * @return 是否修改成功 1 = 成功, 0 = 不成功
	 */
	void updateSfdaZrzj(Map<String,Object> map);
	
	/**
	 * 删除司法档案下的责任追究
	 * @param map 
	 */
	int deleteSfdaZrzj(Map<String,Object> map);

	/**
	 * 按时间查询
	 * @param daid
	 * @param kssj
	 * @param jssj
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> serachZrzjByType(@Param("daid") String daid,
											   @Param("kssj") String kssj,
											   @Param("jssj") String jssj) throws Exception;
	
}
