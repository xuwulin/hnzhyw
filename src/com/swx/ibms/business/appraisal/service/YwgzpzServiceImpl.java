/**
 * 
 */
package com.swx.ibms.business.appraisal.service;

import com.swx.ibms.business.appraisal.bean.Ywgzzbpz;
import com.swx.ibms.business.appraisal.bean.Ywgzzbpzbt;
import com.swx.ibms.business.appraisal.mapper.YwgzzbMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * @author 封泽超
 * @since 2017年6月5日
 */
@SuppressWarnings("all")
@Service("YwgzpzService")
public class YwgzpzServiceImpl implements YwgzpzService {

	/**
	 * 业务工作指标配置
	 */
	@Autowired
	private YwgzzbMapper ywgzzbMapper;

	@Override
	public Ywgzzbpz selectbyid(String id) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<Ywgzzbpz> list = null;

		map.put("p_id", id);
		map.put("p_cur", "");

		ywgzzbMapper.selectbyid(map);

		list = (List<Ywgzzbpz>) map.get("p_cur");

		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public boolean deletebyid(String id) {

		Map<String, String> map = new HashMap<String, String>();

		map.put("p_id", id);
		map.put("p_msg", "");

		ywgzzbMapper.deletebyid(map);

		if ("1".equals(map.get("p_msg"))) {
			return true;
		}

		return false;
	}

	@Override
	public String insert(Ywgzzbpz y) {

		Map<String, String> map = new HashMap<String, String>();
		// 传入
		map.put("p_dwjb", y.getDwjb());
		map.put("p_khlx", y.getKhlx());
		map.put("p_pznr", y.getPznr());
		map.put("p_zbnr", y.getZbnr());
		map.put("p_pjf", y.getPjf());
		map.put("p_czr", y.getCzr());
		map.put("p_pzbtid", y.getPzbtid());
		map.put("p_ssrq", y.getSsrq());
		map.put("p_status", y.getStatus());
		// 传出
		map.put("p_sysid", StringUtils.EMPTY);
		map.put("p_msg", StringUtils.EMPTY);
		try {
			ywgzzbMapper.insert(map);
		} catch (Exception e) {
			throw e;
		}

		if ("1".equals(map.get("p_msg"))) {
			return map.get("p_sysid");
		}
		return "0";
	}

	@Override
	public boolean updata(Ywgzzbpz y) {
		Map<String, String> map = new HashMap<String, String>();

		map.put("p_id", y.getId());
		map.put("p_dwjb", y.getDwjb());
		map.put("p_khlx", y.getKhlx());
		map.put("p_pznr", y.getPznr());
		map.put("p_zbnr", y.getZbnr());
		map.put("p_pjf", y.getPjf());
		map.put("p_czr", y.getCzr());
		map.put("p_pzbtid", y.getPzbtid());
		map.put("p_ssrq", y.getSsrq());
		map.put("p_status", y.getStatus());
		map.put("p_msg", StringUtils.EMPTY);
		
		try {
			ywgzzbMapper.update(map);
		} catch (Exception e) {
			throw e;
		}

		if ("1".equals(map.get("p_msg"))) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> search(int page, int rows, String dwjb) {
		Map<String, Object> map = new HashMap<String, Object>();

		int pages = (page - 1) * rows;
		int pagee = pages + rows;
		pages++;

		map.put("p_pages", pages);
		map.put("p_pagee", pagee);
		map.put("p_dwjb", dwjb);
		map.put("p_cur", StringUtils.EMPTY);
		
		try {
			ywgzzbMapper.search(map);
		} catch (Exception e) {
			throw e;
		}

		return map;
	}

	@Override
	public Ywgzzbpz selectone(String dwjb, String khlx,String ssrq) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Ywgzzbpz> list = null;
		map.put("p_dwjb", dwjb);
		map.put("p_khlx", khlx);
		map.put("p_ssrq", ssrq);
		map.put("p_cur", StringUtils.EMPTY);
		
		try {
			ywgzzbMapper.selectone(map);
		} catch (Exception e) {
			throw e;
		}

		list = (List<Ywgzzbpz>) map.get("p_cur");

		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	// 表头部分

	@Override
	public String insertbt(Ywgzzbpzbt y) {
		Map<String, String> map = new HashMap<String, String>();

		map.put("p_cjr", y.getCjr());
		map.put("p_btsj", y.getBtsj());
		map.put("p_pzbtsj", y.getPzbtsj());
		map.put("p_id", "");

		ywgzzbMapper.insertbt(map);

		return map.get("p_id");
	}

	@Override
	public Ywgzzbpzbt selectbyidbt(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Ywgzzbpzbt> list = null;

		map.put("p_id", id);
		map.put("p_cur", "");

		ywgzzbMapper.selectbyidbt(map);

		list = (List<Ywgzzbpzbt>) map.get("p_cur");

		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public boolean updatebt(Ywgzzbpzbt y) {
		Map<String, String> map = new HashMap<String, String>();

		map.put("p_id", y.getId());
		map.put("p_btsj", y.getBtsj());
		map.put("p_pzbtsj", y.getPzbtsj());
		map.put("p_msg", "");

		ywgzzbMapper.updatebt(map);

		String msg = map.get("p_msg");

		if ("1".equals(msg)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deletebyidbt(String id) {
		Map<String, String> map = new HashMap<String, String>();

		map.put("p_id", id);
		map.put("p_msg", "");

		ywgzzbMapper.deletebyidbt(map);

		String msg = map.get("p_msg");

		if ("1".equals(msg)) {
			return true;
		}
		return false;
	}

	@Override
	public List<Ywgzzbpz> getYwkhZbpzByParams(String khlx, String ssrq, String dwjb) {
		List<Ywgzzbpz> list = ListUtils.EMPTY_LIST;
		try {
			list = ywgzzbMapper.getYwkhZbpzByParams(khlx, ssrq, dwjb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
