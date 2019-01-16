package com.swx.ibms.business.rbac.bean;

/**
 * 部门编码实体类
 * @author 李治鑫 <br/>
 * 主要属性：单位编码,部门编码,父部门编码,部门名称,部门简称,部门文号简称,<br/>
 * 部门案号简称,是否临时机构,是否承办部门,部门序号,备注,是否删除,部门映射(装的是业务类型编码),<br/>
 * 单位名称,单位简称,各个数据库查询出的数据类型，1：统一业务  2：综合业务
 */
public class BMBM {
	/**
	 * 单位编码
	 */
	private String dwbm;
	
	/**
	 * 部门编码
	 */
	private String bmbm;
	
	/**
	 * 父部门编码
	 */
	private String fbmbm;
	
	/**
	 * 部门名称
	 */
	private String bmmc;
	
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
	private int bmxh;
	
	/**
	 * 备注
	 */
	private String bz;
	
	/**
	 * 是否删除
	 */
	private String sfsc;
	
	/**
	 * 部门映射,装的是对应的业务类型的编码 
	 */
	private String bmys;
	
	/**
	 * 单位名称
	 */
	private String dwmc;
	
	/**
	 * 单位简称
	 */
	private String dwjc;
	
	/**
	 * 各个数据库查询出的数据类型，1：统一业务  2：综合业务
	 */
	private String type;
	
	
	/**
	 * 
	 * @return 各个数据库查询出的数据类型，1：统一业务  2：综合业务 
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @param type 传入各个数据库查询出的数据类型，1：统一业务  2：综合业务
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @return 单位编码
	 */
	public String getDwmc() {
		return dwmc;
	}

	/**
	 * 
	 * @param dwmc 单位编码
	 */
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}

	/**
	 * 
	 * @return 单位简称
	 */
	public String getDwjc() {
		return dwjc;
	}

	/**
	 * 
	 * @param dwjc 传入单位简称
	 */
	public void setDwjc(String dwjc) {
		this.dwjc = dwjc;
	}

	/**
	 * 无参的部门编码的构造器
	 */
	public BMBM(){}
	
	
	
	/**
	 * 带参数的部门编码的构造器
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @param fbmbm 父部门编码
	 * @param bmmc 部门名称
	 * @param bmjc 部门简称
	 * @param bmwhjc 部门文号简称
	 * @param bmahjc 部门案号简称
	 * @param sflsjg 是否临时机构
	 * @param sfcbbm 是否承办部门
	 * @param bmxh 部门序号
	 * @param bz 备注
	 * @param sfsc 是否删除
	 * @param bmys 部门映射
	 */
	public BMBM(String dwbm,String bmbm,String fbmbm,
			String bmmc,String bmjc,String bmwhjc,
			String bmahjc,String sflsjg,String sfcbbm,int bmxh, 
			String bz, String sfsc,String bmys) {
		this.dwbm = dwbm;
		this.bmbm = bmbm;
		this.fbmbm = fbmbm;
		this.bmmc = bmmc;
		this.bmjc = bmjc;
		this.bmwhjc = bmwhjc;
		this.bmahjc = bmahjc;
		this.sflsjg = sflsjg;
		this.sfcbbm = sfcbbm;
		this.bmxh = bmxh;
		this.bz = bz;
		this.sfsc = sfsc;
		this.bmys = bmys;
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
	 * @return 是否承办部门
	 */
	public String getSfcbbm() {
		return sfcbbm;
	}
	
	/**
	 * 
	 * @param sfcbbm 传入是否承办部门
	 */
	public void setSfcbbm(String sfcbbm) {
		this.sfcbbm = sfcbbm;
	}
	
	/**
	 * 
	 * @return 部门序号
	 */
	public int getBmxh() {
		return bmxh;
	}
	
	/**
	 * 
	 * @param bmxh 传入部门序号 
	 */
	public void setBmxh(int bmxh) {
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
	
}
