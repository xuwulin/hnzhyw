package com.swx.ibms.business.cases.mapper;
/**
*案管办流程监控
*@author 罗华
*
*@version 1.0
*
*@since 2017年10月24日
*/

import com.swx.ibms.business.cases.bean.AgbZlpc;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;



public interface AgbZlpcMapper {

	/**
	 * 通过办理形式获取对应的记录
	 * @param did 档案id
	 * @param blxs 
	 * @return
	 * @throws Exception
	 */
	List<AgbZlpc> getAllZlpcByBlxs(@Param("did")String did, @Param("blxs")String blxs, @Param("dwbm")String dwbm, @Param("gh")String gh) throws Exception;
	
	/**
	 * 通过质量评查id获取对应记录
	 * @param id
	 * @return
	 * @throws Exception
	 */
	AgbZlpc getZlpcById(@Param("id") String id, @Param("dwbm") String dwbm, @Param("gh") String gh) throws Exception;
	
	/**
	 * 获取每个办理形式的数量
	 * @param did 档案id
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getCountByBlxs(@Param("did")String did, @Param("dwbm") String dwbm, @Param("gh") String gh) throws Exception;
	
	/**
	 * 逻辑批量删除
	 * @param list
	 * @return
	 * @throws Exception
	 */
	int updateSFSCById(List<String> list) throws Exception;
	
	/**
	 * 获取该父部门下平均案件数量
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getCaseAvg(@Param("dwbm") String dwbm,
								   @Param("bmbm") String bmbm,
								   @Param("kssj") String kssj,
								   @Param("jssj") String jssj) throws Exception;
	
	/**
	 * 获取该父部门下最多和最少办案数
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getCaseMaxAndMin(@Param("dwbm") String dwbm,
										 @Param("bmbm") String bmbm,
										 @Param("kssj") String kssj,
										 @Param("jssj") String jssj) throws Exception;
	
	/**
	 * 获取父部门下办案数名次
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @param did 档案id
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getRank(@Param("dwbm") String dwbm,
								@Param("bmbm") String bmbm,
								@Param("did") String did,
								@Param("kssj") String kssj,
								@Param("jssj") String jssj) throws Exception;
	
	/**
	 * 新增案管办质量评查
	 * @param map
	 * @author 徐武林
	 * @since 2018/5/3
	 * @throws Exception
	 */
	int addAgbZlpc(Map<String,Object> map) throws Exception;
	
	/**
	 * 根据id和业务类别删除新增列表中的案管办质量评查
	 * @author 徐武林
	 * @since 2018/5/3
	 * @throws Exception
	 */
	int deleteAgbZlpc(Map<String,Object> map) throws Exception;
	
	/**
	 * 删除对应的附件
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int deleteAgbZlpcFj(Map<String,Object> map) throws Exception;
	
	/**
	 * 通过流程监控id更新对应记录
	 * @param lcjkid
	 * @return
	 * @throws Exception
	 */
	int updateAgbZlpcById(Map<String,Object> map) throws Exception;
	
	/**
	 * 根据id查询质量评查用于表单回显
	 */
	AgbZlpc getZlpc(@Param("id")String id) throws Exception;
}
