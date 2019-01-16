package com.swx.ibms.business.archives.controller;

import com.swx.ibms.business.archives.bean.DAGZGD;
import com.swx.ibms.business.archives.service.DAGZService;
import com.swx.ibms.business.archives.service.SfdacjService;
import com.swx.ibms.business.cases.bean.SFDAAJ;
import com.swx.ibms.business.common.bean.Splcsl;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.common.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 朱春雨 档案归总
 */
@RequestMapping("/dagz")
@Controller
public class DAGZController {
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
	 * @param request
	 *            档案归总新建
	 * @return 返回
	 */
	@RequestMapping(value = "/xinjian", method = RequestMethod.POST)
	public @ResponseBody String xianJian(HttpServletRequest request) {
		RYBM ry = (RYBM) request.getSession().getAttribute("ry");
		String ssr = ry.getGh();
		String ssrdwbm = ry.getDwbm();
		String kssj = DateUtil.getYear(new Date()) + "-01"; // 档案开始时间:当前年的1月1日
		String jssj = DateUtil.getYear(new Date()) + "-12"; // 档案结束时间：当前年的12月31日
		String tjnf = DateUtil.getYear(new Date()) + ""; // 添加年份

		String cjr = ry.getGh();
		String cjrdwbm = ry.getDwbm();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ssr", ssr);
		map.put("ssrdwbm", ssrdwbm);
		map.put("tjnf", tjnf);
		map.put("kssj", kssj);
		map.put("jssj", jssj);
		map.put("cjr", cjr);
		map.put("cjrdwbm", cjrdwbm);
		String xjdagz = "";
		try {
			xjdagz = dagzService.insertDAGZData(map);
			if (!StringUtils.isEmpty(xjdagz)) {
				// 日志记录
				logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
						Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "新建档案归总信息");
			} else {
				// 记录日志
				logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
						Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, "新建档案归总信息失败");
			}

		} catch (Exception e) {
			e.printStackTrace();
			// 记录日志
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, e.toString());
		}
		return Constant.GSON.toJson(xjdagz);
	}

	/**
	 * 是否有自己的档案
	 * 
	 * @param request
	 *            请求
	 * @return 0代表没有自己的档案
	 */
	@RequestMapping(value = "/sfyzj", method = RequestMethod.POST)
	public @ResponseBody String sfyzj(HttpServletRequest request) {
		RYBM ry = (RYBM) request.getSession().getAttribute("ry");
		String gh = ry.getGh();
		String dwbm = ry.getDwbm();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gh", gh);
		map.put("dwbm", dwbm);
		String result = "0";
		try {
			result = dagzService.sFYZJ(map);
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "查看是否有自己的档案");
		} catch (Exception e) {
			e.printStackTrace();
			// 记录日志
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, e.toString());
		}
		return Constant.GSON.toJson(result);
	}

	/**
	 * @param request
	 *            是否有自己当前年份的档案
	 * @return 0代表没有
	 */
	@RequestMapping(value = "/sfyzjnf", method = RequestMethod.POST)
	public @ResponseBody String sfyzjnf(HttpServletRequest request) {
		RYBM ry = (RYBM) request.getSession().getAttribute("ry");
		String gh = ry.getGh();
		String dwbm = ry.getDwbm();
		String kssj = DateUtil.getYear(new Date()) + "-01"; // 档案开始时间:当前年的1月
		String jssj = DateUtil.getYear(new Date()) + "-12"; // 档案结束时间：当前年的12
//		String tjnf = kssj.substring(0, 4);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gh", gh);
		map.put("dwbm", dwbm);
//		map.put("tjnf", tjnf);
		map.put("kssj", kssj);
		map.put("jssj", jssj);
		String result = "0";
		try {
			result = dagzService.sFYZJNF(map);
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "查看是否有自己当前年份的档案");
		} catch (Exception e) {
			e.printStackTrace();
			// 记录日志
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, e.toString());
		}
		return Constant.GSON.toJson(result);
	}

	/**
	 * 是否可以删除档案
	 * 
	 * @param request
	 *            请求
	 * @return true可以删除，false不可以删除
	 */
	@RequestMapping(value = "/sfkyscda", method = RequestMethod.POST)
	public @ResponseBody String sfkyscda(HttpServletRequest request) {
		String wbid = request.getParameter("wbid");
		DAGZGD dagzgd = new DAGZGD();
		dagzgd.setId(wbid);
		List<DAGZGD> list1 = sfdacjService.selectDagzId(dagzgd);
		String cbrgh = list1.get(0).getSsr(); // 承办人工号
		String dwbm = list1.get(0).getSsrdwbm(); // 单位编码
		String kssj = list1.get(0).getKssj();
		String jssj = list1.get(0).getJssj();
		boolean y = true;
		try {
			List<SFDAAJ> list = sfdacjService.getCbAjList(cbrgh, dwbm, kssj, jssj);
			// 判断办案质量是否填写
			for (int i = 0; i < list.size(); i++) {
				SFDAAJ sfdaaj = list.get(i);
				int bazlcount = dagzService.sfBazlCount(sfdaaj.getBmsah());
				if (bazlcount > 0) {
					y = false;
				}
			}
			int dacount2 = 0;
			int dacount3 = 0;
			// 司法责任和职业操守是否有档案
			dacount2 = dagzService.sfDaCount(wbid, "2");// 司法责任
			dacount3 = dagzService.sfDaCount(wbid, "3");// 职业操守
			if (dacount2 > 0 || dacount3 > 0) {
				y = false;
			}
			int spstidCount1 = 0;// 荣誉技能spstid的数量
			int spstidCount2 = 0;// 档案归总spstid的数量
			spstidCount1 = dagzService.spstidCount(wbid, "4");
			spstidCount2 = dagzService.spstidCount(wbid, "2");
			if (spstidCount1 > 0 || spstidCount2 > 0) {
				List<Splcsl> list1s = dagzService.showSplcsl(wbid, "4");
				List<Splcsl> list2s = dagzService.showSplcsl(wbid, "2");
				if (list1s != null&&list1s.size()>0) {
					if ("5".equals(((Splcsl) list1s.get(0)).getSpzt())) {
						y = false;
					}
				}
				if (list2s != null&&list2s.size()>0) {
					if (!"5".equals(((Splcsl) list2s.get(0)).getSpzt())) {
						y = false;
					}
				}
			}
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "是否可以删除档案");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			// 记录日志
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, e.toString());
		}
		return Constant.GSON.toJson(y);
	}

	/**
	 * 档案归总删除
	 * 
	 * @param request
	 *            请求
	 * @return 删除是否成功
	 */
	@RequestMapping(value = "/deletedagz", method = RequestMethod.POST)
	public @ResponseBody String deletedagz(HttpServletRequest request) {
		String wbid = request.getParameter("wbid");
		String dwbm = request.getParameter("dwbm");
		String gh = request.getParameter("gh");
		String sffc = request.getParameter("sffc");
		String grjbxxId = request.getParameter("grjbxxId");
		if (sffc == null || sffc.equals("undefined")) {
			sffc = "0";
		} else {
			sffc = sffc.replace("#", "");
			if (!"1".equals(sffc)) {
				sffc = "0";
			}
		}
		boolean y = false;
		int count = -1;
		try {

			y = dagzService.deleDagz(wbid, grjbxxId);

			if (y) {
				count = dagzService.ssrDagzCount(dwbm, gh, sffc);
				// 日志记录
				logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
						Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "档案归总删除成功信息");
			} else {
				count = -1;
				// 日志记录
				logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
						Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "档案归总删除失败信息");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			// 记录日志
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, e.toString());
		}
		return Constant.GSON.toJson(count);
	}

	/**
	 * @param request
	 *            档案归总数据
	 * @return 返回档案归总数据
	 */
	@RequestMapping(value = "/dagzdata", method = RequestMethod.POST)
	public @ResponseBody String dagzdata(HttpServletRequest request) {
		String dagzid = request.getParameter("dagzid");
		DAGZGD dagzgd = new DAGZGD();
		dagzgd.setId(dagzid);
		List<DAGZGD> list = null;
		try {
			list = sfdacjService.selectDagzId(dagzgd);
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "返回档案归总数据list");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			// 记录日志
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, e.toString());
		}
		return Constant.GSON.toJson(list);
	}

	/**
	 * @param request
	 *            通过审批实体id和审批类型查出yx_sfda_splcsl数据
	 * @return 返回数据集合
	 */
	@RequestMapping(value = "/showSplcsl", method = RequestMethod.POST)
	public @ResponseBody String showSplcsl(HttpServletRequest request) {
		String spstid = request.getParameter("spstid");
		String splx = request.getParameter("splx");
		List<Splcsl> list = null;
		try {
			list = dagzService.showSplcsl(spstid, splx);
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "通过审批实体id和审批类型查出yx_sfda_splcsl数据");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			// 记录日志
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, e.toString());
		}
		return Constant.GSON.toJson(list);
	}

	/**
	 * @param request
	 *            档案所有sp过程中目前的状态
	 * @return 结果
	 */
	@RequestMapping(value = "/showAllFirstSplcsl", method = RequestMethod.POST)
	public @ResponseBody String showAllFirstSplcsl(HttpServletRequest request) {
		String spstid = request.getParameter("spstid");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Splcsl> temp = null;
			for (int i = 1; i <= 8; i++) {
				temp = dagzService.showSplcsl(spstid, i + "");
				if (temp != null && temp.size() > 0) {
					map.put("data" + i, temp.get(0));
				}
				temp = null;
			}

			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "档案所有sp过程中目前的状态");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			// 记录日志
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, e.toString());
		}
		return Constant.GSON.toJson(map);
	}

}
