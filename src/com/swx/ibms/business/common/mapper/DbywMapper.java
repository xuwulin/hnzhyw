package com.swx.ibms.business.common.mapper;

import com.swx.ibms.business.common.bean.DBYW;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 王宇锋
 */
public interface DbywMapper {
	/**
	 * 查询待办业务
	 * @param map 传递参数
	 */
	void getDbyw(Map<String,Object> map);
	/**
	 * 分页查询所有待办业务
	 * @param map 传递参数
	 */
	void getDbywAll(Map<String,Object> map);
	/**
	 * 查询待办业务行数
	 * @param map 传递参数
	 */
	void getDbywRows(Map<String,Object> map);
	
	/**
	 * 查询某人或者某部门的所有待审批信息
	 * @param spzt 
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @param gh 工号
	 * @param clnr 
	 * @return
	 */
	List<DBYW> getAllSpInfoByDwbmGhOrBm(@Param("spzt")String spzt,
										@Param("dwbm")String dwbm,
										@Param("bmbm")String bmbm,
										@Param("gh")String gh,
										@Param("clnr")String clnr);
	
}