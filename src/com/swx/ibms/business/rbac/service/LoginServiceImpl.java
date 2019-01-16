package com.swx.ibms.business.rbac.service;


import com.swx.ibms.business.performance.service.HCPZService;
import com.swx.ibms.business.rbac.bean.JSBM;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.rbac.mapper.LoginMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 登录服务
 * @author 李治鑫
 *
 */
@SuppressWarnings("all")
@Service("loginService")
public class LoginServiceImpl implements LoginService {

	/**
	 * 日志对象
	 * */
	private static final Logger LOG = Logger.getLogger(LoginServiceImpl.class);


	/**
	 * 数据访问接口
	 **/
	@Resource
	private LoginMapper loginmapper;
	/**
	 * 核查配置服务
	 */
	@Resource
	private HCPZService hcpzservice;

	@Override
	public List<RYBM> getRyByGzzh(String dwbm, String yhm) {
		Map<String,Object> map = new HashMap<String,Object>();

		map.put("p_dwbm", dwbm);
		map.put("p_yhm", yhm);
		map.put("p_cursor", "");
		map.put("p_errmsg","");

		try {
			loginmapper.getRyByGzzh(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		String temp = (String) map.get("p_errmsg");
		if(temp == null||"".equals(temp)){
			return (List<RYBM>) map.get("p_cursor");
		}else{
			return null;
		}

	}


	@Override
	public List<JSBM> getgetBmJs(String dwbm, String gh) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_dwbm", dwbm);
		map.put("p_gh", gh);
		map.put("p_cursor",null);
		try {
			loginmapper.getbmjs(map);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return (List<JSBM>) map.get("p_cursor");
	}


	@Override
	public String isOrNotAdmin(String dwbm, String dlbm) {
		Map<String,Object> map = new HashMap<String,Object>();
		if (!"".equals(dwbm)&&!"".equals(dlbm)) {
			map.put("p_dwbm", dwbm);
			map.put("p_dlbm", dlbm);
			map.put("p_cursor",StringUtils.EMPTY );
			loginmapper.isOrNotAdmin(map);
			List<RYBM> list = (List<RYBM>)map.get("p_cursor");
			if (!CollectionUtils.isEmpty(list)&&list.size()>0) {
				return "admin";
			}else{
				return "normal";
			}
		}
		return null;
	}


	@Override
	public List<String> getGrxxByDwbmAndGh(String dwbm, String gh) {
		Map<String, Object> resultMap = new HashMap<String,Object>();

		if (StringUtils.isNotEmpty(dwbm)&&StringUtils.isNotEmpty(gh)) {
			resultMap.put("p_dwbm", dwbm);
			resultMap.put("p_gh", gh);
			resultMap.put("p_cursor", StringUtils.EMPTY);
			loginmapper.getGrxxByDwbmAndGh(resultMap);
			return (List<String>)resultMap.get("p_cursor");
		}
		return null;
	}

	@Override
	public String getYhm(String dwbm, String gh) {
		Map<String, Object> resultMap = new HashMap<String,Object>();

		resultMap.put("p_dwbm", dwbm);
		resultMap.put("p_gh", gh);
		resultMap.put("p_yhm", "");
		loginmapper.getYhm(resultMap);
		return resultMap.get("p_yhm").toString();
	}

	@Override
	public List<RYBM> getRybmInfoByDwGh(String dwbm, String gh) {
		List<RYBM> rybmList = new ArrayList<>();
		try{
			rybmList = loginmapper.getRybmInfoByDwGh(dwbm,gh);
		}catch (Exception e){
			e.printStackTrace();
		}
		return rybmList;
	}


}
