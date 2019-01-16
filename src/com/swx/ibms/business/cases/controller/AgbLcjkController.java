package com.swx.ibms.business.cases.controller;

import com.swx.ibms.business.cases.bean.AgbLcjk;
import com.swx.ibms.business.cases.service.AgbLcjkService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;



/**
*案管办流程监控
*@version 1.0
*@since 2018年4月26日
*/
@RestController
@RequestMapping("agbLcjk")
public class AgbLcjkController {
	
	@Autowired
	private AgbLcjkService agbLcjkService;

	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(AgbLcjkController.class);
	
	/**
	 * 通过流程监控id获取案件受理详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getLcjkById")
	@ResponseBody
	public Map<String,Object> getLcjkById(String id, String dwbm, String gh) throws Exception{
		try {
			 AgbLcjk lcjk = agbLcjkService.getLcjkById(id, dwbm, gh);
			 Map<String,Object> map = new HashMap<>();
			 map.put("lcjk", lcjk);
			return map;
		} catch (Exception e) {
			LOG.error("获取流程监控详情失败" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 获取包含统一业务的案件信息和案管办流程监控的信息
	 * @param did 档案id
	 * @param blxs 办理形式
	 * @param dwbm 单位编码
	 * @param gh  工号
	 * @param page 页码
	 * @param pageSize 页大小
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getLcjkAndTyywAjjbxx")
	@ResponseBody
	public Map<String,Object> getLcjkAndTyywAjjbxx( @RequestParam(value="daid",required=true)String daid,
													@RequestParam(value="blxs",required=true)String blxs,
													@RequestParam(value="dwbm",required=true)String dwbm,
													@RequestParam(value="gh",required=true)String gh,
													@RequestParam(value="page",required=true)Integer page,
													@RequestParam(value="pageSize",required=true)Integer pageSize) throws Exception{
		try {
			Map<String, Object> map = agbLcjkService.getLcjkAndTyywAjjbxx(daid, blxs, dwbm, gh, page, pageSize);
			return map;
		} catch (Exception e) {
			LOG.error("获取案管办流程监控失败" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 根据id查询案件受理用于表单回显
	 * @throws Exception
	 */
	@RequestMapping("/getLcjk")
	@ResponseBody
	public Map<String, Object> getLcjk(@RequestParam(value="id",required=true)String id) throws Exception{
		try {
			Map<String, Object> lcjkMap = agbLcjkService.getLcjk(id);
			return lcjkMap;
		} catch (Exception e) {
			LOG.error("获取案管办案件受理失败" + e.getMessage());
		}
		return null;
	}
	
}
