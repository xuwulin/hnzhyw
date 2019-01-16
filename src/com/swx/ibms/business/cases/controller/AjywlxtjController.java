package com.swx.ibms.business.cases.controller;

import com.swx.ibms.business.cases.service.AjywlxtjService;
import com.swx.ibms.business.common.utils.Constant;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;



/**
 * 案件业务类型统计控制器
 * @author 李治鑫
 *
 */

@RequestMapping("/ajywlxtj")
@Controller
public class AjywlxtjController {
	/**
	 * 日志对象
	 * */
	private static final Logger LOG = Logger.getLogger(AjywlxtjController.class);
	
	/**
	 * 案件业务类型统计服务接口
	 * */
	@Resource
	private AjywlxtjService ajywlxtjService;
	
	
	/**
	 * 获取某人在某年中各类业务的案件的办理数量
	 * @param request 请求参数
	 * @return json数据
	 */
	@RequestMapping("/getcount")
	public @ResponseBody String getCount(HttpServletRequest request){
		
		String daid = request.getParameter("daid");//档案ID
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			map = ajywlxtjService.getCount(daid);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY,e);
		}
		
		if(MapUtils.isNotEmpty(map)){
			return Constant.GSON.toJson(map.get("p_cursor"));
		}else
			return null;
		
		
		
		
	}
}
