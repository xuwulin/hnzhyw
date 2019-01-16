package com.swx.ibms.business.rbac.controller;

import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.JSBM;
import com.swx.ibms.business.rbac.service.RoleService;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.collections.CollectionUtils;
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
 * 
 * @author east
 * @date:2017年4月14日
 * @version:1.0
 * @description:角色配置controller：实现对角色的CRUD；
 * 几个重要的参数：单位编码DWBM、单位名称DWMC、角色名称、角色编码、角色描述。
 *
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/role")
public class RoleController {
	/**
	 * resource service接口
	 */
	@Resource
	private RoleService roleService;
	
	
	/**
	 * 根据单位编码查询此单位下的所有角色信息
	 * @param req 请求对象
	 * @return 结果集字符串
	 */
	@RequestMapping(value = "/selectPageList", method = RequestMethod.POST)
	@ResponseBody
	public String getRoleList(HttpServletRequest req){
		Map<String,Object> map = new HashMap<String,Object>();
		String dwbm = req.getParameter("dwbm");// 单位编码
		String sfsc = req.getParameter("sfsc");// 单位编码
		int nowPage = Integer.parseInt(req.getParameter("page"));//当前页
		int pageSize = Integer.parseInt(req.getParameter("rows"));//每页显示数
		if (!"".equals(dwbm)&&!"".equals(sfsc)) {
			PageCommon<JSBM> pageCommon = roleService.selectPageList(dwbm,sfsc,
					nowPage,pageSize);
			if (null!=pageCommon) {
				map.put("total", pageCommon.getTotalRecords());
				map.put("rows", pageCommon.getList());
			}
		}
		return Constant.GSON.toJson(map);
	}
	
	/**
	 * 根据单位编码、部门编码、是否删除的标示（'Y'已删除 'N'未删除[一个字符且全大写]）查询未删除的角色信息
	 * @param jsbm 角色实体类
	 * @return 是否成功的字符串
	 */
	@RequestMapping(value = "/selectListByBmbm", method = RequestMethod.GET)
	@ResponseBody
	public String getRoleListByBmbm(JSBM jsbm){
		if (!"".equals(jsbm.getBmbm())&&!"".equals(jsbm.getDwbm())&&!"".equals(jsbm.getSfsc())) {
			
			List<JSBM> list = roleService.selectZhywRoleList(
					jsbm.getDwbm(),jsbm.getBmbm(),jsbm.getSfsc());
			if (!CollectionUtils.isEmpty(list)) {
				return Constant.GSON.toJson(list);
			}
		}
		return null;
	}
	
	
	/**
	 * 添加某单位下的角色信息
	 * @param jsbm 角色对象
	 * @return 是否成功的标示
	 */
	@RequestMapping(value = "/addData", method = RequestMethod.GET)
	@ResponseBody
	public String addRoleInfo(JSBM jsbm){
		Map<String,Object> map = new HashMap<String,Object>();
		if (null!=jsbm) {
			String msg = roleService.insertData(jsbm);
			if (msg!=null&&!"".equals(msg)) {
				map.put("msg", msg);
			}
		}
		return Constant.GSON.toJson(map);
	}
	
	
	/**
	 * 根据单位编码、部门编码、角色编码修改角色信息
	 * @param jsbm 角色对象
	 * @return 是否成功的字符串
	 */
	@RequestMapping(value = "/updateData", method = RequestMethod.POST)
	@ResponseBody
	public String updateRoleInfo(JSBM jsbm){
		Map<String,Object> map = new HashMap<String,Object>();
		if (null!=jsbm) {
			String msg = roleService.updateData(jsbm);
			if (msg!=null&&!"".equals(msg)) {
				map.put("msg", msg);
			}
		}
		return Constant.GSON.toJson(map);
	}
	
	/**
	 * 根据单位编码、部门编码、角色编码、是否删除的标示进行假删除
	 * @param jsbm 角色对象
	 * @return 是否成功的字符串 
	 */
	@RequestMapping(value = "/deleteData", method = RequestMethod.POST)
	@ResponseBody
	public String deleteRoleInfo(JSBM jsbm){
		Map<String,Object> map = new HashMap<String,Object>();
		if (null!=jsbm) {
			String msg = roleService.deleteData(jsbm);
			if (msg!=null&&!"".equals(msg)) {
				map.put("msg", msg);
			}
		}
		return Constant.GSON.toJson(map);
	}
}
