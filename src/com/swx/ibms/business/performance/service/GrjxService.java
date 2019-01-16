package com.swx.ibms.business.performance.service;


import com.swx.ibms.business.performance.bean.GRJX;

import java.util.List;
import java.util.Map;

/**
 * 个人绩效服务接口
 * @author 李治鑫
 *
 */
public interface GrjxService {
	
	/**
	 * 插入或更新个人绩效嘴个基础分
	 * @param year
	 * @param jd
	 * @param ywlx
	 * @param dwjb
	 * @param xmbm
	 * @param zgjcf
	 * @return
	 */
	String inOrUpGrjxZgjcf(int year, int jd, String ywlx, String dwjb, String xmbm, Double zgjcf);
	/**
	 * 个人绩效该绩效所有人审批是否通过
	 * @param year
	 * @param jd
	 * @param ywlx
	 * @param dwjb
	 * @param dwbmtwo
	 * @return
	 */
	String grjxAllRSpTg(int year, int jd, String ywlx, String dwjb, String dwbmtwo);
	/**
	 * 得到评价得分，并更新月度考核的zbkpgl,zbkpdf
	 * @param year
	 * @param jd
	 * @param ywlx
	 * @param dwjb
	 * @return
	 */
	Map showGrjxZgjcfList(String ydkhid, int year, int jd, String ywlx, String dwjb);
	/**
	 *  根据月度考核年份，季度，业务类别，单位级别，单位编码前两位，找出所有审批通过的人的月度考核id,然后再遍历showGrjxZgjcfList服务
	 * @param year
	 * @param jd
	 * @param ywlx
	 * @param dwjb
	 * @param dwbmtwo
	 * @return
	 */
	String selectGrjxYdkhlist(int year, int jd, String ywlx, String dwjb, String dwbmtwo);
	/**
	 * 通过档案ID获取个人绩效信息接口
	 * @param daid 档案ID
	 * @return
	 */
	List<GRJX> getGrjxByDaid(String daid);


	/**
	 * 添加个人绩效信息
	 * @param grjx 个人绩效对象
	 * @return 插入结果信息  成功：1  失败： 0
	 */
	int addGrjx(GRJX grjx);

	/**
	 * 更新个人绩效信息
	 * @param grjx 个人绩效对象
	 * @return 更新结果信息  成功：1  失败： 0
	 */
	int updateGrjx(GRJX grjx);

	/**
	 * 删除个人绩效信息
	 * @param grjx 个人绩效对象
	 * @return 删除结果信息  成功：1  失败： 0
	 */
	int deleteGrjx(String daid);

	/** 个人绩效信息中的指标考评概览（zbkpgl）数据重组接口
	 * @param grjx
	 */
	public void conform(List<GRJX> grjx);

	/**
	 * 根据档案归总的ID查询此归总信息的创建人单位和工号以及归总年份
	 * @param daid 档案ID
	 * @return 包含创建人单位和工号以及归总年份的map 分别是dwbm,gh,tjnf
	 */
	public Map<String,String> getRyGhByDaid(String daid);
	/**
	 * 插入最高基础分数据
	 * @param ydkhid
	 * @param dwbm
	 * @return
	 */
	public String insertZgjcfdata(String ydkhid, String dwbm);
	/**
	 *
	 * @param ywlb
	 * @param dwjb
	 * @return
	 */
	public String selectgrjxZbpzNr(String ywlb, String dwjb);
	/**
	 * 根据月度考核id和业务类型查找业务考核分值的zbkpdf
	 * @param ydkhid
	 * @param ywlx
	 * @return
	 */
	public Map selectgrjxZbkpdf(String ydkhid, String ywlx);
}
