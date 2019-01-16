package com.swx.ibms.business.rbac.service;

import com.swx.ibms.business.rbac.bean.JSBM;
import com.swx.ibms.common.utils.PageCommon;

import java.util.List;


/**
 * 
 * @author east
 * @date:2017年4月14日
 * @version:1.0
 * @description:角色service接口
 *
 */
public interface RoleService{
	/**
	 * 根据单位编码查询此单位下的所有未删除的角色信息
	 * @param dwbm 页面获取
	 * @param sfsc 是否删除的标示
	 * @param nowPage 当前页
	 * @param pageSize 每页显示数
	 * @return 此单位下面的所有角色信息
	 */
	PageCommon<JSBM> selectPageList(String dwbm, String sfsc, int nowPage, int pageSize);
	
	/**
	 * 根据单位编码、部门编码查询所有未删除的角色信息
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @param sfsc 是否删除
	 * @return 此单位下部门中的所有角色信息
	 */
	List<JSBM> selectZhywRoleList(String dwbm, String bmbm,String sfsc);
	
	/**
	 * 添加某单位下的角色信息
	 * @param jsbm 传入某单位下的角色信息
	 * @return 操作成功与否的字符串:返回"1"为成功 	 ""为失败
	 */
	String insertData(JSBM jsbm);
	
	/**
	 * 修改 某单位下的角色信息
	 * @param jsbm 传入某单位下的角色信息，主要是部门编码、单位编码
	 * @return 操作成功与否的字符串:返回"1"为成功 	 ""为失败
	 */
	String updateData(JSBM jsbm);
	
	/**
	 * 删除某单位下的角色信息
	 * @param jsbm 传入某单位下的角色信息，主要是部门编码、单位编码、角色编码
	 * @return 操作成功与否的字符串:返回"1"为成功 	 ""为失败
	 */
	 String deleteData(JSBM jsbm);
}
