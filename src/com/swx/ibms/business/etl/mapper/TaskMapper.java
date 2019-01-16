package com.swx.ibms.business.etl.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

/**
 * @author zsq
 * 定时任务服务
 */
public interface TaskMapper {
	

	/**
	 * @return 公示期限
	 */
	int getGsqx();

	/**
	 */
	void jcgs();

	/**
	 */
	void scgsjl();

}
