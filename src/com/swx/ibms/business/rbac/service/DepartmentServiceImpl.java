package com.swx.ibms.business.rbac.service;

import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.*;
import com.swx.ibms.business.rbac.mapper.DepartmentMapper;
import com.swx.ibms.business.system.mapper.GdjsMapper;
import com.swx.ibms.common.utils.PageCommon;
import com.swx.ibms.common.utils.StrUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author east
 * @date:2017年4月19日
 * @version:1.0
 * @description:部门Service实现类
 *
 */
@SuppressWarnings("all")
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
	
	/**
	 *  部门mapper
	 */
	@Resource
	private DepartmentMapper departMapper;
	
	/**
	 * 
	 */
	@Resource
	private GdjsMapper gdjsMapper;
	
	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;
	
	@Override
	public List<BMBM> selectZhywList(String dwbm, String sfsc) {
		List<BMBM> list = null;
		Map<String,Object> map = new HashMap<String,Object>();
		if (!"".equals(dwbm) && !"".equals(sfsc)) {
			map.put("p_dwbm", dwbm);
			map.put("p_sfsc", sfsc);// 获取传入的是否删除的标示并赋值给变量p_sfsc
			map.put("p_cursor", StringUtils.EMPTY);
			departMapper.selectZhywList(map);
			list = (List<BMBM>) map.get("p_cursor");
		}
		return list;
	}

	@Override
	public String deleteData(BMBM bmbm) {
//		Map<String,Object> map = new HashMap<String,Object>();
//		if (null != bmbm) {
//			if (!"".equals(bmbm.getBmbm())&&!"".equals(bmbm.getDwbm())&&
//					!"".equals(bmbm.getSfsc())) {
//				map.put("p_dwbm", bmbm.getDwbm());
//				map.put("p_bmbm", bmbm.getBmbm());
//				map.put("p_sfsc", bmbm.getSfsc());
//				map.put("p_errmsg",StringUtils.EMPTY);
//				departMapper.deleteData(map);
//				if ("1".equals(map.get("p_errmsg"))) {
//					return "操作成功！";
//				}else{
//					return "操作失败！";
//				}
//			}
//		}
//		return null;
		
		try {
			//存在任职于其他部门的人员不能删除
			List<RYJSFP> ryJsfpxxList=departMapper.getJsfpxx(bmbm.getDwbm(),bmbm.getBmbm());
			for(RYJSFP e : ryJsfpxxList){
				List<RYJSFP> qtList=departMapper.rzyQtBm(bmbm.getDwbm(),bmbm.getBmbm(),e.getGh());
				if(CollectionUtils.isEmpty(qtList)){
					departMapper.deleteRy(bmbm.getDwbm(),e.getGh());  
				}
			}
			
			departMapper.deletebm(bmbm.getDwbm(),bmbm.getBmbm());   //删除部门
			departMapper.deletebmjs(bmbm.getDwbm(),bmbm.getBmbm());  //删除原有的角色信息
			departMapper.deleteRyjsfp(bmbm.getDwbm(),bmbm.getBmbm()); //删除某一部门下的人员角色分配信息
		} catch (Exception e) {
			//日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(), 
					Constant.CURRENT_USER.get().getMc(), 
					Constant.RZLX_CWRZ, e.toString());
		}
		return "操作成功！";
		
	}

	@Override
	public String updateData(BMBM bmbm) {
		Map<String,Object> map = new HashMap<String,Object>();
		if (null!=bmbm) {
			if (!"".equals(bmbm.getBmbm())&&!"".equals(bmbm.getDwbm())&&
					!"".equals(bmbm.getSfsc())) {
				map.put("p_dwbm", bmbm.getDwbm());
				map.put("p_bmbm", bmbm.getBmbm());
				map.put("p_bmxh", bmbm.getBmxh());
				map.put("p_fbmbm", bmbm.getFbmbm());
				map.put("p_bmmc", StrUtil.strTransform(bmbm.getBmmc()));
				map.put("p_bmjc", StrUtil.strTransform(bmbm.getBmjc()));
				map.put("p_bmwhjc", StrUtil.strTransform(bmbm.getBmwhjc()));
				map.put("p_bmahjc", StrUtil.strTransform(bmbm.getBmahjc()));
				map.put("p_sflsjg", bmbm.getSflsjg());
				map.put("p_sfcbbm", bmbm.getSfcbbm());
				map.put("p_bz", StrUtil.strTransform(bmbm.getBz()));
				map.put("p_bmys", bmbm.getBmys());
				map.put("p_errmsg",StringUtils.EMPTY);
				//调用mapper执行修改
				departMapper.updateData(map);
			}
			if ("1".equals(map.get("p_errmsg"))) {
				return "操作成功！";
			}else{
				return "操作失败！";
			}
			
		}
		return null;
	}

	@Override
	public String insertData(BMBM bmbm) {
		Map<String,Object> map = new HashMap<String,Object>();
		if (null!=bmbm) {
			if (!"".equals(bmbm.getDwbm())) {
				map.put("p_dwbm", bmbm.getDwbm());
				map.put("p_bmxh", bmbm.getBmxh());
				map.put("p_fbmbm", bmbm.getFbmbm());
				map.put("p_bmmc", StrUtil.strTransform(bmbm.getBmmc()));
				map.put("p_bmjc", StrUtil.strTransform(bmbm.getBmjc()));
				map.put("p_bmwhjc", StrUtil.strTransform(bmbm.getBmwhjc()));
				map.put("p_bmahjc", StrUtil.strTransform(bmbm.getBmahjc()));
				map.put("p_sflsjg", bmbm.getSflsjg());
				map.put("p_sfcbbm", bmbm.getSfcbbm());
				map.put("p_bz", StrUtil.strTransform(bmbm.getBz()));
				map.put("p_bmys", bmbm.getBmys());
				map.put("p_sfsc", "N");
				map.put("p_errmsg",StringUtils.EMPTY);
				departMapper.insertData(map);
			}
		}
		if ("1".equals(map.get("p_errmsg"))) {
			return "操作成功！";
		}else{
			return "操作失败！";
		}
	}

	@Override
	public PageCommon<BMBM> selectPageList(String dwbm, String sfsc,int nowPage,int pageSize) {
		Map<String,Object> map = new HashMap<String,Object>();
		PageCommon<BMBM> pageCommon = new PageCommon();
			int page = (nowPage-1)*pageSize;
			pageCommon.setPageSize(page+pageSize);//每页显示数
			page++;
			pageCommon.setNowPage(page);//当前页
		if (!"".equals(dwbm)&&!"".equals(sfsc)) {
			map.put("p_dwbm", dwbm);
			map.put("p_sfsc", sfsc);
			map.put("p_start", pageCommon.getNowPage());
			map.put("p_end", pageCommon.getPageSize());
			map.put("p_total", StringUtils.EMPTY);
			map.put("p_cursor", StringUtils.EMPTY);
			departMapper.selectPageList(map);
			List<BMBM> list = (List<BMBM>)map.get("p_cursor");
			pageCommon.setList(list);
			pageCommon.setTotalRecords(Integer.parseInt(map.get("p_total").toString()));
		}
		
		return pageCommon;
	}

	@Override
	public DWBM selectDwbm(String dwbm) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		if (dwbm!=null) {
			if (StringUtils.isNotEmpty(dwbm)) {
				map.put("p_dwbm", dwbm);
				map.put("p_sfsc", "N");
				map.put("p_cursor", null);
				
				departMapper.selectDwbm(map);
				List<DWBM> dwbmLsit = (List<DWBM>) map.get("p_cursor");
				
				return dwbmLsit.get(0);
			}
		}
		return null;
	}

	@Override
	public void addbmjs(String[] array, String dwbm, String bmmc) {
		String bmbm = departMapper.selectbmbm(dwbm,bmmc);
		for(String jsbm :array){
			JSBM gdjs = gdjsMapper.getgdjs(jsbm);
			gdjs.setDwbm(dwbm);
			gdjs.setBmbm(bmbm);
			gdjs.setJsbm(jsbm);
			departMapper.addbmjs(gdjs);
		}
	}

	@Override
	public List<JSBM> getJs(String dwbm, String bmbm) {
		return departMapper.getJs(dwbm,bmbm);
	}

	@Override
	public void updatebmjs(String dwbm, String bmbm, String ddt) {
		String[] array = ddt.split(",");		//现在的角色信息
		departMapper.deletebmjs(dwbm,bmbm);  //删除原有的角色信息 
		if("".equals(array[0])){
			return;
		}
		for(String jsbm :array){	//新增现有角色信息
			JSBM gdjs = gdjsMapper.getgdjs(jsbm);
			gdjs.setDwbm(dwbm);
			gdjs.setBmbm(bmbm);
			gdjs.setJsbm(jsbm);
			departMapper.addbmjs(gdjs);
		}
	}

	@Override
	public List<Department> selectAllBmInfo(String dwbm, String sfsc) {
		List<Department> bmList = new ArrayList<Department>();
		try {
			Map<String,Object> params = new HashMap<String,Object>(Constant.NUM_6);
			params.put("p_dwbm", dwbm);
			params.put("p_sfsc", sfsc);
			params.put("p_cursor", StringUtils.EMPTY);
			
			departMapper.selectAllBmInfo(params);
			
			bmList = (List<Department>)params.get("p_cursor");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bmList;
	}

	@Override
	public List<Department> getBmInfoByDwBm(String dwbm, String bmbm) {
		List<Department> bmList = new ArrayList<Department>();
		try {
			Map<String,Object> params = new HashMap<String,Object>(Constant.NUM_6);
			params.put("p_dwbm", dwbm);
			params.put("p_bmbm", bmbm);
			params.put("p_cursor", StringUtils.EMPTY);
			
			departMapper.getBmInfoByDwBm(params);
			
			bmList = (List<Department>)params.get("p_cursor");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bmList;
	}

	@Override
	public List<Map<String, Object>> getRyBmByDwGh(String dwbm, String gh) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> params = new HashMap<String,Object>(Constant.NUM_6);
		params.put("p_dwbm", dwbm);
		params.put("p_gh", gh);
		params.put("p_curosr", StringUtils.EMPTY);
		try {
			departMapper.getRyBmByDwGh(params);
			list = (List<Map<String, Object>>)params.get("p_cursor");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getRyJsByDwBm(String dwbm, String bmbm) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> params = new HashMap<String,Object>(Constant.NUM_6);
		params.put("p_dwbm", dwbm);
		params.put("p_bmbm", bmbm);
		params.put("p_curosr", StringUtils.EMPTY);
		try {
			departMapper.getRyJsByDwBm(params);
			list = (List<Map<String, Object>>)params.get("p_cursor");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getRyByDwBmJs(String dwbm, String bmbm, String jsbm) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> params = new HashMap<String,Object>(Constant.NUM_9);
		params.put("p_dwbm", dwbm);
		params.put("p_bmbm", bmbm);
		params.put("p_jsbm", jsbm);
		params.put("p_curosr", StringUtils.EMPTY);
		try {
			departMapper.getRyByDwBmJs(params);
			list = (List<Map<String, Object>>)params.get("p_cursor");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
