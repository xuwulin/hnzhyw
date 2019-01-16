package com.swx.ibms.business.system.controller;


import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.*;
import com.swx.ibms.business.system.service.XTGLService;
import com.swx.ibms.common.utils.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 系统管理控制器
 * @author 李治鑫
 *
 */
@RequestMapping("/xtgl")
@Controller
public class XTGLController {

	/**
	 * 日志对象
	 * */
	private static final Logger LOG = Logger.getLogger(XTGLController.class);

	/**
	 * 系统管理服务接口
	 * */
	@Resource
	private XTGLService xtglService;


	/**
	 * 获取组织机构中的单位、部门、角色、人员列表
	 * @return  组织机构集合
	 */
	@RequestMapping(value="/zzjg",method=RequestMethod.POST)
	public @ResponseBody String getZzjg(){
		/**
		 * 获取所有单位
		 * */
		List<DWBM> dwbmList=xtglService.getAllDw();//单位编码列表
		List<BMBM> bmbmList=new ArrayList<BMBM>();//部门编码列表
		List<JSBM> jsbmList=new ArrayList<JSBM>();//角色编码列表
		List<RYBM> rybmList=new ArrayList<RYBM>();//人员编码列表

		Map<String,Object> resultMap = new HashMap<String,Object>();



		/**
		 * 获取所有部门
		 **/
		for(int i=0,l=dwbmList.size();i<l;i++){
			try {
				bmbmList.addAll(xtglService.getBmByDwbm(dwbmList.get(i).getDwbm()));
			} catch (Exception e) {
				LOG.error(StringUtils.EMPTY, e);
			}
		}



		/**
		 * 获取所有角色
		 * */
		for(int i=0,l=bmbmList.size();i<l;i++){
			try {
				jsbmList.addAll(xtglService.getJsBybmbm(
						bmbmList.get(i).getDwbm(), bmbmList.get(i).getBmbm()));
			} catch (Exception e) {
				LOG.error(StringUtils.EMPTY, e);
			}
		}



		/**
		 * 获取所有人员
		 * */
		for(int i=0,l=jsbmList.size();i<l;i++){
			try {
				rybmList.addAll(xtglService.getRyByBmbm(jsbmList.get(i).getDwbm(),
						jsbmList.get(i).getBmbm(), jsbmList.get(i).getJsbm()));
			} catch (Exception e) {
				LOG.error(StringUtils.EMPTY, e);
			}
		}

		resultMap.put("dwbm", dwbmList);
		resultMap.put("bmbm", bmbmList);
		resultMap.put("jsbm", jsbmList);
		resultMap.put("rybm", rybmList);

		return Constant.GSON.toJson(resultMap);
	}

	//以下是操作单位的相关代码
	//
	//


	/**
	 * 获取单位详细信息
	 * @param request 请求参数
	 * @return 一个单位信息集合
	 */
	@RequestMapping(value="/getdw",method=RequestMethod.POST)
	public @ResponseBody String getDw(HttpServletRequest request){

		String dwbm=request.getParameter("dwbm");

		List<DWBM> dw = null;
		try {
			dw = xtglService.getDw(dwbm);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		return Constant.GSON.toJson(dw);
	}

	/**
	 * 新增单位
	 * @param request 请求参数
	 * @return 插入信息
	 */
	@RequestMapping(value="/adddw",method=RequestMethod.POST)
	public @ResponseBody String addDw(HttpServletRequest request){

		String dwbm=request.getParameter("dwbm");//单位编码
		String dwmc=request.getParameter("dwmc");//单位名称
		String fdwbm=request.getParameter("fdwbm");//父单位编码
		String dwjb=request.getParameter("dwjb");//单位级别
		String sfsc=request.getParameter("sfsc");//是否删除
		String dwjc=request.getParameter("dwjc");//单位简称
		String dwsx=request.getParameter("dwsx");//单位标识

		DWBM dwbmObject = new DWBM();
		try {
			dwbmObject = xtglService.createDw(dwbm, dwmc,
					fdwbm, dwjb, sfsc, dwjc, dwsx);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		int msg = 0;
		try {
			msg = xtglService.addDw(dwbmObject);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		if(msg>0){
			return "添加成功";
		}else{
			return "添加失败";
		}


	}

	/**
	 * 更新单位信息
	 * @param request 请求参数
	 * @return 更新信息
	 */
	@RequestMapping(value="/updatedw",method=RequestMethod.POST)
	public @ResponseBody String updateDw(HttpServletRequest request){

		String dwbm=request.getParameter("dwbm");//单位编码
		String dwmc=request.getParameter("dwmc");//单位名称
		String fdwbm=request.getParameter("fdwbm");//父单位编码
		String dwjb=request.getParameter("dwjb");//单位级别
		String sfsc=request.getParameter("sfsc");//是否删除
		String dwjc=request.getParameter("dwjc");//单位简称
		String dwsx=request.getParameter("dwsx");//单位标识

		DWBM dwbmObject = new DWBM();
		try {
			dwbmObject = xtglService.createDw(dwbm,
					dwmc, fdwbm, dwjb, sfsc, dwjc, dwsx);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		int msg=0;
		try {
			msg = xtglService.updateDw(dwbmObject);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		if(msg>0){
			return "更新成功";
		}else{
			return "更新失败";
		}


	}

	/**
	 * 删除单位
	 * @param request 请求参数
	 * @return 删除信息
	 */
	@RequestMapping(value="/deletedw",method=RequestMethod.POST)
	public @ResponseBody String deleteDw(HttpServletRequest request){
		String dwbm=request.getParameter("dwbm");

		int msg = 0;
		try {
			msg = xtglService.deleteDw(dwbm);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		if(msg>0){
			return "删除成功";
		}else{
			return "删除失败";
		}


	}



	//以下是操作部门的相关代码
	//
	//

	/**
	 * 获取部门详细信息
	 * @param request 请求参数
	 * @return 一个部门信息集合
	 */
	@RequestMapping(value="/getbm",method=RequestMethod.POST)
	public @ResponseBody String getBm(HttpServletRequest request){

		String dwbm=request.getParameter("dwbm");
		String bmbm=request.getParameter("bmbm");

		List<BMBM> bm = null;
		try {
			bm = xtglService.getBm(dwbm, bmbm);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		return Constant.GSON.toJson(bm);
	}

	/**
	 * 新增部门
	 * @param request 请求参数
	 * @return 插入信息
	 */
	@RequestMapping(value="/addbm",method=RequestMethod.POST)
	public @ResponseBody String addBm(HttpServletRequest request){

		String dwbm=request.getParameter("dwbm");//单位编码
		String bmbm=request.getParameter("bmbm");//部门编码
		String fbmbm=request.getParameter("fbmbm");//父部门编码
		String bmmc=request.getParameter("bmmc");//部门名称
		String bmjc=request.getParameter("bmjc");//部门简称
		String bmwhjc=request.getParameter("bmwhjc");//部门文号简称
		String bmahjc=request.getParameter("bmahjc");//部门案号简称
		String sflsjg=request.getParameter("sflsjg");//是否临时机构
		String sfcbbm=request.getParameter("sfcbbm");//是否承办部门
		int bmxh=Integer.parseInt(request.getParameter("bmxh"));//部门序号
		String bz=request.getParameter("bz");//备注
		String sfsc=request.getParameter("sfsc");//是否删除
		String bmys=request.getParameter("bmys");//部门映射

		BMBM bmbmObject = new BMBM();
		try {
			bmbmObject = xtglService.createBm(dwbm, bmbm, fbmbm, bmmc,
					bmjc, bmwhjc, bmahjc, sflsjg, sfcbbm, bmxh, bz, sfsc, bmys);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		int msg = 0;
		try {
			msg = xtglService.addBm(bmbmObject);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		if(msg>0){
			return "添加成功";
		}else{
			return "添加失败";
		}


	}

	/**
	 * 更新部门信息
	 * @param request 请求参数
	 * @return 更新信息
	 */
	@RequestMapping(value="/updatebm",method=RequestMethod.POST)
	public @ResponseBody String updateBm(HttpServletRequest request){

		String dwbm=request.getParameter("dwbm");//单位编码
		String bmbm=request.getParameter("bmbm");//部门编码
		String fbmbm=request.getParameter("fbmbm");//父部门编码
		String bmmc=request.getParameter("bmmc");//部门名称
		String bmjc=request.getParameter("bmjc");//部门简称
		String bmwhjc=request.getParameter("bmwhjc");//部门文号简称
		String bmahjc=request.getParameter("bmahjc");//部门案号简称
		String sflsjg=request.getParameter("sflsjg");//是否临时机构
		String sfcbbm=request.getParameter("sfcbbm");//是否承办部门
		int bmxh=Integer.parseInt(request.getParameter("bmxh"));//部门序号
		String bz=request.getParameter("bz");//备注
		String sfsc=request.getParameter("sfsc");//是否删除
		String bmys=request.getParameter("bmys");//部门映射

		BMBM bmbmObject = new BMBM();
		try {
			bmbmObject = xtglService.createBm(dwbm, bmbm, fbmbm, bmmc,
					bmjc, bmwhjc, bmahjc, sflsjg, sfcbbm, bmxh, bz, sfsc, bmys);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		int msg = 0;
		try {
			msg = xtglService.updateBm(bmbmObject);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		if(msg>0){
			return "更新成功";
		}else{
			return "更新失败";
		}


	}

	/**
	 * 删除部门
	 * @param request 请求参数
	 * @return 删除信息
	 */
	@RequestMapping(value="/deletebm",method=RequestMethod.POST)
	public @ResponseBody String deleteBm(HttpServletRequest request){

		String dwbm=request.getParameter("dwbm");
		String bmbm=request.getParameter("bmbm");

		int msg = 0;
		try {
			msg = xtglService.deleteBm(dwbm, bmbm);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		if(msg>0){
			return "删除成功";
		}else{
			return "删除失败";
		}


	}


	//以下是操作人员的相关代码
	//
	//

	/**
	 * 获取人员详细信息
	 * @param request 请求参数
	 * @return 一个人员信息集合
	 */
	@RequestMapping(value="/getry",method=RequestMethod.GET)
	public @ResponseBody String getRy(HttpServletRequest request){

		String dwbm=request.getParameter("dwbm");
		String name = request.getParameter("name");
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));

		Map<String, Object> map = new HashMap<>();
		try {
			map = xtglService.getRy(dwbm, name, page, rows);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
			e.printStackTrace();
		}
		return Constant.GSON.toJson(map);
	}

	/**
	 * 新增人员
	 * @param request 请求参数
	 * @return 插入信息
	 */
	@RequestMapping(value="/addry",method=RequestMethod.POST)
	public @ResponseBody String addRy(HttpServletRequest request){

		//单位编码
		String dwbm = request.getParameter("dwbm");
		// 部门编码
		String bmbm = request.getParameter("bmbm");
		// 角色编码
		String jsbm = request.getParameter("jsbm");
		//登录别名
		String dlbm = request.getParameter("dlbm");
		//口令
		String kl = request.getParameter("kl");
		//名称
		String mc = request.getParameter("mc");
		//工作证号
		String gzzh = request.getParameter("gzzh");
		//移动电话号码
		String yddhhm="";
		//电子邮件
		String dzyj="";
		//原单位编码
		String ydwbm="";
		//原单位名称
		String ydwmc="";
		//是否临时人员
		String sflsry = "N";
		//是否删除
		String sfsc = "N";
		//是否停职
		String sftz = "N";
		//性别 0:女 1:男
		String xb = request.getParameter("xb");
		//CA证号
		String caid = "";
		//主任检察官工号
		String zrjcggh = "";
		//工号
		String gh = request.getParameter("gh");

		RYBM rybmObject = new RYBM();
		try {
			rybmObject = xtglService.createRy(dwbm,bmbm,jsbm, gh, dlbm, kl, mc,
					gzzh, yddhhm, dzyj, ydwbm, ydwmc, sflsry, sfsc, sftz,
					xb, caid, zrjcggh);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(StringUtils.EMPTY, e);
		}
		int msg = 0;
		try {
			// 添加人员
			msg = xtglService.addRy(rybmObject);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(StringUtils.EMPTY, e);
		}

		String data = "";

		if(msg>0){
			data = "success";
		}else{
			data = "error";
		}
		return Constant.GSON.toJson(data);

	}

	/**
	 * 更新人员信息
	 * @param request 请求参数
	 * @return 更新信息
	 */
	@RequestMapping(value="/updatery",method=RequestMethod.POST)
	@ResponseBody
	public String updateRy(HttpServletRequest request){

		String dwbm=request.getParameter("dwbm");//单位编码
		String bmbm=request.getParameter("bmbm");//单位编码
		String jsbm=request.getParameter("jsbm");//单位编码
		String gh=request.getParameter("gh");//工号
		String oldGh=request.getParameter("oldGh");//旧工号
		String dlbm=request.getParameter("dlbm");//登录别名
		String kl=request.getParameter("kl");//口令
		String mc=request.getParameter("mc");//名称
		String gzzh=request.getParameter("gzzh");//工作证号
		String yddhhm="";//移动电话号码
		String dzyj="";//电子邮件
		String ydwbm="";//原单位编码
		String ydwmc="";//原单位名称
		String sflsry="N";//是否临时人员
		String sfsc="N";//是否删除
		String sftz="N";//是否停职
		String xb=request.getParameter("xb");//性别 0:女 1:男
		String caid="";//CA证号
		String zrjcggh="";//主任检察官工号

		RYBM rybmObject = new RYBM();
		try {
			rybmObject = xtglService.createRy(dwbm,bmbm,jsbm, gh, dlbm, kl, mc,
					gzzh, yddhhm, dzyj, ydwbm, ydwmc, sflsry, sfsc, sftz,
					xb, caid, zrjcggh);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		int msg = 0;
		try {
			msg = xtglService.updateRy(rybmObject, oldGh);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		String data = "";
		if(msg>0){
			data = "success";
		}else{
			data = "error";
		}
		return Constant.GSON.toJson(data);

	}

	/**
	 * 删除人员
	 * @param request 请求参数
	 * @return 删除信息
	 */
	@RequestMapping(value="/deletery",method=RequestMethod.POST)
	public @ResponseBody String deleteRy(HttpServletRequest request){

		String dwbm=request.getParameter("dwbm");
		String bmbm=request.getParameter("bmbm");
		String jsbm=request.getParameter("jsbm");
		String gh=request.getParameter("gh");

		int msg = 0;
		try {
			msg = xtglService.deleteRy(dwbm, bmbm, jsbm, gh);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		if(msg>0){
			return "删除成功";
		}else{
			return "删除失败";
		}


	}

	//以下是操作角色的相关代码
	//
	//

	/**
	 * 获取角色详细信息
	 * @param request 请求参数
	 * @return 一个角色信息集合
	 */
	@RequestMapping(value="/getjs",method=RequestMethod.POST)
	public @ResponseBody String getJs(HttpServletRequest request){

		String dwbm=request.getParameter("dwbm");
		String bmbm=request.getParameter("bmbm");
		String jsbm=request.getParameter("jsbm");

		List<JSBM> js = null;
		try {
			js = xtglService.getJs(dwbm, bmbm, jsbm);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		return Constant.GSON.toJson(js);
	}

	/**
	 * 新增角色
	 * @param request 请求参数
	 * @return 插入信息
	 */
	@RequestMapping(value="/addjs",method=RequestMethod.POST)
	@ResponseBody
	public String addJs(HttpServletRequest request){

		String dwbm = request.getParameter("dwbm");//单位编码
		String jsbm = request.getParameter("jsbm");//角色编号
		String jsmc = StrUtil.strTransform(request.getParameter("jsmc"));//角色名称
		String bmbm = request.getParameter("bmbm");//部门编码
		int jsxh = Integer.parseInt(request.getParameter("jsxh"));//角色序号
		String spjsbm = request.getParameter("spjsbm");//审批角色编码
		String description = StrUtil.strTransform(request.getParameter("description"));//角色描述

		JSBM jsbmObject = new JSBM();
		try {
			jsbmObject = xtglService.createJs(dwbm, jsbm, jsmc, bmbm,jsxh, spjsbm, description);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(StringUtils.EMPTY, e);
		}
		int msg = 0;
		try {
			msg = xtglService.addJs(jsbmObject);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(StringUtils.EMPTY, e);
		}

		String data = "";
		if(msg>0){
			data = "success";
		}else{
			data = "error";
		}
		return Constant.GSON.toJson(data);
	}

	/**
	 * 更新角色信息
	 * @param request 请求参数
	 * @return 更新信息
	 */
	@RequestMapping(value="/updatejs",method=RequestMethod.POST)
	@ResponseBody
	public String updateJs(HttpServletRequest request){

		String dwbm = request.getParameter("dwbm");//单位编码
		String jsbm = request.getParameter("jsbm");//角色编号
		String jsmc = StrUtil.strTransform(request.getParameter("jsmc"));//角色名称
		String bmbm = request.getParameter("bmbm");//部门编码
		int jsxh = Integer.parseInt(request.getParameter("jsxh"));//角色序号
		String spjsbm = request.getParameter("spjsbm");//审批角色编码
		String description = StrUtil.strTransform(request.getParameter("description"));//角色描述

		JSBM jsbmObject = new JSBM();
		try {
			jsbmObject = xtglService.createJs(dwbm, jsbm, jsmc, bmbm, jsxh, spjsbm, description);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		int msg = 0;
		try {
			msg = xtglService.updateJs(jsbmObject);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(StringUtils.EMPTY, e);
		}

		String data = "";

		if(msg>0){
			data = "success";
		}else{
			data = "error";
		}
		return Constant.GSON.toJson(data);
	}

	/**
	 * 删除角色
	 * @param request 请求参数
	 * @return 删除信息
	 */
	@RequestMapping(value="/deletejs",method=RequestMethod.POST)
	public @ResponseBody String deleteJs(HttpServletRequest request){

		String dwbm=request.getParameter("dwbm");
		String bmbm=request.getParameter("bmbm");
		String jsbm=request.getParameter("jsbm");

		int msg = 0;
		try {
			msg = xtglService.deleteJs(dwbm, bmbm, jsbm);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		String data = "";

		if(msg>0){
			data = "success";
		}else{
			data = "error";
		}
		return data;
	}


	//以下是操作人员角色分配的相关代码
	//
	//

	/**
	 * 获取分配详细信息
	 * @param request 请求参数
	 * @return 一个分配信息集合
	 */
	@RequestMapping(value="/getfp",method=RequestMethod.POST)
	public @ResponseBody String getFp(HttpServletRequest request){

		String dwbm=request.getParameter("dwbm");//单位编码
		String bmbm=request.getParameter("bmbm");//部门编码
		String jsbm=request.getParameter("jsbm");//角色编码
		String gh = request.getParameter("gh");//工号

		List<RYJSFP> ryjsfp = null;
		try {
			ryjsfp = xtglService.getFp(dwbm, bmbm, jsbm, gh);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		return Constant.GSON.toJson(ryjsfp);
	}

	/**
	 * 新增分配
	 * @param request 请求参数
	 * @return 插入信息
	 */
	@RequestMapping(value="/addfp",method=RequestMethod.POST)
	public @ResponseBody String addFp(HttpServletRequest request){

		String dwbm=request.getParameter("dwbm");//单位编码
		String bmbm=request.getParameter("bmbm");//部门编码
		String jsbm=request.getParameter("jsbm");//角色编码
		String gh = request.getParameter("gh");//工号
		String zjldgh=request.getParameter("zjldgh");//直接领导工号
		int ryxh = Integer.parseInt(request.getParameter("ryxh"));//人员序号

		RYJSFP ryjsfpObject = new RYJSFP();
		try {
			ryjsfpObject = xtglService.createFp(dwbm, bmbm,
					jsbm, gh, zjldgh, ryxh);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		int msg = 0;
		try {
			msg = xtglService.addFp(ryjsfpObject);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		if(msg>0){
			return "添加成功";
		}else{
			return "添加失败";
		}

	}

	/**
	 * 更新分配信息
	 * @param request 请求参数
	 * @return 更新信息
	 */
	@RequestMapping(value="/updatefp",method=RequestMethod.POST)
	public @ResponseBody String updateFp(HttpServletRequest request){

		String dwbm=request.getParameter("dwbm");//单位编码
		String bmbm=request.getParameter("bmbm");//部门编码
		String jsbm=request.getParameter("jsbm");//角色编码
		String gh = request.getParameter("gh");//工号
		String zjldgh=request.getParameter("zjldgh");//直接领导工号
		int ryxh = Integer.parseInt(request.getParameter("ryxh"));//人员序号

		RYJSFP ryjsfpObject = new RYJSFP();
		try {
			ryjsfpObject = xtglService.createFp(dwbm, bmbm, jsbm,
					gh, zjldgh, ryxh);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		int msg = 0;
		try {
			msg = xtglService.updateFp(ryjsfpObject);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		if(msg>0){
			return "更新成功";
		}else{
			return "更新失败";
		}


	}

	/**
	 * 删除分配
	 * @param request 请求参数
	 * @return 删除信息
	 */
	@RequestMapping(value="/deletefp",method=RequestMethod.POST)
	public @ResponseBody String deleteFp(HttpServletRequest request){

		String dwbm=request.getParameter("dwbm");//单位编码
		String bmbm=request.getParameter("bmbm");//部门编码
		String jsbm=request.getParameter("jsbm");//角色编码
		String gh = request.getParameter("gh");//工号

		int msg = 0;
		try {
			msg = xtglService.deleteFp(dwbm, bmbm, jsbm, gh);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		if(msg>0){
			return "删除成功";
		}else{
			return "删除失败";
		}


	}

	/**
	 * 通过工号获取名称
	 * @param request 请求参数
	 * @return 名称
	 */
	@RequestMapping(value="/getmc")
	public @ResponseBody String getMcByGh(HttpServletRequest request){
		String ssrdwbm = request.getParameter("ssrdwbm");
		String ssrgh = request.getParameter("ssrgh");
		String mc = "";
		try {
			mc=xtglService.getMcByGh(ssrdwbm, ssrgh);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		Map<String ,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("mc", mc);
		return Constant.GSON.toJson(resultMap);
	}

	/**
	 * 根据单位编码获取角色信息
	 * @return
	 */
	@RequestMapping(value="/getJsByDwbm",method=RequestMethod.GET)
	@ResponseBody
	public String getJsByDwbm(HttpServletRequest request) {
		String dwbm = request.getParameter("dwbm");
		Integer page = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		Map<String, Object> jsInfo = new HashMap<>();
		try {
			jsInfo = xtglService.getJsByDwbm(dwbm, page, rows);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(StringUtils.EMPTY, e);
		}
		return Constant.GSON.toJson(jsInfo);
	}

	/**
	 * 根据单位编码获取角色信息
	 * @return
	 */
	@RequestMapping(value="/suspension",method=RequestMethod.POST)
	@ResponseBody
	public String suspension(HttpServletRequest request) {
		String dwbm = request.getParameter("dwbm");
		String gh = request.getParameter("gh");
		String res = "";
		int data = 0;
		try {
			data = xtglService.suspension(dwbm, gh);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (data > 0) {
			res = "success";
		} else {
			res = "error";
		}
		return res;
	}

	/**
	 * 登录别名验证，不能重复
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/aliasVerify",method=RequestMethod.GET)
	@ResponseBody
	public String aliasVerify(HttpServletRequest request) {
		String dlbm = request.getParameter("dlbm");
		String dwbm = request.getParameter("dwbm");
		String res = "";
		int data = 0;
		try {
			data = xtglService.aliasVerify(dlbm, dwbm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (data > 0) {
			res = "existed";
		} else {
			res = "available";
		}
		return Constant.GSON.toJson(res);
	}

	/**
	 * 部门名称验证，不能重复
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/deptNameVerify",method=RequestMethod.GET)
	@ResponseBody
	public String deptNameVerify(HttpServletRequest request) {
		String bmmc = request.getParameter("bmmc");
		String dwbm = request.getParameter("dwbm");
		String res = "";
		int data = 0;
		try {
			data = xtglService.deptNameVerify(bmmc, dwbm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (data > 0) {
			res = "existed";
		} else {
			res = "available";
		}
		return Constant.GSON.toJson(res);
	}

}
