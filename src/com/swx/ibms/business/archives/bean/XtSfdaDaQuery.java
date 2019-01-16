package com.swx.ibms.business.archives.bean;

public class XtSfdaDaQuery {
	private int page;
	private int rows;
	private String gdid;
	private String dalx;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getGdid() {
		return gdid;
	}
	public void setGdid(String gdid) {
		this.gdid = gdid;
	}
	public String getDalx() {
		return dalx;
	}
	public void setDalx(String dalx) {
		this.dalx = dalx;
	}
	@Override
	public String toString() {
		return "XtSfdaDaQuery [page=" + page + ", rows=" + rows + ", gdid=" + gdid + ", dalx=" + dalx + "]";
	}
	
}
