package com.swx.ibms.business.cases.service;
/**
*案管办案件受理service接口
*@author 罗华
*
*@version 1.0
*
*@since 2017年10月24日
*/

import com.swx.ibms.business.cases.bean.AgbZlpc;

import java.util.List;
import java.util.Map;



/**
*案管办质量评查
*@version 1.0
*@since 2018年4月26日
*/
public interface AgbZlpcService {

	/**
	 * 根据办理形式获取对应的记录
	 * @param blxs blxs == null 获取所有记录
	 * @param did 档案ID
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getAllZlpcByBlxs(String blxs,String did, String dwbm, String gh,int page,int pageSize) throws Exception;
	
	/**
	 * 通过质量评查id获取对应记录
	 * @param id
	 * @return
	 * @throws Exception
	 */
	AgbZlpc getZlpcById(String id, String dwbm, String gh) throws Exception;
	
	/**
	 * 获取所有办理形式的数量
	 * @param did 档案ID
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getCountByBlxs(String did, String dwbm, String gh) throws Exception;
	
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
	 * 添加案管办案件受理
	 */
	 String addAgbZlpc(String did,String ajmc,String ywlb,String cbxzbm,
				       String pczl,String zlpc_blxs,String pckssj,String pcjssj,
				       Integer pcwtgs,String pcjgxs,String zgqk,String zlpc_cbxz
				       ) throws Exception;
	 
	 /**
	  * 删除新增列表中的质量评查
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 String deleteAgbZlpc(String id) throws Exception;
	
	 /**
	  * 修改质量评查
	  * @return
	  * @throws Exception
	  */
	 String updateAgbZlpcById(String id,String ajmc,String ywlb,String cbxzbm,
		       String pczl,String zlpc_blxs,String pckssj,String pcjssj,
		       Integer pcwtgs,String pcjgxs,String zgqk,String zlpc_cbxz
		       ) throws Exception;
	 
	 /**
	  * 根据id查询质量评查用于表单回显
	  */
	 Map<String, Object> getZlpc(String id) throws Exception;
	 
}
