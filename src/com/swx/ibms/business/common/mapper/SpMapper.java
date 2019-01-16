package com.swx.ibms.business.common.mapper;


import com.swx.ibms.business.common.bean.Splcsl;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *<p>Title:SpMapper</p>
 *<p>Description: </p>
 *author 朱春雨
 *date 2017年3月18日 下午11:03:47
 */
public interface SpMapper {

	/**
	 * 查询最新的审批流程实例
	 * @param map 参数map
	 */
	void selectSpsl(Map<String,Object> map);

	/**
	 * 通过流程ID查询最新的审批流程实例
	 * @param map 参数map
	 */
	void selectSpslByLcid(Map<String,Object> map);
	/**
	 * 增加审批实例
	 * @param map 参数map
	 */
	void addSpsl(Map<String,Object> map);
	/**
	 * 第一次增加审批实例
	 * @param map 参数map
	 */
	void addSpslFirst(Map<String,Object> map);
	/**
	 * 更改审批实例
	 * @param map 参数map
	 */
	void updateSpsl(Map<String,Object> map);
	/**
	 * 通过jdlx在YX_SFDA_JDLC查数据
	 * @param map 参数map
	 */
	void selectJdlc1(Map<String,Object> map);
	/**
	 * 通过jdlx,lcjd在YX_SFDA_JDLC查数据
	 * @param map 参数map
	 */
	void selectJdlc2(Map<String,Object> map);

	/**
	 * 根据单位编码、工号、年份、月份将月度考核信息置为已审批完成状态
	 * @param map 参数map
	 */
	void setAudit(Map<String,Object> map);

	/**
	 * 更改档案归总的公示状态
	 * @param map 参数map
	 */
	void updateGszt(Map<String,Object> map);

	/**
	 * 根据月度考核ID查询这个月度考核的业务类型和名称
	 * @param map 参数map
	 */
	void getYwlxByYdkhid(Map<String,Object> map);

	/**根据业务编码查询业务简称
	 * @param map 参数map
	 */
	void getYwmcByYwbm(Map<String,Object>map);

	/**根据部门映射编码查询业务编码
	 * @param map 参数map
	 */
	void getYwlxByBmys(Map<String,Object>map);

	/**
	 * 根据单位编码和工号获取部门映射编码
	 * @param map 参数map
	 */
	void getBmysByGh(Map<String,Object> map);

	/**根据审批实体ID和审批类型获取所有审批实例
	 * @param map 参数map
	 */
	void getAllBySplx(Map<String,Object> map);

	/**
	 * 根据审批流程实例id删除该条信息
	 * @param map 参数map
	 */
	void deleteSpslById(Map<String,Object> map);

	/**
	 * 根据审批流程实例ID修改部分数据
	 * @param map 参数map
	 */
	void updateSpslById(Map<String,Object> map);

	/**
	 * 根据审批类型和归档id查询审批是否通过
	 * @param map 参数map
	 */
	void isSptg(Map<String,Object> map);

	/**
	 * 获取审批流程的发起人
	 * @param map 参数map
	 */
	void getFqr(Map<String,Object> map);

	/**
	 * 根据审批id查询审批信息
	 * @param map 参数审批id
	 */
	void getSpById(Map<String, Object> map);

	/**
	 * 通过部门映射获取部门编码
	 * @param map 参数map
	 */
	void getBmbmByBmys(Map<String,Object> map);

	/**
	 * 通过审批实体ID和审批类型获取上一个审批流程实例的审批状态
	 * @param map 参数map
	 */
	void getPreviousLcslBySpstid(Map<String,Object> map);

	/**
	 * 通过单位编码和部门编码获取部门映射
	 * @param map 参数map
	 */
	void getBmysByBmbm(Map<String,Object> map);

	/**
	 * 通过单位编码和工号获取部门编码
	 * @param map 参数map
	 */
	void getBmbmByGh(Map<String,Object> map);

	/**
	 * 通过审批实体id和审批类型查询发起记录信息
	 * @param spstid 审批实体id
	 * @param splx 审批类型
	 * @return 发起记录
	 */
	Splcsl getFqjlBySpstidAndSplx(@Param("spstid")String spstid,
								  @Param("splx")String splx);

	List<Splcsl> getLartestSpxx(@Param("spstid")String spstid,
								@Param("splx")String splx);

	Integer modifySplcslById(@Param("spzt")String spzt,
							 @Param("spyj")String spyj,
							 @Param("cljs")String cljs,
							 @Param("spId")String spId);

	String getSpztByWbidAndSplx(@Param("spstid")String spstid,@Param("splx") String splx);

	List<Splcsl> getSpxxByWbidAndSplx(@Param("spstid")String spstid,@Param("splx") String splx);
}
