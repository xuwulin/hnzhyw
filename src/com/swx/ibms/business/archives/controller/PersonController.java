package com.swx.ibms.business.archives.controller;

import com.swx.ibms.business.archives.bean.Person;
import com.swx.ibms.business.archives.bean.PersonJl;
import com.swx.ibms.business.archives.service.PersonService;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.service.UploadService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.system.service.XtfjpathService;
import com.swx.ibms.business.system.bean.Upload;
import com.swx.ibms.common.utils.FileUtil;
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
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;



/**
 *
 * PersonController.java 个人基本信息的Controller
 * @author east
 * @date<p>2017年5月16日</p>
 * @version 1.0
 * @description<p></p>
 */
@SuppressWarnings("all")
@RequestMapping("/person")
@Controller
public class PersonController {

	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(PersonController.class);

	/**
	 * 个人基本信息service
	 */
	@Resource
	private PersonService personService;

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
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;


	/**
	 * 根据dwbm(单位编码)\gh(工号)查询个人基本信息
	 * @param req 请求对象
	 * @return json样式的字符串
	 */
	@RequestMapping(value = "/selectList", method = RequestMethod.GET)
	@ResponseBody
	public String getPersonList(HttpServletRequest req){
		String dwbm = req.getParameter("dwbm");// 单位编码
		String gh = req.getParameter("gh");//工号
		String daId = req.getParameter("daId");//工号
		List<Object> list = null;
		if (StringUtils.isNotEmpty(dwbm)&&StringUtils.isNotEmpty(gh)
				&&StringUtils.isNotEmpty(daId)) {
			try {
				//调用个人基本信息的service
				list = personService.selectList(dwbm,gh,daId);
			} catch (Exception e) {//e.getMessage(),  e.getCause(),
				LOG.error(e.getMessage(),e);
			}
			if (!CollectionUtils.isEmpty(list)) {
				return Constant.GSON.toJson(list);
			}
		}
		return null;
	}

	/**
	 * 传入个人基本信息进行个人基本信息的修改
	 * @param person 个人基本信息对象
	 * @return json样式的字符串
	 */
	@RequestMapping(value = "/updateGrjbxxData", method = RequestMethod.POST)
	@ResponseBody
	public String updateGrjbxxData(Person person){
		Map<String,Object> map = new HashMap<String,Object>();
		if(!Objects.isNull(person)){
			if (StringUtils.isNotBlank(person.getId())) {
				try {
					//调用个人基本信息service
					String msg = personService.updateGrjbxxData(person);
					if (msg!=null&&!"".equals(msg)) {
						map.put("msg", msg);
					}
				} catch (Exception e) {
					LOG.error(e.getMessage(),e);
				}
			}
		}
		return Constant.GSON.toJson(map);
	}


	/**
	 * 传入个人基本信息进行个人基本信息的添加
	 * @param person 个人基本信息对象
	 * @return json样式的字符串
	 */
	@RequestMapping(value = "/addGrjbxxData", method = RequestMethod.POST)
	@ResponseBody
	public String addGrjbxxData(Person person){
		if(!Objects.isNull(person)){
			try {
				//调用个人基本信息service
				Map<String,Object> map = personService.insertGrjbxxData(person);
				Map<String,Object> resMap = new HashMap<String,Object>(Constant.NUM_4);
				if (!Objects.isNull(map.get("message"))&&!Objects.isNull(map.get("id"))) {
					resMap.put("msg", map.get("message"));
					resMap.put("id", map.get("id"));
					return Constant.GSON.toJson(resMap);
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(),e);
			}
		}
		return null;
	}

	/**
	 * 传入个人基本信息进行个人经历信息的添加
	 * @param personJl 个人经历信息对象
	 * @return json样式的字符串
	 */
	@RequestMapping(value = "/addGrjlxxData", method = RequestMethod.POST)
	@ResponseBody
	public String addGrjlxxData(PersonJl personJl){
		if(!Objects.isNull(personJl)){
			if (StringUtils.isNotEmpty(personJl.getPersonId())
					&&StringUtils.isNotEmpty(personJl.getType())) {
				try {
					//调用个人经历信息service
					//如果类型值为1 表示工作经历，2表示 为教育经历
					Map<String,Object> map = personService.insertGrjlData(personJl);
					Map<String,Object> resMap = new HashMap<String,Object>(Constant.NUM_4);
					if (!Objects.isNull(map.get("message"))&&!Objects.isNull(map.get("id"))) {
						resMap.put("msg", map.get("message"));
						resMap.put("id", map.get("id"));
						return Constant.GSON.toJson(resMap);
					}

				} catch (Exception e) {
					e.printStackTrace();
					LOG.error(e.getMessage(),e);
				}
			}
		}
		return null;
	}


	/**
	 * 传入个人基本信息进行个人经历信息的修改
	 * @param personJl 个人经历信息对象
	 * @return json样式的字符串
	 */
	@RequestMapping(value = "/updateGrjlxxData", method = RequestMethod.POST)
	@ResponseBody
	public String updateGrjlxxData(PersonJl personJl){
		Map<String,Object> map = new HashMap<String,Object>();
		if(!Objects.isNull(personJl)){
			if (StringUtils.isNotEmpty(personJl.getId())
					&&StringUtils.isNotEmpty(personJl.getType())) {
				try {
					//调用个人经历信息service
					//如果类型值为1 表示工作经历，2表示 为教育经历
					String msg = personService.updateGrjlData(personJl);

					if (msg!=null&&!"".equals(msg)) {
						map.put("msg", msg);
					}

				} catch (Exception e) {
					LOG.error(e.getMessage(),e);
				}
			}
		}
		return Constant.GSON.toJson(map);
	}

	/**
	 * 传入个人经历id删除个人经历信息
	 * @param req 请求对象
	 * @return json样式的字符串
	 */
	@RequestMapping(value = "/deleteGrjlxxData", method = RequestMethod.POST)
	@ResponseBody
	public String deleteGrjlxxData(HttpServletRequest req){
		Map<String,Object> map = new HashMap<String,Object>();
		PersonJl personJl = new PersonJl();//个人经历实体对象
		String id = req.getParameter("id");
		if(StringUtils.isNotEmpty(id)){
			personJl.setId(id);
		}
		if(!Objects.isNull(personJl)){
			if (StringUtils.isNotEmpty(personJl.getId())) {
				try {
					//调用个人经历信息service
					//如果类型值为1 表示工作经历，2表示 为教育经历
					String msg = personService.deleteGrjlData(personJl);

					if (msg!=null&&!"".equals(msg)) {
						map.put("msg", msg);
					}

				} catch (Exception e) {
					LOG.error(e.getMessage(),e);
				}
			}
		}
		return Constant.GSON.toJson(map);
	}


	/**
	 * 添加个人基本信息下的照片信息
	 * @param file 上传文件
	 * @param req 请求对象
	 * @return json样式的字符串
	 * @throws IllegalStateException
	 * @throws IOException io异常
	 */
	@RequestMapping(value = "/addGrPhotoData",produces = { "text/html;charset=UTF-8"},method = RequestMethod.POST)
	@ResponseBody
	public String addGrPhotoData(@RequestParam("file") MultipartFile file,
								 HttpServletRequest req)
			throws IllegalStateException,IOException{

		//获取页面传过来的个人基本信息id、上传附件类型（此处为5标示图片）
		String wbId = req.getParameter("wbId");
		wbId = URLDecoder.decode(wbId, "utf-8");
		String fjlx = req.getParameter("fjlx");
		if(StringUtils.isNotEmpty(wbId)&&StringUtils.isNotEmpty(fjlx)){
			Upload upload = returnGrPhotoInfo(req, file, wbId, fjlx,StringUtils.EMPTY,StringUtils.EMPTY);

			try {

				Map<String,Object> map = personService.insertGrFlData(upload);

				if(!Objects.isNull(map.get("message"))&&!Objects.isNull(map.get("id"))){
					map.put("wjdz", upload.getWjdz());
					map.put("wjmc", upload.getWjmc());
					map.put("id", map.get("id"));
					map.put("msg", map.get("message"));
				}

				LOG.info("PersonController：addGrPhotoData()、添加个人头像成功！");
				//日志记录
				logService.info(Constant.CURRENT_USER.get().getDwbm(),
						Constant.CURRENT_USER.get().getGh(),
						Constant.CURRENT_USER.get().getMc(),
						Constant.RZLX_CZRZ, "上传文件");

				return Constant.GSON.toJson(map);
			} catch (Exception e) {
				LOG.error(StringUtils.EMPTY, e);
				//日志记录
				logService.error(Constant.CURRENT_USER.get().getDwbm(),
						Constant.CURRENT_USER.get().getGh(),
						Constant.CURRENT_USER.get().getMc(),
						Constant.RZLX_CWRZ, e.toString());
			}
		}

		return null;
	}

	/**
	 * 修改个人基本信息下的照片信息
	 * @param file 上传文件
	 * @param req 请求对象
	 * @return json样式的字符串
	 * @throws IllegalStateException
	 * @throws IOException io异常
	 */
	@RequestMapping(value = "/updateGrPhotoData",produces = { "text/html;charset=UTF-8"},method = RequestMethod.POST)
	@ResponseBody
	public String updateGrPhotoData(@RequestParam("file") MultipartFile file,
									HttpServletRequest req)
			throws IllegalStateException,IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		//获取页面传过来的个人基本信息id、上传附件类型（此处为5标示图片）
		String wbId = req.getParameter("wbId");
		wbId = URLDecoder.decode(wbId, "utf-8");
		String fjlx = req.getParameter("fjlx");
		String id = req.getParameter("id");
		if(StringUtils.isNotEmpty(wbId)&&StringUtils.isNotEmpty(fjlx)
				&&StringUtils.isNotEmpty(id)){

			Upload upload= returnGrPhotoInfo(req,file,wbId,fjlx,id,StringUtils.EMPTY);

			try {

				String message = personService.updateFjData(upload);
				map.put("wjdz", upload.getWjdz());
				map.put("wjmc", upload.getWjmc());

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

		}

		return Constant.GSON.toJson(map);
	}

	/**
	 * 共用的上传文件的方法
	 * @param req 请求对象
	 * @param file 文件
	 * @param wbId 个人基本信息id
	 * @param fjlx 附件类型：5 标示 图片
	 * @param id 附件id==个人头像id
	 * @param photoContent 上传图片的目录
	 * @return 上传对象
	 */
	private Upload returnGrPhotoInfo(HttpServletRequest req,
									 MultipartFile file,String wbId,String fjlx,String id,String photoContent){

		//注：xtfjpathservice.getPath()从系统配置中获取路径是 D:\ ;将其去掉空格之后，再将\替换成/
//		String realPath = xtfjpathservice.getPath().trim().replace("\\", "/");

		if (StringUtils.isEmpty(photoContent)) {
			//固定的上传目录
			photoContent = "/image/gr_photo";
		}
		//获取存放图片的绝对路径
		String path1 =  req.getSession().getServletContext().getRealPath(photoContent);

		//判断上传目录是否存在，不存在则创建
		File dir = new File(path1);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		//获取上传文件的文件名以及组装真正要上传到的地址
		//获取上传的文件名
		String fileName = file.getOriginalFilename();

		//开启字节流执行输入流
		//关闭输入流
		//调用上传的 service，设置相关参数并返回是否成功的字符串
		int index = fileName.lastIndexOf(".");
		String suffixName = StringUtils.EMPTY;
		if (index >= 0) {
			suffixName = fileName.substring(index);
		}

		String newFileName = UUID.randomUUID().toString() + suffixName;
		Path realpath = dir.toPath().resolve(newFileName);
		try {
			Files.copy(file.getInputStream(), realpath);
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
		StringBuilder str = new StringBuilder(photoContent);
		str.append("/");
		str.append(newFileName);
		upload.setWjdz(str.toString());
		upload.setWbid(wbId);
		upload.setFjlx(fjlx);
		upload.setId(id);

		return upload;
	}


	/**
	 * 添加个人基本信息下的照片信息到临时目录
	 * @param file 上传文件
	 * @param req 请求对象
	 * @return json样式的字符串
	 * @throws IllegalStateException
	 * @throws IOException io异常
	 */
	@RequestMapping(value = "/addGrPhotoToTempData",produces = { "text/html;charset=UTF-8"},method = RequestMethod.POST)
	@ResponseBody
	public String addGrPhotoToTempData(@RequestParam("file") MultipartFile file,
									   HttpServletRequest req)throws IllegalStateException,IOException{
		Map<String,Object> map = new HashMap<String,Object>();

		//注：xtfjpathservice.getPath()从系统配置中获取路径是 D:\ ;将其去掉空格之后，再将\替换成/
//		String realpath = xtfjpathservice.getPath().trim().replace("\\", "/");

		String photoTempContent = "/image/gr_photo/temp";//临时放入头像的文件目录 /image/gr_photo/temp

		//获取上传的文件名
		String fileName = file.getOriginalFilename();
		Upload upload = returnGrPhotoInfo(req, file, StringUtils.EMPTY,StringUtils.EMPTY,StringUtils.EMPTY,photoTempContent);

		try {
			//上传到临时目录后，取出目录名+文件名返回前台
			map.put("wjdz", upload.getWjdz());
			map.put("wjmc", fileName);

			//日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(),Constant.CURRENT_USER.get().getGh(),Constant.CURRENT_USER.get().getMc(),Constant.RZLX_CZRZ, "上传文件");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(StringUtils.EMPTY, e);
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),Constant.CURRENT_USER.get().getGh(),Constant.CURRENT_USER.get().getMc(),Constant.RZLX_CWRZ, e.toString());
		}

		return Constant.GSON.toJson(map);
	}

	/**
	 * 根据单位编码、工号从yx_sfda_grjbxx表中获取最新档案信息，用于创建新档案时赋值
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectInfo", method = RequestMethod.GET)
	@ResponseBody
	public String selectInfo(HttpServletRequest req) throws Exception{
		Map<String,Object> map = new HashMap<>();
		String dwbm = req.getParameter("dwbm");// 单位编码
		String gh = req.getParameter("gh");//工号

		if (StringUtils.isNotEmpty(dwbm) && StringUtils.isNotEmpty(gh)) {
			try {
				//调用个人基本信息的service
				map = personService.selectInfo(dwbm,gh);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(),e);
			}
			return Constant.GSON.toJson(map);
		}
		return null;
	}

	/**
	 * 根据查询出的最近的个人基本信息的id和da_id去查询对应的工作经历和教育经历
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectExperience", method = RequestMethod.GET)
	@ResponseBody
	public String selectExperience(HttpServletRequest req) throws Exception{
		Map<String,Object> map = new HashMap<>();
		String daid = req.getParameter("daId");// 档案id
		String grjbxxId = req.getParameter("grjbxxId");//个人基本信息id

		if (StringUtils.isNotEmpty(daid) && StringUtils.isNotEmpty(grjbxxId)) {
			try {
				//调用个人基本信息的service
				map = personService.selectExperience(daid,grjbxxId);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(),e);
			}
			return Constant.GSON.toJson(map);
		}
		return null;
	}

	@RequestMapping(value = "/modifyGrjbxxZp",produces = { "text/html;charset=UTF-8"},method = RequestMethod.POST)
	@ResponseBody
	public String modifyGrjbxxZp(@RequestParam(value="wbId",required=true) String wbId,
								 @RequestParam(value="file",required=true) MultipartFile file,
								 HttpServletRequest req) throws IOException {
		Map<String,Object> map = new HashMap<String,Object>();
		String zp = FileUtil.getBase64ImgStr(file);
		String zpName = file.getOriginalFilename();
		Integer res = personService.modifySfdaGrjbxxZpById(zp,wbId,zpName);
		map.put("result", res);
		map.put("zp", zp);
		map.put("zpName", zpName);
		return Constant.GSON.toJson(map);
	}

}
