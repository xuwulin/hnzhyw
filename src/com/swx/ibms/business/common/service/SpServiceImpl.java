package com.swx.ibms.business.common.service;


import com.swx.ibms.business.appraisal.bean.Ywlxmc;
import com.swx.ibms.business.archives.bean.Gsjl;
import com.swx.ibms.business.archives.mapper.JcgSfdaCxMapper;
import com.swx.ibms.business.common.bean.Jdlc;
import com.swx.ibms.business.common.bean.Splc;
import com.swx.ibms.business.common.bean.Splcsl;
import com.swx.ibms.business.common.mapper.SpMapper;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.BMBM;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.rbac.bean.RYJSFP;
import com.swx.ibms.business.system.bean.bmysbm;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 审批服务实现接口
 * @author 李治鑫
 * @since 2017-5-8
 */
@SuppressWarnings("all")
@Service("spService")
public class SpServiceImpl implements SpService {

	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(SpServiceImpl.class);
	/**
	 * 数据交换接口
	 */
	@Resource
	private SpMapper spMapper;
	/**
	 *  数据交换接口
	 */
	@Resource
	private JcgSfdaCxMapper jcgSfdaCxMapper;

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;
	/**
	 * 司法档案内容统计接口
	 */
	@Resource
	private SyCountService syCountService;

	@Override
	public List<Jdlc> getMbByJdlx(String jdlx) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_jdlx", jdlx);
		map.put("p_cursor", StringUtils.EMPTY);
		try {
			spMapper.selectJdlc1(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		return (List<Jdlc>) map.get("p_cursor");
	}

	@Override
	public List<Jdlc> getMbByLcjd(String jdlx, String lcjd) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_jdlx", jdlx);
		map.put("p_lcjd", lcjd);
		map.put("p_cursor", StringUtils.EMPTY);
		try {
			spMapper.selectJdlc2(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		return (List<Jdlc>) map.get("p_cursor");
	}

	@Override
	public Map<String, Object> addSplcslFirst(String spdw, String spbm,
											  String spr, String spyj, String splx, String spzt, String spstid,
											  String cljs, String lcjd, String jdlx,String cljsxz) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("p_spdw", spdw);
		map.put("p_spbm", spbm);
		map.put("p_spr", spr);
		map.put("p_spyj", spyj);
		map.put("p_splx", splx);
		map.put("p_spzt", spzt);
		map.put("p_spstid", spstid);
		map.put("p_cljs", cljs);
		map.put("p_lcjd", lcjd);
		map.put("p_jdlx", jdlx);
		map.put("p_lcid", StringUtils.EMPTY);
		map.put("p_cljsxz", cljsxz);
		map.put("Y", StringUtils.EMPTY);

		try {
			spMapper.addSpslFirst(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
			//记录日志
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, e.toString());
		}
		return map;
	}

	@Override
	public String addSplcsl(String spdw, String spbm, String spr,
							String spyj, String splx, String spzt, String spstid,
							String cljs, String lcjd, String jdlx,String lcid,String cljsxz) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("p_spdw", spdw);
		map.put("p_spbm", spbm);
		map.put("p_spr", spr);
		map.put("p_spyj", spyj);
		map.put("p_splx", splx);
		map.put("p_spzt", spzt);
		map.put("p_spstid", spstid);
		map.put("p_cljs", cljs);
		map.put("p_lcjd", lcjd);
		map.put("p_jdlx", jdlx);
		map.put("p_lcid", lcid);
		map.put("p_cljsxz", cljsxz);
		map.put("Y", StringUtils.EMPTY);

		try {
			spMapper.addSpsl(map);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(StringUtils.EMPTY, e);
			//记录日志
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, e.toString());
		}
		return (String) map.get("Y");
	}

	@Override
	public String updateSplcsl(String spdw, String spbm, String spr,
							   String spyj, String splx, String spzt,
							   String spstid, String cljs, String lcjd, String jdlx) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("p_spdw", spdw);
		map.put("p_spbm", spbm);
		map.put("p_spr", spr);
		map.put("p_spyj", spyj);
		map.put("p_splx", splx);
		map.put("p_spzt", spzt);
		map.put("p_spstid", spstid);
		map.put("p_cljs", cljs);
		map.put("p_lcjd", lcjd);
		map.put("p_jdlx", jdlx);
		map.put("Y", StringUtils.EMPTY);

		try {
			spMapper.updateSpsl(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
			//记录日志
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, e.toString());
		}

		String str = "";
		if("2".equals(spzt)||"4".equals(spzt)){
			str = "同意";
		}else if("3".equals(spzt)){
			str = "退回";
		}else if("5".equals(spzt)){
			str = "标记";
		}

		//日志记录
		logService.info(Constant.CURRENT_USER.get().getDwbm(),
				Constant.CURRENT_USER.get().getGh(),
				Constant.CURRENT_USER.get().getMc(),
				Constant.RZLX_CZRZ, str+"审批申请");


		return (String) map.get("Y");
	}

	@Override
	public List<Splcsl> selectNewSpsl(String spdw, String spbm,
									  String spr, String spstid, String splx) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_spdw", spdw);
		map.put("p_spbm", spbm);
		map.put("p_spr", spr);
		map.put("p_spstid", spstid);
		map.put("p_splx", splx);
		map.put("p_cursor", StringUtils.EMPTY);
		try {
			spMapper.selectSpsl(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		return (List<Splcsl>) map.get("p_cursor");
	}

	@Override
	public List<Splcsl> selectNewSpslByLcid(Map<String, Object> map) {
		try {
			spMapper.selectSpslByLcid(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		return (List<Splcsl>) map.get("p_cursor");
	}

	@Override
	public String setAudit(String spstid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_spstid", spstid);
		map.put("p_errmsg", StringUtils.EMPTY);

		try {
			spMapper.setAudit(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
			//记录日志
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, e.toString());
		}
		String temp = (String) map.get("p_errmsg");
		if (StringUtils.isEmpty(temp)) {
			return "1";
		} else{
			return "0";
		}
	}
	@Override
	public String updateGszt(String spstid,String zt) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_spstid", spstid);
		map.put("p_zt", zt);
		map.put("p_errmsg", StringUtils.EMPTY);

		try {
			spMapper.updateGszt(map);
			if("1".equals(zt)){
				//如果设为已公示，则在公示栏加入公示信息
				Map<String,Object> map1 = new HashMap<String,Object>();
				map1.put("p_fqrbm", Constant.CURRENT_USER.get().getGh());
				map1.put("p_dwbm", Constant.CURRENT_USER.get().getDwbm());
				map1.put("p_dagzid", spstid);
				map1.put("p_errmsg", StringUtils.EMPTY);
				jcgSfdaCxMapper.addGstoGsl(map1);

				//获取公示截止时间，并在公示表中更新公示截止时间
				Date gsJzsj=null;
				List<Gsjl> gsxxList=syCountService.getGsJzSj(spstid, "1");
				if (!CollectionUtils.isEmpty(gsxxList)) {
					gsJzsj = gsxxList.get(0).getGsJzsj();
				}
				jcgSfdaCxMapper.updateGsJzsj(spstid,gsJzsj);

			}else if("2".equals(zt)){
				//如果设为未公示，则在公示栏删除公示信息
				Map<String,Object> map1 = new HashMap<String,Object>();
				map1.put("p_dagzid", spstid);
				map1.put("p_errmsg", StringUtils.EMPTY);
				jcgSfdaCxMapper.deleteGsfromGsl(map1);
			}
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
			//记录日志
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, e.toString());
		}
		String temp = (String) map.get("p_errmsg");
		if (StringUtils.isEmpty(temp)) {
			return "1";
		} else{
			return "0";
		}
	}

	@Override
	public String getRsbBmbm(HttpServletRequest request) {
//		Properties prop = new Properties();
//		InputStream in = this.getClass().getResourceAsStream("/cfg.properties");
//		try {
//			prop.load(in);
//			in.close();
//		} catch (Exception e) {
//		}
//
//		String rsbBmbm = MapUtils.getString(prop, "rsb", StringUtils.EMPTY);
//
//		String[] bmbm = null;
//		if (rsbBmbm.startsWith(dwbm)) {
//			bmbm = rsbBmbm.split(",");
//		}
		String result = StringUtils.EMPTY;
		List<String> bmbmstr =(List<String>)request.getSession().getAttribute("bmjs");
		if(bmbmstr.size()>0){
			result = bmbmstr.get(0).split(",")[1];
		}
		return result;
	}

	@Override
	public String[] getDlrBmbm(HttpServletRequest request) {
		List<String> bmbmstr =(List<String>)request.getSession().getAttribute("bmjs");
		String[] dlrbmbmArray = new String[bmbmstr.size()];
		for(int i = 0 ;i<bmbmstr.size();i++){
			dlrbmbmArray[i] = bmbmstr.get(i).split(",")[1];
		}
		return dlrbmbmArray;
	}

	@Override
	public List<Ywlxmc> getYwlxByYdkhid(String ydkhid) {
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("p_ydkhid", ydkhid);
		map.put("p_cursor", StringUtils.EMPTY);
		map.put("p_errmsg", StringUtils.EMPTY);
		try {
			spMapper.getYwlxByYdkhid(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		String temp = (String)map.get("p_errmsg");
		if(StringUtils.isEmpty(temp)){
			return (List<Ywlxmc>)map.get("p_cursor");
		}else{
			return null;
		}
	}

	@Override
	public String getYwmcByYwbm(String ywbm) {
		Map<String,Object> map = new HashMap<String,Object> ();
		map.put("p_ywbm", ywbm);
		map.put("p_ywmc", StringUtils.EMPTY);
		map.put("p_errmsg", StringUtils.EMPTY);

		try {
			spMapper.getYwmcByYwbm(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		String temp = (String) map.get("p_errmsg");
		if(StringUtils.isEmpty(temp)){
			return (String) map.get("p_ywmc");
		}else{
			return null;
		}
	}

	@Override
	public String getYwlxByBmys(String bmys) {
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("p_bmlbbm", bmys);
		map.put("p_ywbm", StringUtils.EMPTY);
		map.put("p_errmsg", StringUtils.EMPTY);

		try {
			spMapper.getYwlxByBmys(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		String temp = (String) map.get("p_errmsg");
		if(StringUtils.isEmpty(temp)){
			return (String)map.get("p_ywbm");
		}else{
			return null;
		}
	}

	@Override
	public List<String> getBmysByGh(String dwbm, String gh) {
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("p_dwbm", dwbm);
		map.put("p_gh", gh);
		map.put("p_cursor", StringUtils.EMPTY);
		map.put("p_errmsg", StringUtils.EMPTY);

		try {
			spMapper.getBmysByGh(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		String temp = (String) map.get("p_errmsg");
		if(StringUtils.isEmpty(temp)){
			return (List<String>)map.get("p_cursor");
		}else{
			return null;
		}
	}
	/**根据审批实体ID和审批类型判断能否显示撤回申请按钮
	 * @param spstid 审批实体id
	 * @param splx 审批类型
	 * @return  0 不显示   jdlx(节点类型字符串)可能显示
	 */
	@Override
	public String getAllBySplx(String spstid, String splx) {
		Map<String ,Object> map = new HashMap<String,Object> ();
		String jdlx = "0";
		/*map.put("p_spstid", spstid);
		map.put("p_splx", splx);
		map.put("p_sign", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);

		try {
			spMapper.getAllBySplx(map);
		} catch (Exception e) {
			e.printStackTrace();
			//LOG.error(StringUtils.EMPTY, e);
		}
		String sign = (String) map.get("p_sign");
		if("1".equals(sign)){
			List<Splcsl> splcslList = (List<Splcsl>) map.get("p_cursor");
			String jdlx = splcslList.get(1).getJdlx();
			return jdlx;
		}else{
			return "0";
		}*/
		String spzt = StringUtils.EMPTY;
		try {
			spzt = spMapper.getSpztByWbidAndSplx(spstid,splx);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("查询该节点类型的审批状态失败！", e);
		}
		List<Splcsl> splcslList = new ArrayList<Splcsl>(Constant.NUM_4);
		if("1".equals(spzt)) {
			try {
				splcslList = spMapper.getSpxxByWbidAndSplx(spstid, splx);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("查询该节点类型的审批信息失败！", e);
			}
			jdlx = splcslList.get(1).getJdlx();
		}
		return jdlx;
	}

	@Override
	public String recallSp(String spstid, String splx) {
		Map<String ,Object> spmap = new HashMap<String,Object>();
		Map<String,Object> fqmap = new HashMap<String,Object> ();

		Map<String ,Object> map = new HashMap<String,Object> ();
		map.put("p_spstid", spstid);
		map.put("p_splx", splx);
		map.put("p_sign", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);

		String str = "";
		if(splx=="1"){
			str = "公示";
		}else if(splx=="2"){
			str = "档案";
		}else if(splx=="3"){
			str = "个人绩效";
		}else if(splx=="4"){
			str = "荣誉技能";
		}else if(splx=="12"){
			str = "办案业绩";
		}

		try {
			spMapper.getAllBySplx(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		String sign = (String) map.get("p_sign");
		if("1".equals(sign)){
			List<Splcsl> splcslList = (List<Splcsl>) map.get("p_cursor");

			int i = 0;
			for (;i<splcslList.size();i++){
				if("0".equals(splcslList.get(i).getSpzt())){
					break;
				}
			}
			if(i==1){
				spmap.put("p_id", splcslList.get(0).getId());
				spmap.put("p_errmsg", StringUtils.EMPTY);

				fqmap.put("p_id", splcslList.get(1).getId());
				fqmap.put("p_errmsg", StringUtils.EMPTY);

				try {
					spMapper.deleteSpslById(spmap);
					spMapper.deleteSpslById(fqmap);
				} catch (Exception e) {
					LOG.error(StringUtils.EMPTY, e);
					//记录日志
					logService.error(Constant.CURRENT_USER.get().getDwbm(),
							Constant.CURRENT_USER.get().getGh(),
							Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ,
							e.toString());
				}

				String temp1 = (String) spmap.get("p_errmsg");
				String temp2 = (String) fqmap.get("p_errmsg");
				if(StringUtils.isBlank(temp1)&&StringUtils.isBlank(temp2)){
					//日志记录
					logService.info(Constant.CURRENT_USER.get().getDwbm(),
							Constant.CURRENT_USER.get().getGh(),
							Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CZRZ,
							"撤回"+str+"审批申请");
					return "1";
				}else{
					//日志记录
					logService.error(Constant.CURRENT_USER.get().getDwbm(),
							Constant.CURRENT_USER.get().getGh(),
							Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ,
							"撤回"+str+"审批申请失败");
					return "0";
				}

			}else{
				spmap.put("p_id", splcslList.get(0).getId());
				spmap.put("p_errmsg", StringUtils.EMPTY);


				fqmap.put("p_id", splcslList.get(1).getId());
				fqmap.put("p_spdw", splcslList.get(1).getSpdw());
				fqmap.put("p_spbm", splcslList.get(1).getSpbm());
				fqmap.put("p_spr", splcslList.get(1).getSpr());
				fqmap.put("p_spyj", StringUtils.EMPTY);
				fqmap.put("p_splx", splcslList.get(1).getSplx());
				fqmap.put("p_spzt", "1");
				fqmap.put("p_spstid", splcslList.get(1).getSpstid());
				fqmap.put("p_spsj", splcslList.get(1).getSpsj());
				fqmap.put("p_cljs", splcslList.get(1).getCljs());
				fqmap.put("p_lcjd", StringUtils.EMPTY);
				fqmap.put("p_jdlx", splcslList.get(1).getJdlx());
				fqmap.put("p_errmsg", StringUtils.EMPTY);
				try {
					spMapper.deleteSpslById(spmap);
					spMapper.updateSpslById(fqmap);
				} catch (Exception e) {
					LOG.error(StringUtils.EMPTY, e);
					//记录日志
					logService.error(Constant.CURRENT_USER.get().getDwbm(),
							Constant.CURRENT_USER.get().getGh(),
							Constant.CURRENT_USER.get().getMc(),
							Constant.RZLX_CWRZ, e.toString());
				}

				String temp = (String) fqmap.get("p_errmsg");
				if(StringUtils.isBlank(temp)){
					//日志记录
					logService.info(Constant.CURRENT_USER.get().getDwbm(),
							Constant.CURRENT_USER.get().getGh(),
							Constant.CURRENT_USER.get().getMc(),
							Constant.RZLX_CZRZ,
							"撤回"+str+"审批申请");
					return "1";
				}else{
					//日志记录
					logService.error(Constant.CURRENT_USER.get().getDwbm(),
							Constant.CURRENT_USER.get().getGh(),
							Constant.CURRENT_USER.get().getMc(),
							Constant.RZLX_CWRZ,
							"撤回"+str+"审批申请失败");
					return "0";
				}

			}
		}else{
			return "0";
		}
	}

	@Override
	public Splc isSptg(String spstid, String splx, String lcid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_spstid",spstid);
		map.put("p_splx", splx);
		map.put("p_lcid", lcid);
		map.put("p_cursor", "");
		List<Splc> list = new ArrayList<>();
		try {
			spMapper.isSptg(map);
			list =(List<Splc>) map.get("p_cursor");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (list != null && list.size() > 0) {
			return (Splc)list.get(0);
		} else {
			return null;
		}
	}

	/*
	 * 通过单位和部门映射找第一个部门
	 */
	@Override
	public String selectBmysBm(String dwbm, String bmys) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_dwbm",dwbm);
		map.put("p_bmys", bmys);
		map.put("p_cursor", null);
		jcgSfdaCxMapper.selectBmysBm(map);
		List<bmysbm> list = (List<bmysbm>) map.get("p_cursor");
		return (String)list.get(0).getBmbm();
	}

	@Override
	public RYBM getFqr(String lcid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_lcid", lcid);
		map.put("p_cursor", StringUtils.EMPTY);
		map.put("p_errmsg", StringUtils.EMPTY);
		spMapper.getFqr(map);
		List<Splcsl> splcslList = (List<Splcsl>)map.get("p_cursor");

		RYBM ryObj = new RYBM();
		ryObj.setDwbm(splcslList.get(0).getSpdw());
		ryObj.setGh(splcslList.get(0).getSpr());

		return ryObj;
	}

	@Override
	public Splcsl getSpById(String spid) {
		Map<String,Object> map = new HashMap<String,Object>();

		if (StringUtils.isNoneEmpty(spid)) {

			map.put("p_spid", spid);
			map.put("p_cursor", StringUtils.EMPTY);
			spMapper.getSpById(map);

			List<Splcsl> splcslList = (List<Splcsl>)map.get("p_cursor");
			if(!CollectionUtils.isEmpty(splcslList)){
				Splcsl splcsl = splcslList.get(0);
				return splcsl;
			}

		}

		return null;

	}

	@Override
	public String getBmbmByBmys(String dwbm, String bmys) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_dwbm", dwbm);
		map.put("p_bmys", bmys);
		map.put("p_cursor", StringUtils.EMPTY);
		map.put("p_errmsg", StringUtils.EMPTY);

		try {
			spMapper.getBmbmByBmys(map);
			String temp = (String) map.get("p_errmsg");
			if(!StringUtils.isEmpty(temp)){
				return null;
			}else{
				String bmbmstr = "";
				List<BMBM> list = new ArrayList<BMBM>();
				list = (List<BMBM>)map.get("p_cursor");
				if(list.size()>0){
					for(int i = 0;i<list.size();i++){
						bmbmstr = bmbmstr + list.get(i).getBmbm();
						if(i!=list.size()-1){
							bmbmstr = bmbmstr + ",";
						}
					}
				}

				return bmbmstr;
			}
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY,e);
		}

		return null;
	}

	@Override
	public Splcsl getPreviousLcslBySpstid(String spstid, String splx) {

		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("p_spstid", spstid);
		map.put("p_splx", splx);
		map.put("p_errmsg", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);


		try {
			spMapper.getPreviousLcslBySpstid(map);
			String temp = (String) map.get("p_errmsg");
			if(StringUtils.isBlank(temp)){
				List<Splcsl> splcslList = (List<Splcsl>)map.get("p_cursor");
				if(splcslList.size()>0){
					return splcslList.get(0);
				}else{
					return null;
				}
			}else{
				return null;
			}
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY,e);
		}


		return null;
	}

	@Override
	public BMBM getBmysByBmbm(String dwbm, String bmbm) {
		Map<String,Object> map = new HashMap<String,Object> ();
		map.put("p_dwbm", dwbm);
		map.put("p_bmbm", bmbm);
		map.put("p_errmsg", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);

		try {
			spMapper.getBmysByBmbm(map);
			String temp = (String) map.get("p_errmsg");
			if(StringUtils.isBlank(temp)){
				List<BMBM> bmbmList = (List<BMBM>) map.get("p_cursor");
				if(bmbmList.size()>0){
					return bmbmList.get(0);
				}else{
					return null;
				}
			}else{
				return null;
			}
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY,e);
		}

		return null;
	}

	@Override
	public String getBmbmByGh(String dwbm, String gh) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_dwbm", dwbm);
		map.put("p_gh", gh);
		map.put("p_errmsg", StringUtils.EMPTY);
		map.put("p_cursor", StringUtils.EMPTY);

		try {
			spMapper.getBmbmByGh(map);
			String temp = (String) map.get("p_errmsg");

			if(StringUtils.isEmpty(temp)){
				List<RYJSFP> fpList = new ArrayList<RYJSFP>();
				fpList = (List<RYJSFP>) map.get("p_cursor");

				if(fpList.size()>0){
					return fpList.get(0).getBmbm();
				}else{
					return null;
				}
			}else{
				return null;
			}
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY,e);
		}
		return null;
	}

	@Override
	public Splcsl getFqjlBySpstidAndSplx(String spstid, String splx) {
		Splcsl splcslObj = null;
		try {
			splcslObj = spMapper.getFqjlBySpstidAndSplx(spstid,splx);
		} catch (Exception e) {
			LOG.error("通过审批实体id和审批类型查询发起记录信息服务出错，错误信息：", e);
		}
		return splcslObj;
	}

	@Override
	public List<Splcsl> getLartestSpxx(String spstid, String splx) {
		List<Splcsl> spList = new ArrayList<Splcsl>();
		try {
			spList = spMapper.getLartestSpxx(spstid,splx);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("通过审批实体id和审批类型查询最新审批信息出错!", e);
		}
		return spList;
	}

	@Override
	public Integer modifySplcslById(String spyj, String spzt,String cljs,String spId) {
		Integer res = 0;
		try {
			res = spMapper.modifySplcslById(spzt,spyj,cljs,spId);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("通过审批实体id和审批类型查询最新审批信息出错!", e);
		}
		return res;
	}

}
