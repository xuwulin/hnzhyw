package com.swx.ibms.business.system.mapper;

import com.swx.ibms.business.system.bean.Djpd;

import java.util.Map;



/**
 * 等级评定持久层接口
 * @author 李佳
 * @date: 2017年05月22日 
 */
public interface DjpdMapper {

	/**
	 * 得到等级评定信息
	 * @param map map对象
	 */
	void selectDjpd(Map<String, Object> map);

	/**
	 * 评定等级
	 * @param map map对象
	 */
	void djpd(Map<String, Object> map);

	/**
	 * @param djpd  djpd
	 */
	void update(Djpd djpd);

}
