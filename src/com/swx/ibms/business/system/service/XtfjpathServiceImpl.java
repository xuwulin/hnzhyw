/**
 * 
 */
package com.swx.ibms.business.system.service;

import com.swx.ibms.business.system.bean.XTFJPATH;
import com.swx.ibms.business.system.mapper.XtfjpathMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 系统设置-文件上传路径的修改和查询
 * @author 封泽超
 *@since 2017年4月7日
 */
@Service("XtfjpathService")
public class XtfjpathServiceImpl implements XtfjpathService{

	/**
	 * 运行时路径保存,避免重复从数据库中取
	 */
	private static String path = null;
	
	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(XtfjpathServiceImpl.class);

	/**
	 * 数据库接口
	 */
	@Resource
	private XtfjpathMapper xtfjpathmapper;

	/**
	 * 修改上传路径
	 */
	@Override
	public String insert(XTFJPATH x) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("p_path", x.getPath());
		map.put("p_czr", x.getCzr());
		map.put("Y","0");
		xtfjpathmapper.insert(map);
		String y = map.get("Y");
		// 更新临时路径
		if("1".equals(y)) {
			path = x.getPath();
		}
		return y;
	}

	/**
	 * 查询上传路径
	 */
	@Override
	public List<XTFJPATH> select() {
		Map<String,Object> map = new HashMap<String,Object>();
		List<XTFJPATH> list;
		map.put("p_cur", "");
		xtfjpathmapper.select(map);
		Object obj = map.get("p_cur");
		if(obj != null && !"".equals(obj)){
			list = (List<XTFJPATH>) obj;
			if(!CollectionUtils.isEmpty(list)) {
				return list;
			}
		}
		return null;
	}
	
	/**
	 * 得到系统设置的路径
	 * @return String 
	 */
	public String getPath(){
		if(path == null || "".equals(path)){
			List<XTFJPATH> list = select();
			if(CollectionUtils.isEmpty(list)){
				path = null;
			}else{
				path = ((XTFJPATH)list.get(0)).getPath();
			}
		}
		return path;
	}

}
