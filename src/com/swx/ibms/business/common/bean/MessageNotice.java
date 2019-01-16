package com.swx.ibms.business.common.bean;

import java.util.Date;

/**
 * 
 * MessageNotice.java  消息实体类
 * @author east
 * @date<p>2017年5月25日</p>
 * @version 1.0
 * @description<p>记录档案更新通知、待办业务通知、公示通知的操作信息。</p>
 */
public class MessageNotice {
	/**
	 * 消息id
	 */
	private String id;
	/**
	 * 单位编码
	 */
	private String dwbm;
	/**
	 * 部门编码
	 */
	private String bmbm;
	/**
	 * 工号
	 */
	private String gh;
	/**
	 * 消息名称
	 */
	private String name;
	/**
	 * 消息内容
	 */
	private String content;
	/**
	 * 消息类型（保留字段以防后面要用）
	 */
	private String type;
	/**
	 * 操作人
	 */
	private String operator;
	/**
	 * 操作时间
	 */
	private Date operateTime;
	/**
	 * 操作状态:1 未查看 2 已查看 3 其他
	 */
	private String status;
	/**
	 * 设置得到消息id
	 * @return String 
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置消息id
	 * @param id 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 得到单位编码
	 * @return String 
	 */
	public String getDwbm() {
		return dwbm;
	}
	/**
	 * 设置单位编码
	 * @param dwbm 
	 */
	public void setDwbm(String dwbm) {
		this.dwbm = dwbm;
	}
	/**
	 * 得到单位编码
	 * @return String  
	 */
	public String getBmbm() {
		return bmbm;
	}
	/**
	 * 设置部门编码
	 * @param bmbm 
	 */
	public void setBmbm(String bmbm) {
		this.bmbm = bmbm;
	}
	/**
	 * 得到工号
	 * @return String 
	 */
	public String getGh() {
		return gh;
	}
	/**
	 * 设置工号
	 * @param gh 
	 */
	public void setGh(String gh) {
		this.gh = gh;
	}
	/**
	 * 得到消息名称 
	 * @return String 
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置消息名称
	 * @param name 
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 得到消息内容
	 * @return String 
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置消息内容	
	 * @param content 
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 得到消息类型
	 * @return String 
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置消息类型
	 * @param type 
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 得到操作人
	 * @return String 
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * 设置操作人
	 * @param operator 
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	/**
	 * 得到操作时间
	 * @return Date 
	 */
	public Date getOperateTime() {
		return operateTime;
	}
	/**
	 * 设置操作时间
	 * @param operateTime 
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	/**
	 * 得到操作状态
	 * @return String 操作状态:1 未查看 2 已查看 3 其他
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置操作状态
	 * @param status 操作状态:1 未查看 2 已查看 3 其他
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
}
