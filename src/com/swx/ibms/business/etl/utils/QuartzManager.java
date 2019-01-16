package com.swx.ibms.business.etl.utils;

import java.util.Objects;

import javax.annotation.Resource;

import com.swx.ibms.business.etl.bean.XtQuartzPz;
import com.swx.ibms.business.etl.service.XtQuartzPzService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;



/**
 * 任务调度管理类
 * @author admin
 *
 */
@SuppressWarnings("all")
public class QuartzManager {
	private static SchedulerFactory schedulerFactory = new StdSchedulerFactory(); 
	public static String JOB_NAME = "zhyw_ywkh_sfda_zzjg_job";  
	public static String JOB_GROUP_NAME = "zhyw_ywkh_sfda_zzjg_job_group"; 
    public static String TRIGGER_NAME = "zhyw_ywkh_sfda_zzjg_trigger"; 
    public static String TRIGGER_GROUP_NAME = "zhyw_ywkh_sfda_zzjg_trigger_group"; 
   
   @Resource
   private XtQuartzPzService xtQuartzPzService;
    
    /**
     * 任务调度添加通用类
     * @param jobName 任务名称
     * @param jobGroupName 任务组名
     * @param triggerName 触发器名称
     * @param triggerGroupName 触发器组名
     * @param jobClass 任务类
     * @param time 时间表达式
     */
    public static void addJob(String jobName, String jobGroupName,  
            String triggerName, String triggerGroupName, Class jobClass,  
            String time) {  
        try {  
        	Scheduler sched = schedulerFactory.getScheduler(); 
        	// 转换类全限定名
			
        	JobDataMap datas = new JobDataMap();
//			datas.put("test1", "460000_0059");
//			datas.put("mc", "杨宇");
			datas.put("clazz", jobClass.getName());
        	
        	JobDetail jobDetail =  JobBuilder.newJob(jobClass).setJobData(datas).withIdentity(jobName, JOB_GROUP_NAME).build(); // 任务名，任务组，任务执行类  
        	
            // 触发器  
            CronTrigger trigger = (CronTrigger)TriggerBuilder.newTrigger()  
				                    .withIdentity(triggerName, TRIGGER_GROUP_NAME)  // 触发器名,触发器组  
				                    .withSchedule(CronScheduleBuilder.cronSchedule(time))  
				                    .build();  
            		
            sched.scheduleJob(jobDetail, trigger);
            if (!sched.isShutdown()) {  
                sched.start();  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
    
    private void init() {
    	QuartzJob jobClass = new QuartzJob();
    	String jobName = this.JOB_NAME;
    	String jobGroupName = this.JOB_GROUP_NAME;
    	String triggerName = this.TRIGGER_NAME;
    	String triggerGroupName = this.TRIGGER_GROUP_NAME;
    	String time = "59 59 23 ? * L"; //这儿同步日期暂定为业务考核的同步日期为：每年的创建业务考核的最新日期的当晚23：59：59,默认为每周周天晚上23点执行 ：59 59 23 ? * L
    	
    	XtQuartzPz quartzPz = xtQuartzPzService.getLastXtQuartzPz();
    	if(quartzPz!=null) {
    		if(StringUtils.isNotBlank(quartzPz.getCron())) { 
        		time = quartzPz.getCron();
        	}
        	if(StringUtils.isNotBlank(quartzPz.getJobName())) {
        		jobName = quartzPz.getJobName();
        	}
        	if(StringUtils.isNotBlank(quartzPz.getJobGroupName())) {
        		jobName = quartzPz.getJobGroupName();
        	}
        	if(StringUtils.isNotBlank(quartzPz.getTriggerName())) {
        		jobName = quartzPz.getTriggerName();
        	}
        	if(StringUtils.isNotBlank(quartzPz.getTriggerGroupName())) {
        		jobName = quartzPz.getTriggerGroupName();
        	}
    	}
    	
    	this.addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass.getClass(), time);
	}
    
}
