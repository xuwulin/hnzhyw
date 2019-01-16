package com.swx.ibms.business.rbac.controller;

import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.Bmlbywfp;
import com.swx.ibms.business.rbac.service.BmlbywfpService;
import com.swx.ibms.common.utils.PageCommon;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 业务类型分配控制器
 * BmlbywfpController.java 
 * @author 何向东
 * @date<p>2017年12月19日</p>
 * @version 1.0
 * @description<p></p>
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/bmlbywfp")
public class BmlbywfpController {
	
	/**
	 * 业务类型分配接口
	 */
	@Resource
	private BmlbywfpService bmlbywfpService;
	
	@RequestMapping("/getPageList")
	@ResponseBody
	public String ywfpSelectPageList(@RequestParam(value="ywbm",required=false)String ywbm,
									  @RequestParam(value="bmlbbm",required=false)String bmlbbm,
									  @RequestParam(value="dwbm",required=false)String dwbm,
									  @RequestParam(value="type",required=false)String type,
							          @RequestParam(value="page",required=true)Integer nowPage,
							          @RequestParam(value="rows",required=true)Integer pageSize) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_3);
		PageCommon<Bmlbywfp> pageCommon = bmlbywfpService.ywfpSelectPageList(ywbm, bmlbbm,dwbm,
				type,nowPage, pageSize);
		map.put("total", pageCommon.getTotalRecords());
        map.put("rows", pageCommon.getList());
        return Constant.GSON.toJson(map);
	}
	
	@RequestMapping("/getListById")
	@ResponseBody
	public String getYwfpListById(@RequestParam(value="id",required=true)Integer id)
			throws Exception{
		List<Bmlbywfp> pageCommon = bmlbywfpService.getYwfpListById(id);
        return Constant.GSON.toJson(pageCommon);
	}
	
	@RequestMapping("/modifyById")
	@ResponseBody
	public String modifyYwfpById(@RequestParam(value="ywbm",required=true)String ywbm,
								  @RequestParam(value="bmlbbm",required=true)String bmlbbm,
								  @RequestParam(value="dwbm",required=false)String dwbm,
								  @RequestParam(value="id",required=true)Integer id
								  )throws Exception{
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_3);
		String msg = bmlbywfpService.modifyYwfpById(id, bmlbbm, ywbm, dwbm);
		map.put("msg", msg);
        return Constant.GSON.toJson(map);
	}
	
	@RequestMapping("/delById")
	@ResponseBody
	public String delYwfpById(@RequestParam(value="id",required=true)Integer id
								  )throws Exception{
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_3);
		String msg = bmlbywfpService.delYwfpById(id);
		map.put("msg", msg);
        return Constant.GSON.toJson(map);
	}
	
	@RequestMapping("/addYwfp")
	@ResponseBody
	public String addYwfp(@RequestParam(value="ywbm",required=true)String ywbm,
						 @RequestParam(value="bmlbbm",required=true)String bmlbbm,
						 @RequestParam(value="dwbm",required=false)String dwbm
								  )throws Exception{
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_3);
		String msg = bmlbywfpService.addYwfp(bmlbbm, ywbm, dwbm);
		map.put("msg", msg);
        return Constant.GSON.toJson(map);
	}
}
