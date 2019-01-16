package com.swx.ibms.business.rbac.bean;

/**
 * 单位编码实体类
 * @author 李治鑫
 * @since 2017-5-9
 */
public class DWBM {
	/**
	 * 单位编码
	 */
	private String dwbm;	
	/**
	 * 单位名称
	 */
	private String dwmc;
	/**
	 * 父单位编码
	 */
	private String fdwbm;
	/**
	 * 单位级别
	 */
	private String dwjb;
	/**
	 * 是否删除
	 */
	private String sfsc;
	/**
	 * 单位简称
	 */
	private String dwjc;
	/**
	 * 单位标识：0 普通 1铁检 2林检 3军检
	 */
	private String dwsx;
	
	/**
	 * 部门编码
	 */
	private String bmbm;
	/**
	 * 部门名称
	 */
	private String bmmc;
	
	/**
	 * 
	 * @return 部门名称
	 */
	public String getBmmc() {
		return bmmc;
	}

	/**
	 * 
	 * @param bmmc 部门名称
	 */
	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
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
	 * 无参构造函数
	 */
	public DWBM(){}
	

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
	 * 获取单位名称
	 * @return 单位名称
	 */
	public String getDwmc() {
		return dwmc;
	}
	/**
	 * 设置单位名称
	 * @param dwmc 单位名称
	 */
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}
	/**
	 * 获取父单位编码
	 * @return 父单位编码
	 */
	public String getFdwbm() {
		return fdwbm;
	}
	/**
	 * 设置父单位编码
	 * @param fdwbm 父单位编码
	 */
	public void setFdwbm(String fdwbm) {
		this.fdwbm = fdwbm;
	}
	/**
	 * 获取单位级别
	 * @return 单位级别
	 */
	public String getDwjb() {
		return dwjb;
	}
	/**
	 * 设置单位级别
	 * @param dwjb 单位级别
	 */
	public void setDwjb(String dwjb) {
		this.dwjb = dwjb;
	}
	/**
	 * 获取是否删除
	 * @return 是否删除
	 */
	public String getSfsc() {
		return sfsc;
	}
	/**
	 * 设置是否删除
	 * @param sfsc 是否删除
	 */
	public void setSfsc(String sfsc) {
		this.sfsc = sfsc;
	}
	/**
	 * 获取单位简称
	 * @return 单位简称
	 */
	public String getDwjc() {
		return dwjc;
	}
	/**
	 * 设置单位简称
	 * @param dwjc 单位简称
	 */
	public void setDwjc(String dwjc) {
		this.dwjc = dwjc;
	}
	/**
	 * 获取单位标识
	 * @return 单位标识
	 */
	public String getDwsx() {
		return dwsx;
	}
	/**
	 * 设置单位标识
	 * @param dwsx 单位标识
	 */
	public void setDwsx(String dwsx) {
		this.dwsx = dwsx;
	}
	
}
