package com.swx.ibms.business.archives.service;

import com.swx.ibms.business.archives.bean.Sfda;
import com.swx.ibms.business.archives.bean.XtSfdaDaQuery;
import com.swx.ibms.common.bean.EasyUIDatagrid;

import java.util.List;
import java.util.Map;



/**
 * 司法档案内容接口
 * @author 李佳
 * @date: 2017年02月25日
 */
@SuppressWarnings("all")
public interface SfdaService {

	/**
	 * 根据档案类型和归档id查询司法档案数量
	 * @param gdid 归档id
	 * @param dalx 档案类型
	 * @return 司法档案数量
	 */
	int selectGdidSfdaCount(String gdid,String dalx);

	/**
	 * 新增司法技能
	 * @param sfjn 司法技能实体类
	 * @return 是否添加成功 1 = 成功, 0 = 不成功
	 */

	String insertSfjn(Sfda sfjn);


     /**
      * 修改司法技能
	  * @param sfjn 司法技能实体类
	  * @return 是否修改成功 1 = 成功, 0 = 不成功
	  */
	 String updateSfjn(Sfda sfjn);



	  /**
	   * 展示司法技能
	   * @param sfjn 司法技能实体类
	   * @param bottom 分页下限
	   * @param top 分页上限
	   * @return List<Map<String, Object>>
	   */
	Map getSfjnList(Sfda sfjn,int bottom,int top);

	  /**
	   * 删除司法技能
	   * @param sfjn 司法技能实体类
	   * @return 是否删除成功 1 = 成功, 0 = 不成功
	   */
	  String deleteSfjn(Sfda sfjn);

	  /**
	   * 根据归档id查询不同档案类型司法档案数量
	   * @param gdid 归档id
	   * @return Map
	   */
	   Map countSfda(String gdid);


	   /**
	    * 查询某单位某年的档案数量（柱状图的显示数据）
	    * @param dwbm 单位编码
	    * @param gh 工号
	    * @param paramYear 年份
	    * @exception Exception 异常
	    * @return 查询出的档案结果集
	    */
	   List<Map<String,Object>> getSfdaCountByDwOrBm(String dwbm,
			   String gh,String paramYear,String paramDwjb) throws Exception;

	   /**
	    * 查询某单位某年的司法责任、荣誉技能数量
	    * @param dwbm 单位编码
	    * @param bmbm 部门编码
	    * @param paramYear 年份
	    * @return 数量结果集
	    * @throws Exception 异常
	    */
	   List<Map<String,Object>> getRyjnSfzrCount(String dwbm, String gh,
			   String paramYear,String paramDwjb) throws Exception;

	   /**
	    * 查询某人的文化程度
	    * @param dwbm 单位编码
	    * @param gh 工号
	    * @param paramYear 年份
	    * @param paramDwjb 单位级别
	    * @return 文化程度结果集
	    * @throws Exception
	    */
	   List<Map<String,Object>> getRyWhcdByDwGh(String dwbm, String gh,
			   String paramYear,String paramDwjb) throws Exception;

	   public EasyUIDatagrid<Map<String, Object>> selectOldData(XtSfdaDaQuery query) throws Exception ;

}
