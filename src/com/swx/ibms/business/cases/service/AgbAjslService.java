package com.swx.ibms.business.cases.service;
/**
*案管办案件受理service接口
*@author 罗华
*
*@version 1.0
*
*@since 2017年10月24日
*/

import com.swx.ibms.business.cases.bean.AgbAjsl;

import java.util.List;
import java.util.Map;



/**
*案件受理
*@version 1.0
*@since 2018年4月26日
*/
@SuppressWarnings("all")
public interface AgbAjslService {

	/**
	 * 根据办理形式获取对应的记录
	 * @param blxs blxs == null 获取所有记录
	 * @param did 档案ID
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getAllAjslByBlxs(String blxs,String did,int page,int pageSize) throws Exception;
	
	/**
	 * 根据档案id获取案件受理，流程监控，质量评查更正信息 
	 * @param did
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getCorrectByDid(String did,String dwbm, String gh, String zhgxr,int page,int pageSize) throws Exception;
	
	/**
	 * 通过案件受理id获取对应记录
	 * @param id
	 * @return
	 * @throws Exception
	 */
	AgbAjsl getAjslById(String id, String dwbm, String gh) throws Exception;
	
	/**
	 * 根据档案id获取办理形式的数量
	 * @param did 档案id
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getCountByBlxs(String did,String dwbm,String gh) throws Exception;
	
	/**
	 * 获取包含统一业务的案件信息和案管办案件受理的信息
	 * @param did 档案id
	 * @param blxs 办理形式
	 * @param dwbm 单位编码
	 * @param gh  工号
	 * @param page 页码
	 * @param pageSize 页大小
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getAjslAndTyywAjjbxx(String did,String blxs,String dwbm,
			String gh,int page,int pageSize) throws Exception;
	
	/**
	 * 获取该父部门下平均案件数量
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getCaseAvg(String dwbm,String bmbm,String kssj,String jssj) throws Exception;
	
	/**
	 * 获取该父部门下最多和最少办案数
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getCaseMaxAndMin(String dwbm,String bmbm,String kssj,String jssj) throws Exception;
	
	/**
	 * 获取父部门下办案数名次
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @param did 档案id
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getRank(String dwbm,String bmbm,String did,String kssj,String jssj) throws Exception;
	
	/**
	 * 根据档案id,是否删除,展示所有（案件受理，流程监控，质量评查）新增列表中的数据
	 */
	Map<String, Object> selectALLNewAgbajByDid(String did,int page,int pageSize) throws Exception;
	
	/**
	 * 添加案管办案件受理
	 */
	 String addAgbAjsl(String did,String ajmc,String ywlb,String cbxzbm,
				       String slsj,String flsj,String lrry,String shry,
				       Integer jzcs,Integer gp,String sacw,String ajsl_blxs,String cbxz
				       ) throws Exception;
	 
	 /**
	  * 删除新增列表中的案件受理
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 String deleteAgbAjsl(String id) throws Exception;
	 
	 /**
	 * 修改案件受理
	 */
	 String updateAgbAjslById(String id,String ajmc,String ywlb,String cbxzbm,
				       String slsj,String flsj,String lrry,String shry,
				       Integer jzcs,Integer gp,String sacw,String ajsl_blxs,String cbxz
				       ) throws Exception;
	 
	 /**
	  * 根据id查询案件受理用于表单回显
	  */
	 Map<String, Object> getAjsl(String id) throws Exception;

	/**
	 * 根据档案id查询案管办案件总数，查询三张表
	 * @param daId
	 * @throws Exception
	 */
//	 String getCounts(String daId) throws Exception;
	 
}
