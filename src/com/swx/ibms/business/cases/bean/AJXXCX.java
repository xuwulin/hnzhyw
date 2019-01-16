package com.swx.ibms.business.cases.bean;
/**
*<p>Title:AJXXCX</p>
*<p>Description: 案件信息查询实体类</p>
*author 朱春雨
*date 2017年3月17日 上午11:08:42 <br/>
* 主要属性：部门受案号，统一受案号，承办单位编码，承办单位名称，承办人工号，承办人，司法档案案件办案质量的数量
*/
public class AJXXCX {
    /**
     * 部门受案号 
     */
    private String bmsah;
    
    /**
     * 统一受案号
     */
    private String tysah;
    
    /**
     * 承办单位编码
     */
    private String cbdwBm;
    
    /**
     * 承办单位名称
     */
    private String cbdwMc;
    
    /**
     * 承办人工号
     */
    private String cbrgh;
    
    /**
     * 承办人
     */
    private String cbr;
    
    /**
     * 司法档案案件办案质量的数量
     */
    private String ct;
    
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
	 * @return 承办单位编码
	 */
	public String getCbdwBm() {
		return cbdwBm;
	}
	
	/**
	 * 
	 * @param cbdwBm 传入承办单位编码
	 */
	public void setCbdwBm(String cbdwBm) {
		this.cbdwBm = cbdwBm;
	}
	
	/**
	 * 
	 * @return 承办单位名称
	 */
	public String getCbdwMc() {
		return cbdwMc;
	}
	
	/**
	 *  
	 * @param cbdwMc 传入承办单位名称
	 */
	public void setCbdwMc(String cbdwMc) {
		this.cbdwMc = cbdwMc;
	}
	
	/**
	 * 
	 * @return 承办人工号
	 */
	public String getCbrgh() {
		return cbrgh;
	}
	
	/**
	 * 
	 * @param cbrgh 传入承办人工号
	 */
	public void setCbrgh(String cbrgh) {
		this.cbrgh = cbrgh;
	}
	
	/**
	 * 
	 * @return 承办人 
	 */
	public String getCbr() {
		return cbr;
	}
	
	/**
	 * 
	 * @param cbr 传入承办人
	 */
	public void setCbr(String cbr) {
		this.cbr = cbr;
	}
	
	/**
	 * 
	 * @return 司法档案案件办案质量的数量
	 */
	public String getCt() {
		return ct;
	}
	
	/**
	 * 
	 * @param ct 传入司法档案案件办案质量的数量
	 */
	public void setCt(String ct) {
		this.ct = ct;
	}
    
}
