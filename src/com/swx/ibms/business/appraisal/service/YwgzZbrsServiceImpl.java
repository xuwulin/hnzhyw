package com.swx.ibms.business.appraisal.service;

import com.swx.ibms.business.appraisal.bean.YwgzZbrs;
import com.swx.ibms.business.appraisal.mapper.YwgzZbrsMapper;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 
 * YwgzZbrsServiceImpl.java  在编人数实现类 
 * @author east 
 * @date<p>2017年6月14日</p>
 * @version 1.0
 * @description<p></p>
 */
@SuppressWarnings("all")
@Service("ywgzZbrsService")
public class YwgzZbrsServiceImpl implements YwgzZbrsService {
	
	
	/**
	 * //在编人数mapper
	 */
	@Resource
	private YwgzZbrsMapper ywgzZbrsMapper;


	@Override
	public PageCommon<YwgzZbrs> zbrsSelectPageList(Map<String, Object> map) {
		
		PageCommon<YwgzZbrs> pageCommon = new PageCommon();
		
		String dwbm = map.get("dwbm").toString();
		int nowPage = Integer.parseInt(map.get("nowPage").toString());
		int pageSize = Integer.parseInt(map.get("pageSize").toString());
		
		int page = (nowPage-1)*pageSize;
		pageCommon.setPageSize(page+pageSize);//每页显示数
		page++;
		pageCommon.setNowPage(page);//当前页
		
		map.put("p_dwbm", dwbm);
		map.put("p_start", pageCommon.getNowPage());
		map.put("p_end", pageCommon.getPageSize());
		map.put("p_total", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);
			
		ywgzZbrsMapper.zbrsSelectPageList(map);	
		
		//得到page对象、总记录数、
		List<YwgzZbrs> list = (List<YwgzZbrs>)map.get("p_cursor");
		pageCommon.setList(list);
		pageCommon.setTotalRecords(Integer.parseInt(map.get("p_total").toString()));
		
		return pageCommon;
	}

	@Override
	public YwgzZbrs zbrsSelectById(Map<String, Object> map) {
		
		map.put("p_cursor", StringUtils.EMPTY);
		
		ywgzZbrsMapper.zbrsSelectById(map);
		
		List<YwgzZbrs> ywgzZbrsList = (List<YwgzZbrs>)map.get("p_cursor");
		
		if (!CollectionUtils.isEmpty(ywgzZbrsList)) {
			YwgzZbrs ywgzZbrs = ywgzZbrsList.get(0);
			return ywgzZbrs;
		}
		
		return null;
	}

	@Override
	public String zbrsInsertData(Map<String, Object> map) {
		
		map.put("p_errmsg", StringUtils.EMPTY);
		
		ywgzZbrsMapper.zbrsInsertData(map);
		
		if ("1".equals(map.get("p_errmsg"))) {
			return "操作成功！";
		}else if("该记录已存在".equals(map.get("p_errmsg"))){
			return (String) map.get("p_errmsg");
		}else{
			return "操作失败！";
		}
		
	}

	@Override
	public String zbrsUpdateData(Map<String, Object> map) {
		
		map.put("p_errmsg", StringUtils.EMPTY);
		
		ywgzZbrsMapper.zbrsUpdateData(map);
		
		if ("1".equals(map.get("p_errmsg"))) {
			return "操作成功！";
		}else{
			return "操作失败！";
		}
	}

	@Override
	public String zbrsDeleteData(Map<String, Object> map) {
		
		map.put("p_errmsg", StringUtils.EMPTY);
		
		ywgzZbrsMapper.zbrsDeleteData(map);
		
		if ("1".equals(map.get("p_errmsg"))) {
			return "操作成功！";
		}else{
			return "操作失败！";
		}
	}

}
