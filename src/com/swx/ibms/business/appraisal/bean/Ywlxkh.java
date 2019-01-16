package com.swx.ibms.business.appraisal.bean;

import java.util.Date;

/**
 * <p>
 * Title:ywlxkh
 * </p>
 * <p>
 * Description: 业务类型考核实体类
 * </p>
 * author 朱春雨 date 2017年6月5日 下午7:27:39
 */
public class Ywlxkh {
	/**
	 * 业务类型考核分值id
	 */
	private String ywlxkhfzid;
	/**
	 * 业务类型考核id
	 */
	private String ywlxkhid;
	/**
	 * 考核类型
	 */
	private String khlx;
	/**
	 * 考核类型名称
	 */
	private String khmc;
	/**
	 * 单位考核状态
	 */
	private String dwkhzt;
	/**
	 * 业务类型考核状态
	 */
	private String lxkhzt;
	/**
	 * 指标考评概览
	 */
	private String zbkpgl;
	/**
	 * 指标考评得分
	 */
	private String zbkpdf;
	/**
	 * 指标考评表头
	 */
	private String zbkpbt;
	/**
	 * 审批是否通过
	 */
	private String spsftg;
	/**
	 * 总评价得分
	 */
	private String zpjdf;
	/**
	 * 业务总分
	 */
	private String ywzf;
	/**
	 * 改后的指标考评概览
	 */
	private String ghzbkpgl;
	
	/**
	 * 单位编码
	 */
	private String dwbm;
	
	/**
	 * 单位名称
	 */
	private String dwmc;
	
	/**
	 * 业务考核开始时间
	 */
	private Date kssj;
	
	/**
	 * 业务考核结束时间
	 */
	private Date jssj;
	
	/**
	 * 单位级别
	 */
	private String dwjb;

	public String getYwlxkhfzid() {
		return ywlxkhfzid;
	}

	public void setYwlxkhfzid(String ywlxkhfzid) {
		this.ywlxkhfzid = ywlxkhfzid;
	}

	public String getYwlxkhid() {
		return ywlxkhid;
	}

	public void setYwlxkhid(String ywlxkhid) {
		this.ywlxkhid = ywlxkhid;
	}

	public String getKhlx() {
		return khlx;
	}

	public void setKhlx(String khlx) {
		this.khlx = khlx;
	}

	public String getKhmc() {
		return khmc;
	}

	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}

	public String getDwkhzt() {
		return dwkhzt;
	}

	public void setDwkhzt(String dwkhzt) {
		this.dwkhzt = dwkhzt;
	}

	public String getLxkhzt() {
		return lxkhzt;
	}

	public void setLxkhzt(String lxkhzt) {
		this.lxkhzt = lxkhzt;
	}

	public String getZbkpgl() {
		return zbkpgl;
	}

	public void setZbkpgl(String zbkpgl) {
		this.zbkpgl = zbkpgl;
	}

	public String getZbkpdf() {
		return zbkpdf;
	}

	public void setZbkpdf(String zbkpdf) {
		this.zbkpdf = zbkpdf;
	}

	public String getZbkpbt() {
		return zbkpbt;
	}

	public void setZbkpbt(String zbkpbt) {
		this.zbkpbt = zbkpbt;
	}

	public String getSpsftg() {
		return spsftg;
	}

	public void setSpsftg(String spsftg) {
		this.spsftg = spsftg;
	}

	public String getZpjdf() {
		return zpjdf;
	}

	public void setZpjdf(String zpjdf) {
		this.zpjdf = zpjdf;
	}

	public String getYwzf() {
		return ywzf;
	}

	public void setYwzf(String ywzf) {
		this.ywzf = ywzf;
	}

	public String getGhzbkpgl() {
		return ghzbkpgl;
	}

	public void setGhzbkpgl(String ghzbkpgl) {
		this.ghzbkpgl = ghzbkpgl;
	}

	public String getDwbm() {
		return dwbm;
	}

	public void setDwbm(String dwbm) {
		this.dwbm = dwbm;
	}

	public String getDwmc() {
		return dwmc;
	}

	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}

	public Date getKssj() {
		return kssj;
	}

	public void setKssj(Date kssj) {
		this.kssj = kssj;
	}

	public Date getJssj() {
		return jssj;
	}

	public void setJssj(Date jssj) {
		this.jssj = jssj;
	}

	public String getDwjb() {
		return dwjb;
	}

	public void setDwjb(String dwjb) {
		this.dwjb = dwjb;
	}

	@Override
	public String toString() {
		return "Ywlxkh [ywlxkhfzid=" + ywlxkhfzid + ", ywlxkhid=" + ywlxkhid + ", khlx=" + khlx + ", khmc=" + khmc
				+ ", dwkhzt=" + dwkhzt + ", lxkhzt=" + lxkhzt + ", zbkpgl=" + zbkpgl + ", zbkpdf=" + zbkpdf
				+ ", zbkpbt=" + zbkpbt + ", spsftg=" + spsftg + ", zpjdf=" + zpjdf + ", ywzf=" + ywzf + ", ghzbkpgl="
				+ ghzbkpgl + ", dwbm=" + dwbm + ", dwmc=" + dwmc + ", kssj=" + kssj + ", jssj=" + jssj + ", dwjb="
				+ dwjb + "]";
	}
	
}
