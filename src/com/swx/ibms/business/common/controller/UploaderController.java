package com.swx.ibms.business.common.controller;


import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.service.UploadService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.system.service.XtfjpathService;
import com.swx.ibms.business.system.bean.Upload;
import com.swx.ibms.common.utils.Identities;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * 文件上传
 * @author 封泽超
 * @since 2017年3月2日
 */
@RequestMapping("/uploader")
@Controller
@SuppressWarnings("all")
public class UploaderController {

	/**
	 * 日志实体类
	 */
	private static final Logger LOG = Logger.getLogger(UploaderController.class);

	/**
	 * 文件上传服务
	 */
	@Resource
	private UploadService uploadService;
	
	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;

	/**
	 * 文件上传路径服务
	 */
	@Resource
	private XtfjpathService xtfjpathservice;

	/**
	 * 文件上传功能
	 * 
	 * @param file
	 *            文件
	 * @param request
	 *            外部id
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/upload", produces = { "text/html;charset=UTF-8" },
			method = RequestMethod.POST)
	public @ResponseBody String upload(@RequestParam("file") MultipartFile file,
			HttpServletRequest request){

		String wbid = request.getParameter("wbid");
		try {
			wbid = URLDecoder.decode(wbid, "utf-8");
		} catch (Exception e) {
		}
		String fjlx = request.getParameter("fjlx");

		// 从数据库获取路径
		String realpath = xtfjpathservice.getPath();

		File dir = new File(org.apache.commons.lang3.StringUtils.strip(realpath));
		if (!dir.exists()) {
			dir.mkdirs();
		}

		String fileName = file.getOriginalFilename();

		int index = fileName.lastIndexOf(".");
		String suffixName = StringUtils.EMPTY;
		if (index >= 0) {
			suffixName = fileName.substring(index);
		}

		String newFileName = UUID.randomUUID().toString() + suffixName;
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

		Upload upload = new Upload();
		upload.setWjmc(fileName);
		upload.setWjdz(path.toString());
		upload.setWbid(wbid);
		upload.setFjlx(fjlx);

		try {
			String message = uploadService.insertFile(upload);
			LOG.info(message);
			//日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CZRZ, "上传文件");
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e.toString());
		}

		return dir + "";
	}
	
	/**
	 * 文件上传并返回附件的实体id
	 * 
	 * @param file 文件
	 * @param request fjlx 附件类型
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadAndId", produces = { "text/html;charset=UTF-8" },
			method = RequestMethod.POST)
	public @ResponseBody String uploadAndId(@RequestParam("file") MultipartFile file,HttpServletRequest request){
		String wbid = request.getParameter("id");
		String fjlx = request.getParameter("fjlx");
		// 从数据库获取路径
		String realpath = xtfjpathservice.getPath();

		File dir = new File(org.apache.commons.lang3.StringUtils.strip(realpath));
		if (!dir.exists()) {
			dir.mkdirs();
		}

		String fileName = file.getOriginalFilename();
		int index = fileName.lastIndexOf(".");
		String suffixName = StringUtils.EMPTY;
		if (index >= 0) {
			suffixName = fileName.substring(index);
		}

		String newFileName = UUID.randomUUID().toString() + suffixName;
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

		Upload upload = new Upload();
		upload.setWjmc(fileName);//文件名称
		upload.setWjdz(path.toString());//文件地址
		upload.setWbid(wbid);//wbid
		upload.setFjlx(fjlx);//附件类型
		List<String> list=null;
		
		try {
			list = uploadService.insertFileAndId(upload);
			LOG.info(list.get(1));
			//日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CZRZ, "上传文件");
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e.toString());
		}

		return list.get(0);
	}

	/**
	 * 根据外部ID获取文件列表
	 * 
	 * @param request
	 *            外部id
	 * @return String
	 */
	@RequestMapping(value = "/selectbywbid")
	public @ResponseBody String selectbywbid(HttpServletRequest request) {
		String wbid = request.getParameter("wbid");
		try {
			wbid = URLDecoder.decode(wbid, "utf-8");
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e.toString());
		}
		List<Upload> list = uploadService.selectbywbid(wbid);
//		System.out.println(list);
		if (list == null) {
			return Constant.GSON.toJson(new ArrayList<>());
		}
		return Constant.GSON.toJson(list);
	}

	/**
	 * 下载文件
	 * @param request 外部id
	 * @param response 
	 */
	@RequestMapping(value = "/download")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) {
		String wbid = request.getParameter("wbid");
		String id = request.getParameter("id");
		try {
			wbid = URLDecoder.decode(wbid, "utf-8");
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e.toString());
		}

		List<Upload> list = uploadService.selectbywbid(wbid);
		if (list == null || list.size() == 0) {
			return;
		}
		String filename = null;
		String filepath = null;
		if (id == null || "".equals(id)) {
			filename = list.get(0).getWjmc();
			filepath = list.get(0).getWjdz();
		} else {
			for (Upload f : list) {
				if (id.equals(f.getId())) {
					filename = f.getWjmc();
					filepath = f.getWjdz();
				}
			}
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		try {
			response.setHeader("Content-Disposition", 
					"attachment;fileName=" + URLEncoder.encode(filename, "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e1.toString());
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
	 * 通过实体id下载文件
	 * @param request 实体id
	 * @param response 
	 */
	@RequestMapping(value = "/downloadById")
	public void downloadFileById(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		
		List<Upload> list = uploadService.selectbyid(id);
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		String filename = null;
		String filepath = null;
		if (id == null || "".equals(id)) {
			filename = list.get(0).getWjmc();
			filepath = list.get(0).getWjdz();
		} else {
			for (Upload f : list) {
				if (id.equals(f.getId())) {
					filename = f.getWjmc();
					filepath = f.getWjdz();
				}
			}
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		try {
			response.setHeader("Content-Disposition", 
					"attachment;fileName=" + URLEncoder.encode(filename, "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e1.toString());
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
	 * 删除文件
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return String 字符串
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody String delteFile(HttpServletRequest request,
			HttpServletResponse response) {
		String pid = request.getParameter("id");
		String absolutePath = request.getParameter("dz");
		try {
			absolutePath = URLDecoder.decode(absolutePath, "utf-8");
		} catch (Exception e) {
		}
		File file = new File(absolutePath);
		String y = "0";
		try {
			y = uploadService.deleteFj(pid);
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e.toString());
			y = "0";
		}
		return y;
	}
	
	/**
	 * 根据ID获取文件列表
	 * 
	 * @param request id
	 * @return 文件列表
	 */
	@RequestMapping(value = "/selectbyid")
	public @ResponseBody String selectbyid(HttpServletRequest request) {
		String id = request.getParameter("id");
		List<Upload> list = uploadService.selectbyid(id);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return Constant.GSON.toJson(list.get(0));
	}
	
	/**
	 * 根据所有附件id更新附件外部id
	 * @param request idArray 装载id的数组
	 * @return 是否成功 1成功 0不成功
	 */
	@RequestMapping(value = "/updateAllWbidById", method = RequestMethod.POST)
	public @ResponseBody String updateWbidById(HttpServletRequest request) {
		String idArray = request.getParameter("idArray");
		String wbid = request.getParameter("wbid");
		
		idArray = idArray.substring(1, idArray.length()-1);
		String[] array = idArray.split(",");
		String result="1";
		for(String e : array){
			String id=e.substring(1,e.length()-1);
			String y=uploadService.updateWbidById(id,wbid);
			if("0".equals(y)){
				result="0";
			}
		}
		return result;
	}
	
	/**
	 * 根据所有附件id查询出附件信息
	 * @param request idArray 装载id的数组
	 * @return 附件信息List
	 */
	@RequestMapping(value = "/selectAllFjxx", method = RequestMethod.POST)
	public @ResponseBody String selectAllFjxx(HttpServletRequest request) {
		String idArray = request.getParameter("idArray");
		
		idArray = idArray.substring(1, idArray.length()-1);
		String[] array = idArray.split(",");
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Upload> fjxxList= new ArrayList<Upload>();
		for(String e : array){
			String id=e.substring(1,e.length()-1);
			List<Upload> list=uploadService.selectbyid(id) ;
			if(CollectionUtils.isNotEmpty(list)){
				fjxxList.add(list.get(0));
			}
		}
		map.put("fjxxList", fjxxList);
		return Constant.GSON.toJson(map);
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
	 * 通过外部id、类型查询文件/附件信息
	 * @param wbid 外键id 
	 * @param fjlx 附件类型
	 * @return 结果集Json字符串
	 */
	@RequestMapping(value = "/getFjByTypeAndWbid",method = RequestMethod.POST)
	@ResponseBody
	public String getFjByTypeAndWbid(@RequestParam("wbid")String wbid,
									  @RequestParam("fjlx")String fjlx) {
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Upload> fjList= new ArrayList<Upload>();
		if (StringUtils.isNotEmpty(wbid)&&StringUtils.isNotEmpty(fjlx)) {
			map.put("p_wbid", wbid);
			map.put("p_fjlx", fjlx);
			fjList = uploadService.getFjByTypeAndWbid(map);
		}
		return Constant.GSON.toJson(fjList);
	}
	
	
	/**
	 * 
	 * @param file 上传文件的信息
	 * @param request 
	 * @return 成功与否的json字符串以及成功后的附件id
	 */
	@RequestMapping(value = "/addFjOutId", 
			produces = { "text/html;charset=UTF-8" },method = RequestMethod.POST)
	@ResponseBody
	public String addFjOutId(@RequestParam("file") MultipartFile file,
										HttpServletRequest request){
		
		String wbid = request.getParameter("wbid");
		try {
			wbid = URLDecoder.decode(wbid, "utf-8");
		} catch (Exception e) {
		}
		String fjlx = request.getParameter("fjlx");

		// 从数据库获取路径
		String realpath = xtfjpathservice.getPath();
		Upload upload = fileUpload(org.apache.commons.lang3.StringUtils.strip(realpath),file);
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("p_wbid", wbid);
		map.put("p_fjlx", fjlx);
		map.put("p_wjmc", upload.getWjmc());
		map.put("p_wjdz", upload.getWjdz());
		
		Map<String,Object> resultMap=new HashMap<String,Object>();
		try {
			resultMap = uploadService.addFjOutId(map);
			//日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CZRZ, "上传文件");
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e.toString());
		}

		return Constant.GSON.toJson(resultMap);
	}
	
	/**
	 * 展示附件详情
	 * 2018/4/12 徐武林 新增
	 */
	@RequestMapping(value = "/getFjPageList")
	@ResponseBody
	public String getFjPageList(Upload upload,
								@RequestParam(value="page",required=true)Integer nowPage,
					            @RequestParam(value="rows",required=true)Integer pageSize
					            ) throws Exception {

		int start = (nowPage-Constant.NUM_1)*pageSize+Constant.NUM_1;//pageNumber1*p.getPageSize()-p.getPageSize();//开始页
		int end = nowPage*pageSize;//pageNumber1*p.getPageSize();//结束页
		
		Map resultMap = null;
		try {
			resultMap=uploadService.getFjPageList(upload, start, end);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e.toString());
			throw e;
		}
		List<Upload> list = (List<Upload>) resultMap.get("p_cursor");
		int total = (int) resultMap.get("p_total");//总记录数
		Map<String, Object> result = new HashMap<>();
			
		result.put("rows", list);//
		result.put("total", total);//总记录数

 		return Constant.GSON.toJson(result);
	}
	
	@RequestMapping(value = "/downloadTempletFile")
	@ResponseBody
	public void downloadTempletFile(HttpServletRequest request, 
									HttpServletResponse response,
									@RequestParam(value="fileName",required=true)String fileName) {
		
//		String fileName = "templet_ywkhzb.xls";  //模板名称
//		String filepath = (this.getClass().getResource("/resource/templet/")+filename).substring(6); //模板真实路径
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		try {
			response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),Constant.CURRENT_USER.get().getGh(),Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, e1.toString());
		}

		try { //this.getClass().getResourceAsStream("/resource/templet/"+fileName)
			InputStream inputStream = this.getClass().getResourceAsStream("/resource/templet/"+fileName);
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[Constant.NUM_1024];
			int length = 0;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			inputStream.close();
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),Constant.CURRENT_USER.get().getGh(),Constant.CURRENT_USER.get().getMc(),Constant.RZLX_CWRZ, e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),Constant.CURRENT_USER.get().getGh(), Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, e.toString());
		}
	}
	
	
}
