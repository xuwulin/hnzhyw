package com.swx.ibms.business.archives.controller;


import com.swx.ibms.business.archives.bean.SfdaGrjbxxInputExcel;
import com.swx.ibms.business.archives.bean.SfdaGrjlInputExcel;
import com.swx.ibms.business.archives.service.SfdaGrxxPldrService;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.common.utils.ExcelUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
*个人信息批量导入
*/
@RestController
@RequestMapping("grxxPldr")
public class SfdaGrxxPldrController  {

	@Autowired
	private SfdaGrxxPldrService sfdaGrxxPldrService;

	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(SfdaGrxxPldrController.class);

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;

	@RequestMapping("/selectALLGrjbxx")
	@ResponseBody
	public Map<String, Object> selectALLGrjbxx(@RequestParam(value="dwbm",required=true)String dwbm,
											   @RequestParam(value="page",required=true)Integer page,
											   @RequestParam(value="pageSize",required=true)Integer pageSize) throws Exception{
		try {
			Map<String, Object> grxxMap = sfdaGrxxPldrService.selectALLGrjbxx(dwbm,page, pageSize);
			return grxxMap;
		} catch (Exception e) {
			LOG.error("获取批量导入个人信息列表失败" + e.getMessage());
		}
		return null;
	}

	@RequestMapping("/selectALLGrjl")
	@ResponseBody
	public Map<String, Object> selectALLGrjl(@RequestParam(value="dwbm",required=true)String dwbm,
											 @RequestParam(value="page",required=true)Integer page,
											 @RequestParam(value="pageSize",required=true)Integer pageSize) throws Exception{
		try {
			Map<String, Object> grjlMap = sfdaGrxxPldrService.selectALLGrjl(dwbm,page, pageSize);
			return grjlMap;
		} catch (Exception e) {
			LOG.error("获取批量导入个人经历列表失败" + e.getMessage());
		}
		return null;
	}

	@RequestMapping(value = "/insertAllGrjbxx", produces = { "text/html;charset=UTF-8" },
			method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> insertAllGrjbxx(@RequestParam("file") MultipartFile file,
											  HttpServletRequest request) throws Exception{
		Map<String,Object> resMap = new HashMap<>();
    	SfdaGrjbxxInputExcel person = new SfdaGrjbxxInputExcel();
    	SfdaGrjlInputExcel personJl = new SfdaGrjlInputExcel();
    	String result = "";
		try {
			//个人基本信息
			List<Map<String, Object>> objList = ExcelUtil.importExcelData(file, 0,2, person);//ExcelUtil.importExcelData(path, 0,3, person);
			for (Map<String, Object> map : objList) {
				result = sfdaGrxxPldrService.insertAllGrjbxx(map);
			}
			//个人经历，当个人基本信息导入成功时才允许导入个人经历
			if(result.equals("success")){
				List<Map<String, Object>> grjlList = ExcelUtil.importExcelData(file, 1,3, personJl);//ExcelUtil.importExcelData(path, 0,3, person);
				for (Map<String, Object> map2 : grjlList) {
					result = sfdaGrxxPldrService.insertAllGrjl(map2);
				}
			}

		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, ExceptionUtils.getStackTrace(e));
		}
		resMap.put("result", result);
		return resMap;
	}

	@RequestMapping(value = "/deleteGrjbxxById",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteGrjbxxById(HttpServletRequest req) throws Exception{
		Map<String,Object> map = new HashMap<>();
		String id = req.getParameter("id");
		String result = "";
		try {
			result = sfdaGrxxPldrService.deleteGrjbxxById(id);
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

	@RequestMapping(value = "/deleteGrjlById",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteGrjlById(HttpServletRequest req) throws Exception{
		Map<String,Object> map = new HashMap<>();
		String id = req.getParameter("id");
		String result = "";
		try {
			result = sfdaGrxxPldrService.deleteGrjlById(id);
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

	@RequestMapping("/selectGrjbxxBydg")
	@ResponseBody
	public Map<String, Object> selectGrjbxxBydg(@RequestParam(value="dwbm",required=true)String dwbm,
											    @RequestParam(value="gh",required=true)String gh) throws Exception{
		try {
			Map<String, Object> grjlMap = sfdaGrxxPldrService.selectGrjbxxBydg(dwbm, gh);
			return grjlMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/selectGrjlBydg")
	@ResponseBody
	public Map<String, Object> selectGrjlBydg(@RequestParam(value="dwbm",required=true)String dwbm,
											  @RequestParam(value="gh",required=true)String gh) throws Exception{
		try {
			Map<String, Object> grjlMap = sfdaGrxxPldrService.selectGrjlBydg(dwbm, gh);
			return grjlMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
