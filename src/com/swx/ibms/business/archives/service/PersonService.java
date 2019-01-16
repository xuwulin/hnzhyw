package com.swx.ibms.business.archives.service;

import com.swx.ibms.business.archives.bean.Person;
import com.swx.ibms.business.archives.bean.PersonJl;
import com.swx.ibms.business.system.bean.Upload;

import java.util.List;
import java.util.Map;



/**
 *
 * PersionService.java 个人信息service接口
 * @author east
 * @date<p>2017年5月16日</p>
 * @version 1.0
 * @description<p>定义对个人基本信息、个人经历信息、个人附件信息的CRUD</p>
 */
public interface PersonService {

	/**
	 * 根据单位编码、工号查询此人的个人基本信息
	 * 根据经历id以及联合个人基本信息表查询个人经历
	 * 根据附件id以及联合个人基本信息表查询个人头像（附件）的信息
	 * @param dwbm 单位编码
	 * @param gh 工号
	 * @param daId 档案id
	 * @return 查询出的结果集
	 */
	List<Object> selectList(String dwbm,String gh,String daId);

	/**
	 * 新增个人基本信息
	 * @param person 个人基本信息实体类
	 * @return 是否成功的标示 (1 成功 )
	 */
	Map<String,Object> insertGrjbxxData(Person person);

	/**
	 * 新增个人经历信息
	 * @param personjl 个人经历实体类
	 * @return 是否成功的标示 (1 成功 )
	 */
	Map<String,Object> insertGrjlData(PersonJl personjl);

	/**
	 * 新增个人头像信息
	 * @param upload 附件实体类（存放上传文件或头像信息）
	 * @return 上传成功后的头像（附件）id、是否成功的标示
	 */
	Map<String,Object> insertGrFlData(Upload upload);


	/**
	 * 根据个人基本信息id修改个人基本信息
	 * @param person 个人基本信息实体类
	 * @return 是否成功的标示 (1 成功 )
	 */
	String updateGrjbxxData(Person person);


	/**
	 * 根据个人经历id修改个人经历信息
	 * @param personJl 个人经历信息实体类
	 * @return 是否成功的标示 (1 成功 )
	 */
	String updateGrjlData(PersonJl personJl);

	/**
	 * 根据附件id修改个人附件信息
	 * @param upload 附件实体类
	 * @return 是否成功的标示 (1 成功 )
	 */
	String updateFjData(Upload upload);

	/**
	 * 根据个人基本信息id删除个人基本信息
	 * @param person 个人基本信息实体类
	 * @return 是否成功的标示 (1 成功 )
	 */
	String deleteGrjbxxData(Person person);

	/**
	 * 根据个人经历信息id删除个人经历信息
	 * @param personJl 个人经历信息实体类
	 * @return 是否成功的标示 (1 成功 )
	 */
	String deleteGrjlData(PersonJl personJl);

	/**
	 * 根据单位编码、工号从yx_sfda_grjbxx表中获取最新档案信息，用于创建新档案时赋值
	 * @param dwbm
	 * @param gh
	 * @return
	 */
	Map<String,Object> selectInfo(String dwbm,String gh);

	/**
	 * 根据查询出的最近的个人基本信息的id和da_id去查询对应的工作经历和教育经历
	 * @param daid
	 * @param grjbxxId
	 * @return
	 */
	Map<String,Object> selectExperience(String daid,String grjbxxId);

	Integer modifySfdaGrjbxxZpById(String zp,String zpName,String id);

}
