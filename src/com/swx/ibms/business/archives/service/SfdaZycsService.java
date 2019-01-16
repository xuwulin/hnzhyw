package com.swx.ibms.business.archives.service;

import com.swx.ibms.business.archives.bean.SfdaZycs;

import java.util.Map;



/**
*@author:徐武林
*@date:2018年3月28日下午5:27:49
*@Description:职业操守Service接口
*/
@SuppressWarnings("all")
public interface SfdaZycsService {
	
	/**
	 * 展示职业操守
	 * @param zycs 职业操守实体类
	 * @param bottom 分页下限
	 * @param top 分页上限
	 * @return
	 */
	Map getSfdaZycsPageList(SfdaZycs zycs, int bottom, int top);
	
	/**
	 * 添加司法档案下的职业操守
	 * @param ryjn 职业操守实体类		
	 * @return 是否添加成功 1 = 成功, 0 = 不成功
	 */
	 String addSfdaZycs(SfdaZycs zycs) throws Exception;
	
	/**
	 * 修改司法档案下的职业操守
	 * @param ryjn 职业操守实体类 
	 * @return 成功与失败的字符串，1 ：成功
	 */
	String updateSfdaZycs(SfdaZycs zycs) throws Exception;
	
	/**
	 * 删除司法档案下的职业操守
	 * @param ryjn 职业操守实体类 
	 * @return 成功与失败的字符串，1 ：成功
	 */
	String deleteSfdaZycs(SfdaZycs zycs) throws Exception;
	

}
