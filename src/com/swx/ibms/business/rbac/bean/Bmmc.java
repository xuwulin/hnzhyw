package com.swx.ibms.business.rbac.bean;
/**
 * 
 * @author 王宇锋 <br/>
 * 主要属性：
 * 部门编码,部门名称,部门映射,单位编码,父部门编码,部门简称,部门文号简称,部门案号简称,
 * 是否临时机构,是否承办部门,部门序号,备注,是否删除,类别（标识数据来源于统一业务还是综合业务）
 *
 */
@SuppressWarnings("all")
public class Bmmc {
	
	/**
	 *  部门编码
	 */
	private String bmbm;
	
	/**
	 * 部门名称
	 */
	private String bmmc;
	
	/**
	 * 部门映射
	 */
	private String bmys;
	
	/**
	 * 单位编码
	 */
	private String dwbm;
	
	/**
	 * 父部门编码
	 */
	private String fbmbm;
	
	/**
	 * 部门简称
	 */
	private String bmjc;
	
	/**
	 * 部门文号简称
	 */
	private String bmwhjc;
	
	/**
	 * 部门案号简称
	 */
	private String bmahjc;
	
	/**
	 * 是否临时机构
	 */
	private String sflsjg;
	
	/**
	 * 是否承办部门
	 */
	private String sfcbbm;
	
	/**
	 * 部门序号
	 */
	private String bmxh;
	
	/**
	 * 备注 
	 */
	private String bz;
	
	/**
	 * 是否删除 
	 */
	private String sfsc;
	
	/**
	 * 类别，标识数据来源于统一业务还是综合业务
	 */
	private String lb;	
	
	/**
	 * 
	 * @return 类别
	 */
	public String getLb() {
		return lb;
	}
	
	/**
	 * 
	 * @param lb 传入类别
	 */
	public void setLb(String lb) {
		this.lb = lb;
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
	 * @param dwbm 传入单位编码
	 */
	public void setDwbm(String dwbm) {
		this.dwbm = dwbm;
	}
	
	/**
	 * 
	 * @return 父部门编码 
 	 */
	public String getFbmbm() {
		return fbmbm;
	}
	
	/**
	 * 
	 * @param fbmbm 传入父部门编码 
	 */
	public void setFbmbm(String fbmbm) {
		this.fbmbm = fbmbm;
	}
	
	/**
	 * 
	 * @return 部门简称 
	 */
	public String getBmjc() {
		return bmjc;
	}
	
	/**
	 * 
	 * @param bmjc 传入部门简称
	 */
	public void setBmjc(String bmjc) {
		this.bmjc = bmjc;
	}
	
	/**
	 * 
	 * @return 部门文号简称
	 */
	public String getBmwhjc() {
		return bmwhjc;
	}
	
	/**
	 * 
	 * @param bmwhjc 传入部门文号简称
	 */
	public void setBmwhjc(String bmwhjc) {
		this.bmwhjc = bmwhjc;
	}
	
	/**
	 * 
	 * @return 部门案号简称
	 */
	public String getBmahjc() {
		return bmahjc;
	}
	
	/**
	 * 
	 * @param bmahjc 传入部门案号简称 
	 */
	public void setBmahjc(String bmahjc) {
		this.bmahjc = bmahjc;
	}
	
	/**
	 * 
	 * @return 是否临时机构
	 */
	public String getSflsjg() {
		return sflsjg;
	}
	
	/**
	 * 
	 * @param sflsjg 传入是否临时机构
	 */
	public void setSflsjg(String sflsjg) {
		this.sflsjg = sflsjg;
	}
	
	/**
	 * 
	 * @return 是否承办部门 
	 */
	public String getSfcbbm() {
		return sfcbbm;
	}
	
	/**
	 * 
	 * @param sfcbbm  传入是否承办部门 
	 */
	public void setSfcbbm(String sfcbbm) {
		this.sfcbbm = sfcbbm;
	}
	
	/**
	 * 
	 * @return 部门序号
	 */
	public String getBmxh() {
		return bmxh;
	}
	
	/**
	 * 
	 * @param bmxh 传入部门序号 
	 */
	public void setBmxh(String bmxh) {
		this.bmxh = bmxh;
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
	 * @param bz 传入备注 
	 */
	public void setBz(String bz) {
		this.bz = bz;
	}
	
	/**
	 * 
	 * @return 是否删除 
	 */
	public String getSfsc() {
		return sfsc;
	}
	
	/**
	 * 
	 * @param sfsc 传入是否删除 
	 */
	public void setSfsc(String sfsc) {
		this.sfsc = sfsc;
	}
	
	@Override
	public String toString() {
		return "Bmmc [bmbm=" + bmbm + ", bmmc=" + bmmc + ", bmys=" + bmys + "]";
	}

	/**
	 * 
	 * @return 部门映射
	 */
	public String getBmys() {
		return bmys;
	}
	
	/**
	 * 
	 * @param bmys 传入部门映射 
	 */
	public void setBmys(String bmys) {
		this.bmys = bmys;
	}
	
	/**
	 * 
	 * @return 部门编码 
	 */
	public String getBmbm() {
		return bmbm;
	}
	
	/**
	 * 
	 * @param bmbm 传入部门编码
	 */
	public void setBmbm(String bmbm) {
		this.bmbm = bmbm;
	}
	
	/**
	 * 
	 * @return 部门名称 
	 */
	public String getBmmc() {
		return bmmc;
	}
	
	/**
	 * 
	 * @param bmmc 传入部门名称 
	 */
	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bmbm == null) ? 0 : bmbm.hashCode());
		result = prime * result + ((bmmc == null) ? 0 : bmmc.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		Bmmc other = (Bmmc) obj;
		if (bmbm == null) {
			if (other.bmbm != null){
				return false;
			}
		} else if (!bmbm.equals(other.bmbm)){
			return false;
		}
		if (bmmc == null) {
			if (other.bmmc != null){
				return false;
			}
		} else if (!bmmc.equals(other.bmmc)){
			return false;
		}
		return true;
	}
}
