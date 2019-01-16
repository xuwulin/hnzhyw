package com.swx.ibms.business.rbac.controller;

import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.BusinessType;
import com.swx.ibms.business.rbac.service.BusinessTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 
 * @author east
 * @date:2017年4月21日
 * @version 1.0
 * @description:对应统一业务中单位下部门的业务类别Controller
 *
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/businessType")
public class BusinessTypeController{
	
	/**
	 * 业务类别接口
	 */
	@Resource
	private BusinessTypeService businessTypeService;
	
	/**
	 * 查询全部未被删除的业务类别结果集
	 * @return 结果集字符串
	 */
	@RequestMapping(value = "/selectList", method = RequestMethod.POST)
	@ResponseBody
	public String selectList(){
		Map<String,Object> map = new HashMap<String,Object>();
		List<BusinessType> list = businessTypeService.selectList();
		/*if (!CollectionUtils.isEmpty(list)) {
			map.put("businessTypeList",list);
		}*/
		return Constant.GSON.toJson(list);
	}
}
