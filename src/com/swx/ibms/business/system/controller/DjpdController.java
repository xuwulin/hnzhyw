package com.swx.ibms.business.system.controller;

import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.system.bean.Djpd;
import com.swx.ibms.business.system.service.DjpdService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




/**
 * 等级评定Controller
 * @author 李佳
 * @date: 2017年5月22日 
 */
@RequestMapping("/djpd")
@Controller
public class DjpdController {
	/**
	 * 等级评定接口
	 */
	@Resource
	private DjpdService djpdService;

	/**
	 * 得到等级评定信息
	 * @param request 请求参数
	 * @return List<Map<String, Object>>
	 */
	@RequestMapping("/select")
	public @ResponseBody String selectDjpd(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();

		List<Djpd> list=djpdService.selectDjpd();

		result.put("djpdxx", list);
		return Constant.GSON.toJson(result);

	}
	/**
	 * 得到等级评定信息
	 * @param request 请求参数
	 * @return List<Map<String, Object>>
	 */
	@RequestMapping("/update")
	public @ResponseBody String updateDjpd(HttpServletRequest request) {
		String string = request.getParameter("dat");

		String[] djpds = string.split("and");
		List<Djpd> list = new ArrayList<Djpd>();
		String[] everydjpd = null;
		for(String ss : djpds){
			Djpd djpd = new Djpd();
			everydjpd = ss.split(",");
			djpd.setPdjb(everydjpd[0]);
			djpd.setDjdfsdxx(Double.valueOf(everydjpd[1]));
			djpd.setDjdfsdsx(Double.valueOf(everydjpd[2]));
			list.add(djpd);
		}
		boolean b = djpdService.update(list);
		Map<String, Object> result = new HashMap<>();
		result.put("b", b);
		return Constant.GSON.toJson(result);
	}

}
