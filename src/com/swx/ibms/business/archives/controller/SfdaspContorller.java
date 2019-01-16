package com.swx.ibms.business.archives.controller;

import com.swx.ibms.business.archives.service.SfdaspService;
import com.swx.ibms.business.common.bean.Jdlc;
import com.swx.ibms.business.common.bean.Splc;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.performance.service.GrjxService;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.system.controller.XTGLController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;



/**
 * @author 朱春雨
 *
 */
@RequestMapping("/sfdasp")
@Controller
public class SfdaspContorller {
	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(XTGLController.class);
	/**
	 * 审批管理业务层
	 */
	@Resource
	private SfdaspService sfdaspService;
	
	/**
	 * 个人绩效服务接口
	 * */
	@Resource
	private GrjxService grjxService;

	/**
	 * 审批操作控制层
	 * @param request 请求
	 * @return 增加审批实例
	 */
	public @ResponseBody String addSP(HttpServletRequest request) {
		
		RYBM ry=(RYBM)request.getSession().getAttribute("ry");
		String dwbm1 = ry.getDwbm();
		String rybm = ry.getGh();
		String spyj = request.getParameter("spyj");
		String lx = request.getParameter("lx");
		String id = request.getParameter("wbid");
		String type=request.getParameter("type");
		String dwbm2=ry.getDwbm();
		String spr=request.getParameter("spr");
		String clnr=request.getParameter("clnr");
		if ("1".equals(type)) {
			Map<String,Object> map1 = new HashMap<String,Object>();
			map1.put("spdw", dwbm1);
			map1.put("spr", rybm);
			map1.put("spyj", spyj);
			map1.put("splx", lx);
			map1.put("spzt","2");
			map1.put("spid", id);
			String count1 = null;
			try {
				count1=sfdaspService.insertSplcsl(map1);
			} catch (Exception e) {
				LOG.error(StringUtils.EMPTY, e);
			}
			Map<String,Object> map2=new HashMap<String,Object>();
			map2.put("spdw", dwbm2);
			map2.put("spr", spr);
			map2.put("spyj", null);
			map2.put("splx", lx);
			map2.put("spzt","1");
			map2.put("spid", id);
			String count2=null;
			try {
				count2=sfdaspService.insertSplcsl(map2);
			} catch (Exception e) {
				LOG.error(StringUtils.EMPTY, e);
			}
			Map<String,Object> map3=new HashMap<String,Object>();
			map3.put("clnr",clnr);
			map3.put("cllx","2");
			map3.put("wbid",id);
			String count3=null;
			try {
				count3="1";//sfdaspService.insertClsw(map3);
			} catch (Exception e) {
				LOG.error(StringUtils.EMPTY, e);
			}
			Map<String,String> resultMap=new HashMap<String,String>();
			resultMap.put("reg","ty");
			if("111".equals(count1+count2+count3)){
				resultMap.put("status","1");
			}else {
				resultMap.put("status","0");
			}
			return Constant.GSON.toJson(resultMap);
		}else{
			Map<String,Object> map1 = new HashMap<String,Object>();
			map1.put("spdw",dwbm1);
			map1.put("spr", rybm);
			map1.put("spyj", spyj);
			map1.put("splx", lx);
			map1.put("spzt","3");
			map1.put("spid", id);
			String count1 = null;
			try {
				count1=sfdaspService.insertSplcsl(map1);
			} catch (Exception e) {
				LOG.error(StringUtils.EMPTY, e);
			}
			Map<String,Object> map2=new HashMap<String,Object>();
			map2.put("clnr",clnr);
			map2.put("cllx","2");
			map2.put("wbid",id);
			String count2=null;
			try {
				count2=sfdaspService.insertSplcsl(map2);
			} catch (Exception e) {
				LOG.error(StringUtils.EMPTY, e);
			}
			Map<String,String> resultMap=new HashMap<String,String>();
			resultMap.put("reg","bh");
			if("11".equals(count1+count2)){
				resultMap.put("status","1");
			}else{
				resultMap.put("status","0");
			}
			return Constant.GSON.toJson(resultMap);
		}
	}
	/**
	 * 展示审批流流程
	 * @param request 请求
	 * @return 返回审批流程
	 */
	@RequestMapping(value = "/showsplc", method = RequestMethod.POST)
	public @ResponseBody String showSplc(HttpServletRequest request){
		
		Splc splc=new Splc();
		String id = request.getParameter("wbid");
		String splx  = request.getParameter("splx");
		String lcid  = request.getParameter("lcid");
		splc.setSpstid(id);
		splc.setSplx(splx);
		splc.setLcid(lcid);
		List<Splc> resultlist=sfdaspService.showSplc(splc);
		return Constant.GSON.toJson(resultlist);
	}
	/**
	 * 查询是否审批流程实例是否有数据和查当前节点流程的数据
	 * @param request 请求 
	 * @return 返回结果
	 */
	@RequestMapping(value = "/sfylcsl", method = RequestMethod.POST)
	public @ResponseBody String sfylcsl(HttpServletRequest request){
		String id=request.getParameter("wbid");
		String jdzt=request.getParameter("jdzt");
		String lclx=request.getParameter("lclx");
		Map<String,String> map1=new HashMap<String,String>();
		map1.put("gdid", id);
		String Y = "0";
		try {
			Y=sfdaspService.select_sfysplcsl(map1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		Map<String,String> map2=new HashMap<String,String>();
		map2.put("lclx", lclx);
		map2.put("jdzt", jdzt);
		map2.put("cursor","");
		Jdlc jdlc = new Jdlc();
		try {
			jdlc=sfdaspService.select_jdlc(map2);
		} catch (Exception e) {

		}
		jdlc.setY(Y);
		return Constant.GSON.toJson(jdlc);
	}
	
	
	@RequestMapping(value="/sp", method = RequestMethod.POST)
	public @ResponseBody String newSp(HttpServletRequest request){
		
		RYBM ry=(RYBM)request.getSession().getAttribute("ry");
		String dwbm1 = ry.getDwbm();//登录人单位编码
		String rybm = ry.getGh();//登录人工号
		String spyj = request.getParameter("spyj");//审批意见
		String lx = request.getParameter("lx");//审批类型
		String id = request.getParameter("wbid");//审批实体ID
		String type=request.getParameter("type");//1.同意 2.驳回 3.标记为已读
		String isStart = request.getParameter("isStart");//是否是发起审批的标识
		String dwbm2=ry.getDwbm();//审批人单位编码（只能发送审批给同单位下的人员）
		String spr=request.getParameter("spr");//审批人工号
		String clnr=request.getParameter("clnr");//处理内容
		
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//如果是开始（即发起审批，则type值为空或者为undefined）
		
		
		if(!Objects.isNull(isStart)){
			//用于插入发起人的审批实例
			Map<String,Object> fqrmap = new HashMap<String,Object>();
			fqrmap.put("p_spdw", dwbm1);
			fqrmap.put("p_spr", rybm);
			fqrmap.put("p_spyj", spyj);
			fqrmap.put("p_splx", lx);
			fqrmap.put("p_spzt", "2");
			fqrmap.put("p_spstid", id);
			fqrmap.put("p_lcjd", StringUtils.EMPTY);
			fqrmap.put("p_jdlx", StringUtils.EMPTY);
			fqrmap.put("Y","");
			
			
			String msg1 = null;
			try {
				msg1= sfdaspService.addSplcsl(fqrmap);
			} catch (Exception e) {
				LOG.error(StringUtils.EMPTY, e);
			}
			
			
			if(msg1.equals("1")){
				//用于插入审批人的审批实例
				Map<String,Object> sprmap = new HashMap<String,Object> ();
				sprmap.put("p_spdw", dwbm2);
				sprmap.put("p_spr", spr);
				sprmap.put("p_spyj", StringUtils.EMPTY);
				sprmap.put("p_splx", lx);
				sprmap.put("p_spzt", "1");
				sprmap.put("p_spstid", id);
				sprmap.put("p_lcjd", StringUtils.EMPTY);
				sprmap.put("p_jdlx", StringUtils.EMPTY);
				sprmap.put("Y","");
				
				
				String msg2 = null;
				try {
					msg2 = sfdaspService.addSplcsl(sprmap);
				} catch (Exception e) {
					LOG.error(StringUtils.EMPTY, e);
				}
				
				
				if(msg2.equals("1")){
					
					//插入发起人和审批人的审批实例后 插入一条处理事务的信息
					Map<String,Object> swmap=new HashMap<String,Object>();
					swmap.put("clnr",clnr);
					swmap.put("cllx","2");
					swmap.put("wbid",id);
					String count2=null;
					
					
					try {
						count2=sfdaspService.insertClsw(swmap);
					} catch (Exception e) {
						LOG.error(StringUtils.EMPTY, e);
					}
					
					
					if(count2.equals("1")){
						resultMap.put("status", "1");
					}else{
						//插入事务失败，状态置为0
						resultMap.put("status", "0");
					}
					
				}else 
					//插入审批人的审批实例失败，状态置为0
					resultMap.put("status", "0");
			}else 
				//插入发起人的审批实例失败，状态置为0
				resultMap.put("status", "0");
			
			return Constant.GSON.toJson(resultMap);
		}else{//如果是审批（type值为不为空且不为undefined）
			
			//先更新原数据
			Map<String ,Object> fqrmap = new  HashMap<String ,Object> ();
			fqrmap.put("p_spdw", dwbm1);
			fqrmap.put("p_spr", rybm);
			
			
			if("1".equals(type)){//同意置为“4”同意结束
				fqrmap.put("p_dqspzt", "1");
				fqrmap.put("p_spzt", "4");
			}else if("2".equals(type)){//驳回置为“3”不同意
				fqrmap.put("p_dqspzt", "1");
				fqrmap.put("p_spzt", "5");
			}else if("3".equals(type)) {//标记为已读将状态置为“5”不同意结束
				fqrmap.put("p_dqspzt", "3");
				fqrmap.put("p_spzt", "5");
			}
			
			
			fqrmap.put("p_spstid", id);
			fqrmap.put("p_lcjd", StringUtils.EMPTY);
			fqrmap.put("p_jdlx", StringUtils.EMPTY);
			fqrmap.put("p_spyj", spyj);
			
			
			String msg = null;
			try {
				msg=sfdaspService.updateSplcsl(fqrmap);
			} catch (Exception e) {
				LOG.error(StringUtils.EMPTY,e);
			}
			
			
			if("1".equals(msg)){//如果更新成功
				if("1".equals(type)||"3".equals(type)){
					//属于同意或者标记为已读操作
					resultMap.put("status", "1");
				}
				if("2".equals(type)){//属于驳回，则再插入一条包含下一个处理人的信息
					//插入处理人的审批实例
					Map<String,Object> sprmap = new HashMap<String,Object> ();
					Map<String, String> ssrmap = new HashMap<String,String>();
					try {
						ssrmap = grjxService.getRyGhByDaid(id);
					} catch (Exception e) {
						LOG.error(StringUtils.EMPTY, e);
					}
				
					sprmap.put("p_spdw", ssrmap.get("dwbm"));
					sprmap.put("p_spr", ssrmap.get("gh"));
					sprmap.put("p_spyj", StringUtils.EMPTY);
					sprmap.put("p_splx", lx);
					sprmap.put("p_spzt", "3");
					sprmap.put("p_spstid", id);
					sprmap.put("p_lcjd", StringUtils.EMPTY);
					sprmap.put("p_jdlx", StringUtils.EMPTY);
					sprmap.put("Y",StringUtils.EMPTY);
					String msg2 = null;
					try {
						msg2 = sfdaspService.addSplcsl(sprmap);
					} catch (Exception e) {
						LOG.error(StringUtils.EMPTY, e);
					}
					if(msg2.equals("1")){//如果成功，则插入一个事务信息
//						Map<String,Object> swmap=new HashMap<String,Object>();
//						swmap.put("clnr",clnr);
//						swmap.put("cllx","2");
//						swmap.put("wbid",id);
//						String count2=null;
//						try {
//							count2=sfdaspService.insertClsw(swmap);
//						} catch (Exception e) {
//							LOG.error(StringUtils.EMPTY, e);
//						}
						
//						if(count2.equals("1")){
							resultMap.put("status", "1");
//						}else{
//							//插入事务失败，状态置为0
//							resultMap.put("status", "0");
//						}
					}else{//插入新审批人失败，状态置为0
						resultMap.put("status", "0");
					}
				}
			}else{//更新失败，状态置为0
				resultMap.put("status", "0");
			}
		}
		
		return Constant.GSON.toJson(resultMap);
	}
	
	
	/********************************************************/
				/***   统一审批流程控制    ***/
	/********************************************************/
	/**
	 * 获取当前页面的所处的流程节点信息
	 * @param request 
	 * 			请求参数可能有  jdlx 节点类型 或者 jdzt 节点状态 和 lclx 流程类型 
	 * @return 
	 * 			如果有则返回流程模板列表，如果没有则返回null
	 */
	@RequestMapping(value="/getmb")
	public @ResponseBody String getMb(HttpServletRequest request){
		
		String jdlx = request.getParameter("jdlx");//节点类型012
		String jdzt = request.getParameter("jdzt");//节点状态
		String lclx = request.getParameter("lclx");//流程类型
		
		List<Jdlc> jdlcList = null;
		
		try {
			jdlcList = sfdaspService.getLcmb(jdlx, jdzt, lclx);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		
		if(CollectionUtils.isEmpty(jdlcList)){
			return null;
		}else{
			resultMap.put("jdlcList", jdlcList);
			return Constant.GSON.toJson(resultMap);
		}
	}
	
	/**
	 * 审批
	 * @param request 请求参数
	 * @return json数据
	 */
	@RequestMapping(value="/audit")
	public @ResponseBody String audit(HttpServletRequest request){
		String current_jdlx = request.getParameter("jdlx");//当前流程的节点类型
		String current_lcjd = request.getParameter("lcjd");//当前流程的流程节点
		String current_xyjd = request.getParameter("xyjd");//当前流程的下一节点
		String current_jdzt = request.getParameter("jdzt");//当前流程的节点状态
		String current_ztbg = request.getParameter("ztbg");//当前流程操作完成后的状态变更情况
		String current_cljs = request.getParameter("");//dangqian
		
		RYBM ry =(RYBM)request.getSession().getAttribute("ry");
		
		String current_dw = ry.getDwbm();//操作发起人的单位编码
		String current_gh = ry.getGh();//操作发起人的工号
		String current_yj = request.getParameter("fqryj");//发起人的审批意见
		String current_spst = request.getParameter("spstid");//当前审批实体ID
		String current_splx = request.getParameter("splx");//当前审批类型
		String clnr = request.getParameter("clnr");//处理类容
		String cllx = "3";
// 		String wbid = request.getParameter("wbid");//外部ID
		String spdw = current_dw;//审批单位
		String spr = request.getParameter("spr");//审批人
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		
		if(StringUtils.isEmpty(current_xyjd)){//如果该流程没有下一节点信息，则表示该流程是最终的审批
			String info = null;
			try {
				info =sfdaspService.updateSplcsl(current_dw, current_gh, current_ztbg,  
						current_spst, current_yj,current_lcjd, current_jdlx);
			} catch (Exception e) {
				LOG.error(StringUtils.EMPTY, e);
			}
			if("1".equals(info)){
				resultMap.put("status", "1");
			}else{
				resultMap.put("status", "0");
			}
			
		}else{//如果有，则表示该流程是   发起审批    或者     审批通过后转交下一个审批人
			if("0".equals(current_jdzt)){//如果节点状态为0，则表示该节点是发起审批
				String firstmsg =null;
				String secondmsg = null;
				//插入一条自己的审批实例信息
				firstmsg = sfdaspService.addSplcsl(current_dw, current_gh, current_yj,
						current_splx, current_ztbg, current_spst, current_lcjd, current_jdlx);
				if("1".equals(firstmsg)){
					//插入一条下一个审批人的审批实例信息
					secondmsg = sfdaspService.addSplcsl(spdw, spr, "", 
							current_splx, "1", current_spst, "", current_xyjd);
					if("1".equals(secondmsg)){
						resultMap.put("status", "1");
					}else{
						resultMap.put("status", "0");
					}
				}else{
					resultMap.put("status", "0");
				}
				
			}else{//如果节点状态不为0，则表示该节点是审批通过后转交下一个审批人
				String updatemsg = null;
				String addmsg = null;
				updatemsg = sfdaspService.updateSplcsl(current_dw, current_gh, current_ztbg, 
						current_spst, current_yj, current_lcjd, current_jdlx);
				if("1".equals(updatemsg)){
					addmsg = sfdaspService.addSplcsl(spdw, spr, "", 
							current_splx, "1", current_spst, "", current_xyjd);
					if("1".equals(addmsg)){
						resultMap.put("status", "1");
					}else{
						resultMap.put("status", "0");
					}
					
				}else{
					resultMap.put("status", "0");
				}
			}
			
		}
		
		return Constant.GSON.toJson(resultMap);
	}
	
	/**
	 * 标记为已读
	 * @param request 请求参数
	 * @return json 数据
	 */
	@RequestMapping(value="/setread")
	public @ResponseBody String setRead(HttpServletRequest request){
		RYBM ry =(RYBM)request.getSession().getAttribute("ry");
		
		String current_dw = ry.getDwbm();//操作发起人的单位编码
		String current_gh = ry.getGh();//操作发起人的工号
		String current_spst = request.getParameter("spstid");
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		String msg = sfdaspService.updateSplcsl(current_dw, current_gh, "5", 
				current_spst, "", "", "");
		if("1".equals(msg)){
			resultMap.put("status", "1");
		}else{
			resultMap.put("status", "0");
		}
		
		return Constant.GSON.toJson(resultMap);
	}
	
	
	/**
	 * 根据审批实体ID获取对应最新的审批实例信息
	 * @param request 审批实体ID
	 * @return
	 */
	@RequestMapping(value="/getrecentsplcsl")
	public @ResponseBody String getRecentSplcsl(HttpServletRequest request){
		String spstid = request.getParameter("spstid");
		
		String[] resultArray = new String[2];
		resultArray  = sfdaspService.getrecentlcsl(spstid);
		
		Map<String ,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("lcjd", resultArray[0]);
		resultMap.put("jdlx", resultArray[1]);
		
		return Constant.GSON.toJson(resultMap);
	}
}
