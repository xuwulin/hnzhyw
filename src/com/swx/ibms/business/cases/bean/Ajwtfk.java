package com.swx.ibms.business.cases.bean;

/**
 * @author 李佳
 * 案件问题反馈实体类 <br/>
 * 主要属性：案件问题反馈id、单位编码、工号、通知名称、通知类型 、外部实体id、是否读取、创建时间 、统一受案号
 */
public class Ajwtfk {
	/**
	 * 实体id
	 */
	private String id;  
	
	/**
	 * 单位编码
	 */
	private String dwbm; 
	
	/**
	 * 工号
	 */
	private String gh;  
	
	/**
	 * 通知名称
	 */
	private String tzmc;  
	
	/**
	 * 通知类型 
	 */
	private String tzlx;  
	
	/**
	 * 外部实体id
	 */
	private String wbid; 
	
	/**
	 * 是否读取
	 */
	private String isRead; 
	
	/**
	 * 创建时间 
	 */
	private String cjsj;  
	
	/**
	 * 统一受案号 
	 */
	private String tysah;   
	
	/**
	 * 部门受案号
	 */
	private String bmsah;    
	
	/**
	 * 
	 * @return 案件问题反馈id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 
	 * @param id 传入案件问题反馈id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * @return 单位编码
	 */
	public String getDwbm() {
		return dwbm;
	}

	/**
	 * 
	 * @param dwbm 传入单位编码
	 */
	public void setDwbm(String dwbm) {
		this.dwbm = dwbm;
	}

	/**
	 * 
	 * @return 工号
	 */
	public String getGh() {
		return gh;
	}

	/**
	 * 
	 * @param gh 传入工号
	 */
	public void setGh(String gh) {
		this.gh = gh;
	}

	/**
	 * 
	 * @return 通知名称
	 */
	public String getTzmc() {
		return tzmc;
	}

	/**
	 * 
	 * @param tzmc 传入通知名称
	 */
	public void setTzmc(String tzmc) {
		this.tzmc = tzmc;
	}

	/**
	 * 
	 * @return 通知类型 
	 */
	public String getTzlx() {
		return tzlx;
	}

	/**
	 * 
	 * @param tzlx 传入通知类型 
	 */
	public void setTzlx(String tzlx) {
		this.tzlx = tzlx;
	}

	/**
	 * 
	 * @return 外部实体id
	 */
	public String getWbid() {
		return wbid;
	}

	/**
	 * 
	 * @param wbid 传入外部实体id
	 */
	public void setWbid(String wbid) {
		this.wbid = wbid;
	}

	/**
	 * 
	 * @return 是否读取
	 */
	public String getIsRead() {
		return isRead;
	}

	/**
	 * 
	 * @param isRead 传入是否读取
	 */
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	/**
	 * 
	 * @return 创建时间
	 */
	public String getCjsj() {
		return cjsj;
	}

	/**
	 * 
	 * @param cjsj 传入创建时间
	 */
	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}

	/**
	 * 
	 * @return 统一受案号
	 */
	public String getTysah() {
		return tysah;
	}

	/**
	 * 
	 * @param tysah 传入统一受案号
	 */
	public void setTysah(String tysah) {
		this.tysah = tysah;
	}

	/**
	 * 
	 * @return 部门受案号 
	 */
	public String getBmsah() {
		return bmsah;
	}

	/**
	 * 
	 * @param bmsah 传入部门受案号 
	 */
	public void setBmsah(String bmsah) {
		this.bmsah = bmsah;
	}
	
}
