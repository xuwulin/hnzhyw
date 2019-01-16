package com.swx.ibms.business.etl.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 任务调度工作类
 * @author admin
 *
 */
public class QuartzJob implements Job {

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("同步任务执行日期："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ "........."); 
		SynchroDataListener job_detail = new SynchroDataListener();
		job_detail.doTask();
		
	}
	

}
