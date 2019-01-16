package com.swx.ibms.business.archives.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


/**
 * 检察官个人信息批量导入
 */
public interface SfdaGrxxPldrMapper {

	/**
	 * 批量导入个人基本信息
	 */
	int insertAllGrjbxx(Map<String,Object> map) throws Exception;

	/**
	 * 批量导入个人经历
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int insertAllGrjl(Map<String,Object> map) throws Exception;

	/**
	 * 查询导入的个人信息
	 */
	List<Map<String, Object>> selectALLGrjbxx(@Param("dwbm") String dwbm) throws Exception;

	/**
	 * 查询导入的个人经历
	 */
	List<Map<String, Object>> selectALLGrjl(@Param("dwbm") String dwbm) throws Exception;

	/**
	 * 根据id删除个人信息
	 */
	void deleteGrjbxxById(@Param("id") String id) throws Exception;

	/**
	 * 根据id删除个人经历
	 */
	void deleteGrjlById(@Param("id") String id) throws Exception;

	/**
	 * 根据单位编码，工号去查找对应的个人信息
	 * 个人基本信息只有一条，如果查出来有多条，按创建时间取排序最新的一条
	 */
	Map<String,Object> selectGrjbxxBydg(@Param("dwbm") String dwbm,@Param("gh") String gh) throws Exception;

	/**
	 * 根据单位编码，工号去查找对应的个人经历
	 * 个人经历可以有多条
	 */
	List<Map<String, Object>> selectGrjlBydg(@Param("dwbm") String dwbm,@Param("gh") String gh) throws Exception;
}
