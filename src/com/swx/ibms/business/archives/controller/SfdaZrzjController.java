package com.swx.ibms.business.archives.controller;

import com.swx.ibms.business.archives.bean.SfdaZrzj;
import com.swx.ibms.business.archives.service.SfdaZrzjService;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * @author:徐武林
 * @date:2018年3月29日上午9:36:08
 * @Description:责任追究Controller
 */
@Controller
@SuppressWarnings("all")
@RequestMapping("/sfdaZrzj")
public class SfdaZrzjController {

	/**
	 * 日志实体类
	 */
	private static final Logger LOG = Logger.getLogger(SfdaZrzjController.class);

	/**
	 * 司法档案下的责任追究Service
	 */
	@Autowired
	private SfdaZrzjService sfdaZrzjService;

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;

	/**
	 * 展示责任追究
	 */
	@RequestMapping(value = "/getSfdaZrzjPageList")
	@ResponseBody
	public String getSfdaZrzjPageList(SfdaZrzj zrzj, HttpServletRequest request) throws Exception {
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
			resultMap=sfdaZrzjService.getSfdaZrzjPageList(zrzj, start, end);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e.toString());
		}
		List<SfdaZrzj> list = (List<SfdaZrzj>) resultMap.get("p_cursor");
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
	 * 添加责任追究
	 */
	@RequestMapping(value = "/addSfdaZrzj",produces={"text/html;charset=UTF-8"},
					method = RequestMethod.POST)
	@ResponseBody
	public String addSfdaZrzj(SfdaZrzj zrzj) throws Exception{
		String result = "";
		try {
			result = sfdaZrzjService.addSfdaZrzj(zrzj);
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
	 * 修改责任追究
	 */
	@RequestMapping(value = "/updateSfdaZrzj",produces={"text/html;charset=UTF-8"}, 
			method = RequestMethod.POST)
	@ResponseBody
	public String updateSfdaZrzj(SfdaZrzj zrzj) {
		String count = "";
		Map<String,Object> map  = new HashMap<String,Object>();
		map.put("id", zrzj.getId());
		try {
			count = sfdaZrzjService.updateSfdaZrzj(zrzj);
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
	 * 删除责任追究
	 */
	@RequestMapping(value = "/deleteSfdaZrzj", method = RequestMethod.GET)
	@ResponseBody
	public String deleteSfdaZrzj(SfdaZrzj zrzj) throws Exception{
		String count = StringUtils.EMPTY;
		
		try {
			count = sfdaZrzjService.deleteSfdaZrzj(zrzj);
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
	
}
