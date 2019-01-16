package com.swx.ibms.business.archives.mapper;

import com.swx.ibms.business.archives.bean.Dahcsl;
import com.swx.ibms.business.archives.bean.Hcdafl;
import com.swx.ibms.business.archives.bean.Hcwtfl;
import com.swx.ibms.business.common.bean.Splcsl;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


/**
 * @author zsq
 *
 */
public interface DahcMapper {

	/**
	 * @return 核查档案分类
	 */
	List<Hcdafl> getHcdafl();

	/**
	 * @return 核查问题分类
	 */
	List<Hcwtfl> getHcwtfl();

	/**
	 * @param hcsl 档案核查实例
	 */
	void addDahcsl(Dahcsl hcsl);

	/**
	 * @param dwbm 单位编码
	 * @return Hcdafl
	 */
	List<Dahcsl> dahcDbyw(@Param("dwbm")String dwbm);

	/**
	 * @param id id
	 * @return message
	 */
	List<Dahcsl> getDbyw(@Param("id")String id);

	/**
	 * @param wbid dahcslid
	 * @return 附件路径
	 */
	String getFjlj(@Param("wbid")String wbid);

	/**
	 * @param dagzid dagzid
	 */
	void deleteDahcsl(@Param("dagzid")String dagzid);

	/** 
	 * @param gh 工号
	 * @param dwbm 单位编码
	 * @return 根据工号查询个人的档案核查代办业务
	 */
	List<Dahcsl> grdahcdbyw(@Param("dwbm") String dwbm, @Param("gh") String gh);

	/**
	 * @param dagzid dagzid
	 */
	void complete(@Param("dagzid") String dagzid);

	/**
	 * @param dagzid dagzid
	 */
	void dahcupdate(@Param("dagzid") String dagzid);

	/**
	 * @param dagzid dagzid
	 * @return 状态
	 */
	String daspzt(@Param("dagzid") String dagzid);

	/**
	 * @param dagzid dagzid
	 * @return 是否公示
	 */
	String sfgs(@Param("dagzid") String dagzid);

	/** 
	 * @param wbid wbid
	 * @return 是否有附件
	 */
	int getfj(@Param("wbid") String wbid);

	/** 
	 * @param wbid 档案归总id
	 * @return 是否可以再次核查
	 */
	Splcsl sfzzhc(@Param("wbid") String wbid);

	/**
	 * @param dagzid 档案归总id
	 */
	void updateRyjn(@Param("dagzid") String dagzid);

	/**
	 * @param wbid 档案归总id
	 * @return 是否过期
	 */
	Splcsl sfgq(@Param("wbid")String wbid);

	/**
	 * @param spDate 最后审批时间
	 * @param hcqx 核查期限
	 * @param wbid 档案归总id
	 * @return 是否过了核查期限
	 */
	double getMon(@Param("spDate") Date spDate, @Param("hcqx") int hcqx,@Param("wbid") String wbid);

}
