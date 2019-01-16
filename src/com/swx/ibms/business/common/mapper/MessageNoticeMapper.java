package com.swx.ibms.business.common.mapper;

import java.util.Map;

/**
 * 
 * MessageNoticeMapper.java  消息通知mapper
 * @author east
 * @date<p>2017年5月25日</p>
 * @version 1.0
 * @description<p>定义消息通知的查询、修改以及新增消息通知的方法。</p>
 */
public interface MessageNoticeMapper {
	
	/**
	 * 根据消息内容或者状态，查询全部通知消息并分页
	 * @param map 参数集合
	 */
	void selectListPage(Map<String,Object> map);
	
	/**
	 * 添加消息通知信息
	 * @param map 消息通知参数集合
	 */
	void insertData(Map<String,Object> map);
	
	/**
	 * 将已查看的消息通知更改状态值
	 * @param map 已查看消息通知的id
	 */
	void updateDataStatus(Map<String,Object> map);
	
}
