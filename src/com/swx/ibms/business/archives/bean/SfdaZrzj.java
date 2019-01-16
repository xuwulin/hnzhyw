package com.swx.ibms.business.archives.bean;
/**
 * 责任追究实体类
 *@author 徐武林
 *@since 2018年3月26日下午6:46:09
 */
public class SfdaZrzj {
	
	/**
	 * 责任id
	 */
	private String id;
	
	/**
	 * 承担责任种类
	 */
	private String zrzl;
	
	/**
	 * 处理部门（单位）
	 */
	private String clbm;
	
	/**
	 * 处理时间
	 */
	private String clsj;
	
	/**
	 * 处理事由
	 */
	private String clsy;
	
	/**
	 * 处理内容
	 */
	private String clnr;
	
	/**
	 * 处理结果落实情况 1为已经处理，2为处理中，3为待处理
	 */
	private char cljg;
	
	/**
	 * 责任追究的其他情况
	 */
	private String zrqtqk;
	
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
	 * 追后更新人
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
		return "SfdaZrzj{" +
				"id='" + id + '\'' +
				", zrzl='" + zrzl + '\'' +
				", clbm='" + clbm + '\'' +
				", clsj='" + clsj + '\'' +
				", clsy='" + clsy + '\'' +
				", clnr='" + clnr + '\'' +
				", cljg=" + cljg +
				", zrqtqk='" + zrqtqk + '\'' +
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

	public String getZrzl() {
		return zrzl;
	}

	public void setZrzl(String zrzl) {
		this.zrzl = zrzl;
	}

	public String getClbm() {
		return clbm;
	}

	public void setClbm(String clbm) {
		this.clbm = clbm;
	}

	public String getClsj() {
		return clsj;
	}

	public void setClsj(String clsj) {
		this.clsj = clsj;
	}

	public String getClsy() {
		return clsy;
	}

	public void setClsy(String clsy) {
		this.clsy = clsy;
	}

	public String getClnr() {
		return clnr;
	}

	public void setClnr(String clnr) {
		this.clnr = clnr;
	}

	public char getCljg() {
		return cljg;
	}

	public void setCljg(char cljg) {
		this.cljg = cljg;
	}

	public String getZrqtqk() {
		return zrqtqk;
	}

	public void setZrqtqk(String zrqtqk) {
		this.zrqtqk = zrqtqk;
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
