package com.swx.ibms.business.appraisal.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swx.ibms.business.appraisal.bean.YWGZPFCL;
import com.swx.ibms.business.appraisal.bean.Ywlxkh;
import com.swx.ibms.business.appraisal.service.YwlxkhService;
import com.swx.ibms.business.archives.service.JcgSfdaCxService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.rbac.service.LoginService;
import com.swx.ibms.common.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * Title:YwlxkhControl
 * </p>
 * <p>
 * Description: 业务工作类型考核control
 * </p>
 * author 朱春雨 date 2017年6月5日 下午7:04:38
 */
@RequestMapping("/ywlxkh")
@Controller
@SuppressWarnings("all")
public class YwlxkhControl {

    /**
     * 业务类型考核service
     */
    @Resource
    private YwlxkhService ywlxkhservice;

    /**
     * 系统管理服务接口
     */
    @Resource
    private LoginService loginService;


    /**
     * 司法档案查询
     */
    @Resource
    private JcgSfdaCxService jcgSfdaCxService;
    
    /**
     * @param request 得到业务类型考核的表头数据
     * @return 返回值
     */
    @RequestMapping("/getywlxbt")
    public
    @ResponseBody
    String getYwlxBT(HttpServletRequest request) {
        String khlx = request.getParameter("khlx");// 考核类型
        String dwbm = request.getParameter("dwbm");// 单位编码
        String year = request.getParameter("year");// 年份
        Ywlxkh ywlxkh = null;
        try {
            List<Ywlxkh> list = ywlxkhservice.getYwlxB(khlx, dwbm, year);
            if (list.size() > 0) {
                ywlxkh = list.get(0);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return Constant.GSON.toJson(ywlxkh);
    }

    /**
     * @param request 得到业务类型考核的表数据
     * @return 返回值
     */
    @RequestMapping("/getywlxb")
    public
    @ResponseBody
    String getYwlxB(HttpServletRequest request) {
        String khlx = request.getParameter("khlx");// 考核类型
        String dwbm = request.getParameter("dwbm");// 单位编码
        String year = request.getParameter("year");// 年份
        Ywlxkh ywlxkh = null;
        try {
            List<Ywlxkh> list = ywlxkhservice.getYwlxB(khlx, dwbm, year);
            if (list.size() > 0) {
                ywlxkh = list.get(0);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("rows", Constant.JSON_PARSER.parse(ywlxkh.getZbkpgl()));
        return Constant.GSON.toJson(resultMap);
    }

    /**
     * @param request 是否存在材料
     * @return 返回值
     */
    @RequestMapping("/sexistcl")
    public
    @ResponseBody
    String sExistcl(HttpServletRequest request) {
        /*String dwbm = request.getParameter("dwbm");
		String year = request.getParameter("year");
		String khlx = request.getParameter("khlx");*/
        String ywlxkhfzid = request.getParameter("khfzid");
        String pflx = request.getParameter("pflx");
        String zbxbm = request.getParameter("zbxbm");
        String result = "0";
        try {
            YWGZPFCL ywgzpfcl = ywlxkhservice.getcl(ywlxkhfzid, pflx, zbxbm);
            if (ywgzpfcl == null || (ywgzpfcl.getBz() == null && ywgzpfcl.getFjxx() == null)) {
                result = "0";
            } else {
                result = "1";
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;

    }

    /**
     * 获取备注信息
     *
     * @param request 请求参数
     * @return 备注信息
     */
    @RequestMapping(value = "/getbz")
    public
    @ResponseBody
    String getbz(HttpServletRequest request) {
        String dwbm = request.getParameter("dwbm");
        String year = request.getParameter("year");
        String khlx = request.getParameter("khlx");
        String pflx = request.getParameter("pflx");
        String zbxbm = request.getParameter("zbxbm");

        Ywlxkh ywlxkh = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<Ywlxkh> list = ywlxkhservice.getYwlxB(khlx, dwbm, year);
            YWGZPFCL ywgzpfcl = new YWGZPFCL();
            if (list.size() > 0) {
                ywlxkh = list.get(0);
                String ywlxkhfzid = ywlxkh.getYwlxkhfzid();
                ywgzpfcl = ywlxkhservice.getcl(ywlxkhfzid, pflx, zbxbm);

                resultMap.put("id", ywgzpfcl.getId());
                resultMap.put("bz", ywgzpfcl.getBz());
            }
            if (StringUtils.isBlank(ywgzpfcl.getFjxx())) {
                resultMap.put("fjxx", StringUtils.EMPTY);
            } else {
                resultMap.put("fjxx", Constant.JSON_PARSER.parse(ywgzpfcl.getFjxx()));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return Constant.GSON.toJson(resultMap);
    }

    /**
     * 更新备注信息
     *
     * @param request 请求参数
     * @return 执行结果
     */
    @RequestMapping(value = "/setbz")
    public
    @ResponseBody
    String updatabz(HttpServletRequest request) {
        String dwbm = request.getParameter("dwbm");// 所属人的单位编码
        String year = request.getParameter("year");// 年份
        String khlx = request.getParameter("khlx");// 考核类型
        String pflx = request.getParameter("pflx");// 评分类型 1:部门评分，2:案管评分
        String zbxbm = request.getParameter("zbxbm");// 指标项编码
        String fjxx = request.getParameter("fjxx");// 附件ID数组
        String bz = request.getParameter("bz");// 备注

        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 添加或更新个人绩效评分材料信息
        String Y = ywlxkhservice.updatabz(dwbm, year, khlx, pflx, zbxbm, fjxx, bz);
        if (Y == "1") {
            resultMap.put("status", "1");
            return Constant.GSON.toJson(resultMap);
        } else {
            resultMap.put("status", "0");
            return Constant.GSON.toJson(resultMap);
        }
    }

    /**
     * 更新业务类型考核分值
     *
     * @param request 请求参数
     * @return 是否保存成功
     */
    @RequestMapping(value = "/updateywlxkhfz")
    public
    @ResponseBody
    String updateYwkhfz(HttpServletRequest request) {
        String dwbm = request.getParameter("dwbm");
        String year = request.getParameter("year");
        String khlx = request.getParameter("khlx");
        double ywzf = Double.parseDouble(request.getParameter("ywzf"));
        String zbkpgl = request.getParameter("zbkpgl");
        String zbkpdf = request.getParameter("zbkpdf");

        String jdkhid = null;
        String y = "0";
        try {

            List<Ywlxkh> list = ywlxkhservice.getYwlxB(khlx, dwbm, year);
            if (list.size() > 0) {
                String ywlxkhid = list.get(0).getYwlxkhid();// 业务类型考核id

                if (ywlxkhid != null) {
                    y = ywlxkhservice.updateYwlxkhfz(ywlxkhid, khlx, ywzf, zbkpgl, zbkpdf);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            y = "0";
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("Y", y);
        return Constant.GSON.toJson(resultMap);
    }

    /**
     * @param request 更新该单位级别的最高基础分后，把该单位级别中审批通过的单位评价分依次插入
     * @return 是否成功
     */
    @RequestMapping(value = "/updatezjcftoinsertpjf")
    public
    @ResponseBody
    String updatezjcftoinsertpjf(HttpServletRequest request) {
        String dwbm = request.getParameter("dwbm");
        String year = request.getParameter("year");
        String khlx = request.getParameter("khlx");
        String Y = "0";
        try {
            Y = ywlxkhservice.updatezjcftoinsertpjf(dwbm, year, khlx,StringUtils.EMPTY);
        } catch (Exception e) {
            Y = "0";
        }
        return Y;
    }

    @RequestMapping(value = "/countPjdf")
    public
    @ResponseBody
    String countPjdf(HttpServletRequest request) {
        String khfzid = request.getParameter("khfzid");
        String khlx = request.getParameter("khlx");
        String Y = "0";
        try {
            Y = ywlxkhservice.countPjdf(khfzid, khlx);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Y;
    }

    /**
     * @param request 或得指标考评概览指标考评得分最高评价分
     * @return 结果
     */
    @RequestMapping(value = "/selectzbkpglzbkpdfzgpjfById")
    @ResponseBody
    public String selectZbkpglZbkpdfZgpjf(HttpServletRequest request) {
//		long start=System.nanoTime();
        String khfzid = request.getParameter("khfzid");
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map = ywlxkhservice.selectKpglZbkpdfZgpjfById(khfzid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ((String) map.get("zbkpdf") == null) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("name", null);
            map1.put("value", null);
            map1.put("pjdfvalue", null);
            return Constant.GSON.toJson(map1);
        }
//		long end=System.nanoTime();
//		System.out.println("耗时："+(end-start)/1000);
        return Constant.GSON.toJson((JsonObject)
                Constant.JSON_PARSER.parse((String) map.get("zbkpdf")));
    }

    /**
     * 查询业务工作总览
     *
     * @param request
     * @return String
     */
    @RequestMapping("/getkhzl")
    public
    @ResponseBody
    String getkhzl(HttpServletRequest request) {
        String dwbm = request.getParameter("dwbm");
        String year = request.getParameter("year");
        String khlx = request.getParameter("khlx");
        String sfby = request.getParameter("sfby");//是否本院  N 否 Y 是
//		long start=System.nanoTime();

        JsonArray jsonarray = null;

        if (StringUtils.isBlank(khlx)) {
            jsonarray = ywlxkhservice.getkhzl(year, dwbm, sfby);
        } else {
            jsonarray = ywlxkhservice.getkhzl(year, dwbm, khlx, sfby);
        }

//		long end=System.nanoTime();
//		System.out.println("业务共考核首页耗时："+(end-start)/1000);
        return Constant.GSON.toJson(jsonarray);// jsonarray.toString();
    }


    /**
     * 根据当前登录人的单位编码、选中的单位编码、年份查询业务工作考核的单位信息 selectDwkhByDwbmAndYear
     *
     * @param request
     * @return String
     */
    @RequestMapping("/selectDwkh")
    @ResponseBody
    public String selectDwkhByDwbmAndYear(HttpServletRequest request) {
//		long start=System.nanoTime();
        Map<String, Object> map = new HashMap<String, Object>();

        String dlrdwbm = request.getParameter("dlrdwbm");
        String year = request.getParameter("year");
        String cxdwbm = request.getParameter("cxdwbm");
        String zt = request.getParameter("zt");

        map.put("p_year", year);
        map.put("p_dlrdwbm", dlrdwbm);
        map.put("p_cxdwbm", cxdwbm);
        map.put("p_zt", zt);

        JsonArray jsonarray = ywlxkhservice.selectDwkhByDwbmAndYear(map);
//		long end=System.nanoTime();
//		System.out.println("业务工作考核首页条件查询耗时："+(end-start)/1000);	
        return Constant.GSON.toJson(jsonarray);// jsonarray.toString();
    }


    /**
     * 根据考核分值id查询考核分值表信息
     *
     * @param request
     * @return String
     */
    @RequestMapping("/selectYwkhfzById")
    @ResponseBody
    public String selectYwkhfzById(HttpServletRequest request) {

        Map<String, Object> map = new HashMap<String, Object>();

        String ywkhfzid = request.getParameter("ywkhfzid");

        if (StringUtils.isNotEmpty(ywkhfzid)) {
            map.put("p_khfzid", ywkhfzid);
            ywlxkhservice.selectYwkhfzById(map);
        }

        return Constant.GSON.toJson(map);// jsonarray.toString();
    }

    /**
     * 根据业务类型考核分值表id修改业务类型考核分值表指标考评得分
     *
     * @param request 请求对象
     * @return json字符串
     */
    @RequestMapping("/updateZbkpdfById")
    @ResponseBody
    public String updateZbkpdfById(HttpServletRequest request) {

        Map<String, Object> map = new HashMap<String, Object>();

        String khfzid = request.getParameter("khfzid");
        String zbkpdf = request.getParameter("zbkpdf");
        String ywzf = StringUtils.isEmpty(request.getParameter("ywzf")) ? "0" : request.getParameter("ywzf");
        String zpjdf = StringUtils.isEmpty(request.getParameter("zpjdf")) ? "0" : request.getParameter("zpjdf");
        String khf = StringUtils.isEmpty(request.getParameter("khf")) ? "0" : request.getParameter("khf");

        if (StringUtils.isNotEmpty(khfzid)) {
            map.put("p_khfzid", khfzid);
            map.put("p_zbkpdf", zbkpdf);
            map.put("p_zjcf", Double.parseDouble(ywzf));
            map.put("p_zpjf", Double.parseDouble(zpjdf));
            map.put("p_khf", Double.parseDouble(khf));

            String message = ywlxkhservice.updateZbkpdfById(map);
            map.put("msg", message);
        }

        return Constant.GSON.toJson(map);
    }

    /**
     * 修改考核活动表、考核类型表的公布状态
     *
     * @param request 请求对象
     * @return json字符串
     */
    @RequestMapping("/updateGbZtById")
    @ResponseBody
    public String updateGbZtById(HttpServletRequest request) {

        Map<String, Object> map = new HashMap<String, Object>();

        String khid = request.getParameter("ywkhid");
        RYBM ry = (RYBM) request.getSession().getAttribute("ry");
        String sprMc = ry.getMc();//审批人姓名

        if (StringUtils.isNotEmpty(khid)) {
            map.put("p_khid", khid);
            map.put("p_mc", sprMc);

            String message = ywlxkhservice.updateGbZtById(map);
            map.put("msg", message);
        }

        return Constant.GSON.toJson(map);
    }


    /**
     * 根据登录人的单位编码、部门编码查询其所有的业务考核类型
     *
     * @param request 请求对象
     * @return json字符串
     */
    @RequestMapping("/selectKhlxByDwbmAndBmbm")
    @ResponseBody
    public String selectKhlxByDwbmAndBmbm(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> map = new HashMap<String, Object>();

        RYBM ry = (RYBM) session.getAttribute("ry");
        String dlrDwbm = ry.getDwbm();//登录人单位编码
        //获得部门角色
        List<String> dlrBmjs = (List<String>) session.getAttribute("bmjs");

        map = ywlxkhservice.selectKhlxByDwbmAndBmbm(dlrDwbm, dlrBmjs);
        map.put("khlxData", map.get("p_cur"));
        return Constant.GSON.toJson(map);
    }


    /**
     * 根据案管登录人的单位编码、工号查询是否为部门领导
     *
     * @param request 请求对象
     * @return json字符串
     */
    @RequestMapping("/selectJsbmByDwbmAndGh")
    @ResponseBody
    public String selectJsbmByDwbmAndGh(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> reqMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        RYBM ry = (RYBM) session.getAttribute("ry");
        String dlrDwbm = ry.getDwbm();//登录人单位编码
        String dlrGh = ry.getGh();//登录人工号

        reqMap.put("p_dwbm", dlrDwbm);
        reqMap.put("p_gh", dlrGh);
        reqMap.put("p_cur", StringUtils.EMPTY);
        ywlxkhservice.getJsbmByDwbmAndGh(reqMap);
		
		/*//获得角色
		List<JSBM> dlrBmjsList=loginService.getgetBmJs(dlrDwbm, dlrGh);
		List<Integer> ryjsList = new ArrayList<Integer>();// 人员角色
		
		int fgcount = (int)jcgSfdaCxService
				.cxFgBm(dlrDwbm,ry.getGh()).get("count");//查询是否有分管部门以确定是否是分管领导
		if (fgcount>0) {
			ryjsList.add(Constant.NUM_2);// 分管领导
		}
		
		for (int i = 0; i < dlrBmjsList.size(); i++) {
			String jsbm = ((JSBM) dlrBmjsList.get(i)).getJsbm();
			if (jsbm.equals("001") || jsbm.equals("002")) {
				ryjsList.add(Constant.NUM_3);// 部门领导
			} else {
				ryjsList.add(Constant.NUM_4);// 承办人
			}
		}
		
		Collections.sort(ryjsList);// 排序 */
        reqMap.put("ryjsData", reqMap.get("p_cur"));
        return Constant.GSON.toJson(reqMap);
    }


    /**
     * 根据id查询业务考核分值信息、类型考核名称、单位考核状态
     *
     * @param request 请求对象
     * @return JSON字符串
     */
    @RequestMapping("/getYwkhFzBtById")
    @ResponseBody
    public String getYwkhfzById(HttpServletRequest request) {
        String khfzid = request.getParameter("khfzid");// 分值id
        Ywlxkh ywlxkh = null;
        try {
            List<Ywlxkh> list = ywlxkhservice.getYwkhfzById(khfzid);
            if (list.size() > 0) {
                ywlxkh = list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Constant.GSON.toJson(ywlxkh);
    }


    /**
     * 通过部门映射获取业务类型
     *
     * @param request 部门映射字符串
     * @return 业务类型
     * @throws Exception 异常
     */
    @RequestMapping(value = "/getYwlxByBmys", method = RequestMethod.GET)
    @ResponseBody
    public String getYwlxByBmys(@RequestParam(value = "bmysStr", required = true) String bmysStr,
                                HttpServletRequest request) throws Exception {

        String ywlxStr = ywlxkhservice.getYwlxByBmys(bmysStr);

        return Constant.GSON.toJson(ywlxStr);
    }

    /**
     * 根据单位编码、业务类型、时间段查询、是否公示查询检察院业务考核
     *
     * @param dwbm      单位编码
     * @param ywlxStr   业务类型字符串
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 业务考核单位类型信息
     */
    @RequestMapping(value = "/getYwgzDwYwlxkh", method = RequestMethod.GET)
    @ResponseBody
    public String getYwkhDwkh(HttpServletRequest request,
    						 @RequestParam(value = "cxdwbm", required = true) String cxdwbm,
    						 @RequestParam(value = "dlrYwlxStr", required = false) String dlrYwlxStr,
    						 @RequestParam(value = "startDate", required = false) String startDate,
    						 @RequestParam(value = "endDate", required = false) String endDate,
    						 @RequestParam(value = "sfgs", required = false) String sfgs) throws Exception {
        // 查询单位、业务类型字符串、开始时间、结束时间、 单位考核状态(是否公示)
    	//开始时间取完整日期【这儿是因为前台改为了只选择年月，根据条件进行查询时，取不到具体的年月日（案件查询需要）】
    	StringBuilder ksrq = new StringBuilder();  //开始日期
    	StringBuilder jsrq = new StringBuilder();  //结束日期
    	if(StringUtils.isNoneBlank(startDate)||StringUtils.isNoneBlank(endDate)) {
    		int ksYear = Integer.parseInt(startDate.split("-")[0]);
    		int ksMonth = Integer.parseInt(startDate.split("-")[1]);
    		ksrq.append(ksYear).append("-").append(ksMonth).append("-").append(Constant.NUM_1);
    		
    		int jsYear = Integer.parseInt(endDate.split("-")[0]);
    		int jsMonth = Integer.parseInt(endDate.split("-")[1]);
    		int jsDay = DateUtil.getDayByYearAndMonth(jsYear,jsMonth);
    		jsrq.append(jsYear).append("-").append(jsMonth).append("-").append(jsDay);
    	}else {
    		String ksjsrqStr = this.ywlxkhservice.getKhSj();
    		ksrq.append(ksjsrqStr.split(",")[0]);
    		jsrq.append(ksjsrqStr.split(",")[1]);
    	}
    	
        return ywlxkhservice.getYwkhDwkh(cxdwbm, dlrYwlxStr, ksrq.toString(), jsrq.toString(), sfgs);
    }
    
    
    @RequestMapping(value = "/getYwkhDwkhByZtOrGh", method = RequestMethod.GET)
    @ResponseBody
    public String getYwkhDwkhByZtOrGh(HttpServletRequest request) throws Exception {
        
        String sfgs = request.getParameter("sfgs");// 单位考核状态(是否公示)
        RYBM ry = (RYBM) request.getSession().getAttribute("ry");
        String dwbm = ry.getDwbm();//登录人单位编码
        String gh = ry.getGh();//登录人工号
        
//        return ywlxkhservice.getYwkhDwkhByZtOrGh(dwbm,gh,sfgs);
        return null;
    }

    /**
     * 根据业务考核id和业务类型查询业务考核信息
     *
     * @param khdwId  业务考核id
     * @param ywlxStr 业务类型字符串【可能是多个以逗号隔开】
     * @param request 请求对象
     * @return 业务考核信息
     * @throws Exception 异常
     */
    @RequestMapping(value = "/getYwkhByIdAndYwlx", method = RequestMethod.GET)
    @ResponseBody
    public String getYwkhByIdAndYwlx(@RequestParam(value = "khdwId", required = true) String khdwId,
                                     @RequestParam(value = "ywlxStr", required = false) String ywlxStr,
                                     HttpServletRequest request) throws Exception {
        List<Map<String, Object>> list = ywlxkhservice.getYwkhByIdAndYwlx(khdwId, ywlxStr);

        return Constant.GSON.toJson(list);
    }

    /**
     * 根据业务考核id和业务类型查询业务考核信息
     *
     * @param khdwId  业务考核id
     * @param ywlxStr 业务类型字符串【可能是多个以逗号隔开】
     * @param request 请求对象
     * @return 业务考核信息
     * @throws Exception 异常
     */
    @RequestMapping(value = "/getYwkhByywkhIdAndYwlx", method = RequestMethod.GET)
    @ResponseBody
    public String getYwkhByywkhIdAndYwlx(@RequestParam(value = "ywkhkhid", required = true) String ywkhkhid,
                                         @RequestParam(value = "ywlx", required = true) String ywlx,
                                         @RequestParam(value = "khlx", required = false) String khlx,
                                         HttpServletRequest request) throws Exception {
        List<Map<String, Object>> list = ywlxkhservice.getYwkhByywkhIdAndYwlx(ywkhkhid, ywlx,khlx);

        return Constant.GSON.toJson(list);
    }


    /**
     * 根据分值id、类型、指标项编码查询业务考核分的评分材料（即：备注信息以及上传资料）
     *
     * @param ywlxkhfzid 分值表id
     * @param pflx       评分类型【1 考核人评分材料 2 部门评分材料】
     * @param zbxbm      指标项编码
     * @param request    请求对象
     * @return 评分材料的附件信息Json字符串
     */
    @RequestMapping(value = "/getYwkhFzBz")
    @ResponseBody
    public String getYwkhFzBz(@RequestParam(value = "ywlxkhfzid", required = true) String ywlxkhfzid,
                              @RequestParam(value = "pflx", required = true) String pflx,
                              @RequestParam(value = "zbxbm", required = true) String zbxbm,
                              HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        YWGZPFCL ywgzpfcl = new YWGZPFCL();
        ywgzpfcl = ywlxkhservice.getcl2(ywlxkhfzid, pflx, zbxbm);
        resultMap.put("id", ywgzpfcl.getId());
        resultMap.put("bz", ywgzpfcl.getBz());
        resultMap.put("fjxx", (ywgzpfcl.getFjxx()==null||"".equals(ywgzpfcl.getFjxx()))?"":Constant.JSON_PARSER.parse(ywgzpfcl.getFjxx()));
        return Constant.GSON.toJson(resultMap);
    }


    @RequestMapping(value = "/selectkhspr")
    @ResponseBody
    public boolean seletekhspr(@RequestParam(value = "ywkhkhid", required = true) String ywkhkhid) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("P_ID", ywkhkhid);
        map.put("P_COUNT", null);
        map.put("P_ERRMSG", "");
        boolean result = ywlxkhservice.selectkhspr(map);
        return result;
    }

    @RequestMapping(value = "/modifyKhfzBzInfo")
    public
    @ResponseBody
    String modifyKhfzBzInfo(HttpServletRequest request) {
        String ywlxkhfzid = request.getParameter("ywlxkhfzid");
        ;//分值id
        String pflx = request.getParameter("pflx");// 评分类型 1:部门评分，2:案管评分
        String zbxbm = request.getParameter("zbxbm");// 指标项编码
        String fjxx = request.getParameter("fjxx");// 附件ID数组
        String bz = request.getParameter("bz");// 备注

        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 添加或更新个人绩效评分材料信息
        String Y = ywlxkhservice.modifyKhfzBzInfo(ywlxkhfzid, pflx, zbxbm, fjxx, bz);
        if (Y == "1") {
            resultMap.put("status", "1");
            return Constant.GSON.toJson(resultMap);
        } else {
            resultMap.put("status", "0");
            return Constant.GSON.toJson(resultMap);
        }
    }


    /**
     * 根据业务考核分值id修改其填写分值信息
     *
     * @param request 业务考核分值id、业务总分、 zbkpgl 指标考评 、zbkpdf 指标考评得分
     * @return 是否成功的字符串
     */
    @RequestMapping(value = "/modifyYwlxkhfz", method = RequestMethod.POST)
    @ResponseBody
    public String modifyYwlxkhfz(HttpServletRequest request) {
        String khfzid = request.getParameter("khfzid");
        double ywzf = Double.parseDouble(request.getParameter("ywzf"));
        String zbkpgl = request.getParameter("zbkpgl");
        String zbkpdf = request.getParameter("zbkpdf");

        String y = "0";
        try {
            if (khfzid != null) {
                y = ywlxkhservice.modifyYwlxkhfz(khfzid, ywzf, zbkpgl, zbkpdf);
            }
        } catch (Exception e) {
            // TODO: handle exception
            y = "0";
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("Y", y);
        return Constant.GSON.toJson(resultMap);
    }
    
    /**
     * 根据业务考核类型id修改其状态
     *
     * @param request 业务考核类型id
     * @return 是否成功的字符串
     */
    @RequestMapping(value = "/modifyKhlxZtById", method = RequestMethod.POST)
    @ResponseBody
    public String modifyKhlxZtById(@RequestParam(value = "khlxId", required = true) String khlxId,
    							HttpServletRequest request) {
    	Map<String, Object> resultMap = new HashMap<String, Object>(Constant.NUM_3);

        String msg = ywlxkhservice.modifyKhlxZtById(khlxId);
        resultMap.put("msg", msg);
        return Constant.GSON.toJson(resultMap);
    }
    /**
     *
     * 通过id获取考核人的mc和gh
     * @param request
     * @return
     */
    @RequestMapping(value = "/getKhrMcGh", method = RequestMethod.POST)
    @ResponseBody
    public String getKhrMcGh(@RequestParam(value = "id", required = true) String id) {
        return ywlxkhservice.getKhrMcGh(id);
    }
    /**
     *
     * 通过id获取考核人的dwbm和gh
     * @param request
     * @return
     */
    @RequestMapping(value = "/getKhSj", method = RequestMethod.POST)
    @ResponseBody
    public String getKhSj() {
        return ywlxkhservice.getKhSj();  //去数据字典查询开始日期、结束日期
    }

    /**
     *
     * 判断是否是指定考核人
     * @param request
     * @return
     */
    @RequestMapping(value = "/isZdkhr", method = RequestMethod.POST)
    @ResponseBody
    public String isZdkhr(String mc, String gh,String startDate,String endDate) {
        return ywlxkhservice.isZdkhr( mc, gh, startDate, endDate);
    }


    /**
     *
     * 判断是否是当前单位的人
     * @param request
     * @return
     */
    @RequestMapping(value = "/getDqDw", method = RequestMethod.POST)
    @ResponseBody
    public String getDqDw(@RequestParam(value = "id", required = true) String id) {
        return ywlxkhservice.getDqDw(id);
    }
    
    /**
    *通过id查询异议说明
    * @param request
    * @return
    */
   @RequestMapping(value = "/getyysmbyid", method = RequestMethod.POST)
   @ResponseBody
   public String getyysmbyid(@RequestParam(value = "id", required = true) String id) {
         return ywlxkhservice.getyysmbyid(id);
   }

    /**
     *
     * 获取下个发送人的工号
     * @param request
     * @return
     */
    @RequestMapping(value = "/getNextOneGh", method = RequestMethod.POST)
    @ResponseBody
    public String getNextOneGh(String wbid,String cljs) {
        return ywlxkhservice.getNextOneGh(wbid,cljs);
    }
    /**
     *
     * 获取一条考核数据信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getOneMessage", method = RequestMethod.POST)
    @ResponseBody
    public String getOneMessage(String wbid) {
        return ywlxkhservice.getOneMessage(wbid);
    }

    /**
     *
     * 获取一条考核数据信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/dcndkh", method = RequestMethod.POST)
    @ResponseBody
    public String dcndkh(String dz ,String strdwmc ,String kwdwbm ,String strkhlxmc ,String khlx ,String starDate,String endDate, HttpServletRequest request) {

        return ywlxkhservice.dcndkh( dz ,strdwmc , kwdwbm , strkhlxmc , khlx , starDate, endDate,request);

    }
    
}
