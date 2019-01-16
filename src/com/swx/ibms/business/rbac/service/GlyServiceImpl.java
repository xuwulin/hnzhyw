package com.swx.ibms.business.rbac.service;

import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.rbac.mapper.GlyMapper;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Clob;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * <p>
 * Title:GlyServiceImpl
 * </p>
 * <p>
 * Description: 管理员serviceImpl
 * </p>
 * author 朱春雨 date 2017年4月27日 下午2:06:23
 */
@Service("GlyService")
public class GlyServiceImpl implements GlyService {

	/**
	 * 管理员mapper
	 */
	@Resource
	private GlyMapper glyMapper;

	/*
	 * (non-Javadoc) 插入管理员数据
	 * 
	 * @see com.swx.zhyw.service.GlyService#insertGly(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.sql.Clob)
	 */
	@Override
	public String insertGly(String dwbm, String dlbm, String kl, String mc, String xb, Clob qxxx) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_dwbm", dwbm);
		map.put("p_bmbm", "0001");
		map.put("p_jsbm", "001");
		map.put("p_dlbm", dlbm);
		map.put("p_kl", kl);
		map.put("p_mc", mc);
		map.put("p_xb", xb);
		map.put("p_qxxx", qxxx);
		map.put("message", null);
		String message = "新增成功";
		try {
			glyMapper.insertGly(map);
			if ("1".equals(map.get("message"))) {
				message = "登录别名重复";
			} else {
				message = "新增成功";
			}
		} catch (Exception e) {
			message = "新增失败";
			throw new RuntimeException();
			// TODO: handle exception
		}
		return message;
	}

	/*
	 * (non-Javadoc) 删除管理员数据
	 * 
	 * @see com.swx.zhyw.service.GlyService#deleteGly(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String deleteGly(String dwbm, String gh) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_dwbm", dwbm);
		map.put("p_gh", gh);
		String message = "删除成功";
		try {
			glyMapper.deleteGly(map);
			message = "删除成功";
		} catch (Exception e) {
			// TODO: handle exception
			message = "删除失败";
			throw new RuntimeException();
		}

		return message;
	}

	/*
	 * (non-Javadoc) 更新管理员
	 * 
	 * @see com.swx.zhyw.service.GlyService#updateGly(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.sql.Clob)
	 */
	@Override
	public String updateGly(String dwbm, String gh, String dlbm, String kl, String mc, String xb, Clob qxxx) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_dwbm", dwbm);
		map.put("p_gh", gh);
		map.put("p_dlbm", dlbm);
		map.put("p_kl", kl);
		map.put("p_mc", mc);
		map.put("p_xb", xb);
		map.put("p_qxxx", qxxx);
		String message = "更新成功";
		try {
			glyMapper.updateGly(map);
			message = "更新成功";
		} catch (Exception e) {
			message = "更新失败";
			throw new RuntimeException();
		}
		return message;
	}

	@Override
	public PageCommon<RYBM> selectGlyPageList(String dwbm, String sfsc, int nowPage, int pageSize) {
		Map<String,Object> map = new HashMap<String,Object>();
		PageCommon<RYBM> pageCommon = new PageCommon();
			int page = (nowPage-1)*pageSize;
			pageCommon.setPageSize(page+pageSize);//每页显示数
			page++;
			pageCommon.setNowPage(page);//当前页
		if (!"".equals(dwbm)&&!"".equals(sfsc)) {
			map.put("p_dwbm", dwbm);
			map.put("p_sfsc", sfsc);
			map.put("p_start", pageCommon.getNowPage());
			map.put("p_end", pageCommon.getPageSize());
			map.put("p_total", StringUtils.EMPTY);
			map.put("p_cursor", StringUtils.EMPTY);
			glyMapper.selectGly(map);
			List<RYBM> list = (List<RYBM>)map.get("p_cursor");
			pageCommon.setList(list);
			pageCommon.setTotalRecords(Integer.parseInt(map.get("p_total").toString()));
		}
		
		return pageCommon;
	}

}
