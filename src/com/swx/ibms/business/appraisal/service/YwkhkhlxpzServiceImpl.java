package com.swx.ibms.business.appraisal.service;


import com.swx.ibms.business.appraisal.bean.YwkhKhlxPz;
import com.swx.ibms.business.appraisal.mapper.YwkhkhlxpzMapper;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Service("ywkhkhlxService")
public class YwkhkhlxpzServiceImpl implements YwkhkhlxpzService{
	   /**
     * 业务工作类型考核
     */
    @Resource
    private YwkhkhlxpzMapper ywkhkhlxpzmapper;

	@Override
	public PageCommon<Object> getPageListKhlx(String ywlx, Integer page, Integer pageSize) throws Exception {
		 PageCommon<Object> pageCommon = new PageCommon<Object>();
	        Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_6);
	        int nowPage = (0==page? Constant.NUM_1:page);
	        int pageStart = (nowPage - Constant.NUM_1) * pageSize;
	        int pageEnd = pageStart+pageSize;
	        pageStart++;	        
	        map.put("p_ywlx", ywlx);
	        map.put("p_start", pageStart);
	        map.put("p_end", pageEnd);
	        map.put("p_total", StringUtils.EMPTY);
	        map.put("p_cursor", StringUtils.EMPTY);
	        ywkhkhlxpzmapper.getPageListKhlx(map);
	        List<Object> list = (List<Object>) map.get("p_cursor");
	        pageCommon.setList(list);
	        pageCommon.setTotalRecords(Integer.parseInt(map.get("p_total").toString()));
	        return pageCommon;
	}

	@Override
	public List<YwkhKhlxPz> getywkhkhlx(Map<String, Object> map){
		map.put("P_CURSOR",null);
		map.put("P_ERRMSG","");
		ywkhkhlxpzmapper.getywkhkhlx(map);
			List<YwkhKhlxPz> lx= new ArrayList<YwkhKhlxPz>();
			if(null != map.get("P_CURSOR")){
				lx = (List<YwkhKhlxPz>) map.get("P_CURSOR");
			}
			return lx;
	}

	@Override
	public List<String> addywkhkhlx(Map<String, Object> map) {
	     ywkhkhlxpzmapper.addywkhkhlx(map);
		 List<String> string=new ArrayList<String>();
	 	if(null != map.get("P_CURSOR")){
	 		string = (List<String>) map.get("P_CURSOR");
		}
		return string;
	}

	@Override
	public boolean deleteywkhkhlx(Map<String, Object> map) {
		ywkhkhlxpzmapper.deleteywkhkhlx(map);
			if(null != map.get("P_ERRMSG")){
				return false;
			}
			return true;	
	}

	@Override
	public boolean updateywkhkhlx(Map<String, Object> map) {
		  ywkhkhlxpzmapper.updateywkhkhlx(map);
		 	if(null != map.get("P_ERRMSG")){
		 		return false;
			}
			return true;
	}

	@Override
	public String  ywkhkhlxbm(Map<String, Object> map) {
		 ywkhkhlxpzmapper.ywkhkhlxbm(map);
		 String  string="";
		    if(null!= map.get("P_LXBM")){
		    	string= (String)map.get("P_LXBM");
					}
		return string;
	}

	@Override
	public List getZhyw(Map<String, Object> map) {
		try {
			ywkhkhlxpzmapper.getZhyw(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (List) map.get("p_cursor");
	}

	@Override
	public YwkhKhlxPz getKhlxInfoByKhmc(String khlxmc) {
		YwkhKhlxPz lxpz = new YwkhKhlxPz();
		try {
			lxpz = ywkhkhlxpzmapper.getKhlxInfoByKhmc(khlxmc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lxpz;
	}
}
