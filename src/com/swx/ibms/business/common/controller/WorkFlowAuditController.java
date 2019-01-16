package com.swx.ibms.business.common.controller;

import com.swx.ibms.business.common.service.SpService;
import com.swx.ibms.business.common.service.WorkFlowAuditService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.rbac.service.LoginService;
import org.activiti.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;



@Controller
@RequestMapping("/createAudit")
@SuppressWarnings("all")
public class WorkFlowAuditController {
	
	private static final Logger LOG = LoggerFactory.getLogger(WorkFlowAuditController.class);
	
	/********************************** activiti Service **********************************/
	@Autowired
	private ProcessEngine processEngine; 
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private HistoryService historyService;
	
	
	/************************************* others Service ***********************************************/
	@Autowired
	private SpService spService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private WorkFlowAuditService workFlowAuditService;
	
	
	@RequestMapping("/start")
	@ResponseBody
	public String startProcessAndFinishFirstTask(@RequestParam(value="spr_dwbm",required=true)String spr_dwbm,
			 									 @RequestParam(value="spr_gh",required=true)String spr_gh,
			 									 @RequestParam(value="spr_bmbm",required=true)String spr_bmbm,
			 									 @RequestParam(value="sp_wbid",required=true)String sp_wbid, //被审批的id
			 									 @RequestParam(value="sp_lx",required=true)String sp_lx,
			 									 HttpServletRequest request
			 									 ) throws Exception {
		//发起审批的人物信息
		RYBM fqrInfo =(RYBM)request.getSession().getAttribute("ry");
		String fqr_dwbm = fqrInfo.getDwbm();
		String fqr_gh = fqrInfo.getBmbm();
		String fqr_bmbm = spr_bmbm;
		
		//流程图的id  【sfda_audit.bpmn的id】
		String lc_key = "sfdaAudit";
		
		String sprDwbmGH = spr_dwbm+"_"+spr_gh;
		String fqrDwbmGh = fqr_dwbm+"_"+fqr_gh;
		Map<String,Object> resultMap = workFlowAuditService.createAndStartAudit(lc_key,sp_wbid, sprDwbmGH, spr_bmbm, fqrDwbmGh, fqr_bmbm, sp_lx);
		
		return Constant.GSON.toJson(resultMap);
	}
	
}
