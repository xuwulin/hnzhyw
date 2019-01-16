package com.swx.ibms.business.rbac.mapper;

import com.swx.ibms.business.rbac.bean.JSBM;
import com.swx.ibms.business.rbac.bean.RYJSFP;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;



/**
 * 
 * @author east
 * @date:2017年4月19日
 * @version:1.0
 * @description:部门mapper
 *
 */
public interface DepartmentMapper {
	/**
	 * 根据单位编码查询此单位下的所有部门信息
	 * @param map 数据集合
	 */
	void selectZhywList(Map<String,Object> map);
	
	
	/**
	 * 根据单位编码查询此单位下的所有部门信息并分页
	 * @param map 数据集合
	 */
	void selectPageList(Map<String,Object> map);
	
	
	/**
	 * 添加某单位下的部门信息
	 * @param map 数据集合
	 */
	void  insertData(Map<String,Object> map);
	
	/**
	 * 根据单位编码和部门编码修改此单位下的部门信息
	 * @param map  数据集合
	 */
	void updateData(Map<String,Object> map);
	
	
	/**
	 * 根据单位编码和部门编码实现假删除此单位下的部门
	 * @param map   数据集合
	 */
	void deleteData(Map<String,Object> map);
	
	/**
	 * 根据单位编码、是否删除标示查询单位信息
	 * @param map 参数集合
	 */
	void selectDwbm(Map<String,Object> map);


	/**
	 * @param jsbm 角色信息
	 */
	void addbmjs(JSBM jsbm);


	/**
	 * @param dwbm 单位编码
	 * @param bmmc 部门名称
	 * @return 部门编码
	 */
	String selectbmbm(@Param("dwbm") String dwbm,@Param("bmmc") String bmmc);


	/**
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @return 部门角色信息
	 */
	List<JSBM> getJs(@Param("dwbm") String dwbm, @Param("bmbm") String bmbm);

	/**
	 * 查询某一部门下的角色分配信息
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @return 部门角色信息
	 */
	List<RYJSFP> getJsfpxx(@Param("dwbm") String dwbm, @Param("bmbm") String bmbm);
	
	/**
	 * 查询是否任职于其他部门，如果任职于其他部门则返回人员角色分配信息
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @param gh 工号
	 * @return List<RYJSFP> 人员角色分配信息
	 */
	List<RYJSFP> rzyQtBm(@Param("dwbm") String dwbm, @Param("bmbm") String bmbm, @Param("gh") String gh);

	/**
	 * 删除某一部门下的人员信息
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 */
	void deleteRy(@Param("dwbm") String dwbm, @Param("gh") String gh);
	
	/**
	 * @param dwbm 单位编 码
	 * @param bmbm 部门编码
	 */
	void deletebmjs(@Param("dwbm") String dwbm, @Param("bmbm") String bmbm);


	/**
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 */
	void deletebm(@Param("dwbm") String dwbm, @Param("bmbm") String bmbm);

	/**
	 * 删除某一部门下的人员角色分配信息
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 */
	void deleteRyjsfp(@Param("dwbm") String dwbm, @Param("bmbm") String bmbm);
	
	/**
	 * 查询某单位下的所有部门信息【统一业务同步部门表+综合业务库部门表】
	 * @param dwbm 单位编码
	 * @param sfsc 是否删除的标识，“Y” 已删除，“N” 未删除
	 */
	void selectAllBmInfo(Map<String,Object> map);
	
	/**
	 * 根据单位编码、部门编码获取部门信息
	 * @param map 
	 */
	void getBmInfoByDwBm(Map<String,Object> map);
	
	void getRyBmByDwGh(Map<String,Object> map);
	
	void getRyJsByDwBm(Map<String,Object> map);
	
	void getRyByDwBmJs(Map<String,Object> map);
	
}
