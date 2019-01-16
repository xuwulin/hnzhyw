package com.swx.ibms.business.archives.bean;

import java.io.Serializable;

/**
 * @author 李佳
 * 人员实体类 <br/>
 * 主要属性：工号、名称
 */
@SuppressWarnings("all")
public class Person implements Serializable{
	
	 /**
     * 单位编码
     */
    private String dwbm;
    
    /**
     * 部门编码
     */
    private String bmbm;
	
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
	 * 序列化ID
	 */
	private static final long UUID = 1L;
   
    /**
     * 人员id
     */
    private String id;
    
    /**
     * 民族
     */
    private String nation;
    
    /**
     * 证件类型(身份证  工作证 其他)
     */
    private String zjlx;
    
    /**
     * 证件号码
     */
    private String zjhm;
    
    /**
     * 籍贯
     */
    private String jg;
    
    /**
     * 现住址
     */
    private String xzz;
    
    /**
     * 家庭住址
     */
    private String jtzz;
    
    /**
     * 手机号码
     */
    private String phone;
    
    /**
     * 座机
     */
    private String telephone;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 工作单位
     */
    private String gzdw;
    
    
    /**
     *  档案id
     */
    private String daId;
    
    /**
     * 个人头像照片
     */
    private String zp; 
    
    /**
     * 照片名称+后缀
     */
    private String zpName;
    
    public String getZpName() {
		return zpName;
	}

	public void setZpName(String zpName) {
		this.zpName = zpName;
	}

	public String getZp() {
		return zp;
	}

	public void setZp(String zp) {
		this.zp = zp;
	}

	/**
     * 
     * @return 档案id
     */
    public String getDaId() {
		return daId;
	}
    
    /**
     * 
     * @param daId 传入档案id
     */
	public void setDaId(String daId) {
		this.daId = daId;
	}
	
	/**
     * 
     * @return 工作单位
     */
    public String getGzdw() {
		return gzdw;
	}
    /**
     * 
     * @param gzdw 传入工作单位
     */
	public void setGzdw(String gzdw) {
		this.gzdw = gzdw;
	}
	/**
	 * 
	 * @return 文化程度
	 */
	public String getWhcd() {
		return whcd;
	}
	/**
	 * 
	 * @param whcd 文化程度
	 */
	public void setWhcd(String whcd) {
		this.whcd = whcd;
	}
	/**
     * 
     * @return 人员id
     */
	public String getId() {
		return id;
	}
	/**
	 * 
	 * @param id 传入人员id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 
	 * @return 单位编码
	 */
	public String getDwbm() {
		return dwbm;
	}
	/**
	 * 
	 * @param dwbm 传入单位编码
	 */
	public void setDwbm(String dwbm) {
		this.dwbm = dwbm;
	}
	/**
	 * 
	 * @return 部门编码
	 */
	public String getBmbm() {
		return bmbm;
	}
	/**
	 * 
	 * @param bmbm 传入部门编码
	 */
	public void setBmbm(String bmbm) {
		this.bmbm = bmbm;
	}
	/**
	 * 
	 * @return 性别
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * 
	 * @param gender 传入性别
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * 
	 * @return 民族
	 */
	public String getNation() {
		return nation;
	}
	/**
	 * 
	 * @param nation 传入民族
	 */
	public void setNation(String nation) {
		this.nation = nation;
	}
	/**
	 * 
	 * @return 政治面貌
	 */
	public String getZzmm() {
		return zzmm;
	}
	/**
	 *  
	 * @param zzmm 传入政治面貌
	 */
	public void setZzmm(String zzmm) {
		this.zzmm = zzmm;
	}
	/**
	 * 
	 * @return 证件类型
	 */
	public String getZjlx() {
		return zjlx;
	}
	/**
	 * 
	 * @param zjlx 传入证件类型
	 */
	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}
	/**
	 * 
	 * @return 证件号码
	 */
	public String getZjhm() {
		return zjhm;
	}
	/**
	 * 
	 * @param zjhm 传入证件号码
	 */
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	/**
	 * 
	 * @return 籍贯
	 */
	public String getJg() {
		return jg;
	}
	/**
	 * 
	 * @param jg 传入籍贯
	 */
	public void setJg(String jg) {
		this.jg = jg;
	}
	/**
	 * 
	 * @return 现住址
	 */
	public String getXzz() {
		return xzz;
	}
	/**
	 * 
	 * @param xzz 传入现住址
	 */
	public void setXzz(String xzz) {
		this.xzz = xzz;
	}
	/**
	 * 
	 * @return 家庭住址
	 */
	public String getJtzz() {
		return jtzz;
	}
	/**
	 * 
	 * @param jtzz 传入家庭住址
	 */
	public void setJtzz(String jtzz) {
		this.jtzz = jtzz;
	}
	/**
	 * 
	 * @return 电话号码/联系方式
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 
	 * @param phone 传入电话号码/联系方式 
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 
	 * @return 座机
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * 
	 * @param telephone 传入座机
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * 
	 * @return 电子邮箱
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 
	 * @param email 传入电子邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
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

	public String getAdminRank() {
		return adminRank;
	}

	public void setAdminRank(String adminRank) {
		this.adminRank = adminRank;
	}

	public String getReDate() {
		return reDate;
	}

	public void setReDate(String reDate) {
		this.reDate = reDate;
	}
	
	public String getXw() {
		return xw;
	}

	public void setXw(String xw) {
		this.xw = xw;
	}

	@Override
	public String toString() {
		return "Person [dwbm=" + dwbm + ", bmbm=" + bmbm + ", gh=" + gh + ", mc=" + mc + ", gender=" + gender
				+ ", birthday=" + birthday + ", whcd=" + whcd + ", xw=" + xw + ", sflb=" + sflb + ", grade=" + grade
				+ ", postInfo=" + postInfo + ", reDate=" + reDate + ", adminRank=" + adminRank + ", zzmm=" + zzmm
				+ ", id=" + id + ", nation=" + nation + ", zjlx=" + zjlx + ", zjhm=" + zjhm + ", jg=" + jg + ", xzz="
				+ xzz + ", jtzz=" + jtzz + ", phone=" + phone + ", telephone=" + telephone + ", email=" + email
				+ ", gzdw=" + gzdw + ", daId=" + daId + ", zp=" + zp + ", zpName=" + zpName + "]";
	}

}
