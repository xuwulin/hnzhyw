package com.swx.ibms.business.cases.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swx.ibms.business.archives.mapper.SfdacjMapper;
import com.swx.ibms.business.cases.mapper.AjxxcxMapper;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.common.utils.DateUtil;
import com.swx.ibms.common.utils.Identities;
import com.swx.ibms.common.utils.ListCastUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
*<p>Title:AjxxcxServiceImpl</p>
*<p>Description: 案件信息查询</p>
*author 朱春雨
*date 2017年3月17日 下午2:07:07
*/
@SuppressWarnings("all")
@Service("AjxxcxService")
public class AjxxcxServiceImpl implements AjxxcxService{
	/**
	 * 分页显示个数
	 */
	private static final int SHOWNUMBERS=16;
	/**
	 * 数据库接口
	 */
	@Resource
	private AjxxcxMapper ajxxcxMapper;

	@Resource
	private SfdacjMapper sfdacjMapper;

	@Override
	public Map selectlistAjxxcx(String p_year, String p_bmsah, String p_cbdw_bm, int page) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("p_year",p_year);
		map.put("p_bmsah", p_bmsah);
		map.put("p_cbdw_bm", p_cbdw_bm);
		int p_bottom=(page-1)*SHOWNUMBERS+1;
		int p_top=page*SHOWNUMBERS;
		map.put("p_bottom", Integer.toString(p_bottom));
		map.put("p_top",Integer.toString(p_top));
		map.put("p_cur",null);
		map.put("p_count",'0');
		try {
			ajxxcxMapper.selectlistAjxxcx(map);
		} catch (Exception e) {
			// TODO: handle exception
		}
		Map<String,Object> returnMap=new HashMap<>();
		returnMap.put("data",map.get("p_cur"));
		returnMap.put("count",map.get("p_count"));
		return returnMap;
	}

	@Override
	public Map<String, Object> getYwkhXzjcAj(String dwbm, String kssj, String jssj, String ajlbbmstr, String type,Integer page,Integer pageSize) {
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_9);
		Map<String,Object> returnMap = new HashMap<String,Object>(Constant.NUM_9);

		int nowPage = (0==page? Constant.NUM_1:page);
        int pageStart = (nowPage - Constant.NUM_1) * pageSize;
        int pageEnd = pageStart+pageSize;
        pageStart++;

		map.put("p_dwbm", dwbm);
		map.put("p_kssj", kssj);
		map.put("p_jssj", jssj);
		map.put("p_ajlbbmStr", ajlbbmstr);
		map.put("p_type", type);
		map.put("p_start", pageStart);
		map.put("p_end", pageEnd);
		map.put("p_count", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);
		try {
			ajxxcxMapper.getYwkhXzjcAj(map);
			returnMap.put("p_type", map.get("p_type").toString());
			returnMap.put("p_count", map.get("p_count").toString());
			returnMap.put("p_cursor", map.get("p_cursor"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> getYwkhLajdAj(String dwbm, String kssj, String jssj, String ajlbbmstr, String type,Integer page,Integer pageSize) {
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_9);
		Map<String,Object> returnMap = new HashMap<String,Object>(Constant.NUM_9);

		int nowPage = (0==page? Constant.NUM_1:page);
        int pageStart = (nowPage - Constant.NUM_1) * pageSize;
        int pageEnd = pageStart+pageSize;
        pageStart++;

		map.put("p_dwbm", dwbm);
		map.put("p_kssj", kssj);
		map.put("p_jssj", jssj);
		map.put("p_ajlbbmStr", ajlbbmstr);
		map.put("p_type", type);
		map.put("p_start", pageStart);
		map.put("p_end", pageEnd);
		map.put("p_count", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);
		try {
			ajxxcxMapper.getYwkhLajdAj(map);
			returnMap.put("p_type", map.get("p_type").toString());
			returnMap.put("p_count", map.get("p_count").toString());
			returnMap.put("p_cursor", map.get("p_cursor"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> getYwkhXsssjcgzAj(String dwbm, String kssj, String jssj, String ajlbbmstr, String type,Integer page,Integer pageSize) {
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_9);
		Map<String,Object> returnMap = new HashMap<String,Object>(Constant.NUM_9);

		int nowPage = (0==page? Constant.NUM_1:page);
        int pageStart = (nowPage - Constant.NUM_1) * pageSize;
        int pageEnd = pageStart+pageSize;
        pageStart++;

		map.put("p_dwbm", dwbm);
		map.put("p_kssj", kssj);
		map.put("p_jssj", jssj);
		map.put("p_ajlbbmStr", ajlbbmstr);
		map.put("p_type", type);
		map.put("p_start", pageStart);
		map.put("p_end", pageEnd);
		map.put("p_count", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);
		try {
			ajxxcxMapper.getYwkhXsssjcgzAj(map);
			returnMap.put("p_type", map.get("p_type").toString());
			returnMap.put("p_count", map.get("p_count").toString());
			returnMap.put("p_cursor", map.get("p_cursor"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> getYwkhAjglgzAj(String dwbm, String kssj, String jssj, String ajlbbmstr, String type,Integer page,Integer pageSize) {
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_9);
		Map<String,Object> returnMap = new HashMap<String,Object>(Constant.NUM_9);

		int nowPage = (0==page? Constant.NUM_1:page);
        int pageStart = (nowPage - Constant.NUM_1) * pageSize;
        int pageEnd = pageStart+pageSize;
        pageStart++;

		map.put("p_dwbm", dwbm);
		map.put("p_kssj", kssj);
		map.put("p_jssj", jssj);
		map.put("p_ajlbbmStr", ajlbbmstr);
		map.put("p_type", type);
		map.put("p_start", pageStart);
		map.put("p_end", pageEnd);
		map.put("p_count", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);
		try {
			ajxxcxMapper.getYwkhAjglgzAj(map);
			returnMap.put("p_type", map.get("p_type").toString());
			returnMap.put("p_count", map.get("p_count").toString());
			returnMap.put("p_cursor", map.get("p_cursor"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> getYwkhMsxzjcgzAj(String dwbm, String kssj, String jssj, String ajlbbmstr, String type,Integer page,Integer pageSize) {
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_9);
		Map<String,Object> returnMap = new HashMap<String,Object>(Constant.NUM_9);

		int nowPage = (0==page? Constant.NUM_1:page);
        int pageStart = (nowPage - Constant.NUM_1) * pageSize;
        int pageEnd = pageStart+pageSize;
        pageStart++;

		map.put("p_dwbm", dwbm);
		map.put("p_kssj", kssj);
		map.put("p_jssj", jssj);
		map.put("p_ajlbbmStr", ajlbbmstr);
		map.put("p_type", type);
		map.put("p_start", pageStart);
		map.put("p_end", pageEnd);
		map.put("p_count", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);
		try {
			ajxxcxMapper.getYwkhMsxzjcgzAj(map);
			returnMap.put("p_type", map.get("p_type").toString());
			returnMap.put("p_count", map.get("p_count").toString());
			returnMap.put("p_cursor", map.get("p_cursor"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> getYwkhXszxjcgzAj(String dwbm, String kssj, String jssj, String ajlbbmstr, String type,Integer page,Integer pageSize) {
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_9);
		Map<String,Object> returnMap = new HashMap<String,Object>(Constant.NUM_9);

		int nowPage = (0==page? Constant.NUM_1:page);
        int pageStart = (nowPage - Constant.NUM_1) * pageSize;
        int pageEnd = pageStart+pageSize;
        pageStart++;

		map.put("p_dwbm", dwbm);
		map.put("p_kssj", kssj);
		map.put("p_jssj", jssj);
		map.put("p_ajlbbmStr", ajlbbmstr);
		map.put("p_type", type);
		map.put("p_start", pageStart);
		map.put("p_end", pageEnd);
		map.put("p_count", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);
		try {
			ajxxcxMapper.getYwkhXszxjcgzAj(map);
			returnMap.put("p_type", map.get("p_type").toString());
			returnMap.put("p_count", map.get("p_count").toString());
			returnMap.put("p_cursor", map.get("p_cursor"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> getYwkhGsgzAj(String dwbm, String kssj, String jssj, String ajlbbmstr, String type,Integer page,Integer pageSize) {
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_9);
		Map<String,Object> returnMap = new HashMap<String,Object>(Constant.NUM_9);

		int nowPage = (0==page? Constant.NUM_1:page);
        int pageStart = (nowPage - Constant.NUM_1) * pageSize;
        int pageEnd = pageStart+pageSize;
        pageStart++;

		map.put("p_dwbm", dwbm);
		map.put("p_kssj", kssj);
		map.put("p_jssj", jssj);
		map.put("p_ajlbbmStr", ajlbbmstr);
		map.put("p_type", type);
		map.put("p_start", pageStart);
		map.put("p_end", pageEnd);
		map.put("p_count", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);
		try {
			ajxxcxMapper.getYwkhGsgzAj(map);
			returnMap.put("p_type", map.get("p_type").toString());
			returnMap.put("p_count", map.get("p_count").toString());
			returnMap.put("p_cursor", map.get("p_cursor"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> getYwkhWcnrxsjcgzAj(String dwbm, String kssj, String jssj, String ajlbbmstr,
			String type,Integer page,Integer pageSize) {
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_9);
		Map<String,Object> returnMap = new HashMap<String,Object>(Constant.NUM_9);

		int nowPage = (0==page? Constant.NUM_1:page);
        int pageStart = (nowPage - Constant.NUM_1) * pageSize;
        int pageEnd = pageStart+pageSize;
        pageStart++;

		map.put("p_dwbm", dwbm);
		map.put("p_kssj", kssj);
		map.put("p_jssj", jssj);
		map.put("p_ajlbbmStr", ajlbbmstr);
		map.put("p_type", type);
		map.put("p_start", pageStart);
		map.put("p_end", pageEnd);
		map.put("p_count", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);
		try {
			ajxxcxMapper.getYwkhWcnrxsjcgzAj(map);
			returnMap.put("p_type", map.get("p_type").toString());
			returnMap.put("p_count", map.get("p_count").toString());
			returnMap.put("p_cursor", map.get("p_cursor"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	/***
	 * 案件统计列表，所有统计的数据都是在已办结/完成的案件基础之上
	 * @param gh
	 * @param dwbm
	 * @param bmbm
	 * @param page
	 * @param rows
	 * @param kssj
	 * @param jssj
	 * @param xzsj 选择时间，之前可以选择是根据完成日期或者办结日期来进行查询，现在没用到这个参数
	 * @return
	 */
	@Override
	public Map<String, Object> selectAjbl(String gh, String dwbm, String bmbm, Integer page, Integer rows, String kssj, String jssj) {
		Map<String, Object> res = new HashMap<String, Object>();

        // 查询档案所属人所有办案单元list集合
//        List<String> badybmlistAll = ajxxcxMapper.selectAllBadybm(dwbm, gh);

        // 获所有已办案件的案件类别编码集合（包括完成/办结和未完成/办结的案件）
		List<Map<String, String>> lblistAll = ajxxcxMapper.selectbyAjbllxAll(gh, dwbm);
        for (Map<String,String> map : lblistAll) {
            map.remove("ROW_ID");
        }

        // 抽取List<Map>中values值转化为List<String>
        List<String> ajlbBmsAll = ListCastUtils.castListMap(lblistAll);

        // 获取当前人员的所在部门编码集合
//        List<Map<String, String>> bmlist = ajxxcxMapper.selectbyAjblbm(gh, dwbm);
//        for (Map<String,String> map : bmlist) {
//            map.remove("ROW_ID");
//        }

        // 抽取List<Map>中values值转化为List<String>
//        List<String> bmbmlist = ListCastUtils.castListMap(bmlist);

//        int bmListSize = bmbmlist.size();

        if (!bmbm.equals("")) {
            //获取 当前部门下（默认是主要部门，即多部门数组中的第一个，这里的bmbm是页面传过来的)的所有人的工号list集合
			// 注该部门下不是每个都是办案人员，所以要想知道哪些是办案人员，方式1：通过是否使用该系统并且是否创建了档案来判断
			// 方式2：直接获取该部门的所有人员（内勤除外），目前使用的是方式2
            List<String> ajyjGhList = ajxxcxMapper.selectByAjyjGh(dwbm, bmbm);

            //注：统计列表中统计的数据都是在传递进来的时间段之内的数据
			// 获取受理案件总数：受理日期在指定日期内的案件；（获取办理的案件类别名称，案件类别编码，对应的案件数量）
            List<Map<String, Object>> countsOfSlaj = ajxxcxMapper.selectCountsOfSlaj(dwbm, gh, kssj, jssj);

			// 分页：注：PageHelper.startPage(page, rows)只对该语句以后的“第一个查询语句”得到的数据进行分页
			Page<List<Map<String, String>>> pager = PageHelper.startPage(page, rows);

			// 获取完成/办结案件总数：完成日期/办结日期在指定日期内的案件；（获取办理的案件类别名称，案件类别编码，对应的数量）
			// 审查逮捕案件（0201）和一审公诉案件（0301）以办结日期进行计算
			List<Map<String, Object>> countsOfBjaj = null;
			try {
				countsOfBjaj = ajxxcxMapper.selectCountsOfBjaj(dwbm, gh, kssj, jssj);
			} catch (Exception e) {
				e.printStackTrace();
			}

            //创建一个新的list用于存放新生成的办结案件总数
            List<Map<String, Object>> newCountsOfBjaj = new ArrayList<>();

            //创建一个新的list用于存放新生成的受理案件总数：存在以下几种情况
            //1.在指定时间内，当某一类受理案件数有，而办结案件数无时，此类案件不计入统计列表及不显示此类案件
            //2.在指定时间内，当某一类受理案件数无，而办结案件数有时，此类案件则要计入统计类别，此时应将此类案件的受理案件数设置为0，不设置则会显示为空
			List<Map<String, Object>> newCountsOfSlaj = new ArrayList<>();

            String ajlbBmKey = "AJLB_BM";
            int countsOfSlajSize = countsOfSlaj.size();
            int countsOfBjajSize = countsOfBjaj.size();

            // 主要查询在指定时间内完成/办结的案件，受理案件可以为0，如果没有完成/办结的案件就不显示
			if (countsOfBjaj != null && countsOfBjajSize != 0) {
				for (Map<String, Object> bjajMap : countsOfBjaj) {
					if (countsOfSlaj != null && countsOfSlajSize != 0) {
						for (Map<String, Object> slajMap : countsOfSlaj) {
							if (slajMap.get(ajlbBmKey).equals(bjajMap.get(ajlbBmKey))) {
								Map<String, Object> newSlajMap = new HashMap<String, Object>();
								newSlajMap.put("AJLB_BM",slajMap.get("AJLB_BM"));
								newSlajMap.put("AJLB_MC",slajMap.get("AJLB_MC"));
								newSlajMap.put("SLAJSL",slajMap.get("SLAJSL"));
								newCountsOfSlaj.add(newSlajMap);
							}
							// continue语句用在一个循环结构中时，会跳过本次循环的剩余部分继续执行下一次循环，不能完全退出循环结构
							// continue跳出的是就近的循环，不影响外层循环
							// break语句只能跳出自己所在的循环体，当出现嵌套循时，需要使用带标号的break，这样无论多少层循环都能跳出来
//							continue; // 这里的continue好像没意义
						}
					} else {
						Map<String, Object> newSlajMap = new HashMap<String, Object>();
						newSlajMap.put("AJLB_BM",bjajMap.get("AJLB_BM"));
						newSlajMap.put("AJLB_MC",bjajMap.get("AJLB_MC"));
						newSlajMap.put("SLAJSL",0);
						newCountsOfSlaj.add(newSlajMap);
					}
				}
				ListCastUtils.mergeListMap(countsOfBjaj, ajlbBmKey, newCountsOfSlaj);
			}

            // 个人办案效率指标，个人平均办理时间 统计该时间段内 完成/办结的案件
			List<Map<String, Object>> avgTimeOfPer = null;

			//本部门办案效率指标，查询“某个部门”下所有单个案件类型的平均办理时间，最长时间，最短时间（该时间段内办结的案件数）（只管部门，不区分人）
			List<Map<String, Object>> efficiencyOfDep = null;

			//本部门办案效率指标,查询某个人单个案件类型的“平均办理时间在本部门下的排名”（该时间段内办结的案件数）（区分人和部门）
			List<Map<String, Object>> avgTimeRankOfDep = null;

			//本部门办案数量指标，查询“某个部门”下所有 单个案件类型 的最多办案量，最少办案量（该时间段内办结的案件数）
			List<Map<String, Object>> countsOfDep = null;

			//本部门办案数量指标，查询“某个部门”下所有 单个案件类型 的平均办案数量（该时间段内办结的案件数）（只管部门，不区分人）
			List<Map<String, Object>> avgCountOfDep = null;

			//本部门办案数量指标，查询“某个部门”下所有 单个案件类型 办案数量排名（该时间段内办结的案件数）（区分人和部门）
			List<Map<String, Object>> countsRankOfDep = null;

			try {
				avgTimeOfPer = ajxxcxMapper.selectAvgTimeOfPer(dwbm, gh, ajlbBmsAll, kssj, jssj);

				efficiencyOfDep = ajxxcxMapper.selectEfficiencyOfDep(ajyjGhList, dwbm, kssj, jssj);

				avgTimeRankOfDep = ajxxcxMapper.selectAvgTimeRankOfDep(ajyjGhList, dwbm, gh, kssj, jssj);

				countsOfDep = ajxxcxMapper.selectCountsOfDep(ajyjGhList, dwbm, kssj, jssj);

				avgCountOfDep = ajxxcxMapper.selectAvgCountOfDep(ajyjGhList, bmbm, dwbm, kssj, jssj);

				countsRankOfDep = ajxxcxMapper.selectCountsRankOfDep(ajyjGhList, dwbm, gh,kssj,jssj);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//将受理案件数量和办结案件数量合并为一个List<Map<String,Object>>,key分别为：AJLB_BM、AJLB_MC、COUNTSOFSLAJ、COUNTSOFBJAJ
			//注：countsOfBjaj也是合并之后的List
			ListCastUtils.mergeListMap(countsOfBjaj, ajlbBmKey, newCountsOfSlaj);
			ListCastUtils.mergeListMap(countsOfBjaj, ajlbBmKey, avgTimeOfPer);
			ListCastUtils.mergeListMap(countsOfBjaj, ajlbBmKey, efficiencyOfDep);
			ListCastUtils.mergeListMap(countsOfBjaj, ajlbBmKey, avgTimeRankOfDep);
			ListCastUtils.mergeListMap(countsOfBjaj, ajlbBmKey, countsOfDep);
			ListCastUtils.mergeListMap(countsOfBjaj, ajlbBmKey, avgCountOfDep);
			ListCastUtils.mergeListMap(countsOfBjaj, ajlbBmKey, countsRankOfDep);

            // 查询所有案件类别编码(XT_DM_AJLBBM_TYYW表中的所有案件类别编码)
//            List<Map<String,String>> allAjlbbm = ajxxcxMapper.getAllAjlbbm();

            // 将案件类编编码转为List
//            List<String> allAjlbbmList = ListCastUtils.castListMap(allAjlbbm);

            // 判断案件类别编码是否在allAjlbbmList

            newCountsOfBjaj = countsOfBjaj;

			if (newCountsOfBjaj != null && newCountsOfBjaj.size() != 0) {
				res.put("total", pager.getTotal());
			}else {
				res.put("total", 0);
			}
			res.put("rows", newCountsOfBjaj);
			return res;
		}

		return null;
	}

	//判断案件类别是否存在,不存在就添加并返回
    private List<Map<String, Object>> isExist(List<Map<String, Object>> yjfssls,
    										  List<Map<String, Object>> ajyjsls,
                                              String ajlbBmName) {
        //如果办理方式数量为null，根据案件类别编码初始化
    	int listSize = yjfssls.size();
        if (yjfssls == null || listSize == 0) {
            for (Map<String, Object> ajyjsl : ajyjsls) {
                Map<String, Object> yjfssl = new HashMap<String, Object>();
                yjfssl.put(ajlbBmName, ajyjsl.get(ajlbBmName));
                yjfssl.put("DRJCG", 0L);
                yjfssl.put("BAZZRJCG", 0L);
                yjfssl.put("BAZJCG", 0L);
                yjfssl.put("CYXZ", 0L);
                yjfssls.add(yjfssl);
            }
        } else {
            for (Map<String, Object> ajyjsl : ajyjsls) {
                String ajlbbm = (String) ajyjsl.get(ajlbBmName);
                for (int i = 0; i < listSize; i++) {
                    if (!yjfssls.get(i).get(ajlbBmName).equals(ajlbbm)) {
                        if (i == listSize - 1) {
                            Map<String, Object> yjfssl = new HashMap<String, Object>();
                            yjfssl.put(ajlbBmName, ajlbbm);
                            yjfssl.put("DRJCG", 0L);
                            yjfssl.put("BAZZRJCG", 0L);
                            yjfssl.put("BAZJCG", 0L);
                            yjfssl.put("ZJBL", 0L);
                            yjfssls.add(yjfssl);
                        }
                        continue;
                    }
                }
            }
        }
        return yjfssls;
    }

    /**
     * 添加元素到集合中
     * @param srcList
     * @param map
     */
    private void mergeCollection(List<Map<String,Object>> srcList,Map<String,Object> map){
        for(Map<String,Object> tmap:srcList){
            if(StringUtils.equals((String)tmap.get("AJLB_BM"),(String) map.get("AJLB_BM"))){
                tmap.put("SPSH",map.get("SPSH"));
                return;
            }
        }
        srcList.add(map);
    }


    /**
     * 统计案件总数
     * @param srcList
     * @param map
     */
    private void countTotal(List<Map<String,Object>> srcList,Map<String,Object> map){
        for(Map<String,Object> tmap:srcList){
            if(StringUtils.equals((String)tmap.get("AJLB_BM"),(String) map.get("AJLB_BM"))){
                Long flsl = new BigDecimal(tmap.get("FLSL").toString()).longValue(); //(Long) tmap.get("FLSL");
                flsl += new BigDecimal(map.get("SPSH").toString()).longValue(); //(Long) map.get("SPSH");
                tmap.put("FLSL",flsl);
                return;
            }
        }
        //如果是全新的案件类型则案件总数为审批审核的数量
        map.put("FLSL",map.get("SPSH"));
        srcList.add(map);
    }

	@Override
	public Map<String, Object> selectAjblEj(String dwbm, String bmbm, String gh, String ajlbbm, Integer page,
											Integer rows, String kssj,String jssj, String type, String sort, String order) {

		Map<String, Object> res = new HashMap<String, Object>();

        List<Map<String, String>> bmsahlist = new ArrayList<>();

        // 只对该语句下的第一条查询语句生效
        Page<List<Map<String, String>>> pager = PageHelper.startPage(page, rows);

        // 查询在指定案件类别编码，指点时间段内(完成/办结)/(受理)的所有案件的部门受案号
        if (type.equals("2")) { // 办结/完成案件
        	try {
				bmsahlist = ajxxcxMapper.selectbyAllBmsah(dwbm, gh, ajlbbm,kssj,jssj, sort, order);
			} catch (Exception e) {
        		e.printStackTrace();
			}
        } else if (type.equals("1")) { // 受理案件
        	try {
				bmsahlist = ajxxcxMapper.selectbyAllBmsahOfSl(dwbm, gh, ajlbbm,kssj,jssj, sort, order);
			} catch (Exception e) {
        		e.printStackTrace();
			}
        }
		for (Map<String,String> map : bmsahlist) {
			map.remove("ROW_ID");
		}

        //提取map的value转为list
        List<String> sahlist = ListCastUtils.castListMap(bmsahlist);
        if (sahlist.isEmpty()) {
        	List list = new ArrayList<>();
        	res.put("total", 0);
        	res.put("rows", list);
            return res;//之前是return null;-->导致的问题是，页面没有数据但是分页条显示共有10条数据
        }

        //获取办案单元编码list集合（不区分主办从办）
//        List<String> badybmlistAll = ajxxcxMapper.selectAllBadybm(dwbm, gh);

        // 获取案件类别编码集合（所有）
//        List<Map<String, String>> lblistAll = ajxxcxMapper.selectbyAjbllxAll(gh, dwbm);

        // 抽取List<Map>中values值转化为List<String>
//        List<String> ajlbBmsAll = ListCastUtils.castListMap(lblistAll);

		List<Map<String, Object>> blfsEjList = new ArrayList<>();

		//查询二级表单案件名称，案件类别名称，办理时限，平均办理时间
		List<Map<String, Object>> ajDetail = null;
		try {
			ajDetail = ajxxcxMapper.selectbyAjblEjsj(gh, dwbm, sahlist, sort, order);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//统计文书总数
		List<Map<String, Object>> countsOfWs = ajxxcxMapper.countFjAndWS(sahlist);

		//定义合并条件常量
		final String bmsah = "BMSAH";

		ListCastUtils.mergeListMap(ajDetail, bmsah, countsOfWs);

		res.put("total", pager.getTotal());
		res.put("rows", ajDetail);
		return res;
	}

	/**
	 * 根据部门受案号查询案件基本信息
	 */
	@Override
	public Map<String, Object> selectAjxqByBmsah(String bmsah) {
		Map<String,Object> map = new HashMap<>();
		Map<String,Object> map2 = new HashMap<>();
		try {
			map = ajxxcxMapper.selectAjxqByBmsah(bmsah);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map2.put("data", map);
		return map2;
	}

	/**
	 * 根据部门受案号和统一受案号查询办案人员信息
	 * 参与办案人员的信息通过查询制作文书来确定
	 */
	@Override
	public List<Map<String, Object>> selectBaryByBmsah(String bmsah, String tysah) {

		// 所有办案人员
        List<Map<String, Object>> bary = new ArrayList<>();
        // 承办人员
        List<Map<String, Object>> cbry = new ArrayList<>();
        // 协同办案人员
        List<Map<String, Object>> xtbary = new ArrayList<>();
		// 承办人所在单位编码
        String dwbm = "";

        try {
			cbry = ajxxcxMapper.selectCbryByBmsah(bmsah);
			if (cbry != null && cbry.size() > 0) {
				cbry.get(0).put("SFLB", "承办人");
				dwbm = cbry.get(0).get("DWBM").toString();
			}

			if (!tysah.equals("null")) {
				xtbary = ajxxcxMapper.selectXtbaryByBmsahAndTysah(bmsah,tysah);
			}
		} catch (Exception e) {
        	e.printStackTrace();
		}

		if (xtbary.size() == 0 || (xtbary.size() == 1 && xtbary.get(0).get("GH").equals(cbry.get(0).get("GH")) && xtbary.get(0).get("DWBM").equals(cbry.get(0).get("DWBM")))) {
			bary = cbry;
		} else { // 能进这里，xtbary.size()一定是大于1的
        	// 将承办人放在第一位
			bary.add(cbry.get(0));

			if (xtbary.size() > 0) { // 这个判断其实没必要
				for (int i = 0; i < xtbary.size(); i++ ) {
					// 移除协同办案人员中的承办人，因为办案人员也会制作文书
					if (xtbary.get(i).get("GH").equals(cbry.get(0).get("GH")) && xtbary.get(i).get("DWBM").equals(cbry.get(0).get("DWBM"))) {
						xtbary.remove(i);
					}
				}

				// 查询当前单位下的案管部门的人员
				List<Map<String, Object>> agry = ajxxcxMapper.selectAgryByDwbm(dwbm);
				// 承办人员中的系统办案要排除案管人员，因为案管人员不办案，只是发起文书
				for (int j = 0; j < agry.size(); j++) {
					for (int k = 0; k < xtbary.size(); k++) {
						if (agry.get(j).get("GH").equals(xtbary.get(k).get("GH")) && agry.get(j).get("DWBM").equals(xtbary.get(k).get("DWBM"))) {
							xtbary.remove(k);
							break;
						}
					}
				}

				// 协同办案人员中排除承办人本身和案管人员，剩余的就是协同办案人员
				for (int i = 0; i < xtbary.size(); i++ ) {
					// 不能将放在上面的for语句中！
					xtbary.get(i).put("SFLB", "协同办案");
					bary.add(xtbary.get(i));
				}
			}
		}

		// 根据单位编码和工号查询所所在部门名称（可能存在多部门）
		List<Map<String, Object>> resList = new ArrayList<>();

		for (Map<String, Object> map : bary) {
			StringBuilder bmmc = new StringBuilder();
			resList  = sfdacjMapper.getAllBmbm(map.get("DWBM").toString(), map.get("GH").toString());

			if(resList.size()>0){
				for (int i = 0; i < resList.size(); i++) {
					bmmc.append(resList.get(i).get("BMMC") + ",");
				}
				// 去除最后一个,
				if (bmmc.length() > 0) {
					bmmc.deleteCharAt(bmmc.length() - 1);
				}
				map.put("SZBM", bmmc);
			}
		}

        return bary;
	}

	/**
	 * 根据部门受案号查询嫌疑人信息
	 */
	@Override
	public List<Map<String, Object>> selectXyrxxByBmsah(String bmsah) {
		return ajxxcxMapper.selectXyrxxByBmsah(bmsah);
	}

	@Override
	public String addAjbl(String did, String ajmc, String cbdwbm, String cbdwmc, String ajlb,String ajlbbm,
						  String cbrgh, String cbr, String ajslrq, String dqrq,String bjrq,
						  String ajwjrq, String aqzy,String cbbmbm,String cbbmmc, String bjqk, String cbryj,String fz
						  ) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		//日期字符串转成日期
		Date ajslrqDate = DateUtil.stringtoDate(ajslrq,"yyyy-MM-dd");
		Date bjrqDate = DateUtil.stringtoDate(bjrq,"yyyy-MM-dd");
		Date dqrqDate = null;
		Date ajwjrqDate = null;
		if (!dqrq.equals("")) {
			dqrqDate = DateUtil.stringtoDate(dqrq,"yyyy-MM-dd");
		} else {
			dqrqDate = null;
		}

		if (!ajwjrq.equals("")) {
			ajwjrqDate = DateUtil.stringtoDate(ajwjrq,"yyyy-MM-dd");
		} else {
			ajwjrqDate = null;
		}

		String ajId = Identities.get32LenUUID();
		String bmsah = Identities.get32LenUUID();

		// 将英文状态下的单引号和双引号进行转义;注双引号不能用 &quot;只能是\\"，否则json解析还是有问题
		// 姓名
		if (StringUtils.isNotBlank(ajmc)) {
			ajmc = ajmc.replaceAll("'", "&#39;");
			ajmc = ajmc.replaceAll("\"", "\\\"");
		}

		// 案件类别，案件类别编码
		if (StringUtils.isNotBlank(ajlb)) {
			ajlb = ajlb.replaceAll("'", "&#39;");
			ajlb = ajlb.replaceAll("\"", "\\\"");
			ajlbbm = ajlbbm.replaceAll("'", "&#39;");
			ajlbbm = ajlbbm.replaceAll("\"", "\\\"");
		}

		// 案情摘要
		if (StringUtils.isNotBlank(aqzy)) {
			aqzy = aqzy.replaceAll("'", "&#39;");
			aqzy = aqzy.replaceAll("\"", "\\\"");
		}

		// 附注
		if (StringUtils.isNotBlank(fz)) {
			fz = fz.replaceAll("'", "&#39;");
			fz = fz.replaceAll("\"", "\\\"");
		}

		// 承办人意见
		if (StringUtils.isNotBlank(cbryj)) {
			cbryj = cbryj.replaceAll("'", "&#39;");
			cbryj = cbryj.replaceAll("\"", "\\\"");
		}

		// 办结情况
		if (StringUtils.isNotBlank(bjqk)) {
			bjqk = bjqk.replaceAll("'", "&#39;");
			bjqk = bjqk.replaceAll("\"", "\\\"");
		}

		map.put("id", ajId);
		map.put("did", did);
		map.put("ajmc", ajmc);
		map.put("bmsah", bmsah);//部门受案号
		map.put("cbdwbm", cbdwbm);
		map.put("cbdwmc", cbdwmc);
		map.put("ajlb", ajlb);
		map.put("ajlbbm", ajlbbm);
		map.put("cbrgh", cbrgh);
		map.put("cbr", cbr);
		map.put("ajslrq", ajslrqDate);
		map.put("dqrq", dqrqDate);
		map.put("bjrq", bjrqDate);
		map.put("ajwjrq", ajwjrqDate);
		map.put("aqzy", aqzy);
		map.put("cbbmbm", cbbmbm);
		map.put("cbbmmc", cbbmmc);
		map.put("ajzt", "2");//案件状态，1 已审批 2 未审批 3 审批未通过
		map.put("fz", fz); // 附注/备注
		map.put("bjqk", bjqk);
		map.put("cbryj", cbryj);

		try{
			ajxxcxMapper.addAjblToAjxx(map);
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * 展示非案管 案件办理新增案件
	 */
	@Override
	public Map<String, Object> selectALLNewAjbl(String ajId, String did, int page, int pageSize, String ajStatus) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			Page ajslPager = PageHelper.startPage(page, pageSize);
			List ajxx = ajxxcxMapper.selectALLNewAjbl(ajId, did, ajStatus);
			resMap.put("total", ajslPager.getTotal());
			resMap.put("rows", ajxx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resMap;
	}

	@Override
	public String deleteAjbl(String ajid,String bmsah) throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("ajid", ajid);
		map.put("bmsah", bmsah);
		try {
			ajxxcxMapper.deleteAjxx(map);
			ajxxcxMapper.deleteAjblFj(map);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@Override
	public String updateAjbl(String ajId, String ajmc, String ajlb, String ajlbbm,String ajslrq, String dqrq,String bjrq,
							 String ajwjrq, String aqzy,String bjqk, String cbryj,String fz) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		//日期字符串转成日期
		Date ajslrqDate = DateUtil.stringtoDate(ajslrq,"yyyy-MM-dd");
		Date bjrqDate = DateUtil.stringtoDate(bjrq,"yyyy-MM-dd");
		Date dqrqDate = null;
		Date ajwjrqDate = null;
		if (!dqrq.equals("")) {
			dqrqDate = DateUtil.stringtoDate(dqrq,"yyyy-MM-dd");
		}

		if (!ajwjrq.equals("")) {
			ajwjrqDate = DateUtil.stringtoDate(ajwjrq,"yyyy-MM-dd");
		}

		// 将英文状态下的单引号和双引号进行转义;注双引号不能用 &quot;只能是\\"，否则json解析还是有问题
		// 姓名
		if (StringUtils.isNotBlank(ajmc)) {
			ajmc = ajmc.replaceAll("'", "&#39;");
			ajmc = ajmc.replaceAll("\"", "\\\"");
		}

		// 案件类别，案件类别编码
		if (StringUtils.isNotBlank(ajlb)) {
			ajlb = ajlb.replaceAll("'", "&#39;");
			ajlb = ajlb.replaceAll("\"", "\\\"");
			ajlbbm = ajlbbm.replaceAll("'", "&#39;");
			ajlbbm = ajlbbm.replaceAll("\"", "\\\"");
		}

		// 案情摘要
		if (StringUtils.isNotBlank(aqzy)) {
			aqzy = aqzy.replaceAll("'", "&#39;");
			aqzy = aqzy.replaceAll("\"", "\\\"");
		}

		// 附注
		if (StringUtils.isNotBlank(fz)) {
			fz = fz.replaceAll("'", "&#39;");
			fz = fz.replaceAll("\"", "\\\"");
		}

		// 承办人意见
		if (StringUtils.isNotBlank(cbryj)) {
			cbryj = cbryj.replaceAll("'", "&#39;");
			cbryj = cbryj.replaceAll("\"", "\\\"");
		}

		// 办结情况
		if (StringUtils.isNotBlank(bjqk)) {
			bjqk = bjqk.replaceAll("'", "&#39;");
			bjqk = bjqk.replaceAll("\"", "\\\"");
		}

		map.put("ajId", ajId);
		map.put("ajmc", ajmc);
		map.put("ajlb", ajlb);
		map.put("ajlbbm", ajlbbm);
		map.put("ajslrq", ajslrqDate);
		map.put("dqrq", dqrqDate);
		map.put("bjrq", bjrqDate);
		map.put("ajwjrq", ajwjrqDate);
		map.put("aqzy", aqzy);
		map.put("fz", fz); // 附注对应表中的bz字段
		map.put("bjqk", bjqk);
		map.put("cbryj", cbryj);
		map.put("zhgxsj", DateUtil.stringtoDate(DateUtil.getSysCurrentDateTime(),"yyyy-MM-dd HH:mm:ss"));

		try{
			ajxxcxMapper.updateAjxxByBmsah(map);
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}

	@Override
	public Map<String, Object> selectAjlbByywbm(List<String> bmys) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			List ajlb = ajxxcxMapper.selectAjlbByywbm(bmys);
			map.put("data", ajlb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> selectCbxz(String daId, int page, int pageSize) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			Page cbxzPager = PageHelper.startPage(page, pageSize);
			List cbxz = ajxxcxMapper.selectCbxz(daId);
			resMap.put("total", cbxzPager.getTotal());
			resMap.put("rows", cbxz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resMap;
	}

	@Override
	public String addCbxz(String daId, String dwbm, String gh,
						  String blfs, String blfsbm, String cbxzmc)throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		String id = Identities.get32LenUUID();
		String cbxzbm = Identities.get32LenUUID();
		map.put("id", id);
		map.put("daId", daId);
		map.put("dwbm", dwbm);
		map.put("gh", gh);
		map.put("blfs", blfs);
		map.put("blfsbm", blfsbm);
		map.put("cbxzmc", cbxzmc);
		map.put("cbxzbm", cbxzbm);

		try{
			ajxxcxMapper.addCbxz(map);
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}

	@Override
	public String deleteCbxz(String id) throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("id", id);
		try {
			ajxxcxMapper.deleteCbxz(map);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@Override
	public List<Map<String, Object>> getAllCbxz(String daId) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		List cbxz = null;
		try {
			cbxz = ajxxcxMapper.getCbxz(daId);
			resMap.put("rows", cbxz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cbxz;
	}



}
