package com.swx.ibms.business.rbac.service;

import com.swx.ibms.business.rbac.bean.Bmlbywfp;
import com.swx.ibms.common.utils.PageCommon;

import java.util.List;



/**
 * 业务类型分配接口
 * BmlbywfpService.java 
 * @author 何向东
 * @date<p>2017年12月19日</p>
 * @version 1.0
 * @description<p></p>
 */

public interface BmlbywfpService {
	
	/**
	 * 根据传入条件查询业务类型分配信息并分页
	 * @param ywbm 业务编码
	 * @param bmlbbm 部门类别编码【即：部门映射】
	 * @param dwbm 单位编码
	 * @param type 类型
	 * @param start 开始行数 
	 * @param end 结束行数
	 * @return 分页实体类
	 * @throws Exception 异常
	 */
	PageCommon<Bmlbywfp> ywfpSelectPageList(String ywbm, String bmlbbm,
											String dwbm, String type, Integer start, Integer end) throws Exception;
	
	/**
	 * 根据id查询单条信息
	 * @param id 业务分配id
	 * @return 业务分配结果集
	 * @throws Exception 异常
	 */
	List<Bmlbywfp> getYwfpListById(Integer id) throws Exception;
	
	/**
	 * 根据id修改单条信息
	 * @param id 业务分配id
	 * @param bmlbbm 部门类别编码【部门映射】
	 * @param ywbm 业务编码
	 * @param dwbm 单位编码
	 * @return 是否成功的字符串
	 * @throws Exception 异常
	 */
	String modifyYwfpById(Integer id,String bmlbbm,String ywbm,String dwbm) throws Exception;
	
	/**
	 * 根据id删除单条信息
	 * @param id 业务分配id
	 * @return 是否成功的字符串
	 * @throws Exception 异常
	 */
	String delYwfpById(Integer id) throws Exception;
	
	/**
	 * 添加信息
	 * @param bmlbbm 部门类别编码【部门映射】 
	 * @param ywbm 业务编码
	 * @param dwbm 单位编码
	 * @return 是否成功的字符串
	 * @throws Exception 异常
	 */
	String addYwfp(String bmlbbm,String ywbm,String dwbm) throws Exception;
	
}
