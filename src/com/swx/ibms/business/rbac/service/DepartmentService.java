package com.swx.ibms.business.rbac.service;

import com.swx.ibms.business.rbac.bean.BMBM;
import com.swx.ibms.business.rbac.bean.DWBM;
import com.swx.ibms.business.rbac.bean.Department;
import com.swx.ibms.business.rbac.bean.JSBM;
import com.swx.ibms.common.utils.PageCommon;

import java.util.List;
import java.util.Map;



/**
 * 
 * @author east
 * @date:2017年4月19日
 * @version:1.0
 * @description:部门service
 *
 */
public interface DepartmentService {
	/**
	 * 根据单位编码查询此单位下的部门信息
	 * @param dwbm 单位编码
	 * @param sfsc 是否删除
	 * @return 此单位下所有部门信息
	 */
	List<BMBM> selectZhywList(String dwbm, String sfsc);
	
	
	/**
	 * 根据单位编码查询此单位下的部门信息并分页
	 * @param dwbm 单位编码
	 * @param sfsc 是否删除的标示
	 * @param nowPage 当前页
	 * @param pageSize 每页显示数
	 * @return 此单位下所有部门信息
	 */
	PageCommon<BMBM> selectPageList(String dwbm, String sfsc, int nowPage, int pageSize);
	
	/**
	 * 添加某单位下的部门信息
	 * @param bmbm 部门对象
	 * @return 是否成功的字符串
	 */ 
	String insertData(BMBM bmbm);
	
	
	/**
	 * 根据单位编码和部门编码删除此单位下的部门
	 * @param bmbm 部门对象
	 * @return 是否成功的字符串
	 */
	 String deleteData(BMBM bmbm);
	
	
	/**
	 * 根据单位编码和部门编码、未删除标示修改此单位下的部门
	 * @param bmbm 部门对象
	 * @return 是否成功的字符串
	 */
	String updateData(BMBM bmbm);
	
	
	/**
	 * 根据单位编码、是否删除的标示查询单位信息
	 * @param dwbm 单位编码
	 * @return 单位信息
	 */
	DWBM selectDwbm(String dwbm);


	/**
	 * @param array 部门角色
	 * @param dwbm 单位编码 
	 * @param bmmc 部门名称
	 */
	void addbmjs(String[] array, String dwbm, String bmmc);


	/**
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @return 部门角色信息
	 */
	List<JSBM> getJs(String dwbm, String bmbm);


	/**
	 * @param dwbm  单位编码
	 * @param bmbm 部门编码
	 * @param ddt 角色信息
	 */
	void updatebmjs(String dwbm, String bmbm, String ddt);
	
	/**
	 * 查询某单位的所有部门
	 * @param dwbm 单位编码
	 * @param sfsc 是否删除的标识，‘N’未删除，‘Y’已删除
	 * @return 部门集合
	 */
	List<Department> selectAllBmInfo(String dwbm, String sfsc);
	
	/**
	 * 根据单位编码、部门编码获取部门信息
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @return 部门结果集
	 */
	List<Department> getBmInfoByDwBm(String dwbm,String bmbm);
	
	List<Map<String,Object>> getRyBmByDwGh(String dwbm,String gh);
	
	List<Map<String,Object>> getRyJsByDwBm(String dwbm,String bmbm);
	
	List<Map<String,Object>> getRyByDwBmJs(String dwbm,String bmbm,String jsbm);
	
}
