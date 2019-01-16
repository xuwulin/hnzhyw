package com.swx.ibms.business.system.controller;

import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.system.bean.Gdjs;
import com.swx.ibms.business.system.service.GdjsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;



/**
 * @author zsq
 * 固定角色
 */
@Controller
@RequestMapping("/gdjs")
public class GdjsController {
	/**
	 * gdjsService
	 */
	@Resource
	private GdjsService gdjsService;
	
	/**
	 * @return
	 * 返回所有的固定角色信息
	 */
	@RequestMapping("/getjs")
	@ResponseBody
	public String getjs(){
		List<Gdjs> gdjs = gdjsService.getjs();
		return Constant.GSON.toJson(gdjs);
	}
	
	

}
