package com.swx.ibms.business.rbac.mapper;

import com.swx.ibms.business.rbac.bean.RYBM;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 登录数据访问接口
 * @author 李治鑫
 *
 */
public interface LoginMapper {
	/**
	 * 根据登录别名获取人员信息
	 * @param map 结果集
	 * */
	void getRyByGzzh(Map<String,Object> map);
	
	/**
	 * 根据CA证号获取人员信息
	 * @param map 结果集
	 * */
	void getRyByCaid(Map<String,Object> map);
	/**
	 * 得到部门角色
	 * @param map 结果集
	 */
	void getbmjs(Map<String,Object> map);
	
	/**
	 * 根据登录别名和单位编码查询登录人的工号，再结合单位编码查询管理员表，该登录人是不是超级管理员
	 * @param map 结果集
	 */
	void isOrNotAdmin(Map<String,Object> map);
	
	/**
	 * 根据登录工号和单位编码查询登录人所有部门、所有角色 
	 * @param resultMap 参数集合
	 */
	void getGrxxByDwbmAndGh(Map<String, Object> resultMap);

    void getYhm(Map<String, Object> resultMap);

    List<RYBM> getRybmInfoByDwGh(@Param("dwbm")String dwbm, @Param("gh")String gh);
}
