package com.swx.ibms.business.rbac.service;


import com.swx.ibms.business.rbac.bean.JSBM;
import com.swx.ibms.business.rbac.bean.RYBM;

import java.util.List;

/**
 * 登录服务接口
 * @author 李治鑫
 *
 */
public interface LoginService {
	
	/**
	 * 通过单位编码，用户名获取人员编码信息
	 * @param dwbm 单位编码
 	 * @param yhm 用户名
	 * @return 人员编码信息
	 */
	List<RYBM> getRyByGzzh(String dwbm, String yhm);
	
	/**
	 * 通过单位编码，工号获取部门角色信息
	 * @param dwbm 单位编码
 	 * @param gh 工号
	 * @return 角色编码信息
	 */
	List<JSBM> getgetBmJs(String dwbm, String gh);
	
	/**
	 * 根据登录别名和单位编码查询登录人的工号，再结合单位编码查询管理员表，该登录人是不是超级管理员
	 * @param dwbm 单位编码
	 * @param dlbm 登录别名（登录用户名）
	 * @return 字符串"Y"是管理员 ，"N"不是管理员
	 */
	String isOrNotAdmin(String dwbm,String dlbm);
	
	/**
	 * 根据登录工号和单位编码查询登录人所有部门、所有角色 
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @return 返回登录人信息结果集 字符串
	 */
	List<String> getGrxxByDwbmAndGh(String dwbm,String gh);

    String getYhm(String dwbm, String gh);

    List<RYBM> getRybmInfoByDwGh(String dwbm, String gh);
} 
