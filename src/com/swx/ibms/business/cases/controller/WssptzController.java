package com.swx.ibms.business.cases.controller;


import com.swx.ibms.business.cases.service.WssptzService;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.utils.Constant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 *@author:徐武林
 *@date:2018年5月14日上午11:32:53
 *@Description:
 */
@SuppressWarnings("all")
@RequestMapping("/wssptz")
@Controller
public class WssptzController {

	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(WssptzController.class);

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;

	/**
	 * 获取部门受案号和统一受案号
	 */
	@Autowired
	private WssptzService wssptzService;

	@RequestMapping("/selectALLWssptz")
	@ResponseBody
	public Map<String, Object> selectBmsahAndTysah(@RequestParam(value="dwbm",required=true)String dwbm,
												   @RequestParam(value="gh",required=true)String gh,
												   @RequestParam(value="ksrq",required=true)String ksrq,
												   @RequestParam(value="jsrq",required=true)String jsrq,
												   @RequestParam(value="bmsah",required=false)String bmsah,
												   @RequestParam(value="tysah",required=false)String tysah,
												   @RequestParam(value="page",required=true)Integer page,
												   @RequestParam(value="rows",required=true)Integer pageSize) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String,Object> wsDetail = wssptzService.getWsDetail(dwbm, gh, page, pageSize, ksrq, jsrq, bmsah, tysah);
			return wsDetail;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("获取文书审批台账失败" + e.getMessage());
		}
		return null;
	}

	/**
	 * 根据部门受案号和统一受案号获取案件名称
	 * @param bmsah
	 * @param tysah
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAjmcByBmsahAndTysah")
	@ResponseBody
	public String getAjmcByBmsahAndTysah(@RequestParam(value="bmsah",required=true)String bmsah,
										 @RequestParam(value="tysah",required=true)String tysah) throws Exception{

		String ajmc = wssptzService.getAjmcByBmsahAndTysah(bmsah,tysah);
		return Constant.GSON.toJson(ajmc);
	}

	@RequestMapping("/selectSpaj")
	@ResponseBody
	public Map<String, Object> selectSpaj(@RequestParam(value="dwbm",required=true)String dwbm,
										  @RequestParam(value="gh",required=true)String gh,
										  @RequestParam(value="ksrq",required=true)String ksrq,
										  @RequestParam(value="jsrq",required=true)String jsrq,
										  @RequestParam(value="page",required=true)Integer page,
										  @RequestParam(value="rows",required=true)Integer pageSize) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String,Object> wsDetail = wssptzService.getSpaj(dwbm, gh, page, pageSize, ksrq, jsrq);
			return wsDetail;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("获取文书审批台账失败" + e.getMessage());
		}
		return null;
	}

}
