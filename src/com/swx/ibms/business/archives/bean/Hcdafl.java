package com.swx.ibms.business.archives.bean;

/**
 * @author zsq
 * 核查档案问题分类
 */
public class Hcdafl {
	
	/**
	 * id
	 */
	private String id;
	
	/**
	 * 分类
	 */
	private int fl;
	
	/**
	 * 分类名称
	 */
	private String flmc;
	
	/**
	 * 创建人
	 */
	private String cjr;
	
	/**
	 * 是否删除
	 */
	private String sfsc;

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
	 * @return 分类
	 */
	public int getFl() {
		return fl;
	}

	/**
	 * @param fl 分类
	 */
	public void setFl(int fl) {
		this.fl = fl;
	}

	/**
	 * @return 分类名称
	 */
	public String getFlmc() {
		return flmc;
	}

	/**
	 * @param flmc  分类名称
	 */
	public void setFlmc(String flmc) {
		this.flmc = flmc;
	}

	/**
	 * @return 创建人
	 */
	public String getCjr() {
		return cjr;
	}

	/**
	 * @param cjr 创建人
	 */
	public void setCjr(String cjr) {
		this.cjr = cjr;
	}

	/**
	 * @return 是否删除
	 */
	public String getSfsc() {
		return sfsc;
	}

	/**
	 * @param sfsc 是否删除
	 */
	public void setSfsc(String sfsc) {
		this.sfsc = sfsc;
	}

}
