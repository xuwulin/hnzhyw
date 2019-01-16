package com.swx.ibms.business.appraisal.service;

import com.google.gson.JsonArray;
import com.swx.ibms.business.appraisal.bean.YWGZPFCL;
import com.swx.ibms.business.appraisal.bean.Ywlxkh;
import com.swx.ibms.business.appraisal.bean.YwlxkhZgjcf;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title:YwlxkhService
 * </p>
 * <p>
 * Description: 业务类型考核的接口
 * </p>
 * author 朱春雨 date 2017年6月5日 下午7:15:17
 */
/**
 * @author ZCY
 *
 */
/**
 * @author ZCY
 *
 */
/**
 * @author ZCY
 *
 */
public interface YwlxkhService {
	/**
	 * @param khlx
	 *            考核类型
	 * @param dwbm
	 *            单位编码
	 * @param year
	 *            年份
	 * @return 结果
	 */
	List<Ywlxkh> getYwlxB(String khlx, String dwbm, String year);

	/**
	 * @param ywlxkhfzid
	 *            业务类型考核分值ID
	 * @param lx
	 *            类型
	 * @param zbxbm
	 *            指标项编码
	 * @return 得到材料
	 */
	YWGZPFCL getcl(String ywlxkhfzid, String lx, String zbxbm);

	/**
	 * @param ywlxkhfzid
	 *            业务类型考核分值ID
	 * @param lx
	 *            类型
	 * @param zbxbm
	 *            指标项编码
	 * @return 得到材料
	 */
	YWGZPFCL getcl2(String ywlxkhfzid, String lx, String zbxbm);

	/**
	 * @param dwbm 单位编码
	 * @param year 年份
	 * @param khlx 业务类型
	 * @param pflx 评分类型
	 * @param zbxbm 指标项编码
	 * @param fjxx 附件信息
	 * @param bz 备注
	 * @return 保存评分材料
	 */
	String updatabz(String dwbm, String year, String khlx, 
			String pflx, String zbxbm, String fjxx, String bz);
	
	/**
	 * @param ywlxkhid 业务类型考核id
	 * @param khlx 业务类型
	 * @param ywzf 业务总分
	 * @param zbkpgl 指标考评概览
	 * @param zbkpdf 指标考评得分
	 * @return  更新业务类型考核分值表里的数据
	 */
	String updateYwlxkhfz(String ywlxkhid,String  khlx, double ywzf,String  zbkpgl,String zbkpdf);
	
	/**
	 * @param year 年份
	 * @param khlx 考核类型
	 * @param dwjb 单位级别
	 * @param xmbm 项目编码
	 * @param jcf 基础分
	 * @return 更新最高基础分
	 */
	String updateZgjcf(String year,String khlx,String dwjb,String xmbm,double jcf);
	
	/**
	 * @param dwbm 单位编码
	 * @param year 年份
	 * @param khlx 考核类型
	 * @return 查询指标考评得分和最高评价分
	 */
	Map<String,Object> selectZbkpglZbkpdfZgpjf(String dwbm,String year,String khlx);
	
	/**
	 * @param year 年份
	 * @param khlx 考核类型
	 * @param dwjb 单位级别
	 * @return 查询最高基础分list
	 */
	List<YwlxkhZgjcf> selectZgjcf(String year, String khlx, String dwjb);
	
	/**
	 * @param dwbm 单位编码
	 * @param year 年份
	 * @param khlx 考核类型
	 * @return 插入评价分是否成功
	 */
	String insertpjf(String dwbm,String year,String khlx,String fzid);
	
	/**
	 * @param dwbm 单位编码
	 * @param year 年份
	 * @param khlx 考核类型
	 * @return 字符串 
	 */
	String updatezjcftoinsertpjf(String dwbm, String year, String khlx,String khfzid); 

	/**
	 * 查询考核总览
	 * @param year 
	 * @param dwbm 
	 * @param khlx 
	 * @param sfby 是否本院
	 * @return  JsonArray 
	 */
	JsonArray getkhzl(String year,String dwbm,String khlx,String sfby);
	
	/**
	 * 查询考核总览
	 * @param year 
	 * @param dwbm 
	 * @param sfby 是否本院 
	 * @return JsonArray  
	 */
	JsonArray getkhzl(String year,String dwbm,String sfby);
	
	/**
	 * 根据当前登录人的单位编码、选中的单位编码、年份查询业务工作考核的单位信息
	 * @param map  登录人的单位编码、选中的单位编码、年份
	 * @return json数组
	 */
	JsonArray selectDwkhByDwbmAndYear(Map<String,Object> map);
	
	
	/**
	 *  根据业务工作分值id查询业务工作分值信息
	 * @param map 参数集合
	 * @return 结果集
	 */
	Map<String,Object> selectYwkhfzById(Map<String,Object> map);
	
	/**
	 * 根据业务类型考核分值表id修改业务类型考核分值表指标考评得分
	 * @param map 参数集合
	 * @return 提示字符串
	 */
	String updateZbkpdfById(Map<String,Object> map);
	
	/**
	 * 根据考核活动id修改考核活动表、业务类型考核表的状态
	 * @param map 参数集合
	 * @return 是否成功的字符串
	 */
	String updateGbZtById(Map<String,Object> map);
	
	/**
	 * 根据登录人的单位编码、部门编码查询其所有的业务考核类型
	 * @param dlrDwbm 登录人单位编码
	 * @param bmjs 登录人部门角色
	 * @return 结果集
	 */
	Map<String,Object> selectKhlxByDwbmAndBmbm(String dlrDwbm,List<String> bmjs);
	
	/**
	 * 查询当前登录案管人的角色
	 * @param map 参数集合 
	 * @return 结果集
	 */
	Map<String,Object> getJsbmByDwbmAndGh(Map<String,Object> map); 
	
	/**
	 * 根据id查询业务考核分值表信息
	 * @param khfzid 考核分值id
	 * @return 查询指标考评得分和最高评价分
	 */
	Map<String,Object> selectKpglZbkpdfZgpjfById(String khfzid);
	
	
	/**
	 * 根据id查询业务考核分值信息、类型考核名称、单位考核状态
	 * @param khfzid 考核分值id
	 * @return 分值信息结果集
	 */
	List<Ywlxkh> getYwkhfzById(String khfzid);
	
	/**
	 * 通过部门映射获取业务类型
	 * @param bmysStr 部门映射
	 * @return 业务类型字符串
	 * @exception Exception 异常
	 */
	String getYwlxByBmys(String bmysStr) throws Exception;

	/**
	 * 根据单位编码、业务类型、时间段查询、是否公示查询检察院业务考核
	 * @param dwbm
	 * @param ywlxStr
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	String getYwkhDwkh(String dwbm, String ywlxStr, String startDate, String endDate,String sfgs);
	
	/**
	 * 根据考核id、业务部门类型字符串（多个以逗号隔开）查询检察院业务考核[业务考核total页面数据]
	 * @param khid 考核id
	 * @param ywlxStr 业务类型字符串
	 * @return 结果集
	 * @exception Exception 异常
	 */
	List<Map<String,Object>> getYwkhByIdAndYwlx(String khid,String ywlxStr)throws Exception;
	/**
	 * 根据考核业务考核考核id、业务部门类型]
	 * @param ywkhkhid
	 * @return 结果集
	 * @exception Exception 异常
	 */
	List<Map<String,Object>> getYwkhByywkhIdAndYwlx(String ywkhkhid,String ywlx,String khlx)throws Exception;
	
	/**
     * 根据业务考核id查询当前考核项目是否以指定审核人
     * @param map 业务考核id
     * @return 包含true或false
     */ 
    public  boolean selectkhspr(Map<String,Object> map);
	
    /**
	 * 根据业务考核分值id修改其备注信息
	 * @param ywkhfzid 分值id
	 * @param pflx 类型
	 * @param zbxbm 指标项编码
	 * @param fjxx 附件信息
	 * @param bz 备注
	 * @return 成功与否的标示
	 */
    String modifyKhfzBzInfo(String ywkhfzid, String pflx, String zbxbm,
			String fjxx, String bz);
    
    /**
     * 根据业务考核分值id修改其填写分值信息
     * @param khfzid 业务考核分值id 
     * @param ywzf 业务总分
     * @param zbkpgl 指标考评
     * @param zbkpdf 指标考评得分
     * @exception Exception 异常
     * @return 是否成功的标示
     */
    String modifyYwlxkhfz(String khfzid,double ywzf, String zbkpgl, String zbkpdf) throws Exception;
    
	String countPjdf(String khfzid, String khlx);
	
	/**
	 * 根据考核类型id 修改其状态
	 * @param khlxid 类型id
	 * @return 是否成功的标示
	 */
	String modifyKhlxZtById(String khlxid);

    String getKhrMcGh(String id);

	String getKhSj();

	String isZdkhr(String mc, String gh, String startDate, String endDate);

    String getDqDw(String id);

    String getNextOneGh(String wbid, String cljs);

  /**
     * 根据业务考核类型id查询异议说明
    *@param:@param id
    *@param:@return
    *@return:String
    *@throws
     */
    String getyysmbyid(String id);

    String getOneMessage(String wbid);

    String dcndkh(String dz ,String strdwmc ,String kwdwbm ,String strkhlxmc ,String khlx ,String starDate,String endDate, HttpServletRequest request);
}
