package com.swx.ibms.business.performance.controller;


import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.performance.bean.GRJX;
import com.swx.ibms.business.performance.service.GrjxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * 个人绩效控制器
 * 
 * @author 李治鑫
 *
 */
@RequestMapping("/grjx")
@Controller
public class GrjxController {
	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(GrjxController.class);


	/**
	 * 静态json 用于存放考评指标
	 */
	private static  String json = null;
	/**
	 * 系统管理服务接口
	 */
	@Resource
	private GrjxService grjxService;
//	@Resource
//	private HCPZService hcpzservice;


	
	/**
	 * 插入或更新个人绩效每个基础分
	 * @param request 请求参数
	 * @return null
	 */
	@RequestMapping("/inOrUpGrjxZgjcf")
	public @ResponseBody
    String inOrUpGrjxZgjcf(HttpServletRequest request) {
		return null;
	}
	/**
	 * 通过档案ID获取个人绩效
	 * @param request 请求参数
	 * @return 个人绩效信息
	 */
	@RequestMapping("/getgrjxbydaid")
	public @ResponseBody
    String getGrjxByDaid(HttpServletRequest request) {

		String daid = request.getParameter("daid");// 档案ID

		List<GRJX> grjx = null;
		try {
			grjx = grjxService.getGrjxByDaid(daid);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		if (!CollectionUtils.isNotEmpty(grjx)) {// 如果没有个人绩效信息，则生成并添加一个个人绩效信息
			addGrjx(request);
			grjx = grjxService.getGrjxByDaid(daid);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("rows", Constant.JSON_PARSER.parse(grjx.get(0).getZbkpgl()));
		return Constant.GSON.toJson(resultMap);
	}

	/**添加个人绩效信息
	 * @param request 请求参数
	 * @return 执行信息
	 */
	@RequestMapping("/addgrjx")
	public @ResponseBody
    String addGrjx(HttpServletRequest request) {

		String daid = request.getParameter("daid");// 档案ID
		String ywlx = request.getParameter("ywlx");// 业务类型
		String ywzfstr = request.getParameter("ywzf");// 业务总分
		double ywzf = 0;// 业务总分
		if (ywzfstr != null && !"".equals(ywzfstr) ) {
			ywzf = Double.parseDouble(ywzfstr);
		}
		String zbkpgl = request.getParameter("zbkpgl");// 指标考评概览

		int msg = 0;

		URL url = Thread.currentThread().getContextClassLoader().getResource("initJson.txt");

		try {
			List<String> lines = Files.readAllLines(Paths.get(url.toURI()));
			String jsonValue = String.join(StringUtils.EMPTY, lines);
			if (Objects.isNull(json)) {
				json = jsonValue;
			}
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		if (ywzf == 0) {
			// 新增请求
			zbkpgl = json;
			GRJX grjxObject = new GRJX();
			grjxObject.setDaid(daid);
			grjxObject.setYwlx(ywlx);
			grjxObject.setYwzf(ywzf);
			grjxObject.setZbkpgl(zbkpgl);
			List<GRJX> grjx = new ArrayList<GRJX>();
			try {
				grjx.add(grjxObject);
			} catch (Exception e2) {
				LOG.error("新增请求时初始化个人绩效列表错误 ，很可能为空值");
			}
			try {
				grjxService.conform(grjx);
			} catch (Exception e1) {
				LOG.error("个人绩效整合失败");
			}
			grjxObject = grjx.get(0);
			try {
				msg = grjxService.addGrjx(grjxObject);

			} catch (Exception e) {
				LOG.error(StringUtils.EMPTY, e);
			}
		} else {
			updateGrjx(request);
		}

		if (msg > 0) {
			return "添加成功";
		} else {
			return "添加失败";
		}

	}

	/**
	 * 更新个人绩效信息
	 * @param request 请求参数
	 * @return 执行信息
	 */
	@RequestMapping(value = "/updategrjx", method = RequestMethod.POST)
	public @ResponseBody
    String updateGrjx(HttpServletRequest request) {

		String daid = request.getParameter("daid");// 档案ID
		String ywlx = request.getParameter("ywlx");// 业务类型
		String zbkpgl = request.getParameter("zbkpgl");// 指标考评概览
		double ywzf = Double.parseDouble(request.getParameter("ywzf"));// 业务总分

		GRJX grjxObject = new GRJX();
		grjxObject.setDaid(daid);
		grjxObject.setYwlx(ywlx);
		grjxObject.setYwzf(ywzf);
		grjxObject.setZbkpgl(zbkpgl);

		int msg = 0;
		try {
			msg = grjxService.updateGrjx(grjxObject);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		if (msg > 0) {
			return "更新成功";
		} else {
			return "更新失败";
		}

	}

	/**
	 * 删除个人绩效
	 * @param request 请求参数
	 * @return 执行信息
	 */
	@RequestMapping("/deletegrjx")
	public @ResponseBody
    String deleteGrjx(HttpServletRequest request) {

		String daid = request.getParameter("daid");// 档案ID
		int msg = 0;
		try {
			msg = grjxService.deleteGrjx(daid);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		if (msg > 0) {
			return "删除成功";
		} else {
			return "删除失败";
		}

	}
//	/**
//	 * 查询个人绩效最高基础分数据
//	 */
//	@RequestMapping("/showgrjxzgjcflist")
//	@ResponseBody
//	public String showGrjxZgjcfList(HttpServletRequest request){
//		String dwbm=request.getParameter("ssrdwbm");
//		String dwbmtwo=dwbm.substring(0,2);
//		String dwjb = (String) hcpzservice.getdwjb(dwbm).get("p_dwjb"); // 单位级别
//		String yearStr=request.getParameter("year");
//		String jdStr=request.getParameter("jd");
//		String ywlx=request.getParameter("ywlx");
//		String ydkhid=request.getParameter("ydkhid");
//		int year=-1;
//		if(StringUtils.isNotBlank(yearStr)){
//			year=Integer.parseInt(yearStr);
//		}
//		int jd=-1;
//		if(StringUtils.isNotBlank(jdStr)){
//			jd=Integer.parseInt(jdStr);
//		}
//		//是否全部通过
//		String sfqbtg=grjxService.grjxAllRSpTg(year, jd, ywlx, dwjb, dwbmtwo);
//		List<GrjxZgjcf> list=new ArrayList<>();
//		if("1".equals(sfqbtg)){
//			list=grjxService.showGrjxZgjcfList(ydkhid, year, jd, ywlx, dwjb);
//		}
//		return Constant.GSON.toJson(list);
//	}
}
