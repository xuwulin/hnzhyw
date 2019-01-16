package com.swx.ibms.business.archives.service;

import com.swx.ibms.business.archives.mapper.SfdaspMapper;
import com.swx.ibms.business.common.bean.Jdlc;
import com.swx.ibms.business.common.bean.Splc;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 王宇锋
 * @since 2017年3月1日
 */
@SuppressWarnings("all")
@Service("sfdaspService")
public class SfdaspServiceImpl implements SfdaspService{
	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(SfdaspServiceImpl.class);
	@Resource
	private SfdaspMapper sfdaspMapper;
	/**
	 * 操作审批表一
	 */
	@Override
	public String insertSplcsl(Map map) {
		map.put("Y","");
		try {
			sfdaspMapper.addSplcsl(map);;
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		return (String)map.get("Y");
	}
	/**
	 * 操作审批表二
	 */
	@Override
	public String insertClsw(Map map) {
		map.put("Y",StringUtils.EMPTY);
		try {
			sfdaspMapper.addClsw(map);;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("【添加事务处理信息失败！】", e);
		}
		return (String)map.get("Y");
	}
	/**
	 *  查询该档案的审批流程
	 */
	@Override
	public List showSplc(Splc splc) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_spstid", splc.getSpstid());
		map.put("p_splx", splc.getSplx());
		map.put("p_lcid", splc.getLcid());
		map.put("p_cursor", StringUtils.EMPTY);
		try {
			sfdaspMapper.showSplc(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Splc> list=(List<Splc>)map.get("p_cursor");
		return list;
	}
	
	/**
	 * 是否有审批流程实例
	 */
	@Override
	public String select_sfysplcsl(Map map) {
		sfdaspMapper.sfysplcsl(map);
		String Y=(String)map.get("Y");
		return Y;
	}
	/**
	 * 节点流程
	 */
	@Override
	public Jdlc select_jdlc(Map map) {
		
		sfdaspMapper.select_jdlc(map);
		List  list=(List)map.get("cursor");
		Jdlc jdlc=(Jdlc)list.get(0);
		return jdlc;
	}
	
	/**
	 * 更改表结构后的新增审批实例
	 * @param map 参数map
	 * @return 成功 = 1， 不成功 = 0；
	 */
	@Override
	public String addSplcsl(Map<String, Object> map) {
		
		try {
			sfdaspMapper.addNewSplcsl(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		return (String) map.get("Y");
	}
	
	/**
	 * 更改表结构后的跟新审批实例
	 * @param map 参数map
	 * @return 成功=1，失败 = 0；
	 */
	@Override
	public String updateSplcsl(Map<String, Object> map) {
		try {
			sfdaspMapper.updateNewSplcsl(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		return (String) map.get("Y");
	}
	
	
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
	public String addSplcsl(String spdw,String spr,String spyj,
			String splx,String spzt,String spstid,String lcjd,
			String jdlx){
		
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("p_spdw", spdw);
		map.put("p_spr", spr);
		map.put("p_spyj", spyj);
		map.put("p_splx", splx);
		map.put("p_spzt", spzt);
		map.put("p_spstid", spstid);
		map.put("p_lcjd", lcjd);
		map.put("p_jdlx", jdlx);
		map.put("Y", StringUtils.EMPTY);
		
		try {
			sfdaspMapper.addNewSplcsl(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		
		if("1".equals(map.get("Y"))){
			return "1";
		}else{
			return "0";
		}
	}
	
	
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
	public String updateSplcsl(String spdw,String spr,String spzt,String spstid,
			String spyj,String lcjd,String jdlx){
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("p_spdw", spdw);
		map.put("p_spr", spr);
		map.put("p_spzt", spzt);
		map.put("p_spyj", spyj);
		map.put("p_lcjd", lcjd);
		map.put("p_jdlx", jdlx);
		map.put("Y", StringUtils.EMPTY);
		
		try {
			sfdaspMapper.updatesplcsl(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		
		if("1".equals(map.get("Y"))){
			return "1";
		}else{
			return "0";
		}
	}
	
	
	@Override
	public List<Jdlc> getLcmb(String jdlx, String jdzt,String lclx) {
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("p_jdlx", jdlx);
		map.put("p_jdzt", jdzt);
		map.put("p_lclx", lclx);
		map.put("p_cursor", StringUtils.EMPTY);
		map.put("p_errmsg", StringUtils.EMPTY);
		
		try {
			sfdaspMapper.getLcmb(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		
		String temp = (String) map.get("p_errmsg");
		if(StringUtils.isEmpty(temp)){
			List<Jdlc> jdlcList = (List<Jdlc>) map.get("p_cursor");
			return jdlcList;
		}else{
			return null;
		}
	}
	@Override
	public String[] getrecentlcsl(String spstid) {
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("p_spstid", spstid);
		map.put("p_lcjd", StringUtils.EMPTY);
		map.put("p_jdlx", StringUtils.EMPTY);
		map.put("p_errmsg", StringUtils.EMPTY);
		
		String[] resultArray = {"",""};
		
		if(!StringUtils.isEmpty(spstid)){
			try {
				sfdaspMapper.getrecentlcsl(map);
			} catch (Exception e) {
				LOG.error(StringUtils.EMPTY, e);
			}
			resultArray[0] = (String) map.get("p_lcjd");
			resultArray[1] = (String) map.get("p_jdlx");
			
		}
			return resultArray;
	}

	/**
	 * 通过流程id得到流程的详细信息
	 */
	@Override
	public Splc getlcydwlist(String lcid) {
		Map<String,Object> map = new HashMap<>();
		
		map.put("p_lcid", lcid);
		map.put("p_cur", "");
		
		sfdaspMapper.getlcydwlist(map);
		
		List<Splc> list = (List<Splc>) map.get("p_cur");
		
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		Splc splc = null;
		for(Splc s:list){
			if("201".equals(s.getJdlx())){
				splc = s;
				break;
			}
		}
		return splc;
	}
}
