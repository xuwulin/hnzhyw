package com.swx.ibms.business.system.controller;

import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.rbac.bean.RYJSFP;
import com.swx.ibms.business.system.service.XtpzRybmService;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * @author zsq
 *系统配置_人员管理
 */
@Controller
@RequestMapping("/rybm")
public class XtpzRybmController {
	
	/**
	 * 
	 */
	@Resource
	private XtpzRybmService xtpzRybmService;
	
	/**
	 * @param request
	 * request
	 * @return null
	 */
	@RequestMapping("addrybm")
	@ResponseBody
	public String addRy(HttpServletRequest request){
		HttpSession session = request.getSession();
		RYBM rybm = (RYBM) session.getAttribute("ry");
		String bmbm= request.getParameter("bmbm");
		String jsbm= request.getParameter("jsbm");
		String oldbmbm= request.getParameter("oldbmbm");
		String oldjsbm= request.getParameter("oldjsbm");
		String mc= request.getParameter("mc");
		String gh= request.getParameter("gh");
		String gzzh= request.getParameter("gzzh");
		String dlbm= request.getParameter("dlbm");
		String kl= request.getParameter("kl");
		RYJSFP ryjsfp = new RYJSFP();
		ryjsfp.setBmbm(bmbm);
		ryjsfp.setJsbm(jsbm);
		ryjsfp.setDwbm(rybm.getDwbm());
		ryjsfp.setGh(gh);
		RYBM ry = new RYBM();
		ry.setMc(mc);
		ry.setGh(gh);
		ry.setGh(gzzh);
		ry.setDlbm(dlbm);
		ry.setKl(kl);
		ry.setDwbm(rybm.getDwbm());
		xtpzRybmService.insertRy(ry,ryjsfp,oldbmbm,oldjsbm);
		return "";
	}
	
	
	/**
	 * @param dlbm
	 * 登录别名
	 * @param request
	 * request
	 * @return
	 * 判断登录别名是否存在
	 */
	@RequestMapping(value="checkdlbm",method=RequestMethod.POST)
	@ResponseBody
	public String checkDlbm(String dlbm,HttpServletRequest request){
		HttpSession session = request.getSession();
		RYBM rybm = (RYBM) session.getAttribute("ry");
		int i = xtpzRybmService.checkDlbm(rybm.getDwbm(),dlbm);
		if(i>0){
			return "true";
		}
		return "false";
	}
	
	/**
	 * @param dwbm
	 * 单位编码
	 * @param bmbm
	 * 部门编码
	 * @param jsbm
	 * 角色编码
	 * @param gh
	 * 工号
	 * @return
	 * 删除人员
	 */
	@RequestMapping(value="deletery")
	@ResponseBody
	public String deletery(@RequestParam(value="dwbm",required=true)String dwbm,
						   @RequestParam(value="bmbm",required=true)String bmbm,
						   @RequestParam(value="jsbm",required=true)String jsbm,
						   @RequestParam(value="gh",required=true)String gh){
		xtpzRybmService.deletery(dwbm,bmbm,jsbm,gh);
		return "true";
	}
	
	/**
	 * @param dwbm  单位编码
	 * @param gh  工号
	 * @param kl 密码
	 * @return message
	 */
	@ResponseBody
	@RequestMapping("updatepassword")
	public String updatepassword(String dwbm,String gh,String kl){
		kl = DigestUtils.md5DigestAsHex(kl.getBytes());
		xtpzRybmService.updatepassword(dwbm,gh,kl);
		return "success";
	}
	
	/** 根据单位编码和工号查询这个人是不是统一业务数据库的人，如果不是 可以进行密码修改
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @return 是不是综合业务的人
	 */
	@RequestMapping("canupdate")
	@ResponseBody
	public String canupdate(String dwbm,String gh){
		Map<String, Object> map = new HashMap<>();
		String msg = xtpzRybmService.canupdate(dwbm,gh);
		map.put("msg", msg);
		return Constant.GSON.toJson(map);
	}
	
	
	

}
