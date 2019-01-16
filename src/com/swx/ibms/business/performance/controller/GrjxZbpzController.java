package com.swx.ibms.business.performance.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.swx.ibms.business.common.service.UploadService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.performance.bean.GrjxZbpz;
import com.swx.ibms.business.performance.bean.XtGrjxRytype;
import com.swx.ibms.business.performance.service.AutoFZService;
import com.swx.ibms.business.performance.service.GrjxZbpzService;
import com.swx.ibms.business.performance.service.XtGrjxRytypeService;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.system.bean.Upload;
import com.swx.ibms.common.utils.DateUtil;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 
 * @author 赵松强
 * @date:2017年4月13日
 * @version:1.0
 * @description:个人绩效指标配置controller
 *
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/grjxZbpz")
public class GrjxZbpzController {

	/**
	 * 个人绩效指标配置接口
	 */
	@Resource
	private GrjxZbpzService grjxZbpzService;

	@Resource
	private XtGrjxRytypeService xtGrjxRytypeService;
	/**
	 * 文件上传服务
	 */
	@Resource
	private UploadService uploadService;

	/**
	 * 通过单位级别查询个人绩效指标配置的结果集 
	 * @param dwjb 单位级别
	 * @return 个人绩效结果集
	 */
	@RequestMapping(value = "/selectList", method = RequestMethod.POST)
	@ResponseBody
	public String selectList(String dwjb,
							@RequestParam(value="page",required=true)Integer nowPage,
							@RequestParam(value="rows",required=true)Integer pageSize) {
		Map<String,Object> map = new HashMap<String,Object>();
		int jb = Integer.parseInt(dwjb);//单位级别
		PageCommon<Object> pageCommon = grjxZbpzService.selectList(jb,nowPage,pageSize);
		map.put("total", pageCommon.getTotalRecords());
		map.put("rows", pageCommon.getList());
		return Constant.GSON.toJson(map);
	}

	/**
	 * 根据指标配置ID查询单条指标配置信息
	 * @param id 指标配置ID
	 * @return 此指标配置ID对应的指标配置信息
	 */
	@RequestMapping(value = "/selectById", method = RequestMethod.GET)
	@ResponseBody
	public String selectObject(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		GrjxZbpz zbpz = grjxZbpzService.selectById(id);
//		map.put("rows", Constant.JSON_PARSER.parse(zbpz.getPzzbpznr()));
		return Constant.GSON.toJson(zbpz);
	}

	/**
	 * 传入指标配置信息进行新增指标配置信息
	 * @param data 指标配置信息
	 * @return 是否成功的字符串
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public String insertData(String data) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (data != null) {
			// map.put("data", arg1);
			String msg = grjxZbpzService.insertData(map);
			map.put("msg", msg);
		}
		return Constant.GSON.toJson(map);
	}

	/**
	 * 根据指标配置ID修改指标配置信息
	 * @param map 指标配置ID
	 * @return 是否成功的字符串 
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateData(Map<String, Object> map) {
		if (map != null && !"".equals(map.get("zbpzId"))) {
			String msg = grjxZbpzService.updateData(map);
			map.put("msg", msg);
		}
		return Constant.GSON.toJson(map);
	}

	/**
	 * 根据指标配置ID删除指标配置信息 
	 * @param grjxZbpz 指标配置ID
	 * @return 是否成功的字符串 
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteData(GrjxZbpz grjxZbpz) {
		Map<String, Object> map = new HashMap<String,Object>();
		if (grjxZbpz!= null && StringUtils.isNotEmpty(grjxZbpz.getZbpzId())) {
			map.put("zbpzId", grjxZbpz.getZbpzId());
			String msg = grjxZbpzService.deleteData(map);
			if (msg!=null&&!"".equals(msg)) {
				map.put("msg", msg);
			}
		}
		return Constant.GSON.toJson(map);
	}

	/**
	 * 根据表头表ID查询表头表信息
	 * @param id 表头表ID
	 * @return 表头数据的字符串 
	 */
	@RequestMapping(value = "selectBtById", method = RequestMethod.GET)
	@ResponseBody
	public String selectBtById(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		String btsj = grjxZbpzService.selectBtById(id);
		map.put("data", btsj);
		return Constant.GSON.toJson(map);
	}

	/**
	 * 根据业务类别、单位级别、最后一行ID、表头表数据执行新增表头表信息
	 * @param tabletop 插入表头数据
	 * @param khsjdata 考核时间的数据
	 * @param request 请求对象 
	 * @return 字符串
	 */
	@RequestMapping(value = "saveBt", method = RequestMethod.POST)
	@ResponseBody
	public String saveBt(String tabletop,String khsjdata, HttpServletRequest request) {
		RYBM user = (RYBM) request.getSession().getAttribute("ry");

		// tabletop 表头
		// khbtdata 考核评分
		String id = grjxZbpzService.insertBt(tabletop, user.getDlbm(),khsjdata);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return Constant.GSON.toJson(map);
	}

	/**
	 * 通过业务类别、单位级别、表数据、最后一行ID新增指标配置表信息
	 * @param request 请求对象
	 * @return 字符串
	 */
	@RequestMapping(value = "saveTableData", method = RequestMethod.POST)
	@ResponseBody
	public String saveTableData(HttpServletRequest request) {
		RYBM user = (RYBM) request.getSession().getAttribute("ry");
		// 表体
		String pznrdata = request.getParameter("tabledata");
		// 业务类别
		String ywlb = request.getParameter("ywlb");
		// 单位级别
		String dwjb2 = request.getParameter("dwjb");
		// 人员类别
		String rylb = StringUtils.equals("undefined", request.getParameter("rylb"))? StringUtils.EMPTY:request.getParameter("rylb");

		String ssYear = request.getParameter("ssYear");
		String startYear = StringUtils.equals("undefined", request.getParameter("startYear"))?"1":request.getParameter("startYear");
		// 单位级别
		int dwjb = Integer.parseInt(dwjb2);
		//
		String lastid = request.getParameter("lastid");
		// 插入的表头记录的id
		String topid = request.getParameter("topid");

		if (StringUtils.isNoneBlank(lastid)) {
			if(!(Integer.parseInt(startYear)<Integer.parseInt(ssYear))) {
				// 修改XT_GRJX_ZBPZ表的status（状态）字段为0(作废)
				grjxZbpzService.updateStatus(lastid);
			}
		}

		// 存放指标考核的数组
		final JsonArray zbkhJsonArray = new JsonArray();
		// 存放评价分及subxmbm的键值对
		final JsonArray pjfJsonArray = new JsonArray();
		// 表体
		JsonElement jsonEleData = Constant.JSON_PARSER.parse(pznrdata);
		// 将表体转为json数组
		JsonArray jsonArrayData = (JsonArray) jsonEleData;

		// 表体数组的大小（行数）
		int size = jsonArrayData.size();

		int xmbm = Constant.NUM_10;
		int subxmbm = Constant.NUM_10;
		String lastsubxmmc = "";
		String lastxmmc = "";

		try{
			for (int i = 0; i < size; i++) {
				// 表体数组的某一行
				JsonObject jsonObj = (JsonObject) jsonArrayData.get(i);
				// 指标考核对象
				JsonObject zbkhObj = new JsonObject();

				// 存放评价分及subxmbm的键值对
				// JsonObject pjfObj = new JsonObject();

				// 如果该记录的项目名称（指标类别）和上一条不一致，将项目编码进行+1
				if(!lastxmmc.equals(jsonObj.get("fxmmc").getAsString())){
					xmbm++;
				}

				// 指标类别编码
				zbkhObj.addProperty("fxmbm", Integer.toString(xmbm));
				// 指标类别
				zbkhObj.addProperty("fxmmc", jsonObj.get("fxmmc").getAsString());

				//	如果该记录的项目名称（指标小类）和上一条不一致，将子项目编码进行+1
				if(!lastsubxmmc.equals(jsonObj.get("xmmc").getAsString())){
					subxmbm++;

					// 存放最高评价分及subxmbm的键值对
					// 指标小类一样，则只需存放一个最高评价分
					JsonObject pjfObj = new JsonObject();
					// key:子项目编码 value:最高评价分
					pjfObj.addProperty(""+Integer.toString(subxmbm),jsonObj.get("xmfz").getAsNumber());

					// 将最高评价分对象加入到评价分数组
					pjfJsonArray.add(pjfObj);
				}

				// 指标小类
				zbkhObj.addProperty("xmbm", Integer.toString(subxmbm));
				// 指标小类
				zbkhObj.addProperty("xmmc", jsonObj.get("xmmc").getAsString());

				// 最高评价分
				zbkhObj.addProperty("xmfz", jsonObj.get("xmfz").getAsNumber());

				// jsonObj（表体的某一行）
				// 文件id
				if(null != jsonObj.get("wjid")){
					zbkhObj.addProperty("wjid", jsonObj.get("wjid").getAsString());
				}

				// 附件中的文件描述
				if(null != jsonObj.get("wjms")){
					zbkhObj.addProperty("wjms", jsonObj.get("wjms").getAsString());
				}

				// 指标项编码
				zbkhObj.addProperty("zxmbm", Integer.toString(xmbm++));
				// 指标项
				zbkhObj.addProperty("zxmmc", jsonObj.get("zxmmc").getAsString());

				// 该项分值
				zbkhObj.addProperty("gxfz", jsonObj.get("gxfz").getAsString());

				// 备注（说明）
//				if(null != jsonObj.get("description")) {
//					zbkhObj.addProperty("description", jsonObj.get("description").getAsString());
//				}

				// 数量
				zbkhObj.addProperty("sl", "0");
				// 该项得分
				zbkhObj.addProperty("gxdf", "0");


				// 本人自评
				zbkhObj.addProperty("zpdf", "0");
				// 部门评分
				zbkhObj.addProperty("bmdf", "0");
				// 考评委员会评分
				zbkhObj.addProperty("rsbdf", "0");
				// 评价得分
				zbkhObj.addProperty("pjdf", "0");
				// 分管领导评分
				zbkhObj.addProperty("fglddf", "0");

				// 自评备注
				zbkhObj.addProperty("zpzl", "0");
				// 部门评分备注
				zbkhObj.addProperty("bmzl", "1");
				// 考评委员会评分备注
				zbkhObj.addProperty("rsbzl", "3");
				// 分管领导评分备注
				zbkhObj.addProperty("fgldzl", "4");

				//省院不弄交叉考核
				if(2!=dwjb){
					// 交叉考核得分
					zbkhObj.addProperty("jcdf", "0");
					// 交叉考核备注
					zbkhObj.addProperty("jczl", "2");
				}

				XtGrjxRytype type = new XtGrjxRytype();
				type = xtGrjxRytypeService.selectByPrimaryKey(rylb);

				//检察辅助人员
				if (type.getName().equals("检察辅助人员")) {
					// 交叉考核得分
					zbkhObj.addProperty("jcgdf", "0");
					// 交叉考核备注
					zbkhObj.addProperty("jcgzl", "5");
				}

				//是否可编辑
				boolean isOrNotEdit = true;

				// jsonObj（表体中的某一行）
				// 是否有自动评分这一项，改为都能进行编辑
				if(jsonObj.has("sfxtpf")){
					if (StringUtils.equals(jsonObj.get("sfxtpf").getAsString(), "1")) {
						// 自动计算 1；手动计算 0
						// 自动计算不允许编辑
//						isOrNotEdit = false;

						// 是否自动评分标识
						zbkhObj.addProperty("sfxtpf", jsonObj.get("sfxtpf").getAsString());

						// 查询编号/类型
						zbkhObj.addProperty("cxbh", jsonObj.get("cxbh").getAsString());
					}
				}

				// 能否编辑
				zbkhObj.addProperty("editable", isOrNotEdit);

				// 将zbkhObj（指标考核对象）加入到指标考核json数组
				zbkhJsonArray.add(zbkhObj);
				// pjfJsonArray.add(pjfObj);

				// 标记指标小类，如果指标小类一样的话 则指标小类编码应该一样
				lastsubxmmc = jsonObj.get("xmmc").getAsString();
				// 标记指标类别，如果指标类别一样的话 则指标类别编码应该一样
				lastxmmc = jsonObj.get("fxmmc").getAsString();
			}
		}catch(Exception e){
			return "error";
		}

		// 指标考核数组转字符串
		String khnrdata = zbkhJsonArray.toString();
		// 评价分数组（存放的是最高评价分）
		String pjf = pjfJsonArray.toString();

		String id = grjxZbpzService.insert(ywlb,dwjb,khnrdata,pznrdata,topid,user.getDlbm(),pjf,rylb,ssYear);
		return id;
	}

	/**
	 * 根据业务类别和单位级别判断是否已经存在配置记录
	 * @param dwjb 单位级别
	 * @param ywlb 业务类别
	 * @return result(记录条数)
	 */
	@RequestMapping(value = "isExist", method = RequestMethod.POST)
	@ResponseBody
	public int isExist(@RequestParam(value="ywlb",required=true)String ywlb,
					   @RequestParam(value="dwjb",required=true)String dwjb,
					   @RequestParam(value="type",required=true)String typeid,
					   @RequestParam(value="ssyear",required=false)String ssyear) {
		int result=0;
		String year = StringUtils.EMPTY;

		// 查询方式：1，以前年份未有检察官、检查辅助人员的区别；
		// 2，统计检察官、检查辅助人员的个人绩效指标是否存在
		String cxType = StringUtils.EMPTY+Constant.NUM_1;
		try {
			if(StringUtils.isNotBlank(ssyear)) {
				year = ssyear;
			}else {
				//获取当前年份的年
				year = DateUtil.getCurrDate("yyyy");
			}
			if(StringUtils.isNoneBlank(typeid)) {
				cxType = typeid;
			}
			result = grjxZbpzService.isExist(ywlb,dwjb,cxType,year);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @param file 上传 的文件
	 * @param request 请求对象
	 * @return 字符串
	 * @throws IOException
	 * 异常
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@RequestMapping(value = "/upload",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	public String upload(@RequestParam("file")MultipartFile file, HttpServletRequest request)
			throws IOException, InstantiationException, IllegalAccessException {
		String pPath = request.getSession().getServletContext().getRealPath("");
		String filename = file.getOriginalFilename();
		String path = pPath+"WEB-INF\\classes\\com\\swx\\zhyw\\plugin\\impl";
		upLoader(file.getInputStream(),path,filename);
		String id = UUID.randomUUID().toString().trim().replaceAll("-", "");
		String textName = filename.substring(0,filename.lastIndexOf("."));
		Upload upload = new Upload();
		upload.setId(id);
		upload.setWjmc("com.swx.zhyw.plugin.impl."+textName);
		upload.setWjdz("");
		upload.setWbid("");
		upload.setFjlx("3");
		/*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:MM:ss");
		Date sDate = new Date();
		String date = simpleDateFormat.format(sDate);
		upload.setScrq(date);*/
		uploadService.addZbpzfile(upload);
		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Class cls = Class.forName("com.swx.zhyw.plugin.impl."+textName);
			AutoFZService autoFZService = (AutoFZService) cls.newInstance();
			String message = autoFZService.desc();
			map.put("message", message);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		map.put("id", id);
		return Constant.GSON.toJson(map);
	}
	
	/**
	 * 
	 * @param inputStream 输入流
	 * @param path 文件路径
	 * @param filename 文件名
	 * @throws IOException IO异常
	 */
	public void upLoader(InputStream inputStream,String path,String filename) throws IOException{
		File file = new File(path);
		if(!file.exists()){
			file.mkdir();
		}
		FileOutputStream fos = new FileOutputStream(path+"\\"+filename);
		byte[] buffer = new byte[Constant.NUM_1024*Constant.NUM_1024];
		int buffread = 0;
		if((buffread = inputStream.read(buffer))!=-1){
			fos.write(buffer, 0, buffread);
			fos.flush();
		}
		fos.close();
		inputStream.close();
	}
	
	
	/**
	 * 查询统一业务同步表（业务编码表）的信息
	 * @param ywbm 业务编码(为空时查询全部，不为空时查询单个)
	 * @return 业务编码数据
	 * @throws Exception 异常
	 */
	@RequestMapping(value = "getYwlxList", method = RequestMethod.POST)
	@ResponseBody
	public String getYwlxList(@RequestParam(value="ywbm",required=false)String ywbm)
			throws Exception {
		
		List<Map<String,Object>> ywbmList = grjxZbpzService.getYwlxList(ywbm);
		return Constant.GSON.toJson(ywbmList);
	}
	
	
	/**
	 * @param number 传入数字几就获取最近几年的年份
	 * @param getType 传入类型数字几就获取对应的信息
	 * @return 默认返回最近五年的年份,其他返回combobox需要的格式
	 */
	@RequestMapping(value = "getRecentYear", method = RequestMethod.GET)
	@ResponseBody
	public String getRecentYear(@RequestParam(value="number",required=false)String number,
								@RequestParam(value="getType",required=false)String getType,
			HttpServletRequest request){
		List<Integer> yearList = new ArrayList<Integer>();
		int nowYear = Integer.parseInt(DateUtil.getCurrDate("yyyy"));
		int num = Constant.NUM_5;
		int year = 0;
		if(StringUtils.isNotBlank(number)) {
			num = Integer.parseInt(number);
		}
		for (int i = 0; i < num; i++) {
			year = nowYear--;
			yearList.add(year);
		}
		
		if (StringUtils.isNotBlank(getType)) {
			JsonArray jsonArray = new JsonArray();
			for (Integer i : yearList) {
				JsonObject jsonObj = new JsonObject();
				jsonObj.addProperty("id", i);
				jsonObj.addProperty("text", i);
				jsonArray.add(jsonObj);
			}
			return Constant.GSON.toJson(jsonArray);
		}else {
			return Constant.GSON.toJson(yearList);
		}
	}
	
}
