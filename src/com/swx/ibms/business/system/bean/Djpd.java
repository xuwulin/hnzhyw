package com.swx.ibms.business.system.bean;

/**
 * 等级评定实体类
 * @author 李佳
 * @date: 2017年5月22日 
 */
public class Djpd {
	/**
	 * id
	 */
	private String id;
	/**
	 * 等级得分评定下限
	 */
	private double djdfsdxx;
	/**
	 * 等级评定等分上限
	 */
	private double djdfsdsx;
	/**
	 * 评定级别
	 */
	private String pdjb;
	/**
	 * 评定级别名称
	 */
	private String pdjbmc;
	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id 传入id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return 等级得分设定下限
	 */
	public double getDjdfsdxx() {
		return djdfsdxx;
	}
	/**
	 * @param djdfsdxx 传入等级得分设定下限
	 */
	public void setDjdfsdxx(double djdfsdxx) {
		this.djdfsdxx = djdfsdxx;
	}
	/**
	 * @return 等级得分设定上限
	 */
	public double getDjdfsdsx() {
		return djdfsdsx;
	}
	/**
	 * @param djdfsdsx 传入等级得分设定上限
	 */
	public void setDjdfsdsx(double djdfsdsx) {
		this.djdfsdsx = djdfsdsx;
	}
	/**
	 * @return 评定级别
	 */
	public String getPdjb() {
		return pdjb;
	}
	/**
	 * @param pdjb 传入评定级别
	 */
	public void setPdjb(String pdjb) {
		this.pdjb = pdjb;
	}
	/**
	 * @return 评定级别名称
	 */
	public String getPdjbmc() {
		return pdjbmc;
	}
	/**
	 * @param pdjbmc 传入评定级别名称
	 */
	public void setPdjbmc(String pdjbmc) {
		this.pdjbmc = pdjbmc;
	}
}
