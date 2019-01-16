package com.swx.ibms.business.performance.service;


import com.swx.ibms.business.appraisal.bean.Ywkhfz;
import com.swx.ibms.business.performance.bean.Pfcl;
import com.swx.ibms.business.performance.bean.Ydkh;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 月度考核服务接口
 * @author 李治鑫
 *
 */
@SuppressWarnings("all")
public interface YdkhService {

	/**
	 * 通过单位编码和工号查出月度考核表里的最新年月,如果没有，查出的是目前的年和月
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @return Map
	 */
	Map getNewym(String dwbm,String gh);


	/**
	 * 通过单位编码和工号查出月度考核表里的最新年月,如果没有，查出的是目前的年度和季度
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @return Map
	 */
	Map getNewyj(String dwbm,String gh);

	/**通过单位编码、工号、年、季度查找出季度考核ID
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param year 年份
	 * @param season 季度
	 * @return 季度考核ID
	 */
	String selectJdkhid(String dwbm,String gh,int year,int season);


	/**
	 * 判断是否已有月度考核信息
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param year 年份
	 * @param season 季度
	 * @return 存在返回true  不存在返回false
	 */
	boolean isExist(String dwbm,String gh,int year,int season);


	/**
	 * 获取业务考核分值信息
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param year 年份
	 * @param season 季度
	 * @param ywlx 业务类型
	 * @return 业务考核分值信息列表
	 */
	List<Ywkhfz> getYwkhfz(String dwbm, String gh, int year, int season, String ywlx);


	/**
	 * 添加月度考核信息
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param year 年度
	 * @param season 季度
	 * @param ywlx 业务类型
	 * @param exitYdkh  exitYdkh
	 * @param zbkpbt zbkpbt
	 */
	void addYdkh(String dwbm, String gh, int year, int season,
				 String ywlx, boolean exitYdkh,String zbkpbt,String ryType);
	/**
	 * 从pz表中读取评分子项目的配置
	 * @param dwbm 单位编码
	 * @param ywlx 业务类型
	 * @param gh 工号
	 * @return 配置信息
	 */
	String createZbkpglFromZbpz(String ydkhid,String dwbm,String ywlx,String gh,int year,String ryType);

	/**
	 * 通过单位编码、工号、年、月查找出月度考核ID
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param year 年度
	 * @param month 月份
	 * @return 月度考核ID
	 */
	String selectYdkhid(String dwbm,String gh,int year,int month);
	/**
	 * 通过月度考核ID和业务类型更新业务考核分值信息
	 * @param id 编码ID
	 * @param ywlx 业务类型
	 * @param ywzf 业务总分
	 * @param zbkpgl 指标考评概览
	 * @param zbkpdf 指标考评得分
	 * @return 执行结果
	 */
	String updateYwkhfz(String id,double zpzf,double pjzf,String zbkpgl,String zbkpdf);

	/**
	 * 查询评分人记录信息
	 * @param ydkhid 月度考核ID
	 * @param ywlx 业务类型
	 * @return 评分人姓名列表
	 */
	List<String> getpfrjl(String ydkhid,String ywlx);

	/**
	 * 更新评分人记录信息
	 * @param ydkhid 月度考核ID
	 * @param ywlx 业务类型
	 * @param zprgh 自评人工号
	 * @param zpr 自评人姓名
	 * @param bmpfrgh 部门评分人工号
	 * @param bmpfr 部门评分人
	 * @param rsbpfrgh 人事部评分人工号
	 * @param rsbpfr 人事部评分人姓名
	 * @param jcpfrgh 交叉评分人工号
	 * @param jcpfr 交叉评分人姓名
	 * @return 更新信息 成功返回1，失败返回0
	 */
	public String updatepfrjl(String ydkhid, String zprgh,
							  String zpr, String bmpfrgh, String bmpfr,
							  String rsbpfrgh, String rsbpfr,String jcpfrgh,String jcpfr);
	/**
	 * 根据月度考核ID计算该月的月度考核总分
	 * @param ydkhid 月度考核ID
	 * @return  成功返回所得的分数，失败返回-1
	 */
	double calculateYdkhzf(String ydkhid);

	/**
	 * 根据月度考核ID更新月度考核总分
	 * @param id 月度考核ID
	 * @param ydkhzf 月度考核总分
	 */
	void updateydzfbyid(String id, double ydkhzf);


	/**
	 * 获取名称、得分和评价等级
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param year 年度
	 * @param season 季度
	 * @param ywlx 业务类型
	 * @return  名称和得分 Map<String, Object>
	 */
	Map<String, Object> getNameAndScore(String dwbm, String gh,
										String year, String season,String ywlx);

	/**
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param year 年份
	 * @param season 季度
	 * @param pflx 评分类型
	 * @param zbxbm 指标项编码
	 * @param ywlx 业务类型
	 * @param pflx 评分类型
	 * @param zbxbm 指标项编码
	 * @return List<Pfcl>
	 */
	List<Pfcl> getbz(String ydkhfzid, String pflx, String zbxbm);

	/**
	 * 更新备注信息
	 * @param ydkhfzid 月度考核分值id
	 * @param ywlx 业务类型
	 * @param pflx 评分类型
	 * @param zbxbm 指标项编码
	 * @param fjxx 附件信息
	 * @param bz 备注信息
	 * @return 执行结果
	 */
	int updatabz(String ydkhfzid,String pflx,String zbxbm,String fjxx,String bz);

	/**
	 * 判断是否存在评分材料信息
	 * @param ywkhfzid 业务考核ID
	 * @param pflx 评分类型
	 * @param zbxbm 指标项编码
	 * @return  boolean
	 */
	boolean isExistPfcl(String ywkhfzid,String pflx,String zbxbm);
	/**
	 * 根据单位编码获取个人绩效表表头
	 * @param ssrdwbm 所属人单位编码
	 * @param ssrywlx 所属人业务类型
	 * @param ssrgh 所属人工号
	 * @param ssryear 所属人年度
	 * @param ssrseason 所属人季度
	 * @return 表头信息
	 */
	String getkhbt(String ssrdwbm,String ssrywlx,String ssrgh,
				   String ssryear,String ssrseason);
	/**
	 * 判断类型的指标是否存在
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param year 年度
	 * @param season 季度
	 * @param ywlx 业务类型
	 * @return boolean
	 */
	boolean hasZbkhfz(String dwbm,String gh,int year,int season,String ywlx);


	/**
	 * 查询是否有发起审批的信息
	 * @param spstid 审批实体id p_spstid
	 * @return int 审批实例条数
	 */
	int sffqsp(String spstid);

	/**
	 * 删除个人绩效的阅读考核的信息
	 * @param spstid 审批实体id p_spstid
	 * @return String
	 */
	String delydkh(String spstid);

	/**
	 * 通过单位编码 工号查询个人绩效的年度季度
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @return Map<String,Object>
	 */
	Map<String,Object> getndjdlist(String dwbm,String gh);

	/**
	 * 根据月度考核id查询月度考核信息集
	 * @param ydkhId
	 * @return 月度考核结果集
	 */
	List<Ydkh> getYdkhById(String ydkhId);

	/**
	 * 根据传进来的个人绩效的条件添加个人绩效
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param year 年
	 * @param season 季度
	 * @param ywlx 业务类型
	 * @param bgbt 表头数据
	 * @param ryType 人员类型【这个主要用做查找个人绩效-考核指标表的配置数据】
	 * @return 个人绩效考核分值结果集
	 */
	List<Ywkhfz> addGrjxYdkhAndJxfz(String dwbm, String gh,Integer year, String season,
									String ywlx,String bgbt,String ryType);

	Integer insertSelective(Ydkh record);

	Ydkh selectByPrimaryKey(String id);

	Integer updateByPrimaryKeySelective(Ydkh record);

	int deleteByPrimaryKey(String id);

	Ydkh getGrjxByParams(String dlrDwbm, String dlrGh, Integer nowYear, Date jxKsrq, Date jxJsrq);
	/**
	 * 是否审批通过
	 * @param ydkhid
	 * @return
	 */
	boolean isApproved (String ydkhid);
}
