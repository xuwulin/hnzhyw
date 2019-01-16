package com.swx.ibms.business.system.service;

import com.swx.ibms.business.rbac.bean.BMBM;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.system.bean.Fgld;
import com.swx.ibms.business.system.mapper.FgldMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;



/**
 * @author zsq
 * 分管领导
 */
@Service
public class FgldServiceImpl implements FgldService {

	/**
	 * 
	 */
	@Resource
	private FgldMapper fgldMapper;
	
	
	@Override
	public List<Fgld> getFgld(String dwbm) {
		List<Fgld> list = null;
		try{
			list = fgldMapper.getFgld(dwbm);
			List<BMBM> bmbms = null;
			StringBuilder bmmc = null;
			List<String> ll = null;
			for(Fgld fgld : list){
				
				bmmc = new StringBuilder();
				String[] bmbmString = fgld.getBmbm().split(",");
				
				ll = new ArrayList<String>();
				for(String bm : bmbmString){
					ll.add(bm);
				}
				
				bmbms = fgldMapper.getBmmc(dwbm,ll);
				for(BMBM bm : bmbms){
					bmmc.append(bm.getBmmc()+" ");
				}
				fgld.setBmmc(bmmc.toString());
			}
			
			return list;
		}catch(Exception e){
			return list;
		}
	}

	@Override
	public List<RYBM> getLd(String dwbm) {
		List<RYBM> list = null;
		try{
			list = fgldMapper.getLd(dwbm);
			//如果根据单位编码部门映射未查找到领导信息则调用下面这个查询【添加分管领导页面的分管领导数据】
			if(list.size()<=0){
				return  fgldMapper.getAllLdByDwBmmc(dwbm);
			}
			return list;
		}catch(Exception e){
			return list;
		}
	}

	@Override
	public List<BMBM> getBm(String dwbm) {
		List<BMBM> list = null;
		try{
			list = fgldMapper.getBm(dwbm);
			return list;
		}catch(Exception e){
			return list;
		}
	}

	@Override
	public int addFgld(String ldgh, String bmbm, String dwbm) {
		try{
			fgldMapper.addFgld(ldgh,bmbm,dwbm);
			return 1;
		}catch(Exception e){
			return 0;
		}
	}

	@Override
	public int updateFgld(String id, String ldgh, String bmbm, String dwbm) {
		try{
			fgldMapper.updateFgld(id,ldgh,bmbm,dwbm);
			return 1;
		}catch(Exception e){
			return 0;
		}
	}

	@Override
	public int delete(String id) {
		try{
			fgldMapper.delete(id);
			return 1;
		}catch(Exception e){
			return 0;
		}
	}
	
}
