package com.swx.ibms.business.performance.service.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swx.ibms.business.appraisal.bean.Ywkhfz;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.service.UploadService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.performance.bean.Pfcl;
import com.swx.ibms.business.performance.bean.Ydkh;
import com.swx.ibms.business.performance.mapper.GrjxMapper;
import com.swx.ibms.business.performance.mapper.GrjxYwkhfzMapper;
import com.swx.ibms.business.performance.mapper.YdkhMapper;
import com.swx.ibms.business.performance.mapper.YxGrjxAjtjMapper;
import com.swx.ibms.business.performance.service.AutoFZService;
import com.swx.ibms.business.performance.service.YdkhService;
import com.swx.ibms.business.system.bean.Upload;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;

/**
 * 月度考核服务实现接口
 *
 * @author 李治鑫
 * @since 2017-5-8
 */
@SuppressWarnings("all")
@Service("ydkhService")
public class YdkhServiceImpl implements YdkhService {
    /**
     * 日志对象
     */
    private static final Logger LOG = Logger.getLogger(YdkhServiceImpl.class);

    /**
     * 业务考核数据交换接口
     */
    @Resource
    private YdkhMapper ydkhMapper;

    /**
     * 业务考核分值接口
     */
    @Resource
    private GrjxYwkhfzMapper grjxYwkhfzMapper;

    /**
     * 个人绩效数据交换接口
     */
    @Resource
    private GrjxMapper grjxmapper;

    @Resource
    private YxGrjxAjtjMapper yxGrjxAjtjMapper;

    /**
     * 日志记录服务
     */
    @Resource
    private LogService logService;
    /**
     * 附件服务
     */
    @Resource
    private UploadService uploadService;

    /**
     * dblink 链接统一业务数据库
     */
    private static final String DBLINK = "@tyyw";

    /**
     * json数据
     */
    private static String json = null;

    /**
     * 需要进行系统评分的子项目数组列表
     */
    private static final String[] ZXMBM_ARRAY = {"0202", "0303", "0304", "0305", "0306", "0307", "0401", "0403", "0404",
            "0505", "0506", "0601", "0602", "0603", "0703", "0705", "0706",
            "0901"};

    // 业务类型数组 02侦监 03公诉 10预防
    // private static final String[] ywlxarray = { "01", "02", "03", "04", "05",
    // "06", "07", "08", "09", "10", "11", "12",
    // "13", "14", "15" };

    @Override
    public boolean isExist (String dwbm, String gh, int year, int season) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_dwbm", dwbm);// 单位编码
        map.put("p_gh", gh);// 工号
        map.put("p_year", year);// 年份
        map.put("p_season", season);// 季度
        map.put("p_result", StringUtils.EMPTY);// 查询结果
        map.put("p_errmsg", StringUtils.EMPTY);// 错误信息

        try {
            ydkhMapper.isCreateinfo(map);
        } catch (Exception e) {
            LOG.error("查询个人绩效信息失败！", e);
            throw e;
        }

        String msg = (String) map.get("p_errmsg");

        if (StringUtils.isEmpty(msg)) {
            String result = (String) map.get("p_result");

            return "1".equals(result);
        } else {
            return false;
        }
    }

    @Override
    public List<Ywkhfz> getYwkhfz (String dwbm, String gh, int year, int season, String ywlx) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_dwbm", dwbm);
        map.put("p_gh", gh);
        map.put("p_year", year);
        map.put("p_season", season);
        map.put("p_ywlx", ywlx);
        map.put("p_cursor", StringUtils.EMPTY);
        map.put("p_errmsg", StringUtils.EMPTY);

        try {
            ydkhMapper.getGrjx(map);
        } catch (Exception e) {
            LOG.error("获取个人绩效分值信息失败！", e);
            throw e;
        }

        List<Ywkhfz> ywkhfzlist = (List<Ywkhfz>) map.get("p_cursor");

        return ywkhfzlist;
    }

    @Override
    // public void addYdkh(String dwbm, String gh, int year, int season,String[]
    // ywlxarray) {
    public void addYdkh (String dwbm, String gh, int year, int season, String ywlx, boolean exitYdkh, String zbkpbt,
                         String ryType) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_dwbm", dwbm);
        map.put("p_gh", gh);
        map.put("p_year", year);
        map.put("p_month", StringUtils.EMPTY);
        map.put("p_jd", season);
        map.put("p_id", StringUtils.EMPTY);
        map.put("p_errmsg", StringUtils.EMPTY);

        if (!exitYdkh) {// 如果月度考核信息不存在则新增
            try {
                ydkhMapper.addYdkh(map);
                // 日志记录
                logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
                        Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ,
                        "新增" + year + "年" + season + "季度考核信息");
            } catch (Exception e) {
                LOG.error("添加个人绩效信息失败！", e);
                // 记录日志
                logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
                        Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, e.toString());
                throw e;
            }
        } else {
            try {
                ydkhMapper.getYdkhid(map);
                map.put("p_errmsg", null);
            } catch (Exception e) {
                LOG.error("查询个人绩效出错！", e);
                throw e;
            }
        }

        String msg = (String) map.get("p_errmsg");
        if (StringUtils.isEmpty(msg)) {
            String ydkhid = (String) map.get("p_id");
            this.addYwkhfz(dwbm, gh, year, season, ydkhid, ywlx, zbkpbt, ryType);
            double score = this.calculateYdkhzf(ydkhid);
            if (score >= 0) {
                this.updateydzfbyid(ydkhid, score);
            }
        }
    }

    /**
     * 根据月度考核ID计算该月的月度考核总分
     *
     * @param ydkhid 月度考核ID
     *
     * @return 成功返回所得的分数，失败返回-1
     */
    @Override
    public double calculateYdkhzf (String ydkhid) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_ydkhid", ydkhid);
        map.put("p_score", StringUtils.EMPTY);
        map.put("p_errmsg", StringUtils.EMPTY);

        try {
            ydkhMapper.calculateydkhzf(map);
        } catch (Exception e) {
            LOG.error(StringUtils.EMPTY, e);
            // 记录日志
            logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
                    Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, e.toString());
        }

        String temp = (String) map.get("p_errmsg");
        if (StringUtils.isEmpty(temp)) {
            return MapUtils.getDoubleValue(map, "p_score", 0);
        } else {
            return -1;
        }
    }

    /**
     * 根据月度考核ID更新月度考核总分
     *
     * @param id     月度考核ID
     * @param ydkhzf 月度考核总分
     */
    @Override
    public void updateydzfbyid (String id, double ydkhzf) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_id", id);
        map.put("p_ydkhzf", ydkhzf);
        map.put("p_errmsg", StringUtils.EMPTY);

        try {
            ydkhMapper.updateydzfbyid(map);
        } catch (Exception e) {
            LOG.error(StringUtils.EMPTY, e);
            // 记录日志
            logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
                    Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, e.toString());
        }

    }

    /**
     * 添加某个月度的业务考核分值信息
     *
     * @param dwbm   单位编码
     * @param gh     工号
     * @param year   年度
     * @param season 季度
     * @param ydkhid 月度考核id
     * @param ywlx   业务类型
     * @param zbkpbt 指标考评概览
     */
    public void addYwkhfz (String dwbm, String gh, int year, int season, String ydkhid, String ywlx, String zbkpbt,
                           String ryType) {
        double ywzf = 0.0;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_ydkhid", ydkhid);

        map.put("p_errmsg", StringUtils.EMPTY);
        // for (int i = 0; i < ywlxarray.length; i++) {
        // map.put("p_ywlx", ywlxarray[i]);
        map.put("p_ywlx", ywlx);
        // 生成默认指标考评概览
        // String zbkpgl = this.createZbkpglFromProperties(ywlxarray[i]);
        String zbkpgl = this.createZbkpglFromZbpz(ydkhid, dwbm, ywlx, gh, year, ryType);
        // Ywkhfz ywkhfzObj = new Ywkhfz("", ydkhid, ywlxarray[i], ywzf,
        // zbkpgl);
        Ywkhfz ywkhfzObj = new Ywkhfz();
        ywkhfzObj.setId(StringUtils.EMPTY);
        ywkhfzObj.setYdkhid(ydkhid);
        ywkhfzObj.setYwlx(ywlx);
        ywkhfzObj.setYwzf(ywzf);
        ywkhfzObj.setZbkpgl(zbkpgl);
        this.conform(dwbm, gh, year, season, ywkhfzObj);

        // 生成考评指标得分
        String zbkpdf = this.getZbkpdf(ywkhfzObj);

        zbkpgl = ywkhfzObj.getZbkpgl();
        ywzf = ywkhfzObj.getYwzf();
        map.put("p_ywzf", ywzf);
        map.put("p_zbkpgl", zbkpgl);
        map.put("p_zbkpdf", zbkpdf);
        map.put("p_zbkpbt", zbkpbt);
        try {
            ydkhMapper.addYwkhfz(map);
        } catch (Exception e) {
            LOG.error("添加个人绩效分值信息出错！", e);
            // 记录日志
            logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
                    Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, e.toString());
            throw e;
        }
        // }

    }

    /**
     * 创建默认指标考评字段信息
     *
     * @return 字符串 ： 指标考评信息
     */
    // public String createZbkpgl() {
    // if (Objects.isNull(json)) {
    //
    // URL url =
    // Thread.currentThread().getContextClassLoader().getResource("initJson2.txt");
    //
    // try {
    // List<String> lines = Files.readAllLines(Paths.get(url.toURI()));
    // String jsonValue = String.join(StringUtils.EMPTY, lines); // 隔行加空格
    // json = jsonValue;
    // } catch (Exception e) {
    // LOG.error(StringUtils.EMPTY, e);
    // }
    // }
    // return json;
    // }

    /**
     * 从pz表中读取评分子项目的配置
     *
     * @param dwbm 单位编码
     * @param ywlx 业务类型
     * @param gh   工号
     *
     * @return 字符串 ： 指标考评信息
     */
    public String createZbkpglFromZbpz (String ydkhid, String dwbm, String ywlx, String gh, int year, String ryType) {
        Map<String, Object> map = new HashMap<>();
        map.put("p_ssrdwbm", dwbm);
        map.put("p_ssrywlx", ywlx);
        map.put("p_ssYear", StringUtils.EMPTY + year);
        map.put("p_ryType", ryType);
        map.put("p_zbpz", null);
        map.put("p_errmsg", null);
        try {
            ydkhMapper.getZbpz(map);
        } catch (Exception e) {
            LOG.error("查询个人绩效某类型的配置指标出错！", e);
            throw e;
        }
        String zbpzStr = "";
        if ("1".equals(map.get("p_errmsg"))) {
            try {
                zbpzStr = IOUtils.toString(((Clob) map.get("p_zbpz")).getCharacterStream());
            } catch (IOException | SQLException e) {
                LOG.error(StringUtils.EMPTY, e);
                // 记录日志
                logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
                        Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, e.toString());
            }
        }

        // 执行自动核算操作
        if (StringUtils.isNoneBlank(zbpzStr)) {
            JsonArray zbpzJsons = (JsonArray) Constant.JSON_PARSER.parse(zbpzStr);
            for (int i = 0; i < zbpzJsons.size(); i++) {
                JsonObject zbpzJson = zbpzJsons.get(i).getAsJsonObject();
                if (!zbpzJson.get("editable").getAsBoolean()) {
                    if (!zbpzJson.has("wjid") || StringUtils.isBlank(zbpzJson.get("wjid").getAsString())) {
                        continue;
                    }

                    String wjid = zbpzJson.get("wjid").getAsString();
                    map.clear();
                    map.put("wjid", wjid);
                    map.put("fslm", null);
                    map.put("errmsg", null);
                    try {
                        ydkhMapper.getFslm(map);
                    } catch (Exception e) {
                        LOG.error("查询个人绩效配置所上传的文件失败！", e);
                        throw e;
                    }
                    String fslm = null;
                    if ("1".equals(map.get("errmsg"))) {
                        fslm = (String) map.get("fslm");
                        try {
                            //获取该项最高评价分【填写的分值最大值】
                            int zgpjf = Integer.parseInt(zbpzJson.get("xmfz").getAsString());//最高得分
                            //如果是侦监业务则匹配出案件名称【zxmmc】
                            String ajlbmc = zbpzJson.get("zxmmc").getAsString();

                            //获取案件信息数据
                            Map<String, Object> ajMap = getDlrAjxxData(dwbm, gh, ywlx, ydkhid, zgpjf, ajlbmc);
                            // 调用计算分值的实现类进行自动计算的分数
                            Class cls = Class.forName(fslm);
                            AutoFZService autoFZService = (AutoFZService) cls.newInstance();
                            Double autoFs = autoFZService.zdjsfz(ajMap);
                            BigDecimal bg = new BigDecimal(autoFs);
                            autoFs = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                            zbpzJson.addProperty("zpdf", autoFs);
                            zbpzJson.addProperty("bmdf", autoFs);
                            if (zbpzJson.has("jcdf")) {
                                zbpzJson.addProperty("jcdf", autoFs);
                            }
                            zbpzJson.addProperty("rsbdf", autoFs);
                        } catch (ClassNotFoundException e) {
                            LOG.error(StringUtils.EMPTY, e);
                            // 记录日志
                            logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
                                    Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, e.toString());
                        } catch (Exception e) {
                            LOG.error(StringUtils.EMPTY, e);
                            // 记录日志
                            logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
                                    Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, e.toString());
                        }

                    }
                }
            }
            return zbpzJsons.toString();
        }

        return zbpzStr;
    }

    /**
     * 根据单位编码、工号以及当前个人绩效的季度时间、案件类别编码获取当前人的案件个数以及案件情况信息
     *
     * @param dwbm   单位编码
     * @param gh     工号
     * @param ywlx   业务类型
     * @param ydkhid 个人绩效考核id
     * @param zgpjf  最高评价分【可以填写的分值】
     *
     * @return 案件信息结果集
     */
    private Map<String, Object> getDlrAjxxData (String dwbm, String gh, String ywlx, String ydkhid, int zgpjf,
                                                String ajlbmc) {
        //推算侦监业务的案件编码
        //案件类别编码字符串
        String ajlbbm = StringUtils.EMPTY;
        StringBuilder ajlbbmTemp = new StringBuilder();

        if ("02".equals(ywlx)) {
            ajlbbmTemp.append("0201,0210,0204,0212");
			/*if("审查逮捕案件".contains(ajlbmc)){
			}else if("延长侦查羁押期限案件".contains(ajlbmc)){
				ajlbbmTemp.append("0210");
			}else if("立案监督案件".contains(ajlbmc)){
				ajlbbmTemp.append("0204");
			}else if("侦查活动监督案件".contains(ajlbmc)){
				ajlbbmTemp.append("0212");
			}else{ //其他案件
				ajlbbmTemp.append("0201,0210,0204,0212");
			}*/
            //			ajlbbm = ajlbbmTemp.substring(0, ajlbbmTemp.length() - 1).trim();
            ajlbbm = ajlbbmTemp.toString();
        }

        Map<String, Object> map = new HashMap<String, Object>(Constant.NUM_8);
        Map<String, Object> resultMap = new HashMap<String, Object>(Constant.NUM_8);
        map.put("p_dwbm", dwbm);
        map.put("p_gh", gh);
        map.put("p_ywlx", ywlx);
        map.put("p_ydkhid", ydkhid);
        map.put("p_ajlbbm", ajlbbm);
        map.put("p_ajCount", StringUtils.EMPTY);
        map.put("p_rjCount", StringUtils.EMPTY);
        map.put("p_cursor", StringUtils.EMPTY);


        //根据单位编码、工号以及当前个人绩效的季度时间、案件类别编码获取当前人的案件个数以及案件情况信息
        try {
            ydkhMapper.getAjxxByDwGhRqAjlb(map);
        } catch (Exception e) {
            LOG.error("查询某人某业务类型的案件信息出错！", e);
            throw e;
        }
        resultMap.put("ajsl", map.get("p_ajCount")); //案件数量
        resultMap.put("rjbasl", map.get("p_rjCount")); //人均办理案件数量
        resultMap.put("zgpjf", zgpjf);
        resultMap.put("ywlx", ywlx);
        return resultMap;
    }

    /**
     * 通过单位编码获取业务类型
     *
     * @param dwbm 单位编码
     *
     * @return null
     */
    public String getYwlxByDwbm (String dwbm) {

        return null;
    }

    /**
     * 整合子项目
     *
     * @param dwbm      单位编码
     * @param gh        工号
     * @param year      年度
     * @param season    季度
     * @param ywkhfzObj 业务考核分值对象
     */
    public void conform (String dwbm, String gh, int year, int season, Ywkhfz ywkhfzObj) {
        String[] zxmbmarray = ZXMBM_ARRAY;
        double total = 0;
        double score = 0;
        for (int i = 0; i < zxmbmarray.length; i++) {
            score = conformSon(dwbm, gh, year, season, ywkhfzObj, zxmbmarray[i]);
            if (score < 0) {
                ywkhfzObj.setYwzf(0);
                return;
            } else {
                total = total + score;
            }
        }
        ywkhfzObj.setYwzf(26.5 - total);

    }

    /**
     * 整合一个子项目指标考评概览
     *
     * @param dwbm      单位编码
     * @param gh        工号
     * @param year      年份
     * @param season    季度
     * @param ywkhfzObj 业务考核分值对象
     * @param zxmbm     子项目编码
     *
     * @return 分值
     */
    public double conformSon (String dwbm, String gh, int year, int season, Ywkhfz ywkhfzObj, String zxmbm) {
        // 根据单位编码、工号、年份、季度获取部门受案号列表
        List<String> bmsahList = null;
        try {
            String newYear = String.valueOf(year);

            String newSeason = String.valueOf(season);

            bmsahList = this.getBmsah(dwbm, gh, newYear, newSeason);
        } catch (Exception e) {
            LOG.error(StringUtils.EMPTY, e);
        }

        if (CollectionUtils.isEmpty(bmsahList)) {
            return -1;
        }

        // 计算这个人员某一个子项目所得分数
        double score = getScore(bmsahList, zxmbm);

        final String zxmbmCopy = zxmbm;

        /**
         * json反序列化
         */
        JsonArray jsonArray = (JsonArray) Constant.JSON_PARSER.parse(ywkhfzObj.getZbkpgl());
        jsonArray.forEach(item -> {
            JsonObject jsonObjItem = (JsonObject) item;
            String zxmbm1 = jsonObjItem.get("zxmbm").getAsString();
            if (zxmbmCopy.equals(zxmbm1)) {
                double fz = jsonObjItem.get("fz").getAsDouble();
                jsonObjItem.addProperty("zpdf", "" + (fz - score));// 自评得分
                jsonObjItem.addProperty("bmdf", "" + (fz - score));// 部门得分
                jsonObjItem.addProperty("rsbdf", "" + (fz - score));// 人事部得分
            }
        });
        // 重新将JSON数据转化成String并赋值给ywkhfzObj对象的指标考评概览zbkpgl字段中
        String czbkpgl = jsonArray.toString();
        ywkhfzObj.setZbkpgl(czbkpgl);

        return score;
    }

    /**
     * 根据单位编码、工号、年份、月份获取该人员在该月份所办理的案件的部门受案号
     *
     * @param dwbm   单位编码
     * @param gh     工号
     * @param year   年份
     * @param season 季度
     *
     * @return 部门受案号列表
     */
    public List<String> getBmsah (String dwbm, String gh, String year, String season) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_dwbm", dwbm);
        map.put("p_gh", gh);
        map.put("p_year", year);
        map.put("p_season", season);
        map.put("p_cursor", StringUtils.EMPTY);
        map.put("p_errmsg", StringUtils.EMPTY);

        try {
            ydkhMapper.getBmsah(map);
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
     * 获取一个按键列表的最终分数
     *
     * @param bmsahList 案件列表
     * @param zxmbm     子项目编码
     *
     * @return 分数
     */
    public double getScore (List<String> bmsahList, String zxmbm) {
        double score = 0;// 总分
        for (int i = 0; i < bmsahList.size(); i++) {
            score += this.getOneScore(bmsahList.get(i), zxmbm);
        }
        return score / bmsahList.size();
    }

    /**
     * 生成并获取人事部得分信息
     *
     * @param ywkhfzObj 业务考核分值对象
     *
     * @return 指标考评得分信息
     */
    public String getZbkpdf (Ywkhfz ywkhfzObj) {
        JsonArray jsonArray = (JsonArray) Constant.JSON_PARSER.parse(ywkhfzObj.getZbkpgl());

        List<String> names = new ArrayList<>();
        List<Double> values = new ArrayList<>();

        for (int j = 0; j < jsonArray.size(); j++) {

            JsonObject item = (JsonObject) jsonArray.get(j);

            String xmmc = item.get("xmmc").getAsString();
            String rsbdf = item.get("rsbdf").getAsString();
            double rsbdfD = NumberUtils.toDouble(rsbdf, 0d);

            if (names.contains(xmmc)) {
                int index = names.indexOf(xmmc);
                double value = values.get(index);
                value = value + rsbdfD;
                values.set(index, value);
            } else {
                names.add(xmmc);
                values.add(rsbdfD);
            }
        }

        final Map<String, List<? extends Object>> pieMap = new HashMap<>();
        pieMap.put("name", names);
        pieMap.put("value", values);

        return Constant.GSON.toJson(pieMap);
    }

    /**
     * 一个案件的评分处理方法
     *
     * @param bmsah 部门受案号
     * @param zxmbm 子评分项目编码
     *
     * @return 分数
     */
    public double getOneScore (String bmsah, String zxmbm) {
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
     * 通过单位编码、工号、年、月查找出月度考核ID
     */
    @Override
    public String selectYdkhid (String dwbm, String gh, int year, int month) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_dwbm", dwbm);
        map.put("p_gh", gh);
        map.put("p_year", year);
        map.put("p_month", month);
        map.put("p_id", StringUtils.EMPTY);
        map.put("p_errmsg", StringUtils.EMPTY);
        ydkhMapper.selectYdkhid(map);
        String ydkhid = null;
        try {
            ydkhid = (String) map.get("p_id");
        } catch (Exception e) {
        }
        return ydkhid;
    }

    /**
     * 通过月度考核ID和业务类型更新业务考核分值信息
     */
    @Override
    public String updateYwkhfz (String id, double zpzf, double pjzf, String zbkpgl, String zbkpdf) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_ydkhid", id);
        map.put("p_zpzf", zpzf);
        map.put("p_pjzf", pjzf);
        map.put("p_zbkpgl", zbkpgl);
        map.put("p_zbkpdf", zbkpdf);
        // map.put("p_zbkpbt", zbkpbt);
        map.put("Y", null);
        ydkhMapper.updateYwkhfz(map);
        String y = (String) map.get("Y");

        // 记录日志
        logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
                Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "保存绩效评分信息");
        return y;
    }

    @Override
    public List<String> getpfrjl (String ydkhid, String ywlx) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> pfrlist = new ArrayList<String>();

        map.put("p_ydkhid", ydkhid);
        map.put("p_ywlx", ywlx);
        map.put("p_zpr", StringUtils.EMPTY);
        map.put("p_bmpfr", StringUtils.EMPTY);
        map.put("p_rsbpfr", StringUtils.EMPTY);
        map.put("p_jcpfr", StringUtils.EMPTY);
        map.put("p_errmsg", StringUtils.EMPTY);

        try {
            ydkhMapper.getpfrjl(map);
        } catch (Exception e) {
            LOG.error(StringUtils.EMPTY, e);
        }

        String temp = (String) map.get("p_errmsg");
        if (StringUtils.isEmpty(temp)) {
            pfrlist.add((String) map.get("p_zpr"));
            pfrlist.add((String) map.get("p_bmpfr"));
            pfrlist.add((String) map.get("p_rsbpfr"));
            pfrlist.add((String) map.get("p_jcpfr"));
        }
        return pfrlist;
    }

    @Override
    public String updatepfrjl (String ydkhid, String zprgh, String zpr, String bmpfrgh, String bmpfr, String rsbpfrgh,
                               String rsbpfr, String jcpfrgh, String jcpfr) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_ydkhid", ydkhid);
        map.put("p_zprgh", zprgh);
        map.put("p_zpr", zpr);
        map.put("p_bmpfrgh", bmpfrgh);
        map.put("p_bmpfr", bmpfr);
        map.put("p_rsbpfrgh", rsbpfrgh);
        map.put("p_rsbpfr", rsbpfr);
        map.put("p_jcpfrgh", jcpfrgh);
        map.put("p_jcpfr", jcpfr);
        map.put("p_errmsg", StringUtils.EMPTY);

        try {
            ydkhMapper.updatepfrjl(map);
        } catch (Exception e) {
            LOG.error(StringUtils.EMPTY, e);
            // 记录日志
            logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
                    Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, e.toString());
        }

        String temp = (String) map.get("p_errmsg");

        if (StringUtils.isEmpty(temp)) {
            return "1";
        } else {
            return "0";
        }
    }

    /**
     * 通过单位编码和工号查出月度考核表里的最新年月,如果没有，查出的是目前的年和月
     */
    @Override
    public Map getNewym (String dwbm, String gh) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_dwbm", dwbm);
        map.put("p_gh", gh);
        map.put("p_year", null);
        map.put("p_month", null);
        try {
            ydkhMapper.getnewym(map);
        } catch (Exception e) {
            LOG.error(StringUtils.EMPTY, e);
        }
        return map;
    }

    /**
     * 获取姓名和得分
     *
     * @param dwbm   单位编码
     * @param gh     工号
     * @param year   年度
     * @param season 季度
     * @param ywlx   业务类型
     *
     * @return Map<String                               ,                                                               Object>
     */
    public Map<String, Object> getNameAndScore (String dwbm, String gh, String year, String season, String ywlx) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_dwbm", dwbm);
        map.put("p_gh", gh);
        map.put("p_year", year);
        map.put("p_jd", season);
        map.put("p_ywlx", ywlx);
        map.put("p_score", null);
        map.put("p_name", null);
        map.put("p_zpjdf", null);
        map.put("p_pdjbmc", null);
        map.put("p_errmsg", null);
        try {
            ydkhMapper.getNameAndScore(map);
        } catch (Exception e) {
            LOG.error(StringUtils.EMPTY, e);
        }
        return map;
    }

    // @Override
    // public String[] getYwlxByBmbm(String[] bmbmarray){
    // String [] ywlxarray = new String[bmbmarray.length];
    // String ywlx = null;
    //
    // if(bmbmarray.length>0){
    // for(int i = 0;i<bmbmarray.length;i++){
    // switch(bmbmarray[i]){
    // case "0002":ywlx="14";break;//案件管理处
    // case "0004":ywlx="02";break;//侦查监督处
    // case "0005":ywlx="03";break;//公诉处
    // case "0006":ywlx="03";break;//公诉二处
    // case "0007":ywlx="04";break;//反贪污贿赂局
    // case "0008":ywlx="02";break;//侦查一处
    // case "0009":ywlx="02";break;//侦查二处
    // case "0011":ywlx="05";break;//反渎职侵权局
    // case "0012":ywlx="07";break;//民事行政监察处
    // case "0013":ywlx="08";break;//控告监察处
    // case "0014":ywlx="09";break;//刑事申诉监察处
    // case "0015":ywlx="13";break;//检察技术处
    // case "0017":ywlx="10";break;//职务犯罪预防处
    // case "0018":ywlx="11";break;//检委会办公室
    // case "0022":ywlx="14";break;//检委会委员列席人员
    // /**
    // * 没有的业务类型：01监督办 06 监所 12复核办 15铁检
    // * 没有对应业务类型的部门：0001 信息化工作办公室 0003院领导 0010综合指导处 0016刑事执行检察处
    // * 0019人民监督员工作办公室 0020副巡视员 0021巡视员 0023办公室 0024统计部门 0027新闻宣传处
    // * */
    // }
    // ywlxarray[i] = ywlx;
    // }
    // }
    // return ywlxarray;
    // }

    @Override
    public Map getNewyj (String dwbm, String gh) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_dwbm", dwbm);
        map.put("p_gh", gh);
        map.put("p_year", null);
        map.put("p_jd", null);
        try {
            ydkhMapper.getnewyj(map);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(StringUtils.EMPTY, e);
        }
        return map;

    }

    @Override
    public String selectJdkhid (String dwbm, String gh, int year, int season) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_dwbm", dwbm);
        map.put("p_gh", gh);
        map.put("p_year", year);
        map.put("p_jd", season);
        map.put("p_id", StringUtils.EMPTY);
        map.put("p_errmsg", StringUtils.EMPTY);
        ydkhMapper.selectJdkhid(map);
        String jdkhid = null;
        try {
            jdkhid = (String) map.get("p_id");
        } catch (Exception e) {
        }
        return jdkhid;
    }

    @Override
    public List<Pfcl> getbz (String ydkhfzid, String pflx, String zbxbm) {
        //		List<Ywkhfz> ywkhfzList = this.getYwkhfz(dwbm, gh, year, season, ywlx);
        //		String ywkhfzid = ywkhfzList.get(0).getId();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_ywkhfzid", ydkhfzid);
        map.put("p_pflx", pflx);
        map.put("p_zbxbm", zbxbm);
        map.put("p_cursor", StringUtils.EMPTY);
        map.put("p_errmsg", StringUtils.EMPTY);

        // 判断是否存在该评分材料信息
        // 存在则直接读取，不存在则创建一个空的评分材料信息
        if (!this.isExistPfcl(ydkhfzid, pflx, zbxbm)) {
            ydkhMapper.addPfcl(map);
        }

        List<Pfcl> pfclList = new ArrayList<Pfcl>();
        ydkhMapper.getbz(map);

        pfclList = (List<Pfcl>) map.get("p_cursor");
        return pfclList;
    }

    /**
     * 判断是否存在评分材料
     *
     * @param ywkhfzid 业务考核分值ID
     * @param pflx     评分类型
     * @param zbxbm    指标项编码
     *
     * @return boolean
     */
    @Override
    public boolean isExistPfcl (String ywkhfzid, String pflx, String zbxbm) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_ywkhfzid", ywkhfzid);
        map.put("p_pflx", pflx);
        map.put("p_zbxbm", zbxbm);
        map.put("p_result", StringUtils.EMPTY);

        boolean flag = false;

        List<Map<String, Object>> list = new ArrayList<>();

        try {
            list = ydkhMapper.selectPfcl(ywkhfzid, pflx, zbxbm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list.size() > 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public int updatabz (String ydkhfzid, String pflx, String zbxbm, String fjxx, String bz) {

        //		List<Ywkhfz> ywkhfzList = this.getYwkhfz(dwbm, gh, year, season, ywlx);
        //		String ywkhfzid = ywkhfzList.get(0).getId();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_ywkhfzid", ydkhfzid);
        map.put("p_pflx", pflx);
        map.put("p_zbxbm", zbxbm);
        map.put("p_fjxx", fjxx);
        map.put("p_bz", bz);
        map.put("p_errmsg", StringUtils.EMPTY);

        ydkhMapper.updatePfcl(map);

        String errmsg = (String) map.get("p_errmsg");

        if (StringUtils.isEmpty(errmsg)) {
            logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
                    Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ, "设置评分备注信息");
            return 1;
        } else {
            logService.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
                    Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, "设置评分备注信息失败");
            return 0;
        }
    }

    @Override
    public String getkhbt (String ssrdwbm, String ssrywlx, String ssrgh, String ssryear, String ssrseason) {
        int year = -1;
        if (ssrseason.length() > 1) {
            ssrseason = ssrseason.substring(1, 2);
        }
        if (StringUtils.isNotBlank(ssryear)) {
            year = Integer.parseInt(ssryear);
        }
        int season = -1;
        if (StringUtils.isNotBlank(ssrseason)) {
            season = Integer.parseInt(ssrseason);
        }
        boolean hasZbkhfz = hasZbkhfz(ssrdwbm, ssrgh, year, season, ssrywlx);
        String bt = null;
        if (!hasZbkhfz) {
            Map<String, Object> map = new HashMap<>();
            map.put("ssrdwbm", ssrdwbm);
            map.put("ssrywlx", ssrywlx);
            map.put("bt", null);
            map.put("errmsg", null);
            try {
                ydkhMapper.getkhbt(map);
            } catch (Exception e) {
                LOG.error(StringUtils.EMPTY, e);
            }
            if ("1".equals(map.get("errmsg"))) {
                bt = (String) map.get("bt");
            }
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("dwbm", ssrdwbm);
            map.put("gh", ssrgh);
            map.put("year", year);
            map.put("season", season);
            map.put("ywlx", ssrywlx);
            map.put("zbkpbt", null);
            map.put("errmsg", null);
            try {
                ydkhMapper.getBtfromywkhfz(map);
            } catch (Exception e) {
                LOG.error(StringUtils.EMPTY, e);
            }
            if ("1".equals(map.get("errmsg"))) {
                bt = (String) map.get("zbkpbt");
            }
        }
        return bt;
    }

    @Override
    public boolean hasZbkhfz (String dwbm, String gh, int year, int season, String ywlx) {
        Map<String, Object> map = new HashMap<>();
        map.put("dwbm", dwbm);
        map.put("gh", gh);
        map.put("year", year);
        map.put("season", season);
        map.put("ywlx", ywlx);
        map.put("count", null);
        map.put("errmsg", null);
        try {
            ydkhMapper.getYdkhfzcount(map);
        } catch (Exception e) {
            LOG.error(StringUtils.EMPTY, e);
        }
        if ("1".equals(map.get("errmsg"))) {
            return (int) map.get("count") > 0;
        }
        return false;
    }

    /**
     * 查询是否有发起审批的信息
     *
     * @param spstid 审批实体id p_spstid
     *
     * @return int 审批实例条数
     */
    public int sffqsp (String spstid) {
        Map<String, String> map = new HashMap<String, String>();

        map.put("p_spstid", spstid);
        map.put("p_count", "");

        ydkhMapper.sffqsp(map);

        String count = map.get("p_count");

        return Integer.valueOf(count);
    }

    /**
     * 删除个人绩效的阅读考核的信息
     * 月度考核表，月度考核分值表，评分材料表，附件信息表，案件统计表中相关的都要删除
     *
     * @param ydkhid 审批实体id ydkhid
     *
     * @return String
     */
    @Override
    public String delydkh (String ydkhid) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Ywkhfz> listfz = null;
        List<Pfcl> listpf = null;
        List<Upload> listfj = null;
        String ydkhfzids = null;
        //根据业务考核表的id查询 月务考核分值表(YX_GRJX_YWKHFZ)的信息
        map.put("p_ydkhid", ydkhid);
        map.put("p_cur", "");

        ydkhMapper.sydkhfz(map);

        listfz = (List<Ywkhfz>) map.get("p_cur");

        map.clear();

        // 拼接 月务考核分值表的id
        if (!CollectionUtils.isEmpty(listfz)) {
            ydkhfzids = "('" + listfz.get(0).getId();
            for (int i = 1; i < listfz.size(); i++) {
                ydkhfzids += "','" + listfz.get(i).getId();
            }

            ydkhfzids += "')";
        }

        // 根据业务考核分值表的id 查询评分材料
        if (!StringUtils.isEmpty(ydkhfzids)) {
            map.put("p_ydkhfzid", ydkhfzids);
            map.put("p_cur", "");

            ydkhMapper.spfcl(map);

            listpf = (List<Pfcl>) map.get("p_cur");
        }
        map.clear();

        //根据评分材料表(YX_GRJX_PFCL)的id查询附件信息（yx_fj）
        if (!CollectionUtils.isEmpty(listpf)) {
            listfj = new ArrayList<Upload>();
            for (Pfcl pf : listpf) {
                List<Upload> temp = uploadService.selectbywbid(pf.getId());
                if (temp != null) {
                    listfj.addAll(temp);
                }
            }
        }

        //删除 按顺序

        //删除附件
        if (!CollectionUtils.isEmpty(listfj)) {

            for (Upload ul : listfj) {
                String path = ul.getWjdz();
                String fjid = ul.getId();
                try {
                    File file = new File(path);
                    if (file.exists()) {
                        file.delete();
                    }
                    uploadService.deleteFj(fjid);
                } catch (Exception e) {
                    LOG.error(StringUtils.EMPTY, e);
                }
            }
        }

        // 删除评分材料
        if (!StringUtils.isEmpty(ydkhfzids)) {
            map.put("p_ydkhfzid", ydkhfzids);
            map.put("Y", "");

            ydkhMapper.dpfcl(map);
            map.clear();
        }

        // 删除业务考核分值
        int data = 0;
        try {
            data = grjxYwkhfzMapper.deleteByYdKhId(ydkhid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String y = "";
        // 删除月度考核分值成功后再删除月度考核
        if (data > 0) {
            // 删除月度考核
            map.put("p_spstid", ydkhid);
            map.put("Y", "");

            try {
                ydkhMapper.delydkh(map);
                y = (String) map.get("Y");

                // 删除添加的案件
                int res = yxGrjxAjtjMapper.deleteByKhid(ydkhid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return y;
    }

    /**
     * 通过单位编码 工号查询个人绩效的年度季度
     *
     * @param dwbm 单位编码
     * @param gh   工号
     *
     * @return Map<String                               ,                               Object>
     */
    public Map<String, Object> getndjdlist (String dwbm, String gh) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> years = new ArrayList<String>();

        map.put("p_dwbm", dwbm);
        map.put("p_gh", gh);
        map.put("p_cur", "");

        ydkhMapper.getndjdlist(map);

        List<Ydkh> listall = (List<Ydkh>) map.get("p_cur");

        map.clear();

        List<String> temp = null;
        for (Ydkh y : listall) {
            String year = (int) y.getYear() + "";
            if (map.get(year) == null) {
                years.add(year);
                map.put(year, new ArrayList<String>());
            }
            temp = (List<String>) map.get(year);
            temp.add((int) y.getJd() + "");
        }

        map.put("years", years);

        return map;
    }

    @Override
    public List<Ydkh> getYdkhById (String ydkhId) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Ydkh> ydkhList = null;
        if (StringUtils.isNoneEmpty(ydkhId)) {
            map.put("p_ydkh_id", ydkhId);
            map.put("p_cursor", StringUtils.EMPTY);

            ydkhMapper.getYdkhById(map);

            ydkhList = (List<Ydkh>) map.get("p_cursor");

        }
        return ydkhList;
    }

    @Override
    public List<Ywkhfz> addGrjxYdkhAndJxfz (String dwbm, String gh, Integer year, String season, String ywlx,
                                            String bgbt, String ryType) {
        //查询月度考核表头
        return null;
    }

    @Override
    public Integer insertSelective (Ydkh record) {
        Integer res = 0;
        try {
            res = ydkhMapper.insertSelective(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Ydkh selectByPrimaryKey (String id) {
        Ydkh res = new Ydkh();
        try {
            res = ydkhMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Integer updateByPrimaryKeySelective (Ydkh record) {
        Integer res = 0;
        try {
            res = ydkhMapper.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public int deleteByPrimaryKey (String id) {
        Integer res = 0;
        try {
            res = ydkhMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Ydkh getGrjxByParams (String dlrDwbm, String dlrGh, Integer nowYear, Date jxKsrq, Date jxJsrq) {
        Ydkh ydkh = new Ydkh();
        try {
            ydkh = ydkhMapper.getGrjxByParams(dlrDwbm, dlrGh, nowYear, jxKsrq, jxJsrq, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ydkh;
    }

    /**
     * 是否审批通过
     * @param ydkhid
     * @return
     */
    @Override
    public boolean isApproved (String ydkhid) {

        Ydkh ydkh = this.selectByPrimaryKey(ydkhid);
        if (!Objects.isNull(ydkh)) {
            //未审批的/未审批通过的才进入系统自动计算
            if ("1".equals(ydkh.getSfsp())) {// sfsp == "1"表示已通过审批
                return true;
            } else {
                return false;
            }
        }
        return true;
    }


}
