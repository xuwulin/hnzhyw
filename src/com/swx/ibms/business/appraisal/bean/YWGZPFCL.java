package com.swx.ibms.business.appraisal.bean;
/**
*<p>Title:YWGZPFCL</p>
*<p>Description: 业务工作评分材料实体类</p>
*author 朱春雨
*date 2017年6月6日 下午3:41:35
*/
public class YWGZPFCL {
    /**
     * 编号
     */
    private String id;
    /**
     * 业务类型考核分值ID
     */
    private String ywlxkhfzid;
    /**
     * 指标项编码
     */
    private String zbxbm;
    /**
     * 类型 1:部门评分，2:案管评分
     */
    private String lx;
    /**
     * 备注
     */
    private String bz;
    /**
     * 附件编号+附件地址
     */
    private String fjxx;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getYwlxkhfzid() {
		return ywlxkhfzid;
	}
	public void setYwlxkhfzid(String ywlxkhfzid) {
		this.ywlxkhfzid = ywlxkhfzid;
	}
	public String getZbxbm() {
		return zbxbm;
	}
	public void setZbxbm(String zbxbm) {
		this.zbxbm = zbxbm;
	}
	public String getLx() {
		return lx;
	}
	public void setLx(String lx) {
		this.lx = lx;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getFjxx() {
		return fjxx;
	}
	public void setFjxx(String fjxx) {
		this.fjxx = fjxx;
	}
    
}
