package com.swx.ibms.business.rbac.bean;

/**
 * @author 李佳
 * 角色实体类 <br/>
 * 主要属性：角色编码、角色名称
 */
public class Role {
     /**
     * 角色编码
     */
    private String jsbm; 
     /**
     * 角色名称
     */
    private String jsmc; 
	/**
	 * @return 角色编码
	 */
	public String getJsbm() {
		return jsbm;
	}
	/**
	 * @param jsbm 传入角色编码
	 */
	public void setJsbm(String jsbm) {
		this.jsbm = jsbm;
	}
	/**
	 * @return 角色名称
	 */
	public String getJsmc() {
		return jsmc;
	}
	/**
	 * @param jsmc 角色名称
	 */
	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}
     
}
