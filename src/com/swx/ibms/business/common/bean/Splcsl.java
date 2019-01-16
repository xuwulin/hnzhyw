package com.swx.ibms.business.common.bean;

import java.sql.Date;

/**
 * 审批流程实例实体类
 * @author 李治鑫
 * @since 2017-5-8
 */
public class Splcsl {
  
	/**
	 * 编号
	 */
	private String id;
	/**
	 * 审批单位
	 */
	private String spdw;
	/**
	 * 审批部门
	 */
	private String spbm;
	/**
	 * 审批人
	 */
	private String spr;
	/**
	 * 审批意见
	 */
	private String spyj;
	/**
	 * 审批类型
	 */
	private String splx;
	/**
	 * 审批状态
	 */
	private String spzt;
	/**
	 * 审批实体id
	 */
	private String spstid;
	/**
	 * 审批时间
	 */
	private Date spsj;
	/**
	 * 序号
	 */
	private String xh;
	/**
	 * 处理角色
	 */
	private String cljs;
	/**
	 * 流程节点
	 */
	private String lcjd;
	/**
	 * 节点类型
	 */
	private String jdlx;
	
	/**
	 * 流程id
	 */
	private String lcid;
	
//	/**
//	 * 流程ID
//	 */
//	private String lcid;
//	
//	
//	/**
//	 * 获取流程ID
//	 * @return 流程ID
//	 */
//	public String getLcid() {
//		return lcid;
//	}
//	/**
//	 * 设置流程ID
//	 * @param lcid 流程ID
//	 */
//	public void setLcid(String lcid) {
//		this.lcid = lcid;
//	}
	
	public String getLcid() {
		return lcid;
	}
	public void setLcid(String lcid) {
		this.lcid = lcid;
	}
	/**
	 * 获取编号
	 * @return 编号
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置编号
	 * @param id 编号
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取审批单位
	 * @return 审批单位
	 */
	public String getSpdw() {
		return spdw;
	}
	/**
	 * 设置审批单位
	 * @param spdw 审批单位
	 */
	public void setSpdw(String spdw) {
		this.spdw = spdw;
	}
	/**
	 * 获取审批部门
	 * @return 审批部门
	 */
	public String getSpbm() {
		return spbm;
	}
	/**
	 * 设置审批部门
	 * @param spbm 审批部门
	 */
	public void setSpbm(String spbm) {
		this.spbm = spbm;
	}
	/**
	 * 获取审批人工号
	 * @return 审批人工号
	 */
	public String getSpr() {
		return spr;
	}
	/**
	 * 设置审批人工号
	 * @param spr 审批人工号
	 */
	public void setSpr(String spr) {
		this.spr = spr;
	}
	/**
	 * 获取审批意见
	 * @return 审批意见
	 */
	public String getSpyj() {
		return spyj;
	}
	/**
	 * 设置审批意见
	 * @param spyj 审批意见
	 */
	public void setSpyj(String spyj) {
		this.spyj = spyj;
	}
	/**
	 * 获取审批类型
	 * @return 审批类型
	 */
	public String getSplx() {
		return splx;
	}
	/**
	 * 设置审批类型
	 * @param splx 审批类型
	 */
	public void setSplx(String splx) {
		this.splx = splx;
	}
	/**
	 * 获取审批状态
	 * @return 审批状态
	 */
	public String getSpzt() {
		return spzt;
	}
	/**
	 * 设置审批状态
	 * @param spzt 审批状态
	 */
	public void setSpzt(String spzt) {
		this.spzt = spzt;
	}
	/**
	 * 获取审批实体ID
	 * @return 审批实体ID
	 */
	public String getSpstid() {
		return spstid;
	}
	/**
	 * 设置审批实体ID
	 * @param spstid 审批实体ID
	 */
	public void setSpstid(String spstid) {
		this.spstid = spstid;
	}
	
	/**
	 * 获取审批时间
	 * @return 审批时间
	 */
	public Date getSpsj() {
		return spsj;
	}
	/**
	 * 设置审批时间
	 * @param spsj 审批时间
	 */
	public void setSpsj(Date spsj) {
		this.spsj = spsj;
	}
	/**
	 * 获取序号
	 * @return 序号
	 */
	public String getXh() {
		return xh;
	}
	/**
	 * 设置序号
	 * @param xh 序号
	 */
	public void setXh(String xh) {
		this.xh = xh;
	}
	/**
	 * 获取处理角色
	 * @return 处理角色
	 */
	public String getCljs() {
		return cljs;
	}
	/**
	 * 设置处理角色
	 * @param cljs 处理角色
	 */
	public void setCljs(String cljs) {
		this.cljs = cljs;
	}
	/**
	 * 获取流程节点
	 * @return 流程节点
	 */
	public String getLcjd() {
		return lcjd;
	}
	/**
	 * 设置流程节点
	 * @param lcjd 流程节点
	 */
	public void setLcjd(String lcjd) {
		this.lcjd = lcjd;
	}
	/**
	 * 获取节点类型
	 * @return 节点类型
	 */
	public String getJdlx() {
		return jdlx;
	}
	/**
	 * 设置节点类型
	 * @param jdlx 节点类型
	 */
	public void setJdlx(String jdlx) {
		this.jdlx = jdlx;
	}
	
}
