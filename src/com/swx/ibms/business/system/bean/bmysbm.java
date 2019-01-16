package com.swx.ibms.business.system.bean;

/**
 * <p>
 * Title:bmysbm
 * </p>
 * <p>
 * Description: 通过部门映射和单位编码找出部门
 * </p>
 * author 朱春雨 date 2017年3月30日 下午9:05:30 <br/>
 * 主要属性：单位编码,部门编码,部门映射,父部门编码
 */
public class bmysbm{
	/**
	 * 单位编码
	 */
	private String dwbm;
	/**
	 * 部门编码
	 */
	private String bmbm;
	
	/**
	 * 部门映射
	 */
	private String bmys;
	/**
	 * 父部门编码
	 */
	private String fbmbm;

	/**
	 * 
	 * @return 单位编码 
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
	 * @return 部门编码 
	 */
	public String getBmbm() {
		return bmbm;
	}

	/**
	 * 
	 * @param bmbm 传入部门编码
	 */
	public void setBmbm(String bmbm) {
		this.bmbm = bmbm;
	}

	/**
	 * 
	 * @return 部门映射 
	 */
	public String getBmys() {
		return bmys;
	}

	/**
	 * 
	 * @param bmys 传入部门映射 
	 */
	public void setBmys(String bmys) {
		this.bmys = bmys;
	}

	/**
	 * 
	 * @return 父部门编码 
	 */
	public String getFbmbm() {
		return fbmbm;
	}

	/**
	 * 
	 * @param fbmbm 传入父部门编码 
	 */
	public void setFbmbm(String fbmbm) {
		this.fbmbm = fbmbm;
	}
	

}
