package com.swx.ibms.business.appraisal.bean;

/**
 * Created by Try on 2017/11/1.
 */
public class Ywkhkhdw {
    /**
     * 单位编码
     */
    private String dwbm;
    /**
     * 单位名称
     */
    private String dwmc;
    /**
     * 父单位编码
     */
    private String fdwbm;
    /**
     * 单位级别
     */
    private String dwjb;
    /**
     * 单位级别
     */
    private String dwjc;
    /**
     * 是否删除 'Y'已删除 'N'未删除
     */
    private String sfsc;

    public String getDwbm() {
        return dwbm;
    }

    public void setDwbm(String dwbm) {
        this.dwbm = dwbm;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public String getFdwbm() {
        return fdwbm;
    }

    public void setFdwbm(String fdwbm) {
        this.fdwbm = fdwbm;
    }

    public String getDwjb() {
        return dwjb;
    }

    public void setDwjb(String dwjb) {
        this.dwjb = dwjb;
    }

    public String getDwjc() {
        return dwjc;
    }

    public void setDwjc(String dwjc) {
        this.dwjc = dwjc;
    }

    public String getSfsc() {
        return sfsc;
    }

    public void setSfsc(String sfsc) {
        this.sfsc = sfsc;
    }

    @Override
    public String toString() {
        return "Ywkhkhdw{" +
                "dwbm='" + dwbm + '\'' +
                ", dwmc='" + dwmc + '\'' +
                ", fdwbm='" + fdwbm + '\'' +
                ", dwjb='" + dwjb + '\'' +
                ", dwjc='" + dwjc + '\'' +
                ", sfsc=" + sfsc +
                '}';
    }
}
