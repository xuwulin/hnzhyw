package com.swx.ibms.business.archives.bean;
/**
 * 职业操守实体类
 *@author 徐武林
 *@since 2018年3月26日下午6:23:52
 */
public class SfdaZycs {
	
	/**
	 * 项目id
	 */
	private String id;
	
	/**
	 * 项目名称
	 */
	private String xmmc;
	
	/**
	 * 项目类型
	 */
	private String xmlx;
	
	/**
	 * 业务时间
	 */
	private String ywsj;
	
	/**
	 * 业务地点
	 */
	private String ywdd;
	
	/**
	 * 组织部门
	 */
	private String zzbm;
	
	/**
	 * 审批部门
	 */
	private String spbm;
	
	/**
	 * 主要内容
	 */
	private String zynr;
	
	/**
	 * 主要收获
	 */
	private String zysh;
	
	/**
	 * 研究成果
	 */
	private String yjcg;
	
	/**业务其他情况
	 * 
	 */
	private String ywqtqk;
	
	/**
	 * 档案id
	 */
	private String gdid;
	
	/**
	 * 是否删除
	 */
	private char sfsc;
	
	/**
	 * 开始时间
	 */
	private String kssj;
	
	/**
	 * 结束时间
	 */
	private String jssj;
	
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
		return "SfdaZycs{" +
				"id='" + id + '\'' +
				", xmmc='" + xmmc + '\'' +
				", xmlx='" + xmlx + '\'' +
				", ywsj='" + ywsj + '\'' +
				", ywdd='" + ywdd + '\'' +
				", zzbm='" + zzbm + '\'' +
				", spbm='" + spbm + '\'' +
				", zynr='" + zynr + '\'' +
				", zysh='" + zysh + '\'' +
				", yjcg='" + yjcg + '\'' +
				", ywqtqk='" + ywqtqk + '\'' +
				", gdid='" + gdid + '\'' +
				", sfsc=" + sfsc +
				", kssj='" + kssj + '\'' +
				", jssj='" + jssj + '\'' +
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

	public String getXmmc() {
		return xmmc;
	}

	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}

	public String getXmlx() {
		return xmlx;
	}

	public void setXmlx(String xmlx) {
		this.xmlx = xmlx;
	}

	public String getYwsj() {
		return ywsj;
	}

	public void setYwsj(String ywsj) {
		this.ywsj = ywsj;
	}

	public String getYwdd() {
		return ywdd;
	}

	public void setYwdd(String ywdd) {
		this.ywdd = ywdd;
	}

	public String getZzbm() {
		return zzbm;
	}

	public void setZzbm(String zzbm) {
		this.zzbm = zzbm;
	}

	public String getSpbm() {
		return spbm;
	}

	public void setSpbm(String spbm) {
		this.spbm = spbm;
	}

	public String getZynr() {
		return zynr;
	}

	public void setZynr(String zynr) {
		this.zynr = zynr;
	}

	public String getZysh() {
		return zysh;
	}

	public void setZysh(String zysh) {
		this.zysh = zysh;
	}

	public String getYjcg() {
		return yjcg;
	}

	public void setYjcg(String yjcg) {
		this.yjcg = yjcg;
	}

	public String getYwqtqk() {
		return ywqtqk;
	}

	public void setYwqtqk(String ywqtqk) {
		this.ywqtqk = ywqtqk;
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

	public String getKssj() {
		return kssj;
	}

	public void setKssj(String kssj) {
		this.kssj = kssj;
	}

	public String getJssj() {
		return jssj;
	}

	public void setJssj(String jssj) {
		this.jssj = jssj;
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
