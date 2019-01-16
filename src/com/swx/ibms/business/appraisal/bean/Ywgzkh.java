package com.swx.ibms.business.appraisal.bean;

import java.util.Date;

/**
 * 
 * Ywgzkh.java 业务工作考核实体类
 * @author east
 * @date<p>2017年6月5日</p>
 * @version 1.0
 * @description<p></p>
 */
public class Ywgzkh {
	
	/**
	 * 业务工作考核id
	 */
	private String id;
	
	/**
	 * 年份
	 */
	private int year;
	
	/**
	 * 业务考核活动发起单位编码
	 */
	private String dwbm;
	
	/**
	 * 业务考核活动发起部门编码
	 */
	private String bmbm;
	
	/**
	 * 创建人工号
	 */
	private String gh;
	
	/**
	 * 状态，-1 已删除;0 进行中；1：已完成
	 */
	private String zt;
	
	/**
	 * //创建工号 ：一般是案管人员 
	 */
	private String cjgh;
	
	/**
	 * 备注
	 */
	private String bz;
	
	/**
	 * 创建时间
	 */
	private Date cjsj;
	
	/**
	 * 考核时间期限 D天 W周 M月
	 */
	private String sjqx;
	
	/**
	 * //考核单位
	 */
	private String khdw;
	
	/**
	 * //考核单位上级
	 */
	private String khdwsj;
	
	/**
	 * 考核单位级别
	 */
	private String khdwjb;
	
	/**
	 * //异议状态
	 */
	private String yyzt;
	
	/**
	 * //流程类型
	 */
	private String lclx;
	
	/**
	 * //节点类型
	 */
	private String jdlx;
	
	/**
	 * //流程节点 
	 */
	private String lcjd;
	
	/**
	 * //异议说明
	 */
	private String yysm;
	
	/**
	 * 开始日期
	 */
	private String createDate;
	
	/**
	 * 结束日期
	 */
	private String endDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDwbm() {
		return dwbm;
	}

	public void setDwbm(String dwbm) {
		this.dwbm = dwbm;
	}

	public String getBmbm() {
		return bmbm;
	}

	public void setBmbm(String bmbm) {
		this.bmbm = bmbm;
	}

	public String getGh() {
		return gh;
	}

	public void setGh(String gh) {
		this.gh = gh;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getCjgh() {
		return cjgh;
	}

	public void setCjgh(String cjgh) {
		this.cjgh = cjgh;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Date getCjsj() {
		return cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

	public String getSjqx() {
		return sjqx;
	}

	public void setSjqx(String sjqx) {
		this.sjqx = sjqx;
	}

	public String getKhdw() {
		return khdw;
	}

	public void setKhdw(String khdw) {
		this.khdw = khdw;
	}

	public String getKhdwsj() {
		return khdwsj;
	}

	public void setKhdwsj(String khdwsj) {
		this.khdwsj = khdwsj;
	}

	public String getKhdwjb() {
		return khdwjb;
	}

	public void setKhdwjb(String khdwjb) {
		this.khdwjb = khdwjb;
	}

	public String getYyzt() {
		return yyzt;
	}

	public void setYyzt(String yyzt) {
		this.yyzt = yyzt;
	}

	public String getLclx() {
		return lclx;
	}

	public void setLclx(String lclx) {
		this.lclx = lclx;
	}

	public String getJdlx() {
		return jdlx;
	}

	public void setJdlx(String jdlx) {
		this.jdlx = jdlx;
	}

	public String getLcjd() {
		return lcjd;
	}

	public void setLcjd(String lcjd) {
		this.lcjd = lcjd;
	}

	public String getYysm() {
		return yysm;
	}

	public void setYysm(String yysm) {
		this.yysm = yysm;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "Ywgzkh [id=" + id + ", year=" + year + ", dwbm=" + dwbm + ", bmbm=" + bmbm + ", gh=" + gh + ", zt=" + zt
				+ ", cjgh=" + cjgh + ", bz=" + bz + ", cjsj=" + cjsj + ", sjqx=" + sjqx + ", khdw=" + khdw + ", khdwsj="
				+ khdwsj + ", khdwjb=" + khdwjb + ", yyzt=" + yyzt + ", lclx=" + lclx + ", jdlx=" + jdlx + ", lcjd="
				+ lcjd + ", yysm=" + yysm + ", createDate=" + createDate + ", endDate=" + endDate + "]";
	}

}
