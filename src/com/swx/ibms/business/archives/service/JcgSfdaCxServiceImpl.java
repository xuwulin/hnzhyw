package com.swx.ibms.business.archives.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swx.ibms.business.archives.bean.DAGZ;
import com.swx.ibms.business.archives.bean.Gsjl;
import com.swx.ibms.business.archives.bean.JcgSfdaCx;
import com.swx.ibms.business.archives.mapper.DAGZMapper;
import com.swx.ibms.business.archives.mapper.JcgSfdaCxMapper;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.JSBM;
import com.swx.ibms.business.rbac.service.LoginService;
import com.swx.ibms.business.system.bean.Fgld;
import com.swx.ibms.business.system.bean.bmysbm;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 朱春雨
 *
 */
@Service("JcgSfdaCxService")
public class JcgSfdaCxServiceImpl implements JcgSfdaCxService {

	/**
	 * 日志记录服务
	 */
	@Autowired
	private LogService logService;

	/**
	 * 司法档案查询 mapper
	 */
	@Autowired
	private JcgSfdaCxMapper jcgSfdaCxMapper;
	/**
	 * 档案归总mapper
	 */
	@Autowired
	private DAGZMapper dagzMapper;
	/**
	 * 日志记录服务
	 */
//	@Resource
//	private LogService logservice;

//	@Autowired
//	private JcgSfdaCxService jcgSfdaCxService;

	@Autowired
	private LoginService loginService;

	/**
	 * 分页显示个数
	 */
	private static final int SHOWNUMBERS = 20;

	/*
	 * (non-Javadoc)通过归档id判断splcsl里面是否有待审批
	 *
	 * @see com.swx.zhyw.service.JcgSfdaCxService#sfyDsp(java.lang.String)
	 */
	@Override
	public String sfyDsp(String spstid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_spstid", spstid);
		map.put("y", null);
		String y = "0";
		try {
			dagzMapper.sfyDsp(map);
			y = (String) map.get("y");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return y;
	}

	/**
	 * 根据审批类型和档案归总id来判断封存是否显示
	 *
	 * @see com.swx.zhyw.service.JcgSfdaCxService#dasfsp(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public boolean dasfsp(String spstid, String splx) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_spstid", spstid);
		map.put("p_splx", splx);
		map.put("p_spzt", null);
		boolean y = false;
		try {
			jcgSfdaCxMapper.daspZxSpzt(map);
			if ("4".equals((String) map.get("p_spzt"))) {
				y = true;
			} else if ("4".equals(splx)
					&& ("4".equals((String) map.get("p_spzt")) || "N".equals((String) map.get("p_spzt")))) {
				y = true;
			} else if ("7".equals(splx)
					&& ("N".equals((String) map.get("p_spzt")) || ("5".equals((String) map.get("p_spzt"))))) {
				y = true;
			}
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "判断审批类型为：" + splx + ",是否在审批过程中");
		} catch (Exception e) {
			y = false;
			e.printStackTrace();
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, ExceptionUtils.getStackTrace(e));
		}
		return y;
	}

	/*
	 * 档案归总封存
	 *
	 * @see com.swx.zhyw.service.JcgSfdaCxService#dagzFc(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean dagzFc(String id, String sffc) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_id", id); // id
		map.put("p_sffc", sffc); // 是否封存
		boolean y = false;
		try {
			jcgSfdaCxMapper.dagzFc(map);
			y = true;
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "档案封存");
		} catch (Exception e) {
			e.printStackTrace();
			y = false;
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, ExceptionUtils.getStackTrace(e));
		}
		return y;
	}

	/*
	 * 查询档案的结果
	 */
	@Override
	public Map<String, Object> jcgSfdaCx(String sfgs, String sffc, String sfgd, String ssrdwbm, String dlrdwbm, String dlr,
										 String ssrmc, String qx, String bmbm, String kssj, String jssj, int page) {

		if(kssj.equals("") && jssj.equals("")){
			kssj = "1000-01";
			jssj = "3000-12";
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cursor", null); // 游标
		map.put("p_count", null); // 总量
		map.put("p_sfgs", sfgs);
		map.put("p_sffc", sffc);
		map.put("p_sfgd", sfgd);
		map.put("p_ssrdwbm", ssrdwbm);
		map.put("p_dlrdwbm", dlrdwbm);
		map.put("p_dlr", dlr);
		map.put("p_ssr", ssrmc);
		map.put("p_qx", qx);
		map.put("p_bmbm", bmbm);
		map.put("p_kssj", kssj);
		map.put("p_jssj", jssj);
		int pbottom = (page - 1) * SHOWNUMBERS + 1;
		int ptop = page * SHOWNUMBERS;
		map.put("p_top", Integer.toString(ptop));
		map.put("p_bottom", Integer.toString(pbottom));

		try{
			jcgSfdaCxMapper.jcgSfdaCx(map); // 查询自己创建的gdid
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map resultMap = new HashMap();
		resultMap.put("data", (List<JcgSfdaCx>) map.get("cursor"));
		resultMap.put("count", map.get("p_count"));
		return resultMap;

	}

	/**
	 * 发起公示 author 朱春雨
	 *
	 *
	 * @return Map
	 *
	 */
	@Override
	public Map gs(Map map) {
		map.put("Y", ""); // 接收返回参数1代表成功 0代表失败
		jcgSfdaCxMapper.gs(map);
		return map;
	}

	@Override
	public List showGs(Map map) {
		// TODO Auto-generated method stub
		jcgSfdaCxMapper.show_gs(map);
		List list = (List) map.get("cursor");
		return list;
	}

	@Override
	public Map<String, Object> showGsOfIndex(String dwbm) {

		Map<String, Object> resMap = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();

		try {
			list = jcgSfdaCxMapper.showGsOfIndex(dwbm);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resMap.put("rows", list);
		return resMap;
	}

	/*
	 * (non-Javadoc) 通过部门映射和单位编码找部门编码
	 */
	@Override
	public String selectBmysBm(String dwbm, String bmys) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<bmysbm> list = new ArrayList();
		map.put("p_dwbm", dwbm);
		map.put("p_bmys", "(" + bmys + ")");
		try {
			jcgSfdaCxMapper.selectBmysBm(map);
			list.addAll((List<bmysbm>) map.get("p_cursor"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if (list.get(i) != null && list.get(j) != null) {
					if ((list.get(j).getBmbm()).equals(list.get(i).getBmbm())) {
						list.remove(j);
					}
				}
			}
		}
		String bmbm = "''";
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				bmbm = bmbm + "," + list.get(i).getBmbm();
			}
		}
		return bmbm;
	}

	/*
	 * (non-Javadoc) 通过单位和部门编码找部门映射
	 */
	@Override
	public String selectDwbmBmys(String dwbm, String bmbm) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_dwbm", dwbm);
		map.put("p_bmbm", bmbm);
		map.put("p_cursor", null);
		jcgSfdaCxMapper.selectBmBmys(map);
		List<bmysbm> list = (List<bmysbm>) map.get("p_cursor");
		String bmyss = "''";
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				String bmys = list.get(i).getBmys();
				bmyss = bmyss + "," + "'" + bmys + "'";
				if (bmys.contains(",")) {
					String[] bmysArray = bmys.split(",");
					for (int j = 0; j < bmysArray.length; j++) {
						bmyss = bmyss + "," + "'" + bmysArray[j] + "'";
					}
				}
			}
		}
		return bmyss;
	}

	@Override
	public int qxgs(Map<String, Object> paramsmap) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_gdid", paramsmap.get("gdid"));
		map.put("p_gsxx", paramsmap.get("gsxx"));
		map.put("p_errmsg", StringUtils.EMPTY);
		jcgSfdaCxMapper.qxgs(map);
		String errmsg = (String) map.get("p_errmsg");
		if (StringUtils.isEmpty(errmsg)) {
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "取消公示成功！");
			return 1;
		} else {
			// 记录日志
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, "取消公示失败，错误信息：" + errmsg);
			return 0;
		}

	}

	@Override
	public void addGsjl(Gsjl gsjl) {
		//1、向公示详情表【YX_SFDA_GSJL】添加一条记录
		try {
			jcgSfdaCxMapper.addGsjl(gsjl);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//2、修改档案表【YX_SFDA_DAGZ】对应记录的公示信息  --- 1 已公示   2 未公示
		try {
			jcgSfdaCxMapper.updatedagzgsxx(gsjl.getDagzid(),gsjl.getCzlx(), gsjl.getGsxx());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map cxFgBm(String dlrdwbm, String dlr) {
		Map map = new HashMap();
		try {
			List<Fgld> fgld = jcgSfdaCxMapper.cxFgBm(dlrdwbm, dlr);
			String bmbms = "";
			for (int i = 0; i < fgld.size(); i++) {
				if (fgld.get(i) != null) {
					if (bmbms.equals("")) {
						bmbms = bmbms + fgld.get(i).getBmbm();
					} else {
						bmbms = bmbms + "," + fgld.get(i).getBmbm();
					}
				}
			}
			map.put("count", fgld.size());
			map.put("fgbmbm", bmbms);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> getDaByDG(String dwbm, String gh, String sfgs, String sffc, String sfgd, String kssj, String jssj) {
		Map<String, Object> map = new HashMap<>();

		if(kssj.equals("") && jssj.equals("")){
			kssj = "1000-01";
			jssj = "3000-12";
		}

		map.put("cursor", null); // 游标
		map.put("p_sfgs", sfgs);
		map.put("p_sffc", sffc);
		map.put("p_sfgd", sfgd);
		map.put("p_dwbm", dwbm);
		map.put("p_gh", gh);
		map.put("p_kssj", kssj);
		map.put("p_jssj", jssj);

		try {
			jcgSfdaCxMapper.getDaByDG(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("data", (List) map.get("p_cursor"));
		return resultMap;

	}

	@Override
	public Map<String, Object> getFileInfo(String dwbm, String gh, String kssj, String jssj, String tjnf) {
		Map<String, Object> map = new HashMap<>();
		try {
			DAGZ fileInfo = jcgSfdaCxMapper.getFileInfo(dwbm, gh, kssj, jssj, tjnf);
			map.put("data", fileInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> queryArchivesByCondition(String loger_dwbm, String loger_gh,
														String loger_bmbm, List<String> loger_bmyslist,
														List<String> loger_bmjs, String query_dwbm,
														String query_sfgs, String query_sfgd,
														String query_sffc,
														String query_kssj, String query_jssj,
														String query_name, Integer page) {
		Map<String, Object> resultMap = new HashMap<>();
		List<Map<String, Object>> archivesList = null;

		// 人员角色，自己定义人员角色
		// 未用存入session中的ryjs
		List<String> ryjs = new ArrayList<String>();

		// 查询登录人的角色,如果部门映射为空则可以用角色名称判断查询权限，用法参照LoginController.java
		// 不清楚正式环境中部门映射的情况
		List<JSBM> loger_jsbm = null;

		String bmbm = "";
		String bmyss = "";
		String[] bmbmArr = null;
		List<String> bmbmList = new ArrayList<>();

		// 每页显示条数
		Integer rows = 20;

		// 查询是否有分管部门以确定是否是分管领导
		int fgcount = (int)this.cxFgBm(loger_dwbm,loger_gh).get("count");

		if (fgcount > 0) {
			// 分管领导
			ryjs.add("2");
		}

		for (int i = 0; i < loger_bmyslist.size(); i++) {
			// 临时部门映射
			String bmysTemp = loger_bmyslist.get(i);
			// 部门角色：460000,0134,001（单位编码，部门编码，角色编码）
			String[] bmjsStrArray = loger_bmjs.get(i).split(",");
			// 角色编码
			String jsbm = bmjsStrArray[2];

			// 没有部门映射的部门
			if (bmysTemp == null || bmysTemp.equals("")) {
				// 没有部门映射的先根据loger_jsbm中的角色名称来判断
				try {
					loger_jsbm = loginService.getgetBmJs(loger_dwbm, loger_gh);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (loger_jsbm.size() > 0) {
					for (int j = 0; j < loger_jsbm.size(); j++) {
						String jsmc = ((JSBM) loger_jsbm.get(j)).getJsmc();
						if (StringUtils.contains(jsmc, "检察长")) {
							// 检察长
							ryjs.add("1");
						} else if (StringUtils.contains(jsmc, "处长")
								|| StringUtils.contains(jsmc, "副处长")
								|| StringUtils.contains(jsmc, "局长")
								|| StringUtils.contains(jsmc, "副局长")
								|| StringUtils.contains(jsmc, "部长")
								|| StringUtils.contains(jsmc, "副部长")) {
							// 负责人
							ryjs.add("3");
						}
					}
				} else {
					ryjs.add("5");// 普通人
				}
			} else if (bmysTemp.contains("0102")) {
				ryjs.add("8");// 考评委员会
			} else if (bmysTemp.contains("9100")) {
				ryjs.add("7");// 人事部（原为0，暂时改成和案管一样），前台获取到 人事部人员角色为0，这里之所以为7是为了方便查询（和案管一样能够查询全院）
			} else if (bmysTemp.contains("4001")) {
				ryjs.add("7"); // 政治部 前台获取到的 政治部人员角色为 4 这里之所以为7是为了方便查询（和案管一样能够查询全院）
			} else if (bmysTemp.contains("0101")) {
				ryjs.add("6");// 纪检
			} else if (bmysTemp.contains("1100")) {
				ryjs.add("7");// 案管
			} else if (bmysTemp.contains("0000")) { // 院领导
				if (jsbm.equals("001")) { // 部门映射为："0000"且人员角色为"001"，则为检察长，人员角色为"002"，则为副检察长，人员角色为"003"，则为检委会专职委员
					ryjs.add("1");// 检察长
				} else if (jsbm.equals("002")) { // 副检察长
					ryjs.add("4");// 承办人
				} else { // 检委会专职委员
					ryjs.add("4");// 承办人
				}
			} else { // 其余部门映射，为如侦监、公诉等业务部门
				if (jsbm.equals("001") || jsbm.equals("002")) { // 001为处长、002为副处长、003为承办人、004为内勤
					ryjs.add("3");// 部门领导
				} else if (jsbm.equals("003")) {
					ryjs.add("4");// 承办人
				} else {
					ryjs.add("4"); // 内勤
				}
			}

			try {
				if (ryjs.size() > 0 && ryjs.get(i).equals("2")) {	//分管领导
					String fgbmbm = (String)this.cxFgBm(loger_dwbm,loger_gh).get("fgbmbm");
					if (bmbm.equals("")) {
						bmbm = bmbm + fgbmbm;
					} else {
						bmbm = bmbm + "," + fgbmbm;
					}
				} else if (ryjs.size() > 0 && ryjs.get(i).equals("3")) { // 部门负责人
					if (bmbm.equals("")) {
						bmbm = bmbm + bmjsStrArray[1];
					} else {
						bmbm = bmbm + "," + bmjsStrArray[1];
					}
				} else {
					if (bmbm.equals("")) {
						bmbm = bmbm + bmjsStrArray[1];
					} else {
						bmbm = bmbm + "," + bmjsStrArray[1];
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String qx = null;
		// 检察长、纪检、人事部、考评委员会
		if (ryjs.contains("1") || ryjs.contains("6") || ryjs.contains("7") || ryjs.contains("8")) {
			qx = "1";
		} else if (ryjs.contains("2") || ryjs.contains("3")) { // 分管领导和部门负责人
			qx = "2";
		} else {
			qx = "3"; // 一般 人员
		}

		if ((!loger_dwbm.equals(query_dwbm)) && qx.equals("2")) {// 判断是否是部门领导和分管领导，并且查的是否是下级单位
			bmbm = "(" + bmbm + ")";
			bmyss = this.selectDwbmBmys(loger_dwbm, bmbm);// 通过登录单位编码和部门编码找部门映射
			bmbm = this.selectBmysBm(query_dwbm, bmyss);// 通过查询的单位和当前登录人的部门映射找对应的部门
			if (bmbm.startsWith("''")) {
				bmbm = bmbm.replaceFirst("''", "");
			}
			if (bmbm.startsWith(",")) {
				bmbm = bmbm.substring(1);
			}
		}

		bmbm = bmbm.replace("(", "").
				replace(")", "").
				replaceAll("'", "");
		bmbmArr = bmbm.split(",");
		for (int i = 0; i < bmbmArr.length; i++) {
			// 部门负责人和分管领导所能看的部门
			bmbmList.add(bmbmArr[i]);
		}

		Page pager = PageHelper.startPage(page, rows);
		try {
			if ("1".equals(qx)) {
				// 检察长
				archivesList = jcgSfdaCxMapper.queryArchivesByCondition(query_dwbm, null, "",
						query_sfgs, query_sfgd, query_sffc, query_kssj, query_jssj, query_name);
			} else if ("2".equals(qx)) {
				// 分管院领导、部门负责人
				archivesList = jcgSfdaCxMapper.queryArchivesByCondition(query_dwbm, bmbmList, "",
						query_sfgs, query_sfgd, query_sffc, query_kssj, query_jssj, query_name);
			} else { // 普通人，普通人只能查看自己的个人绩效信息，默认显示所有
				archivesList = jcgSfdaCxMapper.queryArchivesByCondition(query_dwbm, null, loger_gh,
						query_sfgs, query_sfgd, query_sffc, query_kssj, query_jssj, query_name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		resultMap.put("total", pager.getTotal());
		resultMap.put("rows", archivesList);
		return resultMap;
	}

}
