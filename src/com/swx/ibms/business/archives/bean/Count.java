package com.swx.ibms.business.archives.bean;

/**
 * 
 * 统计档案内容中4种档案的个数书体类
 * @author 李佳
 * @date<p>2017年5月12日</p>
 * @version 1.0
 * @description<p></p>
 */
public class Count {

	/**
	 * 档案类型
	 */
	private String dalx;
	/**
	 * 总数
	 */
	private String count;
	/**
	 * 
	 * @return 返回档案类型
	 */
	public String getDalx() {
		return dalx;
	}
	/**
	 * 
	 * @param dalx 传入档案类型
	 */
	public void setDalx(String dalx) {
		this.dalx = dalx;
	}
	/**
	 * 
	 * @return 返回总数
	 */
	public String getCount() {
		return count;
	}
	/**
	 * 
	 * @param count 传入总数
	 */ 
	public void setCount(String count) {
		this.count = count;
	}
}
