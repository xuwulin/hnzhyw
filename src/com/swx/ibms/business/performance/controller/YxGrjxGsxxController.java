package com.swx.ibms.business.performance.controller;


import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.performance.bean.Ydkh;
import com.swx.ibms.business.performance.bean.YxGrjxGsxx;
import com.swx.ibms.business.performance.service.YdkhService;
import com.swx.ibms.business.performance.service.YxGrjxGsxxService;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.common.utils.DateUtil;
import com.swx.ibms.common.utils.Identities;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("all")
@Controller
@RequestMapping("/grjxGsxx")
public class YxGrjxGsxxController {

    @Resource
    private YxGrjxGsxxService yxGrjxGsxxService;

    @Resource
    private YdkhService ydkhService;


    @RequestMapping(value = "/getPageListGrjxGsxx", method = RequestMethod.GET)
    @ResponseBody
    public String getPageListWsfzPzPageList(//@RequestParam(value="dwbm",required=false)String dwbm,
                                            @RequestParam(value="page",required=true)Integer page,
                                            @RequestParam(value="rows",required=true)Integer rows,
                                            HttpServletRequest request) {
        RYBM ry =(RYBM)request.getSession().getAttribute("ry");
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);
        resMap = yxGrjxGsxxService.selectPageList(page,rows);
        return Constant.GSON.toJson(resMap);
    }

    @RequestMapping(value = "/addOrModifyGrjxGsxx", method = RequestMethod.POST)
    @ResponseBody
    public String addOrUpdateGrjxKhryTypePz(@RequestParam(value="id",required=false)String id,
                                            @RequestParam(value="jxkhid",required=true)String jxkhid,
                                            @RequestParam(value="remarks",required=false)String remarks,
                                            @RequestParam(value="ksrq",required=false)String ksrq,
                                            @RequestParam(value="jsrq",required=true)String jsrq,
                                            HttpServletRequest request) {
        RYBM ry =(RYBM)request.getSession().getAttribute("ry");
        Integer resline = 0 ;
        Integer line = 0 ;
        String operator = ry.getDwbm()+"_"+ry.getGh();
        Map<String,Object> resMap = new HashMap<>(Constant.NUM_10);

        Ydkh ydkh = new Ydkh();
        //id--jxkhid--remarks--ksrq--jsrq--createby--createdate--updateby--updatedate--delflag
        YxGrjxGsxx grjxGsxx = yxGrjxGsxxService.selectByOtherKey(jxkhid);
        if(Objects.isNull(grjxGsxx)){
            grjxGsxx = new YxGrjxGsxx();
        }

        grjxGsxx.setRemarks(remarks);
        try{
            grjxGsxx.setKsrq(DateUtil.stringtoDate(DateUtil.getCurrDate(DateUtil.FORMAT_FOUR),DateUtil.FORMAT_FOUR));
            grjxGsxx.setJsrq(DateUtil.stringtoDate(jsrq,DateUtil.FORMAT_FOUR));
        }catch (ParseException e){
            e.printStackTrace();
        }

        if(StringUtils.isNotBlank(jxkhid)){
            grjxGsxx.setJxkhid(jxkhid);

            ydkh.setYdkhid(jxkhid);
            ydkh.setSfgs("1");

            if(!Objects.isNull(grjxGsxx)&& StringUtils.isNotBlank(grjxGsxx.getId())){
                grjxGsxx.setRemarks(remarks);
                try{
                    grjxGsxx.setKsrq(DateUtil.stringtoDate(DateUtil.getCurrDate(DateUtil.FORMAT_FOUR),DateUtil.FORMAT_FOUR));
                    grjxGsxx.setJsrq(DateUtil.stringtoDate(jsrq,DateUtil.FORMAT_FOUR));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                grjxGsxx.setUpdateby(operator);
                grjxGsxx.setUpdatedate(new Date());
                resline = yxGrjxGsxxService.updateByPrimaryKeySelective(grjxGsxx);
            }else{
                if (StringUtils.isNotBlank(id)){
                    grjxGsxx.setId(id);
                    grjxGsxx.setUpdateby(operator);
                    grjxGsxx.setUpdatedate(new Date());
                    resline = yxGrjxGsxxService.updateByPrimaryKeySelective(grjxGsxx);
                }else{
                    grjxGsxx.setId(Identities.get32LenUUID());
                    grjxGsxx.setCreateby(operator);
                    grjxGsxx.setDelflag("N");
                    grjxGsxx.setCreatedate(new Date());
                    resline = yxGrjxGsxxService.insertSelective(grjxGsxx);
                }
            }
        }

        if(resline>0){
            line = ydkhService.updateByPrimaryKeySelective(ydkh);
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
        line = yxGrjxGsxxService.deleteByPrimaryKey(id);
        resMap.put("result",line);
        return Constant.GSON.toJson(resMap);
    }

}
