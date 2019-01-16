package com.swx.ibms.business.cases.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.swx.ibms.business.cases.bean.SfdaAjxx;
import com.swx.ibms.business.cases.service.SfdaAjxxService;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.service.UploadService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.system.service.XtfjpathService;
import com.swx.ibms.business.system.bean.Upload;
import com.swx.ibms.common.utils.DateUtil;
import com.swx.ibms.common.utils.Identities;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * 
 * SfdaAjxxController.java  司法档案下的案件信息Controller
 * @author 何向东
 * @date<p>2017年8月3日</p>
 * @version 1.0
 * @description<p></p>
 */
@Controller
@SuppressWarnings("all")
@RequestMapping("/sfdaAjxx")
public class SfdaAjxxController {
	
	/**
	 * 日志实体类
	 */
	private static final Logger LOG = Logger.getLogger(SfdaAjxxController.class);
	
	/**
	 * 司法档案下的案件信息Service
	 */
	@Autowired
	private SfdaAjxxService sfdaAjxxService;
	
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
	
	/**
	 * 查询所有司法档案下的检察官主办案件信息
	 * @param daId 档案id
	 * @param status 审批状态
	 * @param page 当前页
	 * @param rows 每页显示数
	 * @return 结果集Json字符串
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getSfdaZbAjxxPageList")
	@ResponseBody
	public String getSfdaZbAjxxPageList(@RequestParam(value="daId",required=true)String daId,
									  @RequestParam(value="status",required=false)String status,
									  @RequestParam(value="page",required=true)Integer page,
									  @RequestParam(value="rows",required=true)Integer rows) 
											  throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> resMap = new HashMap<String,Object>();
		if (StringUtils.isNotEmpty(daId)&&StringUtils.isNotEmpty(page.toString())
				&&StringUtils.isNotEmpty(rows.toString())) {
			map.put("p_da_id", daId);
			map.put("p_status", status);
			map.put("nowPage", page);
			map.put("pageSize", rows);
			resMap = sfdaAjxxService.getSfdaZbAjxxPageList(map);
			map.clear();
		}
		return Constant.GSON.toJson(resMap);
	}
	
	/**
	 * 查询所有司法档案下的检察官从办/协办/参办案件信息
	 * @param daId 档案id
	 * @param status 审批状态
	 * @param page 当前页
	 * @param rows 每页显示数
	 * @return 结果集Json字符串
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getSfdaCbAjxxPageList")
	@ResponseBody
	public String getSfdaCbAjxxPageList(@RequestParam(value="daId",required=true)String daId,
									  @RequestParam(value="status",required=false)String status,
									  @RequestParam(value="page",required=true)Integer page,
									  @RequestParam(value="rows",required=true)Integer rows) 
											  throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		
		if (StringUtils.isNotEmpty(daId)&&StringUtils.isNotEmpty(page.toString())
				&&StringUtils.isNotEmpty(rows.toString())) {
			map.put("p_da_id", daId);
			map.put("p_status", status);
			map.put("nowPage", page);
			map.put("pageSize", rows);
			PageCommon<Object> pageCommon = sfdaAjxxService.getSfdaCbAjxxPageList(map);
			map.clear();
			if (null!=pageCommon) {
				map.put("total", pageCommon.getTotalRecords());
				map.put("rows", pageCommon.getList());
			}
		}
		return Constant.GSON.toJson(map);
	}
	
	/**
	 * 查询单条司法档案下的检察官案件信息根据id
	 * @param ajId 案件id
	 * @param status 案件审批状态
	 * @return 司法档案下的检察官案件信息Json字符串
	 */
	@RequestMapping(value = "/getSfdaAjxxById", method = RequestMethod.POST)
	@ResponseBody
	public String getSfdaAjxxById(@RequestParam(value="ajId",required=true)String ajId,
						          @RequestParam(value="status",required=false)String status) 
						        		  throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		if (StringUtils.isNotEmpty(ajId)) {
			map.put("p_ajId", ajId);
			map.put("p_status", status);
			SfdaAjxx sfdaAjxx = sfdaAjxxService.getSfdaAjxxById(map);
			return Constant.GSON.toJson(sfdaAjxx);
		}
		return null;
	}
	
	/**
	 * 添加司法档案下的检察官案件信息
	 * @param daId 案件id
	 * @param ajName 案件名
	 * @param ajYwlx 案件业务类型
	 * @param ajSlrq 案件受理日期
	 * @param ajWjrq 案件完结日期
	 * @param ajlb 案件类别
	 * @param ajsl 案件数量
	 * @param bz 备注
	 * @param ajlx 案件类型：zb 主办 cb参办
	 * @return 成功与否的字符串标示
	 */
	@RequestMapping(value = "/addOrUpdateSfdaAjxx", method = RequestMethod.POST)
	@ResponseBody
	public String addSfdaAjxx(@RequestParam(value="ajId",required=false)String ajId,
							  @RequestParam(value="daId",required=true)String daId,
						      @RequestParam(value="ajName",required=true)String ajName,
						      @RequestParam(value="ajYwlx",required=true)String ajYwlx,
						      @RequestParam(value="ajSlrq")String ajSlrq,
						      @RequestParam(value="ajWjrq")String ajWjrq,
						      @RequestParam(value="ajlb")String ajlb,
						      @RequestParam(value="ajsl",required=false)String ajsl,
						      @RequestParam(value="ajlx",required=true)String ajlx,
						      @RequestParam(value="bz",required=false)String bz,
						      @RequestParam(value="fileArray",required=true)String fileArray,
						      @RequestParam(value="fjlx",required=true)String fjlx) 
						    		  throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String,Object> viewMap = new HashMap<String,Object>();
		String msg = "";
		String fileMsg = "";
		
		JsonArray fileJsonArr = new JsonArray();
		if (StringUtils.isNotEmpty(fileArray)) {
			JsonParser jsonParse = new JsonParser();
			fileJsonArr = (JsonArray) jsonParse.parse(fileArray);
		}
		
		if (StringUtils.isNotEmpty(daId)) {
			map.put("p_ajName", ajName);
			map.put("p_ajYwlx", ajYwlx); 
			try {
				map.put("p_ajSlrq", DateUtil.stringtoDate(ajSlrq,DateUtil.FORMAT_FOUR));
				map.put("p_ajWjrq", DateUtil.stringtoDate(ajWjrq,DateUtil.FORMAT_FOUR));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			map.put("p_ajlb", ajlb);
			map.put("p_ajsl", ajsl);
			map.put("p_bz", bz);
			if (StringUtils.isEmpty(ajId)) {
				map.put("p_da_id", daId);
				map.put("p_ajlx", ajlx);
				resultMap.clear();
				resultMap = sfdaAjxxService.addSfdaAjxx(map);
				msg = (String)resultMap.get("p_errMsg");
				//在调用上传接口，传入wbid(p_ajId)、wjdz、wjmc、fjlx
				if (!Objects.isNull(fileJsonArr)) {
					for (int i = 0; i < fileJsonArr.size(); i++) {
						Upload upload = new Upload();
						upload.setFjlx(fjlx);
						upload.setWbid((String)resultMap.get("p_ajId"));
						String wjmc = fileJsonArr.get(i).
								getAsJsonObject().get("fileName").getAsString();
						String wjdz = fileJsonArr.get(i).
								getAsJsonObject().get("fileUrl").getAsString();
						wjdz.replace("\\"+"\\","\\");
						upload.setWjdz(wjdz);
						upload.setWjmc(wjmc);
						fileMsg = uploadService.insertFile(upload);
					}
				}
			}else{
				map.put("p_ajId", ajId);
				msg = sfdaAjxxService.updateSfdaAjxx(map);
			}
			viewMap.put("msg", msg);
			viewMap.put("fileMsg", fileMsg);
			return Constant.GSON.toJson(viewMap);
		}
		return null;
	}
	
	/**
	 * 删除司法档案下的检察官案件信息
	 * @param ajId 案件id
	 * @return 成功与否的字符串标示
	 * @throws Exception 异常
	 */
	@RequestMapping(value = "/deleteSfdaAjxx", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSfdaAjxx(@RequestParam(value="ajId",required=true)String ajId) 
						throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> viewMap = new HashMap<String,Object>();
		String msg = "";
		String fileMsg = "";
		if (StringUtils.isNotEmpty(ajId)) {
			map.put("p_ajId", ajId);
			msg = sfdaAjxxService.deleteSfdaAjxx(map); //删除单个案件
			//根据案件id查询所对应的所有附件，并循环取出文件地址，并调用删除方法 
			List<Upload> uploadList = uploadService.selectbywbid(ajId);
			if (CollectionUtils.isNotEmpty(uploadList)) {
				for (Upload upload : uploadList) {
					boolean msgFlag = delFileByWjdz(upload.getWjdz());
					if (msgFlag) {
						viewMap.put("msgFlag", "1");//删除文件成功
					}else{
						viewMap.put("msgFlag", "0");//删除文件失败
					}
				}
			}
			fileMsg = uploadService.delFjByWbid(ajId);//删除案件所对应的附件
			viewMap.put("msg", msg);
			viewMap.put("fileMsg", fileMsg);
		}
		return Constant.GSON.toJson(viewMap);
	}
	
	/**
	 * 根据档案id修改此档案对应的案件信息的状态
	 * @param daId 档案id
	 * @param status 审批状态
	 * @return 成功与否的字符串标示
	 */
	@RequestMapping(value = "/updateStatusByDaId", method = RequestMethod.POST)
	@ResponseBody
	public String updateStatusByDaId(@RequestParam(value="daId",required=true)String daId,
								     @RequestParam(value="status",required=true)String status) 
								    		 throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String msg = "";
		if (StringUtils.isNotEmpty(daId)) {
			map.put("p_da_id", daId);
			map.put("p_status", status);
			msg = sfdaAjxxService.updateStatusByDaId(map);
			
		}
		return Constant.GSON.toJson(msg);
	}
	
	
	/**
	 * 文件上传
	 * @param filePath 上传后的文件地址
	 * @param file 上传文件信息
	 * @return 文件上传实体类
	 */
	private Upload fileUpload(String filePath,MultipartFile file){
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


  @RequestMapping(value = "/modifyStatusByAjId", method = RequestMethod.POST)
	@ResponseBody
	public String modifyStatusByAjId(@RequestParam(value="ajId",required=true)String ajId,
									 @RequestParam(value="ajStatus",required=true)String ajStatus)throws Exception{
		Map<String,Object> resMap = new HashMap<String,Object>(Constant.NUM_4);
		Integer xgAjzt = sfdaAjxxService.modifyStatusByAjId(ajStatus, ajId);
		resMap.put("ajzt", xgAjzt);
		return Constant.GSON.toJson(resMap);
	}

}
