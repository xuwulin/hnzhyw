package com.swx.ibms.business.archives.service;

import com.swx.ibms.business.common.bean.Splcsl;

import java.util.List;
import java.util.Map;




/**
 * @author 朱春雨
 *档案归总
 */
public interface DAGZService {
	/**
	 * @param spstid 审批实体id
	 * @param splx 审批类型
	 * @return 通过审批实体id和审批类型查出yx_sfda_splcsl数据集
	 */
	List<Splcsl> showSplcsl(String spstid, String splx);
	/**
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param sffc 是否封存
	 * @return 所属人的档案归总
	 */
	int ssrDagzCount(String dwbm,String gh,String sffc);
	/**
	 * 删除档案归总
	 * @param gdid 归档id
	 * @param grjbxxId 个人基本信息id
	 * @return 返回档案是否删除
	 */
	boolean deleDagz(String gdid,String grjbxxId);
	/**
	 * @param bmsah 通过bmsah查出数量
	 * @return 返回该办案质量的数量
	 */
	int sfBazlCount(String bmsah);
	/**
	 * @param gdid 归档id
	 * @param dalx 档案类型
	 * @return 数量
	 */
	int sfDaCount(String gdid,String dalx);
	/**
	 * @param spstid 审批实体id
	 * @param splx 档案类型
	 * @return 返回数量
	 */
	int spstidCount(String spstid,String splx);

	/**
	 * @param map 插入数据到DAGZ
	 * @return 返回插入是否成功
	 */
	String insertDAGZData(Map<String, Object> map);
	
	
	/**
	 * @param map 查询是否有自己的档案
	 * @return  是否有自己的档案
	 */
	String sFYZJ(Map<String, Object> map);
	
	/**
	 * @param map 条件
	 * @return 查询是否有自己的档案年份
	 */
	String sFYZJNF(Map<String, Object> map);

}

