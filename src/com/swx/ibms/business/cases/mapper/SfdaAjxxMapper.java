package com.swx.ibms.business.cases.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * SfdaAjxxMapper.java 司法档案下的案件信息 Mapper接口
 * @author 何向东
 * @date<p>2017年8月3日</p>
 * @version 1.0
 * @description<p></p>
 */
public interface SfdaAjxxMapper {
	/**
	 * 查询所有司法档案下的检察官主办案件信息
	 * @param map 档案id、当前页、每页显示数 
	 */
	void getSfdaZbAjxxPageList(Map<String,Object> map);
	
	/**
	 * 查询所有司法档案下的检察官参办/从办/协办案件信息
	 * @param map 档案id、当前页、每页显示数 
	 */
	void getSfdaCbAjxxPageList(Map<String,Object> map);
	
	/**
	 * 查询单条司法档案下的检察官案件信息根据id
	 * @param map 案件信息id
	 */
	void getSfdaAjxxById(Map<String,Object> map);
	/**
	 * 添加司法档案下的检察官案件信息
	 * @param map 
	 */
	void addSfdaAjxx(Map<String,Object> map);
	/**
	 * 修改司法档案下的检察官案件信息
	 * @param map 
	 */
	void updateSfdaAjxx(Map<String,Object> map);
	/**
	 * 删除司法档案下的检察官案件信息
	 * @param map 
	 */
	void deleteSfdaAjxx(Map<String,Object> map);
	
	/**
	 * 根据档案id修改此档案对应的案件信息的状态 
	 * @param map 
	 */
	void updateStatusByDaId(Map<String,Object> map);
	
	Integer modifyStatusByAjId(@Param("ajStatus")String ajStatus,
							   @Param("ajId")String ajId);
}
