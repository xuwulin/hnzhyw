/**
 * 
 */
package com.swx.ibms.business.performance.service;


import com.swx.ibms.business.performance.bean.HCPZ;

import java.util.List;
import java.util.Map;

/**
 * 核查配置服务
 * @author 封泽超
 *@since 2017年4月11日
 */
public interface HCPZService {
	
	/**
	 * 添加核查配置 
	 * @param hcpz 
	 * @return String 
	 */
	String insert(HCPZ hcpz);
	
	/**
	 * 删除核查配置
	 * @param hcpz 
	 * @return String 
	 */
	String delete(HCPZ hcpz);
	
	/**
	 * 更新核查配置
	 * @param hcpz 
	 * @return String 
	 */
	String update(HCPZ hcpz);
	
	/**
	 * 根据单位编码和部门编码查询核查配置
	 * @param bhcdw 被核查单位编码
	 * @param page 第几页
	 * @param pagesize 页面展示条数
	 * @return Map<String,Object> 
	 */
	Map<String,Object> select(String bhcdw, String page, String pagesize);

	/**
	 * 根据单位编码和类型查询授权
	 * @param hcpz
	 * @return List<HCPZ>
	 */
	List<HCPZ> selectdwbmywlx(HCPZ hcpz);

	/**
	 * 通过业务类型查询部门类别
	 * @param ywlx 业务类型
	 * @return List<HCPZ>
	 */
	List<HCPZ> getbmlbbyywlx(String ywlx);

	/**
	 * 得到业务类型列表
	 * @return List<String>
	 */
	List<String> getywlxlist();

	/**
	 * 选中一个
	 * @param hcpz
	 * @return HCPZ
	 */
	HCPZ selectone(HCPZ hcpz);
	/**
	 * 得到所有核查配置
	 * @param page 第几页
	 * @param pagesize 页面展示条数
	 * @return Map<String,Object>
	 */
	Map<String,Object> getAllhcpz(String page, String pagesize);

	/**
	 * spstid 得到 年度 季度
	 * @param spstid 审批实体id
	 * @return List<String>
	 */
	List<String> getndjd(String spstid);
	/**
	 * 通过单位编码得到单位级别
	 * @param dwbm 单位编码
	 * @return Map<String,String>
	 */
	Map<String,String> getdwjb(String dwbm);
	/**
	 * 通过单位编码 和 审批实体id 判断该节点类型
	 * @param spstid 审批实体id
	 * @param dwbm 单位编码
	 * @return String
	 */
	String getisjc(String spstid, String dwbm);
	
	/**
	 * 通过审批实体id 获取部门列表
 	 * @param spstid 审批实体id
	 * @return List<HCPZ> 
	 */
	List<HCPZ> getbmlistbyspstid(String spstid);

	/**
	 * 单位-业务类型-时间 的方式查询所有相关的单位&业务
 	 * @param hcpz  
	 * @return List<HCPZ> 
	 */
	List<HCPZ> selectdwbmywlxsj(HCPZ hcpz);
	
}
