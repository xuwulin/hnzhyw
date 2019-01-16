/**
 * 
 */
package com.swx.ibms.business.cases.bean;

import java.sql.Date;

/**
 * 检察官档案创建案件嫌疑人基本信息
 * @author 封泽超
 *@since 2017年2月28日
 */
public class DACJAJXYRXX {
	/**
	 * 部门受案号
	 */
	private String bmsah;   //部门受案号
	/**
	 * 统一受案号
	 */
	private String tysah;	//统一受案号
	/**
	 * 嫌疑人编号
	 */
	private String xyrbh;   //嫌疑人编号
	/**
	 * 姓名
	 */
	private String xm;		//姓名
	/**
	 * 性别
	 */
	private String xb;		//性别
	/**
	 * 名族
	 */
	private String mz_mc;		//民族
	/**
	 * 出生日期
	 */
	private String csrq;	//出生日期
	/**
	 * 户籍所在地
	 */
	private String hjszd_mc; //户籍所在地
	/**
	 * 证件类型
	 */
	private String zjlx_mc; //证件类型
	/**
	 * 证件号码
	 */
	private String zjhm;	//证件号码
	/**
	 * 曾用名
	 */
	private String cym; //曾用名
	/**
	 * 绰号
	 */
	private String ch; //绰号
	/**
	 * 作案时年龄
	 */
	private int zasnl;//作案时年龄
	/**
	 * 年龄
	 */
	private int nl;//年龄
	/**
	 * 出生地
	 */
	private String csd_mc;//出生地
	/**
	 * 国籍
	 */
	private String gj_mc;//国籍
	/**
	 * 住所地
	 */
	private String zsd_mc;//住所地
	/**
	 * 住所地详细地址
	 */
	private String zsdxxdz;//住所地详细地址
	/**
	 * 工作单位所在地
	 */
	private String gzdwszd_mc;//工作单位所在地
	/**
	 * 工作单位
	 */
	private String gzdw;//工作单位
	/**
	 * 身份
	 */
	private String sf_mc;//身份
	/**
	 * 受教育情况
	 */
	private String sjyzk_mc;//受教育状况
	/**
	 * 政治面貌
	 */
	private String zzmc_mc;//政治面貌
	/**
	 * 职务
	 */
	private String zw;//职务
	/**
	 * 职级
	 */
	private String zj;//职级
	/**
	 * 职业
	 */
	private String zy_mc;//职业
	/**
	 * 是否担任实职
	 */
	private String sfdrsz;//是否担任实职
	/**
	 * 是否单位领导
	 */
	private String sfdwld;//是否单位领导
	/**
	 * 是否农村基层组织人员
	 */
	private String sfncjczzry;//是否农村基层组织人员
	/**
	 * 是否党委政府领导
	 */
	private String sfdwzfld;//是否党委政府领导
	/**
	 * 是否为两级以上人大代表
	 */
	private String sfwljysrddb;//是否为两级以上人大代表
	/**
	 * 是否为两次以上政协委员
	 */
	private String sfwljyszxwy;//是否为两级以上政协委员
	/**
	 * 法定代表人
	 */
	private String fddlr;//法定代理人
	/**
	 * 是否主犯
	 */
	private String sfzf;//是否主犯
	/**
	 * 是否在逃
	 */
	private String sfzt;//是否在逃
	/**
	 * 羁押开始日期
	 */
	private String jyksrq;//羁押开始日期
	/**
	 * 羁押到期日期
	 */
	private String jydqrq;//羁押到期日期
	/**
	 * 羁押预警状态
	 */
	private String jyyjzt;//羁押预警状态
	/**
	 * 羁押天数
	 */
	private int jyts;//羁押天数
	public String getBmsah() {
		return bmsah;
	}
	public void setBmsah(String bmsah) {
		this.bmsah = bmsah;
	}
	public String getTysah() {
		return tysah;
	}
	public void setTysah(String tysah) {
		this.tysah = tysah;
	}
	public String getXyrbh() {
		return xyrbh;
	}
	public void setXyrbh(String xyrbh) {
		this.xyrbh = xyrbh;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getXb() {
		return xb;
	}
	public void setXb(String xb) {
		this.xb = xb;
	}
	public String getCsrq() {
		return csrq.toString();
	}
	public void setCsrq(Date csrq) {
		this.csrq = csrq.toString();;
	}
	public String getHjszd_mc() {
		return hjszd_mc;
	}
	public void setHjszd_mc(String hjszd_mc) {
		this.hjszd_mc = hjszd_mc;
	}
	public String getZjlx_mc() {
		return zjlx_mc;
	}
	public void setZjlx_mc(String zjlx_mc) {
		this.zjlx_mc = zjlx_mc;
	}
	public String getZjhm() {
		return zjhm;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public String getCym() {
		return cym;
	}
	public void setCym(String cym) {
		this.cym = cym;
	}
	public String getCh() {
		return ch;
	}
	public void setCh(String ch) {
		this.ch = ch;
	}
	public int getZasnl() {
		return zasnl;
	}
	public void setZasnl(int zasnl) {
		this.zasnl = zasnl;
	}
	public int getNl() {
		return nl;
	}
	public void setNl(int nl) {
		this.nl = nl;
	}
	public String getCsd_mc() {
		return csd_mc;
	}
	public void setCsd_mc(String csd_mc) {
		this.csd_mc = csd_mc;
	}
	public String getGj_mc() {
		return gj_mc;
	}
	public void setGj_mc(String gj_mc) {
		this.gj_mc = gj_mc;
	}
	public String getZsd_mc() {
		return zsd_mc;
	}
	public void setZsd_mc(String zsd_mc) {
		this.zsd_mc = zsd_mc;
	}
	public String getZsdxxdz() {
		return zsdxxdz;
	}
	public void setZsdxxdz(String zsdxxdz) {
		this.zsdxxdz = zsdxxdz;
	}
	public String getGzdwszd_mc() {
		return gzdwszd_mc;
	}
	public void setGzdwszd_mc(String gzdwszd_mc) {
		this.gzdwszd_mc = gzdwszd_mc;
	}
	public String getGzdw() {
		return gzdw;
	}
	public void setGzdw(String gzdw) {
		this.gzdw = gzdw;
	}
	public String getSf_mc() {
		return sf_mc;
	}
	public void setSf_mc(String sf_mc) {
		this.sf_mc = sf_mc;
	}
	public String getSjyzk_mc() {
		return sjyzk_mc;
	}
	public void setSjyzk_mc(String sjyzk_mc) {
		this.sjyzk_mc = sjyzk_mc;
	}
	public String getZzmc_mc() {
		return zzmc_mc;
	}
	public void setZzmc_mc(String zzmc_mc) {
		this.zzmc_mc = zzmc_mc;
	}
	public String getZw() {
		return zw;
	}
	public void setZw(String zw) {
		this.zw = zw;
	}
	public String getZj() {
		return zj;
	}
	public void setZj(String zj) {
		this.zj = zj;
	}
	public String getZy_mc() {
		return zy_mc;
	}
	public void setZy_mc(String zy_mc) {
		this.zy_mc = zy_mc;
	}
	public String getSfdrsz() {
		return sfdrsz;
	}
	public void setSfdrsz(String sfdrsz) {
		this.sfdrsz = sfdrsz;
	}
	public String getSfdwld() {
		return sfdwld;
	}
	public void setSfdwld(String sfdwld) {
		this.sfdwld = sfdwld;
	}
	public String getSfncjczzry() {
		return sfncjczzry;
	}
	public void setSfncjczzry(String sfncjczzry) {
		this.sfncjczzry = sfncjczzry;
	}
	public String getSfdwzfld() {
		return sfdwzfld;
	}
	public void setSfdwzfld(String sfdwzfld) {
		this.sfdwzfld = sfdwzfld;
	}
	public String getSfwljysrddb() {
		return sfwljysrddb;
	}
	public void setSfwljysrddb(String sfwljysrddb) {
		this.sfwljysrddb = sfwljysrddb;
	}
	public String getSfwljyszxwy() {
		return sfwljyszxwy;
	}
	public void setSfwljyszxwy(String sfwljyszxwy) {
		this.sfwljyszxwy = sfwljyszxwy;
	}
	public String getFddlr() {
		return fddlr;
	}
	public void setFddlr(String fddlr) {
		this.fddlr = fddlr;
	}
	public String getSfzf() {
		return sfzf;
	}
	public void setSfzf(String sfzf) {
		this.sfzf = sfzf;
	}
	public String getSfzt() {
		return sfzt;
	}
	public void setSfzt(String sfzt) {
		this.sfzt = sfzt;
	}
	public String getJyksrq() {
		return jyksrq.toString();
	}
	public void setJyksrq(Date jyksrq) {
		this.jyksrq = jyksrq.toString();
	}
	public String getJydqrq() {
		return jydqrq.toString();
	}
	public void setJydqrq(Date jydqrq) {
		this.jydqrq = jydqrq.toString();
	}
	public String getJyyjzt() {
		return jyyjzt;
	}
	public void setJyyjzt(String jyyjzt) {
		this.jyyjzt = jyyjzt;
	}
	public int getJyts() {
		return jyts;
	}
	public void setJyts(int jyts) {
		this.jyts = jyts;
	}

	
}
