/**
 * 
 */
package com.swx.ibms.business.archives.mapper;

import com.swx.ibms.business.cases.bean.SFDAAJ;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;




/**
 * 司法档案创建
 * @author 封泽超
 *@since 2017年2月28日
 */
public interface SfdacjMapper {
	/**
	 * 查询承办案件列表
	 * @param map 
	 */
	void getCbAjList(Map<String,Object> map);
	/**
	 * 查询案件嫌疑人列表
	 * @param map 
	 */
	void getAjXyrList(Map<String,Object> map);
	/**
	 * 添加办案质量
	 * @param map 
	 */
	void addBazl(Map<String,Object> map);
	
	/**
	 * 根据归档id查询归档信息
	 * @param map 
	 */
	void selectDagzId(Map<String,Object> map);
	
	/**
	 * 根据统一受案号查询案件信息
	 * @param map 
	 * @return 
	 */
	void selectajbytysah(Map<String,Object> map);
	
	/**
	 * 根据所属人,所属人单位编码查询档案归档
	 * @param map 
	 */
	void selectdassrdwbm(Map<String,Object> map );
	
	/**
	 * 查询办案质量
	 * @param map 
	 */
	void getBazl(Map<String,Object> map);
	
	
	/**
	 * 修改办案质量
	 * @param map 
	 */
	void updateBazl(Map<String,Object> map);
	/**
	 * 嫌疑人详细信息
	 * @param map 
	 */
	void selectXyrDetail(Map<String,Object> map);
	/**
	 * @param map map
	 * @return 主板案件信息
	 */
	List<SFDAAJ> getZbAjList(Map<String, Object> map);
	
	/**
	 * 查询承办案件列表
	 * @param cbrgh 承办人工号	
	 * @param dwbm 单位编码
	 * @param kssj 开始时间
	 * @param jssj 结束时间
	 * @return List<SFDAAJ> 
	 */
	List<SFDAAJ> getCbAjNum(@Param("cbrgh")String cbrgh, @Param("dwbm")String dwbm,
			@Param("kssj")String kssj,@Param("jssj") String jssj);
	
	/**
	 * @param start 开始记录
	 * @param end 结束记录
	 * @param cbrgh 工号
	 * @param dwbm 单位编码
	 * @param kssj 开始时间
	 * @param jssj 结束时间
	 * @return list
	 */
	List<SFDAAJ> getCbAj(@Param("start")int start, @Param("end")int end,
			@Param("cbrgh")String cbrgh, @Param("dwbm")String dwbm,
			@Param("kssj") String kssj,@Param("jssj") String jssj);
	
	int getFileCreater(@Param("dwbm") String dwbm,
					   @Param("gh") String gh);

	/**
	 * 档案统一创建
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int uniteCreate(Map<String,Object> map);

	int getInfoOfPer(@Param("dwbm") String dwbm,
					 @Param("gh") String gh,
					 @Param("kssj") String kssj,
					 @Param("jssj") String jssj);

	/**
	 * 查询所有部门编码
	 * @param dwbm
	 * @param gh
	 * @return
	 */
	List<Map<String, Object>> getAllBmbm(@Param("dwbm") String dwbm,
										 @Param("gh") String gh);
}
