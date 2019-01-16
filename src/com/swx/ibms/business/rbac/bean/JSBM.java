package com.swx.ibms.business.rbac.bean;

/**
 * 角色编码实体类
 * @author 李治鑫
 * @since 2017-5-9
 */
public class JSBM {
	
	/**
	 * 单位编码
	 */
	private String dwbm;
	/**
	 * 角色编号
	 */
	private String jsbm;
	/**
	 * 角色名称
	 */
	private String jsmc;
	/**
	 * 部门编码
	 */
	private String bmbm;
	/**
	 * 角色序号
	 */
	private int jsxh;
	/**
	 * 审批角色编码
	 */
	private String spjsbm;
	/**
	 * 部门角色
	 */
	private String bmjs;
	/**
	 * 部门映射
	 */
	private String bmys;
	/**
	 * 角色描述
	 */
	private String description;
	/**
	 * 部门名称
	 */
	private String bmmc;
	/**
	 * 各个数据库查询出的数据类型，1：统一业务  2：综合业务
	 */
	private String type;
	/**
	 * 是否删除，"N"：否  ，  "Y"：是
	 */
	private String sfsc;
	/**
	 * 单位名称
	 */
	private String dwmc;
	
	/**
	 * 获取单位名称
	 * @return 单位名称
	 */
	public String getDwmc() {
		return dwmc;
	}

	/**
	 * 设置单位名称
	 * @param dwmc 单位名称
	 */
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}

	/**
	 * 获取是否删除
	 * @return 是否删除
	 */
	public String getSfsc() {
		return sfsc;
	}

	/**
	 * 设置是否删除
	 * @param sfsc 是否删除
	 */
	public void setSfsc(String sfsc) {
		this.sfsc = sfsc;
	}

	/**
	 * 获取部门名称
	 * @return 部门名称
	 */
	public String getBmmc() {
		return bmmc;
	}

	/**
	 * 设置部门名称
	 * @param bmmc 部门名称
	 */
	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	/**
	 * 获取各个数据库查询出的数据类型，1：统一业务  2：综合业务
	 * @return 1：统一业务  2：综合业务
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置各个数据库查询出的数据类型，1：统一业务  2：综合业务
	 * @param type 1：统一业务  2：综合业务
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 无参构造函数
	 */
	public JSBM(){}
	

	/**
	 * 获取单位编码
	 * @return 单位编码
	 */
	public String getDwbm() {
		return dwbm;
	}
	/**
	 * 设置单位编码
	 * @param dwbm 单位编码
	 */
	public void setDwbm(String dwbm) {
		this.dwbm = dwbm;
	}
	/**
	 * 获取角色编码
	 * @return 角色编码
	 */
	public String getJsbm() {
		return jsbm;
	}
	/**
	 * 设置角色编码
	 * @param jsbm 角色编码
	 */
	public void setJsbm(String jsbm) {
		this.jsbm = jsbm;
	}
	/**
	 * 获取角色名称
	 * @return 角色名称
	 */
	public String getJsmc() {
		return jsmc;
	}
	/**
	 * 设置角色名称
	 * @param jsmc 角色名称
	 */
	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}
	/**
	 * 获取部门编码
	 * @return 部门编码
	 */
	public String getBmbm() {
		return bmbm;
	}
	/**
	 * 设置部门编码
	 * @param bmbm 部门编码
	 */
	public void setBmbm(String bmbm) {
		this.bmbm = bmbm;
	}
	/**
	 * 获取角色序号
	 * @return 角色序号
	 */
	public int getJsxh() {
		return jsxh;
	}
	/**
	 * 设置角色序号
	 * @param jsxh 角色序号
	 */
	public void setJsxh(int jsxh) {
		this.jsxh = jsxh;
	}
	/**
	 * 获取审批角色编码
	 * @return 审批角色编码
	 */
	public String getSpjsbm() {
		return spjsbm;
	}
	/**
	 * 设置审批角色编码
	 * @param spjsbm 审批角色编码
	 */
	public void setSpjsbm(String spjsbm) {
		this.spjsbm = spjsbm;
	}



	/**
	 * 获取部门角色
	 * @return 部门角色
	 */
	public String getBmjs() {
		return bmjs;
	}



	/**
	 * 设置部门角色
	 * @param bmjs 部门角色
	 */
	public void setBmjs(String bmjs) {
		this.bmjs = bmjs;
	}



	/**
	 * 获取部门映射
	 * @return 部门映射
	 */
	public String getBmys() {
		return bmys;
	}



	/**
	 * 设置部门映射
	 * @param bmys 部门映射
	 */
	public void setBmys(String bmys) {
		this.bmys = bmys;
	}



	/**
	 * 获取部门描述
	 * @return 部门描述
	 */
	public String getDescription() {
		return description;
	}



	/**
	 * 设置部门描述
	 * @param description 部门描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
