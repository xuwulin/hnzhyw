package com.swx.ibms.business.archives.mapper;

import com.swx.ibms.business.archives.bean.DAGZ;
import com.swx.ibms.business.archives.bean.Gsjl;
import com.swx.ibms.business.system.bean.Fgld;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;



/**
 * @author 李佳
 *
 */
public interface JcgSfdaCxMapper {

	/**
	 * @param map 通过spstid、splx来查询档案最新spzt
	 */
	void daspZxSpzt(Map<String, Object> map);
	/**
	 * @param map 档案归总封存
	 */
	void dagzFc(Map<String, Object> map);
	/**
	 * @param map 通过单位和部门找部门映射
	 */
	void selectBmBmys(Map<String, Object> map);
	/**
	 * @param map 通过部门映射和单位编码找部门编码
	 */
	void selectBmysBm(Map<String, Object> map);
	/**
	 * 查询自己创建的gdid
	 *
	 * @param map
	 *
	 *
	 */
	void jcgSfdaCx(Map<String, Object> map);

	/**
	 * 查询自己的gdid
	 *
	 * @param map
	 *
	 *
	 */
//	void jcgSfdaCxBs(Map<String, Object> map);

	/**
	 * 发起公示
	 *
	 * @param map
	 *
	 *
	 */
	void gs(Map<String, Object> map);

	/**
	 * 取消公示
	 * @param map 参数map
	 */
	void qxgs(Map<String,Object> map);

	/**
	 * 朱春雨
	 * 查询公示 list
	 */
	void show_gs(Map<String,Object> map);

	/**
	 * 展示首页的公示 2018/12/14
	 * @param dwbm
	 * @return
	 */
	List<Map<String, Object>> showGsOfIndex(@Param("dwbm") String dwbm);


	/**
	 * @param gsjl 公示记录
	 */
	void addGsjl(Gsjl gsjl);
	/**
	 * @param dagzid 档案归总id
	 * @param gsxx 公示信息
	 */
	void updatedagzgsxx(@Param("dagzid") String dagzid,
						@Param("gszt") String gszt,
						@Param("gsxx") String gsxx);

	/**
	 * 将公示信息加入公示栏
	 * @param map 参数map
	 */
	void addGstoGsl(Map<String,Object> map);

	/**
	 * 将公司信息从公示栏删除
	 * @param map 参数map
	 */
	void deleteGsfromGsl(Map<String,Object> map);

	/**
	 * @param dlrdwbm 登录人单位编码
	 * @param dlr	登录人工号
	 * @return List<Fgld>
	 */
	List<Fgld> cxFgBm(@Param("dlrdwbm") String dlrdwbm, @Param("dlr") String dlr);

	/**
	 * 通过dagzid更新公示截止时间
	 * @param  spstid dagzid
	 * @param  gsJzsj 公示截止时间
	 */
	void updateGsJzsj(@Param("spstid") String spstid ,  @Param("gsJzsj") Date gsJzsj);

	/**
	 * 通过单位编码、工号、开始时间、结束时间、是否公示查询检察官的档案信息
	 * @return 检察官档案结果集
	 */
	List<DAGZ> getSfdaByDaParams(@Param("dwbm") String dwbm,
								 @Param("gh") String gh,
								 @Param("sffc") String sffc,
								 @Param("sfgs") String sfgs,
								 @Param("kssj") String kssj,
								 @Param("jssj") String jssj
	);

	/**
	 * 根据档案id查询审批状态
	 * @param daid 档案id
	 * @return 审批表的结果集【单条】
	 */
//	Map<String,Object> getSfdaSpztByDaId(@Param("daid") String daid);

	/**
	 * 根据单位编码，工号查询个人档案，新建，已公示，已归档
	 */
	void getDaByDG(Map<String, Object> map);

	DAGZ getFileInfo(@Param("dwbm") String dwbm,
					 @Param("gh") String gh,
					 @Param("kssj") String kssj,
					 @Param("jssj") String jssj,
					 @Param("tjnf") String tjnf);


	List<Map<String, Object>> queryArchivesByCondition(@Param("dwbm") String dwbm,
													   @Param("bmbm") List<String> bmbm,
													   @Param("gh") String gh,
													   @Param("sfgs") String sfgs,
													   @Param("sfgd") String sfgd,
													   @Param("sffc") String sffc,
													   @Param("kssj") String kssj,
													   @Param("jssj") String jssj,
													   @Param("mc") String mc);
}
