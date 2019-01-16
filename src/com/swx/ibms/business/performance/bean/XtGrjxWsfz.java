package com.swx.ibms.business.performance.bean;

import java.util.Date;

/**
 * null
 * 
 * @author admin
 * 
 * @date 2018-09-07
 */
public class XtGrjxWsfz {
    /**
     * 主键id
     */
    private String id;

    /**
     * 文书编号
     */
    private String wsNum;

    /**
     * 所属部门
     */
    private String ssbm;

    /**
     * 名称
     */
    private String wsName;

    /**
     * 分值
     */
    private double fz;

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
     * 删除标识
     */
    private String delflag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getWsNum() {
        return wsNum;
    }

    public void setWsNum(String wsNum) {
        this.wsNum = wsNum == null ? null : wsNum.trim();
    }

    public String getSsbm() {
        return ssbm;
    }

    public void setSsbm(String ssbm) {
        this.ssbm = ssbm == null ? null : ssbm.trim();
    }

    public String getWsName() {
        return wsName;
    }

    public void setWsName(String wsName) {
        this.wsName = wsName == null ? null : wsName.trim();
    }

    public double getFz() {
        return fz;
    }

    public void setFz(double fz) {
        this.fz = fz;
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
}