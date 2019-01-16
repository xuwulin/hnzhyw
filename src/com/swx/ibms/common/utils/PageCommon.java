package com.swx.ibms.common.utils;

import com.swx.ibms.business.common.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * PageCommon<Object>：通用分页实体类
 * @param <Object> 传入具体的实体对象,eg：PageCommon<BMBM> 表示部门的分页
 * @author 何向东
 * @date:2017年4月23日
 * @version:1.0
 * @description:主要属性有：当前页，总页数，总记录数，页面数据结果集，每页显示数
 *
 */
@SuppressWarnings("all")
public class PageCommon<Object> {
	
	/**
	 * 当前页  默认为1
	 */
	private int nowPage = 1;
	
	/**
	 * 每页显示数 默认为10
	 */
	private int pageSize = Constant.NUM_10;
	
	/**
	 * 总页数
	 */
	private int totalPages;
	
	/**
	 * 总记录数
	 */
	private int totalRecords;
	
	/**
	 * 页面数据结果集
	 */
	private List<Object> list = new ArrayList<Object>();
	
	/**
	 * 
	 * @return 当前页
	 */
	public int getNowPage() {
		return nowPage;
	}
	
	/**
	 * 
	 * @param nowPage 传入当前页
	 */
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
	
	/**
	 * 
	 * @return 每页显示数
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	/**
	 * 
	 * @param pageSize 传入每页显示数
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * 
	 * @return 总页数
	 */
	public int getTotalPages() {
		return totalPages;
	}
	
	/**
	 * 
	 * @param totalPages 传入总页数
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
	/**
	 * 
	 * @return 总记录数
	 */
	public int getTotalRecords() {
		return totalRecords;
	}
	
	/**
	 * 
	 * @param totalRecords 传入总记录数
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	
	/**
	 * 
	 * @return 页面数据的集合
	 */
	public List<Object> getList() {
		return list;
	}
	
	/**
	 * 
	 * @param list 传入页面数据的集合
	 */
	public void setList(List<Object> list) {
		this.list = list;
	}
	
}
