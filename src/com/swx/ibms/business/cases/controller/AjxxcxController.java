package com.swx.ibms.business.cases.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swx.ibms.business.cases.service.AjxxcxService;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.common.utils.ExcelUtil;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.rbac.controller.LoginController;
import com.swx.ibms.business.system.service.XtfjpathService;
import com.swx.ibms.common.utils.DateUtil;
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
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *<p>Title:AjxxcxController</p>
 *<p>Description: 案件信息查询controller</p>
 *author 朱春雨
 *date 2017年3月17日 上午11:34:57
 */
@SuppressWarnings("all")
@RequestMapping("/ajxxcx")
@Controller
public class AjxxcxController {

	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(LoginController.class);

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;

	/**
	 * 案件信息查询service
	 */
	@Resource
	private AjxxcxService ajxxcxService;

	/**
	 * 文件上传路径服务
	 */
	@Resource
	private XtfjpathService xtfjpathservice;

	/**
	 * @param request  请求
	 * @return 返回查出来的案件
	 */
	@RequestMapping(value = "/getajxxcx", method = RequestMethod.POST)
	@ResponseBody
	public String getAjxxcx(HttpServletRequest request) {
		RYBM ry=(RYBM)request.getSession().getAttribute("ry");
		String pyear=request.getParameter("year");
		String pbmsah=request.getParameter("bmsah");
		String pcbdwbm=ry.getDwbm();
		String pagestr=request.getParameter("page");
		int page=Integer.parseInt(pagestr);
		Map returnMap=null;
		try {
			returnMap=ajxxcxService.selectlistAjxxcx(pyear, pbmsah, pcbdwbm,page);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return Constant.GSON.toJson(returnMap);
	}

	@RequestMapping(value = "/getYwkhAjxx", method = RequestMethod.GET)
	@ResponseBody
	public String getYwkhAjxx(@RequestParam(value="dwbm",required=true)String dwbm,
							  @RequestParam(value="kssj",required=true)String kssj,
							  @RequestParam(value="jssj",required=true)String jssj,
							  @RequestParam(value="type",required=true)String type,
							  @RequestParam(value="khlx",required=true)String khlx,
							  @RequestParam(value="page",required=true)Integer page,
							  @RequestParam(value="rows",required=true)Integer pageSize,
							  HttpServletRequest request) {

		Map<String,Object> returnMap=new HashMap<String,Object>();
		Map<String,Object> viewMap=new HashMap<String,Object>();
		String ajlbbmstr = StringUtils.EMPTY;

		try {
			String tempYwkhKssj = DateUtil.getFmtDateStr(kssj, "yyyy-MM-dd", "yyyy");
			//因为案卡统计案件定义2017年为特殊的一年 案件的时间是2016-12-26 00:00:00 到 2017-12-31 23:59:59
			if(Constant.YWKH_AJCX_TSYEAR.equals(tempYwkhKssj)) {
				kssj = Constant.YWKH_AJCX_TSRQ;
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		try {

			if(Constant.YWKH_KHLX_1.equals(khlx)){
				//1、考核类型为乡镇检察室工作【khlx值为06】的自动计算
				returnMap = ajxxcxService.getYwkhXzjcAj(dwbm, kssj, jssj, ajlbbmstr, type, page, pageSize);
			}else if(Constant.YWKH_KHLX_5.equals(khlx)){
				//4、考核类型为案件管理工作（分市院,县区院）【khlx值为12】的自动计算
				returnMap = ajxxcxService.getYwkhAjglgzAj(dwbm, kssj, jssj, ajlbbmstr, type, page, pageSize);
			}else if(Constant.YWKH_KHLX_3.equals(khlx)){
				//2、考核类型为侦查监督工作【khlx值为02】的自动计算
				returnMap = ajxxcxService.getYwkhLajdAj(dwbm, kssj, jssj, ajlbbmstr, type, page, pageSize);
			}else if(Constant.YWKH_KHLX_4.equals(khlx)){
				//3、考核类型为刑事申诉检察工作（分市院）【khlx值为09】的自动计算
				returnMap = ajxxcxService.getYwkhXsssjcgzAj(dwbm, kssj, jssj, ajlbbmstr, type, page, pageSize);
			}else if(Constant.YWKH_KHLX_6.equals(khlx)){
				//5、考核类型为民事行政检察工作（县区院）【khlx值为07】的自动计算
				returnMap = ajxxcxService.getYwkhMsxzjcgzAj(dwbm, kssj, jssj, ajlbbmstr, type, page, pageSize);
			}else if(Constant.YWKH_KHLX_7.equals(khlx)){
				//6、考核类型为刑事执行检察工作（要区分分市院、县区院）【khlx值为04】的自动计算
				returnMap = ajxxcxService.getYwkhXszxjcgzAj(dwbm, kssj, jssj, ajlbbmstr, type, page, pageSize);
			}else if(Constant.YWKH_KHLX_8.equals(khlx)){
				//7、考核类型为公诉工作（分市院、县区院）【khlx值为03】的自动计算
				returnMap = ajxxcxService.getYwkhGsgzAj(dwbm, kssj, jssj, ajlbbmstr, type, page, pageSize);
			}else if(Constant.YWKH_KHLX_9.equals(khlx)){
				//8、考核类型为未成年人刑事检察工作（分市院、县区院）【khlx值为01】的自动计算
				returnMap = ajxxcxService.getYwkhWcnrxsjcgzAj(dwbm, kssj, jssj, ajlbbmstr, type, page, pageSize);
			}
			viewMap.put("rows", returnMap.get("p_cursor"));
			viewMap.put("total", returnMap.get("p_count"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constant.GSON.toJson(viewMap);
	}


	@RequestMapping("/selectAjbl")
	@ResponseBody
	public Map<String,Object> selectAjbl(@RequestParam(value="dwbm",required=true)String dwbm,
										 @RequestParam(value="gh",required=true)String gh,
										 @RequestParam(value="bmbm",required=true)String bmbm,
										 @RequestParam(value="page",required=true)Integer page,
										 @RequestParam(value="rows",required=true)Integer pageSize,
										 @RequestParam(value="kssj",required=true)String kssj,
										 @RequestParam(value="jssj",required=true)String jssj) throws Exception{
		try{
			Map<String, Object> ajblList = ajxxcxService.selectAjbl(gh, dwbm,bmbm,page,pageSize,kssj,jssj);
			return ajblList;
		}catch(Exception e){
			e.printStackTrace();
			LOG.error("查询[案件办理一级表单信息]失败:", e);
			throw e;
		}
	}

	/**
	 * 查询二级表单
	 * @param  dwbm 单位编码
	 * @param  ajlbbm 案件办理编码
	 * @param  blfsbm 办理方式编码
	 * @param  gh 工号
	 * @return 办理方式
	 * @since 2017年9月8日
	 * @throws Exception 抛出异常
	 */
	@RequestMapping("/selectAjblEJ")
	@ResponseBody
	public Map<String,Object> selectAjblEJ(@RequestParam(value="dwbm",required=true)String dwbm,
										   @RequestParam(value="ajlbbm",required=false)String ajlbbm,
										   @RequestParam(value="gh",required=true)String gh,
										   @RequestParam(value="bmbm",required=true)String bmbm,
										   @RequestParam(value="page",required=true)Integer page,
										   @RequestParam(value="rows",required=true)Integer rows,
										   @RequestParam(value="kssj",required=true)String kssj,
										   @RequestParam(value="jssj",required=true)String jssj,
										   @RequestParam(value="ajmc",required=false)String ajmc,
										   @RequestParam(value="sort",required=false)String sort,
										   @RequestParam(value="order",required=false)String order,
										   @RequestParam(value="type",required=true)String type) throws Exception{
		try{
			Map<String, Object> ajblEjList = ajxxcxService.selectAjblEj(dwbm, bmbm, gh, ajlbbm, page, rows, kssj, jssj, type, sort, order, ajmc);
			return ajblEjList;//null
		}catch(Exception e){
			e.printStackTrace();
			LOG.error("查询[案件办理二级表单信息]失败:", e);
			throw e;
		}
	}

	/**
	 * 根据部门受案号查询案件基本信息
	 * @author 徐武林
	 * @param  bmsah 部门受案号
	 * @return 查询对象转化为json字符串
	 * @since 2018年4月20日
	 * @throws Exception 抛出异常
	 */
	@RequestMapping("/selectAjxqByBmsah")
	@ResponseBody
	public String selectAjxqByBmsah(@RequestParam(value="bmsah",required=true)String bmsah) throws Exception{
		try{
			Map<String, Object> ajjbxx = ajxxcxService.selectAjxqByBmsah(bmsah);
			return Constant.GSON.toJson(ajjbxx);
		}catch(Exception e){
			LOG.error("查询[案件基本信息]失败:", e);
			throw e;
		}
	}

	/**
	 * 根据部门受案号查询办案人员信息
	 * @author 徐武林
	 * @param bmsah 部门受案号
	 * @return 查询对象转化为json字符串
	 * @since 2018年4月20日
	 * @throws Exception 抛出异常
	 */
	@RequestMapping("/selectBaryByBmsah")
	@ResponseBody
	public String selectBaryByBmsah(@RequestParam(value="bmsah",required=true)String bmsah,
									@RequestParam(value="tysah",required=true)String tysah)throws Exception {
		Map<String,Object> map = new HashMap<>();
		List<Map<String, Object>> yxSfdaGrxx = new ArrayList<>();
		try{
			yxSfdaGrxx = ajxxcxService.selectBaryByBmsah(bmsah, tysah);
			map.put("rows", yxSfdaGrxx);
			return JSONObject.toJSONString(map);
		}catch(Exception e){
			e.printStackTrace();
			LOG.error("查询办案人员信息失败:", e);
			throw e;
		}
	}

	/**
	 * 根据部门受案号查询嫌疑人详情
	 * @author 徐武林
	 * @param bmsah 部门受案号
	 * @return 查询对象
	 * @since 2018年4月20日
	 * @throws Exception 抛出异常
	 */
	@RequestMapping("/selectXyrxxByBmsah")
	@ResponseBody
	public String selectXyrxxByBmsah(String bmsah) throws Exception{
		try{
			Map<String,Object> map = new HashMap<>();
			List<Map<String, Object>> xyrxx = ajxxcxService.selectXyrxxByBmsah(bmsah);
			map.put("rows", xyrxx);
			return JSONObject.toJSONString(map);
		}catch(Exception e){
			LOG.error("查询[案件嫌疑人信息]失败:", e);
			throw e;
		}
	}

	/**
	 * 添加非案管 案件办理
	 */
	@RequestMapping(value = "/addAjbl",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAjbl(HttpServletRequest req) throws Exception{
		Map<String,Object> map = new HashMap<>();

		String did = req.getParameter("fag_did");
		String ajmc = req.getParameter("fag_ajmc");
		String cbdwbm = req.getParameter("fag_dwbm");
		String cbdwmc = req.getParameter("fag_dwmc");
		String ajlb = req.getParameter("fag_ajlbmc");
		String ajlbbm = req.getParameter("fag_ajlbbm");
		String cbrgh = req.getParameter("fag_gh");
		String cbr = req.getParameter("fag_cbrxm");
		String ajslrq = req.getParameter("fag_slrq");
		String dqrq = req.getParameter("fag_dqrq");
		String bjrq = req.getParameter("fag_bjrq");
		String ajwjrq = req.getParameter("fag_wcrq");
		String aqzy = req.getParameter("fag_aqzy");
		String bjqk = req.getParameter("fag_bjqk");
		String cbryj = req.getParameter("fag_cbryj");
		String fz = req.getParameter("fag_fz");
		String cbbmbm = req.getParameter("fag_bmbm");
		String cbbmmc = req.getParameter("fag_bmmc");

		String result = "";

		try {
			result = ajxxcxService.addAjbl(did, ajmc, cbdwbm, cbdwmc, ajlb, ajlbbm, cbrgh, cbr, ajslrq, dqrq, bjrq, ajwjrq, aqzy, cbbmbm, cbbmmc, bjqk, cbryj, fz);
		} catch (Exception e) {
			e.printStackTrace();
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
	 * 新增案件展示
	 * @param daid
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectNewAjblByDid")
	@ResponseBody
	public Map<String, Object> selectNewAjblByDid(@RequestParam(value="ajId",required=false)String ajId,
												  @RequestParam(value="daid",required=false)String daid,
												  @RequestParam(value="page",required=true)Integer page,
												  @RequestParam(value="rows",required=true)Integer pageSize,
												  @RequestParam(value="ajStatus",required=false)String ajStatus) throws Exception{
		try {
			String ajzt = StringUtils.EMPTY;
			if (StringUtils.isNotBlank(ajStatus)) {
				ajzt = ajStatus;
			}
			Map<String, Object> ajxxMap = ajxxcxService.selectALLNewAjbl(ajId, daid, page, pageSize, ajzt);
			return ajxxMap;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("获取案件新增列表失败" + e.getMessage());
		}
		return null;
	}

	/**
	 * 删除新增列表中的案件办理
	 */
	@RequestMapping(value = "/deleteAjbl",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteAjbl(HttpServletRequest req) throws Exception{
		Map<String,Object> map = new HashMap<>();
		String ajid = req.getParameter("ajid");
		String bmsah = req.getParameter("bmsah");
		String result = "";
		try {
			result = ajxxcxService.deleteAjbl(ajid,bmsah);
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
	 * 修改非案管 案件办理
	 */
	@RequestMapping(value = "/updateAjbl",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjbl(HttpServletRequest req) throws Exception{
		Map<String,Object> map = new HashMap<>();

		String ajId = req.getParameter("fag_ajId");
		String ajmc = req.getParameter("fag_ajmc");
		String ajlb = req.getParameter("fag_ajlbmc");
		String ajlbbm = req.getParameter("fag_ajlbbm");
		String ajslrq = req.getParameter("fag_slrq");
		String dqrq = req.getParameter("fag_dqrq");
		String bjrq = req.getParameter("fag_bjrq");
		String ajwjrq = req.getParameter("fag_wcrq");
		String aqzy = req.getParameter("fag_aqzy");
		String bjqk = req.getParameter("fag_bjqk");
		String cbryj = req.getParameter("fag_cbryj");
		String fz = req.getParameter("fag_fz");

		String result = "";

		try {
			result = ajxxcxService.updateAjbl(ajId, ajmc, ajlb, ajlbbm, ajslrq, dqrq, bjrq, ajwjrq, aqzy, bjqk, cbryj, fz);
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
	 * 根据部门映射编码查询案件类别
	 * @param ywbms
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectAjlbByywbm",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectAjlbByywbm(@RequestParam(value="bmys",required=false)String bmys) throws Exception{
		String[] bmysArr = bmys.split(",");

		List<String> ywbmList = new ArrayList<>();
		for(int i=0;i<bmysArr.length;i++){
			ywbmList.add(bmysArr[i]);
		}
		try {
			Map<String, Object> ajlb = ajxxcxService.selectAjlbByywbm(ywbmList);
			return ajlb;
		} catch (Exception e) {
			LOG.error("获取案件类别列表失败" + e.getMessage());
			return null;
		}
	}

	@RequestMapping("/selectCbxz")
	@ResponseBody
	public Map<String, Object> selectCbxz(@RequestParam(value="daId",required=true)String daId,
										  @RequestParam(value="page",required=true)Integer page,
										  @RequestParam(value="pageSize",required=true)Integer pageSize) throws Exception{
		try {
			Map<String, Object> cbxzMap = ajxxcxService.selectCbxz(daId, page, pageSize);
			return cbxzMap;
		} catch (Exception e) {
			LOG.error("获取承办小组列表失败" + e.getMessage());
		}
		return null;
	}

	/**
	 * 新增承办小组
	 * @param daid
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCbxz",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCbxz(HttpServletRequest req) throws Exception{
		Map<String,Object> map = new HashMap<>();

		String cbxzmc = req.getParameter("cbxz_mc");
		String dwbm = req.getParameter("cbxz_dwbm");
		String gh = req.getParameter("cbxz_gh");
		String blfs = req.getParameter("cbxz_blfs");
		String blfsbm = req.getParameter("cbxz_blfsbm");
		String daId = req.getParameter("cbxz_daid");

		String result = "";

		try {
			result = ajxxcxService.addCbxz(daId, dwbm, gh, blfs, blfsbm, cbxzmc);
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


	@RequestMapping(value = "/deleteCbxz",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteCbxz(HttpServletRequest req) throws Exception{
		Map<String,Object> map = new HashMap<>();
		String id = req.getParameter("id");
		String result = "";
		try {
			result = ajxxcxService.deleteCbxz(id);
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
	 * 绑定承办小组到下拉框
	 */
	@RequestMapping("/getAllCbxz")
	@ResponseBody
	public Map<String, Object> getAllCbxz(@RequestParam(value="daId",required=true)String daId)throws Exception{
		Map<String, Object> cbxzMap = new HashMap<>();
		try {
			List<Map<String, Object>> cbxz = ajxxcxService.getAllCbxz(daId);
			cbxzMap.put("rows", cbxz);
			return cbxzMap;
		} catch (Exception e) {
			LOG.error("获取承办小组列表失败" + e.getMessage());
		}
		return null;
	}

	/**
	 * 将案件详情列表中的数据导出为excel
	 */
	@RequestMapping(value = "/exportCaseDetailsToExcel", method = RequestMethod.POST)
	@ResponseBody
	public String exportCaseDetailsToExcel (HttpServletRequest request,
											@RequestParam(value = "ownerUnitCode", required = true) String ownerUnitCode,
											@RequestParam(value = "ownerUnitName", required = true) String ownerUnitName,
											@RequestParam(value = "ownerNum", required = true) String ownerNum,
											@RequestParam(value = "ownerName", required = true) String ownerName,
											@RequestParam(value = "ownerDepartmentName", required = true) String ownerDepartmentName,
											@RequestParam(value = "startDate", required = true) String startDate,
											@RequestParam(value = "endDate", required = true) String endDate,
											@RequestParam(value = "tableTh", required = true) String tableTh,
											@RequestParam(value = "rows", required = true) String rows,
											@RequestParam(value = "columnName", required = true) String columnName) throws Exception {

		// 表头
		JsonArray titleArray = (JsonArray)Constant.JSON_PARSER.parse(tableTh);
		// 表头对应的field
		JsonArray columnNameArray = (JsonArray)Constant.JSON_PARSER.parse(columnName);
		// 表体内容
		JsonArray rowsArray =  (JsonArray)Constant.JSON_PARSER.parse(rows);

		// 文本信息
		// 服务器路径
//		final String DEFAULT_EXCEL_PATH = request.getServletContext().getRealPath("/");
		// 文件存放地址 例如："E:/";
		final String DEFAULT_EXCEL_PATH = xtfjpathservice.getPath().trim();
		// 文件后缀名
		final String DEFAULT_EXCEL_SUBFIX = ".xls";
		// 文件名称
		String fileName = StringUtils.EMPTY;
		fileName = startDate + "到" + endDate + ownerName + "的完成（办结）案件统计";

		// 判断文件是否存在  存在则生成新的文件
		//无需判断，如果文件存在，会自动在文件名后生成(数字)
//		File file = new File(DEFAULT_EXCEL_PATH + fileName + DEFAULT_EXCEL_SUBFIX);
//		if (file.exists()) {
//			fileName = startDate + "到" + endDate + ownerName + "的完成（办结）案件统计"+"("+Identities.randomString(2,true)+")";
//		}else{
//			fileName = startDate + "到" + endDate + ownerName + "的完成（办结）案件统计";
//		}

		// 文件地址
		String filePath = DEFAULT_EXCEL_PATH + fileName + DEFAULT_EXCEL_SUBFIX;
		// excel工作簿名
		String sheetName = ownerName + "的完成（办结）案件统计";
		// excel标题，即表格中第一行
		String titleName = startDate + "到" + endDate + ownerName + "的完成（办结）案件统计";

		// 组装表头表体数据成JsonArray
		JsonArray jsonArr = new JsonArray();

		// 第二行列名
		JsonObject jsonObjLine2 = new JsonObject();
		jsonObjLine2.addProperty("line", 2);
		jsonObjLine2.addProperty("length", 4);
		jsonObjLine2.addProperty("coloum1", "单位名称");
		jsonObjLine2.addProperty("coloum2", "所在部门");
		jsonObjLine2.addProperty("coloum3", "工号");
		jsonObjLine2.addProperty("coloum4", "姓名");
		jsonArr.add(jsonObjLine2);

		// 第三行数据
		JsonObject jsonObjLine3 = new JsonObject();
		jsonObjLine3.addProperty("line", 3);
		jsonObjLine3.addProperty("length", 4);
		jsonObjLine3.addProperty("coloum1", ownerUnitName);
		jsonObjLine3.addProperty("coloum2", ownerDepartmentName);
		jsonObjLine3.addProperty("coloum3", ownerNum);
		jsonObjLine3.addProperty("coloum4", ownerName);
		jsonArr.add(jsonObjLine3);

		// 第五行列名
		JsonObject jsonObjLine5 = new JsonObject();
		jsonObjLine5.addProperty("line", 5);
		jsonObjLine5.addProperty("length", titleArray.size());
		for (int i = 0; i < titleArray.size(); i++) {
			jsonObjLine5.addProperty("coloum" + (i + 1), titleArray.get(i).getAsString());
		}
		jsonArr.add(jsonObjLine5);

		// 第六行数据
		try {
			for (int i = 0; i < rowsArray.size(); i++) {
				JsonObject jsonObj = new JsonObject();
				jsonObj.addProperty("line", 6 + i);
				jsonObj.addProperty("length", titleArray.size());

				JsonObject jsonObjTemp = (JsonObject) rowsArray.get(i);
				jsonObj.addProperty("coloum"+(1), jsonObjTemp.get("AJMC").getAsString());
				jsonObj.addProperty("coloum"+(2), jsonObjTemp.get("AJLBMC").getAsString());
				jsonObj.addProperty("coloum"+(3), jsonObjTemp.get("SLRQ").getAsString());
				jsonObj.addProperty("coloum"+(4), jsonObjTemp.get("WJRQ").getAsString());
				jsonObj.addProperty("coloum"+(5), jsonObjTemp.get("BLSX").getAsString());

				// 判断文书数量是否存在
				if (jsonObjTemp.has("WSSL")) {
					jsonObj.addProperty("coloum"+(6), jsonObjTemp.get("WSSL").getAsString());
				} else {
					jsonObj.addProperty("coloum"+(6), 0);
				}
				jsonArr.add(jsonObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 调用专用excel导出方法
		try {
			OutputStream outXlsx = new FileOutputStream(filePath);
			// false表示不需要合并单元格
			ExcelUtil.exportExcelNotUtils(outXlsx,sheetName,titleName,jsonArr,false);
			outXlsx.flush();
			outXlsx.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("filename", fileName + DEFAULT_EXCEL_SUBFIX);
		map.put("filepath", filePath);
		return Constant.GSON.toJson(map);
	}


	@RequestMapping(value = "/selectCountsOfSlaj",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectCountsOfSlaj(@RequestParam(value="dwbm",required=true)String dwbm,
												  @RequestParam(value="gh",required=true)String gh,
												  @RequestParam(value="kssj",required=true)String kssj,
												  @RequestParam(value="jssj",required=true)String jssj,
												HttpServletRequest req) throws Exception{
		Map<String,Object> map = new HashMap<>();
		List<Map<String, Object>> result = new ArrayList<>();
		result = ajxxcxService.selectCountsOfSlaj(dwbm, gh, kssj, jssj);
		int totalCounts = 0;
		for (Map<String, Object> resMap: result) {
			totalCounts += Integer.parseInt(resMap.get("SLAJSL").toString());
		}
		map.put("SLAJSL", totalCounts);
		return map;
	}

}
