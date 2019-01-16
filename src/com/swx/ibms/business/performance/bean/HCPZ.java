/**
 * 
 */
package com.swx.ibms.business.performance.bean;

/**
 * 
 * HCPZ.java  核查配置实体类,交叉审批
 * @author 封泽超
 * @date<p>2017年4月10日</p>
 * @version 1.0
 * @description<p></p>
 */
public class HCPZ {
	/**
	 * 核查配置ID
	 */
	private String id;
	/**
	 * 核查单位编码
	 */
	private String hcdwbm;
	/**
	 * 核查部门编码
	 */
	private String hcbmbm;
	/**
	 * 被核查单位编码
	 */
	private String bhcdwbm;
	/**
	 * 被核查部门编码
	 */
	private String bhcbmbm;
	/**
	 * 业务类型
	 */
	private String ywlx;
	/**
	 * 业务名称
	 */
	private String ywmc;
	/**
	 * 开始时间
	 */
	private String kssj;
	/**
	 * 结束时间
	 */
	private String jssj;
	/**
	 * 部门类别编码
	 */
	private String bmlbbm;
	/**
	 * 被核查单位名称
	 */
	private String bhcdwmc;
	/**
	 * 核查单位名称
	 */
	private String hcdwmc;
	/**
	 * 旧开始时间
	 */
	private String oldkssj;
	
	/**
	 * 
	 * @return 返回旧开始时间
	 */
	public String getOldkssj() {
		return oldkssj;
	}
	/**
	 * 
	 * @param oldkssj 传入旧开始时间
	 */
	public void setOldkssj(String oldkssj) {
		this.oldkssj = oldkssj;
	}
	/**
	 * 
	 * @return 返回被核查单位名称
	 */
	public String getBhcdwmc() {
		return bhcdwmc;
	}
	/**
	 * 
	 * @param bhcdwmc 传入被核查单位名称
	 */
	public void setBhcdwmc(String bhcdwmc) {
		this.bhcdwmc = bhcdwmc;
	}
	/**
	 * 
	 * @return 返回核查单位名称
	 */
	public String getHcdwmc() {
		return hcdwmc;
	}
	/**
	 * 
	 * @param hcdwmc 传入核查单位名称
	 */
	public void setHcdwmc(String hcdwmc) {
		this.hcdwmc = hcdwmc;
	}
	/**
	 * 
	 * @return 返回业务名称
	 */
	public String getYwmc() {
		return ywmc;
	}
	/**
	 * 
	 * @param ywmc 传入业务名称
	 */
	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}
	/**
	 * 
	 * @return 返回部门类别编码
	 */
	public String getBmlbbm() {
		return bmlbbm;
	}
	/**
	 * 
	 * @param bmlbbm 传入部门类别编码
	 */
	public void setBmlbbm(String bmlbbm) {
		this.bmlbbm = bmlbbm;
	}
	/**
	 * 
	 * @return 返回核查配置ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 
	 * @param id 传入核查配置ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 
	 * @return 返回核查单位编码
	 */
	public String getHcdwbm() {
		return hcdwbm;
	}
	/**
	 * 
	 * @param hcdwbm 传入核查单位编码
	 */
	public void setHcdwbm(String hcdwbm) {
		this.hcdwbm = hcdwbm;
	}
	/**
	 * 
	 * @return 返回核查部门编码
	 */
	public String getHcbmbm() {
		return hcbmbm;
	}
	/**
	 * 
	 * @param hcbmbm 传入核查部门编码
	 */
	public void setHcbmbm(String hcbmbm) {
		this.hcbmbm = hcbmbm;
	}
	/**
	 * 
	 * @return 返回被核查单位编码
	 */
	public String getBhcdwbm() {
		return bhcdwbm;
	}
	/**
	 * 
	 * @param bhcdwbm 传入被核查单位编码
	 */
	public void setBhcdwbm(String bhcdwbm) {
		this.bhcdwbm = bhcdwbm;
	}
	/**
	 * 
	 * @return 被核查部门编码
	 */
	public String getBhcbmbm() {
		return bhcbmbm;
	}
	/**
	 * 
	 * @param bhcbmbm 传入被核查部门编码
	 */
	public void setBhcbmbm(String bhcbmbm) {
		this.bhcbmbm = bhcbmbm;
	}
	/**
	 * 
	 * @return 返回业务类型
	 */
	public String getYwlx() {
		return ywlx;
	}
	/**
	 * 
	 * @param ywlx 传入业务类型
	 */
	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}
	/**
	 *  
	 * @return 返回开始时间
	 */
	public String getKssj() {
		return kssj;
	}
	/**
	 * 
	 * @param kssj 传入开始时间
	 */
	public void setKssj(String kssj) {
		this.kssj = kssj;
	}
	/**
	 * 
	 * @return 返回结束时间
	 */
	public String getJssj() {
		return jssj;
	}
	/**
	 * 
	 * @param jssj 传入结束时间
	 */
	public void setJssj(String jssj) {
		this.jssj = jssj;
	}
	
	
	
	
}
