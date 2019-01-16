/**
 * 
 */
package com.swx.ibms.business.cases.bean;

import java.sql.Date;

/**
 * 司法档案案件实体类
 * @author 封泽超
 *@since 2017年2月28日
 */


public class SFDAAJ {
	/**
	 * 承办人单位编码
	 */
	private String dwbm;	//承办人单位编码
	/**
	 * 承办人工号
	 */
	private String cbrgh;	//承办人工号 --
	/**
	 * 统一受案号
	 */
	private String tysah;	//统一受案号
	/**
	 * 案件名称
	 */
	private String ajmc; 	//案件名称
	/**
	 * 业务类型
	 */
	private String ywlx; 	//业务类型 --
	/**
	 * 办案天数
	 */
	private String baxl; 	//办案效率(办理天数) --
	/**
	 * 案件类别
	 */
	private String ajlb; 	//案件类别
	/**
	 * 承办人
	 */
	private String cbr;		//承办人
	/**
	 * 受理日期
	 */
	private String slrq;	//受理日期
	/**
	 * 完成日期
	 */
	private String wcrq;	//完成日期 
	/**
	 * 承办单位
	 */
	private String cbdw;	//承办单位
	/**
	 * 承办部门 
	 */
	private String cbbm;	//承办部门
	/**
	 * 案件状态
	 */
	private String ajzt;	//案件状态
	/**
	 * 部门受案号
	 */
	private String bmsah;	//部门受案号
	/**
	 * 案情摘要
	 */
	private String aqzy;	//案情摘要
	
	/**
	 * 得到承办人工号
	 * @return String 
	 */
	public String getCbrgh() {
		return cbrgh;
	}
	/**
	 * 设置承办人工号	
	 * @param cbrgh 
	 */
	public void setCbrgh(String cbrgh) {
		this.cbrgh = cbrgh;
	}
	/**
	 * 得到统一受案号
	 * @return String 
	 */
	public String getTysah() {
		return tysah;
	}
	/**
	 * 设置统一受案号
	 * @param tysah 
	 */
	public void setTysah(String tysah) {
		this.tysah = tysah;
	}
	/**
	 * 得到案件名称 
	 * @return String 
	 */
	public String getAjmc() {
		return ajmc;
	}
	/**
	 * 设置案件名称
	 * @param ajmc 
	 */
	public void setAjmc(String ajmc) {
		this.ajmc = ajmc;
	}
	/**
	 * 得到业务类型 
	 * @return String 
	 */
	public String getYwlx() {
		return ywlx;
	}
	/**
	 * 设置业务类型
	 * @param ywlx 
	 */
	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}
	/**
	 * 得到办案天数 
	 * @return String
	 */
	public String getBaxl() {
		return baxl;
	}
	/**
	 * 设置办案天数 
	 * @param baxl 
	 */
	public void setBaxl(String baxl) {
		this.baxl = baxl;
	}
	/**
	 * 得到案件类别
	 * @return  String 
	 */
	public String getAjlb() {
		return ajlb;
	}
	/**
	 * 设置案件类别
	 * @param ajlb 
	 */
	public void setAjlb(String ajlb) {
		this.ajlb = ajlb;
	}
	/**
	 * 得到承办人
	 * @return String 
	 */
	public String getCbr() {
		return cbr;
	}
	/**
	 * 设置承办人
	 * @param cbr 
	 */
	public void setCbr(String cbr) {
		this.cbr = cbr;
	}
	/**
	 * 得到承办人单位 
	 * @return String 
	 */
	public String getCbdw() {
		return cbdw;
	}
	/**
	 * 设置承办人单位
	 * @param cbdw 
	 */
	public void setCbdw(String cbdw) {
		this.cbdw = cbdw;
	}
	/**
	 * 得到承办部门
	 * @return String 
	 */
	public String getCbbm() {
		return cbbm;
	}
	/**
	 * 设置承办部门 
	 * @param cbbm 
	 */
	public void setCbbm(String cbbm) {
		this.cbbm = cbbm;
	}
	/**
	 * 得到案件状态
	 * @return String 
	 */
	public String getAjzt() {
		return ajzt;
	}
	/**
	 * 设置案件状态  
	 * @param ajzt 
	 */
	public void setAjzt(String ajzt) {
		this.ajzt = ajzt;
	}
	/**
	 * 得到部门受案号
	 * @return String 
	 */
	public String getBmsah() {
		return bmsah;
	}
	/**
	 * 设置部门受案号 
	 * @param bmsah 
	 */
	public void setBmsah(String bmsah) {
		this.bmsah = bmsah;
	}
	/**
	 * 得到受理日期
	 * @return String 
	 */
	public String getSlrq() {
		return slrq;
	}
	/**
	 * 设置受理日期
	 * @param slrq 
	 */ 
	public void setSlrq(Date slrq) {
		this.slrq = slrq.toString();
	}
	/**
	 * 得到案情摘要
	 * @return String 
	 */
	public String getAqzy() {
		return aqzy;
	}
	/**
	 * 设置案情摘要
	 * @param aqzy 
	 */
	public void setAqzy(String aqzy) {
		this.aqzy = aqzy;
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
	 * 得到完成日期
	 * @return String
	 */
	public String getWcrq() {
		return wcrq;
	}
	/**
	 * 设置完成日期
	 * @param wcrq 
	 */
	public void setWcrq(Date wcrq) {
		this.wcrq = wcrq.toString();
	}
	
	
	
}