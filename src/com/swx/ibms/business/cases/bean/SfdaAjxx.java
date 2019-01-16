package com.swx.ibms.business.cases.bean;

import java.util.Date;

/**
 * 
 * SfdaAjxx.java 司法档案下检察官填写的案件信息实体类
 * @author 何向东
 * @date<p>2017年8月3日</p>
 * @version 1.0
 * @description<p></p>
 */
public class SfdaAjxx {
	/**
	 * //案件id
	 */
	private String ajId;
	/**
	 *  //档案id
	 */
	private String daId;
	/**
	 * //案件名称
	 */
	private String ajName;
	/**
	 * //案件业务类型,eg:公诉、侦监 
	 */
	private String ajYwlx;
	/**
	 * //案件受理日期
	 */
	private Date ajSlrq;
	/**
	 * //案件完结日期
	 */
	private Date ajWjrq;
	/**
	 * //案件类别
	 */
	private String ajlb;
	/**
	 * //案件数量
	 */
	private Integer ajsl;
	/**
	 * //案件备注
	 */
	private String bz;
	/**
	 * //案件类型，eg：主办、参办（从办）
	 */
	private String ajlx;
	/**
	 * //创建时间
	 */
	private Date createTime;
	/**
	 * //修改时间
	 */
	private Date modifyTime;
	/**
	 * 案件状态:1 已审批 2 未审批 3 未通过
	 */
	private String ajStatus;
	
	/**
	 * 
	 * @return 案件状态:1 已审批 2 未审批 3 未通过
	 */
	public String getAjStatus() {
		return ajStatus;
	}
	/**
	 * 
	 * @param ajStatus 案件状态:1 已审批 2 未审批 3 未通过
	 */
	public void setAjStatus(String ajStatus) {
		this.ajStatus = ajStatus;
	}
	/**
	 * 
	 * @return 案件信息id
	 */
	public String getAjId() {
		return ajId;
	}
	/**
	 * 
	 * @param ajId 案件信息id
	 */
	public void setAjId(String ajId) {
		this.ajId = ajId;
	}
	/**
	 * 
	 * @return 档案id
	 */
	public String getDaId() {
		return daId;
	}
	/**
	 * 
	 * @param daId 档案id
	 */
	public void setDaId(String daId) {
		this.daId = daId;
	}
	/**
	 * 
	 * @return 案件名称
	 */
	public String getAjName() {
		return ajName;
	}
	/**
	 * 
	 * @param ajName 案件名称
	 */
	public void setAjName(String ajName) {
		this.ajName = ajName;
	}
	/**
	 * 
	 * @return 案件业务类型,eg:公诉、侦监
	 */
	public String getAjYwlx() {
		return ajYwlx;
	}
	/**
	 * 
	 * @param ajYwlx 案件业务类型,eg:公诉、侦监
	 */
	public void setAjYwlx(String ajYwlx) {
		this.ajYwlx = ajYwlx;
	}
	/**
	 * 
	 * @return 案件受理日期
	 */
	public Date getAjSlrq() {
		return ajSlrq;
	}
	/**
	 * 
	 * @param ajSlrq 案件受理日期
	 */
	public void setAjSlrq(Date ajSlrq) {
		this.ajSlrq = ajSlrq;
	}
	/**
	 * 
	 * @return 案件完结日期
	 */
	public Date getAjWjrq() {
		return ajWjrq;
	}
	/**
	 * 
	 * @param ajWjrq 案件完结日期
	 */
	public void setAjWjrq(Date ajWjrq) {
		this.ajWjrq = ajWjrq;
	}
	/**
	 * 
	 * @return 案件类别
	 */
	public String getAjlb() {
		return ajlb;
	}
	/**
	 * 
	 * @param ajlb 案件类别
	 */
	public void setAjlb(String ajlb) {
		this.ajlb = ajlb;
	}
	/**
	 * 
	 * @return 案件数量
	 */
	public Integer getAjsl() {
		return ajsl;
	}
	/**
	 * 
	 * @param ajsl 案件数量
	 */
	public void setAjsl(Integer ajsl) {
		this.ajsl = ajsl;
	}
	/**
	 * 
	 * @return 备注
	 */
	public String getBz() {
		return bz;
	}
	/**
	 * 
	 * @param bz 备注
	 */
	public void setBz(String bz) {
		this.bz = bz;
	}
	/**
	 * 
	 * @return 案件类型，eg：主办、参办（从办）
	 */
	public String getAjlx() {
		return ajlx;
	}
	/**
	 * 
	 * @param ajlx 案件类型，eg：主办、参办（从办）
	 */
	public void setAjlx(String ajlx) {
		this.ajlx = ajlx;
	}
	/**
	 * 
	 * @return 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 
	 * @param createTime 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 
	 * @return 修改时间
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	/**
	 * 
	 * @param modifyTime 修改时间
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	@Override
	public String toString() {
		return "SfdaAjxx [ajId=" + ajId + ", daId=" + daId + ", ajName=" 
				+ ajName + ", ajYwlx=" + ajYwlx + ", ajSlrq="
				+ ajSlrq + ", ajWjrq=" + ajWjrq + ", ajlb=" + ajlb 
				+ ", ajsl=" + ajsl + ", bz=" + bz + ", ajlx=" + ajlx
				+ ", createTime=" + createTime + ", modifyTime=" + modifyTime + "]";
	}
	
	
	
}
