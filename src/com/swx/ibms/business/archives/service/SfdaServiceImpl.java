package com.swx.ibms.business.archives.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swx.ibms.business.archives.bean.Sfda;
import com.swx.ibms.business.archives.bean.XtSfdaDaQuery;
import com.swx.ibms.business.archives.mapper.SfdaMapper;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.common.bean.EasyUIDatagrid;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * 司法档案内容实现层
 *
 * @author 李佳
 * @date: 2017年02月25日
 */
@Service("SfdaService")
@SuppressWarnings("all")
public class SfdaServiceImpl implements SfdaService {

	/**
	 * 司法档案内容Mapper接口
	 *
	 * @author 李佳
	 */
	@Resource
	private SfdaMapper sfjnMapper;

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;

	@Override
	public int selectGdidSfdaCount(String gdid, String dalx) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_gdid", gdid);
		map.put("p_dalx", dalx);
		map.put("p_count", 0);
		sfjnMapper.selectGdidSfdaCount(map);
		return (int) map.get("p_count");
	}

	@Override
	public String insertSfjn(Sfda sfjn) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map.put("lb", sfjn.getLb());
		map.put("ms", sfjn.getMs());
		map.put("cjr", sfjn.getCjr());
		map.put("dalx", sfjn.getDalx());
		map.put("gdId", sfjn.getGdId());
		map.put("Y", "");
		map.put("id", "");
		sfjnMapper.insert(map);
		map1.put("Y", map.get("Y"));
		map1.put("id", map.get("id"));

		String strLb = "";
		if ("1".equals(sfjn.getLb())) {
			strLb = "荣誉技能";
		} else if ("2".equals(sfjn.getLb())) {
			strLb = "司法责任";
		} else if ("3".equals(sfjn.getLb())) {
			strLb = "职业操守";
		} else if ("4".equals(sfjn.getLb())) {
			strLb = "其他档案";
		}
		String sign = (String) map.get("Y");
		if ("1".equals(sign)) {
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "新增" + strLb);
		} else {
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, "新增" + strLb + "失败");
		}

		return Constant.GSON.toJson(map1);

	}

	@Override
	public String updateSfjn(Sfda sfjn) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", sfjn.getId());
		map.put("lb", sfjn.getLb());
		map.put("ms", sfjn.getMs());
		map.put("cjr", sfjn.getCjr());
		map.put("cjsj", sfjn.getCjsj());
		map.put("dalx", sfjn.getDalx());
		map.put("gdId", sfjn.getGdId());
		map.put("Y", "");
		sfjnMapper.update(map);

		String strLb = "";
		if ("1".equals(sfjn.getLb())) {
			strLb = "荣誉技能";
		} else if ("2".equals(sfjn.getLb())) {
			strLb = "司法责任";
		} else if ("3".equals(sfjn.getLb())) {
			strLb = "职业操守";
		} else if ("4".equals(sfjn.getLb())) {
			strLb = "其他档案";
		}
		String sign = (String) map.get("Y");
		if ("1".equals(sign)) {
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "修改" + strLb);
		} else {
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, "修改" + strLb + "失败");
		}

		return (String) map.get("Y");
	}

	@Override
	public Map getSfjnList(Sfda sfjn, int bottom, int top) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dalx", sfjn.getDalx());
		map.put("gdid", sfjn.getGdId());
		map.put("top", top);
		map.put("bottom", bottom);
		map.put("sum", null);
		map.put("Y", null);

		sfjnMapper.getSfjnList(map);
		return map;
	}

	@Override
	public String deleteSfjn(Sfda sfjn) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("id", sfjn.getId());

		map.put("Y", "");
		sfjnMapper.delete(map);

		String strLb = "";
		if ("1".equals(sfjn.getLb())) {
			strLb = "荣誉技能";
		} else if ("2".equals(sfjn.getLb())) {
			strLb = "司法责任";
		} else if ("3".equals(sfjn.getLb())) {
			strLb = "职业操守";
		} else if ("4".equals(sfjn.getLb())) {
			strLb = "其他档案";
		}
		String sign = (String) map.get("Y");
		if ("1".equals(sign)) {
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "删除" + strLb);
		} else {
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, "删除" + strLb + "失败");
		}

		return (String) map.get("Y");
	}

	@Override
	public Map countSfda(String gdid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gdid", gdid);
		map.put("count", "");
		sfjnMapper.countSfda(map);
		return map;
	}

	@Override
	public List<Map<String, Object>> getSfdaCountByDwOrBm(String dwbm, String gh, String paramYear, String paramDwjb)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> signMap = new HashMap<String, Object>();

		StringBuilder pBmysSql = new StringBuilder();

		Integer sign = (Integer) this.surePersonDwRole(dwbm, gh, paramDwjb).get("ryjsSign");
		String bmys = (String) this.surePersonDwRole(dwbm, gh, paramDwjb).get("bmys");// 取出部门领导人所处部门的部门映射

		if (StringUtils.contains(bmys, ",")) {
			String[] bmysArrTempA = bmys.split(",");
			//数组去空
			String[] bmysArrTempB = this.getNotEmptyArr(bmysArrTempA);
			//数组去重复 返回新数组
			 String[] bmysArr = this.getNewArr(bmysArrTempB);

			if (bmysArr.length > 0) {
				pBmysSql.append(" and bm.bmys in (");
				for (int i = 0; i < bmysArr.length; i++) {
					// 这儿的bm是部门表的别称 如果sql语句更改，这儿也必须修改；切记
					if(bmysArr.length-1==i){
						pBmysSql.append("'"+bmysArr[i]+"'");
					}else{
						pBmysSql.append("'"+bmysArr[i]+"',");
					}
				}
				pBmysSql.append(")");
			}
		}

		map = this.returnNewMapByJsAndDw(paramDwjb, sign, bmys, pBmysSql.toString(), dwbm, gh, paramYear);


		signMap.put("sign", sign);
		map.put("p_cursor", StringUtils.EMPTY);
		sfjnMapper.getSfdaCountByDwOrBm(map);

		List<Map<String, Object>> resList = (List<Map<String, Object>>) map.get("p_cursor");
		resList.add(signMap);
		// 获取当前年份以及后十年 eg:2017、2016、2015等 /////暂未使用
		// Integer recentYear = DateUtil.getYear(new Date());
		// for (int i = 1; i <= Constant.NUM_10; i++) {
		// tempMap.put("year"+i, recentYear--);
		// }
		// resMap.put("recentYear", tempMap);
		// resList.add(resMap);
		return resList;
	}

	@Override
	public List<Map<String, Object>> getRyjnSfzrCount(String dwbm, String gh, String paramYear, String paramDwjb)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> signMap = new HashMap<String, Object>();

		StringBuilder pBmysSql = new StringBuilder();

		Integer sign = (Integer) this.surePersonDwRole(dwbm, gh, paramDwjb).get("ryjsSign");
		String bmys = (String) this.surePersonDwRole(dwbm, gh, paramDwjb).get("bmys");// 取出部门领导人所处部门的部门映射

		if (StringUtils.contains(bmys, ",")) {
			String[] bmysArrTempA = bmys.split(",");
			//数组去空
			String[] bmysArrTempB = this.getNotEmptyArr(bmysArrTempA);
			//数组去重复 返回新数组
			 String[] bmysArr = this.getNewArr(bmysArrTempB);
			if (bmysArr.length > 0) {
				pBmysSql.append(" and bm.bmys in (");
				for (int i = 0; i < bmysArr.length; i++) {
					// 这儿的bm是部门表的别称 如果sql语句更改，这儿也必须修改；切记
					if(bmysArr.length-1==i){
						pBmysSql.append("'"+bmysArr[i]+"'");
					}else{
						pBmysSql.append("'"+bmysArr[i]+"',");
					}
					// .append(" AND da.ssrdwbm IN( SELECT dwbm from XT_ZZJG_DWBM_tyyw START WITH dwbm = '"+dwbm+"' AND sfsc='N' CONNECT BY PRIOR dwbm = fdwbm)");
				}
				pBmysSql.append(")");
			}
		}

		map = this.returnNewMapByJsAndDw(paramDwjb, sign, bmys, pBmysSql.toString(), dwbm, gh, paramYear);

		map.put("p_cursor", StringUtils.EMPTY);
		signMap.put("sign", sign);

		sfjnMapper.getRyjnSfzrCount(map);

		List<Map<String, Object>> resList = (List<Map<String, Object>>) map.get("p_cursor");
		resList.add(signMap);

		return resList;
	}

	/**
	 * 根据下述几个参数进行查询此人的角色信息 并确定他是否是省院、院领导、部门领导、一般职员、案管等
	 *
	 * @param dwbm
	 *            单位编码
	 * @param gh
	 *            工号
	 * @param dwjb
	 *            单位级别
	 * @throws Exception
	 * @return 角色标示
	 */
	private Map<String, Object> surePersonDwRole(String dwbm, String gh, String dwjb) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> resMap = new HashMap<String, Object>();
		map.put("p_dwbm", dwbm);
		map.put("p_gh", gh);
		map.put("p_cursor", StringUtils.EMPTY);
		sfjnMapper.getRyxxBydwAndGh(map);

		List<Map<String, Object>> ryxxlist = (List<Map<String, Object>>) map.get("p_cursor");

		if (CollectionUtils.isNotEmpty(ryxxlist)) {
			resMap = this.sureLdOrNot(ryxxlist);
			return resMap;
		} else {
			resMap.put("ryjsSign", Constant.NUM_7);
			return resMap;
		}

	}

	/**
	 * 根据此人的角色名称、部门映射决定他是否为领导等 角色名称:对应标示
	 * 院领导:1、部门领导:2、纪检:3、考评委员会:4、案管:5、人事部:6、其他:7
	 *
	 * @param ryxxlist
	 *            人员信息
	 * @return 角色标示
	 */
	private Map<String, Object> sureLdOrNot(List<Map<String, Object>> ryxxlist) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer ryjsSign = 0; // 领导标示
		String bmysTempA = StringUtils.EMPTY; // 部门映射临时字符串
		String bmysTempB = StringUtils.EMPTY; // 部门映射临时字符串
		String jsmcTemp = StringUtils.EMPTY; // 角色编码临时字符串
		String bmys = StringUtils.EMPTY; // 部门映射字符串
		String jsmc = StringUtils.EMPTY; // 角色编码字符串

		for (int i = 0; i < ryxxlist.size(); i++) {

			jsmcTemp += ryxxlist.get(i).get("jsmc").toString();
			jsmcTemp += ",";

			if (StringUtils.isNoneBlank(MapUtils.getString(ryxxlist.get(i), "bmys"))) {
				bmysTempA += ryxxlist.get(i).get("bmys").toString();
				bmysTempA += ",";
				if (StringUtils.contains(bmysTempA, ";")) {
					bmysTempB = bmysTempA.replace(";", ",");
					bmysTempB += ",";
				} else {
					bmysTempB += bmysTempA;
					bmysTempB += ",";
				}
			}

		}

		// bmysTempB = bmysTempA;
		bmys = bmysTempB.substring(0, bmysTempB.length() - 1).trim();
		jsmc = jsmcTemp.substring(0, jsmcTemp.length() - 1).trim();
		map.put("bmys", bmys);

		if (bmys.contains("0000")) { // 院领导 对应标示：1
			if (StringUtils.contains(jsmc, "长")) {
				ryjsSign = Constant.NUM_1;
			} else {
				ryjsSign = Constant.NUM_7;
			}
		} else if (bmys.contains("0101")) { // 纪检 对应标示：3
			if (StringUtils.contains(jsmc, "长") || StringUtils.contains(jsmc, "巡视员") || StringUtils.contains(jsmc, "委员")
					|| StringUtils.contains(jsmc, "主任") || StringUtils.contains(jsmc, "副主任")) {
				ryjsSign = Constant.NUM_2;
			} else {
				ryjsSign = Constant.NUM_3;
			}
		} else if (bmys.contains("0102")) { // 考评委员会 对应标示：4
			if (StringUtils.contains(jsmc, "长") || StringUtils.contains(jsmc, "巡视员") || StringUtils.contains(jsmc, "委员")
					|| StringUtils.contains(jsmc, "主任") || StringUtils.contains(jsmc, "副主任")) {
				ryjsSign = Constant.NUM_2;
			} else {
				ryjsSign = Constant.NUM_4;
			}
		} else if (bmys.contains("1100")) { // 案管 对应标示：5
			if (StringUtils.contains(jsmc, "长") || StringUtils.contains(jsmc, "巡视员") || StringUtils.contains(jsmc, "委员")
					|| StringUtils.contains(jsmc, "主任") || StringUtils.contains(jsmc, "副主任")) {
				ryjsSign = Constant.NUM_2;
			} else {
				ryjsSign = Constant.NUM_5;
			}
		} else if (bmys.contains("9100")) { // 人事部 对应标示：6
			if (StringUtils.contains(jsmc, "长") || StringUtils.contains(jsmc, "巡视员") || StringUtils.contains(jsmc, "委员")
					|| StringUtils.contains(jsmc, "主任") || StringUtils.contains(jsmc, "副主任")) {
				ryjsSign = Constant.NUM_2;
			} else {
				ryjsSign = Constant.NUM_6;
			}
		} else {// 一般职工 对应标示：7
			if (StringUtils.containsOnly(jsmc, "检察长") || StringUtils.contains(jsmc, "副检察长")) {
				ryjsSign = Constant.NUM_1;
			} else if (StringUtils.contains(jsmc, "长") || StringUtils.contains(jsmc, "巡视员")
					|| StringUtils.contains(jsmc, "委员") || StringUtils.contains(jsmc, "主任")
					|| StringUtils.contains(jsmc, "副主任")) {
				ryjsSign = Constant.NUM_2;
			} else {
				ryjsSign = Constant.NUM_7;
			}
		}
		map.put("ryjsSign", ryjsSign);
		return map;
	};

	/**
	 * 重新组装参数集合
	 *
	 * @param pLdStr
	 *            领导标示字符串
	 * @param pYear
	 *            年份
	 * @param pDwbm
	 *            单位编码
	 * @param pBmys
	 *            部门映射
	 * @param pGh
	 *            工号
	 * @param pBmysSql
	 *            部门映射字符串
	 * @param map
	 * @return 参数集合
	 */
	private Map<String, Object> changeMapParam(String pLdStr, String pYear, String pDwbm, String pBmys, String pGh,
			String pBmysSql, Map<String, Object> map) {
		map.put("p_ldStr", pLdStr);
		map.put("p_year", pYear);
		map.put("p_dwbm", pDwbm);
		map.put("p_bmys", pBmys);
		map.put("p_gh", pGh);
		map.put("p_bmysSql", pBmysSql);
		return map;
	}

	@Override
	public List<Map<String, Object>> getRyWhcdByDwGh(String dwbm, String gh, String paramYear, String paramDwjb)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> signMap = new HashMap<String, Object>();

		// 查询此人是否为领导\院领导\一般员工
		// * 角色名称:对应标示
		// * 院领导:1、部门领导:2、纪检:3、考评委员会:4、案管:5、人事部:6、其他:7
		Integer sign = (Integer) this.surePersonDwRole(dwbm, gh, paramDwjb).get("ryjsSign");
		String bmys = (String) this.surePersonDwRole(dwbm, gh, paramDwjb).get("bmys");// 取出部门领导人所处部门的部门映射--暂时未用

		map = this.returnNewMapByJsAndDw(paramDwjb, sign, bmys, StringUtils.EMPTY, dwbm, gh, paramYear);

		map.put("p_cursor", StringUtils.EMPTY);
		signMap.put("sign", sign);

		sfjnMapper.getRyWhcdByDwGh(map);

		List<Map<String, Object>> resList = (List<Map<String, Object>>) map.get("p_cursor");
		resList.add(signMap);

		return resList;
	}

	private Map<String, Object> returnNewMapByJsAndDw(String paramDwjb, Integer sign, String bmys,
			String pBmysSql, String dwbm, String gh, String paramYear) {
		Map<String, Object> map = new HashMap<String,Object>();
		// 查询此人是否为领导\院领导\一般员工
		// * 角色名称:对应标示
		// * 院领导:1、部门领导:2、纪检:3、考评委员会:4、案管:5、人事部:6、其他:7
		String pBmys = StringUtils.EMPTY;// 部门映射字符串
		String pLdStr = StringUtils.EMPTY;// 领导字符串
		String pYear = StringUtils.EMPTY;// 年份
		String pGh = StringUtils.EMPTY;// 工号

		if (Constant.NUM_2 == Integer.parseInt(paramDwjb)) {// 省院---院领导--部门领导--一般员工
			if (Constant.NUM_1 == sign || Constant.NUM_3 == sign || Constant.NUM_5 == sign) {
				pLdStr = "ld";
				pYear = paramYear;
				this.changeMapParam(pLdStr, pYear, dwbm, pBmys, pGh, StringUtils.EMPTY, map);
			} else if (Constant.NUM_2 == sign) {
				pLdStr = "bmld";
				pYear = paramYear;
				pBmys = bmys.split(",")[0];
				this.changeMapParam(pLdStr, pYear, dwbm, pBmys, pGh, pBmysSql.toString(), map);
			} else {
				this.changeMapParam(pLdStr, pYear, dwbm, pBmys, gh, StringUtils.EMPTY, map);
			}
		} else if (Constant.NUM_3 == Integer.parseInt(paramDwjb)) {// 市院
			if (Constant.NUM_1 == sign || Constant.NUM_3 == sign || Constant.NUM_5 == sign) {
				pLdStr = "ld";
				pYear = paramYear;
				this.changeMapParam(pLdStr, pYear, dwbm, pBmys, pGh, StringUtils.EMPTY, map);
			} else if (Constant.NUM_2 == sign) {
				pLdStr = "bmld";
				pYear = paramYear;
				pBmys = bmys.split(",")[0];
				this.changeMapParam(pLdStr, pYear, dwbm, pBmys, pGh, pBmysSql.toString(), map);
			} else {
				this.changeMapParam(pLdStr, pYear, dwbm, pBmys, gh, StringUtils.EMPTY, map);
			}
		} else if (Constant.NUM_4 == Integer.parseInt(paramDwjb)) {// 区院
			if (Constant.NUM_1 == sign || Constant.NUM_3 == sign || Constant.NUM_5 == sign) {
				pLdStr = "ld";
				pYear = paramYear;
				this.changeMapParam(pLdStr, pYear, dwbm, pBmys, pGh, StringUtils.EMPTY, map);
			} else if (Constant.NUM_2 == sign) {
				pLdStr = "bmld";
				pYear = paramYear;
				pBmys = bmys.split(",")[0];
				this.changeMapParam(pLdStr, pYear, dwbm, pBmys, pGh, pBmysSql.toString(), map);
			} else {
				this.changeMapParam(pLdStr, pYear, dwbm, pBmys, gh, StringUtils.EMPTY, map);
			}
		}
		return map;

	}


	private String[] getNewArr(String[] bmysArrTemp) {
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < bmysArrTemp.length; i++) {
			set.add(bmysArrTemp[i]);
		}
		String[] strArr = set.toArray(new String[set.size()]);
		return strArr;
	}


	private String[] getNotEmptyArr(String[] bmysArrTempA) {
		String[] strArr = new String[]{};
		List tmp = new ArrayList();
		for (String str : bmysArrTempA) {
			if (str!=null&&str.length()!=0) {
				tmp.add(str);
			}
		}
		strArr = (String[]) tmp.toArray(new String[tmp.size()]);
		return strArr;
	}

	@Override
	public EasyUIDatagrid<Map<String, Object>> selectOldData(XtSfdaDaQuery query) throws Exception {
		Page<Sfda> pageObject = PageHelper.startPage(query.getPage(), query.getRows());
		List<Map<String, Object>> list = sfjnMapper.selectOldData(query);
		return EasyUIDatagrid.create(list, pageObject.getTotal());
	}
}
