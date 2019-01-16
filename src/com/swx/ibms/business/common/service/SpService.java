package com.swx.ibms.business.common.service;


import com.swx.ibms.business.appraisal.bean.Ywlxmc;
import com.swx.ibms.business.common.bean.Jdlc;
import com.swx.ibms.business.common.bean.Splc;
import com.swx.ibms.business.common.bean.Splcsl;
import com.swx.ibms.business.rbac.bean.BMBM;
import com.swx.ibms.business.rbac.bean.RYBM;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 审批服务接口
 * @author 李治鑫
 *
 */
public interface SpService {


	/**
	 * 查询最新的审批流程实例
	 * @param spdw 审批单位
	 * @param spbm 审批部门
	 * @param spr 审批人
	 * @param spstid 审批实体id
	 * @param splx 审批类型
	 * @return 审批流程实例列表
	 */
	List<Splcsl> selectNewSpsl(String spdw, String spbm, String spr, String spstid, String splx);

	/**
	 * 通过流程ID查询最新的审批流程实例
	 * @param map 参数map
	 * @return 审批流程实例列表
	 */
	List<Splcsl> selectNewSpslByLcid(Map<String,Object> map);

	/**
	 * 根据节点类型获取流程模板信息
	 * @param jdlx 节点类型
	 * @return 流程模板的列表
	 */
	List<Jdlc> getMbByJdlx(String jdlx);

	/**
	 * 根据节点类型和流程节点获取流程模板信息
	 * @param jdlx 节点类型
	 * @param lcjd 流程节点
	 * @return 流程模板的列表
	 */
	List<Jdlc> getMbByLcjd(String jdlx,String lcjd);

	/**
	 * 新增审批实例
	 * @param spdw 审批单位
	 * @param spbm 审批部门
	 * @param spr 审批人
	 * @param spyj 审批意见
	 * @param splx 审批类型
	 * @param spzt 审批状态
	 * @param spstid 审批实体ID
	 * @param cljs 处理角色
	 * @param lcjd 流程节点
	 * @param jdlx 节点类型
	 * @param cljsxz 处理角色限制
	 * @return 新增成功返回1，失败返回0
	 */
	Map<String, Object> addSplcslFirst(String spdw,String spbm,String spr,String spyj,String splx,
									   String spzt,String spstid,String cljs,String lcjd,String jdlx,String cljsxz);

	/**
	 * 新增审批实例
	 * @param spdw 审批单位
	 * @param spbm 审批部门
	 * @param spr 审批人
	 * @param spyj 审批意见
	 * @param splx 审批类型
	 * @param spzt 审批状态
	 * @param spstid 审批实体ID
	 * @param cljs 处理角色
	 * @param lcjd 流程节点
	 * @param jdlx 节点类型
	 * @param lcid 流程id
	 * @param cljsxz 处理角色限制
	 * @return 新增成功返回1，失败返回0
	 */
	String addSplcsl(String spdw,String spbm,String spr,String spyj,String splx,
					 String spzt,String spstid,String cljs,String lcjd,String jdlx,String lcid,String cljsxz);


	/**
	 * 更新审批实例
	 * @param spdw 审批单位
	 * @param spbm 审批部门
	 * @param spr 审批人
	 * @param spyj 审批意见
	 * @param splx 审批类型
	 * @param spzt 审批状态
	 * @param spstid 审批实体ID
	 * @param cljs 处理角色
	 * @param lcjd 流程节点
	 * @param jdlx 节点类型
	 * @return 更新成功返回1，失败返回0
	 */
	String updateSplcsl(String spdw,String spbm,String spr,String spyj,String splx,
						String spzt,String spstid,String cljs,String lcjd,String jdlx);

	/**
	 * 根据审批实体ID将月度考核信息置为已审批完成状态
	 * @param spstid 单位编码
	 * @return 更新成功返回1，否则返回0
	 */
	String setAudit(String spstid);

	/**
	 * 根据审批实体ID将档案归总修改公示状态
	 * @param spstid 审批实体ID
	 * @param zt 状态
	 * @return 更新成功返回1，否则返回0
	 */
	String updateGszt(String spstid,String zt);

	/**
	 * 获取人事部部门编码
	 * @param request 请求参数
	 * @return 部门编码
	 */
	String getRsbBmbm(HttpServletRequest request);

	/**
	 * 获取登录人的部门编码数组
	 * @param request 请求参数
	 * @return 部门编码数组
	 */
	String[] getDlrBmbm(HttpServletRequest request);

	/**
	 * 根据月度考核ID查询这个月度考核的业务类型和名称
	 * @param ydkhid 月度考核ID
	 * @return 业务类型名称
	 */
	List<Ywlxmc> getYwlxByYdkhid(String ydkhid);


	/**根据业务编码查询业务简称
	 * @param ywbm 业务编码
	 * @return 业务名称
	 */
	String getYwmcByYwbm (String ywbm);

	/**
	 * 根据部门映射编码查询业务编码
	 * @param bmys 部门映射
	 * @return 业务类型
	 */
	String getYwlxByBmys(String bmys);

	/**
	 * 根据单位编码和工号获取部门映射编码
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @return 部门映射
	 */
	List<String> getBmysByGh (String dwbm,String gh);

	/**根据审批实体ID和审批类型判断能否显示撤回申请按钮
	 * @param spstid 审批实体id
	 * @param splx 审批类型
	 * @return  0 不显示   jdlx(节点类型字符串)可能显示
	 */
	String getAllBySplx(String spstid,String splx);



	/**
	 * 撤回审批申请
	 * @param spstid 审批实体id
	 * @param splx 审批类型
	 * @return 执行结果
	 */
	String recallSp(String spstid,String splx);


	/**
	 * 返回是否有审批通过
	 * @param spstid 审批实体ID
	 * @param splx 审批类型
	 * @param lcid 流程ID
	 * @return 审批流程信息
	 */
	Splc isSptg(String spstid, String splx, String lcid);
	/**
	 * 通过单位和部门映射找部门
	 * @param dwbm 单位编码
	 * @param bmys 部门映射
	 * @return 部门映射编码
	 */
	String selectBmysBm(String dwbm,String  bmys);

	/**
	 * 通过流程ID获取该流程的发起人单位编码和工号
	 * @param lcid 流程ID
	 * @return 发起人
	 */
	RYBM getFqr(String lcid);

	/**
	 * 根据审批id查询审批信息
	 * @param spid 审批id
	 * @return 审批对象
	 */
	Splcsl getSpById(String spid);

	/**
	 * 根据单位编码和部门映射获取部门编码
	 * @param dwbm 单位编码
	 * @param bmys 部门映射
	 * @return 部门编码（如果存在多个部门编码则以","隔开）
	 */
	String getBmbmByBmys(String dwbm,String bmys);


	/**
	 * 通过审批实体ID和审批类型获取上一个审批流程实例的审批状态
	 * @param spstid 审批实体ID
	 * @param splx 审批类型
	 * @return 审批流程实例信息
	 */
	Splcsl getPreviousLcslBySpstid(String spstid,String splx);

	/**
	 * 通过单位编码和部门编码获取部门映射
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @return 部门编码信息
	 */
	BMBM getBmysByBmbm(String dwbm, String bmbm);

	/**
	 * 通过单位编码和工号获取部门编码
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @return 一个部门编码（多个则取第一个）
	 */
	String getBmbmByGh(String dwbm,String gh);

	/**
	 * 通过审批实体id和审批类型查询发起记录信息
	 * @param spstid 审批实体id
	 * @param splx 审批类型
	 * @return 发起记录信息
	 */
	Splcsl getFqjlBySpstidAndSplx(String spstid,String splx);

	List<Splcsl> getLartestSpxx(String spstid,String splx);

	Integer modifySplcslById(String spyj,String spzt, String cljs,String spId);
}
