package com.swx.ibms.business.archives.service;

import com.swx.ibms.business.archives.bean.Dahcsl;
import com.swx.ibms.business.archives.bean.Hcdafl;
import com.swx.ibms.business.archives.bean.Hcwtfl;
import com.swx.ibms.business.archives.mapper.DahcMapper;
import com.swx.ibms.business.common.bean.Splcsl;
import com.swx.ibms.business.system.bean.Hcqx;
import com.swx.ibms.business.system.mapper.SfdaHcqxMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * @author zsq
 * 
 */
@Service
public class DahcServiceImpl implements DahcService{

	
	/**
	 * 
	 */
	@Resource
	private DahcMapper dahcMapper;
	/**
	 * 
	 */
	@Resource
	private SfdaHcqxMapper sfdaHcqxMapper;

	@Override
	public List<Hcdafl> getHcdafl() {
		return dahcMapper.getHcdafl();
	}

	@Override
	public List<Hcwtfl> getHcwtfl() {
		return dahcMapper.getHcwtfl();
	}

	@Override
	public String addDahcsl(Dahcsl hcsl) {
		String id = UUID.randomUUID().toString().replace("-", "");
		hcsl.setId(id);
		try{
			dahcMapper.addDahcsl(hcsl);
		}catch(Exception e){
			return "";
		}
		return id;
	}

	@Override
	public List<Dahcsl> dahcDbyw(String dwbm) {
		List<Dahcsl> list = dahcMapper.dahcDbyw(dwbm);
		return list;
	}

	@Override
	public List<Dahcsl> getDbyw(String id) {
		List<Dahcsl> list = dahcMapper.getDbyw(id);
		return list;
	}

	@Override
	public void deleteDahcsl(String dagzid) {
		dahcMapper.deleteDahcsl(dagzid);
	}

	@Override
	public List<Dahcsl> grdahcdbyw(String dwbm, String gh) {
		return dahcMapper.grdahcdbyw(dwbm,gh);
	}

	@Override
	public void complete(String dagzid) {
		dahcMapper.complete(dagzid);
	}

	@Override
	public void dahcupdate(String dagzid) {
		dahcMapper.dahcupdate(dagzid);
		dahcMapper.updateRyjn(dagzid);
	}

	@Override
	public int daspzt(String dagzid) {
		String string = dahcMapper.daspzt(dagzid);
		if(null == string){
			return 0;
		}else{
			return Integer.parseInt(string);
		}
	}

	@Override
	public int sfgs(String dagzid) {
		String string = dahcMapper.sfgs(dagzid);
		if(null == string){
			return 0;
		}else{
			return Integer.parseInt(string);
		}
	}

	@Override
	public int getfj(String wbid) {
		return dahcMapper.getfj(wbid);
	}

	@Override
	public int sfzzhc(String wbid) {
		
		Splcsl sp = dahcMapper.sfzzhc(wbid);
		if(null == sp){
			return 1;
		}
		if("5".equals(sp.getSpzt())){
			return 1;
		}
		return 0;
	}

	@Override
	public int sfgq(String wbid) {
		try{
			Splcsl sp = dahcMapper.sfgq(wbid);
			if(null == sp){
				return 1;
			}
			Date spDate = sp.getSpsj();
			List<Hcqx> list = sfdaHcqxMapper.getHcqx();
			int hcqx = 0;
			for(Hcqx hc : list){
				if(hc.getQxlx() == 1){
					hcqx =hc.getHcqx(); 
				}
			}
			spDate = DateUtils.addDays(spDate, hcqx);
			int i = spDate.compareTo(new Date());
			if(i == 1){
				return 1;
			}else{
				return 0;
			}
		}catch(Exception e){
			return 0;
		}
	}
	
	
}
