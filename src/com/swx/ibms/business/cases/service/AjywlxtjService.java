package com.swx.ibms.business.cases.service;

import java.util.Map;

/** 
 * 案件业务类型统计服务接口
 * @author 李治鑫
 *
 */
public interface AjywlxtjService {
	/**
	 * 根据归档ID查询此人在这个归档时期内办理的案件类型及数量
	 * @param daid 归档ID
	 * @return map
	 */
	Map<String, Object> getCount(String daid );
}
