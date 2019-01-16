package com.swx.ibms.business.appraisal.controller;


import com.swx.ibms.business.appraisal.bean.Ywgzkh;
import com.swx.ibms.business.appraisal.service.YwgzkhSpService;
import com.swx.ibms.business.archives.service.SfdaspService;
import com.swx.ibms.business.common.bean.Jdlc;
import com.swx.ibms.business.common.bean.PersonTree;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.service.MessageNoticeService;
import com.swx.ibms.business.common.service.SpService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.system.service.XtfjpathService;
import com.swx.ibms.common.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * ywgzkhSpController.java 业务工作考核Controller
 * 
 * @author 何向东
 * @date
 *       <p>
 *       2017年6月5日
 *       </p>
 * @version 1.0
 * @description
 *              <p>
 *              </p>
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("ywgzkh")
public class YwgzkhSpController {
	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(YwgzkhSpController.class);

	/**
	 * 文件上传路径服务
	 */
	@Resource
	private XtfjpathService xtfjpathservice;
	
	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;
	/**
	 * 消息通知服务
	 */
	@Autowired
	private MessageNoticeService messageService;
	/**
	 * 服务访问接口
	 */
	@Resource
	private SpService spService;
	/**
	 * 业务工作考核service
	 */
	@Resource
	private YwgzkhSpService ywgzkhSpService;

	/**
	 * 司法档案审批服务
	 */
	@Resource
	private SfdaspService sfdaspService;

	/**
	 * 省院案件管理处创建市院、基层院的年度业务工作考核
	 * @param request 请求对象
	 * @return json字符串
	 */
	@RequestMapping(value = "/insertNdYwgzkh", method = RequestMethod.GET)
	@ResponseBody
	public String insertNdYwgzkhInfo(@RequestParam(value="dwbm",required=true)String dwbm,
									 @RequestParam(value="gh",required=true)String gh,
									 @RequestParam(value="bmbm",required=false)String bmbm,
									 @RequestParam(value="year",required=true)String year,
									 @RequestParam(value="createDate",required=true)String createDate,
									 @RequestParam(value="endDate",required=true)String endDate,
									 HttpServletRequest request)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_2);
		
		StringBuilder ksrq = new StringBuilder();  //开始日期
    	StringBuilder jsrq = new StringBuilder();  //结束日期
    	int ksYear = Integer.parseInt(createDate.split("-")[0]);
    	int ksMonth = Integer.parseInt(createDate.split("-")[1]);
    	ksrq.append(ksYear).append("-").append(ksMonth).append("-").append(Constant.NUM_1);
    		
    	int jsYear = Integer.parseInt(endDate.split("-")[0]);
    	int jsMonth = Integer.parseInt(endDate.split("-")[1]);
    	int jsDay = DateUtil.getDayByYearAndMonth(jsYear,jsMonth);
    	jsrq.append(jsYear).append("-").append(jsMonth).append("-").append(jsDay);
		
		//单位、工号、开始日期、结束日期、公示期限（D天H小时W周）
		int ywgzkhYear = Integer.parseInt(year);
		
		//将参数设置到业务考核实体类中
		Ywgzkh ywgzkh = new Ywgzkh();
		ywgzkh.setDwbm(dwbm);
		//这儿的年不一定是当前年份，页面获取的是取得结束日期的年份
		ywgzkh.setYear(ywgzkhYear);
		ywgzkh.setBmbm(bmbm);
		ywgzkh.setGh(gh);
		ywgzkh.setCreateDate(ksrq.toString());
		ywgzkh.setEndDate(jsrq.toString());
		//-------注意这儿默认设置公示期限为三个工作日，数据再做的查询数据字典里面设置的具体期限是多少
		ywgzkh.setSjqx(Constant.YWKH_GSQX_VALUE);
		
		String msg = ywgzkhSpService.insertNdYwgzkh(ywgzkh);
		if(StringUtils.isNotEmpty(msg)){
			map.put("msg", msg);
		}
		return Constant.GSON.toJson(map);
	}

	/**
	 * 业务部门领导指定考核人，用到的参数有业务类型考核id、发起人部门编码、发起人工号、发起人姓名、考核人部门编码、考核人工号、考核人名称
	 * @param request 请求对象
	 * @return json字符串
	 */
	@RequestMapping(value= "/addYwgzkhSpr",method=RequestMethod.POST)
	@ResponseBody
	public String addYwgzkhSpr(HttpServletRequest request){
		
		String ywlxkhId = request.getParameter("ywlxkhId");//业务类型考核id
		String khlx= request.getParameter("khlxstr");//考核类型
		String fprBmbm = request.getParameter("fprBmbm");//发起人部门编码
		String fprGh = request.getParameter("fprGh");//发起人工号
		String fprMc = request.getParameter("fprMc");//发起人姓名
		String khrBmbm = request.getParameter("khrBmbm");//考核人部门编码
		String khrGh = request.getParameter("khrGh");//考核人工号
		String khrMc = request.getParameter("khrMc");//考核人名称
		
		if(StringUtils.isNotEmpty(ywlxkhId)&&StringUtils.isNotEmpty(fprBmbm)&&StringUtils.isNotEmpty(khlx)
				&&StringUtils.isNotEmpty(fprGh)&&StringUtils.isNotEmpty(fprMc)
				&&StringUtils.isNotEmpty(khrBmbm)&&StringUtils.isNotEmpty(khrGh)
				&&StringUtils.isNotEmpty(khrMc)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("p_ywlxkhid", ywlxkhId);
			map.put("p_khlx", khlx);
			map.put("p_fprbmbm", fprBmbm);
			map.put("p_fprgh", fprGh);
			map.put("p_fprmc", fprMc);
			map.put("p_khrbmbm", khrBmbm);
			map.put("p_khrgh", khrGh);
			map.put("p_khrmc", khrMc);
			
			String msg = ywgzkhSpService.addYwgzkhSpr(map);
			if (StringUtils.isNotEmpty(msg)) {
				map.put("msg", msg);
				return Constant.GSON.toJson(map);
			}
		}
		
		return null;
	}

	/**
	 * 审批
	 * @param request 请求参数
	 * @return 执行信息
	 */
	@RequestMapping(value = "/audit")
	@ResponseBody
	public String audit(HttpServletRequest request) {
		String jdlx = request.getParameter("jdlx");// 节点类型
		String lcjd = request.getParameter("lcjd");// 流程节点
		List<Jdlc> jdlcList = null;
		try {
			jdlcList = spService.getMbByLcjd(jdlx, lcjd);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		Jdlc jdlc = jdlcList.get(0);
		
		RYBM ry = (RYBM) request.getSession().getAttribute("ry");
		String dlrdwbm = ry.getDwbm();// 登录人单位编码
		String dlrbmbm = ry.getBmbm();//登录人部门编码
		String dlrgh = ry.getGh();// 登录人工号
		String sprdwbm = request.getParameter("sprdwbm");// 审批人单位编码
		String sprbmbm = request.getParameter("sprbmbm");//审批人部门编码
		String sprgh = request.getParameter("sprgh");// 审批人工号
		String spstid = request.getParameter("spstid");// 审批实体ID
		String spyj = request.getParameter("spyj");// 审批意见
		String clnr = request.getParameter("clnr");// 处理内容
		
		try {
			clnr = URLDecoder.decode(clnr, "utf-8");
		} catch (Exception e1) {
		}
		String [] szspstid=spstid.split(",");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for(int i=0;i<szspstid.length;i++){
			  spstid=szspstid[i];
		String fqhcshlcid = null;
		Map<String, Object> swmap = new HashMap<String, Object>();
		swmap.put("clnr", clnr);
		swmap.put("cllx", jdlc.getLclx());
		swmap.put("wbid", spstid);
		String strLclx = "";
		
	    if("9".equals(jdlc.getLclx())){
			strLclx = "年度考核(市院)审批";
		}
	    
	    if (jdlc!=null &&"0".equals(jdlc.getJdzt())) {// 如果此节点状态是0，表示此节点是发起审批
			String firstmsg = null;
			String secondmsg = null;
			if(spyj==null||"undefined".equals(spyj)){
				spyj="";
			}
			Map mapfirst = spService.addSplcslFirst(dlrdwbm, dlrbmbm, dlrgh, spyj, 
					jdlc.getLclx(), "0", spstid, "", lcjd,jdlx,jdlc.getCljsxz());// 插入一条自己的审批实例信息
			firstmsg = (String) mapfirst.get("Y");
			String lcid = (String) mapfirst.get("p_lcid");
			fqhcshlcid = lcid;
			if ("1".equals(firstmsg)) {
				// 插入一条下一个审批人的审批实例信息
				String bmbmstr = sprbmbm;
				if(!StringUtils.isEmpty(jdlc.getXyclbm())){
					bmbmstr = jdlc.getXyclbm();
				}
				secondmsg = spService.addSplcsl(sprdwbm, bmbmstr, sprgh, "",
						jdlc.getLclx(), "1", spstid,jdlc.getXycljs(),
						"", jdlc.getXyjd(), lcid,jdlc.getCljsxz());
				if ("1".equals(secondmsg)) {
					
					//将处理内容存入审批事务表
					swmap.put("lcid", lcid);
					sfdaspService.insertClsw(swmap);
					
					//修改业务类型表的状态
					if (StringUtils.isNotEmpty(spstid)) {
						
						Map<String,Object> ywgzkhSpmap = new HashMap<String,Object>();
						ywgzkhSpmap.put("p_ywlxkhid", spstid);
						ywgzkhSpmap.put("p_zt", jdlc.getZtbg());
						//更新YX_YWGZ_YWLXKH的状态和修改时间
						String msg = ywgzkhSpService.updateYwgzkhSpZt(ywgzkhSpmap);
					}
					
					//记录日志
					logService.info(Constant.CURRENT_USER.get().getDwbm(), 
							Constant.CURRENT_USER.get().getGh(),
							Constant.CURRENT_USER.get().getMc(), 
							Constant.RZLX_CZRZ, 
							"发起"+strLclx+"审批");
					resultMap.put("status", "1");
				}
				} else {
					//记录日志
					logService.error(Constant.CURRENT_USER.get().getDwbm(),
							Constant.CURRENT_USER.get().getGh(),
							Constant.CURRENT_USER.get().getMc(), 
							Constant.RZLX_CZRZ, 
							"发起"+strLclx+"审批失败");
					resultMap.put("status", "0");
				}
			} else {
				//记录日志
				logService.error(Constant.CURRENT_USER.get().getDwbm(), 
						Constant.CURRENT_USER.get().getGh(),
						Constant.CURRENT_USER.get().getMc(), 
						Constant.RZLX_CZRZ, 
						"发起"+strLclx+"审批失败");
				resultMap.put("status", "0");
			}
	    }
		return Constant.GSON.toJson(resultMap);
	}

	/**
	 * 第一次审批开始，存入审批意见、审批状态
	 * @param request 请求对象
	 * @return json字符串对象
	 */
	@RequestMapping(value = "/auditStart")
	@ResponseBody
	public String auditStart(HttpServletRequest request) {
		
		String spId =  request.getParameter("spid");// 审批表id
		String wbId = request.getParameter("wbid");// 业务类型id
		String spLx = request.getParameter("splx");//审批类型
		String lcId = request.getParameter("lcid");//流程id
		String spJl = request.getParameter("spjl");//审批结论
		String spyj = request.getParameter("spyj");// 审批意见
		String fqrGh = request.getParameter("fqrGh");// 发起人工号
		RYBM ry = (RYBM) request.getSession().getAttribute("ry");
		String sprDwbm = ry.getDwbm();//审批人单位编码
		String sprBmbm = ry.getBmbm();//审批人部门编码
		String sprGh = ry.getGh();//审批人工号
		String sprMc = ry.getMc();//审批人姓名
		String ywkhId = request.getParameter("ywkhId")==null?"":request.getParameter("ywkhId");
		
		//p_ywlxkhid  p_dwbm   p_bmbm  p_gh  p_spyj  p_spjl  p_zt  p_errmsg
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_ywlxkhid", wbId);//
		map.put("p_ywkhid", ywkhId);
		map.put("p_spslid", spId);
		map.put("p_splcid", lcId);
		map.put("p_dwbm", sprDwbm);
		map.put("p_bmbm", sprBmbm);
		map.put("p_gh", sprGh);
		map.put("p_fqrgh", fqrGh);
		map.put("p_mc", sprMc);
		map.put("p_spyj", spyj);
		map.put("p_spjl", spJl);
		
		String msg = ywgzkhSpService.updateSplc(map);
		if (StringUtils.isNotEmpty(msg)) {
			map.put("msg", msg);
			return Constant.GSON.toJson(map);
		}
		
		
		
		return Constant.GSON.toJson(map);
	}

	/**
	 * 指定考核人员，获取某个单位的部门下属人员列表
	 * @param request 请求参数
	 * @return json数据
	 */
	@RequestMapping(value = "/bmkhry")
	@ResponseBody
	public String bmkhry(HttpServletRequest request) {
		String khlx = request.getParameter("khlx");
		List<PersonTree> res = ywgzkhSpService.getBmkhry(khlx,Constant.CURRENT_USER.get().getDwbm(),
				Constant.CURRENT_USER.get().getBmbm(),Constant.CURRENT_USER.get().getGh());
		return Constant.GSON.toJson(res);
	}

	/**
	 * 异议审批
	 * @param request 请求参数
	 * @return 执行信息
	 */
	@RequestMapping(value = "/yyAudit")
	@ResponseBody
	public String yyAudit(HttpServletRequest request) {
		String jdlx = request.getParameter("jdlx");// 节点类型
		String lcjd = request.getParameter("lcjd");// 流程节点
		List<Jdlc> jdlcList = null;
		try {
			jdlcList = spService.getMbByLcjd(jdlx, lcjd);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		Jdlc jdlc = jdlcList.get(0);
		
		RYBM ry = (RYBM) request.getSession().getAttribute("ry");	
		String dlrdwbm = ry.getDwbm();// 登录人单位编码
		String dlrbmbm = ry.getBmbm();//登录人部门编码
		String dlrgh = ry.getGh();// 登录人工号
		String sprdwbm = request.getParameter("sprdwbm");// 审批人单位编码
		String sprbmbm = request.getParameter("sprbmbm");//审批人部门编码
		String sprgh = request.getParameter("sprgh");// 审批人工号
		String spstid = request.getParameter("spstid");// 审批实体ID
		String spyj = request.getParameter("spyj");// 审批意见
		String clnr = request.getParameter("clnr");// 处理内容
		String yysm = request.getParameter("yysm");//异议说明
		String lclx = request.getParameter("lclx");//流程类型
		String yyzt = request.getParameter("yyzt");//流程类型
		
		try {
			clnr = URLDecoder.decode(clnr, "utf-8");
		} catch (Exception e1) {
		}
		String fqhcshlcid = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String [] szspstid=spstid.split(",");
		for(int i=0;i<szspstid.length;i++){
			spstid=szspstid[i];	
		Map<String, Object> swmap = new HashMap<String, Object>();
		swmap.put("clnr", clnr);
		swmap.put("cllx", jdlc.getLclx());
		swmap.put("wbid", spstid);
		String strLclx = "";
		
	    if("11".equals(jdlc.getLclx())){
			strLclx = "年度考核异议审批";
		}
	    
	    if (jdlc!=null &&"0".equals(jdlc.getJdzt())) {// 如果此节点状态是0，表示此节点是发起审批
			String firstmsg = null;
			String secondmsg = null;
			if(spyj==null||"undefined".equals(spyj)){
				spyj="";
			}
			Map mapfirst = spService.addSplcslFirst(dlrdwbm, dlrbmbm, dlrgh, spyj, 
					jdlc.getLclx(), "0", spstid, "", lcjd,jdlx,jdlc.getCljsxz());// 插入一条自己的审批实例信息
			firstmsg = (String) mapfirst.get("Y");
			String lcid = (String) mapfirst.get("p_lcid");
			fqhcshlcid = lcid;
			if ("1".equals(firstmsg)) {
				Map khMap=ywgzkhSpService.getKhById(spstid);
				//市院部门(案管)领导审批完后就提交到省院了
				if("3".equals(khMap.get("KHDWJB"))){
					jdlcList = spService.getMbByLcjd("932","93004");
					jdlc=jdlcList.get(0);
				}

				// 插入一条下一个审批人的审批实例信息
				String bmbmstr = sprbmbm;
				if(!StringUtils.isEmpty(jdlc.getXyclbm())){
					bmbmstr = jdlc.getXyclbm();
				}
				secondmsg = spService.addSplcsl(sprdwbm, bmbmstr, sprgh, "",
						jdlc.getLclx(), "1", spstid,jdlc.getXycljs(), "",
						jdlc.getXyjd(), lcid,jdlc.getCljsxz());
				if ("1".equals(secondmsg)) {
					
					//将处理内容存入审批事务表
					swmap.put("lcid", lcid);
					sfdaspService.insertClsw(swmap);
					
					//修改业务类型表的状态
					if (StringUtils.isNotEmpty(spstid)) {
						
						Map<String,Object> ywgzkhYySpMap = new HashMap<String,Object>();
						ywgzkhYySpMap.put("p_khid", spstid);
						ywgzkhYySpMap.put("p_yyzt", yyzt);
						ywgzkhYySpMap.put("p_yysm", yysm);
						ywgzkhYySpMap.put("p_lclx", lclx);
						ywgzkhYySpMap.put("p_jdlx", jdlx);
						ywgzkhYySpMap.put("p_lcjd", lcjd);
						ywgzkhYySpMap.put("p_lcid", lcid);

						//更新YX_YWGZ_YWLXKH的状态和修改时间
						String msg = ywgzkhSpService.updateYwgzkhYYSpZt(ywgzkhYySpMap);
					}
					
					//记录日志
					logService.info(Constant.CURRENT_USER.get().getDwbm(), 
							Constant.CURRENT_USER.get().getGh(),
							Constant.CURRENT_USER.get().getMc(), 
							Constant.RZLX_CZRZ, 
							"发起"+strLclx+"审批");
					resultMap.put("status", "1");
				} else {
					//记录日志
					logService.error(Constant.CURRENT_USER.get().getDwbm(),
							Constant.CURRENT_USER.get().getGh(),
							Constant.CURRENT_USER.get().getMc(), 
							Constant.RZLX_CZRZ, 
							"发起"+strLclx+"审批失败");
					resultMap.put("status", "0");
				}
			} else {
				//记录日志
				logService.error(Constant.CURRENT_USER.get().getDwbm(), 
						Constant.CURRENT_USER.get().getGh(),
						Constant.CURRENT_USER.get().getMc(), 
						Constant.RZLX_CZRZ, 
						"发起"+strLclx+"审批失败");
				resultMap.put("status", "0");
			 }
	       }
		}
		 return Constant.GSON.toJson(resultMap);
	}

	/**
	 * 指定考核人员，获取某个单位的部门下所有人列表（自己除外）
	 * @param request 请求参数
	 * @return json数据
	 */
	@RequestMapping(value = "/allBmry")
	@ResponseBody
	public String selectAllBmry(@RequestParam(value="bmbm",required=false)String bmbm,
									HttpServletRequest request) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("p_dwbm", Constant.CURRENT_USER.get().getDwbm());
		if(StringUtils.isEmpty(bmbm)){
			map.put("p_bmbm", Constant.CURRENT_USER.get().getBmbm());
		}else{
			map.put("p_bmbm", bmbm);
		}
		map.put("p_gh", Constant.CURRENT_USER.get().getGh());
		
		List<PersonTree> res = ywgzkhSpService.selectAllBmry(map);// 
		
		return Constant.GSON.toJson(res);
	}

	/**
	 * 年度考核活动异议审批
	 * @param request 请求对象
	 * @return json字符串对象
	 */
	@RequestMapping(value = "/yyAuditStart")
	@ResponseBody
	public String yyAuditStart(HttpServletRequest request) {
		
		String spId =  request.getParameter("spid");// 审批表id
		String wbId = request.getParameter("wbid");// 被审批表的id
		String spLx = request.getParameter("splx");//审批类型
		String lcId = request.getParameter("lcid");//流程id
		String spJl = request.getParameter("spjl");//审批结论
		String spyj = request.getParameter("spyj");// 审批意见
		RYBM ry = (RYBM) request.getSession().getAttribute("ry");
		String sprDwbm = ry.getDwbm();//审批人单位编码
		String sprBmbm = ry.getBmbm();//审批人部门编码
		String sprGh = ry.getGh();//审批人工号
		String sprMc = ry.getMc();//审批人姓名
//		String ywkhId = request.getParameter("ywkhId")==null?"":request.getParameter("ywkhId");
		
		//p_ywlxkhid  p_dwbm   p_bmbm  p_gh  p_spyj  p_spjl  p_zt  p_errmsg
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_ywkhid", wbId);//
		map.put("p_spslid", spId);
		map.put("p_splcid", lcId);
		map.put("p_dwbm", sprDwbm);
		map.put("p_bmbm", sprBmbm);
		map.put("p_gh", sprGh);
		map.put("p_mc", sprMc);
		map.put("p_spyj", spyj);
		map.put("p_spjl", spJl);
		
		String msg = ywgzkhSpService.updateYwgzYyLc(map);
		if (StringUtils.isNotEmpty(msg)) {
			map.put("msg", msg);
			return Constant.GSON.toJson(map);
		}
		
		return Constant.GSON.toJson(map);
	}

	/**
	 * 年度考核活动案管部门异议申请成功后进行考核活动异议审批，即单位审批（全由案管操作），基层院--市院--省院
	 * @param request 请求对象
	 * @return json字符串对象
	 */
	@RequestMapping(value = "/yyFqAuditStart")
	@ResponseBody
	public String yyFqAuditStart(HttpServletRequest request) {
		
		String jdlx = request.getParameter("jdlx");// 节点类型
		String lcjd = request.getParameter("lcjd");// 流程节点
		String sprdwbm = request.getParameter("sprdwbm");// 审批人单位编码
		String sprbmbm = request.getParameter("sprbmbm");//审批人部门编码
		String sprgh = request.getParameter("sprgh");// 审批人工号
		String spstid = request.getParameter("ywkhid");// 审批实体ID--考核ID
		String spyj = request.getParameter("spyj");// 审批意见
		String clnr = request.getParameter("clnr");// 处理内容
		String lclx = request.getParameter("lclx");//流程类型
		List<Jdlc> jdlcList = null;
		try {
			jdlcList = spService.getMbByLcjd(jdlx, lcjd);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		Jdlc jdlc = jdlcList.get(0);
		
		RYBM ry = (RYBM) request.getSession().getAttribute("ry");	
		String dlrdwbm = ry.getDwbm();// 登录人单位编码
		String dlrbmbm = ry.getBmbm();//登录人部门编码
		String dlrgh = ry.getGh();// 登录人工号
		
		try {
			clnr = URLDecoder.decode(clnr, "utf-8");
		} catch (Exception e1) {
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String fqhcshlcid = null;
		Map<String, Object> swmap = new HashMap<String, Object>();
		swmap.put("clnr", clnr);
		swmap.put("cllx", jdlc.getLclx());
		swmap.put("wbid", spstid);
		String strLclx = "";
		
	    if("10".equals(jdlc.getLclx())){
			strLclx = "异议审批开始";
		}
	    
	    if (jdlc!=null &&"0".equals(jdlc.getJdzt())) {// 如果此节点状态是0，表示此节点是发起审批
			String firstmsg = null;
			String secondmsg = null;
			if(spyj==null||"undefined".equals(spyj)){
				spyj="";
			}
			// 插入一条自己的审批实例信息
			Map mapfirst = spService.addSplcslFirst(dlrdwbm, dlrbmbm, dlrgh, spyj,jdlc.getLclx(), "0", spstid, "", lcjd,jdlx,jdlc.getCljsxz());
			firstmsg = (String) mapfirst.get("Y");
			String lcid = (String) mapfirst.get("p_lcid");
			fqhcshlcid = lcid;
			if ("1".equals(firstmsg)) {
				Map khMap=ywgzkhSpService.getKhById(spstid);
				
				/*//市院部门(案管)领导审批完后就提交到省院了
				if("3".equals(khMap.get("KHDWJB"))){
					jdlcList = spService.getMbByLcjd("951","95002");
					jdlc=jdlcList.get(0);
				}
				//调用servcie查询当前登录人的上级单位以及案管部门
				DWBM dwbm = ywgzkhSpService.getSjDwbmAndAgByDwbm(dlrdwbm);
				String dlrSjDw = dwbm.getDwbm();
				String dlrSjBm = dwbm.getBmbm();
				// 插入一条下一个审批人的审批实例信息
				if(!StringUtils.isEmpty(jdlc.getXyclbm())){
					dlrSjBm = jdlc.getXyclbm();
				}*/
				secondmsg = spService.addSplcsl(sprdwbm, sprbmbm,sprgh,spyj,jdlc.getLclx(), "1", spstid,jdlc.getXycljs(),StringUtils.EMPTY,jdlc.getXyjd(), lcid,jdlc.getCljsxz());
				
				if ("1".equals(secondmsg)) {
					//将处理内容存入审批事务表
					swmap.put("lcid", lcid);
					sfdaspService.insertClsw(swmap);
					
					/*//修改业务类型表的状态
					if (StringUtils.isNotEmpty(spstid)) {
						
						Map<String,Object> ywgzkhYySpMap = new HashMap<String,Object>();
						ywgzkhYySpMap.put("p_khid", spstid);
						ywgzkhYySpMap.put("p_yyzt", yyzt);
						ywgzkhYySpMap.put("p_yysm", yysm);
						ywgzkhYySpMap.put("p_lclx", lclx);
						ywgzkhYySpMap.put("p_jdlx", jdlx);
						ywgzkhYySpMap.put("p_lcjd", lcjd);
						ywgzkhYySpMap.put("p_lcid", lcid);

						//更新YX_YWGZ_YWLXKH的状态和修改时间
						String msg = ywgzkhSpService.updateYwgzkhYYSpZt(ywgzkhYySpMap);
					}*/
					
					//记录日志
					logService.info(Constant.CURRENT_USER.get().getDwbm(),Constant.CURRENT_USER.get().getGh(),Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "发起"+strLclx+"审批");
					resultMap.put("status", "1");
				} else {
					//记录日志
					logService.error(Constant.CURRENT_USER.get().getDwbm(),Constant.CURRENT_USER.get().getGh(),Constant.CURRENT_USER.get().getMc(),Constant.RZLX_CZRZ, "发起"+strLclx+"审批失败");
					resultMap.put("status", "0");
				}
			} else {
				//记录日志
				logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "发起"+strLclx+"审批失败");
				resultMap.put("status", "0");
			}
	    }
		return Constant.GSON.toJson(resultMap);
	}

	/**
	 * 年度考核活动异议审批
	 * @param request 请求对象
	 * @return json字符串对象
	 */
	@RequestMapping(value = "/yyFqAuditLc")
	@ResponseBody
	public String yyFqAuditLc(HttpServletRequest request) {
		
		String spId =  request.getParameter("spid");// 审批表id
		String wbId = request.getParameter("wbid");// 被审批表的id
		String spLx = request.getParameter("splx");//审批类型
		String lcId = request.getParameter("lcid");//流程id
		String spJl = request.getParameter("spjl");//审批结论
		String spyj = request.getParameter("spyj");// 审批意见
		RYBM ry = (RYBM) request.getSession().getAttribute("ry");
		String sprDwbm = ry.getDwbm();//审批人单位编码
		String sprBmbm = ry.getBmbm();//审批人部门编码
		String sprGh = ry.getGh();//审批人工号
		String sprMc = ry.getMc();//审批人姓名
		
		//p_ywlxkhid  p_dwbm   p_bmbm  p_gh  p_spyj  p_spjl  p_zt  p_errmsg
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_ywkhid", wbId);//
		map.put("p_spslid", spId);
		map.put("p_splcid", lcId);
		map.put("p_dwbm", sprDwbm);
		map.put("p_bmbm", sprBmbm);
		map.put("p_gh", sprGh);
		map.put("p_mc", sprMc);
		map.put("p_spyj", spyj);
		map.put("p_spjl", spJl);
		
		String msg = ywgzkhSpService.updateYwgzYyFqSpLc(map);
		if (StringUtils.isNotEmpty(msg)) {
			map.put("msg", msg);
			return Constant.GSON.toJson(map);
		}
		
		return Constant.GSON.toJson(map);
	}
	
	
	/**
	 * 异议修改业务类型考核表
	 * @param request 请求对象
	 * @return json字符串对象
	 */
	@RequestMapping(value = "/yyupdateywlxkh")
	@ResponseBody
	public String yyupdateywlxkh(@RequestParam(value="ywlxkhId",required=true)String ywlxkhId,
			 @RequestParam(value="yyrgh",required=true)String yyrgh,
			 @RequestParam(value="yyrmc",required=true)String yyrmc,
			 @RequestParam(value="yysprgh",required=true)String yysprgh,
			 @RequestParam(value="yysprmc",required=true)String yysprmc,
			 @RequestParam(value="yysm",required=true)String yysm) {				
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("P_ID", ywlxkhId);
		map.put("P_YYRMC", yyrmc);
		map.put("P_YYRGH",yyrgh );
		map.put("P_YYSPRMC",yysprmc);
		map.put("P_YYSPRGH",yysprgh );
		map.put("P_YYSM", yysm);
		map.put("P_YYFJ", yysm);
		map.put("P_ERRMSG", StringUtils.EMPTY);
		String msg= ywgzkhSpService.updateywlxkh(map);
		map.clear();
		map.put("msg", msg);
		return Constant.GSON.toJson(map);
	}

	/**
	 * 发送Email，form提交
	 *
	 * @param Email
	 * @param title
	 * @param text
	 * @param files
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/uploadFile")
	@ResponseBody
	public void uploadFile(String ywlxkhId,
						   String yyrgh,
						   String yyrmc,
						   String yysprgh,
						   String yysprmc,
						   String yysqAgSm,
						   @RequestParam(value = "files", required = false) MultipartFile[] files,
						   HttpServletRequest request, HttpServletResponse response) throws Exception {

		//异议文件地址
		//String path = request.getSession().getServletContext().getRealPath(File.separator + "yywj");
		//创建一个数组接收复制的文件地址		
		//String[] fileStr = new String[files.length];
		//获取配置盘路径并创建yywj（ 异议文件夹）
		String path = xtfjpathservice.getPath().trim()+"yywj";		
		String[] fileStr = new String[files.length];
		int error = 0;
		Map<String, String> map = new HashMap<>();
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
		String[] childrenFile = file.list();
		if (childrenFile.length == 0) {
			map.put("", "");
		} else {
			for (String str : childrenFile) {
				map.put(str, "");
			}
		}
		for (int i = 0; i < files.length; i++) {
			//判空
			if (files[i].isEmpty()) {
				error++;
				continue;
			}
			String fileName = files[i].getOriginalFilename();
			String fileName2 = fileName;

			//限制格式
//		if (fileName.endsWith(".xlsx") || fileName.endsWith(".xls")) {
			if (map.containsKey(fileName)) {
				int num = 1;
				String start = fileName.split("\\.")[0];
				String end = fileName.split("\\.")[1];
				while (true) {
					fileName2 = start + "(" + num + ")." + end;
					if (!map.containsKey(fileName2)) {
						break;
					}
					num++;
				}

			}

			File dir = new File(path, fileName2);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			files[i].transferTo(dir);
			fileStr[i] = path + File.separator + fileName2;
		}
		//判断是否文件全为空（有一个不为空就可以继续）
		if (error == files.length) {
			fileStr = null;
		}
		String yyfj = "";
		if(fileStr!=null){
			for (int i = 0; i < fileStr.length; i++) {
				if(fileStr[i]==null){
					continue;
				}
					yyfj += fileStr[i] + ",";
			}
			yyfj.substring(0,yyfj.length()-1);
		}
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("P_ID", ywlxkhId);
		map2.put("P_YYRMC", yyrmc);
		map2.put("P_YYRGH", yyrgh);
		map2.put("P_YYSPRMC", yysprmc);
		map2.put("P_YYSPRGH", yysprgh);
		map2.put("P_YYFJ", yyfj);
		map2.put("P_YYSM", yysqAgSm);
		map2.put("P_ERRMSG", StringUtils.EMPTY);
		String msg = ywgzkhSpService.updateywlxkh(map2);
		map2.clear();
			//返回消息给页面
			if ("操作成功！".equals(msg)) {
				response.getWriter().print("");
			} else {
				response.getWriter().print(msg);
			}
	}
	
	
	@RequestMapping("/deleteYwkh")
	@ResponseBody
	public String deleteYwkh(@RequestParam(value = "dwbmArr[]", required = true) List<String> dwbmArr,
						   @RequestParam(value = "lxArr[]", required = true) List<String> lxArr,
						   @RequestParam(value = "year", required = false) String year,
						   @RequestParam(value = "startDate", required = true) String startDate,
						   @RequestParam(value = "endDate", required = true) String endDate,
						   HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		StringBuilder ksrq = new StringBuilder();  //开始日期
    	StringBuilder jsrq = new StringBuilder();  //结束日期
    	int ksYear = Integer.parseInt(startDate.split("-")[0]);
    	int ksMonth = Integer.parseInt(startDate.split("-")[1]);
    	ksrq.append(ksYear).append("-").append(ksMonth).append("-").append(Constant.NUM_1);
    		
    	int jsYear = Integer.parseInt(endDate.split("-")[0]);
    	int jsMonth = Integer.parseInt(endDate.split("-")[1]);
    	int jsDay = DateUtil.getDayByYearAndMonth(jsYear,jsMonth);
    	jsrq.append(jsYear).append("-").append(jsMonth).append("-").append(jsDay);
		
    	//先查询是否有该时间区间的考核
    	List<Ywgzkh> khList = ywgzkhSpService.getYwkhByDate(ksrq.toString(),jsrq.toString());
    	
    	if(khList!=null&&khList.size()>0) {
    		Integer resMsg = ywgzkhSpService.deleteYwkh(dwbmArr,lxArr,year,ksrq.toString(),jsrq.toString());
    		if(resMsg>0) {
    			resMap.put("msg", "操作成功！");
    		}
    	}else {
    		resMap.put("msg", "未创建该时间段的业务考核！");
    	}
		return Constant.GSON.toJson(resMap);
	}
	
}
