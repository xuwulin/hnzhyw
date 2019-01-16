package com.swx.ibms.business.cases.service;

import com.swx.ibms.business.cases.bean.Ajwtfk;

import java.util.List;



/**
 * 案件问题问题反馈接口
 * @author 李佳
 * @date: 2017年3月20日
 */
public interface AjwtfkService {
	
	
	/**
	 * 通过wbid(部门受案号)查询工号	
	 * @param wbid	外部id	
	 * @return gh 工号
	 */
	String getGhByBmsah(String wbid); 

	/**
	 * 新增案件问题反馈信息		
	 * @param ajwtfk 案件问题反馈实体类		
	 * @return 是否添加成功 1 = 成功, 0 = 不成功
	 */
	String insertAjwtfk(Ajwtfk ajwtfk);

	/**
	 * 删除案件问题反馈信息		
	 * @param id 案件id;	
	 * @return 是否添加成功 1 = 成功, 0 = 不成功
	 */
	String deleteAjwtfk(String id);

	/**
	 * 修改案件问题反馈信息
	 * @param ajwtfk 案件问题反馈实体类
	 * @return 是否添加成功 1 = 成功, 0 = 不成功
	 */
	String updateAjwtfk(Ajwtfk ajwtfk);

	/**
	 * 根据id获得案件问题反馈信息
	 * @param id 案件id
	 * @return list
	 */
	List getAjwtfk(String id);

	/**
	 * 通过id查询统一受案号和部门受案号
	 * @param id 案件id         
	 * @return list
	 */
	List<Ajwtfk> getBmsahById(String id);

}
