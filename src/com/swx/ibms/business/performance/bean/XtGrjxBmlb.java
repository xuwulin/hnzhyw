package com.swx.ibms.business.performance.bean;

import java.util.Date;

/**
 * Created by xuwl on 2018/9/13.
 */
public class XtGrjxBmlb {

    /**
     * 主键id
     */
    private String id;

    /**
     * 部门类别名称
     */
    private String bmlbmc;

    /**
     * 部门类别编码
     */
    private String bmlbbm;

    /**
     * 创建者，单位编码+工号，eg:“dwbm_gh"
     */
    private String createby;

    /**
     * 创建日期，格式“yyyy-MM-dd hh:mm:ss”
     */
    private String createdate;

    /**
     * 更新者
     */
    private String updateby;

    /**
     * 更新日期
     */
    private String updatedate;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }



    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
        this.delflag = delflag;
    }
}
