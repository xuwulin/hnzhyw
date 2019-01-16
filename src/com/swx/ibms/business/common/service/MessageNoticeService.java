package com.swx.ibms.business.common.service;


import com.swx.ibms.business.common.bean.MessageNotice;
import com.swx.ibms.common.utils.PageCommon;

/**
 * 
 * MessageNoticeService.java  消息通知service
 * @author east
 * @date<p>2017年5月25日</p>
 * @version 1.0
 * @description<p>定义消息通知的查询、修改以及新增消息通知的方法。</p>
 */
public interface MessageNoticeService {
	
	/**
	 * 根据消息内容或者状态，查询全部通知消息并分页
	 * @param content 消息内容
	 * @param status 消息状态
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param nowPage 当前页
	 * @param pageSize 每页显示数
	 * @return 消息通知分页结果集
	 */
	PageCommon<MessageNotice> selectListPage(String content, String status,
											 String dwbm, String gh, int nowPage, int pageSize);
	
	/**
	 * 添加消息通知信息
	 * @param msgNotice 消息通知实体类
	 * @return 是否成功的标示 (1 成功 )字符串
	 */
	String insertData(MessageNotice msgNotice);
	
	/**
	 * 将已查看的消息通知更改状态值
	 * @param id 已查看消息通知的id
	 * @return 是否成功的标示 (1 成功 )字符串
	 */
	String updateDataStatus(String id);
	
}
