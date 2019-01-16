package com.swx.ibms.business.system.service;

import com.swx.ibms.business.rbac.bean.BMBM;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.system.bean.Bmzdpz;
import com.swx.ibms.business.system.mapper.BmzdpzMapper;
import com.swx.ibms.common.utils.Identities;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;



/**
 * 部门指定人配置服务实现类
 * 
 * @author 李治鑫
 * @since 2017年7月14日 下午1:53:54
 */
@SuppressWarnings("all")
@Service("bmzdpzService")
public class BmzdpzServiceImpl implements BmzdpzService {

	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(BmzdpzServiceImpl.class);

	/**
	 * 部门指定人配置数据交换接口
	 */
	@Resource
	private BmzdpzMapper pzMapper;

	@Override
	public List<Bmzdpz> getPzByDwbm(Map<String, Object> map) {
		List<Bmzdpz> bmzdpzList = new ArrayList<Bmzdpz>();
		try {
			map.put("p_errmsg", StringUtils.EMPTY);
			map.put("p_cursor", StringUtils.EMPTY);
			pzMapper.getPzByDwbm(map);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("[查询人员功能配置失败！]", e);
		}

		String temp = (String) map.get("p_errmsg");
		if (StringUtils.isBlank(temp)) {
			bmzdpzList = (List<Bmzdpz>) map.get("p_cursor");
		}

		return bmzdpzList;
	}

	@Override
	public boolean isExistByDwbm(Map<String, Object> map) {
		boolean isExist = false;
		try {
			pzMapper.isExistByDwbm(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		String temp = (String) map.get("p_errmsg");
		if (StringUtils.isBlank(temp)) {
			int n = 0;
			n = (int) map.get("p_isexist");

			if (n > 0) {
				isExist = true;
			}
		}

		return isExist;
	}

	@Override
	public List<Map<String, Object>> getBmysFromJdlcBysplx(Map<String, Object> map) {
		String bmysstr = "";
		Set<String> bmysSet = new HashSet<String>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		try {
			pzMapper.getBmysBySplx(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		String temp = (String) map.get("p_errmsg");

		if (StringUtils.isEmpty(temp)) {
			bmysstr = (String) map.get("p_bmyslist");
		}

		if (StringUtils.isNotBlank(bmysstr)) {
			String[] strArray = bmysstr.split(",");

			for (String str : strArray) {
				bmysSet.add(str);
			}
		}

		if (CollectionUtils.isNotEmpty(bmysSet)) {
			for (String bmys : bmysSet) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("p_bmys", bmys);
				try {
					pzMapper.getInfoOfBmys(param);
				} catch (Exception e) {
					LOG.error(StringUtils.EMPTY, e);
				}
				Map<String, Object> result = new HashMap<String, Object>();
				String temp1 = (String) param.get("p_errmsg");
				if (StringUtils.isBlank(temp1)) {
					result.put("id", (String) param.get("p_bmbm"));
					result.put("text", (String) param.get("p_bmmc"));

					resultList.add(result);
				}
			}
		}

		return resultList;
	}

	@Override
	public Bmzdpz getZdrInfo(Map<String, Object> map) {
		Bmzdpz pzObj = new Bmzdpz();
		try {
			pzMapper.getZdrInfo(map);
		} catch (Exception e) {
			// 如果不存在配置信息，这里将会抛出游标处于关闭状态的异常，并不影响使用
			// LOG.error(StringUtils.EMPTY, e);
			throw e;
		}

		String temp = (String) map.get("p_errmsg");
		if (StringUtils.isBlank(temp)) {
			int isExist = (int) map.get("p_isexist");
			if (isExist == 1) {
				List<Bmzdpz> list = new ArrayList<Bmzdpz>();
				list = (List<Bmzdpz>) map.get("p_cursor");
				pzObj = list.get(0);
			}
		}
		return pzObj;
	}

	@Override
	public List<Map<String, Object>> getdepartlist(Map<String, Object> map) {
		List<BMBM> bmbmlist = new ArrayList<BMBM>();

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		try {
			pzMapper.getdepartlist(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		String temp = (String) map.get("p_errmsg");
		if (StringUtils.isBlank(temp)) {
			bmbmlist = (List<BMBM>) map.get("p_cursor");
			if (CollectionUtils.isNotEmpty(bmbmlist)) {
				for (BMBM bmbmObj : bmbmlist) {
					Map<String, Object> result = new HashMap<String, Object>();
					result.put("id", bmbmObj.getBmbm());
					result.put("text", bmbmObj.getBmmc());

					resultList.add(result);
				}
			}
		}
		return resultList;
	}

	@Override
	public List<Map<String, Object>> getpersonofdepart(Map<String, Object> map) {
		List<RYBM> rybmlist = new ArrayList<RYBM>();

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		try {
			pzMapper.getpersonofdepart(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		String temp = (String) map.get("p_errmsg");
		if (StringUtils.isBlank(temp)) {
			rybmlist = (List<RYBM>) map.get("p_cursor");
			if (CollectionUtils.isNotEmpty(rybmlist)) {
				for (RYBM rybmObj : rybmlist) {
					if (ObjectUtils.notEqual(null, rybmObj)) {
						Map<String, Object> result = new HashMap<String, Object>();
						result.put("id", rybmObj.getGh());
						result.put("text", rybmObj.getDlbm());

						resultList.add(result);
					}
				}
			}
		}
		return resultList;
	}

	@Override
	public PageCommon<Bmzdpz> getBmsprPageList(Map<String, Object> map) {

		PageCommon<Bmzdpz> pageCommon = new PageCommon<Bmzdpz>();

		String dwbm = map.get("dwbm").toString();
		int nowPage = Integer.parseInt(map.get("nowPage").toString());
		int pageSize = Integer.parseInt(map.get("pageSize").toString());

		int page = (nowPage - 1) * pageSize;
		pageCommon.setPageSize(page + pageSize);// 每页显示数
		page++;
		pageCommon.setNowPage(page);// 当前页

		map.put("p_dwbm", dwbm);
		map.put("p_start", pageCommon.getNowPage());
		map.put("p_end", pageCommon.getPageSize());
		map.put("p_total", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);
		pzMapper.getBmsprPageList(map);

		// 得到page对象、总记录数、
		List<Bmzdpz> list = (List<Bmzdpz>) map.get("p_cursor");
		pageCommon.setList(list);
		pageCommon.setTotalRecords(Integer.parseInt(map.get("p_total").toString()));

		return pageCommon;
	}

	@Override
	public String addBmspr(Map<String, Object> map) {
		String message = "";
		if (!Objects.isNull(map)) {
			map.put("p_id", Identities.get32LenUUID());
			map.put("p_errmsg", StringUtils.EMPTY);
			pzMapper.addBmspr(map);
			message = (String)map.get("p_errmsg");
		}
		return message;
	}

	@Override
	public String deleteBmsprById(Map<String, Object> map) {
		String message = "";
		if (!Objects.isNull(map.get("p_id"))) {
			map.put("p_errmsg", StringUtils.EMPTY);
			pzMapper.deleteBmsprById(map);
			message = (String)map.get("p_errmsg");
		}
		return message;
	}

	@Override
	public String updateBmsprById(Map<String, Object> map) {
		String message = "";
		if (!Objects.isNull(map.get("p_id"))) {
			map.put("p_errmsg", StringUtils.EMPTY);
			pzMapper.updateBmsprById(map);
			message = (String)map.get("p_errmsg");
		}
		return message;
	}

	@Override
	public Bmzdpz getBmsprById(Map<String, Object> map) {
		Bmzdpz bmzdpz = null;
		if (!Objects.isNull(map.get("p_id"))) {
			map.put("p_cursor", StringUtils.EMPTY);
			pzMapper.getBmsprById(map);
			List<Bmzdpz> bmzdpzList = (List<Bmzdpz>)map.get("p_cursor");
			if (bmzdpzList.size()>0) {
				bmzdpz = bmzdpzList.get(0);
			}
		}
		return bmzdpz;
	}

	@Override
	public List<Map<String, Object>> getBmListByDwbm(Map<String, Object> map) {
		List<Map<String, Object>> bmList = null;
		if (!Objects.isNull(map)) {
			map.put("p_cursor", StringUtils.EMPTY);
			pzMapper.getBmListByDwbm(map);
			bmList = (List<Map<String, Object>>)map.get("p_cursor");
		}
		return bmList;
	}

	@Override
	public List<Map<String, Object>> getRyListByBm(Map<String, Object> map) {
		List<Map<String, Object>> ryList = null;
		if (!Objects.isNull(map)) {
			map.put("p_cursor", StringUtils.EMPTY);
			pzMapper.getRyListByBm(map);
			ryList = (List<Map<String, Object>>)map.get("p_cursor");
		}
		return ryList;
	}

}
