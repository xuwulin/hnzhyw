package com.swx.ibms.business.archives.mapper;

import com.swx.ibms.business.archives.bean.Person;
import com.swx.ibms.business.archives.bean.PersonJl;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;



/**
 *
 * PersonMapper.java 个人信息mapper
 * @author east
 * @date<p>2017年5月16日</p>
 * @version 1.0
 * @description<p>对个人信息、个人经历、个人照片信息进行crud</p>
 */
public interface PersonMapper {

	/**
	 * 根据单位编码、工号查询此人的个人基本信息
	 * 根据经历id以及联合个人基本信息表查询个人经历
	 * 根据附件id以及联合个人基本信息表查询个人头像（附件）的信息
	 * @param map 参数集合
	 */
	void selectList(Map<String,Object> map);

	/**
	 * 新增个人基本信息
	 * @param map 参数集合
	 */
	void insertGrjbxxData(Map<String,Object> map);

	/**
	 * 新增个人经历信息
	 * @param map 参数集合
	 */
	void insertGrjlData(Map<String,Object> map);

	/**
	 * 新增个人头像信息(附件信息)
	 * @param map 参数集合
	 */
	void insertGrFlData(Map<String,Object> map);

	/**
	 * 根据个人基本信息id修改个人基本信息
	 * @param map 参数集合
	 */
	void updateGrjbxxData(Map<String,Object> map);


	/**
	 * 根据个人经历id修改个人经历信息
	 * @param map 参数集合
	 */
	void updateGrjlData(Map<String,Object> map);

	/**
	 * 根据附件id修改个人附件信息
	 * @param map 参数集合
	 */
	void updateFjData(Map<String,Object> map);

	/**
	 * 根据个人基本信息id删除个人基本信息
	 * @param map 参数集合
	 */
	void deleteGrjbxxData(Map<String,Object> map);

	/**
	 * 根据个人经历信息id删除个人经历信息
	 * @param map 参数集合
	 */
	void deleteGrjlData(Map<String,Object> map);


	/**
	 * 根据档案归总id、个人基本信息id删除个人基本信息表、个人经历表、附件表（个人头像）
	 * @param map 参数集合
	 */
	void deleteAllGrxxById(Map<String,Object> map);

	/**
	 * 根据单位编码、工号从yx_sfda_grjbxx表中获取最新档案信息，用于创建新档案时赋值
	 * @param
	 */
	Person selectInfo(@Param("dwbm") String dwbm, @Param("gh") String gh);

	/**
	 * 根据查询出的最近的个人基本信息的id和da_id去查询对应的工作经历和教育经历
	 * @param daId
	 * @param grjbxxId
	 * @return
	 */
	List<Map<String, Object>> selectExpOfWork(@Param("daId") String daId,@Param("grjbxxId") String grjbxxId);
	List<Map<String, Object>> selectExpOfStudy(@Param("daId") String daId,@Param("grjbxxId") String grjbxxId);

	Integer modifySfdaGrjbxxZpById(@Param("zp") byte[] zp,
								   @Param("zpName") String zpName,
								   @Param("id") String id
	);

	Person selectPerson(@Param("dwbm")String dwbm,
						@Param("gh")String gh,
						@Param("daId")String daId);

	List<PersonJl> selectPersonJlList(@Param("grjbxxId")String grjbxxId);
}
