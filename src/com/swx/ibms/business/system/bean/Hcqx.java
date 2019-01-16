package com.swx.ibms.business.system.bean;

import java.sql.Date;

/**
 * @author zsq
 * 司法档案核查期限
 */
public class Hcqx {
	
	/**
	 * 核查期限
	 */
	private  int hcqx;
	
	/**
	 * 期限类型 1：档案核查期限 2 公示期限
	 */
	private  int qxlx;
	
	/**
	 * 更新人
	 */
	private String gxr;
	
	/**
	 * 更新时间
	 */
	private Date gxsj;

	/**
	 * @return hcqx
	 */
	public int getHcqx() {
		return hcqx;
	}

	/**
	 * @param hcqx hcqx
	 */
	public void setHcqx(int hcqx) {
		this.hcqx = hcqx;
	}

	/**
	 * @return gxr
	 */
	public String getGxr() {
		return gxr;
	}

	/**
	 * @param gxr gxr
	 */
	public void setGxr(String gxr) {
		this.gxr = gxr;
	}

	/**
	 * @return gxsj
	 */
	public Date getGxsj() {
		return gxsj;
	}

	/**
	 * @param gxsj gxsj
	 */
	public void setGxsj(Date gxsj) {
		this.gxsj = gxsj;
	}

	/**
	 * @return qxlx
	 */
	public int getQxlx() {
		return qxlx;
	}

	/**
	 * @param qxlx qxlx
	 */
	public void setQxlx(int qxlx) {
		this.qxlx = qxlx;
	}
	
	
}
