package com.swx.ibms.business.etl.service;

import com.swx.ibms.business.etl.bean.XtQuartzPz;

import java.util.Map;


/**
 * 同步任务配置接口
 * @author admin
 *
 */
public interface XtQuartzPzService {
	
	Map<String, Object> getXtQuartzPzPageList(Integer page, Integer rows);
	
	/**
	 * 获取最新的同步任务配置信息
	 * @param xtQuartzPz
	 * @return 同步任务配置
	 */
	XtQuartzPz getLastXtQuartzPz();
	
	XtQuartzPz getXtQuartzPzById(String id);
	
	Integer addXtQuartzPz(XtQuartzPz xtQuartzPz);
	
	Integer modifyXtQuartzPz(XtQuartzPz xtQuartzPz);
	
	Integer deleteXtQuartzPz(String id);
	
}
