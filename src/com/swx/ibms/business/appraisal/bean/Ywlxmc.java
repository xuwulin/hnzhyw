package com.swx.ibms.business.appraisal.bean;

import java.io.Serializable;

/**
 * 返回业务类型及业务名称
 * @author 王宇锋
 */
public class Ywlxmc implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 业务类型
	 */
	private String ywlx;
	/**
	 * 业务名称
	 */
	private String ywmc;
	/**
	 * @return 业务类型
	 */
	public String getYwlx() {
		return ywlx;
	}
	/**
	 * @param ywlx 业务类型
	 */
	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}
	/**
	 * @return 业务名称
	 */
	public String getYwmc() {
		return ywmc;
	}
	/**
	 * @param ywmc 业务名称
	 */
	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}
	@Override
	public String toString() {
		return "Ywlxmc [ywlx=" + ywlx + ", ywmc=" + ywmc + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ywlx == null) ? 0 : ywlx.hashCode());
		result = prime * result + ((ywmc == null) ? 0 : ywmc.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ywlxmc other = (Ywlxmc) obj;
		if (ywlx == null) {
			if (other.ywlx != null)
				return false;
		} else if (!ywlx.equals(other.ywlx))
			return false;
		if (ywmc == null) {
			if (other.ywmc != null)
				return false;
		} else if (!ywmc.equals(other.ywmc))
			return false;
		return true;
	}
}
