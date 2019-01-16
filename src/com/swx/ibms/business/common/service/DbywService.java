package com.swx.ibms.business.common.service;

import com.swx.ibms.business.common.bean.DBYW;

import java.util.List;
import java.util.Map;



/**
 * 查询待办业务服务接口
 * @author 王宇锋
 * @since 2017年3月2日
 */
public interface DbywService {
	/**
	 * 获得待办业务列表
	 * @param spdw 审批单位
	 * @param spr 审批人工号
	 * @param dlrbm 登录人编码
	 * @param bmjs 部门角色
	 * @return 代办业务列表
	 */
	List<DBYW> getDbywList(String spdw, String spr, String dlrbm, List<String> bmjs);
	/**
	 * 获得DBYW列表和查询行数
	 * @param spdw 审批单位编码
	 * @param spr 审批人
	 * @param dlrbm 登录人编码
	 * @param page 查询页数
	 * @param bmjs 部门角色
	 * @param clnr 名称
	 * @param splx 审批类型
	 * @param sfdb 是否待办
	 * @return 返回DBYW列表和查询行数
	 */
	Map<String,Object> getAllDbywFy(String spdw,String spr, String dlrbm
			,int page,List<String> bmjs,String clnr,String splx,String sfdb);
	/**
	 * 拼接条件sql语句
	 * @param clnr 名称 模糊搜索
	 * @param splx 审批类型
	 * @return String 拼接的sql语句
	 */
	String cxtj(String clnr,String splx);
	
	Map<String, Object> getAllSpInfoByDwbmGhOrBm(String dwbm,String bmbm,String gh,String spzt,String clnr,Integer page,Integer pageSize);
	
}
