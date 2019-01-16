/**
 * 
 */
package com.swx.ibms.business.system.controller;

import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.system.bean.XTFJPATH;
import com.swx.ibms.business.system.service.XtfjpathService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



/**
 * 系统附件上传路径
 * @author 封泽超
 *@since 2017年4月7日
 */

@RequestMapping("/xtfj")
@Controller
public class XtfjpathController {
	
	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(XtfjpathController.class);

	/**
	 * 系统文件上传路径服务对象
	 */
	@Resource
	private XtfjpathService xtfjpathservice;
	
	/**
	 * 查询上传路径
	 * @param request 
	 * @return String
	 */
	@RequestMapping("/select")
	public @ResponseBody String select(HttpServletRequest request){
		return xtfjpathservice.getPath();
	}
	/**
	 * 修改上传文件路径 
	 * @param request 路径
	 * @return String
	 */
	@RequestMapping("/insert")
	public @ResponseBody String insert(HttpServletRequest request){
		XTFJPATH x = new XTFJPATH();
		String path = request.getParameter("path");
		HttpSession session = request.getSession();
		RYBM ry = (RYBM) session.getAttribute("ry");
		String czr = ry.getDlbm();
		x.setPath(path);
		x.setCzr(czr);
		return xtfjpathservice.insert(x);
	}
}
