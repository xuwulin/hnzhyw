package com.swx.ibms.business.system.service;


import com.swx.ibms.business.rbac.bean.*;

import java.util.List;
import java.util.Map;

/**
 * 系统管理服务接口
 * @author 李治鑫
 *
 */
public interface XTGLService {

	/**
	 * 查询所有单位列表
	 * @return List<DWBM>
	 */
	List<DWBM> getAllDw();

	/**
	 * 查询单位信息
	 * @param dwbm 单位编码
	 * @return List<DWBM>
	 */
	List<DWBM> getDw(String dwbm);

	/**
	 * 添加单位
	 * @param dw 单位编码对象
	 * @return 执行结果
	 */
	int addDw(DWBM dw);
	/**
	 * 更新单位信息
	 * @param dw  单位编码对象
	 * @return 执行结果
	 */
	int updateDw(DWBM dw);
	/**
	 * 删除单位
	 * @param dwbm 单位编码
	 * @return 执行结果
	 */
	int deleteDw(String dwbm);

	/**
	 * 创建单位对象
	 * @param dwbm 单位编码
	 * @param dwmc 单位名称
	 * @param fdwbm 父单位编码
	 * @param dwjb 单位级别
	 * @param sfsc 是否删除
	 * @param dwjc 单位简称
	 * @param dwsx 单位标识
	 * @return 单位编码对象
	 */
	DWBM createDw(String dwbm,String dwmc,String fdwbm,
				  String dwjb,String sfsc,String dwjc,String dwsx);

	/**
	 * 查询某个单位下的所有部门列表
	 * @param dwbm 单位编码
	 * @return List<BMBM>
	 */
	List<BMBM> getBmByDwbm(String dwbm);

	/**
	 * 查询部门信息
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @return List<BMBM>
	 */
	List<BMBM> getBm(String dwbm,String bmbm);


	/**
	 * 添加部门
	 * @param bm 部门对象
	 * @return 执行结果
	 */
	int addBm(BMBM bm);

	/**
	 * 更新部门信息
	 * @param bm 部门对象
	 * @return 执行结果
	 */
	int updateBm(BMBM bm);

	/**
	 * 删除部门
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @return 执行结果
	 */
	int deleteBm(String dwbm,String bmbm);

	/**
	 * 创建部门对象
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @param fbmbm 父部门编码
	 * @param bmmc 部门简称
	 * @param bmjc 部门级别
	 * @param bmwhjc 部门文号简称
	 * @param bmahjc 部门案号简称
	 * @param sflsjg 是否临时部门
	 * @param sfcbbm 是否承办部门
	 * @param bmxh 部门序号
	 * @param bz 备注
	 * @param sfsc 是否删除
	 * @param bmys 部门映射
	 * @return 部门编码对象
	 */
	BMBM createBm(String dwbm,String bmbm,String fbmbm,
				  String bmmc,String bmjc,String bmwhjc,
				  String bmahjc,String sflsjg,String sfcbbm,
				  int bmxh,String bz,String sfsc,
				  String bmys);


	/**
	 * 查询某个部门下的所有人员
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @param jsbm 角色编码
	 * @return List<RYBM>
	 */
	List<RYBM> getRyByBmbm(String dwbm, String bmbm, String jsbm);//,int currentpage,int pageSize);

	/**
	 * 查询人员信息
	 * @param dwbm 单位编码
	 * @return List<RYBM>
	 */
	Map<String, Object> getRy(String dwbm, String name, Integer page, Integer rows);

	/**
	 * 添加人员
	 * @param ry 人员对象
	 * @return 执行结果
	 */
	int addRy(RYBM ry);

	/**
	 * 更新人员信息
	 * @param ry 人员对象
	 * @return 执行结果
	 */
	int updateRy(RYBM ry, String oldGh);

	/**
	 * 删除人员信息
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @param jsbm 角色编码
	 * @param gh 工号
	 * @return 执行结果
	 */
	int deleteRy(String dwbm,String bmbm,String jsbm,String gh);

	/**
	 * 创建人员对象
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param dlbm 登录别名
	 * @param kl 口令
	 * @param mc 名称
	 * @param gzzh 工作证号
	 * @param yddhhm 移动电话号码
	 * @param dzyj 电子邮件
	 * @param ydwbm 原单位编码
	 * @param ydwmc 原单位名称
	 * @param sflsry 是否临时人员
	 * @param sfsc 是否删除
	 * @param sftz 是否停职
	 * @param xb 性别
	 * @param caid CAid
	 * @param zrjcggh 主任检察官工号
	 * @return 人员编码对象
	 */
	RYBM createRy(String dwbm,String bmbm, String jsbm,String gh,String dlbm,String kl,
				  String mc,String gzzh,String yddhhm,String dzyj,
				  String ydwbm,String ydwmc,String sflsry,String sfsc,
				  String sftz,String xb,String caid,String zrjcggh);

	/**
	 * 查询某单位下某个部门所有角色列表
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @return List<JSBM>
	 */
	List<JSBM> getJsBybmbm(String dwbm, String bmbm);


	/**
	 * 查询角色信息
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @param jsbm 角色编码
	 * @return List<JSBM>
	 */
	List<JSBM> getJs(String dwbm,String bmbm,String jsbm);

	/**
	 * 添加角色
	 * @param js 角色对象
	 * @return 执行结果
	 */
	int addJs(JSBM js);
	/**
	 * 更新角色
	 * @param js 角色对象
	 * @return 执行结果
	 */
	int updateJs(JSBM js);


	/**
	 * 删除角色
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @param jsbm 角色编码
	 * @return 执行结果
	 */
	int deleteJs(String dwbm,String bmbm,String jsbm);

	/**
	 * 创建角色对象
	 * @param dwbm 单位编码
	 * @param jsbm 角色编码
	 * @param jsmc 角色名称
	 * @param bmbm 部门编码
	 * @param jsxh 角色序号
	 * @param spjsbm 审批角色部门
	 * @param description 描述
	 * @return 角色编码对象
	 */
	JSBM createJs(String dwbm,String jsbm,String jsmc,String bmbm,
				  int jsxh,String spjsbm,String description);

	/**
	 * 获取人员角色分配
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @param jsbm 角色编码
	 * @param gh 工号
	 * @return List<RYJSFP>
	 */
	List<RYJSFP> getFp(String dwbm, String bmbm, String jsbm, String gh);

	/**
	 * 更新分配信息
	 * @param ryjsfp 人员角色分配对象
	 * @return 执行结果
	 */
	int updateFp(RYJSFP ryjsfp);

	/**
	 * 增加分配信息
	 * @param ryjsfp 人员角色分配对象
	 * @return 执行结果
	 */
	int addFp(RYJSFP ryjsfp);

	/**
	 * 创建分配信息对象
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @param jsbm 角色编码
	 * @param gh 工号
	 * @param zjldgh 直接领导工号
	 * @param ryxh 人员序号
	 * @return 人员角色分配对象
	 */
	RYJSFP createFp(String dwbm,String bmbm,String jsbm,
					String gh,String zjldgh,int ryxh);

	/**
	 * 删除分配信息
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @param jsbm 角色编码
	 * @param gh 工号
	 * @return 执行结果
	 */
	int deleteFp(String dwbm,String bmbm,String jsbm,String gh);

	/**
	 * 根据档案中的所属人单位名称和所属人工号查询所属人名字
	 * @param ssrdwbm 所属人单位编码
	 * @param ssrgh 所属人工号
	 * @return 名字
	 */
	String getMcByGh(String ssrdwbm,String ssrgh);

	/**
	 * 根据单位编码获取角色信息
	 * @param dwbm
	 * @return
	 */
	Map<String, Object> getJsByDwbm(String dwbm, Integer page, Integer rows);

	/**
	 * 停职
	 * @param dwbm
	 * @param gh
	 * @return
	 */
	int suspension(String dwbm, String gh);

	/**
	 * 登录别名验证
	 * @param dlbm
	 * @return
	 */
	int aliasVerify(String dlbm, String dwbm);

	/**
	 * 部门名称验证
	 * @param dlbm
	 * @return
	 */
	int deptNameVerify(String dlbm, String dwbm);
}
