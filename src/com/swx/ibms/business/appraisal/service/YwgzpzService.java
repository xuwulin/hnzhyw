/**
 * 
 */
package com.swx.ibms.business.appraisal.service;

import com.swx.ibms.business.appraisal.bean.Ywgzzbpz;
import com.swx.ibms.business.appraisal.bean.Ywgzzbpzbt;

import java.util.List;
import java.util.Map;



/**
 * 业务工作配置
 * @author 封泽超
 * @since 2017年6月5日
 */
public interface YwgzpzService {
	
	/**
	 * 通过id'查询配置
	 * @param id 
	 * @return Ywgzzbpz 
	 */
	Ywgzzbpz selectbyid(String id);
	
	/**
	 * 通过id删除配置
	 * @param id 
	 * @return boolean
	 */
	boolean deletebyid(String id);
	
	/**
	 * 添加
	 * @param ywgzzbpz  
	 * @return String 
	 */
	String insert(Ywgzzbpz ywgzzbpz);
	/**
	 * 修改
	 * @param ywgzzbpz 
	 * @return boolean 
	 */
	boolean updata(Ywgzzbpz ywgzzbpz);
	
	/**
	 * 查询
	 * @param page 页码
	 * @param rows 行数
	 * @param dwjb 单位级别
	 * @return Map<String,Object> 
	 */
	Map<String,Object> search(int page,int rows,String dwjb);
	
	/**
	 * 根据dwjb和khlx得到一个结果
	 * @param dwjb 
	 * @param khlx 
	 * @return Map<String,Object> 
	 */
	Ywgzzbpz selectone(String dwjb,String khlx,String ssrq);
	
	///////////////////////////表头部分
	/**
	 * 添加表头
	 * @param y 
	 * @return boolean
	 */
	String insertbt(Ywgzzbpzbt y);
	/**
	 * 根据id查询
	 * @param id 
	 * @return Ywgzzbpzbt 
	 */
	Ywgzzbpzbt selectbyidbt(String id);
	
	/**
	 * 更新表头
	 * @param y 
	 * @return boolean 
	 */
	boolean updatebt(Ywgzzbpzbt y);
	
	/**
	 * 删除表头
	 * @param map 
	 * @return boolean 
	 */ 
	boolean deletebyidbt(String id);
	
	
	/**
	 * 根据所属年份、状态、考核类型、单位级别查询业务考核指标配置信息
	 * @param khlx 考核类型
	 * @param ssrq 所属年份
	 * @param dwjb 单位级别
	 * @return 业务考核指标配置对象
	 */
	List<Ywgzzbpz> getYwkhZbpzByParams(String khlx,String ssrq,String dwjb);
	
}
