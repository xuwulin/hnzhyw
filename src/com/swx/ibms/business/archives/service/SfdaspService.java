package com.swx.ibms.business.archives.service;

import com.swx.ibms.business.common.bean.Jdlc;
import com.swx.ibms.business.common.bean.Splc;

import java.util.List;
import java.util.Map;



/**
 * 司法档案审批接口
 * @author 王宇锋
 * @since 2017年3月1日
 */
public interface SfdaspService {
	/**
	 * @param 审批map集合
	 * @return 是否修改成功 1 = 成功, 0 = 不成功
	 */
	String insertSplcsl(Map map);
	/**
	 * @param map
	 * @return 是否修改成功 1 = 成功, 0 = 不成功
	 */
	String insertClsw(Map map);
	
	List showSplc(Splc splc);
	/**
	 * 是否有审批流程实例
	 */
	String select_sfysplcsl(Map map);
	
	/**
	 * 节点流程
	 */
	Jdlc select_jdlc(Map map);
	
	//改表后的服务
	/**
	 * 更改表结构后的新增审批实例
	 * @param map 参数map
	 * @return 成功 = 1， 不成功 = 0；
	 */
	String addSplcsl(Map<String,Object> map);
	
	/**
	 * 更改表结构后的跟新审批实例
	 * @param map 参数map
	 * @return 成功=1，失败 = 0；
	 */
	String updateSplcsl(Map<String ,Object> map);
	
	/**
	 * 通过节点类型、流程节点获取流程模板信息
	 * @param jdlx 节点类型
	 * @param lcjd 流程节点
	 * @return 流程模板列表
	 */
	List<Jdlc> getLcmb(String jdlx,String jdzt,String lclx);
	
	//重载审批实例的详细服务
	
		/**
		 * 新增审批实例
		 * @param spdw 审批单位
		 * @param spr 审批人
		 * @param spyj 审批意见
		 * @param splx 审批流程
		 * @param spzt 审批状态
		 * @param spstid 审批实体ID
		 * @param lcjd 流程节点
		 * @param jdlx 节点类型
		 * @return 执行结果，1成功 0失败
		 */
	String addSplcsl(String spdw,String spr,String spyj,
			String splx,String spzt,String spstid,String lcjd,
			String jdlx);
	
	
	/**更新审批流程实例
	 * @param spdw 审批单位
	 * @param spr 审批人
	 * @param spzt 审批状态
	 * @param spstid 审批实体ID
	 * @param spyj 审批意见
	 * @param lcjd 流程节点
	 * @param jdlx 节点类型
	 * @return 执行结果 1成功 0失败
	 */
	String updateSplcsl(String spdw,String spr,String spzt,String spstid,
			String spyj,String lcjd,String jdlx);
	
	
	/**
	 * 根据审批实体ID获取最新的流程实例信息
	 * @param spstid 审批实体ID
	 * @return 节点流程+节点类型
	 */
	String[] getrecentlcsl(String spstid);
	
	Splc getlcydwlist(String lcid);
}
