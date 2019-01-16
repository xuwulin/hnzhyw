package com.swx.ibms.business.etl.service;

/**
 * 同步数据服务
 * @author 李治鑫
 * @since 2017年8月24日  下午2:06:49
 */
public interface SynchroDataService {
	/**
	 * 清空数据
	 * @param tables 表名数组
	 */
	void deleteData(String[] tables);
	/**
	 * 开始同步操作
	 */
	void startSynchro();
}
