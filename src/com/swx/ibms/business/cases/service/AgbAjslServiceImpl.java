package com.swx.ibms.business.cases.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swx.ibms.business.cases.bean.AgbAjsl;
import com.swx.ibms.business.cases.mapper.AgbAjslMapper;
import com.swx.ibms.business.cases.mapper.AgbLcjkMapper;
import com.swx.ibms.business.cases.mapper.AgbZlpcMapper;
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
*案件受理
*@version 1.0
*@since 2018年4月26日
*/
@SuppressWarnings("all")
@Service
public class AgbAjslServiceImpl implements AgbAjslService {
	
	//案件类别编码 案管办案件受理编码
	private static final String AJLBBM = "1401";
	
	@Autowired
	private AgbAjslMapper agbAjslMapper;
	
	@Autowired
	private AgbLcjkMapper agbLcjkMapper;
	
	@Autowired
	private AgbZlpcMapper agbZlpcMapper;
	
	@Override
	public Map<String, Object> getAllAjslByBlxs(String blxs,String did, int page, int pageSize) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Page<AgbAjsl> pager = PageHelper.startPage(page, pageSize);
		List<AgbAjsl> list = agbAjslMapper.getAllAjslByBlxs(did,blxs);
		map.put("total", pager.getTotal());
		map.put("list", list);
		return map;
	}

	@Override
	public List<Map<String, Object>> getCountByBlxs(String did,String dwbm,String gh) throws Exception {
		List<Map<String, Object>> list = agbAjslMapper.getCountByBlxs(did, dwbm, gh);

		return list;
	}

	@Override
	public Map<String, Object> getAjslAndTyywAjjbxx(String did, String blxs, String dwbm, String gh,
			int page, int pageSize) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Page<AgbAjsl> pager = PageHelper.startPage(page, pageSize);
		List<AgbAjsl> list = agbAjslMapper.getAjslAndTyywAjjbxx(did, blxs, AJLBBM, dwbm, gh);
		map.put("total", pager.getTotal());
		map.put("rows", list);
		return map;
	}


	@Override
	public Map<String, Object> getCaseAvg(String dwbm, String bmbm,String kssj,String jssj) throws Exception {
		return agbAjslMapper.getCaseAvg(AJLBBM, dwbm, bmbm,kssj,jssj);
	}

	@Override
	public Map<String, Object> getCaseMaxAndMin(String dwbm, String bmbm,String kssj,String jssj) throws Exception {
		return agbAjslMapper.getCaseMaxAndMin(AJLBBM, dwbm, bmbm,kssj,jssj);
	}

	@Override
	public Map<String, Object> getRank(String dwbm, String bmbm, String did,String kssj,String jssj) throws Exception {
		return agbAjslMapper.getRank(AJLBBM, dwbm, bmbm, did,kssj,jssj);
	}

	@Override
	public Map<String, Object> getCorrectByDid(String did,String dwbm, String gh, String zhgxr, int page, int pageSize) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Page<AgbAjsl> pager = PageHelper.startPage(page, pageSize);
		List<Map<String, Object>> list = agbAjslMapper.getCorrectByDid(did,dwbm, gh, zhgxr);
		map.put("total", pager.getTotal());
		map.put("list", list);
		return map;
	}

	@Override
	public AgbAjsl getAjslById(String id, String dwbm, String gh) throws Exception {
		return agbAjslMapper.getAjslById(id, dwbm, gh);
	}

	@Override
	public Map<String, Object> selectALLNewAgbajByDid(String did, int page, int pageSize) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		Page<AgbAjsl> ajslPager = PageHelper.startPage(page, pageSize);
		List<AgbAjsl> ajsl = agbAjslMapper.selectALLNewAgbajByDid(did);
		
		resMap.put("total", ajslPager.getTotal());
		resMap.put("rows", ajsl);
		return resMap; 
	}

	/**
	 * 新增案管办案件受理
	 */
	@Override
	public String addAgbAjsl(String did,String ajmc,String ywlb,String cbxzbm,
						     String slsj,String flsj,String lrry,String shry,
						     Integer jzcs,Integer gp,String sacw,String ajsl_blxs,String cbxz) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("id", Identities.get32LenUUID());
		map.put("ajmc", ajmc);
		map.put("ywlb", ywlb);
		map.put("slsj", slsj);
		map.put("flsj", flsj);
		map.put("lrry", lrry);
		map.put("shry", shry);
		map.put("jzcs",jzcs);
		map.put("gp", gp);
		map.put("sacw", sacw);
		map.put("blxs", ajsl_blxs);
		map.put("sfsc", 'N');
		map.put("did",did);
		map.put("zhgxbm", Constant.CURRENT_USER.get().getBmbm());
		map.put("zhgxr", Constant.CURRENT_USER.get().getGh());
		map.put("zhgxsj", DateUtil.getSysCurrentDateTime());
		map.put("spr", StringUtils.EMPTY);
		map.put("cbxzbm", cbxzbm);
		map.put("cbxz", cbxz);
		map.put("status","2");//案件状态 1：已审批 2：未审批 3：审批未通过
		
		try{
			agbAjslMapper.addAgbAjsl(map);
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}

	@Override
	public String deleteAgbAjsl(String id) throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("id", id);
		try {
			agbAjslMapper.deleteAgbAjsl(map);
			agbAjslMapper.deleteAgbAjslFj(map);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@Override
	public String updateAgbAjslById(String id, String ajmc, String ywlb, String cbxzbm,String slsj, 
									String flsj,String lrry, String shry, Integer jzcs, Integer gp, 
									String sacw, String ajsl_blxs, String cbxz) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("id", id);
		map.put("ajmc", ajmc);
		map.put("ywlb", ywlb);
		map.put("slsj", slsj);
		map.put("flsj", flsj);
		map.put("lrry", lrry);
		map.put("shry", shry);
		map.put("jzcs",jzcs);
		map.put("gp", gp);
		map.put("sacw", sacw);
		map.put("blxs", ajsl_blxs);
		map.put("zhgxbm", Constant.CURRENT_USER.get().getBmbm());
		map.put("zhgxr", Constant.CURRENT_USER.get().getGh());
		map.put("zhgxsj", DateUtil.getSysCurrentDateTime());
		map.put("spr", StringUtils.EMPTY);
		map.put("cbxzbm", cbxzbm);
		map.put("cbxz", cbxz);
		
		try{
			agbAjslMapper.updateAgbAjslById(map);
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}

	@Override
	public Map<String, Object> getAjsl(String id) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		AgbAjsl ajsl = agbAjslMapper.getAjsl(id);
		resMap.put("data", ajsl);
		return resMap;
	}

	

}
