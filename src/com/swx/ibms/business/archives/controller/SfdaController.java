package com.swx.ibms.business.archives.controller;

import com.swx.ibms.business.archives.bean.Count;
import com.swx.ibms.business.archives.bean.Sfda;
import com.swx.ibms.business.archives.bean.XtSfdaDaQuery;
import com.swx.ibms.business.archives.service.SfdaService;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.common.bean.EasyUIDatagrid;
import com.swx.ibms.common.utils.DateUtil;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;



/**
 * 司法档案内容Controller
 * @author 李佳
 * @date: 2017年02月25日
 */
@RequestMapping("/sfda")
@Controller
@SuppressWarnings("all")
public class SfdaController {

	/**
	 * 司法档案内容接口
	 * @author 李佳
	 */
	@Resource
	private SfdaService sfjnService;

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;

	private static final Logger LOG = LoggerFactory.getLogger(SfdaController.class);


	/**
	 * 根据档案类型和归档id查询司法档案数量
	 * @param request 请求参数
	 * @return 司法档案数量
	 */
	@RequestMapping(value = "/selectgdidsfdacount", method = RequestMethod.POST)
	public @ResponseBody String selectGdidSfdaCount(HttpServletRequest request) {
		String dalx = request.getParameter("dalx");   //档案类型
		String gdid = request.getParameter("gdid");   //归档id
		int count=0;
		try {
			count=sfjnService.selectGdidSfdaCount(gdid, dalx);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, e.toString());
		}
		return count+"";
	}
	/**
	 * 新增司法技能
	 * @param request 请求参数
	 * @return 是否添加成功 1 = 成功, 0 = 不成功
	 */
	@RequestMapping(value = "/insert",produces={"text/html;charset=UTF-8"},
			method = RequestMethod.POST)
	public @ResponseBody String insertSfjn(HttpServletRequest request) {
		Sfda sfjn = new Sfda();

		String lb = request.getParameter("lbSelect"); // 技能类别
		String ms = request.getParameter("ms"); // 技能描述
		String cjr = request.getParameter("cjr"); // 添加人
		String dalx = request.getParameter("dalx");
		String gdid = request.getParameter("gdid");   //归档id

		sfjn.setLb(lb);
		sfjn.setMs(ms);
		sfjn.setCjr(cjr);
		sfjn.setDalx(dalx);
		sfjn.setGdId(gdid);
		String result = "";
		try {
			result = sfjnService.insertSfjn(sfjn);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, ExceptionUtils.getStackTrace(e));
		}
		return result;
	}

	/**
	 * 修改司法技能
	 * @param request 请求参数
	 * @return 是否更新成功 1 = 成功, 0 = 不成功
	 */
	@RequestMapping(value = "/update",produces={"text/html;charset=UTF-8"},
			method = RequestMethod.POST)
	public @ResponseBody String updateSfjn(HttpServletRequest request) {

		Sfda sfjn = new Sfda();
		String id = request.getParameter("id");
		String lb = request.getParameter("lbSelect"); // 技能类别
		String ms = request.getParameter("ms"); // 技能描述
		String cjr = request.getParameter("cjr"); // 添加人
		String dalx = request.getParameter("dalx");
		String gdid = request.getParameter("gdid");   //归档id

		sfjn.setId(id);
		sfjn.setLb(lb);
		sfjn.setMs(ms);
		sfjn.setCjr(cjr);
		sfjn.setDalx(dalx);
		sfjn.setGdId(gdid);
		String count = "";
		Map<String,Object> map  = new HashMap<String,Object>();
		map.put("id", id);

		try {
			count = sfjnService.updateSfjn(sfjn);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, ExceptionUtils.getStackTrace(e));
		}
		map.put("success", count);
		return Constant.GSON.toJson(map);
	}

	/**
	 * 展示司法技能
	 * @param request 请求参数
	 * @return List<Map<String, Object>>
	 */
	@RequestMapping(value = "/show")
	public @ResponseBody String showSfjn(HttpServletRequest request) {

		String dalx = request.getParameter("dalx");
		String gdid=request.getParameter("gdid");
		String pageNumber = request.getParameter("pageNumber");  //页码，第几页
		if (StringUtils.isEmpty(pageNumber)||Integer.parseInt(pageNumber)<=0) {
			pageNumber = "1";
		}
		int pageNumber1 = Integer.parseInt(pageNumber);

		Sfda sfjn = new Sfda();
		sfjn.setDalx(dalx);
		sfjn.setGdId(gdid);

		PageCommon<Object> p=new PageCommon<Object>();
		int bottom = (pageNumber1-Constant.NUM_1)*Constant.NUM_6+Constant.NUM_1;//pageNumber1*p.getPageSize()-p.getPageSize();//开始页
		int top = pageNumber1*Constant.NUM_6;//pageNumber1*p.getPageSize();//结束页

		Map resultMap = null;
		try {
			resultMap=sfjnService.getSfjnList(sfjn,bottom,top);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, e.toString());
		}
		List<Sfda> list = (List<Sfda>) resultMap.get("Y");
		double total = (Double) resultMap.get("sum");
		int pageNo = (int) total / p.getPageSize();
		if(total%p.getPageSize()!=0){
			pageNo=pageNo + 1;
		}
		Map<String, Object> result = new HashMap<>();

		result.put("data", list);
		result.put("total", (int) total);
		result.put("pageNo", pageNo);
 		return Constant.GSON.toJson(result);

	}

	/**
	 * 删除司法技能
	 * @param request 请求参数
	 * @return 是否删除成功 1 = 成功, 0 = 不成功
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public @ResponseBody String delectSfjn(HttpServletRequest request) {

		Sfda sfjn = new Sfda();
		String id = request.getParameter("id");
		sfjn.setId(id);
		String count = "";
		try {
			count = sfjnService.deleteSfjn(sfjn);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, e.toString());
		}
		String deleteState = null;
		if (count.equals("1")) {
			deleteState = "删除成功！";
		} else {
			deleteState = "删除失败！";
		}
		return deleteState;
	}

	 /**
      * 根据归档id查询不同档案类型司法档案数量
	  * @param request 请求参数
	  * @return Map
	  */

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public @ResponseBody String countSfda(HttpServletRequest request) {

        Count count=new Count();
        String gdid=request.getParameter("gdid");

		Map resultMap = null;
		try {
			resultMap= sfjnService.countSfda(gdid);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, e.toString());
		}
		List<Count> list = (List<Count>) resultMap.get("count");
		Map<String, Object> result = new HashMap<>();
		for(Count e:list){
			if("1".equals(e.getDalx())){
				result.put("sfjnCount",e.getCount());
			}
			if("2".equals(e.getDalx())){
				result.put("sfzrCount",e.getCount());
			}
			if("3".equals(e.getDalx())){
				result.put("zycsCount",e.getCount());
			}
			if("4".equals(e.getDalx())){
				result.put("qtCount",e.getCount());
			}
		}
		return Constant.GSON.toJson(result);
	}


	/**
	 * 查询某单位某年的档案数量（柱状图的显示数据）
	 * @param request
	 * @param year 年份
	 * @exception Exception 异常
	 * @return 司法档案的数量结果集参数
	 */
	@RequestMapping(value = "/getSfdaCountBySign", method = RequestMethod.GET)
	@ResponseBody
	public String getSfdaCountByDwOrBm(/*@RequestParam(value="ldSign",required=true)String ldSign,*/
									   @RequestParam(value="year",required=false)String year,
									   @RequestParam(value="dwjb",required=true)String dwjb,
									   HttpServletRequest request) throws Exception {
		//说明：这儿本应该调用登录的接口去查询出当前登录人的角色名称，再根据角色名称查询是否为部门领导、院领导、分管领导，再取出单位编码或者部门编码

		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

		RYBM ry = (RYBM) request.getSession().getAttribute("ry");
		String dwbm = ry.getDwbm();//StringUtils.EMPTY;
//		String bmbm = ry.getBmbm();//StringUtils.EMPTY;
		String gh = ry.getGh();//工号
		String paramDwjb = dwjb;//单位级别
		String paramYear = StringUtils.EMPTY;

		if (StringUtils.isEmpty(year)) {
			paramYear += DateUtil.getYear(new Date());
		}else{
			paramYear = year;
		}

		/*if (StringUtils.isNotEmpty(ldSign)) {
			if("yld".equals(ldSign)||"bmld".equals(ldSign)
					||"ag".equals(ldSign)){//院领导看单位下所有部门的档案数量
				//dwbm = ry.getDwbm();
				if (StringUtils.isEmpty(year)) {
					paramYear += DateUtil.getYear(new Date());
				}else{
					paramYear = year;
				}
			}else{//其他普通人看本部门的档案数量
				dwbm = ry.getDwbm();
				bmbm = ry.getBmbm();
			}*/
			list = sfjnService.getSfdaCountByDwOrBm(dwbm,gh,paramYear,paramDwjb);
//		}
		return Constant.GSON.toJson(list);
	}


	/**
	 * 查询某单位某年的司法责任、荣誉技能数量
	 * @param year 年份
	 * @param request 请求对象
	 * @return json字符串
	 * @throws Exception 异常
	 */
	@RequestMapping(value = "/getRyjnSfzrCount", method = RequestMethod.GET)
	@ResponseBody
	public String getRyjnSfzrCount(@RequestParam(value="year",required=false)String year,
						           @RequestParam(value="dwjb",required=true)String dwjb,
									   HttpServletRequest request) throws Exception {
		//说明：这儿本应该调用登录的接口去查询出当前登录人的角色名称，再根据角色名称查询是否为部门领导、院领导、分管领导，再取出单位编码或者部门编码

		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

		RYBM ry = (RYBM) request.getSession().getAttribute("ry");
		String dwbm = ry.getDwbm();
		String bmbm = ry.getBmbm();
		String paramYear = StringUtils.EMPTY;

		String gh = ry.getGh();//工号
		String paramDwjb = dwjb;//单位级别

		if (StringUtils.isEmpty(year)) {
			paramYear += DateUtil.getYear(new Date());
		}else{
			paramYear = year;
		}

		list = sfjnService.getRyjnSfzrCount(dwbm, gh,paramYear,paramDwjb);
		return Constant.GSON.toJson(list);
	}


	/**
	 * 查询某人文化程度
	 * @param year 年份
	 * @param request 请求对象
	 * @return json字符串
	 * @throws Exception 异常
	 */
	@RequestMapping(value = "/getRyWhcdByDwGh", method = RequestMethod.GET)
	@ResponseBody
	public String getRyWhcdByDwGh(@RequestParam(value="year",required=false)String year,
						           @RequestParam(value="dwjb",required=true)String dwjb,
									   HttpServletRequest request) throws Exception {
		//说明：这儿本应该调用登录的接口去查询出当前登录人的角色名称，再根据角色名称查询是否为部门领导、院领导、分管领导，再取出单位编码或者部门编码

		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

		RYBM ry = (RYBM) request.getSession().getAttribute("ry");
		String dwbm = ry.getDwbm();
		String bmbm = ry.getBmbm();
		String paramYear = StringUtils.EMPTY;

		String gh = ry.getGh();//工号
		String paramDwjb = dwjb;//单位级别

		if (StringUtils.isEmpty(year)) {
			paramYear += DateUtil.getYear(new Date());
		}else{
			paramYear = year;
		}

		list = sfjnService.getRyWhcdByDwGh(dwbm, gh,paramYear,paramDwjb);
		return Constant.GSON.toJson(list);
	}


	/**
	 * 根据传入日期格式类型标示返回不同的服务器日期格式
	 * @param formatType 日期格式类型标示
	 * @param request 请求对象
	 * @return 服务器日期格式
	 * @throws Exception 异常
	 */
	@RequestMapping(value = "/getNowDate", method = RequestMethod.GET)
	@ResponseBody
	public String getNowDate(@RequestParam(value="formatType",required=false)String formatType,
								HttpServletRequest request) throws Exception {

		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_5);

		Date date = new Date();
		//默认 ：年月日   1 ：年    2： 年月日时分秒
		if("1".equals(formatType)){
			map.put("year", DateUtil.getYear(date));
		}else if("2".equals(formatType)){
			map.put("nyrsfm", DateUtil.getSysCurrentDateTime());
		}else{
			map.put("nyr", DateUtil.getSysCurrentDate());
		}
		return Constant.GSON.toJson(map);
	}


	/**
	 * 查询旧的司法档案
	 * @throws Exception
	 */
	@RequestMapping("/selectOldData")
	@ResponseBody
	public Map<String, Object> selectOldData(XtSfdaDaQuery query) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EasyUIDatagrid<Map<String, Object>> easy = sfjnService.selectOldData(query);
			map.put("data", easy);
			map.put("size", easy.getTotal());
			return map;
		} catch (Exception e) {
			LOG.warn("查询档案失败", e);
			throw e;
		}
	}
}
