package com.swx.ibms.business.archives.bean;

/**
 * 
 * GS.java 公示实体类
 * @author 朱春雨
 * @date<p>2017年3月4日</p>
 * @version 1.0
 * @description<p></p>
 */
public class GS {

	/**
	 * 发起人编码
	 */
	private String fqrbm;
	/**
	 * 发起人单位编码
	 */
	private String fqrdwbm;
	/**
	 * 处理 内容 
	 */
	private String clnr;
	/**
	 * 发起时间 
	 */
	private String fqsj;
	/**
	 * 档案归总id
	 */
	private String dagzid;
	/**
	 * 所属人工号
	 */
	private String ssr;
	/**
	 * 所属人单位编码
	 */
	private String ssrdwbm;
	/**
	 * 添加年份
	 */
	private String tjnf;
	/**
	 * 
	 * @return 返回发起人编码
	 */
	public String getFqrbm() {
		return fqrbm;
	}
	/**
	 * 
	 * @param fqrbm 传入发起人编码
	 */
	public void setFqrbm(String fqrbm) {
		this.fqrbm = fqrbm;
	}
	/**
	 * 
	 * @return 返回发起人单位编码
	 */
	public String getFqrdwbm() {
		return fqrdwbm;
	}
	/**
	 * 
	 * @param fqrdwbm 传入发起人单位编码
	 */
	public void setFqrdwbm(String fqrdwbm) {
		this.fqrdwbm = fqrdwbm;
	}
	/**
	 * 
	 * @return 返回处理内容
	 */
	public String getClnr() {
		return clnr;
	}
	/**
	 * 
	 * @param clnr 传入处理内容
	 */
	public void setClnr(String clnr) {
		this.clnr = clnr;
	}
	/**
	 * 
	 * @return 返回发起时间
	 */
	public String getFqsj() {
		return fqsj;
	}
	/**
	 * 
	 * @param fqsj 传入发起时间
	 */
	public void setFqsj(String fqsj) {
		this.fqsj = fqsj;
	}
	/**
	 * 
	 * @return 返回档案归总ID
	 */
	public String getDagzid() {
		return dagzid;
	}
	/**
	 * 
	 * @param dagzid 传入档案柜总ID
	 */
	public void setDagzid(String dagzid) {
		this.dagzid = dagzid;
	}
	/**
	 * 
	 * @return 返回所属人工号 
	 */
	public String getSsr() {
		return ssr;
	}
	/**
	 * 
	 * @param ssr 传入所属人工号
	 */
	public void setSsr(String ssr) {
		this.ssr = ssr;
	}
	/**
	 * 
	 * @return 所属人单位编码
	 */
	public String getSsrdwbm() {
		return ssrdwbm;
	}
	/**
	 * 
	 * @param ssrdwbm 传入所属人单位编码
	 */
	public void setSsrdwbm(String ssrdwbm) {
		this.ssrdwbm = ssrdwbm;
	}
	/**
	 * 
	 * @return 返回添加年份
	 */
	public String getTjnf() {
		return tjnf;
	}
	/**
	 * 
	 * @param tjnf 传入添加年份
	 */
	public void setTjnf(String tjnf) {
		this.tjnf = tjnf;
	}
	
	@Override
	public String toString() {
		return "GS [fqrbm=" + fqrbm + ", fqrdwbm=" + fqrdwbm + ","
				+ " clnr=" + clnr + ", fqsj=" + fqsj + ", dagzid="
				+ dagzid + ", ssr=" + ssr + ", ssrdwbm=" + ssrdwbm + ", tjnf=" + tjnf + "]";
	}
	
   
}
