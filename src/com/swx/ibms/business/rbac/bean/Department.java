package com.swx.ibms.business.rbac.bean;

/**
 * 
 * Department.java 只有部门编码和部门名称的部门实体类
 * @author 李佳 
 * @date<p>2017年5月12日</p>
 * @version 1.0
 * @description<p></p>
 */
public class Department {
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
     * @return 返回部门编码
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
	 * @return 返回部门名称
	 */
	public String getBmmc() {
		return bmmc;
	}
	/**
	 * 
	 * @param bmmc 传入部门名称
	 */
	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}
    
}
