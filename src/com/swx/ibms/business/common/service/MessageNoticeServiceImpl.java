package com.swx.ibms.business.common.service;

import com.swx.ibms.business.common.bean.MessageNotice;
import com.swx.ibms.business.common.mapper.MessageNoticeMapper;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.common.utils.PageCommon;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;



/**
 * 
 * MessageNoticeServiceImpl.java 消息通知service实现类
 * @author east
 * @date<p>2017年5月25日</p>
 * @version 1.0
 * @description<p></p>
 */
@SuppressWarnings("all")
@Service("messageNoticeService")
public class MessageNoticeServiceImpl implements MessageNoticeService {

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;
	
	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(MessageNoticeServiceImpl.class);
	
	/**
	 * 消息通知mapper
	 */
	@Resource
	private MessageNoticeMapper msgNoticeMapper;
	
	@Override
	public PageCommon<MessageNotice> selectListPage(String content, String status,
													String dwbm, String gh, int nowPage, int pageSize) {
		Map<String,Object> map = new HashMap<String,Object>();
		PageCommon<MessageNotice> pageCommon = new PageCommon();
			int page = (nowPage-1)*pageSize;
			pageCommon.setPageSize(page+pageSize);//每页显示数
			page++;
			pageCommon.setNowPage(page);//当前页
		
			map.put("p_content", content);
			map.put("p_ststus", status);
			map.put("p_dwbm", dwbm);
			map.put("p_gh", gh);
			map.put("p_start", pageCommon.getNowPage());
			map.put("p_end", pageCommon.getPageSize());
			map.put("p_total", StringUtils.EMPTY);
			map.put("p_cursor", StringUtils.EMPTY);
			
			try {
				msgNoticeMapper.selectListPage(map);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				//记录日志
				logService.error(Constant.CURRENT_USER.get().getDwbm(),
						Constant.CURRENT_USER.get().getGh(),
						Constant.CURRENT_USER.get().getMc(), 
						Constant.RZLX_CWRZ, e.toString());
			}
			
			List<MessageNotice> list = (List<MessageNotice>)map.get("p_cursor");
			if (CollectionUtils.isNotEmpty(list)) {
				pageCommon.setList(list);
				pageCommon.setTotalRecords(Integer.parseInt(map.get("p_total").toString()));
			}
			
		return pageCommon;
	}

	@Override
	public String insertData(MessageNotice msgNotice) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(!Objects.isNull(msgNotice)){
			if (StringUtils.isNotEmpty(msgNotice.getContent())) {
				map.put("p_dwbm", msgNotice.getDwbm());
				map.put("p_bmbm", msgNotice.getBmbm());
				map.put("p_gh", msgNotice.getGh());
				map.put("p_name", msgNotice.getName());
				map.put("p_content", msgNotice.getContent());
				map.put("p_type", msgNotice.getType());
				map.put("p_operator", msgNotice.getOperator());
				map.put("p_id", StringUtils.EMPTY);
				map.put("p_errmsg", StringUtils.EMPTY);
				
				try {
					msgNoticeMapper.insertData(map);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
					//记录日志
					logService.error(Constant.CURRENT_USER.get().getDwbm(),
							Constant.CURRENT_USER.get().getGh(),
							Constant.CURRENT_USER.get().getMc(), 
							Constant.RZLX_CWRZ, e.toString());
				}
				
				if(!Objects.isNull(map.get("p_errmsg"))){
					if("1".equals(map.get("p_errmsg"))){
						return "操作成功！";
					}else{
						return "操作失败！";
					}
				}
			}
		}
		
		return null;
	}

	@Override
	public String updateDataStatus(String id) {
		Map<String,Object> map = new HashMap<String,Object>();
		if (StringUtils.isNotEmpty(id)) {
			map.put("p_id", id);
			map.put("p_errmsg", StringUtils.EMPTY);
			
			try {
				msgNoticeMapper.updateDataStatus(map);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				//记录日志
				logService.error(Constant.CURRENT_USER.get().getDwbm(),
						Constant.CURRENT_USER.get().getGh(),
						Constant.CURRENT_USER.get().getMc(), 
						Constant.RZLX_CWRZ, e.toString());
			}
			
			if (!Objects.isNull(map.get("p_errmsg"))) {
				if("1".equals(map.get("p_errmsg"))){
					return "操作成功！";
				}else{
					return "操作失败！";
				}
			}
			
		}
		
		return null;
	}

}
