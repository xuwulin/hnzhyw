package com.swx.ibms.business.cases.bean;

/**
*案管办流程监控实体
*@author 徐武林
*@version 1.0
*@since 2017年4月26日
*/
public class AgbLcjk {
	
	/**
	 * 流程监控id
	 */
	private String id;
	
	/**
	 * 案件名称
	 */
	private String ajmc;
	
	/**
	 * 业务类别
	 */
	private String ywlb;
	
	/**
	 * 监控日期
	 */
	private String jkrq;
	
	/**
	 * 监控人
	 */
	private String jkr;
	
	/**
	 * 监控内容
	 */
	private String jknr;
	
	/**
	 * 问题个数
	 */
	private Integer wtgs;
	
	/**
	 * 监控方式
	 */
	private String jkfs;
	
	/**
	 * 办理形式
	 */
	private String blxs;

	/**
	 * 档案Id
	 */
	private String did;
	
	/**
     * 最后更新部门
     */
    private String zhgxbm;
    
    /**
     * 最后更新部门
     */
    private String zhgxr;
    
    /**
     * 最后更新时间
     */
    private String zhgxsj;

	/**
	 * 案件状态
	 */
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 审核人
	 */
	private String spr;

	//承办小组编码
	private String cbxzbm;

	//承办小组名称
	private String cbxz;

	public String getCbxzbm() {
		return cbxzbm;
	}

	public void setCbxzbm(String cbxzbm) {
		this.cbxzbm = cbxzbm;
	}

	public String getCbxz() {
		return cbxz;
	}

	public void setCbxz(String cbxz) {
		this.cbxz = cbxz;
	}

	public String getSpr() {
		return spr;
	}

	public void setSpr(String spr) {
		this.spr = spr;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAjmc() {
		return ajmc;
	}

	public void setAjmc(String ajmc) {
		this.ajmc = ajmc;
	}

	public String getYwlb() {
		return ywlb;
	}

	public void setYwlb(String ywlb) {
		this.ywlb = ywlb;
	}

	public String getJkrq() {
		return jkrq;
	}
	public void setJkrq(String jkrq) {
		this.jkrq = jkrq;
	}

	public String getJkr() {
		return jkr;
	}

	public void setJkr(String jkr) {
		this.jkr = jkr;
	}

	public String getJknr() {
		return jknr;
	}

	public void setJknr(String jknr) {
		this.jknr = jknr;
	}

	public Integer getWtgs() {
		return wtgs;
	}

	public void setWtgs(Integer wtgs) {
		this.wtgs = wtgs;
	}

	public String getJkfs() {
		return jkfs;
	}

	public void setJkfs(String jkfs) {
		this.jkfs = jkfs;
	}

	public String getBlxs() {
		return blxs;
	}

	public void setBlxs(String blxs) {
		this.blxs = blxs;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getZhgxbm() {
		return zhgxbm;
	}

	public void setZhgxbm(String zhgxbm) {
		this.zhgxbm = zhgxbm;
	}

	public String getZhgxr() {
		return zhgxr;
	}

	public void setZhgxr(String zhgxr) {
		this.zhgxr = zhgxr;
	}

	public String getZhgxsj() {
		return zhgxsj;
	}

	public void setZhgxsj(String zhgxsj) {
		this.zhgxsj = zhgxsj;
	}

}
