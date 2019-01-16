package com.swx.ibms.business.system.service;

import com.swx.ibms.business.system.bean.Gdjs;
import com.swx.ibms.business.system.mapper.GdjsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;



/**
 * @author zsq
 *
 */
@Service
public class GdjsServiceImpl implements GdjsService{
	
	/**
	 * gdjsMapper
	 */
	@Resource
	private GdjsMapper gdjsMapper;
	@Override
	public List<Gdjs> getjs() {
		List<Gdjs> gdjslist = gdjsMapper.getjs();
		return gdjslist;
	}
}
