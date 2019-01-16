package com.swx.ibms.business.rbac.controller;


import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.performance.controller.GrjxController;
import com.swx.ibms.business.performance.service.HCPZService;
import com.swx.ibms.business.rbac.bean.Bmmc;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.rbac.service.BmmcService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author 王宇锋
 *
 */
@RequestMapping("/sy")
@Controller
@SuppressWarnings("all")
public class BmmcController {
	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(GrjxController.class);
	/**
	 * 部门名称服务层接口
	 */
	@Resource
	private BmmcService bmmcService;
	/**
	 * 核查配置服务层接口
	 */
	@Resource
	private HCPZService hCPZService;
	/**
	 * @param request request
	 * @return 返回个人绩效信息
	 */
	@RequestMapping(value="/grxx",method=RequestMethod.POST)
	@ResponseBody
	public String getGrxx(HttpServletRequest request){
		HttpSession session=request.getSession();
		RYBM ry=null;
		List<String> bmys=null;
		List<String> ryjs=null;
		try {
			ry=(RYBM)session.getAttribute("ry");
			bmys=(List<String>)session.getAttribute("bmys");
			ryjs=(List<String>)session.getAttribute("ryjs");
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		ry.setKl(StringUtils.EMPTY);
		ry.setGzzh(StringUtils.EMPTY);
		ry.setSfsc(StringUtils.EMPTY);
		ry.setSftz(StringUtils.EMPTY);

		Map<String,Object> returnMap=new HashMap<String, Object>();
		returnMap.put("bmys",bmys);
		//通过单位编码找到单位级别
		String dwjb=(String) hCPZService.getdwjb(ry.getDwbm()).get("p_dwjb");
		returnMap.put("dwjb",dwjb);
		//获得人员名称
		String mc=ry.getMc();
		returnMap.put("mc",mc);


		//获得单位名称
		String dwbm=ry.getDwbm();
		String dwmc=bmmcService.getDwmc(dwbm);
		returnMap.put("dwmc",dwmc);

		//获得部门名称
		String gh=ry.getGh();
		List<Bmmc> list=bmmcService.getBmmc(dwbm, gh);
		if(!CollectionUtils.isEmpty(list)){
			returnMap.put("bmmc",list.get(0).getBmmc());
			returnMap.put("bmbm",list.get(0).getBmbm());
			ry.setBmbm(list.get(0).getBmbm());
		}
		returnMap.put("ryjs", ryjs);
		if(bmys.contains("1100")){
			returnMap.put("isag","1");
		}else{
			returnMap.put("isag","0");
		}

		//放入人员信息
		returnMap.put("ry", ry);

		return Constant.GSON.toJson(returnMap);
	}

	/**
	 * 返回绩效信息,（废弃）
	 * @param request request
	 * @return 绩效信息
	 */
	@RequestMapping(value="/jxxx",method=RequestMethod.POST)
	@ResponseBody
	public String getJxxx(HttpServletRequest request){
		HttpSession session=request.getSession();
		Map<String,Object> resultMap=new HashMap<>();
		RYBM ry=null;
		try {
			ry=(RYBM)session.getAttribute("ry");
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		//当前人的单位编码
		String dwbm=ry.getDwbm();
		//当前人的工号
		String gh=ry.getGh();
		//存入session中的人员角色
		List<Integer> ryjs=(List<Integer>)session.getAttribute("ryjs");
		//存入session中的部门角色
		List<String> bmjs=(List<String>)session.getAttribute("bmjs");

		try {
			resultMap=bmmcService.getSyjxxx(dwbm,gh,ryjs,bmjs);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		return Constant.GSON.toJson(resultMap);
	}
	/**
	 * 通过部门编码,角色编码获得人员
	 * @param bmbm 部门编码
	 * @param jsbm 角色编码
	 * @param request request
	 * @return 返回人员信息
	 */
	@RequestMapping("getrybyjs")
	@ResponseBody
	public String getrybyjs(String bmbm,String jsbm,HttpServletRequest request){

		RYBM ry = (RYBM) request.getSession().getAttribute("ry");
		String dwbm = ry.getDwbm();
		String curentpage = request.getParameter("page");//当前页
		String pagesize = request.getParameter("rows");//每页显示数

		List<RYBM> list = bmmcService.getBmmcBybmbm(curentpage,pagesize,dwbm,bmbm,jsbm);
		Map<String,Object> map = new HashMap<String,Object>();
		int total = bmmcService.getTotal(dwbm,bmbm,jsbm);

		map.put("total", total);
		map.put("rows", list);
		return Constant.GSON.toJson(map);
	}
	/**
	 * 通过部门编码获得人员信息
	 * @param bmbm 部门编码
	 * @param request request
	 * @return 人员信息
	 */
	@RequestMapping(value="getrybybm",method=RequestMethod.POST)
	@ResponseBody
	public String getrybybm(String bmbm,HttpServletRequest request){
		RYBM ry = (RYBM) request.getSession().getAttribute("ry");
		String dwbm = ry.getDwbm();
		String curentpage = request.getParameter("page");//当前页
		String pagesize = request.getParameter("rows");//每页显示数

		List<RYBM> list = bmmcService.getRyByBmbm(curentpage,pagesize,dwbm,bmbm);
		Map<String,Object> map = new HashMap<String,Object>();

		int total = bmmcService.getTotalBybm(dwbm,bmbm);
		map.put("total", total);
		map.put("rows", list);
		return Constant.GSON.toJson(map);
	}

}
