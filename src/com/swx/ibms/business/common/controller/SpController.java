package com.swx.ibms.business.common.controller;


import com.swx.ibms.business.appraisal.bean.Ywlxmc;
import com.swx.ibms.business.archives.bean.DAGZGD;
import com.swx.ibms.business.archives.service.DahcService;
import com.swx.ibms.business.archives.service.SfdacjService;
import com.swx.ibms.business.archives.service.SfdaspService;
import com.swx.ibms.business.cases.service.SfdaAjxxService;
import com.swx.ibms.business.common.bean.Jdlc;
import com.swx.ibms.business.common.bean.MessageNotice;
import com.swx.ibms.business.common.bean.Splc;
import com.swx.ibms.business.common.bean.Splcsl;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.service.MessageNoticeService;
import com.swx.ibms.business.common.service.SpService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.performance.bean.HCPZ;
import com.swx.ibms.business.performance.bean.Ydkh;
import com.swx.ibms.business.performance.bean.ydkhqbtg;
import com.swx.ibms.business.performance.service.*;
import com.swx.ibms.business.rbac.bean.BMBM;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.rbac.service.LoginService;
import com.swx.ibms.business.system.bean.Bmzdpz;
import com.swx.ibms.business.system.service.BmzdpzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.*;

/**
 * 审批流程控制器
 *
 * @author 李治鑫
 * @since 2017-5-8
 */
@SuppressWarnings("all")
@RequestMapping("/sp")
@Controller
public class SpController {
	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(SpController.class);

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;
	/**
	 * 服务
	 */
	@Resource
	private SfdacjService sfdacjService;

	/**
	 *
	 */
	@Autowired
	private DahcService dahcService;

	/**
	 * 服务访问接口
	 */
	@Resource
	private SpService spService;

	/**
	 * 业务考核服务
	 */
	@Resource
	private YdkhService ydkhService;

	/**
	 * 司法档案审批服务
	 */
	@Resource
	private SfdaspService sfdaspService;

	/**
	 *  消息通知服务
	 */
	@Autowired
	private MessageNoticeService messageService;

	/**
	 * 核查配置服务
	 */
	@Resource
	private HCPZService hcpzservice;
	/**
	 * 个人绩效服务
	 */
	@Resource
	private GrjxService grjxService;

	@Resource
	private SfdaAjxxService sfdaAjxxService;

	/**
	 * 交叉审批节点值
	 */
	private String jcsp = "501";

	@Resource
	private BmzdpzService bmzdpzService;

	@Resource
	private GrjxYwkhfzService grjxYwkhfzService;

	@Resource
	private LoginService loginService;

	@Resource
	private XtGrjxRytypeService xtGrjxRytypeService;

	/**
	 * 通过审批单位编码，审批部门编码，审批人工号，审批实体ID，审批类型
	 * 查询最新的审批流程实例信息
	 * @param request 请求参数
	 * @return 最新的审批流程实例信息
	 */
	@RequestMapping(value = "/selectNewSpsl")
	public @ResponseBody String selectNewSpsl(HttpServletRequest request) {
		String spdw = request.getParameter("spdw");
		String spbm = request.getParameter("spbm");
		String spr = request.getParameter("spr");
		String spstid = request.getParameter("spstid");
		String splx = request.getParameter("splx");
		List<Splcsl> splcslList = null;
		try {
			splcslList = spService.selectNewSpsl(spdw, spbm, spr, spstid, splx);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		return Constant.GSON.toJson(splcslList);
	}

	/**
	 * 通过流程ID查询最新的审批流程实例
	 * @param request 请求参数
	 * @return 最新的审批流程实例
	 */
	@RequestMapping(value="/selectNewSpslByLcid")
	public @ResponseBody String selectNewSpslByLcid(HttpServletRequest request){
		String lcid = request.getParameter("lcid");//流程id
		List<Splcsl> splcslList = null;

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_lcid", lcid);
		map.put("p_errmsg", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);

		try {
			splcslList = spService.selectNewSpslByLcid(map);
		} catch (Exception e) {
			LOG.info(StringUtils.EMPTY,e);
		}

		return Constant.GSON.toJson(splcslList);
	}

	/**
	 * 通过节点类型查询流程模板信息
	 * @param request 请求参数
	 * @return 模板信息
	 */
	@RequestMapping(value = "/getmb")
	public @ResponseBody String getMb(HttpServletRequest request) {
		String jdlx = request.getParameter("jdlx");

		List<Jdlc> jdlcList = null;

		try {
			jdlcList = spService.getMbByJdlx(jdlx);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (CollectionUtils.isEmpty(jdlcList)) {
			return null;
		} else {
			resultMap.put("jdlcList", jdlcList);
			return Constant.GSON.toJson(resultMap);
		}
	}

	/**
	 * 审批
	 * @param request 请求参数
	 * @return 执行信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/audit")
	@ResponseBody
	public String audit(@RequestParam(value="jdlx",required=true)String jdlx,
						@RequestParam(value="lcjd",required=true)String lcjd,
						@RequestParam(value="sprdwbm",required=true)String sprdwbm,
						@RequestParam(value="sprbmbm",required=false)String sprbmbm,
						@RequestParam(value="sprgh",required=true)String sprgh,
						@RequestParam(value="spstid",required=true)String spstid,
						@RequestParam(value="spyj",required=false)String spyj,
						@RequestParam(value="clnr",required=false)String clnr,
						HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		request.setCharacterEncoding("utf-8");
		clnr = URLDecoder.decode(clnr, "utf-8");
		spyj = this.getSpyjStr(spyj);
		String fqhcshlcid = StringUtils.EMPTY;  //发起核查流程id
		String logMsgText = StringUtils.EMPTY; //日志消息的部分文本
//		String jdlx = request.getParameter("jdlx");// 节点类型
//		String lcjd = request.getParameter("lcjd");// 流程节点
//		String sprdwbm = request.getParameter("sprdwbm");// 审批人单位编码
//		String sprbmbm = request.getParameter("sprbmbm");//审批人部门编码
//		String sprgh = request.getParameter("sprgh");// 审批人工号
//		String spstid = request.getParameter("spstid");// 审批实体ID
//		String spyj = request.getParameter("spyj");// 审批意见
//		String clnr = request.getParameter("clnr");// 处理内容
		RYBM ry = (RYBM) request.getSession().getAttribute("ry");
		String dlrdwbm = ry.getDwbm();// 登录人单位编码
		String dlrbmbm = ry.getBmbm();//登录人部门编码
		String dlrgh = ry.getGh();// 登录人工号

		//查询该节点流程 YX_SFDA_JDLC
		Jdlc jdlc =  this.getJdlcByParams(jdlx,lcjd);
		// 1：公示 ，2：档案-个人信息，3：个人绩效，4：荣誉技能，5：问题反馈，6：取消公示，
		// 7：档案封存审批   9:年度工作考核  12 司法档案-办案业绩   14 ：司法档案-其他
		// 15：司法档案-业务研修
		String jdlcLclx = jdlc.getLclx();

		//消息通知的文本表体内容
		String strLclx = this.getLclxStr(jdlcLclx);

		Map<String, Object> swmap = new HashMap<String, Object>();
		swmap.put("clnr", clnr);
		swmap.put("cllx", jdlc.getLclx());
		swmap.put("wbid", spstid);


		//根据节点状态判断流程是否结束
		// 节点状态: 0:开始 1:过程中 2:结束
		if ("0".equals(jdlc.getJdzt())) {
			// 1、如果此节点状态是0，表示此节点是发起审批
			String firstmsg = StringUtils.EMPTY;
			String secondmsg = StringUtils.EMPTY;

			// 向审批表【YX_SFDA_SPLCSL】插入一条发起人的审批信息
			Map mapfirst = spService.addSplcslFirst(dlrdwbm, dlrbmbm, dlrgh, spyj, jdlc.getLclx(), "0", spstid, "", lcjd,jdlx,jdlc.getCljsxz());
			firstmsg = (String) mapfirst.get("Y");
			String lcid = (String) mapfirst.get("p_lcid");
			fqhcshlcid = lcid;

			if ("1".equals(firstmsg)) {
				if(StringUtils.isBlank(sprbmbm)){
					if(StringUtils.isNotBlank(jdlc.getXyclbm())){
						sprbmbm = spService.getBmbmByBmys(dlrdwbm, jdlc.getXyclbm()); //根据单位编码、部门映射查询部门信息
					}
				}
				// 向审批表【YX_SFDA_SPLCSL】插入下一个审批人的审批信息
				secondmsg = spService.addSplcsl(sprdwbm, sprbmbm, sprgh, "", jdlc.getLclx(), "1", spstid, jdlc.getXycljs(), "", jdlc.getXyjd(), lcid, jdlc.getCljsxz());

				if ("1".equals(secondmsg)) {
					swmap.put("lcid", lcid);
					sfdaspService.insertClsw(swmap);  //添加处理事务
					resultMap.put("status", "1");
					logMsgText = "审批";
				} else {
					logMsgText = "审批失败";
					resultMap.put("status", "0");
				}

			} else {
				logMsgText = "审批失败";
				resultMap.put("status", "0");
			}
			//记录日志
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "发起"+strLclx+logMsgText);
		} else if (StringUtils.isBlank(jdlc.getXyjd())) {
			if(!"3".equals(jdlc.getLclx()) && !"5".equals(jdlc.getLclx())){
				//档案发起消息服务
				if(StringUtils.isNoneBlank(spstid)){
					DAGZGD dagzgd = new DAGZGD();
					dagzgd.setId(spstid);
					List<DAGZGD> list = sfdacjService.selectDagzId(dagzgd);
					if(null != list){
						//添加消息
						this.addMessage(list.get(0).getSsrdwbm(), list.get(0).getSsr(), clnr+jdlc.getSm(), strLclx+"审批", ry.getDlbm());
					}
				}
			}

			String info = StringUtils.EMPTY;
			//个人绩效的审批流程
			if("3".equals(jdlc.getLclx())){

				List<Ydkh> list =  ydkhService.getYdkhById(spstid);

				if (!CollectionUtils.isEmpty(list)&&list.size()>0) {
					//添加消息 YX_XX_NOTICE表
					this.addMessage(list.get(0).getDwbm(),list.get(0).getGh(), clnr+jdlc.getSm(), strLclx+"审批", ry.getDlbm());
				}
			}

			// 如果此节点没有下一节点，表示此节点是最终节点
			try {
				info = spService.updateSplcsl(dlrdwbm, dlrbmbm, dlrgh, spyj, jdlc.getLclx(), jdlc.getZtbg(), spstid, null,lcjd, jdlx);
				if ("30101".equals(jdlc.getLcjd())) {
					grjxService.insertZgjcfdata(spstid, dlrdwbm);
					List<String> lists = hcpzservice.getndjd(spstid);
					String nd = lists.get(0); // 年度
					int year = Integer.parseInt(nd);
					String jd1 = lists.get(1); // 季度
					int jd=Integer.parseInt(jd1);
					String ywlx = lists.get(2); // 业务类型
					String dwjb = (String) hcpzservice.getdwjb(dlrdwbm).get("p_dwjb"); // 单位级别
					String dwbmtwo=dlrdwbm.substring(0,2);
					grjxService.selectGrjxYdkhlist(year, jd, ywlx, dwjb, dwbmtwo);
				}
			} catch (Exception e) {
				LOG.error(StringUtils.EMPTY, e);
			}
			if ("1".equals(info)) {
				if("3".equals(jdlc.getLclx())){
					spService.setAudit(spstid);
				}else if("1".equals(jdlc.getLclx())){
					spService.updateGszt(spstid, "1");
				}else if("6".equals(jdlc.getLclx())){
					spService.updateGszt(spstid, "2");
				}
				resultMap.put("status", "1");
			} else {
				resultMap.put("status", "0");
			}

		} else {// 其余类型都为转交下一个处理人

			if(!"3".equals(jdlc.getLclx()) && !"5".equals(jdlc.getLclx())){
				if(!"".equals(spstid)||null!=spstid){			//档案发起消息服务
					DAGZGD dagzgd = new DAGZGD();
					dagzgd.setId(spstid);
					List<DAGZGD> list = sfdacjService.selectDagzId(dagzgd);
					if(null != list){
						this.addMessage(list.get(0).getSsrdwbm(), list.get(0).getSsr(), clnr+jdlc.getSm(), strLclx+"审批", ry.getDlbm());
					}
				}
			}

			if("3".equals(jdlc.getLclx())){

				List<Ydkh> list =  ydkhService.getYdkhById(spstid);

				if (!CollectionUtils.isEmpty(list)) {
					this.addMessage(list.get(0).getDwbm(), list.get(0).getGh(), clnr+jdlc.getSm(), strLclx+"审批", ry.getDlbm());
				}
			}

			String updatemsg = null;
			String addmsg = null;
			String lcid = request.getParameter("lcid");
			updatemsg = spService.updateSplcsl(dlrdwbm, dlrbmbm, dlrgh, spyj,jdlc.getLclx(), jdlc.getZtbg(), spstid, null,lcjd, jdlx);
			if ("1".equals(updatemsg)) {
				// 交叉核查判断
				List<String> db = (List<String>) request.getSession().getAttribute("bmjs");
				String[] dbs = db.get(0).split(",");
				Splc splc;
				String dwbm = null; // 审查单位编码
				String bmbm = null; // 审查部门编码
				String bdwbm = dbs[0]; // 被审查单位编码
				String bbmbm = dbs[1]; // 被审查部门编码
				// 是否跳过交叉审节点标记
				boolean isLoop = true;
				// 交叉审批特殊处理,是否跳过节点,判断是否为绩效审查(splx = 3).否则跳过交叉审查
				if (jcsp.equals(jdlc.getXyjd()) && "3".equals(jdlc.getLclx())) {
					HCPZ hcpz = null;
					List<HCPZ> hcpzs =  hcpzservice.getbmlistbyspstid(spstid);
					for(HCPZ h:hcpzs){
						for(String s:db){
							String bmbm11 = s.split(",")[1];
							if(bmbm11.equals(h.getBhcbmbm())){
								bbmbm = bmbm11;
								hcpz = h;
							}
						}
					}
//					if(!CollectionUtils.isEmpty(hcpzs)){
//						hcpz = new HCPZ();
//						hcpz.setBhcdwbm(bdwbm);
//						hcpz.setBhcbmbm(bbmbm);
//						// 交叉审批,查找对应单位,部门,(需要部门编码,单位编码)
//						hcpz = hcpzservice.selectone(hcpz);
//					}
					// 有核查配置,可以交叉审批,不跳过
					if (hcpz != null) {
						isLoop = false;
						dwbm = hcpz.getHcdwbm();
						bmbm = hcpz.getHcbmbm();
						jdlc.setXycljs("JC");
					} else {
						jdlc.setXyjd("301");
						jdlc.setXyclbm("0102");
					}
				}else if(jcsp.equals(jdlc.getXyjd())){
					jdlc.setXyjd("301");
					jdlc.setXyclbm("0102");
				}
				// 交叉审批完成后,移回原单位处理.
				if ("501".equals(jdlc.getJdlx())) {
					splc = sfdaspService.getlcydwlist(lcid);
					dlrdwbm = splc.getSpdw();
				}

				if(!isLoop){
					addmsg = spService.addSplcsl(dwbm, bmbm, null, "", "3","1", spstid, jdlc.getXycljs(), "",jdlc.getXyjd(), lcid,jdlc.getCljsxz());
				}else if(StringUtils.isNotEmpty(jdlc.getXyclbm())){
					if("1".equals(jdlc.getLclx())||"6".equals(jdlc.getLclx())||"7".equals(jdlc.getLclx())){
						//如果属于公示申请或者取消公示申请或者封存申请
						RYBM fqr = spService.getFqr(lcid);

						if(StringUtils.isEmpty(jdlc.getXyclbm())&&StringUtils.isEmpty(sprbmbm)){
							sprbmbm = spService.getBmbmByGh(sprdwbm, fqr.getGh());
						}else if(!StringUtils.isEmpty(jdlc.getXyclbm())
								&&StringUtils.isEmpty(sprbmbm)){
							sprbmbm = spService.getBmbmByGh(sprdwbm, fqr.getGh());
						}

						addmsg = spService.addSplcsl(dlrdwbm, sprbmbm, fqr.getGh(), "", jdlc.getLclx(), "1", spstid,jdlc.getXycljs(), "", jdlc.getXyjd(), lcid,jdlc.getCljsxz());
					}else{
						//如果下一处理部门不为空，则表示由某个或某些部门处理

						String bmbmstr = spService.getBmbmByBmys(dlrdwbm, jdlc.getXyclbm());

						Map<String,Object> bmzdpzParam = new HashMap<String,Object>();
						bmzdpzParam.put("p_bmys", jdlc.getXyclbm());
						bmzdpzParam.put("p_splx", jdlc.getLclx());
						bmzdpzParam.put("p_dwbm", dlrdwbm);

						if(bmzdpzService.isExistByDwbm(bmzdpzParam)){
							List<Bmzdpz> bmzdpzList = bmzdpzService.getPzByDwbm(bmzdpzParam);
							if(CollectionUtils.isNotEmpty(bmzdpzList)){
								Bmzdpz pzObj = bmzdpzList.get(0);
								bmbmstr = pzObj.getBmbm();
								sprgh = pzObj.getGh();
							}
						}

						addmsg = spService.addSplcsl(dlrdwbm, bmbmstr, sprgh, "",jdlc.getLclx(), "1", spstid, jdlc.getXycljs(),"", jdlc.getXyjd(), lcid,jdlc.getCljsxz());
					}
				}else{
					if("8".equals(jdlc.getLclx())){
						//如果属于档案核查流程
						RYBM fqr = spService.getFqr(lcid);

						if(StringUtils.isEmpty(jdlc.getXyclbm())&&StringUtils.isEmpty(sprbmbm)){
							sprbmbm = spService.getBmbmByGh(fqr.getDwbm(), fqr.getGh());
						}else if(!StringUtils.isEmpty(jdlc.getXyclbm())
								&&StringUtils.isEmpty(sprbmbm)){
							sprbmbm = spService.getBmbmByGh(fqr.getDwbm(), fqr.getGh());
						}

						addmsg = spService.addSplcsl(dlrdwbm, sprbmbm, fqr.getGh(), "", jdlc.getLclx(), "1", spstid,jdlc.getXycljs(), "", jdlc.getXyjd(), lcid,jdlc.getCljsxz());
					}else{
						//否则表示会指定具体的处理人

						if(StringUtils.isEmpty(jdlc.getXyclbm())&&StringUtils.isEmpty(sprbmbm)){
							sprbmbm = spService.getBmbmByGh(sprdwbm, sprgh);
						}
						addmsg = spService.addSplcsl(sprdwbm, sprbmbm, sprgh, "",jdlc.getLclx(), "1", spstid, jdlc.getXycljs(),"", jdlc.getXyjd(), lcid,jdlc.getCljsxz());

					}
				}

				if ("1".equals(addmsg)) {
					resultMap.put("status", "1");
				} else {
					resultMap.put("status", "0");
				}

			} else {
				resultMap.put("status", "0");
			}
		}
		resultMap.put("fqhcshlcid", fqhcshlcid);
		return Constant.GSON.toJson(resultMap);
	}

	private void addMessage(String dwbm,String gh,String content,String name,String dlbm) {
		MessageNotice message = new MessageNotice();
		message.setDwbm(dwbm);
		message.setGh(gh);
		message.setContent(content);
		message.setName(name);
		message.setOperator(dlbm);
		messageService.insertData(message);
	}

	private String getSpyjStr(String spyj) {
		String spyjStr = StringUtils.EMPTY;
		if(spyj!=null||!"undefined".equals(spyj)){
			spyjStr=spyj;
		}
		return spyjStr;
	}

	private String getLclxStr(String jdlcLclx) {
		String resultStr = StringUtils.EMPTY;
		if("1".equals(jdlcLclx)){
			resultStr = "司法档案-公示";
		}else if("2".equals(jdlcLclx)){
			resultStr = "司法档案-个人信息";
		}else if("3".equals(jdlcLclx)){
			resultStr = "个人绩效";
		}else if("4".equals(jdlcLclx)){
			resultStr = "司法档案-荣誉奖励";
		}else if("5".equals(jdlcLclx)){
			resultStr = "问题反馈";
		}else if("6".equals(jdlcLclx)){
			resultStr = "司法档案-取消公示";
		}else if("7".equals(jdlcLclx)){
			resultStr = "司法档案-封存";
		}else if("8".equals(jdlcLclx)){
			resultStr = "司法档案-核查";
		}else if("12".equals(jdlcLclx)){
			resultStr = "司法档案-办案业绩";
		}else if("15".equals(jdlcLclx)) {
			resultStr = "司法档案-业务研修";
		}else if("14".equals(jdlcLclx)) {
			resultStr = "司法档案-其他情况";
		}else if("16".equals(jdlcLclx)) {
			resultStr = "司法档案-责任追究";
		}else if("17".equals(jdlcLclx)) {
			resultStr = "司法档案-接受监督";
		}
		return resultStr;
	}

	private Jdlc getJdlcByParams(String jdlx, String lcjd) {
		List<Jdlc> jdlcList = new ArrayList<Jdlc>();
		try {
			jdlcList = spService.getMbByLcjd(jdlx, lcjd);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("【查询流程节点错误！】", e);
		}
		return jdlcList.get(0);
	}

	/**
	 * 设置已读
	 * @param request 请求参数
	 * @return 执行信息
	 */
	@RequestMapping(value = "/setread")
	public @ResponseBody String setRead(HttpServletRequest request) {
		RYBM ry = (RYBM) request.getSession().getAttribute("ry");

		String dlrdwbm = ry.getDwbm();// 登录人单位编码
		String dlrgh = ry.getGh();// 登录人的工号
		String spstid = request.getParameter("spstid");// 审批实体ID
		String splx = request.getParameter("splx");// 审批类型
		String agsfty = request.getParameter("agsfty");// 案管是否同意
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String msg = spService.updateSplcsl(dlrdwbm, null, dlrgh,
				null, splx, "5", spstid, null, null, null);

		if("8".equals(splx) ){//案管同意核查，将档案变为初始状态
			if("同意结束".equals(agsfty)){
				dahcService.dahcupdate(spstid);
			}
		}
		if ("1".equals(msg)) {
			resultMap.put("status", "1");
		} else {
			resultMap.put("status", "0");
		}

		return Constant.GSON.toJson(resultMap);
	}

	/**
	 * 根据单位编码，工号，年，月查询id
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param year 年度
	 * @param season 季度
	 * @return 月度考核ID
	 */
	@ResponseBody
	@RequestMapping(value = "/selectYdkhid")
	public String selectYdkhid(String dwbm, String gh, int year, int season) {
		String ywkkhId = ydkhService.selectJdkhid(dwbm, gh, year, season);
		return ywkkhId;
	}
	/*public String selectYdkhid(HttpServletRequest request) { // String dwbm, String gh, int year, int season
			String dwbm = request.getParameter("dwbm");
			String gh = request.getParameter("gh");
			Integer year = Integer.parseInt(request.getParameter("year"));
			Integer season = Integer.parseInt(request.getParameter("season"));
			String ywkkhId = ydkhService.selectJdkhid(dwbm, gh, year, season);
			return Constant.GSON.toJson(ywkkhId);
	}*/
	/**
	 * 根据单位编码，工号，年、季度，业务类型查询姓名和分数
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param year 年度
	 * @param season 季度
	 * @param ywlx 业务类型
	 * @return 姓名和分数
	 */
	@RequestMapping(value = "getNameAndScroe")
	@ResponseBody
	public String getNameAndScore(String dwbm, String gh, String year, String season,String ywlx) {
		Map<String, Object> nameAndScore = ydkhService.getNameAndScore(dwbm, gh, year, season,ywlx);
		return Constant.GSON.toJson(nameAndScore);
	}


	/**
	 * 获取业务类型
	 * @param request 请求参数
	 * @return 业务类型
	 */
	@RequestMapping(value = "/getywlx")
	@ResponseBody
	public String getYwlxByYdkhid(HttpServletRequest request) {
		String ssrdwbm = request.getParameter("ssrdwbm");
		String ssrgh = request.getParameter("ssrgh");
		String ssryear = request.getParameter("ssryear");
		String ssrseason = request.getParameter("ssrseason");
		int year = Integer.parseInt(ssryear);
		if(ssrseason.length()>1 && !"undefined".equals(ssrseason)){
			ssrseason = ssrseason.substring(1, 2);
		}

		int season = 0;
		if (!"undefined".equals(ssrseason)) {
			season = Integer.parseInt(ssrseason);
		}

		List<Ywlxmc> ywlxList = new ArrayList<Ywlxmc>();

		String ydkhid = ydkhService.selectJdkhid(ssrdwbm, ssrgh, year, season);
		if (StringUtils.isEmpty(ydkhid)) {
			List<String> bmysList = spService.getBmysByGh(ssrdwbm, ssrgh);
			String[] ywlxarray = new String[bmysList.size()];
			for (int i = 0; i < bmysList.size(); i++) {
				ywlxarray[i] = spService.getYwlxByBmys(bmysList.get(i));
			}
			// String[] bmbmarray = spService.getDlrBmbm(request);
			// String[] ywlxarray = ydkhService.getYwlxByBmbm(bmbmarray);
			for (int i = 0; i < ywlxarray.length; i++) {
				String ywmc = spService.getYwmcByYwbm(ywlxarray[i]);
				Ywlxmc ywlxmcObj = new Ywlxmc();
				ywlxmcObj.setYwlx(ywlxarray[i]);
				ywlxmcObj.setYwmc(ywmc);
				ywlxList.add(ywlxmcObj);
			}
		} else {
			ywlxList = spService.getYwlxByYdkhid(ydkhid);
		}
		return Constant.GSON.toJson(ywlxList);
	}

	/**
	 * 获取节点类型，用以判断是否显示撤回审批申请
	 *
	 * @param request
	 *            审批实体ID 审批类型
	 * @return 节点类型
	 */
	@RequestMapping("/getjdlx")
	@ResponseBody
	public String getAllBySplx(@RequestParam(value="spstid",required=true)String spstid,
							   @RequestParam(value="splx",required=true)String splx,
							   HttpServletRequest request) {
//		String spstid = request.getParameter("spstid");
//		String splx = request.getParameter("splx");

		String str = spService.getAllBySplx(spstid, splx);

		return str;
	}

	/**
	 * 撤回审批申请
	 *
	 * @param request
	 *            审批实体ID 审批类型
	 * @return 执行信息
	 */
	@RequestMapping("/recall")
	@ResponseBody
	public String recallSp(@RequestParam(value="spstid",required=true)String spstid,
						   @RequestParam(value="splx",required=true)String splx,
						   HttpServletRequest request) {
//		String spstid = request.getParameter("spstid");
//		String splx = request.getParameter("splx");

		String str = spService.recallSp(spstid, splx);

		Integer ajztModifyResult = 0;
		if("1".equals(str)){
			ajztModifyResult = sfdaAjxxService.modifyStatusByAjId("2", spstid);
		}

		return str;
	}

	/**
	 * 判断是否审批通过
	 * @param request 请求参数
	 * @return 审批流程
	 */
	@RequestMapping("/issptg")
	@ResponseBody
	public String isSptg(HttpServletRequest request) {
		String spstid = request.getParameter("spstid");
		String splx = request.getParameter("splx");
		String lcid = request.getParameter("lcid");
		if ("undefined".equals(lcid)){
			lcid = null;
		}
		Splc splc = new Splc();
		try {
			splc = spService.isSptg(spstid, splx, lcid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constant.GSON.toJson(splc);
	}
	/**
	 * 如果是交叉考核节点
	 * @param request 请求参数
	 * @return 判断结果，是返回1，否返回0
	 */
	@RequestMapping("/getisjc")
	private @ResponseBody  String getjc(HttpServletRequest request){
		String spstid = request.getParameter("spstid");
		List<String> db = (List<String>) request.getSession().getAttribute("bmjs");
		String[] dbs = db.get(0).split(",");
		String dwbm = dbs[0]; // 登录人单位编码

		String jdlx = hcpzservice.getisjc(spstid, dwbm);

		if("501".equals(jdlx)){
			return "1";
		}
		return "0";
	}


	/**
	 * 判断是否审批通过
	 * @param request 请求参数
	 * @return 审批流程
	 */
	@RequestMapping("/getSpById")
	@ResponseBody
	public String getSpById(HttpServletRequest request) {
		String spid = request.getParameter("spid");
		Splcsl splc  = spService.getSpById(spid);

		return Constant.GSON.toJson(splc);
	}

	/**
	 * 通过审批实体ID和审批类型获取上一个审批流程实例的审批状态
	 * @param request 请求参数
	 * @return 审批状态
	 */
	@RequestMapping("/getprespzt")
	public @ResponseBody String getPreviousSpztByLcid(HttpServletRequest request){
		String spstid = request.getParameter("spstid");
		String splx = request.getParameter("splx");

		Map<String,Object> resultMap = new HashMap<String,Object>();

		Splcsl splcslObj = spService.getPreviousLcslBySpstid(spstid,splx);

		if(ObjectUtils.notEqual(splcslObj, null)){
			resultMap.put("pre_spzt", splcslObj.getSpzt().trim());
		}else{
			resultMap.put("pre_spzt", "");
		}
		return Constant.GSON.toJson(resultMap);
	}
	/**
	 * 通过单位编码和部门编码获取部门映射
	 * @param request 请求参数
	 * @return 部门映射
	 */
	@RequestMapping("/getbmysbybmbm")
	public @ResponseBody String getBmysByBmbm(HttpServletRequest request){

		String dwbm = request.getParameter("dwbm");
		String bmbm = request.getParameter("bmbm");

		Map<String,Object> resultMap = new HashMap<String,Object>();

		BMBM bmbmObj = spService.getBmysByBmbm(dwbm,bmbm);

		if(ObjectUtils.notEqual(bmbmObj, null)){
			resultMap.put("bmys", bmbmObj.getBmys().trim());//这里获取的部门映射可能有多，个以“;”分隔，以后出现问题可以看下这里
		}else{
			resultMap.put("bmys", "");
		}

		return Constant.GSON.toJson(resultMap);
	}

	@ResponseBody
	@RequestMapping("/sfdaAjAudit")
	public String sfdaAjAudit(@RequestParam(value="jdlx",required=false)String jdlx,
							  @RequestParam(value="lcjd",required=true)String lcjd,
							  @RequestParam(value="splx",required=true)String splx,
							  @RequestParam(value="sprdwbm",required=true)String sprdwbm,
							  @RequestParam(value="sprbmbm",required=false)String sprbmbm,
							  @RequestParam(value="sprgh",required=true)String sprgh,
							  @RequestParam(value="spstid",required=true)String spstid,
							  @RequestParam(value="spyj",required=false)String spyj,
							  @RequestParam(value="clnr",required=false)String clnr,
							  @RequestParam(value="lczz",required=false)boolean lczz,  //流程终止【看前台页面是否有选中“选择下一审核人”】
							  @RequestParam(value="spSign",required=true)String spSign,  //审批标识【1：同意 2：不同意（退回）】
							  HttpServletRequest request) throws Exception {
		Map<String,Object> ressult = new HashMap<String,Object>();
		RYBM ry = (RYBM)request.getSession().getAttribute("ry");
		String spdwbm = this.getStrResult(ry.getDwbm(),sprdwbm);
		String spbm = this.getStrResult(ry.getBmbm(),sprbmbm);
		String spr = this.getStrResult(ry.getGh(),sprgh);
		String dlbm = ry.getDlbm();
		String spzt = "4";  //表示流程审核同意并结束的状态
		String xxTitle = this.getLclxStr(splx);

		//1、查询最新审批信息
		List<Splcsl> spList = spService.getLartestSpxx(spstid, splx);
		Splcsl splcxx = spList.get(0);
		String spId = splcxx.getId();
		String lcid = splcxx.getLcid();

		//查询该节点流程
		Jdlc jdlc =  this.getJdlcByParams(jdlx,lcjd);
		String lcjdParam = new StringBuffer().append(Integer.parseInt(jdlc.getLcjd())+1).toString();
		String jdlxParam = new StringBuffer().append(Integer.parseInt(jdlc.getJdlx())+1).toString();

		if("2".equals(spSign)) {
			spzt = "3";
		}else {
			if(!lczz) {
				//新增一条数据
				spzt = "2"; //表示流程审核同意的状态
				String addMsg = spService.addSplcsl(spdwbm, spbm, spr, null, splx, "1", spstid, null, lcjdParam, jdlxParam, lcid, null);
			}
		}

		//2、修改原审批状态
		Integer xgztSign = spService.modifySplcslById(spyj,spzt,StringUtils.EMPTY,spId);

		//3、给发起审核人发送审核消息
		Splcsl spxx = spService.getFqjlBySpstidAndSplx(spstid, splx);
		String xxnr = StringUtils.EMPTY;
		if("2".equals(spzt)||"4".equals(spzt)) {
			xxnr = "-同意";
		}else if("3".equals(spzt)) {
			xxnr = "-不同意";
		}
		this.addMessage(spxx.getSpdw(), spxx.getSpr(), clnr+xxnr, xxTitle, dlbm);

		//4、修改案件状态【1：已审核  ，2： 待审 3：审核未通过  4:审核中】
		Integer ajzt =  0 ;
		String addSpMsg = StringUtils.EMPTY;
		if("4" == spzt) {
			//查询档案管理员
			Map<String,Object> queryParams = new HashMap<String,Object>();
			queryParams.put("p_bmys", "1100"); //1100代表案管，档案管理员也是从案管中获取
			queryParams.put("p_splx", "13");  //13代表档案管理
			queryParams.put("p_dwbm", spdwbm);
			List<Bmzdpz> bmzdpzList = bmzdpzService.getPzByDwbm(queryParams);
			if(bmzdpzList.size()>0&&CollectionUtils.isNotEmpty(bmzdpzList)){
				Bmzdpz pzObj = bmzdpzList.get(0);
				if(ry.getDwbm().equals(pzObj.getDwbm())&&ry.getGh().equals(pzObj.getGh())) {
					ajzt = sfdaAjxxService.modifyStatusByAjId("1", spstid);
				}else {
					addSpMsg = spService.addSplcsl(spdwbm, pzObj.getBmbm(), pzObj.getGh(), null, splx, "1", spstid, null, lcjdParam, jdlxParam, lcid, null);
				}
			}
		}else if("3" == spzt) {
			ajzt = sfdaAjxxService.modifyStatusByAjId("3", spstid);
		}


		ressult.put("spztSign",xgztSign);
		ressult.put("ajzt",ajzt);
		return Constant.GSON.toJson(ressult);
	}

	private String getStrResult(String rySxStr, String resStr) {
		String str = rySxStr;
		if(StringUtils.isNotBlank(resStr)&&!"undefined".equals(resStr)) {
			str = resStr;
		}
		return str;
	}


	/**
	 * 整体思路是：向审批表添加下一位审批人的信息，录入当前审批人的审批状态和审批意见；根据审批状态再修改被审批信息的数据
	 * @param jdlx 节点类型（表【YX_SFDA_JDLC】）
	 * @param lcjd 流程节点（表【YX_SFDA_JDLC】）
	 * @param splx 审批类型
	 * @param sprdwbm 审批人单位编码
	 * @param sprbmbm 审批人部门编码
	 * @param sprgh 审批人工号
	 * @param spstid 审批实体ID==审批的外键
	 * @param spyj 审批意见
	 * @param clnr 审批的内容信息，eg:XXX审批同意了XXX
	 * @param lczz 流程是否终止的标识
	 * @param spSign 审批标识
	 * @param pddj 评定等级
	 * @param pddjmc 评定等级名称
	 * @param request 请求对象
	 * @return 是否成功的标识
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/grjxAudit")
	public String GrjxAudit(@RequestParam(value="jdlx",required=false)String jdlx,
							@RequestParam(value="lcjd",required=true)String lcjd,
							@RequestParam(value="splx",required=true)String splx,
							@RequestParam(value="sprdwbm",required=false)String sprdwbm,
							@RequestParam(value="sprbmbm",required=false)String sprbmbm,
							@RequestParam(value="sprgh",required=false)String sprgh,
							@RequestParam(value="spstid",required=true)String spstid,
							@RequestParam(value="spyj",required=false)String spyj,
							@RequestParam(value="clnr",required=false)String clnr,
							@RequestParam(value="lczz",required=false)boolean lczz,  //流程终止【看前台页面是否有选中“选择下一审核人”】
							@RequestParam(value="spSign",required=true)String spSign,  //审批标识【1：同意 2：不同意（退回）】
							@RequestParam(value="pddj",required=false)String pddj,
							@RequestParam(value="pddjmc",required=false)String pddjmc,
							@RequestParam(value="spymtype",required=false)String spymtype,
							HttpServletRequest request) throws Exception {
		Map<String,Object> ressult = new HashMap<String,Object>();

		RYBM ry = (RYBM)request.getSession().getAttribute("ry");
		String spdwbm = this.getStrResult(ry.getDwbm(),sprdwbm);
		String spbm = this.getStrResult(ry.getBmbm(),sprbmbm);
		String spr = this.getStrResult(ry.getGh(),sprgh);
		String gh = ry.getGh();
		String dlbm = ry.getDlbm();
		String spzt = "4";  //表示流程审核同意并结束的状态
		String xxTitle = this.getLclxStr(splx);

		//单位编码-工号查询审批人信息
		List<RYBM> grxxList = loginService.getRybmInfoByDwGh(spdwbm,spr);
		String paramDlbm =StringUtils.EMPTY;
		if(grxxList.size()>0){
			RYBM rybm = grxxList.get(0);
			paramDlbm = rybm.getDlbm();
		}

		//绩效考核分值信息
		ydkhqbtg ydkhqbtg = grjxYwkhfzService.selectByYwkhId(spstid);

		//1、查询最新审批信息
		List<Splcsl> spList = spService.getLartestSpxx(spstid, splx);
		Splcsl splcxx = spList.get(0);
		String spId = splcxx.getId();
		String lcid = splcxx.getLcid();

		//查询该节点流程
		Jdlc jdlc =  this.getJdlcByParams(jdlx,lcjd);
		String lcjdParam = new StringBuffer().append(Integer.parseInt(jdlc.getLcjd())+1).toString();
		String jdlxParam = new StringBuffer().append(Integer.parseInt(jdlc.getJdlx())+1).toString();
		if(!Objects.isNull(jdlc)){
			if(StringUtils.isNotBlank(jdlc.getLcjd())){
				lcjdParam = jdlc.getLcjd();
			}
			if(StringUtils.isNotBlank(jdlc.getJdlx())){
				jdlxParam = jdlc.getJdlx();
			}

			if(StringUtils.isNotBlank(jdlc.getXyjd())){

			}
		}

		if("2".equals(spSign)) {
			spzt = "3";
		}else {
			if(lczz) {
				//新增一条数据
				spzt = "2"; //表示流程审核同意的状态
				String addMsg = spService.addSplcsl(spdwbm, spbm, spr, null, splx, "1", spstid, null, lcjdParam, jdlxParam, lcid, null);
			}
		}

		//2、修改原审批状态
		Integer xgztSign = spService.modifySplcslById(spyj,spzt,StringUtils.EMPTY,spId);

		//3、给发起审核人发送审核消息
		Splcsl spxx = spService.getFqjlBySpstidAndSplx(spstid, splx);
		String xxnr = StringUtils.EMPTY;

		//根据分值id/绩效考核id更改分值表
		ydkhqbtg grjxFz = new ydkhqbtg();
		grjxFz.setYdkhid(spstid);

		if("2".equals(spzt)||"4".equals(spzt)) {
			xxnr = "-同意";
			grjxFz.setSpsftg("1");

			if(StringUtils.isNotBlank(pddj)){
				grjxFz.setPdjb(pddj);
			}
			if(StringUtils.isNotBlank(pddjmc)){
				grjxFz.setPdjbmc(pddjmc);
			}

			// 更新评分人
			this.setGrjxFzSprInfo(ydkhqbtg,spzt,grjxFz,dlbm,gh,ry, spymtype);

			if("4".equals(spzt)){  //设置绩效考核的考核委员会审批人信息
//				grjxFz.setRsbpfr(paramDlbm);
//				grjxFz.setRsbpfrgh(spr);

				//修改个人绩效的状态为审批完成【0 未完成/未通过  1 已完成】
				Ydkh grjx = new Ydkh();
				grjx.setYdkhid(spstid);
				grjx.setSfsp("1");
				ydkhService.updateByPrimaryKeySelective(grjx);
			}else{

//				if(StringUtils.isNotBlank(ydkhqbtg.getBmpfrgh())
//						&&StringUtils.isBlank(ydkhqbtg.getFgyldpfr())
//						&&StringUtils.isBlank(ydkhqbtg.getFgyldpfrgh())){ //设置绩效考核的分管领导审批人的信息
//					grjxFz.setFgyldpfr(paramDlbm);
//					grjxFz.setFgyldpfrgh(spr);
//				}
			}

		}else if("3".equals(spzt)) {
			xxnr = "-不同意";
			grjxFz.setSpsftg("0");

			// 审批退回，将所有评分人全部重置（置空）
			ydkhqbtg ydkhfz = new ydkhqbtg();
			ydkhfz.setYdkhid(spstid);
			ydkhfz.setBmpfr("");
			ydkhfz.setBmpfrgh("");
			ydkhfz.setJcgpfr("");
			ydkhfz.setJcgpfrgh("");
			ydkhfz.setFgyldpfr("");
			ydkhfz.setFgyldpfrgh("");
			ydkhfz.setJcpfr("");
			ydkhfz.setJcpfrgh("");
			ydkhfz.setRsbpfr("");
			ydkhfz.setRsbpfrgh("");
			ydkhfz.setPdjb("");
			ydkhfz.setPdjbmc("");
			grjxYwkhfzService.updatePerOfScoreByPrimaryKey(grjxFz);
		}

		grjxYwkhfzService.updateByYdkhIdSelective(grjxFz);

		this.addMessage(spxx.getSpdw(), spxx.getSpr(), clnr+xxnr, xxTitle, dlbm);

		ressult.put("status",xgztSign);
		return Constant.GSON.toJson(ressult);
	}

	/**
	 * 根据审批状态以及当前被审核的人员的角色以及人员类型设置个人绩效分值表中的审核人员信息
	 * @param ydkhqbtg 根据审批外键查询的个人绩效分值表信息
	 * @param spzt 审批状态
	 * @param grjxFz 个人绩效分值对象
	 * @param paramDlbm 审批人登录别名
	 * @param spr 审批人工号
	 */
	private void setGrjxFzSprInfo(ydkhqbtg ydkhqbtg, String spzt, ydkhqbtg grjxFz,
								  String paramDlbm,String spr,RYBM ry, String spymtype) {

		// 改造：直接根据 spymtype 来更新评分人
		// spymtype 0：自评人；1：部门评分；2：人事部（考核委员会）分 5: 交叉评分人；7：分管领导评分 6：检察官评分
		if (("1").equals(spymtype)) {
			grjxFz.setBmpfr(paramDlbm);
			grjxFz.setBmpfrgh(spr);
		} else if (("2").equals(spymtype)) {
			grjxFz.setRsbpfr(paramDlbm);
			grjxFz.setRsbpfrgh(spr);
		} else if (("6").equals(spymtype)) {
			grjxFz.setJcgpfr(ry.getDlbm());
			grjxFz.setJcgpfrgh(ry.getGh());
		} else if (("5").equals(spymtype)) {
			grjxFz.setJcpfr(ry.getDlbm());
			grjxFz.setJcpfrgh(ry.getGh());
		} else if (("7").equals(spymtype)) {
			grjxFz.setFgyldpfr(paramDlbm);
			grjxFz.setFgyldpfrgh(spr);
		}

//		XtGrjxRytype rytype = null;
//		if(StringUtils.isNotBlank(ydkhqbtg.getRylx())){
//			rytype = xtGrjxRytypeService.selectByPrimaryKey(ydkhqbtg.getRylx());
//		}
//
//		if(Objects.isNull(rytype)){
//			rytype = new XtGrjxRytype();
//		}
//
//		//检察官、//检察辅助人员、//司法行政人员、
//		if(Constant.GRJX_RYLX_JCG.equals(rytype.getName())){
//			//一般检察官---部门领导----院领导
//			if(Constant.GRJX_JSLX_JCG.equals(ydkhqbtg.getRyjs())){
//				if("4".equals(spzt)){  //设置绩效考核的考核委员会审批人信息
//					grjxFz.setRsbpfr(paramDlbm);
//					grjxFz.setRsbpfrgh(spr);
//				}else{
//					if(StringUtils.isNotBlank(ydkhqbtg.getBmpfrgh())
//							&&StringUtils.isBlank(ydkhqbtg.getFgyldpfr())
//							&&StringUtils.isBlank(ydkhqbtg.getFgyldpfrgh())){ //设置绩效考核的分管领导审批人的信息
//						grjxFz.setFgyldpfr(paramDlbm);
//						grjxFz.setFgyldpfrgh(spr);
//					}
//				}
//			}else if(Constant.GRJX_JSLX_JCG_BMLD.equals(ydkhqbtg.getRyjs())){
//				if("4".equals(spzt)){  //设置绩效考核的考核委员会审批人信息
//					grjxFz.setRsbpfr(paramDlbm);
//					grjxFz.setRsbpfrgh(spr);
//				}else{
//					if(StringUtils.isBlank(ydkhqbtg.getFgyldpfr())
//							&&StringUtils.isBlank(ydkhqbtg.getFgyldpfrgh())){ //设置绩效考核的分管领导审批人的信息
//						grjxFz.setFgyldpfr(paramDlbm);
//						grjxFz.setFgyldpfrgh(spr);
//					}
//				}
//			}else if(Constant.GRJX_JSLX_JCG_YLD.equals(ydkhqbtg.getRyjs())){
//				grjxFz.setRsbpfr(paramDlbm);
//				grjxFz.setRsbpfrgh(spr);
//			}
//		}else if(Constant.GRJX_RYLX_JCFZRY.equals(rytype.getName())){
//			//一般人员---部门领导----院领导
//			if(Constant.GRJX_JSLX_JCFZRY.equals(ydkhqbtg.getRyjs())){
//				if("4".equals(spzt)){  //设置绩效考核的考核委员会审批人信息
//					grjxFz.setRsbpfr(paramDlbm);
//					grjxFz.setRsbpfrgh(spr);
//				}else{
//					if(StringUtils.isNotBlank(ydkhqbtg.getBmpfrgh())&&StringUtils.isNotBlank(ydkhqbtg.getJcgpfrgh())
//							&&StringUtils.isBlank(ydkhqbtg.getFgyldpfr())
//							&&StringUtils.isBlank(ydkhqbtg.getFgyldpfrgh())){ //设置绩效考核的分管领导审批人的信息
//						grjxFz.setFgyldpfr(paramDlbm);
//						grjxFz.setFgyldpfrgh(spr);
//					}else {
//						if(StringUtils.isBlank(ydkhqbtg.getJcgpfrgh())){
//							grjxFz.setJcgpfr(ry.getDlbm());
//							grjxFz.setJcgpfrgh(ry.getGh());
//							grjxFz.setBmpfr(paramDlbm);
//							grjxFz.setBmpfrgh(spr);
//						}
//						if(StringUtils.isBlank(ydkhqbtg.getBmpfrgh())){
//							grjxFz.setBmpfr(paramDlbm);
//							grjxFz.setBmpfrgh(spr);
//						}
//					}
//				}
//			}else if(Constant.GRJX_JSLX_JCFZRY_BMLD.equals(ydkhqbtg.getRyjs())){
//				if("4".equals(spzt)){  //设置绩效考核的考核委员会审批人信息
//					grjxFz.setRsbpfr(paramDlbm);
//					grjxFz.setRsbpfrgh(spr);
//				}else{
//					if(StringUtils.isBlank(ydkhqbtg.getFgyldpfr())
//							&&StringUtils.isBlank(ydkhqbtg.getFgyldpfrgh())){ //设置绩效考核的分管领导审批人的信息
//						grjxFz.setFgyldpfr(paramDlbm);
//						grjxFz.setFgyldpfrgh(spr);
//					}
//				}
//			}else if(Constant.GRJX_JSLX_JCFZRY_YLD.equals(ydkhqbtg.getRyjs())){
//				grjxFz.setRsbpfr(paramDlbm);
//				grjxFz.setRsbpfrgh(spr);
//			}
//		}else if(Constant.GRJX_RYLX_SFXZRY.equals(rytype.getName())){
//			//一般人员---部门领导----院领导
//			if(Constant.GRJX_JSLX_JCFZRY.equals(ydkhqbtg.getRyjs())){
//				if("4".equals(spzt)){  //设置绩效考核的考核委员会审批人信息
//					grjxFz.setRsbpfr(paramDlbm);
//					grjxFz.setRsbpfrgh(spr);
//				}else{
//					if(StringUtils.isNotBlank(ydkhqbtg.getBmpfrgh())
//							&&StringUtils.isBlank(ydkhqbtg.getFgyldpfr())
//							&&StringUtils.isBlank(ydkhqbtg.getFgyldpfrgh())){ //设置绩效考核的分管领导审批人的信息
//						grjxFz.setFgyldpfr(paramDlbm);
//						grjxFz.setFgyldpfrgh(spr);
//					}
//				}
//			}else if(Constant.GRJX_JSLX_JCFZRY_BMLD.equals(ydkhqbtg.getRyjs())){
//				if("4".equals(spzt)){  //设置绩效考核的考核委员会审批人信息
//					grjxFz.setRsbpfr(paramDlbm);
//					grjxFz.setRsbpfrgh(spr);
//				}else{
//					if(StringUtils.isBlank(ydkhqbtg.getFgyldpfr())
//							&&StringUtils.isBlank(ydkhqbtg.getFgyldpfrgh())){ //设置绩效考核的分管领导审批人的信息
//						grjxFz.setFgyldpfr(paramDlbm);
//						grjxFz.setFgyldpfrgh(spr);
//					}
//				}
//			}else if(Constant.GRJX_JSLX_JCFZRY_YLD.equals(ydkhqbtg.getRyjs())){
//				grjxFz.setRsbpfr(paramDlbm);
//				grjxFz.setRsbpfrgh(spr);
//			}
//		}
	}


}
