package com.swx.ibms.business.archives.service;

import com.swx.ibms.business.archives.bean.SfdaZrzj;
import com.swx.ibms.business.archives.mapper.SfdaZrzjMapper;
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
*@Description: 责任追究Service实现类
*/
@SuppressWarnings("all")
@Service("SfdaZrzjService")
public class SfdaZrzjServiceImpl implements SfdaZrzjService {
	
	/**
	 * 司法档案下的责任追究Mapper
	 */
	@Autowired
	private SfdaZrzjMapper sfdaZrzjMapper;

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;
	
	/**
	 * 展示所有责任追究（根据档案id）
	 */
	@Override
	public Map getSfdaZrzjPageList(SfdaZrzj zrzj, int start, int end) {
		Map<String, Object> map = new HashMap<String, Object>(Constant.NUM_10);
		Map<String, Object> resMap = new HashMap<String, Object>(Constant.NUM_10);
		map.put("p_gdid", zrzj.getGdid());
		map.put("p_start", start);//开始页
		map.put("p_end", end);//结束页
		map.put("p_total", StringUtils.EMPTY);//总记录
		map.put("p_cursor", StringUtils.EMPTY);//cursor
		try {
			sfdaZrzjMapper.getSfdaZrzjPageList(map);
			resMap.put("p_total", Integer.parseInt(map.get("p_total").toString()));
			resMap.put("p_cursor",map.get("p_cursor"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resMap;
	}
	
	/**
	 * 新增责任追究
	 */
	@Override
	public String addSfdaZrzj(SfdaZrzj zrzj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map.put("p_id", Identities.get32LenUUID());
		map.put("p_zrzl", zrzj.getZrzl());
		map.put("p_clbm", zrzj.getClbm());
		map.put("p_clsj", zrzj.getClsj());
		map.put("p_clsy", StrUtil.strTransform(zrzj.getClsy()));
		map.put("p_clnr", StrUtil.strTransform(zrzj.getClnr()));
		map.put("p_cljg", zrzj.getCljg());
		map.put("p_zrqtqk", StrUtil.strTransform(zrzj.getZrqtqk()));
		map.put("p_gdid", zrzj.getGdid());
		map.put("p_sfsc", 'N');
		map.put("p_zhgxbm", Constant.CURRENT_USER.get().getBmbm());
		map.put("p_zhgxr", Constant.CURRENT_USER.get().getGh());
		map.put("p_zhgxsj", DateUtil.getSysCurrentDateTime());
		map.put("Y", StringUtils.EMPTY);
		sfdaZrzjMapper.addSfdaZrzj(map);
		
		map1.put("Y", map.get("Y"));

		String sign = (String) map.get("Y");
		if ("1".equals(sign)) {
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "新增责任追究成功");
		} else {
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, "新增责任追究失败");
		}

		return Constant.GSON.toJson(map1);
	}

	/**
	 * 修改责任追究
	 */
	@Override
	public String updateSfdaZrzj(SfdaZrzj zrzj) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_id", zrzj.getId());
		map.put("p_zrzl", zrzj.getZrzl());
		map.put("p_clbm", zrzj.getClbm());
		map.put("p_clsj", zrzj.getClsj());
		map.put("p_clsy", StrUtil.strTransform(zrzj.getClsy()));
		map.put("p_clnr", StrUtil.strTransform(zrzj.getClnr()));
		map.put("p_cljg", zrzj.getCljg());
		map.put("p_zrqtqk", StrUtil.strTransform(zrzj.getZrqtqk()));
		map.put("p_zhgxbm", Constant.CURRENT_USER.get().getBmbm());
		map.put("p_zhgxr", Constant.CURRENT_USER.get().getGh());
		map.put("p_zhgxsj", DateUtil.getSysCurrentDateTime());
		map.put("Y", StringUtils.EMPTY);
		
		try {
			sfdaZrzjMapper.updateSfdaZrzj(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String sign = (String) map.get("Y");
		if ("1".equals(sign)) {
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "修改荣誉技能成功");
		} else {
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, "修改荣誉技能失败");
		}

		return sign;
	}

	/**
	 * 删除责任追究
	 */
	@Override
	public String deleteSfdaZrzj(SfdaZrzj zrzj) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_id", zrzj.getId());
		map.put("Y", StringUtils.EMPTY);
		try {
			sfdaZrzjMapper.deleteSfdaZrzj(map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sign = (String) map.get("Y");
		if ("1".equals(sign)) {
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "删除责任追究成功" );
		} else {
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, "删除责任追究失败");
		}

		return (String) map.get("Y");
	}

	

}
