package com.swx.ibms.business.archives.bean;
/**
 * 荣誉技能实体类
 *@author 徐武林
 *@since 2018年3月26日下午6:12:55
 */
public class SfdaRyjn {
	
	/**
	 * 荣誉id，主键
	 */
	private  String id;
	
	/**
	 * 荣誉名称
	 */
	private String rymc;
	
	/**
	 * 获奖时间
	 */
	private String hjsj;
	
	/**
	 * 颁奖单位
	 */
	private String bjdw;
	
	/**
	 * 获奖事由
	 */
	private String hjsy;
	
	/**
	 * 获奖其他情况
	 */
	private String hjqtqk;
	
	/**
	 * 档案id
	 */
	private String gdid;
	
	/**
	 * 获奖等级编码
	 */
	private String hjdjbm;
	
	/**
	 * 获奖等级
	 */
	private String hjdj;
	
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
		return "SfdaRyjn{" +
				"id='" + id + '\'' +
				", rymc='" + rymc + '\'' +
				", hjsj='" + hjsj + '\'' +
				", bjdw='" + bjdw + '\'' +
				", hjsy='" + hjsy + '\'' +
				", hjqtqk='" + hjqtqk + '\'' +
				", gdid='" + gdid + '\'' +
				", hjdjbm='" + hjdjbm + '\'' +
				", hjdj='" + hjdj + '\'' +
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

	public String getRymc() {
		return rymc;
	}

	public void setRymc(String rymc) {
		this.rymc = rymc;
	}

	public String getHjsj() {
		return hjsj;
	}

	public void setHjsj(String hjsj) {
		this.hjsj = hjsj;
	}

	public String getBjdw() {
		return bjdw;
	}

	public void setBjdw(String bjdw) {
		this.bjdw = bjdw;
	}

	public String getHjsy() {
		return hjsy;
	}

	public void setHjsy(String hjsy) {
		this.hjsy = hjsy;
	}

	public String getHjqtqk() {
		return hjqtqk;
	}

	public void setHjqtqk(String hjqtqk) {
		this.hjqtqk = hjqtqk;
	}

	public String getGdid() {
		return gdid;
	}

	public void setGdid(String gdid) {
		this.gdid = gdid;
	}

	public String getHjdjbm() {
		return hjdjbm;
	}

	public void setHjdjbm(String hjdjbm) {
		this.hjdjbm = hjdjbm;
	}

	public String getHjdj() {
		return hjdj;
	}

	public void setHjdj(String hjdj) {
		this.hjdj = hjdj;
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
