package com.swx.ibms.business.common.service;

import com.swx.ibms.business.system.bean.Upload;

import java.util.List;
import java.util.Map;


/**
 * 
 * UploadService.java  附件信息服务层接口
 * @author 朱春雨
 * @date<p></p>
 * @version 1.0
 * @description<p></p>
 */
public interface UploadService {
	/**
	 * 
	 * @author 朱春雨
	 *  文件的信息传入	
	 * @param upload 文件实体类
	 * @return 是否成功的标示 
	 */
	String insertFile(Upload upload);
	
	/**
	 * 通过传入的外部id获取文件信息
	 * @param wbid 外部id
	 * @return 附件信息结果集 
	 */
	List<Upload> selectbywbid(String wbid);
	   
	/**
	 * 删除附件
	 * @param p_id 附件id
	 * @return 是否成功的标示
	 */
	String deleteFj(String p_id);

	/**
	 * @param upload 
	 * 
	 */
	void addZbpzfile(Upload upload);


	/**
	 * 上传文件并返回文件的实体id
	 * @param upload 上传文件实体类
	 * @return List<String>
	 */
	List<String> insertFileAndId(Upload upload);

	/**
	 * @param id 实体id
	 * @return List<Upload>
	 */
	List<Upload> selectbyid(String id);

	/**
	 * @param id 实体id
	 * @param wbid 外部id
	 * @return 是否成功
	 */
	String updateWbidById(String id, String wbid);
	
	/**
	 * 通过外部id、类型查询文件/附件信息
	 * @param map 
	 * @return 文件/附件集合
	 */
	List<Upload> getFjByTypeAndWbid(Map<String, Object> map);
	
	/**
	 * 添加文件/附件信息
	 * @param map  
	 * @return 操作成功与否的字符串结果，以及附件id
	 */
	Map<String, Object> addFjOutId(Map<String, Object> map);
	
	/**
	 * 根据外部id删除附件
	 * @param wbid 外部id
	 * @return 是否成功的标示
	 */
	String delFjByWbid(String wbid);
	
	
	/**
	 * 展示附件详情
	 * 2018/4/12 徐武林新增
	 * @param ryjn 附件实体类
	 * @param start 分页下限
	 * @param end 分页上限
	 * @return
	 */
	Map<String,Object> getFjPageList(Upload upload,int start,int end);
}
