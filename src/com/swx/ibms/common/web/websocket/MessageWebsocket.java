package com.swx.ibms.common.web.websocket;

import com.google.gson.JsonObject;
import com.swx.ibms.business.common.bean.MessageNotice;
import com.swx.ibms.business.common.service.MessageNoticeService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.common.utils.ApplicationContextUtils;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 
 * DaMessageWebsocket.java 档案操作信息通知类
 * 
 * @author east
 * @date<p>2017年5月24日</p>
 * @version 1.0
 * @description<p></p>
 */
@ServerEndpoint(value = "/daMessage")
@SuppressWarnings("all")
public class MessageWebsocket{

	/**
	 *  构建一个Map 来装所有链接webSocket的session
	 */
	public static final Map<String, Session> USERMAP = new HashMap<String, Session>();
	
	
	/**
	 * WebSocket开启中的方法
	 * @param session session
	 * @param config 配置文件
	 */
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
//		System.out.println("onOpen");
		// HttpSession httpSession= (HttpSession)
		// config.getUserProperties().get(HttpSession.class.getName());
		/*String userId = (String) config.getUserProperties().get("sendid");
		System.out.println("Session " + session.getId() + " has opened a connection");
		USERMAP.put(userId, session); // 注意 最好是能在session断开连接的时候
									// 将对应得session清除，不然如果KEY值一样的话第二次websocket连接的时候会出错
		
		try {
			session.getBasicRemote().sendText("Connection Established");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
*/	}

	/**
	 * WebSocket发送信息
	 * @param message 前台传入的参数
	 * @param session session
	 */
	@OnMessage
	public void onMessage(String message, Session session) {

		//组装页面传递过来的参数
		JsonObject jsonObject = (JsonObject) Constant.JSON_PARSER.parse(message);
		String msg = "";//定义发送的消息内容 字符串
		
		String status = jsonObject.get("status").getAsString();
		String dwbm = returnNotingString(jsonObject.get("dwbm").getAsString());
		String gh = returnNotingString(jsonObject.get("gh").getAsString());
		int nowPage = jsonObject.get("nowPage").getAsInt();
		int pageSize = jsonObject.get("pageSize").getAsInt();
		
		//获取 消息Service Bean
//		new ClassPathXmlApplicationContext("applicationContext.xml");
		MessageNoticeService msNoticeService = (MessageNoticeService) ApplicationContextUtils
				.getBean("messageNoticeService");
		
		if (StringUtils.isNotEmpty(dwbm)&&StringUtils.isNotEmpty(gh)) {
			
			PageCommon<MessageNotice> pageCommon = msNoticeService.selectListPage(StringUtils.EMPTY,
					status, dwbm, gh, nowPage, pageSize);
			
			List<MessageNotice> list = pageCommon.getList();//接收消息list
			
			//没有未查看消息时,前台不推送信息
			if(!Objects.isNull(pageCommon)&&CollectionUtils.isNotEmpty(list)){
				//解析获取到的分页对象,取出当前登录人的未查看消息并组装
				for (int i = 0; i < list.size(); i++) {
					msg+=(i+1)+"."+list.get(i).getContent()+"<br/>处理人："
							+list.get(i).getOperator()+"<br/><br/>";
				}
				
		//		String message = "张明的2017-5-6到2017-6-8的个人档案陈更明已经审批通过！";
		//		while(true) {
					try {
						//发送消息
						session.getBasicRemote().sendText(msg);
		//				Thread.sleep(2500);
					} catch (Exception e) {
					}
		//		}
			}
		}
		
		
	}

	/**
	 * WebSocket关闭方法
	 * @param session session
	 */
	@OnClose
	public void onClose(Session session) {
		try {
			session.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("Session " + session.getId() + " has closed!");
	}

	/**
	 * 注意: OnError() 只能出现一次. 其中的参数都是可选的。
	 * @param session session
	 * @param t 异常
	 */
	@OnError
	public void onError(Session session, Throwable t) {
		t.printStackTrace();
	}
	
	/**
	 * 返回空的字符串
	 * @param str 字符串
	 * @return 空字符串或者非空字符串
	 */
	private String returnNotingString(String str){
		if (StringUtils.isEmpty(str)||"".equals(str)) {
			return "";
		}else{
			return str;
		}
	}
}
