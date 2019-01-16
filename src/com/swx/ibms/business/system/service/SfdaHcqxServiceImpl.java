package com.swx.ibms.business.system.service;

import com.swx.ibms.business.system.bean.Hcqx;
import com.swx.ibms.business.system.mapper.SfdaHcqxMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author zsq
 *
 */
@Service
public class SfdaHcqxServiceImpl implements SfdaHcqxService{

	/**
	 * 
	 */
	@Autowired
	private SfdaHcqxMapper sfdaHcqxMapper;

	@Override
	public List<Hcqx> getHcqx() {
		List<Hcqx> hcqx = null;
		try{
			hcqx = sfdaHcqxMapper.getHcqx();
		return hcqx;
		}catch(Exception e){
			return hcqx;
		}
	}

	@Override
	public boolean setHcqx(int hcqx, String gxr) {
		try{
			sfdaHcqxMapper.setHcqx(hcqx,gxr);
			return true;
			}catch(Exception e){
				return false;
			}
	}

	@Override
	public boolean setGsqx(int gsqx, String gxr) {
		try{
			sfdaHcqxMapper.setGsqx(gsqx,gxr);
			return true;
			}catch(Exception e){
				return false;
			}
	}
	
	

}
