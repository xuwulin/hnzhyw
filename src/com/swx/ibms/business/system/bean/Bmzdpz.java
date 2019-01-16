package com.swx.ibms.business.system.bean;

import java.sql.Date;

/**
 * 审批流程部门指定审批人配置表实体类
 * @author 李治鑫
 * @since 2017年7月14日  上午10:27:36
 */
public class Bmzdpz {
	/**
	 * 编号
	 */
	private String id ;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 部门映射
	 */
	private String bmys;
	/**
	 * 类型
	 */
	private String splx;
	/**
	 * 单位编码
	 */
	private String dwbm;
	/**
	 * 单位名称
	 */
	private String dwmc;
	/**
	 * 部门编码
	 */
	private String bmbm;
	/**
	 * 工号
	 */
	private String gh;
	/**
	 * 创建时间
	 */
	private Date createtime;
	/**
	 * 更新时间
	 */
	private Date updatetime;
	/**
	 * 部门名称
	 */
	private String bmmc;
	/**
	 * 登录别名
	 */
	private String dlbm;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBmys() {
		return bmys;
	}
	public void setBmys(String bmys) {
		this.bmys = bmys;
	}
	public String getSplx() {
		return splx;
	}
	public void setSplx(String splx) {
		this.splx = splx;
	}
	public String getDwbm() {
		return dwbm;
	}
	public void setDwbm(String dwbm) {
		this.dwbm = dwbm;
	}
	public String getDwmc() {
		return dwmc;
	}
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}
	public String getBmbm() {
		return bmbm;
	}
	public void setBmbm(String bmbm) {
		this.bmbm = bmbm;
	}
	public String getGh() {
		return gh;
	}
	public void setGh(String gh) {
		this.gh = gh;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getBmmc() {
		return bmmc;
	}
	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}
	public String getDlbm() {
		return dlbm;
	}
	public void setDlbm(String dlbm) {
		this.dlbm = dlbm;
	}
	
	@Override
	public String toString() {
		return "Bmzdpz [id=" + id + ", name=" + name + ", bmys=" + bmys + ", splx=" + splx + ", dwbm=" + dwbm
				+ ", dwmc=" + dwmc + ", bmbm=" + bmbm + ", gh=" + gh + ", createtime=" + createtime + ", updatetime="
				+ updatetime + ", bmmc=" + bmmc + ", dlbm=" + dlbm + "]";
	}
	
}
