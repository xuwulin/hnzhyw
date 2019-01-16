/**
 * 
 */
package com.swx.ibms.business.performance.controller;


import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.performance.bean.HCPZ;
import com.swx.ibms.business.performance.service.HCPZService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 核查配置
 * @author 封泽超
 *@since 2017年4月11日
 */
@Controller
@RequestMapping("/hcpz")
public class HCPZController {
	
	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(HCPZController.class);

	/**
	 * 核查配置
	 */
	@Resource
	private HCPZService hcpzService;
	/**
	 * 添加核查配置
	 * @param request 
	 * @return String
	 */
	@RequestMapping("/insert")
	public @ResponseBody
    String insert(HttpServletRequest request){
		HCPZ hcpz = new HCPZ();
		
		String hcdwbm = request.getParameter("hcdwbm");	//核查单位编码
		String bhcdwbm = request.getParameter("bhcdwbm");//被核查单位编码
		String ywlx = request.getParameter("ywlx");//业务类型
		String kssj = request.getParameter("kssj");//开始时间
		String jssj = request.getParameter("jssj");//结束时间
		
		hcpz.setHcdwbm(hcdwbm);
		hcpz.setBhcdwbm(bhcdwbm);
		hcpz.setYwlx(ywlx);
		hcpz.setKssj(kssj);
		hcpz.setJssj(jssj);
		
		String y = hcpzService.insert(hcpz);
		
		return y;
	}
	/**
	 * 删除.可以多个','隔开
	 * @param request 
	 * @return String
	 */
	@RequestMapping("/delete")
	public @ResponseBody
    String delete(HttpServletRequest request){
		HCPZ hcpz = new HCPZ();
		int y = 0;
		
		String bhcdwbmp = request.getParameter("bhcdwbm"); //被核查单位编码(多个,","隔开)
		String ywlxp = request.getParameter("ywlx"); //业务类型(多个,","隔开)
		String oldkssjp = request.getParameter("kssj");//开始时间(多个,","隔开)
		
		//可能会有多的参数的情况
		String[] bhcdwbms = bhcdwbmp.split(",");
		String[] ywlxs = ywlxp.split(",");
		String[] oldkssjs = oldkssjp.split(",");
		//循环删除
		for(int i = 0 ; i < bhcdwbms.length; i++){
			String bhcdwbm = bhcdwbms[i];
			String ywlx = ywlxs[i];
			String oldkssj = oldkssjs[i];
			hcpz.setBhcdwbm(bhcdwbm);
			hcpz.setYwlx(ywlx);
			hcpz.setKssj(oldkssj);
			y += Integer.valueOf(hcpzService.delete(hcpz));
		}
		return y+"";
	}
	/**
	 * 修改核查信息
	 * @param request  
	 * @return String 
	 */
	@RequestMapping("/update")
	public @ResponseBody
    String updata(HttpServletRequest request){
		HCPZ hcpz = new HCPZ();
		
		String hcdwbm = request.getParameter("hcdwbm");//核查单位编码
		String bhcdwbm = request.getParameter("bhcdwbm");//被核查单位编码
		String ywlx = request.getParameter("ywlx");//业务类型
		String kssj = request.getParameter("kssj");//新的开始时间
		String jssj = request.getParameter("jssj");//新的结束时间
		String oldkssj = request.getParameter("oldkssj"); //被修改的时间(查询用)
		
		hcpz.setHcdwbm(hcdwbm);
		hcpz.setBhcdwbm(bhcdwbm);
		hcpz.setYwlx(ywlx);
		hcpz.setKssj(kssj);
		hcpz.setJssj(jssj);
		hcpz.setOldkssj(oldkssj);
		
		String y = hcpzService.update(hcpz);
		
		return y;
	}
	/**
	 * 通过被核查单位编码查询被核查单位全部业务的配置
	 * @param request
	 * @return
	 */
//	@RequestMapping("/select")
//	public @ResponseBody String select(HttpServletRequest request){
//		HCPZ hcpz = new HCPZ();
//		List<HCPZ> list;
//		String bhcdwbm = request.getParameter("bhcdwbm");
//		
//		hcpz.setBhcdwbm(bhcdwbm);
//		
//		list = hcpzService.select(hcpz);
//		
//		return Constant.GSON.toJson(list);
//	}
	/**
	 * 根据被核查单位编码与业务类型查询,判定核查配置是否存在使用.(因需求修改停止使用)
	 * @param request 请求
	 * @return String 
	 */
	@RequestMapping("/getbmlbbyywlx")
	public @ResponseBody
    String selectone(HttpServletRequest request){
		HCPZ hcpz = new HCPZ();
		
		String bhcdwbm = request.getParameter("bhcdwbm");
		String ywlx = request.getParameter("ywlx");
		
		hcpz.setBhcdwbm(bhcdwbm);
		hcpz.setYwlx(ywlx);
		
		List<HCPZ> list = hcpzService.selectdwbmywlx(hcpz);
		if(CollectionUtils.isEmpty(list)){
			return "";
		}
		return Constant.GSON.toJson(list.get(0));
	}
	/**
	 * 得到业务类型列表
	 * @param request 
	 * @return String
	 */
	@RequestMapping("/getywlxlist")
	public @ResponseBody
    String getywlxlist(HttpServletRequest request){
		List<String> list = hcpzService.getywlxlist();
		return Constant.GSON.toJson(list);
	}
	/**
	 * 得到全部或者bhcbm的核查配置,需要page,rows参数
	 * @param request 
	 * @return String
	 */
	@RequestMapping("/gethcpz")
	public @ResponseBody
    String getAllhcpz(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> result = null;
		String page = (String) request.getParameter("page");
		String pagesize = (String) request.getParameter("rows");
		String bhcdw = (String) request.getParameter("bhcdwbm");
		if(StringUtils.isEmpty(bhcdw)) {
			result = hcpzService.getAllhcpz(page, pagesize);
		} else {
			result = hcpzService.select(bhcdw,page,pagesize);
		}
		
		
		map.put("total", result.get("p_total"));
		map.put("rows", result.get("p_cur"));
		
		
		return Constant.GSON.toJson(map);
	}
	/**
	 * 得到年份列表 当前年的 前四年 后一年
	 * @param request 
	 * @return String
	 */
	@RequestMapping("/getdqnf")
	public @ResponseBody
    String getdqnf(HttpServletRequest request){
		Map<String,Object> map =null;
		List<Object> list = new ArrayList<Object>();
		
		Date date = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm");
		String[] sj = sdf.format(date).split("-");
		int nowyear = Integer.valueOf(sj[0]);
		
		if(sj[1] == "1") {
			nowyear --;
		}
		// 年份,当前时间的 前四年,后一年
		for(int i = 2<<1 ; i >=-1 ; i--){
			map = new HashMap<String,Object>();
			map.put("id", nowyear -i);
			map.put("text", nowyear -i + "年");
			list.add(map);
		}
		
		return Constant.GSON.toJson(list);
	}
	/**
	 * 根据单位编码得到单位级别 2:省院
	 * @param request 
	 * @return String 
	 */
	@RequestMapping("/getdwjb")
	public @ResponseBody
    String getdwjb(HttpServletRequest request){
		String dwbm = request.getParameter("dwbm");
		
		Map<String,String> map = hcpzService.getdwjb(dwbm);
		
		return Constant.GSON.toJson(map);
	}
	
	
}
