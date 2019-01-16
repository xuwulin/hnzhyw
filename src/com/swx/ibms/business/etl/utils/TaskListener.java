package com.swx.ibms.business.etl.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.swx.ibms.business.etl.service.TaskService;
import com.swx.ibms.common.utils.ApplicationContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author zsq
 *
 */
public class TaskListener {
	/**
	 *  日志服务
	 */
	private static Logger logger = LoggerFactory.getLogger(SyncPartyPageVisitJob.class);

	/**
	 *  时间格式
	 */
	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 
	 */
	public void doTask(){
		try{
			TaskService taskService = (TaskService) ApplicationContextUtils.getBean("taskServiceImpl");
					taskService.jcgs();
			logger.debug("---------"+DATE_FORMAT.format(Calendar.getInstance().getTime())
			+"定时任务执行成功");
		}catch(Exception e){
			logger.debug("---------"+DATE_FORMAT.format(Calendar.getInstance().getTime())
			+"定时任务执行失败。抛出异常为:"+e.getMessage());
		}
	}

}
