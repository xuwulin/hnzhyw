package com.swx.ibms.business.common.bean;

/**
 * @author 李佳
 * 树形菜单实体类 <br/>
 * 主要属性：单位编码、单位名称
 */
public class TreeSelect {
	
	/**
	 * 单位编码
	 */
	private String dwbm;  
	/**
	 * 单位名称
	 */
	private String dwmc;  
	
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
}
