package com.swx.ibms.business.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


/**
 * 司法档案内容统计Mapper接口
 * @author 李佳
 */
public interface SyCountMapper {
	/**
	 * 根据归档id统计平均案件办理时长
	 * @param map map对象        
	 */
     void pjajBlsc(Map<String, Object> map);
	/**
	 * 根据归档id统计全院案件平均办理时长
	 * @param map map对象   
	 */
	void qypjajBlsc(Map<String, Object> map);	

	/**
	 * 根据归档id统计档案的个人绩效得分   
	 * @param map map对象    
	 */
	 void countGrjx(Map<String, Object> map);
	 
	/**
	 * 根据归档id、档案类型查询档案中不同类别(司法技能、司法责任...)的数量
	 * @param map map对象           
	 */
     void countSfda(Map<String, Object> map);
      
     /**
 	 * 根据当前登录人员工号、所属人单位编码查询最新的档案是否公示
 	 * @param map map对象
 	 */
	void sfGs(Map<String, Object> map);


	/**
	 * 根据当前登录人员工号、所属人单位编码查询该登录人员是否有司法档案
	 * @param map map对象
	 */
	void sfySfda(Map<String, Object> map);
	
	/**
	 * 根据归档id 计算个人绩效得分
	 * @param gdid 归档id
	 * @return 得分
	 */
	double grjxDf(String gdid);

	/**
	 * 根据工号、所属人单位编码查询此人最新档案的归档id
	 * @param map map对象
	 */
	void getGdid(Map<String, Object> map);

	/**
 	 * 根据工号、所属人单位编码、档案开始时间、档案结束时间 查询归档id
 	 * @param map map对象
 	 */
	void getGdidBySj(Map<String, Object> map);
	/**
 	 * 根据归档id查询档案归总信息
 	 * @param map map对象
 	 */
	void getDagzByGdid(Map<String, Object> map);
	
	/**
	 * 根据归档id和操作类型查询出公示信息
	 * @param map map对象
	 */
	void getGsJzSj(Map<String, Object> map);
	
	/**
	 * 根据ywlx查询khlx
	 */
	List<String> getKhlx(@Param("ywlxList") List<String> ywlxList);
	
	/**
	 * 首页：查询业务考核的时间段:开始时间，结束时间
	 * 先根据单位编码到yx_ywgz_kh表查询id,再根据id到yx_ywgz_ywlxkh表查询时间段
	 * @param dwbm
	 */
	List<Map<String, Object>> getYwkhData(@Param("dwbm") String dwbm,@Param("khlxList") List<String> khlxList);

	String getSfgs(@Param("daId") String daId);
}