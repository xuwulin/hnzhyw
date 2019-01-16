package com.swx.ibms.business.appraisal.controller;

import com.swx.ibms.business.appraisal.bean.YwgzZbrs;
import com.swx.ibms.business.appraisal.service.YwgzZbrsService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.lang.StringUtils;
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
 * YwgzZbrsController.java  在编人数controller
 * @author east
 * @date<p>2017年6月14日</p>
 * @version 1.0
 * @description<p></p>
 */
@Controller
@RequestMapping("zbrsController")
@SuppressWarnings("all")
public class YwgzZbrsController {
	
	/**
	 * 在编人数service
	 */
	@Resource
	private YwgzZbrsService ywgzZbrsService;
	
	
	/**
	 * 分页查询全部在编人数信息,可传入单位编码查询并分页
	 * @param req 请求参数 
	 * @return JSON样式的字符串
	 */
	@RequestMapping(value = "/zbrsSelectPageList", method = RequestMethod.GET)
	@ResponseBody
	public String zbrsSelectPageList(HttpServletRequest req){
		
		Map<String,Object> map = new HashMap<String,Object>();
		String dwbm = req.getParameter("dwbm")==null?"":req.getParameter("dwbm");// 单位编码
		int nowPage = Integer.parseInt(req.getParameter("page"));//当前页
		int pageSize = Integer.parseInt(req.getParameter("rows"));//每页显示数
		
		map.put("dwbm",dwbm);
		map.put("nowPage", nowPage);
		map.put("pageSize", pageSize);
			
		PageCommon<YwgzZbrs> pageCommon = ywgzZbrsService.zbrsSelectPageList(map);
			
		if (!Objects.isNull(pageCommon)) {
			
			map.put("total", pageCommon.getTotalRecords());
			map.put("rows", pageCommon.getList());
			
			return Constant.GSON.toJson(map);
		}
		return null;
	}
	
	
	
	/**
	 * 添加在编人数信息
	 * @param req 请求对象
	 * @return JSON样式的字符串
	 */
	@RequestMapping(value = "/zbrsInsertData", method = RequestMethod.POST)
	@ResponseBody
	public String zbrsInsertData(HttpServletRequest req){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
  		String dwbm = req.getParameter("dwbm");//单位编码
		String zbrs = req.getParameter("zbrs");//在编人数
//  	String bmbm = req.getParameter("bmbm");//部门编码
//		String year = req.getParameter("year");//年
 		
		if (StringUtils.isNotEmpty(dwbm)&&StringUtils.isNotEmpty(zbrs)) {
			
			map.put("p_dwbm", dwbm);
//			map.put("p_bmbm", bmbm);
			map.put("p_zbrs", zbrs);
//			map.put("p_year", year);
			
			String message = ywgzZbrsService.zbrsInsertData(map);
			
			if (StringUtils.isNotEmpty(message)) {
				map.put("msg", message);
			}
		}
		
		return Constant.GSON.toJson(map);
	}
	
	
	/**
	 * 根据在编人数id修改在编人数信息
	 * @param req 请求对象
	 * @return JSON样式的字符串
	 */
	@RequestMapping(value = "/zbrsUpdateData", method = RequestMethod.POST)
	@ResponseBody
	public String zbrsUpdateData(HttpServletRequest req){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
  		String id = req.getParameter("id");//在编人数id
  		String dwbm = req.getParameter("dwbm");//单位编码
		String zbrs = req.getParameter("zbrs");//在编人数
//  	String bmbm = req.getParameter("bmbm");//部门编码
//		String year = req.getParameter("year");//年
		
		if (StringUtils.isNotEmpty(id)) {
			
			map.put("p_id", id);
			map.put("p_dwbm", dwbm);
//			map.put("p_bmbm", bmbm);
			map.put("p_zbrs", zbrs);
//			map.put("p_year", year);
			
			String message = ywgzZbrsService.zbrsUpdateData(map);
			
			if (StringUtils.isNotEmpty(message)) {
				map.put("msg", message);
			}
		}
  		
		return Constant.GSON.toJson(map);
	}
	
	
	/**
	 * 根据在编人数id删除在编人数信息
	 * @param req 请求对象
	 * @return JSON样式的字符串
	 */
	@RequestMapping(value = "/zbrsDeleteData", method = RequestMethod.POST)
	@ResponseBody
	public String zbrsDeleteData(HttpServletRequest req){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		String id = req.getParameter("id");//在编人数id
		
		if (StringUtils.isNotEmpty(id)) {
			
			map.put("p_id", id);
			
			String message = ywgzZbrsService.zbrsDeleteData(map);
			
			if (StringUtils.isNotEmpty(message)) {
				map.put("msg", message);
			}
			
		}
		
		return Constant.GSON.toJson(map);
	}
	
	
	/**
	 * 通过在编人数id查询在编人数信息
	 * @param req 请求对象
	 * @return JSON样式的字符串
	 */
	@RequestMapping(value = "/zbrsSelectById", method = RequestMethod.POST)
	@ResponseBody
	public String zbrsSelectById(HttpServletRequest req){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		String id = req.getParameter("id");//在编人数id
		
		if (StringUtils.isNotEmpty(id)) {
			
			map.put("p_id", id);
			
			YwgzZbrs ywgzZbrs = ywgzZbrsService.zbrsSelectById(map);//根据id获取对象
			
			map.put("ywgzZbrs", ywgzZbrs);
		}
		
		return Constant.GSON.toJson(map);
	}
	
	
}
