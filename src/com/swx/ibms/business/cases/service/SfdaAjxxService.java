package com.swx.ibms.business.cases.service;

import com.swx.ibms.business.cases.bean.SfdaAjxx;
import com.swx.ibms.common.utils.PageCommon;

import java.util.Map;



/**
 * 
 * SfdaAjxxService.java 司法档案下的案件信息Service接口
 * @author 何向东
 * @date<p>2017年8月3日</p>
 * @version 1.0
 * @description<p></p>
 */
public interface SfdaAjxxService {
	/**
	 * 查询所有司法档案下的检察官主办案件信息
	 * @param map 参数“当前页、每页显示数、审批状态、档案id
	 * @return 司法档案下的检察官案件信息结果集
	 */
	Map<String,Object> getSfdaZbAjxxPageList(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询所有司法档案下的检察官从办/参办/协办案件信息
	 * @param map 参数“当前页、每页显示数、审批状态、档案id
	 * @return 司法档案下的检察官案件信息结果集
	 */
	PageCommon<Object> getSfdaCbAjxxPageList(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询单条司法档案下的检察官案件信息根据id
	 * @param map 案件信息id
	 * @return 司法档案下的检察官案件信息
	 */
	SfdaAjxx getSfdaAjxxById(Map<String, Object> map) throws Exception;
	
	/**
	 * 添加司法档案下的检察官案件信息
	 * @param map 
	 * @return 成功与失败的字符串，1 ：成功
	 */
	 Map<String, Object> addSfdaAjxx(Map<String, Object> map) throws Exception;
	
	/**
	 * 修改司法档案下的检察官案件信息
	 * @param map 
	 * @return 成功与失败的字符串，1 ：成功
	 */
	String updateSfdaAjxx(Map<String, Object> map) throws Exception;
	
	/**
	 * 删除司法档案下的检察官案件信息
	 * @param map 
	 * @return 成功与失败的字符串，1 ：成功
	 */
	String deleteSfdaAjxx(Map<String, Object> map) throws Exception;
	
	/**
	 *  根据档案id修改此档案对应的案件信息的状态 
	 * @param map 
	 * @return 成功与失败的字符串，1 ：成功
	 * @throws Exception 
	 */
	String updateStatusByDaId(Map<String, Object> map) throws Exception;
	
	Integer modifyStatusByAjId(String ajStatus, String ajId);
}
