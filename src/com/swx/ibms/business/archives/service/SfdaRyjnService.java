package com.swx.ibms.business.archives.service;

import com.swx.ibms.business.archives.bean.SfdaRyjn;

import java.util.Map;



/**
*@author:徐武林
*@date:2018年3月28日下午5:27:49
*@Description:荣誉技能Service接口
*/
@SuppressWarnings("all")
public interface SfdaRyjnService {
	
	/**
	 * 展示荣誉技能
	 * @param ryjn 荣誉技能实体类
	 * @param bottom 分页下限
	 * @param top 分页上限
	 * @return
	 */
	Map getSfdaRyjnPageList(SfdaRyjn ryjn, int bottom, int top);
	
	/**
	 * 添加司法档案下的荣誉技能
	 * @param ryjn 司法技能实体类		
	 * @return 是否添加成功 1 = 成功, 0 = 不成功
	 */
	 String addSfdaRyjn(SfdaRyjn ryjn) throws Exception;
	
	/**
	 * 修改司法档案下的荣誉技能
	 * @param ryjn 司法技能实体类 
	 * @return 成功与失败的字符串，1 ：成功
	 */
	String updateSfdaRyjn(SfdaRyjn ryjn) throws Exception;
	
	/**
	 * 删除司法档案下的荣誉技能
	 * @param ryjn 司法技能实体类 
	 * @return 成功与失败的字符串，1 ：成功
	 */
	String deleteSfdaRyjn(SfdaRyjn ryjn) throws Exception;
	
	String getAllInfoOfProcurator(String daId,String gh,String dwbm,String bmbm,String kssj,String jssj,boolean isag,String flag) throws Exception;

	Map<String, Object> serachByType(String dalx, String daid, String kssj, String jssj, Integer page, Integer rows) throws Exception;
}
