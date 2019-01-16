package com.swx.ibms.business.rbac.service;

import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.Bmlbywfp;
import com.swx.ibms.business.rbac.mapper.BmlbywfpMapper;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 
 * BmlbywfpServiceImpl.java 
 * @author 何向东
 * @date<p>2017年12月19日</p>
 * @version 1.0
 * @description<p></p>
 */
@SuppressWarnings("all")
@Service("bmlbywfpService")
public class BmlbywfpServiceImpl implements BmlbywfpService {

	/**
	 * 业务类型分配Mapper
	 */
	@Resource
	private BmlbywfpMapper bmlbywfpMapper;
	
	@Override
	public PageCommon<Bmlbywfp> ywfpSelectPageList(String ywbm, String bmlbbm,
												   String dwbm, String type, Integer page, Integer pageSize) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_10);
		PageCommon<Bmlbywfp> ywfPage = new PageCommon<Bmlbywfp>();

		int nowPage = (0==page? Constant.NUM_1:page);
        int pageStart = (nowPage - Constant.NUM_1) * pageSize;
        int pageEnd = pageStart+pageSize;
        pageStart++;	        
		
		map.put("p_ywbm", ywbm);
		map.put("p_bmlbbm", bmlbbm);
		map.put("p_dwbm", dwbm);
		map.put("p_type", type);
		map.put("p_start", pageStart);
		map.put("p_end", pageEnd);
		map.put("p_total", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);
		bmlbywfpMapper.ywfpSelectPageList(map);
		
		ywfPage.setList((List<Bmlbywfp>)map.get("p_cursor"));
		ywfPage.setTotalRecords(Integer.parseInt(map.get("p_total").toString()));
		
		return ywfPage;
	}

	@Override
	public List<Bmlbywfp> getYwfpListById(Integer id) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_3);
		map.put("p_id", id);
		map.put("p_cursor", StringUtils.EMPTY);
		
		bmlbywfpMapper.getYwfpListById(map);
		
		List<Bmlbywfp> ywfpList = (List<Bmlbywfp>)map.get("p_cursor");
		return ywfpList;
	}

	@Override
	public String modifyYwfpById(Integer id, String bmlbbm, 
			String ywbm, String dwbm) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_6);
		map.put("p_id", id);
		map.put("p_bmlbbm", bmlbbm);
		map.put("p_ywbm", ywbm);
		map.put("p_dwbm", dwbm);
		map.put("p_errmsg", StringUtils.EMPTY);
		
		bmlbywfpMapper.modifyYwfpById(map);
		
		return (String)map.get("p_errmsg");
	}

	@Override
	public String delYwfpById(Integer id) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_3);
		map.put("p_id", id);
		map.put("p_errmsg", StringUtils.EMPTY);
		
		bmlbywfpMapper.delYwfpById(map);
		
		return (String)map.get("p_errmsg");
	}

	@Override
	public String addYwfp(String bmlbbm, String ywbm, String dwbm) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_6);
		map.put("p_bmlbbm", bmlbbm);
		map.put("p_ywbm", ywbm);
		map.put("p_dwbm", dwbm);
		map.put("p_id", StringUtils.EMPTY);
		map.put("p_errmsg", StringUtils.EMPTY);
		
		bmlbywfpMapper.addYwfp(map);
		
		return (String)map.get("p_errmsg");
	}

}
