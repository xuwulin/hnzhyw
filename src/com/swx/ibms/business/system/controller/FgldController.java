package com.swx.ibms.business.system.controller;

import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.BMBM;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.system.bean.Fgld;
import com.swx.ibms.business.system.service.FgldService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * @author zsq
 * 分管领导
 */
@Controller
@RequestMapping("/fgld")
public class FgldController {
	
	/**
	 * 分管领导服务
	 */
	@Resource
    private FgldService fgldService;
	
	
	/**
	 * @param dwbm 单位编码 
	 * @return 获取分管领导配置
	 */
	@RequestMapping("/getFgld")
	@ResponseBody
	public String getFgld(String dwbm){
		List<Fgld> list = fgldService.getFgld(dwbm);
		return Constant.GSON.toJson(list);
	}
	/**
	 * @param dwbm 单位编码 
	 * @return 获取该单位的副检察长(只有副检察长才能做分管领导)
	 */
	@RequestMapping("/getLd")
	@ResponseBody
	public String getLd(String dwbm){
		List<RYBM> list = fgldService.getLd(dwbm);
		return Constant.GSON.toJson(list);
	}
	/**
	 * @param dwbm 单位编码 
	 * @param gh 领导工号 
	 * @return 根据工号获取领导的名字
	 */
	@RequestMapping("/getldname")
	@ResponseBody
	public String getldname(String dwbm,String gh){
		List<RYBM> list = fgldService.getLd(dwbm);
		return Constant.GSON.toJson(list);
	}
	/**
	 * @param dwbm 单位编码 
	 * @return 获取该单位的所有部门
	 */
	@RequestMapping("/getBm")
	@ResponseBody
	public String getBm(String dwbm){
		List<BMBM> list = fgldService.getBm(dwbm);
		return Constant.GSON.toJson(list);
	}
	/**
	 * @param ldgh 领导工号
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @return 添加分管领导
	 */
	@RequestMapping("/addFgld")
	@ResponseBody
	public String addFgld(String ldgh,String bmbm,String dwbm){
		int i = fgldService.addFgld(ldgh,bmbm,dwbm);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", i);
		return Constant.GSON.toJson(map);
	}
	/**
	 *  @param ldgh 领导工号
	 * @param dwbm 单位编码
	 * @param id id
	 * @param bmbm 部门编码
	 * @return 添加分管领导
	 */
	@RequestMapping("/updateFgld")
	@ResponseBody
	public String updateFgld(String id,String ldgh,String bmbm,String dwbm){
		int i = fgldService.updateFgld(id,ldgh,bmbm,dwbm);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", i);
		return Constant.GSON.toJson(map);
	}
	/**
	 * @param id id
	 * @return 删除分管领导
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String id){
		int i = fgldService.delete(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", i);
		return Constant.GSON.toJson(map);
	}
	
}
