package com.swx.ibms.business.archives.service;

import com.swx.ibms.business.archives.bean.Dahcsl;
import com.swx.ibms.business.archives.bean.Hcdafl;
import com.swx.ibms.business.archives.bean.Hcwtfl;

import java.util.List;



/**
 * @author zsq
 * 
 */
public interface DahcService {

	/**
	 * @return 核查档案分类
	 */
	List<Hcdafl> getHcdafl();

	/**
	 * @return  核查问题分类
	 */
	List<Hcwtfl> getHcwtfl();

	/**
	 * @param hcsl 档案核查实例
	 * @return id
	 */
	String addDahcsl(Dahcsl hcsl);

	/**
	 * @param dwbm 单位编码
	 * @return  Hcdafl
	 */
	List<Dahcsl> dahcDbyw(String dwbm);

	/**
	 * @param id id
	 * @return message
	 */
	List<Dahcsl> getDbyw(String id);


	/**
	 * @param dagzid  dagzid
	 */
	void deleteDahcsl(String dagzid);

	/** 
	 * @param gh 工号
	 * @param dwbm 单位编码
	 * @return 根据工号查询个人的档案核查代办业务
	 */
	List<Dahcsl> grdahcdbyw(String dwbm, String gh);

	/** 
	 * @param dagzid dagzid
	 */
	void complete(String dagzid);

	/**
	 * @param dagzid dagzid
	 */
	void dahcupdate(String dagzid);

	/**
	 * @param dagzid dagzid
	 * @return 状态
	 */
	int daspzt(String dagzid);

	/**
	 * @param dagzid dagzid
	 * @return 是否公示
	 */
	int sfgs(String dagzid);

	/** 
	 * @param wbid wbid
	 * @return 是否有附件
	 */
	int getfj(String wbid);
	/** 
	 * @param wbid 档案归总id
	 * @return 是否可以再次核查
	 */
	int sfzzhc(String wbid);

	/** 
	 * @param wbid 档案归总id
	 * @return 是否过了核查期限
	 */
	int sfgq(String wbid);
}
