package com.swx.ibms.business.common.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swx.ibms.business.archives.service.SfdacjServiceImp;
import com.swx.ibms.business.common.bean.DBYW;
import com.swx.ibms.business.common.mapper.DbywMapper;
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
 * @author 王宇锋
 * @since 2017年3月1日
 */
@Service("dbywService")
@SuppressWarnings("all")
public class DbywServiceImpl implements DbywService{
	/**
	 * 日志对象
	 * */
	private static final Logger LOG = Logger.getLogger(SfdacjServiceImp.class);
	/**
	 * 分页显示行数
	 * 默认为 7 如果修改需要连同前端js一起修改
	 */
	private static final int SHOWROWS=7;
	/**
	 * 数据库接口
	 */
	@Resource
	private DbywMapper dbywMapper;
	
	/**
	 * 重写待办业务方法
	 */
	@Override
	public List<DBYW> getDbywList(String spdw, String spr, String dlrbm, List<String> bmjs) {
		Map<String,Object> map=new HashMap<String, Object>();
		String bmbmSql=getBmbmSql(bmjs, dlrbm);
		String jsbmSql = this.getJsbmSql(bmjs);
		map.put("spdw",spdw);
		map.put("spr",spr);
		map.put("bmbmSql",bmbmSql);
		map.put("jsbmSql", jsbmSql);
		map.put("cursor",null);
		map.put("Y",null);
		try {
			dbywMapper.getDbyw(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY,e);
		}
		if("1".equals(map.get("Y"))){
			return (List<DBYW>)map.get("cursor");
		}else{
			return null;
		}
	}
	
	/**
	 * @param spdw 审批单位编码
	 * @param spr 审批人工号
	 * @param dlrbm 人事部部门编码
	 * @param page 当前页数
	 * @param bmjs 部门角色
	 * @return 返回DBYW列表和查询行数
	 * 根据审批单位编号和审批人编号返回DBYW列表和查询行数
	 */
	@Override
	public Map<String, Object> getAllDbywFy(String spdw, String spr, String dlrbm,
			int page,List<String> bmjs,String clnr,String splx,String sfdb) {
		//用于返回参数
				Map<String,Object> returnMap=new HashMap<>();
				Map<String,Object> map=new HashMap<>();
				String bmbmSql=getBmbmSql(bmjs, dlrbm);
				String jsbmSql = this.getJsbmSql(bmjs);
				String tjsql = cxtj(clnr,splx);
				String dbtj = sfdb("1".equals(sfdb));
				map.put("spdw", spdw);
				map.put("spr", spr);
				map.put("count",null);
				map.put("Y2",null);
				map.put("bmbmSql", bmbmSql);
				map.put("jsbmSql", jsbmSql);
				map.put("p_tj", tjsql);
				map.put("p_dbtj", dbtj);
				if("1".equals(sfdb)){
					sfdb = "0";
				}else{
					sfdb = "1";
				}
				map.put("p_fkyd", sfdb);
				
				//查询所有DBYW行数
				try {
					dbywMapper.getDbywRows(map);
					returnMap.put("count",map.get("count"));
				} catch (Exception e) {
					LOG.error(StringUtils.EMPTY,e);
				}
				int begin=(page-1)*SHOWROWS+1;
				int end=page*SHOWROWS;
				//分页查询所有DBYM列表
				map.put("cursor",null);
				map.put("Y1",null);
				map.put("begin", begin);
				map.put("end",end);
				try {
					dbywMapper.getDbywAll(map);
				} catch (Exception e) {
					LOG.error(StringUtils.EMPTY,e);
				}
				//Y1和Y2都为1代表都查询数据成功
				if("1".equals(map.get("Y1"))&&"1".equals(map.get("Y2"))){
					returnMap.put("status",1);//代表查询成功,返回数据给前台判断
					returnMap.put("list",(List<DBYW>)map.get("cursor"));
					return returnMap;
				}
				returnMap.put("status",0);
				return returnMap;
	}
	/**
	 * 生成部门编码sql语句
	 * @param bmjs 部门角色
	 * @param dlrbm 人事部部门编码
	 * @return 部门sql语句
	 */
	public String getBmbmSql(List<String> bmjs,String dlrbm){
		String bmbmSql = " and ( ";
		if(CollectionUtils.isNotEmpty(bmjs)){
			for(int i = 0;i<bmjs.size();i++){
				if(i==0){
					bmbmSql = bmbmSql + " s.spbm like '%"+bmjs.get(i).split(",")[1]+"%' ";
				}else{
					bmbmSql = bmbmSql + " or s.spbm like '%"+bmjs.get(i).split(",")[1]+"%' ";
				}
			}
		}
		bmbmSql=bmbmSql+" ) ";
		return bmbmSql;
	}
	
	/**
	 * 生成角色编码sql语句
	 * @param bmjs 部门角色
	 * @return 角色编码SQL
	 */
	public String getJsbmSql(List<String> bmjs){
		String jsbmSql = " and ( ";
		if(CollectionUtils.isNotEmpty(bmjs)){
			for(int i = 0;i<bmjs.size();i++){
				if(i==0){
					jsbmSql = jsbmSql + " s.cljsxz like '%"+bmjs.get(i).split(",")[2]+"%' ";
				}else{
					jsbmSql = jsbmSql + " or s.cljsxz like '%"+bmjs.get(i).split(",")[2]+"%' ";
				}
			}
		}
		jsbmSql=jsbmSql+" ) ";
		return jsbmSql;
	}
	
	/**
	 * 拼接条件sql语句
	 * @param clnr 名称 模糊搜索
	 * @param splx 审批类型 模糊搜索
	 * @return String 拼接的sql语句
	 */
	public String  cxtj(String clnr,String splx){
		String sql = "where clnr is not null ";
		//名称
		if(!StringUtils.isBlank(clnr)){
			sql += " and clnr like '%" + clnr + "%'";
		}
		
		if(!StringUtils.isBlank(splx)){
			sql +=" and lx ='" + splx + "'";
		}
		sql += " order by spsj desc";
		return sql;
	}
	
	/**
	 * 生成查询待办或已办的条件
	 * @param b 
	 * @return String 
	 */
	public String sfdb(boolean b){
		if(b){
			return  "('1')";
		}else{
			return  "('2','3','4','5')";
		}
	}

	@Override
	public Map<String, Object> getAllSpInfoByDwbmGhOrBm(String dwbm, String bmbm, String gh, String spzt, String clnr,Integer page,Integer pageSize) {
		Map<String, Object> res = new HashMap<String, Object>();
		List<DBYW> dbywList = new ArrayList<DBYW>();
		Page<DBYW> pageCommon = PageHelper.startPage(page, pageSize);
		try {
			dbywList = dbywMapper.getAllSpInfoByDwbmGhOrBm(spzt, dwbm, bmbm, gh, clnr);
		} catch (Exception e) {
			LOG.error("查询待办失败！",e);
			throw e;
		}
		res.put("total", pageCommon.getTotal());
		res.put("list", dbywList);
		return res;
	}
	
	
}
