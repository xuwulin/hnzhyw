package com.swx.ibms.business.archives.bean;

import java.sql.Date;

/**
 * 公示记录实体类
 * @author 李治鑫
 *
 */
public class Gsjl {
	/**
	 * 编号
	 */
	private String id;
	/**
	 * 档案归总ID
	 */
	private String dagzid;
	/**
	 * 单位编码
	 */
	private String dwbm;
	/**
	 * 操作人工号
	 */
	private String czrgh;
	/**
	 * 操作时间
	 */
	private Date czsj;
	/**
	 * 操作类型【1 已公示  2 未公示/取消公示】
	 */
	private String czlx;
	/**
	 * 公示开始时间
	 */
	private Date gsKssj;
	/**
	 * 公示截止时间
	 */
	private Date gsJzsj;
	/**
	 * 公示信息
	 */
	private String gsxx;
	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return dagzid
	 */
	public String getDagzid() {
		return dagzid;
	}
	/**
	 * @param dagzid dagzid
	 */
	public void setDagzid(String dagzid) {
		this.dagzid = dagzid;
	}
	/**
	 * @return dwbm
	 */
	public String getDwbm() {
		return dwbm;
	}
	/**
	 * @param dwbm dwbm
	 */
	public void setDwbm(String dwbm) {
		this.dwbm = dwbm;
	}
	/**
	 * @return czrgh
	 */
	public String getCzrgh() {
		return czrgh;
	}
	/**
	 * @param czrgh  czrgh
	 */
	public void setCzrgh(String czrgh) {
		this.czrgh = czrgh;
	}
	/**
	 * @return  czsj
	 */
	public Date getCzsj() {
		return czsj;
	}
	/**
	 * @param czsj czsj
	 */
	public void setCzsj(Date czsj) {
		this.czsj = czsj;
	}
	/**
	 * @return czlx
	 */
	public String getCzlx() {
		return czlx;
	}
	/**
	 * @param czlx czlx
	 */
	public void setCzlx(String czlx) {
		this.czlx = czlx;
	}
	
	/**
	 * @return gsJzsj
	 */
	public Date getGsJzsj() {
		return gsJzsj;
	}
	/**
	 * @param gsJzsj gsJzsj
	 */
	public void setGsJzsj(Date gsJzsj) {
		this.gsJzsj = gsJzsj;
	}
	/**
	 * @param gsJzsj gsJzsj
	 */
	public void setGsJzsj(String gsJzsj) {
		this.gsJzsj = Date.valueOf(gsJzsj);
	}
	/**
	 * @return gsxx
	 */
	public String getGsxx() {
		return gsxx;
	}
	/**
	 * @param gsxx gsxx
	 */
	public void setGsxx(String gsxx) {
		this.gsxx = gsxx;
	}
	
	public Date getGsKssj() {
		return gsKssj;
	}
	public void setGsKssj(Date gsKssj) {
		this.gsKssj = gsKssj;
	}
	
	@Override
	public String toString() {
		return "Gsjl [id=" + id + ", dagzid=" + dagzid + ", dwbm=" + dwbm + ", czrgh=" + czrgh + ", czsj=" + czsj
				+ ", czlx=" + czlx + ", gsKssj=" + gsKssj + ", gsJzsj=" + gsJzsj + ", gsxx=" + gsxx + "]";
	}
	
	
	
}
