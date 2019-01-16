package com.swx.ibms.business.performance.controller;


import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.performance.service.GrjxAjWsService;
import com.swx.ibms.business.performance.service.GrjxYwkhfzService;
import com.swx.ibms.business.rbac.bean.RYBM;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuwl on 2018/9/12.
 * description: 个人绩效案件文书查询
 */
@RequestMapping("/ajws")
@Controller
public class GrjxAjWsController {

    /**
     * 日志对象
     */
    private static final Logger LOG = Logger.getLogger(GrjxAjWsController.class);

    /**
     * 系统管理服务接口
     */
    @Resource
    private GrjxAjWsService grjxAjWsService;

    @Resource
    private GrjxYwkhfzService grjxYwkhfzService;

    /**
     * 获取在指定时间内已办的案件
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/getAj")
    @ResponseBody
    public String getAj(@RequestParam(value = "dwbm", required = true) String dwbm,
                        @RequestParam(value = "gh", required = true) String gh,
                        @RequestParam(value = "kssj", required = true) String kssj,
                        @RequestParam(value = "jssj", required = true) String jssj,
                        @RequestParam(value = "page", required = true) Integer page,
                        @RequestParam(value = "rows", required = true) Integer rows,
                        @RequestParam(value = "gxfz", required = false) String gxfz,
                        @RequestParam(value = "bmlbbm", required = true) String bmlbbm,
                        @RequestParam(value = "queryNo", required = false) String queryNo,
                        @RequestParam(value = "rylx", required = true) String rylx) {

        Map<String, Object> map = new HashMap<>();
        try {
            map = grjxAjWsService.getAj(dwbm, gh, kssj, jssj, page, rows, bmlbbm, rylx);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(StringUtils.EMPTY, e);
        }

        return Constant.GSON.toJson(map);
    }


    @RequestMapping("/getBjAjOfJcjs")
    @ResponseBody
    public String getBjAjOfJcjs(@RequestParam(value = "dwbm", required = true) String dwbm,
                                @RequestParam(value = "gh", required = true) String gh,
                                @RequestParam(value = "kssj", required = true) String kssj,
                                @RequestParam(value = "jssj", required = true) String jssj,
                                @RequestParam(value = "page", required = true) Integer page,
                                @RequestParam(value = "rows", required = true) Integer rows,
                                @RequestParam(value = "gxfz", required = false) String gxfz,
                                @RequestParam(value = "queryNo", required = false) String queryNo) {

        Map<String, Object> map = new HashMap<>();
        try {
            map = grjxAjWsService.queryCountsOfBjajPageList(page, rows, dwbm, gh, kssj, jssj);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(StringUtils.EMPTY, e);
        }

        return Constant.GSON.toJson(map);
    }

    /**
     * 获取指定时间已办案件对应的所有文书
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/getAjWs")
    @ResponseBody
    public String getAjWs(@RequestParam(value = "dwbm", required = true) String dwbm,
                          @RequestParam(value = "gh", required = true) String gh,
                          @RequestParam(value = "kssj", required = true) String kssj,
                          @RequestParam(value = "jssj", required = true) String jssj,
                          @RequestParam(value = "page", required = true) Integer page,
                          @RequestParam(value = "rows", required = true) Integer rows,
                          @RequestParam(value = "gxfz", required = true) String gxfz,
                          @RequestParam(value = "queryNo", required = true) String queryNo,
                          @RequestParam(value = "bmlbbm", required = true) String bmlbbm) {

        Map<String, Object> map = new HashMap<>();
        try {
            map = grjxAjWsService.getAjWs(dwbm, gh, kssj, jssj, page, rows, gxfz, queryNo, bmlbbm);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(StringUtils.EMPTY, e);
        }

        return Constant.GSON.toJson(map);
    }


    @RequestMapping("/getAllAjByBmsahs")
    @ResponseBody
    public String getAllAjByBmsahs(@RequestParam(value = "bmsahArrays", required = true) String[] bmsahArrays,
                                   @RequestParam(value = "khid", required = true) String khid,
                                   @RequestParam(value = "khfzid", required = true) String khfzid,
                                   @RequestParam(value = "zxmbm", required = true) String zxmbm,
                                   @RequestParam(value = "bmlbbm", required = true) String bmlbbm) {

        Map<String, Object> map = new HashMap<>();
        Integer res = 0;
        try {
            res = grjxAjWsService.getAllAjByBmsahs(bmsahArrays,khid,khfzid,zxmbm, bmlbbm);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(StringUtils.EMPTY, e);
        }

        map.put("data", res);
        return Constant.GSON.toJson(map);
    }

    @RequestMapping(value = "/getGrjxAjtjPageList", method = RequestMethod.GET)
    @ResponseBody
    public String getGrjxAjtjPageList(@RequestParam(value="page",required=true)Integer page,
                                      @RequestParam(value="rows",required=true)Integer rows,
                                      @RequestParam(value="khid",required=true)String khid,
                                      @RequestParam(value="khfzid",required=true)String khfzid,
                                      @RequestParam(value="zxmbm",required=true)String zxmbm,
                                      HttpServletRequest request) {
        RYBM ry =(RYBM)request.getSession().getAttribute("ry");
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);

        resMap = grjxAjWsService.getGrjxAjtjPageList(page, rows ,khid, zxmbm);

        return Constant.GSON.toJson(resMap);
    }

    @RequestMapping(value = "/deleteAjById", method = RequestMethod.POST)
    @ResponseBody
    public String deleteByPrimaryKey(@RequestParam(value="id",required=true)String id,
                                     HttpServletRequest request) {
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);
        Integer line = 0 ;
        line = grjxAjWsService.deleteAjById(id);
        resMap.put("result",line);
        return Constant.GSON.toJson(resMap);
    }

    /**
     * 应制作未制作文书
     * @param page
     * @param rows
     * @param dwbm
     * @param gh
     * @param kssj
     * @param jssj
     * @param bmlbbm
     * @param request
     * @return
     */
    @RequestMapping(value = "/getCountsOfNotMadePageList", method = RequestMethod.GET)
    @ResponseBody
    public String getCountsOfNotMadePageList(@RequestParam(value="page",required=true)Integer page,
                                             @RequestParam(value="rows",required=true)Integer rows,
                                             @RequestParam(value="dwbm",required=true)String dwbm,
                                             @RequestParam(value="gh",required=true)String gh,
                                             @RequestParam(value="kssj",required=true)String kssj,
                                             @RequestParam(value="jssj",required=true)String jssj,
                                             @RequestParam(value="bmlbbm",required=true)String bmlbbm,
                                             HttpServletRequest request) {
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);

        resMap = grjxAjWsService.getCountsOfNotMadeWsPageList(page, rows ,dwbm, gh, kssj, jssj, bmlbbm);

        return Constant.GSON.toJson(resMap);
    }

    @RequestMapping(value = "/getCountsOfReviewPageList", method = RequestMethod.GET)
    @ResponseBody
    public String getCountsOfReviewPageList(@RequestParam(value="page",required=true)Integer page,
                                            @RequestParam(value="rows",required=true)Integer rows,
                                            @RequestParam(value="dwbm",required=true)String dwbm,
                                            @RequestParam(value="gh",required=true)String gh,
                                            @RequestParam(value="kssj",required=true)String kssj,
                                            @RequestParam(value="jssj",required=true)String jssj,
                                            HttpServletRequest request) {
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);

        resMap = grjxAjWsService.getCountsOfReviewAjPageList(page, rows ,dwbm, gh, kssj, jssj);

        return Constant.GSON.toJson(resMap);
    }

    @RequestMapping(value = "/getCountsOfOvertimeAjPageList", method = RequestMethod.GET)
    @ResponseBody
    public String getCountsOfOvertimeAjPageList(@RequestParam(value="page",required=true)Integer page,
                                                @RequestParam(value="rows",required=true)Integer rows,
                                                @RequestParam(value="dwbm",required=true)String dwbm,
                                                @RequestParam(value="gh",required=true)String gh,
                                                @RequestParam(value="kssj",required=true)String kssj,
                                                @RequestParam(value="jssj",required=true)String jssj,
                                                @RequestParam(value="bmlbbm",required=true)String bmlbbm,
                                                HttpServletRequest request) {
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);

        resMap = grjxAjWsService.getCountsOfOvertimeAjPageList(page, rows ,dwbm, gh, kssj, jssj, bmlbbm);

        return Constant.GSON.toJson(resMap);
    }

    @RequestMapping(value = "/getFractionOfCaseHandlingEfficiency", method = RequestMethod.GET)
    @ResponseBody
    public String getFractionOfCaseHandlingEfficiency( @RequestParam(value="dwbm",required=true)String dwbm,
                                                       @RequestParam(value="gh",required=true)String gh,
                                                       @RequestParam(value="kssj",required=true)String kssj,
                                                       @RequestParam(value="jssj",required=true)String jssj,
                                                       @RequestParam(value="bmlbbm",required=true)String bmlbbm,
                                                       @RequestParam(value="rylx",required=true)String rylx) {
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);

        resMap = grjxYwkhfzService.getCaseHandlingEfficiency(Constant.NUM_1, Constant.NUM_100000, dwbm, gh, kssj, jssj, bmlbbm, rylx);

        return Constant.GSON.toJson(resMap);
    }

    @RequestMapping(value = "/getCaseHandlingRatio", method = RequestMethod.GET)
    @ResponseBody
    public String getCaseHandlingRatio( @RequestParam(value="dwbm",required=true)String dwbm,
                                        @RequestParam(value="gh",required=true)String gh,
                                        @RequestParam(value="kssj",required=true)String kssj,
                                        @RequestParam(value="jssj",required=true)String jssj,
                                        @RequestParam(value="bmbm",required=true)String bmbm,
                                        @RequestParam(value="bmlbbm",required=true)String bmlbbm,
                                        @RequestParam(value="rylx",required=true)String rylx) {
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);

        resMap = grjxYwkhfzService.getCaseHandlingRatio(Constant.NUM_1, Constant.NUM_100000, dwbm, gh, kssj, jssj, bmbm, bmlbbm, rylx);

        return Constant.GSON.toJson(resMap);
    }

    @RequestMapping(value = "/getWsInfoByBmsahAndTysah", method = RequestMethod.GET)
    @ResponseBody
    public String getWsInfoByBmsahAndTysah(@RequestParam(value="bmsah",required=true)String bmsah,
                                           @RequestParam(value="tysah",required=true)String tysah,
                                           @RequestParam(value="rows",required=true)Integer rows,
                                           @RequestParam(value="page",required=true)Integer page) {
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);

        resMap = grjxAjWsService.getWsInfoByBmsahAndTysah(page, rows,bmsah, tysah);

        return Constant.GSON.toJson(resMap);
    }

    @RequestMapping(value = "/getAjOfAccepted", method = RequestMethod.GET)
    @ResponseBody
    public String getAjOfAccepted(@RequestParam(value="dwbm",required=true)String dwbm,
                                  @RequestParam(value="gh",required=true)String gh,
                                  @RequestParam(value="kssj",required=true)String kssj,
                                  @RequestParam(value="jssj",required=true)String jssj,
                                  @RequestParam(value="rows",required=true)Integer rows,
                                  @RequestParam(value="page",required=true)Integer page) {
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);

        resMap = grjxAjWsService.getAjOfAcceptedPageList(page, rows,dwbm, gh, kssj, jssj);

        return Constant.GSON.toJson(resMap);
    }

    @RequestMapping(value = "/getCountsOfAccept", method = RequestMethod.GET)
    @ResponseBody
    public String getExamineAndAccept(@RequestParam(value="dwbm",required=true)String dwbm,
                                      @RequestParam(value="gh",required=true)String gh,
                                      @RequestParam(value="kssj",required=true)String kssj,
                                      @RequestParam(value="jssj",required=true)String jssj,
                                      @RequestParam(value="rows",required=true)Integer rows,
                                      @RequestParam(value="page",required=true)Integer page) {
        Map<String,Object> resMap = new HashMap<>();

        resMap = grjxAjWsService.getCountsOfExamineAndAcceptPageList(page, rows,dwbm, gh, kssj, jssj);

        return Constant.GSON.toJson(resMap);
    }

    @RequestMapping(value = "/getCountsOfZj", method = RequestMethod.GET)
    @ResponseBody
    public String getCountsOfZj(@RequestParam(value="dwbm",required=true)String dwbm,
                                @RequestParam(value="gh",required=true)String gh,
                                @RequestParam(value="kssj",required=true)String kssj,
                                @RequestParam(value="jssj",required=true)String jssj,
                                @RequestParam(value="rows",required=true)Integer rows,
                                @RequestParam(value="page",required=true)Integer page,
                                @RequestParam(value="bmlbbm",required=true)String bmlbbm,
                                @RequestParam(value="queryNo",required=true)String cxbh) {
        Map<String,Object> resMap = new HashMap<>();

        resMap = grjxAjWsService.countsOfZj(page, rows,dwbm, gh, kssj, jssj, bmlbbm, cxbh);

        return Constant.GSON.toJson(resMap);
    }

    @RequestMapping(value = "/getAjByajlbbm", method = RequestMethod.GET)
    @ResponseBody
    public String getAjByajlbbm(@RequestParam(value="dwbm",required=true)String dwbm,
                                @RequestParam(value="gh",required=true)String gh,
                                @RequestParam(value="kssj",required=true)String kssj,
                                @RequestParam(value="jssj",required=true)String jssj,
                                @RequestParam(value="rows",required=true)Integer rows,
                                @RequestParam(value="page",required=true)Integer page,
                                @RequestParam(value="queryNo",required=true)String queryNo) {
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);

        resMap = grjxAjWsService.getAjByajlbbm(page, rows,dwbm, gh, kssj, jssj, queryNo);

        return Constant.GSON.toJson(resMap);
    }

//    @RequestMapping(value = "/getAjOfCompleted", method = RequestMethod.GET)
//    @ResponseBody
//    public String getAjOfCompleted(@RequestParam(value="dwbm",required=true)String dwbm,
//                                   @RequestParam(value="gh",required=true)String gh,
//                                   @RequestParam(value="kssj",required=true)String kssj,
//                                   @RequestParam(value="jssj",required=true)String jssj,
//                                   @RequestParam(value="rows",required=true)Integer rows,
//                                   @RequestParam(value="page",required=true)Integer page) {
//        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);
//
//        resMap = grjxAjWsService.getAj(dwbm, gh, kssj, jssj, rows, page);
//
//        return Constant.GSON.toJson(resMap);
//    }


}
