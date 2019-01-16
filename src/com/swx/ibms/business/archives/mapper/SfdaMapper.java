package com.swx.ibms.business.archives.mapper;

import com.swx.ibms.business.archives.bean.Sfda;
import com.swx.ibms.business.archives.bean.XtSfdaDaQuery;

import java.util.List;
import java.util.Map;



/**
 * 司法档案内容Mapper接口
 * @author 李佳
 * @date: 2017年02月25日
 */
public interface SfdaMapper {

	/**
	 * 根据档案类型和归档id查询司法档案数量
	 * @param map map对象
	 */
	void selectGdidSfdaCount(Map<String,Object> map);

	/**
	 * 新增司法技能
	 * @param map map对象
	 * @return 是否添加成功 1 = 成功, 0 = 不成功
	 */

	 int insert(Map<String,Object> map);
	 /**
	  * 修改司法技能
	  * @param map map对象
	  * @return 是否修改成功 1 = 成功, 0 = 不成功
	  */
	 int update(Map<String,Object> map);


	 /**
      * 展示司法技能
	  * @param map map对象
	  * @return List<Map<String, Object>>
	 */
	 List<Sfda> getSfjnList(Map<String,Object> map);

	 /**
	  * 删除司法技能
	  * @param map map对象
	  * @return 是否删除成功 1 = 成功, 0 = 不成功
	  */
	 int delete(Map<String,Object> map);
	  /**
      * 司法档案统计
	  * @param map map对象
	  */
	 void countSfda(Map<String, Object> map);

	 /**
	  * 查询某单位某年的档案数量（柱状图的显示数据）
	  * @param map 参数：单位编码、部门编码
	  * @exception Exception 异常
	  */
	 void getSfdaCountByDwOrBm(Map<String, Object> map) throws Exception;

	 /**
	  * 查询某单位某年的司法责任、荣誉技能数量
	  * @param map 参数:年份
	  * @throws Exception 异常
	  */
	 void getRyjnSfzrCount(Map<String, Object> map) throws Exception;

	 /**
	  * 查询某人的角色、部门等信息
	  * @param map 单位编码、工号
	  * @throws Exception 异常
	  */
	 void getRyxxBydwAndGh(Map<String,Object> map) throws Exception;

	 /**
	  * 查询某人的文化程度
	  * @param map 单位编码、工号
	  * @throws Exception 异常
	  */
	 void getRyWhcdByDwGh(Map<String,Object> map) throws Exception;

	 public List<Map<String, Object>> selectOldData(XtSfdaDaQuery query);
}
