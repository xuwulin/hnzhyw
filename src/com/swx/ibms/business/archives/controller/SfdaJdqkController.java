package com.swx.ibms.business.archives.controller;

import com.swx.ibms.business.archives.bean.SfdaJdqk;
import com.swx.ibms.business.archives.service.SfdaJdqkService;
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
 * @Description:监督情况Controller
 */
@Controller
@SuppressWarnings("all")
@RequestMapping("/sfdaJdqk")
public class SfdaJdqkController {

	/**
	 * 日志实体类
	 */
	private static final Logger LOG = Logger.getLogger(SfdaJdqkController.class);

	/**
	 * 司法档案下的监督情况Service
	 */
	@Autowired
	private SfdaJdqkService sfdaJdqkService;

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;

	/**
	 * 展示监督情况
	 */
	@RequestMapping(value = "/getSfdaJdqkPageList")
	@ResponseBody
	public String getSfdaJdqkPageList(SfdaJdqk jdqk, HttpServletRequest request) throws Exception {
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
			resultMap=sfdaJdqkService.getSfdaJdqkPageList(jdqk, start, end);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e.toString());
		}
		List<SfdaJdqk> list = (List<SfdaJdqk>) resultMap.get("p_cursor");
		int total = (int) resultMap.get("p_total");//总记录数
		int pageNo = (int) total / p.getPageSize();
		if(total%p.getPageSize()!=0){
			pageNo=pageNo + 1;
		}
		Map<String, Object> result = new HashMap<>();
			
		result.put("data", list);
		result.put("total", total);//总记录数
		result.put("pageNo", pageNo);

 		return Constant.GSON.toJson(result);
	}
	
	/**
	 * 添加监督情况
	 */
	@RequestMapping(value = "/addSfdaJdqk",produces={"text/html;charset=UTF-8"},
					method = RequestMethod.POST)
	@ResponseBody
	public String addSfdaJdqk(SfdaJdqk jdqk) throws Exception{
		
		String result = "";
		try {
			result = sfdaJdqkService.addSfdaJdqk(jdqk);
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
	 * 修改监督情况
	 */
	@RequestMapping(value = "/updateSfdaJdqk",produces={"text/html;charset=UTF-8"}, 
			method = RequestMethod.POST)
	@ResponseBody
	public String updateSfdaJdqk(SfdaJdqk jdqk) {

		String count = "";
		Map<String,Object> map  = new HashMap<String,Object>();
		map.put("id", jdqk.getId());
		try {
			count = sfdaJdqkService.updateSfdaJdqk(jdqk);
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
	 * 删除监督情况
	 */
	@RequestMapping(value = "/deleteSfdaJdqk", method = RequestMethod.GET)
	@ResponseBody
	public String deleteSfdaJdqk(SfdaJdqk jdqk) throws Exception{
		String count = StringUtils.EMPTY;
		
		try {
			count = sfdaJdqkService.deleteSfdaJdqk(jdqk);
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
