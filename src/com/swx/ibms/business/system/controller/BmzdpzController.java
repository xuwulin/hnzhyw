package com.swx.ibms.business.system.controller;

import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.system.bean.Bmzdpz;
import com.swx.ibms.business.system.service.BmzdpzService;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;



/**
 * 部门受理人配置控制器
 * @author 李治鑫
 * @since 2017年7月14日  下午5:09:45
 */
@Controller
@RequestMapping("/bmzdpz")
@SuppressWarnings("all")
public class BmzdpzController {
	
	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(BmzdpzController.class);
	
	/**
	 * 部门指定人配置服务
	 */
	@Resource
	private BmzdpzService bmzdpzService;
	
	/**
	 * 通过审批类型获取该类审批流程中的那些映射部门信息
	 * @param splx 审批类型
	 * @return 映射部门信息
	 */
	@RequestMapping("/getbmmc")
	@ResponseBody
	public String getBmmcBySplxFromJdlc(String splx){
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_splx", splx);
		
		List<Map<String,Object>> mapList = bmzdpzService.getBmysFromJdlcBysplx(map);
		
		return Constant.GSON.toJson(mapList);
	}
	
	/**
	 * 通过部门映射，审批类型，单位编码获取指定人信息
	 * @param bmys 部门映射
	 * @param splx 审批类型
	 * @param dwbm 单位编码
	 * @return 部门指定人信息
	 */
	@RequestMapping("/getzdrxx")
	@ResponseBody
	public String getZdrInfo(String bmys,String splx,String dwbm,String gh){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_bmys", bmys);
		map.put("p_splx", splx);
		map.put("p_dwbm", dwbm);
		map.put("p_gh", gh);
		map.put("p_isexist", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);
		map.put("p_errmsg", StringUtils.EMPTY);
		
		Bmzdpz zdrObj = bmzdpzService.getZdrInfo(map);
		
		return Constant.GSON.toJson(zdrObj);
	}

	/**
	 * 更新指定人信息
	 * @param request 请求参数
	 * @return 执行结果
	 */
	@RequestMapping("/addOrUpdateBmspr")
	@ResponseBody
	public String addOrUpdateBmspr(HttpServletRequest request){
		String bmys = request.getParameter("bmys");
		String splx = request.getParameter("splx");
		String bmbm = request.getParameter("bmbm");
		String bmmc = request.getParameter("bmmc");
		String gh = request.getParameter("gh");
		String mc = request.getParameter("mc");
		String bmsprId =  request.getParameter("bmsprId");
		String name = request.getParameter("name");
		
		Map<String,Object> map = new HashMap<String,Object>();
		if (StringUtils.isNotEmpty(bmsprId)) {
			map.put("p_id", bmsprId);
		}
		RYBM ry = (RYBM) request.getSession().getAttribute("ry");
		String dwbm = ry.getDwbm();// 登录人单位编码
		map.put("p_dwbm", dwbm);
		map.put("p_bmbm", bmbm);
		map.put("p_bmmc", bmmc);
		map.put("p_splx", splx);
		map.put("p_spr", mc);
		map.put("p_gh", gh);
		map.put("p_name", name);
		map.put("p_bmys", bmys);
		
		String msg = "";
		
		if(StringUtils.isEmpty(bmsprId)){
			msg = bmzdpzService.addBmspr(map);
		}else{
			msg = bmzdpzService.updateBmsprById(map);
		}
		return Constant.GSON.toJson(msg);
	}
	
	/**
	 * 删除指定人配置信息
	 * @param request 请求参数
	 * @return 执行结果
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public String deleteZdr(HttpServletRequest request){
		Map<String ,Object> map = new HashMap<String,Object>();
		String bmsprId =  request.getParameter("bmsprId");
 		map.put("p_id", bmsprId);
		String msg = bmzdpzService.deleteBmsprById(map);
		return Constant.GSON.toJson(msg);
	}
	
	
	/**
	 * 获取全部部门审批人信息
	 * @param req 
	 * @return 全部部门审批人
	 */
	@RequestMapping("/bmsprSelectPageList")
	@ResponseBody
	public String getBmsprPageList(HttpServletRequest req){
		Map<String,Object> map = new HashMap<String,Object>();
		RYBM ry = (RYBM) req.getSession().getAttribute("ry");	
		String dwbm = ry.getDwbm();// 登录人单位编码
		int nowPage = Integer.parseInt(req.getParameter("page"));//当前页
		int pageSize = Integer.parseInt(req.getParameter("rows"));//每页显示数
		
		map.put("dwbm",dwbm);
		map.put("nowPage", nowPage);
		map.put("pageSize", pageSize);
			
		PageCommon<Bmzdpz> pageCommon = bmzdpzService.getBmsprPageList(map);
			
		if (!Objects.isNull(pageCommon)) {
			map.clear();
			map.put("total", pageCommon.getTotalRecords());
			map.put("rows", pageCommon.getList());
			
			return Constant.GSON.toJson(map);
		}
		return null;
	}
	
	/**
	 * 获取人员列表
	 * @param dwbm 单位编码 
	 * @param bmbm 部门编码
	 * @return 列表
	 */
	@RequestMapping("/getpersonlist")
	@ResponseBody
	public String getpersonofdepart(String dwbm,String bmbm){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_dwbm", dwbm);
		map.put("p_bmbm", bmbm);
		
		List<Map<String,Object>> result = bmzdpzService.getpersonofdepart(map);
		return Constant.GSON.toJson(result);
	}
	
	/**
	 * 获取部门列表
	 * @param dwbm 单位编码 
	 * @return 列表
	 */
	@RequestMapping("/getBmListByDwbm")
	@ResponseBody
	public String getBmListByDwbm(@RequestParam("dwbm")String dwbm){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_dwbm", dwbm);
		
		List<Map<String,Object>> result = bmzdpzService.getBmListByDwbm(map);
		return Constant.GSON.toJson(result);
	}
	
	/**
	 * 获取人员列表
	 * @param dwbm 单位编码、部门编码
	 * @param bmbm 单位编码、部门编码
	 * @return 列表
	 */
	@RequestMapping("/getRyListByBm")
	@ResponseBody
	public String getRyListByBm(@RequestParam("dwbm")String dwbm,
								@RequestParam("bmbm")String bmbm){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_dwbm", dwbm);
		map.put("p_bmbm", bmbm);
		
		List<Map<String,Object>> result = bmzdpzService.getRyListByBm(map);
		return Constant.GSON.toJson(result);
	}
}
