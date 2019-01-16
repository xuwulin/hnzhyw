package com.swx.ibms.business.system.mapper;

import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.rbac.bean.RYJSFP;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


/**
 * @author zsq
 *
 */
@SuppressWarnings("all")
public interface XtpzRybmMapper {

	/**
	 * @param dwbm
	 * 单位编码
	 * 
	 * @return
	 * 根据单位编码找出该单位最大的工号和工作证号
	 */
	RYBM getMax(@Param("dwbm")String dwbm);

	/**
	 * @param rybm
	 * 新增人员
	 */
	void insert(RYBM rybm);

	/**
	 * @param ryjsfp
	 * 新增人员 对应的角色分配
	 */
	void insertRyfp(RYJSFP ryjsfp);

	/**
	 * @param rybm
	 * 修改人员
	 */
	void update(RYBM rybm);

	/**
	 * @param map 
	 * 原角色编码
	 */
	void updateRyfp(Map map);

	/**
	 * @param dwbm
	 * 单位编码
	 * @param dlbm
	 * 登录别名
	 * @return 
	 * 返回查出的指定单位是否存在该登录别名
	 */
	int checkDlbm(@Param("dwbm")String dwbm,@Param("dlbm") String dlbm);

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
	void deletery(@Param("dwbm")String dwbm, @Param("bmbm")String bmbm,
			@Param("jsbm")String jsbm,@Param("gh") String gh);

	/** 更新密码
	 * @param dwbm  
	 * @param gh 工号
	 * @param kl 密码
	 */
	void updatepassword(@Param("dwbm") String dwbm, @Param("gh") String gh, @Param("kl") String kl);

	/**
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @return 人员信息
	 */
	RYBM canupdate(@Param("dwbm")String dwbm, @Param("gh")String gh);
	
	/**
	 * 删除人员
	 * @param dwbm 单位编码
	 * @param gh 工号
	 */
	void deleteRybm(@Param("dwbm")String dwbm,@Param("gh") String gh);
}
