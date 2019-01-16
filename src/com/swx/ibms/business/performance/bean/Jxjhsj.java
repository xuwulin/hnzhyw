package com.swx.ibms.business.performance.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author 王宇锋
 */
public class Jxjhsj {
	/**
	 * 业务类型中文形似 
	 */
	private String ywlx;
	
	/**
	 * 对应业务最大值
	 */
	private String jxmax;
	
	/**
	 * 对应业务平均值
	 */
	private String jxavg;
	
	/**
	 * 对应业务最小值
	 */
	private String jxmin;
	
	/**
	 * 
	 * @return 业务类型中文形似 
	 */
	public String getYwlx() {
		return ywlx;
	}
	/**
	 * 
	 * @param ywlx 业务类型中文形似 
	 */
	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}
	/**
	 * 
	 * @return 对应业务最大值
	 */
	public String getJxmax() {
		return jxmax;
	}
	/**
	 * 
	 * @param jxmax 对应业务最大值
	 */
	public void setJxmax(String jxmax) {
		this.jxmax = new BigDecimal(Double.parseDouble(jxmax)).setScale(2,RoundingMode.UP).toString();
	}
	/**
	 *  
	 * @return 对应业务平均值
	 */
	public String getJxavg() {
		return jxavg;
	}
	/**
	 * 
	 * @param jxavg 对应业务平均值
	 */
	public void setJxavg(String jxavg) {
		this.jxavg =new BigDecimal(Double.parseDouble(jxavg)).setScale(2,RoundingMode.UP).toString();
	}
	/**
	 * 
	 * @return 对应业务最小值
	 */
	public String getJxmin() {
		return jxmin;
	}
	/**
	 * 
	 * @param jxmin 对应业务最小值
	 */
	public void setJxmin(String jxmin) {
		this.jxmin = new BigDecimal(Double.parseDouble(jxmin)).setScale(2,RoundingMode.UP).toString();
	}
	@Override
	public String toString() {
		return "Jxjhsj [ywlx=" + ywlx + ", jxmax=" + jxmax + ","
				+ " jxavg=" + jxavg + ", jxmin=" + jxmin + "]";
	}
}
