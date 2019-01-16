package com.swx.ibms.business.rbac.service;

import com.swx.ibms.business.rbac.bean.BusinessType;

import java.util.List;



/**
 * 
 * @author east
 * @date:2017年4月21日
 * @version:1.0
 * @description:业务类别service
 *
 */
public interface BusinessTypeService {
	
	/**
	 * 查询所有未被删除的业务类别信息
	 * @return 业务类别结果集
	 */
	List<BusinessType> selectList();
}
