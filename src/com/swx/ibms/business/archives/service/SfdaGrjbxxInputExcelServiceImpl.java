package com.swx.ibms.business.archives.service;

import com.swx.ibms.business.archives.bean.SfdaGrjbxxInputExcel;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;



public class SfdaGrjbxxInputExcelServiceImpl implements SfdaGrjbxxInputExcelService {

	@Override
	public Map<String, Object> getGrxxExcelPageList(Integer page, Integer pageSize, SfdaGrjbxxInputExcel grxx) {
		Map<String, Object> map = new HashMap<String,Object>();
		
		map.put("total", StringUtils.EMPTY);
		map.put("rows", StringUtils.EMPTY);
		return map;
	}

	@Override
	public SfdaGrjbxxInputExcel getGrxxExcel(SfdaGrjbxxInputExcel grxx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer addGrxxExcel(SfdaGrjbxxInputExcel grxx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modifyGrxxExcel(SfdaGrjbxxInputExcel grxx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delGrxxExcel(SfdaGrjbxxInputExcel grxx) {
		// TODO Auto-generated method stub
		return null;
	}

}
