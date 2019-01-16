package com.swx.ibms.common.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.swx.common.web.listener.StartupListenerExecutor;
import com.swx.ibms.common.utils.ApplicationContextUtils;


/**
 * <h3>Web容器启动或结束的监听</h3>
 * 
 * @author east
 *
 */
public class StartUpListener implements ServletContextListener {
	
	private StartupListenerExecutor startUpListenerExcutor;


	/**
	 * 初始化
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		if(null == startUpListenerExcutor) {
			startUpListenerExcutor = ApplicationContextUtils.getBean(StartupListenerExecutor.class);
		}
		
		if(startUpListenerExcutor != null){
			startUpListenerExcutor.contextInitialized();
		}
		
		welcome();
		
	}
	
	private void welcome() {
		System.out.println("\n");
		System.out.println("\t* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		System.out.println("\t*                                                                                     *");
		System.out.println("\t*                         欢迎使用【海南省人民检察院综合业务管理平台系统】！      ");
		System.out.println("\t*                                                                                     *");
		System.out.println("\t* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		System.out.println("\n");
	}

	/**
	 * 销毁
	 */
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		if(startUpListenerExcutor != null){
			startUpListenerExcutor.contextInitialized();
		}
		goodBye();
	}

	private void goodBye() {
		System.out.println("\n");
		System.out.println("\t* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		System.out.println("\t*                                                                                     *");
		System.out.println("\t*                         感谢使用【海南省人民检察院综合业务管理平台系统】，再见！     ");
		System.out.println("\t*                                                                                     *");
		System.out.println("\t* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		System.out.println("\n");
	}
}
