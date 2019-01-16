package com.swx.ibms.business.appraisal.bean;

/**
 * 个人绩效-考核分值实体类
 * @author 李佳 
 * @since 2017-5-9
 */
public class Ywkhfz {
	/**
	 * 数据id
	 */
	private String id;       
	/**
	 * 月度考核id
	 */
	private String ydkhid;   
	/**
	 * 业务类型
	 */
	private String ywlx;     
	/**
	 * 业务总分
	 */
	private double ywzf;     
	/**
	 * 指标考评概览
	 */
	private String zbkpgl;   
	/**
	 * 指标考评得分
	 */
	private String zbkpdf;   

	/**
	 * 无参构造函数
	 */
	public Ywkhfz(){}
	
//	public Ywkhfz(String id, String ydkhid, String ywlx, double ywzf, String zbkpgl) {
//		this.id = id;
//		this.ydkhid = ydkhid;
//		this.ywlx = ywlx;
//		this.ywzf = ywzf;
//		this.zbkpgl = zbkpgl;
//	}

	/**
	 * 获取编号
	 * @return 编号
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置编号
	 * @param id 编号
  	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取月度考核ID
	 * @return 月度考核ID
	 */
	public String getYdkhid() {
		return ydkhid;
	}

	/**
	 * 设置月度考核ID
	 * @param ydkhid 月度考核ID
	 */
	public void setYdkhid(String ydkhid) {
		this.ydkhid = ydkhid;
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

	/**
	 * 获取指标考评得分
	 * @return 指标考评得分
	 */
	public String getZbkpdf() {
		return zbkpdf;
	}

	/**
	 * 设置指标考评得分
	 * @param zbkpdf 指标考评得分
	 */
	public void setZbkpdf(String zbkpdf) {
		this.zbkpdf = zbkpdf;
	}
	
	
}
