package com.swx.ibms.business.performance.service.impl;


import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.performance.bean.GrjxZbpz;
import com.swx.ibms.business.performance.bean.Zbpzbt;
import com.swx.ibms.business.performance.mapper.GrjxZbpzMapper;
import com.swx.ibms.business.performance.mapper.ZbpzbtMapper;
import com.swx.ibms.business.performance.service.GrjxZbpzService;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author east
 * @date:2017年4月13日
 * @version:1.0
 * @description:个人绩效指标配置实现类
 *
 */
@SuppressWarnings("all")
@Service("grjxZbpzService")
public class GrjxZbpzServiceImpl implements GrjxZbpzService {

	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(GrjxAjWsServiceImpl.class);

	/**
	 * 个人绩效mapper
	 */
	@Resource
	private GrjxZbpzMapper grjxZbpzMapper;
	
	/**
	 * 指标配置表头表mapper
	 */
	@Resource
	private ZbpzbtMapper zbpzbtMapper;
	
	@Override
	public PageCommon<Object> selectList(Integer dwjb, Integer page, Integer pageSize) {
		PageCommon<Object> pageCommon = new PageCommon<Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		
		int nowPage = (0==page? Constant.NUM_1:page);
		int pageStart = (nowPage - Constant.NUM_1) * pageSize;
		int pageEnd = pageStart+pageSize;
		pageStart++;
		
		map.put("p_start", pageStart);
		map.put("p_end", pageEnd);
		map.put("p_dwjb", dwjb);
		map.put("p_total", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);
		try {
			grjxZbpzMapper.selectList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 得到page对象、总记录数、
		List<Object> list = (List<Object>) map.get("p_cursor");
		pageCommon.setList(list);
		pageCommon.setTotalRecords(Integer.parseInt(map.get("p_total").toString()));
		return pageCommon;
	}

	@Override
	public GrjxZbpz selectById(String id) {
		GrjxZbpz grjxZbpz = new GrjxZbpz();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_zbpz_id", id);
		map.put("p_cursor", StringUtils.EMPTY);
		try {
			grjxZbpzMapper.selectById(map);
		} catch (Exception e) {
			e.printStackTrace();
//			LOG.error("根据个人绩效指标id查询个人绩效考核指标出错", e);
		}	
//				grjxZbpz = grjxZbpzMapper.selectById(map);
		ArrayList lsit =  (ArrayList) map.get("p_cursor");
		grjxZbpz = (GrjxZbpz) lsit.get(0);
		return grjxZbpz;
	}

	@Override
	public String insertData(Map<String, Object> map) {
		String str = "0";
		if (map!=null) {
		   str = grjxZbpzMapper.insertData(map);
		   if ("1".equals(str)) {
				return "操作成功！";
			}else{
				return "操作失败！";
			}
		}
		return str;
	}

	@Override
	public String updateData(Map<String, Object> map) {
		String str = "0";
		if (map != null) {
			str = grjxZbpzMapper.updateData(map);
			if ("1".equals(str)) {
				return "操作成功！";
			}else{
				return "操作失败！";
			}
		}
		return str;
	}

	@Override
	public String deleteData(Map<String, Object> map) {
		if (map != null) {
			if (!"".equals(map.get("zbpzId"))) {
				map.put("p_zbpz_id", map.get("zbpzId"));
				map.put("p_status", 0);
				map.put("p_msg", StringUtils.EMPTY);
				grjxZbpzMapper.deleteData(map);
				if ("1".equals(map.get("p_msg"))) {
					return "操作成功！";
				}else{
					return "操作失败！";
				}
			}
		}
		return null;
	}

	/** 
	 * 查找表头数据
	 */
	@Override
	public String selectBtById(String id) {
		GrjxZbpz grjxZbpz = new GrjxZbpz();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_zbpz_id", id);
		map.put("p_cursor", StringUtils.EMPTY);
		try {
			grjxZbpzMapper.selectById(map);
		} catch (Exception e) {
			//LOG.error(StringUtils.EMPTY, e);
		}	
//				grjxZbpz = grjxZbpzMapper.selectById(map);
		ArrayList lsit =  (ArrayList) map.get("p_cursor");
		grjxZbpz = (GrjxZbpz) lsit.get(0);
		Zbpzbt zbpzbt = zbpzbtMapper.getBt(grjxZbpz.getTabletopid());
		return zbpzbt.getBtsj();
	}

	@Override
	public String insertBt(String tabletop,String cjr,String khsjdata) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_cjr", cjr);
		map.put("p_tabletop", tabletop);
		map.put("p_khsjdata", khsjdata);
		map.put("p_id", null);
		zbpzbtMapper.insertBt(map);
		String pid = (String) map.get("p_id");
		return pid;
	}

	@Override
	public void updateStatus(String lastid) {
		grjxZbpzMapper.updateStatus(lastid);
	}

	@Override
	public String insert(String ywlb, int dwjb, String khnrdata,
			String pznrdata,String topid,String dlbm,String pjf,
			String rylb,String ssYear) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ywlb", ywlb);
		map.put("dwjb", dwjb);
		map.put("khnrdata", khnrdata);
		map.put("pznrdata", pznrdata);
		map.put("topid", topid);
		map.put("dlbm", dlbm);
		map.put("pjf", pjf);
		map.put("rylb", rylb);
		map.put("ssYear", ssYear);
		map.put("id", StringUtils.EMPTY);
		try {
			grjxZbpzMapper.savedata(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (String) map.get("id");
	}
	
	@Override
	public int isExist(String ywlb,String dwjb,String type,String year) {
		Map<String, Object> map = new HashMap<String, Object>(Constant.NUM_5);
		map.put("ywlb", ywlb);
		map.put("dwjb", dwjb);
		map.put("rylb", type);
		map.put("ssrq", year);
		int data = 0;
		try {
			data = grjxZbpzMapper.isExist(map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (data > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public List<Map<String,Object>> getYwlxList(String ywbm) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_ywbm", ywbm);
		map.put("p_cursor", StringUtils.EMPTY);
		try {
			grjxZbpzMapper.getYwlxList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Map<String,Object>> ywbmList = (List<Map<String,Object>>)map.get("p_cursor");
		return ywbmList;
	}

	@Override
	public List<Map<String, Object>> getInfoOfZbpz(String dwjb, String bmlb, String rylb, String ssnf) {

		List<Map<String, Object>> zbpzInfo = new ArrayList<>();
		try {
			zbpzInfo = grjxZbpzMapper.getInfoOfZbpz(dwjb, bmlb, rylb, ssnf);
			for (Map<String, Object> map : zbpzInfo) {
				String khnrStr = IOUtils.toString(((Clob) map.get("KHNR")).getCharacterStream());
				String pznrStr = IOUtils.toString(((Clob) map.get("PZNR")).getCharacterStream());
				map.put("KHNR", khnrStr);
				map.put("PZNR", pznrStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("获取个人绩效配置指标失败", e);
		}

		return zbpzInfo;
	}

}
