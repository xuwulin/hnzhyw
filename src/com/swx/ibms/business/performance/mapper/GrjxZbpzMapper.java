package com.swx.ibms.business.performance.mapper;


import com.swx.ibms.business.performance.bean.GrjxZbpz;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author east
 * @date:2017年4月13日
 * @version:1.0
 * @description:个人绩效指标配置mapper,编写对个人绩效指标数据的基本CRUD方法。
 *
 */
@SuppressWarnings("all")
public interface GrjxZbpzMapper {
	/**
	 * 根据单位级别查询个人绩效指标配置所有数据
	 * @param map map
	 * @return 个人绩效指标配置的数据集合
	 */
	List<GrjxZbpz> selectList(Map<String, Object> map);

	/**
	 * 根据个人绩效指标配置ID查询个人绩效指标配置数据
	 * @param map map
	 * @return 个人绩效指标配置
	 */
	GrjxZbpz selectById(Map<String, Object> map);

	/**
	 * 添加个人绩效指标配置数据
	 * @param map map
	 * @return 字符串1 或者 0
	 */
	String insertData(Map<String, Object> map);

	/**
	 * 修改个人绩效指标配置数据
	 * @param map
	 * map
	 * @return 字符串1 或者 0
	 */
	String updateData(Map<String, Object> map);

	/**
	 * 假删除个人绩效指标配置数据
	 * @param map 数据集合
	 *
	 */
	void deleteData(Map<String, Object> map);

	/**
	 * @param zbpzId
	 * 指标配置id
	 */
	void updateStatus(@Param("zbpzId") String zbpzId);

	/**
	 * @param map
	 * 指标配置内容
	 */
	void savedata(Map<String, Object> map);

	/**
	 * 根据业务类别和单位级别判断是否已经存在配置记录
	 * @param map
	 * map
	 */
	int isExist(Map<String, Object> map);

	/**
	 * 获取业务编码表信息
	 * @param map 参数：业务编码
	 * @return 业务编码结果集
	 */
	List<String> getYwlxList(Map<String, Object> map) throws Exception;

	/**
	 * 根据单位级别，部门类别，人员类别，所属年份获取指标配置详细内容，包括表头，表体，评分部分等
	 * @param dwjb
	 * @param bmlb
	 * @param rylb
	 * @param ssnf
	 * @return
	 */
	List<Map<String, Object>> getInfoOfZbpz(@Param("dwjb") String dwjb,
                                            @Param("bmlb") String bmlb,
                                            @Param("rylb") String rylb,
                                            @Param("ssnf") String ssnf);
}
