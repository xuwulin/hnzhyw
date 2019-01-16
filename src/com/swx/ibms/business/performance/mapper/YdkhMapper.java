package com.swx.ibms.business.performance.mapper;


import com.swx.ibms.business.performance.bean.Ydkh;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 李佳 月度考核接口
 */
public interface YdkhMapper {

	/**
	 * 通过单位编码和工号查出月度考核表里的最新年月
	 * @param map 参数map
	 */
	void getnewym(Map<String, Object> map);
	/**
	 * 通过单位编码、工号、年、月查找出月度考核ID
	 * @param map 参数map
	 */
	void selectYdkhid(Map<String, Object> map);

	/**
	 * 通过单位编码和工号查出季度考核表里的最新年和季度
	 * @param map 参数map
	 */
	void getnewyj(Map<String,Object>map);

	/**
	 * 通过单位编码、工号、年、季度查找出季度考核ID
	 * @param map 参数map
	 */
	void selectJdkhid(Map<String,Object>map);

	/**
	 * 通过月度考核ID和业务类型更新业务考核分值信息
	 * @param map 参数map
	 */
	void updateYwkhfz(Map<String, Object> map);
	/**
	 * 通过单位编码、工号、年份、月份、业务类型获取业务考核分值信息
	 *
	 * @param map 参数map
	 *
	 */
	void getGrjx(Map<String, Object> map);

	/**
	 * 判断某年某月是否已经建立月度考核信息
	 *
	 * @param map 参数map
	 */
	void isCreateinfo(Map<String, Object> map);

	/**
	 * 根据月度考核ID计算该月的月度考核总分
	 * @param map 参数map
	 */
	void calculateydkhzf(Map<String,Object>map);

	/**
	 * 根据月度考核ID更新月度考核总分
	 * @param map 参数map
	 */
	void updateydzfbyid(Map<String,Object> map);

	/**
	 * 通过单位编码、工号、年份、月份获取此人在该月完成案件的部门受案号列表
	 *
	 * @param map 参数map
	 */
	void getBmsah(Map<String, Object> map);

	/**
	 * 新增月度考核信息
	 *
	 * @param map 参数map
	 */
	void addYdkh(Map<String, Object> map);

	/**
	 * 新增业务考核分值信息
	 *
	 * @param map 参数map
	 */
	void addYwkhfz(Map<String, Object> map);


	/**新增评分人记录信息
	 * @param map 参数map
	 */
//	void addpfrjl(Map<String,Object> map);

	/**查询评分人记录信息
	 * @param map 参数map
	 */
	void getpfrjl(Map<String,Object> map);

	/**更新评分人记录信息
	 * @param map 参数map
	 */
	void updatepfrjl(Map<String,Object> map);

	/**根据单位编码和工号以及年和月查询姓名和分数
	 * @param map 参数map
	 */
	void getNameAndScore(Map<String, Object> map);

	/**
	 * 添加一个空的评分材料记录信息
	 * @param map 参数map
	 */
	void addPfcl(Map<String, Object> map);

	/**
	 * 获取评分材料记录信息
	 * @param map 参数map
	 */
	void getbz(Map<String, Object> map);

	/**
	 * 查询评分材料记录的ID
	 */
	List<Map<String, Object>> selectPfcl(@Param("ywkhfzid") String ywkhfzid,
										 @Param("pflx") String pflx,
										 @Param("zbxbm") String zbxbm);

	/**
	 * 更新评分材料记录信息
	 * @param map 参数map
	 */
	void updatePfcl(Map<String, Object> map);
	/**
	 * 根据单位编码获取个人绩效表表头
	 * @param map 参数map
	 */
	void getkhbt(Map<String,Object> map);
	/**
	 * 从pz表中读取评分子项目的配置
	 * @param map 参数map
	 */
	void getZbpz(Map<String,Object> map);
	/**
	 * 根据单位编码,工号,年,季度,业务类型查找有无数据信息
	 * @param map 参数map
	 */
	void getYdkhfzcount(Map<String,Object> map);
	/**
	 * 根据单位编码,工号,年,季度,业务类型查找月度考核分值表中表头信息
	 * @param map 参数map
	 */
	void getBtfromywkhfz(Map<String,Object> map);
	/**
	 * 获得月度考核id
	 * @param map 参数map
	 */
	void getYdkhid(Map<String,Object> map);
	/**
	 * 获得反射类名
	 * @param map 参数map
	 */
	void getFslm(Map<String,Object> map);




	/**
	 * 查询是否有发起审批的信息
	 * @param map 审批实体id p_spstid
	 */
	void sffqsp(Map<String,String> map);

	/**
	 * 删除个人绩效的阅读考核的信息
	 * @param map 审批实体id p_spstid
	 */
	void delydkh(Map<String,Object> map);

	/**
	 * 根据p_ydkhid 查询 月度考核分值,可能多个
	 * @param map
	 */
	void sydkhfz(Map<String,Object> map);

	/**
	 * 根据月度考核分值id 查询 评分材料
	 * @param map
	 */
	void spfcl(Map<String,Object> map);

	/**
	 * 根据月度考核分值id 删除 评分材料
	 * @param map
	 */
	void dpfcl(Map<String,Object> map);

	/**
	 * 通过单位编码 工号查询个人绩效的年度季度
	 * @param map
	 */
	void getndjdlist(Map<String,Object> map);


	/**
	 * 根据月度考核id查询月度考核信息
	 * @param map 参数集
	 */
	void getYdkhById(Map<String,Object> map);

	/**
	 * 根据单位编码、工号以及当前个人绩效的季度时间、案件类别编码获取当前人的案件个数以及案件情况信息
	 * @param map 单位、工号、个人绩效所属季度日期、案件类别编码、类型【1 查询指定案件类别编码、2 查询不指定的案件类别编码案件】
	 */
	void getAjxxByDwGhRqAjlb(Map<String,Object> map);

	int insertSelective(Ydkh record);

	Ydkh selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Ydkh record);

	int deleteByPrimaryKey(String id);

	Ydkh getGrjxByParams(@Param("dwbm") String dwbm,
						 @Param("gh") String gh,
						 @Param("year") Integer year,
						 @Param("ksrq") Date ksrq,
						 @Param("jsrq") Date jsrq,
						 @Param("sfgs") Date sfgs,
						 @Param("sfsp") Date sfsp);
}
