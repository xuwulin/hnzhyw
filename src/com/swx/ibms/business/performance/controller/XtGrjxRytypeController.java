package com.swx.ibms.business.performance.controller;


import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.performance.bean.XtGrjxRytype;
import com.swx.ibms.business.performance.service.XtGrjxRytypeService;
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
@RequestMapping("/grjxKhryTypePz")
public class XtGrjxRytypeController {

    @Resource
    private XtGrjxRytypeService xtGrjxRytypeService;

    @RequestMapping(value = "/getPageListRyType", method = RequestMethod.GET)
    @ResponseBody
    public String getGrjxKhryTypePzPageList(//@RequestParam(value="dwbm",required=false)String dwbm,
                                            @RequestParam(value="page",required=true)Integer page,
                                            @RequestParam(value="rows",required=true)Integer rows,
                                            HttpServletRequest request) {
        RYBM ry =(RYBM)request.getSession().getAttribute("ry");
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);
        resMap = xtGrjxRytypeService.getGrjxKhryTypePzPageList(page,rows);
        return Constant.GSON.toJson(resMap);
    }


    @RequestMapping(value = "/addOrUpdateGrjxKhryTypePz", method = RequestMethod.POST)
    @ResponseBody
    public String addOrUpdateGrjxKhryTypePz(@RequestParam(value="id",required=false)String id,
                                        @RequestParam(value="name",required=true)String name,
                                        @RequestParam(value="remarks",required=false)String remarks,
                                        @RequestParam(value="xh",required=false)Integer xh,
                                        HttpServletRequest request) {
        RYBM ry =(RYBM)request.getSession().getAttribute("ry");
        Integer line = 0 ;
        String operator = ry.getDwbm()+"_"+ry.getGh();
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);

        //id--name--createby--createdate--updateby--updatedate--remarks--xh
        XtGrjxRytype khryTypePz = new XtGrjxRytype();
        khryTypePz.setName(name);
        khryTypePz.setRemarks(remarks);
        khryTypePz.setXh(null==xh?0:xh);
        if (StringUtils.isNotBlank(id)){
            khryTypePz.setId(id);
            khryTypePz.setUpdateby(operator);
            khryTypePz.setUpdatedate(new Date());
            line = xtGrjxRytypeService.updateByPrimaryKeySelective(khryTypePz);
        }else{
            khryTypePz.setId(Identities.get32LenUUID());
            khryTypePz.setCreateby(operator);
            khryTypePz.setDelflag("N");
            khryTypePz.setCreatedate(new Date());
            line = xtGrjxRytypeService.insertSelective(khryTypePz);
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
        line = xtGrjxRytypeService.deleteByPrimaryKey(id);
        resMap.put("result",line);
        return Constant.GSON.toJson(resMap);
    }


    @RequestMapping(value = "/getGrjxKhryTypePzList", method = RequestMethod.POST)
    @ResponseBody
    public String getGrjxKhryTypePzList(//@RequestParam(value="dwbm",required=false)String dwbm,
                                     HttpServletRequest request) {
        RYBM ry =(RYBM)request.getSession().getAttribute("ry");
        List<XtGrjxRytype> list = new ArrayList<>();
        list = xtGrjxRytypeService.getGrjxKhryTypePzList();
        return Constant.GSON.toJson(list);
    }

    @RequestMapping(value = "/selectByPrimaryKey", method = RequestMethod.POST)
    @ResponseBody
    public String selectByPrimaryKey(//@RequestParam(value="dwbm",required=false)String dwbm,
                                     @RequestParam(value="id",required=true)String id,
                                     HttpServletRequest request) {
        RYBM ry =(RYBM)request.getSession().getAttribute("ry");
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);
        XtGrjxRytype type = new XtGrjxRytype();
        type = xtGrjxRytypeService.selectByPrimaryKey(id);
//        resMap.put("grjxRyType",type);
        return Constant.GSON.toJson(type);
    }

    @RequestMapping(value = "/getListKhryType", method = RequestMethod.GET)
    @ResponseBody
    public String getListKhryType(//@RequestParam(value="dwbm",required=false)String dwbm,
                                            HttpServletRequest request) {
        RYBM ry =(RYBM)request.getSession().getAttribute("ry");
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);
        List<XtGrjxRytype> list = new ArrayList<>();
        list = xtGrjxRytypeService.getListKhryType();
        return Constant.GSON.toJson(list);
    }

}
