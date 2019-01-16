package com.swx.ibms.business.performance.bean;

import java.sql.Date;

/**
 * GrjxZbpz.java 个人绩效指标配置实体类
 * @author east
 * @date<p>2017年4月13日</p>
 * @version 1.0
 * @description<p>个人绩效指标配置实体类</p>
 *
 */
@SuppressWarnings("all")
public class GrjxZbpz {
	/**
	 * 指标配置ID
	 */
	private String zbpzId;
	/**
	 * 业务类别
	 */
	private String ywlb;
	/**
	 * 单位级别，1,：省院  2:市院  3：区院
	 */
	private int dwjb;
	/**
	 * 指标配置内容，对应个人绩效表体内容
	 */
	private String zbpzNr;	
	/**
	 * 操作人
	 */
	private String operator;
	/**
	 * 操作时间，默认可设置为当前系统时间
	 */
	private String operateTime;
	/**
	 * 配置状态：0，已作废     1，正在使用
	 */
	private int status;	
	/**
	 * 表头表的id
	 */
	private String tabletopid;	
	/**
	 * 配置指标内容
	 */
	private String pzzbpznr;		
	
	/**
	 * 考核指标的所属人员类型，1 检察官 2 检察辅助人员
	 */
	private String type;
	
	/**
	 * 个人绩效考核指标所属日期，格式是年
	 */
	private String ssrq;

	
	@Override
	public String toString() {
		return "GrjxZbpz [zbpzId=" + zbpzId + ", ywlb=" + ywlb + ", dwjb=" + dwjb + ", zbpzNr=" + zbpzNr + ", operator="
				+ operator + ", operateTime=" + operateTime + ", status=" + status + ", tabletopid=" + tabletopid
				+ ", pzzbpznr=" + pzzbpznr + ", type=" + type + ", ssrq=" + ssrq + "]";
	}
	
	/**
	 * 
	 * @return 返回配置指标内容
	 */
	public String getPzzbpznr() {
		return pzzbpznr;
	}
	/**
	 * 
	 * @param pzzbpznr 传入配置指标内容
	 */
	public void setPzzbpznr(String pzzbpznr) {
		this.pzzbpznr = pzzbpznr;
	}
	/**
	 * 
	 * @return 返回指标配置ID
	 */
	public String getZbpzId() {
		return zbpzId;
	}
	/**
	 * 
	 * @param zbpzId 传入指标配置ID
	 */
	public void setZbpzId(String zbpzId) {
		this.zbpzId = zbpzId;
	}
	/**
	 * 
	 * @return 返回业务类别
	 */
	public String getYwlb() {
		return ywlb;
	}
	/**
	 * 
	 * @param ywlb 传入业务类别
	 */
	public void setYwlb(String ywlb) {
		this.ywlb = ywlb;
	}
	/**
	 * 
	 * @return 返回单位级别
	 */
	public int getDwjb() {
		return dwjb;
	}
	/**
	 * 
	 * @param dwjb 传入单位级别
	 */
	public void setDwjb(int dwjb) {
		this.dwjb = dwjb;
	}
	/**
	 * 
	 * @return 返回指标配置内容
	 */
	public String getZbpzNr() {
		return zbpzNr;
	}
	/**
	 * 
	 * @param zbpzNr 传入指标配置内容
	 */
	public void setZbpzNr(String zbpzNr) {
		this.zbpzNr = zbpzNr;
	}
	/**
	 * 
	 * @return 返回操作人 
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * 
	 * @param operator 传入操作人
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	/**
	 * 
	 * @return 返回操作时间 
	 */
	public String getOperateTime() {
		return operateTime;
	}
	/**
	 * 
	 * @param operateTime 传入操作时间
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime.toString();
	}
	/**
	 * 
	 * @return 返回配置状态：0，已作废     1，正在使用 
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * 
	 * @param status 传入配置状态：0，已作废     1，正在使用
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * 
	 * @return 返回表头表的ID
	 */
	public String getTabletopid() {
		return tabletopid;
	}
	/**
	 * 
	 * @param tabletopid 传入表头表的ID
	 */
	public void setTabletopid(String tabletopid) {
		this.tabletopid = tabletopid;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSsrq() {
		return ssrq;
	}
	public void setSsrq(String ssrq) {
		this.ssrq = ssrq;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	
	
}
