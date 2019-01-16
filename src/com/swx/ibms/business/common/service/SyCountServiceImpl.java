package com.swx.ibms.business.common.service;

import com.swx.ibms.business.archives.bean.CountSySfda;
import com.swx.ibms.business.archives.bean.DAGZGD;
import com.swx.ibms.business.archives.bean.Gsjl;
import com.swx.ibms.business.common.mapper.SyCountMapper;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 司法档案内容统计接口实现层
 * @author 李佳
 */
@Service("SyCountbService")
public class SyCountServiceImpl implements SyCountService{

	/**
	 * 司法档案内容统计Mapper接口
	 */
	@Resource
	private SyCountMapper syCountMapper;
	
	@Override
	public String pjajBlsc(String gdid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("count", ""); //接收参数
		map.put("gdid",gdid);
		syCountMapper.pjajBlsc(map);
		String count = (String)map.get("count");
		return count;
	}

	@Override
	public int qypjajBlsc(String gdid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("count", "");  //接收参数
		map.put("gdid",gdid); 
		syCountMapper.qypjajBlsc(map);
		int result1=MapUtils.getInteger(map, "count", 0);
		return result1;
	}
	
	@Override
	public double countGrjx(String gdid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("count",""); //接收参数
		map.put("gdid",gdid);
		syCountMapper.countGrjx(map);
		double result1=MapUtils.getDoubleValue(map, "count", 0);
		return result1;
	}

	@Override
	public List<CountSySfda> countSfda(String gdid, String dalx) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cursor", "");  //游标
		map.put("gdid",gdid);
		map.put("dalx", dalx);
		syCountMapper.countSfda(map);
		List<CountSySfda> list = (List<CountSySfda>) map.get("cursor");
		return list;
	}

	@Override
	public String sfGs(String gh, String ssrdwbm) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sfgs","");   //接收参数
		map.put("gh",gh);
		map.put("ssrdwbm", ssrdwbm);
		syCountMapper.sfGs(map);
		String result=(String) map.get("sfgs");
		return result; 
	}

	@Override
	public int sfySfda(String gh, String ssrdwbm) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("count", ""); //接收参数 值为零代表没有司法档案
		map.put("gh",gh);
		map.put("ssrdwbm", ssrdwbm);
		syCountMapper.sfySfda(map);
		int result1=MapUtils.getInteger(map, "count", 0);
		return result1;
	}
	
	@Override
	public double grjxDf(String gdid) {
		double grjxdf= syCountMapper.grjxDf(gdid);
		return grjxdf;
	}

	@Override
	public String getGdid(String dwbm, String gh ,String sffc) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("dwbm", dwbm);
		map.put("gh", gh);
		map.put("sffc", sffc);
		map.put("gdid", "");
		syCountMapper.getGdid(map);
		String gdid=(String) map.get("gdid");
		return gdid;
	}

	@Override
	public String getGdidBySj(String gh, String dwbm, String kssj, String jssj) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("dwbm", dwbm);
		map.put("gh", gh);
		map.put("kssj",kssj);
		map.put("jssj", jssj);
		map.put("gdid", "");
		syCountMapper.getGdidBySj(map);
		String gdid=(String) map.get("gdid");
		return gdid;
	}

	@Override
	public List<DAGZGD> getDagzByGdid(String gdid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("gdid", gdid);
		map.put("cursor","");//游标
		syCountMapper.getDagzByGdid(map);
		List<DAGZGD> list = (List<DAGZGD>) map.get("cursor");
		return list;
	}

	@Override
	public List<Gsjl> getGsJzSj(String gdid, String czlx) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("gdid", gdid);
		map.put("czlx", czlx);
		map.put("cursor","");//游标
		syCountMapper.getGsJzSj(map);
		List<Gsjl> list = (List<Gsjl>) map.get("cursor");
		return list;
	}

	@Override
	public List<Map<String, Object>> getYwkhInfo(String dwbm,List<String> ywlxList) {
		List<String> khlxList = new ArrayList<>();
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			khlxList = syCountMapper.getKhlx(ywlxList);
			if(khlxList.contains("13")){
				khlxList.remove("13");
			}
			list = syCountMapper.getYwkhData(dwbm,khlxList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String getSfgs(String daId) {
		String sfgs = "";
		try{
			sfgs = syCountMapper.getSfgs(daId);
		}catch (Exception e){
			e.printStackTrace();
		}
		return sfgs;
	}

}
