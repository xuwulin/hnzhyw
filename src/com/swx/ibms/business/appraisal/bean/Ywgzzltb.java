/**
 * 
 */
package com.swx.ibms.business.appraisal.bean;

/**
 * 业务工作考核图表总览搜索结果实体类
 * @author 封泽超
 *@since 2017年6月11日
 */
public class Ywgzzltb {
	/**
	 * 考核id
	 */
	private String khid;
	/**
	 * 业务类型考核id
	 */
	private String ywlxkhid;
	/**
	 * 单位考核状态
	 */
	private String dwkhzt;
	/**
	 * 业务类型考核状态
	 */
	private String lxkhzt;
	/**
	 * 考核分值id
	 */
	private String khfzid;
	/**
	 * 年度
	 */
	private String year;
	/**
	 * 单位编码
	 */
	private String dwbm;
	/**
	 * 
	 */
	private String dwmc;
	/**
	 * 考核类型
	 */
	private String khlx;
	/**
	 * 考核名称
	 */
	private String khmc;
	/**
	 * 考核分
	 */
	private double khf;
	/**
	 * 考核人单位
	 */
	private String khrdw;
	/**
	 * 考核人部门
	 */
	private String khrbm;
	/**
	 * 考核人工号 
	 */
	private String khrgh;
	/**
	 * 异议状态
	 */
	private String yyzt;

	/**
	 * 异议申请时间期限
	 */
	private String yysjqx;
	
	/**
	 * 公布时间
	 */
	private String gbsj;
	/**
	 * 当前单位级别
	 */
	private String dwjb;
	
	/**
	 * 
	 * @return 当前单位级别
	 */
	public String getDwjb() {
		return dwjb;
	}

	/**
	 * 
	 * @param dwjb 当前单位级别
	 */
	public void setDwjb(String dwjb) {
		this.dwjb = dwjb;
	}

	/**
	 * 
	 * @return  异议申请时间期限
	 */
	public String getYysjqx() {
		return yysjqx;
	}

	/**
	 * 
	 * @param yysjqx  异议申请时间期限
	 */
	public void setYysjqx(String yysjqx) {
		this.yysjqx = yysjqx;
	}

	/**
	 * 
	 * @return 公布时间
	 */
	public String getGbsj() {
		return gbsj;
	}

	/**
	 * 
	 * @param gbsj 公布时间 
	 */
	public void setGbsj(String gbsj) {
		this.gbsj = gbsj;
	}

	/**
	 * @return 异议状态值
	 */
	public String getYyzt() {
		return yyzt;
	}

	/**
	 * 
	 * @param yyzt 异议状态
	 */
	public void setYyzt(String yyzt) {
		this.yyzt = yyzt;
	}

	public String getKhrdw() {
		return khrdw;
	}

	public void setKhrdw(String khrdw) {
		this.khrdw = khrdw;
	}

	public String getKhrbm() {
		return khrbm;
	}

	public void setKhrbm(String khrbm) {
		this.khrbm = khrbm;
	}

	public String getKhrgh() {
		return khrgh;
	}

	public void setKhrgh(String khrgh) {
		this.khrgh = khrgh;
	}

	public String getYwlxkhid() {
		return ywlxkhid;
	}

	public void setYwlxkhid(String ywlxkhid) {
		this.ywlxkhid = ywlxkhid;
	}

	public String getDwkhzt() {
		return dwkhzt;
	}

	public void setDwkhzt(String dwkhzt) {
		this.dwkhzt = dwkhzt;
	}

	public String getLxkhzt() {
		return lxkhzt;
	}

	public void setLxkhzt(String lxkhzt) {
		this.lxkhzt = lxkhzt;
	}

	public String getKhid() {
		return khid;
	}
	public void setKhid(String khid) {
		this.khid = khid;
	}
	public String getKhfzid() {
		return khfzid;
	}
	public void setKhfzid(String khfzid) {
		this.khfzid = khfzid;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
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
	public String getKhlx() {
		return khlx;
	}
	public void setKhlx(String khlx) {
		this.khlx = khlx;
	}
	public String getKhmc() {
		return khmc;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public double getKhf() {
		return khf;
	}
	public void setKhf(double khf) {
		this.khf = khf;
	}
	
	

}
