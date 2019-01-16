package com.swx.ibms.business.common.mapper;

import com.swx.ibms.business.system.bean.Upload;

import java.util.Map;



/**
 * @author 朱春雨
 *文件上传下载
 */
public interface UploadMapper {
	
	 /**
	  * 文件的信息传入
	  * @param map 
	  */
	 void insert(Map<String,Object> map);
	 
	 /**
	  * 通过wbid获取文件信息
	  * @param map 
	  */
	 void selectbywbid(Map<String,Object> map);
     
	 /**
	  * 删除附件
	 * @param map 
	 */
	void deletefj(Map<String,Object> map);

	/**
	 * @param upload 
	 * 个人指标配置自动计分配置
	 */
	void addZbpzfile(Upload upload);

	/**
	 * 上传文件并返回id 
	 * @param map map对象
	 */
	void insertFileAndId(Map<String, Object> map);

	/**
	 * 通过id查找文件列表
	 * @param map map对象
	 */
	void selectbyid(Map<String, Object> map);

	/**
	 * 通过id更新wbid
	 * @param map map对象
	 */
	void updateWbidById(Map<String, Object> map);
	
	/**
	 * 通过外部id、类型查询文件/附件信息
	 * @param map 
	 */
	void getFjByTypeAndWbid(Map<String, Object> map);
	
	/**
	 * 添加文件/附件信息
	 * @param map 
	 */
	void addFjOutId(Map<String, Object> map);
	
	/**
	 * 根据外部id删除附件
	 * @param map 外部id
	 */
	void delFjByWbid(Map<String, Object> map);
	
	/**
	 * 分页展示附件详情
	 * 2018/4/12 徐武林新增
	 * @param map
	 */
	void getFjPageList(Map<String,Object> map);
	
}
