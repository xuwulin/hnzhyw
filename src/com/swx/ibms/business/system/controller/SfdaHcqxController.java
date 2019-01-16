package com.swx.ibms.business.system.controller;

import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.system.bean.Hcqx;
import com.swx.ibms.business.system.service.SfdaHcqxService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * @author zsq
 * 司法档案核查期限
 */
@Controller
@RequestMapping("/hcqx")
public class SfdaHcqxController {
	
	/**
	 * 
	 */
	@Resource
	private SfdaHcqxService sfdaHcqxService;
	
	/**
	 * @param request request
	 * @param response response
	 * @return  获取核查期限信息
	 */
	@RequestMapping("/getHcqx")
	@ResponseBody
	public String getHcqx(HttpServletRequest request,HttpServletResponse response){
		List<Hcqx> list = sfdaHcqxService.getHcqx();
		return Constant.GSON.toJson(list);
	}
	/**
	 * @param hcqx 核查期限
	 * @param gxr 更新人
	 * @return 更新核查期限信息
	 */
	@RequestMapping("/setHcqx")
	@ResponseBody
	public String setHcqx(int hcqx,String gxr){
		 boolean t = sfdaHcqxService.setHcqx(hcqx,gxr);
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("set", t);
		return Constant.GSON.toJson(map);
	}
	/**
	 * @param gsqx 公示期限
	 * @param gxr 更新人
	 * @return 更新公示期限信息
	 */
	@RequestMapping("/setGsqx")
	@ResponseBody
	public String setGsqx(int gsqx,String gxr){
		boolean t = sfdaHcqxService.setGsqx(gsqx,gxr);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("set", t);
		return Constant.GSON.toJson(map);
	}
	
	
	
	
	

}
