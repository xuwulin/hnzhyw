package com.swx.ibms.business.archives.service;

import com.swx.ibms.business.archives.bean.SfdaQtqk;
import com.swx.ibms.business.archives.mapper.SfdaQtqkMapper;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.common.utils.DateUtil;
import com.swx.ibms.common.utils.Identities;
import com.swx.ibms.common.utils.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;



/**
*@author:徐武林
*@date:2018年3月28日下午5:39:32
*@Description:
*/
@SuppressWarnings("all")
@Service("SfdaQtqkService")
public class SfdaQtqkServiceImpl implements SfdaQtqkService {
	
	/**
	 * 司法档案下的其他情况Mapper
	 */
	@Autowired
	private SfdaQtqkMapper sfdaQtqkMapper;

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;
	
	/**
	 * 展示司法技能下的其他情况
	 */
	@Override
	public Map getSfdaQtqkPageList(SfdaQtqk qtqk, int start, int end) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>(Constant.NUM_10);
		Map<String, Object> resMap = new HashMap<String, Object>(Constant.NUM_10);
		map.put("p_gdid", qtqk.getGdid());
		map.put("p_start", start);//开始页
		map.put("p_end", end);//结束页
		map.put("p_total", StringUtils.EMPTY);//总记录
		map.put("p_cursor", StringUtils.EMPTY);//cursor
		try {
			sfdaQtqkMapper.getSfdaQtqkPageList(map);
			resMap.put("p_total", Integer.parseInt(map.get("p_total").toString()));
			resMap.put("p_cursor",map.get("p_cursor"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resMap;
	}

	/**
	 * 新增其他情况
	 */
	@Override
	public String addSfdaQtqk(SfdaQtqk qtqk) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map.put("p_id", Identities.get32LenUUID());
		map.put("p_lrbm", qtqk.getLrbm());
		map.put("p_lrr", StrUtil.strTransform(qtqk.getLrr()));
		map.put("p_lrsj", qtqk.getLrsj());
		map.put("p_lrnr", StrUtil.strTransform(qtqk.getLrnr()));
		map.put("p_gdid", qtqk.getGdid());
		map.put("p_sfsc", 'N');
		map.put("p_zhgxbm", Constant.CURRENT_USER.get().getBmbm());
		map.put("p_zhgxr", Constant.CURRENT_USER.get().getGh());
		map.put("p_zhgxsj", DateUtil.getSysCurrentDateTime());
		map.put("Y", StringUtils.EMPTY);
		sfdaQtqkMapper.addSfdaQtqk(map);
		
		map1.put("Y", map.get("Y"));

		String sign = (String) map.get("Y");
		if ("1".equals(sign)) {
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "新增其他情况成功");
		} else {
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, "新增其他情况失败");
		}

		return Constant.GSON.toJson(map1);
	}

	/**
	 * 更新其他情况
	 */
	@Override
	public String updateSfdaQtqk(SfdaQtqk qtqk) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_id", qtqk.getId());
		map.put("p_lrbm", qtqk.getLrbm());
		map.put("p_lrr", StrUtil.strTransform(qtqk.getLrr()));
		map.put("p_lrsj", qtqk.getLrsj());
		map.put("p_lrnr", StrUtil.strTransform(qtqk.getLrnr()));
		map.put("p_zhgxbm", Constant.CURRENT_USER.get().getBmbm());
		map.put("p_zhgxr", Constant.CURRENT_USER.get().getGh());
		map.put("p_zhgxsj", DateUtil.getSysCurrentDateTime());
		map.put("Y", StringUtils.EMPTY);
		
		try {
			sfdaQtqkMapper.updateSfdaQtqk(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String sign = (String) map.get("Y");
		if ("1".equals(sign)) {
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "修改其他情况成功");
		} else {
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, "修改其他情况失败");
		}

		return sign;
	}

	/**
	 * 删除其他情况
	 */
	@Override
	public String deleteSfdaQtqk(SfdaQtqk qtqk) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_id", qtqk.getId());
		map.put("Y", StringUtils.EMPTY);
		try {
			sfdaQtqkMapper.deleteSfdaQtqk(map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sign = (String) map.get("Y");
		if ("1".equals(sign)) {
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "删除其他情况成功" );
		} else {
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, "删除其他情况失败");
		}

		return (String) map.get("Y");
	}

	

}
