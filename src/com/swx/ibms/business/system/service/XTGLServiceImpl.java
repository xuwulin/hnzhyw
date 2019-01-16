package com.swx.ibms.business.system.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.*;
import com.swx.ibms.business.system.mapper.XTGLMapper;
import com.swx.ibms.business.system.service.XTGLService;
import com.swx.ibms.common.utils.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 系统管理服务实现接口
 * @author 李治鑫
 * @since 2017-5-8
 */
@SuppressWarnings("all")
@Service("xTGLService")
public class XTGLServiceImpl implements XTGLService {

	/**
	 * 日志对象
	 * */
	private static final Logger LOG = Logger.getLogger(XTGLServiceImpl.class);

	/**
	 * 数据访问接口
	 **/
	@Resource
	private XTGLMapper xtglmapper;


	/**
	 * 查询所有单位列表
	 * */
	@Override
	public List<DWBM> getAllDw() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_cursor", "");
		map.put("p_errmsg", "");
		try {
			xtglmapper.getAllDw(map);
		} catch (Exception e) {
			LOG.error("查询所有单位信息失败！", e);
			throw e;
		}
		String temp = (String) map.get("p_errmsg");
		if(temp == null||"".equals(temp)){
			return (List<DWBM>) map.get("p_cursor");
		}else{
			return null;
		}
	}

	/**
	 * 查询单位信息
	 * */
	@Override
	public List<DWBM> getDw(String dwbm) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<DWBM> dwList = new ArrayList<DWBM>();
		map.put("p_dwbm", dwbm);
		map.put("p_cursor", "");
		map.put("p_errmsg", "");
		try {
			xtglmapper.getDw(map);
			String temp = (String) map.get("p_errmsg");
			if(StringUtils.isBlank(temp)){
				dwList = (List<DWBM>) map.get("p_cursor");
			}

		} catch (Exception e) {
			LOG.error("查询单位信息失败！", e);
			throw e;
		}
		return dwList;
	}

	@Override
	public int addDw(DWBM dw) {
		Map<String,Object> map = new HashMap<String,Object>();

		map.put("p_dwbm", dw.getDwbm());
		map.put("p_dwmc",dw.getDwmc());
		map.put("p_fdwbm",dw.getFdwbm());
		map.put("p_dwjb",dw.getDwjb());
		map.put("p_sfsc",dw.getSfsc());
		map.put("p_dwjc",dw.getDwjc());
		map.put("p_dwsx",dw.getDwsx());
		map.put("p_errmsg", "");
		try {
			xtglmapper.addDw(map);
		} catch (Exception e) {
			LOG.error("添加单位失败！", e);
			e.printStackTrace();
		}

		String temp = (String) map.get("p_errmsg");
		if(temp == null||"".equals(temp)){
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	public int updateDw(DWBM dw) {
		Map<String,Object> map = new HashMap<String,Object>();

		map.put("p_dwbm", dw.getDwbm());
		map.put("p_dwmc",dw.getDwmc());
		map.put("p_fdwbm",dw.getFdwbm());
		map.put("p_dwjb",dw.getDwjb());
		map.put("p_sfsc",dw.getSfsc());
		map.put("p_dwjc",dw.getDwjc());
		map.put("p_dwsx",dw.getDwsx());
		map.put("p_errmsg", "");
		try {
			xtglmapper.updateDw(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		String temp = (String) map.get("p_errmsg");
		if(temp == null||"".equals(temp)){
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	public int deleteDw(String dwbm) {
		Map<String,Object> map = new HashMap<String,Object>();

		map.put("p_dwbm", dwbm);
		map.put("p_errmsg", "");
		try {
			xtglmapper.deleteDw(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		String temp = (String) map.get("p_errmsg");
		if(temp == null||"".equals(temp)){
			return (int) 1;
		}else{
			return 0;
		}
	}


	@Override
	public List<BMBM> getBmByDwbm(String dwbm) {
		Map<String,Object> map = new HashMap<String,Object>();

		map.put("p_dwbm", dwbm);
		map.put("p_cursor", "");
		map.put("p_errmsg", "");
		try {
			xtglmapper.getBmByDwbm(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		String temp = (String) map.get("p_errmsg");
		if(temp == null||"".equals(temp)){
			return (List<BMBM>) map.get("p_cursor");
		}else{
			return null;
		}
	}

	@Override
	public List<BMBM> getBm(String dwbm, String bmbm) {
		Map<String,Object> map = new HashMap<String,Object>();

		map.put("p_dwbm", dwbm);
		map.put("p_bmbm", bmbm);
		map.put("p_cursor", "");
		map.put("p_errmsg", "");
		try {
			xtglmapper.getBm(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		String temp = (String) map.get("p_errmsg");
		if(temp == null||"".equals(temp)){
			return (List<BMBM>) map.get("p_cursor");
		}else{
			return null;
		}

	}

	@Override
	public int addBm(BMBM bm) {
		Map<String,Object> map = new HashMap<String,Object>();

		map.put("p_dwbm", bm.getDwbm());
		map.put("p_bmbm", bm.getBmbm());
		map.put("p_fbmbm", bm.getFbmbm());
		map.put("p_bmmc", bm.getBmmc());
		map.put("p_bmjc", bm.getBmjc());
		map.put("p_bmwhjc", bm.getBmwhjc());
		map.put("p_bmahjc", bm.getBmahjc());
		map.put("p_sflsjg", bm.getSflsjg());
		map.put("p_sfcbbm", bm.getSfcbbm());
		map.put("p_bmxh", bm.getBmxh());
		map.put("p_bz", bm.getBz());
		map.put("p_sfsc", bm.getSfsc());
		map.put("p_bmys", bm.getBmys());
		map.put("p_errmsg", "");

		try {
			xtglmapper.addBm(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		String temp = (String) map.get("p_errmsg");
		if(temp == null||"".equals(temp)){
			return (int) 1;
		}else{
			return 0;
		}
	}

	@Override
	public int updateBm(BMBM bm) {
		Map<String,Object> map = new HashMap<String,Object>();

		map.put("p_dwbm", bm.getDwbm());
		map.put("p_bmbm", bm.getBmbm());
		map.put("p_fbmbm", bm.getFbmbm());
		map.put("p_bmmc", bm.getBmmc());
		map.put("p_bmjc", bm.getBmjc());
		map.put("p_bmwhjc", bm.getBmwhjc());
		map.put("p_bmahjc", bm.getBmahjc());
		map.put("p_sflsjg", bm.getSflsjg());
		map.put("p_sfcbbm", bm.getSfcbbm());
		map.put("p_bmxh", bm.getBmxh());
		map.put("p_bz", bm.getBz());
		map.put("p_sfsc", bm.getSfsc());
		map.put("p_bmys", bm.getBmys());
		map.put("p_errmsg", "");

		try {
			xtglmapper.updateBm(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		String temp = (String) map.get("p_errmsg");
		if(temp == null||"".equals(temp)){
			return (int) 1;
		}else{
			return 0;
		}
	}

	@Override
	public int deleteBm(String dwbm, String bmbm) {
		Map<String,Object> map = new HashMap<String,Object>();

		map.put("p_dwbm", dwbm);
		map.put("p_bmbm", bmbm);
		map.put("p_errmsg", "");
		try {
			xtglmapper.deleteBm(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		String temp = (String) map.get("p_errmsg");
		if(temp == null||"".equals(temp)){
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	public List<RYBM> getRyByBmbm(String dwbm, String bmbm, String jsbm){
		Map<String,Object> map = new HashMap<String,Object>();

		map.put("p_dwbm", dwbm);
		map.put("p_bmbm", bmbm);
		map.put("p_jsbm", jsbm);
		//map.put("p_upper", ((currentpage-1)*pageSize));
		//map.put("p_floor", (currentpage*pageSize));
		map.put("p_cursor", "");
		map.put("p_errmsg", "");
		try {
			xtglmapper.getRyByBmbm(map);
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
	public Map<String, Object> getRy(String dwbm, String name, Integer page, Integer rows) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String, Object>> ryList = new ArrayList<>();
		Page pager = PageHelper.startPage(page, rows);
		try {
			ryList = xtglmapper.getRy(dwbm, name);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
			e.printStackTrace();
		}

		map.put("total", pager.getTotal());
		map.put("rows", ryList);
		return map;

	}

	@Override
	public int addRy(RYBM ry) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_dwbm", ry.getDwbm());
		map.put("p_gh", ry.getGh());
		map.put("p_gzzh", ry.getGzzh());
		map.put("p_dlbm", ry.getDlbm());
		map.put("p_kl", ry.getKl());
		map.put("p_mc", ry.getMc());
		map.put("p_yddhhm", ry.getYddhhm());
		map.put("p_dzyj", ry.getDzyj());
		map.put("p_ydwbm", ry.getYdwbm());
		map.put("p_ydwmc", ry.getYdwmc());
		map.put("p_sflsry", ry.getSflsry());
		map.put("p_sfsc", ry.getSfsc());
		map.put("p_sftz", ry.getSftz());
		map.put("p_xb", ry.getXb());
		map.put("p_caid", ry.getCaid());
		map.put("p_zrjcggh", ry.getZrjcggh());
		map.put("p_errmsg", "");

		try {
			xtglmapper.addRy(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		String temp = (String) map.get("p_errmsg");
		if(temp == null||"".equals(temp)){
			// 人员添加成功后再给此人分配角色
			int res = 0;
			try {
				res = xtglmapper.addJsToRyjsfp(ry.getDwbm(),ry.getBmbm(),ry.getJsbm(),ry.getGh());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (res > 0) {
				return 1;
			} else {
				return 0;
			}
		}else{
			return 0;
		}
	}

	@Override
	public int updateRy(RYBM ry, String oldGh) {
		Map<String,Object> map = new HashMap<String,Object>();
		String dwbm = ry.getDwbm();
		String bmbm = ry.getBmbm();
		String jsbm = ry.getJsbm();
		String gh = ry.getGh();
		String dlbm = ry.getDlbm();
		String gzzh = ry.getGzzh();
		String kl = ry.getKl();
		String mc = ry.getMc();
		String sflsry = ry.getSflsry();
		String xb = ry.getXb();

		int res = 0;
		try {
			res = xtglmapper.updatePer(dwbm,gh,mc,dlbm,xb,kl,gzzh,oldGh);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(StringUtils.EMPTY, e);
		}
		if (res > 0) {
			// 人员更新成功，再更新角色分配表
			int data = 0;
			try {
				data = xtglmapper.updateJsToRyjsfp(dwbm,bmbm,jsbm,gh,oldGh);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (data > 0) {
				res = 1;
			} else {
				res = 0;
			}
		}
		return res;
	}

	@Override
	public int deleteRy(String dwbm, String bmbm, String jsbm, String gh) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_dwbm", dwbm);
		map.put("p_bmbm", bmbm);
		map.put("p_jsbm", jsbm);
		map.put("p_gh", gh);
		map.put("p_errmsg", "");

		try {
			xtglmapper.deleteRy(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		String temp = (String) map.get("p_errmsg");
		if(temp == null||"".equals(temp)){
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	public List<JSBM> getJsBybmbm(String dwbm, String bmbm) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_dwbm", dwbm);
		map.put("p_bmbm", bmbm);
		map.put("p_cursor", "");
		map.put("p_errmsg", "");
		try {
			xtglmapper.getJsBybmbm(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		String temp = (String) map.get("p_errmsg");
		if(temp == null||"".equals(temp)){
			return (List<JSBM>) map.get("p_cursor");
		}else{
			return null;
		}
	}

	@Override
	public List<JSBM> getJs(String dwbm, String bmbm, String jsbm) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_dwbm", dwbm);
		map.put("p_bmbm", bmbm);
		map.put("p_jsbm", jsbm);
		map.put("p_cursor", "");
		map.put("p_errmsg", "");

		try {
			xtglmapper.getJs(map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String temp = (String) map.get("p_errmsg");
		if(temp == null||"".equals(temp)){
			return (List<JSBM>) map.get("p_cursor");
		}else{
			return null;
		}
	}

	@Override
	public int addJs(JSBM js) {
		Map<String,Object> map = new HashMap<String,Object>();

		int jsbmNum = 0;
		int len = 0;
		String jsbm = "";
		try {
			jsbm = xtglmapper.getJsbmByBmbm(js.getDwbm(), js.getBmbm());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!StringUtils.isEmpty(jsbm)) {
			jsbmNum = Integer.parseInt(jsbm);
		}

		jsbmNum += 1;
		jsbm = jsbmNum + "";
		// 两位
		if (jsbm.length() == 1) {
			jsbm = "00" + jsbm;
		} else if (jsbm.length() == 2) {
			jsbm = "0" + jsbm;
		}

		map.put("p_dwbm", js.getDwbm());
		map.put("p_jsbm", jsbm);
		map.put("p_jsmc", js.getJsmc());
		map.put("p_bmbm", js.getBmbm());
		map.put("p_jsxh", js.getJsxh());
		map.put("p_spjsbm", js.getSpjsbm());
		map.put("p_description", js.getDescription());
		map.put("p_errmsg", "");

		try {
			xtglmapper.addJs(map);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(StringUtils.EMPTY, e);
		}

		String temp = (String) map.get("p_errmsg");
		if("1".equals(temp)){
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	public int updateJs(JSBM js) {
		Map<String,Object> map = new HashMap<String,Object>();

		String dwbm = js.getDwbm();
		String jsbm = js.getJsbm();
		String jsmc = js.getJsmc();
		String bmbm = js.getBmbm();
		int jsxh = js.getJsxh();
		String spjsbm = js.getSpjsbm();
		String description = js.getDescription();

		int data = 0;
		try {
			data = xtglmapper.updateJs(dwbm,bmbm,jsbm,jsmc,jsxh,spjsbm, description);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(StringUtils.EMPTY, e);
		}

		return data;
	}

	@Override
	public int deleteJs(String dwbm, String bmbm, String jsbm) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("dwbm", dwbm);
		map.put("bmbm", bmbm);
		map.put("jsbm", jsbm);

		int data = 0;
		try {
			data = xtglmapper.deleteJs(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		return data;
	}

	@Override
	public DWBM createDw(String dwbm, String dwmc,
						 String fdwbm, String dwjb, String sfsc,
						 String dwjc, String dwsx) {
		DWBM dwbmObject = new DWBM();
		dwbmObject.setDwbm(dwbm);
		dwbmObject.setDwmc(dwmc);
		dwbmObject.setFdwbm(fdwbm);
		dwbmObject.setDwjb(dwjb);
		dwbmObject.setSfsc(sfsc);
		dwbmObject.setDwjc(dwjc);
		dwbmObject.setDwsx(dwsx);
		return dwbmObject;
	}

	@Override
	public BMBM createBm(String dwbm, String bmbm, String fbmbm,
						 String bmmc, String bmjc, String bmwhjc, String bmahjc,
						 String sflsjg, String sfcbbm, int bmxh, String bz, String sfsc, String bmys) {
		BMBM bmbmObject = new BMBM(dwbm, bmbm, fbmbm, bmmc,
				bmjc, bmwhjc, bmahjc, sflsjg, sfcbbm, bmxh, bz, sfsc, bmys);
		return bmbmObject;
	}

	@Override
	public RYBM createRy(String dwbm,String bmbm, String jsbm, String gh, String dlbm,
						 String kl, String mc, String gzzh, String yddhhm,
						 String dzyj, String ydwbm, String ydwmc, String sflsry,
						 String sfsc, String sftz, String xb,
						 String caid, String zrjcggh) {
		RYBM rybmObject = new RYBM();

		if (gh.equals("")) {
			int ghNum = 0;
			int len = 0;
			String getGh = "";
			try {
				getGh = xtglmapper.getMaxGh(dwbm);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (!StringUtils.isEmpty(getGh)) {
				ghNum = Integer.parseInt(getGh);
			}

			ghNum += 1;
			getGh = ghNum + "";

			if (getGh.length() == 1) {
				getGh = "000" + getGh;
			} else if (getGh.length() == 2) {
				getGh = "00" + getGh;
			} else if (getGh.length() == 3) {
				getGh = "0" + getGh;
			}
			rybmObject.setGh(getGh);
		} else {
			rybmObject.setGh(gh);
		}

		// 默认密码
		String pwd = "11111111";
		String newPwd = "";

		if (kl.equals("")) {
			// 将pwd密码转换为MD5格式
			newPwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
		} else {
			if (kl.length() != Constant.NUM_32) {
				// 将pwd密码转换为MD5格式,任何密码经加密后都成为32位密码
				newPwd = DigestUtils.md5DigestAsHex(kl.getBytes());
			} else {
				newPwd = kl;
			}
		}

		rybmObject.setDwbm(dwbm);
		rybmObject.setBmbm(bmbm);
		rybmObject.setJsbm(jsbm);
		rybmObject.setDlbm(StrUtil.strTransform(dlbm));
		rybmObject.setKl(newPwd);
		rybmObject.setMc(StrUtil.strTransform(mc));
		rybmObject.setGzzh(StrUtil.strTransform(gzzh));
		rybmObject.setYddhhm(yddhhm);
		rybmObject.setDzyj(dzyj);
		rybmObject.setYdwbm(ydwbm);
		rybmObject.setYdwmc(ydwmc);
		rybmObject.setSflsry(sflsry);
		rybmObject.setSfsc(sfsc);
		rybmObject.setSftz(sftz);
		rybmObject.setXb(xb);
		rybmObject.setCaid(caid);
		rybmObject.setZrjcggh(zrjcggh);
		return rybmObject;
	}

	@Override
	public JSBM createJs(String dwbm, String jsbm, String jsmc,
						 String bmbm, int jsxh, String spjsbm,String descripton) {
		JSBM jsbmObject = new JSBM();
		jsbmObject.setDwbm(dwbm);
		jsbmObject.setJsbm(jsbm);
		jsbmObject.setJsmc(jsmc);
		jsbmObject.setBmbm(bmbm);
		jsbmObject.setJsxh(jsxh);
		jsbmObject.setSpjsbm(spjsbm);
		jsbmObject.setDescription(descripton);
		return jsbmObject;
	}


	@Override
	public List<RYJSFP> getFp(String dwbm, String bmbm, String jsbm, String gh) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_dwbm", dwbm);
		map.put("p_bmbm", bmbm);
		map.put("p_jsbm", jsbm);
		map.put("p_gh", gh);
		map.put("p_cursor", "");
		map.put("p_errmsg", "");

		try {
			xtglmapper.getFp(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		String temp = (String) map.get("p_errmsg");
		if(temp == null||"".equals(temp)){
			return (List<RYJSFP>) map.get("p_cursor");
		}else{
			return null;
		}

	}

	@Override
	public int updateFp(RYJSFP ryjsfp) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_dwbm", ryjsfp.getDwbm());
		map.put("p_bmbm", ryjsfp.getBmbm());
		map.put("p_jsbm", ryjsfp.getJsbm());
		map.put("p_gh", ryjsfp.getGh());
		map.put("p_zjldgh", ryjsfp.getZjldgh());
		map.put("p_ryxh", ryjsfp.getRyxh());
		map.put("p_errmsg", "");

		try {
			xtglmapper.updateFp(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		String temp = (String) map.get("p_errmsg");
		if(temp == null||"".equals(temp)){
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	public int addFp(RYJSFP ryjsfp) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_dwbm", ryjsfp.getDwbm());
		map.put("p_bmbm", ryjsfp.getBmbm());
		map.put("p_jsbm", ryjsfp.getJsbm());
		map.put("p_gh", ryjsfp.getGh());
		map.put("p_zjldgh", ryjsfp.getZjldgh());
		map.put("p_ryxh", ryjsfp.getRyxh());
		map.put("p_errmsg", "");

		try {
			xtglmapper.addFp(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		String temp = (String) map.get("p_errmsg");
		if(temp == null||"".equals(temp)){
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	public int deleteFp(String dwbm, String bmbm, String jsbm, String gh) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_dwbm", dwbm);
		map.put("p_bmbm", bmbm);
		map.put("p_jsbm", jsbm);
		map.put("p_gh", gh);
		map.put("p_errmsg", "");

		try {
			xtglmapper.deleteFp(map);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}

		String temp = (String) map.get("p_errmsg");
		if(temp == null||"".equals(temp)){
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	public RYJSFP createFp(String dwbm, String bmbm,
						   String jsbm, String gh, String zjldgh, int ryxh) {
		RYJSFP ryjsfpObject = new RYJSFP();
		ryjsfpObject.setDwbm(dwbm);
		ryjsfpObject.setBmbm(bmbm);
		ryjsfpObject.setJsbm(jsbm);
		ryjsfpObject.setGh(zjldgh);
		ryjsfpObject.setZjldgh(zjldgh);
		ryjsfpObject.setRyxh(ryxh);
		return ryjsfpObject;
	}

	@Override
	public String getMcByGh(String ssrdwbm, String ssrgh) {
		Map<String ,Object> map =new HashMap<String,Object>();
		map.put("p_ssrdwbm", ssrdwbm);
		map.put("p_ssrgh", ssrgh);
		map.put("p_mc", StringUtils.EMPTY);
		map.put("p_errmsg", StringUtils.EMPTY);
		String name = "";

		try {
			name = xtglmapper.selectMcByGh(ssrdwbm, ssrgh);
		} catch (Exception e) {
			LOG.error(StringUtils.EMPTY, e);
		}
		return name;
	}

	@Override
	public Map<String, Object> getJsByDwbm(String dwbm, Integer page, Integer rows) {

		Map<String, Object> map = new HashMap<>();
		List<JSBM> js = new ArrayList<>();
		Page pager = PageHelper.startPage(page, rows);
		try {
			js = xtglmapper.getJsByDwbm(dwbm);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(StringUtils.EMPTY, e);
		}
		map.put("total", pager.getTotal());
		map.put("rows", js);
		return map;
	}

	@Override
	public int suspension(String dwbm, String gh) {
		int res = 0;
		try {
			res = xtglmapper.suspension(dwbm,gh);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public int aliasVerify(String dlbm, String dwbm) {
		int res = 0;
		try {
			res = xtglmapper.aliasVerify(dlbm, dwbm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public int deptNameVerify(String bmmc, String dwbm) {
		int res = 0;
		try {
			res = xtglmapper.deptNameVerify(bmmc, dwbm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}


}
