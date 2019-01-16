package com.swx.ibms.business.rbac.service;

import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.common.utils.PageCommon;

import java.sql.Clob;



/**
 * <p>
 * Title:GlyService
 * </p>
 * <p>
 * Description: 管理员service
 * </p>
 * author 朱春雨 date 2017年4月27日 下午2:06:53
 */
public interface GlyService {
	/**
	 * 插入管理员
	 * 
	 * @param dwbm
	 * @param dlbm
	 * @param kl
	 * @param mc
	 * @param xb
	 * @param message
	 * @return
	 */
	String insertGly(String dwbm, String dlbm, String kl, String mc, String xb, Clob qxxx);

	/**
	 * 删除管理员
	 * 
	 * @param dwbm
	 * @param dlbm
	 * @param kl
	 * @param mc
	 * @param xb
	 * @param qxxx
	 * @return
	 */
	String deleteGly(String dwbm, String gh);

	/**
	 * 更新管理员
	 * 
	 * @param dwbm
	 * @param gh
	 * @param dlbm
	 * @param kl
	 * @param mc
	 * @param xb
	 * @param qxxx
	 * @return
	 */
	String updateGly(String dwbm, String gh, String dlbm, String kl, String mc, String xb, Clob qxxx);
	/**
	 * @param dwbm
	 * @param sfsc
	 * @param nowPage
	 * @param pageSize
	 * @return
	 */
	PageCommon<RYBM> selectGlyPageList(String dwbm, String sfsc, int nowPage, int pageSize);
}
