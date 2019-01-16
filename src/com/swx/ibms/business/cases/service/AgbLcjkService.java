package com.swx.ibms.business.cases.service;
/**
*案管办案件受理service接口
*@author 罗华
*
*@version 1.0
*
*@since 2017年10月24日
*/

import com.swx.ibms.business.cases.bean.AgbLcjk;

import java.util.List;
import java.util.Map;



/**
*案管办流程监控
*@version 1.0
*@since 2018年4月26日
*/
public interface AgbLcjkService {

	/**
	 * 根据办理形式获取对应的记录
	 * @param blxs blxs == null 获取所有记录
	 * @param did 档案ID
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getAllLcjkByBlxs(String blxs,String did,int page,int pageSize) throws Exception;
	
	/**
	 * 通过流程监控id获取对应记录
	 * @param id
	 * @return
	 * @throws Exception
	 */
	AgbLcjk getLcjkById(String id, String dwbm, String gh) throws Exception;
	
	/**
	 * 获取所有办理形式的数量
	 * @param did 档案ID
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getCountByBlxs(String did,String dwbm,String gh) throws Exception;
	
	/**
	 * 获取包含统一业务的案件信息和案管办流程监控的信息
	 * @param did 档案id
	 * @param blxs 办理形式
	 * @param dwbm 单位编码
	 * @param gh  工号
	 * @param page 页码
	 * @param pageSize 页大小
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getLcjkAndTyywAjjbxx(String did,String blxs,String dwbm,
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
	 * 添加案管办流程监控
	 */
	 String addAgbLcjk(String did,String ajmc,String ywlb,String cbxzbm,
				       String jkrq,String jkr,String jkfs,String lcjk_blxs,
				       Integer wtgs,String lcjk_cbxz,String jknr) throws Exception;
	 
	 /**
	  * 删除新增列表中的流程监控
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 String deleteAgbLcjk(String id) throws Exception;
	
	 /**
	  * 修改流程监控
	  */
	 String updateAgbLcjkById(String id,String ajmc,String ywlb,String cbxzbm,
				       String jkrq,String jkr,String jkfs,String lcjk_blxs,
				       Integer wtgs,String lcjk_cbxz,String jknr) throws Exception;
	 
	 /**
	  * 根据id查询流程监控用于表单回显
	  */
	 Map<String, Object> getLcjk(String id) throws Exception;
}
