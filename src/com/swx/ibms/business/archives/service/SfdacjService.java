/**
 * 
 */
package com.swx.ibms.business.archives.service;

import com.swx.ibms.business.archives.bean.DAGZGD;
import com.swx.ibms.business.cases.bean.DACJAJXYRXX;
import com.swx.ibms.business.cases.bean.JCGDAAJZL;
import com.swx.ibms.business.cases.bean.SFDAAJ;

import java.util.List;
import java.util.Map;



/**
 * 司法档案创建服务接口
 * @author 封泽超
 *@since 2017年2月28日
 */
public interface SfdacjService {
	

	/**
	 * 查询承办案件列表
	 * @param cbrgh 承办人工号	
	 * @param dwbm 单位编码
	 * @param kssj 开始时间
	 * @param jssj 结束时间
	 * @return List<SFDAAJ> 
	 */
	List<SFDAAJ> getCbAjList(String cbrgh, String dwbm, String kssj, String jssj);
	/**
	 * 查询案件嫌疑人列表
	 * @param tysah 
	 * @return List<DACJAJXYRXX>
	 */
	List<DACJAJXYRXX> getAjXyrList(String tysah);

	/**
	 * 添加办案质量
	 * @param map 
	 * @return String
	 */
	String addBazl(Map<String,Object> map);
	/**
	 * 根据归档id查询归档信息
	 * @param dagzgd 
	 * @return List<DAGZGD>
	 */
	List<DAGZGD> selectDagzId(DAGZGD dagzgd);
	
	/**
	 * 根据统一受案号查询案件信息
	 * @param tysah 统一受案号
	 * @return List<SFDAAJ> 
	 */
	List<SFDAAJ> selectajbytysah(String tysah);
	
	/**
	 * 根据所属人,所属人单位编码查询档案归档
	 * @param gagzgd 
	 * @return List<DAGZGD> 
	 */
	List<DAGZGD> selectdassrdwbm(DAGZGD gagzgd);
	
	/**
	 * 办案质量查询
	 * @param tysah  统一受案号
	 * @return List<JCGDAAJZL>
	 */
	List<JCGDAAJZL> getBazl(String tysah);
	
	/**
	 * 办案质量修改
	 * @param jc 
	 * @return String 
	 */
	
	String updateBazl(JCGDAAJZL jc);
	   
	   /**
	    * 查询嫌疑人detail
	 * @param pbmsah 部门受案号
	 * @param pxyrbh 嫌疑人编码
	 * @return List<DACJAJXYRXX> 
	 */
	List<DACJAJXYRXX> selectXyrDetail(String pbmsah,String pxyrbh);
	/**
	 * 查询承办案件列表
	 * @param cbrgh 承办人工号	
	 * @param curentpage 当前页	
	 * @param pagesize 页大小	
	 * @param dwbm 单位编码
	 * @param kssj 开始时间
	 * @param jssj 结束时间
	 * @return List<SFDAAJ> 
	 */
	List<SFDAAJ> getZbAjList(String curentpage, String pagesize, 
			String cbrgh, String dwbm, String kssj, String jssj);
	
	/**
	 * 查询承办案件列表
	 * @param cbrgh 承办人工号	
	 * @param dwbm 单位编码
	 * @param kssj 开始时间
	 * @param jssj 结束时间
	 * @return List<SFDAAJ> 
	 */
	List<SFDAAJ> getCbAjNum(String cbrgh, String dwbm, String kssj, String jssj);
	
	/**
	 * 查询承办案件列表
	 * @param cbrgh 承办人工号	
	 * @param curentpage 当前页	
	 * @param pagesize 页大小	
	 * @param dwbm 单位编码
	 * @param kssj 开始时间
	 * @param jssj 结束时间
	 * @return List<SFDAAJ> 
	 */
	List<SFDAAJ> getCbAj(String curentpage, String pagesize, 
			String cbrgh, String dwbm, String kssj, String jssj);

	/**
	 * 档案指定创建人的类型为13
	 * @param dwbm
	 * @param gh
	 * @return
	 */
	Boolean getFileCreater(String dwbm,String gh);

	String uniteCreate(String dwbm,String xm ,String gh,String cjr);

}
