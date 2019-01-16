package com.swx.ibms.business.archives.bean;

import java.util.Date;

public class SfdaGrjlInputExcel {
	/**
	 * 单位编码
	 */
	private String dwbm;
	/**
	 * 工号
	 */
	private String gh;
	/**
	 * 工作开始日期
	 */
	private String gzkssj;
	/**
	 * 工作结束日期
	 */
	private String gzjssj;
	/**
	 * 工作部门
	 */
	private String gzbm;
	/**
	 * 工作地址
	 */
	private String gzdd;
	/**
	 * 工作职责
	 */
	private String gwzz;
	/**
	 * 教育开始日期
	 */
	private String jykssj;
	/**
	 * 教育结束日期
	 */
	private String jyjssj;
	/**
	 * 学校名称
	 */
	private String xxmc;
	/**
	 * 学校地址
	 */
	private String xxdz;
	/**
	 * 担任职责
	 */
	private String drzz;
	
	private String id;
	
	/**
	 * 创建时间
	 */
	private Date cjsj;
	
	/**
	 * 修改时间
	 */
	private Date xgsj;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 排序序号
	 */
	private Integer xh;

	public String getDwbm() {
		return dwbm;
	}

	public void setDwbm(String dwbm) {
		this.dwbm = dwbm;
	}

	public String getGh() {
		return gh;
	}

	public void setGh(String gh) {
		this.gh = gh;
	}

	public String getGzkssj() {
		return gzkssj;
	}

	public void setGzkssj(String gzkssj) {
		this.gzkssj = gzkssj;
	}

	public String getGzjssj() {
		return gzjssj;
	}

	public void setGzjssj(String gzjssj) {
		this.gzjssj = gzjssj;
	}

	public String getGzbm() {
		return gzbm;
	}

	public void setGzbm(String gzbm) {
		this.gzbm = gzbm;
	}

	public String getGzdd() {
		return gzdd;
	}

	public void setGzdd(String gzdd) {
		this.gzdd = gzdd;
	}

	public String getGwzz() {
		return gwzz;
	}

	public void setGwzz(String gwzz) {
		this.gwzz = gwzz;
	}

	public String getJykssj() {
		return jykssj;
	}

	public void setJykssj(String jykssj) {
		this.jykssj = jykssj;
	}

	public String getJyjssj() {
		return jyjssj;
	}

	public void setJyjssj(String jyjssj) {
		this.jyjssj = jyjssj;
	}

	public String getXxmc() {
		return xxmc;
	}

	public void setXxmc(String xxmc) {
		this.xxmc = xxmc;
	}

	public String getXxdz() {
		return xxdz;
	}

	public void setXxdz(String xxdz) {
		this.xxdz = xxdz;
	}

	public String getDrzz() {
		return drzz;
	}

	public void setDrzz(String drzz) {
		this.drzz = drzz;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCjsj() {
		return cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

	public Date getXgsj() {
		return xgsj;
	}

	public void setXgsj(Date xgsj) {
		this.xgsj = xgsj;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getXh() {
		return xh;
	}

	public void setXh(Integer xh) {
		this.xh = xh;
	}

	@Override
	public String toString() {
		return "SfdaGrjlInputExcel [dwbm=" + dwbm + ", gh=" + gh + ", gzkssj=" + gzkssj + ", gzjssj=" + gzjssj
				+ ", gzbm=" + gzbm + ", gzdd=" + gzdd + ", gwzz=" + gwzz + ", jykssj=" + jykssj + ", jyjssj=" + jyjssj
				+ ", xxmc=" + xxmc + ", xxdz=" + xxdz + ", drzz=" + drzz + ", id=" + id + ", cjsj=" + cjsj + ", xgsj="
				+ xgsj + ", status=" + status + ", xh=" + xh + "]";
	}
	
}
