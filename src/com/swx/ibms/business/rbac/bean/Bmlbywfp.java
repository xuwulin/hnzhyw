package com.swx.ibms.business.rbac.bean;

/**
 * 部门类别业务分配实体类【部门映射与业务编码的中间表】
 * Bmlbywfp.java 
 * @author 何向东
 * @date<p>2017年12月12日</p>
 * @version 1.0
 * @description<p></p>
 */
public class Bmlbywfp {
	
	/**
	 * 部门类别业务分配id【主键】 
	 */
	private int id;
	/**
	 * 部门类别编码【部门映射】（一般来说只有4位）
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
	 * 业务名称
	 */
	private String ywjc;
	/**
	 * 单位编码
	 */
	private String dwbm;
	
	/**
	 * 单位名称
	 */
	private String dwmc;
	
	/**
	 * 类型【1 统一业务 2 综合业务】
	 */
	private String type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBmlbbm() {
		return bmlbbm;
	}

	public void setBmlbbm(String bmlbbm) {
		this.bmlbbm = bmlbbm;
	}

	public String getYwbm() {
		return ywbm;
	}

	public void setYwbm(String ywbm) {
		this.ywbm = ywbm;
	}

	public String getYwmc() {
		return ywmc;
	}

	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}

	public String getYwjc() {
		return ywjc;
	}

	public void setYwjc(String ywjc) {
		this.ywjc = ywjc;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Bmlbywfp [id=" + id + ", bmlbbm=" + bmlbbm + ", ywbm=" + ywbm + ", ywmc=" + ywmc + ", ywjc=" + ywjc
				+ ", dwbm=" + dwbm + ", dwmc=" + dwmc + ", type=" + type + "]";
	}
	
}
