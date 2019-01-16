package com.swx.ibms.business.cases.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *文书审批台账
 */
public interface WssptzMapper {

	/**
	 * 根据单位编码，工号，开始日期，结束日期到ajjbxx表中去查部门受案号和统一受案号(集合)
	 * @param dwbm
	 * @param gh
	 * @param ksrq
	 * @param jsrq
	 * @return
	 */
	List<String> getBmsah(@Param("dwbm") String dwbm,
						  @Param("gh") String gh,
						  @Param("ksrq") String ksrq,
						  @Param("jsrq") String jsrq);

	List<String> getTysah(@Param("dwbm") String dwbm,
						  @Param("gh") String gh,
						  @Param("ksrq") String ksrq,
						  @Param("jsrq") String jsrq);

	/**
	 * 根据档案id查询所有文书审批台账
	 * @param
	 * @return
	 */
	List<Map<String, Object>> selectALLWssptz(@Param("dwbm") String bmsah, @Param("gh") String gh);

	/**
	 * 查看文书审批详情
	 * @return
	 */
	List<Map<String, Object>> selectWsDetail(@Param("dwbm") String dwbm,
											 @Param("gh") String gh,
											 @Param("startDate") String startDate,
											 @Param("endDate") String endDate,
											 @Param("bmsah") String bmsah,
											 @Param("tysah") String tysah);

	String getAjmcByBmsahAndTysah(@Param("bmsah") String bmsah,
								  @Param("tysah") String tysah);

	/**
	 * 获取审批案件部门受案号
	 * @param dwbm
	 * @param gh
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<String> selectSpajBmsahs(@Param("dwbm") String dwbm,
								  @Param("gh") String gh,
								  @Param("startDate") String startDate,
								  @Param("endDate") String endDate);

	/**
	 * 根据部门受案号查询案件
	 * @param bmsahs
	 * @return
	 */
	List<Map<String, Object>> selectAjByBmsahs(@Param("bmsahs") List<List<String>> bmsahs,
											   @Param("dwbm") String dwbm,
											   @Param("gh") String gh);
}
