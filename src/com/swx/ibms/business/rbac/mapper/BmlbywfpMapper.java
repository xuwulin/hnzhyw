package com.swx.ibms.business.rbac.mapper;

import java.util.Map;

/**
 * 部门类别业务分配Mapper类
 * BmlbywfpMapper.java 
 * @author 何向东
 * @date<p>2017年12月12日</p>
 * @version 1.0
 * @description<p></p>
 */
public interface BmlbywfpMapper {
	
	
	/**
	 * //条件查询、查询全部并分页
	 * @param map 查询条件集合
	 */
	void ywfpSelectPageList(Map<String,Object> map);
	
	
	/**
	 * //根据id查询单条信息
	 * @param map 业务分配id
	 */
	void getYwfpListById(Map<String,Object> map);
	
	
	/**
	 * //根据id修改单条信息
	 * @param map 业务分配id
	 */
	void modifyYwfpById(Map<String,Object> map);
	
	
	/**
	 * //根据id删除单条信息
	 * @param map 业务分配id
	 */
	void delYwfpById(Map<String,Object> map);
	
	
	/**
	 * //添加信息
	 * @param map 业务分配数据信息
	 */
	void addYwfp(Map<String,Object> map);
	
}
