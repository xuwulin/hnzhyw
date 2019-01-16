package com.swx.ibms.business.performance.bean;

/**
 * 评分材料实体类
 * @author 李治鑫
 * @since 2017-5-9
 */
public class Pfcl {
	/**
	 * 材料ID
	 */
	private String id ;
	/**
	 * 业务考核分值ID
	 */
	private String ywkhfzid;
	/**
	 * 指标项编码
	 */
	private String zbxbm;
	/**
	 * 类型
	 */
	private String lx;
	/**
	 * 附件信息
	 */
	private String fjxx;
	/**
	 * 备注
	 */
	private String bz;
	
	/**
	 * 无参构造函数
	 */
	public Pfcl (){}
	/**
	 * 获取材料ID
	 * @return 材料ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置材料ID
	 * @param id 材料ID
 	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取业务考核分值ID
	 * @return 业务考核分值ID
	 */
	public String getYwkhfzid() {
		return ywkhfzid;
	}
	/**
	 * 设置业务考核分值ID
	 * @param ywkhfzid 业务考核分值ID
	 */
	public void setYwkhfzid(String ywkhfzid) {
		this.ywkhfzid = ywkhfzid;
	}
	/**
	 * 获取指标项编码
	 * @return 指标项编码
	 */
	public String getZbxbm() {
		return zbxbm;
	}
	/**
	 * 设置指标项编码
	 * @param zbxbm 指标项编码
	 */
	public void setZbxbm(String zbxbm) {
		this.zbxbm = zbxbm;
	}
	/**
	 * 获取类型
	 * @return 类型
	 */
	public String getLx() {
		return lx;
	}
	/**
	 * 设置类型
	 * @param lx 类型
	 */
	public void setLx(String lx) {
		this.lx = lx;
	}
	/**
	 * 获取附件信息
	 * @return 附件信息
	 */
	public String getFjxx() {
		return fjxx;
	}
	/**
	 * 设置附件信息
	 * @param fjxx 附件信息
	 */
	public void setFjxx(String fjxx) {
		this.fjxx = fjxx;
	}
	/**
	 * 获取备注信息
	 * @return 备注信息
	 */
	public String getBz() {
		return bz;
	}
	/**
	 * 设置备注信息
	 * @param bz 备注信息
 	 */
	public void setBz(String bz) {
		this.bz = bz;
	}
	
	
}
