package com.swx.ibms.business.system.service;

import com.swx.ibms.business.system.bean.Djpd;

import java.util.List;




/**
 * 等级评定接口
 * @author 李佳
 * @date: 2017年5月22日 
 */
public interface DjpdService {

	/**
	 * 得到等级评定信息
	 * @return List<Djpd>
	 */
	List<Djpd> selectDjpd();
	
	/**
	 * 评定级别
	 * @param zpjdf  总评价得分
	 * @return 评定级别名称
	 */
	List<String> djpd(double zpjdf);

	/**
	 * @param list list
	 * @return 更新等级
	 */
	boolean update(List<Djpd> list);

	

}
