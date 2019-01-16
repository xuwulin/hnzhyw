package com.swx.ibms.business.appraisal.bean;

import java.util.Date;

/**
 * 
 * YwgzZbrs.java  某院实有政法编制在编人数实体类
 * @author east
 * @date<p>2017年6月12日</p>
 * @version 1.0
 * @description<p></p>
 */
public class YwgzZbrs {
	
	/**
	 * 在编人数id
	 */
	private String id;
	/**
	 * 单位编码
	 */
	private String dwbm;
	/**
	 * 部门编码 
	 */
	private String bmbm;
	/**
	 * 在编人数
	 */
	private String zbrs;
	
	/**
	 * 年
	 */
	private String year;
	
	/**
	 * 创建时间或者修改时间 
	 */
	private Date createTime;
	
	/**
	 * 单位名称
	 */
	private String dwmc;
	
	/**
	 * 
	 * @return 单位名称
	 */
	public String getDwmc() {
		return dwmc;
	}
	/**
	 * 
	 * @param dwmc 单位名称
	 */
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}
	/**
	 * 
	 * @return 年份
	 */
	public String getYear() {
		return year;
	}
	/**
	 * 
	 * @param year 年
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * 
	 * @return 创建时间或者修改时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 
	 * @param createTime 创建时间或者修改时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 
	 * @return 在编人数id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 
	 * @param id 在编人数id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 
	 * @return 单位编码
	 */
	public String getDwbm() {
		return dwbm;
	}
	/**
	 * 
	 * @param dwbm 单位编码
	 */
	public void setDwbm(String dwbm) {
		this.dwbm = dwbm;
	}
	/**
	 * 
	 * @return 部门编码
	 */
	public String getBmbm() {
		return bmbm;
	}
	/**
	 * 
	 * @param bmbm 部门编码 
	 */
	public void setBmbm(String bmbm) {
		this.bmbm = bmbm;
	}
	/**
	 * 
	 * @return 在线人数
	 */
	public String getZbrs() {
		return zbrs;
	}
	/**
	 * 
	 * @param zbrs 在编人数
	 */
	public void setZbrs(String zbrs) {
		this.zbrs = zbrs;
	}
	
	
	@Override
	public String toString() {
		return "YwgzZbrs [id=" + id + ", dwbm=" + dwbm + ", "
				+ "bmbm=" + bmbm + ", zbrs=" + zbrs + ", year=" + year
				+ ", createTime=" + createTime + ", dwmc=" + dwmc + "]";
	}
	
	
}
