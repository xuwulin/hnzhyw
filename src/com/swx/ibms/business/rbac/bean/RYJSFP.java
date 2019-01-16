package com.swx.ibms.business.rbac.bean;

/**
 * 人员角色分配实体类
 * @author 李治鑫
 * @since 2017-5-9
 */
public class RYJSFP {
	/**
	 * 单位编码
	 */
	private String dwbm;
	/**
	 * 部门编码
	 */
	private String bmbm;
	/**
	 * 角色编码
	 */
	private String jsbm;
	/**
	 * 工号
	 */
	private String gh;
	/**
	 * 直接领导工号
	 */
	private String zjldgh;
	/**
	 * 人员序号
	 */
	private int ryxh;
	/**
	 * 无参构造函数
	 */
	public RYJSFP(){}
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
	 * 获取部门编码
	 * @return 部门编码
	 */
	public String getBmbm() {
		return bmbm;
	}
	/**
	 * 设置部门编码
	 * @param bmbm 部门编码
	 */
	public void setBmbm(String bmbm) {
		this.bmbm = bmbm;
	}
	/**
	 * 获取角色编码
	 * @return 角色编码
	 */
	public String getJsbm() {
		return jsbm;
	}
	/**
	 * 设置角色编码
	 * @param jsbm 角色编码
	 */
	public void setJsbm(String jsbm) {
		this.jsbm = jsbm;
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
	 * 获取直接领导工号
	 * @return 直接领导工号
	 */
	public String getZjldgh() {
		return zjldgh;
	}
	/**
	 * 设置直接领导工号
	 * @param zjldgh 直接领导工号
	 */
	public void setZjldgh(String zjldgh) {
		this.zjldgh = zjldgh;
	}
	/**
	 * 获取人员序号
	 * @return 人员序号
	 */
	public int getRyxh() {
		return ryxh;
	}
	/**
	 * 设置人员序号
	 * @param ryxh 人员序号
	 */
	public void setRyxh(int ryxh) {
		this.ryxh = ryxh;
	}
	
}
