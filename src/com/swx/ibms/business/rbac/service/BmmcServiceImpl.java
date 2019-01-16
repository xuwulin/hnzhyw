package com.swx.ibms.business.rbac.service;


import com.swx.ibms.business.archives.mapper.SfdacjMapper;
import com.swx.ibms.business.archives.service.JcgSfdaCxService;
import com.swx.ibms.business.archives.service.SfdacjServiceImp;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.performance.bean.Jxdfpm;
import com.swx.ibms.business.performance.bean.Jxjhsj;
import com.swx.ibms.business.performance.mapper.GrjxkhsyMapper;
import com.swx.ibms.business.performance.service.XtGrjxKhrqService;
import com.swx.ibms.business.rbac.bean.BMBM;
import com.swx.ibms.business.rbac.bean.Bmmc;
import com.swx.ibms.business.rbac.bean.JSBM;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.rbac.mapper.BmmcMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据部门编号和工号返回部门名称实体类集合 根据单位编号返回单位名称
 *
 * @author 王宇锋
 *
 */
@Service("bmmcService")
public class BmmcServiceImpl implements BmmcService {
	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(SfdacjServiceImp.class);
	/**
	 * 数据库接口
	 */
	@Resource
	private BmmcMapper bmmcMapper;
	/**
	 * 数据库接口
	 */
	@Resource
	private GrjxkhsyMapper grjxkhsyMapper;

	/**
	 * 司法档案查询service
	 */
	@Resource
	private JcgSfdaCxService jcgSfdaCxService;

	@Resource
	private SfdacjMapper sfdacjMapper;

	@Resource
	private XtGrjxKhrqService xtGrjxKhrqService;

	@Resource
	private LoginService loginService;
	/*
	 * 查询该单位下的所有部门
	 *
	 * @see com.swx.zhyw.service.BmmcService#getAllBmmc(java.lang.String)
	 */
	@Override
	public List<Bmmc> getAllBmmc(String dwbm) {
		Map<String, Object> map = new HashMap<>();
		map.put("dwbm", dwbm);
		map.put("cursor", null);

		// 该单位下的所有部门
		try {
			bmmcMapper.getAllBmmc(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		return (List<Bmmc>) map.get("cursor");
	}

	/**
	 * @param dwbm
	 *            单位编码
	 * @return 得到全部的部门编码
	 */
	@Override
	public String getAllBmbms(String dwbm) {
		Map<String, Object> map = new HashMap<>();
		map.put("p_dwbm", dwbm);
		bmmcMapper.getAllBmbms(map);
		List<BMBM> bmbmlist = (List<BMBM>) map.get("p_cursor");
		String bmbms = "''";
		for (int i = 0; i < bmbmlist.size(); i++) {
			bmbms = bmbms + "," + "'" + bmbmlist.get(i).getBmbm() + "'";
		}
		return bmbms;
	}

	/**
	 * 返回部门名称实体类集合
	 */
	@Override
	public List<Bmmc> getBmmc(String dwbm, String gh) {
		Map<String, Object> map = new HashMap<>();
		map.put("dwbm", dwbm);
		map.put("gh", gh);
		map.put("cursor", null);
		map.put("errmsg", null);

		// 通过单位编码和工号查询数据
		try {
			bmmcMapper.getBmmc(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		if ("1".equals(map.get("errmsg"))) {
			return (List<Bmmc>) map.get("cursor");
		}
		return null;
	}

	/**
	 * 通过单位编码返回单位名称
	 */
	@Override
	public String getDwmc(String dwbm) {
		Map<String, Object> map = new HashMap<>();
		map.put("dwbm", dwbm);
		map.put("dwmc", null);
		map.put("errmsg", null);

		try {
			bmmcMapper.getDwmc(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		if ("1".equals(map.get("errmsg"))) {
			return (String) map.get("dwmc");
		}
		return null;
	}

	/**
	 * 根据单位编码分角色返回首页绩效信息
	 */
	@Override
	public Map<String, Object> getSyjxxx(String dwbm, String gh, List<Integer> ryjs, List<String> bmjs) {
		// 此map用于返回首页所有绩效信息
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> jsxxMap = getJsxxSql(dwbm, gh, ryjs, bmjs);

		// 获得未考核人数,已考核人数
		// 此map用于传递存储过程参数
		Map<String, Object> map = new HashMap<>();
		// 已考核数量
		map.put("ykhcount", null);
		// 总考核数量
		map.put("zgkhcount", null);
		map.put("dwbm", dwbm);
		// 判断之后的人员角色
		map.put("ryjs", jsxxMap.get("ryjs"));
		// 判断之后生成的部门编码
		map.put("bmbms", jsxxMap.get("bmbms"));
		map.put("bmyss", "(''," + jsxxMap.get("bmyss") + ")");
		// 最大年
		map.put("maxyear", null);
		// 最大季度
		map.put("maxseason", null);
		map.put("errmsg", null);

		try {
			bmmcMapper.getKhRs(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		// 此map用于存储考核人数
		Map<String, Object> khrsMap = new HashMap<>();
		if ("1".equals(map.get("errmsg"))) {
			String ykhcount = (String) map.get("ykhcount");
			String zgkhcount = (String) map.get("zgkhcount");
			if (!StringUtils.isBlank(zgkhcount) && !StringUtils.isBlank(ykhcount)) {
				Integer ykhNumber = Integer.parseInt(ykhcount);
				Integer wkhNumber = Integer.parseInt(zgkhcount) - ykhNumber;
				khrsMap.put("ykhNumber", ykhNumber);
				khrsMap.put("wkhNumber", wkhNumber);
				resultMap.put("khrs", khrsMap);
			}
			resultMap.put("year", map.get("maxyear"));
			resultMap.put("season", map.get("maxseason"));
		}

		// 返回绩效得分前五名
		map.clear();
		map.put("dwbm", dwbm);
		map.put("ryjs", jsxxMap.get("ryjs"));
		map.put("bmbms", jsxxMap.get("bmbms"));
		map.put("bmyss", "(''," + jsxxMap.get("bmyss") + ")");
		map.put("cursor", null);
		map.put("errmsg", null);
		try {
			bmmcMapper.getJxdfpm(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		if ("1".equals(map.get("errmsg"))) {
			List<Jxdfpm> jxdfpm = (List<Jxdfpm>) map.get("cursor");
			if (CollectionUtils.isNotEmpty(jxdfpm)) {
				resultMap.put("jxdfpm", jxdfpm);
			} else {
				resultMap.put("jxdfpm", ListUtils.EMPTY_LIST);
			}
		}

		// 返回业务类型,最高分,平均分,最低分
		map.clear();
		map.put("dwbm", dwbm);
		map.put("ryjs", jsxxMap.get("ryjs"));
		map.put("bmbms", jsxxMap.get("bmbms"));
		// 判断之后生成的部门映射
		map.put("bmyss", "(''," + jsxxMap.get("bmyss") + ")");
		map.put("cursor", null);
		map.put("errmsg", null);
		try {
			bmmcMapper.getJxjhsj(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		if ("1".equals(map.get("errmsg"))) {
			List<Jxjhsj> jxjhsj = (List<Jxjhsj>) map.get("cursor");
			if (CollectionUtils.isNotEmpty(jxjhsj)) {
				resultMap.put("jxjhsj", jxjhsj);
			} else {
				resultMap.put("jxjhsj", ListUtils.EMPTY_LIST);
			}
		}

		return resultMap;
	}

	/**
	 * 获得首页绩效信息角色sql
	 *
	 * @param dwbm
	 *            单位编码
	 * @param gh
	 *            工号
	 * @param ryjs
	 *            人员角色
	 * @param bmjs
	 *            部门角色
	 * @return 返回角色信息
	 */
	public Map<String, Object> getJsxxSql(String dwbm, String gh, List<Integer> ryjs, List<String> bmjs) {
		Map<String, Object> returnMap = new HashMap<>();
		String bmBms = "";
		int[] arrRyjs = { 0, Constant.NUM_6,Constant.NUM_7, Constant.NUM_8 };// 人事部,纪检,案管,考评委员会
		returnMap.put("ryjs", ryjs.get(0));

		if (ryjs.contains(1)) {
			returnMap.put("ryjs", 1);
			bmBms = this.getAllBmbms(dwbm);
		} else {
			for (int i = 0; i < arrRyjs.length; i++) {
				if (ryjs.contains(arrRyjs[i])) {// 如果是人事部,纪检,案管,考评委员会则可以看全部部门
					returnMap.put("ryjs", arrRyjs[i]);
					bmBms = this.getAllBmbms(dwbm);
				}
			}
		}
		// 当角色是分管领导时通过统一业务查找分管部门
		if (2 == (int) returnMap.get("ryjs")) {// 分管领导
			bmBms = (String)jcgSfdaCxService.cxFgBm(dwbm,gh).get("fgbmbm");
			// 如果是其他人员则返回本部门的部门编码
		} else if (Constant.NUM_3 == (int) returnMap.get("ryjs") || Constant.NUM_4 == (int) returnMap.get("ryjs")
				|| Constant.NUM_5 == (int) returnMap.get("ryjs") || Constant.NUM_7 == (int) returnMap.get("ryjs")) {
			for (int i = 0; i < bmjs.size(); i++) {
				if (i == (bmjs.size() - 1)) {
					bmBms = bmBms + "'" + bmjs.get(i).split(",")[1] + "'";
				} else {
					bmBms = bmBms + "'" + bmjs.get(i).split(",")[1] + "',";
				}
			}
		}
		if (bmBms == null || "".equals(bmBms)) {
			bmBms = "('')";
		} else {
			bmBms = "(" + bmBms + ")";
		}
		String bmyss=jcgSfdaCxService.selectDwbmBmys(dwbm, bmBms);
		returnMap.put("bmyss",bmyss);
		bmBms=jcgSfdaCxService.selectBmysBm(dwbm,bmyss);//这样通过部门映射集获取部门是为了确保每个部门都有对应的业务类型
		returnMap.put("bmbms",bmBms);
		return returnMap;
	}

	/**
	 * 通过单位编码获得部门名称
	 */
	@Override
	public List<Bmmc> getBmByDwbm(String rootdwbm) {
		List<Bmmc> list = bmmcMapper.getBmByDwbm(rootdwbm);
		return list;
	}

	/**
	 * 根据部门编码获得部门名称
	 */
	@Override
	public List<RYBM> getBmmcBybmbm(String curentpage, String pagesize, String dwbm, String bmbm, String jsbm) {
		int i = Integer.parseInt(curentpage);
		int j = Integer.parseInt(pagesize);
		int startrow = (i - 1) * j;
		int endrow = i * j + 1;
		return bmmcMapper.getBmmcBybmbm(startrow, endrow, dwbm, bmbm, jsbm);
	}

	@Override
	public List<JSBM> getJsBybm(String dwbm, String bmbm) {
		return bmmcMapper.getJsBybm(dwbm, bmbm);
	}

	@Override
	public int getTotal(String dwbm, String bmbm, String jsbm) {
		return bmmcMapper.getTotal(dwbm, bmbm, jsbm);
	}

	@Override
	public int getTotalBybm(String dwbm, String bmbm) {
		return bmmcMapper.getTotalBybm(dwbm, bmbm);
	}

	/**
	 * 通过部门编码获得人员
	 */
	@Override
	public List<RYBM> getRyByBmbm(String curentpage, String pagesize, String dwbm, String bmbm) {
		int i = Integer.parseInt(curentpage);
		int j = Integer.parseInt(pagesize);
		int startrow = (i - 1) * j;
		int endrow = i * j + 1;
		return bmmcMapper.getRyByBmbm(startrow, endrow, dwbm, bmbm);
	}


}
