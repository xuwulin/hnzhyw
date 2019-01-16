package com.swx.ibms.business.archives.service;

import com.swx.ibms.business.archives.bean.Gsjl;

import java.util.List;
import java.util.Map;




/**
 * @author 朱春雨
 *司法档案查询
 */
public interface JcgSfdaCxService {
	/**
	 * @param spstid 审批实体id
	 * @return 通过归档id判断splcsl里面是否有待审批
	 */
	String sfyDsp(String spstid);
	/**
	 * @param spstid 审批实体id
	 * @param splx 审批类型
	 * @return 该类型审批是否通过审批
	 */
	boolean dasfsp(String spstid,String splx);
	/**
	 * 通过单位和部门找部门映射
	 * @param dwbm 单位编码
	 * @param bmbm 部门编码
	 * @return bmys以','隔开
	 */
	String selectDwbmBmys(String dwbm,String bmbm);

	/**
	 * 通过部门映射和单位编码找部门编码
	 * @param dwbm 单位编码
	 * @param bmys 部门映射
	 * @return bmbm以','隔开
	 */
	String selectBmysBm(String dwbm,String bmys);
	/**
	 * @param id 档案归总id
	 * @param sffc 是否封存
	 * @return 档案归总封存
	 */
	boolean dagzFc(String id,String sffc);

	/**
	 * @param sfgs 是否公示
	 * @param ssrdwbm 档案所属人单位编码
	 * @param dlrdwbm 登录人单位编码
	 * @param dlr 登录人工号
	 * @param ssrmc 所属人名称
	 * @param qx 查询权限
	 * @param bmbm 部门编码
	 * @param kssj 开始时间
	 * @param jssj 结束时间
	 * @param page 查询此时处于第几页
	 * @return 查询出来的结果
	 */
	Map<String, Object>  jcgSfdaCx(String sfgs,String sffc,String sfgd, String ssrdwbm,String dlrdwbm,String dlr,String ssrmc,String qx,String bmbm,String kssj,
								   String jssj,int page);

	/**
	 * 发起公示
	 * @param map 发起公示传入的map
	 * @return  返回发起公示的返回值
	 */
	Map gs(Map map);

	/**
	 * 取消公示
	 * @param  参数map
	 * @return 执行成功返回1，执行失败返回0
	 */
	int qxgs(Map<String ,Object> paramsmap);
	/**
	 * 展示公示
	 * @param map 展示公示传入的map
	 * @return 返回展示公示的map
	 */
	List showGs(Map map);

	/**
	 * 展示首页的公示信息
	 * @param dwbm
	 * @return
	 */
	Map<String, Object> showGsOfIndex(String dwbm);

	/**
	 * @param gsjl 公示记录
	 */
	void addGsjl(Gsjl gsjl);
	/**
	 * 分管领导通过登录人单位编码和登录人工号查询分管部门
	 * @param dlrdwbm 登录人单位编码
	 * @param dlr 登录人工号
	 * @return bmbm 所管部门的部门编码
	 */
	Map cxFgBm(String dlrdwbm, String dlr);

	Map<String, Object> getDaByDG(String dwbm, String gh, String sfgs, String sffc, String sfgd, String kssj, String jssj);

	Map<String, Object> getFileInfo(String dwbm, String gh, String kssj, String jssj, String tjnf);

	Map<String, Object> queryArchivesByCondition(String loger_dwbm, String loger_gh,
												 String loger_bmbm, List<String> loger_bmyslist,
												 List<String> loger_bmjs, String query_dwbm,
												 String query_sfgs, String query_sfgd, String query_sffc,
												 String query_kssj, String query_jssj,
												 String query_name, Integer page);

}
