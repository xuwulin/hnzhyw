package com.swx.ibms.business.archives.bean;
/**
 *监督情况实体类
 *@author徐武林
 *@since 2018年3月26日下午6:36:24
 */
public class SfdaJdqk {
	
	/**
	 * 监督情况id
	 */
	private String id;
	
	/**
	 * 接受监督种类
	 */
	private String jdzl;
	
	/**
	 * 履行监督职责的部门
	 */
	private String jdbm;
	
	/**
	 * 接受监督时间
	 */
	private String jdsj;
	
	/**
	 * 接受监督事由
	 */
	private String jdsy;
	
	/**
	 * 接受监督处理，是否结束，Y是，N否
	 */
	private char jdcl;
	
	/**
	 * 落实整改情况 1为已经整改，2为整改中，3为待整改
	 */
	private char zgqk;
	
	/**
	 * 结束监督其他情况
	 */
	private String jdqtqk;
	
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
		return "SfdaJdqk{" +
				"id='" + id + '\'' +
				", jdzl='" + jdzl + '\'' +
				", jdbm='" + jdbm + '\'' +
				", jdsj='" + jdsj + '\'' +
				", jdsy='" + jdsy + '\'' +
				", jdcl=" + jdcl +
				", zgqk=" + zgqk +
				", jdqtqk='" + jdqtqk + '\'' +
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

	public String getJdzl() {
		return jdzl;
	}

	public void setJdzl(String jdzl) {
		this.jdzl = jdzl;
	}

	public String getJdbm() {
		return jdbm;
	}

	public void setJdbm(String jdbm) {
		this.jdbm = jdbm;
	}

	public String getJdsj() {
		return jdsj;
	}

	public void setJdsj(String jdsj) {
		this.jdsj = jdsj;
	}

	public String getJdsy() {
		return jdsy;
	}

	public void setJdsy(String jdsy) {
		this.jdsy = jdsy;
	}

	public char getJdcl() {
		return jdcl;
	}

	public void setJdcl(char jdcl) {
		this.jdcl = jdcl;
	}

	public char getZgqk() {
		return zgqk;
	}

	public void setZgqk(char zgqk) {
		this.zgqk = zgqk;
	}

	public String getJdqtqk() {
		return jdqtqk;
	}

	public void setJdqtqk(String jdqtqk) {
		this.jdqtqk = jdqtqk;
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
