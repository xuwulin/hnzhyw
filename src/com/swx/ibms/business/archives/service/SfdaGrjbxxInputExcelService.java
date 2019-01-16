package com.swx.ibms.business.archives.service;

import com.swx.ibms.business.archives.bean.SfdaGrjbxxInputExcel;

import java.util.Map;



public interface SfdaGrjbxxInputExcelService {
	
	/**
	 * 查询导入的所有信息
	 * @param page
	 * @param pageSize
	 * @param grxx
	 * @return
	 */
	Map<String,Object> getGrxxExcelPageList(Integer page,Integer pageSize,SfdaGrjbxxInputExcel grxx);
	
	SfdaGrjbxxInputExcel getGrxxExcel(SfdaGrjbxxInputExcel grxx);
	
	Integer addGrxxExcel(SfdaGrjbxxInputExcel grxx);
	
	Integer modifyGrxxExcel(SfdaGrjbxxInputExcel grxx);
	
	Integer delGrxxExcel(SfdaGrjbxxInputExcel grxx);
	
}
