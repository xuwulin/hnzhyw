package com.swx.ibms.business.archives.mapper;

import com.swx.ibms.business.archives.bean.SfdaGrjbxxInputExcel;
import java.util.List;


public interface SfdaGrjbxxInputExcelMapper {
	
	List<SfdaGrjbxxInputExcel> getGrxxExcelPageList(Integer page, Integer pageSize, SfdaGrjbxxInputExcel grxx);
	
	SfdaGrjbxxInputExcel getGrxxExcel(SfdaGrjbxxInputExcel grxx);
	
	Integer addGrxxExcel(SfdaGrjbxxInputExcel grxx);
	
	Integer modifyGrxxExcel(SfdaGrjbxxInputExcel grxx);
	
	Integer delGrxxExcel(SfdaGrjbxxInputExcel grxx);
}
