package com.swx.ibms.business.performance.controller;


import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.performance.bean.XtGrjxRypz;
import com.swx.ibms.business.performance.service.XtGrjxKhryService;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.common.utils.Identities;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@SuppressWarnings("all")
@Controller
@RequestMapping("/grjxKhryPz")
public class XtGrjxKhryController {

    @Resource
    private XtGrjxKhryService xtGrjxKhryService;


    @RequestMapping(value = "/getPageListRy", method = RequestMethod.GET)
    @ResponseBody
    public String getGrjxKhryPzList(@RequestParam(value="dwbm",required=false)String dwbm,
                                    @RequestParam(value="page",required=true)Integer page,
                                    @RequestParam(value="rows",required=true)Integer rows,
                                    HttpServletRequest request) {
        RYBM ry =(RYBM)request.getSession().getAttribute("ry");
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);
        resMap = xtGrjxKhryService.getGrjxKhryPzList(dwbm, page,rows);
        return Constant.GSON.toJson(resMap);
    }

    @RequestMapping(value = "/addOrUpdateGrjxKhryPz", method = RequestMethod.POST)
    @ResponseBody
    public String addOrUpdateGrjxKhryPz(@RequestParam(value="id",required=false)String id,
                                        @RequestParam(value="bmlbmc",required=true)String bmlbmc,
                                        @RequestParam(value="bmlbbm",required=true)String bmlbbm,
                                        @RequestParam(value="typeid",required=true)String typeid,
                                        @RequestParam(value="rydwbm",required=true)String rydwbm,
                                        @RequestParam(value="rygh",required=true)String rygh,
                                        @RequestParam(value="remarks",required=false)String remarks,
                                        @RequestParam(value="xh",required=false)Integer xh,
                                    HttpServletRequest request) {
        RYBM ry =(RYBM)request.getSession().getAttribute("ry");
        Integer line = 0 ;
        String operator = ry.getDwbm()+"_"+ry.getGh();
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);

        //id--typeid--rydwbm--rygh--createby--createdate--updateby--updatedate--remarks--xh
        XtGrjxRypz khryPz = new XtGrjxRypz();
        khryPz.setTypeid(typeid);
        khryPz.setRydwbm(rydwbm);
        khryPz.setRygh(rygh);
        khryPz.setRemarks(remarks);
        khryPz.setXh(null==xh?0:xh);
        khryPz.setBmlbmc(bmlbmc);
        khryPz.setBmlbbm(bmlbbm);
        if (StringUtils.isNotBlank(id)){
            khryPz.setId(id);
            khryPz.setUpdateby(operator);
            khryPz.setUpdatedate(new Date());
            line = xtGrjxKhryService.updateByPrimaryKeySelective(khryPz);
        }else{
            khryPz.setId(Identities.get32LenUUID());
            khryPz.setCreateby(operator);
            khryPz.setDelflag("N");
            khryPz.setCreatedate(new Date());
            line = xtGrjxKhryService.insertSelective(khryPz);
        }
        resMap.put("result",line);
        return Constant.GSON.toJson(resMap);
    }

    @RequestMapping(value = "/deleteByPrimaryKey", method = RequestMethod.POST)
    @ResponseBody
    public String deleteByPrimaryKey(//@RequestParam(value="dwbm",required=false)String dwbm,
                                     @RequestParam(value="id",required=true)String id,
                                     HttpServletRequest request) {
        RYBM ry =(RYBM)request.getSession().getAttribute("ry");
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);
        Integer line = 0 ;
        line = xtGrjxKhryService.deleteByPrimaryKey(id);
        resMap.put("result",line);
        return Constant.GSON.toJson(resMap);
    }

    @RequestMapping(value = "/getGrjxKhryPz", method = RequestMethod.POST)
    @ResponseBody
    public String getGrjxKhryPz(//@RequestParam(value="dwbm",required=false)String dwbm,
                                     HttpServletRequest request) {
        RYBM ry =(RYBM)request.getSession().getAttribute("ry");
        List<XtGrjxRypz> listInfo = new ArrayList<>();
        listInfo = xtGrjxKhryService.getGrjxKhryPzByDwGh(ry.getDwbm(),ry.getGh());
        return Constant.GSON.toJson(listInfo);
    }

}
