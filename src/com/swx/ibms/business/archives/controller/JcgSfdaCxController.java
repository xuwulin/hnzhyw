package com.swx.ibms.business.archives.controller;

import com.swx.ibms.business.archives.bean.GS;
import com.swx.ibms.business.archives.bean.Gsjl;
import com.swx.ibms.business.archives.service.DAGZService;
import com.swx.ibms.business.archives.service.JcgSfdaCxService;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.service.QXService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.RYBM;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * @author 朱春雨
 * @since 检察官司法档案查询Controller
 */
@RequestMapping("/jcgSfdaCx")
@Controller
public class JcgSfdaCxController {

	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(JcgSfdaCxController.class);
	/**
	 * 司法档案查询
	 */
	@Resource
	private JcgSfdaCxService jcgSfdaCxService;
	/**
	 * 权限服务接口
	 */
	@Resource
	private QXService qxService;

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;
	/**
	 * 档案归总
	 */
	@Resource
	private DAGZService dagzService;


	/**
	 * 检查官司法档案查询
	 *
	 * @param request
	 *            sfgs(是否公示),tjnf(年度),ssrdwbm(所属人单位编码)cjrdwbm(创建人单位编码),cjr(创建人)
	 *            ssrmc(所属人名称)
	 *
	 * @return guid
	 *
	 */
	@RequestMapping(value = "/jcgSfdaCx", method = RequestMethod.POST)
	public @ResponseBody String jcgSfdaCx(HttpServletRequest request) {
		RYBM ry = (RYBM) request.getSession().getAttribute("ry");
		List<String> bmyslist = (List<String>) request.getSession().getAttribute("bmys");
		List<String> bmjs = (List<String>) request.getSession().getAttribute("bmjs");
		String dqrMc = ry.getMc(); // 从session中获取当前登录人名字
		String sfgs = request.getParameter("sfgs"); // 是否公示 1代表公示所有 2.已公示 3.未公示
		String sffc = request.getParameter("sffc"); // 是否封存
		String sfgd = request.getParameter("sfgd"); // 是否归档
		String ssrdwbm = request.getParameter("ssrdwbm"); // 所属人单位编码
		String dlrdwbm = ry.getDwbm(); // 登录人单位编码
		String dlr = ry.getGh(); // 登录人工号
		String ssrmc = request.getParameter("ssrmc"); // 所属于人名称
		String kssj = request.getParameter("kssj");// 开始时间
		String jssj = request.getParameter("jssj");// 结束时间
		String pagestr = request.getParameter("page");
		int page = Integer.parseInt(pagestr);// 目前页数

		Map resultMap = new HashMap();
		List<String> ryjs = new ArrayList<String>();
		String bmbm = "";
		String bmyss = "";
		int fgcount = (int)jcgSfdaCxService.cxFgBm(dlrdwbm,dlr).get("count");//查询是否有分管部门以确定是否是分管领导
		if (fgcount>0) {
			ryjs.add("2");// 分管领导
		}
		for (int i = 0; i < bmyslist.size(); i++) {
			String bmys1 = bmyslist.get(i);
			String[] bmjsStrArray = bmjs.get(i).split(",");
			String jsbm = bmjsStrArray[2];
			if (bmys1 == null || bmys1.equals("")) {
				ryjs.add("5");// 普通人
			} else if (bmys1.contains("0102")) {
				ryjs.add("8");// 考评委员会
			} else if (bmys1.contains("9100")) {
				ryjs.add("7");// 人事部（原为0，暂时改成和案管一样），前台获取到 人事部人员角色为0，这里之所以为7是为了方便查询（和案管一样能够查询全院）
			} else if (bmys1.contains("4001")) {
				ryjs.add("7"); // 政治部 前台获取到的 政治部人员角色为 4 这里之所以为7是为了方便查询（和案管一样能够查询全院）
			} else if (bmys1.contains("0101")) {
				ryjs.add("6");// 纪检
			} else if (bmys1.contains("1100")) {
				ryjs.add("7");// 案管
			} else if (bmys1.contains("0000")) {
				if (jsbm.equals("001")) {
					ryjs.add("1");// 检察长
				} else {
					ryjs.add("4");// 承办人
				}
			} else {
				if (jsbm.equals("001") || jsbm.equals("002")) {
					ryjs.add("3");// 部门领导
				} else {
					ryjs.add("4");// 承办人
				}
			}
			try {
				if (ryjs.get(i).equals("2")) {	//分管领导
					String fgbmbm = (String)jcgSfdaCxService.cxFgBm(dlrdwbm,dlr).get("fgbmbm");
					if (bmbm.equals("")) {
						bmbm = bmbm + fgbmbm;
					} else {
						bmbm = bmbm + "," + fgbmbm;
					}
				} else if (ryjs.get(i).equals("3")) {
					if (bmbm.equals("")) {
						bmbm = bmbm + bmjsStrArray[1];
					} else {
						bmbm = bmbm + "," + bmjsStrArray[1];
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String qx = null;
		if (ryjs.contains("1") || ryjs.contains("6") || ryjs.contains("7")) {
			qx = "1";
		} else if (ryjs.contains("2") || ryjs.contains("3")) {
			qx = "2";
		} else {
			qx = "3";
		}

		if ((!dlrdwbm.equals(ssrdwbm)) && qx.equals("2")) {// 判断是否是部门领导和分管领导，并且查的是否是下级单位
			bmbm = "(" + bmbm + ")";
			bmyss = jcgSfdaCxService.selectDwbmBmys(dlrdwbm, bmbm);// 通过登录单位编码和部门编码找部门映射
			bmbm = jcgSfdaCxService.selectBmysBm(ssrdwbm, bmyss);// 通用所属人单位和部门映射找部门
		}

		bmbm = "(" + bmbm + ")";
		if (bmbm.equals("()")) {
			bmbm = "('')";
		}

		if (sfgd.equals("0")) {
			sfgd = "(1,2)";
		}

		resultMap = jcgSfdaCxService.jcgSfdaCx(sfgs, sffc, sfgd, ssrdwbm, dlrdwbm, dlr, ssrmc, qx, bmbm, kssj, jssj, page);
		return Constant.GSON.toJson(resultMap);
	}

	/**
	 * 发起公示
	 *
	 * @param request 发起公示
	 *
	 * @return 是否公示成功 1 代表成功 2代表未成功
	 *
	 */
	@RequestMapping(value = "/gs", method = RequestMethod.POST)
	public @ResponseBody String Gs(HttpServletRequest request) {
		String gdid = request.getParameter("gdid"); // 归档id
		String fqrbm = request.getParameter("fqrbm");
		String fqrdwbm = request.getParameter("fqrdwbm");
		String clnr = request.getParameter("clnr");
		String gsxx = request.getParameter("gsxx");
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("gdid", gdid);
		map1.put("fqrbm", fqrbm);
		map1.put("fqrdwbm", fqrdwbm);
		map1.put("clnr", clnr);
		map1.put("gsxx", gsxx);

		Map resultMap = null;
		try {
			resultMap = jcgSfdaCxService.gs(map1);
		} catch (Exception e) {
			e.printStackTrace();
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, ExceptionUtils.getStackTrace(e));
		}
		String result = (String) resultMap.get("Y");
		if (result.equals("1")) {
			result = "发起公示成功 !";
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "发起公示");
		} else {
			result = "发起公示失败! ";
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, "发起公示失败");
		}
		return Constant.GSON.toJson(result);
	}

	/**
	 * 取消公示
	 *
	 * @param request
	 *            请求参数
	 * @return 执行信息
	 */
	@RequestMapping(value = "/qxgs")
	public @ResponseBody String qxgs(HttpServletRequest request) {
		String gsxx = request.getParameter("gsxx");
		String gdid = request.getParameter("gdid"); // 归档id
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		paramsMap.put("gdid", gdid);
		paramsMap.put("gsxx", gsxx);

		int num = jcgSfdaCxService.qxgs(paramsMap);
		if (num > 0) {
			resultMap.put("msg", "取消公示成功！");
		} else {
			resultMap.put("msg", "取消公示失败！");
		}
		return Constant.GSON.toJson(resultMap);
	}

	/**
	 * 展示公示
	 *
	 * @param request
	 *            请求
	 * @return 返回展示公示的list
	 */
	@RequestMapping(value = "/showgs", method = RequestMethod.POST)
	@ResponseBody
	public String showGs(HttpServletRequest request) {
		RYBM ry = (RYBM) request.getSession().getAttribute("ry");
		Map<String, Object> map = new HashMap<String, Object>();

		Map<String, Object> resMap = new HashMap<>();
		String dwbm = ry.getDwbm();

		map.put("dwbm", ry.getDwbm());
		map.put("begin", "1");
		map.put("end", "4");
		map.put("sffc", "0");
		List<GS> resultList = null;
		try {
//			resultList = jcgSfdaCxService.showGs(map);
			resMap = jcgSfdaCxService.showGsOfIndex(dwbm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constant.GSON.toJson(resMap);
	}

	/**
	 * 判断当前登录人员是否是档案管理员
	 *
	 * @param request
	 *            请求参数
	 * @return json数据
	 */
	@RequestMapping(value = "/isadmin", method = RequestMethod.POST)
	public @ResponseBody String isAdmin(HttpServletRequest request) {
		List<String> bmyslist = (List<String>) request.getSession().getAttribute("bmys");

		Boolean isadmin = false;
		try {
			for (int i = 0; i < bmyslist.size(); i++) {
				String[] array = bmyslist.get(i).split(";");
				if (ArrayUtils.contains(array, "1100")) {
					isadmin = true;
				}
			}
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("isadmin", isadmin);

		return Constant.GSON.toJson(resultMap);
	}

	/**
	 * @param gsjl
	 *            公示实例
	 * @return message
	 */
	@RequestMapping("/addgsjl")
	public @ResponseBody String addGsjl(Gsjl gsjl) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		jcgSfdaCxService.addGsjl(gsjl);
		resultMap.put("msg", "操作成功！");
		return Constant.GSON.toJson(resultMap);
	}

	/**
	 * @param request
	 *            档案封存
	 * @return 是否操作成功
	 */
	@RequestMapping("/dagzfc")
	public @ResponseBody String dagzfc(HttpServletRequest request) {
		String id = request.getParameter("gdid");// 档案归总ID
		String dwbm = request.getParameter("dwbm");// 档案归总ID
		String gh = request.getParameter("gh");// 档案归总ID
		boolean y = false;
		int count=-1;
		try {
			y = jcgSfdaCxService.dagzFc(id, "1");
			if(y)
			{
				count = dagzService.ssrDagzCount(dwbm, gh,"0");
			}else{
				count=-1;
			}
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "档案封存");
		} catch (Exception e) {
			e.printStackTrace();
			count= -1;
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, ExceptionUtils.getStackTrace(e));
		}

		return Constant.GSON.toJson(count);
	}

	/**
	 * @param request
	 *            请求
	 * @return 档案是否在通过审批
	 */
	@RequestMapping("/dasfsp")
	public @ResponseBody String dasfsp(HttpServletRequest request) {
		String spstid = request.getParameter("gdid");// 档案归总ID
		boolean y2 = false;// 档案是否在审批过程中
		boolean y7= false;//是否在封存审批过程中
		boolean y=false;
		String dsp="0";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			y2 = jcgSfdaCxService.dasfsp(spstid, "2");
			y7 = jcgSfdaCxService.dasfsp(spstid, "7");
			dsp= jcgSfdaCxService.sfyDsp(spstid);
			if (y2&&y7) {
				y=true;
			}
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "档案是否在审批过程中");
		} catch (Exception e) {
			y=false;
			e.printStackTrace();
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, ExceptionUtils.getStackTrace(e));
		}
		resultMap.put("y", y);
		resultMap.put("dsp", dsp);
		return Constant.GSON.toJson(resultMap);
	}

	/**
	 * 根据单位编码，工号查询个人档案，新建，已公示，已归档
	 */
	@RequestMapping(value = "/getDaByDG",method = RequestMethod.GET)
	@ResponseBody
	public String getDaByDG(HttpServletRequest request){

		String sfgs = request.getParameter("sfgs"); // 是否公示 1代表公示所有 2.已公示 3.未公示
		String sffc = request.getParameter("sffc"); // 是否封存
		String sfgd = request.getParameter("sfgd"); // 是否归档
		String dwbm = request.getParameter("dwbm"); // 单位编码
		String gh = request.getParameter("gh"); // 单位编码
		String kssj = request.getParameter("kssj");// 开始时间
		String jssj = request.getParameter("jssj");// 结束时间

		Map<String, Object> map = new HashMap<>();
		map = jcgSfdaCxService.getDaByDG(dwbm,gh,sfgs,sffc,sfgd,kssj,jssj);
		return Constant.GSON.toJson(map);
	}

	/**
	 * 根据单位编码，工号，档案起始时间，添加年份查询档案信息（唯一）
	 * @param dwbm
	 * @param gh
	 * @param kssj
	 * @param jssj
	 * @param tjnf
	 * @return
	 */
	@RequestMapping(value = "/getFileInfo",method = RequestMethod.GET)
	@ResponseBody
	public String getFileInfo(@RequestParam(value="dwbm",required=true)String dwbm,
							  @RequestParam(value="gh",required=true)String gh,
							  @RequestParam(value="kssj",required=true)String kssj,
							  @RequestParam(value="jssj",required=true)String jssj,
							  @RequestParam(value="tjnf",required=true)String tjnf){
		Map<String, Object> map = new HashMap<>();
		map = jcgSfdaCxService.getFileInfo(dwbm, gh, kssj, jssj, tjnf);
		return Constant.GSON.toJson(map);
	}


	/**
	 * 根据条件查询司法档案--12月24日修改
	 */
	@RequestMapping(value = "/queryArchives", method = RequestMethod.POST)
	@ResponseBody
	public String queryArchivesByCondition(@RequestParam(value="ssrdwbm",required=false) String query_dwbm,
										   @RequestParam(value="sfgs",required=false) String query_sfgs,
										   @RequestParam(value="sfgd",required=false) String query_sfgd,
										   @RequestParam(value="sffc",required=false) String query_sffc,
										   @RequestParam(value="kssj",required=false) String query_kssj,
										   @RequestParam(value="jssj",required=false) String query_jssj,
										   @RequestParam(value="ssrmc",required=false) String query_name,
										   @RequestParam(value="page",required=false) Integer page,
										   HttpServletRequest request) {
		// 获得相应参数
		HttpSession session = request.getSession();
		RYBM ry = (RYBM) session.getAttribute("ry");
		// 登录人单位编码
		String loger_dwbm = ry.getDwbm();
		// 登录人工号
		String loger_gh = ry.getGh();
		// 登录人部门编码
		String loger_bmbm = ry.getBmbm();
		// 部门映射
		List<String> loger_bmyslist = (List<String>) request.getSession().getAttribute("bmys");
		// 部门角色：460000,0134,001（单位编码，部门编码，角色编码）
		List<String> loger_bmjs = (List<String>) request.getSession().getAttribute("bmjs");

		Map<String, Object> res = new HashMap<>();
		try {
			res = jcgSfdaCxService.queryArchivesByCondition(loger_dwbm, loger_gh, loger_bmbm, loger_bmyslist, loger_bmjs,
					query_dwbm, query_sfgs, query_sfgd, query_sffc, query_kssj, query_jssj, query_name, page);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Constant.GSON.toJson(res);
	}

}