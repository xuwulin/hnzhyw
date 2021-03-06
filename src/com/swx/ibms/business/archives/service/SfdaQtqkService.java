package com.swx.ibms.business.archives.service;

import com.swx.ibms.business.archives.bean.SfdaQtqk;

import java.util.Map;



/**
*@author:徐武林
*@date:2018年3月28日下午5:27:49
*@Description:其他情况Service接口
*/
@SuppressWarnings("all")
public interface SfdaQtqkService {
	
	/**
	 * 展示司法档案下的其他情况
	 * @param map 
	 */
	Map getSfdaQtqkPageList(SfdaQtqk qtqk, int start, int end) throws Exception;
	
	/**
	 * 添加司法档案下的其他情况
	 * @param qtqk 司法技能实体类
	 * @return 是否添加成功 1 = 成功, 0 = 不成功
	 */
	 String addSfdaQtqk(SfdaQtqk qtqk) throws Exception;
	
	/**
	 * 修改司法档案下的其他情况
	 * @param qtqk 司法技能实体类 
	 * @return 成功与失败的字符串，1 ：成功
	 */
	String updateSfdaQtqk(SfdaQtqk qtqk) throws Exception;
	
	/**
	 * 删除司法档案下的其他情况
	 * @param qtqk 司法技能实体类 
	 * @return 成功与失败的字符串，1 ：成功
	 */
	String deleteSfdaQtqk(SfdaQtqk qtqk) throws Exception;
	

}
