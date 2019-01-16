package com.swx.ibms.business.cases.controller;

import com.swx.ibms.business.cases.bean.AgbZlpc;
import com.swx.ibms.business.cases.service.AgbZlpcService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
*案管办质量评查
*@version 1.0
*@since 2018年4月26日
*/
@RestController
@RequestMapping("agbZlpc")
public class AgbZlpcController {

	@Autowired
	private AgbZlpcService agbZlpcService;

	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(AgbLcjkController.class);
	
	@RequestMapping("/getAllZlpcByBlxs")
	@ResponseBody
	public Map<String,Object> getAllZlpcByBlxs(@RequestParam(value="daid",required=true)String daid,
											   @RequestParam(value="blxs",required=true)String blxs,
											   @RequestParam(value="dwbm",required=true)String dwbm,
											   @RequestParam(value="gh",required=true)String gh,
											   @RequestParam(value="page",required=true)Integer page,
											   @RequestParam(value="pageSize",required=true)Integer pageSize) throws Exception{
		try {
			Map<String, Object> allZlpcByBlxs = agbZlpcService.getAllZlpcByBlxs(daid,blxs, dwbm, gh, page, pageSize);
			return allZlpcByBlxs;
		} catch (Exception e) {
			LOG.error("根据办理形式获取质量评查记录失败");
		}
		return null;
	}
	
	/**
	 * 通过质量评查id获取案件受理详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getZlpcById")
	public Map<String,Object> getZlpcById(String id, String dwbm, String gh) throws Exception{
		try {
			 AgbZlpc zlpc = agbZlpcService.getZlpcById(id, dwbm, gh);
			 Map<String,Object> map = new HashMap<>();
			 map.put("zlpc", zlpc);
			return map;
		} catch (Exception e) {
			LOG.error("获取质量评查详情失败" + e.getMessage());
		}
		return null;
	}
	
	@RequestMapping("/getBlxsCount")
	@ResponseBody
	public Map<String,Object> getCountByBlxs(String did, String dwbm, String gh) throws Exception{
		try {
			List<Map<String, Object>> countByBlxs = agbZlpcService.getCountByBlxs(did, dwbm, gh);
			Map<String,Object> map = new HashMap<>();
			map.put("countByBlxs", countByBlxs);
			return map;
		} catch (Exception e) {
			LOG.error("根据办理形式获取数量失败");
		}
		return null;
	}
	
	/**
	 * 根据id查询案件受理用于表单回显
	 * @throws Exception
	 */
	@RequestMapping("/getZlpc")
	@ResponseBody
	public Map<String, Object> getZlpc(@RequestParam(value="id",required=true)String id) throws Exception{
		try {
			Map<String, Object> lcjkMap = agbZlpcService.getZlpc(id);
			return lcjkMap;
		} catch (Exception e) {
			LOG.error("获取案管办案件受理失败" + e.getMessage());
		}
		return null;
	}
	
}
