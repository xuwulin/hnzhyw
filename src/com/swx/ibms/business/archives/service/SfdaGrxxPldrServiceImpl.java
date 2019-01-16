package com.swx.ibms.business.archives.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swx.ibms.business.archives.mapper.SfdaGrxxPldrMapper;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.common.utils.Identities;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("all")
@Service
public class SfdaGrxxPldrServiceImpl implements SfdaGrxxPldrService {

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;

	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(PersonServiceImpl.class);

	/**
	 * 个人基本信息的mapper接口
	 */
	@Resource
	private SfdaGrxxPldrMapper sfdaGrxxPldrMapper;

	@Override
	public Map<String, Object> selectALLGrjbxx(String dwbm,int page, int pageSize) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		Page grxxPager = PageHelper.startPage(page, pageSize);
		List grxx = sfdaGrxxPldrMapper.selectALLGrjbxx(dwbm);

		resMap.put("total", grxxPager.getTotal());
		resMap.put("rows", grxx);
		return resMap;
	}

	@Override
	public Map<String, Object> selectALLGrjl(String dwbm,int page, int pageSize) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		Page grjlPager = PageHelper.startPage(page, pageSize);
		List grjl = sfdaGrxxPldrMapper.selectALLGrjl(dwbm);

		resMap.put("total", grjlPager.getTotal());
		resMap.put("rows", grjl);
		return resMap;
	}

	@Override
	public String insertAllGrjbxx(Map<String,Object> map) {
		String strRes = "error";
		if(StringUtils.isNotBlank(map.get("dwbm").toString()) &&
				StringUtils.isNotBlank(map.get("gh").toString())) {
			map.put("id", Identities.get32LenUUID());
			if(map.get("gender").equals("男")){
				map.put("gender", "1");
			}else{
				map.put("gender", "0");
			}
			try {
				//调用个人基本信息mapper
				sfdaGrxxPldrMapper.insertAllGrjbxx(map);
				strRes = "success";
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				//记录日志
				logService.error(Constant.CURRENT_USER.get().getDwbm(),
						Constant.CURRENT_USER.get().getGh(),
						Constant.CURRENT_USER.get().getMc(),
						Constant.RZLX_CWRZ, e.toString());
			}
		}
		return strRes;
	}

	@Override
	public String insertAllGrjl(Map<String, Object> map) {
		map.put("id", Identities.get32LenUUID());
		try {
			//调用个人基本信息mapper
			sfdaGrxxPldrMapper.insertAllGrjl(map);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			//记录日志
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
						Constant.CURRENT_USER.get().getGh(),
						Constant.CURRENT_USER.get().getMc(),
						Constant.RZLX_CWRZ, e.toString());
			return "error";
		}
	}

	@Override
	public String deleteGrjbxxById(String id) throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("id", id);
		try {
			sfdaGrxxPldrMapper.deleteGrjbxxById(id);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	@Override
	public String deleteGrjlById(String id) throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("id", id);
		try {
			sfdaGrxxPldrMapper.deleteGrjlById(id);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	@Override
	public Map<String, Object> selectGrjbxxBydg(String dwbm, String gh) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		Map<String, Object> grjbxx = new HashMap<String, Object>();
		grjbxx = sfdaGrxxPldrMapper.selectGrjbxxBydg(dwbm,gh);
		resMap.put("rows", grjbxx);
		return resMap;
	}

	@Override
	public Map<String, Object> selectGrjlBydg(String dwbm, String gh) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		List<Map<String, Object>> grjl = new ArrayList<>();
		grjl = sfdaGrxxPldrMapper.selectGrjlBydg(dwbm,gh);
		resMap.put("rows", grjl);
		return resMap;
	}




}
