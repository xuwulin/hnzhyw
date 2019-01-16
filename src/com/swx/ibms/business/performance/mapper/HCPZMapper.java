/**
 * 
 */
package com.swx.ibms.business.performance.mapper;

import java.util.Map;

/**
 * 核查配置,交叉审查权限
 * @author 封泽超
 *@since 2017年4月10日
 */
public interface HCPZMapper {
	/**
	 * 添加临时授权
	 * @param map 
	 */
	void insert(Map<String, Object> map);

	/**
	 * 根据单位编码和部门编码查询核查配置
	 * @param map
	 */
	void select(Map<String, Object> map);
	/**
	 * 根据单位编码和类型查询授权
	 * @param map
	 */
	void selectdwbmywlx(Map<String, Object> map);

	/**
	 * 删除临时授权
	 * @param map
	 */
	void delete(Map<String, String> map);

	/**
	 * 修改临时授权
	 * @param map
	 */
	void update(Map<String, Object> map);

	/**
	 * 通过业务类型得到部门编码
	 * @param map
	 */
	void getbmlbbyywlx(Map<String, Object> map);
	/**
	 * 通过业务类 时间型得到部门编码
	 * @param map
	 */
	void selectdwbmywlxsj(Map<String, Object> map);

	/**
	 * 通过单位编码和部门编码确定一个hcpz
	 * @param map
	 */
	void selectone(Map<String, Object> map);

	/**
	 * 得到业务类型列表
	 * @param map
	 */
	void getywlxlist(Map<String, Object> map);

	/**
	 * 得到所有核查配置
	 * @param map
	 */
	void getAllhcpz(Map<String, Object> map);

	/**
	 * 得到hcpz数量
	 * @param map
	 */
	void gethcpzcount(Map<String, Object> map);
	/**
	 * spstid 得到 年度 季度
	 * @param map
	 */
	void getndjd(Map<String, String> map);
	/**
	 * 通过单位编码得到单位级别
	 * @param map
	 */
	void getdwjb(Map<String, String> map);

	/**
	 * 通过单位编码 和 审批实体id 判断该节点类型
	 * @param map
	 */
	void getisjc(Map<String, String> map);
}
