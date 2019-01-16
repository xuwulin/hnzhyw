package com.swx.ibms.business.cases.mapper;

import com.swx.ibms.business.cases.bean.AgbAjsl;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;



/**
*案官办案件受理dao
*@author 徐武林
*@version 1.0
*@since 2017年4月26日
*/
public interface AgbAjslMapper {
	
	/**
	 * 根据办理形式获取对应的记录
	 * @param did 档案id
	 * @param blxs 办理形式
	 * @return
	 * @throws Exception
	 */
	List<AgbAjsl> getAllAjslByBlxs(@Param("did") String did, @Param("blxs")String blxs) throws Exception;
	
	/**
	 * 通过案件受理id获取对应记录
	 * @param id
	 * @return
	 * @throws Exception
	 */
	AgbAjsl getAjslById(@Param("id") String id, @Param("dwbm") String dwbm, @Param("gh") String gh) throws Exception;
	
	/**
	 * 根据档案id获取办理形式的数量
	 * @param did 档案id
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getCountByBlxs(@Param("did") String did, @Param("dwbm") String dwbm, @Param("gh") String gh) throws Exception;
	
	/**
	 * 批量逻辑删除案件受理
	 * @return
	 * @throws Exception
	 */
	int updateSFSCById(@Param("list")List<String> list) throws Exception;
	

	/**
	 * 获取包含统一业务的案件信息和案管办案件受理的信息
	 * @param did 档案id
	 * @param blxs 办理形式
	 * @param ajlbbm 案件类别编码 1401 案件受理 1402 流程监控
	 * @param dwbm 单位编码
	 * @param gh  工号
	 * @return
	 * @throws Exception
	 */
	List<AgbAjsl> getAjslAndTyywAjjbxx(@Param("did")String did,@Param("blxs")String blxs,@Param("ajlbbm")String ajlbbm,
			@Param("dwbm")String dwbm,@Param("gh")String gh) throws Exception; 
	
	/**
	 * 根据档案id获取案件受理，流程监控，质量评查更正信息 
	 * @param did
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getCorrectByDid(@Param("did")String did,@Param("dwbm")String dwbm,@Param("gh")String gh,@Param("zhgxr")String zhgxr) throws Exception;
	
	/**
	 * 获取该父部门下平均案件数量
	 * @param ajlbbm 案件类别编码 1401 案件受理 1402流程监控
	 * @param dwbm 单位编码
	 * @param bmbm 父部门编码
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getCaseAvg(@Param("ajlbbm") String ajlbbm,
								   @Param("dwbm") String dwbm,
								   @Param("bmbm") String bmbm,
								   @Param("kssj") String kssj,
								   @Param("jssj") String jssj) throws Exception;
	
	/**
	 * 获取该父部门下最多和最少办案数
	 * @param ajlbbm 案件类别编码 1401 案件受理 1402流程监控
	 * @param dwbm 单位编码
	 * @param bmbm 父部门编码
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getCaseMaxAndMin(@Param("ajlbbm") String ajlbbm,
										 @Param("dwbm") String dwbm,
										 @Param("bmbm") String bmbm,
										 @Param("kssj") String kssj,
										 @Param("jssj") String jssj) throws Exception;
	
	/**
	 * 获取父部门下办案数名次
	 * @param ajlbbm 案件类别编码 1401 案件受理 1402流程监控
	 * @param dwbm 单位编码
	 * @param bmbm 父部门编码
	 * @param did 档案id
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getRank(@Param("ajlbbm") String ajlbbm,
								@Param("dwbm") String dwbm,
								@Param("bmbm") String bmbm,
								@Param("did") String did,
								@Param("kssj") String kssj,
								@Param("jssj") String jssj) throws Exception;
	
	/**
	 * 根据档案id,是否删除,展示所有（案件受理，流程监控，质量评查）新增列表中的数据
	 * @author 徐武林
	 * @param
	 * @throws Exception
	 * @since 2018/5/3
	 */
	List<AgbAjsl> selectALLNewAgbajByDid(@Param("did")String did) throws Exception;
	
	/**
	 * 新增案件受理
	 * @author 徐武林
	 * @param map 
	 * @since 2018/5/3
	 */
	int addAgbAjsl(Map<String,Object> map) throws Exception;
	
	/**
	 * 根据id和业务类别删除新增列表中的案管办案件受理
	 * @author 徐武林
	 * @since 2018/5/3
	 * @throws Exception
	 */
	int deleteAgbAjsl(Map<String,Object> map) throws Exception;
	
	/**
	 * 删除对应的附件
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int deleteAgbAjslFj(Map<String,Object> map) throws Exception;
	
	/**
	 * 根据案件受理id更新对应记录 
	 * @param
	 * @return
	 * @throws Exception
	 */
	int updateAgbAjslById(Map<String,Object> map) throws Exception;
	
	/**
	 * 根据id查询案件受理用于表单回显
	 */
	AgbAjsl getAjsl(@Param("id")String id) throws Exception;

	/**
	 * 根据档案id查询案管办案件数量，查询三张表
	 * @param daId
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getCounts(@Param("daId") String daId) throws Exception;
	
}
