package com.swx.ibms.business.archives.service;

import java.util.Map;

/**
 *
 * PersionService.java 个人信息service接口
 * @author east
 * @date<p>2017年5月16日</p>
 * @version 1.0
 * @description<p>定义对个人基本信息、个人经历信息、个人附件信息的CRUD</p>
 */
public interface SfdaGrxxPldrService {


	/**
	 * 新增个人基本信息
	 */
	String insertAllGrjbxx(Map<String,Object> map);

	/**
	 * 新增个人经历信息
	 */
	String insertAllGrjl(Map<String,Object> map);

	/**
	 * 查询新增基本信息
	 */
	Map<String, Object> selectALLGrjbxx(String dwbm,int page,int pageSize) throws Exception;

	/**
	 * 查询新增个人经历
	 */
	Map<String, Object> selectALLGrjl(String dwbm,int page,int pageSize) throws Exception;

	/**
	 * 删除个人基本信息
	 */
	String deleteGrjbxxById(String id) throws Exception;

	/**
	 * 删除个人经历
	 */
	String deleteGrjlById(String id) throws Exception;

	/**
	 * 根据单位编码，工号查询个人基本信息
	 */
	Map<String, Object> selectGrjbxxBydg(String dwbm,String gh) throws Exception;

	/**
	 * 根据单位编码，工号查询个人经历
	 */
	Map<String, Object> selectGrjlBydg(String dwbm,String gh) throws Exception;
}
