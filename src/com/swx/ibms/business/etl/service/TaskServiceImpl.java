package com.swx.ibms.business.etl.service;

import com.swx.ibms.business.etl.mapper.TaskMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;



/**
 * @author zsq
 * 定时任务
 */
@Service("taskServiceImpl")
public class TaskServiceImpl implements TaskService{
	
	
	@Resource 
	private TaskMapper taskMapper;
	
	/**
	 *  档案公示超过公示期限后 变为不公示
	 */
	@Override
	public void jcgs(){
		int gsqx = taskMapper.getGsqx();
		Date date = new Date();
		date = DateUtils.addDays(date, 0-gsqx);
		try{
			taskMapper.jcgs();
			taskMapper.scgsjl();
		}catch(Exception e){
		}
	}
	

}
