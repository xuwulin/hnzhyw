package com.swx.ibms.business.cases.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swx.ibms.business.cases.bean.AgbLcjk;
import com.swx.ibms.business.cases.mapper.AgbLcjkMapper;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.common.utils.DateUtil;
import com.swx.ibms.common.utils.Identities;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
*案管办流程监控
*@version 1.0
*@since 2018年4月26日
*/
@Service
public class AgbLcjkServiceImpl implements AgbLcjkService {
	//案件类别编码案管办流程监控编码
	private static final String AJLBBM = "1402";
	@Autowired
	private AgbLcjkMapper agbLcjkMapper;
	
	@Override
	public Map<String, Object> getAllLcjkByBlxs(String blxs,String did, int page, int pageSize) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Page<AgbLcjk> pager = PageHelper.startPage(page, pageSize);
		List<AgbLcjk> list = agbLcjkMapper.getAllLcjkByBlxs(did,blxs);
		map.put("total", pager.getTotal());
		map.put("list", list);
		return map;
	}

	@Override
	public List<Map<String, Object>> getCountByBlxs(String did,String dwbm,String gh) throws Exception {
		List<Map<String, Object>> list = agbLcjkMapper.getCountByBlxs(did, dwbm, gh);
		return list;
	}

	@Override
	public Map<String, Object> getLcjkAndTyywAjjbxx(String did, String blxs, String dwbm, String gh, int page,
			int pageSize) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Page<AgbLcjk> pager = PageHelper.startPage(page, pageSize);
		List<AgbLcjk> list = agbLcjkMapper.getLcjkAndTyywAjjbxx(did, blxs, AJLBBM, dwbm, gh);
		map.put("total", pager.getTotal());
		map.put("rows", list);
		return map;
	}

	@Override
	public Map<String, Object> getCaseAvg(String dwbm, String bmbm,String kssj,String jssj) throws Exception {
		return agbLcjkMapper.getCaseAvg(AJLBBM, dwbm, bmbm,kssj,jssj);
	}

	@Override
	public Map<String, Object> getCaseMaxAndMin(String dwbm, String bmbm,String kssj,String jssj) throws Exception {
		return agbLcjkMapper.getCaseMaxAndMin(AJLBBM, dwbm, bmbm,kssj,jssj);
	}

	@Override
	public Map<String, Object> getRank(String dwbm, String bmbm, String did,String kssj,String jssj) throws Exception {
		return agbLcjkMapper.getRank(AJLBBM, dwbm, bmbm, did,kssj,jssj);
	}

	@Override
	public AgbLcjk getLcjkById(String id, String dwbm, String gh) throws Exception {
		return agbLcjkMapper.getLcjkById(id, dwbm, gh);
	}

	@Override
	public String addAgbLcjk(String did, String ajmc, String ywlb, String cbxzbm, String jkrq, 
							 String jkr, String jkfs,String lcjk_blxs, Integer wtgs, 
							 String lcjk_cbxz, String jknr) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("id", Identities.get32LenUUID());
		map.put("ajmc", ajmc);
		map.put("ywlb", ywlb);
		map.put("jkr", jkr);
		map.put("wtgs", wtgs);
		map.put("jkfs", jkfs);
		map.put("blxs", lcjk_blxs);
		map.put("jkrq",jkrq);
		map.put("sfsc", 'N');
		map.put("did", did);
		map.put("zhgxbm", Constant.CURRENT_USER.get().getBmbm());
		map.put("zhgxr", Constant.CURRENT_USER.get().getGh());
		map.put("zhgxsj", DateUtil.getSysCurrentDateTime());
		map.put("spr", StringUtils.EMPTY);
		map.put("cbxzbm", cbxzbm);
		map.put("cbxz", lcjk_cbxz);
		map.put("jknr", jknr);
		map.put("status","2");//案件状态 1：已审批 2：未审批 3：审批未通过

		try{
			agbLcjkMapper.addAgbLcjk(map);
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}

	@Override
	public String deleteAgbLcjk(String id) throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("id", id);
		try {
			agbLcjkMapper.deleteAgbLcjk(map);
			agbLcjkMapper.deleteAgbLcjkFj(map);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@Override
	public String updateAgbLcjkById(String id, String ajmc, String ywlb, String cbxzbm, String jkrq,
									String jkr,String jkfs, String lcjk_blxs, Integer wtgs, 
									String lcjk_cbxz, String jknr) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("id", id);
		map.put("ajmc", ajmc);
		map.put("ywlb", ywlb);
		map.put("jkr", jkr);
		map.put("wtgs", wtgs);
		map.put("jkfs", jkfs);
		map.put("blxs", lcjk_blxs);
		map.put("jkrq",jkrq);
		map.put("zhgxbm", Constant.CURRENT_USER.get().getBmbm());
		map.put("zhgxr", Constant.CURRENT_USER.get().getGh());
		map.put("zhgxsj", DateUtil.getSysCurrentDateTime());
		map.put("spr", StringUtils.EMPTY);
		map.put("cbxzbm", cbxzbm);
		map.put("cbxz", lcjk_cbxz);
		map.put("jknr", jknr);
		try{
			agbLcjkMapper.updateAgbLcjkById(map);
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}

	@Override
	public Map<String, Object> getLcjk(String id) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		AgbLcjk lcjk = agbLcjkMapper.getLcjk(id);
		resMap.put("data", lcjk);
		return resMap;
	}


}
