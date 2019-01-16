package com.swx.ibms.business.cases.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swx.ibms.business.cases.mapper.WssptzMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
*@author:徐武林
*@date:2018年5月14日上午11:28:45
*@Description:文书审批台账
*/
@SuppressWarnings("all")
@Service("WssptzService")
public class WssptzServiceImpl implements WssptzService {

	@Autowired
	private WssptzMapper wssptzMapper;
	
	private List<String> bmsahList = new ArrayList<>();
	private List<String> tysahList = new ArrayList<>();
	
	@Override
	public List<String> getBmsah(String dwbm, String gh, String ksrq, String jsrq) throws Exception {
		try {
			bmsahList = wssptzMapper.getBmsah(dwbm, gh, ksrq, jsrq);
			return bmsahList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<String> getTysah(String dwbm, String gh, String ksrq, String jsrq) throws Exception {
		try {
			tysahList = wssptzMapper.getTysah(dwbm, gh, ksrq, jsrq);
			return tysahList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Map<String, Object> getALLWssptz(String dwbm, String gh, int page, int pageSize)
			throws Exception {
		Map<String, Object> map = new HashMap<>();
		List list = new ArrayList<>();
		try {
			Page wssptzPager = PageHelper.startPage(page, pageSize);
			list = wssptzMapper.selectALLWssptz(dwbm,gh);
			map.put("rows", list);
			map.put("total", wssptzPager.getTotal());
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Map<String, Object> getWsDetail(String dwbm, String gh, int page, int pageSize,
										   String ksrq, String jsrq,String bmsah, String tysah) throws Exception {
//		int currentYear = DateUtil.getYear(new Date());

		Map<String, Object> map = new HashMap<>();
		List list = new ArrayList<>();
		try {
			Page wsDetailPager = PageHelper.startPage(page, pageSize);
			list = wssptzMapper.selectWsDetail(dwbm, gh, ksrq, jsrq, bmsah, tysah);
			map.put("total", wsDetailPager.getTotal());
			map.put("rows", list);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getAjmcByBmsahAndTysah(String bmsah, String tysah) throws Exception {

		String ajmc = "";
		try {
			ajmc = wssptzMapper.getAjmcByBmsahAndTysah(bmsah,tysah);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ajmc;
	}

	@Override
	public Map<String, Object> getSpaj(String dwbm, String gh, int page, int pageSize, String ksrq, String jsrq) throws Exception {
		Map<String, Object> map = new HashMap<>();
		List<String> bmsahs = new ArrayList<>();
		List<Map<String, Object>> ajInfo = new ArrayList<>();
		try {
			// 审批的案件的部门受案号集合
			bmsahs = wssptzMapper.selectSpajBmsahs(dwbm, gh, ksrq, jsrq);

			// 如果bmsahs的大小超过1000，则Oracle的in表达式将会出错：列表中的最大表达式数为1000
			// 故将bmsahs进行拆分
			List<List<String>> bmsahList = new ArrayList<>();
			if (bmsahs != null && bmsahs.size() > 0) {
				bmsahList = splistList(bmsahs, 999);
			}

			// 根据部门受案号查询案件
			Page wsDetailPager = PageHelper.startPage(page, pageSize);
			if (bmsahList != null && bmsahList.size() > 0) {
				ajInfo = wssptzMapper.selectAjByBmsahs(bmsahList, dwbm, gh);
			}

			map.put("total", wsDetailPager.getTotal());
			map.put("rows", ajInfo);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 按指定大小，分割集合将集合按规定个数分为n个部分
	 * @param list
	 * @param len
	 * @return
	 */
	private static List<List<String>> splistList(List<String> list, int len) {
		if (list == null || list.size() == 0 || len < 1) {
			return null;
		}

		List<List<String>> result = new ArrayList<>();
		int size = list.size();
		int count = (size + len - 1) / len;
		for (int i = 0; i < count; i++) {
			List<String> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
			result.add(subList);
		}
		return result;
	}

}
