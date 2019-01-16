package com.swx.ibms.business.common.service;

/**
 * 权限服务接口
 * @author 李治鑫
 *
 */
public interface QXService {
	/**
	 * @param dwbm 登录人单位编码
	 * @param gzzh 登录人工号
	 * @return 如果该账号属于档案管理员，返回true；否则返回false
	 */
	Boolean isAdmin(String dwbm,String gzzh);
}
