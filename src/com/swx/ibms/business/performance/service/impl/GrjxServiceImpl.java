package com.swx.ibms.business.performance.service.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.performance.bean.GRJX;
import com.swx.ibms.business.performance.bean.GrjxZgjcf;
import com.swx.ibms.business.performance.bean.ydkhqbtg;
import com.swx.ibms.business.performance.mapper.GrjxMapper;
import com.swx.ibms.business.performance.service.GrjxService;
import com.swx.ibms.business.performance.service.HCPZService;
import com.swx.ibms.business.system.service.DjpdService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 个人绩效服务实现类
 * @author 李治鑫
 * @since 2017-5-8
 *
 */
@SuppressWarnings("all")
@Service("grjxService")
public class GrjxServiceImpl implements GrjxService {

	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(GrjxServiceImpl.class);

	/**
	 * 统一业务dblink
	 */
	private static final String  DBLINK= "@tyyw";
	/**
	 * 需要进行系统评分的子项目数组列表
	 */
	private static final String[] ZXMBM_ARRAY = { 
			"0202", "0303", "0304", "0305", 
			"0306", "0307", "0401", "0403",
			"0404", "0505", "0506", "0601",
			"0602", "0603", "0703", "0705", 
			"0706", "0901" };

	/**
	 * 数据访问接口
	 **/
	@Resource
	private GrjxMapper grjxmapper;
	/**
	 * 核查配置服务
	 */
	@Resource
	private HCPZService hcpzservice;
	
	/**
	 * 等级评定接口
	 */
	@Resource
	private DjpdService djpdService;

	@Override
	public List<GRJX> getGrjxByDaid(String daid) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("p_daid", daid);
		map.put("p_cursor", "");
		map.put("p_errmsg", "");

		try {
			grjxmapper.getGrjxByDaid(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		String temp = (String) map.get("p_errmsg");
		if (temp == null || "".equals(temp)) {
			return (List<GRJX>) map.get("p_cursor");
		} else {
			return null;
		}
	}

	@Override
	public int addGrjx(GRJX grjx) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("p_daid", grjx.getDaid());
		map.put("p_ywlx", grjx.getYwlx());
		map.put("p_ywzf", grjx.getYwzf());
		map.put("p_zbkpgl", grjx.getZbkpgl());
		map.put("p_errmsg", "");

		try {
			grjxmapper.addGrjx(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		String temp = (String) map.get("p_errmsg");
		if (temp == null || "".equals(temp)) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int updateGrjx(GRJX grjx) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("p_daid", grjx.getDaid());
		map.put("p_ywlx", grjx.getYwlx());
		map.put("p_ywzf", grjx.getYwzf());
		map.put("p_zbkpgl", grjx.getZbkpgl());
		map.put("p_errmsg", "");

		try {
			grjxmapper.updateGrjx(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		String temp = (String) map.get("p_errmsg");
		if (temp == null || "".equals(temp)) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int deleteGrjx(String daid) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("p_daid", daid);
		map.put("p_errmsg", "");

		try {
			grjxmapper.deleteGrjx(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		String temp = (String) map.get("p_errmsg");
		if (temp == null || "".equals(temp)) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 根据档案ID获取人员所在单位编码和工号
	 * 
	 * @param daid
	 *            档案ID
	 * @return 工号
	 */
	@Override
	public Map<String, String> getRyGhByDaid(String daid) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> map1 = new HashMap<String, String>();

		map.put("p_daid", daid);
		map.put("p_dwbm", StringUtils.EMPTY);
		map.put("p_gh", StringUtils.EMPTY);
		map.put("p_tjnf", StringUtils.EMPTY);
		map.put("errmsg", StringUtils.EMPTY);

		try {
			grjxmapper.getRyGhByDaid(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		String temp = MapUtils.getString(map, "p_errmsg", StringUtils.EMPTY);
		if (!StringUtils.isNotBlank(temp)) {
			String dwbm = MapUtils.getString(map, "p_dwbm", StringUtils.EMPTY);
			String gh = MapUtils.getString(map, "p_gh", StringUtils.EMPTY);
			String tjnf = MapUtils.getString(map, "p_tjnf", StringUtils.EMPTY);

			map1.put("dwbm", dwbm);
			map1.put("gh", gh);
			map1.put("tjnf", tjnf);
		}

		return map1;

	}

	/**
	 * 根据单位编码、工号、添加年份获取该人员在该年份所办理案件的部门受案号
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param tjnf 添加年份
	 * @return List<String>
	 */
	public List<String> getBmsahByGh(String dwbm, String gh, String tjnf) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("p_dwbm", dwbm);
		map.put("p_gh", gh);
		map.put("p_wcrq", tjnf);
		map.put("p_cursor", StringUtils.EMPTY);
		map.put("p_errmsg", StringUtils.EMPTY);

		try {
			grjxmapper.getBmsahByGh(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		String temp = MapUtils.getString(map, "p_errmsg", StringUtils.EMPTY);
		if (!StringUtils.isNotBlank(temp)) {
			return (List<String>) map.get("p_cursor");
		} else {
			return null;
		}
	}

	/**
	 * 一个案件的评分处理方法
	 * 
	 * @param bmsah
	 *            部门受案号
	 * @param zxmbm
	 *            子评分项目编码
	 * @return 分数
	 */
	public double getOneScore(String bmsah, String zxmbm) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_bmsah", bmsah);// 【案件】被评查案件部门受案号
		String a[] = bmsah.split("]");
		int len = a[1].length();
		String char2 = a[1].substring(0, len - 1);

		map.put("p_oclbbm", "");// 【评查】评查类别编码
		map.put("p_pcslbm", "");// 【评查】评查受理编码
		map.put("p_pcxbm", "");// 【评查】评查项编码
		map.put("p_pcxmc", "");// 【评查】评查项名称
		map.put("p_pcxzf", "");// 【评查】评查项总分
		map.put("p_char3", "");// 自定义参数3
		map.put("p_pcxfz", "");// 【评查】评查项分值
		map.put("p_result", "");// 【评查】评查理由
		map.put("p_errmsg", "");// 返回错误信息
		switch (zxmbm) {
		// 属于公用项
		case "0202":
			map.put("p_mxkf", "2");// 每项扣分
			map.put("p_char1", DBLINK);// 自定义参数1 DB Link 连接
			map.put("p_char2", char2);// 自定义参数2
			map.put("p_char4", "0");// 自定义参数4
			map.put("p_char5", "XXXX,YYYY");// 自定义参数5
			try {
				grjxmapper.checkws(map);
			} catch (Exception e) {
				LOG.error("风险评估");
			}
			break;
		// 属于公用项
		case "0303":
			map.put("p_mxkf", "1");
			map.put("p_char1", "");// 自定义参数1 DB Link 连接
			map.put("p_char2", "");// 自定义参数2
			map.put("p_char4", "");// 自定义参数4
			map.put("p_char5", "");// 自定义参数5
			try {
				grjxmapper.check017(map);
			} catch (Exception e) {
				LOG.error("审查逮捕意见");
			}
			break;
		// 属于对于应当听取辩护律师的意见
		case "0304":
			map.put("p_mxkf", "1");
			map.put("p_char1", "");// 自定义参数1 DB Link 连接
			map.put("p_char2", "");// 自定义参数2
			map.put("p_char4", "");// 自定义参数4
			map.put("p_char5", "");// 自定义参数5
			try {
				grjxmapper.check016(map);
			} catch (Exception e) {
				LOG.error("听取辩护律师意见");
			}
			break;
		// 属于公用项
		case "0305":
			map.put("p_mxkf", "1");
			map.put("p_char1", DBLINK);// 自定义参数1 DB Link 连接
			map.put("p_char2", char2);// 自定义参数2
			map.put("p_char4", "0");// 自定义参数4
			map.put("p_char5", "XXXX,YYYY");// 自定义参数5
			try {
				grjxmapper.checkws(map);
			} catch (Exception e) {
				LOG.error("诉讼权利告知");
			}
			break;
		// 属于公用项
		case "0306":
			map.put("p_mxkf", "1");
			map.put("p_char1", DBLINK);// 自定义参数1 DB Link 连接
			map.put("p_char2", char2);// 自定义参数2
			map.put("p_char4", "0");// 自定义参数4
			map.put("p_char5", "XXXX,YYYY");// 自定义参数5
			try {
				grjxmapper.checkws(map);
			} catch (Exception e) {
				LOG.error("听取意见听取犯罪嫌疑人意见");
			}
			break;
		// 属于讯问犯罪嫌疑人
		case "0307":
			map.put("p_mxkf", "1");
			map.put("p_char1", "");// 自定义参数1 DB Link 连接
			map.put("p_char2", "");// 自定义参数2
			map.put("p_char4", "");// 自定义参数4
			map.put("p_char5", "");// 自定义参数5
			try {
				grjxmapper.check013(map);
			} catch (Exception e) {
				LOG.error("讯问");
			}
			break;
		// 属于审查终结未制作《批准逮捕决定书》
		case "0401":
			map.put("p_mxkf", "1");
			map.put("p_char1", "");// 自定义参数1 DB Link 连接
			map.put("p_char2", "");// 自定义参数2
			map.put("p_char4", "");// 自定义参数4
			map.put("p_char5", "");// 自定义参数5
			try {
				grjxmapper.check021(map);
			} catch (Exception e) {
				LOG.error("审查批准逮捕");
			}
			break;
		// 属于公用项
		case "0403":
			map.put("p_mxkf", "1");
			map.put("p_char1", DBLINK);// 自定义参数1 DB Link 连接
			map.put("p_char2", char2);// 自定义参数2
			map.put("p_char4", "0");// 自定义参数4
			map.put("p_char5", "XXXX,YYYY");// 自定义参数5
			try {
				grjxmapper.checkws(map);
			} catch (Exception e) {
				LOG.error("执行回执");
			}
			break;
		// 属于批准（不批准）逮捕案件
		case "0404":
			map.put("p_mxkf", "2");
			map.put("p_char1", "");// 自定义参数1 DB Link 连接
			map.put("p_char2", "");// 自定义参数2
			map.put("p_char4", "");// 自定义参数4
			map.put("p_char5", "");// 自定义参数5
			try {
				grjxmapper.check023(map);
			} catch (Exception e) {
				LOG.error("批准（不批准）逮捕案件通知书");
			}
			break;
		// 属于公用项
		case "0505":
			map.put("p_mxkf", "1");
			map.put("p_char1", DBLINK);// 自定义参数1 DB Link 连接
			map.put("p_char2", char2);// 自定义参数2
			map.put("p_char4", "0");// 自定义参数4
			map.put("p_char5", "XXXX,YYYY");// 自定义参数5
			try {
				grjxmapper.checkws(map);
			} catch (Exception e) {
				LOG.error("立案监督");
			}
			break;
		// 属于不应当立案而立案的
		case "0506":
			map.put("p_mxkf", "0.5");
			map.put("p_char1", "");// 自定义参数1 DB Link 连接
			map.put("p_char2", "");// 自定义参数2
			map.put("p_char4", "");// 自定义参数4
			map.put("p_char5", "");// 自定义参数5
			try {
				grjxmapper.check025(map);
			} catch (Exception e) {
				LOG.error("撤案监督");
			}
			break;
		// 属于公用项
		case "0601":
			map.put("p_mxkf", "1");
			map.put("p_char1", DBLINK);// 自定义参数1 DB Link 连接
			map.put("p_char2", char2);// 自定义参数2
			map.put("p_char4", "0");// 自定义参数4
			map.put("p_char5", "XXXX,YYYY");// 自定义参数5
			try {
				grjxmapper.checkws(map);
			} catch (Exception e) {
				LOG.error("文书制作不规范");
			}
			break;
		// 属于公用项
		case "0602":
			map.put("p_mxkf", "0.5");
			map.put("p_char1", DBLINK);// 自定义参数1 DB Link 连接
			map.put("p_char2", char2);// 自定义参数2
			map.put("p_char4", "0");// 自定义参数4
			map.put("p_char5", "XXXX,YYYY");// 自定义参数5
			try {
				grjxmapper.checkws(map);
			} catch (Exception e) {
				LOG.error("补充侦查提纲");
			}
			break;
		// 属于不批准逮捕案件
		case "0603":
			map.put("p_mxkf", "0.5");
			map.put("p_char1", "");// 自定义参数1 DB Link 连接
			map.put("p_char2", "");// 自定义参数2
			map.put("p_char4", "");// 自定义参数4
			map.put("p_char5", "");// 自定义参数5
			try {
				grjxmapper.check028(map);
			} catch (Exception e) {
				LOG.error("不捕理由说明");
			}
			break;
		// 属于公用项
		case "0703":
			map.put("p_mxkf", "1");
			map.put("p_char1", DBLINK);// 自定义参数1 DB Link 连接
			map.put("p_char2", char2);// 自定义参数2
			map.put("p_char4", "0");// 自定义参数4
			map.put("p_char5", "XXXX,YYYY");// 自定义参数5
			try {
				grjxmapper.checkws(map);
			} catch (Exception e) {
				LOG.error("系统外制作法律文书");
			}
			break;
		// 属于公用项
		case "0705":
			map.put("p_mxkf", "0.5");
			map.put("p_char1", DBLINK);// 自定义参数1 DB Link 连接
			map.put("p_char2", char2);// 自定义参数2
			map.put("p_char4", "0");// 自定义参数4
			map.put("p_char5", "XXXX,YYYY");// 自定义参数5
			try {
				grjxmapper.checkws(map);
			} catch (Exception e) {
				LOG.error("法律文书公开");
			}
			break;
		// 属于公用项
		case "0706":
			map.put("p_mxkf", "0.5");
			map.put("p_char1", DBLINK);// 自定义参数1 DB Link 连接
			map.put("p_char2", char2);// 自定义参数2
			map.put("p_char4", "0");// 自定义参数4
			map.put("p_char5", "XXXX,YYYY");// 自定义参数5
			try {
				grjxmapper.checkws(map);
			} catch (Exception e) {
				LOG.error("流程信息公开");
			}
			break;
		case "0901":
			map.put("p_mxkf", "10");
			map.put("p_char1", DBLINK);// 自定义参数1 DB Link 连接
			map.put("p_char2", char2);// 自定义参数2
			map.put("p_char4", "0");// 自定义参数4
			map.put("p_char5", "XXXX,YYYY");// 自定义参数5
			try {
				grjxmapper.checkws(map);
			} catch (Exception e) {
				LOG.error("审查逮捕案件、延长侦查羁押期限案件");
			}
			break;
		default:
			break;
		}
		double score = 0;
		if (map.get("p_errmsg") == null || "".equals(map.get("p_errmsg"))) {
			score = (double) map.get("p_pcxfz");
		}
		return score;
	}

	/**
	 * 获取一个案件列表的最终分数
	 * 
	 * @param bmsahList
	 *            案件列表
	 * @param zxmbm
	 *            子评分项目编码
	 * @return 分数
	 */
	public double getScore(List<String> bmsahList, String zxmbm) {

		double score = 0;// 总分
		int l = bmsahList.size();
		for (int i = 0; i < l; i++) {
			score += getOneScore(bmsahList.get(i), zxmbm);
		}
		return score / l;

	}

	/**
	 * 子项目整合
	 * @param grjx 个人绩效对象
	 * @param zxmbm 子项目编码
	 * @return  得分
	 */
	public double conformSon(List<GRJX> grjx, String zxmbm) {

		GRJX grjxObj = grjx.get(0);
		String zbkpgl = grjxObj.getZbkpgl();
		String daid = grjxObj.getDaid();

		// 根据档案ID获取人员所在单位编码和工号和归档时间
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = getRyGhByDaid(daid);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY);
		}

		// 根据单位编码和工号获取部门受案号
		List<String> bmsahList = null;
		try {
			bmsahList = getBmsahByGh(map.get("dwbm"), map.get("gh"), map.get("tjnf"));
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY);
		}
		if (CollectionUtils.isEmpty(bmsahList)) {
			return -1;
		}
		// 计算这个人员某一个子项目所得的分数
		double score = getScore(bmsahList, zxmbm);

		final String zxmbmCopy = zxmbm;

		/**
		 * json反序列化
		 */
		JsonArray jsonArray = (JsonArray) Constant.JSON_PARSER.parse(zbkpgl);
		jsonArray.forEach(item -> {
			JsonObject jsonObjItem = (JsonObject) item;
			String zxmbm1 = jsonObjItem.get("zxmbm").getAsString();
			if (zxmbmCopy.equals(zxmbm1)) {
				double fz = jsonObjItem.get("fz").getAsDouble();
				jsonObjItem.addProperty("df", "" + (fz - score));
			}
		});
		// 重新将JSON数据转化成String并赋值给grjx对象的指标考评概览zbkpgl字段中
		String czbkpgl = jsonArray.toString();
		grjxObj.setZbkpgl(czbkpgl);

		return score;
	}

	/**
	 * 所有子项目整合
	 * 
	 * @param grjx
	 *            个人绩效
	 */
	@Override
	public void conform(List<GRJX> grjx) {

		String[] zxmbmarray = ZXMBM_ARRAY;
		double total = 0;
		double score = 0;
		for (int i = 0; i < zxmbmarray.length; i++) {
			score = conformSon(grjx, zxmbmarray[i]);
			if (score < 0) {
				grjx.get(0).setYwzf(0);
				return;
			} else {
				total = total + score;
			}
		}
		grjx.get(0).setYwzf(26.5 - total);

	}

	/**
	 * 插入或更新个人绩效每个基础分
	 * 
	 * @param year 年度
	 * @param jd 季度
	 * @param ywlx 业务类型
	 * @param dwjb 单位级别
	 * @param xmbm 项目编码
	 * @param zgjcf 最高基础分
	 * @return 执行结果
	 */
	@Override
	public String inOrUpGrjxZgjcf(int year, int jd, String ywlx, 
			String dwjb, String xmbm, Double zgjcf) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_year", year);
		map.put("p_jd", jd);
		map.put("p_ywlx", ywlx);
		map.put("p_dwjb", dwjb);
		map.put("p_xmbm", xmbm);
		map.put("p_zgjcf", zgjcf);
		map.put("Y", '0');
		grjxmapper.inOrUpGrjxZgjcf(map);
		return (String) map.get("Y");
	}

	/**
	 * 个人绩效该绩效所有人审批是否通过
	 * 
	 * @param year 年份
	 * @param jd 季度
	 * @param ywlx 业务类型
	 * @param dwjb 单位级别
	 * @param dwbmtwo 单位编码
	 * @return 执行结果
	 */
	@Override
	public String grjxAllRSpTg(int year, int jd, String ywlx, String dwjb, String dwbmtwo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_year", year);
		map.put("p_jd", jd);
		map.put("p_ywlx", ywlx);
		map.put("p_dwjb", dwjb);
		map.put("p_dwbmtwo", dwbmtwo);
		map.put("Y", '0');
		grjxmapper.grjxSfSsxt(map);
		return (String) map.get("Y");
	}
	/* 根据月度考核年份，季度，业务类别，单位级别，单位编码前两位，找出所有审批通过的人的月度考核id,然后再遍历showGrjxZgjcfList服务
	 * @see com.swx.zhyw.service.GrjxService#selectGrjxYdkhlist(int, int, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String selectGrjxYdkhlist(int year, int jd, String ywlx, String dwjb, String dwbmtwo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_year",year);
		map.put("p_jd", jd);
		map.put("p_ywlx", ywlx);
		map.put("p_dwjb",dwjb );
		map.put("p_dwbmtwo",dwbmtwo);
		map.put("cursor", null);
		grjxmapper.selectGrjxYdkhlist(map);
		List<ydkhqbtg> list= (List<ydkhqbtg>)map.get("p_cursor");
		for(int i=0;i<list.size();i++){
			ydkhqbtg ydkhqbtgI = list.get(i);
			showGrjxZgjcfList(ydkhqbtgI.getYdkhid(), 
					ydkhqbtgI.getYear(), ydkhqbtgI.getJd(),
					ydkhqbtgI.getYwlx(), ydkhqbtgI.getDwjb());
		}
		return null;
	}
	/**
	 * 得到评价得分，并更新月度考核的zbkpgl,zbkpdf
	 * 
	 * @param year 年份
	 * @param jd 季度
	 * @param ywlx 业务类型
	 * @param dwjb 单位级别
	 * @return Map 
	 */
	@Override
	public Map showGrjxZgjcfList(String ydkhid, int year, int jd, String ywlx, String dwjb) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_year", year);
		map.put("p_jd", jd);
		map.put("p_ywlx", ywlx);
		map.put("p_dwjb", dwjb);
		grjxmapper.showGrjxZgjcfList(map);
		// 最高评价分
		List<GrjxZgjcf> resultList = (List<GrjxZgjcf>) map.get("p_cursor");
		String zgpjfstr = selectgrjxZbpzNr(ywlx, dwjb);
		JsonArray zgpjfjsonArray = (JsonArray) Constant.JSON_PARSER.parse(zgpjfstr);
		// 基础分
		String zbkpdf = (String)selectgrjxZbkpdf(ydkhid, ywlx).get("zbkpdf");
		JsonObject zbkpdfjson = (JsonObject) Constant.JSON_PARSER.parse(zbkpdf);
		JsonArray zbkpdfvalueJson = (JsonArray) zbkpdfjson.get("value");
		
		JsonArray pjdfvalue=new JsonArray();

		//指标考评概览
		String zbkpgl = (String)selectgrjxZbkpdf(ydkhid, ywlx).get("zbkpgl");
		JsonArray zbkpgljsonArray = (JsonArray) Constant.JSON_PARSER.parse(zbkpgl);
		//总评价得分
		Double zpjdf=0.0;
		int maxlength=resultList.size();
		for (int i = 0; i < maxlength; i++) {
			JsonObject jo = (JsonObject) zgpjfjsonArray.get(i);
			Iterator iter = jo.entrySet().iterator();
			Entry e = (Entry) iter.next();
			//项目编码
			final String xmbm = e.getKey().toString();
//			xmbm=xmbm;
            //最高评价分
			Double zgpjf = Double.valueOf(e.getValue().toString());
            //基础分
			Double jcf = zbkpdfvalueJson.get(maxlength-i-1).getAsDouble();

//			GrjxZgjcf grjxzgjcf = resultList.get(i);

			GrjxZgjcf grjxzgjcf = resultList.stream().filter(item -> {
				return item.getXmbm().equals(xmbm);
			}).findFirst().orElse(new GrjxZgjcf());

			Double pjdf = 1.0;
			if (grjxzgjcf.getZgjcf() == 0.0) {
				pjdf = zgpjf;
			} else {
				pjdf = zgpjf / grjxzgjcf.getZgjcf() * jcf;
			}
			BigDecimal bg=new BigDecimal(pjdf);
			pjdf=bg.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
				for(int j=0;j<zbkpgljsonArray.size();j++){
					JsonObject zbkpglvalueJsonI = ((JsonObject)zbkpgljsonArray.get(j));
					String zbkpgxmbm=zbkpglvalueJsonI.get("xmbm").getAsString();

					if(zbkpgxmbm.equals(xmbm)){
						zbkpglvalueJsonI.add("pjdf",new JsonPrimitive(pjdf));
					}
				}
			pjdfvalue.add(new JsonPrimitive(pjdf));
			zpjdf=zpjdf+pjdf;
		}
		//根据总评价得分计算出评价等级
		List djpdList=djpdService.djpd(zpjdf);
		String pdjb=(String) djpdList.get(0);
		String pdjbmc=(String) djpdList.get(1);

		zbkpdfjson.add("pjdfvalue",pjdfvalue);
		Map<String, Object> resultmap = new HashMap<String, Object>();
		resultmap.put("p_ydkhid",ydkhid);
		resultmap.put("p_ywlx",ywlx);
		resultmap.put("p_zbkpgl",zbkpgljsonArray.toString());
		resultmap.put("p_zbkpdf",zbkpdfjson.toString());
		resultmap.put("p_zpjdf", zpjdf);
		resultmap.put("p_pdjb", pdjb);
		resultmap.put("p_pdjbmc", pdjbmc);
		try {
			grjxmapper.updateGrjxYwkhfzLf(resultmap);
		} catch (Exception e) {
		}
		return resultmap;
	}

	@Override
	public String insertZgjcfdata(String ydkhid, String dwbm) {
		List<String> lists = hcpzservice.getndjd(ydkhid);
		String nd = lists.get(0); // 年度
		String jd = lists.get(1); // 季度
		String ywlx = lists.get(2); // 业务类型
		String dwjb = (String) hcpzservice.getdwjb(dwbm).get("p_dwjb"); // 单位级别
		int grjxyear = Integer.valueOf(nd);
		int grjxjd = Integer.valueOf(jd);
		String zbpznr = selectgrjxZbpzNr(ywlx, dwjb);
		JsonArray zbpznrjsonArray = (JsonArray) Constant.JSON_PARSER.parse(zbpznr);
		// JsonArray zbpz_nrjsonArray = (JsonArray) zbpz_nrjson;

		String zbkpdf = (String)selectgrjxZbkpdf(ydkhid, ywlx).get("zbkpdf");
		JsonObject zbkpdfjson = (JsonObject) Constant.JSON_PARSER.parse(zbkpdf);
		JsonArray zbkpdfvalueJson = (JsonArray) zbkpdfjson.get("value");
        int maxlength=zbkpdfvalueJson.size();
		for (int i = 0; i < maxlength; i++) {
			JsonObject jo = (JsonObject) zbpznrjsonArray.get(i);
			Iterator iter = jo.entrySet().iterator();
			Entry e = (Entry) iter.next();
			String xmbm = (String) e.getKey();
			inOrUpGrjxZgjcf(grjxyear, grjxjd, ywlx, 
					dwjb, xmbm, zbkpdfvalueJson.get(maxlength-i-1).getAsDouble());
		}
		return null;
	}

	@Override
	public String selectgrjxZbpzNr(String ywlb, String dwjb) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_ywlb", ywlb);
		map.put("p_dwjb", dwjb);
		map.put("p_zbpz_nr", null);
		map.put("p_pjf", null);
		grjxmapper.selectgrjxZbpzNr(map);
		String zbpznr = null;
		String zgpjf = null;
		try {
			zbpznr = IOUtils.toString(((Clob) map.get("p_zbpz_nr")).getCharacterStream());
			zgpjf = (String) map.get("p_pjf");
		} catch (Exception e) {
		}
		return zgpjf;
	}

	/*
	 * (non-Javadoc) 根据月度考核id和业务类型查找业务考核分值的zbkpdf
	 */
	@Override
	public Map selectgrjxZbkpdf(String ydkhid, String ywlx) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_ydkhid", ydkhid);
		map.put("p_ywlx", ywlx);
		map.put("p_zbkpgl", null);
		map.put("p_zbkpdf", null);
		grjxmapper.selectgrjxZbkpdf(map);
		String zbkpgl = null;
		String zbkpdf = null;
		Map<String, Object> mapresult = new HashMap<String, Object>();
		try {
			zbkpgl = IOUtils.toString(((Clob) map.get("p_zbkpgl")).getCharacterStream());
			zbkpdf = IOUtils.toString(((Clob) map.get("p_zbkpdf")).getCharacterStream());
			 mapresult.put("zbkpgl", zbkpgl);
			 mapresult.put("zbkpdf", zbkpdf);
		} catch (Exception e) {
		}
		return mapresult;
	}


}
