package com.swx.ibms.business.performance.mapper;

import com.swx.ibms.business.performance.bean.Zbpzbt;
import org.apache.ibatis.annotations.Param;

import java.util.Map;



public interface ZbpzbtMapper {
	
	Zbpzbt getBt(@Param("id") String id);

	void setStatus(@Param("id")String id);

	void insertBt(Map<String, Object> map);


}
