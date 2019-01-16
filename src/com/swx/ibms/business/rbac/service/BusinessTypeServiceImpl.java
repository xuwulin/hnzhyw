package com.swx.ibms.business.rbac.service;

import com.swx.ibms.business.rbac.bean.BusinessType;
import com.swx.ibms.business.rbac.mapper.BusinessTypeMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * @author east
 * @date:2017年4月21日
 * @version:1.0
 * @description:业务类别实现类
 *
 */
@SuppressWarnings("all")
@Service("businessTypeService")
public class BusinessTypeServiceImpl implements BusinessTypeService {

	/**
	 * 业务类别mapper
	 */
	@Resource
	private BusinessTypeMapper businessTypeMapper;
	
	@Override
	public List<BusinessType> selectList() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_cursor", StringUtils.EMPTY);
		businessTypeMapper.selectList(map);
		List<BusinessType> list = (List<BusinessType>)map.get("p_cursor");
		return list;
	}

}
