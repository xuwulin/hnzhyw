package com.swx.ibms.business.etl.utils;


import com.swx.ibms.business.etl.service.SynchroDataService;
import com.swx.ibms.common.utils.ApplicationContextUtils;

/**
 * 同步任务执行类
 * @author 李治鑫
 * @since 2017年8月24日  下午2:25:44
 */
public class SynchroDataListener {
	
	/**
	 * 执行
	 */
	public void doTask(){
		try {
			SynchroDataService syncService = (SynchroDataService) ApplicationContextUtils.getBean("synchroDataService");
			syncService.startSynchro();
		} catch (Exception e) {
			System.out.println("同步数据任务执行出错！");
			e.printStackTrace();
		}
	}
	
}
