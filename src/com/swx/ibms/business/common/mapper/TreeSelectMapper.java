package com.swx.ibms.business.common.mapper;


import com.swx.ibms.business.rbac.bean.BMBM;
import com.swx.ibms.business.rbac.bean.DWBM;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 树形结构展示Mapper接口
 * @author 李佳
 * @date: 2017年3月5日
 */
@SuppressWarnings("all")
public interface TreeSelectMapper {
	
	/**
	 * 查询所有检查院
	 * @param map map对象			
	 */
	void allJcy(Map<String, Object> map);
	
	/**
	 * 根据单位编码获取所有子单位编码
	 * @param map map对象
	 */
	void zDwbm(Map<String, Object> map);

	/**
	 *  根据检查院编码查询出所有部门的编码和名称
	 *  @param map map对象
	 */
	void department(Map<String, Object> map);

	 /**
	 *  根据单位、部门编码查询出所有角色的编码和名称
	 *  @param map map对象
	 */
	void role(Map<String, Object> map);

	 /**
	 *   根据角色、单位、部门、查询下属的所有人员的工号和名称
	 *   @param map map对象
	 */
	void person(Map<String, Object> map);
	/**
	 * 根骨单位编码，部门编码，工号查询直接领导工号
	 * @param map map对象
	 */
	void selectzjld(Map<String, Object> map);
	
	
	/**
	 * 根据单位编码和部门编码获取部门名称
	 * @param dwbm 单位编码 
	 * @param bmbm 部门编码
	 * @return 部门名称
	 */
	String getBmmcByBmbm(@Param("dwbm")String dwbm,@Param("bmbm")String bmbm);
	
	/**
	 * 通过单位编码和工号获取姓名
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @return 姓名
	 */
	String getNameByGh(@Param("dwbm")String dwbm,@Param("gh")String gh);
	
	/**
	 * 通过单位编码和角色编码获取角色名称
	 * @param dwbm 单位编码
	 * @param jsbm 角色编码
	 * @return 角色名称
	 */
	String getJsmcByJsbm(@Param("dwbm")String dwbm,
						 @Param("bmbm")String bmbm,
						 @Param("jsbm")String jsbm);
	
	/**
	 * 根据单位编码、部门编码、角色编码获取人员角色分配信息
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @param jsbm 角色编码
	 * @return 人员角色分配信息
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getFpJl(@Param("dwbm")String dwbm,
					  @Param("bmbm")String bmbm,
					  @Param("jsbm")String jsbm);
	
	/**
	 * 通过单位编码和工号获取其人员分配信息
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @return 人员角色分配信息
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getFpJl2(@Param("dwbm")String dwbm,@Param("gh")String gh);
	
	/**
	 * 通过单位编码和部门映射获取部门编码信息
	 * @param dwbm 单位编码
	 * @param bmys 部门映射
	 * @return 部门编码信息
	 */
	BMBM getBmbmList(@Param("dwbm")String dwbm, @Param("bmys") String bmys);
	
	/**
	 * 通过单位编码和部门编码查询该部门的分管领导信息
	 * @param dwbm 单位编码
 	 * @param bmbm 部门编码
	 * @return 分管信息
	 */
	List<Map> getFgld(@Param("dwbm")String dwbm,@Param("bmbm")String bmbm);

	void getAllBmbmByDw(Map<String, Object> params);
	
	void getAllBmByDwBmbm(Map<String, Object> params);

	List<Map<String, Object>> getAllYwbm();

	List<Map<String, Object>> getAllAjlbByYwbm(@Param("ywbm")String ywbm);
	
	/**
	 * 获取该单位以及子单位信息
	 * @param dwbm 单位编码 
	 * @return 单位结果集
	 */
	List<DWBM> getAllDwbmByDw(@Param("dwbm")String dwbm);


	/**
	 * 根据单位编码/父部门编码查询两个系统的所有部门信息
	 * @param dwbm 单位编码
	 * @param fbmbm 父部门编码
	 * @return 部门信息结果集
	 */
	List<BMBM> getAllXtBmInfo(@Param("dwbm")String dwbm,
							  @Param("fbmbm")String fbmbm);

	/**
	 * 获取综合业务/统一业务中所有的人员信息
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @param jsbm 角色编码
	 * @param dlbm 登录别名
	 * @return 人员信息
	 */
	List<Map<String,Object>> getAllXtRyInfo(@Param("dwbm")String dwbm,
											@Param("bmbm")String bmbm,
											@Param("jsbm")String jsbm,
											@Param("dlbm")String dlbm);

	/**
	 * 获取综合业务/统一业务中某单位，某部门的所有角色
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @return
	 */
	List<Map<String,Object>> getAllXtBmJs(@Param("dwbm")String dwbm,
										  @Param("bmbm")String bmbm);

	/**
	 * 获取统一业务与综合业务中所有存在人员的父/子部门
	 * @param dwbm 单位编码（必传）
	 * @param fbmbm 父部门编码（可传/不传）
	 * @return
	 */
	List<Map<String,Object>> getDwBmByParamDwFbm(@Param("dwbm")String dwbm,
												  @Param("fbmbm")String fbmbm
												  );

	/**
	 * 获取统一业务与综合业务中该人员的所有父/子部门
	 * @param dwbm 单位编码（必传）
	 * @param gh 工号（必传）
	 * @param fbmbm 父部门编码（非必传）
	 * @return
	 */
	List<Map<String,Object>> getFbmByParamDwGhFbm(@Param("dwbm")String dwbm,
												 @Param("gh")String gh,
												  @Param("fbmbm")String fbmbm);
}
