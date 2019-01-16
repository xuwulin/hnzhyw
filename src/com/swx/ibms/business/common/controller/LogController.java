package com.swx.ibms.business.common.controller;

import com.swx.ibms.business.common.bean.Log;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.RYBM;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 日志管理控制器
 * @author 李治鑫
 * @since 2017-5-3
 */
@SuppressWarnings("all")
@RequestMapping("/log")
@Controller
public class LogController {
	
	/**
	 * 日志服务 
	 */
	@Resource 
	private LogService logservice;
	
	/**
	 * 根据不同情况获取日志信息
	 * @param request 请求参数
	 * @return 日志信息
	 */
	@RequestMapping(value="/getlog")
	public @ResponseBody String getLog(HttpServletRequest request){
		String pageStr = request.getParameter("page");//当前页码
		String pageSizeStr = request.getParameter("rows");//一页显示行数
		String rzlx = request.getParameter("rzlx");//日志类型
		String rzzl = request.getParameter("rzzl");//日志种类
		
		if(StringUtils.isEmpty(pageStr)){
			pageStr = "1";
		}
		
		int page = Integer.parseInt(pageStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		
		Map<String,Object> logmap = new HashMap<String,Object>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		logmap = logservice.getLog(page, pageSize, rzlx, rzzl);
		
		resultMap.put("total", (int)logmap.get("p_total"));
		resultMap.put("rows", (List<Log>)logmap.get("p_cursor"));
		
		return Constant.GSON.toJson(resultMap);
	}
	
	
	/**
	 * 根据不同情况获取日志信息
	 * @param request 请求参数
	 * @return 日志信息
	 */
	@RequestMapping(value="/searchlog")
	public @ResponseBody String searchLog(HttpServletRequest request){
		String pageStr = request.getParameter("page");//当前页码
		String pageSizeStr = request.getParameter("rows");//一页显示行数
		String rzlx = request.getParameter("rzlx");//日志类型
		String rzzl = request.getParameter("rzzl");//日志种类
		String dwmc = request.getParameter("dwmc");//单位名称
		String xm = request.getParameter("xm");//操作人姓名
		String rznr = request.getParameter("rznr");//操作内容
		String dwbm = request.getParameter("dwbm");
		if(StringUtils.isEmpty(pageStr)){
			pageStr = "1";
		}
		
		if(StringUtils.isBlank(dwbm)){
			RYBM ry = (RYBM) request.getSession().getAttribute("ry");
			char[] c = ry.getDwbm().toCharArray();
			for(int i = c.length-1 ; i>= 0 ; i-- ){
				if(c[i] == '0'){
					c[i] = ' ';
				}else{
					break;
				}
			}
			dwbm = new String(c);
			dwbm = dwbm.trim();
		}
		
		
		int page = Integer.parseInt(pageStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		
		Map<String,Object> logmap = new HashMap<String,Object>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		logmap = logservice.searchLog(page, pageSize, rzlx, rzzl,dwmc,xm,rznr,dwbm);
		
		resultMap.put("total", (int)logmap.get("p_total"));
		resultMap.put("rows", (List<Log>)logmap.get("p_cursor"));
		
		return Constant.GSON.toJson(resultMap);
	}
	
	
	/**
	 * 删除所有日志信息
	 * @return 返回执行结果信息，1：成功，0：失败
	 */
	@RequestMapping(value="/deleteall")
	public @ResponseBody String deleteAllLog(){
		int num = 0;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		num = logservice.deleteAllLog();
		resultMap.put("msg", num);
		return Constant.GSON.toJson(resultMap);
	}
	
	/**
	 * 删除指定的日志信息
	 * @param request 请求参数
 	 * @return 返回执行信息 1：成功，0：失败
	 */
	@RequestMapping(value="/deletelog")
	public @ResponseBody String deleteLog(HttpServletRequest request){
		String idArraystr = request.getParameter("idArray");
		int num = 0;
		String [] idArray = idArraystr.split(",");
		Map<String,Object> resultMap = new HashMap<String,Object>();
		num = logservice.deleteLog(idArray);
		resultMap.put("msg", num);
		return Constant.GSON.toJson(resultMap);
	}
}
