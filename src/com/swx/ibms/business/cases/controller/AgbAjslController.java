package com.swx.ibms.business.cases.controller;

import com.swx.ibms.business.cases.bean.AgbAjsl;
import com.swx.ibms.business.cases.service.AgbAjslService;
import com.swx.ibms.business.cases.service.AgbLcjkService;
import com.swx.ibms.business.cases.service.AgbZlpcService;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.utils.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;



/**
*案管办案件受理
*@version 1.0
*@since 2018年4月26日
*/
@RestController
@RequestMapping("agbAjsl")
public class AgbAjslController  {
	
	@Autowired
	private AgbAjslService agbAjslService;
	
	@Autowired
	private AgbLcjkService agbLcjkService;
	
	@Autowired
	private AgbZlpcService agbZlpcService;
	
	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(AgbAjslController.class);

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;
	
	/**
	 * 根据档案id获取案件受理，流程监控，质量评查更正信息 
	 * @param daid
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getCorrectByDid")
	@ResponseBody
	public Map<String, Object> getCorrectByDid(String daid,String dwbm, String gh, String zhgxr,String page,String pageSize) throws Exception{
		
		int pg = 0;
		int pgSize = 0;
		if(StringUtils.isEmpty(page) || StringUtils.isEmpty(pageSize)) {
//			pg = super.parsePage();
//			pgSize = super.parseRows();
		}else {
			pg = Integer.parseInt(page);
			pgSize = Integer.parseInt(pageSize);
		}
		try {
			Map<String, Object> map = agbAjslService.getCorrectByDid(daid,dwbm, gh, zhgxr, pg, pgSize);
			return map;
		} catch (Exception e) {
			LOG.error("获取案件受理，流程监控，质量评查更正信息失败"+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 通过案件受理id获取案件受理详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAjslById")
	@ResponseBody
	public Map<String, Object> getAjslById(String id, String dwbm, String gh) throws Exception{
		try {
			 AgbAjsl ajsl = agbAjslService.getAjslById(id, dwbm, gh);
			 Map<String, Object> map = new HashMap<>();
			 map.put("ajsl", ajsl);
			return map;
		} catch (Exception e) {
			LOG.error("获取案件受理详情失败" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 获取包括案件受理，流程监控，质量评查 的办理形式数的集合
	 * @param daid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAllBlxsCount")
	@ResponseBody
	public Map<String,Object> getAllCountByBlxs(@RequestParam(value="daid",required=true)String daid,
												@RequestParam(value="dwbm",required=true)String dwbm,
												@RequestParam(value="gh",required=true)String gh,
												@RequestParam(value="bmbm",required=true)String bmbm,
												@RequestParam(value="kssj",required=true)String kssj,
												@RequestParam(value="jssj",required=true)String jssj) throws Exception{
		try {
			/**
			 * 案件受理
			 */
			List<Map<String, Object>> ajsl = agbAjslService.getCountByBlxs(daid,dwbm,gh);//根据档案id获取办理形式的数量 [{AMOUNT=2, BLXS=11005}]参与协助
			Map<String, Object> ajslAvg = agbAjslService.getCaseAvg(dwbm, bmbm,kssj,jssj);//获取该部门下平均案件数量 -->{AVG=0}
			Map<String, Object> ajslMaxAndMin = agbAjslService.getCaseMaxAndMin(dwbm, bmbm,kssj,jssj);//获取该部门下最多和最少办案数 {MAXNUM=0, MINNUM=0}
			Map<String, Object> ajslrank = agbAjslService.getRank(dwbm, bmbm, daid,kssj,jssj);//获取该部门下办案数名次 {RANK=1}
			
			Map<String, Object> ajslMap = new HashMap<>();
			ajslMap.put("list", ajsl);
			if(!Objects.isNull(ajslAvg)) {
				ajslMap.put("avg", String.format("%.2f", ajslAvg.get("AVG")));
			}
			if(!Objects.isNull(ajslMaxAndMin)) {
				ajslMap.put("maxNum", ajslMaxAndMin.get("MAXNUM"));
				ajslMap.put("minNum", ajslMaxAndMin.get("MINNUM"));
			}
			if(!Objects.isNull(ajslrank)) {
				ajslMap.put("rank", ajslrank.get("RANK"));
			}
			
			/**
			 * 流程监控
			 */
			List<Map<String, Object>> lcjk = agbLcjkService.getCountByBlxs(daid,dwbm,gh);//[{AMOUNT=1, BLXS=11001}, {AMOUNT=10, BLXS=11008}]
			Map<String, Object> lcjkAvg = agbLcjkService.getCaseAvg(dwbm, bmbm,kssj,jssj);
			Map<String, Object> lcjkMaxAndMin = agbLcjkService.getCaseMaxAndMin(dwbm, bmbm,kssj,jssj);
			Map<String, Object> lcjkrank = agbLcjkService.getRank(dwbm, bmbm, daid,kssj,jssj);
			
			Map<String, Object> lcjkMap = new HashMap<>();
			lcjkMap.put("list", lcjk);
			if(!Objects.isNull(lcjkAvg)) {
				lcjkMap.put("avg", String.format("%.2f", lcjkAvg.get("AVG")));
			}
			if(!Objects.isNull(lcjkMaxAndMin)) {
				lcjkMap.put("maxNum", lcjkMaxAndMin.get("MAXNUM"));
				lcjkMap.put("minNum", lcjkMaxAndMin.get("MINNUM"));
			}
			if(!Objects.isNull(lcjkrank)) {
				lcjkMap.put("rank", lcjkrank.get("RANK"));
			}
			
			/**
			 * 质量评查
			 */
			List<Map<String, Object>> zlpc = agbZlpcService.getCountByBlxs(daid, dwbm, gh);//getCountByBlxs
			Map<String, Object> zlpcAvg = agbZlpcService.getCaseAvg(dwbm, bmbm,kssj,jssj);//{AVG=0}
			Map<String, Object> zlpcMaxAndMin = agbZlpcService.getCaseMaxAndMin(dwbm, bmbm,kssj,jssj);//{MAXNUM=0, MINNUM=0}
			Map<String, Object> zlpcrank = agbZlpcService.getRank(dwbm, bmbm, daid,kssj,jssj);//null
			
			Map<String, Object> zlpcMap = new HashMap<>();
			zlpcMap.put("list", zlpc);
			if(!Objects.isNull(zlpcAvg)) {
				zlpcMap.put("avg", String.format("%.2f", zlpcAvg.get("AVG")));
			}
			if(!Objects.isNull(zlpcMaxAndMin)) {
				zlpcMap.put("maxNum", zlpcMaxAndMin.get("MAXNUM"));
				zlpcMap.put("minNum", zlpcMaxAndMin.get("MINNUM"));
			}
			if(!Objects.isNull(zlpcrank)) {
				zlpcMap.put("rank", zlpcrank.get("RANK"));
			}
			
			List<Map<String, Object>> list= new ArrayList<>();
			Map<String, Object> map = new HashMap<>();
			list.add(ajslMap);
			list.add(lcjkMap);
			list.add(zlpcMap);
			map.put("data", list);
			return map;
		} catch (Exception e) {
			LOG.error("根据档案id获取办理形式获取数量失败" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 获取包含统一业务的案件信息和案管办案件受理的信息
	 * @param daid 档案id
	 * @param blxs 办理形式
	 * @param dwbm 单位编码
	 * @param gh  工号
	 * @param page 页码
	 * @param pageSize 每页大小
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAjslAndTyywAjjbxx")
	@ResponseBody
	public Map<String, Object> getAjslAndTyywAjjbxx(@RequestParam(value="daid",required=true)String daid,
													@RequestParam(value="blxs",required=true)String blxs,
													@RequestParam(value="dwbm",required=true)String dwbm,
													@RequestParam(value="gh",required=true)String gh,
													@RequestParam(value="page",required=true)Integer page,
													@RequestParam(value="pageSize",required=true)Integer pageSize) throws Exception{
		try {
			Map<String, Object> map = agbAjslService.getAjslAndTyywAjjbxx(daid, blxs, dwbm, gh, page, pageSize);
			return map;
		} catch (Exception e) {
			LOG.error("获取案管办案件受理失败" + e.getMessage());
		}
		return null;
	}
	
	/***
	 * 根据档案id,是否删除,展示所有（案件受理，流程监控，质量评查）新增列表中的数据
	 * @param daid
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectNewagbajByDid")
	@ResponseBody
	public Map<String, Object> selectNewagbajByDid(@RequestParam(value="daid",required=true)String daid,
												   @RequestParam(value="page",required=true)Integer page,
												   @RequestParam(value="pageSize",required=true)Integer pageSize) throws Exception{
		try {
			Map<String, Object> ajslMap = agbAjslService.selectALLNewAgbajByDid(daid, page, pageSize);
			return ajslMap;
		} catch (Exception e) {
			LOG.error("获取案管办案件新增列表失败" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 添加案管办案件
	 */
	@RequestMapping(value = "/addAgbAj",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addAgbAj(HttpServletRequest req) throws Exception{
		Map<String,Object> map = new HashMap<>();
		//共用
		String did = req.getParameter("ag_did_add");
		String ywlb = req.getParameter("ag_ywlb_add");
		String ajmc = req.getParameter("ag_ajmc_add");
		
		String result = "";
		try {
			if(ywlb.equals("2501")){
				//案件受理
				String slsj = req.getParameter("ajsl_slsj_add");
				String flsj = req.getParameter("ajsl_flsj_add");
				String lrry = req.getParameter("ajsl_lrry_add");
				String shry = req.getParameter("ajsl_shry_add");
				int jzcs = Integer.parseInt(req.getParameter("ajsl_jzcs_add"));
				int gp = Integer.parseInt(req.getParameter("ajsl_gp_add"));
				String sacw = req.getParameter("ajsl_sacw_add");
				String ajsl_blxs = req.getParameter("ajsl_blxs_add");
				String cbxz = req.getParameter("ajsl_cbxz_add");
				String cbxzbm = req.getParameter("ajsl_cbxzbm_add");
				
				result = agbAjslService.addAgbAjsl(did,ajmc,ywlb,cbxzbm,slsj,flsj,lrry,shry,jzcs,gp,sacw,ajsl_blxs,cbxz);
			}else if(ywlb.equals("2502")){
				//流程监控
				String jkrq = req.getParameter("lcjk_jkrq_add");
				String jkr = req.getParameter("lcjk_jkr_add");
				String jkfs = req.getParameter("lcjk_jkfs_add");
				String lcjk_blxs = req.getParameter("lcjk_blxs_add");
				int wtgs = Integer.parseInt(req.getParameter("lcjk_wtgs_add"));
				String lcjk_cbxz = req.getParameter("lcjk_cbxz_add");
				String jknr = req.getParameter("lcjk_jknr_add");
				String cbxzbm = req.getParameter("lcjk_cbxzbm_add");
				result = agbLcjkService.addAgbLcjk(did,ajmc,ywlb,cbxzbm,jkrq,jkr,jkfs,lcjk_blxs,wtgs,lcjk_cbxz,jknr);
			}else{
				//质量评查
				String pczl = req.getParameter("zlpc_pczl_add");
				String zlpc_blxs = req.getParameter("zlpc_blxs_add");
				String pckssj = req.getParameter("zlpc_pckssj_add");
				String pcjssj = req.getParameter("zlpc_pcjssj_add");
				int pcwtgs = Integer.parseInt(req.getParameter("zlpc_pcwtgs_add"));
				String pcjgxs = req.getParameter("zlpc_pcjgxs_add");
				String zgqk = req.getParameter("zlpc_zgqk_add");
				String zlpc_cbxz = req.getParameter("zlpc_cbxz_add");
				String cbxzbm = req.getParameter("zlpc_cbxzbm_add");
				result = agbZlpcService.addAgbZlpc(did,ajmc,ywlb,cbxzbm,pczl,zlpc_blxs,pckssj,pcjssj,pcwtgs,pcjgxs,zgqk,zlpc_cbxz);
			}
			
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, ExceptionUtils.getStackTrace(e));
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 删除案管办案件
	 */
	@RequestMapping(value = "/deleteAgbAj",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteAgbAj(HttpServletRequest req) throws Exception{
		Map<String,Object> map = new HashMap<>();
		String ywlb = req.getParameter("ywlb");
		String id = req.getParameter("id");
		String result = "";
		try {
			if(ywlb.equals("2501")){
				result = agbAjslService.deleteAgbAjsl(id);
			}else if(ywlb.equals("2502")){
				result = agbLcjkService.deleteAgbLcjk(id);
			}else{
				result = agbZlpcService.deleteAgbZlpc(id);
			}
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, ExceptionUtils.getStackTrace(e));
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 修改案管办案件
	 */
	@RequestMapping(value = "/updateAgbAj",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateAgbAj(HttpServletRequest req) throws Exception{
		Map<String,Object> map = new HashMap<>();
		//共用
		String id = req.getParameter("ag_id_add");
		String ywlb = req.getParameter("ag_ywlb_add");
		String ajmc = req.getParameter("ag_ajmc_add");
		
		String result = "";
		try {
			if(ywlb.equals("2501")){
				//案件受理
				String slsj = req.getParameter("ajsl_slsj_add");
				String flsj = req.getParameter("ajsl_flsj_add");
				String lrry = req.getParameter("ajsl_lrry_add");
				String shry = req.getParameter("ajsl_shry_add");
				int jzcs = Integer.parseInt(req.getParameter("ajsl_jzcs_add"));
				int gp = Integer.parseInt(req.getParameter("ajsl_gp_add"));
				String sacw = req.getParameter("ajsl_sacw_add");
				String ajsl_blxs = req.getParameter("ajsl_blxs_add");
				String cbxz = req.getParameter("ajsl_cbxz_add");
				String cbxzbm = req.getParameter("ajsl_cbxzbm_add");
				result = agbAjslService.updateAgbAjslById(id, ajmc, ywlb, cbxzbm, slsj, flsj, lrry, shry, jzcs, gp, sacw, ajsl_blxs, cbxz);
			}else if(ywlb.equals("2502")){
				//流程监控
				String jkrq = req.getParameter("lcjk_jkrq_add");
				String jkr = req.getParameter("lcjk_jkr_add");
				String jkfs = req.getParameter("lcjk_jkfs_add");
				String lcjk_blxs = req.getParameter("lcjk_blxs_add");
				int wtgs = Integer.parseInt(req.getParameter("lcjk_wtgs_add"));
				String lcjk_cbxz = req.getParameter("lcjk_cbxz_add");
				String jknr = req.getParameter("lcjk_jknr_add");
				String cbxzbm = req.getParameter("lcjk_cbxzbm_add");
				result = agbLcjkService.updateAgbLcjkById(id, ajmc, ywlb, cbxzbm, jkrq, jkr, jkfs, lcjk_blxs, wtgs, lcjk_cbxz, jknr);
			}else{
				//质量评查
				String pczl = req.getParameter("zlpc_pczl_add");
				String zlpc_blxs = req.getParameter("zlpc_blxs_add");
				String pckssj = req.getParameter("zlpc_pckssj_add");
				String pcjssj = req.getParameter("zlpc_pcjssj_add");
				int pcwtgs = Integer.parseInt(req.getParameter("zlpc_pcwtgs_add"));
				String pcjgxs = req.getParameter("zlpc_pcjgxs_add");
				String zgqk = req.getParameter("zlpc_zgqk_add");
				String zlpc_cbxz = req.getParameter("zlpc_cbxz_add");
				String cbxzbm = req.getParameter("zlpc_cbxzbm_add");
				result = agbZlpcService.updateAgbZlpcById(id, ajmc, ywlb, cbxzbm, pczl, zlpc_blxs, pckssj, pcjssj, pcwtgs, pcjgxs, zgqk, zlpc_cbxz);
			}
			
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, ExceptionUtils.getStackTrace(e));
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 根据id查询案件受理用于表单回显
	 * @throws Exception
	 */
	@RequestMapping("/getAjsl")
	@ResponseBody
	public Map<String, Object> getAjsl(@RequestParam(value="id",required=true)String id) throws Exception{
		try {
			Map<String, Object> ajslMap = agbAjslService.getAjsl(id);
			return ajslMap;
		} catch (Exception e) {
			LOG.error("获取案管办案件受理失败" + e.getMessage());
		}
		return null;
	}
	
}
