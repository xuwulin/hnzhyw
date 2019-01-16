/**
 * 
 */
package com.swx.ibms.business.appraisal.bean;

import java.sql.Date;

/**
 * 业务工作指标配置表头
 * @author 封泽超
 * @since 2017年6月6日
 */
public class Ywgzzbpzbt {
	/**
	 * 编号
	 */
	private String id;
	/**
	 * 创建时间
	 */
	private Date cjsj;
	/**
	 * 创建人
	 */
	private String cjr;
	/**
	 * 表头数据
	 */
	private String btsj;
	/**
	 * 配置表头数据
	 */
	private String pzbtsj;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCjsj() {
		return cjsj;
	}
	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}
	public String getCjr() {
		return cjr;
	}
	public void setCjr(String cjr) {
		this.cjr = cjr;
	}
	public String getBtsj() {
		return btsj;
	}
	public void setBtsj(String btsj) {
		this.btsj = btsj;
	}
	public String getPzbtsj() {
		return pzbtsj;
	}
	public void setPzbtsj(String pzbtsj) {
		this.pzbtsj = pzbtsj;
	}
	
	
}
