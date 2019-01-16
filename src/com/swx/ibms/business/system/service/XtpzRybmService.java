package com.swx.ibms.business.system.service;


import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.rbac.bean.RYJSFP;

/**
 * @author zsq
 *
 */
public interface XtpzRybmService {

	/**
	 * @param rybm
	 * rybm
	 * @param ryjsfp
	 * ryjsfp
	 * @param oldbmbm
	 * 原部门编码
	 * @param oldjsbm
	 * 原角色编码
	 */
	void insertRy(RYBM rybm, RYJSFP ryjsfp, String oldbmbm, String oldjsbm);

	/**
	 * @param dwbm
	 * 单位编码
	 * @param dlbm
	 * 被检查的登录别名
	 * @return 
	 * 返回查出的指定单位是否存在该登录别名
	 */
	int checkDlbm(String dwbm, String dlbm);

	/**删除人员
	 * @param dwbm
	 * 单位编码
	 * @param bmbm
	 * 部门编码
	 * @param jsbm
	 * 角色编码
	 * @param gh
	 * 工号
	 */
	void deletery(String dwbm, String bmbm, String jsbm, String gh);

	/** 更新密码
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param kl 密码
	 */
	void updatepassword(String dwbm, String gh, String kl);

	/** 
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @return 是不是综合业务的人
	 */
	String canupdate(String dwbm, String gh);

}
