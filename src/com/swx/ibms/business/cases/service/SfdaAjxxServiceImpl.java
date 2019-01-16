package com.swx.ibms.business.cases.service;

import com.swx.ibms.business.cases.bean.SfdaAjxx;
import com.swx.ibms.business.cases.mapper.SfdaAjxxMapper;
import com.swx.ibms.common.utils.Identities;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;



/**
 * 
 * SfdaAjxxServiceImpl.java  司法档案下的案件信息Service接口实现类
 * @author 何向东
 * @date<p>2017年8月3日</p>
 * @version 1.0
 * @description<p></p>
 */
@Service("sfdaAjxxService")
@SuppressWarnings("all")
public class SfdaAjxxServiceImpl implements SfdaAjxxService {

	/**
	 * 司法档案下的案件信息Mapper
	 */
	@Autowired
	private SfdaAjxxMapper sfdaAjxxMapper;
	
	@Override
	public Map<String,Object> getSfdaZbAjxxPageList(Map<String, Object> map) throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		PageCommon<Object> pageCommon = new PageCommon<Object>();
		
		int nowPage = Integer.parseInt(map.get("nowPage").toString());
		int pageSize = Integer.parseInt(map.get("pageSize").toString());
		int page = (nowPage - 1) * pageSize;
		pageCommon.setPageSize(page + pageSize);// 每页显示数
		page++;
		pageCommon.setNowPage(page);// 当前页
		
		map.put("p_start", pageCommon.getNowPage());
		map.put("p_end", pageCommon.getPageSize());
		map.put("p_total", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);
		map.put("p_ywlx_cursor", StringUtils.EMPTY);//每个业务类型所占的案件个数结果集
		map.put("p_avg_basc", StringUtils.EMPTY);//单个案件的平均办案时长
		map.put("p_all_avg_basc", StringUtils.EMPTY);//全院的所有案件办案时长
		sfdaAjxxMapper.getSfdaZbAjxxPageList(map);
			
		// 得到page对象、总记录数、
		List<Object> list = (List<Object>) map.get("p_cursor");
		List<Object> ywlxslList = (List<Object>) map.get("p_ywlx_cursor");//统计出来的业务类型数量
		pageCommon.setList(list);
		pageCommon.setTotalRecords(Integer.parseInt(map.get("p_total").toString()));
		
		resMap.put("total", pageCommon.getTotalRecords());
		resMap.put("rows", pageCommon.getList());
		resMap.put("ywlxsl", ywlxslList.get(0));
		resMap.put("avgBasc", Integer.parseInt(map.get("p_avg_basc").toString()));
		resMap.put("allAvgBasc", Integer.parseInt(map.get("p_all_avg_basc").toString()));
		return resMap;
	}
	
	@Override
	public PageCommon<Object> getSfdaCbAjxxPageList(Map<String, Object> map) throws Exception {
		PageCommon<Object> pageCommon = new PageCommon<Object>();
		
		int nowPage = Integer.parseInt(map.get("nowPage").toString());
		int pageSize = Integer.parseInt(map.get("pageSize").toString());
		int page = (nowPage - 1) * pageSize;
		pageCommon.setPageSize(page + pageSize);// 每页显示数
		page++;
		pageCommon.setNowPage(page);// 当前页
		
		map.put("p_start", pageCommon.getNowPage());
		map.put("p_end", pageCommon.getPageSize());
		map.put("p_total", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);
		sfdaAjxxMapper.getSfdaCbAjxxPageList(map);
			
		// 得到page对象、总记录数、
		List<Object> list = (List<Object>) map.get("p_cursor");
		pageCommon.setList(list);
		pageCommon.setTotalRecords(Integer.parseInt(map.get("p_total").toString()));
		return pageCommon;
	}

	@Override
	public SfdaAjxx getSfdaAjxxById(Map<String, Object> map) throws Exception {
		SfdaAjxx sfdaAjxx = new SfdaAjxx();
		
		if (!Objects.isNull(map)&&StringUtils.isNotEmpty(map.get("p_ajId").toString())) {
			map.put("p_cursor", StringUtils.EMPTY);
			sfdaAjxxMapper.getSfdaAjxxById(map);
			sfdaAjxx = (SfdaAjxx)map.get("p_cursor");
		}
		
		return sfdaAjxx;
	}

	@Override
	public Map<String, Object> addSfdaAjxx(Map<String, Object> map) throws Exception {
		if (!Objects.isNull(map)&&StringUtils.isNotEmpty(map.get("p_da_id").toString())) {
			map.put("p_ajId", Identities.get32LenUUID());
			map.put("p_createTime", new Date());
			map.put("p_errMsg", StringUtils.EMPTY);
			sfdaAjxxMapper.addSfdaAjxx(map);
			return map;
		}
		return null;
	}

	@Override
	public String updateSfdaAjxx(Map<String, Object> map) throws Exception {
		if (!Objects.isNull(map)&&StringUtils.isNotEmpty(map.get("p_ajId").toString())) {
			map.put("p_modifyTime", new Date());
			map.put("p_errMsg", StringUtils.EMPTY);
			sfdaAjxxMapper.updateSfdaAjxx(map);
			return (String)map.get("p_errMsg");
		}
		return null;
	}

	@Override
	public String deleteSfdaAjxx(Map<String, Object> map) throws Exception {
		if (!Objects.isNull(map)&&StringUtils.isNotEmpty(map.get("p_ajId").toString())) {
			map.put("p_errMsg", StringUtils.EMPTY);
			sfdaAjxxMapper.deleteSfdaAjxx(map);
			return (String)map.get("p_errMsg");
		}
		return null;
	}

	@Override
	public String updateStatusByDaId(Map<String, Object> map) throws Exception {
		if (!Objects.isNull(map)&&StringUtils.isNotEmpty(map.get("p_da_id").toString())
				&&StringUtils.isNotEmpty(map.get("p_status").toString())) {
			map.put("p_errMsg", StringUtils.EMPTY);
			sfdaAjxxMapper.updateStatusByDaId(map);
			return (String)map.get("p_errMsg");
		}
		return null;
	}

	@Override
	public Integer modifyStatusByAjId(String ajStatus, String ajId) {
		Integer res = 0;
		try {
			res = sfdaAjxxMapper.modifyStatusByAjId(ajStatus, ajId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
}
