package com.swx.ibms.business.common.service;

import java.util.Map;

/**
 * 审批接口类
 * @author admin
 *
 */
public interface WorkFlowAuditService {
	
	/**
	 * 申请人创建并发起流程，指定到下一位执行者
	 * @param businessKey 档案id
	 * @param sprDwbmGH 审批人单位编码_工号
	 * @param sprBmbm 审批人部门
	 * @param fqrDwbmGh 发起人单位编码_工号
	 * @param fqrBmbm 发起人部门
	 * @param spLx 审批类型
	 * @return 是否成功得标识
	 */
	Map<String,Object> createAndStartAudit(String lc_key
										  ,String businessKey
										  ,String sprDwbmGH
										  ,String sprBmbm
										  ,String fqrDwbmGh
										  ,String fqrBmbm
										  ,String spLx
										  );
}
