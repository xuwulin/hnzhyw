package com.swx.ibms.business.performance.controller;


import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.performance.bean.XtGrjxBmlb;
import com.swx.ibms.business.performance.service.XtGrjxBmlbService;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.common.utils.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Controller
@RequestMapping("/grjxBmlb")
public class XtGrjxBmlbController {

    @Resource
    private XtGrjxBmlbService xtGrjxBmlbService;

    @RequestMapping(value = "/selectAllBmlb", method = RequestMethod.GET)
    @ResponseBody
    public String selectAllBmlb(@RequestParam(value="page",required=true)Integer page,
                                @RequestParam(value="rows",required=true)Integer rows) {

        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);
        resMap = xtGrjxBmlbService.selectAllBmlb(page,rows);
        return Constant.GSON.toJson(resMap);
    }


    @RequestMapping(value = "/insertBmlb", method = RequestMethod.POST)
    @ResponseBody
    public String insertBmlb(@RequestParam(value="bmlbmc",required=true)String bmlbmc,
                             @RequestParam(value="remarks",required=true)String ms,
                             @RequestParam(value="xh",required=false)Integer xh,
                             HttpServletRequest request) {

        RYBM ry =(RYBM)request.getSession().getAttribute("ry");
        Integer line = 0 ;
        String cjr = ry.getDwbm()+"_"+ry.getGh();

        XtGrjxBmlb bmlb = new XtGrjxBmlb();
        bmlb.setBmlbmc(StrUtil.strTransform(bmlbmc));
        bmlb.setRemarks(StrUtil.strTransform(ms));
        bmlb.setXh(null==xh?0:xh);
        bmlb.setCreateby(cjr);

        int data = 0;
        String res = "";
        data = xtGrjxBmlbService.insertBmlb(bmlb);
        if (data > 0) {
            res = "success";
        } else {
            res = "error";
        }

        return res;
    }

    @RequestMapping(value = "/deleteBmlb", method = RequestMethod.POST)
    @ResponseBody
    public String deleteBmlb(@RequestParam(value="id",required=true)String id,
                             HttpServletRequest request) {
        int data = 0;
        String res = "";
        data = xtGrjxBmlbService.deleteBmlb(id);

        if (data > 0) {
            res = "success";
        } else {
            res = "error";
        }

        return Constant.GSON.toJson(res);
    }


    @RequestMapping(value = "/updateBmlb", method = RequestMethod.POST)
    @ResponseBody
    public String updateBmlb(@RequestParam(value="id",required=true)String id,
                             @RequestParam(value="bmlbmc",required=true)String bmlbmc,
                             @RequestParam(value="remarks",required=true)String ms,
                             @RequestParam(value="xh",required=false)Integer xh,
                             HttpServletRequest request) {

        RYBM ry =(RYBM)request.getSession().getAttribute("ry");
        String gxr = ry.getDwbm()+"_"+ry.getGh();
        String res = StringUtils.EMPTY;

        if(StringUtils.isNotBlank(bmlbmc)){
            XtGrjxBmlb bmlb = new XtGrjxBmlb();
            bmlb.setId(id);
            bmlb.setBmlbmc(StrUtil.strTransform(bmlbmc));
            bmlb.setRemarks(StrUtil.strTransform(ms));
            bmlb.setXh(xh);
            bmlb.setUpdateby(gxr);

            int data = 0;

            data = xtGrjxBmlbService.updateBmlb(bmlb);

            if (data > 0) {
                res = "success";
            } else {
                res = "error";
            }
        }else{
            res = "error";
        }

        return res;
    }


    @RequestMapping(value = "/getBmlbList", method = RequestMethod.POST)
    @ResponseBody
    public String getBmlbList(HttpServletRequest request) {
        RYBM ry =(RYBM)request.getSession().getAttribute("ry");
        List<XtGrjxBmlb> list = new ArrayList<>();
        list = xtGrjxBmlbService.getGrjxBmlbList();
        return Constant.GSON.toJson(list);
    }

    @RequestMapping(value = "/getBmlbByBmlbbm", method = RequestMethod.GET)
    @ResponseBody
    public String getBmlbByBmlbbm(@RequestParam(value="bmlbbm",required=true)String bmlbbm) {

        XtGrjxBmlb bmlb = new XtGrjxBmlb();
        bmlb = xtGrjxBmlbService.getBmlbByBmlbbm(bmlbbm);
        return Constant.GSON.toJson(bmlb);
    }


}
