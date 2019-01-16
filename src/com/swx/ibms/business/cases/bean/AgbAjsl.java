package com.swx.ibms.business.cases.bean;

/**
*案管办案件受理实体类
*@author 徐武林
*@version 1.0
*@since 2018年4月26日
*/
public class AgbAjsl {
	/**
	 * 案件受理id
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
	 * 受理时间
	 */
	private String slsj;
	
	/**
	 * 分类时间
	 */
	private String flsj;
	
	/**
	 * 录入人员
	 */
	private String lrry;
	
	/**
	 * 审核人员
	 */
	private String shry;
	
	/**
	 * 卷宗册数
	 */
	private Integer jzcs;
	
	/**
	 * 光盘数
	 */
	private Integer gp;
	
	/**
	 * 涉案财物数
	 */
	private String sacw;
	
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
	 * 审核人
	 */
	private String spr;

	//承办小组编码
	private String cbxzbm;

	//承办小组名称
	private String cbxz;

	//案件状态
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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
	
	public String getSlsj() {
		return slsj;
	}
	
	public void setSlsj(String slsj) {
		this.slsj = slsj;
	}
	
	public String getFlsj() {
		return flsj;
	}
	
	public void setFlsj(String flsj) {
		this.flsj = flsj;
	}

	public String getLrry() {
		return lrry;
	}

	public void setLrry(String lrry) {
		this.lrry = lrry;
	}

	public String getShry() {
		return shry;
	}

	public void setShry(String shry) {
		this.shry = shry;
	}

	public Integer getJzcs() {
		return jzcs;
	}

	public void setJzcs(Integer jzcs) {
		this.jzcs = jzcs;
	}

	public Integer getGp() {
		return gp;
	}

	public void setGp(Integer gp) {
		this.gp = gp;
	}

	public String getSacw() {
		return sacw;
	}

	public void setSacw(String sacw) {
		this.sacw = sacw;
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
