package com.swx.ibms.business.cases.service;

import com.swx.ibms.business.archives.bean.DAGZGD;
import com.swx.ibms.business.cases.mapper.AjywlxtjMapper;
import com.swx.ibms.business.common.mapper.SyCountMapper;
import com.swx.ibms.business.performance.mapper.GrjxMapper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 案件业务类型统计服务接口实现类
 * @author 李治鑫
 *
 */
@Service("ajywlxtjService")
public class AjywlxtjServiceImpl implements AjywlxtjService {
	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(AjywlxtjService.class);
	
	/**
	 * 数据访问接口
	 **/
	@Resource
	private AjywlxtjMapper ajywlxtjmapper;
	
	@Resource
	private GrjxMapper grjxmapper;
	
	@Resource
	private SyCountMapper syCountMapper;
	
	/**
	 * 根据归档ID查询此人在这个归档时期内办理的案件类型及数量
	 * @param daid 归档ID
	 * @return map
	 */
	@Override
	public Map<String ,Object> getCount(String daid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("gdid", daid);
		map.put("cursor","");//游标
		try {
			syCountMapper.getDagzByGdid(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		
		List<DAGZGD> list = (List<DAGZGD>) map.get("cursor");

  		Map<String, Object> ajmap=new HashMap<String,Object> ();
  		
  		ajmap.put("p_cbdwbm", list.get(0).getSsrdwbm());
  		ajmap.put("p_cbrgh", list.get(0).getSsr());
  		ajmap.put("p_kssj", list.get(0).getKssj());
  		ajmap.put("p_jssj", list.get(0).getJssj());
		ajmap.put("p_cursor", StringUtils.EMPTY);
		ajmap.put("p_errmsg", StringUtils.EMPTY);
		
		try {
			ajywlxtjmapper.getMountOfAj(ajmap);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		String temp = MapUtils.getString(ajmap, "p_errmsg", StringUtils.EMPTY);
		if (!StringUtils.isNotBlank(temp)) {
			return ajmap;
		}else
			return null;
		
	}
	
	
	/**
	 * 根据归档ID从档案归总中查询该人的单位编码和工号及添加年限
	 * @param daid 归档ID
	 * @return map
	 */
	public Map<String, Object> getRyGhByDaid(String daid) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();

		map.put("p_daid", daid);
		map.put("p_dwbm", StringUtils.EMPTY);
		map.put("p_gh", StringUtils.EMPTY);
		map.put("p_tjnf", StringUtils.EMPTY);
		map.put("errmsg", StringUtils.EMPTY);

		try {
			grjxmapper.getRyGhByDaid(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		String temp = MapUtils.getString(map, "p_errmsg", StringUtils.EMPTY);
		if (!StringUtils.isNotBlank(temp)) {
			String dwbm = MapUtils.getString(map, "p_dwbm", StringUtils.EMPTY);
			String gh = MapUtils.getString(map, "p_gh", StringUtils.EMPTY);
			String tjnf =  MapUtils.getString(map, "p_tjnf", StringUtils.EMPTY);

			map1.put("dwbm", dwbm);
			map1.put("gh", gh);
			map1.put("tjnf", tjnf);
		}

		return map1;

	}

}
