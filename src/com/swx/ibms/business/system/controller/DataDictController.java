package com.swx.ibms.business.system.controller;

import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.system.bean.DataDict;
import com.swx.ibms.business.system.service.DataDictService;
import com.swx.ibms.common.bean.TreeNode;
import com.swx.ibms.common.utils.DateUtil;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by Try on 2017/11/8.
 * 数据字典Controller
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/dataDict")
public class DataDictController {

    @Resource
    private DataDictService dataDictService;

    /**
     * 获取数据字典树
     * @param nowPage
     * @param pageSize
     * @return
     * @throws Exception 异常
     */
    @RequestMapping(value = "/getDictTree", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getDictTree(HttpServletRequest request)throws Exception {
        Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_3);
        TreeNode dictTree = dataDictService.getDictTree();
        map.put("value",dictTree);
        return map;
    }

    /**
     * 查询数据字典并分页
     * @param dictPid 数据字典父id
     * @param nowPage 当前页
     * @param pageSize 每页显示数
     * @return 数据字典结果集
     * @throws Exception 异常
     */
    @RequestMapping(value = "/getPageListDict", method = RequestMethod.GET)
    @ResponseBody
    public String getPageListDict(@RequestParam(value="dictFid",required=false)String dictFid,
                                  @RequestParam(value="page",required=true)Integer nowPage,
                                  @RequestParam(value="rows",required=true)Integer pageSize)throws Exception {
        Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_3);
        PageCommon<Object> pageCommon = dataDictService.getPageListDict(dictFid,nowPage,pageSize);
        map.put("total", pageCommon.getTotalRecords());
        map.put("rows", pageCommon.getList());
        return Constant.GSON.toJson(map);
    }

    /**
     * 添加数据字典信息
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addDict", method = RequestMethod.POST)
    @ResponseBody
    public String addDict(@RequestParam(value="dictFid",required=false)String dictFid,
                          @RequestParam(value="dictName",required=true)String dictName,
                          @RequestParam(value="dictIdentifying",required=true)String dictIdentifying,
                          @RequestParam(value="dictType",required=true)String dictType,
                          @RequestParam(value="dictTypeValue",required=true)String dictTypeValue,
                          @RequestParam(value="dictExplain",required=false)String dictExplain,
                          @RequestParam(value="dwbm",required=true)String dwbm,
                          @RequestParam(value="mc",required=true)String mc)throws Exception {
        Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_3);
        DataDict dict = new DataDict();
        dict.setDictFid(dictFid);
        dict.setDictName(dictName);
        dict.setDictIdentifying(dictIdentifying);
        dict.setDictType(dictType);
        dict.setDictTypeValue(dictTypeValue);
        dict.setDictExplain(dictExplain);
        dict.setDictOperateDwbm(dwbm);
        dict.setDictOperator(mc);
        map = dataDictService.addDict(dict);
        return Constant.GSON.toJson(map);
    }

    /**
     * 根据字典id修改字典信息
     * @param dictId 数据字典信息
     * @return
     * @throws Exception 异常
     */
    @RequestMapping(value = "/modifyDict", method = RequestMethod.POST)
    @ResponseBody
    public String modifyDict(@RequestParam(value="dictId",required=true)String dictId,
                             @RequestParam(value="dictFid",required=false)String dictFid,
                             @RequestParam(value="dictName",required=true)String dictName,
                             @RequestParam(value="dictIdentifying",required=true)String dictIdentifying,
                             @RequestParam(value="dictType",required=true)String dictType,
                             @RequestParam(value="dictTypeValue",required=true)String dictTypeValue,
                             @RequestParam(value="dictExplain",required=false)String dictExplain,
                             @RequestParam(value="dwbm",required=true)String dwbm,
                             @RequestParam(value="mc",required=true)String mc)throws Exception {
        Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_3);
        DataDict dict = new DataDict();
        dict.setDictId(dictId);
        dict.setDictFid(dictFid);
        dict.setDictName(dictName);
        dict.setDictIdentifying(dictIdentifying);
        dict.setDictType(dictType);
        dict.setDictTypeValue(dictTypeValue);
        dict.setDictExplain(dictExplain);
        dict.setDictOperateDwbm(dwbm);
        dict.setDictOperator(mc);
        map = dataDictService.modifyDict(dict);
        return Constant.GSON.toJson(map);
    }

    
    @RequestMapping(value ="/selectidcount")
	@ResponseBody
	public boolean   seleteid(DataDict dict ) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("P_ID",dict.getDictId());
		map.put("P_COUNT",null);
		map.put("P_ERRMSG","");
		boolean result= dataDictService.selectidcount(map);
	 return result;
	}
    
    /**
     * 根据字典id删除字典信息
     * @param dictId 字典id
     * @return
     * @throws Exception 异常
     */
    @RequestMapping(value = "/delDict", method = RequestMethod.GET)
    @ResponseBody
    public String delDict(@RequestParam(value="dictId",required=true)String dictId)throws Exception {
        Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_3);
        map = dataDictService.delDict(dictId);
        return Constant.GSON.toJson(map);
    }
    
    /**
     * @param request 请求
     * @return 返回查出来的公示值
     */
    @RequestMapping(value = "/getgsz")
    @ResponseBody
    public List<DataDict> getgsz() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("P_DICIDEN", Constant.YWKH_GS_VALUE);
        map.put("P_CURSOR", null);
        map.put("P_ERRMSG", "");
        List<DataDict> list = dataDictService.selectisgs(map);
        return list;
    }
    
    
    /**
     * 根据类型或者标识获取下拉框的结果集，或者单一下拉框的值
     * @param request 请求
     * @return 返回查出来的值
     */
    @RequestMapping(value = "/getSelectByTypeOrSign")
    @ResponseBody
    public List<DataDict> getDataDictBylxSign(@RequestParam(value="type",required=false)String type,
            								 @RequestParam(value="sign",required=true)String sign) {
        List<DataDict> list = Collections.EMPTY_LIST;
		try {
			list = dataDictService.getDataDictBylxSign(type,sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return list;
    }
    

    @RequestMapping(value = "/getSystemDateTime", method = RequestMethod.GET)
    @ResponseBody
    public String getSystemDateTime(@RequestParam(value="format",required=false)String format,
    								HttpServletRequest request) {
    	String dateFormat = "yyyy-MM-dd hh:mm:ss";
    	if(StringUtils.isNoneBlank(format)) {
    		dateFormat = format;
    	}
        String systemDateTime = DateUtil.getCurrDate(dateFormat);
		return Constant.GSON.toJson(systemDateTime);
    }

}
