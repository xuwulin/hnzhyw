package com.swx.ibms.business.system.mapper;

import com.swx.ibms.business.rbac.bean.BMBM;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.system.bean.Fgld;
import org.apache.ibatis.annotations.Param;

import java.util.List;



/**
 * @author zsq
 * 分管领导
 */
public interface FgldMapper {

	/**
	 * @param dwbm 单位编码 
	 * @return 获取分管领导配置
	 */
	List<Fgld> getFgld(@Param("dwbm") String dwbm);

	/**
	 * @param dwbm 单位编码 
	 * @return 获取该单位的副检察长(只有副检察长才能做分管领导)
	 */
	List<RYBM> getLd(@Param("dwbm") String dwbm);

	/**
	 * @param dwbm 单位编码 
	 * @return 获取该单位的所有部门
	 */
	List<BMBM> getBm(@Param("dwbm") String dwbm);

	/**
	 * @param ldgh 领导工号
	 * @param bmbm 部门编码
	 * @param dwbm 单位编码
	 */
	void addFgld(@Param("ldgh") String ldgh, @Param("bmbm") String bmbm,@Param("dwbm") String dwbm);

	/**
	 *  @param ldgh 领导工号
	 * @param dwbm 单位编码
	 * @param id id
	 * @param bmbm 部门编码
	 */
	void updateFgld(@Param("id")String id,@Param("ldgh") String ldgh,
					@Param("bmbm") String bmbm, @Param("dwbm")String dwbm);

	/**
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @return 返回部门信息
	 */
	List<BMBM> getBmmc(@Param("dwbm")String dwbm, @Param("bmbm")List<String> bmbm);

	/**
	 * @param id id
	 */
	void delete(@Param("id") String id);

	/**
	 * 根据单位编码和部门名称获取院领导信息【当根据部门映射查找院领导失败时调用】
	 *@param:@param dwbm 单位编码
	 *@param:@return
	 *@return:List<Fgld>
	 *@throws
	 */
	List<RYBM> getAllLdByDwBmmc(@Param("dwbm") String dwbm);

}
