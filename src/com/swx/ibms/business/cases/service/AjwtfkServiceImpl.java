package com.swx.ibms.business.cases.service;

import com.swx.ibms.business.cases.bean.Ajwtfk;
import com.swx.ibms.business.cases.mapper.AjwtfkMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 案件问题问题反馈接口实现层
 * @author 李佳
 * @date: 2017年3月20日
 */
@Service("AjwtfkService")
public class AjwtfkServiceImpl implements AjwtfkService{

	/**
	 * 案件问题问题反馈Mapper接口
	 */
	@Resource
	private AjwtfkMapper ajwtfkMapper;
	
	@Override
	public String getGhByBmsah(String wbid) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("wbid", wbid);
		map.put("gh", "");
		ajwtfkMapper.getGhByBmsah(map);
		String gh=(String) map.get("gh");
		return gh;
	}

	@Override
	public String insertAjwtfk(Ajwtfk ajwtfk) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("dwbm", ajwtfk.getDwbm());
		map.put("gh", ajwtfk.getGh());
		map.put("tzmc", ajwtfk.getTzmc());
		map.put("tzlx", ajwtfk.getTzlx());
		map.put("wbid", ajwtfk.getWbid());
	    map.put("isRead", ajwtfk.getIsRead());
		map.put("Y", "");                          //是否添加成功
		ajwtfkMapper.insert(map);
		String result=(String) map.get("Y");
		return result;
	}

	@Override
	public String deleteAjwtfk(String id) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		ajwtfkMapper.delete(map);
		String result=(String) map.get("Y");
		return result;
	}

	@Override
	public String updateAjwtfk(Ajwtfk ajwtfk) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", ajwtfk.getId());
		map.put("dwbm", ajwtfk.getDwbm());
		map.put("gh", ajwtfk.getGh());
		map.put("tzmc", ajwtfk.getTzmc());
		map.put("tzlx", ajwtfk.getTzlx());
		map.put("wbid", ajwtfk.getWbid());
	    map.put("isRead", ajwtfk.getIsRead());
	    ajwtfkMapper.update(map);
	    String result=(String) map.get("Y");
		return result;
	}

	@Override
	public List getAjwtfk(String id) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("cursor", "");
		ajwtfkMapper.getAjwtfk(map);
		List<Ajwtfk> list=(List<Ajwtfk>) map.get("cursor");
		return list;
	}

	@Override
	public List<Ajwtfk> getBmsahById(String id) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("cursor", "");
		ajwtfkMapper.getBmsahById(map);
		List<Ajwtfk> list=(List<Ajwtfk>) map.get("cursor");
		return list;
	}

}
