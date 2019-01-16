package com.swx.ibms.business.etl.mapper;


import com.swx.ibms.business.etl.bean.XtQuartzPz;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 同步任务配置接口
 * @author admin
 *
 */
public interface XtQuartzPzMapper {
	
	List<XtQuartzPz> getXtQuartzPzPageList();
	
	/**
	 * 获取最新的同步任务配置信息
	 * @param xtQuartzPz
	 * @return 同步任务配置
	 */
	XtQuartzPz getLastXtQuartzPz();
	
	XtQuartzPz getXtQuartzPzById(@Param("id")String id);
	
	Integer addXtQuartzPz(XtQuartzPz xtQuartzPz);
	
	Integer modifyXtQuartzPz(XtQuartzPz xtQuartzPz);
	
//	Integer modifyXtQuartzPzStatus(@Param("id")String id);
	
	Integer deleteXtQuartzPz(@Param("id")String id);
}
