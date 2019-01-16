package com.swx.ibms.business.archives.bean;


/**
 * @author 李佳
 * 司法档案内容实体类 <br/>
 * 主要属性：实体id、档案类别、技能描述、添加人、添加时间、档案类型、归档id
 */
public class Sfda {
	
	/**
	 * 实体id
	 */
	private String id;
	/**
	 * 档案类别
	 */
	private String lb; 
	/**
	 * 技能描述
	 */
	private String ms;  
	/**
	 * 添加人
	 */
	private String cjr;   
	/**
	 * 添加时间
	 */
	private String cjsj;    
	/**
	 * 档案类型 
	 */
	private String dalx;   
	/**
	 * 归档id
	 */
	private String gdId;    
	
	/**
	 * @return 实体id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id 传入实体id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return 档案类别
	 */
	public String getLb() {
		return lb;
	}
	/**
	 * @param lb 传入档案类别
	 */
	public void setLb(String lb) {
		this.lb = lb;
	}
	/**
	 * @return 档案描述
	 */
	public String getMs() {
		return ms;
	}
	/**
	 * @param ms 传入档案描述
	 */
	public void setMs(String ms) {
		this.ms = ms;
	}
	/**
	 * @return 创建人
	 */
	public String getCjr() {
		return cjr;
	}
	/**
	 * @param cjr 传入创建人
	 */
	public void setCjr(String cjr) {
		this.cjr = cjr;
	}
	/**
	 * @return 创建时间
	 */
	public String getCjsj() {
		return cjsj;
	}
	/**
	 * @param cjsj 传入创建时间
	 */
	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}
	/**
	 * @return 档案类型
	 */
	public String getDalx() {
		return dalx;
	}
	/**
	 * @param dalx 传入档案类型
	 */
	public void setDalx(String dalx) {
		this.dalx = dalx;
	}
	/**
	 * @return 归档id
	 */
	public String getGdId() {
		return gdId;
	}
	/**
	 * @param gdId 传入归档id
	 */
	public void setGdId(String gdId) {
		this.gdId = gdId;
	}
	
}
