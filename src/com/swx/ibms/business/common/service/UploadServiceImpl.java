package com.swx.ibms.business.common.service;

import com.swx.ibms.business.common.mapper.UploadMapper;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.system.bean.Upload;
import com.swx.ibms.common.utils.Identities;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;



/**
 * 
 * UploadServiceImpl.java
 * 
 * @author
 * @date
 * <p>
 * </p>
 * @version 1.0
 * @description
 * <p>
 * </p>
 */
@Service("UploadService")
@SuppressWarnings("all")
public class UploadServiceImpl implements UploadService {

	/**
	 * 文件上传的mapper
	 */
	@Resource
	private UploadMapper uploadMapper;

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;

	/**
	 * 
	 * @author 朱春雨 文件的信息传入
	 * @param upload
	 *            文件实体类
	 * 
	 * @return
	 */
	@Override
	public String insertFile(Upload upload) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("wjmc", upload.getWjmc());
		map.put("wjdz", upload.getWjdz());
		map.put("wbid", upload.getWbid());
		map.put("fjlx", upload.getFjlx());
		map.put("Y", StringUtils.EMPTY);
		uploadMapper.insert(map);
		return (String) map.get("Y");
	}

	/**
	 * 通过传入的外部id查询文件
	 */
	@Override
	public List<Upload> selectbywbid(String wbid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_wbid", wbid);
		map.put("p_cur", "");
		try {
			uploadMapper.selectbywbid(map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Upload> list = (List<Upload>) map.get("p_cur");
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}

	/**
	 * 删除附件
	 * 
	 * @param p_id
	 * @return
	 */
	@Override
	public String deleteFj(String p_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_id", p_id);
		map.put("Y", "0");
		try {
			uploadMapper.deletefj(map);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return (String) map.get("Y");
	}

	@Override
	public void addZbpzfile(Upload upload) {
		uploadMapper.addZbpzfile(upload);
	}

	@Override
	public List<String> insertFileAndId(Upload upload) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("wjmc", upload.getWjmc());
		map.put("wjdz", upload.getWjdz());
		map.put("wbid", upload.getWbid());
		map.put("fjlx", upload.getFjlx());
		map.put("id", StringUtils.EMPTY);
		map.put("Y", StringUtils.EMPTY);
		uploadMapper.insertFileAndId(map);
		List<String> list = new ArrayList<>();
		list.add((String) map.get("id"));
		list.add((String) map.get("Y"));
		return list;
	}

	@Override
	public List<Upload> selectbyid(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_id", id);
		map.put("p_cur", "");
		try {
			uploadMapper.selectbyid(map);
		} catch (Exception e) {
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, e.toString());
		}

		List<Upload> list = (List<Upload>) map.get("p_cur");
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list;
	}

	@Override
	public String updateWbidById(String id, String wbid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_id", id);
		map.put("p_wbid", wbid);
		map.put("Y", StringUtils.EMPTY);

		try {
			uploadMapper.updateWbidById(map);
		} catch (Exception e) {
			// 日志记录
			logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, e.toString());
		}
		String y = (String) map.get("Y");

		return y;
	}

	@Override
	public List<Upload> getFjByTypeAndWbid(Map<String, Object> map) {
		List<Upload> uploadList = new ArrayList<Upload>();
		if (!Objects.isNull(map) && StringUtils.isNotEmpty((String) map.get("p_wbid"))
				&& StringUtils.isNotEmpty((String) map.get("p_fjlx"))) {
			map.put("p_cursor", StringUtils.EMPTY);
			try {
				uploadMapper.getFjByTypeAndWbid(map);
			} catch (Exception e) {
				// 日志记录
				logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
						Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, e.toString());
			}
			uploadList = (List<Upload>) map.get("p_cursor");
		}
		return uploadList;
	}

	@Override
	public Map<String, Object> addFjOutId(Map<String, Object> map) {
		List<String> list = new ArrayList<String>();
		if (!Objects.isNull(map) && StringUtils.isNotEmpty((String) map.get("p_wbid"))) {
			map.put("p_id", Identities.get32LenUUID());
			map.put("p_scrq", new Date());
			map.put("p_msg", StringUtils.EMPTY);
			try {
				uploadMapper.addFjOutId(map);
				map.remove("p_scrq");
				map.remove("p_wjmc");
				map.remove("p_wjdz");
				map.remove("p_fjlx");
				map.remove("p_wbid");
			} catch (Exception e) {
				// 日志记录
				logService.error(Constant.CURRENT_USER.get().getDwbm(), Constant.CURRENT_USER.get().getGh(),
						Constant.CURRENT_USER.get().getMc(), Constant.RZLX_CWRZ, e.toString());
			}
			return map;
		}
		return null;
	}

	@Override
	public String delFjByWbid(String wbid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_wbid", wbid);
		map.put("p_msg", StringUtils.EMPTY);
		uploadMapper.delFjByWbid(map);
		return (String)map.get("p_msg");
	}

	/**
	 * 展示附件详情
	 */
	@Override
	public Map<String,Object> getFjPageList(Upload upload,int start,int end) {
		Map<String, Object> map = new HashMap<String, Object>(Constant.NUM_10);
		Map<String, Object> resMap = new HashMap<String, Object>(Constant.NUM_10);
		map.put("p_wbid", upload.getWbid());
		map.put("p_start", start);
		map.put("p_end", end);
		map.put("p_total", StringUtils.EMPTY);//总记录
		map.put("p_cursor", StringUtils.EMPTY);
		try {
			uploadMapper.getFjPageList(map);
			resMap.put("p_total", Integer.parseInt(map.get("p_total").toString()));
			resMap.put("p_cursor",map.get("p_cursor"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resMap;
	}
}
