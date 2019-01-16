package com.swx.ibms.business.common.service;

import com.swx.ibms.business.archives.bean.CountSySfda;
import com.swx.ibms.business.archives.bean.DAGZGD;
import com.swx.ibms.business.archives.bean.Gsjl;

import java.util.List;
import java.util.Map;



/**
 * 司法档案内容统计接口
 * @author 李佳
 */
public interface SyCountService {

	
	/**
	 * 根据归档id统计平均案件办理时长
	 * @param gdid 归档id
	 * @return 个数
	 */
	String pjajBlsc(String gdid);
	
	/**
	 * 根据归档id统计全院案件平均办理时长
	 * @param gdid 归档id
	 * @return 个数
	 */
	int qypjajBlsc(String gdid);
	
	/**
	 * 根据归档id档案的个人绩效得分      
	 * @param gdid 归档id
	 * @return 得分
	 */
	double countGrjx(String gdid);
	
	/**
	 *根据归档id、档案类型查询档案中不同类别(司法技能、司法责任...)的数量
	 * @param gdid 归档id、
	 * @param dalx 档案类型（计算司法技能传1司法责任传2依此类推）
	 * @return list
	 */
	List<CountSySfda> countSfda(String gdid, String dalx);
	
	/**
	 * 根据当前登录人员工号、所属人单位编码查询最新的档案是否公示
	 * @param gh 当前登录人员工号
	 * @param ssrdwbm 所属人单位编码
	 * @return 是否公示
	 */
	String sfGs(String gh, String ssrdwbm);
	
	/**
	 * 根据当前登录人员工号、所属人单位编码查询该登录人员是否有司法档案
	 * @param gh 当前登录人员工号
	 * @param ssrdwbm 所属人单位编码        
	 * @return 是否有司法档案
	 * 
	 */
	int sfySfda(String gh, String ssrdwbm);
	
	/**
	 * 根据归档id 计算个人绩效得分
	 * @param gdid 归档id         
	 * @return 得分
	 */
	double grjxDf(String gdid);
	
	/**
	 * 根据工号、所属人单位编码查询此人最新档案的归档id
	 * @param gh 工号(gh)
	 * @param dwbm 单位编码(dwbm)  
	 * @param sffc 是否封存         
	 * @return 归档id
	 */
     String getGdid(String dwbm,String gh,String sffc);

 	/**
 	 * 根据工号、所属人单位编码、档案开始时间、档案结束时间 查询归档id
 	 * @param gh 工号(gh)、
 	 * @param dwbm 单位编码(dwbm)
 	 * @param kssj 档案开始时间(kssj)
 	 * @param jssj 档案结束时间(jssj)
 	 * @return 归档id
 	 */
	 String getGdidBySj(String gh, String dwbm, String kssj, String jssj);
	 
	/**
	 * 根据归档id查询档案归总信息
	 * @param gdid 归档id
	 * @return list
	 */
	List<DAGZGD> getDagzByGdid(String gdid);

	/**
	 * @param gdid  归档id
	 * @param czlx  操作类型
	 * @return 公示记录信息
	 */
	List<Gsjl> getGsJzSj(String gdid, String czlx);
	
	/**
	 * 首页：查询业务考核的时间段:开始时间，结束时间
	 * 先根据单位编码到yx_ywgz_kh表查询id,再根据id到yx_ywgz_ywlxkh表查询时间段
	 * @param dwbm
	 */
	 List<Map<String, Object>> getYwkhInfo(String dwbm,List<String> ywlxList);

	/**
	 * 查询档案是否公示
	 * @param daId
	 * @return
	 */
	 String getSfgs(String daId);
}
