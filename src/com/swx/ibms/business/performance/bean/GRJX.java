package com.swx.ibms.business.performance.bean;

/**
 * 个人绩效实体类
 * @author 李治鑫
 * @since 2017-5-9
 */
public class GRJX {
	/**
	 * 档案ID
	 */
	private String daid;
	/**
	 * 业务类型
	 */
	private String ywlx;
	/**
	 * 业务总分
	 */
	private double ywzf;
	/**
	 * 指标考评
	 */
	private String zbkpgl;
	
	/**
	 * 无参构造函数
	 */
	public GRJX(){}


	/**
	 * 获取档案ID
	 * @return 档案ID
	 */
	public String getDaid() {
		return daid;
	}

	/**
	 * 设置档案ID
	 * @param daid 档案ID
	 */
	public void setDaid(String daid) {
		this.daid = daid;
	}

	/**
	 * 获取业务类型
	 * @return 业务类型
	 */
	public String getYwlx() {
		return ywlx;
	}

	/**
	 * 设置业务类型
	 * @param ywlx 业务类型
	 */
	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}

	/**
	 * 获取业务总分
	 * @return 业务总分
	 */
	public double getYwzf() {
		return ywzf;
	}

	/**
	 * 设置业务总分
	 * @param ywzf 业务总分
	 */
	public void setYwzf(double ywzf) {
		this.ywzf = ywzf;
	}

	/**
	 * 获取指标考评概览
	 * @return 指标考评概览
	 */
	public String getZbkpgl() {
		return zbkpgl;
	}

	/**
	 * 设置指标考评概览
	 * @param zbkpgl 指标考评概览
 	 */
	public void setZbkpgl(String zbkpgl) {
		this.zbkpgl = zbkpgl;
	}
	
	
}
