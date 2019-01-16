package com.swx.ibms.business.common.bean;

import java.sql.Date;

/**
 * @author ZCY
 *审批流程实体类
 */
public class Splc {
	/**
	 * 审批实体id
	 */
	private String spstid;
	/**
	 * 审批人名称
	 */
	private String sprmc;
	/**
	 * 审批单位
	 */
	private String spdw;
	/**
	 * 审批人
	 */
	private String spr;
	/**
	 * 审批人意见
	 */
	private String spyj;
	/**
	 * 审批时间
	 */
	private String spsj;
	/**
	 * 审批状态
	 */
	private String spzt;
	/**
	 *审批状态编码
	 */
	private String spztbm;
	
	/**
	 * 流程id
	 */
	private String lcid;
	/**
	 *审批部门
	 */
	private String spbm;
	
	private String jdlx;
	/**
	 * 审批单位名称
	 */
	private String spdwmc;
	/**
	 * 审批部门名称
	 */
	private String spbmmc;
	
	/**
	 * 
	 * @return 单位名称
	 */
	public String getSpdwmc() {
		return spdwmc;
	}
	/**
	 * 
	 * @param spdwmc 单位名称
	 */
	public void setSpdwmc(String spdwmc) {
		this.spdwmc = spdwmc;
	}
	/**
	 * 
	 * @return 部门名称 
	 */
	public String getSpbmmc() {
		return spbmmc;
	}
	/**
	 * 
	 * @param spbmmc 部门名称
	 */
	public void setSpbmmc(String spbmmc) {
		this.spbmmc = spbmmc;
	}
	public String getJdlx() {
		return jdlx;
	}
	public void setJdlx(String jdlx) {
		this.jdlx = jdlx;
	}
	public String getSpdw() {
		return spdw;
	}
	public void setSpdw(String spdw) {
		this.spdw = spdw;
	}
	public String getSpr() {
		return spr;
	}
	public void setSpr(String spr) {
		this.spr = spr;
	}
	public String getSpbm() {
		return spbm;
	}
	public void setSpbm(String spbm) {
		this.spbm = spbm;
	}
	public String getSplx() {
		return splx;
	}
	public void setSplx(String splx) {
		this.splx = splx;
	}
	
	private String splx;//审批类型
	
	public String getSprmc() {
		return sprmc;
	}
	public void setSprmc(String sprmc) {
		this.sprmc = sprmc;
	}
	public String getSpyj() {
		return spyj;
	}
	public void setSpyj(String spyj) {
		this.spyj = spyj;
	}
	public String getSpsj() {
		return spsj;
	}
	public void setSpsj(Date spsj) {
		this.spsj = spsj.toString();
	}
	public String getSpzt() {
		return spzt;
	}
	public void setSpzt(String spzt) {
		this.spzt = spzt;
	}
	public String getSpstid() {
		return spstid;
	}
	public void setSpstid(String spstid) {
		this.spstid = spstid;
	}
	
	public String getLcid() {
		return lcid;
	}
	public void setLcid(String lcid) {
		this.lcid = lcid;
	}
	
	public String getSpztbm() {
		return spztbm;
	}
	public void setSpztbm(String spztbm) {
		this.spztbm = spztbm;
	}
	@Override
	public String toString() {
		return "Splc [spstid=" + spstid + ", sprmc=" + sprmc + ", spyj=" + spyj + ", spsj=" + spsj + ", spzt=" + spzt
				+ "]";
	}
	
}
