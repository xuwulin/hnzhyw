package com.swx.ibms.business.performance.service;


import com.swx.ibms.business.performance.bean.GrjxZbpz;
import com.swx.ibms.common.utils.PageCommon;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author east
 * @date:2017年4月13日
 * @version:1.0
 * @description:个人绩效指标配置Service
 *
 */
@SuppressWarnings("all")
public interface GrjxZbpzService {
	
	/**
	 * @param t
	 * 单位级别
	 * @return
	 * 部门列表
	 */
	PageCommon<Object> selectList(Integer dwjb, Integer page, Integer pageSize);

	/**
	 * @param id
	 * id
	 * @return
	 * 根据id查找指标配置
	 */
	GrjxZbpz selectById(String id);

	/**
	 * @param map
	 * map
	 * @return
	 * 插入数据
	 */
	String insertData(Map<String, Object> map);

	/**
	 * @param map
	 * map
	 * @return
	 * 更新指标配置
	 */
	String updateData(Map<String, Object> map);

	/**
	 * @param map
	 * map
	 * @return
	 * 删除指标配置
	 */
	String deleteData(Map<String, Object> map);

	/**
	 * @param id
	 * id
	 * @return
	 * 根据id查找指标配置
	 */
	String selectBtById(String id);

	/**
	 * @param tabletop
	 * 表头数据
	 * @param cjr
	 * 创建人
	 * @param khsjdata
	 * 考核时间
	 * @return
	 * 插入成功与否
	 */
	String insertBt(String tabletop, String cjr, String khsjdata);

	/**
	 * @param lastid
	 * 将状态修改为0，标识不可用
	 */
	void updateStatus(String lastid);

	/**
	 * @param ywlb 业务类别
	 * @param dwjb 单位级别
	 * @param khnrdata 考核内容
	 * @param pznrdata 配置内容
	 * @param topid 表头id
	 * @param dlbm 创建人
	 * @param pjf 评价分
	 * @return 指标配置id
	 * @param rylb 人员类别
	 * @param ssYear 所属年份
	 */
	String insert(String ywlb, int dwjb, String khnrdata, String pznrdata,
                  String topid, String dlbm, String pjf, String rylb, String ssYear);


	/**
	 * 根据业务类别和单位级别判断是否已经存在配置记录
	 * @param ywlb 业务类别
	 * @param dwjb 单位级别
	 * @param type 人员类型
	 * @param year 年份
	 * @return 记录条数
	 */
	int isExist(String ywlb, String dwjb, String type, String year);
	
	/**
	 * 获取业务编码表信息
	 * @param ywbm 参数：业务编码
	 * @return 业务编码结果集
	 */
	List<Map<String,Object>> getYwlxList(String ywbm) throws Exception;

	/**
	 * 根据单位级别，部门类别，人员类别，所属年份获取指标配置详细内容，包括表头，表体，评分部分等
	 * @param dwjb
	 * @param bmlb
	 * @param rylb
	 * @param ssnf
	 * @return
	 */
	List<Map<String, Object>> getInfoOfZbpz(String dwjb, String bmlb, String rylb, String ssnf);
}
