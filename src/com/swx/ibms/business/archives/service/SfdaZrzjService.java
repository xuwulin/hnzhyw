package com.swx.ibms.business.archives.service;

import com.swx.ibms.business.archives.bean.SfdaZrzj;

import java.util.Map;



/**
*@author:徐武林
*@date:2018年3月28日下午5:27:49
*@Description:责任追究Service接口
*/
@SuppressWarnings("all")
public interface SfdaZrzjService {
	
	/**
	 * 展示责任追究
	 * @param zrzj 责任追究实体类
	 * @param bottom 分页下限
	 * @param top 分页上限
	 * @return
	 */
	Map getSfdaZrzjPageList(SfdaZrzj zrzj, int bottom, int top);
	
	/**
	 * 添加司法档案下的责任追究
	 * @param zrzj 责任追究实体类		
	 * @return 是否添加成功 1 = 成功, 0 = 不成功
	 */
	String addSfdaZrzj(SfdaZrzj zrzj) ;
	
	/**
	 * 修改司法档案下的责任追究
	 * @param zrzj 责任追究实体类 
	 * @return 成功与失败的字符串，1 ：成功
	 */
	String updateSfdaZrzj(SfdaZrzj zrzj) ;
	
	/**
	 * 删除司法档案下的责任追究
	 * @param zrzj 责任追究实体类 
	 * @return 成功与失败的字符串，1 ：成功
	 */
	String deleteSfdaZrzj(SfdaZrzj zrzj) ;
	

}
