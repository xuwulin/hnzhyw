package com.swx.ibms.business.appraisal.controller;


import com.swx.ibms.business.appraisal.bean.YwkhKhlxPz;
import com.swx.ibms.business.appraisal.service.YwkhkhlxpzService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.common.utils.Identities;
import com.swx.ibms.common.utils.PageCommon;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Try on 2017/11/7.
 * 业务考核模块的controller
 */
@Controller
@RequestMapping("ywkhkhlxpz")
@SuppressWarnings("all")
public class YwkhkhlxpzController {
    @Resource
    private YwkhkhlxpzService ywkhkhlxpzService;

    /**
     * 查询全部业务类型并分页
     *
     * @param ywlx     业务类型
     * @param nowPage  当前页
     * @param pageSize 每页显示数
     * @return 参与业务考核的考核单位结果集的json字符串
     * @throws Exception 异常信息
     */
    @RequestMapping(value = "/getPageListKhlxpz", method = RequestMethod.POST)
    @ResponseBody
    public String getPageListKhlxpz(@RequestParam(value = "ywlx", required = false) String ywlx,
                                    @RequestParam(value = "page", required = true) Integer nowPage,
                                    @RequestParam(value = "rows", required = true) Integer pageSize) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>(Constant.NUM_3);
        PageCommon<Object> pageCommon = ywkhkhlxpzService.getPageListKhlx(ywlx, nowPage, pageSize);
        map.put("total", pageCommon.getTotalRecords());
        map.put("rows", pageCommon.getList());
        return Constant.GSON.toJson(map);
    }


    /**
     * @param request 请求
     * @return 返回查出来的业务类型
     */
    @RequestMapping(value = "/getywkhkhlxcom")
    @ResponseBody
    public List<YwkhKhlxPz> getywkhkhlx() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<YwkhKhlxPz> list = ywkhkhlxpzService.getywkhkhlx(map);
        return list;
    }


    /**
     * @param request请求
     * @return 添加业务类型
     */
    @RequestMapping(value = "/addywkhkhlx")
    @ResponseBody
    public String addywkhkhlx(YwkhKhlxPz ywkhKhlxPz) {
        Map<String, Object> map = new HashMap<String, Object>();
        String sid = Identities.get32LenUUID();
        map.put("P_ID", sid);
        map.put("P_YWLX", ywkhKhlxPz.getYwlx());
        map.put("P_YWMC", ywkhKhlxPz.getYwmc());
        map.put("P_KHLX", ywkhKhlxPz.getKhlx());
        map.put("P_KHMC", ywkhKhlxPz.getKhmc());
        map.put("P_QZ", ywkhKhlxPz.getQz());
        map.put("P_ERRMSG", "");
        try {
        	List<String> list = ywkhkhlxpzService.addywkhkhlx(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "success";
    }


    /**
     * @param request请求
     * @return 查询业务类型下考核类型新增编码结果
     */
    @RequestMapping(value = "/cxywkhkhlxbm")
    @ResponseBody
    public String cxywkhkhlxbm() {
        Map<String, Object> map = new HashMap<String, Object>();
        String list = ywkhkhlxpzService.ywkhkhlxbm(map);
        return list;
    }

    @RequestMapping(value = "/deleteywkhkhlx")
    @ResponseBody
    public boolean deleteywkhkhlx(YwkhKhlxPz ywkhKhlxPz) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("P_ID", ywkhKhlxPz.getId());
        map.put("P_ERRMSG", "");
        boolean result = ywkhkhlxpzService.deleteywkhkhlx(map);
        return result;
    }


    /**
     * @param request请求
     * @return 修改业务类型
     */
    @RequestMapping(value = "/updateywkhkhlx")
    @ResponseBody
    public boolean updateywkhkhlx(YwkhKhlxPz ywkhKhlxPz) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("P_ID", ywkhKhlxPz.getId());
        map.put("P_YWLX", ywkhKhlxPz.getYwlx());
        map.put("P_YWMC", ywkhKhlxPz.getYwmc());
        map.put("P_KHMC", ywkhKhlxPz.getKhmc());
        map.put("P_QZ", ywkhKhlxPz.getQz());
        map.put("P_ERRMSG", "");
        boolean result = false;
        try {
        	result = ywkhkhlxpzService.updateywkhkhlx(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return result;
    }

    /**
     * 分业务类型获取业务考核中的考核 【此处还有系统管理中的业务考核-指标管理模块有调用】
     * @param request请求
     * @return 修改业务类型
     */
    @RequestMapping(value = "/getZhyw")
    @ResponseBody
    public List getZhyw() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("p_errmsg", "");
        map.put("p_cursor", null);

        return ywkhkhlxpzService.getZhyw(map);
    }
}
