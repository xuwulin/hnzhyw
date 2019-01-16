package com.swx.ibms.business.archives.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swx.ibms.business.archives.bean.SfdaRyjn;
import com.swx.ibms.business.archives.mapper.*;
import com.swx.ibms.business.cases.mapper.AgbAjslMapper;
import com.swx.ibms.business.cases.mapper.AjxxcxMapper;
import com.swx.ibms.business.cases.service.AjxxcxService;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.common.utils.DateUtil;
import com.swx.ibms.common.utils.Identities;
import com.swx.ibms.common.utils.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
*@author:徐武林
*@date:2018年3月28日下午5:39:32
*@Description:
*/
@SuppressWarnings("all")
@Service("SfdaRyjnService")
public class SfdaRyjnServiceImpl implements SfdaRyjnService {
	
	/**
	 * 司法档案下的荣誉技能Mapper
	 */
	@Autowired
	private SfdaRyjnMapper sfdaRyjnMapper;

	@Autowired
	private AjxxcxService ajxxcxService;

	@Autowired
	private AjxxcxMapper ajxxcxMapper;

	@Autowired
	private AgbAjslMapper agbAjslMapper;

    @Autowired
    private SfdaZrzjMapper sfdaZrzjMapper;

    @Autowired
    private SfdaZycsMapper sfdaZycsMapper;

    @Autowired
    private SfdaQtqkMapper sfdaQtqkMapper;

    @Autowired
    private SfdaJdqkMapper sfdaJdqkMapper;

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;
	
	/**
	 * 展示所有荣誉技能（根据档案id）
	 */
	@Override
	public Map getSfdaRyjnPageList(SfdaRyjn ryjn, int start, int end) {
		Map<String, Object> map = new HashMap<String, Object>(Constant.NUM_10);
		Map<String, Object> resMap = new HashMap<String, Object>(Constant.NUM_10);
		map.put("p_gdid", ryjn.getGdid());
		map.put("p_start", start);//开始页
		map.put("p_end", end);//结束页
		map.put("p_total", StringUtils.EMPTY);//总记录
		map.put("p_cursor", StringUtils.EMPTY);//cursor
		try {
			sfdaRyjnMapper.getSfdaRyjnPageList(map);
			resMap.put("p_total", Integer.parseInt(map.get("p_total").toString()));
			resMap.put("p_cursor",map.get("p_cursor"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resMap;
	}
	
	/**
	 * 新增荣誉
	 */
	@Override
	public String addSfdaRyjn(SfdaRyjn ryjn) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map.put("p_id", Identities.get32LenUUID());
		// 去掉前后空格，和将英文状态的单引号和双引号转义
		map.put("p_rymc", StrUtil.strTransform(ryjn.getRymc()));
		map.put("p_hjsj", ryjn.getHjsj());
		map.put("p_bjdw", StrUtil.strTransform(ryjn.getBjdw()));
		map.put("p_hjsy", StrUtil.strTransform(ryjn.getHjsy()));
		map.put("p_hjqtqk", StrUtil.strTransform(ryjn.getHjqtqk()));
		map.put("p_gdid", ryjn.getGdid());
		map.put("p_hjdjbm",Identities.randomString(5,true));
		map.put("p_hjdj", ryjn.getHjdj());
		map.put("p_sfsc", 'N');
		map.put("p_zhgxbm", Constant.CURRENT_USER.get().getBmbm());
		map.put("p_zhgxr", Constant.CURRENT_USER.get().getGh());
		map.put("p_zhgxsj", DateUtil.getSysCurrentDateTime());
		map.put("Y", StringUtils.EMPTY);
		sfdaRyjnMapper.addSfdaRyjn(map);
		
		map1.put("Y", map.get("Y"));

		String sign = (String) map.get("Y");
		if ("1".equals(sign)) {
			// 日志记录
			logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "新增荣誉技能成功");
		} else {
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, "新增荣誉技能失败");
		}

		return Constant.GSON.toJson(map1);
	}

	/**
	 * 修改荣誉技能
	 */
	@Override
	public String updateSfdaRyjn(SfdaRyjn ryjn) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_id", ryjn.getId());
		// 去掉前后空格，和将英文状态的单引号和双引号转义
		map.put("p_rymc", StrUtil.strTransform(ryjn.getRymc()));
		map.put("p_hjsj", ryjn.getHjsj());
		map.put("p_bjdw", StrUtil.strTransform(ryjn.getBjdw()));
		map.put("p_hjsy", StrUtil.strTransform(ryjn.getHjsy()));
		map.put("p_hjqtqk", StrUtil.strTransform(ryjn.getHjqtqk()));
		map.put("p_hjdj", ryjn.getHjdj());
		map.put("p_zhgxbm", Constant.CURRENT_USER.get().getBmbm());
		map.put("p_zhgxr", Constant.CURRENT_USER.get().getGh());
		map.put("p_zhgxsj", DateUtil.getSysCurrentDateTime());
		map.put("Y", StringUtils.EMPTY);
		
		try {
			sfdaRyjnMapper.updateSfdaRyjn(map);
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
	 * 删除荣誉技能
	 */
	@Override
	public String deleteSfdaRyjn(SfdaRyjn ryjn) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_id", ryjn.getId());
		map.put("Y", StringUtils.EMPTY);
		try {
			sfdaRyjnMapper.deleteSfdaRyjn(map);
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

	@Override
	public String getAllInfoOfProcurator(String daid,String gh, String dwbm, String bmbm, String kssj, String jssj,boolean isag,String flag) throws Exception {
		List<Map<String, Object>> ghAndMc = null;
		String daId = null;
		List<String> daIds = new ArrayList<>();
		Map<String, Object> count = null;
		List<Map<String, Object>> counts = new ArrayList<>();
		Map<String, Object> basl = null;
		Map<String, Object> result = new HashMap<>();
		int slOfAjxx = 0;

		String kssj_nyr = kssj + "-01";
		String jssj_nyr = jssj + "-31";

		if (flag.equals("2")){ // 本部门的荣誉奖励等情况数量，办案数量
			try {
				//1.根据单位编码，工号获取该部门下当前年已经创建档案的工号和名称
				ghAndMc = sfdaRyjnMapper.getAllGhAndMc(dwbm, bmbm, kssj, jssj);

				//2.根据单位编码，工号，档案开始时间，档案结束时间，查询档案的id
				int size = ghAndMc.size();
				for(int i=0;i<size;i++){
					daId = sfdaRyjnMapper.getAllDaid(dwbm, ghAndMc.get(i).get("GH").toString(), kssj, jssj);
					if(StringUtils.isNotEmpty(daId)){
						daIds.add(daId);
					}else{
						daIds.add("");
					}

					//3.根据档案id查询荣誉奖励，责任追究等数量
					count = sfdaRyjnMapper.getCounts(daIds.get(i));

					//4.根据单位编码，工号，开始时间，结束时间查询办案总数
					//tyyw_gg_ajjbxx_tyyw表和yx_sfda_ajxx表（已审批）
					basl = ajxxcxService.selectAjblEj(dwbm, bmbm,  ghAndMc.get(i).get("GH").toString(), "", 1, 10, kssj_nyr, jssj_nyr, "2");

					//将办案数量（两表之和）加入到count中
					count.put("BASL", Integer.parseInt(basl.get("total").toString()));

					//将名称加入到count中
					count.put("MC", ghAndMc.get(i).get("MC").toString());
					counts.add(count);
				}
				//排序前
//			for (Map<String, Object> map : counts) {
//	            System.out.println(map.get("BASL"));
//	        }
				// 之前是按照办案数量排序，现在是按照领导--副领导--检察官排序
				/*Collections.sort(counts, new Comparator<Map<String,Object>>() {
					@Override
					public int compare(Map<String, Object> o1, Map<String, Object> o2) {
						//进行判断,按照办案数量倒叙排列
						Integer name1 = Integer.valueOf(o1.get("BASL").toString());
						Integer name2 = Integer.valueOf(o2.get("BASL").toString());
						return name2.compareTo(name1);//如果要升序排列，则为：name1.compareTo(name2)
					}
				});*/

				//排序后
//			for(Map<String,Object> m:counts){
//				System.out.println("Map[MC="+m.get("MC")+"basl="+m.get("BASL")+"]");
//			}

				result.put("data", counts);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{ // 本人的荣誉奖励情况数量，办案数量
			try{
				//荣誉奖励等情况数量
				count = sfdaRyjnMapper.getCounts(daid);

				//办案数量tyyw_gg_ajjbxx_tyyw表和yx_sfda_ajxx表（已审批）
				kssj = kssj + "-01"; // 查询案件开始日期
				jssj = jssj + "-31"; // 查询案件结束日期
				basl = ajxxcxService.selectAjblEj(dwbm, bmbm,  gh, "",1, 10,  kssj_nyr, jssj_nyr, "2");

				//将办案数量加入到count中
				count.put("BASL", Integer.parseInt(basl.get("total").toString()));
				result.put("data", count);

			}catch (Exception e){
				e.printStackTrace();
			}
		}

		return JSONObject.toJSONString(result);//这里返回的是已经排好序的结果
	}

	@Override
	public Map<String, Object> serachByType(String dalx, String daid, String kssj, String jssj, Integer page, Integer rows) throws Exception {

		if (kssj.equals("") || jssj.equals("")) {
			kssj = DateUtil.getYear(new Date()) + "-01-01";
			jssj = DateUtil.getYear(new Date()) + "-12-31";
		}

		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> resMap = new HashMap<>();
		// PageHelper只对该语句后的第一条查询语句起作用
		Page pager = PageHelper.startPage(page, rows);
		try {
		    // 荣誉奖励
			if (dalx.equals("1")) {
				list = sfdaRyjnMapper.serachRyjnByType(daid, kssj, jssj);
				// 责任追究
			} else if (dalx.equals("2")) {
				list = sfdaZrzjMapper.serachZrzjByType(daid, kssj, jssj);
			    // 职业操守
			} else if (dalx.equals("3")) {
				list = sfdaZycsMapper.serachZycsByType(daid, kssj, jssj);
			    // 其他情况
			} else if (dalx.equals("4")) {
				list = sfdaQtqkMapper.serachQtqkByType(daid, kssj, jssj);
			    // 接受监督
			} else {
				list = sfdaJdqkMapper.serachJdqkByType(daid, kssj, jssj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		resMap.put("rows", list);
		resMap.put("total", pager.getTotal());
		return resMap;
	}


}
