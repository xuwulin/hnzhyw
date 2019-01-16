package com.swx.ibms.business.rbac.controller;

import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.BMBM;
import com.swx.ibms.business.rbac.bean.DWBM;
import com.swx.ibms.business.rbac.bean.Department;
import com.swx.ibms.business.rbac.bean.JSBM;
import com.swx.ibms.business.rbac.service.DepartmentService;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 
 * @author 何向东
 * @date：2017年4月19日
 * @version 1.0
 * @description：部门配置controller：实现对某单位下部门的CRUD；
 * 几个重要的参数 。
 * 
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/department")
public class DepartmentController{
	
	/**
	 * 部门Service接口 
	 */
	@Resource
	private DepartmentService departmentService;
	
	
	/**
	 * 根据单位编码、是否删除的标示查询此单位下的所有部门信息 
	 * @param req 请求参数 
	 * @return JSON样式的字符串
	 */
	@RequestMapping(value = "/selectList", method = RequestMethod.GET)
	@ResponseBody
	public String getDepartmentList(HttpServletRequest req){
		String dwbm = req.getParameter("dwbm");// 单位编码
		String sfsc = req.getParameter("sfsc");//是否删除
		if (!"".equals(dwbm)&&!"".equals(sfsc)) {
			List<BMBM> list = departmentService.selectZhywList(dwbm,sfsc);
			if (!CollectionUtils.isEmpty(list)) {
				return Constant.GSON.toJson(list);
			}
		}
		return null;
	}
	
	
	/**
	 * 根据单位编码、是否删除的标示、当前页、每页显示数查询此单位下的所有部门信息并分页 
	 * @param req 请求参数 
	 * @return JSON样式的字符串
	 */
	@RequestMapping(value = "/selectPageList", method = RequestMethod.POST)
	@ResponseBody
	public String getPageList(HttpServletRequest req){
		Map<String,Object> map = new HashMap<String,Object>();
		String dwbm = req.getParameter("dwbm");// 单位编码
		String sfsc = req.getParameter("sfsc");//是否删除
		int nowPage = Integer.parseInt(req.getParameter("page"));//当前页
		int pageSize = Integer.parseInt(req.getParameter("rows"));//每页显示数
		if (!"".equals(dwbm)&&!"".equals(sfsc)) {
			PageCommon<BMBM> pageCommon = departmentService.selectPageList(dwbm,sfsc,
					nowPage,pageSize);
			if (null!=pageCommon) {
				map.put("total", pageCommon.getTotalRecords());
				map.put("rows", pageCommon.getList());
			}
		}
		return Constant.GSON.toJson(map);
	}
	
	
	
	
	/**
	 * 添加某单位下的部门信息
	 * @param bmbm 部门对象 
	 * @return JSON样式的字符串
	 */
	@RequestMapping(value = "/addData", method = RequestMethod.POST)
	@ResponseBody
	public String addDeptInfo(BMBM bmbm){
		Map<String,Object> map = new HashMap<String,Object>();
		if (null!=bmbm) {
			if (!"".equals(bmbm.getDwbm())) {
				String msg = departmentService.insertData(bmbm);
				if (msg!=null&&!"".equals(msg)) {
					map.put("msg", msg);
				}
			}
		}
		return Constant.GSON.toJson(map);
	}
	
	
	/**
	 * 根据单位编码、部门编码 修改部门信息 
	 * @param bmbm 部门对象
	 * @return JSON样式的字符串
	 */
	@RequestMapping(value = "/updateData", method = RequestMethod.POST)
	@ResponseBody
	public String updateDeptInfo(BMBM bmbm){
		Map<String,Object> map = new HashMap<String,Object>();
		if (null!=bmbm) {
			if (!"".equals(bmbm.getBmbm())&&!"".equals(bmbm.getDwbm())
					&&!"".equals(bmbm.getSfsc())) {
				String msg = departmentService.updateData(bmbm);
				if (msg!=null&&!"".equals(msg)) {
					map.put("msg", msg);
				}
			}
		}
		return Constant.GSON.toJson(map);
	}
	
	
	/**
	 * 根据单位编码、部门编码、是否删除的标示修改部门编码的是否删除的标示为'Y'，实现假删除 
	 * @param bmbm 部门对象
	 * @return JSON样式的字符串
	 */
	@RequestMapping(value = "/deleteData", method = RequestMethod.POST)
	@ResponseBody
	public String deleteDeptInfo(BMBM bmbm){
		Map<String,Object> map = new HashMap<String,Object>();
		if (null!=bmbm) {
			if (!"".equals(bmbm.getBmbm())&&!"".equals(bmbm.getDwbm())
					&&!"".equals(bmbm.getSfsc())) {
				String msg = departmentService.deleteData(bmbm);
				if (msg!=null&&!"".equals(msg)) {
					map.put("msg", msg);
				}
			}
		}
		return Constant.GSON.toJson(map);
	}
	
	
	/**
	 * 根据单位编码、是否删除标示查询单位信息（是否删除的标示在实现类里面定义的）
	 * @param req 请求对象
	 * @return JSON样式的字符串
	 */
	@RequestMapping(value = "/selectDwbmData", method = RequestMethod.GET)
	@ResponseBody
	public String selectDwbmData(HttpServletRequest req){
		String dwbm = req.getParameter("dwbm");
		DWBM unitNo =null;
//		Map<String,Object> map = new HashMap<String,Object>();
		if (StringUtils.isNotEmpty(dwbm)) {
			unitNo = departmentService.selectDwbm(dwbm);
//			map.put("dwbm", unitNo);
		}
		return Constant.GSON.toJson(unitNo);
	}
	
	/**
	 * @param ddt 部门角色
	 * @param dwbm 单位编码
	 * @param bmmc 部门名称
	 * @return message
	 */
	@RequestMapping(value="addbmjs")
	@ResponseBody
	public String addbmjs(String ddt,String dwbm,String bmmc){
		String[] array = ddt.split(",");
		departmentService.addbmjs(array,dwbm,bmmc);
		return "";
	}
	
	
	
	/**
	 * @param dwbm  单位编码
	 * @param bmbm 部门编码
	 * @param ddt 角色信息
	 * @return 更新部门角色信息
	 */
	@RequestMapping("updatebmjs")
	@ResponseBody
	public String updatebmjs(String dwbm,String bmbm,String ddt){
		departmentService.updatebmjs(dwbm,bmbm,ddt);
		return "true";
	}
	/**
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @return 部门角色信息
	 */
	@RequestMapping("selectbmjs")
	@ResponseBody
	public String selectbmjs(String dwbm,String bmbm){
		List<JSBM> jsbms = departmentService.getJs(dwbm,bmbm);
		return Constant.GSON.toJson(jsbms);
	}
	
	@RequestMapping(value = "/getAllBmList", method = RequestMethod.GET)
    @ResponseBody
	public String getAllBmInfo(@RequestParam(value="dwbm",required=true)String dwbm){
		//查询某单位未被删除的所有部门
		List<Department> bmList = departmentService.selectAllBmInfo(dwbm,"N");
		return Constant.GSON.toJson(bmList);
	}
	
	
	@RequestMapping(value = "/getBmInfoByDwBm", method = RequestMethod.GET)
    @ResponseBody
	public String getBmInfoByDwBm(@RequestParam(value="dwbm",required=true)String dwbm,
							      @RequestParam(value="bmbm",required=true)String bmbm){
		List<Department> bmList = departmentService.getBmInfoByDwBm(dwbm,bmbm);
		return Constant.GSON.toJson(bmList);
	}
	
}
