package com.swx.ibms.business.cases.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swx.ibms.business.cases.bean.AgbZlpc;
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
*案管办质量评查
*@version 1.0
*@since 2018年4月26日
*/
@Service
public class AgbZlpcServiceImpl implements AgbZlpcService {

	@Autowired
	private AgbZlpcMapper agbZlpcMapper;
	
	@Override
	public Map<String, Object> getAllZlpcByBlxs(String did,String blxs, String dwbm, String gh, int page, int pageSize) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Page<AgbZlpc> pager = PageHelper.startPage(page, pageSize);
		List<AgbZlpc> list = agbZlpcMapper.getAllZlpcByBlxs(did,blxs, dwbm, gh);
		map.put("total", pager.getTotal());
		map.put("rows", list);
		return map;
	}

	@Override
	public List<Map<String, Object>> getCountByBlxs(String did, String dwbm, String gh) throws Exception {
		return agbZlpcMapper.getCountByBlxs(did, dwbm, gh);
	}

	@Override
	public Map<String, Object> getCaseAvg(String dwbm, String bmbm,String kssj,String jssj) throws Exception {
		return agbZlpcMapper.getCaseAvg(dwbm, bmbm,kssj,jssj);
	}

	@Override
	public Map<String, Object> getCaseMaxAndMin(String dwbm, String bmbm,String kssj,String jssj) throws Exception {
		return agbZlpcMapper.getCaseMaxAndMin(dwbm, bmbm,kssj,jssj);
	}

	@Override
	public Map<String, Object> getRank(String dwbm, String bmbm, String did,String kssj,String jssj) throws Exception {
		return agbZlpcMapper.getRank(dwbm, bmbm, did,kssj,jssj);
	}

	@Override
	public AgbZlpc getZlpcById(String id, String dwbm, String gh) throws Exception {
		return agbZlpcMapper.getZlpcById(id, dwbm, gh);
	}

	@Override
	public String addAgbZlpc(String did, String ajmc, String ywlb, String cbxzbm, String pczl, String zlpc_blxs,
						     String pckssj, String pcjssj, Integer pcwtgs, String pcjgxs, 
						     String zgqk, String zlpc_cbxz) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("id", Identities.get32LenUUID());
		map.put("ajmc", ajmc);
		map.put("ywlb", ywlb);
		map.put("pczl", pczl);
		map.put("blxs", zlpc_blxs);
		map.put("pckssj", pckssj);
		map.put("pcjssj", pcjssj);
		map.put("pcwtgs",pcwtgs);
		map.put("pcjgxs", pcjgxs);
		map.put("zgqk", zgqk);
		map.put("sfsc", 'N');
		map.put("did",did);
		map.put("zhgxbm", Constant.CURRENT_USER.get().getBmbm());
		map.put("zhgxr", Constant.CURRENT_USER.get().getGh());
		map.put("zhgxsj", DateUtil.getSysCurrentDateTime());
		map.put("spr", StringUtils.EMPTY);
		map.put("cbxzbm", cbxzbm);
		map.put("cbxz", zlpc_cbxz);
		map.put("status","2");//案件状态 1：已审批 2：未审批 3：审批未通过
		
		try{
			agbZlpcMapper.addAgbZlpc(map);
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}

	@Override
	public String deleteAgbZlpc(String id) throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("id", id);
		try {
			agbZlpcMapper.deleteAgbZlpc(map);
			agbZlpcMapper.deleteAgbZlpcFj(map);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}	}

	@Override
	public String updateAgbZlpcById(String id, String ajmc, String ywlb, String cbxzbm, String pczl, 
									String zlpc_blxs,String pckssj, String pcjssj, Integer pcwtgs, 
									String pcjgxs, String zgqk, String zlpc_cbxz) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("id", id);
		map.put("ajmc", ajmc);
		map.put("ywlb", ywlb);
		map.put("pczl", pczl);
		map.put("blxs", zlpc_blxs);
		map.put("pckssj", pckssj);
		map.put("pcjssj", pcjssj);
		map.put("pcwtgs",pcwtgs);
		map.put("pcjgxs", pcjgxs);
		map.put("zgqk", zgqk);
		map.put("zhgxbm", Constant.CURRENT_USER.get().getBmbm());
		map.put("zhgxr", Constant.CURRENT_USER.get().getGh());
		map.put("zhgxsj", DateUtil.getSysCurrentDateTime());
		map.put("spr", StringUtils.EMPTY);
		map.put("cbxzbm", cbxzbm);
		map.put("cbxz", zlpc_cbxz);
		
		try{
			agbZlpcMapper.updateAgbZlpcById(map);
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}

	@Override
	public Map<String, Object> getZlpc(String id) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		AgbZlpc zlpc = agbZlpcMapper.getZlpc(id);
		resMap.put("data", zlpc);
		return resMap;
	}


}
