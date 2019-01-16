package com.swx.ibms.business.archives.bean;

/**
 * @author 李佳
 * 检察官查询实体类<br/>
 * 主要属性 工号、名称、归档ID、单位编码、是否公示、审批状态、开始时间、结束时间、荣誉技能档案数量、司法责任档案数量、职业操守档案数量、其他档案数量、主办案件数量   
 */
public class JcgSfdaCx {

	/**
	 * 工号
	 */
	private String gh; 
	/**
	 * 名称
	 */
	private String mc; 
	/**
	 * 归档ID
	 */
	private String id; // 归档ID
	/**
	 * 单位编码
	 */
	private String dwbm; // 单位编码
	/**
	 * 是否公示
	 */
	private String sfgs;// 是否公示
    /**
     * 审批状态
     */
    private String spzt;//审批状态
    /**
     * 开始时间
     */
    private String kssj;//开始时间
    /**
     * 结束时间
     */
    private String jssj;//结束时间
    /**
     * 荣誉技能档案数量
     */
    private Integer rycount;//荣誉技能档案数量
    /**
     * 司法责任档案数量
     */
    private Integer sfcount;//司法责任档案数量
    /**
     * 监督情况档案数量
     */
    private Integer jdcount;//司法责任档案数量
    public Integer getJdcount() {
		return jdcount;
	}

	public void setJdcount(Integer jdcount) {
		this.jdcount = jdcount;
	}

	/**
     * 职业操守档案数量
     */
    private Integer zycount;//职业操守档案数量
    /**
     * 其他档案数量
     */
    private Integer qtcount;//其他档案数量
    /**
     * 主办案件数量
     */
    private Integer zbajcount;//主办案件数量
    /**
     * 参办案件数量
     */
    private Integer cbajcount;
    
    /**
     * 办理案件总数
     */
    private Integer blajcount;
    
    /**
     * 司法追究数量
     */
    private Integer sfzjcount;
    
    
	public Integer getSfzjcount() {
		return sfzjcount;
	}

	public void setSfzjcount(Integer sfzjcount) {
		this.sfzjcount = sfzjcount;
	}

	public Integer getBlajcount() {
		return blajcount;
	}

	public void setBlajcount(Integer blajcount) {
		this.blajcount = blajcount;
	}

	/**
	 * @return 工号
	 */
	public String getGh() {
		return gh;
	}

	/**
	 * @param gh 传入工号
	 */
	public void setGh(String gh) {
		this.gh = gh;
	}

	/**
	 * @return 名称
	 */
	public String getMc() {
		return mc;
	}

	/**
	 * @param mc 传入名称
	 */
	public void setMc(String mc) {
		this.mc = mc;
	}

	/**
	 * @return 归档ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id 传入归档ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return 单位编码
	 */
	public String getDwbm() {
		return dwbm;
	}

	/**
	 * @param dwbm 传入单位编码
	 */
	public void setDwbm(String dwbm) {
		this.dwbm = dwbm;
	}

	/**
	 * @return 是否公示
	 */
	public String getSfgs() {
		return sfgs;
	}

	/**
	 * @param sfgs 传入是否公示
	 */
	public void setSfgs(String sfgs) {
		this.sfgs = sfgs;
	}

	/**
	 * @return 审批状态
	 */
	public String getSpzt() {
		return spzt;
	}

	/**
	 * @param spzt 传入审批状态
	 */
	public void setSpzt(String spzt) {
		this.spzt = spzt;
	}

	/**
	 * @return 开始时间
	 */
	public String getKssj() {
		return kssj;
	}

	/**
	 * @param kssj 传入开始时间
	 */
	public void setKssj(String kssj) {
		this.kssj = kssj;
	}

	/**
	 * @return 结束时间
	 */
	public String getJssj() {
		return jssj;
	}

	/**
	 * @param jssj 结束时间
	 */
	public void setJssj(String jssj) {
		this.jssj = jssj;
	}

	/**
	 * @return 荣誉技能档案数量
	 */
	public Integer getRycount() {
		return rycount;
	}

	/**
	 * @param rycount 传入荣誉技能档案数量
	 */
	public void setRycount(Integer rycount) {
		this.rycount = rycount;
	}

	/**
	 * @return 司法责任档案数量
	 */
	public Integer getSfcount() {
		return sfcount;
	}

	/**
	 * @param sfcount 传入司法责任档案数量
	 */
	public void setSfcount(Integer sfcount) {
		this.sfcount = sfcount;
	}

	/**
	 * @return 职业操守档案数量
	 */
	public Integer getZycount() {
		return zycount;
	}

	/**
	 * @param zycount 传入职业操守档案数量
	 */
	public void setZycount(Integer zycount) {
		this.zycount = zycount;
	}

	/**
	 * @return 其他档案数量
	 */
	public Integer getQtcount() {
		return qtcount;
	}

	/**
	 * @param qtcount 传入其他档案数量
	 */
	public void setQtcount(Integer qtcount) {
		this.qtcount = qtcount;
	}

	/**
	 * @return 主办案件数量
	 */
	public Integer getZbajcount() {
		return zbajcount;
	}

	/**
	 * @param zbajcount 传入主办案件数量
	 */
	public void setZbajcount(Integer zbajcount) {
		this.zbajcount = zbajcount;
	}

	/**
	 * 监督情况总数
	 * @return
	 */
	public Integer getCbajcount() {
		return cbajcount;
	}

	public void setCbajcount(Integer cbajcount) {
		this.cbajcount = cbajcount;
	}
   
   
}
