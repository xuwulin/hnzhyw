package com.swx.ibms.business.appraisal.controller;


import com.swx.ibms.business.appraisal.bean.Ywkhkhdw;
import com.swx.ibms.business.appraisal.service.YwkhkhdwService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.common.bean.TreeNode;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Try on 2017/11/1.
 *   业务考核模块的controller
 */
@Controller
@RequestMapping("ywkhkhdw")
@SuppressWarnings("all")
public class YwkhkhdwController {

    @Resource
    private YwkhkhdwService ywkhkhdwService;


    /**
     * 查询全部考核单位并分页
     * @param dwbm 单位编码
     * @param nowPage 当前页
     * @param pageSize 每页显示数
     * @return 参与业务考核的考核单位结果集的json字符串
     * @throws Exception 异常信息
     */
    @RequestMapping(value = "/getPageListKhdw", method = RequestMethod.POST)
    @ResponseBody
    public String getPageListKhdw(@RequestParam(value="dwbm",required=false)String dwbm,
                                  @RequestParam(value="page",required=true)Integer nowPage,
                                  @RequestParam(value="rows",required=true)Integer pageSize)throws Exception {
        Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_3);
        PageCommon<Object> pageCommon = ywkhkhdwService.getPageListKhdw(dwbm,nowPage,pageSize);
        map.put("total", pageCommon.getTotalRecords());
        map.put("rows", pageCommon.getList());
        return Constant.GSON.toJson(map);
    }


    /**
     * 通过单位编码获取单位级别
     * @param dwbm 单位编码
     * @return 单位级别
     * @throws Exception 异常信息
     */
    @RequestMapping(value = "/getDwjbByDwbm", method = RequestMethod.POST)
    @ResponseBody
    public String getDwjbByDwbm(@RequestParam(value="dwbm",required=true)String dwbm)throws Exception {
        Map<String,Object> map = ywkhkhdwService.getDwjbByDwbm(dwbm);
        return Constant.GSON.toJson(map);
    }

    /**
     * 添加或者修改被考核单位信息
     * @param dwbm 考核单位编码
     * @param dwmc 考核单位名称
     * @param fdwbm 考核父单位编码
     * @param dwjb 考核单位级别
     * @param dwjc 考核单位简称
     * @return 是否成功的字符串信息
     * @throws Exception 异常
     */
    @RequestMapping(value = "/addOrModifyKhdw", method = RequestMethod.POST)
    @ResponseBody
    public String addOrModifyKhdw(@RequestParam(value="dwbm",required=true)String dwbm,
                                  @RequestParam(value="dwmc",required=true)String dwmc,
                                  @RequestParam(value="fdwbm",required=true)String fdwbm,
                                  @RequestParam(value="dwjb",required=true)String dwjb,
                                  @RequestParam(value="dwjc",required=true)String dwjc)throws Exception {
        Ywkhkhdw khdw = new Ywkhkhdw();
        khdw.setDwbm(dwbm);
        khdw.setDwmc(dwmc);
        khdw.setFdwbm(fdwbm);
        khdw.setDwjb(dwjb);
        khdw.setDwjc(dwjc);
        Map<String,Object> map = ywkhkhdwService.addKhdw(khdw);
        return Constant.GSON.toJson(map);
    }

    /**
     * 根据考核单位编码删除考核信息（逻辑删除）
     * @param dwbm 考核单位编码
     * @return 是否成功的字符串信息
     * @throws Exception 异常
     */
    @RequestMapping(value = "/deleteKhdw", method = RequestMethod.POST)
    @ResponseBody
    public String modifyKhdw(@RequestParam(value="dwbm",required=true)String dwbm)throws Exception {
        Map<String,Object> map = ywkhkhdwService.modifyKhdw(dwbm);
        return Constant.GSON.toJson(map);
    }

    /**
     * 根据单位编码获取考核单位以及考核单位下级
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getKhdwTree", method = RequestMethod.POST)
    @ResponseBody
    public List<TreeNode> getKhdwTree(@RequestParam(value="dwbm",required=false)String dwbm,
                            HttpServletRequest request)throws Exception {
        String khdw = StringUtils.EMPTY; //考核单位
        if(StringUtils.isNotBlank(dwbm)){
            khdw = dwbm;
        }else{
            RYBM ry = (RYBM) request.getSession().getAttribute("ry");
            khdw = ry.getDwbm();// 登录人单位编码
        }
        TreeNode khdwTree = ywkhkhdwService.getKhdwTree(khdw);
        return khdwTree.getChidren();
    }

}
