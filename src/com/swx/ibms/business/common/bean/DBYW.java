package com.swx.ibms.business.common.bean;

import java.io.Serializable;

/**
 * 待办业务实体类
 * @author 王宇锋
 */
public class DBYW implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 为123时是处理内容lx为4时是通知名称
	 */
	private String clnr;
	
	/**
	 * lx类型1为公示档案 2为档案
	 *3为绩效 4为荣誉技能 5为问题反馈
	 */
	private String lx;
	/**
	 * 日期
	 */
	private String rq;
	/**
	 * wbid也是spstid
	 */
	private String wbid;
	/**
	 * 所属人编号
	 */
	private String ssr;
	/**
	 * 所属人单位编码
	 */
	private String ssrdwbm;
	/**
	 * 年份
	 */
	private String nf;
	/**
	 * 季度
	 */
	private String jd;
	/**
	 * 审批状态
	 */
	private String spzt;
	/**
	 * 节点类型
	 */
	private String jdlx;
	/**
	 * 处理角色
	 */
	private String cljs;
	/**
	 * 档案开始时间
	 */
	private String kssj;
	/**
	 * 档案结束时间
	 */
	private String jssj;
	/**
	 * 流程id
	 */
	private String lcid;
	
	/**
	 * 被审批单位编码名称
	 */
	private String bspdwmc;
	
	
	/**
	 * 被审批部门编码名称
	 */
	private String bspbmmc;
	
	/**
	 * 被审批单位编码
	 */
	private String bspdwbm;
	
	/**
	 * 被审批部门编码
	 */
	private String bspbmbm;
	
	/**
	 * 审批id
	 */
	private String spId;
	/**
	 * 业务类型
	 */
	private String ywlx;

	public String getYwlx() {
		return ywlx;
	}

	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}

	/**
	 * 
	 * @return 审批id
	 */
	public String getSpId() {
		return spId;
	}

	/**
	 * 
	 * @param spId 审批id
	 */
	public void setSpId(String spId) {
		this.spId = spId;
	}

	/**
	 * 
	 * @return 被审批单位编码
	 */
	public String getBspdwbm() {
		return bspdwbm;
	}
	
	/**
	 * 
	 * @param bspdwbm 被审批单位编码
	 */
	public void setBspdwbm(String bspdwbm) {
		this.bspdwbm = bspdwbm;
	}
	
	/**
	 * 
	 * @return 被审批部门编码
	 */
	public String getBspbmbm() {
		return bspbmbm;
	}
	
	/**
	 * 
	 * @param bspbmbm 被审批部门编码
	 */
	public void setBspbmbm(String bspbmbm) {
		this.bspbmbm = bspbmbm;
	}
	/**
	 * 被审批单位编码名称
	 * @return 被审批部门编码名称
	 */
	public String getBspdwmc() {
		return bspdwmc;
	}
	/**
	 * 
	 * @param bspdwmc 被审批单位编码名称
	 */
	public void setBspdwmc(String bspdwmc) {
		this.bspdwmc = bspdwmc;
	}
	/**
	 * 被审批部门编码名称
	 * @return 被审批部门编码名称
	 */
	public String getBspbmmc() {
		return bspbmmc;
	}
	/**
	 * 被审批部门编码名称
	 * @param bspbmmc 被审批部门编码名称
	 */
	public void setBspbmmc(String bspbmmc) {
		this.bspbmmc = bspbmmc;
	}
	/**
	 * 流程id
	 * @return 流程id
	 */
	public String getLcid() {
		return lcid;
	}
	/**
	 * 
	 * @param lcid 流程id
	 */
	public void setLcid(String lcid) {
		this.lcid = lcid;
	}
	/**
	 * 处理内容
	 * @return 处理内容
	 */
	public String getClnr() {
		return clnr;
	}
	/**
	 * 
	 * @param clnr 处理内容
	 */
	public void setClnr(String clnr) {
		this.clnr = clnr;
	}
	/**
	 * 
	 * @return 审批类型 
	 */
	public String getLx() {
		return lx;
	}
	/**
	 * 
	 * @param lx 审批类型 
	 */
	public void setLx(String lx) {
		this.lx = lx;
	}
	/**
	 * @return 日期
	 */
	public String getRq() {
		return rq;
	}
	/**
	 * @param rq 日期
	 */
	public void setRq(String rq) {
		this.rq = rq;
	}
	/**
	 * @return 外部id
	 */
	public String getWbid() {
		return wbid;
	}
	/**
	 * @param wbid 外部id
	 */
	public void setWbid(String wbid) {
		this.wbid = wbid;
	}
	/**
	 * @return ssr工号
	 */
	public String getSsr() {
		return ssr;
	}
	/**
	 * @param ssr ssr工号
	 */
	public void setSsr(String ssr) {
		this.ssr = ssr;
	}
	/**
	 * @return 所属人单位编码
	 */
	public String getSsrdwbm() {
		return ssrdwbm;
	}
	/**
	 * @param ssrdwbm 所属人单位编码
	 */
	public void setSsrdwbm(String ssrdwbm) {
		this.ssrdwbm = ssrdwbm;
	}
	/**
	 * @return 年份
	 */
	public String getNf() {
		return nf;
	}
	/**
	 * @param nf 年份
	 */
	public void setNf(String nf) {
		this.nf = nf;
	}
	/**
	 * @return 季度
	 */
	public String getJd() {
		return jd;
	}
	/**
	 * @param jd 季度
	 */
	public void setJd(String jd) {
		this.jd = jd;
	}
	/**
	 * @return 审批状态
	 */
	public String getSpzt() {
		return spzt;
	}
	/**
	 * @param spzt 审批状态
	 */
	public void setSpzt(String spzt) {
		this.spzt = spzt;
	}
	/**
	 * 
	 * @return 节点类型
	 */
	public String getJdlx() {
		return jdlx;
	}
	/**
	 * 
	 * @param jdlx 节点类型
	 */
	public void setJdlx(String jdlx) {
		this.jdlx = jdlx;
	}
	/**
	 * @return 处理角色
	 */
	public String getCljs() {
		return cljs;
	}
	/**
	 * @param cljs 传入处理角色   
	 */
	public void setCljs(String cljs) {
		this.cljs = cljs;
	}
	/**
	 * @return 开始时间
	 */
	public String getKssj() {
		return kssj;
	}
	/**
	 * @param kssj 开始时间
	 */
	public void setKssj(String kssj) {
		this.kssj = kssj;
	}
	/**
	 * @return 结束时间
	 */
	public String getJssj() {
		return jssj;
	}
	/**
	 * 
	 * @param jssj 结束时间
	 */
	public void setJssj(String jssj) {
		this.jssj = jssj;
	}

	@Override
	public String toString() {
		return "DBYW [clnr=" + clnr + ", lx=" + lx + ", rq=" + rq 
				+ ", wbid=" + wbid + ", ssr=" + ssr + ", ssrdwbm="
				+ ssrdwbm + ", nf=" + nf + ", jd=" + jd + ", spzt=" 
				+ spzt + ", jdlx=" + jdlx + ", cljs=" + cljs
				+ ", kssj=" + kssj + ", jssj=" + jssj + ", lcid=" + lcid + "]";
	}
}
