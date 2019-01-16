package com.swx.ibms.business.system.bean;

import java.util.Date;

/**
 * 
 * Upload.java 上传附件实体类
 * @author 朱春雨(ZCY)
 * @date<p>2017年5月16日</p>
 * @version 1.0
 * @description<p></p>
 */
public class Upload {
	/**
	 * 上传附件实体类id				
	 */
	private String id;
	/**
	 * 文件名称
	 */
	private String wjmc;
	/**
	 * 文件存放地址
	 */
	private String wjdz;
	/**
	 * 外键id(存放的是外键的id，并没有在数据库进行真正的外键关联)
	 */
	private String wbid;
	/**
	 * 附件类型(1 司法文档 附件 2 案件问题反馈附件 3 图片 4 文档  5 其他)
	 */
	private String fjlx;
	/**
	 * 上传日期
	 */
	private Date scrq;
	
	/**
	 * @return 上传附件实体类id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 
	 * @param id 传入上传附件实体类id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 
	 * @return 上传附件名称
	 */
	public String getWjmc() {
		return wjmc;
	}
	/**
	 * 
	 * @param wjmc 传入上传附件名称
	 */
	public void setWjmc(String wjmc) {
		this.wjmc = wjmc;
	}
	/**
	 * 
	 * @return 文件存放地址
	 */
	public String getWjdz() {
		return wjdz;
	}
	/**
	 * @param wjdz  传入文件存放地址
	 */
	public void setWjdz(String wjdz) {
		this.wjdz = wjdz;
	}
	/**
	 * 
	 * @return 外键id
	 */
	public String getWbid() {
		return wbid;
	}
	/**
	 * 
	 * @param wbid 传入外键id
	 */
	public void setWbid(String wbid) {
		this.wbid = wbid;
	}
	/**
	 * 
	 * @return 附件类型(1 司法文档 附件 2 案件问题反馈附件 3 图片 4 文档  5 其他)
	 */
	public String getFjlx() {
		return fjlx;
	}
	/**
	 * 
	 * @param fjlx 传入附件类型(1 司法文档 附件 2 案件问题反馈附件 3 图片 4 文档  5 其他)
	 */
	public void setFjlx(String fjlx) {
		this.fjlx = fjlx;
	}
	/**
	 * 
	 * @return 上传时间
	 */ 
	public Date getScrq() {
		return scrq;
	}
	/**
	 * 
	 * @param scrq 传入上传时间
	 */
	public void setScrq(Date scrq) {
		this.scrq = scrq;
	}
    
}
