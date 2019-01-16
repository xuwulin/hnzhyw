package com.swx.ibms.business.common.controller;

import com.swx.ibms.business.common.bean.DBYW;
import com.swx.ibms.business.common.service.DbywService;
import com.swx.ibms.business.common.service.SpService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.performance.controller.GrjxController;
import com.swx.ibms.business.rbac.bean.RYBM;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 待办业务控制器
 * 
 * @author 王宇锋
 */
@SuppressWarnings("all")
@RequestMapping("/dbyw")
@Controller
public class DbywController {
	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(GrjxController.class);
	/**
	 * 待办业务服务接口
	 */
	@Resource
	private DbywService dbywService;

	/**
	 * 审批服务接口
	 */
	@Resource
	private SpService spService;

	/**
	 * 根据单位编号和审批人编号获得待办业务列表
	 * @param request request
	 * @return 返回待办业务列表
	 */
	@RequestMapping(value = "/cx", method = RequestMethod.POST)
	@ResponseBody
	public String getDbyw(HttpServletRequest request) {
		HttpSession session = request.getSession();
		RYBM ry = null;
		try {
			ry = (RYBM) session.getAttribute("ry");
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		List<DBYW> list = null;
		String spdw = ry.getDwbm();
		String spr = ry.getGh();
		
		//获得人事部部门编码
		String dlrbm = spService.getRsbBmbm(request);
		//获得部门角色
		List<String> bmjs=(List<String>)session.getAttribute("bmjs");
		try {
			list = dbywService.getDbywList(spdw, spr, dlrbm,bmjs);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		return Constant.GSON.toJson(list);
	}

	/**
	 * 根据单位编号和审批人编号，页数分页获得待办业务列表
	 * @param request request
	 * @return 返回代办业务列表
	 */
	@RequestMapping(value = "/fycx", method = RequestMethod.POST)
	@ResponseBody
	public String getAllDbywFy(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String pagestr = request.getParameter("page");
		String clnr = request.getParameter("clnr");
		String splx = request.getParameter("splx");
		String sfdb = request.getParameter("sfdb");
		
		if(StringUtils.isBlank(sfdb)){
			sfdb = "1";
		}
		
		// 从session中获得
		RYBM ry = null;
		try {
			ry = (RYBM) session.getAttribute("ry");
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		
		int page = -1;
		if (StringUtils.isBlank(pagestr)) {
			page = 1;
		} else {
			page = Integer.parseInt(pagestr);
		}

		Map<String, Object> returnMap = null;
		String spdw = ry.getDwbm();
		String spr = ry.getGh();
		
		//获得人事部部门编码
		String dlrbm = spService.getRsbBmbm(request);
	    //获得部门角色
	    List<String> bmjs=(List<String>)session.getAttribute("bmjs");
	    
		returnMap = dbywService.getAllDbywFy(spdw, spr, dlrbm, page,bmjs,clnr,splx,sfdb);

		return Constant.GSON.toJson(returnMap);
	}
	
	
	/**
	 * 根据单位编号和审批人编号，页数分页获得待办业务列表
	 * @param request request
	 * @return 返回代办业务列表
	 */
	@RequestMapping(value = "/getDbyw", method = RequestMethod.POST)
	@ResponseBody
	public String getAllSpInfoByDwbmGhOrBm(HttpServletRequest request,
										   @RequestParam(value="page",required=false)Integer page,
										   @RequestParam(value="pageSize",required=false)Integer pageSize,
										   @RequestParam(value="clnr",required=false)String clnr,
										   @RequestParam(value="cxType",required=true)String cxType) {
		Map<String, Object> returnMap = new HashMap<String,Object>();
		HttpSession session = request.getSession();
		// 从session中获得
		RYBM ry = (RYBM) session.getAttribute("ry");
		String dlrDwbm = ry.getDwbm();
		String dlrBmbm = "";
		//获得人事部部门编码
		String dlrbm = spService.getRsbBmbm(request);
		if (StringUtils.isNoneBlank(dlrbm)) {
			dlrBmbm = dlrbm;
		}
		String dlrGh = ry.getGh();
		
		String spzt = "1";  //默认查询待办、参数为0查询已办
		if (StringUtils.isNoneBlank(cxType)&&"0".equals(cxType)) {
			spzt = "2,3,4,5";
		}
		
		int pageParam = Constant.NUM_1;
		int pageSizeParam = Constant.NUM_6;
		if(page>0&&pageSize>0&&pageSize>pageSizeParam) {
			pageParam = page;
			pageSizeParam = pageSize;
		}
		
		returnMap= dbywService.getAllSpInfoByDwbmGhOrBm(dlrDwbm, dlrBmbm, dlrGh, spzt, clnr, pageParam, pageSizeParam);
		return Constant.GSON.toJson(returnMap);
	}
	
}
