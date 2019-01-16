package com.swx.ibms.business.archives.controller;

import com.alibaba.fastjson.JSONObject;
import com.swx.ibms.business.archives.bean.SfdaRyjn;
import com.swx.ibms.business.archives.mapper.SfdaRyjnMapper;
import com.swx.ibms.business.archives.service.SfdaRyjnService;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.service.UploadService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.system.service.XtfjpathService;
import com.swx.ibms.business.system.bean.Upload;
import com.swx.ibms.common.utils.DateUtil;
import com.swx.ibms.common.utils.Identities;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:徐武林
 * @date:2018年3月29日上午9:36:08
 * @Description:荣誉技能Controller，首页显示中的一些查询也是在这里
 */
@Controller
@SuppressWarnings("all")
@RequestMapping("/sfdaRyjn")
public class SfdaRyjnController {

	/**
	 * 日志实体类
	 */
	private static final Logger LOG = Logger.getLogger(SfdaRyjnController.class);

	/**
	 * 司法档案下的荣誉技能Service
	 */
	@Autowired
	private SfdaRyjnService sfdaRyjnService;

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;

	/**
	 * 文件上传服务
	 */
	@Resource
	private UploadService uploadService;

	/**
	 * 文件上传路径服务
	 */
	@Resource
	private XtfjpathService xtfjpathservice;

	@Autowired
	private SfdaRyjnMapper sfdaRyjnMapper;

	/**
	 * 展示荣誉技能信息
	 * 
	 */
	@RequestMapping(value = "/getSfdaRyjnPageList")
	@ResponseBody
	public String getSfdaRyjnPageList(SfdaRyjn ryjn, HttpServletRequest request) throws Exception {
		String pageNumber = request.getParameter("pageNumber");  //页码，第几页

		if (StringUtils.isEmpty(pageNumber)||Integer.parseInt(pageNumber)<=0) {
			pageNumber = "1";
		}
		int pageNumber1 = Integer.parseInt(pageNumber);
		
		PageCommon<Object> p=new PageCommon<Object>();
		int start = (pageNumber1- Constant.NUM_1)*Constant.NUM_6+Constant.NUM_1;//pageNumber1*p.getPageSize()-p.getPageSize();//开始页
		int end = pageNumber1*Constant.NUM_6;//pageNumber1*p.getPageSize();//结束页
		
		Map resultMap = null;
		try {
			resultMap=sfdaRyjnService.getSfdaRyjnPageList(ryjn, start, end);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e.toString());
		}
		List<SfdaRyjn> list = (List<SfdaRyjn>) resultMap.get("p_cursor");
		int total = (int) resultMap.get("p_total");//总记录数
		int pageNo = (int) total / p.getPageSize();
		if(total%p.getPageSize()!=0){
			pageNo=pageNo + 1;
		}
		Map<String, Object> result = new HashMap<>();
			
		result.put("data", list);//
		result.put("total", total);//总记录数
		result.put("pageNo", pageNo);//


 		return Constant.GSON.toJson(result);
	}
	
	/**
	 * 添加荣誉技能
	 */
	@RequestMapping(value = "/addSfdaRyjn",produces={"text/html;charset=UTF-8"},
					method = RequestMethod.POST)
	@ResponseBody
	public String addSfdaRyjn(SfdaRyjn ryjn) throws Exception{
		String result = "";
		try {
			result = sfdaRyjnService.addSfdaRyjn(ryjn);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, ExceptionUtils.getStackTrace(e));
		}
		return result;
	}
	
	/**
	 * 修改司法技能
	 */
	@RequestMapping(value = "/updateSfdaRyjn",produces={"text/html;charset=UTF-8"}, 
			method = RequestMethod.POST)
	@ResponseBody
	public String updateSfdaRyjn(SfdaRyjn ryjn) {
		String count = "";
		Map<String,Object> map  = new HashMap<String,Object>();
		map.put("id", ryjn.getId());
		try {
			count = sfdaRyjnService.updateSfdaRyjn(ryjn);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, ExceptionUtils.getStackTrace(e));
		}
		map.put("success", count);
		return Constant.GSON.toJson(map);
	}
	
	/**
	 * 删除司法档案下的荣誉技能信息
	 */
	@RequestMapping(value = "/deleteSfdaRyjn", method = RequestMethod.GET)
	@ResponseBody
	public String deleteSfdaRyjn(SfdaRyjn ryjn) throws Exception{
		String count = StringUtils.EMPTY;
		
		try {
			count = sfdaRyjnService.deleteSfdaRyjn(ryjn);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e.toString());
		}
		String deleteState = StringUtils.EMPTY;
		if ("1".equals(count)) {
			deleteState = "删除成功！";
		} else {
			deleteState = "删除失败！";
		}
		return deleteState;
	}
	
	/**
	 * 文件上传
	 * @param filePath 上传后的文件地址
	 * @param file 上传文件信息
	 * @return 文件上传实体类
	 */
	private Upload fileUpload(String filePath, MultipartFile file){
		Upload upload = new Upload();
		File dir = new File(filePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		String fileName = file.getOriginalFilename();
		int index = fileName.lastIndexOf(".");
		String suffixName = StringUtils.EMPTY;
		if (index >= 0) {
			suffixName = fileName.substring(index);
		}

		String newFileName = Identities.get32LenUUID() + suffixName;
		Path path = dir.toPath().resolve(newFileName);
		try {
			Files.copy(file.getInputStream(), path);
		} catch (IOException e1) {
			e1.printStackTrace();
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e1.toString());
		}
		upload.setWjmc(fileName);
		upload.setWjdz(path.toString());
		return upload;
	}
	
	/**
	 * 
	 * @param file 上传文件的信息
	 * @param request 
	 * @return 成功与否的json字符串以及成功后的附件id
	 */
	@RequestMapping(value = "/uploadSfdaAjxxFj", 
			produces = { "text/html;charset=UTF-8" },method = RequestMethod.POST)
	@ResponseBody
	public String addFjOutId(@RequestParam("file") MultipartFile file,
										HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		// 从数据库获取路径
		String realpath = xtfjpathservice.getPath();
		Upload upload = fileUpload(org.apache.commons.lang3.StringUtils.strip(realpath),file);
		map.put("wjmc", upload.getWjmc());
		map.put("wjdz", upload.getWjdz());
		return Constant.GSON.toJson(map);
	}
	
	
	/**
	 * 案件信息上传后文件下载的方法
	 * @param wjdz 文件地址
	 * @param response 
	 */
	@RequestMapping(value = "/fileDownload")
	public void downloadFile(@RequestParam(value="wjdz",required=true)String wjdz,
							  HttpServletResponse response)throws Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");

		try {
			File file = new File(wjdz);
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
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e.toString());
		}
	}
	
	/**
	 * 根据附件地址删除附件
	 * @param wjdz 文件地址
	 * @return 是否成功的标示
	 */
	private boolean delFileByWjdz(String wjdz){
		boolean msg = false;
		try {
			wjdz = URLDecoder.decode(wjdz, "utf-8");
		} catch (Exception e) {
		}
		File file = new File(wjdz);
		if (file.exists()) {
			file.delete();
			msg = true;
		}
		return msg;
	}
	
	/**
	 * 根据文件地址删除磁盘中的文件
	 * @param wjdz 文件地址
	 * @return 是否成功的标示
	 */
	@RequestMapping(value = "/deleteFjByWjdz", method = RequestMethod.POST)
	@ResponseBody
	public String delteFile(@RequestParam(value="wjdz",required=true)String wjdz) throws Exception{
		boolean msg = delFileByWjdz(wjdz);
		if (msg) {
			return "1";//删除磁盘中的附件成功
		}else{
			return "0";//删除磁盘中的附件失败
		}
	}
	
	/**
	 * 首页查询：司法档案
	 * 	查询当前登录人所属部门下所有人在当前时间段中所有检察官的办案数量，荣誉奖励数量，责任追究数量等
	 * 1.先根据单位编码，部门编码查询该部门下所有人员的工号
	 * 2.根据单位编码，工号，开始时间，结束时间到yx_sfda_dagz表中去查找对应的档案的id
	 * 3.根据档案id查询荣誉奖励，责任追究等数量
	 * 4.根据单位编码，工号，开始时间，结束时间查询办案总数
	 */
	@RequestMapping(value = "/getAllInfoOfProcurator", method = RequestMethod.POST)
	@ResponseBody
	public String getAllInfoOfProcurator(@RequestParam(value="daId",required=true)String daId,
										 @RequestParam(value = "gh",required=true)String gh,
										 @RequestParam(value="dwbm",required=true)String dwbm,
										 @RequestParam(value="bmbm",required=true)String bmbm,
										 @RequestParam(value="kssj",required=true)String kssj,
										 @RequestParam(value="jssj",required=true)String jssj,
										 @RequestParam(value="isag",required=true)boolean isag,
										 @RequestParam(value="flag",required=true)String flag) throws Exception{
		String result = null;
		try {
			if(!kssj.equals("")&&!jssj.equals("")){
				result = sfdaRyjnService.getAllInfoOfProcurator(daId, gh, dwbm, bmbm, kssj, jssj, isag, flag);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e.toString());
		}
		return result;
	}

	/**
	 * 首页显示部门总人数
	 * @param dwbm
	 * @param bmbm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCountsOfPer", method = RequestMethod.POST)
	@ResponseBody
	public String getCountsOfPer (@RequestParam(value = "dwbm", required=true) String dwbm,
								  @RequestParam(value = "bmbm", required=true) String bmbm,
								  @RequestParam(value = "kssj", required=false) String kssj,
								  @RequestParam(value = "jssj", required=false) String jssj) throws Exception {

		if (StringUtils.isEmpty(kssj) || StringUtils.isEmpty(jssj)) {
			kssj = DateUtil.getYear(new Date()) + "-01"; // 档案开始时间:当前年的1月
			jssj = DateUtil.getYear(new Date()) + "-12"; // 档案结束时间：当前年的12月
		}

		int size = sfdaRyjnMapper.getAllGhAndMc(dwbm, bmbm, kssj, jssj).size();
		return JSONObject.toJSONString(size);
	}

	@RequestMapping(value = "/serachByType", method = RequestMethod.GET)
	@ResponseBody
	public String serachByType (@RequestParam(value = "dalx", required=true) String dalx,
								@RequestParam(value = "daid", required=true) String daid,
								@RequestParam(value = "kssj", required=true) String kssj,
								@RequestParam(value = "jssj", required=true) String jssj,
								@RequestParam(value = "page", required=true) Integer page,
								@RequestParam(value = "rows", required=true) Integer rows) throws Exception {

		Map<String, Object> map = sfdaRyjnService.serachByType(dalx, daid, kssj, jssj, page, rows);

		return JSONObject.toJSONString(map);
	}

}
