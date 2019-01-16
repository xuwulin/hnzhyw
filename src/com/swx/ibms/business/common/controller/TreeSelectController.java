package com.swx.ibms.business.common.controller;


import com.swx.ibms.business.archives.bean.Person;
import com.swx.ibms.business.common.bean.TreeSelect;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.service.TreeSelectService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.Department;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.rbac.bean.Role;
import com.swx.ibms.common.bean.TreeNode;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 树形结构展示Controller
 * @author 李佳
 * @date: 2017年3月5日
 */
@RequestMapping("/tree")
@Controller
@SuppressWarnings("all")
public class TreeSelectController {


	/**
	 * 树形结构展示接口
	 */
	@Resource
	private TreeSelectService treeSelectService;

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;

	/**
	 * 查询所有检查院编码和名称
	 * @param request 请求参数
	 * @return Map
	 */
	@RequestMapping(value = "/allJcy", method = RequestMethod.GET)
	public @ResponseBody String allJcy(HttpServletRequest request) {
		Map map = null;
		try {
			map = treeSelectService.allJcy();
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, e.toString());
		}
		List<TreeSelect> allJcyList = (List<TreeSelect>) map.get("Y");

		return Constant.GSON.toJson(allJcyList);

	}

	/**
	 * 根据检察院查询下属的所有部门编码和名称
	 * @param request 请求参数
	 * @return Map
	 */
	@RequestMapping(value = "/department", method = RequestMethod.GET)
	public @ResponseBody String department(HttpServletRequest request) {
		String dwbm = "460000"; // 检查院编码
		Map map = null;
		try {
			map = treeSelectService.department(dwbm);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, e.toString());
		}
		List<Department> departmentList = (List<Department>) map.get("Y");
		return Constant.GSON.toJson(departmentList);

	}

	/**
	 * 根据单位、部门查询下属的所有角色编码和名称
	 * @param request 请求参数
	 * @return Map
	 */
	@RequestMapping(value = "/role", method = RequestMethod.GET)
	public @ResponseBody String role(HttpServletRequest request) {
		String dwbm = "370100"; // 单位编码
		String bmbm = "0004"; // 部门编码
		Map map = null;
		try {
			map = treeSelectService.role(dwbm, bmbm);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, e.toString());
		}
		List<Role> roleList = (List<Role>) map.get("Y");
		return Constant.GSON.toJson(roleList);

	}

	/**
	 * 根据角色、单位、部门、查询下属的所有人员的工号和名称
	 * @param request 请求参数
	 * @return Map
	 */
	@RequestMapping(value = "/person", method = RequestMethod.GET)
	public @ResponseBody String person(HttpServletRequest request) {
		String dwbm = "460000"; // 单位编码
		String bmbm = "0005"; // 部门编码
		String jsbm = "008"; // 角色编码
		Map map = null;
		try {
			map = treeSelectService.person(jsbm, dwbm, bmbm);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, e.toString());
		}
		List<Person> personList = (List<Person>) map.get("Y");
		return Constant.GSON.toJson(personList);

	}

	/**
	 * 获取单位的下属单位列表
	 * @param request 请求参数
	 * @return json数据
	 */
	@RequestMapping(value = "/dwtree")
	public @ResponseBody String dwtree(HttpServletRequest request) {
		String rootdwbm = request.getParameter("dwbm");
		String all = request.getParameter("showall");

		boolean isAll = StringUtils.isNotBlank(all) && BooleanUtils.toBooleanObject(all);

		TreeNode dwtreenode = treeSelectService.getTreeNode(rootdwbm, isAll);
		dwtreenode.setOpen("true");
		return Constant.GSON.toJson(dwtreenode);
	}

	/**
	 * 获取某个单位的组织机构列表
	 * @param request 请求参数
	 * @return json数据
	 */
	@RequestMapping(value = "/zzjgtree")
	public @ResponseBody String zzjg(HttpServletRequest request) {

		RYBM ry =(RYBM)request.getSession().getAttribute("ry");
		String rootdwbm = ry.getDwbm();
		String gh = ry.getGh();
		TreeNode zzjgtreenode = treeSelectService.getZzjgTree(rootdwbm, gh);
		return Constant.GSON.toJson(zzjgtreenode);
	}

	/**
	 * 获取某个单位的部门列表
	 * @param request 请求参数
	 * @return json数据
	 */
	@RequestMapping(value = "/bmtree")
	public @ResponseBody String bmtree(HttpServletRequest request) {
		RYBM ry =(RYBM)request.getSession().getAttribute("ry");
		String rootdwbm = ry.getDwbm();
		String dwmc = ry.getDlbm();
		TreeNode bmtreenode = treeSelectService.getBmtree(dwmc,rootdwbm);
		return Constant.GSON.toJson(bmtreenode);
	}

	/**
	 * 生成领导树
	 * @param request 请求
	 * @return 树形结构
	 */
	@RequestMapping("/headertree")
	public @ResponseBody String headerTree(HttpServletRequest request){
		RYBM ry =(RYBM)request.getSession().getAttribute("ry");

		HttpSession session = request.getSession();
		List<String> bmjs=(List<String>)session.getAttribute("bmjs");

		String rootdwbm = ry.getDwbm();
		String gh = ry.getGh();

		TreeNode headerTree = treeSelectService.getTreeOfHeader(rootdwbm,gh,bmjs);

		return Constant.GSON.toJson(headerTree);
	}

	/**
	 * 查询某单位的所有部门并组装成下拉树
	 * @param dwbm 单位编码
	 * @return
	 */
	@RequestMapping(value = "/getBmSelectTree", method = RequestMethod.GET)
	@ResponseBody
	public String getBmbmSelectTree(@RequestParam(value="dwbm",required=false)String dwbm,HttpServletRequest request) {
		RYBM ry =(RYBM)request.getSession().getAttribute("ry");
		String paramDwbm = ry.getDwbm();
		if (StringUtils.isNotBlank(dwbm)&&!"null".equals(dwbm)) {
			paramDwbm = dwbm;
		}
		List<TreeNode> bmSelectTree = treeSelectService.getBmbmSelectTree(paramDwbm);
		return Constant.GSON.toJson(bmSelectTree);
	}

	/**
	 * 查询某单位某人的所在单位->部门->角色->人员并组装成下拉树
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param type //查询类型：（默认）1、 只查询本单位本人本部门的【单位->部门->角色->人员】树  2、查询本单位的【单位->部门->角色->人员】树
	 * @param request
	 * @return 组装好的【单位->部门->角色->人员】树对象
	 */
	@RequestMapping(value = "/getAllRyOfDwBmTree", method = RequestMethod.GET)
	@ResponseBody
	public String getAllRyOfDwBmTree(@RequestParam(value="dwbm",required=false)String dwbm,
									 @RequestParam(value="gh",required=false)String gh,
									 @RequestParam(value="type",required=false)String type,
									 HttpServletRequest request) {
		RYBM ry =(RYBM)request.getSession().getAttribute("ry");
		String paramDwbm = ry.getDwbm();
		String paramGh = ry.getGh();
		String paramType = "1";
		if (StringUtils.isNotBlank(dwbm)&&StringUtils.isNotBlank(gh)) {
			paramDwbm = dwbm;
			paramGh = gh;
		}
		if(StringUtils.isNotBlank(type)) {
			paramType = type;
		}
		List<TreeNode> rySelectTree = treeSelectService.getAllRyOfDwBmTree(paramDwbm,paramGh,paramType);
		return Constant.GSON.toJson(rySelectTree);
	}


	@RequestMapping(value = "/getAllYwAjlbTree", method = RequestMethod.GET)
	@ResponseBody
	public String getAllYwAjlbTree(/*@RequestParam(value="dwbm",required=false)String dwbm,*/
			HttpServletRequest request) {
		List<TreeNode> rySelectTree = treeSelectService.getAllYwAjlbTree();
		return Constant.GSON.toJson(rySelectTree);
	}


	@RequestMapping(value = "/getDwTreeByDwbm", method = RequestMethod.GET)
	@ResponseBody
	public String getAllYwAjlbTree(@RequestParam(value="dwbm",required=false)String dwbm,
								   HttpServletRequest request) {
		String paramDwbm = StringUtils.EMPTY;
		if(StringUtils.isNoneBlank(dwbm)) {
			paramDwbm = dwbm;
		}else {
			RYBM ry =(RYBM)request.getSession().getAttribute("ry");
			paramDwbm = ry.getDwbm();
		}
		List<TreeNode> dwSelectTree = treeSelectService.getDwTreeByDwbm(paramDwbm);
		return Constant.GSON.toJson(dwSelectTree);
	}

	@RequestMapping(value = "/xtBmJsTree", method = RequestMethod.GET)
	@ResponseBody
	public String getAllXtBmJsTree(@RequestParam(value="dwbm",required=false)String dwbm,
								   HttpServletRequest request) {
		String paramDwbm = StringUtils.EMPTY;
		if(StringUtils.isNoneBlank(dwbm)&&!"null".equals(dwbm)) {
			paramDwbm = dwbm;
		}else {
			RYBM ry =(RYBM)request.getSession().getAttribute("ry");
			paramDwbm = ry.getDwbm();
		}
		List<TreeNode> dwSelectTree = treeSelectService.getAllXtBmJsTree(paramDwbm);
		return Constant.GSON.toJson(dwSelectTree);
	}


	@RequestMapping(value = "/getAllXtRyInfo", method = RequestMethod.GET)
	@ResponseBody
	public String getAllXtBmJsTree(@RequestParam(value="dwbm",required=false)String dwbm,
								   @RequestParam(value="bmbm",required=false)String bmbm,
								   @RequestParam(value="jsbm",required=false)String jsbm,
								   @RequestParam(value="dlbm",required=false)String dlbm,
								   @RequestParam(value="page",required=false)Integer page,
								   @RequestParam(value="rows",required=false)Integer rows,
								   HttpServletRequest request) {
		String paramDwbm = StringUtils.EMPTY;
		if(StringUtils.isNoneBlank(dwbm)&&!"null".equals(dwbm)) {
			paramDwbm = dwbm;
		}else {
			RYBM ry =(RYBM)request.getSession().getAttribute("ry");
			paramDwbm = ry.getDwbm();
		}
		Map<String,Object> resMap = treeSelectService.getAllXtRyInfo(paramDwbm,bmbm,jsbm,dlbm,page,rows);
		return Constant.GSON.toJson(resMap);
	}

	@RequestMapping(value = "/xtBmJsCombobox", method = RequestMethod.POST)
	@ResponseBody
	public String getXtBmJsCombobox(@RequestParam(value="dwbm",required=false)String dwbm,
									@RequestParam(value="bmbm",required=false)String bmbm,
									HttpServletRequest request) {
		String paramDwbm = StringUtils.EMPTY;
		String paramBmbm = StringUtils.EMPTY;
		RYBM ry =(RYBM)request.getSession().getAttribute("ry");
		paramDwbm = ry.getDwbm();
		paramBmbm = ry.getBmbm();
		if(StringUtils.isNotBlank(dwbm)&&!"null".equals(dwbm)&&null!=dwbm) {
			paramDwbm = dwbm;
		}
		if(StringUtils.isNotBlank(bmbm)&&!"null".equals(bmbm)&&null!=bmbm){
			paramBmbm = bmbm;
		}
		List<Map<String,Object>> jsCombobox = treeSelectService.getAllXtBmJs(paramDwbm,paramBmbm);
		return Constant.GSON.toJson(jsCombobox);
	}

	/**
	 * 查询某人所办案件，并组装成下拉树
	 * @param dwbm 单位编码
	 * @return
	 */
	@RequestMapping(value = "/getAjSelectTree", method = RequestMethod.GET)
	@ResponseBody
	public String getAjSelectTree(@RequestParam(value="dwbm",required=true)String dwbm,
								  @RequestParam(value="gh",required=true)String gh,
								  @RequestParam(value="kssj",required=true)String kssj,
								  @RequestParam(value="jssj",required=true)String jssj,
								  @RequestParam(value="bmlbbm",required=true)String bmlbbm,
								  @RequestParam(value="rylx",required=true)String rylx,
								  HttpServletRequest request) {

		List<TreeNode> bmSelectTree = treeSelectService.getAjSelectTree(dwbm, gh, kssj, jssj, bmlbbm, rylx);
		return Constant.GSON.toJson(bmSelectTree);
	}

}
