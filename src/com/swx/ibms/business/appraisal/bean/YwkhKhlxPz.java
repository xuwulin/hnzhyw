package com.swx.ibms.business.appraisal.bean;

/**
 * Created by Try on 2017/11/6.
 * 业务考核模块的考核类型实体类
 */
public class YwkhKhlxPz {
    /**
     * 考核类型id
     */
    private String id;
    /**
     * 考核类型
     */
    private String khlx;
    /**
     * 业务类型
     */
    private String ywlx;
    /**
     * 考核名称
     */
    private String khmc;
    /**
     * 业务名称
     */
    private String ywmc;
	/**
     * 业务编码
     */
    private String ywbm;
    /**
     * 业务简称
     */
    private String ywjc;
    /**
     * 权重值，单位为%，存值为数字
     */
    private String qz;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKhlx() {
		return khlx;
	}
	public void setKhlx(String khlx) {
		this.khlx = khlx;
	}
	public String getYwlx() {
		return ywlx;
	}
	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}
	public String getKhmc() {
		return khmc;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public String getYwmc() {
		return ywmc;
	}
	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}
	public String getYwbm() {
		return ywbm;
	}
	public void setYwbm(String ywbm) {
		this.ywbm = ywbm;
	}
	public String getYwjc() {
		return ywjc;
	}
	public void setYwjc(String ywjc) {
		this.ywjc = ywjc;
	}
	public String getQz() {
		return qz;
	}
	public void setQz(String qz) {
		this.qz = qz;
	}
	
	@Override
	public String toString() {
		return "YwkhKhlxPz [id=" + id + ", khlx=" + khlx + ", ywlx=" + ywlx + ", khmc=" + khmc + ", ywmc=" + ywmc
				+ ", ywbm=" + ywbm + ", ywjc=" + ywjc + ", qz=" + qz + "]";
	}
    
}
