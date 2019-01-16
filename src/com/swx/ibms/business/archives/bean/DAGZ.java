package com.swx.ibms.business.archives.bean;

import java.util.Date;

/**
 * 【司法档案/档案的实体类】
 * @author ZCY
 * 
 */
public class DAGZ {
	
	private String id;
	private String ssrdwbm;
	private String ssr;
	private String cjrdwbm;
	private String cjr;
	private String tjnf;
	private String kssj;
	private String jssj;
	private String sfgs;
	private String gsxx;
	private String sffc;
	private Date fcsj;
	private String fcly;
	private String cjsj;
	private String sfsc;
	private String sfgd;

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSsrdwbm() {
		return ssrdwbm;
	}
	public void setSsrdwbm(String ssrdwbm) {
		this.ssrdwbm = ssrdwbm;
	}
	public String getSsr() {
		return ssr;
	}
	public void setSsr(String ssr) {
		this.ssr = ssr;
	}
	public String getCjrdwbm() {
		return cjrdwbm;
	}
	public void setCjrdwbm(String cjrdwbm) {
		this.cjrdwbm = cjrdwbm;
	}
	public String getCjr() {
		return cjr;
	}
	public void setCjr(String cjr) {
		this.cjr = cjr;
	}
	public String getTjnf() {
		return tjnf;
	}
	public void setTjnf(String tjnf) {
		this.tjnf = tjnf;
	}
	public String getKssj() {
		return kssj;
	}
	public void setKssj(String kssj) {
		this.kssj = kssj;
	}
	public String getJssj() {
		return jssj;
	}
	public void setJssj(String jssj) {
		this.jssj = jssj;
	}
	public String getSfgs() {
		return sfgs;
	}
	public void setSfgs(String sfgs) {
		this.sfgs = sfgs;
	}
	public String getGsxx() {
		return gsxx;
	}
	public void setGsxx(String gsxx) {
		this.gsxx = gsxx;
	}
	public String getSffc() {
		return sffc;
	}
	public void setSffc(String sffc) {
		this.sffc = sffc;
	}
	public Date getFcsj() {
		return fcsj;
	}
	public void setFcsj(Date fcsj) {
		this.fcsj = fcsj;
	}
	public String getFcly() {
		return fcly;
	}
	public void setFcly(String fcly) {
		this.fcly = fcly;
	}
	public String getCjsj() {
		return cjsj;
	}
	public void setCjsj(String chsj) {
		this.cjsj = chsj;
	}

	public void setSfsc(String sfsc) {
		this.sfsc = sfsc;
	}

	public void setSfgd(String sfgd) {
		this.sfgd = sfgd;
	}

	public String getSfsc() {
		return sfsc;
	}

	public String getSfgd() {
		return sfgd;
	}

	@Override
	public String toString() {
		return "DAGZ{" +
				"id='" + id + '\'' +
				", ssrdwbm='" + ssrdwbm + '\'' +
				", ssr='" + ssr + '\'' +
				", cjrdwbm='" + cjrdwbm + '\'' +
				", cjr='" + cjr + '\'' +
				", tjnf='" + tjnf + '\'' +
				", kssj='" + kssj + '\'' +
				", jssj='" + jssj + '\'' +
				", sfgs='" + sfgs + '\'' +
				", gsxx='" + gsxx + '\'' +
				", sffc='" + sffc + '\'' +
				", fcsj=" + fcsj +
				", fcly='" + fcly + '\'' +
				", cjsj='" + cjsj + '\'' +
				", sfsc='" + sfsc + '\'' +
				", sfgd='" + sfgd + '\'' +
				'}';
	}
}
