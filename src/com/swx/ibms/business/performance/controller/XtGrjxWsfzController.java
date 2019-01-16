package com.swx.ibms.business.performance.controller;


import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.performance.bean.XtGrjxWsfz;
import com.swx.ibms.business.performance.service.XtGrjxWsfzService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Controller
@RequestMapping("/xtGrjxWsfzPz")
public class XtGrjxWsfzController {

    @Resource
    private XtGrjxWsfzService xtGrjxWsfzService;

    @RequestMapping(value = "/getPageListWsfz", method = RequestMethod.GET)
    @ResponseBody
    public String getPageListWsfzPzPageList(//@RequestParam(value="dwbm",required=false)String dwbm,
                                            @RequestParam(value="page",required=true)Integer page,
                                            @RequestParam(value="rows",required=true)Integer rows,
                                            HttpServletRequest request) {
        RYBM ry =(RYBM)request.getSession().getAttribute("ry");
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);
        resMap = xtGrjxWsfzService.getXtGrjxWsfzAllPageList(page,rows);
        return Constant.GSON.toJson(resMap);
    }

    @RequestMapping(value = "/addOrUpdateGrjxWsfzPz", method = RequestMethod.POST)
    @ResponseBody
    public String addOrUpdateGrjxKhryTypePz(@RequestParam(value="id",required=false)String id,
                                            @RequestParam(value="ssbm",required=false)String ssbm,
                                            @RequestParam(value="wsNum",required=true)String wsNum,
                                            @RequestParam(value="wsName",required=true)String wsName,
                                            @RequestParam(value="fz",required=true)double fz,
                                            @RequestParam(value="remarks",required=false)String remarks,
                                            @RequestParam(value="xh",required=false)Integer xh,
                                            HttpServletRequest request) {
        RYBM ry =(RYBM)request.getSession().getAttribute("ry");
        Integer line = 0 ;
        String operator = ry.getDwbm()+"_"+ry.getGh();
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);

        //id--wsNum--ssbm--wsName--fz--createby--createdate--updateby--updatedate--remarks--xh
        XtGrjxWsfz grjxWsfzz = new XtGrjxWsfz();
        grjxWsfzz.setSsbm(ssbm);
        grjxWsfzz.setWsNum(wsNum);
        grjxWsfzz.setWsName(wsName);
        grjxWsfzz.setFz(fz);
        grjxWsfzz.setRemarks(remarks);
        grjxWsfzz.setXh(null==xh?0:xh);

        if (StringUtils.isNotBlank(id)){
            grjxWsfzz.setId(id);
            grjxWsfzz.setUpdateby(operator);
            grjxWsfzz.setUpdatedate(new Date());
            line = xtGrjxWsfzService.updateByPrimaryKeySelective(grjxWsfzz);
        }else{

            //查询此文书编码是否存在，存在则不能添加
            List<XtGrjxWsfz> wsfzList = xtGrjxWsfzService.queryResultByParams(grjxWsfzz);
            if(wsfzList.size()>0){
                resMap.put("wsfzList",wsfzList);
            }else{
                grjxWsfzz.setId(Identities.get32LenUUID());
                grjxWsfzz.setCreateby(operator);
                grjxWsfzz.setDelflag("N");
                grjxWsfzz.setCreatedate(new Date());
                line = xtGrjxWsfzService.insertSelective(grjxWsfzz);
            }
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
        line = xtGrjxWsfzService.deleteByPrimaryKey(id);
        resMap.put("result",line);
        return Constant.GSON.toJson(resMap);
    }

}
