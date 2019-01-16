/**
 * 
 */
package com.swx.ibms.business.system.service;

import com.swx.ibms.business.system.bean.XTFJPATH;

import java.util.List;



/**
 * 文件上传路径系统设置服务
 * @author 封泽超
 *@since 2017年4月7日
 */
public interface XtfjpathService {
	/**
	 * 修改上传路径
	 * @param x 
	 * @return String
	 */
	String insert(XTFJPATH x);
	
	/**
	 * 查询上传路径
	 * @return List<XTFJPATH> 
	 */
	List<XTFJPATH> select();
	
	/**
	 * 得到系统设置的路径
	 * @return String 
	 */
	String getPath();
}
