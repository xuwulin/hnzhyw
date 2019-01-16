/**
 * 
 */
package com.swx.ibms.business.archives.bean;

import java.sql.Date;

/**
 * 档案归档完整实体类
 * @author 封泽超
 *@since 2017年3月3日
 */
public class DAGZGD {
	/**
	 * id
	 */
	private String id;
	/**
	 * 所属人
	 */
	private String ssr;
	/**
	 * 添加年份
	 */
	private String tjnf;
	/**
	 * 开始时间
	 */
	private String kssj;
	/**
	 * 结束时间
	 */
	private String jssj;
	/**
	 * 创建人
	 */
	private String cjr;
	/**
	 * 创建时间
	 */
	private String cjsj;
	/**
	 * 所属人单位编码
	 */
	private String ssrdwbm;
	/**
	 * 创建人单位编码
	 */
	private String cjrdwbm;
	/**
	 * 是否公示
	 */
	private String sfgs;
	
	/**
	 * 公示信息
	 */
	private String gsxx;
	
	/**
	 *是否封存
	 */
	private String sffc;
	/**
	 * 封存时间
	 */
	private String fcsj;
	/**
	 * 封存理由
	 */
	private String fcly;
	
	private String spzt;
	
	private String sfsc;
	
	private String sfgd; 
	
	public String getSfsc() {
		return sfsc;
	}
	public void setSfsc(String sfsc) {
		this.sfsc = sfsc;
	}
	public String getSfgd() {
		return sfgd;
	}
	public void setSfgd(String sfgd) {
		this.sfgd = sfgd;
	}
	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}
	public void setFcsj(String fcsj) {
		this.fcsj = fcsj;
	}
	public String getSpzt() {
		return spzt;
	}
	public void setSpzt(String spzt) {
		this.spzt = spzt;
	}
	/**
	 * @return 公示信息
	 */
	public String getGsxx() {
		return gsxx;
	}
	/**
	 * @param gsxx  公示信息
	 */
	public void setGsxx(String gsxx) {
		this.gsxx = gsxx;
	}
	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return 所属人
	 */
	public String getSsr() {
		return ssr;
	}
	/**
	 * @param ssr 所属人
	 */
	public void setSsr(String ssr) {
		this.ssr = ssr;
	}
	/**
	 * @return 添加年份
	 */
	public String getTjnf() {
		return tjnf;
	}
	/**
	 * @param tjnf 添加年份
	 */
	public void setTjnf(String tjnf) {
		this.tjnf = tjnf;
	}
	/**
	 * @return 开始时间
	 */
	public String getKssj() {
		return kssj;
	}
	/**
	 * @param kssj 开始时间
	 */
	public void setKssj(String kssj) {
		this.kssj = kssj;
	}
	/**
	 * @return 结束时间
	 */ 
	public String getJssj() {
		return jssj;
	}
	/**
	 * @param jssj 结束时间
	 */
	public void setJssj(String jssj) {
		this.jssj = jssj;
	}
	/**
	 * @return 创建人
	 */
	public String getCjr() {
		return cjr;
	}
	/**
	 * @param cjr 创建人
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
	 * @param cjsj 创建时间
	 */
	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj.toString();
	}
	/**
	 * @return 所属人单位编码
	 */
	public String getSsrdwbm() {
		return ssrdwbm;
	}
	/**
	 * @param ssrdwbm 所属人单位编码
	 */
	public void setSsrdwbm(String ssrdwbm) {
		this.ssrdwbm = ssrdwbm;
	}
	/** 
	 * @return 创建人单位编码
	 */
	public String getCjrdwbm() {
		return cjrdwbm;
	}
	/**
	 * @param cjrdwbm 创建人单位编码
	 */
	public void setCjrdwbm(String cjrdwbm) {
		this.cjrdwbm = cjrdwbm;
	}
	/**
	 * @return 是否公示
	 */
	public String getSfgs() {
		return sfgs;
	}
	/**
	 * @param sfgs 是否公示
	 */
	public void setSfgs(String sfgs) {
		this.sfgs = sfgs;
	}
	/**
	 * @return 是否封存get
	 */
	public String getSffc() {
		return sffc;
	}
	/**
	 * @param sffc 是否封存set
	 */
	public void setSffc(String sffc) {
		this.sffc = sffc;
	}
	/**
	 * @return 封存时间 get
	 */
	public String getFcsj() {
		return fcsj;
	}
	/**
	 * @param fcsj 封存时间set
	 */
	public void setFcsj(Date fcsj) {
		this.fcsj = fcsj.toString();
	}
	/**
	 * @return 封存理由get
	 */ 
	public String getFcly() {
		return fcly;
	}
	/**
	 * @param fcly 封存理由set
	 */
	public void setFcly(String fcly) {
		this.fcly = fcly;
	}	
	
}
