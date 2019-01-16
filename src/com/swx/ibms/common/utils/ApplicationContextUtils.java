package com.swx.ibms.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * ApplicationContextUtils.java Spring容器上下文通用类
 * 
 * @author east
 * @date
 * <p>
 * 2017年5月26日
 * </p>
 * @version 1.0
 * @description
 * <p>
 * </p>
 */
public class ApplicationContextUtils implements ApplicationContextAware {

	/**
	 * spring容器上下文 private static ApplicationContext applicationContext;
	 */
	private static ApplicationContext applicationContext;

	/**
	 * spring容器上下文
	 */
	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		// SpringConte
		ApplicationContextUtils.applicationContext = appContext;
	}

	/**
	 * 
	 * @param name bean的id名
	 * @return bean的id所对应的对象
	 */
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}


	/**
	 * @param t bean类
	 * @return
	 */
	public static <T> T getBean(Class<T> t){
		if(applicationContext == null){
			return null;
		}

		return applicationContext.getBean(t);
	}


}
