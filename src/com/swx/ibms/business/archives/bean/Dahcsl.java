package com.swx.ibms.business.archives.bean;

import java.sql.Date;

/**
 * @author zsq
 * 档案核查实例
 */
public class Dahcsl {
	
	/**
	 * id
	 */
	private String id;
	
	/**
	 * 档案归总id
	 */
	private String dagzid;
	
	/**
	 * 问题所属分类
	 */
	private int wtfl;
	
	/**
	 * 问题所属分类名称
	 */
	private String wtmc;
	
	/**
	 * 档案问题分类
	 */
	private int dafl;
	
	/**
	 * 档案问题分类名称
	 */
	private String damc;
	
	/**
	 * 问题描述
	 */
	private String wtms;
	
	/**
	 * 附件路径
	 */
	private String fjid;
	
	/**
	 * 状态
	 */
	private int zt;
	
	/**
	 * 处理人
	 */
	private String clr;
	
	/**
	 * 处理时间
	 */
	private Date clsj;
	
	/**
	 * 处理结果
	 */
	private String cljg;
	
	/**
	 * 处理意见
	 */
	private String clyj;
	
	
	/**
	 * 处理人单位编码
	 */
	private String clrdwbm;
	
	
	/**
	 * 处理人单位名称
	 */
	private String clrdwmc;
	
	
	/**
	 * 处理人部门编码
	 */
	private String clrbmbm;
	
	
	/**
	 * 处理人部门名称
	 */
	private String clrbmmc;
	
	
	/**
	 * 发起人工号
	 */
	private String fqrgh;
	
	/**
	 * @return clrdwbm
	 */
	public String getClrdwbm() {
		return clrdwbm;
	}

	/**
	 * @param clrdwbm clrdwbm
	 */
	public void setClrdwbm(String clrdwbm) {
		this.clrdwbm = clrdwbm;
	}

	/**
	 * @return clrdwmc
	 */
	public String getClrdwmc() {
		return clrdwmc;
	}

	/**
	 * @param clrdwmc clrdwmc
	 */
	public void setClrdwmc(String clrdwmc) {
		this.clrdwmc = clrdwmc;
	}

	/**
	 * @return clrbmbm
	 */
	public String getClrbmbm() {
		return clrbmbm;
	}

	/**
	 * @param clrbmbm clrbmbm
	 */
	public void setClrbmbm(String clrbmbm) {
		this.clrbmbm = clrbmbm;
	}

	/**
	 * @return clrbmmc
	 */
	public String getClrbmmc() {
		return clrbmmc;
	}

	/**
	 * @param clrbmmc clrbmmc
	 */
	public void setClrbmmc(String clrbmmc) {
		this.clrbmmc = clrbmmc;
	}

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return dagzid
	 */
	public String getDagzid() {
		return dagzid;
	}

	/**
	 * @param dagzid dagzid
	 */
	public void setDagzid(String dagzid) {
		this.dagzid = dagzid;
	}

	/**
	 * @return wtfl
	 */
	public int getWtfl() {
		return wtfl;
	}

	/**
	 * @param wtfl wtfl
	 */
	public void setWtfl(int wtfl) {
		this.wtfl = wtfl;
	}

	/**
	 * @return wtmc
	 */
	public String getWtmc() {
		return wtmc;
	}

	/**
	 * @param wtmc wtmc
	 */
	public void setWtmc(String wtmc) {
		this.wtmc = wtmc;
	}

	/**
	 * @return dafl
	 */
	public int getDafl() {
		return dafl;
	}

	/**
	 * @param dafl dafl
	 */
	public void setDafl(int dafl) {
		this.dafl = dafl;
	}

	/**
	 * @return damc
	 */
	public String getDamc() {
		return damc;
	}

	/**
	 * @param damc damc
	 */
	public void setDamc(String damc) {
		this.damc = damc;
	}

	
	/**
	 * @return wtms
	 */
	public String getWtms() {
		return wtms;
	}

	/**
	 * @param wtms wtms
	 */
	public void setWtms(String wtms) {
		this.wtms = wtms;
	}

	/**
	 * @return fjid
	 */
	public String getFjid() {
		return fjid;
	}

	/**
	 * @param fjid fjid
	 */
	public void setFjid(String fjid) {
		this.fjid = fjid;
	}

	/**
	 * @return zt
	 */
	public int getZt() {
		return zt;
	}

	/**
	 * @param zt zt
	 */
	public void setZt(int zt) {
		this.zt = zt;
	}

	/**
	 * @return clr
	 */
	public String getClr() {
		return clr;
	}

	/**
	 * @param clr clr
	 */
	public void setClr(String clr) {
		this.clr = clr;
	}

	/**
	 * @return clsj
	 */
	public Date getClsj() {
		return clsj;
	}

	/**
	 * @param clsj clsj
	 */
	public void setClsj(Date clsj) {
		this.clsj = clsj;
	}

	/**
	 * @return cljg
	 */
	public String getCljg() {
		return cljg;
	}

	/**
	 * @param cljg cljg
	 */
	public void setCljg(String cljg) {
		this.cljg = cljg;
	}

	/**
	 * @return clyj
	 */
	public String getClyj() {
		return clyj;
	}

	/**
	 * @param clyj clyj
	 */
	public void setClyj(String clyj) {
		this.clyj = clyj;
	}

	/**
	 * @return fqrgh
	 */
	public String getFqrgh() {
		return fqrgh;
	}

	/**
	 * @param fqrgh fqrgh
	 */
	public void setFqrgh(String fqrgh) {
		this.fqrgh = fqrgh;
	}
	
	
	
	
}
