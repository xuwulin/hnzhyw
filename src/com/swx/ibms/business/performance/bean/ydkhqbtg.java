package com.swx.ibms.business.performance.bean;

import java.io.Serializable;

/**
 *<p>Title:ydkhqbtg</p>
 *<p>Description: 个人绩效考核详情实体类</p>
 * 【还有一个或者多个类都是类似的作用，因其他人分工合作未沟通的原因，现用此类作为主要使用的类，其他的不做改动。】
 *author 朱春雨
 *date 2017年4月23日 下午5:20:43
 */
public class ydkhqbtg implements Serializable{

	/**
	 * 个人绩效详情id
	 */
	private String id;
	/**
	 * 月度考核id
	 */
	private String ydkhid;
	/**
	 * 业务类型【现在存入部门编码，因为之前是指增对业务部门考核，现变更为所有的部门】
	 */
	private String ywlx;

	/**
	 * 个人绩效得分总分【评价分总分】
	 */
	private double ywzf;

	/**
	 * 指标考评概览
	 */
	private String zbkpgl;

	/**
	 * 指标考评得分
	 */
	private String zbkpdf;

	/**
	 * 自评人工号
	 */
	private String zprgh;

	/**
	 * 自评人姓名
	 */
	private String zpr;

	/**
	 * 检察官评分人姓名
	 */
	private String jcgpfr;

	/**
	 * 检察官评分人工号
	 */
	private String jcgpfrgh;

	/**
	 * 分管院领导人姓名
	 */
	private String fgyldpfr;

	/**
	 * 分管院领导人工号
	 */
	private String fgyldpfrgh;

	/**
	 * 部门评分人工号
	 */
	private String bmpfrgh;

	/**
	 * 部门评分人姓名
	 */
	private String bmpfr;

	/**
	 * 人事部评分人工号
	 */
	private String rsbpfrgh;

	/**
	 * 人事部评分人
	 */
	private String rsbpfr;

	/**
	 * 指标考评表头
	 */
	private String zbkpbt;

	/**
	 * 审批是否通过【1：通过，2：未通过】
	 */
	private String spsftg;

	/**
	 * 交叉评分人工号
	 */
	private String jcpfrgh;

	/**
	 * 交叉评分人（姓名）
	 */
	private String jcpfr;

	/**
	 * 总评价得分
	 */
	private double zpjdf;

	/**
	 * 评定级别名称
	 */
	private String pdjbmc;

	/**
	 * 评定级别
	 */
	private String pdjb;

	/**
	 * 所属部门
	 */
	private String bmbm;

	/**
	 * 单位级别
	 */
	private String dwjb;

	/**
	 * 年份
	 */
	private int year;

	/**
	 * 季度
	 */
	private int jd;

	/**
	 * 人员类型
	 */
	private String rylx;

	/**
	 * 人员角色
	 */
	private String ryjs;

	/**
	 * 审批时的当前处理角色，eg:SP、JCG、YLD、RSB
	 */
	private String cljs;

	public String getCljs() {
		return cljs;
	}

	public void setCljs(String cljs) {
		this.cljs = cljs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getYdkhid() {
		return ydkhid;
	}

	public void setYdkhid(String ydkhid) {
		this.ydkhid = ydkhid;
	}

	public String getYwlx() {
		return ywlx;
	}

	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}

	public double getYwzf() {
		return ywzf;
	}

	public void setYwzf(double ywzf) {
		this.ywzf = ywzf;
	}

	public String getZbkpgl() {
		return zbkpgl;
	}

	public void setZbkpgl(String zbkpgl) {
		this.zbkpgl = zbkpgl;
	}

	public String getZbkpdf() {
		return zbkpdf;
	}

	public void setZbkpdf(String zbkpdf) {
		this.zbkpdf = zbkpdf;
	}

	public String getZprgh() {
		return zprgh;
	}

	public void setZprgh(String zprgh) {
		this.zprgh = zprgh;
	}

	public String getZpr() {
		return zpr;
	}

	public void setZpr(String zpr) {
		this.zpr = zpr;
	}

	public String getJcgpfr() {
		return jcgpfr;
	}

	public void setJcgpfr(String jcgpfr) {
		this.jcgpfr = jcgpfr;
	}

	public String getJcgpfrgh() {
		return jcgpfrgh;
	}

	public void setJcgpfrgh(String jcgpfrgh) {
		this.jcgpfrgh = jcgpfrgh;
	}

	public String getFgyldpfr() {
		return fgyldpfr;
	}

	public void setFgyldpfr(String fgyldpfr) {
		this.fgyldpfr = fgyldpfr;
	}

	public String getFgyldpfrgh() {
		return fgyldpfrgh;
	}

	public void setFgyldpfrgh(String fgyldpfrgh) {
		this.fgyldpfrgh = fgyldpfrgh;
	}

	public String getBmpfrgh() {
		return bmpfrgh;
	}

	public void setBmpfrgh(String bmpfrgh) {
		this.bmpfrgh = bmpfrgh;
	}

	public String getBmpfr() {
		return bmpfr;
	}

	public void setBmpfr(String bmpfr) {
		this.bmpfr = bmpfr;
	}

	public String getRsbpfrgh() {
		return rsbpfrgh;
	}

	public void setRsbpfrgh(String rsbpfrgh) {
		this.rsbpfrgh = rsbpfrgh;
	}

	public String getRsbpfr() {
		return rsbpfr;
	}

	public void setRsbpfr(String rsbpfr) {
		this.rsbpfr = rsbpfr;
	}

	public String getZbkpbt() {
		return zbkpbt;
	}

	public void setZbkpbt(String zbkpbt) {
		this.zbkpbt = zbkpbt;
	}

	public String getSpsftg() {
		return spsftg;
	}

	public void setSpsftg(String spsftg) {
		this.spsftg = spsftg;
	}

	public String getJcpfrgh() {
		return jcpfrgh;
	}

	public void setJcpfrgh(String jcpfrgh) {
		this.jcpfrgh = jcpfrgh;
	}

	public String getJcpfr() {
		return jcpfr;
	}

	public void setJcpfr(String jcpfr) {
		this.jcpfr = jcpfr;
	}

	public double getZpjdf() {
		return zpjdf;
	}

	public void setZpjdf(double zpjdf) {
		this.zpjdf = zpjdf;
	}

	public String getPdjbmc() {
		return pdjbmc;
	}

	public void setPdjbmc(String pdjbmc) {
		this.pdjbmc = pdjbmc;
	}

	public String getPdjb() {
		return pdjb;
	}

	public void setPdjb(String pdjb) {
		this.pdjb = pdjb;
	}

	public String getBmbm() {
		return bmbm;
	}

	public void setBmbm(String bmbm) {
		this.bmbm = bmbm;
	}

	public String getDwjb() {
		return dwjb;
	}

	public void setDwjb(String dwjb) {
		this.dwjb = dwjb;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getJd() {
		return jd;
	}

	public void setJd(int jd) {
		this.jd = jd;
	}

	public String getRylx() {
		return rylx;
	}

	public void setRylx(String rylx) {
		this.rylx = rylx;
	}

	public String getRyjs() {
		return ryjs;
	}

	public void setRyjs(String ryjs) {
		this.ryjs = ryjs;
	}

	@Override
	public String toString() {
		return "ydkhqbtg{" +
				"id='" + id + '\'' +
				", ydkhid='" + ydkhid + '\'' +
				", ywlx='" + ywlx + '\'' +
				", ywzf=" + ywzf +
				", zbkpgl='" + zbkpgl + '\'' +
				", zbkpdf='" + zbkpdf + '\'' +
				", zprgh='" + zprgh + '\'' +
				", zpr='" + zpr + '\'' +
				", jcgpfr='" + jcgpfr + '\'' +
				", jcgpfrgh='" + jcgpfrgh + '\'' +
				", fgyldpfr='" + fgyldpfr + '\'' +
				", fgyldpfrgh='" + fgyldpfrgh + '\'' +
				", bmpfrgh='" + bmpfrgh + '\'' +
				", bmpfr='" + bmpfr + '\'' +
				", rsbpfrgh='" + rsbpfrgh + '\'' +
				", rsbpfr='" + rsbpfr + '\'' +
				", zbkpbt='" + zbkpbt + '\'' +
				", spsftg='" + spsftg + '\'' +
				", jcpfrgh='" + jcpfrgh + '\'' +
				", jcpfr='" + jcpfr + '\'' +
				", zpjdf=" + zpjdf +
				", pdjbmc='" + pdjbmc + '\'' +
				", pdjb='" + pdjb + '\'' +
				", bmbm='" + bmbm + '\'' +
				", dwjb='" + dwjb + '\'' +
				", year=" + year +
				", jd=" + jd +
				", rylx='" + rylx + '\'' +
				", ryjs='" + ryjs + '\'' +
				", cljs='" + cljs + '\'' +
				'}';
	}
}
