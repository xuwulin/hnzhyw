package com.swx.ibms.business.common.controller;

import com.swx.ibms.business.common.bean.MessageNotice;
import com.swx.ibms.business.common.service.MessageNoticeService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



/**
 * 
 * MessageNoticeController.java 消息通知controller
 * 
 * @author east
 * @date<p>2017年5月25日</p>
 * @version 1.0
 * @description<p></p>
 */
@Controller
@RequestMapping("/msgNoticeController")
@SuppressWarnings("all")
public class MessageNoticeController {

	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(MessageNoticeController.class);

	/**
	 * 消息通知service
	 */
	@Resource
	private MessageNoticeService msgNoticeService;

	/**
	 * 根据消息内容、状态查询全部消息并分页
	 * @param req 请求对象
	 * @return json样式的字符串
	 */
	@RequestMapping(value = "/selectPageList", method = RequestMethod.GET)
	@ResponseBody
	public String selectPageList(HttpServletRequest req) {

		Map<String, Object> map = new HashMap<String, Object>();

		String content = req.getParameter("content");// 单位编码
		String status = req.getParameter("status");// 工号
		String dwbm = req.getParameter("dwbm");// 单位编码
		String gh = req.getParameter("gh");// 单位编码
		int nowPage = Integer.parseInt(req.getParameter("page"));// 当前页
		int pageSize = Integer.parseInt(req.getParameter("pageSize"));// 每页显示数

		try {
			// 调用消息通知的service
			PageCommon<MessageNotice> pageCommon = msgNoticeService.selectListPage(
					content, status, dwbm,gh,nowPage, pageSize);

			if (!Objects.isNull(pageCommon)) {
				map.put("pageList", pageCommon.getList());
				map.put("nowPage", pageCommon.getNowPage());
				map.put("pageSize", pageCommon.getPageSize());
				map.put("totalRecords", pageCommon.getTotalRecords());
				map.put("totalPages", pageCommon.getTotalPages());
			}

		} catch (Exception e) {// e.getMessage(), e.getCause(),
			LOG.error(e.getMessage(), e);
		}

		return Constant.GSON.toJson(map);
	}
	
	
	/**
	 *	添加消息
	 * @param msgNotice 请求对象
	 * @return json样式的字符串
	 */
	/*@RequestMapping(value = "/addMsgData", method = RequestMethod.GET)
	@ResponseBody
	public String addMsgData(MessageNotice msgNotice) {

		Map<String, Object> map = new HashMap<String, Object>();
		
		if(!Objects.isNull(msgNotice)){
			
			try {
				// 调用消息通知的service
				String msg = msgNoticeService.insertData(msgNotice);
				
				if (StringUtils.isNotEmpty(msg)) {
					map.put("msg", msg);
				}
				
			} catch (Exception e) {// e.getMessage(), e.getCause(),
				LOG.error(e.getMessage(), e);
			}
			
		}

		return Constant.GSON.toJson(map);
	}*/
	
	/**
	 *	修改消息
	 * @param req 请求对象
	 * @return json样式的字符串
	 */
	@RequestMapping(value = "/updateMsgData", method = RequestMethod.GET)
	@ResponseBody
	public String updateMsgData(HttpServletRequest req) {

		Map<String, Object> map = new HashMap<String, Object>();

		String id = req.getParameter("id");// 消息id
		
		if (StringUtils.isNotEmpty(id)) {
			try {
				// 调用消息通知的service
				String msg = msgNoticeService.updateDataStatus(id);
				
				if (StringUtils.isNotEmpty(msg)) {
					map.put("msg", msg);
				}
				return Constant.GSON.toJson(map);
			} catch (Exception e) {// e.getMessage(), e.getCause(),
				LOG.error(e.getMessage(), e);
			}
		}

		return null;
	}
}
