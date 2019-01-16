package com.swx.ibms.business.common.service;

import com.swx.ibms.business.common.mapper.LogMapper;
import com.swx.ibms.business.common.utils.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;



/**
 * 日志服务实现类
 * @author 李治鑫
 * @since 2017-5-3
 */
@Service("logService")
public class LogServiceImpl implements LogService {
	
	/**
	 * 日志数据访问接口
	 */
	@Resource
	private LogMapper logmapper;
	
	@Override
	public void info(String dwbm, String gh,String mc, String rzlx, String msg) {
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("p_dwbm", dwbm);
		map.put("p_gh", gh);
		map.put("p_mc", mc);
		map.put("p_rzlx", rzlx);
		map.put("p_msg", msg);
		map.put("p_errmsg",StringUtils.EMPTY);
		try {
			logmapper.info(map);
		} catch (Exception e) {
			this.error(dwbm, gh,StringUtils.EMPTY, Constant.RZLX_CWRZ, e.toString());
		}
		
		String msG = (String)map.get("p_errmsg");
		if(!StringUtils.isEmpty(msG)){
			this.error(dwbm, gh,StringUtils.EMPTY, Constant.RZLX_CWRZ, "日志记录失败");
		}
	}

	@Override
	public void error(String dwbm, String gh,String mc,String rzlx, String msg) {
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("p_dwbm", dwbm);
		map.put("p_gh", gh);
		map.put("p_mc",mc);
		map.put("p_rzlx", rzlx);
		map.put("p_msg", msg);
		map.put("p_errmsg",StringUtils.EMPTY);
		try {
			logmapper.error(map);
		} catch (Exception e) {
			this.error(dwbm, gh,StringUtils.EMPTY, Constant.RZLX_CWRZ, e.toString());
		}
		String msG = (String)map.get("p_errmsg");
		if(!StringUtils.isEmpty(msG)){
			this.error(dwbm, gh,StringUtils.EMPTY, Constant.RZLX_CWRZ, "日志记录失败");
		}
	}

	@Override
	public void warn(String dwbm, String gh,String mc, String rzlx, String msg) {
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("p_dwbm", dwbm);
		map.put("p_gh", gh);
		map.put("p_mc", mc);
		map.put("p_rzlx", rzlx);
		map.put("p_msg", msg);
		map.put("p_errmsg",StringUtils.EMPTY);
		try {
			logmapper.warn(map);
		} catch (Exception e) {
			this.error(dwbm, gh,StringUtils.EMPTY, Constant.RZLX_CWRZ, e.toString());
		}
		String msG = (String)map.get("p_errmsg");
		if(!StringUtils.isEmpty(msG)){
			this.error(dwbm, gh,StringUtils.EMPTY, Constant.RZLX_CWRZ, "日志记录失败");
		}

	}

	@Override
	public Map<String,Object> getLog(int page, int pageSize, String rzlx, String rzzl) {
		
		int start = (page-1)*pageSize;//开始行数
		int end = page * pageSize;//结束行数
		
		Map<String,Object> map = new HashMap<String,Object> ();
		
		map.put("p_start", start);
		map.put("p_end", end);
		map.put("p_rzlx", rzlx);
		map.put("p_rzzl", rzzl);
		map.put("p_total", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);
		map.put("p_errmsg", StringUtils.EMPTY);
		
		if(!StringUtils.isEmpty(rzlx)&&!StringUtils.isEmpty(rzzl)){
			//日志种类和日志类型都不为空
			try {
				logmapper.getLog(map);
			} catch (Exception e) {
				e.printStackTrace();
				this.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
						Constant.CURRENT_USER.get().getMc(),
						Constant.RZLX_CWRZ, e.toString());
			}
			
		}else if(!StringUtils.isEmpty(rzlx)&&StringUtils.isEmpty(rzzl)){
			//只有日志类型
			try {
				logmapper.getLog2(map);
			} catch (Exception e) {
				e.printStackTrace();
				this.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
						Constant.CURRENT_USER.get().getMc(),
						Constant.RZLX_CWRZ, e.toString());
			}
		}else if(StringUtils.isEmpty(rzlx)&&!StringUtils.isEmpty(rzzl)){
			//只有日志种类
			try {
				logmapper.getLog3(map);
			} catch (Exception e) {
				e.printStackTrace();
				this.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
						Constant.CURRENT_USER.get().getMc(),
						Constant.RZLX_CWRZ, e.toString());
			}
		}else if(StringUtils.isEmpty(rzlx)&&StringUtils.isEmpty(rzzl)){
			//都为空
			try {
				logmapper.getLog1(map);
			} catch (Exception e) {
				e.printStackTrace();
				this.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
						Constant.CURRENT_USER.get().getMc(),
						Constant.RZLX_CWRZ, e.toString());
			}
		}
		
		return map;
	}

	@Override
	public int deleteAllLog() {
		int num = 0;
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_rows", StringUtils.EMPTY);//受影响行数
		map.put("p_errmsg", StringUtils.EMPTY);//错误信息
		
		try {
			logmapper.deleteAllLog(map);
		} catch (Exception e) {
			e.printStackTrace();
			this.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, e.toString());
		}
		
		String rows =(String)map.get("p_rows");
		String errmsg = (String)map.get("p_errmsg");
		
		if(!StringUtils.isEmpty(rows)&&StringUtils.isEmpty(errmsg)){
			num = 1;
			this.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ,
					"删除所有日志信息");
		}else{
			if(!StringUtils.isEmpty(errmsg)){
				this.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
						Constant.CURRENT_USER.get().getMc(),
						Constant.RZLX_CWRZ, errmsg);
			}
			num = 0;
		}
		
		
		return num;
	}

	@Override
	public int deleteLog(String[] idArray) {
		int num = 0;
		
		for(int i =0;i<idArray.length;i++){
			num = num + this.deleteLogById(idArray[i]);
		}
		
		if(num>0){
			this.info(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ,
					"删除"+idArray.length+"条日志信息");
			return 1;
		}else{
			return 0;
			}
	}
	
	/**
	 * 通过日志编号删除日志信息
	 * @param id 日志编号
	 * @return 1：成功 0：失败
	 */
	public int deleteLogById(String id){
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("p_id", id);
		map.put("p_errmsg", StringUtils.EMPTY);
		
		try {
			logmapper.deleteLogById(map);
		} catch (Exception e) {
			e.printStackTrace();
			this.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, e.toString());
		}
		
		String errmsg = (String)map.get("p_errmsg");
		
		if(!StringUtils.isEmpty(errmsg)){
			this.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, errmsg);
			return 0;
		}else{
			return 1;
		}
		
	}
	/**
	 * 拼接查询sql
	 * @param rzlx 日志类型
	 * @param rzzl 日志种类
	 * @param dwmc 单位名称
	 * @param xm 姓名
	 * @return String 
	 */
	private String searchsql(String rzlx,String rzzl,String dwmc,String xm,String rznr,String dwbm){
		String sql = "";
		//a.rzlx = :rzlx and a.rzzl = :rzzl
		//b.dwmc,a.mc
		if(!StringUtils.isBlank(rzlx)){
			sql += " and a.rzlx = " + rzlx;
		}
		if(!StringUtils.isBlank(rzzl)){
			sql += " and a.rzzl = " + rzzl;
		}
		if(!StringUtils.isBlank(dwmc)){
			sql += " and b.dwmc like '%" + dwmc +"%'";
		}
		if(!StringUtils.isBlank(xm)){
			sql += " and a.mc like '%" + xm + "%'";
		}
		if(!StringUtils.isBlank(rznr)){
			sql += " and a.rznr like '%" + rznr + "%'";
		}
		if(!StringUtils.isBlank(dwbm)){
			sql += " and a.dwbm like '" + dwbm + "%'";
		}
		
		return sql;
	}
	/**
	 * 获取日志信息
	 * @param page 当前页码
	 * @param pageSize 当前页面显示行数
	 * @param rzlx 日志类型
	 * @param rzzl 日志种类
	 * @param dwmc 单位名称
	 * @param xm   操作人姓名
	 * @param rznr 日志内容
	 * @return 日志信息列表 
	 */
	@Override
	public Map<String,Object> searchLog(int page, int pageSize, String rzlx, String rzzl,String dwmc,String xm,String rznr,String dwbm) {
		
		int start = (page-1)*pageSize;//开始行数
		int end = page * pageSize;//结束行数
		// 查询条件
		String tjslq = searchsql(rzlx,rzzl,dwmc,xm,rznr,dwbm);
		
		Map<String,Object> map = new HashMap<String,Object> ();
		
		map.put("p_start", start);
		map.put("p_end", end);
		map.put("p_tjsql", tjslq);
		map.put("p_total", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);
		map.put("p_errmsg", StringUtils.EMPTY);
		
		logmapper.search(map);
		
		return map;
	}
	
	
	
	
	
}
