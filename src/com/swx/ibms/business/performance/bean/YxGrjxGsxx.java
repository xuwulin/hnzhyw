package com.swx.ibms.business.performance.bean;

import java.util.Date;

/**
 * 个人绩效-公示信息表
 * 
 * @author admin
 * 
 * @date 2018-11-19
 */
public class YxGrjxGsxx {
    /**
     * 主键id
     */
    private String id;

    /**
     * 外键，绩效考核id
     */
    private String jxkhid;

    /**
     * 说明/描述
     */
    private String remarks;

    /**
     * 公示开始日期，格式“yyyy-MM-dd hh:mm:ss”
     */
    private Date ksrq;

    /**
     * 公示结束日期，格式“yyyy-MM-dd hh:mm:ss”
     */
    private Date jsrq;

    /**
     * 创建者，单位编码+工号，eg:“dwbm_gh”
     */
    private String createby;

    /**
     * 创建日期，格式“yyyy-MM-dd hh:mm:ss”
     */
    private Date createdate;

    /**
     * 更新者
     */
    private String updateby;

    /**
     * 更新日期
     */
    private Date updatedate;

    /**
     * 删除标识，默认为N（未删除）
     */
    private String delflag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getJxkhid() {
        return jxkhid;
    }

    public void setJxkhid(String jxkhid) {
        this.jxkhid = jxkhid == null ? null : jxkhid.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Date getKsrq() {
        return ksrq;
    }

    public void setKsrq(Date ksrq) {
        this.ksrq = ksrq;
    }

    public Date getJsrq() {
        return jsrq;
    }

    public void setJsrq(Date jsrq) {
        this.jsrq = jsrq;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby == null ? null : createby.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby == null ? null : updateby.trim();
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
    }

	@Override
	public String toString() {
		return "YxGrjxGsxx [id=" + id + ", jxkhid=" + jxkhid + ", remarks=" + remarks + ", ksrq=" + ksrq + ", jsrq="
				+ jsrq + ", createby=" + createby + ", createdate=" + createdate + ", updateby=" + updateby
				+ ", updatedate=" + updatedate + ", delflag=" + delflag + "]";
	}
    
}