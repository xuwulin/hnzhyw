package com.swx.ibms.business.rbac.service;


import com.swx.ibms.business.rbac.bean.Bmmc;
import com.swx.ibms.business.rbac.bean.JSBM;
import com.swx.ibms.business.rbac.bean.RYBM;

import java.util.List;
import java.util.Map;

/**
 * 根据单位编号和工号返回部门名称实体类集合
 * 根据单位编号返回单位名称
 * @author 王宇锋
 */
public interface BmmcService {
	/**
	 * 得到该单位编码下的所有部门名称
	 * @param dwbm 单位编码
	 * @return 全部部门名称集合
	 */
	List<Bmmc> getAllBmmc(String dwbm);
	/**
	 * @param dwbm 单位编码
	 * @return 查询所有部门编码
	 */
	String getAllBmbms(String dwbm);
	/**
	 * 返回部门名称
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @return 部门名称
	 */
	List<Bmmc> getBmmc(String dwbm, String gh);
	/**
	 * 返回单位名称
	 * @param dwbm 单位编码
	 * @return 单位名称
	 */
	String getDwmc(String dwbm);
	/**
	 * 根据单位编码返回首页绩效信息
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param ryjs 人员角色
	 * @param bmjs 部门角色
	 * @return  绩效首页信息
	 */
	Map<String,Object> getSyjxxx(String dwbm, String gh, List<Integer> ryjs, List<String> bmjs);
	/**
	 * @param rootdwbm
	 * 单位编码
	 * @return
	 * 根据单位查找部门
	 */
	List<Bmmc> getBmByDwbm(String rootdwbm);
	/**
	 * @param dwbm
	 * 单位编码
	 * @param bmbm
	 * 部门编码
	 * @param jsbm
	 * 角色编码
	 * @param pagesize
	 * 页面大小
	 * @param curentpage
	 * 当前页
	 * @return
	 * 根据部门单位 查找人员
	 */
	List<RYBM> getBmmcBybmbm(String dwbm, String bmbm, String curentpage, String pagesize, String jsbm);
	/**
	 * @param dwbm
	 * 单位编码
	 * @param bmbm
	 * 部门编码
	 * @return
	 * 根据部门编码获得该部门下所有的角色信息
	 */
	List<JSBM> getJsBybm(String dwbm, String bmbm);
	/**
	 * @param dwbm
	 * 单位编码
	 * @param bmbm
	 * 部门编码
	 * @param jsbm
	 * 角色编码
	 * @return
	 * 根据单位编码 部门编码 角色编码查询人员总数
	 */
	int getTotal(String dwbm, String bmbm, String jsbm);
	/**
	 * @param dwbm
	 * 单位编码
	 * @param bmbm
	 * 部门编码
	 * @return
	 * 根据单位编码 部门编码查询人员总数
	 */
	int getTotalBybm(String dwbm, String bmbm);
	/**
	 * @param dwbm
	 * 单位编码
	 * @param bmbm
	 * 部门编码
	 * @param pagesize
	 * 页面大小
	 * @param curentpage
	 * 当前页
	 * @return
	 * 根据部门单位 查找人员
	 */
	List<RYBM> getRyByBmbm(String curentpage, String pagesize, String dwbm, String bmbm);

}
