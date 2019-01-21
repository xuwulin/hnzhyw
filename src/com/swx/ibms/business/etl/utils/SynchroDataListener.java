package com.swx.ibms.business.etl.utils;


import com.swx.ibms.business.etl.service.SynchroDataService;
import com.swx.ibms.common.utils.ApplicationContextUtils;
import com.swx.ibms.common.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

/**
 * 同步任务执行类
 * @author 李治鑫
 * @since 2017年8月24日  下午2:25:44
 */
public class SynchroDataListener {

	private static Logger logger = LoggerFactory.getLogger(SyncPartyPageVisitJob.class);

	/**
	 * 执行
	 */
	public void doTask(){
		try {
			logger.debug("{定时同步任务【synchroData】开始执行} =====>"+ DateUtil.dateToString(new Date(),DateUtil.FORMAT_ONE));

			SynchroDataService syncService = (SynchroDataService) ApplicationContextUtils.getBean("synchroDataService");
			syncService.startSynchro();

			logger.debug("{定时同步任务【synchroData】执行完成} =====>"+ DateUtil.dateToString(new Date(),DateUtil.FORMAT_ONE));

		} catch (Exception e) {
			System.out.println("同步数据任务【synchroData】执行出错！");
			e.printStackTrace();
		}
	}
	
}
