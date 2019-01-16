package com.swx.ibms.business.appraisal.service;


import com.swx.ibms.business.appraisal.bean.Ywgzkh;
import com.swx.ibms.business.common.bean.PersonTree;
import com.swx.ibms.business.rbac.bean.DWBM;

import java.util.List;
import java.util.Map;

/**
 * 
 * YwgzkhSpService.java  业务工作考核service
 * @author east
 * @date<p>2017年6月5日</p>
 * @version 1.0
 * @description<p></p>
 */
public interface YwgzkhSpService {
	
	/**
	 * 省院案件管理处创建市院、基层院的年度业务工作考核
	 * @param ywgzkh 业务考核实体
	 * @return 是否成功得字符串
	 */
	String insertNdYwgzkh(Ywgzkh ywgzkh) throws Exception;
	
	/**
	 * 业务部门领导指定考核人，用到的参数有业务类型考核id、发起人部门编码、发起人工号、发起人姓名、考核人部门编码、考核人工号、考核人名称
	 * @param map 参数集合
	 * @return 是否成功的字符串
	 */
	String addYwgzkhSpr(Map<String,Object> map);
	
	/**
	 * 根据业务工作业务类型年度考核id修改状态值
	 * @param map 参数集合
	 * @return 是否成功的字符串
	 */
	String updateYwgzkhSpZt(Map<String,Object> map);
	
	/**
	 * 第一次审批开始，存入审批意见、审批状态
	 * @param map 参数集合
	 * @return 是否成功的字符串
	 */
	String updateSplc(Map<String,Object> map);
	
	/**
	 * 
	 * @param khlx 
	 * @param dwbm 
	 * @param bmbm 
	 * @param gh 
	 * @return 部门树
	 */
	List<PersonTree> getBmkhry(String khlx, String dwbm,
							   String bmbm, String gh);
	
	/**
	 * 修改业务考核活动表的状态和异议说明
	 * @param map 参数：业务考核id，异议说明
	 * @return 字符串 
	 */
	String updateYwgzkhYYSpZt(Map<String,Object> map);
	
	/**
	 * 查询本部门所有人员
	 * @param map 参数结合：单位编码、部门编码、工号
	 * @return 全部人员 
	 */
	List<PersonTree> selectAllBmry(Map<String,Object> map);

	/**
	 * 根据id查询考核信息
	 * @param id 考核id
	 * @return 结果集
	 */
	Map<String,Object> getKhById(String id);
	
	/**
	 * 业务考核活动异议流程
	 * @param map 参数集合
	 * @return 标示字符串
	 */
	String updateYwgzYyLc(Map<String,Object> map);
	
	/**
	 * 根据单位编码查询此单位编码上级单位编码以及案管部门编码
	 * @param dwbm 单位编码
	 * @return 单位编码
	 */
	DWBM getSjDwbmAndAgByDwbm(String dwbm);
	
	/**
	 * 
	 * @param map 参数集合
	 * @return 是否成功字符串 
	 */
	String updateYwgzYyFqSpLc(Map<String,Object> map);
	
	
	/**异议修改业务类型考核表
	 * @param map 参数集合
	 * @return 是否成功字符串 
	 */
	String updateywlxkh(Map<String,Object> map);

	/**
	 * 删除业务考核【需要删除三张表（考核表、考核类型表、考核分指表）】
	 * @param dwbmArr 单位数组
	 * @param lxArr 类型数组
	 * @param year 年份
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 
	 */
	Integer deleteYwkh(List<String> dwbmArr,List<String> lxArr, String year, String startDate, String endDate);
	
	Integer delYwkhlxfz(String khlxId);
	
	Integer delYwkh(String kssj, String jssj,List<String> khidList);

	Integer delYwkhlx(String khlxid);

	List<Ywgzkh> getYwkhByDate(String ksrq, String jsrq);

}
