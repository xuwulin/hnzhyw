package com.swx.ibms.business.system.mapper;


import com.swx.ibms.business.rbac.bean.JSBM;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统管理数据访问接口
 * @author 李治鑫
 * @since 2017-5-9
 *
 */
public interface XTGLMapper {
	/**
	 * 查询所有单位列表
	 * @param map 参数map
	 * */
	void getAllDw(Map<String,Object> map);

	/**
	 * 查询单位信息
	 * @param map 参数map
	 * */
	void getDw(Map<String,Object> map);

	/**
	 * 添加单位
	 * @param map 参数map
	 * */
	void addDw(Map<String,Object> map);

	/**
	 * 更新单位信息
	 * @param map 参数map
	 * */
	void updateDw(Map<String,Object> map);

	/**
	 * 删除单位
	 * @param map 参数map
	 * */
	void deleteDw(Map<String,Object> map);

	/**
	 * 查询某个单位下的所有部门列表
	 * @param map 参数map
	 * */
	void getBmByDwbm(Map<String,Object> map);

	/**
	 * 查询部门信息
	 * @param map 参数map
	 * */
	void getBm(Map<String,Object> map);

	/**
	 * 添加部门
	 * @param map 参数map
	 * */
	void addBm(Map<String,Object> map);

	/**
	 * 更新部门信息
	 * @param map 参数map
	 * */
	void updateBm(Map<String,Object> map);

	/**
	 * 删除部门
	 * @param map 参数map
	 * */
	void deleteBm(Map<String,Object> map);

	/**
	 * 查询某个部门下的所有人员
	 * @param map 参数map
	 * */
	void getRyByBmbm(Map<String,Object> map);

	/**
	 *查询人员信息
	 * */
	List<Map<String, Object>> getRy(@Param("dwbm") String dwbm,
									@Param("name") String name);

	/**
	 *添加人员
	 *@param map 参数map
	 * */
	void addRy(Map<String,Object> map);

	/**
	 * 更新人员信息
	 * @param dwbm
	 * @param gh
	 * @param oldGh
	 * @param mc
	 * @param dlbm
	 * @param xb
	 * @param kl
	 * @param gzzh
	 * @return
	 */
	int updateRy(@Param("dwbm") String dwbm,
				 @Param("gh") String gh,
				 @Param("oldGh") String oldGh,
				 @Param("mc") String mc,
				 @Param("dlbm") String dlbm,
				 @Param("xb") String xb,
				 @Param("kl") String kl,
				 @Param("gzzh") String gzzh);

	int updatePer(@Param("dwbm") String dwbm,
				  @Param("gh") String gh,
				  @Param("mc") String mc,
				  @Param("dlbm") String dlbm,
				  @Param("xb") String xb,
				  @Param("kl") String kl,
				  @Param("gzzh") String gzzh,
				  @Param("oldGh") String oldGh);

	/**
	 *删除人员信息
	 *@param map 参数map
	 * */
	void deleteRy(Map<String,Object> map);

	/**
	 * 查询某单位下某个部门所有角色列表
	 * @param map 参数map
	 * */
	void getJsBybmbm(Map<String,Object> map);

	/**
	 * 查询角色信息
	 * @param map 参数map
	 * */
	void getJs(Map<String,Object> map);

	/**
	 *添加角色
	 *@param map 参数map
	 * */
	void addJs(Map<String,Object> map);

	/**
	 * 更新角色
	 * @param dwbm
	 * @param bmbm
	 * @param jsbm
	 * @param jsmc
	 * @param jsxh
	 * @param spjsbm
	 * @param description
	 * @return
	 */
	int updateJs(@Param("dwbm") String dwbm,
				 @Param("bmbm") String bmbm,
				 @Param("jsbm") String jsbm,
				 @Param("jsmc") String jsmc,
				 @Param("jsxh") Integer jsxh,
				 @Param("spjsbm") String spjsbm,
				 @Param("description") String description);


	/**
	 *删除角色
	 *@param map 参数map
	 * */
	int deleteJs(Map<String,Object> map);

	/**
	 * 查询人员的直接领导
	 * @param map 参数map
	 * */
	void getFp(Map<String,Object> map);


	/**
	 * 更新分配信息
	 * @param map 参数map
	 * */
	void updateFp(Map<String,Object> map);

	/**
	 * 增加分配信息
	 * @param map 参数map
	 * */
	void addFp(Map<String,Object> map);

	/**
	 * 删除分配信息
	 * @param map 参数map
	 * */
	void deleteFp(Map<String,Object> map);

	/**
	 * 根据档案中的所属人单位名称和所属人工号查询所属人名字
	 * */
	String selectMcByGh(@Param("dwbm") String dwbm, @Param("gh") String gh);

	List<JSBM> getJsByDwbm (String dwbm);

	/**
	 * 查询最大角色编码
	 * @param bmbm
	 * @return
	 */
	String getJsbmByBmbm (@Param("dwbm") String dwbm, @Param("bmbm") String bmbm);

	/**
	 * 查询最大工号
	 * @param dwbm
	 * @return
	 */
	String getMaxGh (@Param("dwbm") String dwbm);

	/**
	 * 添加人员分配
	 * @param dwbm
	 * @param bmbm
	 * @param jsbm
	 * @param gh
	 * @return
	 */
	int addJsToRyjsfp(@Param("dwbm") String dwbm,
					  @Param("bmbm") String bmbm,
					  @Param("jsbm") String jsbm,
					  @Param("gh") String gh);

	/**
	 * 更新人员分配
	 * @param dwbm
	 * @param bmbm
	 * @param jsbm
	 * @param gh
	 * @param oldGh
	 * @return
	 */
	int updateJsToRyjsfp(@Param("dwbm") String dwbm,
						 @Param("bmbm") String bmbm,
						 @Param("jsbm") String jsbm,
						 @Param("gh") String gh,
						 @Param("oldGh") String oldGh);

	int suspension(@Param("dwbm") String dwbm,
				   @Param("gh") String gh);

	/**
	 * 登录别名验证，不能重复
	 */
	int aliasVerify(@Param("dlbm") String dlbm, @Param("dwbm") String dwbm);

	/**
	 * 部门名称验证，不能重复
	 */
	int deptNameVerify(@Param("bmmc") String bmmc, @Param("dwbm") String dwbm);

}
