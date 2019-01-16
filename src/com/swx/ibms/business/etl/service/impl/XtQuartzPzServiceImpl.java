package com.swx.ibms.business.etl.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.pagehelper.Page;
import com.swx.ibms.business.etl.bean.XtQuartzPz;
import com.swx.ibms.business.etl.mapper.XtQuartzPzMapper;
import com.swx.ibms.business.etl.service.XtQuartzPzService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

/**
 * 同步任务配置
 * @author baby
 *
 */
@SuppressWarnings("all")
@Service("xtQuartzPzService")
public class XtQuartzPzServiceImpl implements XtQuartzPzService {
	
	private static final Logger LOG = Logger.getLogger(XtQuartzPzServiceImpl.class);

	@Resource
	private XtQuartzPzMapper xtQuartzPzMapper;
	
	@Override
	public Map<String, Object> getXtQuartzPzPageList(Integer page, Integer rows) {
		Map<String, Object> res = new HashMap<String, Object>();
		List<XtQuartzPz> pzList = new ArrayList<XtQuartzPz>();
		try {
			pzList = xtQuartzPzMapper.getXtQuartzPzPageList();
//			XtQuartzPz PZ = xtQuartzPzMapper.getLastXtQuartzPz();
//			pzList.add(PZ);
		} catch (Exception e) {
			LOG.error("获取同步配置分页信息失败！",e);
			e.printStackTrace();
		}
		Page<List<XtQuartzPz>> pager = PageHelper.startPage(page, rows);
		res.put("total", pager.getTotal());
        res.put("rows", pzList);
		return res;
	}

	@Override
	public XtQuartzPz getXtQuartzPzById(String id) {
		XtQuartzPz xtQuartzPz = new XtQuartzPz();
		try {
			xtQuartzPz = xtQuartzPzMapper.getXtQuartzPzById(id);
		} catch (Exception e) {
			LOG.error("根据id获取同步配置信息失败！",e);
			e.printStackTrace();
		}
		return xtQuartzPz;
	}

	@Override
	public Integer addXtQuartzPz(XtQuartzPz xtQuartzPz) {
		Integer ressult = 0;
		try {
			ressult = xtQuartzPzMapper.addXtQuartzPz(xtQuartzPz);
		} catch (Exception e) {
			LOG.error("添加同步配置信息失败！",e);
			e.printStackTrace();
		}
		return ressult;
	}

	@Override
	public Integer modifyXtQuartzPz(XtQuartzPz xtQuartzPz) {
		Integer ressult = 0;
		try {
			ressult = xtQuartzPzMapper.modifyXtQuartzPz(xtQuartzPz);
		} catch (Exception e) {
			LOG.error("修改同步配置信息失败！",e);
			e.printStackTrace();
		}
		return ressult;
	}

	@Override
	public Integer deleteXtQuartzPz(String id) {
		Integer ressult = 0;
		try {
			ressult = xtQuartzPzMapper.deleteXtQuartzPz(id);
		} catch (Exception e) {
			LOG.error("删除同步配置信息失败！",e);
			e.printStackTrace();
		}
		return ressult;
	}

	@Override
	public XtQuartzPz getLastXtQuartzPz() {
		XtQuartzPz xtQuartzPz = new XtQuartzPz();
		try {
			xtQuartzPz = xtQuartzPzMapper.getLastXtQuartzPz();
		} catch (Exception e) {
			LOG.error("获取最新创建的同步配置信息失败！",e);
			e.printStackTrace();
		}
		return xtQuartzPz;
	}

}
