package com.swx.ibms.business.common.service;

import com.swx.ibms.business.common.controller.WorkFlowAuditController;
import com.swx.ibms.business.rbac.service.LoginService;
import com.swx.ibms.common.web.workflow.ActivitiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;



@SuppressWarnings("all")
@Service("workFlowAuditService")
public class WorkFlowAuditServiceImpl implements WorkFlowAuditService{
	
	private static final Logger LOG = LoggerFactory.getLogger(WorkFlowAuditController.class);
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private ActivitiService activitiService;

	@Override
	public Map<String, Object> createAndStartAudit(String lc_key,String businessKey, String sprDwbmGH, String sprBmbm,
			String fqrDwbmGh, String fqrBmbm, String spLx) {
		Map<String, Object> resMap = new HashMap<String,Object>();
		
		//组装数据进行发起工作流
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("FQR_DWBM_GH", fqrDwbmGh);
		activitiService.startProcessByKey(lc_key, businessKey, params);  //startProcessInstanceByKey(lc_key,da_id, paramsMap);
		//返回是否成功得message
		
		return resMap;
	}
	

}
