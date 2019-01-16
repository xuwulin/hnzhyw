package com.swx.ibms.business.common.controller;

import com.swx.ibms.business.archives.bean.CountSySfda;
import com.swx.ibms.business.archives.bean.DAGZGD;
import com.swx.ibms.business.archives.bean.Gsjl;
import com.swx.ibms.business.archives.service.SfdacjService;
import com.swx.ibms.business.cases.bean.SFDAAJ;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.service.SyCountService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.RYBM;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 司法档案内容统计Controller
 * @author 李佳
 * @date: 2017年3月5日
 */
@RequestMapping("/syCount")
@Controller
public class SyCountController {

	/**
	 * 司法档案内容统计接口
	 */
	@Resource
	private SyCountService syCountService;

	/**
	 * 司法档案创建服务接口
	 */
	@Resource
	private SfdacjService sfdacjService;

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;

	/**
	 * 统计结果
	 * @param request 请求对象
	 * @return 统计结果的map
	 */
	@RequestMapping(value = "/countSum", method = RequestMethod.POST)
	public @ResponseBody String countSum(HttpServletRequest request) {
		String gdid = request.getParameter("gdid");
		String isOnlyGsxx = request.getParameter("isOnlyGsxx");
		List<DAGZGD> dagzList=syCountService.getDagzByGdid(gdid);
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			if (StringUtils.isBlank(isOnlyGsxx)) { // 是否只显示公示信息

				String countPjajblsc = syCountService.pjajBlsc(gdid); // 计算平安案件办理时长
				int countQypjajBlsc = syCountService.qypjajBlsc(gdid);// 计算全院案件平均办理时长
				double countGrjx = syCountService.countGrjx(gdid); // 计算个人绩效得分

				int countSfjn = 0; // 司法技能
				int countSfzr = 0; // 司法责任
				int countZycs = 0; // 职业操守
				List<CountSySfda> listType1 = (List<CountSySfda>) syCountService.countSfda(gdid, "1");
				for (CountSySfda e : listType1) {
					countSfjn += e.getSl();
				}
				List<CountSySfda> listType2 = (List<CountSySfda>) syCountService.countSfda(gdid, "2");
				for (CountSySfda e : listType2) {
					countSfzr += e.getSl();
				}
				List<CountSySfda> listType3 = (List<CountSySfda>) syCountService.countSfda(gdid, "3");
				for (CountSySfda e : listType3) {
					countZycs += e.getSl();
				}

				String sfgs = dagzList.get(0).getSfgs(); // 司法档案是否公示
				if (sfgs == null) {
					sfgs = "2";
				}

				String dakssj = ""; // 档案开始时间
				String dajssj = ""; // 档案结束时间
				String gh = ""; // 工号
				String ssrdwbm = ""; // 单位编码

				int zbajsl = 0; // 主办案件数量
				if (CollectionUtils.isNotEmpty(dagzList)) {
					dakssj = dagzList.get(0).getKssj();
					dajssj = dagzList.get(0).getJssj();
					gh = dagzList.get(0).getSsr();
					ssrdwbm = dagzList.get(0).getSsrdwbm();
					List<SFDAAJ> zbajList = sfdacjService.getCbAjList(gh, ssrdwbm, dakssj, dajssj);
					if (CollectionUtils.isNotEmpty(zbajList)) {
						zbajsl = zbajList.size();
					}
				}

				int cbajsl=0; //参办案件数量
				if (CollectionUtils.isNotEmpty(dagzList)) {
					dakssj = dagzList.get(0).getKssj();
					dajssj = dagzList.get(0).getJssj();
					gh = dagzList.get(0).getSsr();
					ssrdwbm = dagzList.get(0).getSsrdwbm();
					List<SFDAAJ> cbajList = sfdacjService.getCbAjNum(gh, ssrdwbm, dakssj, dajssj);
					if (CollectionUtils.isNotEmpty(cbajList)) {
						cbajsl = cbajList.size();
					}
				}

				map.put("countPjajblsc", countPjajblsc);
				map.put("countQypjajBlsc", countQypjajBlsc);
				map.put("countGrjx", countGrjx);
				map.put("countSfjn", countSfjn);
				map.put("countSfzr", countSfzr);
				map.put("countZycs", countZycs);
				map.put("sfgs", sfgs);
				map.put("dakssj", dakssj);
				map.put("dajssj", dajssj);
				map.put("zbajsl", zbajsl);
				map.put("cbajsl", cbajsl);
			}

			// 公示信息和取消公示信息展示
			List<Gsjl> gsList = syCountService.getGsJzSj(gdid, "1");
			List<Gsjl> qxGsList = syCountService.getGsJzSj(gdid, "2");
			String gsxx = null; // 公示信息
			String gsJzsj = null; // 公示截止时间
			String qxGsxx = null; // 取消公示信息
			if (!CollectionUtils.isEmpty(gsList)) {
				gsxx = gsList.get(0).getGsxx();
				gsJzsj = (new SimpleDateFormat("yyyy-MM-dd")).format(gsList.get(0).getGsJzsj());
			}
			if (!CollectionUtils.isEmpty(qxGsList)) {
				qxGsxx = qxGsList.get(0).getGsxx();
			}
			map.put("gsxx", gsxx);
			map.put("gsJzsj", gsJzsj);
			map.put("qxGsxx", qxGsxx);

		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, e.toString());
		}
		return Constant.GSON.toJson(map);
	}
	/**
	 * 根据归档id 计算个人绩效得分
	 * @param request 请求对象
	 * @return 得分
	 */
	@RequestMapping(value = "/grjxDf", method = RequestMethod.POST)
	public @ResponseBody String countGrjxDf(HttpServletRequest request) {
		String gdid = request.getParameter("gdid");
		double grjxdf = 0;
		try {
			grjxdf = syCountService.grjxDf(gdid);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, e.toString());
		}

		return Double.toString(grjxdf);
	}


	/**
	 * 根据工号、所属人单位编码查询此人最新档案的归档id
	 * @param  request 请求对象
	 * @return 归档id
	 */
	@RequestMapping(value = "/getGdid", method = RequestMethod.POST)
	public @ResponseBody String getGdid(HttpServletRequest request) {
		String sffc = request.getParameter("sffc");
		if(sffc == null || sffc.equals("undefined")){
			sffc = "0";
		}else{
			sffc = sffc.replace("#", "");
			if(!"1".equals(sffc)){
				sffc = "0";
			}
		}
		HttpSession session = request.getSession();
		RYBM ry = (RYBM) session.getAttribute("ry");
		String gh = ry.getGh(); // 登录时获取当前人工号
		String ssrdwbm = ry.getDwbm(); // 登录时获取当前人所属单位编码
		String gdid=null;
		try {
			gdid = syCountService.getGdid(ssrdwbm, gh,sffc);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, e.toString());
		}
		return gdid;
	}

	/**
	 * 根据工号、所属人单位编码、档案开始时间、档案结束时间 查询归档id
	 * @param request 请求对象         
	 * @return 归档id
	 */
	@RequestMapping(value = "/getGdidBySj")
	public @ResponseBody String getGdidBySj(HttpServletRequest request) {
		String gh = request.getParameter("gh");
		String dwbm=request.getParameter("dwbm");
		String kssj=request.getParameter("kssj");
		String jssj=request.getParameter("jssj");
		String gdid=null;
		try {
			gdid = syCountService.getGdidBySj(gh,dwbm,kssj,jssj);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, e.toString());
		}
		return gdid;
	}

	@RequestMapping(value = "/getYwkhInfo")
	@ResponseBody
	public String getYwkhInfo(@RequestParam(value="dwbm",required=true) String dwbm,
							  @RequestParam(value="ywlx",required=true) String ywlx) throws Exception{
		List<Map<String, Object>> list = new ArrayList<>();
		List<String> ywlxList = new ArrayList<>();
		for(int i=0;i<ywlx.split(",").length;i++){
			ywlxList.add(ywlx.split(",")[i]);
		}
		try {
			list = syCountService.getYwkhInfo(dwbm,ywlxList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constant.GSON.toJson(list);
	}

	/**
	 * 根据档案id查询档案是否公示，用于首页司法档案栏右上角的更多链接
	 * @param daId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSfgs")
	@ResponseBody
	public String getSfgs(@RequestParam(value = "daId",required = true) String daId) throws Exception{
		String sfgs = "";
		try {
			sfgs = syCountService.getSfgs(daId);
		}catch (Exception e){
			e.printStackTrace();
		}
		return sfgs;
	}
}
