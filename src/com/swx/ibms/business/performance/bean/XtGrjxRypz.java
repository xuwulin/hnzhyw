package com.swx.ibms.business.performance.bean;

import java.util.Date;

/**
 * null
 * 
 * @author admin
 * 
 * @date 2018-09-07
 */
public class XtGrjxRypz {
    /**
     * 主键id
     */
    private String id;

    /**
     * 人员类型id,外键
     */
    private String typeid;

    /**
     * 单位编码
     */
    private String rydwbm;

    /**
     * 工号
     */
    private String rygh;

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
     * 说明/描述
     */
    private String remarks;

    /**
     * 排序的序号，默认数字0
     */
    private Integer xh;

    /**
     * 删除标识[默认为'N']
     */
    private String delflag;

    /**
     * 部门类别名称
     */
    private String bmlbmc;

    /**
     * 部门类别编码
     */
    private String bmlbbm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid == null ? null : typeid.trim();
    }

    public String getRydwbm() {
        return rydwbm;
    }

    public void setRydwbm(String rydwbm) {
        this.rydwbm = rydwbm == null ? null : rydwbm.trim();
    }

    public String getRygh() {
        return rygh;
    }

    public void setRygh(String rygh) {
        this.rygh = rygh == null ? null : rygh.trim();
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
    }

    public String getBmlbmc() {
        return bmlbmc;
    }

    public void setBmlbmc(String bmlbmc) {
        this.bmlbmc = bmlbmc;
    }

    public String getBmlbbm() {
        return bmlbbm;
    }

    public void setBmlbbm(String bmlbbm) {
        this.bmlbbm = bmlbbm;
    }
}