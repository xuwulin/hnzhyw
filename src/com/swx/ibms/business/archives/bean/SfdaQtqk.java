package com.swx.ibms.business.archives.bean;
/**
 *其他情况实体类
 *@author徐武林
 *@since 2018年3月26日下午6:55:16
 */
public class SfdaQtqk {
	
	/**
	 * 情况id
	 */
	private String id;
	
	/**
	 * 录入部门
	 */
	private String lrbm;
	
	/**
	 * 录入人
	 */
	private String lrr;
	
	/**
	 * 录入时间
	 */
	private String lrsj;
	
	/**
	 * 录入类容
	 */
	private String lrnr;
	
	/**
	 * 档案id
	 */
	private String gdid;
	
	/**
	 * 是否删除
	 */
	private char sfsc;
	
	/**
	 * 最后更新部门
	 */
	private char zhgxbm;
	
	/**
	 * 最后更新人
	 */
	private char zhgxr;
	
	/**
	 * 最后更新时间
	 */
	private String zhgxsj;

	/**
	 * 状态
	 */
	private String status;

	@Override
	public String toString() {
		return "SfdaQtqk{" +
				"id='" + id + '\'' +
				", lrbm='" + lrbm + '\'' +
				", lrr='" + lrr + '\'' +
				", lrsj='" + lrsj + '\'' +
				", lrnr='" + lrnr + '\'' +
				", gdid='" + gdid + '\'' +
				", sfsc=" + sfsc +
				", zhgxbm=" + zhgxbm +
				", zhgxr=" + zhgxr +
				", zhgxsj='" + zhgxsj + '\'' +
				", status='" + status + '\'' +
				'}';
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {

		return status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLrbm() {
		return lrbm;
	}

	public void setLrbm(String lrbm) {
		this.lrbm = lrbm;
	}

	public String getLrr() {
		return lrr;
	}

	public void setLrr(String lrr) {
		this.lrr = lrr;
	}

	public String getLrsj() {
		return lrsj;
	}

	public void setLrsj(String lrsj) {
		this.lrsj = lrsj;
	}

	public String getLrnr() {
		return lrnr;
	}

	public void setLrnr(String lrnr) {
		this.lrnr = lrnr;
	}

	public String getGdid() {
		return gdid;
	}

	public void setGdid(String gdid) {
		this.gdid = gdid;
	}

	public char getSfsc() {
		return sfsc;
	}

	public void setSfsc(char sfsc) {
		this.sfsc = sfsc;
	}

	public char getZhgxbm() {
		return zhgxbm;
	}

	public void setZhgxbm(char zhgxbm) {
		this.zhgxbm = zhgxbm;
	}

	public char getZhgxr() {
		return zhgxr;
	}

	public void setZhgxr(char zhgxr) {
		this.zhgxr = zhgxr;
	}

	public String getZhgxsj() {
		return zhgxsj;
	}

	public void setZhgxsj(String zhgxsj) {
		this.zhgxsj = zhgxsj;
	}
	
	

}
