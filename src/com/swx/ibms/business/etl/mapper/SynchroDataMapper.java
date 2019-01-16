package com.swx.ibms.business.etl.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * 数据同步清空操作
 * @author 李治鑫
 * @since 2017年8月24日  下午2:20:15
 */
public interface SynchroDataMapper {
	/**
	 * 清空数据
	 * @param tableName 表名
	 */
	void deleteData(@Param("tableName")String tableName);
}
