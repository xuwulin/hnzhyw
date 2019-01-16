package com.swx.ibms.business.appraisal.bean;

import java.util.Date;

/**
 * 
 * Ywgzlxkh.java 业务工作类型考核实体类
 * @author east
 * @date<p>2017年6月7日</p>
 * @version 1.0
 * @description<p></p>
 */
public class Ywgzlxkh {
	
	/**
	 * //年度考核id 
	 */
	private String id;
	/**
	 * //业务考核活动ID
	 */
	private String khId;
	/**
	 * //年份
	 */
	private String year;
	/**
	 * //单位编码
	 */
	private String dwbm;
	/**
	 * 分配人部门编码
	 */
	private String fprBmbm;
	/**
	 * 分配人工号 
	 */
	private String fprGh;
	/**
	 * 分配人名称
	 */
	private String fprMc;
	/**
	 * 考核人部门编码
	 */
	private String khrBmbm;
	/**
	 * 考核人工号
	 */
	private String khrGh;
	/**
	 * 考核人名称
	 */
	private String khrMc;
	/**
	 * 考核类型
	 */
	private String khlx;
	/**
	 * //状态  0 待指定考核人， 1考核中， 2待审批， 3 审批完成
	 */
	private String zt;
	/**
	 * //备注
	 */
	private String bz;
	/**
	 * //单位级别 1:最高人名检察院 2:省院 3:市院 4:区院
	 */
	private String dwjb;
	/**
	 * 创建时间
	 */
	private Date cjsj;
	/**
	 * 修改时间
	 */
	private Date xgsj;
	
	/**
	 * 
	 * @return 年度考核id 
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 
	 * @param id 年度考核id 
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return 业务考核活动ID
	 */
	public String getKhId() {
		return khId;
	}
	
	/**
	 * 
	 * @param khId 业务考核活动ID
	 */
	public void setKhId(String khId) {
		this.khId = khId;
	}
	
	/**
	 * 
	 * @return 年份
	 */
	public String getYear() {
		return year;
	}
	
	/**
	 * 
	 * @param year 年份
	 */
	public void setYear(String year) {
		this.year = year;
	}
	
	/**
	 * 
	 * @return 单位编码
	 */
	public String getDwbm() {
		return dwbm;
	}
	
	/**
	 * 
	 * @param dwbm 单位编码 
	 */
	public void setDwbm(String dwbm) {
		this.dwbm = dwbm;
	}
	
	/**
	 * 
	 * @return 分配人部门编码
	 */
	public String getFprBmbm() {
		return fprBmbm;
	}
	
	/**
	 * 
	 * @param fprBmbm 分配人部门编码
	 */
	public void setFprBmbm(String fprBmbm) {
		this.fprBmbm = fprBmbm;
	}
	
	/**
	 * 
	 * @return 分配人工号 
	 */
	public String getFprGh() {
		return fprGh;
	}
	
	/**
	 * 
	 * @param fprGh 分配人工号 
	 */
	public void setFprGh(String fprGh) {
		this.fprGh = fprGh;
	}
	
	/**
	 * 
	 * @return 分配人名称
	 */
	public String getFprMc() {
		return fprMc;
	}
	
	/**
	 * 
	 * @param fprMc 分配人名称
	 */
	public void setFprMc(String fprMc) {
		this.fprMc = fprMc;
	}
	
	/**
	 * 
	 * @return 考核人部门编码
	 */
	public String getKhrBmbm() {
		return khrBmbm;
	}
	
	/**
	 * 
	 * @param khrBmbm 考核人部门编码
	 */
	public void setKhrBmbm(String khrBmbm) {
		this.khrBmbm = khrBmbm;
	}
	
	/**
	 * 
	 * @return 考核人工号 
	 */
	public String getKhrGh() {
		return khrGh;
	}
	
	/**
	 * 
	 * @param khrGh 考核人工号 
	 */
	public void setKhrGh(String khrGh) {
		this.khrGh = khrGh;
	}
	
	/**
	 * 
	 * @return 考核人名称
	 */
	public String getKhrMc() {
		return khrMc;
	}
	
	/**
	 * 
	 * @param khrMc 考核人名称
	 */
	public void setKhrMc(String khrMc) {
		this.khrMc = khrMc;
	}
	
	/**
	 * 
	 * @return 考核类型
	 */
	public String getKhlx() {
		return khlx;
	}
	
	/**
	 * 
	 * @param khlx 考核类型
	 */
	public void setKhlx(String khlx) {
		this.khlx = khlx;
	}
	
	/**
	 * 
	 * @return 状态 0 待指定考核人， 1考核中， 2待审批， 3 审批完成
	 */
	public String getZt() {
		return zt;
	}
	
	/**
	 * 
	 * @param zt 状态 0 待指定考核人， 1考核中， 2待审批， 3 审批完成
	 */
	public void setZt(String zt) {
		this.zt = zt;
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
	 * @return 单位级别
	 */
	public String getDwjb() {
		return dwjb;
	}
	
	/**
	 * 
	 * @param dwjb 单位级别
	 */
	public void setDwjb(String dwjb) {
		this.dwjb = dwjb;
	}
	
	/**
	 * 
	 * @return 创建时间
	 */
	public Date getCjsj() {
		return cjsj;
	}
	
	/**
	 * 
	 * @param cjsj 创建时间
	 */
	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}
	
	/**
	 * 
	 * @return 修改时间
	 */
	public Date getXgsj() {
		return xgsj;
	}
	
	/**
	 * 
	 * @param xgsj 修改时间
	 */
	public void setXgsj(Date xgsj) {
		this.xgsj = xgsj;
	}
	
	@Override
	public String toString() {
		return "Ywgzlxkh [id=" + id + ", khId=" + khId + ", year=" + year + ", dwbm=" + dwbm
				+ ", fprBmbm=" + fprBmbm + ", fprGh=" + fprGh + ", fprMc=" + fprMc + ", khrBmbm=" + khrBmbm + ", khrGh="
				+ khrGh + ", khrMc=" + khrMc + ", khlx=" + khlx + ", zt=" + zt + ", bz=" + bz + ", dwjb=" + dwjb
				+ ", cjsj=" + cjsj + ", xgsj=" + xgsj + "]";
	}
	
}
