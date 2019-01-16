/**
 * 
 */
package com.swx.ibms.business.appraisal.bean;

import java.util.Date;

/**
 * 检察机关业务考核指标配置实体类
 * @author 封泽超
 *@since 2017年6月5日
 */
public class Ywgzzbpz {
	/**
	 * 编号
	 */
	private String id;
	/**
	 * 单位级别
	 */
	private String dwjb;
	/**
	 * 考核类型
	 */
	private String khlx;
	/**
	 * 考核类型名称
	 */
	private String khlxmc;
	/**
	 * 配置内容	
	 */
	private String pznr;
	/**
	 * 指标内容
	 */
	private String zbnr;
	/**
	 * 评价分
	 */
	private String pjf;
	/**
	 * 操作人
	 */
	private String czr;
	/**
	 * 操作时间
	 */
	private Date czsj;
	/**
	 * 配置表头id
	 */
	private String pzbtid;
	
	/**
	 * 版本
	 */
	private String version;
	
	/**
	 * 所属年份，业务考核考核指标的所属日期，暂时定的格式是“年”，后面如有更改则以更改的为准
	 */
	private String ssrq;
	
	/**
	 * 业务考核考核指标的状态，“0”：启用【默认】，“1”：禁用
	 */
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDwjb() {
		return dwjb;
	}

	public void setDwjb(String dwjb) {
		this.dwjb = dwjb;
	}

	public String getKhlx() {
		return khlx;
	}

	public void setKhlx(String khlx) {
		this.khlx = khlx;
	}

	public String getKhlxmc() {
		return khlxmc;
	}

	public void setKhlxmc(String khlxmc) {
		this.khlxmc = khlxmc;
	}

	public String getPznr() {
		return pznr;
	}

	public void setPznr(String pznr) {
		this.pznr = pznr;
	}

	public String getZbnr() {
		return zbnr;
	}

	public void setZbnr(String zbnr) {
		this.zbnr = zbnr;
	}

	public String getPjf() {
		return pjf;
	}

	public void setPjf(String pjf) {
		this.pjf = pjf;
	}

	public String getCzr() {
		return czr;
	}

	public void setCzr(String czr) {
		this.czr = czr;
	}

	public Date getCzsj() {
		return czsj;
	}

	public void setCzsj(Date czsj) {
		this.czsj = czsj;
	}

	public String getPzbtid() {
		return pzbtid;
	}

	public void setPzbtid(String pzbtid) {
		this.pzbtid = pzbtid;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSsrq() {
		return ssrq;
	}

	public void setSsrq(String ssrq) {
		this.ssrq = ssrq;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Ywgzzbpz [id=" + id + ", dwjb=" + dwjb + ", khlx=" + khlx + ", khlxmc=" + khlxmc + ", pznr=" + pznr
				+ ", zbnr=" + zbnr + ", pjf=" + pjf + ", czr=" + czr + ", czsj=" + czsj + ", pzbtid=" + pzbtid
				+ ", version=" + version + ", ssrq=" + ssrq + ", status=" + status + "]";
	}
	
}
