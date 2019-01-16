package com.swx.ibms.business.performance.bean;

/**
 * 
 * GrjxZgjcf.java 
 * @author 朱春雨
 * @date<p>2017年5月12日</p>
 * @version 1.0
 * @description<p></p>
 */
public class GrjxZgjcf {
	/**
	 * 年份
	 */
	private int year;
	/**
	 * 季度
	 */
	private int jd;
	/**
	 * 业务类别
	 */
	private String ywlx;
	/**
	 * 单位级别
	 */
	private String dwjb;
	/**
	 * 项目编码
	 */
	private String xmbm;
	/**
	 * 最高基础分
	 */
	private Double zgjcf;
	/**
	 * 评价得分
	 */
	private Double pjdf;
	/**
	 * 
	 * @return 返回年份
	 */
	public int getYear() {
		return year;
	}
	/**
	 * 
	 * @param year 传入年份
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * 
	 * @return 返回季度
	 */
	public int getJd() {
		return jd;
	}
	/**
	 * 
	 * @param jd 传入季度
	 */
	public void setJd(int jd) {
		this.jd = jd;
	}
	/**
	 * 
	 * @return 返回业务类型
	 */
	public String getYwlx() {
		return ywlx;
	}
	/**
	 * 
	 * @param ywlx 传入业务类型
	 */
	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}
	/**
	 * 
	 * @return 返回单位级别
	 */
	public String getDwjb() {
		return dwjb;
	}
	/**
	 * 
	 * @param dwjb 传入单位级别 
	 */
	public void setDwjb(String dwjb) {
		this.dwjb = dwjb;
	}
	/**
	 * 
	 * @return 返回项目编码 
	 */
	public String getXmbm() {
		return xmbm;
	}
	/**
	 * 
	 * @param xmbm 传入项目编码
	 */
	public void setXmbm(String xmbm) {
		this.xmbm = xmbm;
	}
	/**
	 * 
	 * @return 返回最高基础分
	 */
	public Double getZgjcf() {
		return zgjcf;
	}
	/**
	 * 
	 * @param zgjcf 传入最高基础分
	 */
	public void setZgjcf(Double zgjcf) {
		this.zgjcf = zgjcf;
	}
	/**
	 * 
	 * @return 评价得分
	 */
	public Double getPjdf() {
		return pjdf;
	}
	/**
	 * 
	 * @param pjdf 传入评价得分 
	 */
	public void setPjdf(Double pjdf) {
		this.pjdf = pjdf;
	}
	
	

}
