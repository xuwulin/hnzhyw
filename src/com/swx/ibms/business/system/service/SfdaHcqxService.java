package com.swx.ibms.business.system.service;

import com.swx.ibms.business.system.bean.Hcqx;

import java.util.List;



/**
 * @author zsq
 *
 */
public interface SfdaHcqxService {

	/**
	 * @return  获取核查期限信息
	 */
	List<Hcqx> getHcqx();

	/**
	 * @param hcqx 核查期限
	 * @param gxr 更新人
	 * @return 更新核查期限信息
	 */
	boolean setHcqx(int hcqx, String gxr);

	/**
	 * @param gsqx 公示期限
	 * @param gxr 更新人
	 * @return 更新公示期限信息
	 */
	boolean setGsqx(int gsqx, String gxr);

}
