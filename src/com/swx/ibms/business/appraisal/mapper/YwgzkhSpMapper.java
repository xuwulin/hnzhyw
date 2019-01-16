package com.swx.ibms.business.appraisal.mapper;

import com.swx.ibms.business.appraisal.bean.Ywgzkh;
import com.swx.ibms.business.appraisal.bean.Ywgzlxkh;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;



/**
 * 
 * YwghkhSpMapper.java 业务工作考核审批mapper
 * @author east
 * @date<p>2017年6月5日</p>
 * @version 1.0
 * @description<p>省院的案管才能创建考核、对市院、区院发起审批</p>
 */
public interface YwgzkhSpMapper {
	
	/**
	 * 省院案件管理处创建市院、基层院的年度业务工作考核
	 * @param map 参数集合
	 */
	void insertNdYwgzkh(Map<String,Object> map);
	
	/**
	 * 业务部门领导指定考核人，用到的参数有业务类型考核id、发起人部门编码、发起人工号、发起人姓名、考核人部门编码、考核人工号、考核人名称
	 * @param map 参数集合
	 */
	void addYwgzkhSpr(Map<String,Object> map);
	
	
	/**
	 *  根据业务工作业务类型年度考核id修改状态值,即：更新YX_YWGZ_YWLXKH的状态和修改时间
	 * @param map 参数集合，业务工作业务类型年度考核id
	 */
	void updateYwgzkhSpZt(Map<String,Object> map);
	
	/**
	 * 第一次审批开始，存入审批意见、审批状态
	 * @param map 参数集合
	 */
	void updateSplc(Map<String,Object> map);

	/**
	 * 指定考核人员，获取某个单位的部门下属人员列表
	 * @param map 
	 */ 
	void selectBmkhry(Map<String, Object> map);
	
	/**
	 * 修改业务考核表的状态和异议说明
	 * @param map 参数集合
	 */
	void updateYwgzkhYYSpZt(Map<String, Object> map);
	
	/**
	 * 查询本部门下所有人员
	 * @param map 参数集合
	 */
	void selectAllBmry(Map<String,Object> map);

	/**
	 * 根据id查YX_YWGZ_KH记录
	 * @param id 业务考核id查询业务考核信息
	 * @return 结果集
	 */
	Map<String,Object> getKhById(String id);
	
	
	/**
	 * 年度业务考核活动异议审批
	 * @param map 参数集合
	 */
	void updateYwgzYyLc(Map<String,Object> map);
	
	
	/**
	 * 根据单位编码查询此单位编码上级单位编码以及案管部门编码
	 * @param map 结果集
	 */
	void getSjDwbmAndAgByDwbm(Map<String,Object> map);
	
	/**
	 * 异议申请流程成功以后对单位考核重新发起审批的流程
	 * @param map 结果集
	 */
	void updateYwgzYyFqSpLc(Map<String,Object> map);

	Integer delYwkh(@Param("kssj") String kssj, 
			 		@Param("jssj")String jssj,
			 		@Param("khidList") List<String> khidList);
	
	Integer delYwkhlx(@Param("khlxid") String khlxid);
	
	Integer delYwkhlxfz(@Param("ywlxid") String ywlxid);
	
	List<Ywgzkh> getYwkhByParams(@Param("year")String year,
								 @Param("kssj") String kssj,
								 @Param("jssj")String jssj,
								 @Param("khdwList")List<String> khdwList
								 );
	
	List<Ywgzlxkh> getYwkhlxByParams(@Param("kssj") String kssj,
									 @Param("jssj")String jssj,
									 @Param("khidList")List<String> khidList,
									 @Param("khlxList")List<String> khlxList
							 		);

	List<Ywgzkh> getYwkhByDate(@Param("kssj")String kssj, @Param("jssj")String jssj);
}
