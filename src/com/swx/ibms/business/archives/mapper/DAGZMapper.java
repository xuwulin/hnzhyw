package com.swx.ibms.business.archives.mapper;

import java.util.Map;

/**
 * @author 朱春雨
 *档案归总
 */
public interface DAGZMapper {
	/**
	 * @param map 通过归档id判断splcsl里面是否有待审批
	 */
	void sfyDsp(Map<String,Object> map);
	/**
	 * @param map 通过审批实体id和审批类型查出yx_sfda_splcsl数据
	 */
	void showSplcsl(Map<String,Object> map);
	/**
	 * @param map 所属人的档案数
	 */
	void ssrDagzCount(Map<String,Object> map);
	/**
	 * @param map	删除档案归总
	 */
	void deleteDagz(Map<String,Object> map);
	/**
	 * @param map 通过归档id和删除各种类型的档案
	 */
	void deleteDaDalx(Map<String,Object> map);
	/**
	 * @param map 该办案质量中在表中的数量
	 */
	void sfBazlCount(Map<String,Object> map);
	/**
	 * @param map 该类型的司法档案数量
	 */
	void sfDaCount(Map<String,Object> map);
	/**
	 * @param map 该dalx的的档案的数量
	 */
	void spstidCount(Map<String,Object> map);
	/**
	 * 插入数据到DAGZ
	 * @param map 
	 */
	void insertDAGZData(Map<String,Object> map);
	
	/**
	 * 查询档案所属人的信息
	 * @param map 
	 */
	void sFYZJ(Map<String,Object> map);
	/**
	 * 查询档案所属人的信息年份
	 * @param map 
	 */
	void sFYZJNF(Map<String,Object> map);

}
