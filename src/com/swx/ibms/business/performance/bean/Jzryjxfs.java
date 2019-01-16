package com.swx.ibms.business.performance.bean;


import com.swx.ibms.business.appraisal.bean.Ywlxmc;

import java.io.Serializable;
import java.util.List;

/**
 * 加载时人员名称绩效总分实体类
 * @author 王宇锋
 *
 */
/**
 * @author dell
 *
 */
public class Jzryjxfs implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 月度考核id
	 */
	private String ydkhid;
	/**
	 * 单位编码
	 */
	private String dwbm;
	/**
	 * 单位名称
	 */
	private String dwmc;
	/**
	 * 工号
	 */
	private String gh;
	/**
	 * 人员名称
	 */
	private String mc;
	/**
	 * 月度考核总分
	 */
	private String ydkhzf;
	/**
	 * 年份
	 */
	private String year;
	/**
	 * 季度
	 */
	private String jd;
	
	//默认加载业务类型数最小对应业务类型,业务总分,指标考评得分
	/**
	 * 相应业务总分
	 */
	private Double ywzf;
	/**
	 * 指标考评得分
	 */
	private String zbkpdf;
	/**
	 * 业务类型
	 */
	private String ywlx;
	/**
	 * 总评价得分
	 */
	private Double zpjdf;
	
	/**
	 * 评定级别
	 */
	private String pdjb;
	
	/**
	 * 评定级别名称
	 */
	private String pdjbmc;
	
	/**
	 * 全部业务类型，业务类型名称
	 */
	private List<Ywlxmc> ywlxmcs;//业务类型名称
	/**
	 * 排名
	 */
	private String pm;
	
	/**
	 * 
	 * @return 年份
	 */
	public String getYear() {
		return year;
	}
	/***
	 * 
	 * @param year 年份
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * 
	 * @return 季度
	 */
	public String getJd() {
		return jd;
	}
	/**
	 * 
	 * @param jd 季度
	 */
	public void setJd(String jd) {
		this.jd = jd;
	}
	/**
	 * 
	 * @return 总评价得分
	 */
	public Double getZpjdf() {
		return zpjdf;
	}
	/**
	 * 
	 * @param zpjdf 总评价得分
	 */ 
	public void setZpjdf(Double zpjdf) {
		this.zpjdf = zpjdf;
	}
	
	/**
	 * @return 评定级别名称
	 */
	public String getPdjbmc() {
		return pdjbmc;
	}
	/**
	 * @param pdjbmc 传入评定级别名称
	 */
	public void setPdjbmc(String pdjbmc) {
		this.pdjbmc = pdjbmc;
	}
	/**
	 * @return 评定级别
	 */
	public String getPdjb() {
		return pdjb;
	}
	/**
	 * @param pdjb 传入评定级别
	 */
	public void setPdjb(String pdjb) {
		this.pdjb = pdjb;
	}
	/**
	 * 
	 * @return 相应业务总分
	 */
	public Double getYwzf() {
		return ywzf;
	}
	/**
	 * 
	 * @return 相应业务总分
	 */
	public List<Ywlxmc> getYwlxmcs() {
		return ywlxmcs;
	}
	/**
	 * 
	 * @param ywlxmcs 全部业务类型，业务类型名称
	 */
	public void setYwlxmcs(List<Ywlxmc> ywlxmcs) {
		this.ywlxmcs = ywlxmcs;
	}
	/**
	 * 
	 * @param ywzf 业务总分
	 */
	public void setYwzf(Double ywzf) {
		this.ywzf = ywzf;
	}
	/**
	 * 
	 * @return  指标考评得分
	 */
	public String getZbkpdf() {
		return zbkpdf;
	}
	/**
	 * 
	 * @param zbkpdf 指标考评得分
	 */
	public void setZbkpdf(String zbkpdf) {
		this.zbkpdf = zbkpdf;
	}
	/**
	 * 
	 * @return 业务类型
	 */
	public String getYwlx() {
		return ywlx;
	}
	/**
	 *  
	 * @param ywlx 业务类型
	 */
	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}
	
	/**
	 * 
	 * @return 月度考核id
	 */
	public String getYdkhid() {
		return ydkhid;
	}
	/**
	 * 
	 * @param ydkhid 月度考核id
	 */
	public void setYdkhid(String ydkhid) {
		this.ydkhid = ydkhid;
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
	 * @param dwbm 单位编码
	 */
	public void setDwbm(String dwbm) {
		this.dwbm = dwbm;
	}
	
	/**
	 * @return 单位名称
	 */
	public String getDwmc() {
		return dwmc;
	}
	/**
	 * @param dwmc 返回单位名称
	 */
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}
	/**
	 * 
	 * @return 工号
	 */
	public String getGh() {
		return gh;
	}
	/**
	 * @param gh  工号
	 */
	public void setGh(String gh) {
		this.gh = gh;
	}
	/**
	 * 
	 * @return 人员名称
	 */
	public String getMc() {
		return mc;
	}
	/**
	 * 
	 * @param mc 人员名称
	 */
	public void setMc(String mc) {
		this.mc = mc;
	}
	/**
	 * 
	 * @return 月度考核总分
	 */
	public String getYdkhzf() {
		return ydkhzf;
	}
	/**
	 * 
	 * @param ydkhzf 月度考核总分
	 */
	public void setYdkhzf(String ydkhzf) {
		this.ydkhzf = ydkhzf;
	}
	
	/**
	 * @return 排名
	 */
	public String getPm() {
		return pm;
	}
	/**
	 * @param pm 排名
	 */
	public void setPm(String pm) {
		this.pm = pm;
	}
	@Override
	public String toString() {
		return "Jzryjxfs [ydkhid=" + ydkhid + ", dwbm=" + dwbm + ", gh=" + gh 
				+ ", mc=" + mc + ", ydkhzf=" + ydkhzf
				+ ", year=" + year + ", jd=" + jd + ", ywzf=" + ywzf + ", zbkpdf=" 
				+ zbkpdf + ", ywlx=" + ywlx
				+ ", zpjdf=" + zpjdf + ", ywlxmcs=" + ywlxmcs + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dwbm == null) ? 0 : dwbm.hashCode());
		result = prime * result + ((gh == null) ? 0 : gh.hashCode());
		result = prime * result + ((jd == null) ? 0 : jd.hashCode());
		result = prime * result + ((mc == null) ? 0 : mc.hashCode());
		result = prime * result + ((ydkhid == null) ? 0 : ydkhid.hashCode());
		result = prime * result + ((ydkhzf == null) ? 0 : ydkhzf.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		result = prime * result + ((ywlx == null) ? 0 : ywlx.hashCode());
		result = prime * result + ((ywlxmcs == null) ? 0 : ywlxmcs.hashCode());
		result = prime * result + ((ywzf == null) ? 0 : ywzf.hashCode());
		result = prime * result + ((zbkpdf == null) ? 0 : zbkpdf.hashCode());
		result = prime * result + ((zpjdf == null) ? 0 : zpjdf.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		Jzryjxfs other = (Jzryjxfs) obj;
		if (dwbm == null) {
			if (other.dwbm != null){
				return false;
			}
		} else if (!dwbm.equals(other.dwbm)){
			return false;
		}
		if (gh == null) {
			if (other.gh != null){
				return false;
			}
		} else if (!gh.equals(other.gh)){
			return false;
		}
		if (jd == null) {
			if (other.jd != null){
				return false;
			}
		} else if (!jd.equals(other.jd)){
			return false;
		}
		if (mc == null) {
			if (other.mc != null){
				return false;
			}
		} else if (!mc.equals(other.mc)){
			return false;
		}
		if (ydkhid == null) {
			if (other.ydkhid != null){
				return false;
			}
		} else if (!ydkhid.equals(other.ydkhid)){
			return false;
		}
		if (ydkhzf == null) {
			if (other.ydkhzf != null){
				return false;
			}
		} else if (!ydkhzf.equals(other.ydkhzf)){
			return false;
		}
		if (year == null) {
			if (other.year != null){
				return false;
			}
		} else if (!year.equals(other.year)){
			return false;
		}
		if (ywlx == null) {
			if (other.ywlx != null){
				return false;
			}
		} else if (!ywlx.equals(other.ywlx)){
			return false;
		}
		if (ywlxmcs == null) {
			if (other.ywlxmcs != null){
				return false;
			}
		} else if (!ywlxmcs.equals(other.ywlxmcs)){
			return false;
		}
		if (ywzf == null) {
			if (other.ywzf != null){
				return false;
			}
		} else if (!ywzf.equals(other.ywzf)){
			return false;
		}
		if (zbkpdf == null) {
			if (other.zbkpdf != null){
				return false;
			}
		} else if (!zbkpdf.equals(other.zbkpdf)){
			return false;
		}
		if (zpjdf == null) {
			if (other.zpjdf != null){
				return false;
			}
		} else if (!zpjdf.equals(other.zpjdf)){
			return false;
		}
		return true;
	}
}
