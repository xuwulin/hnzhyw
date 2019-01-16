package com.swx.ibms.business.performance.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swx.ibms.business.appraisal.bean.Ywlxmc;
import com.swx.ibms.business.archives.mapper.SfdacjMapper;
import com.swx.ibms.business.archives.service.JcgSfdaCxService;
import com.swx.ibms.business.archives.service.SfdacjServiceImp;
import com.swx.ibms.business.common.mapper.TreeSelectMapper;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.common.utils.ExcelUtil;
import com.swx.ibms.business.performance.bean.Jzryjxfs;
import com.swx.ibms.business.performance.bean.XtGrjxKhrq;
import com.swx.ibms.business.performance.mapper.GrjxkhsyMapper;
import com.swx.ibms.business.performance.service.GrjxkhsyService;
import com.swx.ibms.business.performance.service.XtGrjxKhrqService;
import com.swx.ibms.business.rbac.bean.Bmmc;
import com.swx.ibms.business.rbac.bean.JSBM;
import com.swx.ibms.business.rbac.service.LoginService;
import com.swx.ibms.business.system.service.XtfjpathService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 个人绩效首页实现类
 *
 * @author 王宇锋
 *
 */
@SuppressWarnings("all")
@Service("grjxkhsyService")
public class GrjxkhsyServiceImpl implements GrjxkhsyService {
	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(SfdacjServiceImp.class);

	/**
	 * 分页显示行数
	 */
	private static final int SHOWROWS = 9;

	/**
	 * 持久层接口
	 */
	@Resource
	private GrjxkhsyMapper grjxkhsyMapper;

	/**
	 * 树形结构展示Mapper接口
	 */
	@Resource
	private TreeSelectMapper treeSelectMapper;

	@Resource
	private XtGrjxKhrqService xtGrjxKhrqService;

	@Resource
	private SfdacjMapper sfdacjMapper;

	@Resource
	private LoginService loginService;

	/**
	 * 司法档案查询
	 */
	@Resource
	private JcgSfdaCxService jcgSfdaCxService;

	/**
	 * 文件上传路径服务
	 */
	@Resource
	private XtfjpathService xtfjpathservice;

	/**
	 * 个人绩效首页刚加载时返回数据
	 */
	@Override
	public Map<String, Object> getJzjxkh(String dwbm, String rymc, int page, List<Integer> ryjs,
										 String gh,List<String> bmjs, String cxbmbm, String cxdwbm,String ywlx,int year,int jd) {
		// 用于返回数据
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 此map用于存储过程传递参数
		Map<String, Object> map = new HashMap<>();
		// 判断生成人员名称模糊查询sql语句
		String rymcSql = null;
		if (StringUtils.isNotBlank(rymc)) {
			rymcSql = getRymcSql(rymc);
		}
		// 生成角色人员sql
		String jssql = getJsSql(ryjs, dwbm, gh, bmjs);
		if (cxbmbm == null) {//如果查询部门编码为空则说明是没有跨院查询
			// 获得考评得分详情条件过滤后总行数
			map.put("dwbm", dwbm);
			map.put("bmbm", cxbmbm);
			map.put("year", year);
			map.put("jd", jd);
			map.put("ywlx", ywlx);
			map.put("likemc", rymcSql);
			map.put("jssql", jssql);
			map.put("count", null);
			map.put("errmsg", null);
		} else {
			map.put("dwbm", cxdwbm);
			map.put("bmbm", cxbmbm);
			map.put("year", year);
			map.put("jd", jd);
			map.put("ywlx", ywlx);
			map.put("likemc", rymcSql);
			map.put("jssql", "");
			map.put("count", null);
			map.put("errmsg", null);
		}
		try {
			grjxkhsyMapper.getKpdfxqcount(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		if ("1".equals(map.get("errmsg"))) {
			resultMap.put("count", map.get("count"));
		} else {
			resultMap.put("count", null);
		}

		// 刚加载时根据单位编码分页获得月度考核id,单位编码工号,人员名称,月度考核总分,年份,月份
		map.clear();
		int begin = (page - 1) * SHOWROWS + 1;
		int end = page * SHOWROWS;
		// 分页显示数据
		if (cxbmbm == null) {// 当跨院的时候
			map.put("dwbm", dwbm);
			map.put("bmbm", null);
			map.put("year", year);
			map.put("jd", jd);
			map.put("ywlx", ywlx);
			map.put("likemc", rymcSql);
			map.put("jssql", jssql);
			map.put("begin", begin);
			map.put("end", end);
			map.put("cursor", null);
			map.put("errmsg", null);
		} else {// 当不跨院的时候
			map.put("dwbm", cxdwbm);
			map.put("bmbm", cxbmbm);
			map.put("year", year);
			map.put("jd", jd);
			map.put("ywlx", ywlx);
			map.put("likemc", rymcSql);
			map.put("jssql", "");
			map.put("begin", begin);
			map.put("end", end);
			map.put("cursor", null);
			map.put("errmsg", null);
		}

		try {
			grjxkhsyMapper.getJzryjxfs(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		if ("1".equals(map.get("errmsg"))) {
			List<Jzryjxfs> jxdfs = (List<Jzryjxfs>) map.get("cursor");
			// 按照月度考核ID获得指标考评得分,业务名称和业务类型
			for (Jzryjxfs jzryjxfs : jxdfs) {
				map.clear();
				map.put("ydkhid", jzryjxfs.getYdkhid());
				map.put("ywzf", null);
				map.put("zbkpdf", null);
				map.put("ywlx", null);
				map.put("zpjdf", null);
				map.put("pdjb", null);
				map.put("pdjbmc", null);
				map.put("cursor", null);
				map.put("errmsg", null);
				try {
					grjxkhsyMapper.getZbkpdf(map);
				} catch (Exception e) {
					LOG.error(StringUtils.EMPTY, e);
				}
				if ("1".equals(map.get("errmsg"))) {
					//业务总分
					jzryjxfs.setYwzf((Double) map.get("ywzf"));
					//总评价得分
					jzryjxfs.setZpjdf((Double) map.get("zpjdf"));

					//获取单位名称
					Map<String, Object> dwmcMap = new HashMap<>();
					try {
						dwmcMap.put("dwbm", jzryjxfs.getDwbm());
						dwmcMap.put("dwmc", StringUtils.EMPTY);
						grjxkhsyMapper.getDwmcByDwbm(dwmcMap);
						jzryjxfs.setDwmc((String) dwmcMap.get("dwmc"));
					} catch (Exception e) {
						LOG.error(StringUtils.EMPTY, e);
					}

					//评定级别
					jzryjxfs.setPdjb((String)map.get("pdjb"));
					//评定级别名称
					jzryjxfs.setPdjbmc((String)map.get("pdjbmc"));
					String zbkpdf = StringUtils.EMPTY;

					try {
						//指标考评得分
//						zbkpdf = IOUtils.toString(((Clob) map.get("zbkpdf")).getCharacterStream());
						zbkpdf = ((Clob) map.get("zbkpdf")).getSubString((long)1,
								(int)((Clob) map.get("zbkpdf")).length());

						JsonObject zbkpdfjson = (JsonObject) Constant.JSON_PARSER.parse(zbkpdf);
						//获得评分项名称json数组
						JsonArray namesJson = (JsonArray) zbkpdfjson.get("name");
						//获得对应评价得分json数组
						JsonArray valueJson = (JsonArray) zbkpdfjson.get("pjdfvalue");

						JsonArray zbkpdfArray = new JsonArray();
						for (int i = 0; i < namesJson.size(); i++) {
							JsonObject value = new JsonObject();
							value.addProperty("name", namesJson.get(i).getAsString());
//							value.addProperty("value", valueJson.get(i).getAsString());
							zbkpdfArray.add(value);
						}

						zbkpdf = zbkpdfArray.toString();

					} catch (Exception e) {
						e.printStackTrace();
						LOG.error(StringUtils.EMPTY, e);
					}

					LOG.info(zbkpdf);

					jzryjxfs.setZbkpdf(zbkpdf);
					jzryjxfs.setYwlx((String) map.get("ywlx"));
					jzryjxfs.setYwlxmcs((List<Ywlxmc>) map.get("cursor"));
				}
			}
			resultMap.put("jxdfs", jxdfs);
		} else {
			resultMap.put("jxdfs", null);
		}
		return resultMap;
	}

	/**
	 * 生成rymcSql语句
	 *
	 * @param rymc 人员名称
	 * @return 人员名称sql
	 */
	@Override
	public String getRymcSql(String rymc) {
		String rymcSql = "and (";
		//以空格符号分割
		String[] rymcs = rymc.split(" ");
		for (int i = 0; i < rymcs.length; i++) {
			if (i == 0) {
				rymcSql = rymcSql + "Y2.mc like '%" + rymcs[i] + "%'";
			} else {
				rymcSql = rymcSql + " or Y2.mc like'%" + rymcs[i] + "%'";
			}
		}
		rymcSql = rymcSql + ")";
		return rymcSql;
	}

	/**
	 * 生成角色sql语句
	 * @param ryjs 人员名称
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param bmjs 部门角色
	 * @return 角色sql
	 */
	public String getJsSql(List<Integer> ryjs, String dwbm, String gh, List<String> bmjs) {
		String jssql = "";
		if (1 == ryjs.get(0) || 0 == ryjs.get(0) || ryjs.contains(Constant.NUM_6)
				|| ryjs.contains(Constant.NUM_8)) {// 1检察长和0人事部和6为纪检和8考评委员会可以查看全部信息
			return "";
		} else if (2 == ryjs.get(0)) {// 分管领导
			String fgbmbm = (String)jcgSfdaCxService.cxFgBm(dwbm,gh).get("fgbmbm");
			int fgcount = (int)jcgSfdaCxService.cxFgBm(dwbm,gh).get("count");
			if (fgcount>0) {
				jssql = " and Y2.gh in( select gh from xt_qx_ryjsfp@tyyw where dwbm='"
						+ dwbm + "' and bmbm in (";
				jssql=jssql+fgbmbm;
				jssql = jssql + "))";
				return jssql;
			} else {
				jssql = " and Y2.gh=" + gh;
				return jssql;
			}
		} else if (Constant.NUM_3 == ryjs.get(0)) {//部门领导可以看所管部分的绩效
			jssql = " and Y2.gh in (select gh from xt_qx_ryjsfp@tyyw where dwbm='"
					+ dwbm + "' and bmbm in(";
			for (int i = 0; i < bmjs.size(); i++) {//001代表处长，002代表副处长
				if (bmjs.get(i).endsWith("001") || bmjs.get(i).endsWith("002")) {
					if (i == (bmjs.size() - 1)) {
						jssql = jssql + "'" + bmjs.get(i).split(",")[1] + "'";
					} else {
						jssql = jssql + "'" + bmjs.get(i).split(",")[1] + "',";
					}
				}
			}
			if (jssql.endsWith(",")) {
				jssql = jssql.substring(0, jssql.length() - 1);
			}
			jssql = jssql + "))";
			return jssql;
		} else {//其他可以看自己的
			jssql = " and Y2.gh=" + gh;
			return jssql;
		}
	}

	/**
	 * 获得当前时间,此人所在部门的业务类型
	 */
	@Override
	public Map<String, Object> getDqsj(String dwbm, String gh,
									   List<Integer> ryjs, List<String> bmjs) {
		// 此map用于返回参数
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 找到相应的相应的业务类型和业务编码
		// 没有相应的业务人员
		if (1 == ryjs.get(0) || 0 == ryjs.get(0)
				|| ryjs.contains(Constant.NUM_6) || ryjs.contains(Constant.NUM_8)) {
			resultMap.put("ywlxmcs", null);
		} else {
			//生成部门编码sql语句
			String bmbmsql = this.getBmSqlBybmjs(bmjs);
			//根据单位编码和部门编码sql语句生成部门映射语句
			Set<String> bmysSet = getBmysList(dwbm, bmbmsql);
			Map<String, Object> map = new HashMap<>();
			Set<Ywlxmc> ywlxSet = new HashSet<>();
			//根据部门映射集合循环找到对应业务类型
			List<Ywlxmc> list = new ArrayList<Ywlxmc>();
			for (String bmys : bmysSet) {
				map.clear();
				map.put("bmys", bmys);
				map.put("cursor", null);
				map.put("errmsg", null);
				try {
					grjxkhsyMapper.getYwlxbmByBmys(map);
				} catch (Exception e) {
					LOG.error(StringUtils.EMPTY, e);
				}
				list = (List<Ywlxmc>) map.get("cursor");
				ywlxSet.addAll(list);
			}
			resultMap.put("ywlxmcs", ywlxSet);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy,MM,dd");
		String dqsj = sdf.format(new Date());
		// 当前年
		String dqnf = dqsj.split(",")[0];
		// 当前月份
		String dqyf = dqsj.split(",")[1];
		int dqyfint = Integer.parseInt(dqyf);
		// 获得当前季度
		int dqjd = -1;
		if (dqyfint >= 2 && dqyfint <= Constant.NUM_4) {//2到4为第一季度
			dqjd = 1;
		} else if (dqyfint >= Constant.NUM_5 && dqyfint <= Constant.NUM_7) {//5到7为第一季度
			dqjd = 2;
		} else if (dqyfint >= Constant.NUM_8 && dqyfint <= Constant.NUM_10) {//8到10为第一季度
			dqjd = Constant.NUM_3;
		} else {//11到1为第四季度
			dqjd = Constant.NUM_4;
		}

		// 获得默认选择季度,对应年份(上一季度)
		if (dqjd - 1 == 0) {
			resultMap.put("mrjd", Constant.NUM_4);
			resultMap.put("mrnf", Integer.parseInt(dqnf) - 1);
		} else {
			resultMap.put("mrjd", dqjd - 1);
			resultMap.put("mrnf", dqnf);
		}
		// 获得当前年份和上四年
		List<Map<String, Integer>> nflist = new ArrayList<>();
		for (int i = 0; i < Constant.NUM_5; i++) {
			Map<String, Integer> map = new HashMap<>();
			map.put("label", (Integer.parseInt(dqnf) - Constant.NUM_4) + i);
			map.put("value", (Integer.parseInt(dqnf) - Constant.NUM_4) + i);
			nflist.add(map);
		}
		resultMap.put("nflist", nflist);
		return resultMap;
	}

	/**
	 * 获得相关部门信息
	 */
	@Override
	public List<Bmmc> getXgBm(String dwbm, String gh, List<Integer> ryjs,
							  String cxDwbm, List<String> bmjs) {
		// 该map用于传递参数
		Map<String, Object> map = new HashMap<>();
		List<Bmmc> returnList = new ArrayList<>();
		// 获得部门编码sql
		String bmbmsql = getBmbmsql(ryjs, bmjs, dwbm, gh, null);
		if (bmbmsql == null) {//如果部门sql为null则是可以查看全部
			map.put("cxdwbm", cxDwbm);
			map.put("bmys", null);
			map.put("cursor", null);
			map.put("errmsg", null);
			try {
				grjxkhsyMapper.getXgBm(map);
			} catch (Exception e) {
				LOG.error(StringUtils.EMPTY, e);
			}
			if ("1".equals(map.get("errmsg"))) {
				returnList = (List<Bmmc>) map.get("cursor");
			}
			return returnList;
		} else {
			//sql不为null
			Set<String> bmysSet = getBmysList(dwbm, bmbmsql);
			for (String bmys : bmysSet) {
				map.clear();
				map.put("cxdwbm", cxDwbm);
				map.put("bmys", bmys);
				map.put("cursor", null);
				map.put("errmsg", null);
				try {
					grjxkhsyMapper.getXgBm(map);
				} catch (Exception e) {
					LOG.error(StringUtils.EMPTY, e);
				}
				if ("1".equals(map.get("errmsg"))) {
					List<Bmmc> list = (List<Bmmc>) map.get("cursor");
					returnList.addAll(list);
				}
			}
			// 去重list
			Set<Bmmc> bmmcSet = new HashSet<>(returnList);
			returnList.clear();
			returnList.addAll(bmmcSet);
			return returnList;
		}
	}
	/**
	 * 根据单位编码和部门编码sql获得部门映射
	 * @param dwbm 单位编码
	 * @param bmbmsql 部门编码sql
	 * @return 返回部门映射集合
	 */
	public Set<String> getBmysList(String dwbm, String bmbmsql) {
		Map<String, Object> map = new HashMap<>();
		Set returnSet = new HashSet<>();
		map.put("dwbm", dwbm);
		map.put("bmbmsql", bmbmsql);
		map.put("cursor", null);
		map.put("errmsg", null);
		try {
			grjxkhsyMapper.getBmys(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		List<Bmmc> list = (List<Bmmc>) map.get("cursor");
		if (CollectionUtils.isNotEmpty(list)) {
			for (Bmmc bmmc : list) {

				if (Objects.isNull(bmmc)) {
					continue;
				}

				String bmys = bmmc.getBmys();

				if (StringUtils.isBlank(bmys)) {
					continue;
				}

				if (bmys.contains(",")) {// 如果部门映射有多个则拆分
					String[] bmyss = bmys.split(",");
					for (String string : bmyss) {
						returnSet.add(string);
					}
				} else {// 有一个则加入
					returnSet.add(bmys);
				}
			}
		}
		return returnSet;
	}

	/**
	 * 获得部门编码sql,注： 其他情况的返回检查官,分管领导,部门领导 jklx=1的时候返回中加入承办人
	 *
	 * @param ryjs 人员角色
	 * @param bmjs 部门角色
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param jklx 判断如何返回部门编码sql jklx为1则是返回处长以下的部门编码
	 * @return 返回部门编码sql
	 */
	public String getBmbmsql(List<Integer> ryjs, List<String> bmjs, String dwbm,
							 String gh, String jklx) {
		String bmbmsql = "";
		if (ryjs.get(0) == 1) {// 检查官
			return null;
		} else if (ryjs.get(0) == 2) {// 分管领导
			String fgbmbm = (String)jcgSfdaCxService.cxFgBm(dwbm,gh).get("fgbmbm");
			int fgcount = (int)jcgSfdaCxService.cxFgBm(dwbm,gh).get("count");
			Map<String, Object> map = new HashMap<>();
			map.put("dwbm", dwbm);
			map.put("gh", gh);
			map.put("cursor", null);
			map.put("errmsg", null);
			try {
				grjxkhsyMapper.getZjldbmbm(map);
			} catch (Exception e) {
				LOG.error(StringUtils.EMPTY, e);
			}
			List<Bmmc> bmbms = new ArrayList<>();
			if ("1".equals(map.get("errmsg"))) {
				bmbms = (List<Bmmc>) map.get("cursor");
			}
			if (fgcount==0) {
				return "and bmbm in (\'\')";
			} else {
				bmbmsql = "and bmbm in (";
				bmbmsql=bmbmsql+fgbmbm;
				// 如果是以,结尾则截取
				if (bmbmsql.endsWith(",")) {
					bmbmsql = bmbmsql.substring(0, bmbmsql.length() - 1);
				}
				return bmbmsql + ")";
			}
		} else if (ryjs.get(0) == Constant.NUM_3) {//部门领导
			bmbmsql = "and bmbm in (";
			if ("1".equals(jklx)) {//为一该部门领导兼职的所有部门都要返回
				for (int i = 0; i < bmjs.size(); i++) {
					if (i == (bmjs.size() - 1)) {
						bmbmsql = bmbmsql + "'" + bmjs.get(i).split(",")[1] + "'";
					} else {
						bmbmsql = bmbmsql + "'" + bmjs.get(i).split(",")[1] + "',";
					}
				}
			}else{//不为只返回所领导部门的部门编码
				for (int i = 0; i < bmjs.size(); i++) {
					if (bmjs.get(i).endsWith("001") || bmjs.get(i).endsWith("002")) {
						if (i == (bmjs.size() - 1)) {
							bmbmsql = bmbmsql + "'" + bmjs.get(i).split(",")[1] + "'";
						} else {
							bmbmsql = bmbmsql + "'" + bmjs.get(i).split(",")[1] + "',";
						}
					}
				}
			}
			// 如果是以,结尾则截取
			if (bmbmsql.endsWith(",")) {
				bmbmsql = bmbmsql.substring(0, bmbmsql.length() - 1);
			}
			return bmbmsql + ")";
		}
		if ("1".equals(jklx)) {//若为其他人则在jklx为1的时候返回所在部门编码
			bmbmsql = "and bmbm in (";
			for (int i = 0; i < bmjs.size(); i++) {
				if (i == (bmjs.size() - 1)) {
					bmbmsql = bmbmsql + "'" + bmjs.get(i).split(",")[1] + "'";
				} else {
					bmbmsql = bmbmsql + "'" + bmjs.get(i).split(",")[1] + "',";
				}
			}
			// 如果是以,结尾则截取
			if (bmbmsql.endsWith(",")) {
				bmbmsql = bmbmsql.substring(0, bmbmsql.length() - 1);
			}
			return bmbmsql + ")";
		}
		return bmbmsql;
	}

	/**
	 * 返回考核行数
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @return 考核行数
	 */
	@Override
	public int getkhcount(String dwbm, String gh) {
		Map<String, Object> map = new HashMap<>();
		map.put("dwbm", dwbm);
		map.put("gh", gh);
		map.put("count", null);
		map.put("errmsg", null);
		try {
			grjxkhsyMapper.getkhcount(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		if ("1".equals(map.get("errmsg"))) {
			return (int) map.get("count");
		}
		return -1;
	}

	/**
	 * 通过部门角色
	 * @param bmjs 部门角色列表
	 * @return bmSql语句
	 */
	private String getBmSqlBybmjs(List<String> bmjs){
		String bmbmsql = "and bmbm in (";
		for (int i = 0; i < bmjs.size(); i++) {
			if (i == bmjs.size() - 1) {
				bmbmsql = bmbmsql + "'" + bmjs.get(i).split(",")[1] + "'";
			} else {
				bmbmsql = bmbmsql + "'" + bmjs.get(i).split(",")[1] + "',";
			}

		}
		// 如果是以,结尾则截取
		if (bmbmsql.endsWith(",")) {
			bmbmsql = bmbmsql.substring(0, bmbmsql.length() - 1);
		}
		return bmbmsql + ")";
	}

	@Override
	public String getYwkhfzidByYdkhid(String ydkhid) {
		String ywkhfzid = "";
		try {
			ywkhfzid = grjxkhsyMapper.getYwkhfzidByYdkhid(ydkhid);
		} catch (Exception e) {
			LOG.error("通过月度考核id查询业务考核分值id出错，错误信息：", e);
		}
		return ywkhfzid;
	}

	@Override
	public String getBmmcByDwbmAndBmbm(String dwbm, String bmbm) {
		String bmmc = "";
		try {
			bmmc = treeSelectMapper.getBmmcByBmbm(dwbm, bmbm);
		} catch (Exception e) {
			LOG.error("通过单位编码和部门编码获取部门名称出错，错误信息：", e);
		}
		return bmmc;
	}

	@Override
	public Map<String, Object> queryGrjxByCondition(String loger_dwbm, String loger_gh,
													String loger_bmbm, List<String> loger_bmyslist,
													List<String> loger_bmjs, String query_dwbm,
													String query_bmbm, String query_sfgs,
													String query_year, String query_kssj,
													String query_jssj, String query_name, Integer page) {

		Map<String, Object> resultMap = new HashMap<>();
		List<Map<String, Object>> grjxList = null;
		List<Map<String, Object>> rankList = null;

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
		List<String> queryBmbmList = new ArrayList<>();
		List<String> logerBmbmList = new ArrayList<>();

		// 当前人员所在的所有部门编码
		List<Map<String, Object>> allBmbmList = sfdacjMapper.getAllBmbm(loger_dwbm, loger_gh);
		StringBuilder bmbmsStr = new StringBuilder();
		for (Map<String, Object> bmbmMap : allBmbmList) {
			bmbmsStr.append(bmbmMap.get("BMBM") + ",");
		}
		// 去除最后一个,
		bmbmsStr.deleteCharAt(bmbmsStr.length() - 1);
		String[] logerBmbmArr = bmbmsStr.toString().split(",");

		for (int i = 0; i < logerBmbmArr.length; i++) {
			logerBmbmList.add(logerBmbmArr[i]);
		}

		// 每页显示条数
		Integer rows = 10;

		// 如果查询日期为空，代表初次加载个人绩效，年份应该去获取考核日期配置的最新年份
		if (StringUtils.isBlank(query_year)) {
			XtGrjxKhrq khrqpz = xtGrjxKhrqService.getGrjxKhrqPzLatest();
			if(!Objects.isNull(khrqpz)){
				query_year = khrqpz.getKsrq().substring(0, 4);
			}
		}

		//查询是否有分管部门以确定是否是分管领导
		int fgcount = (int)jcgSfdaCxService.cxFgBm(loger_dwbm,loger_gh).get("count");

		if (fgcount > 0) {
			ryjs.add("2");// 分管领导
		}

		for (int i = 0; i < loger_bmyslist.size(); i++) {
			String bmysTemp = loger_bmyslist.get(i); // 临时部门映射
			// 部门角色：460000,0134,001（单位编码，部门编码，角色编码）
			String[] bmjsStrArray = loger_bmjs.get(i).split(",");
			String jsbm = bmjsStrArray[2]; // 角色编码

			if (bmysTemp == null || bmysTemp.equals("")) { // 没有部门映射的部门
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
							ryjs.add("1"); // 检察长
						} else if (StringUtils.contains(jsmc, "处长")
								|| StringUtils.contains(jsmc, "副处长")
								|| StringUtils.contains(jsmc, "局长")
								|| StringUtils.contains(jsmc, "副局长")
								|| StringUtils.contains(jsmc, "部长")
								|| StringUtils.contains(jsmc, "副部长")) {
							ryjs.add("3"); // 负责人
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
			} /*else if (bmysTemp.contains("1100")) {
				ryjs.add("7");// 案管
			}*/ else if (bmysTemp.contains("0000")) { // 院领导
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
					String fgbmbm = (String)jcgSfdaCxService.cxFgBm(loger_dwbm,loger_gh).get("fgbmbm");
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
			bmyss = jcgSfdaCxService.selectDwbmBmys(loger_dwbm, bmbm);// 通过登录单位编码和部门编码找部门映射
			bmbm = jcgSfdaCxService.selectBmysBm(query_dwbm, bmyss);// 通过查询的单位和当前登录人的部门映射找对应的部门
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
			if ("1".equals(qx)) { // 检察长
				// 如果查询部门编码为空，则表示查询当前单位所有人的个人绩效
				if (StringUtils.isBlank(query_bmbm)) {
					bmbmList.clear();
					bmbmList = null;
				} else {
					bmbmList.clear();
					bmbmList.add(query_bmbm);
				}
				grjxList = grjxkhsyMapper.getGrjxByCondition(query_dwbm, bmbmList, "", query_sfgs, query_year,
						query_kssj, query_jssj, query_name);

			} else if ("2".equals(qx)) { // 分管院领导、部门负责人

				if (StringUtils.isBlank(query_bmbm)) {
					queryBmbmList = bmbmList;
				} else {
					for (int i = 0; i < bmbmArr.length; i++) {
						if (bmbmArr[i].equals(query_bmbm)) {
							queryBmbmList.clear();
							queryBmbmList.add(query_bmbm);
						}
					}
				}

				if (queryBmbmList.size() == 0) {
					queryBmbmList.add("");
				}

				grjxList = grjxkhsyMapper.getGrjxByCondition(query_dwbm, queryBmbmList, "", query_sfgs, query_year,
						query_kssj, query_jssj, query_name);
			} else { // 普通人，普通人只能查看自己的个人绩效信息，默认显示所有
				if (StringUtils.isBlank(query_bmbm)) {
					bmbmList.clear();
					bmbmList = null;
				} else {
					// 登录人可能存在于多个部门，只要查询部门编码在登录人所在部门集合之中，就能查到
					if (logerBmbmList.contains(query_bmbm)) {
						bmbmList.clear();
						bmbmList = logerBmbmList;
					} else {
						bmbmList.clear();
						bmbmList.add("");
					}
				}
				grjxList = grjxkhsyMapper.getGrjxByCondition(query_dwbm, bmbmList, loger_gh, query_sfgs, query_year,
						query_kssj, query_jssj, query_name);
			}

			// 个人绩效排名
			rankList = grjxkhsyMapper.getRankOfGrjx(query_dwbm, query_year);
			// 将排名放进个人绩效信息中
			for (Map<String, Object> grjxMap : grjxList) {
				for (Map<String, Object> rankMap : rankList) {
					if (grjxMap.get("YDKHID").equals(rankMap.get("YDKHID"))) {
						grjxMap.put("PM", rankMap.get("PM"));
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		resultMap.put("count", pager.getTotal());
		resultMap.put("jxdfs", grjxList);
		resultMap.put("userPermissions", qx);
		resultMap.put("deptList", bmbmList);
		resultMap.put("queryDeptList", queryBmbmList);
		return resultMap;
	}

	@Override
	public Map<String, Object> getGrjxOfIndex(String dwbm, String gh, List<String> bmysList, List<String> bmjsList) {

		Map<String, Object> resultMap = new HashMap<>();
		List<Map<String, Object>> grjxList = null;

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
		List<String> logerBmbmList = new ArrayList<>();

		// 当前人员所在的所有部门编码
		List<Map<String, Object>> allBmbmList = sfdacjMapper.getAllBmbm(dwbm, gh);
		StringBuilder bmbmsStr = new StringBuilder();
		for (Map<String, Object> bmbmMap : allBmbmList) {
			bmbmsStr.append(bmbmMap.get("BMBM") + ",");
		}
		// 去除最后一个,
		bmbmsStr.deleteCharAt(bmbmsStr.length() - 1);
		String[] logerBmbmArr = bmbmsStr.toString().split(",");

		for (int i = 0; i < logerBmbmArr.length; i++) {
			logerBmbmList.add(logerBmbmArr[i]);
		}

		// 如果查询日期为空，代表初次加载个人绩效，年份应该去获取考核日期配置的最新年份
		String queryYear = "";
		XtGrjxKhrq khrqpz = xtGrjxKhrqService.getGrjxKhrqPzLatest();
		if(!Objects.isNull(khrqpz)){
			queryYear = khrqpz.getKsrq().substring(0, 4);
		}

		//查询是否有分管部门以确定是否是分管领导
		int fgcount = (int)jcgSfdaCxService.cxFgBm(dwbm,gh).get("count");

		if (fgcount > 0) {
			ryjs.add("2");// 分管领导
		}

		for (int i = 0; i < bmysList.size(); i++) {
			String bmysTemp = bmysList.get(i); // 临时部门映射
			// 部门角色：460000,0134,001（单位编码，部门编码，角色编码）
			String[] bmjsStrArray = bmjsList.get(i).split(",");
			String jsbm = bmjsStrArray[2]; // 角色编码

			if (bmysTemp == null || bmysTemp.equals("")) { // 没有部门映射的部门
				// 没有部门映射的先根据loger_jsbm中的角色名称来判断
				try {
					loger_jsbm = loginService.getgetBmJs(dwbm, gh);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (loger_jsbm.size() > 0) {
					for (int j = 0; j < loger_jsbm.size(); j++) {
						String jsmc = ((JSBM) loger_jsbm.get(j)).getJsmc();
						if (StringUtils.contains(jsmc, "检察长")) {
							ryjs.add("1"); // 检察长
						} else if (StringUtils.contains(jsmc, "处长")
								|| StringUtils.contains(jsmc, "副处长")
								|| StringUtils.contains(jsmc, "局长")
								|| StringUtils.contains(jsmc, "副局长")) {
							ryjs.add("3"); // 负责人
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
			} /*else if (bmysTemp.contains("1100")) {
				ryjs.add("7");// 案管
			}*/ else if (bmysTemp.contains("0000")) { // 院领导
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
					String fgbmbm = (String)jcgSfdaCxService.cxFgBm(dwbm,gh).get("fgbmbm");
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

		bmbm = bmbm.replace("(", "").
				replace(")", "").
				replaceAll("'", "");
		bmbmArr = bmbm.split(",");
		for (int i = 0; i < bmbmArr.length; i++) {
			// 部门负责人和分管领导所能看的部门
			bmbmList.add(bmbmArr[i]);
		}

		try {
			if ("1".equals(qx)) { // 检察长
				bmbmList = null;
				grjxList = grjxkhsyMapper.getGrjxOfIndex(dwbm, bmbmList, queryYear);
			} else if ("2".equals(qx)) { // 分管院领导、部门负责人
				grjxList = grjxkhsyMapper.getGrjxOfIndex(dwbm, bmbmList, queryYear);
			} else { // 普通人，普通人只能查看自己所在部门的绩效考核，可能存在多个部门
				grjxList = grjxkhsyMapper.getGrjxOfIndex(dwbm, logerBmbmList, queryYear);
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		resultMap.put("jxdfInfo", grjxList);
		return resultMap;
	}

	@Override
	public Map<String, Object> exportAll(String dwbm, String dwmc, String bmmc, String bmbm, String sfgs, String year, String kssj, String jssj,
										 String name, String[] deptList, String[] queryDeptList, String userPermissions, String nameOfExporter) {

		List<Map<String, Object>> grjxList = new ArrayList<>();
		List<Map<String, Object>> rankList = null;
		List<String> bmbmList = new ArrayList<>();
		List<String> queryList = new ArrayList<>();
		// 检察官人数
		int countsOfJcg = 0;
		// 检察辅助人员人数
		int countsOfJcfzry = 0;
		// 司法行政人员人数
		int countsOfSfxzry = 0;
		// 一等次人数
		int countsOfNo1 = 0;
		// 二等地人数
		int countsOfNo2 = 0;
		// 三等地人数
		int countsOfNo3 = 0;
		// 四等地人数
		int countsOfNo4 = 0;
		// 暂未评定等次人数
		int countsOfNull = 0;


		if (deptList.length > 0) {
			for (int i = 0; i < deptList.length; i++) {
				bmbmList.add(deptList[i]);
			}
		}
		if (queryDeptList.length > 0) {
			for (int i = 0; i < queryDeptList.length; i++) {
				queryList.add(queryDeptList[i]);
			}
		}

		// 得到数据
		try {
			if (StringUtils.equals(userPermissions, "1")) {
				grjxList = grjxkhsyMapper.getGrjxByCondition(dwbm, bmbmList, "", sfgs, year, kssj, jssj, name);
			} else if (StringUtils.equals(userPermissions, "2")) {
				if (queryList.size() == 0) {
					queryList.add("");
				}
				grjxList = grjxkhsyMapper.getGrjxByCondition(dwbm, queryList, "", sfgs, year, kssj, jssj, name);
			}

			// 个人绩效排名
			rankList = grjxkhsyMapper.getRankOfGrjx(dwbm, year);
			// 将排名放进个人绩效信息中
			for (Map<String, Object> grjxMap : grjxList) {
				for (Map<String, Object> rankMap : rankList) {
					if (grjxMap.get("YDKHID").equals(rankMap.get("YDKHID"))) {
						grjxMap.put("PM", rankMap.get("PM"));
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		final String DEFAULT_EXCEL_PATH = xtfjpathservice.getPath().trim();
		// 文件后缀名
		final String DEFAULT_EXCEL_SUBFIX = ".xls";
		// 文件名称
		String fileName = StringUtils.EMPTY;

		if (StringUtils.isNotBlank(bmmc)) {
			fileName = dwmc + "-" + bmmc + year + "年个人绩效考核统计（导出人：" + nameOfExporter + "）";
		} else {
			fileName = dwmc + year + "年个人绩效考核统计（导出人：" + nameOfExporter + "）";
		}

		// 判断文件是否存在  存在则生成新的文件
		// 无需判断，如果文件存在，会自动在文件名后生成(数字)
//		File file = new File(DEFAULT_EXCEL_PATH + fileName + DEFAULT_EXCEL_SUBFIX);

		// 文件地址
		String filePath = DEFAULT_EXCEL_PATH + fileName + DEFAULT_EXCEL_SUBFIX;
		// excel工作簿名
		String sheetName = "";
		// excel标题，即表格中第一行
		String titleName = "";
		if (StringUtils.isNotBlank(bmmc)) {
			sheetName = dwmc + "-" + bmmc + year + "年个人绩效考核统计";
			titleName = dwmc + "-" + bmmc + year + "年个人绩效考核统计";
		} else {
			sheetName = dwmc + year + "年个人绩效考核统计";
			titleName = dwmc + year + "年个人绩效考核统计";
		}

		// 组装表头表体数据成JsonArray
		JsonArray jsonArr = new JsonArray();

		// 第二行列名
		JsonObject jsonObjLine2 = new JsonObject();
		jsonObjLine2.addProperty("line", 2);
		jsonObjLine2.addProperty("length", 9);
		jsonObjLine2.addProperty("coloum1", "人员总数");
		jsonObjLine2.addProperty("coloum2", "检察官人数");
		jsonObjLine2.addProperty("coloum3", "检查辅助人员人数");
		jsonObjLine2.addProperty("coloum4", "司法行政人员人数");
		jsonObjLine2.addProperty("coloum5", "一等次人数");
		jsonObjLine2.addProperty("coloum6", "二等次人数");
		jsonObjLine2.addProperty("coloum7", "三等次人数");
		jsonObjLine2.addProperty("coloum8", "四等次人数");
		jsonObjLine2.addProperty("coloum9", "暂未评定人数");
		jsonArr.add(jsonObjLine2);

		// 第三行数据
		for (Map<String, Object> map : grjxList) {
			if (StringUtils.equals(map.get("RYLXMC").toString(), "检察官")) {
				countsOfJcg ++ ;
			}
			if (StringUtils.equals(map.get("RYLXMC").toString(), "检察辅助人员")) {
				countsOfJcfzry ++ ;
			}
			if (StringUtils.equals(map.get("RYLXMC").toString(), "司法行政人员")) {
				countsOfSfxzry ++ ;
			}
			if (!Objects.isNull(map.get("PDJBMC"))) {
				if (StringUtils.equals(map.get("PDJBMC").toString(), "一等次")) {
					countsOfNo1 ++;
				} else if (StringUtils.equals(map.get("PDJBMC").toString(), "二等次")) {
					countsOfNo2 ++;
				} else if (StringUtils.equals(map.get("PDJBMC").toString(), "三等次")) {
					countsOfNo3 ++;
				} else if (StringUtils.equals(map.get("PDJBMC").toString(), "四等次")) {
					countsOfNo4 ++;
				}
			} else {
				countsOfNull ++;
			}
		}

		JsonObject jsonObjLine3 = new JsonObject();
		jsonObjLine3.addProperty("line", 3);
		jsonObjLine3.addProperty("length", 9);
		jsonObjLine3.addProperty("coloum1", grjxList.size());
		jsonObjLine3.addProperty("coloum2", countsOfJcg);
		jsonObjLine3.addProperty("coloum3", countsOfJcfzry);
		jsonObjLine3.addProperty("coloum4", countsOfSfxzry);
		jsonObjLine3.addProperty("coloum5", countsOfNo1);
		jsonObjLine3.addProperty("coloum6", countsOfNo2);
		jsonObjLine3.addProperty("coloum7", countsOfNo3);
		jsonObjLine3.addProperty("coloum8", countsOfNo4);
		jsonObjLine3.addProperty("coloum9", countsOfNull);
		jsonArr.add(jsonObjLine3);

		// 第五行列名
		JsonObject jsonObjLine5 = new JsonObject();
		jsonObjLine5.addProperty("line", 5);
		jsonObjLine5.addProperty("length", 8);
		jsonObjLine5.addProperty("coloum1", "单位名称");
		jsonObjLine5.addProperty("coloum2", "考核部门");
		jsonObjLine5.addProperty("coloum3", "姓名");
		jsonObjLine5.addProperty("coloum4", "工号");
		jsonObjLine5.addProperty("coloum5", "人员类型");
		jsonObjLine5.addProperty("coloum6", "个人绩效得分");
		jsonObjLine5.addProperty("coloum7", "评定等级");
		jsonObjLine5.addProperty("coloum8", "部门排名");
		jsonArr.add(jsonObjLine5);

		// 第六行及以后的数据
		for (int i = 0; i < grjxList.size(); i++) {
			JsonObject jsonObj = new JsonObject();
			jsonObj.addProperty("line", 6 + i);
			jsonObj.addProperty("length", 8);
			jsonObj.addProperty("coloum"+(1), grjxList.get(i).get("DWMC").toString());
			jsonObj.addProperty("coloum"+(2), grjxList.get(i).get("BMLBMC").toString());
			jsonObj.addProperty("coloum"+(3), grjxList.get(i).get("MC").toString());
			jsonObj.addProperty("coloum"+(4), grjxList.get(i).get("GH").toString());
			jsonObj.addProperty("coloum"+(5), grjxList.get(i).get("RYLXMC").toString());
			if (grjxList.get(i).containsKey("ZPJDF")) {
				jsonObj.addProperty("coloum"+(6), grjxList.get(i).get("ZPJDF").toString());
			} else {
				jsonObj.addProperty("coloum"+(6), "");
			}
			if (grjxList.get(i).containsKey("PDJBMC")) {
				jsonObj.addProperty("coloum"+(7), grjxList.get(i).get("PDJBMC").toString());
			} else {
				jsonObj.addProperty("coloum"+(7), "");
			}
			if (grjxList.get(i).containsKey("PM")) {
				jsonObj.addProperty("coloum"+(8), grjxList.get(i).get("PM").toString());
			} else {
				jsonObj.addProperty("coloum"+(8), "");
			}

			jsonArr.add(jsonObj);
		}

		// 调用专用excel导出方法
		try {
			OutputStream outXlsx = new FileOutputStream(filePath);
			// false表示不需要合并单元格
			ExcelUtil.exportExcelNotUtils(outXlsx,sheetName,titleName,jsonArr,false);
			outXlsx.flush();
			outXlsx.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("filename", fileName + DEFAULT_EXCEL_SUBFIX);
		map.put("filepath", filePath);
		map.put("counts", grjxList.size());
		return map;
	}
}
