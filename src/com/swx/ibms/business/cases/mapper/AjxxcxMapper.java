package com.swx.ibms.business.cases.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


/**
*<p>Title:AjxxcxMapper</p>
*<p>Description: 案件信息查询</p>
*author 朱春雨
*date 2017年3月17日 上午10:23:00
*/
public interface AjxxcxMapper {
	/**
	 * 案件信息查询
	 * @param map
	 */
	void selectlistAjxxcx(Map<String,Object> map);

	/**
	 * 查询业务考核模块-乡镇检查-案件
	 * @param map
	 */
	void getYwkhXzjcAj(Map<String,Object> map);

	/**
	 * 查询业务考核模块-侦查监督工作-案件
	 * @param map
	 */
	void getYwkhLajdAj(Map<String,Object> map);

	/**
	 * 查询业务考核模块-刑事申诉检察工作（分市院）-案件
	 * @param map
	 */
	void getYwkhXsssjcgzAj(Map<String,Object> map);

	/**
	 * 查询业务考核模块-案件管理工作（分市院,县区院）-案件
	 * @param map
	 */
	void getYwkhAjglgzAj(Map<String,Object> map);

	/**
	 * 查询业务考核模块-民事行政检察工作（县区院）-案件
	 * @param map
	 */
	void getYwkhMsxzjcgzAj(Map<String,Object> map);

	/**
	 * 查询业务考核模块-刑事执行检察工作（要区分分市院、县区院）-案件
	 * @param map
	 */
	void getYwkhXszxjcgzAj(Map<String,Object> map);

	/**
	 * 查询业务考核模块-公诉工作（分市院、县区院）-案件
	 * @param map
	 */
	void getYwkhGsgzAj(Map<String,Object> map);

	/**
	 * 查询业务考核模块-未成年人刑事检察工作（分市院、县区院）-案件
	 * @param map
	 */
	void getYwkhWcnrxsjcgzAj(Map<String,Object> map);

	List<String> selectAllBadybm(@Param("dwbm") String dwbm,
								 @Param("gh") String gh);

	List<Map<String, String>> selectbyAjbllxAll(@Param("gh") String gh,
												@Param("dwbm") String dwbm);

	List<Map<String, String>> selectbyAjblbm(@Param("gh") String gh,
											 @Param("dwbm") String dwbm);

	List<String> selectByAjyjGh(@Param("dwbm") String dwbm,
								@Param("bmbm") String bmbm);

	// 受理案件总数
	List<Map<String, Object>> selectCountsOfSlaj(@Param("dwbm") String dwbm,
												 @Param("gh") String gh,
												 @Param("kssj") String kssj,
												 @Param("jssj") String jssj);


	// 查询办结案件总数
	List<Map<String, Object>> selectCountsOfBjaj(@Param("dwbm") String dwbm,
												 @Param("gh") String gh,
												 @Param("kssj") String kssj,
												 @Param("jssj") String jssj);

	// 个人办案效率，平均办理时间
	List<Map<String, Object>> selectAvgTimeOfPer(@Param("dwbm") String dwbm,
												 @Param("gh") String gh,
												 @Param("ajlbBmsAll") List<String> ajlbBmsAll,
												 @Param("kssj") String kssj,
												 @Param("jssj") String jssj);

	List<Map<String, Object>> selectByAjblYjXzsl(@Param("dwbm") String dwbm,
												 @Param("gh") String gh,
												 @Param("kssj") String kssj,
												 @Param("jssj") String jssj);

	List<Map<String, Object>> selectbyAjblYjslAll(@Param("gh") String gh,
												  @Param("dwbm") String dwbm,
												  @Param("badybmlistAll") List<String> badybmlistAll,
												  @Param("kssj") String kssj,
												  @Param("jssj") String jssj);

	//本部门办案效率指标，查询“某个部门”下所有单个案件类型的平均办理时间，最长时间，最短时间（只管部门，不区分人）
	List<Map<String, Object>> selectEfficiencyOfDep(@Param("ajyjGhList") List<String> ajyjGhList,
												 	@Param("dwbm") String dwbm,
													@Param("kssj") String kssj,
													@Param("jssj") String jssj);

	//本部门办案效率指标,查询某个人单个案件类型的“平均办理时间在本部门下的排名”（区分人和部门）
	List<Map<String, Object>> selectAvgTimeRankOfDep(@Param("ajyjGhList") List<String> ajyjGhList,
													 @Param("dwbm") String dwbm,
													 @Param("gh") String gh,
													 @Param("kssj") String kssj,
													 @Param("jssj") String jssj);

	// 本部门办案数量指标，查询“某个部门”下所有 单个案件类型 的平均办案数量（该时间段内受理的案件数）（只管部门，不区分人）
	List<Map<String, Object>> selectAvgCountOfDep(@Param("ajyjGhList") List<String> ajyjGhList,
											      @Param("bmbm") String bmbm,
											      @Param("dwbm") String dwbm,
											      @Param("kssj") String kssj,
												  @Param("jssj") String jssj);

	// 本部门办案数量指标，查询“某个部门”下所有 单个案件类型 的最多办案量，最少办案量（注：只统计参与该类型案件办理的人）
	List<Map<String, Object>> selectCountsOfDep(@Param("ajyjGhList") List<String> ajyjGhList,
												@Param("dwbm") String dwbm,
												@Param("kssj") String kssj,
												@Param("jssj") String jssj);

	//本部门办案数量指标，查询“某个部门”下所有 单个案件类型 办案数量排名（该时间段内受理的案件数）（区分人和部门）
	List<Map<String, Object>> selectCountsRankOfDep(@Param("ajyjGhList") List<String> ajyjGhList,
												  	@Param("dwbm") String dwbm,
												  	@Param("gh") String gh,
													@Param("kssj") String kssj,
													@Param("jssj") String jssj);

	List<Map<String, Object>> selectbyAjblYjfssl(@Param("gh") String gh,
											     @Param("dwbm") String dwbm,
											     @Param("kssj") String kssj,
												 @Param("jssj") String jssj);

	List<Map<String, Object>> getAjblSPSHCountByDwbmAndGh(@Param("dwbm") String dwbm,
														  @Param("gh") String gh,
														  @Param("kssj") String kssj,
														  @Param("jssj") String jssj);

	List<Map<String, String>> selectbyAjblEjZrsah(@Param("dwbm") String dwbm,
												  @Param("gh") String gh,
												  @Param("ajlbbm") String ajlbbm,
												  @Param("kssj") String kssj,
												  @Param("jssj") String jssj);

	List<Map<String, String>> selectbyAjblEjJcgsah(@Param("dwbm") String dwbm,
												  @Param("gh") String gh,
												  @Param("ajlbbm") String ajlbbm,
												  @Param("kssj") String kssj,
												  @Param("jssj") String jssj);

	List<Map<String, String>> selectbyAjblEjDrsah(@Param("dwbm") String dwbm,
												  @Param("gh") String gh,
												  @Param("ajlbbm") String ajlbbm,
												  @Param("kssj") String kssj,
												  @Param("jssj") String jssj);

	/**
	 * 办结案件部门受案号
	 * @param dwbm
	 * @param gh
	 * @param ajlbbm
	 * @param kssj
	 * @param jssj
	 * @return
	 */
	List<Map<String, String>> selectbyAllBmsah(@Param("dwbm") String dwbm,
											   @Param("gh") String gh,
											   @Param("ajlbbm") String ajlbbm,
											   @Param("kssj") String kssj,
											   @Param("jssj") String jssj,
											   @Param("sort") String sort,
											   @Param("order") String order,
											   @Param("ajmc") String ajmc);

	/**
	 * 受理案件部门受案号
	 * @param dwbm
	 * @param gh
	 * @param ajlbbm
	 * @param kssj
	 * @param jssj
	 * @return
	 */
	List<Map<String, String>> selectbyAllBmsahOfSl(@Param("dwbm") String dwbm,
												   @Param("gh") String gh,
												   @Param("ajlbbm") String ajlbbm,
												   @Param("kssj") String kssj,
												   @Param("jssj") String jssj,
												   @Param("sort") String sort,
												   @Param("order") String order,
												   @Param("ajmc") String ajmc);

	List<Map<String, String>> selectbyAjblEjSYsah(@Param("dwbm") String dwbm,
												  @Param("gh") String gh,
												  @Param("ajlbbm") String ajlbbm,
												  @Param("blfsbm") String blfsbm,
												  @Param("kssj") String kssj,
												  @Param("jssj") String jssj);

	List<Map<String, Object>> selectTablenameAndField(@Param("ajlbBmsAll") List<String> ajlbBmsAll);

	List<Map<String, Object>> getAllEjblfs(@Param("sahlist") List<String> sahlist,
										   @Param("dwbm") String dwbm,
										   @Param("gh") String gh);

	List<Map<String, Object>> selectbyAjblEjsj(@Param("gh") String gh,
											   @Param("dwbm") String dwbm,
										   	   @Param("sahlist") List<String> sahlist,
											   @Param("sort") String sort,
											   @Param("order") String order);

	List<Map<String, Object>> selectbyAjblEjsl(@Param("sahlist") List<String> sahlist);

	List<Map<String, Object>> countFjAndWS(@Param("sahlist") List<String> sahlist);

	/**
     * 根据部门受案号查询案件基本信息
     * @param bmsah
     * @return 查询对象
     * @since 2018年4月20日
     */
    Map<String, Object> selectAjxqByBmsah(@Param("bmsah")String bmsah);

    /**
     * 根据-部门受案号-查询 办案单元 编码
     * @param bmsah 部门受案号
     * @return 个人信息结果集
     * @since 2018年4月20日
     */
    List<Map<String, String>> selectBadyByBmsah(@Param("bmsah") String bmsah);

    /**
     * 根据-办案单元编码-查询 办案人员
     * @param
     * @return 个人信息结果集
     * @since 2018年4月20日
     */
    List<Map<String, Object>> selectBaryByBadybm(@Param("badybm")List<String> badybm);

    /**
     * 根据-部门受案号-查询承办人员
     * @param bmsah 部门受案号
     * @return 个人信息结果集
     * @since 2018年4月20日
     */
    List<Map<String, Object>> selectCbryByBmsah(@Param("bmsah") String bmsah);


	/**
	 * 根据部门受案号和统一受案号查询协同办案人员（根据文书查询yx_ws_sl_tyyw表）
	 * @param bmsah
	 * @param tysah
	 * @return
	 */
    List<Map<String, Object>> selectXtbaryByBmsahAndTysah(@Param("bmsah") String bmsah,
												  		  @Param("tysah") String tysah);

    List<Map<String, Object>> selectAgryByDwbm(@Param("dwbm") String dwbm);

    /**
     * 根据部门受案号查询嫌疑人详情
     *
     * @param bmsah 部门受案号
     * @return 查询对象
     * @since 2018年4月20日
     */
    List<Map<String, Object>> selectXyrxxByBmsah(@Param("bmsah")String bmsah);


    /**
	 * 新增 案件办理 yx_sfda_ajxx表
	 * @param map
	 */
	int addAjblToAjxx(Map<String,Object> map) throws Exception;

    /**
     * 根据档案id查询新增列表中的数据
     * YX_SFDA_AJXX表
     * @param did
     * @return
     */
	List<Map<String, Object>> selectALLNewAjbl(@Param("ajId")String ajId,
                                               @Param("did") String did,
                                               @Param("ajStatus")String ajStatus);

	/**
	 * 根据案件id删除新增列表中的非案管办案 ajxx表
	 */
	int deleteAjxx(Map<String,Object> map) throws Exception;

	/**
	 * 删除对应的附件
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int deleteAjblFj(Map<String,Object> map) throws Exception;

	/**
	 * 根据部门受案号修改案件办理 ajxx表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int updateAjxxByBmsah(Map<String,Object> map) throws Exception;

	/**
	 * 根据业务编码获取案件类别编码
	 * @param ywbm
	 * @return
	 */
	List<Map<String, Object>> selectAjlbByywbm(@Param("ywbm")List<String> ywbm);

	/**
	 * 根据档案id，单位编码，工号，办理方式编码查询承办小组
	 * @param daId
	 * @throws Exception
	 */
	List<Map<String, Object>> selectCbxz(@Param("daId") String daId) throws Exception;

	/**
	 * 增加承办小组
	 * @param map
	 * @throws Exception
	 */
	int addCbxz(Map<String,Object> map) throws Exception;

	/**
	 * 删除承办小组
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int deleteCbxz(Map<String,Object> map) throws Exception;

	/**
	 * 根据档案id查询承办小组
	 * @param daId
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getCbxz(@Param("daId") String daId) throws Exception;

	/**
	 * 根据档案id查询yx_sfda_ajxx表中的已审批的案件数量
	 * @param daId
	 * @return
	 * @throws Exception
	 */
	int getAjslOfAjxx(@Param("daId") String daId) throws Exception;

    List<Map<String, String>> getAllAjlbbm();

	Map<String, Object> getAjsByBmsahs(@Param("bmsah") String bmsah) throws Exception;
}
