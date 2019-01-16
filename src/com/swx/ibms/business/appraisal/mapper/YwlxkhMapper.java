package com.swx.ibms.business.appraisal.mapper;


import com.swx.ibms.business.appraisal.bean.YwlxkhZgjcf;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
*<p>Title:YwlxkhMapper</p>
*<p>Description: 业务类型考核mapper</p>
*author 朱春雨
*date 2017年6月5日 下午7:22:48
*/
public interface YwlxkhMapper {
	/**
	 * @param map 得到业务类型考核表
	 */
	void selectYwlxkh(Map<String,Object> map);
	/**
	 * @param map 得到
	 */
	void getcl(Map<String,Object> map);
	/**
	 * @param map 更新或插入评分材料记录信息 
	 */
	void upDatePfcl(Map<String,Object> map);
	/**
	 * @param map 更新业务类型考核分值
	 */
	void updateYwlxkhfz(Map<String,Object> map);
	/**
	 * @param map 更新或插入最高基础分
	 */
	void updateZgjcf(Map<String,Object> map);
	/**
	 * @param map 查询最高基础分list
	 * @return 集合
	 */
	List<YwlxkhZgjcf> selectZgjcf(Map<String,Object> map);
	/**
	 * @param map 查询考评概览基础分和最高评价分
	 */
	void selectKpglZbkpdfZgpjf(Map<String,Object> map);
	/**
	 * @param map 算出评价得分后，对yx_ywgz_ywlxkhfz进行更新
	 */
	void pjdfUpdateYwlxkhfz(Map<String,Object> map);
	/**
	 * @param map 通过单位级别和单位编码的前两位查出该省的此单位级别的单位信息
	 */
	void dwjbDwtwoDwbm(Map<String,Object> map);
	/**
	 * 查询考核总览
	 * @param map 
	 */
	void getkhzl(Map<String,Object> map);
	
	/**
	 * 根据当前登录人的单位编码、选中的单位编码、年份查询业务工作考核的单位信息
	 * @param map 参数集合 
	 */
	void selectDwkhByDwbmAndYear(Map<String,Object> map);
	
	/**
	 * 根据业务工作分值id查询业务工作分值信息
	 * @param map 参数集合
	 */
	void selectYwkhfzById(Map<String,Object> map);
	
	
	/**
	 * 根据业务类型考核分值表id修改业务类型考核分值表指标考评得分
	 * @param map 参数集合
	 */
	void updateZbkpdfById(Map<String,Object> map);
	
	/**
	 * 根据考核活动id修改考核活动表、业务类型考核表的状态
	 * @param map 参数集合
	 */
	void updateGbZtById(Map<String,Object> map);
	
	/**
	 * 根据登录人的单位编码、部门编码查询其所有的业务考核类型
	 * @param map 参数集合 
	 */
	void selectKhlxByDwbmAndBmbm(Map<String,Object> map);
	
	/**
	 * 查询当前登录案管的角色信息
	 * @param map 参数集合
	 */
	void getJsbmByDwbmAndGh(Map<String,Object> map);
	
	/**
	 * 根据id查询业务考核分值信息
	 * @param map 参数：业务考核id
	 */
	void selectKpglZbkpdfZgpjfById(Map<String,Object> map);
	
	/**
	 * 根据id查询业务考核分值信息、类型考核名称、单位考核状态
	 * @param map 参数：业务考核id
	 */
	void getYwkhfzById(Map<String,Object> map);
	
	/**
	 * 根据部门映射获取业务类型
	 * @param map 部门映射字符串
	 */
	void getYwlxByBmys(Map<String,Object> map);

    /**
     * 根据单位编码、业务类型、时间段查询、是否公示查询检察院业务考核
     * @param map 
     */
    void getYwkhDwkh(Map<String, Object> map);
    
    /**
     * 根据考核id、业务部门类型字符串（多个以逗号隔开）查询检察院业务考核[业务考核total页面数据] 
     * @param map 考核id、业务部门类型字符串（多个以逗号隔开）
     */
    void getYwkhByIdAndYwlx(Map<String, Object> map);
    /**
     * 根据业务考核考核id、查询检察院业务考核
     * @param map 业务考核考核id、业务部门类型
     */
    void getYwkhByywkhIdAndYwlx(Map<String, Object> map);
    
    
    /**
     * 根据业务考核id查询当前考核项目是否以指定审核人
     * @param map 业务考核id
     */
    void selectkhspr(Map<String,Object> map);
    
    /**
     * 更新业务类型考核分值表的数据[根据考核分值id]
     * @param map 考核分值id
     */
    void modifyYwlxkhfz(Map<String,Object> map);
    
    /**
     * 根据考核分值id查询基础分和最高评价分
     * @param map 考核分值id
     */
    void getKpglZbkpdfZgpjf(Map<String,Object> map);

	void getcl2(Map<String, Object> map);
	
	/**
	 * 根据类型id修改其状态
	 * @param map 类型id
	 */
	void modifyKhlxZtById(Map<String, Object> map);

    void getKhrMcGh(Map<String, Object> map);

	void getKhSj(Map<String, Object> map);
	
	/**
	 * 异议修改业务类型考核表
	*@param:@param map
	*@return:void
	*@throws
	 */
	void updateywlxkhById(Map<String,Object> map);

	/**
	 * 根据单位编码、业务类型、时间段查询、是否公示查询检察院业务考核
	 * @param map
	 */
    void getYwkhDwkh2(Map<String, Object> map);
	/**
	 * 根据单位编码、业务类型、时间段查询、是否公示查询检察院业务考核
	 * @param map
	 */
    void isZdkhr(Map<String, Object> map);

    void getDqDw(Map<String, Object> map);
    
    void getyysmbyid(Map<String, Object> map);

	void getNextOneGh(Map<String, Object> map);

	/**
	 * 获取一条业务考核数据信息
	 * @param map
	 */
	void getOneMessage(Map<String, Object> map);

    void dcndkh(Map<String, Object> map);
    
    /**
     * 根据业务考核分值id获取分值信息
     * @param id 分值id
     * @return 业务考核分值对象
     */
    Map<String,Object> getYwkhfzInfoById(@Param("id")String id);
    
	Integer getZgjcfCount(@Param("dwjb")String dwjb, 
							  @Param("khlx")String khlx, 
							  @Param("xmbm")String xmbm, 
							  @Param("kssj")Date kssj, 
							  @Param("jssj")Date jssj);
	
	void addZgjcfTableInfo(@Param("id")String id,
						   @Param("year")String year,
						   @Param("khlx")String khlx, 
						   @Param("dwjb")String dwjb, 
						   @Param("xmbm")String xmbm, 
						   @Param("zgjcf")Double zgjcf, 
						   @Param("cjsj")Date cjsj, 
						   @Param("kssj")Date kssj,
						   @Param("jssj")Date jssj);
	
	void modifyZgjcfTableInfo(@Param("khlx")String khlx, 
							  @Param("dwjb")String dwjb, 
							  @Param("xmbm")String xmbm, 
							  @Param("zgjcf")Double zgjcf, 
						      @Param("kssj")Date kssj,
							  @Param("jssj")Date jssj);
}
