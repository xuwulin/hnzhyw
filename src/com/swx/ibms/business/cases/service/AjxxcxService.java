package com.swx.ibms.business.cases.service;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Map;

/**
*<p>Title:AjxxcxService</p>
*<p>Description:案件信息查询 </p>
*author 朱春雨
*date 2017年3月17日 下午2:04:33
*/
@SuppressWarnings("all")
public interface AjxxcxService {
	public Map selectlistAjxxcx(String p_year,String p_bmsah,String p_cbdw_bm,int page);

	/**
	 * 查询业务考核模块-乡镇检查-案件
	 * @param dwbm 单位编码
	 * @param kssj 开始时间
	 * @param jssj 结束时间
	 * @param ajlbbmstr 案件类别编码
	 * @param type 类型
	 * @param page 当前页
	 * @param pageSize 每页显示数
	 * @return 结果集
	 */
	Map<String,Object> getYwkhXzjcAj(String dwbm,String kssj,String jssj,String ajlbbmstr,String type,Integer page,Integer pageSize);

	/**
	 *
	 * 查询业务考核模块-未成年人刑事检察工作、侦查监督工作-案件
	 * @param dwbm 单位编码
	 * @param kssj 开始时间
	 * @param jssj 结束时间
	 * @param ajlbbmstr 案件类别编码
	 * @param type 类型
	 * @param page 当前页
	 * @param pageSize 每页显示数
	 * @return 结果集
	 */
	Map<String,Object> getYwkhLajdAj(String dwbm,String kssj,String jssj,String ajlbbmstr,String type,Integer page,Integer pageSize);

	/**
	 * 查询业务考核模块-刑事申诉检察工作（分市院）-案件
	 * @param dwbm 单位编码
	 * @param kssj 开始时间
	 * @param jssj 结束时间
	 * @param ajlbbmstr 案件类别编码
	 * @param type 类型
	 * @param page 当前页
	 * @param pageSize 每页显示数
	 * @return 结果集
	 */
	Map<String,Object> getYwkhXsssjcgzAj(String dwbm,String kssj,String jssj,String ajlbbmstr,String type,Integer page,Integer pageSize);

	/**
	 * 查询业务考核模块-案件管理工作（分市院,县区院）-案件
	 * @param dwbm 单位编码
	 * @param kssj 开始时间
	 * @param jssj 结束时间
	 * @param ajlbbmstr 案件类别编码
	 * @param type 类型
	 * @param page 当前页
	 * @param pageSize 每页显示数
	 * @return 结果集
	 */
	Map<String,Object> getYwkhAjglgzAj(String dwbm,String kssj,String jssj,String ajlbbmstr,String type,Integer page,Integer pageSize);

	/**
	 * 查询业务考核模块-民事行政检察工作（县区院）-案件
	 * @param dwbm 单位编码
	 * @param kssj 开始时间
	 * @param jssj 结束时间
	 * @param ajlbbmstr 案件类别编码
	 * @param type 类型
	 * @return 结果集
	 */
	Map<String,Object> getYwkhMsxzjcgzAj(String dwbm,String kssj,String jssj,String ajlbbmstr,String type,Integer page,Integer pageSize);

	/**
	 * 查询业务考核模块-刑事执行检察工作（要区分出分市院、县区院）-案件
	 * @param dwbm 单位编码
	 * @param kssj 开始时间
	 * @param jssj 结束时间
	 * @param ajlbbmstr 案件类别编码
	 * @param type 类型
	 * @param page 当前页
	 * @param pageSize 每页显示数
	 * @return 结果集
	 */
	Map<String,Object> getYwkhXszxjcgzAj(String dwbm,String kssj,String jssj,String ajlbbmstr,String type,Integer page,Integer pageSize);

	/**
	 *
	 * 查询业务考核模块-公诉工作（分市院、县区院）-案件
	 * @param dwbm 单位编码
	 * @param kssj 开始时间
	 * @param jssj 结束时间
	 * @param ajlbbmstr 案件类别编码
	 * @param type 类型
	 * @param page 当前页
	 * @param pageSize 每页显示数
	 * @return 结果集
	 */
	Map<String,Object> getYwkhGsgzAj(String dwbm,String kssj,String jssj,String ajlbbmstr,String type,Integer page,Integer pageSize);

	/**
	 *
	 * 查询业务考核模块-未成年人刑事检察工作（分市院、县区院）-案件
	 * @param dwbm 单位编码
	 * @param kssj 开始时间
	 * @param jssj 结束时间
	 * @param ajlbbmstr 案件类别编码
	 * @param type 类型
	 * @param page 当前页
	 * @param pageSize 每页显示数
	 * @return 结果集
	 */
	Map<String,Object> getYwkhWcnrxsjcgzAj(String dwbm,String kssj,String jssj,String ajlbbmstr,String type,Integer page,Integer pageSize);


	public Map<String, Object> selectAjbl(String gh, String dwbm, String bmbm, Integer page, Integer pageSize, String kssj, String jssj);

	public Map<String, Object> selectAjblEj(String dwbm, String bmbm, String gh, String ajlbbm, Integer page,
											Integer rows, String kssj,String jssj, String type);

	/**
     * 根据部门受案号查询案件基本信息
     * @author 徐武林
     * @param bmsah 部门受案号
     * @return 查询对象
     * @since 2018年4月30日
     */
    Map<String, Object> selectAjxqByBmsah(String bmsah);

    /**
     * 根据档案部门受案号查询办案人员信息
     * @author 徐武林
     * @param bmsah 部门受案号
     * @return 个人信息结果集
     * @since 2018年4月20日
     */
    List<Map<String, Object>> selectBaryByBmsah(String bmsah, String tysah);

    /**
     * 根据部门受案号查询嫌疑人详情
     * @author 徐武林
     * @param bmsah 部门受案号
     * @return 查询对象
     * @since 2018年4月20日
     */
    List<Map<String, Object>> selectXyrxxByBmsah(String bmsah);

    /**
	 * 添加非案管 案件办理
	 */
	 String addAjbl(String did, String ajmc, String cbdwbm, String cbdwmc, String ajlb, String ajlbbm,
			 		String cbrgh, String cbr, String ajslrq, String dqrq,String bjrq,
			 		String ajwjrq, String aqzy, String cbbmbm,String cbbmmc, String bjqk, String cbryj,String fz) throws Exception;

	/**
	 * 展示非案管 新增案件办理数据
	 * @param did
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> selectALLNewAjbl(String ajId, String did, int page, int pageSize,String ajStatus) throws Exception;

	/**
	 * 删除新增列表中的案件办理
	 * @param id
	 * @return
	 * @throws Exception
	 */
	String deleteAjbl(String ajid,String bmsah) throws Exception;

	/**
	 * 修改案件办理
	 */
	String updateAjbl(String ajId, String ajmc, String ajlb, String ajlbbm,String ajslrq, String dqrq,String bjrq,
			 		  String ajwjrq, String aqzy,String bjqk, String cbryj,String fz) throws Exception;

	/**
	 * 根据部门映射编码查询案件类别
	 * @param ywbm
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> selectAjlbByywbm(List<String> bmys) throws Exception;

	/**
	 * 展示承办小组
	 */
	Map<String, Object> selectCbxz(String daId, int page, int pageSize) throws Exception;

	/**
	 * 添加承办小组
	 */
	String addCbxz(String daId,String dwbm,String gh,String blfs,String blfsbm,String cbxzmc) throws Exception;

	/**
	 * 删除新增小组
	 */
	String deleteCbxz(String id) throws Exception;

	/**
	 * 绑定承办小组到下拉框
	 */
	List<Map<String, Object>> getAllCbxz(String daId) throws Exception;
}
