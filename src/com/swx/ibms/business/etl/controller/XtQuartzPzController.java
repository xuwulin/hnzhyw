package com.swx.ibms.business.etl.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.etl.bean.XtQuartzPz;
import com.swx.ibms.business.etl.service.XtQuartzPzService;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.common.utils.DateUtil;
import com.swx.ibms.common.utils.Identities;
import org.apache.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * 同步任务配置控制层
 * @author admin
 *
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/xtQuartzPz")
public class XtQuartzPzController {
	
	@Resource
	private XtQuartzPzService xtQuartzPzService;
	
	@RequestMapping(value = "/getXtQuartzPzPageList", method = RequestMethod.GET)
    @ResponseBody
	public String getXtQuartzPzPageList(@RequestParam(value="page",required=true)Integer page,
										@RequestParam(value="rows",required=true)Integer rows) {
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_8);
		map = xtQuartzPzService.getXtQuartzPzPageList(page, rows);
		return Constant.GSON.toJson(map);
	}
	
	
	@RequestMapping(value = "/getLastXtQuartzPz", method = RequestMethod.GET)
    @ResponseBody
	public String getLastXtQuartzPz(HttpRequest req) {
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_8);
		XtQuartzPz xtQuartzPz = xtQuartzPzService.getLastXtQuartzPz();
		map.put("data", xtQuartzPz);
		return Constant.GSON.toJson(map);
	}
	
	@RequestMapping(value = "/getXtQuartzPzById", method = RequestMethod.POST)
    @ResponseBody
	public String getXtQuartzPzById(@RequestParam(value="id",required=true)String id) {
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_8);
		XtQuartzPz xtQuartzPz = xtQuartzPzService.getXtQuartzPzById(id);
		map.put("data", xtQuartzPz);
		return Constant.GSON.toJson(map);
	}
	
	@RequestMapping(value = "/addXtQuartzPz", method = RequestMethod.POST)
    @ResponseBody
	public String addXtQuartzPz(XtQuartzPz xtQuartzPz,HttpServletRequest request) {
		RYBM rybm = (RYBM)request.getSession().getAttribute("ry");
		xtQuartzPz.setId(Identities.get32LenUUID());
		xtQuartzPz.setCjr(rybm.getDwbm()+"_"+rybm.getGh());
		xtQuartzPz.setCjrMc(rybm.getDlbm());
		xtQuartzPz.setCjsj(DateUtil.getDate4Str(DateUtil.getCurrDate("YYYY-MM-dd HH:mm:ss")));
		//将页面传来的日期格式字符串"2018-06-13 09:27:25"处理为同步任务能接受的表达式，年份不需要，暂定每年的创建日期进行同步。
		//秒 分 时 日 月 周/年;
		String cronStr = DateUtil.getQuartzCronByDateString(xtQuartzPz.getCron());
		xtQuartzPz.setCron(cronStr);
		Integer res = xtQuartzPzService.addXtQuartzPz(xtQuartzPz);
		return Constant.GSON.toJson(res);
	}
	
	@RequestMapping(value = "/modifyXtQuartzPz", method = RequestMethod.POST)
    @ResponseBody
	public String modifyXtQuartzPz(XtQuartzPz xtQuartzPz,HttpServletRequest request) {
		RYBM rybm = (RYBM)request.getSession().getAttribute("ry");
		xtQuartzPz.setCjr(rybm.getDwbm()+"_"+rybm.getGh());
		xtQuartzPz.setCjrMc(rybm.getDlbm());
		xtQuartzPz.setXgsj(DateUtil.getDate4Str(DateUtil.getCurrDate("YYYY-MM-dd HH:mm:ss")));
		//秒 分 时 日 月 周/年;
		String cronStr = DateUtil.getQuartzCronByDateString(xtQuartzPz.getCron());
		xtQuartzPz.setCron(cronStr);
		Integer res = xtQuartzPzService.modifyXtQuartzPz(xtQuartzPz);
		return Constant.GSON.toJson(res);
	}
	
	@RequestMapping(value = "/deleteXtQuartzPz", method = RequestMethod.POST)
    @ResponseBody
	public String deleteXtQuartzPz(@RequestParam(value="id",required=true)String id) {
		Integer res = xtQuartzPzService.deleteXtQuartzPz(id);
		return Constant.GSON.toJson(res);
	}
	
	
}
