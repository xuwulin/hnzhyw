package com.swx.ibms.business.archives.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


/**
 * @author:徐武林
 * @date:2018年3月28日上午9:51:15
 * @Description:司法档案荣誉技能Mapper接口
 */

public interface SfdaRyjnMapper {
	
	/**
	 * 展示荣誉技能（根据归档id（DID））
	 * @param map
	 */
	void getSfdaRyjnPageList(Map<String,Object> map);
	
	/**
	 * 新增荣誉技能
	 * @param map 
	 */
	int addSfdaRyjn(Map<String,Object> map);
	
	/**
	 * 修改荣誉技能
	 * @param map
	 * @return 是否修改成功 1 = 成功, 0 = 不成功
	 */
	void updateSfdaRyjn(Map<String,Object> map);
	
	/**
	 * 删除司法档案下的荣誉技能
	 * @param map 
	 */
	int deleteSfdaRyjn(Map<String,Object> map);
	
	/**
	 * 根据单位编码，部门编码获取该部门下所有人员的工号
	 * @param dwbm
	 * @param bmbm
	 * @return
	 */
	List<Map<String, Object>> getAllGhAndMc(@Param("dwbm") String dwbm,
									   		@Param("bmbm") String bmbm,
									   		@Param("kssj") String kssj,
									   		@Param("jssj") String jssj) throws Exception;

	/**
	 * 根据单位编码，工号获取档案id，一档制不需要时间
	 * @param dwbm
	 * @param gh
	 * @return
	 * @throws Exception
	 */
	String getAllDaid(@Param("dwbm") String dwbm,
			  		  @Param("gh") String gh,
			  		  @Param("kssj") String kssj,
			  		  @Param("jssj") String jssj) throws Exception;
	
	/**
	 * 根据档案id查询各项技能数量
	 * @param did
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getCounts(@Param("did") String did) throws Exception;

    /**
     * 根据时间查询
     * @param daid
     * @param kssj
     * @param jssj
     * @return
     * @throws Exception
     */
	List<Map<String, Object>> serachRyjnByType(@Param("daid") String daid,
											   @Param("kssj") String kssj,
										   	   @Param("jssj") String jssj) throws Exception;
}
