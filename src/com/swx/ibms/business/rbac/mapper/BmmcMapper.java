package com.swx.ibms.business.rbac.mapper;

import com.swx.ibms.business.rbac.bean.Bmmc;
import com.swx.ibms.business.rbac.bean.JSBM;
import com.swx.ibms.business.rbac.bean.RYBM;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * @author 王宇锋
 */
public interface BmmcMapper {
	/**
	 *
	 * @param map 得到该单位下的所有部门
	 */
	void getAllBmmc(Map<String,Object> map);
	/**
	 * @param map 查询该单位下所有部门编码
	 */
	void getAllBmbms(Map<String,Object> map);
	/**
	 * 查询单位名称
	 */
	void getDwmc(Map<String,Object> map);
	/**
	 * 查询部门名称
	 */
	void getBmmc(Map<String,Object> map);
	/**
	 * 获得未考核人数和总人数
	 */
	void getKhRs(Map<String,Object> map);
	/**
	 * 获得绩效得分排名前五
	 */
	void getJxdfpm(Map<String,Object> map);
	/**
	 * 获得绩效业务类型,最高分,平均分,最低分
	 */
	void getJxjhsj(Map<String,Object> map);
	/**
	 * @param dwbm
	 * @return
	 * 根据单位查找部门
	 */
	List<Bmmc> getBmByDwbm(@Param("dwbm") String dwbm);
	/**
	 * @param startrow 起始记录数
	 * @param endrow  结束记录数
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @param jsbm 角色编码
	 * @return  根据部门单位 查找人员
	 */
	List<RYBM> getBmmcBybmbm(@Param("startrow") Integer startrow, @Param("endrow") Integer endrow,
							 @Param("dwbm") String dwbm, @Param("bmbm")String bmbm, @Param("jsbm")String jsbm);
	/**
	 * @param startrow 起始记录数
	 * @param endrow 结束记录数
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @return
	 * 根据部门单位 查找人员
	 */
	List<RYBM> getBmmcBybmbm2(@Param("startrow") Integer startrow,@Param("endrow") Integer endrow,
							  @Param("dwbm") String dwbm, @Param("bmbm")String bmbm);
	/**
	 * @param bmbm
	 * @param dwbm
	 * @return
	 * 根据部门编码获得该部门下所有的角色信息
	 */
	List<JSBM> getJsBybm(@Param("dwbm")String dwbm, @Param("bmbm") String bmbm);
	/**
	 * @param dwbm
	 * 单位编码
	 * @param bmbm
	 * 部门编码
	 * @param jsbm
	 * 角色编码
	 * @return
	 * 总记录数
	 */
	int getTotal(@Param("dwbm")String dwbm, @Param("bmbm")String bmbm, @Param("jsbm")String jsbm);
	/**
	 * @param dwbm
	 * 单位编码
	 * @param bmbm
	 * 部门编码
	 * @return
	 * 根据单位编码 部门编码查询人员总数
	 */
	int getTotalBybm(@Param("dwbm")String dwbm, @Param("bmbm")String bmbm);
	/**
	 * @param dwbm
	 * 单位编码
	 * @param bmbm
	 * 部门编码
	 * @param startrow
	 * 起始位置
	 * @param endrow
	 * 结束位置
	 * @return
	 * 根据部门单位 查找人员
	 */
	List<RYBM> getRyByBmbm(@Param("startrow") int startrow,@Param("endrow") int endrow,
						   @Param("dwbm") String dwbm, @Param("bmbm") String bmbm);
}
