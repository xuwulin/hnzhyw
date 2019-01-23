package com.swx.ibms.business.performance.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.performance.bean.XtGrjxRytype;
import com.swx.ibms.business.performance.bean.XtGrjxWsfz;
import com.swx.ibms.business.performance.bean.ydkhqbtg;
import com.swx.ibms.business.performance.mapper.GrjxYwkhfzMapper;
import com.swx.ibms.business.performance.mapper.XtGrjxRytypeMapper;
import com.swx.ibms.business.performance.service.GrjxAjWsService;
import com.swx.ibms.business.performance.service.GrjxYwkhfzService;
import com.swx.ibms.business.performance.service.XtGrjxWsfzService;
import com.swx.ibms.business.performance.service.YdkhService;
import com.swx.ibms.common.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@SuppressWarnings("all")
@Service("grjxYwkhfzService")
public class GrjxYwkhfzServiceImpl implements GrjxYwkhfzService {

    @Autowired
    private GrjxYwkhfzMapper grjxYwkhfzMapper;

    @Autowired
    private GrjxAjWsService grjxAjWsService;

    @Autowired
    private YdkhService ydkhService;

    @Autowired
    private XtGrjxWsfzService xtGrjxWsfzService;

    @Autowired
    private XtGrjxRytypeMapper xtGrjxRytypeMapper;

    /**
     * 日志对象
     */
    private static final Logger LOG = Logger.getLogger(GrjxYwkhfzServiceImpl.class);

    @Override
    public Integer deleteByPrimaryKey(String id) {
        Integer res = 0;
        try{
            res = grjxYwkhfzMapper.deleteByPrimaryKey(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Integer insert(ydkhqbtg record) {
        Integer res = 0;
        try{
            res = grjxYwkhfzMapper.insert(record);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Integer insertSelective(ydkhqbtg record) {
        Integer res = 0;
        try{
            res = grjxYwkhfzMapper.insertSelective(record);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public ydkhqbtg selectByPrimaryKey(String id) {
        ydkhqbtg res = new ydkhqbtg();
        try{
            res = grjxYwkhfzMapper.selectByPrimaryKey(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Integer updateByPrimaryKeySelective(ydkhqbtg record) {
        Integer res = 0;
        try{
            res = grjxYwkhfzMapper.updateByPrimaryKeySelective(record);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Integer updateByPrimaryKey(ydkhqbtg record) {
        Integer res = 0;
        try{
            res = grjxYwkhfzMapper.updateByPrimaryKey(record);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public ydkhqbtg getGrjxKhBtAndKhNrByParams (String dwbm, String dwjb, String gh, Integer year,
                                                Date ksrq, Date jsrq, String bmbm) {

        ydkhqbtg ydkhqbtg = new ydkhqbtg();

        try {
            // 个人绩效分值表
            ydkhqbtg = grjxYwkhfzMapper.getGrjxKhBtAndKhNrByParams(dwbm, gh, year, ksrq, jsrq, bmbm);
            if (Objects.isNull(ydkhqbtg)) {
                return ydkhqbtg;
            }

            // 部门类别编码
            String bmlbbm = ydkhqbtg.getYwlx();

            boolean isApproved =  ydkhService.isApproved(ydkhqbtg.getYdkhid());
            // 未审批通过的才进入系统自动计算 ，创建成功后为点击保存按钮的才自动计算？
            if (isApproved) {
                return ydkhqbtg;
            }

            String paramKsrq = DateUtil.dateToString(ksrq, "yyyy-MM-dd");
            String paramJsrq = DateUtil.dateToString(jsrq, "yyyy-MM-dd");

            Integer page = Constant.NUM_1;
            Integer pageSize = Constant.NUM_100000;

            // 个人绩效所属人部门编码
            String ssrBmbm  = ydkhqbtg.getBmbm();
            String rylx = ydkhqbtg.getRylx();
            XtGrjxRytype perType = xtGrjxRytypeMapper.selectByPrimaryKey(rylx);
            // 人员类型名称（检察官/检察辅助人员/司法行政人员）
            String perTypeName = perType.getName();

            JSONArray zbnrJsonArray = JSONArray.parseArray(ydkhqbtg.getZbkpgl());

            for (int i = 0; i < zbnrJsonArray.size(); i++) {
                JSONObject jsonObject = zbnrJsonArray.getJSONObject(i);
                //是否系统评分
                if (!jsonObject.keySet().contains("sfxtpf")) {
                    continue;
                }
                // sfxtpf == "1"表示需要自动计算的项
                if (!StringUtils.equals(jsonObject.get("sfxtpf").toString(), "1")) {
                    continue;
                }

                if (jsonObject.keySet().contains("cxbh")) {
                    // 数量
                    double countSl = 0;
                    // 该项的分值，即基础分，默认为0
                    BigDecimal cxdf = new BigDecimal("0");
                    // 最后得分，zhdf = countSl * cxdf
                    BigDecimal zhdf = new BigDecimal("0");
                    // 查询编号
                    String cxbh = jsonObject.get("cxbh").toString();

                    // 每一个部门中cxbh为0的都是代表有基础分，实行加减分的项
                    if ("0".equals(cxbh)) {
                        // 直接将基础分值赋给最后得分
                        zhdf = new BigDecimal(jsonObject.get("gxfz").toString());
                    } else {
                        // 加载个人绩效表格时自动计算得分
                        zhdf = this.calculationScore(dwbm, dwjb, gh, paramKsrq, paramJsrq, page, pageSize, jsonObject,
                                countSl, cxdf, perTypeName, zhdf, ydkhqbtg, bmlbbm, ssrBmbm, cxbh);
                    }
                    jsonObject = this.FinalScore(jsonObject, zhdf);
                }
            }
            ydkhqbtg.setZbkpgl(zbnrJsonArray.toJSONString());
        } catch (Exception e) {
            LOG.error("个人绩效自动计算出错", e);
            e.printStackTrace();
            throw e;
        }
        return ydkhqbtg;
    }

    /**
     * 计算得分（根据查询编号计算对应的得分：查询编号--> 查询案件/文书数量-->数量*每件分数=得分）
     * cxbh:
     * 0:表示有基础分，实行加减分的项，
     * 1:表示查询一般2分或3分的文书的数量，
     * 2:则是查询其他文书数量，
     * 3:表示查询案件数量，
     * 4:表示查询结案率
     * 5:表示查询应制作未制作文书案件
     * 6:表示部门负责人核阅案件
     * 7:表示亮红灯案件
     * 8:办案数量超过本部门平均办案量xx%的(公诉部门)
     */
    public BigDecimal calculationScore(String dwbm, String dwjb, String gh, String paramKsrq, String paramJsrq,Integer page,
                                       Integer pageSize,JSONObject jsonObject,Double countSl,BigDecimal cxdf, String rylx,
                                       BigDecimal zhdf,ydkhqbtg ydkhqbtg, String bmlbbm, String bmbm, String cxbh) {

        // 单位级别为2表示是省院，暂时不区分省院和下级院（省院和下级院考核指标一样）
//        if ("2".equals(dwjb)) {
        // 涉及到需要查数量的指标项
        if (StringUtils.equals(cxbh,"1") || StringUtils.equals(cxbh,"2") || StringUtils.equals(cxbh,"3")
                || StringUtils.equals(cxbh,"5") || StringUtils.equals(cxbh,"6") || StringUtils.equals(cxbh,"7")
                || StringUtils.equals(cxbh,"9") || StringUtils.equals(cxbh,"10")  || StringUtils.equals(cxbh,"11")
                || StringUtils.equals(cxbh,"12") || StringUtils.equals(cxbh,"13") || StringUtils.equals(cxbh,"14")
                || StringUtils.equals(cxbh,"15") || StringUtils.equals(cxbh,"16") || StringUtils.equals(cxbh,"17")
                || StringUtils.equals(cxbh,"18") || StringUtils.equals(cxbh,"19") || StringUtils.equals(cxbh,"20")
                || StringUtils.equals(cxbh,"21") || StringUtils.equals(cxbh,"22") || StringUtils.equals(cxbh,"23")
                || StringUtils.equals(cxbh,"24") || StringUtils.equals(cxbh,"25") || StringUtils.equals(cxbh,"26")
                || StringUtils.equals(cxbh,"27") || StringUtils.equals(cxbh,"28") || StringUtils.equals(cxbh,"29")
                || StringUtils.equals(cxbh,"30") || StringUtils.equals(cxbh,"31") || StringUtils.equals(cxbh,"32")
                || StringUtils.equals(cxbh,"33") || StringUtils.equals(cxbh,"34")) {

            if (jsonObject.keySet().contains("gxdf")) {
                // 基础分值
                cxdf = new BigDecimal(jsonObject.get("gxfz").toString());
            }

            // 该月度考核的id
            String ydkhid = ydkhqbtg.getYdkhid();
            // 子项目编码（相当于考核表中的每一行的标识码）
            String zxmbm = jsonObject.get("zxmbm").toString();
            // 查询所得的数量
            countSl = this.queryResultCount(dwbm,gh,paramKsrq,paramJsrq,page,pageSize,cxdf, cxbh, ydkhid, zxmbm, bmlbbm);

            if (jsonObject.keySet().contains("sl")) {
                jsonObject.put("sl",countSl);
            }

            BigDecimal sl = new BigDecimal(countSl);

            // 最后得分 = 基础分值 * 数量
            zhdf = cxdf.multiply(sl).setScale(Constant.NUM_2,BigDecimal.ROUND_HALF_UP);
        } else if (jsonObject.get("cxbh").equals("4")) {
            // 查询编号为4：结案率得分
            Double score = this.getFractionOfCaseHandlingEfficiency(page,pageSize,dwbm, gh, paramKsrq, paramJsrq,
                    new BigDecimal(jsonObject.get("gxfz").toString()), bmlbbm, rylx);
            zhdf = new BigDecimal(score);
        } else if (jsonObject.get("cxbh").equals("8")) {
            // 查询编号8：办案数量超过本部门平均办案量xx%的
            Double score = this.getScoreOfCountsRatio(page,pageSize,dwbm, gh, paramKsrq, paramJsrq,bmbm, bmlbbm, rylx);
            zhdf = new BigDecimal(score);
        }
//        }

        return zhdf;
    }

    /**
     * 最后分数
     * @param jsonObject
     * @param zhdf
     * @return
     */
    public JSONObject FinalScore(JSONObject jsonObject, BigDecimal zhdf) {
        // 系统测算基础分
        jsonObject.put("gxdf",zhdf);
        // 自评得分
        if (jsonObject.keySet().contains("zpdf") && jsonObject.get("zpdf").equals("0")) {
            jsonObject.put("zpdf",zhdf);
        }
        // 检察官得分（检察辅助人员才有此项）
        if (jsonObject.keySet().contains("jcgdf") && jsonObject.get("jcgdf").equals("0")) {
            jsonObject.put("jcgdf",zhdf);
        }
        // 部门得分（部门负责人和院领导无此项）
        if (jsonObject.keySet().contains("bmdf") && jsonObject.get("bmdf").equals("0")) {
            jsonObject.put("bmdf",zhdf);
        }
        // 分管领导评分（院领导无此项）
        if (jsonObject.keySet().contains("fglddf") && jsonObject.get("fglddf").equals("0")) {
            jsonObject.put("fglddf",zhdf);
        }
        // 考核委员会得分
        if (jsonObject.keySet().contains("rsbdf") && jsonObject.get("rsbdf").equals("0")) {
            jsonObject.put("rsbdf",zhdf);
        }
        // 交叉考核得分（省院没有此项）
        if (jsonObject.keySet().contains("jcdf") && jsonObject.get("jcdf").equals("0")) {
            jsonObject.put("jcdf",zhdf);
        }

        return jsonObject;
    }

    /**思路：
     *  1、查询类型的编号不同调用不同的SQL
     *  2、文书查询带入单项考核指标的分值方便查询结果
     *  cxbh == 1:查询文书
     *  cxbh == 2:其他文书
     *  cxbh == 3:查询案件
     *
     * */
    private Double queryResultCount(String dwbm, String gh, String paramKsrq, String paramJsrq,
                                    Integer page, Integer pageSize, BigDecimal cxdf, String cxbh,
                                    String ydkhid, String zxmbm, String bmlbbm) {
        double queryResultCount = 0;

        if (StringUtils.equals(cxbh,"1") || StringUtils.equals(cxbh,"2")){ // 查询2分/3分的文书数量，部门不同，文书的模板编号不同
            queryResultCount = this.countsOfWssl(dwbm,gh,paramKsrq,paramJsrq,page,pageSize,cxdf,cxbh,bmlbbm);
        } else if (StringUtils.equals(cxbh,"3")){ // 获取从【办结案件的下拉树】中选择案件添加到yx_grjx_ajtj表的案件数量
            queryResultCount = this.countsOfTjajsl(dwbm,gh,paramKsrq,paramJsrq,page,pageSize,ydkhid,zxmbm);
        } else if (StringUtils.equals(cxbh,"5")) { // 应制作而未制作文书的案件数
            queryResultCount = this.countsOfNotMadeWs(dwbm,gh,paramKsrq,paramJsrq,page,pageSize,bmlbbm);
        } else if (StringUtils.equals(cxbh,"6")) { // 部门负责人核阅案件数
            queryResultCount = this.countsOfReviewAj(dwbm,gh,paramKsrq,paramJsrq,page,pageSize);
        } else if (StringUtils.equals(cxbh,"7")) { // 亮红灯案件（超期案件）,部门不同判断方式不同
            queryResultCount = this.countsOfOvertimeAj(dwbm,gh,paramKsrq,paramJsrq,page,pageSize,bmlbbm);
        } else if (StringUtils.equals(cxbh,"9")) { // 获取办结的案件【受理日期，完成日期都在指定时间内】【检技】
            queryResultCount = this.countsOfTechniqueAj(dwbm,gh,paramKsrq,paramJsrq,page,pageSize);
        } else if (StringUtils.equals(cxbh,"10")) { // 获取指定时间内所受理的案件【控告】
            queryResultCount = this.countsOfAcceptAj(dwbm,gh,paramKsrq,paramJsrq,page,pageSize);
        } else if (StringUtils.equals(cxbh,"11") || StringUtils.equals(cxbh,"12") || StringUtils.equals(cxbh,"13")
                || StringUtils.equals(cxbh,"14") || StringUtils.equals(cxbh,"15") || StringUtils.equals(cxbh,"16")
                || StringUtils.equals(cxbh,"17") || StringUtils.equals(cxbh,"18") || StringUtils.equals(cxbh,"19")
                || StringUtils.equals(cxbh,"20") || StringUtils.equals(cxbh,"21") || StringUtils.equals(cxbh,"22")
                || StringUtils.equals(cxbh,"23") || StringUtils.equals(cxbh,"24") || StringUtils.equals(cxbh,"25")
                || StringUtils.equals(cxbh,"26") || StringUtils.equals(cxbh,"27") || StringUtils.equals(cxbh,"28")
                || StringUtils.equals(cxbh,"29") || StringUtils.equals(cxbh,"30") || StringUtils.equals(cxbh,"31")
                || StringUtils.equals(cxbh,"32") || StringUtils.equals(cxbh,"33") || StringUtils.equals(cxbh,"34")) { // 【侦监、执检，从基础表中获取】
            queryResultCount = this.countsOfZj(dwbm,gh,paramKsrq,paramJsrq,page,pageSize,bmlbbm,cxbh);
        }

        return queryResultCount;
    }

    /**
     * 【侦监、执检】：从基础表工具中获取的案件
     * @param dwbm
     * @param gh
     * @param paramKsrq
     * @param paramJsrq
     * @param page
     * @param pageSize
     * @param bmlbbm
     * @return
     */
    private Integer countsOfZj(String dwbm, String gh, String paramKsrq, String paramJsrq,
                               Integer page, Integer pageSize, String bmlbbm, String cxbh) {
        Integer sl = 0 ;
        Map<String, Object> map = grjxAjWsService.countsOfZj(page,pageSize,dwbm,gh,paramKsrq,paramJsrq,bmlbbm,cxbh);
        int counts = Integer.parseInt(map.get("total").toString());
        if(counts > 0){
            sl = counts;
        }
        return  sl;
    }

    /**
     * 【民行】：根据案件类别编码查询案件   还未确认！
     * @param dwbm
     * @param gh
     * @param paramKsrq
     * @param paramJsrq
     * @param cxbh
     * @param page
     * @param pageSize
     * @return
     */
    private Double getAjByajlbbm(String dwbm, String gh, String paramKsrq, String paramJsrq,
                                 String cxbh, Integer page, Integer pageSize) {
        double sl = 0 ;
        Map<String, Object> map = grjxAjWsService.getAjByajlbbm(page,pageSize,dwbm,gh,paramKsrq,paramJsrq,cxbh);
        int counts = Integer.parseInt(map.get("total").toString());
        //1.两名以上检察官组成办案组共同承办案件，平均计算得分 2.串案按以下方式记分：
        //(一)3件以下(含3件)按1件计算;
        //(二)4至10件按1.5件计算;
        //(三)11件以上(含11件)按2件计算。
        if(counts > 0 && counts <= 3){
            sl = 1;
        } else if (counts >= 4 && counts <= 10) {
            sl = 1.5;
        } else if (counts >= 11) {
            sl = 2;
        }
        return  sl;
    }

    /**
     * 【控告】：审查受理控告申诉案件，
     * 经确认获取考核日期内所有受理的案件
     * @param dwbm
     * @param gh
     * @param paramKsrq
     * @param paramJsrq
     * @param page
     * @param pageSize
     * @return
     */
    private Integer countsOfAcceptAj(String dwbm, String gh, String paramKsrq, String paramJsrq, Integer page, Integer pageSize) {
        Integer sl = 0 ;
        pageSize = Constant.NUM_10;
        Map<String, Object> map = grjxAjWsService.getAjOfAcceptedPageList(page,pageSize,dwbm,gh,paramKsrq,paramJsrq);
        int counts = Integer.parseInt(map.get("total").toString());
        if(counts > 0){
            sl = counts;
        }
        return  sl;
    }

    /**
     * 【检技】：积极参与办理检验鉴定、技术协助、技术性证据审查等检察技术业务工作
     * 经确认查询【受理日期和完成日期都在指定时间内】的办结的案件数量
     * @param dwbm
     * @param gh
     * @param paramKsrq
     * @param paramJsrq
     * @param page
     * @param pageSize
     * @return
     */
    private Integer countsOfTechniqueAj(String dwbm, String gh, String paramKsrq, String paramJsrq, Integer page, Integer pageSize) {
        Integer sl = 0 ;
        Map<String, Object> map = grjxAjWsService.queryCountsOfBjajPageList(page,pageSize,dwbm,gh,paramKsrq,paramJsrq);
        int counts = Integer.parseInt(map.get("total").toString());
        // 每年至少办理5件，每少一件扣1分,如果大于5，则返回0，如果小于5，则返回5 - counts
        if(counts >= 5){
            sl = 0;
        } else {
            sl = 5 - counts;
        }
        return  sl;
    }

    /**
     * 亮红灯案件（办理超期案件）
     * 注意部门类别不同，判定方式不同
     * @param dwbm
     * @param gh
     * @param paramKsrq
     * @param paramJsrq
     * @param page
     * @param pageSize
     * @return
     */
    private Integer countsOfOvertimeAj(String dwbm, String gh, String paramKsrq, String paramJsrq,
                                       Integer page, Integer pageSize, String bmlbbm) {
        Integer sl = 0 ;
        Map<String, Object> map = grjxAjWsService.getCountsOfOvertimeAjPageList(page,pageSize,dwbm,gh,paramKsrq,paramJsrq, bmlbbm);
        int counts = Integer.parseInt(map.get("total").toString());
        if(counts > 0){
            sl = counts;
        }
        return  sl;
    }

    /**
     * 部门负责人或部门负责人指定核阅人，核阅案件数量
     * @param dwbm
     * @param gh
     * @param paramKsrq
     * @param paramJsrq
     * @param page
     * @param pageSize
     * @return
     */
    private Integer countsOfReviewAj(String dwbm, String gh, String paramKsrq, String paramJsrq, Integer page, Integer pageSize) {
        Integer sl = 0 ;
        Map<String, Object> map = grjxAjWsService.getCountsOfReviewAjPageList(page,pageSize,dwbm,gh,paramKsrq,paramJsrq);
        int counts = Integer.parseInt(map.get("total").toString());
        if(counts > 0){
            sl = counts;
        }
        return  sl;
    }

    /**
     * 应该制而未制作文书的案件数量
     * @param dwbm
     * @param gh
     * @param paramKsrq
     * @param paramJsrq
     * @param page
     * @param pageSize
     * @param khid
     * @param zxmbm
     * @return
     */
    private Integer countsOfNotMadeWs(String dwbm, String gh, String paramKsrq, String paramJsrq, Integer page, Integer pageSize, String bmlbbm) {
        Integer sl = 0 ;
        Map<String, Object> map = grjxAjWsService.getCountsOfNotMadeWsPageList(page,pageSize,dwbm,gh,paramKsrq,paramJsrq,bmlbbm);
        int counts = Integer.parseInt(map.get("total").toString());
        if(counts > 0){
            sl = counts;
        }
        return  sl;
    }

    /**
     * 查询添加到案件统计列表中的案件
     * @param dwbm
     * @param gh
     * @param paramKsrq
     * @param paramJsrq
     * @param page
     * @param pageSize
     * @return
     */
    private Integer countsOfTjajsl(String dwbm, String gh, String paramKsrq, String paramJsrq, Integer page, Integer pageSize, String khid, String zxmbm) {
        Integer sl = 0 ;
        Map<String, Object> map = grjxAjWsService.getGrjxAjtjPageList(page,pageSize,khid,zxmbm);
        Integer wssl = Integer.parseInt(map.get("total").toString());
        if(wssl>0){
            sl = wssl;
        }
        return  sl;
    }

    /**
     * 查询 2/3分文书
     *  思路：
     *  1、查询文书分数配置表所有分数为该项分值的文书
     *  2、拿出这些文书的编码【A】
     *  3、获取办理案件中的所有文书，然后将其文书模板编号【B】和【A】中的对比，记录统计个数
     * @param dwbm
     * @param gh
     * @param paramKsrq
     * @param paramJsrq
     * @param page
     * @param pageSize
     * @return
     */
    private Integer countsOfWssl(String dwbm, String gh, String paramKsrq, String paramJsrq, Integer page,
                                 Integer pageSize,BigDecimal cxfz, String cxbh, String bmlbbm) {
        Integer wssl = 0;

        // 先根据部门类别编码和文书的分值查询出文书模板信息
        List<XtGrjxWsfz> wsfzpzList = xtGrjxWsfzService.queryWsfzByFz(bmlbbm, cxfz.doubleValue());

        // 如果根据部门类别编码和文书的分值没有查询到文书模板信息，则表示该部门没有配置文书模板或者没有该分值的文书模板
        if (wsfzpzList.size() > 0) {
            Map<String, Object> map = grjxAjWsService.getAjWs(dwbm,gh,paramKsrq,paramJsrq,page,pageSize, cxfz.toString(), cxbh, bmlbbm);
            Integer rows = Integer.parseInt(map.get("total").toString());
            if(rows>0){
                wssl = rows;
            }
        }
        return wssl;
    }

    @Override
    public ydkhqbtg getGrjxkhByParams(String dwbm, String gh, Integer year, Date ksrq, Date jsrq, String bmbm) {
        ydkhqbtg ydkhqbtg = new ydkhqbtg();
        try {
            ydkhqbtg = grjxYwkhfzMapper.getGrjxKhBtAndKhNrByParams(dwbm, gh, year, ksrq, jsrq, bmbm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ydkhqbtg;
    }

    @Override
    public Integer updateByYdkhIdSelective(ydkhqbtg record) {
        Integer res = 0;
        try{
            res = grjxYwkhfzMapper.updateByYdkhIdSelective(record);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Integer updatePerOfScoreByPrimaryKey(ydkhqbtg record) {
        Integer res = 0;
        try{
            res = grjxYwkhfzMapper.updatePerOfScoreByPrimaryKey(record);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public ydkhqbtg selectByYwkhId(String ydkhid) {
        ydkhqbtg res = new ydkhqbtg();
        try{
            res = grjxYwkhfzMapper.selectByYwkhId(ydkhid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Map<String, Object> getCaseHandlingEfficiency(Integer page, Integer pageSize, String dwbm, String gh,
                                                         String ksrq, String jsrq, String bmlbbm, String rylx) {
        Map<String, Object> resMap = new HashMap<>();

        Integer countsOfCompleted = 0;
        Integer countsOfAccepted = 0;
        Double efficiency = 0.0;
        try {
            countsOfCompleted = Integer.parseInt(grjxAjWsService.getAj(dwbm, gh, ksrq, jsrq, page, pageSize, bmlbbm, rylx).get("total").toString());
            countsOfAccepted = Integer.parseInt(grjxAjWsService.getAjOfAcceptedPageList(page, pageSize, dwbm, gh, ksrq, jsrq).get("total").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (countsOfAccepted != 0) { // 受理案件数量不为0的情况
            efficiency =  (double)countsOfCompleted / countsOfAccepted;
            if (efficiency > 1) {
                efficiency = 1.0;
            }
        } else { // 受理案件数量为0的情况
            if (countsOfCompleted != 0) { // 办结案件数量不为0
                efficiency = 1.0;
            } else { // 办结案件数量为0
                efficiency = 0.0;
            }
        }

        resMap.put("countsOfCompleted", countsOfCompleted);
        resMap.put("countsOfAccepted", countsOfAccepted);
        resMap.put("efficiency", (double)Math.round(efficiency*100)/100); // (double)Math.round(dou*100)/100
        return resMap;
    }

    @Override
    public Map<String, Object> getCaseHandlingRatio(Integer page, Integer pageSize, String dwbm, String gh,
                                                    String ksrq, String jsrq, String bmbm, String bmlbbm, String rylx) {
        Map<String, Object> resMap = new HashMap<>();
        int countsOfCompleted = 0; // 本人所办结的案件数量
        double ratio = 0;
        int sum = 0; // 本部门办结案件数量总和
        double avg = 0; // 本部门办结案件数量平均值
        if (bmbm.length() > 4) {
            bmbm = bmbm.substring(0,4); // 因为要求本部门平均办案数量，故只能取一个部门（如果存在多个部门），因为不确定哪一个是主要部门，所以只能取第一个部门，如果不对，暂时只能改数据库的bmbm为考核的bmbm
        }

        try {
            // 本人办结案件数量
            countsOfCompleted = Integer.parseInt(grjxAjWsService.getAj(dwbm, gh, ksrq, jsrq, page, pageSize, bmlbbm, rylx).get("total").toString());

            // 本部门所办结的案件数量
            sum = Integer.parseInt(grjxAjWsService.selectBjAjOfBm(dwbm, bmbm, ksrq, jsrq, page, pageSize, bmlbbm).get("total").toString());

            // 本部门办结案件数量平均值
            // 1.先查询本部门所有人员
            List<String> ghList = grjxYwkhfzMapper.selectByAjyjGh(dwbm, bmbm); // 本部门所有人员工号（内勤除外）
            if (ghList.size() > 0) {
                // 人均办案数量
                avg = (double)sum / ghList.size();
                // 保留两位小数
                avg = (double)Math.round(avg * 100) / 100;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (avg != 0) { // 平均值不为0
            // 本人办案数量/人均办案数
            ratio = countsOfCompleted / avg;
        } else {
            ratio = 1;
        }

        resMap.put("countsOfCompleted", countsOfCompleted);
        resMap.put("avg", avg);
        resMap.put("ratio", (double)Math.round(ratio*100)/100); // (double)Math.round(dou*100)/100
        return resMap;
    }

    /**
     * 结案率得分
     * @return
     */
    public Double getFractionOfCaseHandlingEfficiency (Integer page, Integer pageSize, String dwbm, String gh,
                                                       String ksrq, String jsrq, BigDecimal gxfz, String bmlbbm,
                                                       String rylx) {

        Double score = 0.0;
        Double efficiency = (Double) this.getCaseHandlingEfficiency(page, pageSize, dwbm, gh, ksrq, jsrq, bmlbbm, rylx).get("efficiency");
        if (efficiency >= 0.8) {
            score = gxfz.doubleValue();
        } else if (efficiency >= 0.7 && efficiency < 0.8) {
            score = gxfz.doubleValue() - 2;
        } else if (efficiency >= 0.6 && efficiency < 0.7) {
            score = gxfz.doubleValue() - 4;
        } else if (efficiency >= 0.5 && efficiency < 0.6) {
            score = gxfz.doubleValue() - 6;
        } else if (efficiency >= 0.4 && efficiency < 0.5) {
            score = gxfz.doubleValue() - 8;
        } else if (efficiency >= 0.3 && efficiency < 0.4) {
            score = gxfz.doubleValue() - 10;
        } else if (efficiency >= 0.2 && efficiency < 0.3) {
            score = gxfz.doubleValue() - 12;
        } else if (efficiency >= 0.1 && efficiency < 0.2) {
            score = gxfz.doubleValue() - 14;
        } else {
            score = 0.0;
        }

        if (score <= 0) {
            score = 0.0;
        }

        return score;
    }

    /**
     * 本人办结案件数量/本部门平均办结案件数量 得分
     * @param page
     * @param pageSize
     * @param dwbm
     * @param gh
     * @param ksrq
     * @param jsrq
     * @param gxfz
     * @param bmbm
     * @return
     */
    public Double getScoreOfCountsRatio(Integer page, Integer pageSize, String dwbm, String gh, String ksrq,
                                        String jsrq, String bmbm, String bmlbbm, String rylx) {

        Double score = 0.0;
        Double ratio = (Double) this.getCaseHandlingRatio(page, pageSize, dwbm, gh, ksrq, jsrq, bmbm, bmlbbm, rylx).get("ratio");
        if (ratio > 0.5) {
            score = 5.0;
        } else if (ratio < 0.5 && ratio > 0.2) {
            score = 3.0;
        } else {
            score = 0.0;
        }

        return score;
    }


}
