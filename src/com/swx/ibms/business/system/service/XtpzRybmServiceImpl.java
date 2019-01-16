package com.swx.ibms.business.system.service;

import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.rbac.bean.RYJSFP;
import com.swx.ibms.business.system.mapper.XtpzRybmMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * @author zsq
 * 系统配置人员管理
 *
 */
@Service
public class XtpzRybmServiceImpl implements XtpzRybmService {
	
	/**
	 * 系统配置人员管理
	 */
	@Autowired 
	private XtpzRybmMapper xtpzRybmMapper;

	@Override
	public void insertRy(RYBM rybm, RYJSFP ryjsfp, String oldbmbm, String oldjsbm) {
		if("".equals(rybm.getGh())||null==rybm.getGh()){		//如果没有工号则新增
			
			RYBM maxGhAndGzzh = xtpzRybmMapper.getMax(rybm.getDwbm());
			int maxGh;
			int maxGzzh;
			if(null == maxGhAndGzzh){
				maxGh = 1000;
				maxGzzh = 1000;
			}else{
				maxGh = Integer.parseInt(maxGhAndGzzh.getGh());
				maxGzzh = Integer.parseInt(maxGhAndGzzh.getGzzh());
			}
			maxGh++;
			maxGzzh++;
			rybm.setKl(DigestUtils.md5DigestAsHex("11111111".getBytes()));  //密码默认为11111111
			Integer ghinteger = new Integer(maxGh);
			Integer gzzhinteger = new Integer(maxGzzh);
			rybm.setGh(ghinteger.toString());
			rybm.setGzzh(gzzhinteger.toString());
			ryjsfp.setGh(ghinteger.toString());
			xtpzRybmMapper.insertRyfp(ryjsfp);
			xtpzRybmMapper.insert(rybm);
			}else{		//如果有工号就修改
				//rybm.setKl(DigestUtils.md5DigestAsHex(rybm.getKl().getBytes()));
				xtpzRybmMapper.update(rybm);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("dwbm", ryjsfp.getDwbm());
				map.put("bmbm", ryjsfp.getBmbm());
				map.put("jsbm", ryjsfp.getJsbm());
				map.put("gh", ryjsfp.getGh());
				map.put("oldbmbm", oldbmbm);
				map.put("oldjsbm", oldjsbm);
				xtpzRybmMapper.updateRyfp(map);
			}
	}

	@Override
	public int checkDlbm(String dwbm, String dlbm) {
		int i = xtpzRybmMapper.checkDlbm(dwbm,dlbm);
		return i;
	}

	@Override
	public void deletery(String dwbm, String bmbm, String jsbm, String gh) {
		xtpzRybmMapper.deletery(dwbm, bmbm, jsbm, gh);
		xtpzRybmMapper.deleteRybm(dwbm, gh);
	}

	@Override
	public void updatepassword(String dwbm, String gh, String kl) {
		xtpzRybmMapper.updatepassword(dwbm,gh,kl);
	}

	@Override
	public String canupdate(String dwbm, String gh) {
		RYBM rybm  = xtpzRybmMapper.canupdate(dwbm,gh);
		//如果这个人不是综合业务库里面的人，就不能修改密码
		/*if(null == rybm){
			return "tyyw";
		}else{
			String mrkl = DigestUtils.md5DigestAsHex("11111111".getBytes());	//默认密码
			if(mrkl.equals(rybm.getKl())){		//如果口令为默认口令
				return "true";
			}else{
				return "false";
			}
		}*/
		String resultMsg = "zhyw";
		if(Objects.isNull(rybm)){
			resultMsg = "tyyw";
		}
		return resultMsg;
	}


}
