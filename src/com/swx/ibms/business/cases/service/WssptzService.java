package com.swx.ibms.business.cases.service;
/**
 *案管办案件受理service接口
 *@author 罗华
 *
 *@version 1.0
 *
 *@since 2017年10月24日
 */

import java.util.List;
import java.util.Map;

/**
 *文书审批台账
 */
@SuppressWarnings("all")
public interface WssptzService {

	/**
	 * 获取部门受案号
	 * @param dwbm
	 * @param gh
	 * @param ksrq
	 * @param jsrq
	 * @return
	 * @throws Exception
	 */
	List<String> getBmsah(String dwbm, String gh, String ksrq, String jsrq) throws Exception;

	/**
	 * 获取统一受案号
	 * @param dwbm
	 * @param gh
	 * @param ksrq
	 * @param jsrq
	 * @return
	 * @throws Exception
	 */
	List<String> getTysah(String dwbm, String gh, String ksrq, String jsrq) throws Exception;

	/**
	 * 展示文书审批台账
	 * @param did
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getALLWssptz(String dwbm, String gh, int page, int pageSize) throws Exception;

	/**
	 * 展示文书审批详情
	 * @param bmsah
	 * @param tysah
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getWsDetail(String dwbm, String gh, int page, int pageSize,
									String ksrq, String jsrq, String bmsah, String tysah) throws Exception;

	String getAjmcByBmsahAndTysah(String bmsah, String tysah) throws Exception;

	/**
	 * 获取审批的案件
	 * @param dwbm
	 * @param gh
	 * @param page
	 * @param pageSize
	 * @param ksrq
	 * @param jsrq
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getSpaj(String dwbm, String gh, int page, int pageSize, String ksrq, String jsrq) throws Exception;

}
