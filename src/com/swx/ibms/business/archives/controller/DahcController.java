package com.swx.ibms.business.archives.controller;

import com.swx.ibms.business.archives.bean.Dahcsl;
import com.swx.ibms.business.archives.bean.Hcdafl;
import com.swx.ibms.business.archives.bean.Hcwtfl;
import com.swx.ibms.business.archives.service.DAGZService;
import com.swx.ibms.business.archives.service.DahcService;
import com.swx.ibms.business.common.bean.Splcsl;
import com.swx.ibms.business.common.service.SpService;
import com.swx.ibms.business.common.utils.Constant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author zsq
 * 
 */
@Controller
@RequestMapping("/dahc")
public class DahcController {
	
	/**
	 * dahcService
	 */
	@Autowired
	private DahcService dahcService;
	
	/**
	 * 服务访问接口
	 */
	@Resource
	private SpService spService;
	
	/**
	 * 档案归总
	 */
	@Resource
	private DAGZService dagzService;
	
	/**
	 * @return 核查档案分类
	 */
	@RequestMapping("/getHcdafl")
	@ResponseBody
	public String getHcdafl(){
		List<Hcdafl> list = dahcService.getHcdafl();
		return Constant.GSON.toJson(list);
	}
	/**
	 * @return 核查问题分类
	 */
	@RequestMapping("/getHcwtfl")
	@ResponseBody
	public String getHcwtfl(){
		List<Hcwtfl> list = dahcService.getHcwtfl();
		return Constant.GSON.toJson(list);
	}
	
	
	/**
	 * @param hcsl 档案核查实例
	 * @return message
	 */
	@RequestMapping("/adddahcsl")
	@ResponseBody
	public String addDahcsl(Dahcsl hcsl){
		if(hcsl.getZt() != 1){
			dahcService.complete(hcsl.getDagzid());
		}
		String fjid = dahcService.addDahcsl(hcsl);
		return fjid;
	}
	
	/**
	 * @param dwbm 单位编码
	 * @return message
	 */
	@RequestMapping("/dahcdbyw")
	@ResponseBody
	public String dahcDbyw(String dwbm){
		List<Dahcsl> list = dahcService.dahcDbyw(dwbm);
		return Constant.GSON.toJson(list);
	}
	
	/**
	 * 可以分页的档案核查待办业务
	 * @param request 
	 * @return message
	 */
	@RequestMapping("/dahcdbywfy")
	@ResponseBody
	public String dahcDbyw1(HttpServletRequest request){
		List<Dahcsl> list = new ArrayList<Dahcsl>();
		String dwbm = request.getParameter("dwbm");
		String sstart = request.getParameter("start");  // 起始标号
		String snum = request.getParameter("num");      // 数量
		
		int start = 1;
		int num = 0;
		
		if(!StringUtils.isBlank(sstart)){
			start = Integer.valueOf(sstart);
		}
		if(!StringUtils.isBlank(snum)){
			num = Integer.valueOf(snum);
		}
		
		start --;
		
		List<Dahcsl> temp = dahcService.dahcDbyw(dwbm);
		
		for(int i = start; i < start + num&&  i < temp.size(); i++){
			list.add(temp.get(i));
		}
		return Constant.GSON.toJson(list);
	}
	
	
	
	
	/** 
	 * @param gh 工号
	 * @param dwbm 单位编码
	 * @return 根据工号查询个人的档案核查代办业务
	 */
	@RequestMapping("/grdahcdbyw")
	@ResponseBody
	public String grdahcdbyw(String dwbm,String gh){
		List<Dahcsl> list = dahcService.grdahcdbyw(dwbm,gh);
		return Constant.GSON.toJson(list);
	}
	
	
	/**
	 * @param id id
	 * @return message
	 */
	@RequestMapping("/getDbywByDagzid")
	@ResponseBody
	public String getDbywByDagzid(String id){
		List<Dahcsl> list = dahcService.getDbyw(id);
		return Constant.GSON.toJson(list);
	}
	/** 
	 * @param dagzid dagzid
	 * @param zt zt
	 * @return message
	 */
	@RequestMapping("/hcyd")
	@ResponseBody
	public String hcyd(String dagzid,int zt){
		if(zt == 2){		//核查审批同意
			dahcService.dahcupdate(dagzid);
		}
		dahcService.deleteDahcsl(dagzid);
		return "true";
	}
	/** 
	 * @param dagzid dagzid
	 * @return message
	 */
	@RequestMapping("/daspzt")
	@ResponseBody
	public String daspzt(String dagzid){
		int status = dahcService.daspzt(dagzid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		return Constant.GSON.toJson(map);
	}
	/** 
	 * @param dagzid dagzid
	 * @return 是否公示
	 */
	@RequestMapping("/sfgs")
	@ResponseBody
	public String sfgs(String dagzid){
		int sfgs = dahcService.sfgs(dagzid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sfgs", sfgs);
		return Constant.GSON.toJson(map);
	}
	/** 
	 * @param wbid wbid
	 * @return 是否有附件
	 */
	@RequestMapping("/getfj")
	@ResponseBody
	public String getfj(String wbid){
		int fj = dahcService.getfj(wbid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fj", fj);
		return Constant.GSON.toJson(map);
	}
	/** 
	 * @param wbid 档案归总id
	 * @return 是否可以再次核查
	 */
	@RequestMapping("/sfzzhc")
	@ResponseBody
	public String sfzzhc(String wbid){
		int sfzzhc = dahcService.sfzzhc(wbid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sfzzhc", sfzzhc);
		return Constant.GSON.toJson(map);
	}
	/** 
	 * @param wbid 档案归总id
	 * @return 是否过了核查期限
	 */
	@RequestMapping("/sfgq")
	@ResponseBody
	public String sfgq(String wbid){
		int sfgq = dahcService.sfgq(wbid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sfgq", sfgq);
		return Constant.GSON.toJson(map);
	}
	
	/**
	 * 获取档案、核查（变更）的审批状态
	 * @param request spstid审批实体id
	 * @return map
	 */
	@RequestMapping(value = "/getHcSpzt", method = RequestMethod.POST)
	public @ResponseBody String getHcSpzt(HttpServletRequest request) {
		
		String spstid = request.getParameter("spstid");  //审批实体id
	
		List<Splcsl> listHcDaSpzt = null;
		List<Splcsl> listHcSpzt = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			listHcDaSpzt = dagzService.showSplcsl(spstid, "2");
			listHcSpzt = dagzService.showSplcsl(spstid,"8");
			
			if(!CollectionUtils.isEmpty(listHcDaSpzt)){
				map.put("hcDaSpzt", listHcDaSpzt.get(0).getSpzt());
			}
			if(!CollectionUtils.isEmpty(listHcSpzt)){
				map.put("hcSpzt", listHcSpzt.get(0).getSpzt());
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return Constant.GSON.toJson(map);
	}
}
