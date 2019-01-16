/**
 * 
 */
package com.swx.ibms.business.appraisal.mapper;

import com.swx.ibms.business.appraisal.bean.Ywgzzbpz;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;



/**
 * 业务工作mapper
 * @author 封泽超
 *@since 2017年6月5日
 */
public interface YwgzzbMapper {
	/**
	 * 新增业务工作配置
	 * @param map 
	 */
	void insert(Map<String, String> map);
	/**
	 * 根据id得到业务工作配置
	 * @param map 
	 */
	void selectbyid(Map<String,Object> map);
	/**
	 * 根据id删除业务工作配置
	 * @param map 
	 */
	void deletebyid(Map<String,String> map);
	/**
	 * 修改业务工作
	 * @param map 
	 */ 
	void update(Map<String, String> map);
	/**
	 * 根据dwjb进行分页
	 * @param map 
	 */
	void search(Map<String,Object> map);
	
	/**
	 * 根据dwjb 和考核类型 得到一个结果 
	 * @param map 
	 */
	void selectone(Map<String,Object> map);
	
	///////////////////表头部分
	
	/**
	 * 添加
	 * @param map 
	 */
	void insertbt(Map<String, String> map);
	
	/**
	 * 根据id查询表头数据
	 * @param map 
	 */
	void selectbyidbt(Map<String,Object> map);
	
	/**
	 * 跟新表头
	 * @param map 
	 */
	void updatebt(Map<String,String> map);
	
	/**
	 * 删除表头
	 * @param map 
	 */
	void deletebyidbt(Map<String,String> map);
	
	/**
	 * 根据所属年份、状态、考核类型、单位级别查询业务考核指标配置信息
	 * @param khlx 考核类型
	 * @param ssrq 所属年份
	 * @param dwjb 单位级别
	 * @return 业务考核指标配置对象
	 */
	List<Ywgzzbpz> getYwkhZbpzByParams(@Param("khlx")String khlx, @Param("ssrq")String ssrq, @Param("dwjb")String dwjb);
	
	
}
