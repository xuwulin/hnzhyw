package com.swx.ibms.business.archives.bean;

import java.util.Date;

public class SfdaGrjbxxInputExcel {
	
	 /**
     * 单位编码
     */
    private String dwbm;
    
    /**
     * 部门编码
     */
    private String bmmc;
	
    /**
     * 工号
     */
    private String gh;
    
    /**
     * 名称
     */
    private String mc;
    
    /**
     * 性别(0 女  1 男)
     */
    private String gender;
    
    /**
     * 出生年月，格式存储为yyyy-MM-dd
     */
    private String birthday;
    
    /**
     * 文化程度
     */
    private String whcd;
    
    /**
     * 学位【数据字典取数据】
     */
    private String xw;
    
    /**
     * 身份类别，eg：检察长、副检察长【数据字典表取数据】
     */
    private String sflb;
    
    /**
     * 检察官等级，eg：一级检查官、二级检察官【数据字典取数据】
     */
    private String grade;
    
    /**
     * 职务信息，eg：检察长、副检察长【数据字典取数据】
     */
    private String postInfo;
    
    /**
     * 入额时间，格式存储为yyyy-MM-dd
     */
    private String reDate;
    
    /**
     * 行政职级，eg：正国级、正厅级【数据字典取数据】
     */
    private String adminRank;
    
    /**
     * 政治面貌
     */
    private String zzmm;
    
    /**
     * 身份类别标识
     */
    private String sflbBs;
    
    private String zp;
    
    /**
	 * 所属年份
	 */
	private String year;
	
	private String id;
	
	/**
	 * 创建时间
	 */
	private Date cjsj;
	
	/**
	 * 修改时间
	 */
	private Date xgsj;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 排序序号
	 */
	private Integer xh;

	public String getDwbm() {
		return dwbm;
	}

	public void setDwbm(String dwbm) {
		this.dwbm = dwbm;
	}

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String getSflbBs() {
		return sflbBs;
	}

	public void setSflbBs(String sflbBs) {
		this.sflbBs = sflbBs;
	}

	public String getGh() {
		return gh;
	}

	public void setGh(String gh) {
		this.gh = gh;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getWhcd() {
		return whcd;
	}

	public void setWhcd(String whcd) {
		this.whcd = whcd;
	}

	public String getXw() {
		return xw;
	}

	public void setXw(String xw) {
		this.xw = xw;
	}

	public String getSflb() {
		return sflb;
	}

	public void setSflb(String sflb) {
		this.sflb = sflb;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getPostInfo() {
		return postInfo;
	}

	public void setPostInfo(String postInfo) {
		this.postInfo = postInfo;
	}

	public String getReDate() {
		return reDate;
	}

	public void setReDate(String reDate) {
		this.reDate = reDate;
	}

	public String getAdminRank() {
		return adminRank;
	}

	public void setAdminRank(String adminRank) {
		this.adminRank = adminRank;
	}

	public String getZzmm() {
		return zzmm;
	}

	public void setZzmm(String zzmm) {
		this.zzmm = zzmm;
	}

	public String getZp() {
		return zp;
	}

	public void setZp(String zp) {
		this.zp = zp;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCjsj() {
		return cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

	public Date getXgsj() {
		return xgsj;
	}

	public void setXgsj(Date xgsj) {
		this.xgsj = xgsj;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getXh() {
		return xh;
	}

	public void setXh(Integer xh) {
		this.xh = xh;
	}

	@Override
	public String toString() {
		return "SfdaGrjbxxInputExcel [dwbm=" + dwbm + ", bmmc=" + bmmc + ", gh=" + gh + ", mc=" + mc + ", gender="
				+ gender + ", birthday=" + birthday + ", whcd=" + whcd + ", xw=" + xw + ", sflb=" + sflb + ", sflbBs="
				+ sflbBs + ", grade=" + grade + ", postInfo=" + postInfo + ", reDate=" + reDate + ", adminRank="
				+ adminRank + ", zzmm=" + zzmm + ", zp=" + zp + ", year=" + year + ", id=" + id + ", cjsj=" + cjsj
				+ ", xgsj=" + xgsj + ", status=" + status + ", xh=" + xh + "]";
	}

	
	
}
