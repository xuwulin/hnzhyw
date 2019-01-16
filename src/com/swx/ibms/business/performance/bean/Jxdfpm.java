package com.swx.ibms.business.performance.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author 王宇锋
 */
public class Jxdfpm {
	/**
	 * 名称
	 */
	private String mc;
	/**
	 * 绩效总分
	 */
	private String jxzf;
	/**
	 * 业务简称
	 */
	private String ywjc;
	/**
	 * 获取名称
	 * @return 名称
 	 */
	public String getMc() {
		return mc;
	}
	/**
	 * 设置名称
	 * @param mc 名称
	 */
	public void setMc(String mc) {
		this.mc = mc;
	}
	/**
	 * 获取绩效总分 
	 * @return 绩效总分
	 */
	public String getJxzf() {
		return jxzf;
	}
	/**
	 * 设置绩效总分
	 * @param jxzf 绩效总分
	 */
	public void setJxzf(String jxzf) {
		this.jxzf = new BigDecimal(Double.parseDouble(jxzf)).setScale(2,RoundingMode.UP).toString();
	}
	
	public String getYwjc() {
		return ywjc;
	}
	public void setYwjc(String ywjc) {
		this.ywjc = ywjc;
	}
	@Override
	public String toString() {
		return "Jxdfpm [mc=" + mc + ", jxzf=" + jxzf + "]";
	}
}
