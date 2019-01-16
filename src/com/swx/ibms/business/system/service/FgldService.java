package com.swx.ibms.business.system.service;

import com.swx.ibms.business.rbac.bean.BMBM;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.system.bean.Fgld;
import org.apache.ibatis.annotations.Param;

import java.util.List;



/**
 * @author zsq
 *  分管领导
 */
public interface FgldService {

	/**
	 * @param dwbm 单位编码 
	 * @return 获取分管领导配置
	 */
	List<Fgld> getFgld(@Param("dwbm") String dwbm);

	/**
	 * @param dwbm 单位编码 
	 * @return 获取该单位的副检察长(只有副检察长才能做分管领导)
	 */
	List<RYBM> getLd(String dwbm);

	/**
	 * @param dwbm 单位编码 
	 * @return 获取该单位的所有部门
	 */
	List<BMBM> getBm(String dwbm);

	/**
	 * @param ldgh 领导工号
	 * @param bmbm 部门编码
	 * @param dwbm 单位编码
	 * @return 添加分管领导
	 */
	int addFgld(String ldgh, String bmbm, String dwbm);

	/**
	 *  @param ldgh 领导工号
	 * @param dwbm 单位编码
	 * @param id id
	 * @param bmbm 部门编码
	 * @return 添加分管领导
	 */
	int updateFgld(String id, String ldgh, String bmbm, String dwbm);

	/**
	 * @param id id
	 * @return 删除分管领导
	 */
	int delete(String id);

}
