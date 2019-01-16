package com.swx.ibms.business.archives.service;

import com.swx.ibms.business.archives.bean.SfdaJdqk;
import com.swx.ibms.business.archives.mapper.SfdaJdqkMapper;
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
*@date:2018年4月10日下午1:32:18
*@Description:司法档案监督情况Service实现类
*/
@SuppressWarnings("all")
@Service("sfdaJdqkService")
public class SfdaJdqkServiceImpl implements SfdaJdqkService {
	
	/**
	 * 司法档案下的监督情况Mapper
	 */
	@Autowired
	private SfdaJdqkMapper sfdaJdqkMapper;

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;
	
	/**
	 * 展示监督情况
	 */
	@Override
	public Map getSfdaJdqkPageList(SfdaJdqk jdqk, int start, int end) {
		Map<String, Object> map = new HashMap<String, Object>(Constant.NUM_10);
		Map<String, Object> resMap = new HashMap<String, Object>(Constant.NUM_10);
		map.put("p_gdid", jdqk.getGdid());
		map.put("p_start", start);//开始页
		map.put("p_end", end);//结束页
		map.put("p_total", StringUtils.EMPTY);//总记录
		map.put("p_cursor", StringUtils.EMPTY);//cursor
		try {
			sfdaJdqkMapper.getSfdaJdqkPageList(map);
			resMap.put("p_total", Integer.parseInt(map.get("p_total").toString()));
			resMap.put("p_cursor",map.get("p_cursor"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resMap;
	}

	/**
	 * 添加监督情况
	 */
	@Override
	public String addSfdaJdqk(SfdaJdqk jdqk) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map.put("p_id", Identities.get32LenUUID());
		map.put("p_jdzl", jdqk.getJdzl());
		map.put("p_jdbm", jdqk.getJdbm());
		map.put("p_jdsj", jdqk.getJdsj());
		map.put("p_jdsy", StrUtil.strTransform(jdqk.getJdsy()));
		map.put("p_jdcl", jdqk.getJdcl());
		map.put("p_zgqk", jdqk.getZgqk());
		map.put("p_jdqtqk", StrUtil.strTransform(jdqk.getJdqtqk()));
		map.put("p_gdid", jdqk.getGdid());
		map.put("p_sfsc", 'N');
		map.put("p_zhgxbm", Constant.CURRENT_USER.get().getBmbm());
		map.put("p_zhgxr", Constant.CURRENT_USER.get().getGh());
		map.put("p_zhgxsj", DateUtil.getSysCurrentDateTime());
		map.put("Y", StringUtils.EMPTY);
		sfdaJdqkMapper.addSfdaJdqk(map);
		
		map1.put("Y", map.get("Y"));

		String sign = (String) map.get("Y");
		if ("1".equals(sign)) {
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "新增监督情况成功");
		} else {
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, "新增监督情况失败");
		}

		return Constant.GSON.toJson(map1);
	}

	/**
	 * 更新监督情况
	 */
	@Override
	public String updateSfdaJdqk(SfdaJdqk jdqk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_id", jdqk.getId());
		map.put("p_jdzl", jdqk.getJdzl());
		map.put("p_jdbm", jdqk.getJdbm());
		map.put("p_jdsj", jdqk.getJdsj());
		map.put("p_jdsy", StrUtil.strTransform(jdqk.getJdsy()));
		map.put("p_jdcl", jdqk.getJdcl());
		map.put("p_zgqk", jdqk.getZgqk());
		map.put("p_jdqtqk", StrUtil.strTransform(jdqk.getJdqtqk()));
		map.put("p_zhgxbm", Constant.CURRENT_USER.get().getBmbm());
		map.put("p_zhgxr", Constant.CURRENT_USER.get().getGh());
		map.put("p_zhgxsj", DateUtil.getSysCurrentDateTime());
		map.put("Y", StringUtils.EMPTY);
		
		try {
			sfdaJdqkMapper.updateSfdaJdqk(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String sign = (String) map.get("Y");
		if ("1".equals(sign)) {
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "修改监督情况成功");
		} else {
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, "修改监督情况失败");
		}

		return sign;
	}

	/**
	 * 删除监督情况
	 */
	@Override
	public String deleteSfdaJdqk(SfdaJdqk jdqk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_id", jdqk.getId());
		map.put("Y", StringUtils.EMPTY);
		try {
			sfdaJdqkMapper.deleteSfdaJdqk(map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sign = (String) map.get("Y");
		if ("1".equals(sign)) {
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "删除监督情况成功" );
		} else {
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, "删除监督情况失败");
		}

		return (String) map.get("Y");
	}

}
