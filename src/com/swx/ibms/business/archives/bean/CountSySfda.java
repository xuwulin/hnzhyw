package com.swx.ibms.business.archives.bean;
/**
 * @author 李佳
 * 统计档案内容个数实体类 <br/>
 * 主要属性：档案类别，档案数量
 */
public class CountSySfda {

	/**
	 * 档案类别
	 */
	private String lb;
	/**
	 * 档案数量
	 */
	private int sl;
	/**
	 * @return 档案类别
	 */
	public String getLb() {
		return lb;
	}
	/**
	 * @param lb 传入档案类别
	 */
	public void setLb(String lb) {
		this.lb = lb;
	}
	/**
	 * @return 档案数量
	 */
	public int getSl() {
		return sl;
	}
	/**
	 * @param sl 传入档案数量
	 */
	public void setSl(int sl) {
		this.sl = sl;
	}
}
