package com.swx.ibms.business.performance.bean;

import java.util.Date;

/**
 * 个人绩效案件统计
 * 
 * @author wcyong
 * 
 * @date 2018-10-24
 */
public class YxGrjxAjtj {
    /**
     * id
     */
    private String id;

    /**
     * 考核id
     */
    private String khid;

    /**
     * 考核分值id
     */
    private String khfzid;

    /**
     * 子项目编码
     */
    private String zxmbm;

    /**
     * 部门受案号
     */
    private String bmsah;

    /**
     * 统一受案号
     */
    private String tysah;

    /**
     * 案件名称
     */
    private String ajmc;

    /**
     * 案件类别名称
     */
    private String ajlbmc;

    /**
     * 承办人
     */
    private String cbr;

    /**
     * 承办部门
     */
    private String cbbm;

    /**
     * 受理日期
     */
    private Date slrq;

    /**
     * 完成日期
     */
    private Date wcrq;

    /**
     * 办结日期
     */
    private Date bjrq;

    /**
     * 创建时间
     */
    private Date createdate;

    /**
     * 更新时间
     */
    private Date updatedate;

    /**
     * 说明
     */
    private String remarks;

    /**
     * 序号
     */
    private Short xh;

    /**
     * 状态
     */
    private String status;

    /**
     * 案情摘要

     */
    private String aqzy;

    public String getAqzy() {
        return aqzy;
    }

    public void setAqzy(String aqzy) {
        this.aqzy = aqzy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getKhid() {
        return khid;
    }

    public void setKhid(String khid) {
        this.khid = khid == null ? null : khid.trim();
    }

    public String getKhfzid() {
        return khfzid;
    }

    public void setKhfzid(String khfzid) {
        this.khfzid = khfzid == null ? null : khfzid.trim();
    }

    public String getZxmbm() {
        return zxmbm;
    }

    public void setZxmbm(String zxmbm) {
        this.zxmbm = zxmbm == null ? null : zxmbm.trim();
    }

    public String getBmsah() {
        return bmsah;
    }

    public void setBmsah(String bmsah) {
        this.bmsah = bmsah == null ? null : bmsah.trim();
    }

    public String getTysah() {
        return tysah;
    }

    public void setTysah(String tysah) {
        this.tysah = tysah == null ? null : tysah.trim();
    }

    public String getAjmc() {
        return ajmc;
    }

    public void setAjmc(String ajmc) {
        this.ajmc = ajmc == null ? null : ajmc.trim();
    }

    public String getAjlbmc() {
        return ajlbmc;
    }

    public void setAjlbmc(String ajlbmc) {
        this.ajlbmc = ajlbmc == null ? null : ajlbmc.trim();
    }

    public String getCbr() {
        return cbr;
    }

    public void setCbr(String cbr) {
        this.cbr = cbr == null ? null : cbr.trim();
    }

    public String getCbbm() {
        return cbbm;
    }

    public void setCbbm(String cbbm) {
        this.cbbm = cbbm == null ? null : cbbm.trim();
    }

    public Date getSlrq() {
        return slrq;
    }

    public void setSlrq(Date slrq) {
        this.slrq = slrq;
    }

    public Date getWcrq() {
        return wcrq;
    }

    public void setWcrq(Date wcrq) {
        this.wcrq = wcrq;
    }

    public Date getBjrq() {
        return bjrq;
    }

    public void setBjrq(Date bjrq) {
        this.bjrq = bjrq;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
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

    public Short getXh() {
        return xh;
    }

    public void setXh(Short xh) {
        this.xh = xh;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}