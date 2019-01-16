package com.swx.ibms.business.performance.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swx.ibms.business.cases.mapper.AjxxcxMapper;
import com.swx.ibms.business.performance.bean.YxGrjxAjtj;
import com.swx.ibms.business.performance.mapper.GrjxAjWsMapper;
import com.swx.ibms.business.performance.mapper.YxGrjxAjtjMapper;
import com.swx.ibms.business.performance.service.GrjxAjWsService;
import com.swx.ibms.common.utils.DateUtil;
import com.swx.ibms.common.utils.Identities;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.swx.ibms.common.utils.DateUtil.countExcludeWorkday;


/**
 * Created by xuwl on 2018/9/12.
 * description: 个人绩效案件文书查询
 */
@Service
public class GrjxAjWsServiceImpl implements GrjxAjWsService {

    /**
     * 日志对象
     */
    private static final Logger LOG = Logger.getLogger(GrjxAjWsServiceImpl.class);

    /**
     * 数据访问接口
     **/
    @Resource
    private GrjxAjWsMapper grjxAjWsMapper;

    /**
     * 数据访问接口
     **/
    @Resource
    private YxGrjxAjtjMapper yxGrjxAjtjMapper;

    /**
     * 数据访问接口
     **/
    @Resource
    private AjxxcxMapper ajxxcxMapper;

    @Override
    public Map<String, Object> getAj(String dwbm, String gh, String kssj, String jssj, Integer page, Integer rows, String bmlbbm, String rylx) {
        Map<String, Object> resMap = new HashMap<>();
        List<Map<String, Object>> ajInfo = new ArrayList<>();

        Page pager = PageHelper.startPage(page, rows);
        try {
            if (StringUtils.equals(bmlbbm, "3")) {
                // 公诉，办结案件查询方式不一样
                ajInfo = grjxAjWsMapper.selectBjAj(dwbm, gh, kssj, jssj);
            } else if (StringUtils.equals(bmlbbm, "1")) {
                // 刑申，办结案件查询方式不一样
                ajInfo = grjxAjWsMapper.getAj(dwbm, gh, kssj, jssj);
            } else if (StringUtils.equals(bmlbbm, "4")) {
                // 执检
                if (StringUtils.equals(rylx, "检察官")) {
                    ajInfo = grjxAjWsMapper.selectBjAjOfZj(dwbm, gh, kssj, jssj);
                } else if (StringUtils.equals(rylx, "检察辅助人员")) {
                    ajInfo = grjxAjWsMapper.selectBjAjOfZjJf(dwbm, gh, kssj, jssj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(StringUtils.EMPTY, e);
        }

        resMap.put("total", pager.getTotal());
        resMap.put("rows", ajInfo);

        return resMap;
    }

    @Override
    public Map<String, Object> selectBjAjOfBm(String dwbm, String bmbm, String kssj, String jssj, Integer page, Integer rows, String bmlbbm) {
        Map<String, Object> resMap = new HashMap<>();
        List<Map<String, Object>> ajInfo = new ArrayList<>();
        Page pager = PageHelper.startPage(page, rows);
        try {
            ajInfo = grjxAjWsMapper.selectBjAjOfBm(dwbm, bmbm, kssj, jssj);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(StringUtils.EMPTY, e);
        }

        resMap.put("total", pager.getTotal());
        resMap.put("rows", ajInfo);

        return resMap;
    }

    @Override
    public Map<String, Object> getAjWs(String dwbm, String gh, String kssj, String jssj, Integer page, Integer rows ,
                                       String fz, String queryNo, String bmlbbm) {
        Map<String, Object> resMap = new HashMap<>();

        List<String> bmsahs = new ArrayList<>();
        List<String> tysahs = new ArrayList<>();

        // 1、先查询在指定时间内办结的案件
        List<Map<String, Object>> ajInfo = new ArrayList<>();
        try {
            // 注意：部门不同，办结案件的获取方式不同
            ajInfo = grjxAjWsMapper.getAj(dwbm, gh, kssj, jssj);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(StringUtils.EMPTY, e);
        }

        if (ajInfo.size() > 0) {
            for (Map<String, Object> map : ajInfo) {
                bmsahs.add(map.get("BMSAH").toString());
                tysahs.add(map.get("TYSAH").toString());
            }
        }

        List<Map<String, Object>> wsInfo = new ArrayList<>();
        Page pager = PageHelper.startPage(page, rows);
        try {
            if (bmsahs.size() > 0 && tysahs.size() > 0) {
                // 其他文书
                if (queryNo.equals("2")) {
                    wsInfo = grjxAjWsMapper.getOtherWs(bmsahs, tysahs, fz, gh, bmlbbm);
                    // 2分/3分文书
                } else if (queryNo.equals("1")) {
                    wsInfo = grjxAjWsMapper.getWsByScore(bmsahs, tysahs, fz, gh, bmlbbm);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(StringUtils.EMPTY, e);
        }

        resMap.put("total", pager.getTotal());
        resMap.put("rows", wsInfo);

        return resMap;
    }

    @Override
    public Integer getAllAjByBmsahs(String[] bmsahs,String khid,String khfzid,String zxmbm, String bmlbbm) {

        Map<String, Object> map = new HashMap<>();
        Integer res = 0;

        List<String> bmsahList = new ArrayList<>();

        for (int i = 0; i < bmsahs.length; i++) {
            if (bmsahs[i].length() > 4) {
                bmsahList.add(bmsahs[i]);
            }
        }

        try {
            for (int i = 0;i < bmsahList.size(); i++) {
                if (StringUtils.equals(bmlbbm, "1")) {
                    // 刑申 的案件办结日期要根据文书来判断
                    map = grjxAjWsMapper.getAjByBmsah(bmsahList.get(i));
                } else {
                    map = grjxAjWsMapper.selectAjByBmsah(bmsahList.get(i));
                }
                if (StringUtils.isNotBlank(map.get("AJMC").toString()) || StringUtils.isNotBlank(map.get("BMSAH").toString())) {
                    YxGrjxAjtj ajtj = new YxGrjxAjtj();
                    String id = Identities.get32LenUUID();
                    ajtj.setId(id);
                    ajtj.setKhid(khid);
                    ajtj.setKhfzid(khfzid);
                    ajtj.setZxmbm(zxmbm);
                    ajtj.setBmsah(map.get("BMSAH").toString());
                    ajtj.setTysah(map.get("TYSAH").toString());
                    ajtj.setAjmc(map.get("AJMC").toString());
                    ajtj.setAjlbmc(map.get("AJLB_MC").toString());
                    ajtj.setCbr(map.get("CBR").toString());
                    ajtj.setCbbm(map.get("CBBM_MC").toString());
                    if (map.containsKey("SLRQ")) {
                        ajtj.setSlrq(DateUtil.stringtoDate(map.get("SLRQ").toString(),"yyyy-MM-dd"));
                    }
                    if (map.containsKey("WJRQ")) {
                        ajtj.setWcrq(DateUtil.stringtoDate(map.get("WJRQ").toString(),"yyyy-MM-dd"));
                    }
                    if (map.containsKey("AQZY")) {
                        ajtj.setAqzy(map.get("AQZY").toString());
                    }

                    try {
                        // 插入前先判断此条记录是否已存在，如果存在则不添加
                        int isExit = 0;
                        isExit = yxGrjxAjtjMapper.selectByBmsah(khid,khfzid,bmsahList.get(i),map.get("TYSAH").toString(),zxmbm);
                        if (isExit > 0) {
                            res = 0;
                        } else {
                            res = yxGrjxAjtjMapper.insertSelective(ajtj);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Map<String, Object> getGrjxAjtjPageList(Integer page, Integer pageSize, String khid, String zxmbm) {
        Map<String,Object> resMap = new HashMap<String,Object>();
        List<YxGrjxAjtj> listInfo = new ArrayList<>();
        Page<YxGrjxAjtj> pager = PageHelper.startPage(page, pageSize);
        try{
            listInfo = yxGrjxAjtjMapper.getGrjxAjtjList(khid, zxmbm);
        }catch (Exception e){
            e.printStackTrace();
        }
        resMap.put("total",pager.getTotal());
        resMap.put("rows",listInfo);
        return resMap;
    }

    @Override
    public int deleteAjById(String id) {
        Integer result = 0 ;
        try{
            result = yxGrjxAjtjMapper.deleteByPrimaryKey(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Map<String, Object> getCountsOfNotMadeWsPageList(Integer page, Integer pageSize, String dwbm, String gh, String kssj, String jssj, String bmlbbm) {
        Map<String,Object> resMap = new HashMap<String,Object>();
        List<Map<String, Object>> listInfo = new ArrayList<>();
        List<Map<String, Object>> ajInfo = new ArrayList<>();
        int countsOfQuery = 0;
        List<String> bmsahs = new ArrayList<>();
        List<String> tysahs = new ArrayList<>();

        try {
            // bmblbm 为7表示检技
            if (StringUtils.equals(bmlbbm, "7")) {
                ajInfo = grjxAjWsMapper.queryBjAj(dwbm, gh, kssj, jssj);
            } else {
                ajInfo = grjxAjWsMapper.getAj(dwbm, gh, kssj, jssj);
                // 刑事申诉案件需要排除备案审查案件
                for (int i = 0; i < ajInfo.size(); i++) {
                    if (ajInfo.get(i).get("AJLB_BM").equals("090")) {
                        ajInfo.remove(i);
                    }
                }
            }

            if (ajInfo.size() > 0) {
                for (Map<String, Object> map : ajInfo) {
                    // 查询 应制作而未制作文书 的案件
                    countsOfQuery = grjxAjWsMapper.getCountsOfNotMadeWs(map.get("BMSAH").toString(), map.get("TYSAH").toString());
                    // 如果数量为0,则表示该案件是 “应该制作文书但是却没有制作文书的案件”，要将此案件查询出来显示
                    if (countsOfQuery == 0) {
                        bmsahs.add(map.get("BMSAH").toString());
                        tysahs.add(map.get("TYSAH").toString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(StringUtils.EMPTY, e);
        }

        // 将应该制作但是没制作文书的案件 查询出来
        Page pager = PageHelper.startPage(page, pageSize);
        try {
            if (bmsahs.size() > 0) {
                if (StringUtils.equals(bmlbbm, "7")) {
                    listInfo = grjxAjWsMapper.getWjajsByBmsahs(bmsahs);
                } else {
                    listInfo = grjxAjWsMapper.getAjsByBmsahs(bmsahs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(StringUtils.EMPTY, e);
        }

        resMap.put("total",pager.getTotal());
        resMap.put("rows",listInfo);
        return resMap;
    }

    @Override
    public Map<String, Object> getCountsOfReviewAjPageList(Integer page, Integer pageSize, String dwbm, String gh, String kssj, String jssj) {
        Map<String,Object> resMap = new HashMap<String,Object>();
        List<Map<String, Object>> listInfo = new ArrayList<>();
        Page pager = PageHelper.startPage(page, pageSize);
        try{
            listInfo = grjxAjWsMapper.getAjOfReviewAj(dwbm, gh, kssj, jssj);
        }catch (Exception e){
            e.printStackTrace();
        }
        resMap.put("total",pager.getTotal());
        resMap.put("rows",listInfo);
        return resMap;
    }

    @Override
    public Map<String, Object> getCountsOfOvertimeAjPageList(Integer page, Integer pageSize, String dwbm,
                                                             String gh, String kssj, String jssj, String bmlbbm) {
        Map<String,Object> resMap = new HashMap<String,Object>();
        List<Map<String, Object>> listInfo = new ArrayList<>();
        Page pager = null;
        try{
            // 注意部门不同，获取亮红灯/超期案件的方式不同
            // bmlbbm: 7：检技
            if (StringUtils.equals(bmlbbm, "7")) {
                // 检技的超期案件计算需要满足以下条件：
                // 1、首先是办结案件；为了减少循环次数，这里只需要查询检验鉴定和技术性证据审查这两类办结的案件
                List<Map<String, Object>> bjajList = grjxAjWsMapper.queryBjAjOfJyAndJs(dwbm, gh, kssj, jssj);
                // 2、检验鉴定类案件：【完成日期-受理日期 > 20天】算超期；技术性证据审查类案件：【完成日期-受理日期 > 6天】算超期；
                // 3、排除非工作日

                // 工作日天数
                int countsOfWorkday = 0;
                pager = PageHelper.startPage(page, pageSize);
                for (Map<String, Object> map : bjajList) {
                    if (map.get("AJLB_BM").equals("1304")) {
                        // 检验鉴定
                        countsOfWorkday = countExcludeWorkday(map.get("SLRQ").toString(), map.get("WCRQ").toString());
                        if (countsOfWorkday > 20 ) {
                            listInfo.add(map);
                        }
                    } else if (map.get("AJLB_BM").equals("1305")) {
                        // 技术性证据审查
                        countsOfWorkday = countExcludeWorkday(map.get("SLRQ").toString(), map.get("WCRQ").toString());
                        if (countsOfWorkday > 6 ) {
                            listInfo.add(map);
                        }
                    }
                }
            } else {
                // 刑申、侦监
                pager = PageHelper.startPage(page, pageSize);
                listInfo = grjxAjWsMapper.getAjOfOvertimeAj(dwbm, gh, kssj, jssj);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if (StringUtils.equals(bmlbbm, "7")) {
            resMap.put("total",listInfo.size());
        } else {
            resMap.put("total",pager.getTotal());
        }
        resMap.put("rows",listInfo);
        return resMap;
    }

    @Override
    public Map<String, Object> countsOfZj(Integer page, Integer pageSize, String dwbm, String gh,
                                          String kssj, String jssj, String bmlbbm, String cxbh) {
        Map<String,Object> resMap = new HashMap<>();
        List<Map<String, Object>> listInfo = new ArrayList<>();
        Page pager = PageHelper.startPage(page, pageSize);
        try{
            // 【侦监】
            if (StringUtils.equals(bmlbbm, "2")) {
                if (StringUtils.equals(cxbh, "11")) {
                    // 追加逮捕的犯罪嫌疑人被依法批准逮捕的，加分0.3分/人
                    listInfo = grjxAjWsMapper.selectZj1(dwbm, gh, kssj, jssj);
                } else if (StringUtils.equals(cxbh, "12")) {
                    // 监督行政执法部门移送涉嫌犯罪案件的，加分0.4分/件
                    listInfo = grjxAjWsMapper.selectZj2(dwbm, gh, kssj, jssj);
                } else if (StringUtils.equals(cxbh, "13")) {
                    // 监督行政执法机关移送后公安机关直接立案的，加分0.5分/件
                    listInfo = grjxAjWsMapper.selectZj3(dwbm, gh, kssj, jssj);
                } else if (StringUtils.equals(cxbh, "14")) {
                    // 监督公安机关立案或者撤案的，加分0.4分/件
                    listInfo = grjxAjWsMapper.selectZj4(dwbm, gh, kssj, jssj);
                } else if (StringUtils.equals(cxbh, "15")) {
                    // 上述1-5监督案件的犯罪嫌疑人被判有罪的，再加分0.3分/件
                    listInfo = grjxAjWsMapper.selectZj5(dwbm, gh, kssj, jssj);
                } else if (StringUtils.equals(cxbh, "16")) {
                    // 上述1-5监督案件的犯罪嫌疑人被判处三年以上有期徒刑的，再加分0.2分/件
                    listInfo = grjxAjWsMapper.selectZj6(dwbm, gh, kssj, jssj);
                } else if (StringUtils.equals(cxbh, "17")) {
                    // 上述1-5监督案件的犯罪嫌疑人被判处十年以上有期徒刑的，再加分0.5分/件
                    listInfo = grjxAjWsMapper.selectZj7(dwbm, gh, kssj, jssj);
                } else if (StringUtils.equals(cxbh, "18")) {
                    // 书面纠正违法且侦查机关回复整改的，加分0.3分/件
                    listInfo = grjxAjWsMapper.selectZj8(dwbm, gh, kssj, jssj);
                } else if (StringUtils.equals(cxbh, "19")) {
                    // 发出检察建议且有关单位整改回复的，加分0.5分/件
                    listInfo = grjxAjWsMapper.selectZj9(dwbm, gh, kssj, jssj);
                }
            } else if (StringUtils.equals(bmlbbm, "4")) {
                // 【执检】
                if (StringUtils.equals(cxbh, "20")) {
                    // 减假暂案件发现不当提出书面纠正意见被采纳的，每件计2分
                    listInfo = grjxAjWsMapper.selectZj10(dwbm, gh, kssj, jssj);
                } else if (StringUtils.equals(cxbh, "21")) {
                    // 提出释放或变更强制措施建议被采纳的，每件计2分
                    listInfo = grjxAjWsMapper.selectZj11(dwbm, gh, kssj, jssj);
                } else if (StringUtils.equals(cxbh, "22")) {
                    // 纠正违法通知书或检察建议书被采纳的，每件计2分
                    listInfo = grjxAjWsMapper.selectZj12(dwbm, gh, kssj, jssj);
                }

            } else if (StringUtils.equals(bmlbbm, "5")) {
                // 【民行】
                if (StringUtils.equals(cxbh, "23")) {
                    // 办理生效裁判（含调解）监督案件提出再审检察建议或者提请、提出抗诉的，每件计8分
                    listInfo = grjxAjWsMapper.selectMx1(dwbm, gh, kssj, jssj);
                } else if (StringUtils.equals(cxbh, "24")) {
                    // 办理审判人员违法行为监督案件提出检察建议的，每件计5分
                    listInfo = grjxAjWsMapper.selectMx2(dwbm, gh, kssj, jssj);
                } else if (StringUtils.equals(cxbh, "25")) {
                    // 办理执行监督案件提出检察建议的，每件计5分
                    listInfo = grjxAjWsMapper.selectMx3(dwbm, gh, kssj, jssj);
                } else if (StringUtils.equals(cxbh, "26")) {
                    // 办理行政机关不当履行职责监督案件（包括督促起诉案件）提出检察建议的，每件计5分
                    listInfo = grjxAjWsMapper.selectMx4(dwbm, gh, kssj, jssj);
                } else if (StringUtils.equals(cxbh, "27")) {
                    // 检察官办理对下级院不支持监督决定进行复查的案件,维持原处理决定的，每件计1分
                    listInfo = grjxAjWsMapper.selectMx5(dwbm, gh, kssj, jssj);
                } else if (StringUtils.equals(cxbh, "28")) {
                    // 办理生效裁判监督案件提请上级院抗诉未获支持或案件抗诉后法院未予采纳的，每件扣2分
                    listInfo = grjxAjWsMapper.selectMx6(dwbm, gh, kssj, jssj);
                } else if (StringUtils.equals(cxbh, "29")) {
                    // 办理生效裁判（含调解）监督案件提出再审检察建议被法院采纳或者提请抗诉被上级院支持、提出抗诉得到法院采纳的，每件加2分
                    listInfo = grjxAjWsMapper.selectMx7(dwbm, gh, kssj, jssj);
                } else if (StringUtils.equals(cxbh, "30")) {
                    // 办理案件中促成当事人和解，案件作终结审查处理的，每件加3分;
                    listInfo = grjxAjWsMapper.selectMx8(dwbm, gh, kssj, jssj);
                } else if (StringUtils.equals(cxbh, "31")) {
                    // 办理审判人员违法行为监督案件提出检察建议被法院采纳的，每件加2分；
                    listInfo = grjxAjWsMapper.selectMx9(dwbm, gh, kssj, jssj);
                } else if (StringUtils.equals(cxbh, "32")) {
                    // 办理执行监督案件提出检察建议被法院采纳的，每件加2分；
                    listInfo = grjxAjWsMapper.selectMx10(dwbm, gh, kssj, jssj);
                } else if (StringUtils.equals(cxbh, "33")) {
                    // 办理行政机关不当履职的监督案件（包括督促起诉案件）提出检察建议被采纳的，每件加2分
                    listInfo = grjxAjWsMapper.selectMx11(dwbm, gh, kssj, jssj);
                } else if (StringUtils.equals(cxbh, "34")) {
                    // 办理复查案件，改变原处理决定，提出抗诉，得到法院采纳的，每件加3分
                    listInfo = grjxAjWsMapper.selectMx12(dwbm, gh, kssj, jssj);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        resMap.put("total",pager.getTotal());
        resMap.put("rows",listInfo);
        return resMap;
    }

    @Override
    public Map<String, Object> queryCountsOfBjajPageList(Integer page, Integer pageSize, String dwbm, String gh, String kssj, String jssj) {
        Map<String,Object> resMap = new HashMap<>();
        List<Map<String, Object>> listInfo = new ArrayList<>();
        Page pager = PageHelper.startPage(page, pageSize);
        try{
            // 查询办结的案件：【受理日期和完成日期】都在指定时间内的案件
            listInfo = grjxAjWsMapper.queryBjAj(dwbm, gh, kssj, jssj);
        }catch (Exception e){
            e.printStackTrace();
        }
        resMap.put("total",pager.getTotal());
        resMap.put("rows",listInfo);
        return resMap;
    }

    @Override
    public Map<String, Object> getCountsOfExamineAndAcceptPageList(Integer page, Integer pageSize, String dwbm, String gh, String kssj, String jssj) {
        Map<String,Object> resMap = new HashMap<String,Object>();
        List<Map<String, Object>> listInfo = new ArrayList<>();
        Page pager = PageHelper.startPage(page, pageSize);
        try{
            listInfo = grjxAjWsMapper.countsOfExamineAndAccept(dwbm, gh, kssj, jssj);
        }catch (Exception e){
            e.printStackTrace();
        }
        resMap.put("total",pager.getTotal());
        resMap.put("rows",listInfo);
        return resMap;
    }

    @Override
    public Map<String, Object> getWsInfoByBmsahAndTysah(Integer page, Integer pageSize, String bmsah, String tysah) {
        Map<String,Object> resMap = new HashMap<String,Object>();
        List<Map<String, Object>> listInfo = new ArrayList<>();
        Page pager = PageHelper.startPage(page, pageSize);
        try{
            listInfo = grjxAjWsMapper.getWsInfoByBmsahAndTysah(bmsah, tysah);
        }catch (Exception e){
            e.printStackTrace();
        }
        resMap.put("total",pager.getTotal());
        resMap.put("rows",listInfo);
        return resMap;
    }

    @Override
    public Map<String, Object> getAjOfAcceptedPageList(Integer page, Integer pageSize, String dwbm, String gh, String kssj, String jssj) {
        Map<String,Object> resMap = new HashMap<String,Object>();
        List<Map<String, Object>> listInfo = new ArrayList<>();
        Page pager = PageHelper.startPage(page, pageSize);
        try{
            listInfo = grjxAjWsMapper.getAjOfAccepted(dwbm, gh, kssj, jssj);
        }catch (Exception e){
            e.printStackTrace();
        }
        resMap.put("total",pager.getTotal());
        resMap.put("rows",listInfo);
        return resMap;
    }

    @Override
    public Map<String, Object> getAjByajlbbm(Integer page, Integer pageSize, String dwbm, String gh,
                                             String kssj, String jssj, String cxbh) {
        Map<String,Object> resMap = new HashMap<String,Object>();
        List<Map<String, Object>> listInfo = new ArrayList<>();
        List<String> ajlbbms = new ArrayList<>();
        if (StringUtils.equals(cxbh, "11")) {
            ajlbbms.add("0701");
        } else if (StringUtils.equals(cxbh, "12")) {
            ajlbbms.add("0702");
        } else if (StringUtils.equals(cxbh, "13")) {
            ajlbbms.add("0703");
        } else if (StringUtils.equals(cxbh, "14")) {
            ajlbbms.add("0704");
        } else if (StringUtils.equals(cxbh, "15")) {
            ajlbbms.add("0707");
        }
        Page pager = PageHelper.startPage(page, pageSize);
        try{
            listInfo = grjxAjWsMapper.getAjByajlbbm(dwbm, gh, kssj, jssj, ajlbbms);
        }catch (Exception e){
            e.printStackTrace();
        }
        resMap.put("total",pager.getTotal());
        resMap.put("rows",listInfo);
        return resMap;
    }
}
