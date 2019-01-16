package com.swx.ibms.business.archives.service;

import com.swx.ibms.business.archives.bean.SfdaJdqk;

import java.util.Map;



/**
*@author:徐武林
*@date:2018年3月28日下午5:27:49
*@Description:监督情况Service接口
*/
@SuppressWarnings("all")
public interface SfdaJdqkService {
	
	/**
	 * 展示监督情况
	 * @param zrzj 监督情况实体类
	 * @param bottom 分页下限
	 * @param top 分页上限
	 * @return
	 */
	Map getSfdaJdqkPageList(SfdaJdqk jdqk, int start, int end);
	
	/**
	 * 添加司法档案下的监督情况
	 * @param jdqk 监督情况实体类		
	 * @return 是否添加成功 1 = 成功, 0 = 不成功
	 */
	String addSfdaJdqk(SfdaJdqk jdqk) ;
	
	/**
	 * 修改司法档案下的监督情况
	 * @param jdqk 监督情况实体类 
	 * @return 成功与失败的字符串，1 ：成功
	 */
	String updateSfdaJdqk(SfdaJdqk jdqk) ;
	
	/**
	 * 删除司法档案下的监督情况
	 * @param jdqk 监督情况实体类 
	 * @return 成功与失败的字符串，1 ：成功
	 */
	String deleteSfdaJdqk(SfdaJdqk jdqk) ;
	

}
