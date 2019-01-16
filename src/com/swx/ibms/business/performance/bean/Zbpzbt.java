package com.swx.ibms.business.performance.bean;

import java.sql.Date;

/**
 * 指标配置表头实体类
 * @author zsq
 *
 */
public class Zbpzbt {
	/**
	 * id
	 */
	private String id;		
	/**
	 * 创建时间
	 */
	private Date cjsjl;
	/**
	 * 创建人
	 */
	private String cjr;
	/**
	 * 表头数据
	 */
	private String btsj;
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
	 * @return cjsjl
	 */
	public Date getCjsjl() {
		return cjsjl;
	}
	/**
	 * @param cjsjl cjsjl
	 */
	public void setCjsjl(Date cjsjl) {
		this.cjsjl = cjsjl;
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
	 * @return 表头数据
	 */
	public String getBtsj() {
		return btsj;
	}
	/**
	 * @param btsj 表头数据
	 */
	public void setBtsj(String btsj) {
		this.btsj = btsj;
	}
	

}
