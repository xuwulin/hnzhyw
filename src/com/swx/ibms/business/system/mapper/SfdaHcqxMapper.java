package com.swx.ibms.business.system.mapper;

import com.swx.ibms.business.system.bean.Hcqx;
import org.apache.ibatis.annotations.Param;

import java.util.List;



/**
 * @author zsq
 *
 */
public interface SfdaHcqxMapper {

	/**
	 * @return  获取核查期限信息
	 */
	List<Hcqx> getHcqx();

	/** 更新核查期限信息
	 * @param hcqx 核查期限
	 * @param gxr 更新人
	 */
	void setHcqx(@Param("hcqx")int hcqx, @Param("gxr")String gxr);

	/**
	 * @param gsqx 公示期限
	 * @param gxr 更新人
	 */
	void setGsqx(@Param("gsqx") int gsqx, @Param("gxr") String gxr);

}
