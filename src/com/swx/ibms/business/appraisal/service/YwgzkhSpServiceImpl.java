package com.swx.ibms.business.appraisal.service;

import com.swx.ibms.business.appraisal.bean.Ywgzkh;
import com.swx.ibms.business.appraisal.bean.Ywgzlxkh;
import com.swx.ibms.business.appraisal.mapper.YwgzkhSpMapper;
import com.swx.ibms.business.appraisal.mapper.YwlxkhMapper;
import com.swx.ibms.business.common.bean.PersonTree;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.etl.bean.XtQuartzPz;
import com.swx.ibms.business.etl.service.XtQuartzPzService;
import com.swx.ibms.business.etl.utils.QuartzManager;
import com.swx.ibms.business.rbac.bean.DWBM;
import com.swx.ibms.business.system.service.XTGLServiceImpl;
import com.swx.ibms.common.utils.DateUtil;
import com.swx.ibms.common.utils.Identities;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 
 * YwgzkhSpServiceImpl.java  业务工作考核审批service实现类 
 * @author east
 * @date<p>2017年6月5日</p>
 * @version 1.0
 * @description<p></p>
 */
@Service("ywgzkhSpService")
@SuppressWarnings("all")
public class YwgzkhSpServiceImpl implements YwgzkhSpService {
	
	/**
	 * 日志对象
	 * */
	private static final Logger LOG = Logger.getLogger(XTGLServiceImpl.class);
	
	/**
	 * 业务工作考核Mapper
	 */
	@Resource
	private YwgzkhSpMapper ywgzkhSpMapper;
	
	@Resource
	private XtQuartzPzService xtQuartzPzService;
	
	@Resource
	private YwlxkhMapper ywlxkhMapper;
	
	@Override
	public String insertNdYwgzkh(Ywgzkh ywgzkh) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>(Constant.NUM_2);
		if(!Objects.isNull(ywgzkh)){
			map.put("p_year", ywgzkh.getYear());
			map.put("p_dwbm", ywgzkh.getDwbm());
			map.put("p_bmbm", ywgzkh.getBmbm());
			map.put("p_gh", ywgzkh.getGh());
			map.put("p_sjqx", ywgzkh.getSjqx());
			map.put("p_createDate", DateUtil.stringtoDate(ywgzkh.getCreateDate(),"yyyy-MM-dd"));
			map.put("p_endDate", DateUtil.stringtoDate(ywgzkh.getEndDate(),"yyyy-MM-dd"));
			map.put("p_errmsg", StringUtils.EMPTY);
			try {
				ywgzkhSpMapper.insertNdYwgzkh(map);
			} catch (Exception e) {
				LOG.error("添加业务考核失败！", e);
				throw e;
			}
			String message = map.get("p_errmsg").toString();
			if(StringUtils.isNotEmpty(message)){
				if ("1".equals(message)) {
					//添加同步任务信息
					XtQuartzPz xtQuartzPz = new XtQuartzPz();
					xtQuartzPz.setId(Identities.get32LenUUID());
					xtQuartzPz.setCjr(ywgzkh.getDwbm()+"_"+ywgzkh.getGh());
					xtQuartzPz.setCjsj(DateUtil.getDate4Str(DateUtil.getCurrDate("YYYY-MM-dd HH:mm:ss")));
					//将页面传来的日期格式字符串"2018-06-13 09:27:25"处理为同步任务能接受的表达式，年份不需要，暂定每年的创建日期进行同步。
					//秒 分 时 日 月 周/年;
					String cronStr = DateUtil.getQuartzCronByDateString(DateUtil.getCurrDate("YYYY-MM-dd")+" 23:59:59");
					xtQuartzPz.setCron(cronStr);
					xtQuartzPz.setCjrMc(StringUtils.EMPTY);
					xtQuartzPz.setClazz("com.swx.zhyw.task.QuartzJob");
					xtQuartzPz.setJobName(QuartzManager.JOB_NAME);
					xtQuartzPz.setJobGroupName(QuartzManager.JOB_GROUP_NAME);
					xtQuartzPz.setTriggerName(QuartzManager.TRIGGER_NAME);
					xtQuartzPz.setTriggerGroupName(QuartzManager.TRIGGER_GROUP_NAME);
					xtQuartzPz.setState(StringUtils.EMPTY);
					xtQuartzPzService.addXtQuartzPz(xtQuartzPz);
					
					return "操作成功!";
				}else if("2".equals(message)){
					return "该时间段业务考核已存在";
				}else{
					return "操作失败!";
				}
			}
		}
		return StringUtils.EMPTY;
	}


	@Override
	public String addYwgzkhSpr(Map<String, Object> map) {
		if (!Objects.isNull(map)) {
			map.put("p_errmsg", StringUtils.EMPTY);
			ywgzkhSpMapper.addYwgzkhSpr(map);
			
			String message = (String)map.get("p_errmsg");
			if (StringUtils.isNotEmpty(message)) {
				if ("1".equals(message)) {
					return "操作成功！";
				}else{
					return "操作失败！";
				}
			}
		}
		return null;
	}


	@Override
	public String updateYwgzkhSpZt(Map<String,Object> map) {
		if (!Objects.isNull(map)) {
			map.put("p_errmsg", StringUtils.EMPTY);
			ywgzkhSpMapper.updateYwgzkhSpZt(map);
			
			String message = (String)map.get("p_errmsg");
			if (StringUtils.isNotEmpty(message)) {
				return message;
			}
		}
		
		return null;
	}


	@Override
	public String updateSplc(Map<String, Object> map) {
		
		if (!Objects.isNull(map)) {
			map.put("p_zt", StringUtils.EMPTY);
			map.put("p_errmsg", StringUtils.EMPTY);
			
			ywgzkhSpMapper.updateSplc(map);
			
			String message = (String)map.get("p_errmsg");
			if (StringUtils.isNotEmpty(message)) {
				if ("1".equals(message)) {
					return "操作成功！";
				}else{
					return "操作失败！";
				}
			}
		}
		return null;
	}
	@Override
	public List<PersonTree> getBmkhry(String khlx, String dwbm, String bmbm, String gh) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_khlx", khlx);
		map.put("p_dwbm", dwbm);
		map.put("p_bmbm", bmbm);
		map.put("p_gh", gh);
		map.put("p_cursor", null);

		try {
			ywgzkhSpMapper.selectBmkhry(map);
		} catch (Exception e) {
			LOG.error("查询部门考核人员失败!", e);
			throw e;
		}
		return (List<PersonTree>) map.get("p_cursor");
	}


	@Override
	public String updateYwgzkhYYSpZt(Map<String, Object> map) {
		map.put("p_cursor", StringUtils.EMPTY);
		
		try {
			
			ywgzkhSpMapper.updateYwgzkhYYSpZt(map);
			
			String message = (String)map.get("p_errmsg");
			if (StringUtils.isNotEmpty(message)) {
				if ("1".equals(message)) {
					return "操作成功！";
				}else{
					return "操作失败！";
				}
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		return null;
	}


	@Override
	public List<PersonTree> selectAllBmry(Map<String, Object> map) {
		
		map.put("p_cursor", StringUtils.EMPTY);
		
		try {
					
			ywgzkhSpMapper.selectAllBmry(map);
			
		} catch (Exception e) {
			throw e;
		}
		
		return (List<PersonTree>) map.get("p_cursor");
	}

    @Override
    public Map<String,Object> getKhById(String id) {

			Map<String,Object> res=	ywgzkhSpMapper.getKhById(id);

		return res;
    }


	@Override
	public String updateYwgzYyLc(Map<String, Object> map) {
		if (!Objects.isNull(map)) {
			map.put("p_errmsg", StringUtils.EMPTY);
			try {
				ywgzkhSpMapper.updateYwgzYyLc(map);
			} catch (Exception e) {
				LOG.error("异议流程审批失败！", e);
				e.printStackTrace();
			}
			
			String message = (String)map.get("p_errmsg");
			if (StringUtils.isNotEmpty(message)) {
				if ("1".equals(message)) {
					return "操作成功！";
				}else{
					return "操作失败！";
				}
			}
		}
		return null;
	}


	@Override
	public DWBM getSjDwbmAndAgByDwbm(String dwbm) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("p_dwbm", dwbm);
		map.put("p_cursor", StringUtils.EMPTY);
		
		ywgzkhSpMapper.getSjDwbmAndAgByDwbm(map);
		
		List<DWBM> list = (List<DWBM>)(map.get("p_cursor"));
		return list.get(0);
	}
	
	@Override
	public String updateYwgzYyFqSpLc(Map<String, Object> map){
		if (!Objects.isNull(map)) {
			map.put("p_errmsg", StringUtils.EMPTY);
			try {
				ywgzkhSpMapper.updateYwgzYyFqSpLc(map);
			} catch (Exception e) {
				LOG.error("业务考核异议发起审批失败！", e);
				throw e;
			}
			
			String message = (String)map.get("p_errmsg");
			if (StringUtils.isNotEmpty(message)) {
				if ("1".equals(message)) {
					return "操作成功！";
				}else{
					return "操作失败！";
				}
			}
		}	
		return null;
	}


	@Override
	public String updateywlxkh(Map<String, Object> map) {
		ywlxkhMapper.updateywlxkhById(map);			
			String message = (String)map.get("P_ERRMSG");
			if (StringUtils.isNotEmpty(message)) {
				if ("1".equals(message)) {
					return "操作成功！";
				}else{
					return "操作失败！";
				}
			}
			return message;
	}
	
	@Override
	public Integer deleteYwkh(List<String> dwbmArr, List<String> lxArr, String year, String startDate, String endDate) {
		Integer delRes = 0 ;
		List<Ywgzkh> ywkhList = ListUtils.EMPTY_LIST;
		List<String> khidList = new ArrayList<String>();;
		List<Ywgzlxkh> ywkhlxList = new ArrayList<Ywgzlxkh>();;
		try {
			ywkhList = ywgzkhSpMapper.getYwkhByParams(year, startDate, endDate, dwbmArr);
		} catch (Exception e) {
			LOG.error("查询业务考核表数据失败！", e);
			throw e;
		}
		if(ywkhList!=null && ywkhList.size()>0) {
			for (Ywgzkh ywgzkh : ywkhList) {
				khidList.add(ywgzkh.getId());
			}
			try {
				ywkhlxList = ywgzkhSpMapper.getYwkhlxByParams(startDate,endDate,khidList,lxArr);
			} catch (Exception e) {
				LOG.error("查询业务考核类型表数据失败！", e);
				throw e;
			}
			if(ywkhlxList!=null&&ywkhlxList.size()>0) {
				Integer result = 0;
				Integer lxresult = 0;
				for (Ywgzlxkh ywgzlxkh : ywkhlxList) {
					//删除业务考核分值表
					result = this.delYwkhlxfz(ywgzlxkh.getId());
					if(result>0) {
						lxresult = this.delYwkhlx(ywgzlxkh.getId());
					}
				}
				if(lxresult>0) {
					//删除考核表
					delRes = this.delYwkh(startDate,endDate,khidList);
				}
			}
		}
		
		return delRes;
	}

	@Override
	public Integer delYwkhlxfz(String khlxId) {
		Integer result = 0;
		try {
			result = ywgzkhSpMapper.delYwkhlxfz(khlxId);
		} catch (Exception e) {
			LOG.error("删除业务考核分值表数据失败！", e);
			throw e;
		}
		return result;
	}


	@Override
	public Integer delYwkh(String kssj, String jssj, List<String> khidList) {
		Integer res = 0 ;
		try {
			res = ywgzkhSpMapper.delYwkh(kssj, jssj, khidList);
		} catch (Exception e) {
			LOG.error("删除业务考核表数据失败！", e);
			throw e;
		}
		return res;
	}


	@Override
	public Integer delYwkhlx(String khlxid) {
		Integer res = 0 ;
		try {
			res = ywgzkhSpMapper.delYwkhlx(khlxid);
		} catch (Exception e) {
			LOG.error("删除业务考核类型表数据失败！", e);
			throw e;
		}
		return res;
	}


	@Override
	public List<Ywgzkh> getYwkhByDate(String ksrq, String jsrq) {
		List<Ywgzkh> khList = new ArrayList<Ywgzkh>();
		try {
			khList = ywgzkhSpMapper.getYwkhByDate(ksrq,jsrq);
		} catch (Exception e) {
			LOG.error("查询业务考核表数据失败！", e);
			throw e;
		}
		return khList;
	}
	
}
