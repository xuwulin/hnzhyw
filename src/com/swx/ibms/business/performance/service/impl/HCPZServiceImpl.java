/**
 * 
 */
package com.swx.ibms.business.performance.service.impl;


import com.swx.ibms.business.archives.mapper.JcgSfdaCxMapper;
import com.swx.ibms.business.performance.bean.HCPZ;
import com.swx.ibms.business.performance.mapper.HCPZMapper;
import com.swx.ibms.business.performance.service.HCPZService;
import com.swx.ibms.business.system.bean.bmysbm;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 核查配置服务
 * @author 封泽超
 *@since 2017年4月11日
 */
@Service("hCPZService")
public class HCPZServiceImpl implements HCPZService {

	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(HCPZServiceImpl.class);

	/**
	 * 核查配置mapper
	 */
	@Resource
	private HCPZMapper hcpzmapper;
	
	/**
	 * 映射关系
	 */
	@Resource
	private JcgSfdaCxMapper jcgSfdaCxMapper;

	/**
	 * 根据核查单位与被核查单位和业务类型,进行部门映射.
	 */
	@Override
	public String insert(HCPZ hcpz) {
		//查询是否存在交叉核查配置,存在则跳出
		if(isYscz(hcpz)){
			return "-1";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		bmysbm hcbm = null;
		
		//获取被核查部门列表
			//获取部门映射
		map.put("p_ywlx", hcpz.getYwlx());
		map.put("p_cur","");
		hcpzmapper.getbmlbbyywlx(map);
		List<HCPZ> bmyslista =  (List<HCPZ>) map.get("p_cur");
		if(CollectionUtils.isEmpty(bmyslista)) {
			return "0";
		}
		map.clear();
			//获取部门列表
		List<bmysbm> listb = new ArrayList<bmysbm>();
		List<bmysbm> temp;
		for(HCPZ ss:bmyslista){
			map.put("p_dwbm", hcpz.getBhcdwbm());
			map.put("p_bmys", "'"+ss.getBmlbbm() +"'");
			map.put("p_cursor", null);
			jcgSfdaCxMapper.selectBmysBm(map);
			temp = (List<bmysbm>) map.get("p_cursor");
			listb.addAll(temp);
			map.clear();
		}

		//获取核查部门,取一个
			//获取部门列表
		List<bmysbm> list = new ArrayList<bmysbm>();
		for(HCPZ ss:bmyslista){
			map.put("p_dwbm", hcpz.getHcdwbm());
			map.put("p_bmys", "'"+ss.getBmlbbm() +"'");
			map.put("p_cursor", null);
			jcgSfdaCxMapper.selectBmysBm(map);
			temp = (List<bmysbm>) map.get("p_cursor");
			list.addAll(temp);
			map.clear();
		}
		//查询结果其中有一方部门结果集为空.则无法完成配置
		if(CollectionUtils.isEmpty(list) || CollectionUtils.isEmpty(listb)) {
			return "0";
		}
		
		//特殊处理,优先处理只有一个部门映射的部门,大概率可能是主要负责该业务类型的部门
		for(bmysbm b:list){
			if(b.getFbmbm() ==null || "".equals(b.getFbmbm())){
				if(hcbm == null || (hcbm.getBmys().indexOf(";") > 0)) {
					hcbm = b;
				}
			}
		}
		//如果特殊处理没有找到这样的部门,则只能取第一个
		if(hcbm == null) {
			hcbm = list.get(0);
		}
		
		//生成交叉核查配置的数据
		ArrayList<HCPZ> hcpzs = new ArrayList<HCPZ>();
		for(bmysbm b:listb){
			HCPZ h = new HCPZ();
			
			h.setHcdwbm(hcbm.getDwbm());
			h.setHcbmbm(hcbm.getBmbm());
			h.setBhcdwbm(b.getDwbm());
			h.setBhcbmbm(b.getBmbm());
			h.setYwlx(hcpz.getYwlx());
			h.setKssj(hcpz.getKssj().toString());
			h.setJssj(hcpz.getJssj().toString());
			
			hcpzs.add(h);
		}
		
		//插入交叉核查配置信息
		
		for(HCPZ h:hcpzs){
			map.put("p_hcdwbm", h.getHcdwbm());
			map.put("p_hcbmbm", h.getHcbmbm());
			map.put("p_bhcdwbm", h.getBhcdwbm());
			map.put("p_bhcbmbm", h.getBhcbmbm());
			map.put("p_ywlx", h.getYwlx());
			map.put("p_kssj", h.getKssj());
			map.put("p_jssj", h.getJssj());
			map.put("Y", "0");
			hcpzmapper.insert(map);
		}
		return (String) map.get("Y");
	}


	/**
	 * 根据被核查单位与业务类型,删除
	 */
	@Override
	public String delete(HCPZ hcpz) {
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("p_bhcdwbm", hcpz.getBhcdwbm());
		map.put("p_ywlx", hcpz.getYwlx());
		map.put("p_kssj", hcpz.getKssj());
		map.put("Y", "0");
		
		hcpzmapper.delete(map);
		
		return map.get("Y");
	}


	/**
	 * 由于修改映射后,部门对部门的映射个数可能有变化,所以先删除
	 * 修改按照时间来修改
	 */
	@Override
	public String update(HCPZ hcpz) {
		//是否可以继续操作的标示
		String p = "1";
		//查询修改的配置是否存在
		if(isYscz(hcpz)){
			 return "-1";
		}
		//查询是否存在映射,存在则删除,用旧时间查询
		String kssj = hcpz.getKssj();
		hcpz.setKssj(hcpz.getOldkssj());
		if(isYscz(hcpz)){
			p = delete(hcpz);
		}
		hcpz.setKssj(kssj);
		if("1".equals(p)){
			p = insert(hcpz);
		}
		return p;
	}


	/**
	 * 单位编码 的方式查询 与该单位相关的所有映射
	 */
	@Override
	public Map<String,Object> select(String bhcdw,String page1,String pagesize1) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		//分页,计算开始条数和结束条数
		int page = Integer.valueOf(page1);
		int pagesize = Integer.valueOf(pagesize1);
		if(page <= 0 || pagesize <=0){
			return null;
		}
		int pages = (page - 1) * pagesize;
		int pagee = pages + pagesize;
		pages++;
		//查询
		map.put("p_bhcdwbm", bhcdw);
		map.put("p_pages", "" + pages);
		map.put("p_pagee", "" + pagee);
		map.put("p_total", "");
		map.put("p_cur", "");

		hcpzmapper.select(map);
		
		return map;
	}


	/**
	 * 单位-业务类型 的方式查询所有相关的单位&业务
	 */
	@Override
	public List<HCPZ> selectdwbmywlx(HCPZ hcpz) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("p_bhcdwbm", hcpz.getBhcdwbm());
		map.put("p_ywlx", hcpz.getYwlx());
		map.put("p_cur", "");
		
		hcpzmapper.selectdwbmywlx(map);
		List<HCPZ> list = (List<HCPZ>) map.get("p_cur");
		
		if(CollectionUtils.isEmpty(list)) {
			list = null;
		}
		return list;
	}
	/**
	 * 单位-业务类型-时间 的方式查询所有相关的单位&业务
	 */
	@Override
	public List<HCPZ> selectdwbmywlxsj(HCPZ hcpz) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("p_bhcdwbm", hcpz.getBhcdwbm());
		map.put("p_ywlx", hcpz.getYwlx());
		map.put("p_sj", hcpz.getKssj());
		map.put("p_cur", "");
		
		hcpzmapper.selectdwbmywlxsj(map);
		List<HCPZ> list = (List<HCPZ>) map.get("p_cur");
		
		if(CollectionUtils.isEmpty(list)) {
			list = null;
		}
		return list;
	}

	/**
	 * 根据部门编码和单位编码得到 核查配置列表,可能有多个
	 * @param hcpz 
	 * @return HCPZ 
	 */
	public HCPZ selectone(HCPZ hcpz){
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("p_bhcdwbm", hcpz.getBhcdwbm());
		map.put("p_bhcbmbm", hcpz.getBhcbmbm());
		map.put("p_cur", "");
		
		hcpzmapper.selectone(map);
		List<HCPZ> list = (List<HCPZ>) map.get("p_cur");
		
		if(CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 通过ywlx得到部门类别
	 */
	@Override
	public List<HCPZ> getbmlbbyywlx(String ywlx) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("p_ywlx", ywlx);
		map.put("p_cur", "");
		
		hcpzmapper.getbmlbbyywlx(map);
		
		return (List<HCPZ>) map.get("p_cur");
	}

	/**
	 * 按照被核查单位编码,业务类型,时间 判断一个交叉核查配置是否存在是否存在
	 * @param hcpz 
	 * @return boolean
	 */
	private boolean isYscz(HCPZ hcpz){
		List<HCPZ> list;
		list = selectdwbmywlxsj(hcpz);
		//没有查询到该单位业务的配置,返回false;
		if(CollectionUtils.isEmpty(list)) {
			return false;
		}
		return true;
	}


	/**
	 * 得到业务类型列表
	 */
	@Override
	public List<String> getywlxlist() {
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("p_cur", "");
		
		hcpzmapper.getywlxlist(map);

		List<String> list = (List<String>) map.get("p_cur");
		
		if(CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list;
	}
	/**
	 * 得到所有核查配置
	 */
	@Override
	public Map<String,Object> getAllhcpz(String page1,String pagesize1) {
		Map<String,Object> map = new HashMap<String,Object>();
		int page = 0,pagesize = 0;
		
		if(!StringUtils.isEmpty(pagesize1)|| StringUtils.isEmpty(page1)){
			page = Integer.valueOf(page1);
			pagesize = Integer.valueOf(pagesize1);
		}
		//分页,计算开始行号和结束行号
		int pages = (page - 1) * pagesize;
		int pagee = pages + pagesize;
		pages++;
		
		map.put("p_pages", "" + pages);
		map.put("p_pagee", "" + pagee);
		map.put("p_total", "");
		map.put("p_cur", "");
		
		hcpzmapper.getAllhcpz(map);
		
		return map;
	}


	
	/**
	 * spstid 得到 该审批的信息  list中下标  0:年度 1:季度  2:业务类型 3:单位编码 
	 */
	@Override
	public List<String> getndjd(String spstid) {
		Map<String,String> map = new HashMap<String,String>();
		List<String> list = new  ArrayList<String>();
		
		map.put("p_spstid", spstid);
		map.put("p_nd", "");
		map.put("p_jd", "");
		map.put("p_ywlx", "");
		map.put("p_dwbm", "");
		
		hcpzmapper.getndjd(map);
		
		String nd = map.get("p_nd");
		String jd = map.get("p_jd");
		String ywlx = map.get("p_ywlx");
		String dwbm = map.get("p_dwbm");
		
		if("".equals(nd) || "".equals(jd)||"".equals(ywlx)) {
			return null;
		} 
		//按顺序添加入list
		list.add(nd); //年度
		list.add(jd); //季度
		list.add(ywlx); //业务类型
		list.add(dwbm); //单位编码
		
		return list;
	}

	/**
	 * 通过单位编码得到单位级别
	 */
	@Override
	public Map<String,String> getdwjb(String dwbm) {
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("p_dwbm", dwbm);
		map.put("p_dwmc","");
		map.put("p_dwjb", "");
		
		hcpzmapper.getdwjb(map);
		
		return map;
	}


	/**
	 * 通过单位编码 和 审批实体id 判断该节点类型
	 */
	@Override
	public String getisjc(String spstid, String dwbm) {
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("p_spstid", spstid);
		map.put("p_dwbm",dwbm);
		map.put("p_jdlx", "");
		
		hcpzmapper.getisjc(map);
		
		String jdlx  = map.get("p_jdlx");
		
		return jdlx;
	}
	
	/**
	 * 通过审批实体id 获取部门列表
	 * @param  spstid 审批实体id
	 * @return List<HCPZ>
	 */
	public List<HCPZ> getbmlistbyspstid(String spstid){
		String[] yf = {"2","5","8","11"};
		List<String> list = this.getndjd(spstid);
		Map<String,Object> map = new HashMap<String,Object>();
		//list中 第一个为年份,第二个为季度,第 3个位ywlx 第4个为dwbm
		String nd = list.get(0);
		int jd = Integer.valueOf(list.get(1));
		String ywlx = list.get(2);
		String dwbm = list.get(2+1);
		
		map.put("p_bhcdwbm", dwbm);
		map.put("p_ywlx", ywlx);
		map.put("p_sj",nd+"-"+yf[jd-1]+"-"+"2");  //构造时间
		map.put("p_cur","");
		
		//hcpzmapper.selectdwbmywlx(map);
		
		hcpzmapper.selectdwbmywlxsj(map);
		
		List<HCPZ> list1 = (List<HCPZ>) map.get("p_cur");
		
		if(CollectionUtils.isEmpty(list)) {
			list1 = null;
		}
		
		return list1;
	}

}
