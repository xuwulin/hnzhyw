package com.swx.ibms.business.system.service;

import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.system.bean.Djpd;
import com.swx.ibms.business.system.mapper.DjpdMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 等级评定实现层
 * @author 李佳
 * @date: 2017年05月22日 
 */
@Service("DjpdService")
public class DjpdServiceImpl implements DjpdService{
	/**
	 * 等级评定Mapper接口
	 */
	@Resource
	private DjpdMapper djpdMapper;
	
	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;

	@Override
	public List<Djpd> selectDjpd() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_cursor", "");
		try {
			djpdMapper.selectDjpd(map);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e.toString());
		}
		List<Djpd> list=(List<Djpd>) map.get("p_cursor");
		return list;
	}

	@Override
	public List<String> djpd(double zpjdf) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_zpjdf", zpjdf);
		map.put("p_pdjb", "");
		map.put("p_pdjbmc", "");
		map.put("p_errmsg", "");
		try {
			djpdMapper.djpd(map);
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e.toString());
		}
		String errMsg=(String)map.get("p_errmsg");
		List<String> list=new ArrayList<String>();
		if(map.get("p_errmsg") == null || "".equals(map.get("p_errmsg"))){
			String pdjb = (String) map.get("p_pdjb");
			list.add(pdjb);
			String pdjbmc = (String) map.get("p_pdjbmc");
			list.add(pdjbmc);
		}
		return list;
	}

	@Override
	public boolean update(List<Djpd> list) {
		try{
			for(Djpd djpd : list){
				djpdMapper.update(djpd);
			}
			return true;
		}catch(Exception e){
			return false;
		}
	}


	
 
}
