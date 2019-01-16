package com.swx.ibms.business.appraisal.service;

import com.swx.ibms.business.appraisal.bean.YwgzZbrs;
import com.swx.ibms.common.utils.PageCommon;

import java.util.Map;



/**
 * 
 * YwgzZbrsService.java  在编人数service
 * @author east
 * @date<p>2017年6月14日</p>
 * @version 1.0
 * @description<p></p>
 */
public interface YwgzZbrsService {
	
	/**
	 * 分页查询在编人数
	 * @param map 参数集合
	 * @return 分页对象
	 */
	PageCommon<YwgzZbrs> zbrsSelectPageList(Map<String,Object> map);
	
	/**
	 * 根据id查询在编人数
	 * @param map 参数集合
	 * @return 在编人数对象
	 */
	YwgzZbrs zbrsSelectById(Map<String,Object> map);
	
	/**
	 * 添加在编人数
	 * @param map 参数集合
	 * @return 是否成功字符串
	 */
	String zbrsInsertData(Map<String,Object> map);
	
	/**
	 * 修改在编人数
	 * @param map 参数集合
	 * @return 是否成功字符串
	 */ 
	String zbrsUpdateData(Map<String,Object> map);
	
	/**
	 * 删除在编人数
	 * @param map 参数集合
	 * @return 是否成功的字符串
	 */
	String zbrsDeleteData(Map<String,Object> map);
	
	
	
}
