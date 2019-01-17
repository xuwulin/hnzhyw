package com.swx.ibms.business.performance.service;


import com.swx.ibms.business.rbac.bean.Bmmc;

import java.util.List;
import java.util.Map;

/**
 * 个人绩效首页展示接口
 * @author 王宇锋
 */
public interface GrjxkhsyService {
	/**
	 * 个人绩效首页刚加载时返回数据
	 * @param dwbm 单位编码
	 * @param rymc 人员名称
	 * @param page 页数
	 * @param ryjs 人员角色
	 * @param gh 工号
	 * @param bmjs 部门角色
	 * @param cxbmbm 查询部门编码
	 * @param cxdwbm 查询单位编码
	 * @return 返回值
	 */
	Map<String, Object> getJzjxkh(String dwbm, String rymc, int page, List<Integer> ryjs,
								  String gh, List<String> bmjs, String cxbmbm, String cxdwbm, String ywlx, int year, int jd);
	/**
	 * 通过单位编码,工号,人员角色,部门角色找到时间数据和对应类型列表
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param ryjs 人员角色
	 * @param bmjs 部门角色
	 * @return 时间数据和相关部门列表
	 */
	Map<String, Object> getDqsj(String dwbm, String gh, List<Integer> ryjs, List<String> bmjs);
	/**
	 * 生成人员名称sql
	 * @param rymc 人员名称字符串
	 * @return 返回生成的人员名称sql语句
	 */
	String getRymcSql(String rymc);
	/**
	 * 获得相关部门信息
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param ryjs 人员角色
	 * @param cxDwbm 查询单位编码
	 * @param bmjs 部门角色
	 * @return 部门信息列表
	 */
	List<Bmmc> getXgBm(String dwbm, String gh, List<Integer> ryjs, String cxDwbm, List<String> bmjs);
	/**
	 * 返回考核行数
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @return 考核行数
	 */
	int getkhcount(String dwbm, String gh);

	/**
	 * 通过月度考核id查询业务考核分值id
	 * @param ydkhid 月度考核id
	 * @return 业务考核分值id
	 */
	String getYwkhfzidByYdkhid(String ydkhid);

	/**
	 * 通过单位编码和部门编码获取部门名称
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @return 部门名称
	 */
	String getBmmcByDwbmAndBmbm(String dwbm, String bmbm);

	Map<String, Object> queryGrjxByCondition(String loger_dwbm, String loger_gh,
											 String loger_bmbm, List<String> loger_bmyslist,
											 List<String> loger_bmjs, String query_dwbm,
											 String query_bmbm, String query_bmlbbm, String query_sfgs,
											 String query_year, String query_kssj,
											 String query_jssj, String query_name, Integer page);

	/**
	 * 获取首页的个人绩效信息统计
	 * 考核时间：直接取考核日期表中最新的考核日期
	 * 是否审批：首页只展示已审批的个人绩效
	 * 权限：同个人绩效首页查询权限
	 * @param dwbm
	 * @param gh
	 * @param bmysList
	 * @param bmjsList
	 * @return
	 */
	Map<String, Object> getGrjxOfIndex(String dwbm, String gh, List<String> bmysList, List<String> bmjsList);

	Map<String, Object> exportAll(String dwbm, String dwmc, String bmbm, String bmlbbm, String bmmc, String sfgs, String year, String kssj, String jssj,
								  String name, String[] deptList, String[] queryDeptList ,String userPermissions, String nameOfExporter);
}
