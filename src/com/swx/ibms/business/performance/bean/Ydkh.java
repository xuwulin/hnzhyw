package com.swx.ibms.business.performance.bean;

import java.util.Date;

/**
 * 个人绩效的实体类
 * 【还有一个或者多个类都是类似的作用，因其他人分工合作未沟通的原因，现用此类作为主要使用的类，其他的不做改动。】
 * @author 李佳
 * @since 2017-5-9
 */
public class Ydkh {
	/**
	 * 月度考核ID
	 */
	private String ydkhid;
	/**
	 * 单位编码
	 */
	private String dwbm;
	/**
	 * 工号
	 */
	private String gh;
	/**
	 * 年份
	 */
	private int year;
	/**
	 * 月份（以前最小绩效考核单位是月份，现在最小绩效考核单位是季度）
	 */
	private int month;

	/**
	 * 个人绩效考核总分值【评价分总分】
	 */
	private double ydkhzf;

	/**
	 * 季度
	 */
	private double jd;

	/**
	 * 是否审批（0 未审批完成  1 已审批完成）
	 */
	private String sfsp;

	/**
	 * 创建时间
	 */
	private Date cjsj;

	/**
	 * 创建时间
	 */
	private Date ksrq;

	/**
	 * 创建时间
	 */
	private Date jsrq;

	/**
     * 月度总分
     */
    private double ydzf;

	/**
	 * 是否公示
	 */
	private String sfgs;


	public String getSfgs() {
		return sfgs;
	}

	public void setSfgs(String sfgs) {
		this.sfgs = sfgs;
	}

	/**
	 * 获取月度考核ID
	 * @return 月度考核ID
	 */
	public String getYdkhid() {
		return ydkhid;
	}
	/**
	 * 设置月度考核ID
	 * @param ydkhid 月度考核ID
	 */
	public void setYdkhid(String ydkhid) {
		this.ydkhid = ydkhid;
	}
	/**
	 * 获取年份
	 * @return 年份
	 */
	public double getYear() {
		return year;
	}
	/**
	 * 设置年份
	 * @param year 年份
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * 获取季度
	 * @return 季度
	 */
	public double getMonth() {
		return month;
	}
	/**
	 * 设置月份
	 * @param month 月份
	 */
	public void setMonth(int month) {
		this.month = month;
	}
	/**
	 * 获取单位编码
	 * @return 单位编码
	 */
	public String getDwbm() {
		return dwbm;
	}
	/**
	 * 设置单位编码
	 * @param dwbm 单位编码
	 */
	public void setDwbm(String dwbm) {
		this.dwbm = dwbm;
	}
	/**
	 * 获取工号
	 * @return 工号
 	 */
	public String getGh() {
		return gh;
	}
	/**
	 * 设置工号
	 * @param gh 工号
	 */
	public void setGh(String gh) {
		this.gh = gh;
	}
	/**
	 * 月度考核分值
	 * @return 月度考核分值
	 */
	public double getYdzf() {
		return ydzf;
	}
	/**
	 * 设置月度考核分值
	 * @param ydzf 月度考核分值
	 */
	public void setYdzf(double ydzf) {
		this.ydzf = ydzf;
	}
	/**
	 * 获取季度
	 * @return 季度
	 */
	public double getJd() {
		return jd;
	}
	/**
	 * 设置季度
	 * @param jd 季度
	 */
	public void setJd(double jd) {
		this.jd = jd;
	}

	public double getYdkhzf() {
		return ydkhzf;
	}

	public void setYdkhzf(double ydkhzf) {
		this.ydkhzf = ydkhzf;
	}

	public String getSfsp() {
		return sfsp;
	}

	public void setSfsp(String sfsp) {
		this.sfsp = sfsp;
	}

	public Date getCjsj() {
		return cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

	public Date getKsrq() {
		return ksrq;
	}

	public void setKsrq(Date ksrq) {
		this.ksrq = ksrq;
	}

	public Date getJsrq() {
		return jsrq;
	}

	public void setJsrq(Date jsrq) {
		this.jsrq = jsrq;
	}

	@Override
	public String toString() {
		return "Ydkh{" +
				"ydkhid='" + ydkhid + '\'' +
				", dwbm='" + dwbm + '\'' +
				", gh='" + gh + '\'' +
				", year=" + year +
				", month=" + month +
				", ydkhzf=" + ydkhzf +
				", jd=" + jd +
				", sfsp='" + sfsp + '\'' +
				", cjsj=" + cjsj +
				", ksrq=" + ksrq +
				", jsrq=" + jsrq +
				", ydzf=" + ydzf +
				", sfgs='" + sfgs + '\'' +
				'}';
	}
}
