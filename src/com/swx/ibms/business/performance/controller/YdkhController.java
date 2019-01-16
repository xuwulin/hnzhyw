package com.swx.ibms.business.performance.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.swx.ibms.business.appraisal.bean.Ywkhfz;
import com.swx.ibms.business.common.service.SpService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.common.utils.ExcelUtil;
import com.swx.ibms.business.performance.bean.*;
import com.swx.ibms.business.performance.service.*;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.rbac.service.LoginService;
import com.swx.ibms.business.system.service.XtfjpathService;
import com.swx.ibms.common.utils.DateUtil;
import com.swx.ibms.common.utils.Identities;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;


/**
 * 个人绩效控制器
 * @author 李治鑫
 * @since 2017-5-3
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/ydkh")
public class YdkhController {
	/**
	 * LOG日志对象
	 */
	private static final Logger LOG = Logger.getLogger(YdkhController.class);


	/**
	 * 月度考核服务接口
	 */
	@Resource
	private YdkhService ydkhService;

	/**
	 * 审批服务接口
	 */
	@Resource
	private SpService spService;

	/**
	 * 文件上传路径服务
	 */
	@Resource
	private XtfjpathService xtfjpathservice;

	/**
	 * 核查配置服务接口
	 */
	@Resource
	private HCPZService hCPZService;

	@Resource
	private LoginService loginService;

	@Resource
	private XtGrjxKhrqService xtGrjxKhrqService;

	@Resource
	private GrjxZbpzService grjxZbpzService;

	@Resource
	private XtGrjxKhryService xtGrjxKhryService;

	@Resource
	private GrjxYwkhfzService grjxYwkhfzService;

	/**
	 * 获得数据表头
	 *
	 * @param request 请求参数
	 * @return 表头信息
	 */
	@RequestMapping("/getydkhbt")
	@ResponseBody
	public String getkhbt(@RequestParam(value="ssrdwbm",required=false)String ssrdwbm,
						  @RequestParam(value="ssrywlx",required=false)String ssrywlx,
						  @RequestParam(value="ssrgh",required=false)String ssrgh,
						  @RequestParam(value="ssryear",required=false)String ssryear,
						  @RequestParam(value="ssrseason",required=false)String ssrseason,
						  @RequestParam(value="ssrbmbm",required=false)String ssrbmbm,
						  HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();

		// 指标考评表头
		String ydkhbt = StringUtils.EMPTY;
		// 月度考核
		Ydkh ydkh = null;
		// 月度考核分值
		ydkhqbtg ydkhDetail = null;

		// 当前年份
		Integer nowYear = DateUtil.getYear(new Date());
		String ksrq = nowYear + "-01-01";
		String jsrq = nowYear + "-12-31";

		// 查询最新的考核日期配置
		XtGrjxKhrq khrqpz = xtGrjxKhrqService.getGrjxKhrqPzLatest();
		if(!Objects.isNull(khrqpz)){
			ksrq = nowYear+khrqpz.getKsrq().substring(4);
			jsrq = nowYear+khrqpz.getJsrq().substring(4);
		}

		Integer year = nowYear;
		if(StringUtils.isNotBlank(ssryear) && !"null".equals(ssryear)){
			year = Integer.parseInt(ssryear);
			ksrq = year + "-01-01";
			jsrq = year + "-12-31";
		}

		try {
//			ydkhbt = ydkhService.getkhbt(ssrdwbm, ssrywlx, ssrgh, ssryear, ssrseason);
			ydkhDetail = grjxYwkhfzService.getGrjxkhByParams(ssrdwbm,ssrgh,year,DateUtil.stringtoDate(ksrq,"yyyy-MM-dd"),DateUtil.stringtoDate(jsrq,"yyyy-MM-dd"),ssrbmbm);
			if(!Objects.isNull(ydkhDetail)||StringUtils.isNotBlank(ydkhDetail.getZbkpbt())){
				ydkhbt = ydkhDetail.getZbkpbt();
				// 月度考核信息
				ydkh = ydkhService.selectByPrimaryKey(ydkhDetail.getYdkhid());
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(StringUtils.EMPTY, e);
		}
		map.put("tableHead", ydkhbt);
		map.put("ydkh", ydkh);
		map.put("ydkhfz", ydkhDetail);
		return Constant.GSON.toJson(map); // ydkhbt
//		return ydkhbt;
	}

	/**
	 * 通过单位编码、工号、年份、月份、业务类型获取个人绩效的表体内容
	 *
	 * @param request 单位编码 dwbm 工号 gh 年份 year 月份 month 业务类型 ywlx
	 * @return json 考核分值信息
	 */
	@RequestMapping(value = "/getydkh")
	@ResponseBody
	public String getYdkh(@RequestParam(value="dwbm",required=true)String dwbm,
						  @RequestParam(value="dwjb",required=true)String dwjb,
						  @RequestParam(value="gh",required=true)String gh,
						  @RequestParam(value="year",required=false)String year,
						  @RequestParam(value="season",required=false)String season,
						  @RequestParam(value="ywlx",required=false)String ywlx,
						  @RequestParam(value="bgbt",required=false)String bgbt,
						  @RequestParam(value="ryType",required=false)String ryType,
						  @RequestParam(value="ssrbmbm",required=false)String ssrbmbm,
						  HttpServletRequest request) throws Exception{
		int nowYear = DateUtil.getYear(new Date());
//		int seasonParam = 0;
//		if (season.length() > 1) {
//			season = season.substring(1, 2);
//		}

//		String [] ywlxArray = null;
//		Map map = new HashMap();
		//判断获取的年月是否是空
		if (StringUtils.isBlank(year)) {
//			map = ydkhService.getNewyj(dwbm, gh);
//			double yeard = (double) map.get("p_year");
//			double seasond = (double) map.get("p_jd");
//			year = (int) yeard;
//			seasonParam = (int) seasond;
		} else {
			nowYear = Integer.parseInt(year);
//			seasonParam = Integer.parseInt(season);
		}

		String khKsrq = year+"-01-01";
		String khJsrq = year+"-12-31";
		XtGrjxKhrq khrqpz = xtGrjxKhrqService.getGrjxKhrqPzLatest();
		if(!Objects.isNull(khrqpz)){
			khKsrq = nowYear+khrqpz.getKsrq().substring(4);
			khJsrq = nowYear+khrqpz.getJsrq().substring(4);
		}

		/*if (StringUtils.isBlank(ywlx)) {
			List<String> bmysList = spService.getBmysByGh(dwbm, gh);
			String[] ywlxarray = new String[bmysList.size()];
			for (int i = 0; i < bmysList.size(); i++) {
				ywlxarray[i] = spService.getYwlxByBmys(bmysList.get(i));
			}
			ywlx = ywlxarray[0];
//			ywlxArray = ywlxarray;
		}*/

		// 判断月度考核信息是否存在
//		boolean isexist = ydkhService.isExist(dwbm, gh, nowYear, seasonParam);

		// 判断类型的指标是否存在
//		boolean hasZbkhfz = ydkhService.hasZbkhfz(dwbm, gh, nowYear, seasonParam, ywlx);// 判断类型的指标是否存在

		List<Ywkhfz> ywkhfzlist = new ArrayList<>();
		String ydkhZbnr = StringUtils.EMPTY;

		// 如果存在该信息，则直接将考核分值信息取回；
		// 如果不存在，则新建
		try {
//			if (isexist && hasZbkhfz) { // 月度考核信息存在且类型的指标存在，只需要获取指标信息
//				ywkhfzlist = ydkhService.getYwkhfz(dwbm, gh, nowYear, seasonParam, ywlx);
			ydkhqbtg ydkhDetail = grjxYwkhfzService.getGrjxKhBtAndKhNrByParams(dwbm,dwjb,gh,nowYear,DateUtil.stringtoDate(khKsrq,"yyyy-MM-dd"),DateUtil.stringtoDate(khJsrq,"yyyy-MM-dd"),ssrbmbm);
			ydkhZbnr = ydkhDetail.getZbkpgl();
			/*} else {
//				ydkhService.addYdkh(dwbm, gh, year, season,ywlxArray);
				ydkhService.addYdkh(dwbm, gh, nowYear, seasonParam, ywlx, isexist, bgbt,ryType);
				ywkhfzlist = ydkhService.getYwkhfz(dwbm, gh, nowYear, seasonParam, ywlx);
			}*/
		}catch (Exception e){
			e.printStackTrace();
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
//		resultMap.put("rows", Constant.JSON_PARSER.parse(ywkhfzlist.get(0).getZbkpgl()));
		resultMap.put("rows", Constant.JSON_PARSER.parse(ydkhZbnr));
		return Constant.GSON.toJson(resultMap);
	}


	/**
	 * 返回年月。如果没有年月，通过单位编码和工号查出月度考核表里的最新年月。如果没有，查出的是目前的年和月。
	 *
	 * @param request 请求参数
	 * @return 最新年度和季度
	 */
	@RequestMapping(value = "/getNewym")
	public @ResponseBody
	String getNewym(HttpServletRequest request) {
		String dwbm = request.getParameter("dwbm");
		String gh = request.getParameter("gh");
		String yearstr = request.getParameter("year");
		String seasonstr = request.getParameter("season");
		if (seasonstr.length() > 1 && !"undefined".equals(seasonstr)) {
			seasonstr = seasonstr.substring(1, 2);
		}
		int year = 0;
		int season = 0;
		Map map = null;
		Map<String, Object> map1 = new HashMap<String, Object>();
		if (yearstr == null || seasonstr == null || "".equals(yearstr) || "".equals(seasonstr) || !"undefined".equals(seasonstr)) {
			map = ydkhService.getNewyj(dwbm, gh);
			double yeard = (double) map.get("p_year");
//			if (StringUtils.isNotEmpty((String)map.get("p_jd"))) {
//				double seasond = (double) map.get("p_jd");
//				season = (int) seasond;
//			}
			year = (int) yeard;
			map1.put("year", year);
			map1.put("season", season);
		} else {
			year = Integer.parseInt(yearstr);
//			if (!"undefined".equals(seasonstr)) {
//				season = Integer.parseInt(seasonstr);
//			} else {
//				season = 0;
//			}
			map1.put("year", year);
			map1.put("season", season);
		}
		return Constant.GSON.toJson(map1);
	}

	/**
	 * 更新业务考核分值
	 *
	 * @param request 请求参数
	 * @return 季度考核编码
	 */
	@RequestMapping(value = "/updateywkhfz")
	public @ResponseBody
	String updateYwkhfz(HttpServletRequest request) {
//		String dwbm = request.getParameter("dwbm");
//		String gh = request.getParameter("gh");
//		int year = Integer.parseInt(request.getParameter("year"));
//		int season = Integer.parseInt(request.getParameter("season"));
//		String ywlx = request.getParameter("ywlx");
		String ywlx = "3";
		// 自评总分
		double zpzf = Double.parseDouble(request.getParameter("zpzf"));
		// 评价总分
		double pjzf = Double.parseDouble(request.getParameter("pjzf"));
		// 指标考评概览
		String zbkpgl = request.getParameter("zbkpgl");
		// 指标考评得分
		String zbkpdf = request.getParameter("zbkpdf");
		// 月度考核id
		String ydkhid = request.getParameter("ydkhid");
		// 当前审批页面类型
		String currentspymtype = request.getParameter("spymtype");
		// 人员类型
		String typeOfPer = request.getParameter("typeOfPer");
		// 人员角色
		String roleOfPer = request.getParameter("roleOfPer");
		// 部门类别编码
		String bmlbbm = request.getParameter("bmlbbm");

		// 0发起审批
		// 1部门审批
		// 2人事部审批
		RYBM ry = (RYBM) request.getSession().getAttribute("ry");

		String currentgh = ry.getGh();// 当前登录人的工号
		String currentmc = ry.getMc();// 当前登录人的姓名

//		String jdkhid = null;
		double score = 0;

//		jdkhid = ydkhService.selectJdkhid(dwbm, gh, year, season);// 季度考核id
		String y = "0";
		if (ydkhid != null) {
			y = ydkhService.updateYwkhfz(ydkhid, zpzf, pjzf, zbkpgl, zbkpdf);
			score = ydkhService.calculateYdkhzf(ydkhid);
			ydkhService.updateydzfbyid(ydkhid, score);
			if ("0".equals(currentspymtype)) {
				ydkhService.updatepfrjl(ydkhid, currentgh, currentmc,
						StringUtils.EMPTY, StringUtils.EMPTY,
						StringUtils.EMPTY, StringUtils.EMPTY,
						StringUtils.EMPTY, StringUtils.EMPTY);
			}
//			if ("1".equals(currentspymtype)) {
//				ydkhService.updatepfrjl(ydkhid, StringUtils.EMPTY, StringUtils.EMPTY,
//						currentgh, currentmc, StringUtils.EMPTY, StringUtils.EMPTY,
//						StringUtils.EMPTY, StringUtils.EMPTY);
//			}
//			if ("2".equals(currentspymtype)) {
//				ydkhService.updatepfrjl(ydkhid, StringUtils.EMPTY, StringUtils.EMPTY,
//						StringUtils.EMPTY, StringUtils.EMPTY, currentgh, currentmc,
//						StringUtils.EMPTY, StringUtils.EMPTY);
//			}
//			if ("5".equals(currentspymtype)) {
//				ydkhService.updatepfrjl(ydkhid, StringUtils.EMPTY, StringUtils.EMPTY,
//						StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
//						currentgh, currentmc);
//			}
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("Y", y);
		resultMap.put("jdkhid", ydkhid);

		return Constant.GSON.toJson(resultMap);
	}

	/**
	 * 获取评分人信息
	 *
	 * @param request 请求参数
	 * @return 评分人信息
	 */
	@RequestMapping(value = "/getpfrjl")
	public @ResponseBody
	String getPfrjl(HttpServletRequest request) {
//		String ssrdwbm = request.getParameter("dwbm");// 绩效所属人的单位编码
//		String ssrgh = request.getParameter("gh");// 绩效所属人的工号
//		int ssryear = Integer.parseInt(request.getParameter("year"));// 年份
//		int ssrseason = Integer.parseInt(request.getParameter("season"));// 季度
//		String ssrywlx = request.getParameter("ywlx");// 业务类型
		String ydkhfzid = request.getParameter("ydkhfzid");// 月度考核分值

//		String jdkhid = ydkhService.selectJdkhid(ssrdwbm, ssrgh, ssryear, ssrseason);

//		List<String> pfrlist = ydkhService.getpfrjl(jdkhid, ssrywlx);
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		resultMap.put("zpr", pfrlist.get(0));
//		resultMap.put("bmpfr", pfrlist.get(Constant.NUM_1));
//		resultMap.put("rsbpfr", pfrlist.get(Constant.NUM_2));
//		resultMap.put("jcpfr", pfrlist.get(Constant.NUM_3));

		ydkhqbtg res = new ydkhqbtg();
		res = grjxYwkhfzService.selectByPrimaryKey(ydkhfzid);

		return Constant.GSON.toJson(res);
	}

	/**
	 * 更新评分人信息
	 * 2018/9/19修改（没被调用）
	 * @param request 请求参数
	 * @return null
	 */
	@RequestMapping(value = "/updatepfrjl")
	public @ResponseBody
	String updatePfrjl(HttpServletRequest request) {
		String ssrdwbm = request.getParameter("dwbm");// 绩效所属人的单位编码
		String ssrgh = request.getParameter("gh");// 绩效所属人的工号
		int ssryear = Integer.parseInt(request.getParameter("year"));// 年份
		int ssrseason = Integer.parseInt(request.getParameter("season"));// 季度
		String ssrywlx = request.getParameter("ywlx");// 业务类型
		String currentspymtype = request.getParameter("spymtype");// 当前审批页面类型
		// 0发起审批
		// 1部门审批
		// 2人事部审批
		RYBM ry = (RYBM) request.getSession().getAttribute("ry");

		String currentgh = ry.getGh();// 当前登录人的工号
		String currentmc = ry.getMc();// 当前登录人的姓名

		String jdkhid = ydkhService.selectJdkhid(ssrdwbm, ssrgh, ssryear, ssrseason);

		if ("0".equals(currentspymtype)) {
			ydkhService.updatepfrjl(jdkhid, currentgh, currentmc,
					StringUtils.EMPTY, StringUtils.EMPTY,
					StringUtils.EMPTY, StringUtils.EMPTY,
					StringUtils.EMPTY, StringUtils.EMPTY);
		}
		if ("1".equals(currentspymtype)) {
			ydkhService.updatepfrjl(jdkhid, StringUtils.EMPTY,
					StringUtils.EMPTY, currentgh, currentmc,
					StringUtils.EMPTY, StringUtils.EMPTY,
					StringUtils.EMPTY, StringUtils.EMPTY);
		}
		if ("2".equals(currentspymtype)) {
			ydkhService.updatepfrjl(jdkhid, StringUtils.EMPTY,
					StringUtils.EMPTY, StringUtils.EMPTY,
					StringUtils.EMPTY, currentgh, currentmc,
					StringUtils.EMPTY, StringUtils.EMPTY);
		}
		if ("5".equals(currentspymtype)) {
			ydkhService.updatepfrjl(jdkhid, StringUtils.EMPTY,
					StringUtils.EMPTY, StringUtils.EMPTY,
					StringUtils.EMPTY, StringUtils.EMPTY,
					StringUtils.EMPTY, currentgh, currentmc);
		}
		return null;
	}

	/**
	 * 获取备注信息
	 *
	 * @param request 请求参数
	 * @return 备注信息
	 */
	@RequestMapping(value = "/getbz")
	public @ResponseBody
	String getbz(HttpServletRequest request) {
//		String ssrdwbm = request.getParameter("dwbm");// 绩效所属人的单位编码
//		String ssrgh = request.getParameter("gh");// 绩效所属人的工号
//		int ssryear = Integer.parseInt(request.getParameter("year"));// 年份
//		int ssrseason = Integer.parseInt(request.getParameter("season"));// 季度
//		String ssrywlx = request.getParameter("ywlx");// 业务类型
		String ydkhfzid = request.getParameter("ydkhfzid"); // 月度考核分值id
		String pflx = request.getParameter("pflx");//评分类型 0:自评，1:部门评分，2:交叉评分，3:考评委员会评分
		String zbxbm = request.getParameter("zbxbm");//指标项编码

		List<Pfcl> bzList = new ArrayList<Pfcl>();
		//获取个人绩效评分材料信息
		bzList = ydkhService.getbz(ydkhfzid, pflx, zbxbm);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("id", bzList.get(0).getId());
		resultMap.put("bz", bzList.get(0).getBz());
		if (StringUtils.isBlank(bzList.get(0).getFjxx())) {
			resultMap.put("fjxx", StringUtils.EMPTY);
		} else {
			resultMap.put("fjxx", Constant.JSON_PARSER.parse(bzList.get(0).getFjxx()));
		}
		return Constant.GSON.toJson(resultMap);
	}

	/**
	 * 更新备注信息
	 *
	 * @param request 请求参数
	 * @return 执行结果
	 */
	@RequestMapping(value = "/setbz")
	public @ResponseBody
	String updatabz(HttpServletRequest request) {
//		String ssrdwbm = request.getParameter("dwbm");// 绩效所属人的单位编码
//		String ssrgh = request.getParameter("gh");// 绩效所属人的工号
//		int ssryear = Integer.parseInt(request.getParameter("year"));// 年份
//		int ssrseason = Integer.parseInt(request.getParameter("season"));// 季度
//		String ssrywlx = request.getParameter("ywlx");// 业务类型
		String ydkhfzid = request.getParameter("ydkhfzid"); // 月度考核分值id
		String pflx = request.getParameter("pflx");//评分类型 0:自评，1:部门评分，2:交叉评分，3:考评委员会评分
		String zbxbm = request.getParameter("zbxbm");//指标项编码
		String fjxx = request.getParameter("fjxx");//附件ID数组
		String bz = request.getParameter("bz");//备注

		Map<String, Object> resultMap = new HashMap<String, Object>();
		//添加或更新个人绩效评分材料信息
		int num = ydkhService.updatabz(ydkhfzid, pflx, zbxbm, fjxx, bz);
		if (num == 1) {
			resultMap.put("status", "1");
			return Constant.GSON.toJson(resultMap);
		} else {
			resultMap.put("status", "0");
			return Constant.GSON.toJson(resultMap);
		}
	}

	/**
	 * 判断是否存在评分材料
	 *
	 * @param request 请求参数
	 * @return 存在返回1，不存在返回0
	 */
	@RequestMapping(value = "/isExistPfcl")
	public @ResponseBody
	String isExistPfcl(HttpServletRequest request) {
//		String ssrdwbm = request.getParameter("dwbm");// 绩效所属人的单位编码
//		String ssrgh = request.getParameter("gh");// 绩效所属人的工号
//		int ssryear = Integer.parseInt(request.getParameter("year"));// 年份
//		String seasonstr = request.getParameter("season");//季度
//		if (seasonstr.length() > 1) {
//			seasonstr = seasonstr.substring(1, 2);
//		}
//		int ssrseason = Integer.parseInt(seasonstr);//
//		String ssrywlx = request.getParameter("ywlx");// 业务类型
		String pflx = request.getParameter("pflx");//评分类型 0:自评，1:部门评分，2:交叉评分，3:考评委员会评分
		String zbxbm = request.getParameter("zbxbm");//指标项编码
		String ydkhid = request.getParameter("ydkhid");// 月度考核id
		String ydkhfzid = request.getParameter("ydkhfzid");// 月度考核分值id

//		List<Ywkhfz> ywkhfzList = ydkhService.getYwkhfz(ssrdwbm, ssrgh, ssryear, ssrseason, ssrywlx);
//		String ywkhfzid = ywkhfzList.get(0).getId();

		// 直接根据业务考核id(ydkhid)获取考核分值信息
		boolean isexist = false;
		try {
			isexist = ydkhService.isExistPfcl(ydkhfzid, pflx, zbxbm);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (isexist) {
			List<Pfcl> bzList = new ArrayList<Pfcl>();
			//获取个人绩效评分材料信息
			try {
				bzList = ydkhService.getbz(ydkhfzid, pflx, zbxbm);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (StringUtils.isBlank(bzList.get(0).getFjxx())
					&& StringUtils.isBlank(bzList.get(0).getBz())) {
				resultMap.put("status", "0");
			} else {
				resultMap.put("status", "1");
			}
		} else {
			resultMap.put("status", "0");
		}
		return Constant.GSON.toJson(resultMap);
	}

	/**
	 * 获取所属人单位级别信息
	 *
	 * @param request 请求参数
	 * @return 单位级别
	 */
	@RequestMapping("/getssrdwjb")
	@ResponseBody
	public String getSsrDwjb(HttpServletRequest request) {
		String ssrdwbm = request.getParameter("ssrdwbm");
		String ssrdwjb = (String) hCPZService.getdwjb(ssrdwbm).get("p_dwjb");
		return ssrdwjb;
	}

	/**
	 * 得到是否发起审批
	 *
	 * @param request
	 * @return String
	 */
	@RequestMapping("/getsffqsp")
	public @ResponseBody
	String getsffqsp(HttpServletRequest request) {
		String spstid = request.getParameter("spstid");

		int p = ydkhService.sffqsp(spstid);

		if (p > 0) {
			return "1";
		}
		return "0";
	}

	/**
	 * 删除月度考核
	 *
	 * @param request
	 * @return String
	 */
	@RequestMapping("/delydkh")
	public @ResponseBody
	String delydkh(HttpServletRequest request) {
		String ydkhid = request.getParameter("ydkhid");

		String y = ydkhService.delydkh(ydkhid);

		return y;
	}

	/**
	 * 通过单位编码 工号查询个人绩效的年度季度
	 *
	 * @param request
	 * @return String
	 */
	@RequestMapping("getndjdlist")
	public @ResponseBody
	String getndjdlist(HttpServletRequest request) {
		String dwbm = request.getParameter("dwbm");
		String gh = request.getParameter("gh");
		String year = request.getParameter("year");

		List<String> temp = null;

		Map<String, Object> map = ydkhService.getndjdlist(dwbm, gh);

		if (StringUtils.isEmpty(year)) {
			year = "years";
		}

		temp = (List<String>) map.get(year);

		return Constant.GSON.toJson(temp);
	}


	/****导出自己的个人绩效信息到excel**/
	@RequestMapping("/exportExcel")
	@ResponseBody
	public String exportExcel(HttpServletRequest request) throws Exception {

		// 获取表头//表体数据
		String dwmc = request.getParameter("dwmc");// 单位名称
		String dwbm = request.getParameter("dwbm");// 单位编码
		String dwjb = request.getParameter("dwjb");// 单位编码
		String year = request.getParameter("year");// 年度
		String season = request.getParameter("season");// 季度
		String grjcdf = request.getParameter("grjcdf");// 个人基础得分
		if (StringUtils.isEmpty(grjcdf)) {
			grjcdf = "0";
		}
		String grpjzf = request.getParameter("grpjzf");// 个人评价总分
		if (StringUtils.isEmpty(grpjzf)) {
			grpjzf = "0";
		}
		String pddj = request.getParameter("pddj");// 评定等级
		if (StringUtils.isEmpty(pddj)) {
			pddj = "暂未评定";
		}

		String ryType = request.getParameter("ryType");// 人员类型
		String zpr = request.getParameter("zpr");// 自评人
		String mc = request.getParameter("ssr");; // 个人绩效所属人员
		String gh = request.getParameter("ssrgh");// 个人绩效所属人员工号
		String bmlb = request.getParameter("bmlb"); // 部门类别
		String bmlbbm = request.getParameter("bmlbbm"); // 部门类别编码

		String jcgpfr = request.getParameter("jcgpfr"); // 检察官评分人 （只有检察辅助人员有这一项指标）
		String bmpfr = request.getParameter("bmpfr");// 部门评分人
		String fgldpfr = request.getParameter("fgyldpfr"); // 分管院领导评分人
		String jcpfr = request.getParameter("jcpfr");// 交叉评分人 （省院没有这一项指标）
		String rsbpfr = request.getParameter("rsbpfr");// 考评委员会评分人

		String strTitles = request.getParameter("tableTh");// 表头
		String strColumnName = request.getParameter("columnName");// 表头对应的feild
		String strRows = request.getParameter("rows");// 表格数据
		String strFoot = request.getParameter("foot");// 页脚行数据

		RYBM ry = (RYBM) request.getSession().getAttribute("ry"); // 获取登录人单位编码、工号

		//调用接口查询人员信息-单位名称、部门名称、
		List<String> resList = new ArrayList<String>();
		resList = loginService.getGrxxByDwbmAndGh(dwbm,gh);
		//转换并获取人员信息list的信息
		JsonObject ryJsonObj  = (JsonObject)Constant.JSON_PARSER.parse(
				Constant.GSON.toJson(resList.get(0)));
		String bmmc = ryJsonObj.get("BMMC").getAsString();

		//转换数据
		//去除表头的备注、以及表体的备注对应列
		JsonArray titleArrayTemp = (JsonArray)Constant.JSON_PARSER.parse(strTitles);
		JsonArray titleArray = new JsonArray();
		for (int i = 0; i < titleArrayTemp.size(); i++) {
			if ("备注".equals(titleArrayTemp.get(i).getAsString())) {
				titleArrayTemp.remove(i);
			}
		}
		titleArray.addAll(titleArrayTemp);

		JsonArray columnNameArray = (JsonArray)Constant.JSON_PARSER.parse(strColumnName);
		JsonArray rowsArrayTemp =  (JsonArray)Constant.JSON_PARSER.parse(strRows);  //表体内容
		JsonArray rowsArray = new JsonArray();
		Entry<String, JsonElement> tableKeyValue = null;
		String tableKey;
		rowsArray.addAll(rowsArrayTemp);
		JsonArray footArray = (JsonArray)Constant.JSON_PARSER.parse(strFoot);

		//文本信息--文件名、工作薄名称、文件存放地址
		// 服务器路径
//		final String DEFAULT_EXCEL_PATH = request.getServletContext().getRealPath("/");
		// 文件存放地址："E:/";
		final String DEFAULT_EXCEL_PATH = xtfjpathservice.getPath().trim();;
		final String DEFAULT_EXCEL_SUBFIX = ".xls";
		String fileName = StringUtils.EMPTY;
		fileName = year+"年"+mc+"个人绩效信息";
		//判断文件是否存在  存在则生成新的文件
		// 无需判断，如果文件存在，会自动在文件名后生成(数字)
//		File file = new File(DEFAULT_EXCEL_PATH + fileName + DEFAULT_EXCEL_SUBFIX);
//		if (file.exists()) {
//			fileName = year+"年"+mc+"个人绩效信息"+"("+Identities.randomString(2,true)+")";
//		}else{
//			fileName = year+"年"+mc+"个人绩效信息";
//		}
		String filePath = DEFAULT_EXCEL_PATH + fileName + DEFAULT_EXCEL_SUBFIX;
		String sheetName = mc + "的个人绩效信息";
		String titleName = year + "年" + mc + "的个人绩效";

		//组装表头表体数据成JsonArray
		JsonArray jsonArr = new JsonArray();

		// 调用生成excel表头表体的方法
		jsonArr = setExcelTitleByRyType(ryType, rowsArray, footArray, jsonArr, titleArray, dwbm, dwmc, bmlb, bmlbbm,
				gh, mc, pddj, jcgpfr, bmpfr, fgldpfr, rsbpfr, jcpfr, grjcdf, grpjzf, dwjb);

		//调用专用excel导出方法
		OutputStream outXlsx = new FileOutputStream(filePath);
		try {
			ExcelUtil.exportExcelNotUtils(outXlsx,sheetName,titleName,jsonArr,true); // true表示需要合并单元格
		} catch (Exception e) {
			e.printStackTrace();
		}
		outXlsx.flush();
		outXlsx.close();

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("filename", fileName+DEFAULT_EXCEL_SUBFIX);
		map.put("filepath", filePath);
		return Constant.GSON.toJson(map);
	}


	/**下载个人绩效
	 * @param response response
	 * @param request request
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/getExcel")
	public void download(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		String filename = request.getParameter("filename");
		String filepath = request.getParameter("filepath");
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		//设置Content-Disposition
		response.setHeader("Content-Disposition", "attachment;filename="+filename);

		try {
			response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(filename, "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			//日志记录
			LOG.error("导出excel出错!",e1);
			throw e1;
		}

		try {
			File file = new File(filepath);
			InputStream inputStream = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[Constant.NUM_1024];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			//日志记录
			LOG.info("导出excel出错");
		} catch (IOException e) {
			e.printStackTrace();
			//日志记录
			LOG.info("导出excel出错");
		}
	}


	@RequestMapping(value = "/addGrjxAndDetail", method = RequestMethod.POST)
	@ResponseBody
	public String addGrjxAndDetail(@RequestParam(value="typeid",required=true)String typeid,
								   @RequestParam(value="ksrq",required=false)String ksrq,
								   @RequestParam(value="jsrq",required=false)String jsrq,
								   @RequestParam(value="dwjb",required=true)String dwjb,
								   @RequestParam(value="bmlbbm",required=true)String bmlbbm,
								   @RequestParam(value="table_head",required=true)String table_head,
								   @RequestParam(value="jslx",required=true)String jslx,
								   HttpServletRequest request) throws ParseException {
		Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);
		Integer grjxResult = 0 ;
		Integer kbzbResult = 0; //考核指标记录（默认为0）
		Integer grjxDetailResult = 0 ;
		RYBM ry =(RYBM)request.getSession().getAttribute("ry");
		String dlrDwbm = ry.getDwbm();
		String dlrBmbm = ry.getBmbm(); // 部门编码应该由参数传进来，因为某个人可能存在于多个部门，在选择部门类别时将该部门类别对应的bmbm确定
		String dlrGh = ry.getGh();
		//////////////////////////////////////////////////////////个人绩效的添加\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		/**
		 * 思路：
		 * 1、获取考核日期，
		 * 2、获取考核人员属于哪一类型
		 * 3、获取这一类型人员对应的考核指标（根据人员类型id查询到考核指标、 查询人员类型配置表、添加个人绩效表、绩效详情指标表）
		 */

		//准备考核日期

		String ksrqStr = "01-01";
		if(StringUtils.isNotBlank(ksrq)){
			ksrqStr = ksrq;
		}
		String jsrqStr = "12-31";
		if(StringUtils.isNotBlank(jsrq)){
			jsrqStr = jsrq;
		}

		Integer year = DateUtil.getYear(new Date());
		String jxKsrq = year+"-"+ksrqStr;
		String jxJsrq = year+"-"+jsrqStr;
		XtGrjxKhrq khrqpz = xtGrjxKhrqService.getGrjxKhrqPzLatest();
		if(!Objects.isNull(khrqpz)){
			//如果不超期，则创建去年的绩效考核
			if(!khrqpz.isOverdue()){
				year = year-1;
			};

			jxKsrq = year+khrqpz.getStartDateExcludeYear();
			jxJsrq = year+khrqpz.getEndDateExcludeYear();
		}

		//准备考核类型-指标
		String ryTypeId = typeid;
		String bmlbbmParam = bmlbbm;
		if(StringUtils.isBlank(typeid)||"null".equals(typeid)){
			List<XtGrjxRypz> listInfoGrjxKhry = xtGrjxKhryService.getGrjxKhryPzByDwGh(dlrDwbm,dlrGh);
			XtGrjxRypz xtGrjxRypz = new XtGrjxRypz();
			if(listInfoGrjxKhry.size()>0){
				xtGrjxRypz = listInfoGrjxKhry.get(0); //一般应该一个人对应一个类型
				if(!Objects.isNull(xtGrjxRypz)){
					ryTypeId = xtGrjxRypz.getTypeid();
					if(StringUtils.isBlank(bmlbbm)||"null".equals(bmlbbm)){
						bmlbbmParam = xtGrjxRypz.getBmlbbm();
					}
				}
			}else{
				resMap.put("result",grjxResult);
				return Constant.GSON.toJson(resMap);
			}
		}

		//查询考核指标表是否存在该配置。
		kbzbResult = grjxZbpzService.isExist(bmlbbm,dwjb,typeid,year.toString());
		if(kbzbResult>0){
			//查询是否存在个人绩效
			Ydkh grjxYdkh = ydkhService.getGrjxByParams(dlrDwbm,dlrGh,year,DateUtil.stringtoDate(jxKsrq,"yyyy-MM-dd"),DateUtil.stringtoDate(jxJsrq,"yyyy-MM-dd"));
			if(!Objects.isNull(grjxYdkh)){
				resMap.put("grjxObj",grjxYdkh);
			}else{
				//dwjb --- year -- typeid -- bmlb
				List<Map<String,Object>> grjxZbpzList = grjxZbpzService.getInfoOfZbpz(dwjb,bmlbbmParam,ryTypeId,year.toString()); //selectById("6037DF1CE9314583AEED9DC520D071F1");
				String grjxKhBt = StringUtils.EMPTY; //表头
				String grjxKhnr = StringUtils.EMPTY; //表体内容
				String grjxBmlbbm = StringUtils.EMPTY; //部门类别编码
				if(grjxZbpzList.size()>0){
					Map<String,Object> grjxZbpzMap = grjxZbpzList.get(0);
					for (Entry<String, Object> entry : grjxZbpzMap.entrySet()) {
//			System.out.println(entry.getKey() +"--"+ entry.getValue());
						if("KHBT".equals(entry.getKey())){
							grjxKhBt = entry.getValue().toString();
						}
						if("KHNR".equals(entry.getKey())){
							grjxKhnr = entry.getValue().toString();
						}
						if ("BMLB".equals(entry.getKey())){
							grjxBmlbbm = entry.getValue().toString();
						}
					}
				}

				//ydkhid--dwbm--gh--ksrq--jsrq--year--month--ydkhzf--jd--sfsp--cjsj
				Ydkh ydkh = new Ydkh();
				ydkh.setYdkhid(Identities.get32LenUUID());
				ydkh.setDwbm(ry.getDwbm());
				ydkh.setGh(ry.getGh());
				ydkh.setYear(year);
				ydkh.setSfsp("0");  // 0 未审批  1 已审批
				ydkh.setYdkhzf(0);
				ydkh.setKsrq(DateUtil.stringtoDate(jxKsrq,"yyyy-MM-dd"));
				ydkh.setJsrq(DateUtil.stringtoDate(jxJsrq,"yyyy-MM-dd"));
				ydkh.setCjsj(new Date());

				//id--ydkhid--ywlx--ywzf--zbkpgl--zbkpdf--zprgh--zpr--bmpfrgh--bmpfr--rsbpfrgh--rsbpfr--zbkpbt--spsftg
				// --jcpfrgh--jcpfr--zpjdf--pdjbmc--pdjb--bmbm;
				ydkhqbtg ydkhqbtg = new ydkhqbtg();
				ydkhqbtg.setId(Identities.get32LenUUID());
				ydkhqbtg.setYdkhid(ydkh.getYdkhid());
				ydkhqbtg.setYwzf(0);
				ydkhqbtg.setDwjb(dwjb);
				ydkhqbtg.setYear(year);
				ydkhqbtg.setYwlx(grjxBmlbbm);
				ydkhqbtg.setBmbm(ry.getBmbm());
				ydkhqbtg.setZbkpgl(grjxKhnr); //个人绩效datagrid表体【从个人绩效指标配置表的表获取】
				ydkhqbtg.setZbkpbt(table_head);// 个人绩效datagrid表头【从个人绩效指标配置表头表获取】 grjxKhBt
				ydkhqbtg.setRylx(typeid); // 人员类型id
				ydkhqbtg.setRyjs(jslx); // 角色类型值

				grjxResult = ydkhService.insertSelective(ydkh);
				if(grjxResult>0){
					grjxDetailResult = grjxYwkhfzService.insertSelective(ydkhqbtg);
				}
				if(grjxDetailResult <= 0 ){
					ydkhService.deleteByPrimaryKey(ydkh.getYdkhid());
					grjxResult = 0;
				}
			}
		}else{
			resMap.put("khzbResult",kbzbResult);
			return Constant.GSON.toJson(resMap);
		}

		resMap.put("result",grjxResult);
		return Constant.GSON.toJson(resMap);
	}

	/**
	 * 添加个人绩效的时候，查询是否存在该时间段的绩效
	 * @param typeid
	 * @param ksrq
	 * @param jsrq
	 * @param dwjb
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getGrjxByParams", method = RequestMethod.POST)
	@ResponseBody
	public String getGrjxByParams(@RequestParam(value="typeid",required=false)String typeid,
								  @RequestParam(value="ksrq",required=false)String ksrq,
								  @RequestParam(value="jsrq",required=false)String jsrq,
								  @RequestParam(value="dwjb",required=false)String dwjb,
								  HttpServletRequest request) throws Exception {
		Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);
		RYBM ry =(RYBM)request.getSession().getAttribute("ry");
		String dlrDwbm = ry.getDwbm();
		String dlrBmbm = ry.getBmbm();
		String dlrGh = ry.getGh();

		Integer nowYear = DateUtil.getYear(new Date());
		String jxKsrq = nowYear+"-"+ksrq;
		String jxJsrq = nowYear+"-"+jsrq;
		XtGrjxKhrq khrqpz = xtGrjxKhrqService.getGrjxKhrqPzLatest();
		if(!Objects.isNull(khrqpz)){
			jxKsrq = nowYear+khrqpz.getStartDateExcludeYear();
			jxJsrq = nowYear+khrqpz.getEndDateExcludeYear();
		}

		//准备考核类型-指标
		String ryTypeId = typeid;
		List<XtGrjxRypz> listInfoGrjxKhry = xtGrjxKhryService.getGrjxKhryPzByDwGh(dlrDwbm,dlrGh);
		XtGrjxRypz xtGrjxRypz = listInfoGrjxKhry.get(0); //一般应该一个人对应一个类型
		if(!Objects.isNull(xtGrjxRypz)){
			ryTypeId = xtGrjxRypz.getTypeid();
		}

		Ydkh ydkh = ydkhService.getGrjxByParams(dlrDwbm,dlrGh,nowYear,DateUtil.stringtoDate(jxKsrq,"yyyy-MM-dd"),DateUtil.stringtoDate(jxJsrq,"yyyy-MM-dd"));
		return Constant.GSON.toJson(ydkh);
	}


	@RequestMapping("/getGrjxKhzb")
	@ResponseBody
	public String getGrjxKhzb(@RequestParam(value="dwbm",required=true)String dwbm,
							  @RequestParam(value="dwjb",required=false)String dwjb,
							  @RequestParam(value="gh",required=true)String gh,
							  @RequestParam(value="year",required=false)String ssryear,
							  @RequestParam(value="bmbm",required=false)String bmbm,
							  HttpServletRequest request) {
		Map<String,Object> map = new HashMap<>();
		Integer nowYear = DateUtil.getYear(new Date());
		String ksrq = nowYear+"-01-01";
		String jsrq = nowYear+"-12-31";
		XtGrjxKhrq khrqpz = xtGrjxKhrqService.getGrjxKhrqPzLatest();
		if(!Objects.isNull(khrqpz)){
			ksrq = nowYear+khrqpz.getStartDateExcludeYear();
			jsrq = nowYear+khrqpz.getEndDateExcludeYear();
		}
		Integer year = nowYear;
		if(StringUtils.isNotBlank(ssryear)&&!"null".equals(ssryear)){
			year = Integer.parseInt(ssryear);
		}

		ydkhqbtg ydkhDetail = new ydkhqbtg();

		try {
			ydkhDetail = grjxYwkhfzService.getGrjxKhBtAndKhNrByParams(dwbm,dwjb,gh,year,DateUtil.stringtoDate(ksrq,"yyyy-MM-dd"),DateUtil.stringtoDate(jsrq,"yyyy-MM-dd"),bmbm);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("获取【个人绩效】的考核指标数据失败！", e);
		}
		map.put("khzbBt",ydkhDetail.getZbkpbt());
		map.put("total",1);
		map.put("rows",Constant.JSON_PARSER.parse(ydkhDetail.getZbkpgl()));
		map.put("ydkhDetail",ydkhDetail);
		return Constant.GSON.toJson(map);
	}

	/**
	 * 根据人员类型生成不同的excel表头第二行、第五行
	 * @param ryType 人员类型
	 * @param rowsArray 表体
	 * @param footArray 页脚
	 * @param jsonArr 返回的json数组
	 * @param titleArray 表头
	 * @param dwbm
	 * @param dwmc
	 * @param bmlb
	 * @param gh
	 * @param mc
	 * @param pddj
	 * @param jcgpfr
	 * @param bmpfr
	 * @param fgldpfr
	 * @param rsbpfr
	 * @param jcpfr
	 * @param grjcdf
	 * @param grpjzf
	 * @return
	 */
	public JsonArray setExcelTitleByRyType(String ryType, JsonArray rowsArray, JsonArray footArray, JsonArray jsonArr,
										   JsonArray titleArray, String dwbm, String dwmc, String bmlb,String bmlbbm,
										   String gh, String mc, String pddj , String jcgpfr, String bmpfr, String fgldpfr,
										   String rsbpfr, String jcpfr, String grjcdf, String grpjzf, String dwjb) {

		//第二行的列名
		JsonObject jsonObj2 = new JsonObject();
		//第三行的列名
		JsonObject jsonObj3 = new JsonObject();

		// 判断属于哪一类人员，人员类型不同第二行表头中的考核人员不同
		/*if (!dwjb.equals("2")) { // 非省院
			// 一般检察辅助人员，包含检察官评分
			if (ryType.equals("jcfzry_lx-1")) {
				jsonObj2.addProperty("line", 2);
				jsonObj2.addProperty("length", 11);
				jsonObj2.addProperty("coloum1", "单位名称");
				jsonObj2.addProperty("coloum2", "部门类别");
				jsonObj2.addProperty("coloum3", "工号");
				jsonObj2.addProperty("coloum4", "姓名");
				jsonObj2.addProperty("coloum5", "检察官评分人");
				jsonObj2.addProperty("coloum6", "部门评分人");
				jsonObj2.addProperty("coloum7", "分管院领导评分人");
				jsonObj2.addProperty("coloum8", "交叉评分人");
				jsonObj2.addProperty("coloum9", "考核委员会评分人");
				jsonObj2.addProperty("coloum10", "自评总分");
				jsonObj2.addProperty("coloum11", "评价总分");
				jsonObj2.addProperty("coloum12", "评定等级");

				jsonObj3.addProperty("line", 3);
				jsonObj3.addProperty("length", 11);
				jsonObj3.addProperty("coloum1", dwmc);
				jsonObj3.addProperty("coloum2", bmlb);
				jsonObj3.addProperty("coloum3", gh);
				jsonObj3.addProperty("coloum4", mc);
				jsonObj3.addProperty("coloum5", jcgpfr);
				jsonObj3.addProperty("coloum6", bmpfr);
				jsonObj3.addProperty("coloum7", fgldpfr);
				jsonObj3.addProperty("coloum8", jcpfr);
				jsonObj3.addProperty("coloum9", rsbpfr);
				jsonObj3.addProperty("coloum10", grjcdf);
				jsonObj3.addProperty("coloum11", grpjzf);
				if ("".equals(pddj) || pddj == null) {
					jsonObj3.addProperty("coloum12", "暂未评定");
				} else {
					jsonObj3.addProperty("coloum12", pddj);
				}

				// 一般检察官和一般司法行政人员第二行表头一样
			} else if (ryType.equals("jcg_lx-1") || ryType.equals("sfxzry_lx-1")) {

				jsonObj2.addProperty("line", 2);
				jsonObj2.addProperty("length", 10);
				jsonObj2.addProperty("coloum1", "单位名称");
				jsonObj2.addProperty("coloum2", "部门类别");
				jsonObj2.addProperty("coloum3", "工号");
				jsonObj2.addProperty("coloum4", "姓名");
				jsonObj2.addProperty("coloum5", "部门评分人");
				jsonObj2.addProperty("coloum6", "分管院领导评分人");
				jsonObj2.addProperty("coloum7", "交叉评分人");
				jsonObj2.addProperty("coloum8", "考核委员会评分人");
				jsonObj2.addProperty("coloum9", "自评总分");
				jsonObj2.addProperty("coloum10", "评价总分");
				jsonObj2.addProperty("coloum11", "评定等级");

				jsonObj3.addProperty("line", 3);
				jsonObj3.addProperty("length", 10);
				jsonObj3.addProperty("coloum1", dwmc);
				jsonObj3.addProperty("coloum2", bmlb);
				jsonObj3.addProperty("coloum3", gh);
				jsonObj3.addProperty("coloum4", mc);
				jsonObj3.addProperty("coloum5", bmpfr);
				jsonObj3.addProperty("coloum6", fgldpfr);
				jsonObj3.addProperty("coloum7", jcpfr);
				jsonObj3.addProperty("coloum8", rsbpfr);
				jsonObj3.addProperty("coloum9", grjcdf);
				jsonObj3.addProperty("coloum10", grpjzf);
				if ("".equals(pddj) || pddj == null) {
					jsonObj3.addProperty("coloum11", "暂未评定");
				} else {
					jsonObj3.addProperty("coloum11", pddj);
				}

				// 检察官和司法行政人员的部门负责人第二行表头一样
			} else if (ryType.equals("jcg_lx-2") || ryType.equals("sfxzry_lx-2")) {

				jsonObj2.addProperty("line", 2);
				jsonObj2.addProperty("length", 9);
				jsonObj2.addProperty("coloum1", "单位名称");
				jsonObj2.addProperty("coloum2", "部门类别");
				jsonObj2.addProperty("coloum3", "工号");
				jsonObj2.addProperty("coloum4", "姓名");
				jsonObj2.addProperty("coloum5", "分管院领导评分人");
				jsonObj2.addProperty("coloum6", "交叉评分人");
				jsonObj2.addProperty("coloum7", "考核委员会评分人");
				jsonObj2.addProperty("coloum8", "自评总分");
				jsonObj2.addProperty("coloum9", "评价总分");
				jsonObj2.addProperty("coloum10", "评定等级");

				jsonObj3.addProperty("line", 3);
				jsonObj3.addProperty("length", 9);
				jsonObj3.addProperty("coloum1", dwmc);
				jsonObj3.addProperty("coloum2", bmlb);
				jsonObj3.addProperty("coloum3", gh);
				jsonObj3.addProperty("coloum4", mc);
				jsonObj3.addProperty("coloum5", fgldpfr);
				jsonObj3.addProperty("coloum6", jcpfr);
				jsonObj3.addProperty("coloum7", rsbpfr);
				jsonObj3.addProperty("coloum8", grjcdf);
				jsonObj3.addProperty("coloum9", grpjzf);
				if ("".equals(pddj) || pddj == null) {
					jsonObj3.addProperty("coloum10", "暂未评定");
				} else {
					jsonObj3.addProperty("coloum10", pddj);
				}

				// 检察官和司法行政人员的院领导第二行表头一样
			} else if (ryType.equals("jcg_lx-3") || ryType.equals("sfxzry_lx-3")) {

				jsonObj2.addProperty("line", 2);
				jsonObj2.addProperty("length", 8);
				jsonObj2.addProperty("coloum1", "单位名称");
				jsonObj2.addProperty("coloum2", "部门类别");
				jsonObj2.addProperty("coloum3", "工号");
				jsonObj2.addProperty("coloum4", "姓名");
				jsonObj2.addProperty("coloum5", "交叉评分人");
				jsonObj2.addProperty("coloum6", "考核委员会评分人");
				jsonObj2.addProperty("coloum7", "自评总分");
				jsonObj2.addProperty("coloum8", "评价总分");
				jsonObj2.addProperty("coloum9", "评定等级");

				jsonObj3.addProperty("line", 3);
				jsonObj3.addProperty("length", 8);
				jsonObj3.addProperty("coloum1", dwmc);
				jsonObj3.addProperty("coloum2", bmlb);
				jsonObj3.addProperty("coloum3", gh);
				jsonObj3.addProperty("coloum4", mc);
				jsonObj3.addProperty("coloum5", jcpfr);
				jsonObj3.addProperty("coloum6", rsbpfr);
				jsonObj3.addProperty("coloum7", grjcdf);
				jsonObj3.addProperty("coloum8", grpjzf);
				if ("".equals(pddj) || pddj == null) {
					jsonObj3.addProperty("coloum9", "暂未评定");
				} else {
					jsonObj3.addProperty("coloum9", pddj);
				}
			}
		} else { // 省院*/
		// 一般检察辅助人员，包含检察官评分
		if (ryType.equals("jcfzry_lx-1")) {
			// 检技只有检查辅助人员，但是没有检查官评分项
			if (StringUtils.equals(bmlbbm, "7")) {
				jsonObj2.addProperty("line", 2);
				jsonObj2.addProperty("length", 10);
				jsonObj2.addProperty("coloum1", "单位名称");
				jsonObj2.addProperty("coloum2", "部门类别");
				jsonObj2.addProperty("coloum3", "工号");
				jsonObj2.addProperty("coloum4", "姓名");
				jsonObj2.addProperty("coloum5", "部门评分人");
				jsonObj2.addProperty("coloum6", "分管院领导评分人");
				jsonObj2.addProperty("coloum7", "考核委员会评分人");
				jsonObj2.addProperty("coloum8", "自评总分");
				jsonObj2.addProperty("coloum9", "评价总分");
				jsonObj2.addProperty("coloum10", "评定等级");

				jsonObj3.addProperty("line", 3);
				jsonObj3.addProperty("length", 10);
				jsonObj3.addProperty("coloum1", dwmc);
				jsonObj3.addProperty("coloum2", bmlb);
				jsonObj3.addProperty("coloum3", gh);
				jsonObj3.addProperty("coloum4", mc);
				jsonObj3.addProperty("coloum5", bmpfr);
				jsonObj3.addProperty("coloum6", fgldpfr);
				jsonObj3.addProperty("coloum7", rsbpfr);
				jsonObj3.addProperty("coloum8", grjcdf);
				jsonObj3.addProperty("coloum9", grpjzf);
				if ("".equals(pddj) || pddj == null) {
					jsonObj3.addProperty("coloum10", "暂未评定");
				} else {
					jsonObj3.addProperty("coloum10", pddj);
				}
			} else {
				jsonObj2.addProperty("line", 2);
				jsonObj2.addProperty("length", 11);
				jsonObj2.addProperty("coloum1", "单位名称");
				jsonObj2.addProperty("coloum2", "部门类别");
				jsonObj2.addProperty("coloum3", "工号");
				jsonObj2.addProperty("coloum4", "姓名");
				jsonObj2.addProperty("coloum5", "检察官评分人");
				jsonObj2.addProperty("coloum6", "部门评分人");
				jsonObj2.addProperty("coloum7", "分管院领导评分人");
				jsonObj2.addProperty("coloum8", "考核委员会评分人");
				jsonObj2.addProperty("coloum9", "自评总分");
				jsonObj2.addProperty("coloum10", "评价总分");
				jsonObj2.addProperty("coloum11", "评定等级");

				jsonObj3.addProperty("line", 3);
				jsonObj3.addProperty("length", 11);
				jsonObj3.addProperty("coloum1", dwmc);
				jsonObj3.addProperty("coloum2", bmlb);
				jsonObj3.addProperty("coloum3", gh);
				jsonObj3.addProperty("coloum4", mc);
				jsonObj3.addProperty("coloum5", jcgpfr);
				jsonObj3.addProperty("coloum6", bmpfr);
				jsonObj3.addProperty("coloum7", fgldpfr);
				jsonObj3.addProperty("coloum8", rsbpfr);
				jsonObj3.addProperty("coloum9", grjcdf);
				jsonObj3.addProperty("coloum10", grpjzf);
				if ("".equals(pddj) || pddj == null) {
					jsonObj3.addProperty("coloum11", "暂未评定");
				} else {
					jsonObj3.addProperty("coloum11", pddj);
				}
			}
			// 一般检察官和一般司法行政人员第二行表头一样
		} else if (ryType.equals("jcg_lx-1") || ryType.equals("sfxzry_lx-1")) {

			jsonObj2.addProperty("line", 2);
			jsonObj2.addProperty("length", 10);
			jsonObj2.addProperty("coloum1", "单位名称");
			jsonObj2.addProperty("coloum2", "部门类别");
			jsonObj2.addProperty("coloum3", "工号");
			jsonObj2.addProperty("coloum4", "姓名");
			jsonObj2.addProperty("coloum5", "部门评分人");
			jsonObj2.addProperty("coloum6", "分管院领导评分人");
			jsonObj2.addProperty("coloum7", "考核委员会评分人");
			jsonObj2.addProperty("coloum8", "自评总分");
			jsonObj2.addProperty("coloum9", "评价总分");
			jsonObj2.addProperty("coloum10", "评定等级");

			jsonObj3.addProperty("line", 3);
			jsonObj3.addProperty("length", 10);
			jsonObj3.addProperty("coloum1", dwmc);
			jsonObj3.addProperty("coloum2", bmlb);
			jsonObj3.addProperty("coloum3", gh);
			jsonObj3.addProperty("coloum4", mc);
			jsonObj3.addProperty("coloum5", bmpfr);
			jsonObj3.addProperty("coloum6", fgldpfr);
			jsonObj3.addProperty("coloum7", rsbpfr);
			jsonObj3.addProperty("coloum8", grjcdf);
			jsonObj3.addProperty("coloum9", grpjzf);
			if ("".equals(pddj) || pddj == null) {
				jsonObj3.addProperty("coloum10", "暂未评定");
			} else {
				jsonObj3.addProperty("coloum10", pddj);
			}

			// 检察官和司法行政人员的部门负责人第二行表头一样
		} else if (ryType.equals("jcg_lx-2") || ryType.equals("sfxzry_lx-2")) {

			jsonObj2.addProperty("line", 2);
			jsonObj2.addProperty("length", 9);
			jsonObj2.addProperty("coloum1", "单位名称");
			jsonObj2.addProperty("coloum2", "部门类别");
			jsonObj2.addProperty("coloum3", "工号");
			jsonObj2.addProperty("coloum4", "姓名");
			jsonObj2.addProperty("coloum5", "分管院领导评分人");
			jsonObj2.addProperty("coloum6", "考核委员会评分人");
			jsonObj2.addProperty("coloum7", "自评总分");
			jsonObj2.addProperty("coloum8", "评价总分");
			jsonObj2.addProperty("coloum9", "评定等级");

			jsonObj3.addProperty("line", 3);
			jsonObj3.addProperty("length", 9);
			jsonObj3.addProperty("coloum1", dwmc);
			jsonObj3.addProperty("coloum2", bmlb);
			jsonObj3.addProperty("coloum3", gh);
			jsonObj3.addProperty("coloum4", mc);
			jsonObj3.addProperty("coloum5", fgldpfr);
			jsonObj3.addProperty("coloum6", rsbpfr);
			jsonObj3.addProperty("coloum7", grjcdf);
			jsonObj3.addProperty("coloum8", grpjzf);
			if ("".equals(pddj) || pddj == null) {
				jsonObj3.addProperty("coloum9", "暂未评定");
			} else {
				jsonObj3.addProperty("coloum9", pddj);
			}
		} else if (ryType.equals("jcg_lx-3") || ryType.equals("sfxzry_lx-3")) { // 检察官和司法行政人员的院领导第二行表头一样

			jsonObj2.addProperty("line", 2);
			jsonObj2.addProperty("length", 8);
			jsonObj2.addProperty("coloum1", "单位名称");
			jsonObj2.addProperty("coloum2", "部门类别");
			jsonObj2.addProperty("coloum3", "工号");
			jsonObj2.addProperty("coloum4", "姓名");
			jsonObj2.addProperty("coloum5", "考核委员会评分人");
			jsonObj2.addProperty("coloum6", "自评总分");
			jsonObj2.addProperty("coloum7", "评价总分");
			jsonObj2.addProperty("coloum8", "评定等级");

			jsonObj3.addProperty("line", 3);
			jsonObj3.addProperty("length", 8);
			jsonObj3.addProperty("coloum1", dwmc);
			jsonObj3.addProperty("coloum2", bmlb);
			jsonObj3.addProperty("coloum3", gh);
			jsonObj3.addProperty("coloum4", mc);
			jsonObj3.addProperty("coloum5", rsbpfr);
			jsonObj3.addProperty("coloum6", grjcdf);
			jsonObj3.addProperty("coloum7", grpjzf);
			if ("".equals(pddj) || pddj == null) {
				jsonObj3.addProperty("coloum8", "暂未评定");
			} else {
				jsonObj3.addProperty("coloum8", pddj);
			}
		}
//		}

		jsonArr.add(jsonObj2);
		jsonArr.add(jsonObj3);

		// 第五行的列名，直接取页面上的表头
		JsonObject jsonObj5 = new JsonObject();
		jsonObj5.addProperty("line", 5);
		jsonObj5.addProperty("length", titleArray.size());
		for (int i = 0; i < titleArray.size(); i++) {
			jsonObj5.addProperty("coloum"+(i+1), titleArray.get(i).getAsString());
		}
		jsonArr.add(jsonObj5);

		//第六行及以后的数据，数据填充徐亚判断人员类型，不然数据和表头不对应，主要是评分部分
		Entry<String, JsonElement> keyValue = null;
		String key = null;

		/*if (!dwjb.equals("2")) { // 非省院
			// 一般检察辅助人员（包含检察官评分项）
			if (ryType.equals("jcfzry_lx-1")) {
				for (int i = 0; i < rowsArray.size(); i++) {
					JsonObject jsonObj = new JsonObject();
					jsonObj.addProperty("line", 6+i); // 7 ?
					jsonObj.addProperty("length", titleArray.size()); // 9  titleArray.size() == 12
					JsonObject jsonObjTemp = (JsonObject) rowsArray.get(i);
					jsonObj.addProperty("fxmbm", jsonObjTemp.get("fxmbm").getAsString());
					jsonObj.addProperty("xmbm", jsonObjTemp.get("xmbm").getAsString());
					jsonObj.addProperty("zxmbm", jsonObjTemp.get("zxmbm").getAsString());
					jsonObj.addProperty("coloum1", jsonObjTemp.get("fxmmc").getAsString());
					jsonObj.addProperty("coloum2", jsonObjTemp.get("xmmc").getAsString());
					jsonObj.addProperty("coloum3", jsonObjTemp.get("xmfz").getAsString());
					jsonObj.addProperty("coloum4", jsonObjTemp.get("zxmmc").getAsString());

					jsonObj.addProperty("coloum5", jsonObjTemp.get("gxfz").getAsString());
					jsonObj.addProperty("coloum6", jsonObjTemp.get("sl").getAsString());
					jsonObj.addProperty("coloum7", jsonObjTemp.get("gxdf").getAsString());

					jsonObj.addProperty("coloum8", jsonObjTemp.get("zpdf").getAsString());
					jsonObj.addProperty("coloum9", jsonObjTemp.get("jcgdf").getAsString());
					jsonObj.addProperty("coloum10", jsonObjTemp.get("bmdf").getAsString());
					jsonObj.addProperty("coloum11", jsonObjTemp.get("fglddf").getAsString());
					jsonObj.addProperty("coloum12", jsonObjTemp.get("jcdf").getAsString());
					jsonObj.addProperty("coloum13", jsonObjTemp.get("rsbdf").getAsString());
					jsonObj.addProperty("coloum14", jsonObjTemp.get("pjdf").getAsString());
					jsonArr.add(jsonObj);
				}

				//最后一行
				for (int i = 0; i < footArray.size(); i++) {
					JsonObject jsonObj = new JsonObject();
					jsonObj.addProperty("line", rowsArray.size()+6);
					jsonObj.addProperty("length", titleArray.size());
					jsonObj.addProperty("coloum1", footArray.get(i).getAsJsonObject().get("fxmmc").getAsString());
					jsonObj.addProperty("coloum2", StringUtils.EMPTY);
					jsonObj.addProperty("coloum3", StringUtils.EMPTY);
					jsonObj.addProperty("coloum4", StringUtils.EMPTY);
					jsonObj.addProperty("coloum5", StringUtils.EMPTY);
					jsonObj.addProperty("coloum6", StringUtils.EMPTY);
					jsonObj.addProperty("coloum7", StringUtils.EMPTY);
					jsonObj.addProperty("coloum8", footArray.get(i).getAsJsonObject().get("zpdf").getAsString());
					jsonObj.addProperty("coloum9", footArray.get(i).getAsJsonObject().get("jcgdf").getAsString());
					jsonObj.addProperty("coloum10", footArray.get(i).getAsJsonObject().get("bmdf").getAsString());
					jsonObj.addProperty("coloum11", footArray.get(i).getAsJsonObject().get("fglddf").getAsString());
					jsonObj.addProperty("coloum12", footArray.get(i).getAsJsonObject().get("jcdf").getAsString());
					jsonObj.addProperty("coloum13", footArray.get(i).getAsJsonObject().get("rsbdf").getAsString());
					jsonObj.addProperty("coloum14", footArray.get(i).getAsJsonObject().get("pjdf").getAsString());
					jsonArr.add(jsonObj);
				}

				// 检察官-》一般检察官（本人评分，部门评分，分管领导评分，考核委员会评分），一般司法行政人员没有数量
			} else if (ryType.equals("jcg_lx-1")) {

				for (int i = 0; i < rowsArray.size(); i++) {
					JsonObject jsonObj = new JsonObject();
					jsonObj.addProperty("line", 6+i); // 7 ?
					jsonObj.addProperty("length", titleArray.size()); // 9  titleArray.size() == 12
					JsonObject jsonObjTemp = (JsonObject) rowsArray.get(i);
					jsonObj.addProperty("fxmbm", jsonObjTemp.get("fxmbm").getAsString());
					jsonObj.addProperty("xmbm", jsonObjTemp.get("xmbm").getAsString());
					jsonObj.addProperty("zxmbm", jsonObjTemp.get("zxmbm").getAsString());
					jsonObj.addProperty("coloum1", jsonObjTemp.get("fxmmc").getAsString());
					jsonObj.addProperty("coloum2", jsonObjTemp.get("xmmc").getAsString());
					jsonObj.addProperty("coloum3", jsonObjTemp.get("xmfz").getAsString());
					jsonObj.addProperty("coloum4", jsonObjTemp.get("zxmmc").getAsString());

					jsonObj.addProperty("coloum5", jsonObjTemp.get("gxfz").getAsString());
					jsonObj.addProperty("coloum6", jsonObjTemp.get("sl").getAsString());
					jsonObj.addProperty("coloum7", jsonObjTemp.get("gxdf").getAsString());

					jsonObj.addProperty("coloum8", jsonObjTemp.get("zpdf").getAsString());
					jsonObj.addProperty("coloum9", jsonObjTemp.get("bmdf").getAsString());
					jsonObj.addProperty("coloum10", jsonObjTemp.get("fglddf").getAsString());
					jsonObj.addProperty("coloum11", jsonObjTemp.get("jcdf").getAsString());
					jsonObj.addProperty("coloum12", jsonObjTemp.get("rsbdf").getAsString());
					jsonObj.addProperty("coloum13", jsonObjTemp.get("pjdf").getAsString());
					jsonArr.add(jsonObj);
				}

				//最后一行
				for (int i = 0; i < footArray.size(); i++) {
					JsonObject jsonObj = new JsonObject();
					jsonObj.addProperty("line", rowsArray.size()+6);
					jsonObj.addProperty("length", titleArray.size());
					jsonObj.addProperty("coloum1", footArray.get(i).getAsJsonObject().get("fxmmc").getAsString());
					jsonObj.addProperty("coloum2", StringUtils.EMPTY);
					jsonObj.addProperty("coloum3", StringUtils.EMPTY);
					jsonObj.addProperty("coloum4", StringUtils.EMPTY);
					jsonObj.addProperty("coloum5", StringUtils.EMPTY);
					jsonObj.addProperty("coloum6", StringUtils.EMPTY);
					jsonObj.addProperty("coloum7", StringUtils.EMPTY);
					jsonObj.addProperty("coloum8", footArray.get(i).getAsJsonObject().get("zpdf").getAsString());
					jsonObj.addProperty("coloum9", footArray.get(i).getAsJsonObject().get("bmdf").getAsString());
					jsonObj.addProperty("coloum10", footArray.get(i).getAsJsonObject().get("fglddf").getAsString());
					jsonObj.addProperty("coloum11", footArray.get(i).getAsJsonObject().get("jcdf").getAsString());
					jsonObj.addProperty("coloum12", footArray.get(i).getAsJsonObject().get("rsbdf").getAsString());
					jsonObj.addProperty("coloum13", footArray.get(i).getAsJsonObject().get("pjdf").getAsString());
					jsonArr.add(jsonObj);
				}

				// 部门负责人（本人评分，分管领导评分，考核委员会评分）
			} else if (ryType.equals("jcg_lx-2") || ryType.equals("jcfzry_lx-2")) {

				for (int i = 0; i < rowsArray.size(); i++) {
					JsonObject jsonObj = new JsonObject();
					jsonObj.addProperty("line", 6+i); // 7 ?
					jsonObj.addProperty("length", titleArray.size()); // 9  titleArray.size() == 12
					JsonObject jsonObjTemp = (JsonObject) rowsArray.get(i);
					jsonObj.addProperty("fxmbm", jsonObjTemp.get("fxmbm").getAsString());
					jsonObj.addProperty("xmbm", jsonObjTemp.get("xmbm").getAsString());
					jsonObj.addProperty("zxmbm", jsonObjTemp.get("zxmbm").getAsString());
					jsonObj.addProperty("coloum1", jsonObjTemp.get("fxmmc").getAsString());
					jsonObj.addProperty("coloum2", jsonObjTemp.get("xmmc").getAsString());
					jsonObj.addProperty("coloum3", jsonObjTemp.get("xmfz").getAsString());
					jsonObj.addProperty("coloum4", jsonObjTemp.get("zxmmc").getAsString());

					jsonObj.addProperty("coloum5", jsonObjTemp.get("gxfz").getAsString());
					jsonObj.addProperty("coloum6", jsonObjTemp.get("sl").getAsString());
					jsonObj.addProperty("coloum7", jsonObjTemp.get("gxdf").getAsString());

					jsonObj.addProperty("coloum8", jsonObjTemp.get("zpdf").getAsString());
					jsonObj.addProperty("coloum9", jsonObjTemp.get("fglddf").getAsString());
					jsonObj.addProperty("coloum10", jsonObjTemp.get("jcdf").getAsString());
					jsonObj.addProperty("coloum11", jsonObjTemp.get("rsbdf").getAsString());
					jsonObj.addProperty("coloum12", jsonObjTemp.get("pjdf").getAsString());
					jsonArr.add(jsonObj);
				}

				//最后一行
				for (int i = 0; i < footArray.size(); i++) {
					JsonObject jsonObj = new JsonObject();
					jsonObj.addProperty("line", rowsArray.size()+6);
					jsonObj.addProperty("length", titleArray.size());
					jsonObj.addProperty("coloum1", footArray.get(i).getAsJsonObject().get("fxmmc").getAsString());
					jsonObj.addProperty("coloum2", StringUtils.EMPTY);
					jsonObj.addProperty("coloum3", StringUtils.EMPTY);
					jsonObj.addProperty("coloum4", StringUtils.EMPTY);
					jsonObj.addProperty("coloum5", StringUtils.EMPTY);
					jsonObj.addProperty("coloum6", StringUtils.EMPTY);
					jsonObj.addProperty("coloum7", StringUtils.EMPTY);
					jsonObj.addProperty("coloum8", footArray.get(i).getAsJsonObject().get("zpdf").getAsString());
					jsonObj.addProperty("coloum9", footArray.get(i).getAsJsonObject().get("fglddf").getAsString());
					jsonObj.addProperty("coloum10", footArray.get(i).getAsJsonObject().get("jcdf").getAsString());
					jsonObj.addProperty("coloum11", footArray.get(i).getAsJsonObject().get("rsbdf").getAsString());
					jsonObj.addProperty("coloum12", footArray.get(i).getAsJsonObject().get("pjdf").getAsString());
					jsonArr.add(jsonObj);
				}

				// 院领导（本人评分，考核委员会评分）
			} else if (ryType.equals("jcg_lx-3") || ryType.equals("jcfzry_lx-3")) {

				for (int i = 0; i < rowsArray.size(); i++) {
					JsonObject jsonObj = new JsonObject();
					jsonObj.addProperty("line", 6+i); // 7 ?
					jsonObj.addProperty("length", titleArray.size()); // 9  titleArray.size() == 12
					JsonObject jsonObjTemp = (JsonObject) rowsArray.get(i);
					jsonObj.addProperty("fxmbm", jsonObjTemp.get("fxmbm").getAsString());
					jsonObj.addProperty("xmbm", jsonObjTemp.get("xmbm").getAsString());
					jsonObj.addProperty("zxmbm", jsonObjTemp.get("zxmbm").getAsString());
					jsonObj.addProperty("coloum1", jsonObjTemp.get("fxmmc").getAsString());
					jsonObj.addProperty("coloum2", jsonObjTemp.get("xmmc").getAsString());
					jsonObj.addProperty("coloum3", jsonObjTemp.get("xmfz").getAsString());
					jsonObj.addProperty("coloum4", jsonObjTemp.get("zxmmc").getAsString());

					jsonObj.addProperty("coloum5", jsonObjTemp.get("gxfz").getAsString());
					jsonObj.addProperty("coloum6", jsonObjTemp.get("sl").getAsString());
					jsonObj.addProperty("coloum7", jsonObjTemp.get("gxdf").getAsString());

					jsonObj.addProperty("coloum8", jsonObjTemp.get("zpdf").getAsString());
					jsonObj.addProperty("coloum9", jsonObjTemp.get("jcdf").getAsString());
					jsonObj.addProperty("coloum10", jsonObjTemp.get("rsbdf").getAsString());
					jsonObj.addProperty("coloum11", jsonObjTemp.get("pjdf").getAsString());
					jsonArr.add(jsonObj);
				}

				//最后一行
				for (int i = 0; i < footArray.size(); i++) {
					JsonObject jsonObj = new JsonObject();
					jsonObj.addProperty("line", rowsArray.size()+6);
					jsonObj.addProperty("length", titleArray.size());
					jsonObj.addProperty("coloum1", footArray.get(i).getAsJsonObject().get("fxmmc").getAsString());
					jsonObj.addProperty("coloum2", StringUtils.EMPTY);
					jsonObj.addProperty("coloum3", StringUtils.EMPTY);
					jsonObj.addProperty("coloum4", StringUtils.EMPTY);
					jsonObj.addProperty("coloum5", StringUtils.EMPTY);
					jsonObj.addProperty("coloum6", StringUtils.EMPTY);
					jsonObj.addProperty("coloum7", StringUtils.EMPTY);
					jsonObj.addProperty("coloum8", footArray.get(i).getAsJsonObject().get("zpdf").getAsString());
					jsonObj.addProperty("coloum9", footArray.get(i).getAsJsonObject().get("jcdf").getAsString());
					jsonObj.addProperty("coloum10", footArray.get(i).getAsJsonObject().get("rsbdf").getAsString());
					jsonObj.addProperty("coloum11", footArray.get(i).getAsJsonObject().get("pjdf").getAsString());
					jsonArr.add(jsonObj);
				}

				// 司法行政人员-》 一般人员
			} else if (ryType.equals("sfxzry_lx-1")) {

				for (int i = 0; i < rowsArray.size(); i++) {
					JsonObject jsonObj = new JsonObject();
					jsonObj.addProperty("line", 6+i); // 7 ?
					jsonObj.addProperty("length", titleArray.size()); // 9  titleArray.size() == 12
					JsonObject jsonObjTemp = (JsonObject) rowsArray.get(i);
					jsonObj.addProperty("fxmbm", jsonObjTemp.get("fxmbm").getAsString());
					jsonObj.addProperty("xmbm", jsonObjTemp.get("xmbm").getAsString());
					jsonObj.addProperty("zxmbm", jsonObjTemp.get("zxmbm").getAsString());
					jsonObj.addProperty("coloum1", jsonObjTemp.get("fxmmc").getAsString());
					jsonObj.addProperty("coloum2", jsonObjTemp.get("xmmc").getAsString());
					jsonObj.addProperty("coloum3", jsonObjTemp.get("xmfz").getAsString());
					jsonObj.addProperty("coloum4", jsonObjTemp.get("zxmmc").getAsString());

					jsonObj.addProperty("coloum5", jsonObjTemp.get("gxfz").getAsString());

					jsonObj.addProperty("coloum6", jsonObjTemp.get("zpdf").getAsString());
					jsonObj.addProperty("coloum7", jsonObjTemp.get("bmdf").getAsString());
					jsonObj.addProperty("coloum8", jsonObjTemp.get("fglddf").getAsString());
					jsonObj.addProperty("coloum9", jsonObjTemp.get("jcdf").getAsString());
					jsonObj.addProperty("coloum10", jsonObjTemp.get("rsbdf").getAsString());
					jsonObj.addProperty("coloum11", jsonObjTemp.get("pjdf").getAsString());
					jsonArr.add(jsonObj);
				}

				//最后一行
				for (int i = 0; i < footArray.size(); i++) {
					JsonObject jsonObj = new JsonObject();
					jsonObj.addProperty("line", rowsArray.size()+6);
					jsonObj.addProperty("length", titleArray.size());
					jsonObj.addProperty("coloum1", footArray.get(i).getAsJsonObject().get("fxmmc").getAsString());
					jsonObj.addProperty("coloum2", StringUtils.EMPTY);
					jsonObj.addProperty("coloum3", StringUtils.EMPTY);
					jsonObj.addProperty("coloum4", StringUtils.EMPTY);
					jsonObj.addProperty("coloum5", StringUtils.EMPTY);
					jsonObj.addProperty("coloum6", footArray.get(i).getAsJsonObject().get("zpdf").getAsString());
					jsonObj.addProperty("coloum7", footArray.get(i).getAsJsonObject().get("bmdf").getAsString());
					jsonObj.addProperty("coloum8", footArray.get(i).getAsJsonObject().get("fglddf").getAsString());
					jsonObj.addProperty("coloum9", footArray.get(i).getAsJsonObject().get("jcdf").getAsString());
					jsonObj.addProperty("coloum10", footArray.get(i).getAsJsonObject().get("rsbdf").getAsString());
					jsonObj.addProperty("coloum11", footArray.get(i).getAsJsonObject().get("pjdf").getAsString());
					jsonArr.add(jsonObj);
				}

				// 司法行政人员-》负责人
			} else if (ryType.equals("sfxzry_lx-2")) {

				for (int i = 0; i < rowsArray.size(); i++) {
					JsonObject jsonObj = new JsonObject();
					jsonObj.addProperty("line", 6+i); // 7 ?
					jsonObj.addProperty("length", titleArray.size()); // 9  titleArray.size() == 12
					JsonObject jsonObjTemp = (JsonObject) rowsArray.get(i);
					jsonObj.addProperty("fxmbm", jsonObjTemp.get("fxmbm").getAsString());
					jsonObj.addProperty("xmbm", jsonObjTemp.get("xmbm").getAsString());
					jsonObj.addProperty("zxmbm", jsonObjTemp.get("zxmbm").getAsString());
					jsonObj.addProperty("coloum1", jsonObjTemp.get("fxmmc").getAsString());
					jsonObj.addProperty("coloum2", jsonObjTemp.get("xmmc").getAsString());
					jsonObj.addProperty("coloum3", jsonObjTemp.get("xmfz").getAsString());
					jsonObj.addProperty("coloum4", jsonObjTemp.get("zxmmc").getAsString());

					jsonObj.addProperty("coloum5", jsonObjTemp.get("gxfz").getAsString());

					jsonObj.addProperty("coloum6", jsonObjTemp.get("zpdf").getAsString());
					jsonObj.addProperty("coloum7", jsonObjTemp.get("fglddf").getAsString());
					jsonObj.addProperty("coloum8", jsonObjTemp.get("jcdf").getAsString());
					jsonObj.addProperty("coloum9", jsonObjTemp.get("rsbdf").getAsString());
					jsonObj.addProperty("coloum10", jsonObjTemp.get("pjdf").getAsString());
					jsonArr.add(jsonObj);
				}

				//最后一行
				for (int i = 0; i < footArray.size(); i++) {
					JsonObject jsonObj = new JsonObject();
					jsonObj.addProperty("line", rowsArray.size()+6);
					jsonObj.addProperty("length", titleArray.size());
					jsonObj.addProperty("coloum1", footArray.get(i).getAsJsonObject().get("fxmmc").getAsString());
					jsonObj.addProperty("coloum2", StringUtils.EMPTY);
					jsonObj.addProperty("coloum3", StringUtils.EMPTY);
					jsonObj.addProperty("coloum4", StringUtils.EMPTY);
					jsonObj.addProperty("coloum5", StringUtils.EMPTY);
					jsonObj.addProperty("coloum6", footArray.get(i).getAsJsonObject().get("zpdf").getAsString());
					jsonObj.addProperty("coloum7", footArray.get(i).getAsJsonObject().get("fglddf").getAsString());
					jsonObj.addProperty("coloum8", footArray.get(i).getAsJsonObject().get("jcdf").getAsString());
					jsonObj.addProperty("coloum9", footArray.get(i).getAsJsonObject().get("rsbdf").getAsString());
					jsonObj.addProperty("coloum10", footArray.get(i).getAsJsonObject().get("pjdf").getAsString());
					jsonArr.add(jsonObj);
				}

				// 司法行政人员-》院领导
			} else if (ryType.equals("sfxzry_lx-3")) {

				for (int i = 0; i < rowsArray.size(); i++) {
					JsonObject jsonObj = new JsonObject();
					jsonObj.addProperty("line", 6+i); // 7 ?
					jsonObj.addProperty("length", titleArray.size()); // 9  titleArray.size() == 12
					JsonObject jsonObjTemp = (JsonObject) rowsArray.get(i);
					jsonObj.addProperty("fxmbm", jsonObjTemp.get("fxmbm").getAsString());
					jsonObj.addProperty("xmbm", jsonObjTemp.get("xmbm").getAsString());
					jsonObj.addProperty("zxmbm", jsonObjTemp.get("zxmbm").getAsString());
					jsonObj.addProperty("coloum1", jsonObjTemp.get("fxmmc").getAsString());
					jsonObj.addProperty("coloum2", jsonObjTemp.get("xmmc").getAsString());
					jsonObj.addProperty("coloum3", jsonObjTemp.get("xmfz").getAsString());
					jsonObj.addProperty("coloum4", jsonObjTemp.get("zxmmc").getAsString());

					jsonObj.addProperty("coloum5", jsonObjTemp.get("gxfz").getAsString());

					jsonObj.addProperty("coloum6", jsonObjTemp.get("zpdf").getAsString());
					jsonObj.addProperty("coloum7", jsonObjTemp.get("jcdf").getAsString());
					jsonObj.addProperty("coloum8", jsonObjTemp.get("rsbdf").getAsString());
					jsonObj.addProperty("coloum9", jsonObjTemp.get("pjdf").getAsString());
					jsonArr.add(jsonObj);
				}

				//最后一行
				for (int i = 0; i < footArray.size(); i++) {
					JsonObject jsonObj = new JsonObject();
					jsonObj.addProperty("line", rowsArray.size()+6);
					jsonObj.addProperty("length", titleArray.size());
					jsonObj.addProperty("coloum1", footArray.get(i).getAsJsonObject().get("fxmmc").getAsString());
					jsonObj.addProperty("coloum2", StringUtils.EMPTY);
					jsonObj.addProperty("coloum3", StringUtils.EMPTY);
					jsonObj.addProperty("coloum4", StringUtils.EMPTY);
					jsonObj.addProperty("coloum5", StringUtils.EMPTY);
					jsonObj.addProperty("coloum6", footArray.get(i).getAsJsonObject().get("zpdf").getAsString());
					jsonObj.addProperty("coloum7", footArray.get(i).getAsJsonObject().get("jcdf").getAsString());
					jsonObj.addProperty("coloum8", footArray.get(i).getAsJsonObject().get("rsbdf").getAsString());
					jsonObj.addProperty("coloum9", footArray.get(i).getAsJsonObject().get("pjdf").getAsString());
					jsonArr.add(jsonObj);
				}
			}
		} else {*/ // 省院
		// 一般检察辅助人员（包含检察官评分项）
		if (ryType.equals("jcfzry_lx-1")) {
			if (StringUtils.equals(bmlbbm, "7")) { // 检技只有检查辅助人员，但是没有检察官评分
				for (int i = 0; i < rowsArray.size(); i++) {
					JsonObject jsonObj = new JsonObject();
					jsonObj.addProperty("line", 6+i); // 7 ?
					jsonObj.addProperty("length", titleArray.size()); // 9  titleArray.size() == 12
					JsonObject jsonObjTemp = (JsonObject) rowsArray.get(i);
					jsonObj.addProperty("fxmbm", jsonObjTemp.get("fxmbm").getAsString());
					jsonObj.addProperty("xmbm", jsonObjTemp.get("xmbm").getAsString());
					jsonObj.addProperty("zxmbm", jsonObjTemp.get("zxmbm").getAsString());
					jsonObj.addProperty("coloum1", jsonObjTemp.get("fxmmc").getAsString());
					jsonObj.addProperty("coloum2", jsonObjTemp.get("xmmc").getAsString());
					jsonObj.addProperty("coloum3", jsonObjTemp.get("xmfz").getAsString());
					jsonObj.addProperty("coloum4", jsonObjTemp.get("zxmmc").getAsString());

					jsonObj.addProperty("coloum5", jsonObjTemp.get("gxfz").getAsString());
					jsonObj.addProperty("coloum6", jsonObjTemp.get("sl").getAsString());
					jsonObj.addProperty("coloum7", jsonObjTemp.get("gxdf").getAsString());

					jsonObj.addProperty("coloum8", jsonObjTemp.get("zpdf").getAsString());
					jsonObj.addProperty("coloum9", jsonObjTemp.get("bmdf").getAsString());
					jsonObj.addProperty("coloum10", jsonObjTemp.get("fglddf").getAsString());
					jsonObj.addProperty("coloum11", jsonObjTemp.get("rsbdf").getAsString());
					jsonObj.addProperty("coloum12", jsonObjTemp.get("pjdf").getAsString());
					jsonArr.add(jsonObj);
				}

				//最后一行
				for (int i = 0; i < footArray.size(); i++) {
					JsonObject jsonObj = new JsonObject();
					jsonObj.addProperty("line", rowsArray.size()+6);
					jsonObj.addProperty("length", titleArray.size());
					jsonObj.addProperty("coloum1", footArray.get(i).getAsJsonObject().get("fxmmc").getAsString());
					jsonObj.addProperty("coloum2", StringUtils.EMPTY);
					jsonObj.addProperty("coloum3", StringUtils.EMPTY);
					jsonObj.addProperty("coloum4", StringUtils.EMPTY);
					jsonObj.addProperty("coloum5", StringUtils.EMPTY);
					jsonObj.addProperty("coloum6", StringUtils.EMPTY);
					jsonObj.addProperty("coloum7", StringUtils.EMPTY);
					jsonObj.addProperty("coloum8", footArray.get(i).getAsJsonObject().get("zpdf").getAsString());
					jsonObj.addProperty("coloum9", footArray.get(i).getAsJsonObject().get("bmdf").getAsString());
					jsonObj.addProperty("coloum10", footArray.get(i).getAsJsonObject().get("fglddf").getAsString());
					jsonObj.addProperty("coloum11", footArray.get(i).getAsJsonObject().get("rsbdf").getAsString());
					jsonObj.addProperty("coloum12", footArray.get(i).getAsJsonObject().get("pjdf").getAsString());
					jsonArr.add(jsonObj);
				}
			} else {
				for (int i = 0; i < rowsArray.size(); i++) {
					JsonObject jsonObj = new JsonObject();
					jsonObj.addProperty("line", 6+i); // 7 ?
					jsonObj.addProperty("length", titleArray.size()); // 9  titleArray.size() == 12
					JsonObject jsonObjTemp = (JsonObject) rowsArray.get(i);
					jsonObj.addProperty("fxmbm", jsonObjTemp.get("fxmbm").getAsString());
					jsonObj.addProperty("xmbm", jsonObjTemp.get("xmbm").getAsString());
					jsonObj.addProperty("zxmbm", jsonObjTemp.get("zxmbm").getAsString());
					jsonObj.addProperty("coloum1", jsonObjTemp.get("fxmmc").getAsString());
					jsonObj.addProperty("coloum2", jsonObjTemp.get("xmmc").getAsString());
					jsonObj.addProperty("coloum3", jsonObjTemp.get("xmfz").getAsString());
					jsonObj.addProperty("coloum4", jsonObjTemp.get("zxmmc").getAsString());

					jsonObj.addProperty("coloum5", jsonObjTemp.get("gxfz").getAsString());
					jsonObj.addProperty("coloum6", jsonObjTemp.get("sl").getAsString());
					jsonObj.addProperty("coloum7", jsonObjTemp.get("gxdf").getAsString());

					jsonObj.addProperty("coloum8", jsonObjTemp.get("zpdf").getAsString());
					jsonObj.addProperty("coloum9", jsonObjTemp.get("jcgdf").getAsString());
					jsonObj.addProperty("coloum10", jsonObjTemp.get("bmdf").getAsString());
					jsonObj.addProperty("coloum11", jsonObjTemp.get("fglddf").getAsString());
					jsonObj.addProperty("coloum12", jsonObjTemp.get("rsbdf").getAsString());
					jsonObj.addProperty("coloum13", jsonObjTemp.get("pjdf").getAsString());
					jsonArr.add(jsonObj);
				}

				//最后一行
				for (int i = 0; i < footArray.size(); i++) {
					JsonObject jsonObj = new JsonObject();
					jsonObj.addProperty("line", rowsArray.size()+6);
					jsonObj.addProperty("length", titleArray.size());
					jsonObj.addProperty("coloum1", footArray.get(i).getAsJsonObject().get("fxmmc").getAsString());
					jsonObj.addProperty("coloum2", StringUtils.EMPTY);
					jsonObj.addProperty("coloum3", StringUtils.EMPTY);
					jsonObj.addProperty("coloum4", StringUtils.EMPTY);
					jsonObj.addProperty("coloum5", StringUtils.EMPTY);
					jsonObj.addProperty("coloum6", StringUtils.EMPTY);
					jsonObj.addProperty("coloum7", StringUtils.EMPTY);
					jsonObj.addProperty("coloum8", footArray.get(i).getAsJsonObject().get("zpdf").getAsString());
					jsonObj.addProperty("coloum9", footArray.get(i).getAsJsonObject().get("jcgdf").getAsString());
					jsonObj.addProperty("coloum10", footArray.get(i).getAsJsonObject().get("bmdf").getAsString());
					jsonObj.addProperty("coloum11", footArray.get(i).getAsJsonObject().get("fglddf").getAsString());
					jsonObj.addProperty("coloum12", footArray.get(i).getAsJsonObject().get("rsbdf").getAsString());
					jsonObj.addProperty("coloum13", footArray.get(i).getAsJsonObject().get("pjdf").getAsString());
					jsonArr.add(jsonObj);
				}
			}
			// 检察官-》一般检察官（本人评分，部门评分，分管领导评分，考核委员会评分），一般司法行政人员没有数量
		} else if (ryType.equals("jcg_lx-1")) {

			for (int i = 0; i < rowsArray.size(); i++) {
				JsonObject jsonObj = new JsonObject();
				jsonObj.addProperty("line", 6+i); // 7 ?
				jsonObj.addProperty("length", titleArray.size()); // 9  titleArray.size() == 12
				JsonObject jsonObjTemp = (JsonObject) rowsArray.get(i);
				jsonObj.addProperty("fxmbm", jsonObjTemp.get("fxmbm").getAsString());
				jsonObj.addProperty("xmbm", jsonObjTemp.get("xmbm").getAsString());
				jsonObj.addProperty("zxmbm", jsonObjTemp.get("zxmbm").getAsString());
				jsonObj.addProperty("coloum1", jsonObjTemp.get("fxmmc").getAsString());
				jsonObj.addProperty("coloum2", jsonObjTemp.get("xmmc").getAsString());
				jsonObj.addProperty("coloum3", jsonObjTemp.get("xmfz").getAsString());
				jsonObj.addProperty("coloum4", jsonObjTemp.get("zxmmc").getAsString());

				jsonObj.addProperty("coloum5", jsonObjTemp.get("gxfz").getAsString());
				jsonObj.addProperty("coloum6", jsonObjTemp.get("sl").getAsString());
				jsonObj.addProperty("coloum7", jsonObjTemp.get("gxdf").getAsString());

				jsonObj.addProperty("coloum8", jsonObjTemp.get("zpdf").getAsString());
				jsonObj.addProperty("coloum9", jsonObjTemp.get("bmdf").getAsString());
				jsonObj.addProperty("coloum10", jsonObjTemp.get("fglddf").getAsString());
				jsonObj.addProperty("coloum11", jsonObjTemp.get("rsbdf").getAsString());
				jsonObj.addProperty("coloum12", jsonObjTemp.get("pjdf").getAsString());
				jsonArr.add(jsonObj);
			}

			//最后一行
			for (int i = 0; i < footArray.size(); i++) {
				JsonObject jsonObj = new JsonObject();
				jsonObj.addProperty("line", rowsArray.size()+6);
				jsonObj.addProperty("length", titleArray.size());
				jsonObj.addProperty("coloum1", footArray.get(i).getAsJsonObject().get("fxmmc").getAsString());
				jsonObj.addProperty("coloum2", StringUtils.EMPTY);
				jsonObj.addProperty("coloum3", StringUtils.EMPTY);
				jsonObj.addProperty("coloum4", StringUtils.EMPTY);
				jsonObj.addProperty("coloum5", StringUtils.EMPTY);
				jsonObj.addProperty("coloum6", StringUtils.EMPTY);
				jsonObj.addProperty("coloum7", StringUtils.EMPTY);
				jsonObj.addProperty("coloum8", footArray.get(i).getAsJsonObject().get("zpdf").getAsString());
				jsonObj.addProperty("coloum9", footArray.get(i).getAsJsonObject().get("bmdf").getAsString());
				jsonObj.addProperty("coloum10", footArray.get(i).getAsJsonObject().get("fglddf").getAsString());
				jsonObj.addProperty("coloum11", footArray.get(i).getAsJsonObject().get("rsbdf").getAsString());
				jsonObj.addProperty("coloum12", footArray.get(i).getAsJsonObject().get("pjdf").getAsString());
				jsonArr.add(jsonObj);
			}

			// 部门负责人（本人评分，分管领导评分，考核委员会评分）
		} else if (ryType.equals("jcg_lx-2") || ryType.equals("jcfzry_lx-2")) {

			for (int i = 0; i < rowsArray.size(); i++) {
				JsonObject jsonObj = new JsonObject();
				jsonObj.addProperty("line", 6+i); // 7 ?
				jsonObj.addProperty("length", titleArray.size()); // 9  titleArray.size() == 12
				JsonObject jsonObjTemp = (JsonObject) rowsArray.get(i);
				jsonObj.addProperty("fxmbm", jsonObjTemp.get("fxmbm").getAsString());
				jsonObj.addProperty("xmbm", jsonObjTemp.get("xmbm").getAsString());
				jsonObj.addProperty("zxmbm", jsonObjTemp.get("zxmbm").getAsString());
				jsonObj.addProperty("coloum1", jsonObjTemp.get("fxmmc").getAsString());
				jsonObj.addProperty("coloum2", jsonObjTemp.get("xmmc").getAsString());
				jsonObj.addProperty("coloum3", jsonObjTemp.get("xmfz").getAsString());
				jsonObj.addProperty("coloum4", jsonObjTemp.get("zxmmc").getAsString());

				jsonObj.addProperty("coloum5", jsonObjTemp.get("gxfz").getAsString());
				jsonObj.addProperty("coloum6", jsonObjTemp.get("sl").getAsString());
				jsonObj.addProperty("coloum7", jsonObjTemp.get("gxdf").getAsString());

				jsonObj.addProperty("coloum8", jsonObjTemp.get("zpdf").getAsString());
				jsonObj.addProperty("coloum9", jsonObjTemp.get("fglddf").getAsString());
				jsonObj.addProperty("coloum10", jsonObjTemp.get("rsbdf").getAsString());
				jsonObj.addProperty("coloum11", jsonObjTemp.get("pjdf").getAsString());
				jsonArr.add(jsonObj);
			}

			//最后一行
			for (int i = 0; i < footArray.size(); i++) {
				JsonObject jsonObj = new JsonObject();
				jsonObj.addProperty("line", rowsArray.size()+6);
				jsonObj.addProperty("length", titleArray.size());
				jsonObj.addProperty("coloum1", footArray.get(i).getAsJsonObject().get("fxmmc").getAsString());
				jsonObj.addProperty("coloum2", StringUtils.EMPTY);
				jsonObj.addProperty("coloum3", StringUtils.EMPTY);
				jsonObj.addProperty("coloum4", StringUtils.EMPTY);
				jsonObj.addProperty("coloum5", StringUtils.EMPTY);
				jsonObj.addProperty("coloum6", StringUtils.EMPTY);
				jsonObj.addProperty("coloum7", StringUtils.EMPTY);
				jsonObj.addProperty("coloum8", footArray.get(i).getAsJsonObject().get("zpdf").getAsString());
				jsonObj.addProperty("coloum9", footArray.get(i).getAsJsonObject().get("fglddf").getAsString());
				jsonObj.addProperty("coloum10", footArray.get(i).getAsJsonObject().get("rsbdf").getAsString());
				jsonObj.addProperty("coloum11", footArray.get(i).getAsJsonObject().get("pjdf").getAsString());
				jsonArr.add(jsonObj);
			}

			// 院领导（本人评分，考核委员会评分）
		} else if (ryType.equals("jcg_lx-3") || ryType.equals("jcfzry_lx-3")) {

			for (int i = 0; i < rowsArray.size(); i++) {
				JsonObject jsonObj = new JsonObject();
				jsonObj.addProperty("line", 6+i); // 7 ?
				jsonObj.addProperty("length", titleArray.size()); // 9  titleArray.size() == 12
				JsonObject jsonObjTemp = (JsonObject) rowsArray.get(i);
				jsonObj.addProperty("fxmbm", jsonObjTemp.get("fxmbm").getAsString());
				jsonObj.addProperty("xmbm", jsonObjTemp.get("xmbm").getAsString());
				jsonObj.addProperty("zxmbm", jsonObjTemp.get("zxmbm").getAsString());
				jsonObj.addProperty("coloum1", jsonObjTemp.get("fxmmc").getAsString());
				jsonObj.addProperty("coloum2", jsonObjTemp.get("xmmc").getAsString());
				jsonObj.addProperty("coloum3", jsonObjTemp.get("xmfz").getAsString());
				jsonObj.addProperty("coloum4", jsonObjTemp.get("zxmmc").getAsString());

				jsonObj.addProperty("coloum5", jsonObjTemp.get("gxfz").getAsString());
				jsonObj.addProperty("coloum6", jsonObjTemp.get("sl").getAsString());
				jsonObj.addProperty("coloum7", jsonObjTemp.get("gxdf").getAsString());

				jsonObj.addProperty("coloum8", jsonObjTemp.get("zpdf").getAsString());
				jsonObj.addProperty("coloum9", jsonObjTemp.get("rsbdf").getAsString());
				jsonObj.addProperty("coloum10", jsonObjTemp.get("pjdf").getAsString());
				jsonArr.add(jsonObj);
			}

			//最后一行
			for (int i = 0; i < footArray.size(); i++) {
				JsonObject jsonObj = new JsonObject();
				jsonObj.addProperty("line", rowsArray.size()+6);
				jsonObj.addProperty("length", titleArray.size());
				jsonObj.addProperty("coloum1", footArray.get(i).getAsJsonObject().get("fxmmc").getAsString());
				jsonObj.addProperty("coloum2", StringUtils.EMPTY);
				jsonObj.addProperty("coloum3", StringUtils.EMPTY);
				jsonObj.addProperty("coloum4", StringUtils.EMPTY);
				jsonObj.addProperty("coloum5", StringUtils.EMPTY);
				jsonObj.addProperty("coloum6", StringUtils.EMPTY);
				jsonObj.addProperty("coloum7", StringUtils.EMPTY);
				jsonObj.addProperty("coloum8", footArray.get(i).getAsJsonObject().get("zpdf").getAsString());
				jsonObj.addProperty("coloum9", footArray.get(i).getAsJsonObject().get("rsbdf").getAsString());
				jsonObj.addProperty("coloum10", footArray.get(i).getAsJsonObject().get("pjdf").getAsString());
				jsonArr.add(jsonObj);
			}

			// 司法行政人员-》 一般人员
		} else if (ryType.equals("sfxzry_lx-1")) {

			for (int i = 0; i < rowsArray.size(); i++) {
				JsonObject jsonObj = new JsonObject();
				jsonObj.addProperty("line", 6+i); // 7 ?
				jsonObj.addProperty("length", titleArray.size()); // 9  titleArray.size() == 12
				JsonObject jsonObjTemp = (JsonObject) rowsArray.get(i);
				jsonObj.addProperty("fxmbm", jsonObjTemp.get("fxmbm").getAsString());
				jsonObj.addProperty("xmbm", jsonObjTemp.get("xmbm").getAsString());
				jsonObj.addProperty("zxmbm", jsonObjTemp.get("zxmbm").getAsString());
				jsonObj.addProperty("coloum1", jsonObjTemp.get("fxmmc").getAsString());
				jsonObj.addProperty("coloum2", jsonObjTemp.get("xmmc").getAsString());
				jsonObj.addProperty("coloum3", jsonObjTemp.get("xmfz").getAsString());
				jsonObj.addProperty("coloum4", jsonObjTemp.get("zxmmc").getAsString());

				jsonObj.addProperty("coloum5", jsonObjTemp.get("gxfz").getAsString());

				jsonObj.addProperty("coloum6", jsonObjTemp.get("zpdf").getAsString());
				jsonObj.addProperty("coloum7", jsonObjTemp.get("bmdf").getAsString());
				jsonObj.addProperty("coloum8", jsonObjTemp.get("fglddf").getAsString());
				jsonObj.addProperty("coloum9", jsonObjTemp.get("rsbdf").getAsString());
				jsonObj.addProperty("coloum10", jsonObjTemp.get("pjdf").getAsString());
				jsonArr.add(jsonObj);
			}

			//最后一行
			for (int i = 0; i < footArray.size(); i++) {
				JsonObject jsonObj = new JsonObject();
				jsonObj.addProperty("line", rowsArray.size()+6);
				jsonObj.addProperty("length", titleArray.size());
				jsonObj.addProperty("coloum1", footArray.get(i).getAsJsonObject().get("fxmmc").getAsString());
				jsonObj.addProperty("coloum2", StringUtils.EMPTY);
				jsonObj.addProperty("coloum3", StringUtils.EMPTY);
				jsonObj.addProperty("coloum4", StringUtils.EMPTY);
				jsonObj.addProperty("coloum5", StringUtils.EMPTY);
				jsonObj.addProperty("coloum6", footArray.get(i).getAsJsonObject().get("zpdf").getAsString());
				jsonObj.addProperty("coloum7", footArray.get(i).getAsJsonObject().get("bmdf").getAsString());
				jsonObj.addProperty("coloum8", footArray.get(i).getAsJsonObject().get("fglddf").getAsString());
				jsonObj.addProperty("coloum9", footArray.get(i).getAsJsonObject().get("rsbdf").getAsString());
				jsonObj.addProperty("coloum10", footArray.get(i).getAsJsonObject().get("pjdf").getAsString());
				jsonArr.add(jsonObj);
			}

			// 司法行政人员-》负责人
		} else if (ryType.equals("sfxzry_lx-2")) {

			for (int i = 0; i < rowsArray.size(); i++) {
				JsonObject jsonObj = new JsonObject();
				jsonObj.addProperty("line", 6+i); // 7 ?
				jsonObj.addProperty("length", titleArray.size()); // 9  titleArray.size() == 12
				JsonObject jsonObjTemp = (JsonObject) rowsArray.get(i);
				jsonObj.addProperty("fxmbm", jsonObjTemp.get("fxmbm").getAsString());
				jsonObj.addProperty("xmbm", jsonObjTemp.get("xmbm").getAsString());
				jsonObj.addProperty("zxmbm", jsonObjTemp.get("zxmbm").getAsString());
				jsonObj.addProperty("coloum1", jsonObjTemp.get("fxmmc").getAsString());
				jsonObj.addProperty("coloum2", jsonObjTemp.get("xmmc").getAsString());
				jsonObj.addProperty("coloum3", jsonObjTemp.get("xmfz").getAsString());
				jsonObj.addProperty("coloum4", jsonObjTemp.get("zxmmc").getAsString());

				jsonObj.addProperty("coloum5", jsonObjTemp.get("gxfz").getAsString());

				jsonObj.addProperty("coloum6", jsonObjTemp.get("zpdf").getAsString());
				jsonObj.addProperty("coloum7", jsonObjTemp.get("fglddf").getAsString());
				jsonObj.addProperty("coloum8", jsonObjTemp.get("rsbdf").getAsString());
				jsonObj.addProperty("coloum9", jsonObjTemp.get("pjdf").getAsString());
				jsonArr.add(jsonObj);
			}

			//最后一行
			for (int i = 0; i < footArray.size(); i++) {
				JsonObject jsonObj = new JsonObject();
				jsonObj.addProperty("line", rowsArray.size()+6);
				jsonObj.addProperty("length", titleArray.size());
				jsonObj.addProperty("coloum1", footArray.get(i).getAsJsonObject().get("fxmmc").getAsString());
				jsonObj.addProperty("coloum2", StringUtils.EMPTY);
				jsonObj.addProperty("coloum3", StringUtils.EMPTY);
				jsonObj.addProperty("coloum4", StringUtils.EMPTY);
				jsonObj.addProperty("coloum5", StringUtils.EMPTY);
				jsonObj.addProperty("coloum6", footArray.get(i).getAsJsonObject().get("zpdf").getAsString());
				jsonObj.addProperty("coloum7", footArray.get(i).getAsJsonObject().get("fglddf").getAsString());
				jsonObj.addProperty("coloum8", footArray.get(i).getAsJsonObject().get("rsbdf").getAsString());
				jsonObj.addProperty("coloum9", footArray.get(i).getAsJsonObject().get("pjdf").getAsString());
				jsonArr.add(jsonObj);
			}

			// 司法行政人员-》院领导
		} else if (ryType.equals("sfxzry_lx-3")) {

			for (int i = 0; i < rowsArray.size(); i++) {
				JsonObject jsonObj = new JsonObject();
				jsonObj.addProperty("line", 6+i); // 7 ?
				jsonObj.addProperty("length", titleArray.size()); // 9  titleArray.size() == 12
				JsonObject jsonObjTemp = (JsonObject) rowsArray.get(i);
				jsonObj.addProperty("fxmbm", jsonObjTemp.get("fxmbm").getAsString());
				jsonObj.addProperty("xmbm", jsonObjTemp.get("xmbm").getAsString());
				jsonObj.addProperty("zxmbm", jsonObjTemp.get("zxmbm").getAsString());
				jsonObj.addProperty("coloum1", jsonObjTemp.get("fxmmc").getAsString());
				jsonObj.addProperty("coloum2", jsonObjTemp.get("xmmc").getAsString());
				jsonObj.addProperty("coloum3", jsonObjTemp.get("xmfz").getAsString());
				jsonObj.addProperty("coloum4", jsonObjTemp.get("zxmmc").getAsString());

				jsonObj.addProperty("coloum5", jsonObjTemp.get("gxfz").getAsString());

				jsonObj.addProperty("coloum6", jsonObjTemp.get("zpdf").getAsString());
				jsonObj.addProperty("coloum7", jsonObjTemp.get("rsbdf").getAsString());
				jsonObj.addProperty("coloum8", jsonObjTemp.get("pjdf").getAsString());
				jsonArr.add(jsonObj);
			}

			//最后一行
			for (int i = 0; i < footArray.size(); i++) {
				JsonObject jsonObj = new JsonObject();
				jsonObj.addProperty("line", rowsArray.size()+6);
				jsonObj.addProperty("length", titleArray.size());
				jsonObj.addProperty("coloum1", footArray.get(i).getAsJsonObject().get("fxmmc").getAsString());
				jsonObj.addProperty("coloum2", StringUtils.EMPTY);
				jsonObj.addProperty("coloum3", StringUtils.EMPTY);
				jsonObj.addProperty("coloum4", StringUtils.EMPTY);
				jsonObj.addProperty("coloum5", StringUtils.EMPTY);
				jsonObj.addProperty("coloum6", footArray.get(i).getAsJsonObject().get("zpdf").getAsString());
				jsonObj.addProperty("coloum7", footArray.get(i).getAsJsonObject().get("rsbdf").getAsString());
				jsonObj.addProperty("coloum8", footArray.get(i).getAsJsonObject().get("pjdf").getAsString());
				jsonArr.add(jsonObj);
			}
		}
//		}

		return jsonArr;
	}


}