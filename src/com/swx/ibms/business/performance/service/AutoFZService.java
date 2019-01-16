package com.swx.ibms.business.performance.service;

import java.util.Map;

/**
 * 
 * @author 王宇锋
 *
 */
public interface AutoFZService{
	/**
	 * 自动计算分值
	 * @param map map封装相关参数
	 * @return 返回相关分值信息
	 */
	Double zdjsfz(Map<String,Object> map);	
	
	/**
	 * 自动计算分值的方法描述
	 * @return 自动计算分值的方法描述的字符串 
	 */
	String desc();
}
