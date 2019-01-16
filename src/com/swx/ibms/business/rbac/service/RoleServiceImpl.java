package com.swx.ibms.business.rbac.service;

import com.swx.ibms.business.rbac.bean.JSBM;
import com.swx.ibms.business.rbac.mapper.RoleMapper;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * @author east
 * @date:2017年4月14日
 * @version:1.0
 * @description:角色Service实现类
 *
 */
@SuppressWarnings("all")
@Service("roleService")
public class RoleServiceImpl implements RoleService {

	/**
	 * 角色mapper
	 */
	@Resource
	private RoleMapper roleMapper;
	
	
	@Override
	public PageCommon<JSBM> selectPageList(String dwbm, String sfsc, int nowPage, int pageSize) {
		Map<String,Object> map = new HashMap<String,Object>();
		PageCommon<JSBM> pageCommon = new PageCommon();
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
			roleMapper.selectPageList(map);
			List<JSBM> list = (List<JSBM>)map.get("p_cursor");
			pageCommon.setList(list);
			pageCommon.setTotalRecords(Integer.parseInt(map.get("p_total").toString()));
		}
		
		return pageCommon;
	}


	@Override
	public String insertData(JSBM jsbm) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(null!=jsbm){
			map.put("p_jsbm", jsbm.getJsbm());
			map.put("p_dwbm", jsbm.getDwbm());
			map.put("p_bmbm", jsbm.getBmbm());
			map.put("p_jsmc", jsbm.getJsmc());
			map.put("p_jsxh", jsbm.getJsxh());
			map.put("p_spjsbm", jsbm.getSpjsbm());
			map.put("p_description", jsbm.getDescription());
			map.put("p_sfsc", jsbm.getSfsc());
			map.put("p_errmsg",StringUtils.EMPTY);
		}
		roleMapper.insertData(map);
		if ("1".equals(map.get("p_errmsg"))) {
			return "操作成功!";
		}else{
			return "操作失败!";
		}
	}


	@Override
	public String updateData(JSBM jsbm) {
		Map<String,Object> map = new HashMap<String,Object>();
		if (null!=jsbm) {
			if (!"".equals(jsbm.getBmbm())&&!"".equals(jsbm.getDwbm())) {
				map.put("p_jsbm", jsbm.getJsbm());
				map.put("p_dwbm", jsbm.getDwbm());
				map.put("p_bmbm", jsbm.getBmbm());
				map.put("p_jsmc", jsbm.getJsmc());
				map.put("p_jsxh", jsbm.getJsxh());
				map.put("p_spjsbm", jsbm.getSpjsbm());
				map.put("p_description", jsbm.getDescription());
				map.put("p_errmsg",StringUtils.EMPTY);
				roleMapper.updateData(map);
				if ("1".equals(map.get("p_errmsg"))) {
					return "操作成功!";
				}else{
					return "操作失败!";
				}
			}
		}
		return null;
	}


	@Override
	public String deleteData(JSBM jsbm) {
		Map<String,Object> map = new HashMap<String,Object>();
		if (null!=jsbm) {
			if (!"".equals(jsbm.getBmbm())&&!"".equals(jsbm.getDwbm())
					&&!"".equals(jsbm.getJsbm())) {
				map.put("p_jsbm", jsbm.getJsbm());
				map.put("p_dwbm", jsbm.getDwbm());
				map.put("p_bmbm", jsbm.getBmbm());
				map.put("p_sfsc", jsbm.getSfsc());
				map.put("p_errmsg",StringUtils.EMPTY);
				roleMapper.deleteData(map);
			}
			if ("1".equals(map.get("p_errmsg"))) {
				return "操作成功!";
			}else{
				return "操作失败!";
			}
		}
		return null;
	}


	@Override
	public List<JSBM> selectZhywRoleList(String dwbm, String bmbm,String sfsc) {
		List<JSBM> list =null;
		Map<String,Object> map = new HashMap<String,Object>();
		if (dwbm!=null&&!"".equals(dwbm)&&!"".equals(bmbm)&&!"".equals(sfsc)) {
			map.put("p_dwbm", dwbm);
			map.put("p_bmbm", bmbm);
			map.put("p_sfsc", sfsc);
			map.put("p_cursor",StringUtils.EMPTY);
			roleMapper.selectZhywRoleList(map); 
			list = (List<JSBM>) map.get("p_cursor");
		}
		return list;
	}

	
}
