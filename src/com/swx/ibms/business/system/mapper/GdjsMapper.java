package com.swx.ibms.business.system.mapper;

import com.swx.ibms.business.rbac.bean.JSBM;
import com.swx.ibms.business.system.bean.Gdjs;
import org.apache.ibatis.annotations.Param;

import java.util.List;



/**
 * @author zsq
 *
 */
public interface GdjsMapper {

	/**
	 * @return
	 * 返回所有的固定角色信息
	 */
	List<Gdjs> getjs();

	/**
	 * @param jsbm 角色编码
	 * @return 固定角色
	 */
	JSBM getgdjs(@Param("jsbm")String jsbm);
	
	
	
}
