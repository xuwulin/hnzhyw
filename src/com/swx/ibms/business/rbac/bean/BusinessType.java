package com.swx.ibms.business.rbac.bean;
/**
 * BusinessType.java 业务类别实体类
 * @author east
 * @date:2017年4月21日
 * @version:1.0
 * @description:--对应统一业务数据库中的当前单位下所对应部门的所属业务类别
 *
 */
public class BusinessType {
	/**
	 * 业务类别ID
	 */
	private int id;
	/**
	 * 部门类别编码
	 */
	private String bmlbbm;
	/**
	 * 业务编码
	 */
	private String ywbm;
	/**
	 * 业务名称 
	 */
	private String ywmc;
	/**
	 * 单位编码
	 */
	private String dwbm;
	/**
	 * 是否删除 Y:是  N：否
	 */
	private String sfsc;
	
	/**
	 * 
	 * @return 返回业务类别ID
	 */
	public int getId() {
		return id;
	}
	/**
	 * 
	 * @param id 传入业务类别ID
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 
	 * @return 返回部门类别编码
	 */
	public String getBmlbbm() {
		return bmlbbm;
	}
	/**
	 *
	 * @param bmlbbm 传入部门类别编码
	 */
	public void setBmlbbm(String bmlbbm) {
		this.bmlbbm = bmlbbm;
	}
	/**
	 * 
	 * @return 返回业务编码
	 */
	public String getYwbm() {
		return ywbm;
	}
	/**
	 * 
	 * @param ywbm 传入业务编码
	 */
	public void setYwbm(String ywbm) {
		this.ywbm = ywbm;
	}
	/**
	 * 
	 * @return 返回业务名称
	 */
	public String getYwmc() {
		return ywmc;
	}
	/**
	 * 
	 * @param ywmc 传入业务名称
	 */
	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}
	/**
	 * 
	 * @return 返回单位编码
	 */
	public String getDwbm() {
		return dwbm;
	}
	/**
	 * 
	 * @param dwbm 传入单位编码
	 */
	public void setDwbm(String dwbm) {
		this.dwbm = dwbm;
	}
	/**
	 * 
	 * @return 返回是否删除
	 */
	public String getSfsc() {
		return sfsc;
	}
	/**
	 * 
	 * @param sfsc 传入是否删除
	 */
	public void setSfsc(String sfsc) {
		this.sfsc = sfsc;
	}
	
}
