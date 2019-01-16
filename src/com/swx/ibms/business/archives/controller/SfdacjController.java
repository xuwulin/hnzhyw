/**
 *
 */
package com.swx.ibms.business.archives.controller;

import com.swx.ibms.business.archives.bean.DAGZGD;
import com.swx.ibms.business.archives.service.DAGZService;
import com.swx.ibms.business.archives.service.SfdacjService;
import com.swx.ibms.business.cases.bean.DACJAJXYRXX;
import com.swx.ibms.business.cases.bean.JCGDAAJZL;
import com.swx.ibms.business.cases.bean.SFDAAJ;
import com.swx.ibms.business.common.bean.Splcsl;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.RYBM;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * @author 封泽超
 * @since 2017年2月28日
 */

@RequestMapping("/sfdacj")
@Controller
public class SfdacjController {
	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(SfdacjController.class);

	/**
	 * 司法档案服务对象
	 */
	@Resource
	private SfdacjService sfdacjService;

	/**
	 * 档案归总
	 */
	@Resource
	private DAGZService dagzService;

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;

	/**
	 * 通过工号和单位编码、档案开始时间、档案结束时间查询承办案件列表
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/cbajlist", method = RequestMethod.POST)
	public @ResponseBody String getCbajList(HttpServletRequest request) {

		// 查询归档信息
		String gdid = request.getParameter("gdid");
		DAGZGD dagzgd = new DAGZGD();
		dagzgd.setId(gdid);
		List<DAGZGD> list1 = sfdacjService.selectDagzId(dagzgd);
		if (CollectionUtils.isEmpty(list1)) {
			return null;
		}
		//查询承办案件信息
		String cbrgh = list1.get(0).getSsr(); // 承办人工号
		String dwbm = list1.get(0).getSsrdwbm(); // 单位编码
		String kssj = list1.get(0).getKssj();
		String jssj = list1.get(0).getJssj();
		List<SFDAAJ> list = sfdacjService.getCbAjList(cbrgh, dwbm, kssj, jssj);
		String json = Constant.GSON.toJson(list);
		return json;
	}

	/**
	 * 查询嫌疑人
	 * @param request  部门受案号  嫌疑人编号
	 * @return String
	 */
	@RequestMapping(value = "/zbajfylist")
	public @ResponseBody String zbajfylist(HttpServletRequest request) {


		String curentpage = request.getParameter("page");//当前页
		String pagesize = request.getParameter("rows");//每页显示数

		String gdid = request.getParameter("gdid");
		DAGZGD dagzgd = new DAGZGD();
		dagzgd.setId(gdid);
		List<DAGZGD> list1 = sfdacjService.selectDagzId(dagzgd);
//		List<DAGZGD> list1 = sfdacjService.selectZbaj(curentpage,pagesize,dagzgd);
		if (CollectionUtils.isEmpty(list1)) {
			return null;
		}
		String cbrgh = list1.get(0).getSsr(); // 承办人工号
		String dwbm = list1.get(0).getSsrdwbm(); // 单位编码
		String kssj = list1.get(0).getKssj();
		String jssj = list1.get(0).getJssj();
		List<SFDAAJ> list2 = sfdacjService.getCbAjList(cbrgh, dwbm, kssj, jssj);
		List<SFDAAJ> list = sfdacjService.getZbAjList(curentpage,pagesize,cbrgh, dwbm, kssj, jssj);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", list2.size());
		map.put("rows", list);
		return Constant.GSON.toJson(map);
	}

	/**
	 * 查询嫌疑人
	 * @param request  部门受案号  嫌疑人编号
	 * @return String
	 */
	@RequestMapping(value = "/cbajfylist")
	public @ResponseBody String cbajfylist(HttpServletRequest request) {
		String curentpage = request.getParameter("page");//当前页
		String pagesize = request.getParameter("rows");//每页显示数

		String gdid = request.getParameter("gdid");
		DAGZGD dagzgd = new DAGZGD();
		dagzgd.setId(gdid);
		List<DAGZGD> list1 = sfdacjService.selectDagzId(dagzgd);
		if (CollectionUtils.isEmpty(list1)) {
			return null;
		}
		String cbrgh = list1.get(0).getSsr(); // 承办人工号
		String dwbm = list1.get(0).getSsrdwbm(); // 单位编码
		String kssj = list1.get(0).getKssj();
		String jssj = list1.get(0).getJssj();
		List<SFDAAJ> list2 = sfdacjService.getCbAjNum(cbrgh, dwbm, kssj, jssj);
		List<SFDAAJ> list = sfdacjService.getCbAj(curentpage,pagesize,cbrgh, dwbm, kssj, jssj);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", list2.size());
		map.put("rows", list);
		return Constant.GSON.toJson(map);
	}

	/**
	 * 返回案件数量.
	 * @param request 归档id
	 * @return String
	 */
	@RequestMapping(value = "/cbajcount", method = RequestMethod.POST)
	public @ResponseBody String getCbajList1(HttpServletRequest request) {

		// 查询归档信息
		String gdid = request.getParameter("gdid");
		DAGZGD dagzgd = new DAGZGD();
		dagzgd.setId(gdid);
		List<DAGZGD> list1 = sfdacjService.selectDagzId(dagzgd);
		if (CollectionUtils.isEmpty(list1)) {
			return null;
		}
		String cbrgh = list1.get(0).getSsr(); // 承办人工号
		String dwbm = list1.get(0).getSsrdwbm(); // 单位编码
		String kssj = list1.get(0).getKssj();
		String jssj = list1.get(0).getJssj();
		List<SFDAAJ> list = sfdacjService.getCbAjList(cbrgh, dwbm, kssj, jssj);
		return list.size() + "";
	}

	/**
	 * 查询案件嫌疑人列表
	 * @param request 统一受案号
	 * @return String
	 */
	@RequestMapping(value = "/ajxyr", method = RequestMethod.POST)
	public @ResponseBody String getAjXyrList(HttpServletRequest request) {
		String tysah = request.getParameter("tysah"); // 统一受案号
		List<DACJAJXYRXX> list = sfdacjService.getAjXyrList(tysah);
		String json = Constant.GSON.toJson(list);
		return json;
	}

	/**
	 * 添加办案质量
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/bazl", method = RequestMethod.POST)
	public @ResponseBody String addBazl(HttpServletRequest request) {
		HttpSession sessoin = request.getSession();
		String cjr = null;
		String bmsah = request.getParameter("bmsah");
		String dwbm = request.getParameter("dwbm");
		String cbrgh = request.getParameter("cbrgh");
		String baxg = request.getParameter("baxg");
		String wfxszqqk = request.getParameter("wfxszqqk");
		String baaq = request.getParameter("baaq");
		String sfxc = request.getParameter("sfxc");
		String ajzlpcjg = request.getParameter("ajzlpcjg");
		try {
			bmsah = URLDecoder.decode(bmsah, "utf-8");
			baxg = URLDecoder.decode(baxg, "utf-8");
			wfxszqqk = URLDecoder.decode(wfxszqqk, "utf-8");
			baaq = URLDecoder.decode(baaq, "utf-8");
			sfxc = URLDecoder.decode(sfxc, "utf-8");
			ajzlpcjg = URLDecoder.decode(ajzlpcjg, "utf-8");
		} catch (Exception e) {
		}
		try {
			RYBM rybm = (RYBM) sessoin.getAttribute("ry");
			cjr = rybm.getMc();
		} catch (Exception e) {
			cjr = "test";
			LOG.error("未找到登录信息!", e);
		}
		String success = "0";
		List<JCGDAAJZL> list = sfdacjService.getBazl(bmsah);
		if (!CollectionUtils.isEmpty(list)) {
			JCGDAAJZL jc = new JCGDAAJZL();
			jc.setTysah(bmsah);
			jc.setBmsah(bmsah);
			jc.setBaxg(baxg);
			jc.setBaaq(baaq);
			jc.setWfxszqqk(wfxszqqk);
			jc.setAjzlpcjg(ajzlpcjg);
			jc.setSfxc(sfxc);
			success = sfdacjService.updateBazl(jc);
		} else {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("p_tysah", bmsah);
			map.put("p_dwbm", dwbm);
			map.put("p_cbrgh", cbrgh);
			map.put("p_cjr", cjr);
			map.put("p_baxg", baxg);
			map.put("p_wfxszqqk", wfxszqqk);
			map.put("p_baaq", baaq);
			map.put("p_sfxc", sfxc);
			map.put("p_ajzlpcjg", ajzlpcjg);
			success = sfdacjService.addBazl(map);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("SUCCESS", success);
		return Constant.GSON.toJson(map);
	}

	/**
	 * 查询档案归综
	 * @param request 归档id
	 * @return String
	 */
	@RequestMapping(value = "/dagz")
	public @ResponseBody String getdagzlsit(HttpServletRequest request) {
		String gdid = request.getParameter("gdid");
		DAGZGD dagzgd = new DAGZGD();
		dagzgd.setId(gdid);
		List<DAGZGD> list = sfdacjService.selectDagzId(dagzgd);
		if (CollectionUtils.isEmpty(list)) {
			return "";
		}
		return Constant.GSON.toJson(list);
	}

	/**
	 * 查询案件信息
	 * @param request 统一受案号
	 * @return String
	 */
	@RequestMapping(value = "/getajxxbytysah")
	public @ResponseBody String getajxxbytyash(HttpServletRequest request) {
		String tysah = request.getParameter("tysah");
		try {
			tysah = URLDecoder.decode(tysah, "utf-8");
		} catch (Exception e) {
		}
		//通过统一受案号查询案件信息
		List<SFDAAJ> list = sfdacjService.selectajbytysah(tysah);
		if (CollectionUtils.isEmpty(list)) {
			return "";
		}
		return Constant.GSON.toJson(list);
	}

	/**
	 * 得到档案所属人单位编码
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/selectdassrdwbm")
	public @ResponseBody String selectdassrdwbm(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String ssr = request.getParameter("ssr");
		String dwbm = request.getParameter("dwbm");
		String gdid = request.getParameter("gdid");
		String sffc = request.getParameter("sffc");
		String spym = request.getParameter("spym");

		if (sffc==null || sffc.equals("undefined")) {
			sffc="0";
		}
		if("2".equals(spym)){
			sffc="3";
		}

		DAGZGD dagzgd = new DAGZGD();
		dagzgd.setSsr(ssr);
		dagzgd.setSsrdwbm(dwbm);
		dagzgd.setSffc(sffc);
		dagzgd.setId(gdid);

		List<DAGZGD> list = sfdacjService.selectdassrdwbm(dagzgd);
		try {
			if (CollectionUtils.isEmpty(list)) {
				return "";
			}
			String kssj = "";
			String jssj = "";
			for (int i = 0; i < list.size(); i++) {
				DAGZGD d = list.get(i);
				if (!kssj.equals(d.getKssj()) || !jssj.equals(d.getJssj())) {
					kssj = d.getKssj();
					jssj = d.getJssj();
				} else {
					list.remove(i--);
				}
			}
			RYBM ry = (RYBM) session.getAttribute("ry");
			if (!ssr.equals(ry.getGh()) || !dwbm.equals(ry.getDwbm())||"1".equals(spym)||"2".equals(spym)) {
				for (DAGZGD d : list){
					if (d.getId().equals(gdid)) {
						list.clear();
						list.add(d);
						break;
					}
				}
			}
		} catch (Exception e) {
			LOG.error("未找到登录信息!", e);
		}
		return Constant.GSON.toJson(list);
	}


//	public @ResponseBody List<DAGZGD> getArchives(String ssr,String dwbm,String sffc,String spym){
//
//	}
	/**
	 * 办案质量查询
	 * @param request 统一受案号
	 * @return String
	 */
	@RequestMapping(value = "/getbazl")
	public @ResponseBody String getbazl(HttpServletRequest request) {
		String bmsah = request.getParameter("bmsah");
		try {
			bmsah = URLDecoder.decode(bmsah, "utf-8");
		} catch (Exception e) {
		}
		List<JCGDAAJZL> list = sfdacjService.getBazl(bmsah);
		if (CollectionUtils.isEmpty(list)) {
			return "";
		}
		return Constant.GSON.toJson(list);
	}

	/**
	 * 查询嫌疑人
	 * @param request  部门受案号  嫌疑人编号
	 * @return String
	 */
	@RequestMapping(value = "/selectxyrdetail", method = RequestMethod.POST)
	public @ResponseBody String selectXyrDetail(HttpServletRequest request) {
		String bmsah = request.getParameter("bmsah");//部门受案号
		String xyrbh = request.getParameter("xyrbh");//嫌疑人编号
		List<DACJAJXYRXX> list = null;
		try {
			list = sfdacjService.selectXyrDetail(bmsah, xyrbh);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return Constant.GSON.toJson(list.get(0));
	}

	/**
	 * 获取档案、公示、取消公示的审批状态
	 * @param request spstid审批实体id
	 * @return map
	 */
	@RequestMapping(value = "/getAllGsSpzt", method = RequestMethod.POST)
	public @ResponseBody String getAllGsSpzt(HttpServletRequest request) {

		String spstid = request.getParameter("spstid");  //审批实体id

		List<Splcsl> listSfda = null;
		List<Splcsl> listGs = null;
		List<Splcsl> listQxgs = null;
		List<Splcsl> listDafc = null;
		List<Splcsl> listSfdaHc=null;
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			listSfda = dagzService.showSplcsl(spstid, "2");
			listGs = dagzService.showSplcsl(spstid,"1");
			listQxgs = dagzService.showSplcsl(spstid, "6");
			listDafc = dagzService.showSplcsl(spstid, "7");
			listSfdaHc = dagzService.showSplcsl(spstid, "8");

			if(!CollectionUtils.isEmpty(listSfda)){
				map.put("Sfda", listSfda.get(0).getSpzt());
			}
			if(!CollectionUtils.isEmpty(listGs)){
				map.put("Gs", listGs.get(0).getSpzt());
			}
			if(!CollectionUtils.isEmpty(listQxgs)){
				map.put("Qxgs", listQxgs.get(0).getSpzt());
			}
			if(!CollectionUtils.isEmpty(listDafc)){
				map.put("Dafc", listDafc.get(0).getSpzt());
			}
			if(!CollectionUtils.isEmpty(listSfdaHc)){
				map.put("SfdaHc", listSfdaHc.get(0).getSpzt());
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return Constant.GSON.toJson(map);
	}

	/**
	 * 显示主办和参办案件数量
	 * @param request  gdid 归档id
	 * @return map 主办案件数量、参办案件数量
	 */
	@RequestMapping(value = "/zbajCbajCount", method = RequestMethod.POST)
	public @ResponseBody String zbajCbajCount(HttpServletRequest request) {
		String gdid = request.getParameter("gdid");//归档id
		DAGZGD dagzgd = new DAGZGD();
		dagzgd.setId(gdid);

		Map<String, Object> map = new HashMap<String, Object>();
		try {

			List<DAGZGD> list1 = sfdacjService.selectDagzId(dagzgd);
			if (CollectionUtils.isEmpty(list1)) {
				return null;
			}
			String cbrgh = list1.get(0).getSsr(); // 承办人工号
			String dwbm = list1.get(0).getSsrdwbm(); // 单位编码
			String kssj = list1.get(0).getKssj();
			String jssj = list1.get(0).getJssj();

			int zbajsl = 0; // 主办案件数量
			List<SFDAAJ> zbajList = sfdacjService.getCbAjList(cbrgh, dwbm, kssj, jssj);
			if (CollectionUtils.isNotEmpty(zbajList)) {
				zbajsl = zbajList.size();
			}

			int cbajsl = 0; // 参办案件数量
			List<SFDAAJ> cbajList = sfdacjService.getCbAjNum(cbrgh, dwbm, kssj, jssj);
			if (CollectionUtils.isNotEmpty(cbajList)) {
				cbajsl = cbajList.size();
			}

			map.put("zbajsl", zbajsl);
			map.put("cbajsl", cbajsl);

		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, e.toString());
		}
		return Constant.GSON.toJson(map);
	}


	@RequestMapping(value = "/isFileCreater", method = RequestMethod.GET)
	@ResponseBody
	public String isFileCreater(@RequestParam(value = "dwbm",required = true) String dwbm,
								@RequestParam(value = "gh",required = true) String gh) {

		String result = "error";
		try {
			Boolean flag = sfdacjService.getFileCreater(dwbm,gh);
			if (flag) {
				result = "success";
				return Constant.GSON.toJson(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constant.GSON.toJson(result);
	}


	@RequestMapping(value = "/uniteCreate", method = RequestMethod.POST)
	@ResponseBody
	public String uniteCreate(@RequestParam(value = "dwbm",required = true) String dwbm,
							  @RequestParam(value = "xm",required = true) String xm,
							  @RequestParam(value = "gh",required = true) String gh,
							  @RequestParam(value = "cjr",required = true) String cjr) {

		String  res = sfdacjService.uniteCreate(dwbm,xm,gh,cjr);
		return res;
	}
}


