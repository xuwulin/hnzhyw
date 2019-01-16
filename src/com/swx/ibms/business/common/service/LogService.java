package com.swx.ibms.business.common.service;

import java.util.Map;

/**
 * 日志管理服务接口
 * @author 李治鑫
 * @since 2017-5-3
 */
public interface LogService {
	
	/**
	 * 一般日志信息
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param mc 操作人
	 * @param rzlx 日志类型
	 * @param msg 日志内容
	 */
	void info (String dwbm,String gh,String mc,String rzlx,String msg);
	
	/**
	 * 错误日志信息
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param mc 操作人
	 * @param rzlx 日志类型
	 * @param msg 日志内容
	 */
	void error(String dwbm,String gh,String mc,String rzlx,String msg);
	
	/**
	 * 警告日志信息
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param mc 操作人
	 * @param rzlx 日志类型
	 * @param msg 日志内容
	 */
	void warn(String dwbm,String gh,String mc,String rzlx,String msg); 
	
	/**
	 * 获取日志信息
	 * @param page 当前页码
	 * @param pageSize 当前页面显示行数
	 * @param rzlx 日志类型
	 * @param rzzl 日志种类
	 * @return 日志信息列表
	 */
	Map<String,Object> getLog(int page,int pageSize,String rzlx,String rzzl);
	
	/**
	 * 获取日志信息
	 * @param page 当前页码
	 * @param pageSize 当前页面显示行数
	 * @param rzlx 日志类型
	 * @param rzzl 日志种类
	 * @param dwmc 单位名称
	 * @param xm   操作人姓名
	 * @return 日志信息列表 
	 */
	Map<String,Object> searchLog(int page,int pageSize,String rzlx,String rzzl,String dwmc,String xm,String rznr,String dwbm);
	
	/**
	 * 删除所有日志信息
	 * @return 1：成功 0：失败
	 */
	int deleteAllLog();
	
	/**
	 * 删除多条日志记录信息
	 * @param idArray 日志编号数组
	 * @return 1：成功，0：失败
	 */
	int deleteLog(String[] idArray);
}
