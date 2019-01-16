package com.swx.ibms.common.bean;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 树形结构节点
 * 
 * @author 魏明欣
 *
 * @since 2017年3月4日
 */
public class TreeNode {
	/**
	 * ID
	 */
	private String id = null;
	/**
	 * 文本描述
	 */
	private String text = null;
	/**
	 * 子节点
	 */
	private List<TreeNode> children = null;
	
	/**
	 * 节点开闭状态 【默认为"",设置值为closed标识关闭节点展示,open表示开启】
	 */
	private String state = null;
	
	private String open =null;
	
	/**
	 * 是否可以选择该节点
	 */
	private boolean valueSelect = true;
	
	/**
	 * 标识节点数据的上级节点id
	 */
	private String source = null;


	private String iconCls = StringUtils.EMPTY;

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	/**
	 * ID
	 * @param id ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * ID
	 * @return ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 文本描述
	 * @param text 文本描述
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 文本描述
	 * @return 文本描述
	 */
	public String getText() {
		return text;
	}

	/**
	 * 子节点
	 * @param chidren 子节点列表
	 */
	public void setChidren(List<TreeNode> chidren) {
		this.children = chidren;
	}

	/**
	 * 子节点
	 * @return 子节点
	 */
	public List<TreeNode> getChidren() {
		return children;
	}

	/**
	 * 节点开闭状态
	 * @return false/true
	 */

	public String getOpen() {
		return open;
	}

	/**
	 * 节点开闭状态
	 * @param open closed/open
	 */
	public void setOpen(String open) {
		this.open = open;
	}

	/**
	 * 获取是否可以选择该节点
	 * @return 是否可以选择该节点
	 */
	public boolean isValueSelect() {
		return valueSelect;
	}

	/**
	 * 设置是否可以选择该节点
	 * @param valueSelect 是否可以选择该节点
	 */
	public void setValueSelect(boolean valueSelect) {
		this.valueSelect = valueSelect;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return 标识节点数据的上级节点id
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source 标识节点数据的上级节点id
	 */
	public void setSource(String source) {
		this.source = source;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
}


