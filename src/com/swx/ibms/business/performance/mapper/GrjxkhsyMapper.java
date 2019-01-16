package com.swx.ibms.business.performance.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 个人绩效考核
 * @author 王宇锋
 */
public interface GrjxkhsyMapper {
	/**
	 * 获得考评得分详情条件过滤后总行数
	 */
	void getKpdfxqcount(Map map);

	/**
	 * 刚加载时根据单位编码分页获得月度考核id,单位编码
	 * 工号,人员名称,月度考核总分
	 * @param map
	 */
	void getJzryjxfs(Map map);

	/**
	 * 按照月度考核ID获得指标考评得分,业务名称和业务类型
	 */
	void getZbkpdf(Map map);
	/**
	 * 根据直接领导人工号获得单位编码
	 */
	void getZjldbmbm(Map map);
	/**
	 * 通过单位编码，部门编码sql语句找到bmys
	 */
	void getBmys(Map map);
	/**
	 * 通过登陆人单位编码,部门编码sql语句,查询单位编码找到相应部门数据
	 */
	void getXgBm(Map map);
	/**
	 * 通过登陆人单位编码,登陆人工号,返回考核行数
	 */
	void getkhcount(Map map);
	/**
	 * 通过部门映射找到业务类型编码和业务名称
	 */
	void getYwlxbmByBmys(Map map);
	/**
	 * 通过单位编码查找出单位名称
	 * @param map map对象
	 */
	void getDwmcByDwbm(Map map);

	/**
	 * 通过月度考核id查询业务考核分值id
	 * @param ydkhid 月度考核id
	 * @return 业务考核分值id
	 */
	String getYwkhfzidByYdkhid(String ydkhid);

	/**
	 * 查询个人绩效排名
	 * @param dwbm
	 * @return
	 */
	List<Map<String, Object>> getRankOfGrjx(@Param("dwbm") String dwbm,
                                            @Param("year") String year);

	/**
	 * 根据条件获取个人绩效信息
	 * @return
	 */
	List<Map<String, Object>> getGrjxByCondition(@Param("dwbm") String dwbm,
                                                 @Param("bmbm") List<String> bmbm,
                                                 @Param("gh") String gh,
                                                 @Param("sfgs") String sfgs,
                                                 @Param("year") String year,
                                                 @Param("kssj") String kssj,
                                                 @Param("jssj") String jssj,
                                                 @Param("mc") String mc);

	List<Map<String, Object>> getGrjxOfIndex(@Param("dwbm") String dwbm,
                                             @Param("bmbm") List<String> bmbm,
                                             @Param("year") String year);
}
