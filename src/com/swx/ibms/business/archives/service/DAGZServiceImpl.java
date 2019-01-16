package com.swx.ibms.business.archives.service;


import com.swx.ibms.business.archives.mapper.DAGZMapper;
import com.swx.ibms.business.archives.mapper.PersonMapper;
import com.swx.ibms.business.common.bean.Splcsl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * @author 朱春雨
 *档案归总
 */
@Service("DAGZService")
public class DAGZServiceImpl implements DAGZService{

	/**
	 * 档案归总mapper
	 */
	@Resource
	private DAGZMapper dagzMapper;
	
	/**
	 * 个人信息mapper
	 */
	@Resource
	private PersonMapper personMapper;
	
	
	/* (non-Javadoc)通过审批实体id和审批类型查出yx_sfda_splcsl数据集
	 * @see com.swx.zhyw.service.DAGZService#showSplcsl(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Splcsl> showSplcsl(String spstid, String splx) {
		// TODO Auto-generated method stub
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("p_spstid", spstid);
		map.put("p_splx", splx);
		List<Splcsl> list=null;
		try {
			dagzMapper.showSplcsl(map);
			list=(List<Splcsl>)map.get("p_cursor");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	/* (non-Javadoc)所属人档案归总的数量
	 * @see com.swx.zhyw.service.DAGZService#ssrDagzCount(java.lang.String, java.lang.String)
	 */
	@Override
	public int ssrDagzCount(String dwbm, String gh,String sffc) {
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("p_dwbm", dwbm);
		map.put("p_gh",gh);
		map.put("p_sffc",sffc);
		map.put("p_count",0);
		int y=0;
		try {
			dagzMapper.ssrDagzCount(map);
			y=(int)map.get("p_count");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return y;
	}
	/* (non-Javadoc)删除档案归总
	 * @see com.swx.zhyw.service.DAGZService#deleDagz(java.lang.String)
	 */
	@Override
	public boolean deleDagz(String gdid,String grjbxxId) {
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("p_id", gdid);
		map.put("p_gdid",gdid);
		
		if (StringUtils.isNotEmpty(grjbxxId)&&StringUtils.isNotEmpty(gdid)) {
			map.put("p_grjbxx_id", grjbxxId);
			map.put("p_da_id", gdid);
		}
		
		map.put("p_errmsg", StringUtils.EMPTY);
		
	    boolean y=true;
		try {
			
			dagzMapper.deleteDagz(map);
			
			dagzMapper.deleteDaDalx(map);
			
			//删除个人信息
			personMapper.deleteAllGrxxById(map);
			
		} catch (Exception e) {
			// TODO: handle exception
			y=false;
		}
		return y;
	}
	/* 插入数据到DAGZ
	 * @see com.swx.zhyw.service.DAGZService#insertDAGZData(com.swx.zhyw.po.DAGZ)
	 */
	@Override
	public int sfBazlCount(String bmsah) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_bmsah", bmsah);
		map.put("p_count",0);
		try {
			dagzMapper.sfBazlCount(map);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return (int)map.get("p_count");
	}
	/* 
	 * @see com.swx.zhyw.service.DAGZService#sfDaCount(java.lang.String, java.lang.String)
	 */
	@Override
	public int sfDaCount(String gdid, String dalx) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_gdid", gdid);
		map.put("p_dalx", dalx);
		map.put("p_count", 0);
		try {
			dagzMapper.sfDaCount(map);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return (int)map.get("p_count");
	}
	/* (non-Javadoc)通过spstid和dalx条件查出splcsl表里的数量
	 * @see com.swx.zhyw.service.DAGZService#spstidCount(java.lang.String, java.lang.String)
	 */
	@Override
	public int spstidCount(String spstid, String splx) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_spstid", spstid);
		map.put("p_splx", splx);
		map.put("p_count", 0);
		try {
			dagzMapper.spstidCount(map);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return (int)map.get("p_count");
	}
	@Override
	public String insertDAGZData(Map<String, Object> map) {
		map.put("Y", "");
		dagzMapper.insertDAGZData(map);
		return   (String) map.get("Y");
	}

	/* 是否有自己的档案
	 * @see com.swx.zhyw.service.DAGZService#sFYZJ(java.util.Map)
	 */
	@Override
	public String sFYZJ(Map<String, Object> map) {
		map.put("Y", "");
		dagzMapper.sFYZJ(map);
		return   (String) map.get("Y");
	}

	/* 查询是是否有自己年份的档案
	 * @see com.swx.zhyw.service.DAGZService#sFYZJNF(java.util.Map)
	 */
	@Override
	public String sFYZJNF(Map<String, Object> map) {
		map.put("Y", "");
		dagzMapper.sFYZJNF(map);
		return   (String) map.get("Y");
	}
}
