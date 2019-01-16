package com.swx.ibms.business.performance.controller;


import com.swx.ibms.business.archives.service.JcgSfdaCxService;
import com.swx.ibms.business.common.bean.Splcsl;
import com.swx.ibms.business.common.service.SpService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.performance.service.GrjxkhsyService;
import com.swx.ibms.business.rbac.bean.Bmmc;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.system.service.XTGLService;
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
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人绩效首页控制层
 *
 * @author 王宇锋
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/grjxsy")
public class GrjxkhsyController {
	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(GrjxController.class);
	/**
	 * 个人绩效首页业务层接口
	 */
	@Resource
	private GrjxkhsyService grjxkhsyService;

	/**
	 * 审批服务接口
	 */
	@Resource
	private SpService spService;

	/**
	 * 系统管理服务接口
	 */
	@Resource
	private XTGLService xtglService;

	@Resource
	private JcgSfdaCxService jcgSfdaCxService;

	/**
	 * 加载绩效考核信息
	 *
	 * @param request
	 *            request
	 * @return 返回绩效考核信息
	 */
	@RequestMapping(value = "/jz", method = RequestMethod.POST)
	@ResponseBody
	public String getJzjxkh(HttpServletRequest request) {
		HttpSession session = request.getSession();
		// 获取人员session信息
		RYBM ry = (RYBM) session.getAttribute("ry");
		// 获取人员角色
		List<Integer> ryjs = (List<Integer>) session.getAttribute("ryjs");
		// 获取部门角色
		List<String> bmjs = (List<String>) session.getAttribute("bmjs");

		// 获得传过来的参数
		// 查询状态：0表示没有条件查询; 1为有条件查询(没跨院);2为跨院查询
		String status = request.getParameter("status");
		// 人员名称
		String rymc = request.getParameter("rymc");
		// 要查询的单位编码
		String cxDwbm = request.getParameter("dwbm");
		// 业务类型
		String ywlx = request.getParameter("ywlx");
		// 年份
		String yearstr = request.getParameter("year");
		int year = 0;
		if (yearstr != null && !"".equals(yearstr)) {
			year = Integer.valueOf(yearstr);
		}
		// 季度
		String jdstr = request.getParameter("jd");
		int jd = 0;
		if (jdstr != null && !"".equals(jdstr)) {
			jd = Integer.valueOf(jdstr);
		}
		// 人员名称
		if (StringUtils.isNotBlank(rymc)) {
			try {
				rymc = URLDecoder.decode(rymc, "utf-8");
			} catch (Exception e) {
				LOG.error(StringUtils.EMPTY, e);
			}
		}

		// 获取单位编码
		String dwbm = ry.getDwbm();
		// 获取工号
		String gh = ry.getGh();
		// 获取第几页
		int page = -1;
		String pagestr = request.getParameter("page");
		if (StringUtils.isBlank(pagestr)) {
			page = 1;
		} else {
			page = Integer.parseInt(pagestr);
		}

		// 0表示没有条件查询 1为有条件查询(没跨院)2为跨院查询
		if ("0".equals(status)) {
			rymc = null;
			Map<String, Object> resultMap = grjxkhsyService.getJzjxkh(dwbm, rymc, page, ryjs, gh, bmjs, null, null,
					ywlx, year, jd);
			return Constant.GSON.toJson(resultMap);
		} else if ("1".equals(status)) {
			Map<String, Object> resultMap = grjxkhsyService.getJzjxkh(dwbm, rymc, page, ryjs, gh, bmjs, null, null,
					ywlx, year, jd);
			return Constant.GSON.toJson(resultMap);
		} else if ("2".equals(status)) {
			String cxbmbm = request.getParameter("cxbmbm");
			String cxdwbm = request.getParameter("cxdwbm");
			if ("0".equals(cxbmbm)) {
				// 得到的相关部门名称数据
				List<Bmmc> list = new ArrayList<>();
				cxbmbm = "";
				try {
					list = grjxkhsyService.getXgBm(dwbm, gh, ryjs, cxdwbm, bmjs);

					for (Bmmc bmmc : list) {
						if ("".equals(cxbmbm)) {
							cxbmbm = bmmc.getBmbm();
						}else{
							cxbmbm = cxbmbm+","+bmmc.getBmbm();
						}
					}
				} catch (Exception e) {
					LOG.error(StringUtils.EMPTY, e);
				}
			}
			cxbmbm="("+cxbmbm+")";
			if ("()".equals(cxbmbm)) {
				cxbmbm = "('')";
			}
			Map<String, Object> resultMap = grjxkhsyService.getJzjxkh(dwbm, rymc, page, ryjs, gh, bmjs, cxbmbm, cxdwbm,
					ywlx, year, jd);
			return Constant.GSON.toJson(resultMap);
		}

		return null;
	}

	/**
	 * 返回当前时间,此人所在部门的业务类型
	 *
	 * @param request
	 *            request
	 * @return 当前时间,此人所在部门的业务类型map
	 */
	@RequestMapping(value = "/jzdqsj", method = RequestMethod.POST)
	@ResponseBody
	public String getDqsj(HttpServletRequest request) {
		// 获得相应参数
		HttpSession session = request.getSession();
		RYBM ry = (RYBM) session.getAttribute("ry");
		String dwbm = ry.getDwbm();
		String gh = ry.getGh();
		List<Integer> ryjs = (List<Integer>) session.getAttribute("ryjs");
		List<String> bmjs = (List<String>) session.getAttribute("bmjs");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap = grjxkhsyService.getDqsj(dwbm, gh, ryjs, bmjs);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		return Constant.GSON.toJson(resultMap);
	}

	/**
	 * 返回相关部门
	 *
	 * @param request
	 *            request
	 * @return 相关部门
	 */
	@RequestMapping(value = "/jzbm", method = RequestMethod.POST)
	@ResponseBody
	public String getXgBm(HttpServletRequest request) {
		// 获得相应参数
		HttpSession session = request.getSession();
		RYBM ry = (RYBM) session.getAttribute("ry");

		// 获得登陆人的单位编码
		String dwbm = ry.getDwbm();
		String gh = ry.getGh();

		// 需要查询的dwbm
		String cxDwbm = request.getParameter("cxDwbm");
		List<Integer> ryjs = (List<Integer>) session.getAttribute("ryjs");
		List<String> bmjs = (List<String>) session.getAttribute("bmjs");

		// 得到的相关部门名称数据
		List<Bmmc> list = new ArrayList<>();
		try {
			list = grjxkhsyService.getXgBm(dwbm, gh, ryjs, cxDwbm, bmjs);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		return Constant.GSON.toJson(list);
	}

	/**
	 * 返回考核行数
	 *
	 * @param request
	 *            request
	 * @return 考核行数
	 */
	@RequestMapping(value = "/fhkhcount", method = RequestMethod.POST)
	@ResponseBody
	public String getkhcount(HttpServletRequest request) {
		// 获得相应参数
		HttpSession session = request.getSession();
		RYBM ry = (RYBM) session.getAttribute("ry");
		String dwbm = ry.getDwbm();
		String gh = ry.getGh();

		// 返回考核行数
		try {
			return Constant.GSON.toJson(grjxkhsyService.getkhcount(dwbm, gh));
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		return Constant.GSON.toJson(1);
	}

	/**
	 * 通过审批实体id和审批类型获取发起人的部门名称
	 * @param ydkhid 月度考核id
	 * @param splx 审批类型
	 * @return 发起人的部门名称
	 */
	@RequestMapping("/getbmmc")
	public @ResponseBody String getBmmcBySpstidAndSplx(String ydkhid,String splx){
		String bmmc = "";
		if(StringUtils.isNotBlank(ydkhid)&&StringUtils.isNotBlank(splx)){
			Splcsl splcslObj = spService.getFqjlBySpstidAndSplx(ydkhid, splx);

			if(splcslObj!=null){
				bmmc = grjxkhsyService.getBmmcByDwbmAndBmbm(
						splcslObj.getSpdw(), splcslObj.getSpbm());
			}
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("bmmc", bmmc);
		return Constant.GSON.toJson(resultMap);
	}

	@RequestMapping(value = "/queryGrjxByCondition", method = RequestMethod.GET)
	@ResponseBody
	public String queryGrjxByCondition(@RequestParam(value="query_dwbm",required=false) String query_dwbm,
									   @RequestParam(value="query_bmbm",required=false) String query_bmbm,
									   @RequestParam(value="query_sfgs",required=false) String query_sfgs,
									   @RequestParam(value="query_year",required=false) String query_year,
									   @RequestParam(value="query_kssj",required=false) String query_kssj,
									   @RequestParam(value="query_jssj",required=false) String query_jssj,
									   @RequestParam(value="query_name",required=false) String query_name,
									   @RequestParam(value="page",required=false) Integer page,
									   HttpServletRequest request) {
		// 获得相应参数
		HttpSession session = request.getSession();
		RYBM ry = (RYBM) session.getAttribute("ry");
		String loger_dwbm = ry.getDwbm(); // 登录人单位编码
		String loger_gh = ry.getGh(); // 登录人工号
		String loger_bmbm = ry.getBmbm(); // 登录人部门编码
		List<String> loger_bmyslist = (List<String>) request.getSession().getAttribute("bmys"); // 部门映射
		// 部门角色：460000,0134,001（单位编码，部门编码，角色编码）
		List<String> loger_bmjs = (List<String>) request.getSession().getAttribute("bmjs");

		Map<String, Object> res = new HashMap<>();
		try {
			res = grjxkhsyService.queryGrjxByCondition(loger_dwbm,loger_gh,loger_bmbm,loger_bmyslist,
					loger_bmjs,query_dwbm,query_bmbm,query_sfgs,query_year,query_kssj,query_jssj,query_name,page);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Constant.GSON.toJson(res);
	}





	/**
	 * 获取首页的个人绩效信息统计
	 * 考核时间：直接取考核日期表中最新的考核日期
	 * 是否审批：首页只展示已审批的个人绩效
	 * 权限：同个人绩效首页查询权限
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getGrjxOfIndex", method = RequestMethod.GET)
	@ResponseBody
	public String getGrjxOfIndex(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map<String,Object> resultMap = new HashMap<>();
		RYBM ry = (RYBM)session.getAttribute("ry");

		//当前人的单位编码
		String dwbm=ry.getDwbm();
		//当前人的工号
		String gh=ry.getGh();
		// 部门映射
		List<String> bmysList = (List<String>) request.getSession().getAttribute("bmys");
		// 部门角色：460000,0134,001（单位编码，部门编码，角色编码）
		List<String> bmjsList = (List<String>) request.getSession().getAttribute("bmjs");

		resultMap = grjxkhsyService.getGrjxOfIndex(dwbm, gh, bmysList, bmjsList);

		return Constant.GSON.toJson(resultMap);
	}

	/**
	 * 个人绩效首页的导出
	 * @param export_dwbm
	 * @param export_bmbm
	 * @param export_sfgs
	 * @param export_year
	 * @param export_kssj
	 * @param export_jssj
	 * @param export_name
	 * @param userPermissions
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "exportAll", method = RequestMethod.GET)
	@ResponseBody
	public String exportAll(@RequestParam(value="export_dwbm",required=false) String export_dwbm,
							@RequestParam(value="export_dwmc",required=false) String export_dwmc,
							@RequestParam(value="export_bmbm",required=false) String export_bmbm,
							@RequestParam(value="export_bmmc",required=false) String export_bmmc,
							@RequestParam(value="export_sfgs",required=false) String export_sfgs,
							@RequestParam(value="export_year",required=false) String export_year,
							@RequestParam(value="export_kssj",required=false) String export_kssj,
							@RequestParam(value="export_jssj",required=false) String export_jssj,
							@RequestParam(value="export_name",required=false) String export_name,
							@RequestParam(value="export_deptList",required=false) String[] export_deptList,
							@RequestParam(value="export_queryDeptList",required=false) String[] export_queryDeptList,
							@RequestParam(value="userPermissions",required=false) String userPermissions,
							HttpServletRequest request) {
		HttpSession session = request.getSession();
		RYBM ry = (RYBM)session.getAttribute("ry");
		String mc = ry.getMc();

		Map<String, Object> map = new HashMap<>();
		try {
			map = grjxkhsyService.exportAll(export_dwbm, export_dwmc, export_bmmc, export_bmbm, export_sfgs, export_year, export_kssj, export_jssj, export_name, export_deptList, export_queryDeptList, userPermissions, mc);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Constant.GSON.toJson(map);
	}



}
