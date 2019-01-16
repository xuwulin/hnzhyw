package com.swx.ibms.business.cases.bean;

/**
*案官办质量评查
*@author 徐武林
*@version 1.0
*@since 2017年4月26日
*/
public class AgbZlpc {

	/**
	 * 质量评查id
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
	 * 评查种类
	 */
	private String pczl;
	
	/**
	 * 办理方式
	 */
	private String blxs;
	
	/**
	 * 评查开始时间
	 */
	private String pckssj;
	
	/**
	 * 评查结束时间
	 */
	private String pcjssj;
	
	/**
	 * 评查问题个数
	 */
	private Integer pcwtgs;
	
	/**
	 * 评查结果形式
	 */
	private String pcjgxs;
	
	/**
	 * 整改情况
	 */
	private String zgqk;
	
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

	public String getBlxs() {

		return blxs;
	}

	public void setBlxs(String blxs) {
		this.blxs = blxs;
	}

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

	public String getPczl() {
		return pczl;
	}

	public void setPczl(String pczl) {
		this.pczl = pczl;
	}

	public String getBlfs() {
		return blxs;
	}

	public void setBlfs(String blxs) {
		this.blxs = blxs;
	}

	public String getPckssj() {
		return pckssj;
	}
	public void setPckssj(String pckssj) {
		this.pckssj = pckssj;
	}

	public String getPcjssj() {
		return pcjssj;
	}
	public void setPcjssj(String pcjssj) {
		this.pcjssj = pcjssj;
	}


	public Integer getPcwtgs() {
		return pcwtgs;
	}

	public void setPcwtgs(Integer pcwtgs) {
		this.pcwtgs = pcwtgs;
	}

	public String getPcjgxs() {
		return pcjgxs;
	}

	public void setPcjgxs(String pcjgxs) {
		this.pcjgxs = pcjgxs;
	}

	public String getZgqk() {
		return zgqk;
	}

	public void setZgqk(String zgqk) {
		this.zgqk = zgqk;
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
