/**
 * 
 */
package com.swx.ibms.business.system.bean;

import java.sql.Date;

/**
 * 附件上传路径实体类
 * @author 封泽超
 *@since 2017年4月7日
 */
public class XTFJPATH {
	/**
	 * 编号
	 */
	private String id; 		//编号
	/**
	 * 路径
	 */
	private String path; 	//路径
	/**
	 * 操作人
	 */
	private String czr;		//操作人
	/**
	 * 操作时间
	 */
	private String czsj;	//操作时间
	
	/**
	 * 得到编号
	 * @return String 
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置编号
	 * @param id 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 得到路径
	 * @return String 
	 */
	public String getPath() {
		return path;
	}
	/**
	 * 设置路径
	 * @param path 
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * 得到操作人 
	 * @return String 
	 */
	public String getCzr() {
		return czr;
	}
	/**
	 * 设置操作人 
	 * @param czr 
	 */
	public void setCzr(String czr) {
		this.czr = czr;
	}
	/**
	 * 得到操作时间
	 * @return String
	 */
	public String getCzsj() {
		return czsj;
	}
	/**
	 * 设置操作时间
	 * @param czsj 
	 */
	public void setCzsj(Date czsj) {
		this.czsj = czsj.toString();
	}
	
	
}
