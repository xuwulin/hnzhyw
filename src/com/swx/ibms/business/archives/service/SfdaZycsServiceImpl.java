package com.swx.ibms.business.archives.service;

import com.swx.ibms.business.archives.bean.SfdaZycs;
import com.swx.ibms.business.archives.mapper.SfdaZycsMapper;
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
*@Description: 职业操守Service实现类
*/
@SuppressWarnings("all")
@Service("SfdaZycsService")
public class SfdaZycsServiceImpl implements SfdaZycsService {
	
	/**
	 * 司法档案下的职业操守Mapper
	 */
	@Autowired
	private SfdaZycsMapper sfdaZycsMapper;

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;
	
	/**
	 * 展示所有职业操守（根据档案id）
	 */
	@Override
	public Map getSfdaZycsPageList(SfdaZycs zycs, int start, int end) {
		Map<String, Object> map = new HashMap<String, Object>(Constant.NUM_10);
		Map<String, Object> resMap = new HashMap<String, Object>(Constant.NUM_10);
		map.put("p_gdid", zycs.getGdid());
		map.put("p_start", start);//开始页
		map.put("p_end", end);//结束页
		map.put("p_total", StringUtils.EMPTY);//总记录
		map.put("p_cursor", StringUtils.EMPTY);//cursor
		try {
			sfdaZycsMapper.getSfdaZycsPageList(map);
			resMap.put("p_total", Integer.parseInt(map.get("p_total").toString()));
			resMap.put("p_cursor",map.get("p_cursor"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resMap;
	}
	
	/**
	 * 新增职业操守
	 */
	@Override
	public String addSfdaZycs(SfdaZycs zycs) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map.put("p_id", Identities.get32LenUUID());
		map.put("p_xmmc", StrUtil.strTransform(zycs.getXmmc()));
		map.put("p_xmlx", zycs.getXmlx());
		map.put("p_ywsj", DateUtil.getSysCurrentDateTime());// 业务时间
		map.put("p_ywdd", StrUtil.strTransform(zycs.getYwdd()));
		map.put("p_zzbm", zycs.getZzbm());
		map.put("p_spbm", zycs.getSpbm());
		map.put("p_zynr", StrUtil.strTransform(zycs.getZynr()));
		map.put("p_zysh", StrUtil.strTransform(zycs.getZysh()));
		map.put("p_yjcg", StrUtil.strTransform(zycs.getYjcg()));
		map.put("p_ywqtqk", StrUtil.strTransform(zycs.getYwqtqk()));
		map.put("p_gdid", zycs.getGdid());
		map.put("p_sfsc", 'N');
		map.put("p_kssj", zycs.getKssj());
		map.put("p_jssj", zycs.getJssj());
		map.put("p_zhgxbm", Constant.CURRENT_USER.get().getBmbm());
		map.put("p_zhgxr", Constant.CURRENT_USER.get().getGh());
		map.put("p_zhgxsj", DateUtil.getSysCurrentDateTime());
		map.put("Y", StringUtils.EMPTY);
		sfdaZycsMapper.addSfdaZycs(map);
		
		map1.put("Y", map.get("Y"));

		String sign = (String) map.get("Y");
		if ("1".equals(sign)) {
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "新增职业操守成功");
		} else {
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, "新增职业操守失败");
		}

		return Constant.GSON.toJson(map1);
	}

	/**
	 * 修改职业操守
	 */
	@Override
	public String updateSfdaZycs(SfdaZycs zycs) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_id", zycs.getId());
		map.put("p_xmmc", StrUtil.strTransform(zycs.getXmmc()));
		map.put("p_xmlx", zycs.getXmlx());
		map.put("p_ywdd", StrUtil.strTransform(zycs.getYwdd()));
		map.put("p_zzbm", zycs.getZzbm());
		map.put("p_spbm", zycs.getSpbm());
		map.put("p_zynr", StrUtil.strTransform(zycs.getZynr()));
		map.put("p_zysh", StrUtil.strTransform(zycs.getZysh()));
		map.put("p_yjcg", StrUtil.strTransform(zycs.getYjcg()));
		map.put("p_ywqtqk", StrUtil.strTransform(zycs.getYwqtqk()));
		map.put("p_kssj", zycs.getKssj());
		map.put("p_jssj", zycs.getJssj());
		map.put("p_zhgxbm", Constant.CURRENT_USER.get().getBmbm());
		map.put("p_zhgxr", Constant.CURRENT_USER.get().getGh());
		map.put("p_zhgxsj", DateUtil.getSysCurrentDateTime());
		map.put("Y", StringUtils.EMPTY);
		
		try {
			sfdaZycsMapper.updateSfdaZycs(map);
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
	 * 删除职业操守
	 */
	@Override
	public String deleteSfdaZycs(SfdaZycs zycs) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_id", zycs.getId());
		map.put("Y", StringUtils.EMPTY);
		try {
			sfdaZycsMapper.deleteSfdaZycs(map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sign = (String) map.get("Y");
		if ("1".equals(sign)) {
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "删除荣誉技能成功" );
		} else {
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, "删除荣誉技能失败");
		}

		return (String) map.get("Y");
	}

	

}
