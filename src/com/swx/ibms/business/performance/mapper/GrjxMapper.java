package com.swx.ibms.business.performance.mapper;

import java.util.Map;

/**
 * 个人绩效数据交换接口
 * @author 李治鑫
 * @since 2017-5-9
 */
public interface GrjxMapper {
	/**
	 * 插入或更新个人绩效嘴个基础分
	 * @param map 参数map
	 */
	void inOrUpGrjxZgjcf(Map<String, Object> map);
	/**
	 * @param map 参数map
	 */
	void grjxSfSsxt(Map<String, Object> map);
	/**
	 *查询个人绩效最高基础分数据
	 * @param map 参数map
	 */
	void showGrjxZgjcfList(Map<String, Object> map);
	/**
	 * 通过业务类别和单位级别查找指标配置
	 * @param map 参数map
	 */
	void selectgrjxZbpzNr(Map<String, Object> map);
	/**
	 * 根据月度考核id和业务类型查找业务考核分值的zbkpdf
	 * @param map 参数map
	 */
	void selectgrjxZbkpdf(Map<String, Object> map);
	/**
	 * 根据月度考核id和业务类型更新zbkpgl和zbkpdf
	 * @param map 参数map
	 */
	void updateGrjxYwkhfzLf(Map<String, Object> map);
	/**
	 * 根据月度考核年份，季度，业务类别，单位级别，单位编码前两位，找出所有审批通过的人的月度考核id
	 * @param map 参数map
	 */
	void selectGrjxYdkhlist(Map<String, Object> map);
	/**
	 * 通过档案ID获取个人绩效信息
	 * @param map 参数map
	 */
	void getGrjxByDaid(Map<String, Object> map);

	/**
	 * 插入个人绩效
	 * @param map 参数map
	 */
	void addGrjx(Map<String, Object> map);

	/**
	 * 更新个人绩效
	 * @param map 参数map
	 */
	void updateGrjx(Map<String, Object> map);

	/**
	 * 删除个人绩效
	 * @param map 参数map
	 */
	void deleteGrjx(Map<String, Object> map);


	/**
	 * 根据档案ID获取人员所在单位编码和工号
	 * @param map 参数map
	 * */
	void getRyGhByDaid(Map<String, Object> map);

	/**
	 * 根据单位编码和工号获取部门受案号
	 * @param map 参数map
	 * */
	void getBmsahByGh(Map<String, Object> map);

	/**
	 * 公用
	 * @param map 参数map
	 * */
	void checkws(Map<String, Object> map);

	/**
	 * 讯问犯罪嫌疑人
	 * @param map 参数map
	 * */
	void check013(Map<String, Object> map);

	/**
	 * 对于应当听取辩护律师的意见
	 * @param map 参数map
	 * */
	void check016(Map<String, Object> map);

	/**
	 * 案件审查完毕
	 * @param map 参数map
	 * */
	void check017(Map<String, Object> map);

	/**
	 * 审查终结未制作《批准逮捕决定书》
	 * @param map 参数map
	 * */
	void check021(Map<String, Object> map);


	/**
	 * 批准（不批准）逮捕案件
	 * @param map 参数map
	 * */
	void check023(Map<String, Object> map);

	/**
	 * 应当立案而未立案的
	 * @param map 参数map
	 * */
	void check024(Map<String, Object> map);

	/**
	 * 不应当立案而立案的
	 * @param map 参数map
	 * */
	void check025(Map<String, Object> map);

	/**
	 * 对于不批准逮捕案件
	 * @param map 参数map
	 * */
	void check028(Map<String, Object> map);
	
}
