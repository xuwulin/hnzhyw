package com.swx.ibms.business.performance.controller;


import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.performance.bean.XtGrjxKhrq;
import com.swx.ibms.business.performance.service.XtGrjxKhrqService;
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
import java.util.Map;

@SuppressWarnings("all")
@Controller
@RequestMapping("/grjxKhrqPz")
public class XtGrjxKhrqController {

    @Resource
    private XtGrjxKhrqService xtGrjxKhrqService;

    @RequestMapping(value = "/getGrjxKhrqPzPageList", method = RequestMethod.GET)
    @ResponseBody
    public String getGrjxKhrqPzPageList(//@RequestParam(value="dwbm",required=false)String dwbm,
                                        @RequestParam(value="page",required=true)Integer page,
                                        @RequestParam(value="rows",required=true)Integer rows,
                                        HttpServletRequest request) {
        RYBM ry =(RYBM)request.getSession().getAttribute("ry");
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);
        resMap = xtGrjxKhrqService.getGrjxKhrqPzPageList(page,rows);
        return Constant.GSON.toJson(resMap);
    }



    @RequestMapping(value = "/addOrUpdateGrjxKhrqPz", method = RequestMethod.POST)
    @ResponseBody
    public String addOrUpdateGrjxKhrqPz(@RequestParam(value="id",required=false)String id,
                                        @RequestParam(value="ksrq",required=true)String ksrq,
                                        @RequestParam(value="jsrq",required=true)String jsrq,
                                        @RequestParam(value="deadline",required=true)String deadline,
                                        @RequestParam(value="remarks",required=false)String remarks,
                                        @RequestParam(value="xh",required=false)Integer xh,
                                        HttpServletRequest request) {
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);
        Integer line = 0 ;
        RYBM ry =(RYBM)request.getSession().getAttribute("ry");
        String operator = ry.getDwbm()+"_"+ry.getGh();

        //id--ksrq--jsrq--createby--createdate--updateby--updatedate--remarks--xh--delflag
        XtGrjxKhrq record = new XtGrjxKhrq();
        record.setKsrq(ksrq);
        record.setJsrq(jsrq);
        record.setXh(null==xh?0:xh);
        record.setRemarks(remarks);
        record.setDeadline(deadline);
        record.setUpdateby(operator);

        if(StringUtils.isNotBlank(id)&&!"null".equals(id)){
            record.setId(id);
            record.setUpdatedate(new Date());
            record.setUpdateby(operator);
            line = xtGrjxKhrqService.updateByPrimaryKeySelective(record);
        }else{
            record.setId(Identities.get32LenUUID());
            record.setCreateby(operator);
            record.setDelflag("N");
            record.setCreatedate(new Date());
            line = xtGrjxKhrqService.insertSelective(record);
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
        line = xtGrjxKhrqService.deleteByPrimaryKey(id);
        resMap.put("result",line);
        return Constant.GSON.toJson(resMap);
    }
}
