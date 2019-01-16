package com.swx.ibms.business.cases.controller;

import com.swx.ibms.business.archives.service.SfdacjService;
import com.swx.ibms.business.cases.bean.Ajwtfk;
import com.swx.ibms.business.cases.bean.DACJAJXYRXX;
import com.swx.ibms.business.cases.bean.JCGDAAJZL;
import com.swx.ibms.business.cases.bean.SFDAAJ;
import com.swx.ibms.business.cases.service.AjwtfkService;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.service.UploadService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.system.bean.Upload;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;



/**
 * 案件问题反馈Controller
 * @author 李佳 
 * @date: 2017年3月15日
 */
@RequestMapping("/ajwtfk")
@Controller
public class AjwtfkController {

	/**
	 * 案件问题反馈接口
	 */
	@Resource
	private AjwtfkService ajwtfkService;
	
	/**
	 * 司法档案服务对象接口
	 */
	@Resource
	private SfdacjService sfdacjService;
	
	/**
	 * 文件上传接口
	 */
	@Resource
	private UploadService uploadService;
	
	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;

	/**
	 * 新增案件问题反馈
	 * @param request 请求参数
	 * @return 是否新增成功
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public @ResponseBody String insert(HttpServletRequest request) {
		Ajwtfk ajwtfk = new Ajwtfk();
		HttpSession session = request.getSession();
		RYBM ry = (RYBM) session.getAttribute("ry");
		String dwbm = ry.getDwbm(); // 登录时获取当前人所属单位编码
		String tzmc = request.getParameter("tzmc"); // 通知名称
		try {
			tzmc = URLDecoder.decode(tzmc, "utf-8");
		} catch (Exception e) {
		}
		String tzlx = request.getParameter("tzlx");// 通知类型
		String wbid = request.getParameter("wbid"); // 外部实体id
		try {
			wbid = URLDecoder.decode(wbid, "utf-8");
		} catch (Exception e) {
		}
		String gh = ajwtfkService.getGhByBmsah(wbid); // 通过wbid(部门受案号)获取工号

		ajwtfk.setDwbm(dwbm);
		ajwtfk.setGh(gh);
		ajwtfk.setTzmc(tzmc);
		ajwtfk.setTzlx(tzlx);
		ajwtfk.setWbid(wbid);
		ajwtfk.setIsRead("0");

		String insertState = "";
		try {
			String result = ajwtfkService.insertAjwtfk(ajwtfk);
			if ("1".equals(result)) {
				insertState = "新增成功";
			} else {
				insertState = "新增失败";
			}
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e.toString());
		}
		return insertState;
	}

	/**
	 * 删除案件问题反馈信息
	 * @param request 请求参数
	 * @return 是否删除成功
	 */
	@RequestMapping("/delete")
	public @ResponseBody String delete(HttpServletRequest request) {

		String id = request.getParameter("id"); // 单位编码
		String deleteState = "";
		try {
			String result = ajwtfkService.deleteAjwtfk(id);
			if ("1".equals(result)) {
				deleteState = "删除成功";
			} else {
				deleteState = "删除失败";
			}
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e.toString());
		}
		return deleteState;
	}

	/**
	 * 修改案件问题反馈信息
	 * @param request 请求参数
	 * @return 是否更新成功
	 */
	@RequestMapping("/update")
	public @ResponseBody String update(HttpServletRequest request) {
		Ajwtfk ajwtfk = new Ajwtfk();
		String id = request.getParameter("id"); // 实体id
		String dwbm = request.getParameter("dwbm"); // 单位编码
		String gh = request.getParameter("gh"); // 工号
		String tzmc = request.getParameter("tzmc"); // 通知名称
		String tzlx = request.getParameter("tzlx");// 通知类型
		String wbid = request.getParameter("wbid"); // 外部实体id
		try {
			tzmc = URLDecoder.decode(tzmc, "utf-8");
			wbid = URLDecoder.decode(wbid, "utf-8");
		} catch (Exception e) {
		}
		
		if("".equals(gh)||null==gh){
			gh = ajwtfkService.getGhByBmsah(wbid);
		}
		String isRead = request.getParameter("isRead");// 是否已读取

		ajwtfk.setId(id);
		ajwtfk.setDwbm(dwbm);
		ajwtfk.setGh(gh);
		ajwtfk.setTzmc(tzmc);
		ajwtfk.setTzlx(tzlx);
		ajwtfk.setWbid(wbid);
		ajwtfk.setIsRead(isRead);

		String updateState = "";
		try {
			String result = ajwtfkService.updateAjwtfk(ajwtfk);
			if ("1".equals(result)) {
				updateState = "修改成功";
			} else {
				updateState = "修改失败";
			}
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e.toString());
		}
		return updateState;
	}

	/**
	 * 根据id获得案件问题反馈信息
	 * @param request 请求参数
	 * @return list
	 */
	@RequestMapping("/select")
	public @ResponseBody String getAjwtfk(HttpServletRequest request) {
		String id = request.getParameter("id"); // 实体id

		List<Ajwtfk> list = null;
		try {
			list = ajwtfkService.getAjwtfk(id);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e.toString());
		}
		Map<String, Object> result = new HashMap<>();
		result.put("data", list);
		return Constant.GSON.toJson(result);

	}

	/**
	 * 通过id查询统一受案号和部门受案号
	 * @param request 请求参数
	 * @return map
	 */
	@RequestMapping("/getbmsah")
	public @ResponseBody String getBmsahById(HttpServletRequest request) {
		String id = request.getParameter("id"); // 实体id

		Ajwtfk ajwtfk = null;
		try {
			List<Ajwtfk> list = ajwtfkService.getBmsahById(id);
			 ajwtfk = list.get(0);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e.toString());
		}
		
		if (Objects.isNull(ajwtfk)) {
			return StringUtils.EMPTY;
		}
		
		String bmsah = ajwtfk.getBmsah();
		String tysah = ajwtfk.getTysah();
		
		List<DACJAJXYRXX> xyrList = sfdacjService.getAjXyrList(tysah);//案件嫌疑人列表
		List<SFDAAJ> ajList= sfdacjService.selectajbytysah(bmsah);//查询案件信息
		List<JCGDAAJZL> bazlList = sfdacjService.getBazl(bmsah);//办案质量查询
		List<Upload> list = uploadService.selectbywbid(bmsah);

		Map<String, Object> map= new HashMap<>();
		map.put("xyrList", xyrList);
		map.put("aj", ajList.get(0));
		map.put("bazl", bazlList.get(0));
		map.put("fj", list);
		
		return Constant.GSON.toJson(map);
	}

}
