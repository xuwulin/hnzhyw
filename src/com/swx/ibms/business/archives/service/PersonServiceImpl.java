package com.swx.ibms.business.archives.service;

import com.swx.ibms.business.archives.bean.Person;
import com.swx.ibms.business.archives.bean.PersonJl;
import com.swx.ibms.business.archives.mapper.PersonMapper;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.system.bean.Upload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;



/**
 *
 * PersonServiceImpl.java  个人基本信息service实现类
 * @author east
 * @date<p>2017年5月16日</p>
 * @version 1.0
 * @description<p>实现具体对个人基本信息的CRUD操作</p>
 */
@SuppressWarnings("all")
@Service("personService")
public class PersonServiceImpl implements PersonService {

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;

	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(PersonServiceImpl.class);

	/**
	 * 个人基本信息的mapper接口
	 */
	@Resource
	private PersonMapper personMapper;

	@Override
	public List<Object> selectList(String dwbm,String gh,String daId) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Object> resultList = new ArrayList<Object>();
		/*if(StringUtils.isNotEmpty(dwbm)&&StringUtils.isNotEmpty(gh)
				&&StringUtils.isNotEmpty(daId)){
			map.put("p_dwbm", dwbm);
			map.put("p_gh", gh);
			map.put("p_da_id", daId);
			map.put("p_cursor1", StringUtils.EMPTY);//个人基本信息结果集
			map.put("p_cursor2", StringUtils.EMPTY);//个人经历信息结果集
			map.put("p_cursor3", StringUtils.EMPTY);//个人头像（附件）信息结果集
			try {
				//调用个人基本信息mapper进行查询
				personMapper.selectList(map);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				//记录日志
				logService.error(Constant.CURRENT_USER.get().getDwbm(),Constant.CURRENT_USER.get().getGh(),Constant.CURRENT_USER.get().getMc(),Constant.RZLX_CWRZ, e.toString());
			}

			//依次是个人基本信息结果集--个人经历信息结果集--个人头像（附件）信息结果集
			List<Person> personList = ((List<Person>)map.get("p_cursor1"));
			if(CollectionUtils.isNotEmpty(personList)){
				Person person = personList.get(0);
				resultList.add(person);
			}
			List<PersonJl> personJlList = (List<PersonJl>)map.get("p_cursor2");
			if(CollectionUtils.isNotEmpty(personJlList)){
				resultList.add(personJlList);
			}
			List<Upload> uploadList = (List<Upload>)map.get("p_cursor3");
			if(CollectionUtils.isNotEmpty(uploadList)){
				Upload upload = uploadList.get(0);
				resultList.add(upload);
			}
			return resultList;
		}*/

		//依次是个人基本信息结果集--个人经历信息结果集--个人头像（附件）信息结果集
		if(StringUtils.isNotBlank(dwbm)&&StringUtils.isNotBlank(gh)&&StringUtils.isNotBlank(daId)) {
			//1、个人基本信息
			Person person = null;
			try {
				person = personMapper.selectPerson(dwbm,gh,daId);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("查询个人基本信息失败！", e);
				//记录日志
				logService.error(Constant.CURRENT_USER.get().getDwbm(),Constant.CURRENT_USER.get().getGh(),Constant.CURRENT_USER.get().getMc(),Constant.RZLX_CWRZ, e.toString());
			}
			if (person != null) {
				resultList.add(person);
				//2、个人经历
				String grjbxxId = person.getId();
				List<PersonJl> personJlList = new ArrayList<PersonJl>();
				try {
					personJlList = personMapper.selectPersonJlList(grjbxxId);
				} catch (Exception e) {
					e.printStackTrace();
					LOG.error("查询个人经历失败！", e);
					//记录日志
					logService.error(Constant.CURRENT_USER.get().getDwbm(),Constant.CURRENT_USER.get().getGh(),Constant.CURRENT_USER.get().getMc(),Constant.RZLX_CWRZ, e.toString());
				}
				resultList.add(personJlList);
			}
		}
		return resultList;
	}

	@Override
	public Map<String,Object> insertGrjbxxData(Person person) {
		Map<String,Object> map = new HashMap<String,Object>();
		if (!Objects.isNull(person)) {
			if(StringUtils.isNotBlank(person.getDaId())) {
				//将页面数据组装到map
				map.put("p_dwbm", person.getDwbm());
				map.put("p_bmbm", person.getBmbm());
				map.put("p_gh", person.getGh());
				map.put("p_gender", person.getGender());
				map.put("p_nation", person.getNation());
				map.put("p_zzmm", person.getZzmm());
				map.put("p_zjlx", person.getZjlx());
				map.put("p_zjhm", person.getZjhm());
				map.put("p_jg", person.getJg());
				map.put("p_xzz", person.getXzz());
				map.put("p_jtzz", person.getJtzz());
				map.put("p_phone", person.getPhone());
				map.put("p_telephone", person.getTelephone());
				map.put("p_email", person.getEmail());
				map.put("p_gzdw", person.getGzdw());
				map.put("p_whcd", person.getWhcd());
				map.put("p_da_id", person.getDaId());
				map.put("p_birthday", person.getBirthday());
				map.put("p_sflb", person.getSflb());
				map.put("p_grade", person.getGrade());
				map.put("p_postInfo", person.getPostInfo());
				map.put("p_adminRank", person.getAdminRank());
				map.put("p_reDate", person.getReDate());
				map.put("p_xw", person.getXw());
				map.put("p_errmsg", StringUtils.EMPTY);
				map.put("p_id", StringUtils.EMPTY);
				try {
					//调用个人基本信息mapper
					personMapper.insertGrjbxxData(map);
				} catch (Exception e) {
					e.printStackTrace();
					LOG.error(e.getMessage(), e);
					//记录日志
					logService.error(Constant.CURRENT_USER.get().getDwbm(),
							Constant.CURRENT_USER.get().getGh(),
							Constant.CURRENT_USER.get().getMc(),
							Constant.RZLX_CWRZ, e.toString());
				}
				//获取操作返回值
				String message = (String)map.get("p_errmsg");
				String id = (String)map.get("p_id");

				if(StringUtils.isNotEmpty(message)&&StringUtils.isNotEmpty(id)){
					map.put("id", id);
					if ("1".equals(message)) {
						map.put("message", "操作成功!");
					}else{
						map.put("message", "操作失败!");
					}
					// 插入照片
					if (StringUtils.isNotEmpty(person.getZp())) {
						Integer res = 0;
						try {
							byte[] grzp = person.getZp().getBytes();
							res = personMapper.modifySfdaGrjbxxZpById(grzp,person.getZpName(),id);
						} catch (Exception e) {
							e.printStackTrace();
							LOG.error("照片存入数据库错误！", e);
							//记录日志
							logService.error(Constant.CURRENT_USER.get().getDwbm(),Constant.CURRENT_USER.get().getGh(),Constant.CURRENT_USER.get().getMc(),Constant.RZLX_CWRZ, e.toString());
						}
					}
					return map;
				}
			}
		}else{
			return null;
		}
		return null;
	}

	@Override
	public Map<String,Object> insertGrjlData(PersonJl personjl) {
		Map<String,Object> map = new HashMap<String,Object>();
		if (!Objects.isNull(personjl)) {
			if (StringUtils.isNotBlank(personjl.getType())
					&&StringUtils.isNotBlank(personjl.getDaId())) {

				// 转义英文的单引号和双引号
				String name = personjl.getName();
				if (StringUtils.isNotEmpty(name)) {
					name = name.replaceAll("'", "\\\"");
					name = name.replaceAll("\"", "\\\"");
				}

				String adress = personjl.getAdress();
				if (StringUtils.isNotEmpty(adress)) {
					adress = adress.replaceAll("'", "\\\"");
					adress = adress.replaceAll("\"", "\\\"");
				}

				String zwName = personjl.getZwName();
				if (StringUtils.isNotEmpty(zwName)) {
					zwName = zwName.replaceAll("'", "\\\"");
					zwName = zwName.replaceAll("\"", "\\\"");
				}

				String gzbm = personjl.getGzbm();
				if (StringUtils.isNotEmpty(gzbm)) {
					gzbm = gzbm.replaceAll("'", "\\\"");
					gzbm = gzbm.replaceAll("\"", "\\\"");
				}

				String gzzz = personjl.getGzzz();
				if (StringUtils.isNotEmpty(gzzz)) {
					gzzz = gzzz.replaceAll("'", "\\\"");
					gzzz = gzzz.replaceAll("\"", "\\\"");
				}

				//组装个人经历信息
				map.put("p_grjbxx_id",personjl.getPersonId());
				map.put("p_s_date",personjl.getsDate());
				map.put("p_e_date",personjl.geteDate());
				map.put("p_name",name);
				map.put("p_adress",adress);
				map.put("p_zw_name",zwName);
				map.put("p_type",personjl.getType());
				map.put("p_gzbm", gzbm);
				map.put("p_gzzz", gzzz);
				map.put("p_errmsg",StringUtils.EMPTY);
				map.put("p_id",StringUtils.EMPTY);
				map.put("p_da_id",personjl.getDaId());
				try {
					//调用个人经历信息mapper
					personMapper.insertGrjlData(map);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
					//记录日志
					logService.error(Constant.CURRENT_USER.get().getDwbm(),
							Constant.CURRENT_USER.get().getGh(),
							Constant.CURRENT_USER.get().getMc(),
							Constant.RZLX_CWRZ, e.toString());
				}
				//获取操作返回值
				String message = (String)map.get("p_errmsg");
				String id = (String)map.get("p_id");

				if(StringUtils.isNotEmpty(message)&&StringUtils.isNotEmpty(id)){
					map.put("id", id);
					if ("1".equals(message)) {
						map.put("message", "1");
					}else{
						map.put("message", "-1");
					}
					return map;
				}

			}
		}
		return null;
	}

	@Override
	public String updateGrjbxxData(Person person) {
		Map<String,Object> map = new HashMap<String,Object>();
		if (!Objects.isNull(person)) {
			if (StringUtils.isNotBlank(person.getId())) {
				map.put("p_id", person.getId());
				map.put("p_dwbm", person.getDwbm());
				map.put("p_bmbm", person.getBmbm());
				map.put("p_gh", person.getGh());
				map.put("p_gender", person.getGender());
				map.put("p_nation", person.getNation());
				map.put("p_zzmm", person.getZzmm());
				map.put("p_zjlx", person.getZjlx());
				map.put("p_zjhm", person.getZjhm());
				map.put("p_jg", person.getJg());
				map.put("p_xzz", person.getXzz());
				map.put("p_jtzz", person.getJtzz());
				map.put("p_phone", person.getPhone());
				map.put("p_telephone", person.getTelephone());
				map.put("p_email", person.getEmail());
				map.put("p_gzdw", person.getGzdw());
				map.put("p_whcd", person.getWhcd());
				map.put("p_da_id", person.getDaId());
				map.put("p_birthday", person.getBirthday());
				map.put("p_sflb", person.getSflb());
				map.put("p_grade", person.getGrade());
				map.put("p_postInfo", person.getPostInfo());
				map.put("p_adminRank", person.getAdminRank());
				map.put("p_reDate", person.getReDate());
				map.put("p_xw", person.getXw());
				map.put("p_errmsg", StringUtils.EMPTY);
				try {
					//调用个人基本信息mapper
					personMapper.updateGrjbxxData(map);
				} catch (Exception e) {
					e.printStackTrace();
					LOG.error(e.getMessage(), e);
					//记录日志
					logService.error(Constant.CURRENT_USER.get().getDwbm(),
							Constant.CURRENT_USER.get().getGh(),
							Constant.CURRENT_USER.get().getMc(),
							Constant.RZLX_CWRZ, e.toString());
				}
				//接收返回信息
				String msg = (String)map.get("p_errmsg");
				if ("1".equals(msg)) {
					return "操作成功!";
				}else{
					return "操作失败！";
				}
			}else{
				return null;
			}
		}
		return null;
	}

	@Override
	public String updateGrjlData(PersonJl personJl) {
		Map<String,Object> map = new HashMap<String,Object>();
		if (!Objects.isNull(personJl)) {
			if (StringUtils.isNotBlank(personJl.getId())) {

				// 转义英文的单引号和双引号
				String name = personJl.getName();
				if (StringUtils.isNotEmpty(name)) {
					name = name.replaceAll("'", "\\\"");
					name = name.replaceAll("\"", "\\\"");
				}

				String adress = personJl.getAdress();
				if (StringUtils.isNotEmpty(adress)) {
					adress = adress.replaceAll("'", "\\\"");
					adress = adress.replaceAll("\"", "\\\"");
				}

				String zwName = personJl.getZwName();
				if (StringUtils.isNotEmpty(zwName)) {
					zwName = zwName.replaceAll("'", "\\\"");
					zwName = zwName.replaceAll("\"", "\\\"");
				}

				String gzbm = personJl.getGzbm();
				if (StringUtils.isNotEmpty(gzbm)) {
					gzbm = gzbm.replaceAll("'", "\\\"");
					gzbm = gzbm.replaceAll("\"", "\\\"");
				}

				String gzzz = personJl.getGzzz();
				if (StringUtils.isNotEmpty(gzzz)) {
					gzzz = gzzz.replaceAll("'", "\\\"");
					gzzz = gzzz.replaceAll("\"", "\\\"");
				}

				map.put("p_id", personJl.getId());
				map.put("p_s_date", personJl.getsDate());
				map.put("p_e_date", personJl.geteDate());
				map.put("p_name", name);
				map.put("p_adress", adress);
				map.put("p_zw_name", zwName);
				map.put("p_type", personJl.getType());
				map.put("p_gzbm", gzbm);
				map.put("p_gzzz", gzzz);
				map.put("p_errmsg", StringUtils.EMPTY);
				try {
					//调用个人基本信息mapper
					personMapper.updateGrjlData(map);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
					//记录日志
					logService.error(Constant.CURRENT_USER.get().getDwbm(),
							Constant.CURRENT_USER.get().getGh(),
							Constant.CURRENT_USER.get().getMc(),
							Constant.RZLX_CWRZ, e.toString());
				}
				//接收返回信息
				String msg = (String)map.get("p_errmsg");
				if ("1".equals(msg)) {
					return "1";
				}else{
					return "-1";
				}
			}else{
				return null;
			}
		}
		return null;
	}

	@Override
	public String updateFjData(Upload upload) {
		Map<String,Object> map = new HashMap<String,Object>();
		if (!Objects.isNull(upload)) {
			if (StringUtils.isNotEmpty(upload.getId())) {
				map.put("p_id", upload.getId());
				map.put("p_wjmc", upload.getWjmc());
				map.put("p_wjdz", upload.getWjdz());
				map.put("p_fjlx", upload.getFjlx());
				map.put("p_errmsg", StringUtils.EMPTY);
				try {
					//调用个人基本信息mapper
					personMapper.updateFjData(map);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
					//记录日志
					logService.error(Constant.CURRENT_USER.get().getDwbm(),
							Constant.CURRENT_USER.get().getGh(),
							Constant.CURRENT_USER.get().getMc(),
							Constant.RZLX_CWRZ, e.toString());
				}
				//接收返回信息
				String msg = (String)map.get("p_errmsg");
				if ("1".equals(msg)) {
					return "操作成功!";
				}else{
					return "操作失败！";
				}
			}else{
				return null;
			}
		}


		return null;
	}

	@Override
	public String deleteGrjbxxData(Person person) {
		Map<String,Object> map = new HashMap<String,Object>();
		if (!Objects.isNull(person)) {
			if (StringUtils.isNotEmpty(person.getId())) {
				map.put("p_id", person.getId());
				map.put("p_errmsg", StringUtils.EMPTY);
				try {
					//调用个人基本信息mapper
					personMapper.deleteGrjbxxData(map);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
					//记录日志
					logService.error(Constant.CURRENT_USER.get().getDwbm(),
							Constant.CURRENT_USER.get().getGh(),
							Constant.CURRENT_USER.get().getMc(),
							Constant.RZLX_CWRZ, e.toString());
				}
				//接收返回信息
				String msg = (String)map.get("p_errmsg");
				if ("1".equals(msg)) {
					return "操作成功!";
				}else{
					return "操作失败！";
				}
			}else{
				return null;
			}
		}

		return null;
	}

	@Override
	public String deleteGrjlData(PersonJl personJl) {
		Map<String,Object> map = new HashMap<String,Object>();
		if (!Objects.isNull(personJl)) {
			if (StringUtils.isNotEmpty(personJl.getId())) {
				map.put("p_id", personJl.getId());
				map.put("p_errmsg", StringUtils.EMPTY);
				try {
					//调用个人基本信息mapper
					personMapper.deleteGrjlData(map);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
					//记录日志
					logService.error(Constant.CURRENT_USER.get().getDwbm(),
							Constant.CURRENT_USER.get().getGh(),
							Constant.CURRENT_USER.get().getMc(),
							Constant.RZLX_CWRZ, e.toString());
				}
				//接收返回信息
				String msg = (String)map.get("p_errmsg");
				if ("1".equals(msg)) {
					return "操作成功!";
				}else{
					return "操作失败！";
				}
			}else{
				return null;
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> insertGrFlData(Upload upload) {
		Map<String,Object> map = new HashMap<String,Object>();
		if (!Objects.isNull(upload)) {

			map.put("p_wjmc", upload.getWjmc());
			map.put("p_wjdz", upload.getWjdz());
			map.put("p_wbid", upload.getWbid());
			map.put("p_fjlx", upload.getFjlx());
			map.put("p_id", StringUtils.EMPTY);
			map.put("p_errmsg", StringUtils.EMPTY);
			try {
				//调用个人基本信息mapper
				personMapper.insertGrFlData(map);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				//记录日志
				logService.error(Constant.CURRENT_USER.get().getDwbm(),
						Constant.CURRENT_USER.get().getGh(),
						Constant.CURRENT_USER.get().getMc(),
						Constant.RZLX_CWRZ, e.toString());
			}

			//获取操作返回值
			String message = (String)map.get("p_errmsg");
			String id = (String)map.get("p_id");

			if(StringUtils.isNotEmpty(message)&&StringUtils.isNotEmpty(id)){
				map.put("id", id);
				if ("1".equals(message)) {
					map.put("message", "操作成功!");
				}else{
					map.put("message", "操作失败!");
				}
				return map;
			}
		}
		return null;
	}

	@Override
	public Map<String,Object> selectInfo(String dwbm, String gh) {
		Map<String,Object> map = new HashMap<>();
		Map<String,Object> resultMap = new HashMap<>();
		Person person = null;

		try {
			person = personMapper.selectInfo(dwbm, gh);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			//记录日志
			logService.error(Constant.CURRENT_USER.get().getDwbm(),
					Constant.CURRENT_USER.get().getGh(),
					Constant.CURRENT_USER.get().getMc(),
					Constant.RZLX_CWRZ, e.toString());
		}
		resultMap.put("data", person);
		return resultMap;
	}

	@Override
	public Map<String, Object> selectExperience(String daid, String grjbxxId) {
		List<Map<String,Object>> mapOfWork = new ArrayList<>();
		List<Map<String,Object>> mapOfStudy = new ArrayList<>();
		Map<String,Object> resultMap = new HashMap<>();
		List<Map<String, Object>> list = null;
		try {
			mapOfWork = personMapper.selectExpOfWork(daid, grjbxxId);
			mapOfStudy = personMapper.selectExpOfStudy(daid, grjbxxId);
		} catch (Exception e) {
			e.printStackTrace();
//			LOG.error(e.getMessage(), e);
//			//记录日志
//			logService.error(Constant.CURRENT_USER.get().getDwbm(),
//					Constant.CURRENT_USER.get().getGh(),
//					Constant.CURRENT_USER.get().getMc(),
//					Constant.RZLX_CWRZ, e.toString());
		}
		resultMap.put("dataOfWork", mapOfWork);
		resultMap.put("dataOfStudy", mapOfStudy);
		return resultMap;
	}

	@Override
	public Integer modifySfdaGrjbxxZpById(String zp,String id,String zpName) {
		Integer res = 0;
		try {
			byte[] grzp = zp.getBytes();
			res = personMapper.modifySfdaGrjbxxZpById(grzp,zpName,id);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("照片存入数据库错误！", e);
			//记录日志
			logService.error(Constant.CURRENT_USER.get().getDwbm(),Constant.CURRENT_USER.get().getGh(),Constant.CURRENT_USER.get().getMc(),Constant.RZLX_CWRZ, e.toString());
		}
		return res;
	}
}
