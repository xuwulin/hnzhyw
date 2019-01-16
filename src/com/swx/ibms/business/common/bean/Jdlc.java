package com.swx.ibms.business.common.bean;

/**
 * <p>
 * Title:jdlc
 * </p>
 * <p>
 * Description: 节点流程实体类
 * </p>
 * author 朱春雨 date 2017年3月6日 下午2:13:45
 */
public class Jdlc {

	/**
	 * 流程节点编号
	 */
	private String id;
	/**
	 * 节点类型
	 */
	private String jdlx;
	/**
	 * 流程节点
	 */
	private String lcjd;
	/**
	 * 下一节点
	 */
	private String xyjd;
	/**
	 * 下一处理角色
	 */
	private String xycljs;
	/**
	 * 下一处理部门 0:当前部门
	 */
	private String xyclbm;
	/**
	 * 下一处理单位 0:当前单位
	 */
	private String xycldw;
	/**
	 * 节点状态 0:开始 1:过程中 2:结束
	 */
	private String jdzt;
	/**
	 * 说明
	 */
	private String sm;
	/**
	 * 下一步处理单位类型 1：上级 2：当前
	 */
	private String xycldwlx;
	/**
	 * 流程类型：1：公示 ，2：档案
	 */
	private String lclx;
	
	/**
	 * 状态变更 
	 */
	private String ztbg;
	
	/**
	 * 处理角色限制
	 */
	private String cljsxz;
	
	/**
	 * 是否有流程实例 
	 */
	private String Y;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJdlx() {
		return jdlx;
	}
	public void setJdlx(String jdlx) {
		this.jdlx = jdlx;
	}
	public String getLcjd() {
		return lcjd;
	}
	public void setLcjd(String lcjd) {
		this.lcjd = lcjd;
	}
	public String getXyjd() {
		return xyjd;
	}
	public void setXyjd(String xyjd) {
		this.xyjd = xyjd;
	}
	public String getXycljs() {
		return xycljs;
	}
	public void setXycljs(String xycljs) {
		this.xycljs = xycljs;
	}
	public String getXyclbm() {
		return xyclbm;
	}
	public void setXyclbm(String xyclbm) {
		this.xyclbm = xyclbm;
	}
	public String getXycldw() {
		return xycldw;
	}
	public void setXycldw(String xycldw) {
		this.xycldw = xycldw;
	}
	public String getJdzt() {
		return jdzt;
	}
	public void setJdzt(String jdzt) {
		this.jdzt = jdzt;
	}
	public String getSm() {
		return sm;
	}
	public void setSm(String sm) {
		this.sm = sm;
	}
	public String getXycldwlx() {
		return xycldwlx;
	}
	public void setXycldwlx(String xycldwlx) {
		this.xycldwlx = xycldwlx;
	}
	public String getLclx() {
		return lclx;
	}
	public void setLclx(String lclx) {
		this.lclx = lclx;
	}
	public String getZtbg() {
		return ztbg;
	}
	public void setZtbg(String ztbg) {
		this.ztbg = ztbg;
	}
	public String getY() {
		return Y;
	}
	public void setY(String y) {
		Y = y;
	}
	public String getCljsxz() {
		return cljsxz;
	}
	public void setCljsxz(String cljsxz) {
		this.cljsxz = cljsxz;
	}
	
	
}
